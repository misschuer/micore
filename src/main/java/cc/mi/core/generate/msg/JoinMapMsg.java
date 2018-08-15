package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 玩家加入地图
 **/
public class JoinMapMsg extends PacketImpl  {
	//客户端连接的fd
	private int fd;
	//玩家id
	private String ownerId;
	//传送地图id
	private int teleMapId;
	//地图实例id
	private int instId;
	//x坐标
	private float x;
	//y坐标
	private float y;
	//传送标志
	private byte sign;

	public JoinMapMsg() {
		super(29);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		StringCoder.writeString(buffer, this.ownerId);
		buffer.writeInt(this.teleMapId);
		buffer.writeInt(this.instId);
		buffer.writeFloat(this.x);
		buffer.writeFloat(this.y);
		buffer.writeByte(this.sign);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.ownerId = StringCoder.readString(buffer);
		this.teleMapId = buffer.readInt(); 
		this.instId = buffer.readInt(); 
		this.x = buffer.readFloat(); 
		this.y = buffer.readFloat(); 
		this.sign = buffer.readByte(); 
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
		
	public int getTeleMapId() {
		return this.teleMapId;
	}
	
	public void setTeleMapId(int teleMapId) {
		this.teleMapId = teleMapId;
	}
		
	public int getInstId() {
		return this.instId;
	}
	
	public void setInstId(int instId) {
		this.instId = instId;
	}
		
	public float getX() {
		return this.x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
		
	public float getY() {
		return this.y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
		
	public byte getSign() {
		return this.sign;
	}
	
	public void setSign(byte sign) {
		this.sign = sign;
	}
		

	public PacketImpl newInstance() {
		return new JoinMapMsg();
	}
}