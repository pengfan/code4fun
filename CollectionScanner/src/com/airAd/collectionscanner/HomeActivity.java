package com.airAd.collectionscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;

public class HomeActivity extends BaseActivity {
    /** Called when the activity is first created. */
    private TextView textView;

    public static final int SCAN_SEAL = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void scan(View view) {
        startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_SEAL);
    }
}
