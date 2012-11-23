package com.code4fun.travles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.code4fun.travles.model.MapPoint;
import com.code4fun.travles.model.Paragraph;
import com.code4fun.travles.model.TravelLine;
import com.code4fun.travles.ui.TravelFragment;

/**
 * 游记线路
 * @author pengfan
 *
 */
public class TravelsLineActivity extends FragmentActivity {

    private MapView mapView = null;
    private ViewPager viewPager = null;
    private List<TravelLine> lines;
    private ItemizedOverlay itemOverlay;
    private Drawable marker;
    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelslist);
        mapView = (MapView) findViewById(R.id.mapView);
        marker = getResources().getDrawable(R.drawable.map_pin); //得到需要标在地图上的资源
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        initData();
        initMapView();

        /*viewPager = (ViewPager) findViewById(R.id.pager);
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
        });*/
    }

    /**
     * 初始化静态数据
     */
    public void initData() {
        lines = new ArrayList<TravelLine>();

        TravelLine line = new TravelLine();
        line = new TravelLine();
        MapPoint point = new MapPoint();
        point.lon = 118.961666;
        point.lat = 32.156736;
        point.des = "栖霞山道";
        line.setPoint(point);
        Paragraph p =
                new Paragraph("xixia_1.jpeg", "9:18，南栖线。门票35，在站台买门票可以送两块钱的巴士费，还蛮人性化的。10:45到栖霞车站。栖霞车站（终点站）"
                        + "到栖霞寺还要步行一段距离，路上看到河和河边整整齐齐的菜畦，从小就读cai wa，打来打去打不出，最后发现自己果然还是没文化，" + "真正的读法叫cai qi……有勤劳的人儿在角落里工作，小小的生气……");
        line.addParagraph(p);
        lines.add(line);

        line = new TravelLine();
        point = new MapPoint();
        point.lon = 118.961841;
        point.lat = 32.157218;
        point.des = "栖霞山门";
        line.setPoint(point);
        p =
                new Paragraph("xixia_2.jpeg", "栖霞寺前有一根长长的步道，步道两边种满了梧桐，梧桐的分叉那么低，一看就是南京的样子，" + "虽然叶色没有杭州的北山路那么美，但也满足了，阳光洒在地上，窸窸窣窣。走到头就看到红墙、"
                        + "小狮子，一如所有的庙宇一样。两个力士，四个金刚。金刚很萌，我摸出口袋里仅有的零钱，给南方广目天王，带着娃娃稚气的菩萨……欢喜的。");

        line.addParagraph(p);
        lines.add(line);

        line = new TravelLine();
        point = new MapPoint();
        point.lon = 118.963049;
        point.lat = 32.158001;
        point.des = "栖霞池塘";
        line.setPoint(point);
        p = new Paragraph("xixia_3.jpeg", "穿过小小的山门继续左行便看到一个池塘，池塘的那一边是我看到的第一抹橙……");
        line.addParagraph(p);
        p =
                new Paragraph("xixia_4.jpeg", "以下的一些照片都为D3X出品，因为层次太好，没舍得P，如觉得寡淡，自动掠过。自己却觉得很好啊，那种真实的色泽，"
                        + "仿佛可以看到叶片的舞动，和阳光的跳跃。喜欢D3X预览时进深变化的瞬间，那么的迅速，所见即所得。那种即时的回馈，让自己感觉重要。");
        line.addParagraph(p);
        p = new Paragraph("xixia_5.jpeg", "枫树是一种很奇怪的植物，枫叶的背光面才是橙红色的，迎光面却是淡粉色，所以对着天空，它才是最艳丽的。叶片都在不可控的随风飘舞，你就对着它，等着它倏然安静的那一刻。");
        line.addParagraph(p);
        p = new Paragraph("xixia_6.jpeg", "屏息凝视。");
        line.addParagraph(p);
        lines.add(line);

    }

    private void initCenter() {
        int size = lines.size();
        GeoPoint center = lines.get(0).getPoint().getGeoPoint();
        if (size > 1) {
            GeoPoint last = lines.get(size - 1).getPoint().getGeoPoint();
            center =
                    new GeoPoint((center.getLatitudeE6() + last.getLatitudeE6()) / 2, (center.getLongitudeE6() + last.getLongitudeE6()) / 2);
        }
        final GeoPoint centerPoint = center;
        mapView.postDelayed((new Runnable() {
            @Override
            public void run() {
                mapView.getController().animateTo(centerPoint);
            }
        }), 200);
    }

    //TODO:增加path效果，参考apiDemo里的com.example.android.apis.graphics.PathEffects
    /**
     * 刷新地图
     * @param index
     */
    public void refreshMapView(int index) {
        if (itemOverlay != null && mapView.getOverlays().contains(itemOverlay)) {
            mapView.getOverlays().remove(itemOverlay);
        }
        itemOverlay = new OverItemT(marker);
        mapView.getOverlays().add(itemOverlay);
        mapView.getController().setZoom(mapView.getMaxZoomLevel());
    }

    public void initMapView() {
        MyApplication app = (MyApplication) getApplication();
        if (app.mBMapMan == null) {
            app.mBMapMan = new BMapManager(getApplication());
            app.mBMapMan.init(MyApplication.mStrKey, new MyApplication.MyGeneralListener());
        }

        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.mBMapMan);

        mapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mapView.setDrawOverlayWhenZooming(true);

        refreshMapView(0);
        initCenter();
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
            /*fragement.setInroduction(line);*/
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

        public OverItemT(Drawable marker) {
            super(boundCenterBottom(marker));

            this.marker = marker;
            // 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
            for (TravelLine line : lines) {
                if (line.getPoint() != null) {
                    mGeoList.add(new OverlayItem(line.getPoint().getGeoPoint(), "", ""));
                }
            }
            populate(); //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
        }

        public void updateOverlay() {
            populate();
        }

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {

            /* // Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
             Projection projection = mapView.getProjection();
             Path p = new Path();
             for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
                 OverlayItem overLayItem = getItem(index); // 得到给定索引的item

                 //String title = overLayItem.getTitle();
                 // 把经纬度变换到相对于MapView左上角的屏幕像素坐标
                 Point point = projection.toPixels(overLayItem.getPoint(), null);
                 p.moveTo(0, 0);
                 p.lineTo(point.x, point.y);

                  paint.setPathEffect(mEffects[i]);
                  mPaint.setColor(mColors[i]);
                  canvas.drawPath(mPath, mPaint);

                 // 可在此处添加您的绘制代码
                  Paint paintText = new Paint();
                  paintText.setColor(Color.BLUE);
                  paintText.setTextSize(15);
                  canvas.drawText(title, point.x - 30, point.y, paintText); // 绘制文本
             }

             
             //调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
             boundCenterBottom(marker);*/
            super.draw(canvas, mapView, shadow);
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
