package com.codingpower.accounting.analysis.banktype;

import java.util.HashMap;
import java.util.Map;

import com.codingpower.accounting.analysis.Meta;
import com.codingpower.accounting.analysis.SMSStringType;

/**
 * 招商银行
 * @author fortransit
 *
 */
public class CmbchinaType implements SMSStringType {

	@Override
	public String phone() {
		return "95555";
	}


	@Override
	public String pattern() {
		return "尾数为(\\d+)[^\\d]+(\\d+月\\d+日\\d+:\\d+)[^\\d]+([\\d\\.]+)元(.+?)，.*";
	}

	@Override
	public Map<Integer, String> meaningMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, Meta.ACCOUNT);
		map.put(2, Meta.DATETIME);
		map.put(3, Meta.SUM);
		map.put(4, Meta.TYPE);
		return map;
	}

}
