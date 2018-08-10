package cc.mi.core.xlsxData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.constance.MapTypeConst;

public final class MapTemplate {
	// 基础信息
	protected MapBaseinfo baseInfo;
	// 障碍物信息
	protected int[] obstacleMask;
	// 复活点
	protected List<MapRaise> raises;
	// 传送点
	protected List<MapTeleport> teleports;
	//刷怪点
	protected Map<Integer, MapMonster> monsters;
	//刷游戏对象点
	protected List<MapGameobject> gameobjects;
	
	public MapTemplate() {
		this.raises = new LinkedList<>();
		this.teleports = new LinkedList<>();
		this.gameobjects = new LinkedList<>();
		this.monsters = new HashMap<>();
	}
	
	public boolean isInstance() {
		int type = this.getBaseInfo().getType();
		return type == MapTypeConst.MAP_TYPE_INSTANCE;
	}
	
	public boolean isValidPosition(int x, int y) {
		//TODO:
		return true;
	}

	public MapBaseinfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(MapBaseinfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public void setObstacleMask(int[] obstacleMask) {
		this.obstacleMask = obstacleMask;
	}

	public void addRaise(MapRaise raise) {
		this.raises.add(raise);
	}
	
	public void addTeleport(MapTeleport teleport) {
		this.teleports.add(teleport);
	}
	
	public void addGameObject(MapGameobject obj) {
		this.gameobjects.add(obj);
	}
	
	public void addMonster(int pos, MapMonster monster) {
		this.monsters.put(pos, monster);
	}
}
