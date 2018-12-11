package cc.mi.core.xlsxData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.utils.MIMath;
import cc.mi.core.utils.Point2D;
import cc.mi.core.utils.ShortestPath;
import cc.mi.core.utils.Tuple3D;

/**
 * 主干道 为了加速A*寻路
 * @author gy
 *
 */
public class MapMainLoad {
	private final Map<Integer, Tuple3D<Integer>> hash = new HashMap<>();
	private float dist[][];
	public MapMainLoad() {}
	
	public void addMainNode(int id, int x, int y) {
		Tuple3D<Integer> node = new Tuple3D<Integer>(x, y, id);
		this.hash.put(id, node);
	}
	
	public void initDist(int n) {
		this.dist = new float[ n ][ n ];
		for (int i = 0; i < n; ++ i) {
			this.dist[ i ][ i ] = 0;
			for (int j = i+1; j < n; ++ j) {
				this.dist[ i ][ j ] = this.dist[ j ][ i ] = 1 << 30;
			}
		}
	}
	
	public void linkMainNode(int id1, int id2) {
		Tuple3D<Integer> info1 = hash.get(id1);
		if (info1 == null) {
			throw new RuntimeException();
		}
		Tuple3D<Integer> info2 = hash.get(id2);
		if (info2 == null) {
			throw new RuntimeException();
		}
		
		dist[id1][id2] = dist[id2][id1] = (float) MIMath.INSTANCE.getDistance(info1.getX(), info1.getY(), info2.getX(), info2.getY());
	}

//	TODO:生成A*算法 或者dijkstra算法, floyd算法对象
	public ShortestPath createShortestPathGraph(float sx, float sy, float tx, float ty) {
		
		return null;
	}

	static class MainNode {
		private final Point2D<Integer> node;
		private final List<Point2D<Integer>> neib;
		
		public MainNode(Point2D<Integer> node) {
			this.node = node;
			this.neib = new LinkedList<>();
		}
		
		public void addNeibNode(Point2D<Integer> neibNode) {
			this.neib.add(neibNode);
		}
		
		public void addAllNeibNode(List<Point2D<Integer>> neibNodeList) {
			this.neib.addAll(neibNodeList);
		}

		public Point2D<Integer> getNode() {
			return node;
		}
	}
}
