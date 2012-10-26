package com.code4fun.travles.model;

import com.baidu.mapapi.GeoPoint;

/**
 * 地图位置
 * @author pengfan
 *
 */
public class MapPoint {

    public double lat;
    public double lon;
    public String des;

    public GeoPoint getGeoPoint() {
        return new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
    }
}
