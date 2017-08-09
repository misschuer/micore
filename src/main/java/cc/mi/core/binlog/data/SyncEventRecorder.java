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

public class SyncEventRecorder {
	private static final String BINLOG_DATA_SEP = "\1";
	
	//事件记录器模式
	protected final int mode;
	
	//这三个成员是这个对象的所有值
	protected String guid;
	protected final Bytes intValues;
	protected final String[] strValues;
	
	private BinlogUpdateCallback updateCallback;
	
	//记录着所有下标操作,仅当从模式下有效
	private final Map<Integer, BinlogStru> binlogStruHash;

	//为便于下标更新的事件触发，从模式下增加所有	
	private final Mask intMask;
	private final Mask strMask;

	public SyncEventRecorder(int mode, String guid, int intMaxSize, int strMaxSize) {
		this.mode = mode;
		this.guid = guid;
		intValues = new Bytes(intMaxSize << 2);
		strValues = new String[strMaxSize];
		
		binlogStruHash = new HashMap<>();
		intMask = new Mask(intMaxSize);
		strMask = new Mask(strMaxSize);
	}

	public BinlogUpdateCallback getUpdateCallback() {
		return updateCallback;
	}

	public void setUpdateCallback(BinlogUpdateCallback updateCallback) {
		this.updateCallback = updateCallback;
	}

	public int getMode() {
		return mode;
	}

	public Map<Integer, BinlogStru> getBinlogStruHash() {
		return binlogStruHash;
	}

	public Mask getIntMask() {
		return intMask;
	}

	public Mask getStrMask() {
		return strMask;
	}
	
	//操作记录为空
	public boolean isBinlogEmpty() {
		return this.binlogStruHash.size() > 0;
	}
	
	//数字下标创建包掩码
	public Mask getCreateMask(){
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
		if ((this.mode & BinlogSyncMode.SYNC_UPDATEDATA) == 1) {
			this.intMask.mark(indx);
			return;
		}

		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
		
		BinlogStruValueInt binlog = new BinlogStruValueInt(optType, indx, value);		
		//从模式下记录下标操作记录
		//主模式下记录哪些下标发生变化,直接覆盖式更新即可
		this.binlogStruHash.put(indx, binlog);
		
		//只有主模式会执行到这条代码， 所以这里不会有这块的性能损失
//		if(notify_info_)
//			notify_info_->SetChangeObj(this);
	}
	
	public void onEventStr(byte optType, int indx, String value) {
		if ((this.mode & BinlogSyncMode.SYNC_UPDATEDATA) == 1) {
			this.intMask.mark(indx);
			return;
		}

		//不需要记录binlog
		if ((this.mode & BinlogSyncMode.SYNC_NONE) == 1) {
			return;
		}
		
		//从模式下记录下标操作记录
		//主模式下记录哪些下标发生变化,直接覆盖式更新即可
		BinlogStruValueStr binlog = new BinlogStruValueStr(optType, indx, value);		
		//从模式下记录下标操作记录
		//主模式下记录哪些下标发生变化,直接覆盖式更新即可
		this.binlogStruHash.put(indx, binlog);
		
		//只有主模式会执行到这条代码， 所以这里不会有这块的性能损失
//		if(notify_info_)
//			notify_info_->SetChangeObj(this);
	}

	public void onEventSyncBinLog(BinlogStru binlog) {
		
		if (binlog instanceof BinlogStruValueStr) {
			this.strMask.mark(binlog.getIndx());
		} else {
			this.intMask.mark(binlog.getIndx());
		}

		//如果是主模式
		if ((this.mode & BinlogSyncMode.SYNC_MASTER) == 1) {
			//注意：如果这里就由主库发过来的更新应用到主库就会影响到下标0
			//不是原子操作，主模式下设置更新标志
			this.binlogStruHash.put(binlog.getIndx(), binlog);
		}
		
		//让主从可以共存,仅为了与客户端保持一致的行为
//		if ((this.mode & BinlogSyncMode.SYNC_SLAVE) == 1) {
//			//如果是从模式的原子操作则触发回调
//			if(binlog.typ == TYPE_STRING) {
//				events_str_value_.Dispatch(binlog.index,&binlog);
//			} else{
//				events_value_.Dispatch(binlog.index,&binlog);
//			}
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
