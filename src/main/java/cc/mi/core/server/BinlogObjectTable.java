package cc.mi.core.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.generate.stru.BinlogInfo;
import cc.mi.core.log.CustomLogger;

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
		logger.devLog("add new data guid = {}", obj.getGuid());
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
	
	
	public void parseBinlogInfo(BinlogInfo binlogInfo) {
		String guid = binlogInfo.getBinlogId();
		BinlogData binlogData = this.get(guid);
		if (binlogData == null) {
			binlogData = new BinlogData(1 << 6, 1 << 6);
		}
		List<Integer> intMask = binlogInfo.getIntMask();
		List<Integer> intValueChanged = binlogInfo.getIntValues();
		List<Integer> strMask = binlogInfo.getStrMask();
		List<String>  strValueChanged = binlogInfo.getStrValues();
		if (binlogInfo.getState() == BinlogOptType.OPT_NEW) {
			binlogData.setGuid(binlogInfo.getBinlogId());
			binlogData.onCreateEvent(intMask, intValueChanged, strMask, strValueChanged);
			this.attachObject(binlogData);
		} else if (binlogInfo.getState() == BinlogOptType.OPT_UPDATE) {
			binlogData.onUpdateEvent(intMask, intValueChanged, strMask, strValueChanged);
		}
	}
}
