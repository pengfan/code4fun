package com.airAd.collectionscanner;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airAd.collectionscanner.data.Card;
import com.airAd.collectionscanner.data.CardDataSource;
import com.airAd.collectionscanner.fragment.ScanResultFragment;
import com.airAd.collectionscanner.fragment.StatFragment;
import com.airAd.collectionscanner.net.Response;
import com.airAd.collectionscanner.service.CardSynService;
import com.airAd.collectionscanner.worker.NetWorker;
import com.airAd.collectionscanner.worker.NetWorkerHandler;
import com.google.zxing.client.android.CaptureActivity;

public class HomeActivity extends BaseActivity {

	private TextView topShow;
	private NetWorker networker;
	private CardDataSource dataSource;

	private ScanResultFragment scanResultFragment;
	private StatFragment statFragment;

	public static final String CARD_SHARED = "card_shared";
	public static final String SYNC = "sync";
	public static final int SCAN_SEAL = 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		scanResultFragment = new ScanResultFragment();
		statFragment = new StatFragment();
		
		dataSource = new CardDataSource(this);
		networker = new NetWorker(this);
		topShow = findView(R.id.top_show);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		Log.i("deviceId", deviceId);
		changeSynView();
		init();
	}
	
	public void init()
	{
	    changeFragment(scanResultFragment);
	}
	
	private void changeFragment(Fragment fragment)
	{
	    Fragment lastFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
	    if(lastFragment == fragment)
	        return ;
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    if(lastFragment != null)
	    {
	        ft.detach(lastFragment);
	    }
        if(!fragment.isDetached())
        {
            ft.add(R.id.frame_layout, fragment);
        }
        else
        {
            ft.attach(fragment);
        }
	    ft.commit();
	}

	public void scan(View view) {
	    changeFragment(scanResultFragment);
		startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_SEAL);
	}

	public void stat(View view) {
	    changeFragment(statFragment);
    }

	public void sync(View view) {
		sync(false);
		/*TestService tService = new TestService();
		tService.setUrl("http://192.168.1.247:8860/passbook/down/1271.pkpass");
		networker.request(tService, new NetWorkerHandler() {
            
            @Override
            public void progressUpdate(long current, long allLength) {
                
            }
            
            @Override
            public void handleData(Response rsp) {
                
            }
        });*/
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
				String id = data.getStringExtra(CaptureActivity.FLAG);
				Card card = dataSource.query(id);
				if (card == null) {
					dataSource.insert(id, 1);
					scanResultFragment.setResult("该用户首次使用\n累计使用点数 1");
				} else {
					dataSource.update(id, card.getAmount() + 1);
					scanResultFragment.setResult("该用户累计使用点数" + (card.getAmount() + 1));
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

}
