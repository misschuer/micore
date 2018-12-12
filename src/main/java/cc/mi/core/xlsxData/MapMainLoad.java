package cc.mi.core.xlsxData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.utils.MIMath;
import cc.mi.core.utils.Point2D;
import cc.mi.core.utils.ShortestPath;

/**
 * 主干道 为了加速A*寻路
 * @author gy
 *
 */
public class MapMainLoad {
	private final Map<Integer, JointNode<Float, Integer>> hash = new HashMap<>();
	private float baseDist[][];
	private int n;
	public MapMainLoad() {}
	
	public void addMainNode(int id, int x, int y) {
		JointNode<Float, Integer> node = new JointNode<Float, Integer>((float)x, (float)y, id);
		this.hash.put(id, node);
	}
	
	public void initDist(int n) {
		this.n = n;
		this.baseDist = new float[ n ][ n ];
		for (int i = 0; i < n; ++ i) {
			this.baseDist[ i ][ i ] = 0;
			for (int j = i+1; j < n; ++ j) {
				this.baseDist[ i ][ j ] = this.baseDist[ j ][ i ] = (1 << 30) - 1;
			}
		}
	}
	
	public void putNode(JointNode<Float, Integer> a, JointNode<Float, Integer> b) {
		this.hash.put(n-2, a);
		this.hash.put(n-1, b);
	}
	
	public JointNode<Float, Integer> getNode(int indx) {
		return this.hash.get(indx);
	}
	
	public float[][] cloneDist() {
		float newDist[][] = new float[ n ][ n ];
		for (int i = 0; i < n; ++ i) {
			for (int j = 0; j < n; ++ j) {
				newDist[ i ][ j ] = this.baseDist[ i ][ j ];
			}
		}
		
		return newDist;
	}
	
	public List<JointNode<Float, Integer>> getNodeInfoList() {
		List<JointNode<Float, Integer>> infos = new ArrayList<>();
		for (JointNode<Float, Integer> info : hash.values()) {
			infos.add(info);
		}
		return infos;
	}
	
	public void linkMainNode(int id1, int id2) {
		JointNode<Float, Integer> info1 = hash.get(id1);
		if (info1 == null) {
			throw new RuntimeException();
		}
		JointNode<Float, Integer> info2 = hash.get(id2);
		if (info2 == null) {
			throw new RuntimeException();
		}
		
		baseDist[id1][id2] = baseDist[id2][id1] = (float) MIMath.INSTANCE.getDistance(info1.getX(), info1.getY(), info2.getX(), info2.getY());
	}

//	生成A*算法 或者dijkstra算法 或者 floyd算法对象
	public ShortestPath createAvailableGraph() {
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

	public int getN() {
		return n;
	}
}
