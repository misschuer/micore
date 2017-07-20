package cc.mi.core.algorithm;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

abstract interface MIComparable {
	public int compare(RankNode a, RankNode b);
	public int find(RankNode a, int key, int roleId);
}

public class RankTree {
	public static final int ORDER_DESC = 1;
	public static final int ORDER_ASC = 0;
	public static final int LEFT_CHILDREN = -1;
	public static final int RIGHT_CHILDREN = 1;
	private static final int SELF = 0;
	private RankNode root = null;
	private MIComparable cmp = null;
	private static final int ERROR_RETURN = (int)-1E9;
	private Map<Integer, Integer> hashMap = null;
	private static final int MAX_RANKER = 1 << 14;
	
	
	/** 测试开始 */
	public static void main(String[] args) {
		dot();
	}

	public static void dot() {
		RankTree rt = new RankTree(null, RankTree.ORDER_DESC);

		Random random = new Random();
		for (int i = 0; i < 20; ++i) {
			RankNode root = rt.getRoot();
			int key = random.nextInt(20) + 1;
			int roleId = random.nextInt(10) + 1;
			List<?> list = Arrays.asList(roleId, key, "名字" + i, "没有联盟");
			RankNode newNode = new RankNode(key, list);

			System.out.println(list.toString());

			root = rt.update(root, roleId, newNode);
			rt.setRoot(root);
			List<RankNode> rankList = rt.getRankList(root, 1, 100);
			printList(rankList);
		}
	}

	public static void printList(List<RankNode> list) {
		System.out.print("result============:");
		for (RankNode rn : list) {
			System.out.print(rn.toString());
		}
		System.out.println();
	}
	/** 测试结束 */

	public RankTree(RankNode root, int priority) {
		this.root = root;
		if (priority == RankTree.ORDER_ASC)
			this.cmp = new Ascending();
		else
			this.cmp = new Descending();
		this.hashMap = new HashMap<Integer, Integer>(MAX_RANKER);
	}

	private static int cnt(RankNode x) {
		if (x == null)
			return 0;
		return x.getLtCnt() + 1 + x.getRtCnt();
	}

	private static int height(RankNode x) {
		if (x == null)
			return 0;
		return x.getHeight();
	}

	private static int callHeight(RankNode x) {
		return Math.max(height(x.getLt()), height(x.getRt())) + 1;
	}

	private static RankNode rtRotation(RankNode x) {
		RankNode y = x.getLt();
		y.setParent(x.getParent());
		x.setLtCnt(y.getRtCnt());

		x.setLt(y.getRt());
		if (x.getLt() != null)
			x.getLt().setParent(x);
		y.setRtCnt(cnt(x));

		y.setRt(x);
		x.setParent(y);

		RankNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
			z.setLtCnt(cnt(z.getLt()));
			z.setRtCnt(cnt(z.getRt()));
		}

		x.setHeight(callHeight(x));
		y.setHeight(callHeight(y));

		return y;
	}

	private static RankNode ltRotation(RankNode x) {
		RankNode y = x.getRt();
		y.setParent(x.getParent());
		x.setRtCnt(y.getLtCnt());

		x.setRt(y.getLt());
		if (x.getRt() != null)
			x.getRt().setParent(x);
		y.setLtCnt(cnt(x));

		y.setLt(x);
		x.setParent(y);

		RankNode z = y.getParent();
		if (z != null) {
			if (z.getLt() == x)
				z.setLt(y);
			else
				z.setRt(y);
			z.setLtCnt(cnt(z.getLt()));
			z.setRtCnt(cnt(z.getRt()));
		}

		x.setHeight(callHeight(x));
		y.setHeight(callHeight(y));

		return y;
	}

	private static RankNode deleteRotation(RankNode node) {
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

	public RankNode insert(RankNode node, RankNode newNode) {
		if (node == null)
			node = newNode;
		else {
			int cmpResult = this.cmp.compare(node, newNode);

			if (cmpResult == RankTree.LEFT_CHILDREN) {
				node.ModifyLtCnt(1);
				node.setLt(this.insert(node.getLt(), newNode));
				node.getLt().setParent(node);

				if (height(node.getLt()) - height(node.getRt()) == 2) {
					int subCmp = this.cmp.compare(node.getLt(), newNode);
					if (subCmp == RIGHT_CHILDREN)
						node.setLt(ltRotation(node.getLt()));
					node = rtRotation(node);
				}
			} else if (cmpResult == RankTree.RIGHT_CHILDREN) {
				node.ModifyRtCnt(1);
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
		node.setLtCnt(cnt(node.getLt()));
		node.setRtCnt(cnt(node.getRt()));

		return node;
	}

	public RankNode delete(RankNode node, int key, int roleId) {
		if (node == null)
			return null;

		int findCmpResult = this.cmp.find(node, key, roleId);
		if (findCmpResult == SELF) {
			if (node.getRt() == null) {
				// RankNode tmpNode = node;
				if (node != this.root) {
					if (node.getParent().getLt() == node) {
						node.getParent().setLtCnt(cnt(node.getLt()));
						node.getParent().setLt(node.getLt());
					} else {
						node.getParent().setRtCnt(cnt(node.getLt()));
						node.getParent().setRt(node.getLt());
					}
					if (node.getLt() != null)
						node.getLt().setParent(node.getParent());
				}
				node = node.getLt();
				// tmpNode = null;
			} else {
				int tmpkey = node.getKey();
				List<?> list = node.getList();

				RankNode leftMost = getLeftMost(node.getRt());
				node.setKey(leftMost.getKey());
				node.setList(leftMost.getList());

				leftMost.setKey(tmpkey);
				leftMost.setList(list);

				node.setRt(this.delete(node.getRt(), key, roleId));
				node.setRtCnt(cnt(node.getRt()));
			}
		} else if (findCmpResult == LEFT_CHILDREN) {
			node.ModifyLtCnt(-1);
			node.setLt(this.delete(node.getLt(), key, roleId));
		} else {
			node.ModifyRtCnt(-1);
			node.setRt(this.delete(node.getRt(), key, roleId));
		}

		if (node != null) {
			node.setHeight(callHeight(node));
			node = deleteRotation(node);
		}

		return node;
	}

	public RankNode update(RankNode node, int roleId, RankNode newNode) {
		int key = -1;
		if (this.hashMap.containsKey(roleId))
			key = this.hashMap.get(roleId);
		/** 没改变就不需要操作 */
		if (key == newNode.getKey())
			return node;

		this.hashMap.put(roleId, newNode.getKey());
		if (key > -1)
			node = this.delete(node, key, roleId);
		node = this.insert(node, newNode);

		return node;
	}

	public int getWhoRank(RankNode node, int key, int roleId) {
		if (node == null)
			return ERROR_RETURN;

		int findCmpResult = this.cmp.find(node, key, roleId);
		if (findCmpResult == RankTree.LEFT_CHILDREN) {
			return this.getWhoRank(node.getLt(), key, roleId);
		} else if (findCmpResult == RankTree.RIGHT_CHILDREN) {
			return node.getLtCnt() + 1 + this.getWhoRank(node.getRt(), key, roleId);
		}

		return node.getLtCnt() + 1;
	}

	public List<RankNode> getRankList(RankNode node, int a, int b) {
		if (node == null || a > b)
			return Arrays.asList();
		int less = 1;
		int most = cnt(node);
		if (a < less)
			a = less;
		if (b > most)
			b = most;

		List<RankNode> rankL = new ArrayList<RankNode>();
		List<RankNode> rankM = new ArrayList<RankNode>();
		List<RankNode> rankR = new ArrayList<RankNode>();
		int cura = node.getLtCnt() + 1;
		int curb = node.getLtCnt() + 1;

		if (b < cura) {
			rankL = this.getRankList(node.getLt(), a, b);
		} else if (a > curb) {
			rankR = this.getRankList(node.getRt(), a - curb, b - curb);
		} else {
			if (a < cura) {
				rankL = this.getRankList(node.getLt(), a, cura - 1);
				if (b > node.getLtCnt()) {
					rankM = Arrays.asList(node);
				}
				if (b > curb) {
					rankR = this.getRankList(node.getRt(), 1, b - curb);
				}
			} else if (a >= cura) {
				int ga = a - cura + 1;
				int gb = b - node.getLtCnt();

				if (gb >= ga && ga > 0 && gb > 0) {
					rankM = Arrays.asList(node);
				}
				if (b > curb) {
					rankR = this.getRankList(node.getRt(), 1, b - curb);
				}
			}
		}

		rankL.addAll(rankM);
		rankL.addAll(rankR);
		return rankL;
	}

	private static RankNode getLeftMost(RankNode node) {
		if (node == null)
			return null;
		while (node.getLt() != null)
			node = node.getLt();
		return node;
	}

	public RankNode getRoot() {
		return root;
	}

	public void setRoot(RankNode root) {
		this.root = root;
	}

	public Map<Integer, Integer> getHashMap() {
		return hashMap;
	}

	public void setHashMap(Map<Integer, Integer> hashMap) {
		this.hashMap = hashMap;
	}
}

class Ascending implements MIComparable {
	@Override
	public int compare(RankNode a, RankNode b) {
		int key = b.getKey();
		Object roleId = b.getList().get(0);
		return find(a, key, (int)roleId);
	}

	@Override
	public int find(RankNode a, int key, int roleId) {
		if (key < a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key > a.getKey())
			return RankTree.RIGHT_CHILDREN;
		Object valueA = a.getList().get(0);
		int roleA = (int) valueA;
		if (roleId < roleA)
			return RankTree.LEFT_CHILDREN;
		if (roleId > roleA)
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}


class Descending implements MIComparable {
	@Override
	public int compare(RankNode a, RankNode b) {
		int key = b.getKey();
		Object roleId = b.getList().get(0);
		return find(a, key, (int)roleId);
	}

	@Override
	public int find(RankNode a, int key, int roleId) {
		if (key > a.getKey())
			return RankTree.LEFT_CHILDREN;
		if (key < a.getKey())
			return RankTree.RIGHT_CHILDREN;
		Object valueA = a.getList().get(0);
		int roleA = (int) valueA;
		if (roleId < roleA)
			return RankTree.LEFT_CHILDREN;
		if (roleId > roleA)
			return RankTree.RIGHT_CHILDREN;
		return 0;
	}
}