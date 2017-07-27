package cc.mi.core.server;

import cc.mi.core.binlog.data.BinlogObject;
import cc.mi.core.constance.BinlogSyncMode;
import cc.mi.core.constance.ObjectType;

public enum GuidManager {
	INSTANCE;
	
	// 临时数据
	private BinlogObject myData;
	// 存档的数据
	private BinlogObject data;
	
	public void initData(BinlogObject data) {
		if (this.data != null) {
			return;
		}
		this.data = data;
		this.loadMyData();
	}
	
	private GuidManager() {}
	
	private final String makeNewGuid(char objectType, long indx, String suffix) {
		if (suffix.length() > 0) {
			return String.format("%c%d.%s", objectType, indx, suffix);
		}
		return String.format("%c%d", objectType, indx);
	}
	
	public final String makeNewGuid(char objectType) {
		return makeNewGuid(objectType, "");
	}
	
	public final String makeNewGuid(char objectType, String suffix) {
		return makeNewGuid(objectType, newIndex(objectType), suffix);
	}
	
	public String getSuffixFromGuid(String guid) {
		int indx = guid.indexOf('.');
		String s = "";
		if (indx > -1) {
			s = guid.substring(indx+1);
		}
		return s;
	}
	
	public String replaceSuffix(String guid, char objectType) {
		return objectType + guid.substring(1);
	}
	
	public long newIndex(char objectType) {
		this.data.addUInt32(objectType, 1);
		this.myData.addUInt32(objectType, 1);
		return this.myData.getUInt32(objectType);
	}

	private void loadMyData() {
		this.myData = new BinlogObject(BinlogSyncMode.SYNC_NONE, 255, 1);
		for (int i = 0; i < this.data.intSize(); ++ i) {
			this.myData.setUInt32(i, (int) this.data.getUInt32(i));
		}
	}
	
	//同步guid累加最大值
	public void syncMaxGuid(long playerMax) {
		if (this.myData.getUInt32(ObjectType.PLAYER) > playerMax) {
			playerMax = this.myData.getUInt32(ObjectType.PLAYER);
		}
		playerMax += 1500;
		this.myData.setUInt32(ObjectType.PLAYER, (int) playerMax);
		this.data.setUInt32(ObjectType.PLAYER, (int) playerMax);

		this.myData.clear();
	}

	public void setUnitGuid(int unitId) {
		this.myData.setUInt32(ObjectType.UNIT, unitId);
	}
}
