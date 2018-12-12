package cc.mi.core.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * 适用 n <= 100
 * @author gy
 *
 */
public class Floyd extends ShortestPath {
	protected final int n;
	
	public Floyd(int n) {
		this.n = n;
	}
	
	@Override
	public List<Integer> getPath(final int a, final int b, final float dist[][]) {
		// 初始化
		// 到达j点需要经过的最优点
		int path[][] = new int[ n ][ n ];
		for (int i = 0; i < n; ++ i) {
			for (int j = 0; j < n; ++ j) {
				path[ i ][ j ] = j;
			}
		}
		
		for (int v = 0; v < n; ++ v) {
			for (int i = 0; i < n; ++ i) {
				for (int j = 0; j < n; ++ j) {
					if (dist[ i ][ j ] > dist[ i ][ v ] + dist[ v ][ j ]) {
						dist[ i ][ j ] = dist[ i ][ v ] + dist[ v ][ j ];
						path[ i ][ j ] = path[ i ][ v ];
					}
				}
			}
		}
		
		List<Integer> ret = new LinkedList<>();
		int temp = a;
		while (path[temp][ b ] != b) {
			ret.add(path[temp][ b ]);
			temp = path[temp][ b ];
		}
		ret.add(b);
		
		return ret;
	}
}
