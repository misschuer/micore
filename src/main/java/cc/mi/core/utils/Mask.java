package cc.mi.core.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author gy
 */
public class Mask implements Cloneable {
	static final byte MAX_VALUE = (byte)((1 << 8) - 1);
	private final byte[] mask;
	private final int maxIndex;
	
	public Mask(int count) {
		this.maxIndex = count;
		this.mask = new byte[(count+7)>>>3];
	}
	
	public void fullAll() {
		Arrays.fill(mask, MAX_VALUE);
		int rest = this.maxIndex & 7;
		if (rest > 0) {
			int last = mask.length-1;
			mask[last] &= (1 << rest) - 1;
		}
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
	
	public List<Integer> toNewList() {
		return ArrayUtils.bytesToIntegers(this.mask);
	}
	
	public Mask clone() {
		Mask newMask = new Mask(this.maxIndex);
		System.arraycopy(this.mask, 0, newMask.mask, 0, this.maxIndex);
		return newMask;
	}
}
