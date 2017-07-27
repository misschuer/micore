package cc.mi.core.binlog.callbackParam;

import cc.mi.core.binlog.data.SyncEventRecorder;
import cc.mi.core.utils.Mask;

public class BinlogUpdateCallbackParam {
	private final SyncEventRecorder data;
	private final int flag;
	private final Mask intMask;
	private final Mask strMask;
	
	public BinlogUpdateCallbackParam(SyncEventRecorder data, int flag, Mask intMask, Mask strMask) {
		this.data = data;
		this.flag = flag;
		this.intMask = intMask;
		this.strMask = strMask;
	}

	public SyncEventRecorder getData() {
		return data;
	}

	public int getFlag() {
		return flag;
	}

	public Mask getIntMask() {
		return intMask;
	}

	public Mask getStrMask() {
		return strMask;
	}
}
