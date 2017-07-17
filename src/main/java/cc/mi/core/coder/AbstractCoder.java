package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public abstract class AbstractCoder implements Coder {
	private int opcode;
	private int id;
	// 仅内部连接用(内部目标fd), -2表示从客户端来的, -1表示给客户端的
	private int internalDestFD = -2;
	
	public AbstractCoder(int opcode) {
		this.opcode = opcode;
	}
	
	@Override
	public void onEncode(ByteBuf buffer) {
		this.onEncode(buffer, false);
	}
	
	@Override
	public void onEncode(ByteBuf buffer, boolean isfromClient) {
		buffer.writeInt(opcode);
		this.encode(buffer);
		if (!isfromClient) {
			buffer.writeInt(id);
			buffer.writeInt(internalDestFD);
		}
	}
	
	@Override
	public void onDecode(ByteBuf buffer) {
		this.onDecode(buffer, false);
	}

	@Override
	public void onDecode(ByteBuf buffer, boolean isfromClient) {
		this.opcode = buffer.readInt();
		this.decode(buffer);
		if (!isfromClient) {
			this.id		= buffer.readInt();
			this.internalDestFD = buffer.readInt();
		}
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
	
	@Override
	public int getInternalDestFD() {
		return internalDestFD;
	}
	
	@Override
	public void setInternalDestFD(int internalDestFD) {
		this.internalDestFD = internalDestFD;
	}
	
	public abstract AbstractCoder newInstance();
}
