package com.airAd.collectionscanner.service;

import org.apache.http.HttpResponse;

import com.airAd.collectionscanner.net.BasicService;
import com.airAd.collectionscanner.net.Response;

public class TestService extends BasicService{
    private String url;
    
    public TestService() {
        super(TYPE_GET);
        addHeader("User-Agent", "MQQBrowser/3.5/Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; Desire HD Build/FRG83D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
    @Override
    public String getRemoteUrl() {
        return url;
    }

    @Override
    public void handleResponse(HttpResponse httpRsp, Response rsp) {
       System.out.println(httpRsp);
       System.out.println(httpRsp.getAllHeaders());
    }
}
