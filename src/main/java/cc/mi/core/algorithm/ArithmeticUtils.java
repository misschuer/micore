package cc.mi.core.algorithm;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ArithmeticUtils {
	public static final String SYMBOL_REVERSE_BIN = "b0";
	public static final String SYMBOL_REVERSE_OCT = "o0";
	public static final String SYMBOL_REVERSE_HEX = "x0";
	private static final int LEN = 31;
	private static int power[] = { 
		1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 
		2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 
		8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824 
	};

	public static void main(String args[]) {
		powerOf2();
	}

	public static void powerOf2() {
		for (int i = 0; i < LEN; ++i) {
			power[i] = 1 << i;
		}
		System.out.print("[1");
		for (int i = 1; i < LEN; ++i) {
			System.out.print(", " + power[i]);
		}
		System.out.println("]");
	}


	public static int closetPowerOf2(int value) {
//		return power[BinarySearch.findCeilNumber(power, value)];
		return 0;
	}

	public static int closetLengthWithPowerOf2(int value) {
		return closetPowerOf2(value) - value;
	}
	
	
	public static int sum(List<Integer> list) {
		int value = 0;

		for (int val:list) {
			value += val;
		}
		
		return value;
	}
	
	public static int min(List<Integer> list) {
		int value = list.get(0);

		for (int val:list) {
			if (value > val)
				value = val;
		}
		
		return value;
	}
	
	public static int max(List<Integer> list) {
		int value = list.get(0);

		for (int val:list) {
			if (value < val)
				value = val;
		}
		
		return value;
	}
	
	
	public static String bin(int value) {
		return bin(value, 0);
	}
	
	
	public static String bin(int value, int len) {
		return radixFormat(value, 1, len, SYMBOL_REVERSE_BIN);
	}
	
	
	public static String oct(int value) {
		return oct(value, 0);
	}
	
	
	public static String oct(int value, int len) {
		return radixFormat(value, 3, len, SYMBOL_REVERSE_OCT);
	}
	
	
	public static String hex(int value) {
		return hex(value, 0);
	}
	
	
	public static String hex(int value, int len) {
		return radixFormat(value, 4, len, SYMBOL_REVERSE_HEX);
	}
	
	
	private static String toDigit(int sin) {
		String item = "" + sin;
		if (sin >= 10)
			item = "" + (char)(sin - 10 + 'a');
		
		return item;
	}
	
	public static String radixFormat(int value, int digit, int len, String SYMBOL_REVERSE) {
		StringBuffer sBuffer = new StringBuffer();
		String ret = "";
		int radix = (1 << digit) - 1;
		
		while (value > 0) {
			int sin = value & radix;
			value >>= digit;
			String item = toDigit(sin);
			sBuffer.append(item);
		}
		for (int i = sBuffer.length(); i < len; ++ i) {
			sBuffer.append(0);
		}
		sBuffer.append(SYMBOL_REVERSE);
		ret = sBuffer.reverse().toString();
		sBuffer = null;
		
		return ret;
	}
	
	public static double eval(String exp) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine se = manager.getEngineByName("js");
	    double result = (double) se.eval(exp);
	    return result;
	}
	
	public static String repr(String str) {
		StringBuffer sbBuffer = new StringBuffer();
		
		for (int i = 0; i < str.length(); ++ i) {
			char ch = str.charAt(i);
			String ss = "" + ch;
			switch (ch) {
				case '\'':
					ss = "\\\'";
					break;
				case '\"':
					ss = "\\\"";
					break;
				case '\\':
					ss = "\\\\";
					break;
				default:
					break;
			}
			sbBuffer.append(ss);
		}
		
		String ret = sbBuffer.toString();
		sbBuffer = null;
		
		return ret;
	}
}