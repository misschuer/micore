package cc.mi.core.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ByteBuffer {
	static final int DEFAULT_LEN = 1 << 7;
	static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private byte[] values;
	private int reader = 0;
	private int writer = 0;
	
	public final static ByteBuffer alloc() {
		return new ByteBuffer();
	}
	
	public final static ByteBuffer alloc(int length) {
		return new ByteBuffer(length);
	}
	
	public ByteBuffer(byte[] bytes) {
		this.values = bytes;
		this.writer = bytes.length;
	}
	
	private ByteBuffer() {
		this(DEFAULT_LEN);
	}
	
	private ByteBuffer(int len) {
		len = Math.max(len, DEFAULT_LEN);
		values = new byte[len];
	}
	
	public int capacity() {
		return values.length;
	}
	
	public void readerIndex(int index) {
		if (index < 0 || index > this.writerIndex()) {
			throw new RuntimeException(String.format("index=%d, 超出容量", index));
		}
		this.reader = index;
	}
	
	public int readerIndex() {
		return this.reader;
	}
	
	public void writerIndex(int index) {
		if (index < 0 || index > capacity()) {
			throw new RuntimeException(String.format("index=%d, 超出容量", index));
		}
		this.writer = index;
	}
	
	public int writerIndex() {
		return this.writer;
	}
	
	private final void writeByte0(byte value) {
		values[nextInputIndex()] = value;
	}
	
	public void writeByte(byte value) {
		checkIndex(this.writerIndex());
		this.writeByte0(value);
	}
	
	public void writeBoolean(boolean value) {
		this.writeByte((byte) (value ? 1 : 0));
	}

	public void writeShort(short value) {
		checkIndex(this.writerIndex(), 2);
		this.writeByte0((byte) ((value >>> 8) & 0xFF));
		this.writeByte0((byte) (value & 0xFF));
	}
	
	public void writeUnsignedShort(int value) {
		this.writeShort((short) value);
	}
	
	public void writeInt(int value) {
		checkIndex(this.writerIndex(), 4);
		this.writeByte0((byte) ((value >>> 24) & 0xFF));
		this.writeByte0((byte) ((value >>> 16) & 0xFF));
		this.writeByte0((byte) ((value >>> 8) & 0xFF));
		this.writeByte0((byte) (value & 0xFF));
	}

	public void writeFloat(float value) {
		this.writeInt(Float.floatToRawIntBits(value));
	}

	public void writeLong(long value) {
		checkIndex(this.writerIndex(), 8);
		this.writeByte0((byte) ((value >>> 56) & 0xFF));
		this.writeByte0((byte) ((value >>> 48) & 0xFF));
		this.writeByte0((byte) ((value >>> 40) & 0xFF));
		this.writeByte0((byte) ((value >>> 32) & 0xFF));
		this.writeByte0((byte) ((value >>> 24) & 0xFF));
		this.writeByte0((byte) ((value >>> 16) & 0xFF));
		this.writeByte0((byte) ((value >>> 8) & 0xFF));
		this.writeByte0((byte) (value & 0xFF));
	}

	public void writeDouble(double value) {
		this.writeLong(Double.doubleToRawLongBits(value));
	}

	public void writeString(String value) {
		byte[] bytes = value.getBytes(DEFAULT_CHARSET);
		checkString(bytes);
		this.writeUnsignedShort(bytes.length);
		this.writeBytes(bytes);
	}
	
	public void writeBytes(byte[] bytes) {
		checkIndex(this.writerIndex(), bytes.length);
		for (int i = 0; i < bytes.length; ++ i) {
			this.writeByte0(bytes[ i ]);
		}
	}
	
	private final byte readByte0() {
		return values[this.nextOutputIndex()];
	}
	
	public byte readByte() {
		checkEOF(this.readerIndex());
		return this.readByte0();
	}
	
	public boolean readBoolean() {
		return this.readByte() > 0 ? true : false;
	}
	
	public short readShort() {
		checkEOF(this.readerIndex(), 2);
		return (short) (((this.readByte0() & 0xFF) << 8) + (this.readByte0() & 0xFF));
	}
	
	public int readUnsignedShort() {
		return 0xFFFF & this.readShort();
	}
	
	public int readInt() {
		checkEOF(this.readerIndex(), 4);
		return ((this.readByte0() & 0xFF) << 24)
			+  ((this.readByte0() & 0xFF) << 16)
			+  ((this.readByte0() & 0xFF) << 8)
			+   (this.readByte0() & 0xFF);
	}
	
	public float readFloat() {
		return Float.intBitsToFloat(this.readInt());
	}
	
	public long readLong() {
		checkEOF(this.readerIndex(), 8);
		return ((long)(this.readByte0() & 0xFF) << 56)
			+  ((long)(this.readByte0() & 0xFF) << 48)
			+  ((long)(this.readByte0() & 0xFF) << 40)
			+  ((long)(this.readByte0() & 0xFF) << 32)
			+  ((long)(this.readByte0() & 0xFF) << 24)
			+  ((long)(this.readByte0() & 0xFF) << 16)
			+  ((long)(this.readByte0() & 0xFF) << 8)
			+   (long)(this.readByte0() & 0xFF);
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(this.readLong());
	}
	
	public String readString() {
		int length = this.readUnsignedShort();
		return new String(this.readBytes(length), DEFAULT_CHARSET);
	}

	public byte[] readBytes(int length) {
		checkEOF(this.readerIndex(), length);
		byte[] bytes = new byte[length];
		System.arraycopy(values, this.readerIndex(), bytes, 0, length);
		this.reader += length;
		return bytes;
	}
	
	public byte[] getBytes() {
		return this.values;
	}
	
	private final void checkIndex(int index) {
		checkIndex(index, 1);
	}
	
	private final void checkString(byte[] bytes) {
		if (bytes.length >= 1 << 16) {
			throw new RuntimeException("string is too long");
		}
	}
	
	private final void checkIndex(int index, int fieldLength) {
		if (isOutOfBounds(index, fieldLength, values.length)) {
			grow(index + fieldLength);
		}
	}
	
	private final void grow(int neededCapacity) {
		int newCapacity = Math.max(neededCapacity, values.length + (values.length >>> 1));
		values = Arrays.copyOf(values, newCapacity);
	}
	
	private final boolean isOutOfBounds(int index, int fieldLength, int capacity) {
		return index + fieldLength > capacity;
	}
	
	private final void checkEOF(int index) {
		checkEOF(index, 1);
	}
	
	private final void checkEOF(int index, int fieldLength) {
		if (this.readerIndex() + fieldLength > this.writerIndex()) {
			throw new RuntimeException("read end of file");
		}
	}
	
	private final int nextInputIndex() {
		return this.writer ++;
	}
	
	private final int nextOutputIndex() {
		return this.reader ++;
	}
	
	public static void main(String[] args) {
		ByteBuffer rb = new ByteBuffer();
		rb.writeString("阿凡达激发了第三方姐啊来得及来看岁的李电视李电视发呆发呆发李电视发呆发呆发李电视发呆发呆发李电视发呆发呆发发呆发呆发凤姐阿拉丁放假啦");
		System.out.println(rb.readString());
	}
}
