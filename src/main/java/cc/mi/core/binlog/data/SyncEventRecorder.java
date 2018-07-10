package cc.mi.core.binlog.data;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.binlog.callback.BinlogUpdateCallback;
import cc.mi.core.binlog.stru.BinlogStru;
import cc.mi.core.binlog.stru.BinlogStruValueInt;
import cc.mi.core.binlog.stru.BinlogStruValueStr;
import cc.mi.core.constance.BinlogSyncMode;
import cc.mi.core.utils.Bytes;
import cc.mi.core.utils.Mask;

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
	protected final String[] strValues;
	
	private BinlogUpdateCallback updateCallback;
	
	// 记录哪个下标的值修改
	private final Map<Integer, BinlogStru> bsIntIndxHash;
	private final Map<Integer, BinlogStru> bsStrIndxHash;
	
	// 临时变量用来存
	private final Mask intMask;
	private final Mask strMask;

	public SyncEventRecorder(int mode, String guid, int intMaxSize, int strMaxSize) {
		this.guid = guid;
		this.mode = mode;
		intValues = new Bytes(intMaxSize << 2);
		strValues = new String[strMaxSize];
		
		bsIntIndxHash = new HashMap<>();
		bsStrIndxHash = new HashMap<>();
		intMask = new Mask(intMaxSize);
		strMask = new Mask(strMaxSize);
	}
	
	public void writeCreatePacket() {
		
	}

	public BinlogUpdateCallback getUpdateCallback() {
		return updateCallback;
	}

	public void setUpdateCallback(BinlogUpdateCallback updateCallback) {
		this.updateCallback = updateCallback;
	}
	
	/**
	 *  TODO: 对象有更新了
	 */
	public void onUpdateEvent() {
		
	}
	
	public Mask getIntMask() {
		return intMask;
	}

	public Mask getStrMask() {
		return strMask;
	}
	
	//操作记录为空
	public boolean isBinlogEmpty() {
		return this.bsIntIndxHash.size() == 0 && this.bsStrIndxHash.size() == 0;
	}
	
	//数字下标创建包掩码
	public Mask getCreateMask() {
//		if(mask.GetCount() < (int)uint32_values_.size())
//			mask.SetCount(uint32_values_.size());
//
//		for(int i = 0; i < (int)uint32_values_.size(); i++){
//			//如果该下标不等于0则需要下发
//			if(uint32_values_[i])
//				mask.SetBit(i);
//		}
		return null;
	}
	
	//字符串创建包掩码
	public Mask getCreateStringMask(){
//		//扩展一下内存
//		mask.Clear();
//		if(mask.GetCount() < (int)str_values_.size())
//			mask.SetCount(str_values_.size());
//
//		for(int i = 0; i < (int)str_values_.size(); i++){
//			if(!str_values_[i].empty())
//				mask.SetBit(i);
//		}
		return null;
	}
	
//	//根据掩码写入整数下标的值
//	void WriteValues(UpdateMask &mask,ByteArray& bytes){
//		int len = mask.GetCount();
//		for(int i = 0; i<len; i++){
//			if(mask.GetBit(i))
//				bytes.writeT(uint32_values_[i]);
//		}
//	}
//
//	void WriteStringValues(UpdateMask &mask,ByteArray& bytes){
//		int len = mask.GetCount();
//		for(int i = 0; i < len; i++){
//			if(mask.GetBit(i))
//				bytes.writeUTF(str_values_[i]);
//		}
//	}
	
	public void onEventUInt32(byte optType, int indx, int value) {
		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
		BinlogStruValueInt binlog = new BinlogStruValueInt(optType, indx, value);
		this.bsIntIndxHash.put(indx, binlog);
	}
	
	public void onEventStr(byte optType, int indx, String value) {
		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
		BinlogStruValueStr binlog = new BinlogStruValueStr(optType, indx, value);
		this.bsStrIndxHash.put(indx, binlog);
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
		str += this.strValues[ 0 ];
		
		int size = this.strValues.length;
		for (int i = 1; i < size; ++ i) {
			str += BINLOG_DATA_SEP + this.strValues[ i ];
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
