package cc.mi.core.binlog.data;

import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.constance.BinlogStrFieldIndice;

/**
 * binlog对象修改器
 * @author gy
 *
 */
public class BinlogModifier extends SyncEventRecorder {

	public BinlogModifier(int mode, int intMaxSize, int strMaxSize) {
		this(mode, "", intMaxSize, strMaxSize);
	}
	
	public BinlogModifier(int mode, String guid, int intMaxSize, int strMaxSize) {
		super(mode, guid, intMaxSize, strMaxSize);
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
		this.intValues.setBit(indx, offset);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public void unSetBit(int indx, short offset) {
		this.intValues.unSetBit(indx, offset);
		this.onEventUInt32(BinlogOptType.OPT_SET, indx, this.intValues.getInt(indx));
	}
	
	public String getStr(int indx) {
		return this.strValues[indx];
	}
	
	public void setStr(int indx, String str) {
		this.strValues[indx] = str;
		this.onEventStr(BinlogOptType.OPT_SET, indx, this.getStr(indx));
	}
	
	public int intSize() {
		return this.intValues.intSize();
	}
	
	public int strSize() {
		return this.strValues.length;
	}
}
