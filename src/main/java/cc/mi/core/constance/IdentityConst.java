package cc.mi.core.constance;

import java.util.HashMap;
import java.util.Map;

public class IdentityConst {
	public static final byte IDENDITY_GATE	= -1;
	public static final byte IDENDITY_CENTER = 0;
	public static final byte IDENDITY_LOGIN	= 1;
	public static final byte IDENDITY_APP	= 2;
	public static final byte IDENDITY_RECORD = 3;
	public static final byte IDENDITY_SCENE	= 4;
	
	private static final Map<Byte, String> serverNameHash = new HashMap<>();
	static {
		serverNameHash.put(IDENDITY_GATE, "gate");
		serverNameHash.put(IDENDITY_LOGIN, "login");
		serverNameHash.put(IDENDITY_APP, "app");
		serverNameHash.put(IDENDITY_RECORD, "record");
		serverNameHash.put(IDENDITY_SCENE, "scene");
	}
	
	public static String getServerName(byte identity) {
		if (serverNameHash.containsKey(identity)) {
			return serverNameHash.get(identity);
		}
		return String.format("unrecognized identity(%d)", identity);
	}
}
