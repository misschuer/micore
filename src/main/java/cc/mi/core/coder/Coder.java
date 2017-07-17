package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public interface Coder {
	void onEncode(ByteBuf buffer);
	void onDecode(ByteBuf buffer);
	
	void onEncode(ByteBuf buffer, boolean isfromClient);
	void onDecode(ByteBuf buffer, boolean isfromClient);
	
	public int getOpcode();
	
	public int getId();
	public void setId(int id);
	
	public int getInternalDestFD();
	public void setInternalDestFD(int internalDestFD);
}
