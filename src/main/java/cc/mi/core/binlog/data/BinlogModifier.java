package cc.mi.core.binlog.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.stru.BinlogStruValueInt;
import cc.mi.core.binlog.stru.BinlogStruValueStr;
import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.constance.BinlogStrFieldIndice;
import cc.mi.core.generate.stru.BinlogInfo;
import cc.mi.core.utils.Mask;

/**
 * binlog对象修改器
 * @author gy
 *
 */
public class BinlogModifier extends SyncEventRecorder {
	// 临时
	protected final Mask tmpIntMask;
	protected final Mask tmpStrMask;
	
	public BinlogModifier(int mode, int intMaxSize, int strMaxSize) {
		this(mode, "", intMaxSize, strMaxSize);
	}
	
	public BinlogModifier(int mode, String guid, int intMaxSize, int strMaxSize) {
		super(mode, guid, intMaxSize, strMaxSize);
		this.tmpIntMask = new Mask(intMaxSize);
		this.tmpStrMask = new Mask(strMaxSize);
	}
	
	/**
	 * 创建普通对象包
	 * @return
	 */
	public BinlogInfo packNewBinlogInfo() {
		return this.packNewBinlogInfo(null, null);
	}
	
	/**
	 * 创建普通对象包
	 * 打包规则是 intmask是int类型数据的掩码,包括数据的下表
	 * intvalues 是掩码对应的有序的int数据
	 * str同理
	 * @param createIntMask	需要打包的int类型的数据
	 * @param createStrMask 需要打包的str类型的数据
	 * @return
	 */
	public BinlogInfo packNewBinlogInfo(Mask createIntMask, Mask createStrMask) {
		BinlogInfo data = new BinlogInfo();
		data.setBinlogId(this.guid);
		data.setState(BinlogOptType.OPT_NEW);
		
		tmpIntMask.clear();
		int size = this.intValues.intSize();
		List<Integer> newIntList = new ArrayList<>(size);
		for (int i = 0; i < size; ++ i) {
			int value = this.intValues.getInt(i);
			if (value > 0 && (createIntMask == null || createIntMask.isMarked(i))) {
				newIntList.add(value);
				tmpIntMask.mark(i);
			}
		}
		data.setIntMask(tmpIntMask.toNewList());
		data.setIntValues(newIntList);
		
		tmpStrMask.clear();
		List<String> newStrList = new ArrayList<>(strValues.capacity());
		for (int i = 0; i < this.strValues.capacity(); ++ i) {
			String str = this.strValues.get(i);
			if (str != null && !"".equals(str) && (createStrMask == null || createStrMask.isMarked(i))) {
				newStrList.add(str);
				tmpStrMask.mark(i);
			}
		}
		data.setStrMask(tmpStrMask.toNewList());
		data.setStrValues(newStrList);
		
		return data;
	}
	
	public Map<Integer, Integer> getIntCreateHash(Mask mask) {
		int size = this.intValues.intSize();
		Map<Integer, Integer> values = new HashMap<>();
		for (int i = 0; i < size; ++ i) {
			int value = this.intValues.getInt(i);
			if (value > 0 && (mask == null || mask.isMarked(i))) {
				values.put(i, value);
			}
		}
		
		return values;
	}
	
	public Map<Integer, String> getStrCreateHash(Mask mask) {
		int size = this.strValues.capacity();
		Map<Integer, String> values = new HashMap<>();
		for (int i = 0; i < size; ++ i) {
			String str = this.strValues.get(i);
			if (str != null && !"".equals(str) && (mask == null || mask.isMarked(i))) {
				values.put(i, str);
			}
		}
		return values;
	}
	
	/**
	 * 更新普通对象包
	 * @return
	 */
	public BinlogInfo packUpdateBinlogInfo() {
		return this.packUpdateBinlogInfo(null, null);
	}
	
	/**
	 * 更新普通对象包
	 * @param updateIntMask
	 * @param updateStrMask
	 * @return
	 */
	public BinlogInfo packUpdateBinlogInfo(Mask updateIntMask, Mask updateStrMask) {
		
		if (bsIntIndxHash.size() == 0 && bsStrIndxHash.size() == 0) {
			return null;
		}
		
		BinlogInfo data = new BinlogInfo();
		data.setBinlogId(this.guid);
		data.setState(BinlogOptType.OPT_UPDATE);
		// 先排序后处理		
		List<Integer> indice = new ArrayList<>(bsIntIndxHash.size());
		for (int indx : bsIntIndxHash.keySet()) {
			indice.add(indx);
		}
		Collections.sort(indice);
		tmpIntMask.clear();
		int size = this.intValues.intSize();
		List<Integer> newIntList = new ArrayList<>(size);
		for (int indx : indice) {
			BinlogStruValueInt bs = bsIntIndxHash.get(indx);
			if (updateIntMask == null || updateIntMask.isMarked(indx)) {
				tmpIntMask.mark(indx);
				newIntList.add(bs.getValue());
			}
		}
		data.setIntMask(tmpIntMask.toNewList());
		data.setIntValues(newIntList);
		
		// 先排序后处理		
		indice = new ArrayList<>(bsStrIndxHash.size());
		for (int indx : bsStrIndxHash.keySet()) {
			indice.add(indx);
		}
		Collections.sort(indice);
		tmpStrMask.clear();
		size = this.strValues.capacity();
		List<String> newStrList = new ArrayList<>(size);
		for (int indx : indice) {
			BinlogStruValueStr bs = bsStrIndxHash.get(indx);
			if (updateStrMask == null || updateStrMask.isMarked(indx)) {
				tmpStrMask.mark(indx);
				newStrList.add(bs.getValue());
			}
		}
		data.setStrMask(tmpStrMask.toNewList());
		data.setStrValues(newStrList);
		
//		这里交给外部调用clear()函数
//		bsIntIndxHash.clear();
//		bsStrIndxHash.clear();
		
		return data;
	}
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
		this.setStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_GUID, guid);
	}
	
	/******* long的操作 *******/
	public long getLong(int indx) {
		return this.intValues.getLong(indx);
	}
	
	public void setLong(int indx, long value) {
		if (this.intValues.getLong(indx) == value) {
			return;
		}
		this.intValues.setLong(indx, value);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx  ));
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx+1));
	}
	
	/**
	 * float 操作
	 * @param indx
	 * @return
	 */
	public float getFloat(int indx) {
		return this.intValues.getFloat(indx);
	}
	
	public void setFloat(int indx, float value) {
		if (this.getFloat(indx) == value) {
			return;
		}
		this.intValues.setInt(indx, Float.floatToIntBits(value));
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	/**
	 * int 操作
	 * @param indx
	 * @return
	 */
	public long getUInt32(int indx) {
		return this.intValues.getUnsignedInt(indx);
	}
	
	public void setUInt32(int indx, int value) {
		if (this.getUInt32(indx) == value) {
			return;
		}
		this.intValues.setInt(indx, value);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public void addUInt32(int indx, int value) {
		long prev = this.getUInt32(indx);
		this.setUInt32(indx, (int) (prev + value));
	}
	
	public void subUInt32(int indx, int value) {
		long prev = this.getUInt32(indx);
		this.setUInt32(indx, (int) (prev - value));
	}
	
	public int getInt32(int indx) {
		return this.intValues.getInt(indx);
	}
	
	public void setInt32(int indx, int value) {
		if (this.getInt32(indx) == value) {
			return;
		}
		this.intValues.setInt(indx, value);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	/**
	 * short操作
	 * @param indx
	 * @param offset
	 * @return
	 */
	public int getUInt16(int indx, short offset) {
		return this.intValues.getUnsignedShort(indx, offset);
	}
	
	public void setUInt16(int indx, short offset, int value) {
		if (this.getUInt16(indx, offset) == value) {
			return;
		}
		this.intValues.setShort(indx, offset, value);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public void addUInt16(int indx, short offset, int value) {
		int prev = this.getUInt16(indx, offset);
		this.setUInt16(indx, offset, prev + value);
	}
	
	public void subUInt16(int indx, short offset, int value) {
		int prev = this.getUInt16(indx, offset);
		this.setUInt16(indx, offset, prev - value);
	}

	/**
	 * byte 操作
	 * @param indx
	 * @param offset
	 * @return
	 */
	public int getUInt8(int indx, short offset) {
		return this.intValues.getUnsignedByte(indx, offset);
	}
	
	public void setUInt8(int indx, short offset, int value) {
		if (this.getUInt8(indx, offset) == value) {
			return;
		}
		this.intValues.setByte(indx, offset, value);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public void addUInt8(int indx, short offset, int value) {
		int prev = this.getUInt8(indx, offset);
		this.setUInt8(indx, offset, prev + value);
	}
	
	public void subUInt8(int indx, short offset, int value) {
		int prev = this.getUInt8(indx, offset);
		this.setUInt8(indx, offset, prev - value);
	}
	
	public boolean getBit(int indx, short offset) {
		return this.intValues.getBit(indx, offset);
	}
	
	public void setBit(int indx, short offset) {
		if (this.intValues.getBit(indx, offset)) {
			return;
		}
		this.intValues.setBit(indx, offset);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public void unSetBit(int indx, short offset) {
		if (!this.intValues.getBit(indx, offset)) {
			return;
		}
		this.intValues.unSetBit(indx, offset);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public String getStr(int indx) {
		return this.strValues.get(indx);
	}
	
	public void setStr(int indx, String str) {
		if (str.equals(this.strValues.get(indx))) {
			return;
		}
		this.strValues.set(indx, str);
		this.onEventStr(BinlogOptType.OPT_SET, indx, this.getStr(indx));
	}
	
	public int intSize() {
		return this.intValues.intSize();
	}
	
	public int strSize() {
		return this.strValues.capacity();
	}
}
