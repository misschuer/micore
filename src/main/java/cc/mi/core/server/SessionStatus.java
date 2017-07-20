package cc.mi.core.server;

public enum SessionStatus {
	STATUS_NONE,
	STATUS_NEVER,					
	STATUS_AUTHED,				//身份验证通过
	STATUS_LOGGEDIN,			//登录成功
	STATUS_TRANSFER,			//传送中	
	STATUS_TRANSFER2,			
	STATUS_DELETE,				//等待删除
	STATUS_PUT,					//提交数据中
	STATUS_PUT_OK;				//提交数据完毕
}
