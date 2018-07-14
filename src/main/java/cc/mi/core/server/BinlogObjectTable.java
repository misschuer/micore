package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;

public class BinlogObjectTable {
	private final Map<String, BinlogData> objHash;
	
	public BinlogObjectTable() {
		this.objHash = new HashMap<>();
	}
	
	public BinlogData get(String guid) {
		return objHash.get(guid);
	}
	
	public BinlogData createObject(String guid, int intMaxSize, int strMaxSize) {
//		GuidObject obj = new GuidObject(this.mode, guid, intMaxSize, strMaxSize);
//		this.attachObject(obj);
//		return obj;
		return null;
	}
	
	public void attachObject(BinlogData obj) {
		this.objHash.put(obj.getGuid(), obj);
	}
	
	public void releaseObject(String guid) {
		BinlogData obj = this.get(guid);
		if (obj == null) {
			return;
		}
		
		this.detachObject(obj);
	}
	
	public void detachObject(BinlogData obj) {
		this.objHash.remove(obj.getGuid());
	}
}
