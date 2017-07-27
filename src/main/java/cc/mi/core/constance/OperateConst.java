package cc.mi.core.constance;

public class OperateConst {
	public static final short OPERATE_TYPE_LOGIN = 1;		//登录
	// 下面的都是 登录类型 原因
	public static final short OPERATE_LOGIN_REASON_SUCCESS				= 1;	//成功
	public static final short OPERATE_LOGIN_REASON_NAME_REPEAT			= 2;	//名称重复
	public static final short OPERATE_LOGIN_REASON_NAME_ILLEGAL			= 3;	//名称非法
	public static final short OPERATE_LOGIN_REASON_CHAR_CAP 			= 4;	//角色数量达到上限
	public static final short OPERATE_LOGIN_REASON_SCENED_CLOSE 		= 5;	//场景服未开启
	public static final short OPERATE_LOGIN_REASON_SCENED_ERROR			= 6;	//场景服重置，其实就是崩了一个场景服换一个新的
	public static final short OPERATE_LOGIN_REASON_LOGINED_IN 			= 7;	//角色已登录
	public static final short OPERATE_LOGIN_REASON_OTHER_LOGINED		= 8;	//角色其他地方登录
	public static final short OPERATE_LOGIN_REASON_GAME_VERSION_ERROR	= 9;	//游戏版本不对
	public static final short OPERATE_LOGIN_REASON_MAP_VERSION_ERROR	= 10;	//地图素材版本不对
	public static final short OPERATE_LOGIN_REASON_DATA_VERSION_ERROR	= 11;	//data素材版本不对
	public static final short OPERATE_LOGIN_REASON_VERSION_FORMAT_ERROR	= 12;	//客户端发来的版本信息格式不对
	public static final short OPERATE_LOGIN_REASON_APPD_ERROR			= 13;	//应用服异常重启
	public static final short OPERATE_LOGIN_REASON_LOCK_ACCOUNT			= 14;	//帐号被封
	public static final short OPERATE_LOGIN_REASON_LOCK_IP				= 15;	//IP被封
	public static final short OPERATE_LOGIN_REASON_APPD_CLOSE 			= 16;	//应用服未开启
	public static final short OPERATE_LOGIN_REASON_NAME_TOO_LONG		= 17;	//名字太长
	public static final short OPERATE_LOGIN_REASON_NAME_HAS_PINGBI		= 18;	//有屏蔽词
	public static final short OPERATE_LOGIN_REASON_LOGOUT_UNFINISHED	= 19;	//该角色上一次的登出未完成，请等待
	public static final short OPERATE_LOGIN_REASON_PID_OR_SID_ERROR		= 20;	//服务器id或者平台id错误
	public static final short OPERATE_LOGIN_REASON_DB_RESULT_ERROR		= 21;	//数据库查询出问题了
	public static final short OPERATE_LOGIN_REASON_MERGE_SERVER			= 22;	//合服了
	public static final short OPERATE_LOGIN_REASON_PLAYER_ZIBAO			= 23;	//自爆
	public static final short OPERATE_LOGIN_REASON_GENDER_ILLEGAL		= 24;	//角色种类异常
	
	//************************************************************
	
	public static final short OPERATE_TYPE_CLOSE = 2;		//连接关闭
	// 下面的都是连接关闭 原因
	public static final short OPERATE_CLOSE_REASON_OTHER_LOGINED = 1;	//角色在其他地方登陆
		
	//************************************************************
}
