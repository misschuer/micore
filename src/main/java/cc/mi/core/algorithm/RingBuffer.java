package cc.mi.core.algorithm;

public class RingBuffer {
	private static final int DEFAULT_EXPR = 5;
	private static final int MIN_EXPR = 1;
	private static final int MAX_EXPR = 20;
	private final Object[] buffers;
	private final int mask;
	private int head = 0;
	private int tail = 0;
	
	public RingBuffer() {
		this(DEFAULT_EXPR);
	}
	
	public RingBuffer(int expr) {
		ensureExpr(expr);
		mask = (1 << expr) - 1;
		buffers = new Object[1 << expr];
	}
	
	private void ensureExpr(int expr) {
		if (expr < MIN_EXPR || expr > MAX_EXPR)
			throw new RuntimeException(String.format("expr belongs to [%d, %d]", MIN_EXPR, MAX_EXPR));
	}
	
	public boolean isFull() {
		return (head & mask) == ((tail + 1) & mask);
	}
	
	public boolean hasElement() {
		return head != tail;
	}
	
	public void put(Object val) {
		buffers[tail] = val;
		tail = (tail + 1) & mask;
	}
	
	public Object get() {
		if (hasElement()) {
			Object val = buffers[head];
			head = (head + 1) & mask;
			return val;
		}
		return null;
	}
}