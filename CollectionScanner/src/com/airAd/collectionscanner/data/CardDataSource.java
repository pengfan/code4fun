package com.airAd.collectionscanner.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CardDataSource {
    
    private MySQLiteOpenHelper helper;
    private Context context;
    
    private static final String[] CAED_QUERY_ARRAY  = new String[]{Card.ID, Card.AMOUNT, Card.LAST_TIME, Card.TYPE_ID};
    private static final String ID_WHERE =  Card.ID + " = ?";
    
    public CardDataSource(Context context){
        this.context = context;
        this.helper = new MySQLiteOpenHelper(context);
    }
    
    public List<Card> query()
    {
        List<Card> res = new ArrayList<Card>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(Card.TABLE, CAED_QUERY_ARRAY, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            res.add(generateCard(cursor));
            cursor.moveToNext();
        }
        db.close();
        return res;
    }
    
    public List<Card> query(String id)
    {
        List<Card> res = new ArrayList<Card>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(Card.TABLE, CAED_QUERY_ARRAY, ID_WHERE, new String[]{id}, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            res.add(generateCard(cursor));
            cursor.moveToNext();
        }
        db.close();
        return res;
    }
    
    public void insert(String id, int amount)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Card.ID, id);
        contentValues.put(Card.AMOUNT, amount);
        contentValues.put(Card.LAST_TIME, Card.formatTime(new Date()));
        contentValues.put(Card.TYPE_ID, Card.DEFAULT_TYPE_ID);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(Card.TABLE, null, contentValues);
        db.close();
    }
    
    public void update(String id, int amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Card.AMOUNT, amount);
        contentValues.put(Card.LAST_TIME, Card.formatTime(new Date()));
        contentValues.put(Card.TYPE_ID, Card.DEFAULT_TYPE_ID);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(Card.TABLE, contentValues, ID_WHERE, new String[]{id});
        db.close();
    }
    
    private Card generateCard(Cursor cursor)
    {
        Card card = new Card();
        card.setId(cursor.getString(0));
        card.setAmount(cursor.getInt(1));
        card.setStrLastTime(cursor.getString(2));
        card.setTypeId(cursor.getString(3));
        return card;
    }

}
