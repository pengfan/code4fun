package com.codingpower.accounting;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String TAG = "accounting";
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.result);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,
				null);
		String sms = "";
		String[] test = cur.getColumnNames();
		for(int i = 0 ; i < test.length; i++)
		{
			Log.i(TAG, i+"," +test[i]);
		}
		Date date = new Date();
		while (cur.moveToNext()) {
			date.setTime(cur.getLong(4));
			sms += sdf.format(date) + ",From :" +  cur.getString(2)  + " : " + cur.getString(11)
					+ "\n\n\n";
		}
		textView.setText(sms);
	}

}
