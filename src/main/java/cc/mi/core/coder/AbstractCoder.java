package cc.mi.core.coder;

import io.netty.buffer.ByteBuf;

public abstract class AbstractCoder implements Packet {
	/**
	 * 消息号
	 */
	private int opcode;
	/**
	 *  如果是发给客户端的fd > 0, 否则只是内部发
	 */
	private int fd;
	
	public AbstractCoder(int opcode) {
		this.opcode = opcode;
		this.fd = 0;
	}
	
	@Override
	public void onEncode(ByteBuf buffer) {
		buffer.writeInt(opcode);
		buffer.writeInt(fd);
		this.encode(buffer);
	}
	
	@Override
	public void onDecode(ByteBuf buffer) {
		this.opcode = buffer.readInt();
		this.fd = buffer.readInt();
		this.decode(buffer);
	}

	public abstract void encode(ByteBuf buffer);
	
	public abstract void decode(ByteBuf buffer);
	
	@Override
	public int getOpcode() {
		return this.opcode;
	}
	
	@Override
	public int getFD() {
		return this.fd;
	}
	
	@Override
	public void setFD(int fd) {
		this.fd = fd;
	}
	
	public abstract AbstractCoder newInstance();
}
