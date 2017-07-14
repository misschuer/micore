package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public abstract class AbstractCoder implements Coder {
	private int opcode;
	private int id;
	
	public AbstractCoder(int opcode) {
		this.opcode = opcode;
	}
	
	@Override
	public void onEncode(ByteBuf buffer) {
		buffer.writeInt(opcode);
		this.encode(buffer);
		buffer.writeInt(id);
	}

	@Override
	public void onDecode(ByteBuf buffer) {
		this.opcode = buffer.readInt();
		this.decode(buffer);
		this.id		= buffer.readInt();
	}
	
	public abstract void encode(ByteBuf buffer);
	
	public abstract void decode(ByteBuf buffer);
	
	@Override
	public int getOpcode() {
		return this.opcode;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public abstract AbstractCoder newInstance();
}
