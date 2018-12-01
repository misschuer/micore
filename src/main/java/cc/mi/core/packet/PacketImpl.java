package cc.mi.core.packet;

import io.netty.buffer.ByteBuf;

public abstract class PacketImpl implements Packet {
	/**
	 * 消息号
	 */
	private int opcode;
	/**
	 *  如果是发给客户端的fd > 0, 否则只是内部发
	 */
	private int baseFd;
	
	public PacketImpl(int opcode) {
		this.opcode = opcode;
		this.baseFd = 0;
	}
	
	@Override
	public void onEncode(ByteBuf buffer) {
		buffer.writeInt(opcode);
		buffer.writeInt(baseFd);
		this.encode(buffer);
	}
	
	@Override
	public void onDecode(ByteBuf buffer) {
		this.opcode = buffer.readInt();
		this.baseFd = buffer.readInt();
		this.decode(buffer);
	}

	public abstract void encode(ByteBuf buffer);
	
	public abstract void decode(ByteBuf buffer);
	
	@Override
	public int getOpcode() {
		return this.opcode;
	}
	
	@Override
	public int getBaseFd() {
		return this.baseFd;
	}
	
	@Override
	public void setBaseFd(int fd) {
		this.baseFd = fd;
	}
	
	public abstract PacketImpl newInstance();
}
