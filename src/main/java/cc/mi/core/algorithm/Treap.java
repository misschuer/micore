package cc.mi.core.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.mi.core.utils.RandomUtils;

public class Treap {
	private TreapNode root = null;
	public static final int ORDER_DESC = 1;
	public static final int ORDER_ASC = 0;
	public static final int LEFT_CHILDREN = -1;
	public static final int RIGHT_CHILDREN = 1;
	public static final int SELF = 0;
	private int priority = 0;
	private TreapComparable cmp = null;
	
	public static void main(String[] args) {
		Treap treap = new Treap(Treap.ORDER_ASC);
		List<Integer> list = new ArrayList<Integer>(10);
		
		for (int i = 0; i < 10; ++ i) {
			list.add(i);
		}
		
		for (int i = 0; i < 10; ++ i) {
			int index = RandomUtils.randomArrayListIndex(list);
			int element = list.remove(index);
			System.out.println("current element:" + element);
			TreapNode newNode = new TreapNode(element, RandomUtils.nextInt(100));
			treap.setRoot(treap.insert(treap.getRoot(), newNode));
			List<Integer> rankList = treap.getRankList(treap.getRoot());
			for (int k = 0; k < rankList.size(); k += 2) {
				System.out.println(rankList.get(k) + " " + rankList.get(k+1));
			}
			System.out.println();
		}
	}
	
	public Treap(int priority) {
		this.priority = priority;
		if (priority == ORDER_ASC)
			this.cmp = new TreapAscending();
		else
			this.cmp = new TreapDescending();
	}
	
	public TreapNode insert(TreapNode node, TreapNode newNode) {
		if (node == null) {
			return newNode;
		}
		
		int cmpResult = this.cmp.compare(node, newNode);
		if (cmpResult == LEFT_CHILDREN) {
			node.setLt(this.insert(node.getLt(), newNode));
			node.getLt().setParent(node);
			
			if (node.getPriority() > node.getLt().getPriority()) {
				node = rtRotation(node);
			}
		} else if (cmpResult == RIGHT_CHILDREN) {
			node.setRt(this.insert(node.getRt(), newNode));
			node.getRt().setParent(node);

			if (node.getPriority() > node.getRt().getPriority()) {
				node = ltRotation(node);
			}
		}
		
		return node;
	}
	
	public static TreapNode onLeftDelete(TreapNode node) {
		TreapNode tmp = node;
		node = node.getLt();
		node.setParent(tmp.getParent());
		if (node.getParent() != null) {
			node.getParent().setLt(node);
		}
		tmp = null;
		return node;
	}
	
	public static TreapNode onRightDelete(TreapNode node) {
		TreapNode tmp = node;
		node = node.getRt();
		node.setParent(tmp.getParent());
		if (node.getParent() != null) {
			node.getParent().setRt(node);
		}
		tmp = null;
		return node;
	}
	
	public TreapNode delete(TreapNode node, int key) {
		if (node == null)
			return null;

		int findCmpResult = this.cmp.find(node, key);
		if (findCmpResult == SELF) {
			if (node.getLt() == null && node.getLt() == null) {
				node = null;
				return node;
			}
			if (node.getRt() == null) {
				node = onLeftDelete(node);
				return node;
			}
			if (node.getLt() == null) {
				node = onRightDelete(node);
				return node;
			}
			if (node.getLt().getPriority() < node.getRt().getPriority()) {
				node = rtRotation(node);
				node.setRt(onRightDelete(node.getRt()));
			}
			else {
				node = ltRotation(node);
				node.setLt(onRightDelete(node.getLt()));
			}
			
			return node;
		} else if (findCmpResult == LEFT_CHILDREN) {
			node.setLt(this.delete(node.getLt(), key));
		} else {
			node.setRt(this.delete(node.getRt(), key));
		}

		return node;
	}
	

	/** < key 和  > key 的分割开来*/
	public static Treap[] split(Treap treap, int key) {
		Treap[] treapList = null;
		
		
		return treapList;
	}
	
	
	public static Treap merge(Treap treap1, Treap treap2) throws Exception {
		if (treap1.getPriority() != treap2.getPriority()) {
			throw new Exception("2个treap的优先级不一致");
		}
		
		int min1 = treap1.getMinValue();
		int max1 = treap1.getMaxValue();
		int min2 = treap2.getMinValue();
		int max2 = treap2.getMaxValue();
		if (!(min1 > max2 || min2 > max1)) {
			throw new Exception("2个treap合并不满足二叉树左子树的值都比右子树大(小)的性质");
		}
		
		Treap treap = new Treap(treap1.getPriority());
		TreapNode root = new TreapNode(Integer.MIN_VALUE, 0);
		if (treap1.getPriority() == ORDER_ASC) {
			if (min2 > max1) {
				root.setLt(treap1.getRoot());
				root.setRt(treap2.getRoot());
			}
			else {
				root.setLt(treap2.getRoot());
				root.setRt(treap1.getRoot());
			}
		}
		else {
			if (min2 > max1) {
				root.setLt(treap2.getRoot());
				root.setRt(treap1.getRoot());
			}
			else {
				root.setLt(treap1.getRoot());
				root.setRt(treap2.getRoot());
			}
		}
		treap1.setRoot(root);
		treap2.setRoot(root);
		root = treap.delete(root, Integer.MIN_VALUE);
		treap.setRoot(root);
		
		Treap.free(treap1);
		Treap.free(treap2);
		
		return treap;
	}
	
	public int getMostValue(int priority) {
		TreapNode node = root;
		if (this.priority == priority) {
			while (node.getLt() != null)
				node = node.getLt();
		}
		else {
			while (node.getRt() != null)
				node = node.getRt();
		}
		
		return node.getKey();
	}
	
	
	public int getMinValue() {
		return getMostValue(ORDER_ASC);
	}
	
	
	public int getMaxValue() {
		return getMostValue(ORDER_DESC);
	}
	
	/** 释放treap的内存 */
	public static void free(Treap treap) {
		if (treap == null)
			return;
		freeNode(treap.getRoot());
	}
	
	/** 释放节点的内存 */
	private static void freeNode(TreapNode node) {
		if (node == null)
			return;
		freeNode(node.getLt());
		freeNode(node.getRt());
		node.setLt(null);
		node.setRt(null);
		node = null;
	}
	
	public TreapNode update(TreapNode node, TreapNode newNode) {
		node = this.delete(node, newNode.getKey());
		node = this.insert(node, newNode);

		return node;
	}
	
	public List<Integer> getRankList(TreapNode node) {
		if (node == null)
			return Arrays.asList();
		List<Integer> rankList = new ArrayList<Integer>();
		List<Integer> rankL = this.getRankList(node.getLt());
		List<Integer> rankM = Arrays.asList(node.getKey(), node.getPriority());
		List<Integer> rankR = this.getRankList(node.getRt());
		
		rankList.addAll(rankL);
		rankList.addAll(rankM);
		rankList.addAll(rankR);
		
		return rankList;
	}
	
	private static TreapNode rtRotation(TreapNode x) {
		TreapNode y = x.getLt();
		y.setParent(x.getParent());

		x.setLt(y.getRt());
		if (x.getLt() != null)
			x.getLt().setParent(x);

		y.setRt(x);
		x.setParent(y);

		TreapNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
		}

		return y;
	}

	private static TreapNode ltRotation(TreapNode x) {
		TreapNode y = x.getRt();
		y.setParent(x.getParent());

		x.setRt(y.getLt());
		if (x.getRt() != null)
			x.getRt().setParent(x);

		y.setLt(x);
		x.setParent(y);

		TreapNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
		}

		return y;
	}
	

	public TreapNode getRoot() {
		return root;
	}

	public void setRoot(TreapNode root) {
		this.root = root;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}

abstract interface TreapComparable {
	public int compare(TreapNode a, TreapNode b);
	public int find(TreapNode a, int key);
}

class TreapNode {
	TreapNode lt = null;
	TreapNode rt = null;
	TreapNode parent = null;
	int priority = 0;
	int key = 0;
	
	public TreapNode(int key, int priority) {
		this.key      =      key;
		this.priority = priority;
	}

	public TreapNode getLt() {
		return lt;
	}

	public void setLt(TreapNode lt) {
		this.lt = lt;
	}

	public TreapNode getRt() {
		return rt;
	}

	public void setRt(TreapNode rt) {
		this.rt = rt;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public TreapNode getParent() {
		return parent;
	}

	public void setParent(TreapNode parent) {
		this.parent = parent;
	}
}

class TreapAscending implements TreapComparable {
	@Override
	public int compare(TreapNode a, TreapNode b) {
		int key = b.getKey();
		return find(a, key);
	}

	@Override
	public int find(TreapNode a, int key) {
		if (key < a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key > a.getKey())
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}


class TreapDescending implements TreapComparable {
	@Override
	public int compare(TreapNode a, TreapNode b) {
		int key = b.getKey();
		return find(a, key);
	}

	@Override
	public int find(TreapNode a, int key) {
		if (key > a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key < a.getKey())
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}