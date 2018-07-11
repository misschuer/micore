package cc.mi.core.binlog.callbackParam;

import java.util.Map;

public class BinlogUpdateCallbackParam {
	private final Map<Integer, Integer> intPrevValue;
	private final Map<Integer, String> strPrevValue;
	
	public BinlogUpdateCallbackParam(Map<Integer, Integer> intPrevValue, Map<Integer, String> strPrevValue) {
		this.intPrevValue = intPrevValue;
		this.strPrevValue = strPrevValue;
	}

	public Map<Integer, Integer> getIntPrevValue() {
		return intPrevValue;
	}

	public Map<Integer, String> getStrPrevValue() {
		return strPrevValue;
	}
}
