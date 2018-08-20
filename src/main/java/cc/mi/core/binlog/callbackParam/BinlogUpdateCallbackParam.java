package cc.mi.core.binlog.callbackParam;

import java.util.Map;

public class BinlogUpdateCallbackParam {
	private final byte flags;
	private final Map<Integer, Integer> intValueHash;
	private final Map<Integer, String> strValueHash;
	
	public BinlogUpdateCallbackParam(byte flags, Map<Integer, Integer> intValueHash, Map<Integer, String> strValueHash) {
		this.flags			=	flags;
		this.intValueHash	=	intValueHash;
		this.strValueHash	=	strValueHash;
	}

	public byte getFlags() {
		return flags;
	}

	public Map<Integer, Integer> getIntValueHash() {
		return intValueHash;
	}

	public Map<Integer, String> getStrValueHash() {
		return strValueHash;
	}
}
