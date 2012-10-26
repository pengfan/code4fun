package com.code4fun.travles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.code4fun.travles.model.MapPoint;
import com.code4fun.travles.model.TravelIntroduction;
import com.code4fun.travles.ui.TravelFragment;

/**
 * 游记列表
 * @author pengfan
 *
 */
public class TravelsListActivity extends FragmentActivity {

    private MapView mapView = null;
    private ViewPager viewPager = null;
    private List<TravelIntroduction> introduction;
    private ItemizedOverlay itemOverlay;
    private Drawable marker;
    private int lastIndex = -1;
    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelslist);
        mapView = (MapView) findViewById(R.id.mapView);
        initMapView();
        initData();
        marker = getResources().getDrawable(R.drawable.map_pin); //得到需要标在地图上的资源
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

        refreshMapView(0);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), 3));
        viewPager.setOffscreenPageLimit(2);

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                refreshMapView(pos);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 初始化静态数据
     */
    public void initData() {
        introduction = new ArrayList<TravelIntroduction>();
        for (int i = 0; i < 3; i++) {
            TravelIntroduction t1 = new TravelIntroduction();
            MapPoint point = new MapPoint();
            point.lon = 118.974542;
            point.lat = 32.16208;
            point.des = "栖霞山";
            t1.addPoint(point);
            point = new MapPoint();
            point.lon = 118.795995;
            point.lat = 32.028142;
            point.des = "夫子庙";
            t1.addPoint(point);
            t1.setAuthor("jiashan529(shanghai)");
            t1.setTitle(i + "栖霞浸染之view of nanking 20101203-04（肆）");
            try {
                t1.setRecordTime(FORMATER.parse("2011-04-22"));
            } catch (ParseException e) {

            }
            t1.setIntroduction("那次去南京是要去看红叶和银杏，突然想去，那么想，就去了。" + "去了之后的周一和thechens83居然拿出了一样的小玩意，两个人，无约定的，在同一时间去了同一个地方，没相遇。");
            introduction.add(t1);
        }
    }

    /**
     * 刷新地图
     * @param index
     */
    public void refreshMapView(int index) {
        if (lastIndex != index) {
            if (itemOverlay != null && mapView.getOverlays().contains(itemOverlay)) {
                mapView.getOverlays().remove(itemOverlay);
            }
            itemOverlay = new OverItemT(marker, index);
            mapView.getOverlays().add(itemOverlay);
            lastIndex = index;
        }
        GeoPoint center = introduction.get(index).getLine().get(0).getGeoPoint();
        int size = introduction.get(index).getLine().size();
        if (size > 1) {
            GeoPoint last = introduction.get(index).getLine().get(size - 1).getGeoPoint();
            center =
                    new GeoPoint((center.getLatitudeE6() + last.getLatitudeE6()) / 2, (center.getLongitudeE6() + last.getLongitudeE6()) / 2);

        }
        if (lastIndex == -1) {
            mapView.getController().setCenter(center);
        } else {
            mapView.getController().animateTo(center);
        }

        // 

    }

    public void initMapView() {
        MyApplication app = (MyApplication) getApplication();
        if (app.mBMapMan == null) {
            app.mBMapMan = new BMapManager(getApplication());
            app.mBMapMan.init(app.mStrKey, new MyApplication.MyGeneralListener());
        }

        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.mBMapMan);

        mapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mapView.setDrawOverlayWhenZooming(true);
    }

    @Override
    protected void onPause() {
        MyApplication app = (MyApplication) getApplication();
        if (app.mBMapMan != null)
            app.mBMapMan.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MyApplication app = (MyApplication) getApplication();
        if (app.mBMapMan != null)
            app.mBMapMan.start();
        super.onResume();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    private class TabAdapter extends FragmentStatePagerAdapter {
        private int size;

        public TabAdapter(FragmentManager fm, int size) {
            super(fm);
            this.size = size;
        }

        @Override
        public Fragment getItem(int pos) {
            TravelFragment fragement = new TravelFragment();
            fragement.setInroduction(introduction.get(pos));
            return fragement;
        }

        @Override
        public int getCount() {
            return size;
        }

    }

    class OverItemT extends ItemizedOverlay<OverlayItem> {

        private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
        private Drawable marker;

        public OverItemT(Drawable marker, int index) {
            super(boundCenterBottom(marker));

            this.marker = marker;

            // 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
            for (MapPoint mp : introduction.get(index).getLine()) {
                mGeoList.add(new OverlayItem(mp.getGeoPoint(), "", ""));
            }
            populate(); //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
        }

        public void updateOverlay() {
            populate();
        }

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {

            // Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
            Projection projection = mapView.getProjection();
            for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
                OverlayItem overLayItem = getItem(index); // 得到给定索引的item

                String title = overLayItem.getTitle();
                // 把经纬度变换到相对于MapView左上角的屏幕像素坐标
                Point point = projection.toPixels(overLayItem.getPoint(), null);

                // 可在此处添加您的绘制代码
                Paint paintText = new Paint();
                paintText.setColor(Color.BLUE);
                paintText.setTextSize(15);
                canvas.drawText(title, point.x - 30, point.y, paintText); // 绘制文本
            }

            super.draw(canvas, mapView, shadow);
            //调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
            boundCenterBottom(marker);
        }

        @Override
        protected OverlayItem createItem(int i) {
            return mGeoList.get(i);
        }

        @Override
        public int size() {
            return mGeoList.size();
        }

        /* @Override
         // 处理当点击事件
         protected boolean onTap(int i) {
             setFocus(mGeoList.get(i));
             // 更新气泡位置,并使之显示
             GeoPoint pt = mGeoList.get(i).getPoint();
             ItemizedOverlayDemo.mMapView.updateViewLayout(ItemizedOverlayDemo.mPopView, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
                     LayoutParams.WRAP_CONTENT, pt, MapView.LayoutParams.BOTTOM_CENTER));
             ItemizedOverlayDemo.mPopView.setVisibility(View.VISIBLE);
             Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(), Toast.LENGTH_SHORT).show();
             return true;
         }

         @Override
         public boolean onTap(GeoPoint arg0, MapView arg1) {
             // TODO Auto-generated method stub
             // 消去弹出的气泡
             ItemizedOverlayDemo.mPopView.setVisibility(View.GONE);
             return super.onTap(arg0, arg1);
         }*/
    }

}
