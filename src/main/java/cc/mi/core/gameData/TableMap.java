package cc.mi.core.gameData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cc.mi.core.annotation.data.DataKey;
import cc.mi.core.constance.MapTypeConst;

@DataKey(value = "tb_map")
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
}
