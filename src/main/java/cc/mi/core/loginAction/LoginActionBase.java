package cc.mi.core.loginAction;

import cc.mi.core.constance.LoginActionEnum;
import cc.mi.core.impl.Tick;

public abstract class LoginActionBase implements Tick {
	private final int fd;
	private final String guid;
	
	public LoginActionBase(int fd, String guid) {
		this.fd   =   fd;
		this.guid = guid;
	}
	
	public abstract LoginActionEnum getType();

	public int getFd() {
		return fd;
	}

	public String getGuid() {
		return guid;
	}
}
