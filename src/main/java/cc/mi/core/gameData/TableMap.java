package cc.mi.core.gameData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cc.mi.core.constance.MapTypeConst;

public enum TableMap {
	INSTANCE;
	
	private final Map<Integer, JsonObject> dataHash = new HashMap<>();
	private TableMap() {}
	
	public void parse(JsonObject json) {
		for (Entry<String, JsonElement> info : json.entrySet()) {
			int id = Integer.parseInt(info.getKey());
			dataHash.put(id, info.getValue().getAsJsonObject());
		}
	}
	
	public int getInstanceType(int id) {
		if (!dataHash.containsKey(id)) {
			return -1;
		}
		JsonObject obj = dataHash.get(id);
		JsonElement element = obj.get("type");
		if (element.isJsonPrimitive()) {
			return element.getAsInt();
		}
		return -1;
	}
	
	public boolean isInstanceMap(int id) {
		int type = this.getInstanceType(id);
		if (type == -1) {
			throw new RuntimeException(String.format("mapid = %s is not valid", id));
		}
		return type == MapTypeConst.MAP_TYPE_INSTANCE;
	}
	
	public boolean isNeedGeneral(int id) {
		if (!dataHash.containsKey(id)) {
			throw new RuntimeException(String.format("mapid = %s is not valid", id));
		}
		JsonObject obj = dataHash.get(id);
		JsonElement element = obj.get("isNeedGeneral");
		if (element.isJsonPrimitive()) {
			return element.getAsInt() == 1;
		}
		
		throw new RuntimeException(String.format("mapid = %s is not valid", id));
	}
	
	public String getTableName() {
		return "tb_map";
	}
	
//	static class MapData {
//		// 父级id
//		private final int parentId;
//		// 选择的场景服索引
//		private final int connIndx;
//		// 传送位置
//		private final Point2D<Integer> tele;
//		// 限制进入等级
//		private final int levelLimit;
//		// 地图类型
//		private final int type;
//		// 是否有时间限制
//		private final int isTimeLimit;
//		// 是否允许切换战斗模式
//		private final int allowChangeMode;
//		// 如果地图是副本地图的类型
//		private final int instType;
//		// 判断是哪个副本地图的子类型
//		private final int instSubType;
//		// 判断是否需要传送参数
//		private final int isNeedGeneral;
//		// 判断是否允许离线重进
//		private final int isOfflineReenter;
//	}
}
