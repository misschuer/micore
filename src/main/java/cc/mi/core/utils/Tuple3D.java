package cc.mi.core.utils;

public class Tuple3D<T> {
	private final T x;
	private final T y;
	private final T z;
	
	public Tuple3D(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}

	public T getZ() {
		return z;
	}
}
