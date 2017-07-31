package cc.mi.core.constance;

public enum LoginActionEnum {
	CONTEXT_LOGIN_ACTION_LOGIN("CONTEXT_LOGIN_ACTION_LOGIN"),	//真正的登录，该干嘛干嘛
	CONTEXT_LOGIN_ACTION_CLOSE("CONTEXT_LOGIN_ACTION_CLOSE");	//登出。。
	
	private final String text;
	private LoginActionEnum(String text) {
		this.text = text;
	}
	
	public String toString() {
		return this.text;
	}
}
