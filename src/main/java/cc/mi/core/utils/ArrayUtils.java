package cc.mi.core.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	public static final List<Integer> bytesToIntegers(byte[] bytes) {
		int size = (bytes.length + 3) >> 2;
		List<Integer> mr = new ArrayList<>(size);
		for (int i = 0; i < bytes.length; i += 4) {
			int a = bytes[ i ];
			int b = 0;
			int c = 0;
			int d = 0;
			if (i + 1 < bytes.length) c = bytes[i+1] << 8;
			if (i + 2 < bytes.length) c = bytes[i+2] << 16;
			if (i + 3 < bytes.length) d = bytes[i+3] << 24;
			mr.add(a + b + c + d);
		}
		return mr;
	}
}
