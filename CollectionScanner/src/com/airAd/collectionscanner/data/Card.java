package com.airAd.collectionscanner.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Card {

    public static final String TABLE = "card";
    public static final String ID = "id";
    public static final String AMOUNT = "amount";
    public static final String LAST_TIME = "lasttime";
    public static final String TYPE_ID = "typeid";
    public static final String DEFAULT_TYPE_ID = "storeCard";
    
    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd"); 
    
    private String id;
    private int amount;
    private Date lastTime;
    private String typeId;
    

    public Card() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
   
    public String getStrLastTime()
    {
        if(lastTime == null)
            return null;
        else
            return formater.format(lastTime);
    }
    
    public void setStrLastTime(String lastTimeStr)
    {
        if(lastTimeStr != null)
        {
            try {
                lastTime = formater.parse(lastTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
                lastTime = null;
            }
        }
        else
        {
            lastTime = null;
        }
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public static String formatTime(Date date)
    {
        if(date == null)
            return null;
        else
            return formater.format(date);
    }
}
