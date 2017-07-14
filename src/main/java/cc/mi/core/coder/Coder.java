package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public interface Coder {
	void onEncode(ByteBuf buffer);
	void onDecode(ByteBuf buffer);
	
	public int getOpcode();
	
	public int getId();
	public void setId(int id);
	
	public int getInternalDestFD();
	public void setInternalDestFD(int internalDestFD);
}
