package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public abstract class AbstractCoder implements Coder {
	private int opcode;
	
	public AbstractCoder(int opcode) {
		this.opcode = opcode;
	}
	
	@Override
	public void onEncode(ByteBuf buffer) {
		buffer.writeInt(opcode);
		this.encode(buffer);
	}

	@Override
	public void onDecode(ByteBuf buffer) {
		opcode = buffer.readInt();
		this.decode(buffer);
	}
	
	public abstract void encode(ByteBuf buffer);
	
	public abstract void decode(ByteBuf buffer);
	
	public int getOpcode() {
		return opcode;
	}
}
