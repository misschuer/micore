package cc.mi.core.utils;

import java.util.LinkedList;
import java.util.Queue;

public class Path {
	private final Queue<Point2D<Float>> pathList;
	public Path() {
		this.pathList = new LinkedList<>();
	}
	
	/**
	 * 把点加入到路径中
	 * @param point
	 */
	public void addPath(Point2D<Float> point) {
		this.pathList.add(point);
	}
	
	// 返回最前面的点
	public Point2D<Float> front() {
		return pathList.peek();
	}
	
	// 删除并返回最前面的点
	public Point2D<Float> pop() {
		return pathList.poll();
	}
	
	public boolean isEmpty() {
		return pathList.isEmpty();
	}
}
