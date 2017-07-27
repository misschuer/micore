package cc.mi.core.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class Bytes {
	static final int DEFAULT_LEN = 1 << 7;
	static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private byte[] values;
	
	public Bytes() {
		this(DEFAULT_LEN);
	}
	
	public Bytes(byte[] bytes) {
		this.values = bytes;
	}
	
	public Bytes(int len) {
		len = Math.max(len, DEFAULT_LEN);
		values = new byte[len];
	}
	
	public void clear() {
		Arrays.fill(values, (byte)0);
	}
	
	public int capacity() {
		return values.length;
	}
	
	public int intSize() {
		int len = this.values.length;
		return (len >> 2) + (len > 0 ? 1 : 0);
	}
	
	public void setBit(int indx, int offset) {
		indx += offset >> 5;
		offset &= 31;
		int bitSet = offset & 7;
		offset >>= 3;
		this.values[(indx << 2) + offset] |= 1 << bitSet;
	}
	
	public void unSetBit(int indx, int offset) {
		indx += offset >> 5;
		offset &= 31;
		int bitSet = offset & 7;
		offset >>= 3;
		this.values[(indx << 2) + offset] &= ~(1 << bitSet);
	}
	
	public void setByte(int indx, int offset, int value) {
		indx += offset >> 2;
		offset &= 3;
		this.values[(indx << 2) + offset] = (byte) value;
	}
	
	public void setShort(int indx, int offset, int value) {
		indx += offset >> 1;
		offset &= 1;
		this.values[(indx << 2) + offset+1] = (byte) ((value >>> 8) & 0xFF);
		this.values[(indx << 2) + offset] = (byte) (value & 0xFF);
	}
	
	public void setInt(int indx, int value) {
		this.values[(indx << 2) + 3] = (byte) ((value >>> 24) & 0xFF);
		this.values[(indx << 2) + 2] = (byte) ((value >>> 16) & 0xFF);
		this.values[(indx << 2) + 1] = (byte) ((value >>> 8) & 0xFF);
		this.values[(indx << 2) + 0] = (byte) (value & 0xFF);
	}
	
	public void setFloat(int indx, float value) {
		this.setInt(indx, Float.floatToRawIntBits(value));
	}
	
	public void setLong(int indx, long value) {
		this.values[(indx << 2) + 7] = ((byte) ((value >>> 56) & 0xFF));
		this.values[(indx << 2) + 6] = ((byte) ((value >>> 48) & 0xFF));
		this.values[(indx << 2) + 5] = ((byte) ((value >>> 40) & 0xFF));
		this.values[(indx << 2) + 4] = ((byte) ((value >>> 32) & 0xFF));
		this.values[(indx << 2) + 3] = ((byte) ((value >>> 24) & 0xFF));
		this.values[(indx << 2) + 2] = ((byte) ((value >>> 16) & 0xFF));
		this.values[(indx << 2) + 1] = ((byte) ((value >>> 8) & 0xFF));
		this.values[(indx << 2) + 0] = ((byte) (value & 0xFF));
	}

	public boolean getBit(int indx, int offset) {
		indx += offset >> 5;
		offset &= 31;
		int bitSet = offset & 7;
		offset >>= 3;
		return ((this.values[(indx << 2) + offset] >>> bitSet) & 1) == 1;
	}
	
	public byte getByte(int indx, int offset) {
		indx += offset >> 2;
		offset &= 3;
		return this.values[(indx << 2) + offset];
	}
	
	public int getUnsignedByte(int indx, int offset) {
		return Byte.toUnsignedInt(this.getByte(indx, offset));
	}
	
	public short getShort(int indx, int offset) {
		indx += offset >> 1;
		offset &= 1;
		
		int a0 = Byte.toUnsignedInt(this.values[(indx << 2) + offset  ]);
		int a1 = Byte.toUnsignedInt(this.values[(indx << 2) + offset+1]);
		
		return (short) ((a1 << 8) + a0);
	}
	
	public int getUnsignedShort(int indx, int offset) {
		return Short.toUnsignedInt(this.getShort(indx, offset));
	}
	
	public int getInt(int indx) {
		int a0 = Byte.toUnsignedInt(this.values[(indx << 2) + 0]);
		int a1 = Byte.toUnsignedInt(this.values[(indx << 2) + 1]);
		int a2 = Byte.toUnsignedInt(this.values[(indx << 2) + 2]);
		int a3 = Byte.toUnsignedInt(this.values[(indx << 2) + 3]);
		
		return (a3 << 24) + (a2 << 16) + (a1 << 8) + a0;
	}
	
	public float getFloat(int indx) {
		return Float.intBitsToFloat(this.getInt(indx));
	}
	
	public long getUnsignedInt(int indx) {
		return Integer.toUnsignedLong(this.getInt(indx));
	}
	
	public long getLong(int indx) {
		int a0 = Byte.toUnsignedInt(this.values[(indx << 2) + 0]);
		int a1 = Byte.toUnsignedInt(this.values[(indx << 2) + 1]);
		int a2 = Byte.toUnsignedInt(this.values[(indx << 2) + 2]);
		int a3 = Byte.toUnsignedInt(this.values[(indx << 2) + 3]);
		int a4 = Byte.toUnsignedInt(this.values[(indx << 2) + 4]);
		int a5 = Byte.toUnsignedInt(this.values[(indx << 2) + 5]);
		int a6 = Byte.toUnsignedInt(this.values[(indx << 2) + 6]);
		int a7 = Byte.toUnsignedInt(this.values[(indx << 2) + 7]);
		
		return ((long)(a7) << 56)
			+  ((long)(a6) << 48)
			+  ((long)(a5) << 40)
			+  ((long)(a4) << 32)
			+  ((long)(a3) << 24)
			+  ((long)(a2) << 16)
			+  ((long)(a1) << 8)
			+ a0;
	}
	
	
}
