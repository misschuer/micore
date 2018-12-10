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
}
