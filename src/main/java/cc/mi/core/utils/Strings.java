package cc.mi.core.utils;

import java.util.Arrays;

public class Strings {
	private String[] values;
	
	public Strings(int len) {
		this.values = new String[len];
	}
	
	public int capacity() {
		return values.length;
	}
	
	public void capacity(int len) {
		// 长度不比原来大不需要扩展
		if (this.values.length >= len) {
			return;
		}
		int oldLen = this.values.length;
		int newLen = len > (oldLen << 1) ? len + oldLen : oldLen << 1;
		String[] tmp = new String[newLen];
		System.arraycopy(this.values, 0, tmp, 0, this.values.length);
		this.values = tmp;
	}
	
	private void ensureIndex(int index) {
		if (index >= this.capacity()) {
			this.capacity(index);
		}
	}
	
	public String get(int index) {
		if (index >= this.capacity()) {
			return null;
		}
		return values[index];
	}
	
	public void set(int index, String str) {
		this.ensureIndex(index);
		this.values[index] = str;
	}
	
	public void clear() {
		Arrays.fill(this.values, null);
	}
}
