package cc.mi.core.binlog.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.callback.BinlogUpdateCallback;
import cc.mi.core.binlog.callbackParam.BinlogUpdateCallbackParam;
import cc.mi.core.binlog.stru.BinlogStruValueInt;
import cc.mi.core.binlog.stru.BinlogStruValueStr;
import cc.mi.core.constance.BinlogSyncMode;
import cc.mi.core.utils.Bytes;
import cc.mi.core.utils.Strings;

/**
 * 同步事件记录器
 * @author gy
 *
 */
public class SyncEventRecorder {
	private static final String BINLOG_DATA_SEP = "\1";
	
	protected final int mode;
	
	protected String guid;
	protected final Bytes intValues;
	protected Strings strValues;
	
	private BinlogUpdateCallback updateCallback;
	
	// 记录哪个下标的值修改
	protected final Map<Integer, BinlogStruValueInt> bsIntIndxHash;
	protected final Map<Integer, BinlogStruValueStr> bsStrIndxHash;

	public SyncEventRecorder(int mode, String guid, int intMaxSize, int strMaxSize) {
		this.guid = guid;
		this.mode = mode;
		intValues = new Bytes(intMaxSize << 2);
		strValues = new Strings(strMaxSize);
		
		bsIntIndxHash = new HashMap<>();
		bsStrIndxHash = new HashMap<>();
	}
	
	public BinlogUpdateCallback getUpdateCallback() {
		return updateCallback;
	}

	public void setUpdateCallback(BinlogUpdateCallback updateCallback) {
		this.updateCallback = updateCallback;
	}
	
	private void setIntValues(List<Integer> intMask, 
								List<Integer> intValueUpdated, 
								Map<Integer, Integer> intValuePrevHash) {
		int intValueIndx = 0;
		for (int i = 0; i < intMask.size(); ++ i) {
			int offset = i << 5; // * 32
			int maskValue = intMask.get(i);
			for (int bit = 0; bit < 32; ++ bit) {
				if ((maskValue & (1 << bit)) > 0) {
					int intValue = intValueUpdated.get(intValueIndx);
					++ intValueIndx;
					int indx = offset+bit;
					int prev = this.intValues.getInt(indx);
					this.intValues.setInt(indx, intValue);
					if (intValuePrevHash != null) {
						intValuePrevHash.put(indx, prev);
					}
				}
			}
		}
	}
	
	private void setStrValues(List<Integer> strMask, 
								List<String> strValueUpdated, 
								Map<Integer, String> strValuePrevHash) {
		int strValueIndx = 0;
		for (int i = 0; i < strMask.size(); ++ i) {
			int offset = i << 5; // * 32
			int maskValue = strMask.get(i);
			for (int bit = 0; bit < 32; ++ bit) {
				if ((maskValue & (1 << bit)) > 0) {
					String strValue = strValueUpdated.get(strValueIndx);
					++ strValueIndx;
					int indx = offset+bit;
					String prev = this.strValues.get(indx);
					this.strValues.set(indx, strValue);
					if (strValuePrevHash != null) {
						strValuePrevHash.put(indx, prev);
					}
				}
			}
		}
	}
	
	private Map<Integer, Integer> setAndGetIntPrevHashMap(List<Integer> intMask, List<Integer> intValueUpdated) {
		Map<Integer, Integer> intValuePrevHash = new HashMap<>();
		this.setIntValues(intMask, intValueUpdated, intValuePrevHash);
		return intValuePrevHash;
	}
	
	private Map<Integer, String> setAndGetStrPrevHashMap(List<Integer> strMask, List<String> strValueUpdated) {
		Map<Integer, String> strValuePrevHash = new HashMap<>();
		this.setStrValues(strMask, strValueUpdated, strValuePrevHash);
		return strValuePrevHash;
	}
	
	/**
	 * 创建新对象
	 * @param intMask
	 * @param intValueChanged
	 * @param strMask
	 * @param strValueChanged
	 */
	public void onCreateEvent(List<Integer> intMask, List<Integer> intValueChanged, 
			  				  List<Integer> strMask, List<String>  strValueChanged) {
		this.setIntValues(intMask, intValueChanged, null);
		this.setStrValues(strMask, strValueChanged, null);
	}
	
	/**
	 *  对象有更新了
	 */
	public void onUpdateEvent(List<Integer> intMask, List<Integer> intValueChanged, 
							  List<Integer> strMask, List<String>  strValueChanged) {
		
		Map<Integer, Integer> intValuePrevHash = this.setAndGetIntPrevHashMap(intMask, intValueChanged);
		Map<Integer, String> strValuePrevHash = this.setAndGetStrPrevHashMap(strMask, strValueChanged);
		if (this.updateCallback != null) {
			BinlogUpdateCallbackParam param = new BinlogUpdateCallbackParam(intValuePrevHash, strValuePrevHash);
			this.updateCallback.invoke(param);
		}
	}
	
	//操作记录为空
	public boolean isBinlogEmpty() {
		return this.bsIntIndxHash.size() == 0 && this.bsStrIndxHash.size() == 0;
	}
	
	
	public void onEventUInt32(byte optType, int indx, int value) {
		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
//		if (this.updateIntMask == null || this.updateIntMask.isMarked(indx)) {
			BinlogStruValueInt binlog = new BinlogStruValueInt(optType, indx, value);
			this.bsIntIndxHash.put(indx, binlog);
//		}
	}
	
	public void onEventStr(byte optType, int indx, String value) {
		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
		
//		if (this.updateStrMask == null || this.updateStrMask.isMarked(indx)) {
			BinlogStruValueStr binlog = new BinlogStruValueStr(optType, indx, value);
			this.bsStrIndxHash.put(indx, binlog);
//		}
	}

	public String getIntDataString() {
		String str = "";
		str += this.intValues.getInt(0);
		
		int size = this.intValues.intSize();
		for (int i = 1; i < size; ++ i) {
			str += BINLOG_DATA_SEP + this.intValues.getInt(i);
		}
		
		return str;
	}
	
	public String getStrDataString() {
		String str = "";
		str += this.strValues.get(0);
		
		int size = this.strValues.capacity();
		for (int i = 1; i < size; ++ i) {
			str += BINLOG_DATA_SEP + this.strValues.get(i);
		}
		
		return str;
	}
	
	public void fromString(String ints, String strs) {
		String[] params = ints.split(BINLOG_DATA_SEP);
		for (int i = 0; i < params.length; ++ i) {
			intValues.setInt(i, Integer.parseInt(params[ i ]));
		}
		
		params = strs.split(BINLOG_DATA_SEP);
		System.arraycopy(params, 0, strValues, 0, params.length);
	}
}
