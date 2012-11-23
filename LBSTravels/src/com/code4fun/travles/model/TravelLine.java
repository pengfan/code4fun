package com.code4fun.travles.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅行路线
 * @author pengfan
 *
 */
public class TravelLine {

    private MapPoint point;
    private List<Paragraph> paragraphes;

    public TravelLine() {
        paragraphes = new ArrayList<Paragraph>();
    }

    public MapPoint getPoint() {
        return point;
    }

    public void setPoint(MapPoint point) {
        this.point = point;
    }

    public List<Paragraph> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(List<Paragraph> paragraphes) {
        this.paragraphes = paragraphes;
    }

    public void addParagraph(Paragraph paragraph) {
        paragraphes.add(paragraph);
    }

    public void removeParagraph(Paragraph paragraph) {
        paragraphes.remove(paragraph);
    }

}
