package cc.mi.core.gameData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cc.mi.core.annotation.data.DataKey;

@DataKey(value = "tb_creature_template")
public enum TableCreature {
	INSTANCE;
	private final Map<Integer, JsonObject> dataHash = new HashMap<>();
	private TableCreature() {}
	
	public void parse(JsonObject json) {
		for (Entry<String, JsonElement> info : json.entrySet()) {
			int id = Integer.parseInt(info.getKey());
			dataHash.put(id, info.getValue().getAsJsonObject());
		}
	}
	
	public boolean isDataExist(int id) {
		return dataHash.containsKey(id);
	}
	
	/**
	 * 根据需求来
	 * {"id" : 7001, "name" : "小竹精", "npcflag" : 0, "monsterType" : 0, "level" : 5, 
	 * "pros" : [[1,69603],[14,25],[2,696],[22,10000],[23,0],[18,2000],[9,80]], 
	 * "spells" : "13001,10000,1500,1,1", "visionRadius" : 20, "actionRadius" : 20, 
	 * "attackType" : 2, "attackRange" : 4, "exp" : 4, "rewardId" : 0, "rebornTime" : 2000, 
	 * "recureAfterOutBattle" : 0, "moveType" : 1, "bodyMiss" : 2000, "isRobot" : 0}
	 * @param id
	 * @return
	 */
//	public JsonObject getJsonObjectData(int id) {
//		return dataHash.get(id);
//	}
	
	public String getName(int id) {
		if (!dataHash.containsKey(id)) {
			return "";
		}
		return dataHash.get(id).get("name").getAsString();
	}
	
	public int getVisionRadius(int id) {
		if (!dataHash.containsKey(id)) {
			return 0;
		}
		return dataHash.get(id).get("visionRadius").getAsInt();
	}
	
	public int getActionRadius(int id) {
		if (!dataHash.containsKey(id)) {
			return 0;
		}
		return dataHash.get(id).get("actionRadius").getAsInt();
	}
}
