package cc.mi.core.binlog.data;

import java.util.Arrays;

import cc.mi.core.constance.BinlogStrFieldIndice;
import cc.mi.core.constance.BinlogSyncMode;

public class BinlogData extends BinlogModifier {
	private int dbHashCode;
	
	public BinlogData(int intMaxSize, int strMaxSize) {
		this(BinlogSyncMode.SYNC_UPDATEDATA, intMaxSize, strMaxSize);
	}
	
	public BinlogData(int mode, int intMaxSize, int strMaxSize) {
		this(mode, "", intMaxSize, strMaxSize);
	}
	
	public BinlogData(int mode, String guid, int intMaxSize, int strMaxSize) {
		super(mode, guid, intMaxSize, strMaxSize);
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
}
