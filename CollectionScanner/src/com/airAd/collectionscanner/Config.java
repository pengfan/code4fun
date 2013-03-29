package com.airAd.collectionscanner;

import android.content.Context;

/**
 * 全局配置类，必须初始化使用
 * @author pengfan
 *
 */
public class Config {

    //public static final String host = "http://192.168.1.247:8860"; 
    public static final String host = "http://192.168.1.204:9090";
    
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
