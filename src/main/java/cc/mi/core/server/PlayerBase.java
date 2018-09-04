package cc.mi.core.server;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.constance.BinlogSyncMode;
import cc.mi.core.constance.PlayerEnumFields;

public class PlayerBase extends BinlogData {
	
	public PlayerBase(int intMaxSize, int strMaxSize) {
		super(BinlogSyncMode.SYNC_UPDATEDATA, intMaxSize, strMaxSize);
	}
	
	
	public boolean isBinlogCreated(short offset) {
		return this.getBit(PlayerEnumFields.PLAYER_INT_FIELD_BINLOG_FLAG, offset);
	}
	
	public void setBinlogCreate(short offset) {
		this.setBit(PlayerEnumFields.PLAYER_INT_FIELD_BINLOG_FLAG, offset);
	}
	
	public void setSceneFd(int fd) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_FD, fd);
	}
	
	public int getSceneFd() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_FD);
	}
	
	public void setLevel(int level) {
		this.setUInt32(PlayerEnumFields.PLAYER_INT_FIELD_LEVEL, level);
	}
	
	public void setCreateTime(int time) {
		this.setUInt32(PlayerEnumFields.PLAYER_INT_FIELD_CREATE_TIME, time);
	}
	
	public void setGender(int gender) {
		this.setUInt8(PlayerEnumFields.PLAYER_INT_FIELD_GENDER, (short) 0, gender);
	}
	
	public void setAccount(String account) {
		this.setStr(PlayerEnumFields.PLAYER_STR_FIELD_ACCOUNT, account);
	}
	
	public void setCreateLoginIp(String ip) {
		this.setStr(PlayerEnumFields.PLAYER_STR_FIELD_CREATE_LOGIN_IP, ip);
	}
	
	public void setPlatData(String data) {
		this.setStr(PlayerEnumFields.PLAYER_STR_FIELD_PLATFORM_INFO, data);
	}
	
	//变换对象位置和朝向
	public void relocate(float x, float y, float orient) {
		this.setPosition(x, y);
		this.setOrientation(orient);
	}
	
	//获取x坐标
	public float getPositionX() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_POS_X);
	}
	
	//获取y坐标
	public float getPositionY() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_POS_Y);
	}
	
	public void setPosition (float x, float y) { 
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_POS_X, x);
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_POS_Y, y);
	}

	//变换对象朝向
	public void setOrientation(float orient) {
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_ORIENTATION, orient);
	}
	
	//获取朝向
	public float getOrientation() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_ORIENTATION); 
	}
	
	public void setMapId(int mapId) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_MAP_ID, mapId);
	}
	
	public int getMapId() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_MAP_ID);
	}
	
	public int getMapLineNo() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_LINE_NO);
	}
	
	public void setMapLineNo(int lineNo) {
		if (this.getMapLineNo() != lineNo) {
			this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_LINE_NO, lineNo);
		}
	}
	
	//所在地图实例的id，若id为0，则是传送中
	public int getInstanceId() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_INSTANCE_ID);
	}
	
	public void setInstanceId(int id) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_INSTANCE_ID, id);
	}
	
	//传送相关
	
	// 传送地图id
	public int getTeleportMapID() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_MAP_ID);
	}
	
	public void setTeleportMapID(int mapId) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_MAP_ID, mapId);
	}
	
	// 传送坐标x
	public float getTeleportPosX() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_TELE_POS_X);
	}
	
	public void setTeleportPosX(float value) {
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_TELE_POS_X, value);
	}
	
	// 传送坐标y
	public float getTeleportPosY() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_TELE_POS_Y);
	}
	
	public void setTeleportPosY(float value) {
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_TELE_POS_Y, value);
	}
	
	// 传送的分线
	public int getTeleportLineNo() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_LINE_NO);
	}
	
	public void setTeleportLineNo(int lineNo) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_LINE_NO, lineNo);
	}
	
	// 传送的实例id
	public int getTeleportInstanceId() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_INST_ID);
	}
	
	public void setTeleportInstanceId(int instId) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_TELE_INST_ID, instId);
	}
	
	// 传送参数
	public String getTeleportExt() {
		return this.getStr(PlayerEnumFields.PLAYER_STR_FIELD_TELE_EXT);
	}
	
	public void setTeleportExt(String ext) {
		this.setStr(PlayerEnumFields.PLAYER_STR_FIELD_TELE_EXT, ext);
	}

	public byte getTeleportSign() {
		return (byte) this.getUInt8(PlayerEnumFields.PLAYER_INT_FIELD_CHECK_DATA, (short) 1);
	}
	
	public void setTeleportSign(byte sign) {
		 this.setUInt8(PlayerEnumFields.PLAYER_INT_FIELD_CHECK_DATA, (short) 1, sign);
	}
	
	public void setTeleportInfo(int mapId, float x, float y, int lineNo, final String ext) {
		
		this.setTeleportMapID(mapId);
		this.setTeleportPosX(x);
		this.setTeleportPosY(y);
		this.setTeleportLineNo(lineNo);
		if (!ext.equals(this.getTeleportExt())) {
			this.setTeleportExt(ext);
		}
		this.setInstanceId(0); //传送中
	}
	
	// 获得传送前的地图及其位置
	public int getDBMapId() {
		return this.getInt32(PlayerEnumFields.PLAYER_INT_FIELD_DB_MAP_ID);
	}
	
	public float getDBPosX() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_DB_POS_X);
	}
	
	public float getDBPosY() {
		return this.getFloat(PlayerEnumFields.PLAYER_INT_FIELD_DB_POS_Y);
	}
	
	// 还原传送前的位置
	public void relocateDBPosition() {
		if (this.getDBMapId() == 0) {
			return;
		}

		this.setMapId(this.getDBMapId());
		this.setPosition(this.getDBPosX(), this.getDBPosY());
	}
	
	// 保存传送前的地图和位置
	void setToDBPositon(int mapId, float x, float y) {
		this.setInt32(PlayerEnumFields.PLAYER_INT_FIELD_DB_MAP_ID, mapId);
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_DB_POS_X, x);
		this.setFloat(PlayerEnumFields.PLAYER_INT_FIELD_DB_POS_Y, y);
	}
}
