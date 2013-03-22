package com.airAd.collectionscanner.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airAd.collectionscanner.R;
import com.airAd.collectionscanner.data.Card;
import com.airAd.collectionscanner.data.CardDataSource;

public class StatFragment extends Fragment {

    private ListView listView;
    private CardDataSource dataSource;
    private StartAdapter adapter;
    private List<Card> cardList = new ArrayList<Card>();
    
    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stat, null);
        listView = (ListView)v.findViewById(R.id.list);
        adapter = new StartAdapter();
        listView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new CardDataSource(getActivity());
    }
    
    @Override
    public void onStart()
    {
        super.onStart();
        cardList = dataSource.query();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    
    private class StartAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cardList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.start_item,
                        null);
            }
            ((TextView) convertView.findViewById(R.id.index)).setText("序号:"
                    + position);
            ((TextView) convertView.findViewById(R.id.count)).setText("消费次数:"
                    + cardList.get(position).getAmount());
            return convertView;
        }

    }

}
