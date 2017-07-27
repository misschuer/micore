package cc.mi.core.constance;

/**
 * 定义p对象的下标
 * @author DZ-A-009
 *
 */
public class PlayerEnumFields {

	public static final int PLAYER_INT_FIELD_FD				= 0;								//所在场景服的网络连接号
	public static final int PLAYER_INT_FIELD_ENTRY			= PLAYER_INT_FIELD_FD + 1;			//模板
	public static final int PLAYER_INT_FIELD_MAP_ID			= PLAYER_INT_FIELD_ENTRY + 1;		//地图
	public static final int PLAYER_INT_FIELD_INSTANCE_ID	= PLAYER_INT_FIELD_MAP_ID + 1;		//地图实例
	public static final int PLAYER_INT_FIELD_LINE_NO		= PLAYER_INT_FIELD_INSTANCE_ID + 1;	//地图分线号
	public static final int PLAYER_INT_FIELD_POS_X			= PLAYER_INT_FIELD_LINE_NO + 1;		//X坐标
	public static final int PLAYER_INT_FIELD_POS_Y			= PLAYER_INT_FIELD_POS_X + 1;		//Y坐标
	public static final int PLAYER_INT_FIELD_ORIENTATION	= PLAYER_INT_FIELD_POS_Y + 1;		//朝向
	public static final int PLAYER_INT_FIELD_LEVEL			= PLAYER_INT_FIELD_ORIENTATION + 1;	//等级
	public static final int PLAYER_INT_FIELD_GENDER			= PLAYER_INT_FIELD_LEVEL + 1;	//四个字节,分别存放 0角色id,
	public static final int PLAYER_INT_FIELD_CREATE_TIME	= PLAYER_INT_FIELD_GENDER + 1;	//角色创建时间
	
	//长度
	public static final int PLAYER_INT_FIELDS_SIZE			= PLAYER_INT_FIELD_CREATE_TIME + 1;	//int类型数据的大小
	
	/*****************************************************************************************************************/
	public static final int PLAYER_STR_FIELD_ACCOUNT		= BinlogStrFieldIndice.BINLOG_STRING_FIELD_OWNER + 1; //账号
	public static final int PLAYER_STR_FIELD_CREATE_LOGIN_IP= PLAYER_STR_FIELD_ACCOUNT + 1;				//创建角色ip
	public static final int PLAYER_STR_FIELD_PLATFORM_INFO  = PLAYER_STR_FIELD_CREATE_LOGIN_IP + 1;		//平台信息
	//长度
	public static final int PLAYER_STR_FIELDS_SIZE			= PLAYER_STR_FIELD_PLATFORM_INFO + 1;		//str类型数据的大小
}
