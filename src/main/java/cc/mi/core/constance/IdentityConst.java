package cc.mi.core.constance;

import java.util.HashMap;
import java.util.Map;

public class IdentityConst {
	public static final byte SERVER_TYPE_GATE	= 1;
	public static final byte SERVER_TYPE_CENTER = 2;
	public static final byte SERVER_TYPE_LOGIN	= 3;
	public static final byte SERVER_TYPE_APP	= 4;
	public static final byte SERVER_TYPE_RECORD	= 5;
	public static final byte SERVER_TYPE_SCENE  = 6;
	
	private static final Map<Byte, String> serverNameHash = new HashMap<>();
	static {
		serverNameHash.put(SERVER_TYPE_GATE, "gate");
		serverNameHash.put(SERVER_TYPE_CENTER, "center");
		serverNameHash.put(SERVER_TYPE_LOGIN, "login");
		serverNameHash.put(SERVER_TYPE_APP, "app");
		serverNameHash.put(SERVER_TYPE_SCENE, "scene");
		serverNameHash.put(SERVER_TYPE_RECORD, "record");
	}
	
	public static String getServerName(byte identity) {
		if (serverNameHash.containsKey(identity)) {
			return serverNameHash.get(identity);
		}
		return String.format("unrecognized identity(%d)", identity);
	}
}
