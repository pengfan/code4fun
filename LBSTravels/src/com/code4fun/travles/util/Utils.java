package com.code4fun.travles.util;

import android.app.Activity;

import com.baidu.mapapi.BMapManager;
import com.code4fun.travles.MyApplication;

public class Utils {

    public static BMapManager initMapManager(Activity act) {
        BMapManager mBMapMan = new BMapManager(act);
        mBMapMan.init(MyApplication.instance.mStrKey, new MyApplication.MyGeneralListener());
        mBMapMan.getLocationManager().setNotifyInternal(10, 5);
        return mBMapMan;
    }

    public static void destoryMapManager(BMapManager mBMapMan) {
        if (mBMapMan != null) {
            mBMapMan.destroy();
            mBMapMan = null;
        }
    }
}
