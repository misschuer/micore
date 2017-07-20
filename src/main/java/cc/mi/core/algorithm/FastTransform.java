package cc.mi.core.algorithm;

public class FastTransform {
	private static Complex[] toComplexes(String a, int n) {
		int length = a.length();
		Complex[] cps = new Complex[n];

		for (int i = 0; i < n; ++i) {
			int real = 0;
			if (i < length) {
				real = a.charAt(length - i - 1) - '0';
			}
			cps[i] = new Complex(real, 0);
		}

		return cps;
	}

	private static int getSize(String a, String b) {
		int mmax = a.length() + b.length();
		int n = ArithmeticUtils.closetPowerOf2(mmax);

		return n;
	}

	public static String Multiply(String stra, String strb) {
		int n = getSize(stra, strb);
		Complex[] a = FFT(toComplexes(stra, n), 1);
		Complex[] b = FFT(toComplexes(strb, n), 1);

		Complex[] c = new Complex[n];

		for (int i = 0; i < n; ++i) {
			c[i] = a[i].multiply(b[i]);
		}

		return DFT(c);
	}

	/** recursive */
	private static Complex[] FFT(Complex[] a, int flag) {
		int n = a.length;
		if (n == 1)
			return a;

		double base = 2 * Math.PI / n * flag;
		Complex Wn = new Complex(Math.cos(base), Math.sin(base));
		Complex W = new Complex(1, 0);
		int half = n >> 1;
		Complex[] a0 = new Complex[half];
		Complex[] a1 = new Complex[half];

		for (int i = 0; i < n; i += 2) {
			int index = i >> 1;
			a0[index] = a[i];
			a1[index] = a[i + 1];
		}

		Complex[] y0 = FFT(a0, flag);
		Complex[] y1 = FFT(a1, flag);
		Complex[] y = new Complex[n];

		for (int k = 0; k < half; ++k) {
			Complex tmp = W.multiply(y1[k]);
			y[k] = y0[k].add(tmp);
			y[k + half] = y0[k].substract(tmp);
			W = W.multiply(Wn);
		}

		return y;
	}

	// private static void bitRev(Complex[] a) {
	// int n = a.length;
	//
	// for (int i = 1, j = n >> 1; i < n - 1; ++ i) {
	// if (i < j) {
	// Complex tmp = a[ i ];
	// a[ i ] = a[ j ];
	// a[ j ] = tmp;
	// }
	// int k = n >> 1;
	// while (j >= k) {
	// j -= k;
	// k >>= 1;
	// }
	// if (j < k) j += k;
	// }
	// }
	//
	// private static Complex[] FFT(Complex[] a, int flag) {
	// int n = a.length;
	// Complex[] y = a;
	//
	// bitRev(y);
	// for (int b = 2; b <= n; b <<= 1) {
	// double base = 2.0 * Math.PI * flag / b;
	// double real = Math.cos(base);
	// double image = Math.sin(base);
	// Complex Wn = new Complex(real, image);
	// int half = b >> 1;
	//
	// for (int i = 0; i < n; i += b) {
	// Complex W = new Complex(1, 0);
	// for (int j = 0; j < half; ++ j) {
	// int k = i + j;
	// Complex t = W.multiply(y[k+half]);
	// Complex u = y[ k ];
	// y[k+half] = u.substract(t);
	// y[ k ] = u.add(t);
	// W = W.multiply(Wn);
	// }
	// }
	// }
	//
	// return y;
	// }

	private static String DFT(Complex[] a) {
		Complex[] b = FFT(a, -1);
		int n = b.length;
		StringBuffer sb = new StringBuffer();

		int tmp = 0;
		for (int i = 0; i < n; ++i) {
			Complex cp = b[i];
			int nt = (int) (cp.getReal() / n + 0.5) + tmp;
			sb.append(nt % 10);
			tmp = nt / 10;
		}

		while (tmp > 0) {
			sb.append(tmp % 10);
			tmp = tmp / 10;
		}

		String str = sb.reverse().toString();
		int i = 0;
		for (; i < str.length(); ++i) {
			if (str.charAt(i) != '0')
				break;
		}

		return str.substring(i);
	}
}
