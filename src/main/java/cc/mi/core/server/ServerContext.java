package cc.mi.core.server;

import cc.mi.core.coder.Packet;
import cc.mi.core.constance.OperateConst;
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
	}
	
	protected abstract void send(Packet coder);
	
	protected void sendPacketToOtherServer(int serverFd, Packet coder) {
//		coder.setId(0);
//		coder.setInternalDestFD(serverFd);
//		this.send(coder);
	}
	
	protected void sendToClient(Packet coder) {
//		coder.setId(fd);
//		coder.setInternalDestFD(MsgConst.MSG_TO_GATE);
//		this.send(coder);
	}
	
	protected void sendToCenter(Packet coder) {
		
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
	
	public void close(Channel channel, short reason, String data) {
		close(channel, reason, data, false);
	}
	
	public void close(Channel channel, short reason, String data, boolean isForced) {
		if (reason > 0) {
			this.callOperationResult(channel, OperateConst.OPERATE_TYPE_CLOSE, reason, data);
		}
		ContextManager.closeSession(channel, fd, isForced);
	}
	
	public void callOperationResult(Channel channel, short type, short reason, String data) {
//		OperationResult result = new OperationResult();
//		result.setData(data);
//		result.setReason(reason);
//		result.setType(type);
//		result.setId(this.fd);
//		result.setInternalDestFD(MsgConst.MSG_TO_GATE);
//		channel.writeAndFlush(result);
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
