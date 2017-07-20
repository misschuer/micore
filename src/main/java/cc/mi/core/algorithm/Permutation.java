package cc.mi.core.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
	private static final int[] factor = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880 };

	public static String release(String str, char ch) {
		String ret = "";

		for (int i = 0; i < str.length(); ++i) {
			char cht = str.charAt(i);
			if (cht > ch)
				cht -= 1;
			ret += cht;
		}

		return ret;
	}

	public static void charAddStringArray(char ch, List<String> sub) {
		for (int i = 0; i < sub.size(); ++i)
			sub.set(i, ch + sub.get(i));
	}

	private static StringBuilder clone(StringBuilder sb) {
		return new StringBuilder(sb.toString());
	}

	public static List<String> permutation(StringBuilder sb) {
		int len = sb.length();
		List<String> ret = new ArrayList<String>(factor[len]);

		if (len == 1) {
			ret.add(sb.toString());
			return ret;
		}
		for (int i = 0; i < len; ++i) {
			List<String> sub = permutation(clone(sb).deleteCharAt(i));
			charAddStringArray(sb.charAt(i), sub);
			ret.addAll(sub);
		}

		return ret;
	}

	public static int permutationIndex(String str) {
		if (str.length() == 1)
			return 1;

		int rtlen = factor[str.length() - 1];
		char ch = str.charAt(0);
		int value = ch - '1';
		int ans = value * rtlen;
		String next = release(str.substring(1), ch);

		return ans + permutationIndex(next);
	}
	
	public static String kthPermutation(int index, int len) {
		boolean[] vist = new boolean[len+1];
	    String str = "";
	    index --;
	    
		for (int i = 0; i < len; ++ i) {
	        int div = index / factor[len-i-1];
	        int j = findLessExist(vist, div);
	        str += j;
	        vist[ j ] = true;  
	        index %= factor[len-i-1];  
	    }  
		return str;
	}
	
	public static int findLessExist(boolean[] vist, int div) {
		int i = 0;
		for (i = 1; i <= vist.length; ++ i) {
			if (!vist[ i ]) {
				if (div == 0) break;
				div --;
			}
		}
		return i;
	}

	public static void main(String[] args) {
		int len = 4;
		for (int i = 1; i <= factor[len]; ++ i) {
			System.out.println(kthPermutation(i, len));
		}
	}
}
