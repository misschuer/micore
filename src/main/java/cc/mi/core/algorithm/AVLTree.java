package cc.mi.core.algorithm;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.mi.core.utils.RandomUtils;

public class AVLTree {
	public static final int ORDER_DESC = 1;
	public static final int ORDER_ASC = 0;
	public static final int LEFT_CHILDREN = -1;
	public static final int RIGHT_CHILDREN = 1;
	public static final int SELF = 0;
	private AVLNode root = null;
	private AVLComparable cmp = null;
	
	public static void main(String[] args) {
		AVLTree avl = new AVLTree(null, AVLTree.ORDER_ASC);
		List<Integer> list = new ArrayList<Integer>(10);
		for (int i = 0; i < 10; ++ i) {
			list.add(i);
		}
		
		for (int i = 0; i < 10; ++ i) {
			int index = RandomUtils.randomArrayListIndex(list);
			int element = list.remove(index);
			System.out.println("current element:" + element);
			AVLNode newNode = new AVLNode(element);
			avl.setRoot(avl.insert(avl.getRoot(), newNode));
			List<Integer> rankList = avl.getRankList(avl.getRoot());
			for (int rank:rankList) {
				System.out.print(rank + " ");
			}
			System.out.println();
		}
	}
	
	
	public AVLTree(AVLNode root, int priority) {
		this.root = root;
		if (priority == AVLTree.ORDER_ASC)
			this.cmp = new AVLAscending();
		else
			this.cmp = new AVLDescending();
	}

	private static int height(AVLNode x) {
		if (x == null)
			return 0;
		return x.getHeight();
	}

	private static int callHeight(AVLNode x) {
		return Math.max(height(x.getLt()), height(x.getRt())) + 1;
	}

	private static AVLNode rtRotation(AVLNode x) {
		AVLNode y = x.getLt();
		y.setParent(x.getParent());

		x.setLt(y.getRt());
		if (x.getLt() != null)
			x.getLt().setParent(x);

		y.setRt(x);
		x.setParent(y);

		AVLNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
		}

		x.setHeight(callHeight(x));
		y.setHeight(callHeight(y));

		return y;
	}

	private static AVLNode ltRotation(AVLNode x) {
		AVLNode y = x.getRt();
		y.setParent(x.getParent());

		x.setRt(y.getLt());
		if (x.getRt() != null)
			x.getRt().setParent(x);

		y.setLt(x);
		x.setParent(y);

		AVLNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
		}

		x.setHeight(callHeight(x));
		y.setHeight(callHeight(y));

		return y;
	}

	private static AVLNode deleteRotation(AVLNode node) {
		if (height(node.getLt()) - height(node.getRt()) == 2) {
			if (node.getLt().getLt() == null)
				node.setLt(ltRotation(node.getLt()));
			node = rtRotation(node);
		} else if (height(node.getRt()) - height(node.getLt()) == 2) {
			if (node.getRt().getRt() == null)
				node.setRt(rtRotation(node.getRt()));
			node = ltRotation(node);
		}

		return node;
	}

	public AVLNode insert(AVLNode node, AVLNode newNode) {
		if (node == null)
			node = newNode;
		else {
			int cmpResult = this.cmp.compare(node, newNode);

			if (cmpResult == LEFT_CHILDREN) {
				node.setLt(this.insert(node.getLt(), newNode));
				node.getLt().setParent(node);

				if (height(node.getLt()) - height(node.getRt()) == 2) {
					int subCmp = this.cmp.compare(node.getLt(), newNode);
					if (subCmp == RIGHT_CHILDREN)
						node.setLt(ltRotation(node.getLt()));
					node = rtRotation(node);
				}
			} else if (cmpResult == RIGHT_CHILDREN) {
				node.setRt(this.insert(node.getRt(), newNode));
				node.getRt().setParent(node);

				if (height(node.getRt()) - height(node.getLt()) == 2) {
					int subCmp = this.cmp.compare(node.getRt(), newNode);
					if (subCmp == LEFT_CHILDREN)
						node.setRt(rtRotation(node.getRt()));
					node = ltRotation(node);
				}
			} else {
				try {
					throw new InvalidAlgorithmParameterException("排名的2个节点不可能具有相同属性, 请检查数据或者IComparable对象的方法");
				} catch (InvalidAlgorithmParameterException e) {
					e.printStackTrace();
				}
			}
		}

		node.setHeight(callHeight(node));

		return node;
	}

	public AVLNode delete(AVLNode node, int key) {
		if (node == null)
			return null;

		int findCmpResult = this.cmp.find(node, key);
		if (findCmpResult == SELF) {
			if (node.getRt() == null) {
				// AVLNode tmpNode = node;
				if (node != this.root) {
					if (node.getParent().getLt() == node) {
						node.getParent().setLt(node.getLt());
					} else {
						node.getParent().setRt(node.getLt());
					}
					if (node.getLt() != null)
						node.getLt().setParent(node.getParent());
				}
				node = node.getLt();
				// tmpNode = null;
			} else {
				int tmpkey = node.getKey();

				AVLNode leftMost = getLeftMost(node.getRt());
				node.setKey(leftMost.getKey());

				leftMost.setKey(tmpkey);

				node.setRt(this.delete(node.getRt(), key));
			}
		} else if (findCmpResult == LEFT_CHILDREN) {
			node.setLt(this.delete(node.getLt(), key));
		} else {
			node.setRt(this.delete(node.getRt(), key));
		}

		if (node != null) {
			node.setHeight(callHeight(node));
			node = deleteRotation(node);
		}

		return node;
	}
	
	
	public AVLNode update(AVLNode node, AVLNode newNode) {
		node = this.delete(node, newNode.getKey());
		node = this.insert(node, newNode);

		return node;
	}
	

	public List<Integer> getRankList(AVLNode node) {
		if (node == null)
			return Arrays.asList();
		List<Integer> rankList = new ArrayList<Integer>();
		List<Integer> rankL = this.getRankList(node.getLt());
		List<Integer> rankM = Arrays.asList(node.getKey());
		List<Integer> rankR = this.getRankList(node.getRt());
		
		rankList.addAll(rankL);
		rankList.addAll(rankM);
		rankList.addAll(rankR);
		
		return rankList;
	}

	private static AVLNode getLeftMost(AVLNode node) {
		if (node == null)
			return null;
		while (node.getLt() != null)
			node = node.getLt();
		return node;
	}

	public AVLNode getRoot() {
		return root;
	}

	public void setRoot(AVLNode root) {
		this.root = root;
	}
}

abstract interface AVLComparable {
	public int compare(AVLNode a, AVLNode b);
	public int find(AVLNode a, int key);
}


class AVLNode {
	private AVLNode lt = null;
	private AVLNode rt = null;
	private AVLNode parent = null;
	private int key = 0;
	private int height = 0;

	public AVLNode(int key) {
		this.key = key;
	}


	public AVLNode getLt() {
		return lt;
	}

	public void setLt(AVLNode lt) {
		this.lt = lt;
	}

	public AVLNode getRt() {
		return rt;
	}

	public void setRt(AVLNode rt) {
		this.rt = rt;
	}

	public AVLNode getParent() {
		return parent;
	}

	public void setParent(AVLNode parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
}

class AVLAscending implements AVLComparable {
	@Override
	public int compare(AVLNode a, AVLNode b) {
		int key = b.getKey();
		return find(a, key);
	}

	@Override
	public int find(AVLNode a, int key) {
		if (key < a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key > a.getKey())
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}


class AVLDescending implements AVLComparable {
	@Override
	public int compare(AVLNode a, AVLNode b) {
		int key = b.getKey();
		return find(a, key);
	}

	@Override
	public int find(AVLNode a, int key) {
		if (key > a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key < a.getKey())
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}