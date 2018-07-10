package cc.mi.core.server;

import cc.mi.core.binlog.data.Binlog;
import cc.mi.core.constance.BinlogSyncMode;
import cc.mi.core.constance.PlayerEnumFields;

public class PlayerBase extends Binlog {
	
	public PlayerBase(int intMaxSize, int strMaxSize) {
		super(BinlogSyncMode.SYNC_UPDATEDATA, intMaxSize, strMaxSize);
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
		this.setUInt32(PlayerEnumFields.PLAYER_INT_FIELD_MAP_ID, mapId);
	}
}
