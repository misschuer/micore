package cc.mi.core.utils;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class PriorityQueueUtils {
	public static final int ORDER_ASC = 0;
	public static final int ORDER_DESC = 1;
	
	private Queue<Integer> queue = null;
	private int size = 0;
	private int order = 0;
	
	public static void main(String[] args) {
		Random rand = new Random();
		int size = 15;
		PriorityQueueUtils pq = new PriorityQueueUtils(size, ORDER_DESC);
		
		for (int i = 0; i < 10; ++ i) {
			int num = rand.nextInt(100);
			System.out.print(num + " ");
			pq.add(num);
		}
		System.out.println();
		System.out.println(pq.toString());
	}
	
	public static Comparator<Integer> descending() {
		return new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) return -1;
				if (o1 > o2) return 1;
				return 0;
			}
		};
	}
	
	public static Comparator<Integer> ascending() {
		return new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) return 1;
				if (o1 > o2) return -1;
				return 0;
			}
		};
	}
	
	/**
	 * @param order: 1 降序, 其他升序
	 **/
	public PriorityQueueUtils(int size, int order) {
		Comparator<Integer> comparator = null;
		this.order = order;
		if (order == ORDER_DESC)
			comparator = descending();
		else
			comparator = ascending();
		queue = new PriorityQueue<Integer>(size, comparator);
		this.size = size;
	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}
	
	private boolean isFull() {
		if (size == 0)
			return false;
		return queue.size() >= size;
	}
	
	public int top() {
		return queue.peek();
	}
	
	public int pop() {
		return queue.poll();
	}
	
	public void add(int element) {
		if (this.isFull()) {
			if (!canAdd(element))
				return;
			this.pop();
		}
		queue.add(element);
	}
	
	private boolean canAdd(int newElement) {
		int topElement = this.top();
		if (order == ORDER_DESC)
			return newElement > topElement;
		return newElement < topElement;
	}
	
	public int[] toArray() {
		int[] arr = new int[queue.size()];
		int index = arr.length;
		
		while (!this.isEmpty()) {
			arr[--index] = this.pop();
		}
		
		return arr;
	}
	
	public String toString() {
		String str = "";
		
		while (!this.isEmpty()) {
			str = this.pop() + " " + str;
		}
		
		return str;
	}
}
