package cc.mi.core.constance;

import java.util.Arrays;
import java.util.List;

public class ObjectType {
	//全局对象
	public static final String GLOBAL_VALUE_OWNER_STRING = "GLOBAL_VALUE";
	public static final String GLOBAL_CLIENT_GAME_CONFIG = "G.gameconfig";
	public static final String GLOBAL_CONFIG = "G.global";
	public static final String GLOBAL_COUNTER_CONFIG = "G.counter";
	public static final String GLOBAL_GUID_INDEX_MANAGER = "G.indexmanager";
	public static final List<String> GLOBAL_VALUE_LIST = Arrays.asList(GLOBAL_CLIENT_GAME_CONFIG, GLOBAL_CONFIG, GLOBAL_COUNTER_CONFIG, GLOBAL_GUID_INDEX_MANAGER);
	// 帮派变量
	public static final String FACTION_BINLOG_OWNER_STRING = "FACTION";
	// 组队变量
	public static final String GROUP_BINLOG_OWNER_STRING = "GROUP";
	
	//玩家
	public static final char PLAYER = 'P';
	// APP服务器用到的对象
	public static final char PLAYER_ITEM = 'i';
	public static final char PLAYER_SOCIAL = 's';
	
	//生物
	public static final char UNIT = 'U';
	//地图变量
	public static final char MAP = 'M';
	//地图上的grid战利品信息
	public static final char LOOT = 'L';
	//副本
	public static final char INSTANCE = 'I';
	
	// 索引
	public static final String GLOBAL_INDEX = "G";
	public static final String FACTION_INDEX = "F";
	public static final String PLAYER_INDEX = "" + PLAYER;
	
}
