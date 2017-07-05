package cc.mi.core.coder;


import io.netty.buffer.ByteBuf;

public interface Coder {
	void onEncode(ByteBuf buffer);
	void onDecode(ByteBuf buffer);
	public int getOpcode();
}
