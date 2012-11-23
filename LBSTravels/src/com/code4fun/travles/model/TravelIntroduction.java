package com.code4fun.travles.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 游记介绍页
 * @author pengfan
 *
 */
public class TravelIntroduction {

    private List<MapPoint> line;
    private String avatarUrl;
    private String title;
    private String author;
    private Date recordTime;
    private String introduction;
    private String id;

    public TravelIntroduction() {
        line = new ArrayList<MapPoint>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLine(List<MapPoint> line) {
        this.line = line;
    }

    public List<MapPoint> getLine() {
        return line;
    }

    public void addPoint(MapPoint p) {
        line.add(p);
    }

    public MapPoint getPoint(int index) {
        return line.get(index);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
