package cc.mi.core.packet;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;

public class StringCoder {
	public static final Charset UTF8 = Charset.forName("UTF-8");
	private StringCoder() {}
	
	public static void writeString(ByteBuf buffer, String str) {
		byte[] bytes = str.getBytes(UTF8);
		buffer.writeShort(bytes.length);
		buffer.writeBytes(bytes);
	}
	
	public static String readString(ByteBuf buffer) {
		int size = buffer.readUnsignedShort();
		byte[] bytes = new byte[size];
		buffer.readBytes(bytes);
		
		return new String(bytes, UTF8);
	}
}
