package cc.mi.core.algorithm;


public class xxtea {
	/** KEYS 可变 */
	public static final String KEYS = "As a symbol of my love!";
	public static final byte NULL = 0;
	public static final long UNSIGNED_INTEGER_MAX = 0xFFFFFFFFL;
	public static final long UNSIGNED_BYTE_MAX = 0xFFL;
	
	public static void main(String[] args) throws Exception {
		String str = "活着60秒，想你一分钟。我做梦也想你";
		String enc = encrypt(str);
		String dec = decrypt(enc);
		
		System.out.println(enc.length() + " === " + enc);
		System.out.println(dec.length() + " === " + dec);
	}

	
	private static byte[] hex2bytearray(String s) {
		int length = s.length();
		byte[] result = new byte[length >> 1];
		
		for (int i = 0; i < length; i += 2) {
			result[i >> 1] = (byte) (Integer.parseInt(s.substring(i, i+2), 16) & UNSIGNED_BYTE_MAX);
		}
		
		return result;
	}

	
	private static String bytearray2hex(byte[] s) {
		String table = "0123456789abcdef";
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < s.length; ++ i) {
			int x = s[ i ] & 0xFF;
			sb.append(table.charAt(x >> 4));
			sb.append(table.charAt(x & 0xF));
		}
		
		return sb.toString();
	}


	private static byte[] ljust(byte[] s, int length, byte regex) {
		byte[] ret = new byte[length];
		
		for (int i = 0; i < s.length; ++ i)
			ret[ i ] = s[ i ];
		for (int i = s.length; i < length; ++ i)
			ret[ i ] = regex;
		
		return ret;
	}
	
	
	private static final long[] bytearray2longs(byte[] t) {
		int length = (t.length + 3) >> 2;
		byte[] s = ljust(t, length << 2, NULL);
		long[] ret = new long[length];
		
		for (int i = 0; i < length; ++ i) {
			long b = s[i * 4] & 0xFF;
			long b1 = (long)(s[i * 4 + 1] & 0xFF) <<  8;
			long b2 = (long)(s[i * 4 + 2] & 0xFF) << 16;
			long b3 = (long)(s[i * 4 + 3] & 0xFF) << 24;
			ret[ i ] = b | b1 | b2 | b3;
		}
		
		s = null;
		
		return ret;
	}

	
	private static byte[] rstrip(byte[] s, byte regex) {
		int index = s.length-1;
		
		for (index = s.length - 1; index >= 0; -- index)
			if (s[index] != regex) break;
		byte[] ret = new byte[index+1];
		for (int i = 0; i <= index; ++ i)
			ret[ i ] = s[ i ];
		
		return ret;
	}
	
	
	private static byte[] longs2bytearray(long[] s) {
		byte[] result = new byte[s.length << 2];
		
		for (int i = 0; i < s.length; ++ i) {
			long c = s[ i ];
			result[i * 4] = (byte) (c & UNSIGNED_BYTE_MAX);
			result[i * 4 + 1] = (byte) (c >>  8 & UNSIGNED_BYTE_MAX);
			result[i * 4 + 2] = (byte) (c >> 16 & UNSIGNED_BYTE_MAX);
			result[i * 4 + 3] = (byte) (c >> 24 & UNSIGNED_BYTE_MAX);
		}
		
		byte[] ret = rstrip(result, NULL);
		result = null;
		
		return ret;
	}
	
	
	private static long xx(long z, long y, long _sum, long[] k, int p, int e) {
		return ( (((z & UNSIGNED_INTEGER_MAX)>>5)^(y<<2)) + (((y & UNSIGNED_INTEGER_MAX)>>3)^(z<<4))^(_sum^y) + (k[(p & 3)^e]^z) ) & UNSIGNED_INTEGER_MAX;
	}
	
	
	private static void btea(long[] v, int n, long[] k) {
		final long DELTA = 0x9e3779b9L;
		
	    if (n > 1) { 
	        long z = v[n-1];
	        int q = 6 + 52 / n;
	        long _sum = 0;
	        
	        while (q > 0) {
	            q -= 1;
	            _sum = (_sum + DELTA) & UNSIGNED_INTEGER_MAX;
	            int e = (int) ((_sum >> 2) & 3);
	            int p = 0;
	            while (p < n - 1) {
	                long y = v[p + 1];
	                v[p] = v[p] + xx(z, y, _sum, k, p, e);
	                v[p] &= UNSIGNED_INTEGER_MAX;
	                z = v[p];
	                p += 1;
	            }
	            long y = v[0];
	            v[n-1] = v[n-1] + xx(z, y, _sum, k, p, e);
	            v[n-1] &= UNSIGNED_INTEGER_MAX;
	            z = v[n-1];
	        }
	    }
	    else if (n < -1) {
	        n = -n;
	        int q = 6 + 52 / n;
	        long _sum = q * DELTA;
	        long y = v[ 0 ];
	        while (_sum != 0) {
	            int e = (int) (((_sum & UNSIGNED_INTEGER_MAX) >> 2) & 3);
	            int p = n - 1;
	            while (p > 0) {
	                long z = v[p - 1];
	                v[p] = v[p] - xx(z, y, _sum, k, p, e);
	                v[p] &= UNSIGNED_INTEGER_MAX;
	                y = v[p];
	                p -= 1;
	            }
	            long z = v[n-1];
	            v[0] = v[0] - xx(z, y, _sum, k, p, e);
	            v[0] &= UNSIGNED_INTEGER_MAX;
	            y = v[0];
	            _sum = _sum - DELTA;
	        }
	    }
	}

	public static int getPower2NearBy(int length) throws Exception {
		int g = 1;
		
		while (g < length && g > 0) {
			g = g << 1;
		}
		
		if (g < 0)
			throw new Exception("KEYS's length is too long!");
		
		return g;
	}
	
	private static byte[] byteArrayKey() throws Exception {
		byte s[] = KEYS.getBytes("UTF-8");
		int length = getPower2NearBy(s.length);
		
		byte[] ret = ljust(s, length, NULL);
		s = null;
		
		return ret;
	}

	
	public static String encrypt(String str) throws Exception {
		byte[] bytekey = byteArrayKey();
		byte[] bytestr = str.getBytes("UTF-8");
		
		long[] v = bytearray2longs(bytestr);
		long[] k = bytearray2longs(bytekey);
		
	    btea(v, v.length, k);
	    
	    byte[] tp = longs2bytearray(v);
	    String result = bytearray2hex(tp);
	    
	    bytekey = null;
	    bytestr = null;
	    v  = null;
	    k  = null;
	    tp = null;
	    
	    return result;
	}
	
	
	public static String decrypt(String s) throws Exception {
		byte[] bytekey = byteArrayKey();
		byte[] bytestr = hex2bytearray(s);
		
		long[] v = bytearray2longs(bytestr);
		long[] k = bytearray2longs(bytekey);
		
	    btea(v, -v.length, k);
	    byte[] result = longs2bytearray(v);
	    
	    String ret = new String(result, "UTF-8");
	    bytekey = null;
	    bytestr = null;
	    v  = null;
	    k  = null;
	    result = null;
	    
	    return ret;
	}
}