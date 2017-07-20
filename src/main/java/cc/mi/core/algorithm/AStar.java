package cc.mi.core.algorithm;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class AStar {
	private static StreamTokenizer st = new StreamTokenizer(new InputStreamReader(System.in));
	private static PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	private static final int IN_CLOSE = -2;
	private static Node[] elements;
	private static MinHeap heap;
	private static String[] mapData;
	private static final int[][] moved = {
		{0, 1},
		{0, -1},
		{1, 0},
		{-1, 0}
	};
//	private static boolean[] available = {false, false, false, false};
//	private static int[] ggId = {0, 0, 0, 0};
	
	public static void main(String[] args) throws IOException {
//		long a = System.currentTimeMillis();
//		init(MAX_VERTEX);
		st.ordinaryChar('.');
		st.wordChars('.', '.');
		for (;;) {
			int r = nextInt();
			int c = nextInt();
			if (r + c == 0) {
				break;
			}
			read(r, c);
		}
//		long b = System.currentTimeMillis();
//		pw.println((b-a) + "ms");
//		print(199 * 201);
		pw.flush();
		pw.close();
	}
	
	private static int nextInt() throws IOException {
		int value;
		st.nextToken();
		value = (int) st.nval;
		return value;
	}
	
	private static String next() throws IOException {
		String value;
		st.nextToken();
		value = st.sval;
		return value;
	}
	
	private static void init(int r, int c) {
		int maxVertex = r * c;
		heap = new MinHeap(maxVertex);
		elements = new Node[maxVertex];
		mapData = new String[ r ];
	}
	
	private static Node getNode(int index) {
		if (elements[index] == null) {
			elements[index] = new Node(index);
		}
		return elements[index];
	}
	
	private static void read(int r, int c) throws IOException {
//		Scanner cin = new Scanner(System.in);
		init(r, c);
		for (int i = 0; i < r; ++ i) {
			mapData[ i ] = next();
		}
		int si = nextInt();
		int sj = nextInt();
		int ti = nextInt();
		int tj = nextInt();
		
		astar(r, c, si, sj, ti, tj);
		
//		cin.close();
	}
	
	private static void astar(int r, int c, int si, int sj, int ti, int tj) {
		-- si; -- sj; -- ti; -- tj;
		heap.resetCursor();
		int sourcePoint = si * c + sj;
		int targetPoint = ti * c + tj;
		
		Node sourceNode = getNode(sourcePoint);
		sourceNode.reset();
		sourceNode.setG(0);
		sourceNode.setH(getLength(si, sj, ti, tj));
		sourceNode.setX(si);
		sourceNode.setY(sj);
		heap.put(sourcePoint);
		while (heap.hasElement()) {
//			System.out.println("=======================================");
			int currId = heap.get();
			Node prevNode = getNode(currId);
//			System.out.println(String.format("NODE fffffffff id=%d, heapIndex=%d, g=%d, h=%d, prev=%d, (%d, %d)", 
//					currId, prevNode.getHeapIndex(), prevNode.getG(), prevNode.getH(), prevNode.getPrev(), prevNode.getX(),  prevNode.getY())
//			);
			
			if (currId == targetPoint) {
				break;
			}
			int prevX = prevNode.getX();
			int prevY = prevNode.getY();
			for (int i = 0; i < moved.length; ++ i) {
				int nextX = prevX + moved[ i ][ 0 ];
				int nextY = prevY + moved[ i ][ 1 ];
				if (nextX < 0 || nextX >= r || nextY < 0 || nextY >= c) {
					continue;
				}
				int nextId = nextX * c + nextY;
				int cost = 10000;
				if (mapData[nextX].charAt(nextY) == 'X')
					cost = 0;
				calculate(currId, nextId, nextX, nextY, prevNode.getG() + cost, getLength(nextX, nextY, ti, tj));
			}
			prevNode.setHeapIndex(IN_CLOSE);
		}
		pw.println(getNode(targetPoint).getF() / 10000);
	}
	
	private static int getLength(int x0, int y0, int x1, int y1) {
		return Math.abs(x0 - x1) + Math.abs(y0 - y1);
	}
	
//	private static void print(int prev) {
//		if (prev == -1) {
//			return;
//		}
//		Node prevNode = getNode(prev);
//		print(prevNode.getPrev());
//		pw.println(String.format("(%d, %d) = %d + %d = %d", prevNode.getX(), prevNode.getY(), prevNode.getG(), prevNode.getH(), prevNode.getF()));
//	}
	
	
	private static void calculate(int prevId, int nextId, int nextX, int nextY, int realCost, int h) {
//		if (mins[nextX][nextY] <= realCost) {
//			return;
//		}
//		mins[nextX][nextY] = realCost;
		// 在open表
		Node nextNode = getNode(nextId);
		if (nextNode.getHeapIndex() > -1) {
//			System.out.println(nextId + " in openlist");
			int heapIndex = nextNode.getHeapIndex();
			if (realCost < nextNode.getG()) {
				nextNode.setG(realCost);
				nextNode.setPrev(prevId);
				heapIndex = nextNode.getHeapIndex();
				heap.siftUp(heapIndex);
//				System.out.println("openlist changed:" + nextNode.getF());
			}
			return;
		}
		// 在close表
		if (nextNode.getHeapIndex() == IN_CLOSE) {
//			System.out.println(nextId + " in closeList");
			if (realCost < nextNode.getG()) {
				nextNode.reset();
				nextNode.setG(realCost);
				nextNode.setPrev(prevId);
				heap.put(nextId);
//				System.out.println("close list changed:" + nextNode.getF());
			}
			return;
		}
		
		nextNode.reset();
		nextNode.setG(realCost);
		nextNode.setH(h);
		nextNode.setPrev(prevId);
		nextNode.setX(nextX);
		nextNode.setY(nextY);
		heap.put(nextId);
//		System.out.println(String.format("id=%d, heapIndex=%d, g=%d, h=%d, prev=%d, (%d, %d)", 
//				nextId, nextNode.getHeapIndex(), realCost, h, prevId, nextX,  nextY)
//		);
	}
	
	static class MinHeap {
		private final int[] buffers;
		private int cursor = 0;
		
		public MinHeap(int expr) {
			buffers = new int[expr];
		}
		
		public boolean isFull() {
			return cursor == buffers.length;
		}
		
		public boolean hasElement() {
			return cursor > 0;
		}
		
		public void put(int id) {
//			if (isFull())
//				throw new RuntimeException();
			buffers[cursor] = id;
			getNode(id).setHeapIndex(cursor);
			siftUp(cursor);
			cursor ++;
//			check();
//			System.out.println("after put ==============:" + cursor);
		}
		
		private void siftDown(int heapIndex) {
			while (heapIndex < cursor) {
				int ltHeapIndex = (heapIndex << 1) + 1;
				int rtHeapIndex = ltHeapIndex + 1;
				
				// 不存在左儿子一定不存在右儿子
				if (ltHeapIndex >= cursor) {
					return;
				}
				
				// 找出左右儿子里面的最小值
				int childMinHeapIndex = ltHeapIndex;
				if (rtHeapIndex < cursor && getNode(buffers[rtHeapIndex]).getF() < getNode(buffers[childMinHeapIndex]).getF()) {
					childMinHeapIndex = rtHeapIndex;
				}
				
				// 如果儿子的最小值大于等于父亲的值, 跳出循环
				if (getNode(buffers[childMinHeapIndex]).getF() >= getNode(buffers[heapIndex]).getF())
					break;
				
				swap(childMinHeapIndex, heapIndex);
				heapIndex = childMinHeapIndex;
			}
		}
		
		private void siftUp(int heapIndex) {
			while (heapIndex > 0) {
				int parent = (heapIndex - 1) >>> 1;
				// 父亲的值不大于儿子的值则跳过
				if (getNode(buffers[parent]).getF() < getNode(buffers[heapIndex]).getF())
					break;
				swap(parent, heapIndex);
				heapIndex = parent;
			}
		}
		
		private void swap(int ind1, int ind2) {
			int tmp = buffers[ind1];
			
			buffers[ind1] = buffers[ind2];
			getNode(buffers[ind1]).setHeapIndex(ind1);
			
			buffers[ind2] = tmp;
			getNode(buffers[ind2]).setHeapIndex(ind2);
		}
		
		
		public int get() {
			int val = buffers[ 0 ];
			-- cursor;
//			System.out.println("after get==============:" + cursor);
			if (cursor > 0) {
				buffers[ 0 ] = buffers[cursor];
				getNode(buffers[ 0 ]).setHeapIndex(0);
				siftDown(0);
			}
			buffers[cursor] = -1;
//			getNode(val).setHeapIndex(-1);
//			check();
//			System.out.println("rest cursor:" + cursor);
			return val;
		}
		
		public void resetCursor() {
			this.cursor = 0;
		}
	}

	static class Node {
		private final int id;
		private int prev = -1;
		private int x = -1;
		private int y = -1;
		private int g = Integer.MAX_VALUE;
		private int h = Integer.MAX_VALUE;
		private int heapIndex = -1;
		
		public Node(int id) {
			this.id = id;
		}
		
		public void reset() {
			this.prev = -1;
			this.heapIndex = -1;
		}
		
		public void setHeapIndex(int heapIndex) {
			this.heapIndex = heapIndex;
//			System.out.println(this.id + " set heapIndex:" + heapIndex);
		}

		public int getId() {
			return id;
		}

		public int getPrev() {
			return prev;
		}

		public int getG() {
			return g;
		}

		public int getH() {
			return h;
		}

		public int getHeapIndex() {
			return heapIndex;
		}
		
		public int getF() {
			return this.g + this.h;
		}

		public void setPrev(int prev) {
			this.prev = prev;
		}

		public void setG(int g) {
			this.g = g;
		}

		public void setH(int h) {
			this.h = h;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
}


/**
6 6
..X...
XXX.X.
....X.
X.....
X.....
X.X...
3 5
6 3
*/