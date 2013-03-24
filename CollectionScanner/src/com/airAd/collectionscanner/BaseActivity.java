package com.airAd.collectionscanner;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity extends SherlockFragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
    }
    
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
