package com.code4fun.travles.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code4fun.travles.R;
import com.code4fun.travles.TravelsLineActivity;
import com.code4fun.travles.model.TravelIntroduction;

/**
 * 游记的详细页面
 * @author pengfan
 *
 */
public class TravelFragment extends Fragment {

    private TextView title;
    private TextView description;
    private TravelIntroduction inroduction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.travel_fragment, container, false);
        title = (TextView) v.findViewById(R.id.titleText);
        description = (TextView) v.findViewById(R.id.description);
        v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TravelsLineActivity.class));
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title.setText(inroduction.getTitle());
        description.setText(inroduction.getIntroduction());
    }

    public TravelIntroduction getInroduction() {
        return inroduction;
    }

    public void setInroduction(TravelIntroduction inroduction) {
        this.inroduction = inroduction;
    }

}
