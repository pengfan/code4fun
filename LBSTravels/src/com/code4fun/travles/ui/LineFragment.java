package com.code4fun.travles.ui;

import android.app.Fragment;

import com.code4fun.travles.model.TravelLine;

/**
 * 一条路线上的展示
 * @author pengfan
 *
 */
public class LineFragment extends Fragment {

    private TravelLine line;

    public TravelLine getLine() {
        return line;
    }

    public void setLine(TravelLine line) {
        this.line = line;
    }

}
