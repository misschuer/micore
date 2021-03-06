package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cc.mi.core.constance.ObjectType;

public enum GuidManager {
	INSTANCE;
	
	private final Map<Integer, Integer> indxHash;
	private GuidManager() {
		this.indxHash = new HashMap<>();
	}
	
	public final String makeNewGuidByUuid(char objectType) {
		return String.format("%c%s", objectType, UUID.randomUUID().toString().replace("-", "").toLowerCase());
	}
	
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
	
	public int newIndex(int objectType) {
		int id = 0;
		if (this.indxHash.containsKey(objectType)) {
			id = indxHash.get(objectType);
		}
		id ++;
		indxHash.put(objectType, id);
		return id;
	}

	public void syncPlayerGuidIndex(int playerMax) {
		indxHash.put((int) ObjectType.PLAYER, playerMax);
	}
	
	public int getGuidIndex(int objectType) {
		return indxHash.get(objectType);
	}
	
	public boolean isPlayerGuid(String guid) {
		return guid != null && guid.charAt(0) == ObjectType.PLAYER;
	}
	
	public int getElementIntGuid(String guid) {
		int len = guid.indexOf(".");
		if (len == -1) {
			len = guid.length();
		}
		return Integer.parseInt(guid.substring(1, len));
	}
}
