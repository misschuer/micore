package cc.mi.core.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialFill {
	
	public static void main(String[] args) {
		int[] materials = {10, 15, 11, 9, 12, 1, 13, 1};
		List<Integer> ans = select(100, materials);
		int sum = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ans.size(); ++ i) {
			int g = i << 1;
			sum += materials[ g ] * ans.get(i);
			sb.append(materials[ g ] + " * " + ans.get(i) + " + ");
		}
		
		sb.replace(sb.length()-2, sb.length()-1, "=");
		sb.append(sum);
		System.out.println(sb);
//		select(100, 10, 9);
	}
	
	/**
	 * O(V*n*log(m))
	 * @param targetValue
	 * @param materials
	 * @return
	 */
	public static List<Integer> select(int targetValue, int...materials) {
		ensure(materials);
		int n = materials.length >>> 1;
		int[] dp = new int[targetValue+1];
		int[][] ans = new int[targetValue+1][ n ];
		
		for (int i = 0; i < n; ++ i) {
			int g = i << 1;
			int val = materials[ g ];
			int cnt = materials[g+1];
//			System.out.println(val + " : " + cnt);
			int tmpCnt = cnt;
			for (int k = 1; k <= tmpCnt; tmpCnt -= k, k <<= 1) {
				fill(dp, ans, i, k, val, targetValue);
			}
			
			if (tmpCnt > 0) {
				fill(dp, ans, i, tmpCnt, val, targetValue);
			}
		}
		List<Integer> ret = new ArrayList<>(n);
		for (int i = 0; i < n; ++ i) {
			ret.add(ans[targetValue][ i ]);
		}
		System.out.println(dp[targetValue]);
		System.out.println(Arrays.toString(ret.toArray()));
		return ret;
	}
	
	private static void fill(int[] dp, int[][] ans, int index, int cnt, int val, int targetValue) {
		int currValue = cnt * val;
		for (int k = targetValue; k > 0; -- k) {
			if (dp[ k ] < k) {
				if (k <= currValue) {
					dp[ k ] = currValue;
					ans[ k ][index] = cnt;
					System.out.print("1>");
				}
				else {
					dp[ k ] = dp[k-currValue] + currValue;
					copy(ans, k-currValue, k);
					ans[ k ][index] += cnt;
					System.out.print("2>");
				}
				System.out.println(Arrays.toString(ans[ k ]) + " = " + k + "$" + dp[ k ]);
			}
			else {
				if (k >= currValue) {
					if (dp[ k ] > dp[k-currValue] + currValue) {
						dp[ k ] = dp[k-currValue] + currValue;
						copy(ans, k-currValue, k);
						ans[ k ][index] += cnt;
						System.out.println("3>" + Arrays.toString(ans[ k ])+ " = " + k + "$" + dp[ k ]);
					}
				}
			}
		}
	}
	
	private static void copy(int[][] ans, int from, int to) {
		for (int i = 0; i < ans[from].length; ++ i) {
			ans[to][ i ] = ans[from][ i ];
		}
	}
	
	private static void ensure(int...materials) {
		if ((materials.length & 1) == 1) {
			throw new RuntimeException("materials数据有误");
		}
		for (int i = 0; (i << 1) < materials.length; ++ i) {
			int g = i << 1;
			if (materials[ g ] <= 0 || materials[g+1] < 0) {
				throw new RuntimeException("materials数据有误");
			}
		}
	}
}