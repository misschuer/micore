package cc.mi.core.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.log.CustomLogger;

/**
 * FIXME: 需要和ownerdataset关联
 * @author gy
 *
 */
public class BinlogObjectTable {
	static final CustomLogger logger = CustomLogger.getLogger(BinlogObjectTable.class);
	
	private final Set<String> indice;
	private final Map<String, BinlogData> objHash;
	private final Map<String, Map<String, BinlogData>> indiceObjHash;
	
	public BinlogObjectTable() {
		this.objHash = new HashMap<>();
		this.indice = new HashSet<>();
		this.indiceObjHash = new HashMap<>();
	}
	
	public BinlogData get(String guid) {
		return objHash.get(guid);
	}
	
	public boolean contains(String guid) {
		return this.objHash.containsKey(guid);
	}
	
	public String getBinlogDataIndice(String binlogId) {
		String prefix = binlogId.substring(0, 1);
		return this.indice.contains(prefix) ? prefix : null;
	}
	
	public void attachObject(BinlogData obj) {
		this.objHash.put(obj.getGuid(), obj);
		String indice = this.getBinlogDataIndice(obj.getGuid());
		if (indice != null) {
			this.indiceObjHash.get(indice).put(obj.getGuid(), obj);
		}
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
		String indice = this.getBinlogDataIndice(obj.getGuid());
		if (indice != null) {
			this.indiceObjHash.get(indice).remove(obj.getGuid());
		}
	}
	
	/**
	 *  字符串的前缀
	 * @param indexRegex
	 */
	public void createIndex(String indexRegex) {
		this.indice.add(indexRegex);
		if (!this.indiceObjHash.containsKey(indexRegex)) {
			this.indiceObjHash.put(indexRegex, new HashMap<>());
		}
	}
}
