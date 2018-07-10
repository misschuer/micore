package cc.mi.core.utils;

import java.util.Arrays;

/**
 * @author gy
 */
public class Mask {
	private final byte[] mask;
	private final int maxIndex;
	
	public Mask(int count) {
		this.maxIndex = count;
		this.mask = new byte[(count+7)>>>3];
	}
	
	public void fullAll() {
		Arrays.fill(mask, (byte)1);
	}
	
	public void clear() {
		Arrays.fill(mask, (byte)0);
	}
	
	public boolean isMarked(int indx) {
		if (indx >= this.getCount()) {
			return false;
		}
		return (this.mask[indx>>>3] & (1 << (indx & 0x7))) != 0;
	}
	
	public void mark(int indx) {
		if (indx >= this.getCount()) {
			return;
		}
		this.mask[indx>>>3] |= 1 << (indx & 0x7);
	}
	
	public void unMark(int indx) {
		if (indx >= this.getCount()) {
			return;
		}
		this.mask[indx>>>3] &= 0xFF ^ (1 << (indx & 0x7));
	}
	
	public int getCount() {
		return this.maxIndex;
	}
}
