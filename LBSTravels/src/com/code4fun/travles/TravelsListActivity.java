package com.code4fun.travles;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;

/**
 * 游记列表
 * @author pengfan
 *
 */
public class TravelsListActivity extends MapActivity {

    private MapView mapView = null;
    private ViewPager viewPager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelslist);
        mapView = (MapView) findViewById(R.id.mapView);
        initMapView();
        viewPager = (ViewPager) findViewById(R.id.pager);
    }

    public void initMapView() {
        MyApplication app = (MyApplication) getApplication();
        if (app.mBMapMan == null) {
            app.mBMapMan = new BMapManager(getApplication());
            app.mBMapMan.init(app.mStrKey, new MyApplication.MyGeneralListener());
        }
        app.mBMapMan.start();
        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.mBMapMan);

        mapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mapView.setDrawOverlayWhenZooming(true);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
