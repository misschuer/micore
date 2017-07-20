package cc.mi.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KMP {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int t = cin.nextInt();
		for (int i = 0; i < t; ++ i) {
			String p = cin.next();
			String str = cin.next();
			System.out.println(matchCountRepeat(p, str));
		}
		cin.close();
	}

	public static int[] getNext(String p) {
        int len = p.length();
        int[] b = new int[len+1];
        int   i =  0;
        int   k = -1;
        
        b[ i ] = k;
        while (i < len) {
            if (k < 0 || p.charAt(i) == p.charAt(k)) {
                b[++ i] = ++ k;
                if (i < p.length() && p.charAt(i) == p.charAt(k)) {
                    b[ i ] = b[ k ];
                }
            }
            else {
                k = b[ k ];
            }
        }
        
        return b;
    }
    
    public static int matchsign(int[] next, String p, String str) {
        int index = -1;
        int i     =  0;
        int j     =  0;
        
        while (i < p.length() && j < str.length()) {
            if (i < 0 || p.charAt(i) == str.charAt(j)) {
                ++ i;
                ++ j;
            }
            else {
                i = next[ i ];
            }
        }
        if (i >= p.length()) {
            index = j - i;
        }
        
        return index;
    }
    
    public static List<Integer> matchIndexes(String p, String str) {
    	int[] next = getNext(p);
        List<Integer> indexList = new ArrayList<Integer>();
        int i     =  0;
        int j     =  0;
        
        while (j < str.length()) {
	        while (i < p.length() && j < str.length()) {
	            if (i < 0 || p.charAt(i) == str.charAt(j)) {
	                ++ i;
	                ++ j;
	            }
	            else {
	                i = next[ i ];
	            }
	        }
	        if (i >= p.length()) {
	        	int index = j - i;
	            i = 0;
	            indexList.add(index);
	        }
        }
        indexList.add(str.length());
        next = null;
        
        return indexList;
    }
    
    public static boolean contains(String str, String p) {
    	int[] next = getNext(p);
    	return matchsign(next, p, str) >= 0;
    }
    
    public static int matchCount(String p, String str) {
    	int[] next = getNext(p);
        int ans = 0;
        int i   = 0;
        int j   = 0;
        
        while (i < p.length() && j < str.length()) {
	        while (i < p.length() && j < str.length()) {
	            if (i < 0 || p.charAt(i) == str.charAt(j)) {
	                ++ i;
	                ++ j;
	            }
	            else {
	                i = next[ i ];
	            }
	        }
	        if (i >= p.length()) {
	            ans ++;
	            i = 0;
	        }
        }
        
        return ans;
    }
    
    public static int matchCountRepeat(String p, String str) {
    	int[] next = getNext(p);
        int ans = 0;
        int i   = 0;
        int j   = 0;
        
        while (i < p.length() && j < str.length()) {
	        while (i < p.length() && j < str.length()) {
	            if (i < 0 || p.charAt(i) == str.charAt(j)) {
	                ++ i;
	                ++ j;
	            }
	            else {
	                i = next[ i ];
	            }
	        }
	        if (i >= p.length()) {
	            ans ++;
	            i = next[ i ];
	        }
        }
        
        return ans;
    }
}
