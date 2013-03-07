package com.airAd.collectionscanner.service;

import java.util.List;

import org.apache.http.HttpResponse;

import com.airAd.collectionscanner.Config;
import com.airAd.collectionscanner.data.Card;
import com.airAd.collectionscanner.net.BasicService;
import com.airAd.collectionscanner.net.Response;

public class CardSynService extends BasicService {

    public CardSynService() {
        super(TYPE_POST);
    }

    @Override
    public String getRemoteUrl() {
        return Config.host + "/passapi/consume";
    }

    public void setSynList(List<Card> list) {
        StringBuffer snBuffer = new StringBuffer();
        StringBuffer countBuffer = new StringBuffer();
        boolean head = true;
        for (Card card : list) {
            if (!head) {
                snBuffer.append(",");
                countBuffer.append(",");
            } else {
                head = false;
            }
            snBuffer.append(card.getId());
            countBuffer.append(card.getAmount());
        }
        putString("sn", snBuffer.toString());
        putString("c", countBuffer.toString());
    }

    @Override
    public void handleResponse(HttpResponse httpRsp, Response rsp) {
    }

}
