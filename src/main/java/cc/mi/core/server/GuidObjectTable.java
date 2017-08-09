package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.binlog.data.GuidObject;

public class GuidObjectTable {
	private final int mode;
	private final Map<String, GuidObject> objHash;
	
	public GuidObjectTable(int mode) {
		this.mode = mode;
		this.objHash = new HashMap<>();
	}
	
	public GuidObject get(String guid) {
		return objHash.get(guid);
	}
	
	public GuidObject createObject(String guid, int intMaxSize, int strMaxSize) {
		GuidObject obj = new GuidObject(this.mode, guid, intMaxSize, strMaxSize);
		this.attachObject(obj);
		return obj;
	}
	
	public void attachObject(GuidObject obj) {
		this.objHash.put(obj.getGuid(), obj);
	}
	
	public void releaseObject(String guid) {
		GuidObject obj = this.get(guid);
		if (obj == null) {
			return;
		}
		
		this.detachObject(obj);
	}
	
	public void detachObject(GuidObject obj) {
		this.objHash.remove(obj.getGuid());
	}
}
