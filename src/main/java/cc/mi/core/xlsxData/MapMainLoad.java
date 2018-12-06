package cc.mi.core.xlsxData;

import java.util.LinkedList;
import java.util.List;

import cc.mi.core.utils.Point2D;

/**
 * 主干道 为了加速A*寻路
 * @author gy
 *
 */
public class MapMainLoad {
	private final List<MainNode> nodeList;
	
	public MapMainLoad() {
		this.nodeList = new LinkedList<>();
	}
	
	public void addMainNode(int x, int y, List<Point2D<Integer>> neibNodeList) {
		MainNode node = new MainNode(new Point2D<Integer>(x, y));
		node.addAllNeibNode(neibNodeList);
		this.nodeList.add(node);
	}

//	生成A*算法 或者dijkstra算法 对象
//	public Astar createAstarGraph() {
//		return null;
//	}

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
