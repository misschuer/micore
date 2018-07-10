package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogModifier;

public class GuidObjectTable {
	private final Map<String, BinlogModifier> objHash;
	
	public GuidObjectTable() {
		this.objHash = new HashMap<>();
	}
	
	public BinlogModifier get(String guid) {
		return objHash.get(guid);
	}
	
	public BinlogModifier createObject(String guid, int intMaxSize, int strMaxSize) {
//		GuidObject obj = new GuidObject(this.mode, guid, intMaxSize, strMaxSize);
//		this.attachObject(obj);
//		return obj;
		return null;
	}
	
	public void attachObject(BinlogModifier obj) {
		this.objHash.put(obj.getGuid(), obj);
	}
	
	public void releaseObject(String guid) {
		BinlogModifier obj = this.get(guid);
		if (obj == null) {
			return;
		}
		
		this.detachObject(obj);
	}
	
	public void detachObject(BinlogModifier obj) {
		this.objHash.remove(obj.getGuid());
	}
}
