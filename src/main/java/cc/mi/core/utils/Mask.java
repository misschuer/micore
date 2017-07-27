package cc.mi.core.utils;

import java.util.Arrays;

public class Mask {
	// 掩码
	private final byte[] mask;
	
	public Mask(int count) {
		this.mask = new byte[count<<2];
	}
	
	public void clear() {
		Arrays.fill(mask, (byte)0);
	}
	
	public boolean isMarked(int indx) {
		if (indx < this.getCount()) {
			return (this.mask[indx>>>3] & (1 << (indx & 0x7))) != 0;
		}
		return false;
	}
	
	public void mark(int indx) {
		if (indx >= this.getCount()) {
			return;
		}
		this.mask[indx>>>3] |= 1 << (indx & 0x7);
	}
	
	public void unMark(int indx) {
		if (indx < this.getCount()) {
			this.mask[indx>>>3] &= 0xFF ^ (1 << (indx & 0x7));
		}
	}
	
	public void fill(int intIndex, int value) {
		int offset = intIndex << 2;
		for (int i = 0; i < 4; ++ i) {
			byte val = (byte) (value & 0xFF);
			value >>>= 8;
			this.mask[offset+i] = val;
		}
	}
	
	public int getCount() {
		return mask.length << 3;
	}
}
