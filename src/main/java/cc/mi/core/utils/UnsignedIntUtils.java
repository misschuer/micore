package cc.mi.core.utils;

public class UnsignedIntUtils {
	
	public static int unsignedByteToInt(byte value) {
		return 0xFF & value;
	}
	
	public static int unsignedShortToInt(short value) {
		return 0xFFFF & value;
	}
	
	public static long unsignedIntToLong(int value) {
        return value & 0xFFFFFFFFL;
	}
	
	public static void main(String[] args) {
		System.out.println(unsignedByteToInt((byte)((1<<8)-1)));
		System.out.println(unsignedShortToInt((short)((1<<16)-1)));
		System.out.println(unsignedIntToLong(-1));
	}
}
