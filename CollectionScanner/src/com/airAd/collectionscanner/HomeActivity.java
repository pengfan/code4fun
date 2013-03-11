package com.airAd.collectionscanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airAd.collectionscanner.data.Card;
import com.airAd.collectionscanner.data.CardDataSource;
import com.airAd.collectionscanner.net.Response;
import com.airAd.collectionscanner.service.CardSynService;
import com.airAd.collectionscanner.worker.NetWorker;
import com.airAd.collectionscanner.worker.NetWorkerHandler;
import com.google.zxing.client.android.CaptureActivity;

public class HomeActivity extends BaseActivity {
	/** Called when the activity is first created. */
	private TextView textView;
	private ListView listView;
	private TextView topShow;
	private CardDataSource dataSource;
	private List<Card> cardList = new ArrayList<Card>();
	private StartAdapter adapter;
	private NetWorker networker;

	public static final String CARD_SHARED = "card_shared";
	public static final String SYNC = "sync";
	public static final int SCAN_SEAL = 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		networker = new NetWorker(this);
		textView = findView(R.id.card_show);
		listView = findView(R.id.stat_show);
		topShow = findView(R.id.top_show);
		adapter = new StartAdapter();
		listView.setAdapter(adapter);
		dataSource = new CardDataSource(this);
		changeSynView();
	}

	public void scan(View view) {
		startActivityForResult(new Intent(this, CaptureActivity.class),
				SCAN_SEAL);
	}

	public void stat(View view) {
		cardList = dataSource.query();
		adapter.notifyDataSetChanged();
		listView.setVisibility(View.VISIBLE);
		textView.setVisibility(View.GONE);
	}

	public void sync(View view) {
		sync(false);
	}
	
	private void sync(final boolean isBackground)
	{
		setSyncing();
		List<Card> cardList = dataSource.query();
		CardSynService service = new CardSynService();
		service.setSynList(cardList);
		networker.request(service, new NetWorkerHandler() {

			@Override
			public void progressUpdate(long current, long allLength) {
			}

			@Override
			public void handleData(Response rsp) {
				if(rsp.getStatus() != 200  && !isBackground){
					Toast.makeText(HomeActivity.this, "数据同步失败，请检查网络",
							Toast.LENGTH_LONG).show();
				}
				changeSyncStatus(rsp.getStatus() == 200);
				changeSynView();
			}
		});
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
				// 每次修改完后会同步一遍
				sync(false);
			}
		}
	}

	private void changeSyncStatus(boolean synced) {
		getSharedPreferences(CARD_SHARED, 0).edit().putBoolean(SYNC, synced)
				.commit();
	}

	private void setSyncing()
	{
		topShow.setText(R.string.syncing);
		topShow.setTextColor(Color.DKGRAY);
	}
	
	private void changeSynView() {
		if (getSharedPreferences(CARD_SHARED, 0).getBoolean(SYNC, false)) {
			topShow.setText(R.string.aready_sync);
			topShow.setTextColor(Color.GREEN);
		} else {
			topShow.setText(R.string.not_sync);
			topShow.setTextColor(Color.RED);
		}
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
				convertView = getLayoutInflater().inflate(R.layout.start_item,
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
