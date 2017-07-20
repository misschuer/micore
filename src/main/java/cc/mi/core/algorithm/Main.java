package cc.mi.core.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;

public class Main {
	private static StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
	private static PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	private static final int MAX_VERTEX = 1000000;
	private static final int IN_CLOSE = -2;
	private static String[] mapData;
	// 可改成hashmap 不是所有的点都会访问 浪费空间
	private static Map<Integer, Node> elementHash;
	private static MinHeap heap;
	private static boolean[] available = {false, false, false, false};
	private static int[] ggId = {0, 0, 0, 0};
	
	public static void main(String[] args) throws IOException {
		init();
		read();
	}
	
	private static void init() {
		heap = new MinHeap(MAX_VERTEX);
		mapData = new String[1000];
		elementHash = new HashMap<>();
	}
	
	private static void read() throws IOException {
		st.ordinaryChars('0', '9');
        st.ordinaryChars(' ', ' ');
        st.ordinaryChars('.', '.');
        st.wordChars('0', '9');
        st.wordChars(' ', ' ');
        st.wordChars('.', '.');
		while (true) {
			int r = 5; 
			int c = 5;
			String head = readDouble();
            String[] headParams = head.split(" ");
            r = Integer.parseInt(headParams[ 0 ]);
            c = Integer.parseInt(headParams[ 1 ]);
            headParams = null;
            
            if (r == 0 && c == 0)
                break;

            for (int i = 0; i < r; ++ i) {
                st.nextToken();
                mapData[ i ] = st.sval;
            }
            
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < r; ++ i) {
//                sb.append('.');
//            }
//            for (int i = 0; i < r; ++ i) {
//                mapData[ i ] = sb.toString();
//            }
//            sb.setCharAt(0, 'X');
//            mapData[ 0 ] = sb.toString();
//            sb.setCharAt(0, '.');
//            sb.setCharAt(c-1, 'X');
//            mapData[r-1] = sb.toString();
            
            String source = readDouble();
            String[] sourceParams = source.split(" ");
            int si = Integer.parseInt(sourceParams[ 0 ]);
            int sj = Integer.parseInt(sourceParams[ 1 ]);
            sourceParams = null;
            
            String target = readDouble();
            String[] targetParams = target.split(" ");
            int ti = Integer.parseInt(targetParams[ 0 ]);
            int tj = Integer.parseInt(targetParams[ 1 ]);
            targetParams = null;
            
            astar(r, c, si, sj, ti, tj);
		}
		
		pw.flush();
		pw.close();
	}
	
	private static String readDouble() throws IOException {
        st.nextToken();
        return st.sval;
    }
	
	private static void astar(int r, int c, int si, int sj, int ti, int tj) {
		-- si; -- sj; -- ti; -- tj;
		heap.resetCursor();
		elementHash.clear();
		int expr = r * c;
		int sourcePoint = si * c + sj;
		int targetPoint = ti * c + tj;
		
		Node node = new Node(sourcePoint);
		node.reset();
		node.setG(0);
		node.setH(getLength(sourcePoint, targetPoint, c));
		elementHash.put(sourcePoint, node);
		heap.put(sourcePoint);
		while (heap.hasElement()) {
//			System.out.println("=======================================");
			int currId = heap.get();
//			Node prevNode = elementHash.get(currId);
//			System.out.println((prevNode.getId() / c) + " " + (prevNode.getId() % c) + " = " + prevNode.getG() + " " + prevNode.getH());
			if (currId == targetPoint) {
				break;
			}
			// left
			ggId[ 0 ] = currId - 1;
			available[ 0 ] = currId % c != 0;
			// right
			ggId[ 1 ] = currId + 1;
			available[ 1 ] = ggId[ 1 ] % c != 0;
			// up
			ggId[ 2 ] = currId - c;
			available[ 2 ] = ggId[ 2 ] >= 0;
			// down
			ggId[ 3 ] = currId + c;
			available[ 3 ] = ggId[ 3 ] < expr;
			
			for (int i = 0; i < available.length; ++ i) {
				if (!available[ i ])
					continue;
				int nextId = ggId[ i ];
				int i0 = nextId / c;
				int j0 = nextId % c;
				int cost = 0;
				if (mapData[i0].charAt(j0) == '.')
					cost = 10000;
				calculate(currId, nextId, elementHash.get(currId).getG() + cost, getLength(nextId, targetPoint, c));
			}
			elementHash.get(currId).setHeapIndex(IN_CLOSE);
		}
//		print(targetPoint, c);
		pw.println(elementHash.get(targetPoint).getG() / 10000);
	}
	
	private static int getLength(int id0, int id1, int c) {
		int i0 = id0 / c;
		int j0 = id0 % c;
		int i1 = id1 / c;
		int j1 = id1 % c;
		
		return Math.abs(i0 - i1) + Math.abs(j0 - j1);
	}
	
//	private static void print(int prev, int c) {
//		if (prev == -1) {
//			return;
//		}
//		Node prevNode = elementHash.get(prev);
//		print(prevNode.getPrev(), c);
//		pw.println(String.format("(%d, %d)", prevNode.getId() / c, prevNode.getId() % c));
//	}
	
	
	private static void calculate(int prevId, int nextId, int realCost, int h) {
		// 在open表
		if (elementHash.containsKey(nextId) && elementHash.get(nextId).getHeapIndex() > -1) {
			int heapIndex = elementHash.get(nextId).getHeapIndex();
			if (realCost < elementHash.get(nextId).getG()) {
				elementHash.get(nextId).setG(realCost);
				elementHash.get(nextId).setPrev(prevId);
				heapIndex = elementHash.get(nextId).getHeapIndex();
				heap.siftUp(heapIndex);
			}
			return;
		}
		// 在close表
		if (elementHash.containsKey(nextId) && elementHash.get(nextId).getHeapIndex() == IN_CLOSE) {
			if (realCost < elementHash.get(nextId).getG()) {
				elementHash.get(nextId).reset();
				elementHash.get(nextId).setG(realCost);
				elementHash.get(nextId).setPrev(prevId);
				heap.put(nextId);
			}
			return;
		}
		
		Node node = new Node(nextId);
		node.reset();
		node.setG(realCost);
		node.setH(h);
		node.setPrev(prevId);
		elementHash.put(nextId, node);
		heap.put(nextId);
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
			if (isFull())
				throw new RuntimeException();
			buffers[cursor] = id;
			elementHash.get(id).setHeapIndex(cursor);
			siftUp(cursor);
			cursor ++;
//			System.out.println("after put ==============:" + cursor);
		}
		
		private void siftDown(int heapIndex) {
			while (heapIndex <= cursor) {
				int ltHeapIndex = (heapIndex << 1) + 1;
				int rtHeapIndex = ltHeapIndex + 1;
				
				// 不存在左儿子一定不存在右儿子
				if (ltHeapIndex > cursor || elementHash.get(buffers[heapIndex]).getF() <= elementHash.get(buffers[ltHeapIndex]).getF())
					return;
				
				// 找出左右儿子里面的最小值
				int childMinHeapIndex = ltHeapIndex;
				if (rtHeapIndex <= cursor && elementHash.get(buffers[rtHeapIndex]).getF() < elementHash.get(buffers[childMinHeapIndex]).getF()) {
					childMinHeapIndex = rtHeapIndex;
				}
				
				// 如果儿子的最小值大于等于父亲的值, 跳出循环
				if (elementHash.get(buffers[childMinHeapIndex]).getF() >= elementHash.get(buffers[heapIndex]).getF())
					break;
				
				swap(childMinHeapIndex, heapIndex);
				heapIndex = childMinHeapIndex;
			}
		}
		
		private void siftUp(int heapIndex) {
			while (heapIndex > 0) {
				int parent = (heapIndex - 1) >>> 1;
				// 父亲的值不大于儿子的值则跳过
				if (elementHash.get(buffers[parent]).getF() <= elementHash.get(buffers[heapIndex]).getF())
					break;
				swap(parent, heapIndex);
				heapIndex = parent;
			}
		}
		
		private void swap(int ind1, int ind2) {
			int tmp = buffers[ind1];
			
			buffers[ind1] = buffers[ind2];
			elementHash.get(buffers[ind1]).setHeapIndex(ind1);
			
			buffers[ind2] = tmp;
			elementHash.get(buffers[ind2]).setHeapIndex(ind2);
		}
		
		
		public int get() {
			int val = buffers[ 0 ];
			-- cursor;
//			System.out.println("after get==============:" + cursor);
			if (cursor > 0) {
				buffers[ 0 ] = buffers[cursor];
				elementHash.get(buffers[ 0 ]).setHeapIndex(0);
				siftDown(0);
			}
			elementHash.get(val).setHeapIndex(-1);
			return val;
		}

		public void resetCursor() {
			this.cursor = 0;
		}
	}

	static class Node {
		private final int id;
		private int prev = -1;
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
	}
}


/**
0 1 0 0 0
0 1 0 1 0
0 0 0 0 0
0 1 1 1 0
0 0 0 1 0

0 1 0 0 0
0 1 0 1 0
0 1 0 0 0
0 1 1 1 0
0 0 0 0 0

0 1 0 0 0
0 1 0 1 0
0 0 0 1 0
0 1 1 1 0
0 0 0 1 0
*/