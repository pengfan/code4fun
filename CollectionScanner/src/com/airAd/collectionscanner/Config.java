package com.airAd.collectionscanner;

import android.content.Context;

/**
 * 全局配置类，必须初始化使用
 * @author pengfan
 *
 */
public class Config {

    public static final String host = "http://192.168.1.16:9091"; 
   
    private static Config instance;
        
    private void Config()
    {}
    
    public static Config getInstance(){
        return instance;
    };
    
    public void init(Context context)
    {
        instance = new Config();
    }
}
