package cc.mi.core.xlsxData;

import java.util.HashMap;
import java.util.Map;

public final class MapTemplate {
	private static final Map<Integer, MapTemplate> mapTemplateHash= new HashMap<>();
	
	private boolean isInstance;
	
	
	public static MapTemplate getTemplate(int mapId) {
		return mapTemplateHash.get(mapId);
	}
	
	public static boolean containsTemplate(int mapId) {
		return mapTemplateHash.containsKey(mapId);
	}
	
	public static void load() {
		//TODO:
	}


	public boolean isInstance() {
		return isInstance;
	}
	
	public boolean isValidPosition(int x, int y) {
		//TODO:
		return true;
	}
	

//	private void setInstance(boolean isInstance) {
//		this.isInstance = isInstance;
//	}
}
