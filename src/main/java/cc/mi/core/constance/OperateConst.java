package cc.mi.core.constance;

public class OperateConst {
	public static final short OPERATE_TYPE_LOGIN = 1;		//登录
	// 下面的都是 登录类型 原因
	public static final short OPERATE_LOGIN_REASON_LOGINED_IN = 1;	//角色已登录
	
	//************************************************************
	
	public static final short OPERATE_TYPE_CLOSE = 2;		//连接关闭
	// 下面的都是连接关闭 原因
	public static final short OPERATE_CLOSE_REASON_OTHER_LOGINED = 1;	//角色在其他地方登陆
		
	//************************************************************
}
