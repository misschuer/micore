package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 玩家登出
 **/
public class PlayerLogoutMsg extends PacketImpl  {
	//客户端连接的fd
	private int clientFd;
	//玩家guid
	private String guid;

	public PlayerLogoutMsg() {
		super(32);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.clientFd);
		StringCoder.writeString(buffer, this.guid);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.clientFd = buffer.readInt(); 
		this.guid = StringCoder.readString(buffer);
	}
	
	public int getClientFd() {
		return this.clientFd;
	}
	
	public void setClientFd(int clientFd) {
		this.clientFd = clientFd;
	}
		
	public String getGuid() {
		return this.guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
		

	public PacketImpl newInstance() {
		return new PlayerLogoutMsg();
	}
}