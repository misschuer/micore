package cc.mi.core.server;

import cc.mi.core.generate.msg.OperationResult;
import cc.mi.core.packet.Packet;
import io.netty.channel.Channel;

public abstract class ServerContext {
	// 客户端连接网关服的fd
	private final int fd;
	private SessionStatus status;
	private String remoteIp;
	private short remotePort;
	private String guid;
	
	public ServerContext(int fd) {
		this.fd = fd;
		this.status = SessionStatus.STATUS_NONE;
	}
	
	public abstract void sendToGate(Packet coder);
	
	public abstract void sendToCenter(Packet coder);
	
	protected void sendPacketToOtherServer(int serverFd, Packet coder) {
		coder.setFD(serverFd);
		this.sendToCenter(coder);
	}
	
	protected void sendToClient(Packet coder) {
		coder.setFD(fd);
		this.sendToGate(coder);
	}
	
	public int getFd() {
		return fd;
	}

	public SessionStatus getStatus() {
		return status;
	}

	public void setStatus(SessionStatus status) {
		this.status = status;
	}
	
	abstract public void closeSession(int type);
	
	public void callOperationResult(Channel channel, short type, String data) {
		OperationResult result = new OperationResult();
		result.setData(data);
		result.setType(type);
		result.setFD(this.fd);
		channel.writeAndFlush(result);
	}
	
	protected void operationResult(short type, String data) {
		//TODO:
	}
	

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public short getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(short remotePort) {
		this.remotePort = remotePort;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
