package com.code4fun.travles;

import java.util.Stack;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;

/**
 * 保存地图key相关数据，以及全局区域
 * 
 * @author pengfan
 *
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    public BMapManager mBMapMan = null;
    public static String mStrKey = "41C407C88F3A6839FD45B913C8213F21C6E72A08";
    boolean m_bKeyRight = true;
    private Stack<Object> stack;

    public static class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetNetworkState(int iError) {
            Log.w("MyGeneralListener", "onGetNetworkState error is " + iError);
            Toast.makeText(instance.getApplicationContext(), R.string.network_init_error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onGetPermissionState(int iError) {
            Log.w("MyGeneralListener", "onGetPermissionState error is " + iError);
            if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
                instance.m_bKeyRight = false;
            }
        }
    }

    public Object pop() {
        return stack.pop();
    }

    public void push(Object obj) {
        stack.push(obj);
    }

    @Override
    public void onCreate() {
        Log.v("SophiaApplication", "onCreate");
        instance = this;
        mBMapMan = new BMapManager(this);
        mBMapMan.init(this.mStrKey, new MyGeneralListener());
        mBMapMan.getLocationManager().setNotifyInternal(10, 5);
        stack = new Stack<Object>();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        if (mBMapMan != null) {
            mBMapMan.destroy();
            mBMapMan = null;
        }
        instance = null;
        super.onTerminate();
    }
}
