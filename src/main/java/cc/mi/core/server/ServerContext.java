package cc.mi.core.server;

import cc.mi.core.coder.Coder;
import cc.mi.core.constance.MsgConst;

public abstract class ServerContext {
	// 客户端连接网关服的fd
	private final int fd;
	
	public ServerContext(int fd) {
		this.fd = fd;
	}
	
	protected abstract void send(Coder coder);
	
	protected void sendPacketToOtherServer(int serverFd, Coder coder) {
		coder.setId(0);
		coder.setInternalDestFD(serverFd);
		this.send(coder);
	}
	
	protected void sendToClient(Coder coder) {
		coder.setId(fd);
		coder.setInternalDestFD(MsgConst.MSG_TO_CLIENT);
		this.send(coder);
	}
	
	protected void sendToCenter(Coder coder) {
		
	}
	
	
	public int getFd() {
		return fd;
	}
}
