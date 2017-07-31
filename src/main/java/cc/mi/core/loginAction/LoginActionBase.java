package cc.mi.core.loginAction;

import cc.mi.core.constance.LoginActionEnum;

public abstract class LoginActionBase {
	private final int fd;
	private final String guid;
	
	public LoginActionBase(int fd, String guid) {
		this.fd   =   fd;
		this.guid = guid;
	}
	
	public abstract boolean update(int diff);
	
	public abstract LoginActionEnum getType();

	public int getFd() {
		return fd;
	}

	public String getGuid() {
		return guid;
	}
}
