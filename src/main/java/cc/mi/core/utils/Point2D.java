package cc.mi.core.utils;

public class Point2D<T> {
	private final T x;
	private final T y;
	
	public Point2D(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}
}
