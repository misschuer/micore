package cc.mi.core.binlog.data;

import java.util.Arrays;

import cc.mi.core.constance.BinlogStrFieldIndice;
import cc.mi.core.constance.BinlogSyncMode;

public class BinlogObject extends GuidObject {
	private static final char BINLOG_DATA_SEP = '\1';
	private int dbHashCode;
	
	public BinlogObject(int intMaxSize, int strMaxSize) {
		this(BinlogSyncMode.SYNC_SLAVE, intMaxSize, strMaxSize);
	}
	
	public BinlogObject(int mode, int intMaxSize, int strMaxSize) {
		this(mode, "", intMaxSize, strMaxSize);
	}
	
	public BinlogObject(int mode, String guid, int intMaxSize, int strMaxSize) {
		super(mode, guid, intMaxSize, strMaxSize);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
		this.setStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_GUID, guid);
	}
	
	public String getName() {
		return this.getStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_NAME);
	}
	
	public void setName(String name) {
		this.setStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_NAME, name);
	}
	
	public String getOwner() {
		return this.getStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_OWNER);
	}
	
	public void setOwner(String ownerGuid) {
		this.setStr(BinlogStrFieldIndice.BINLOG_STRING_FIELD_OWNER, ownerGuid);
	}

	public int getDbHashCode() {
		return dbHashCode;
	}

	public void setDbHashCode(int dbHashCode) {
		this.dbHashCode = dbHashCode;
	}
	
	public void clear() {
		this.intValues.clear();
		Arrays.fill(this.strValues, null);
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
}
