package com.codingpower.accounting;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;
import com.codingpower.accounting.analysis.banktype.CmbchinaType;

public class MainActivity extends Activity {
	private String TAG = "accounting";
	private TextView textView;
	private Map<String, Method> metaMethodMap = new HashMap<String, Method>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
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
		SMSStringType cmbType = new CmbchinaType();
		Pattern pattern = Pattern.compile(cmbType.pattern());
		Map<Integer, String> meaningMap = cmbType.meaningMap();
		List<Meta> metaList = new ArrayList<Meta>();
		while (cur.moveToNext()) {
			date.setTime(cur.getLong(4));
			String adr = cur.getString(2);
			String content = cur.getString(11);
			/*sms += sdf.format(date) + ",From :" + adr  + " : " + content
					+ "\n\n\n";*/
			if(adr.equals(cmbType.phone()))
			{
				patternToMeta(metaList, content, pattern, meaningMap);
			}
		}
		StringBuffer sb = new StringBuffer();
		for(Meta m : metaList)
		{
			sb.append(m.toString()).append("\n\n\n");
		}
		textView.setText(sb.toString());
		
		
	}
	
	private void init()
	{
		for(Method m: Meta.class.getMethods())
		{
			if(m.getName().startsWith("set"))
			{
				String name = m.getName().substring(3).toLowerCase();
				metaMethodMap.put(name, m);
			}
		}
	}
	
	public void patternToMeta(List<Meta> metaList, String content,Pattern pattern, Map<Integer,String> map)
	{
		Matcher matcher = pattern.matcher(content);
		if(matcher.matches())
		{
			Meta a = new Meta();
			for(int i = 1; i <= matcher.groupCount(); i++)
			{
				String propName = map.get(i);
				String propVal = matcher.group(i);
				//Log.w(TAG, propName + "," + propVal);
				setVal(a, propName, propVal);
				
			}
			metaList.add(a);
		}
	}
	
	public void setVal(Meta meta, String propName, String propVal)
	{
		Method m = metaMethodMap.get(propName);
		Set set = metaMethodMap.keySet();
		Log.w(TAG, propName + "," + String.valueOf(m));
		if(metaMethodMap.containsKey(propName))
		{
			try {
				
				metaMethodMap.get(propName).invoke(meta, propVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
