package com.airAd.collectionscanner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "card.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE IF NOT EXISTS").append(DATABASE_NAME).append("(")
        .append(Card.ID).append(" TEXT,")
        .append(Card.LAST_TIME).append(" TEXT,")
        .append(Card.AMOUNT).append(" INTEGER DEFAULT 0,")
        .append(Card.TYPE_ID).append(" TEXT").append(")");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void clear() {
        
    }
}
