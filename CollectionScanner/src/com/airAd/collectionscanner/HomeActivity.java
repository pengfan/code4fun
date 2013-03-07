package com.airAd.collectionscanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airAd.collectionscanner.data.Card;
import com.airAd.collectionscanner.data.CardDataSource;
import com.google.zxing.client.android.CaptureActivity;

public class HomeActivity extends BaseActivity {
    /** Called when the activity is first created. */
    private TextView textView;
    private ListView listView;
    private CardDataSource dataSource;
    private List<Card> cardList = new ArrayList<Card>();
    private StartAdapter adapter;
    
    public static final int SCAN_SEAL = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        textView = findView(R.id.card_show);
        listView = findView(R.id.stat_show);
        adapter = new StartAdapter();
        listView.setAdapter(adapter);
        dataSource = new CardDataSource(this);
    }

    public void scan(View view) {
        startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_SEAL);
    }
    
    public void stat(View view) {
        cardList = dataSource.query();
        adapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_SEAL) {
            if (resultCode == RESULT_OK) {
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                String id = data.getStringExtra(CaptureActivity.FLAG);
                Card card = dataSource.query(id);
                if (card == null) {
                    dataSource.insert(id, 1);
                    textView.setText("该用户首次使用\n累计使用点数 1");
                } else {
                    dataSource.update(id, card.getAmount() + 1);
                    textView.setText("该用户累计使用点数" + (card.getAmount() + 1));
                }
            }
        }
    }
    
    private class StartAdapter extends BaseAdapter
    {

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
            if(convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.start_item, null);
            }
            ((TextView)convertView.findViewById(R.id.index)).setText("序号:" + position);
            ((TextView)convertView.findViewById(R.id.count)).setText("消费次数:" + cardList.get(position).getAmount());
            return convertView;
        }
        
    }

}
