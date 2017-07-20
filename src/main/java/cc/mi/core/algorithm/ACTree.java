package cc.mi.core.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ACTree {
	public static final int MAX = 510000;
	public static final int TYPE_BYTE = 0;
	public static final int TYPE_HEX = 1;
	public static final int TYPE_UPPERCASE = 2;
	public static final int TYPE_LOWERCASE = 3;
	public static final int TYPE_NUMBER = 4;
	public static final Map<Integer, Integer> hash = calHash();
	public static final String STAR_HEX = "002A";
	
	private Node[] tree = null;
	private Node root = null;
	private Node[] qu = null;
	private int head = 0;
	private int tail = 0;
	private int ptr = 0;
	private int type = 0;
	private int num = 0;
	
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		ACTree ac = new ACTree(ACTree.TYPE_HEX);
		int t = cin.nextInt();
		
		for (int ca = 0; ca < t; ++ ca) {
			int n = cin.nextInt();
			ac.init();
			for (int i = 0; i < n; ++ i) {
				String str = cin.next();
				ac.insert(str);
			}
			ac.buildAC();
			String str = cin.next();
			str = ac.filter(str);
			System.out.println(str);
		}
		
		cin.close();
	}
	
	
	private static final Map<Integer, Integer> calHash() {
		Map<Integer, Integer> hash = new HashMap<Integer, Integer>(5);
		hash.put(TYPE_BYTE, 256);
		hash.put(TYPE_HEX, 16);
		hash.put(TYPE_UPPERCASE, 26);
		hash.put(TYPE_LOWERCASE, 26);
		hash.put(TYPE_NUMBER, 10);
		return hash;
	}
	
	public ACTree(int type) {
		this.type = type;
		tree = new Node[MAX];
		qu = new Node[MAX];
		this.num = hash.get(type);
		init();
	}
	
	public void init() {
		ptr = 0;
		root = createNode(0);
		head = tail = 0;
	}
	
	public Node createNode(int layer) {
		Node node = new Node(num);
		node.setLayer(layer);
		tree[ptr ++] = node;
		return node;
	}
	
	public void insert(String str) {
		if (type == ACTree.TYPE_HEX)
			str = StringCode.encode(str);
		Node p = root;
		
		for (int i = 0; i < str.length(); ++ i) {
			int k = getIndex(str.charAt(i));
			if (p.getNextNodeByIndex(k) == null) {
				p.setNextNodeByIndex(k, createNode(i+1));
			}
			p = p.getNextNodeByIndex(k);
		}
		
		p.plusCnt();
		p.setFlag(true);
	}
	
	public void buildAC() {
		qu[head ++] = root;
		
		while (tail < head) {
			Node p = qu[tail ++];
			for (int k = 0; k < num; ++k) {
				if (p.getNextNodeByIndex(k) != null) {
					if (p == root) {
						p.getNextNodeByIndex(k).setFail(root);
					}
					else {
						p.getNextNodeByIndex(k).setFail(p.getFail().getNextNodeByIndex(k));
						if (!p.getNextNodeByIndex(k).isFlag()) {
							p.getNextNodeByIndex(k).setCnt(1);
						}
					}
					qu[head++] = p.getNextNodeByIndex(k);
				}
				else {
					if (p == root) {
						p.setNextNodeByIndex(k, root);
					}
					else {
						p.setNextNodeByIndex(k, p.getFail().getNextNodeByIndex(k));
					}
				}
			}
		}
	}
	
	public boolean contains(String str) {
		if (type == ACTree.TYPE_HEX)
			str = StringCode.encode(str);
		Node p = root;
		
		for (int i = 0; i < str.length(); ++ i) {
			int k = getIndex(str.charAt(i));
			p = p.getNextNodeByIndex(k);
			if (p.getCnt() > 0) {
				Node tmp = p;
				while (tmp != root) {
					if (tmp.isFlag()) {
						return true;
					}
					tmp = tmp.getFail();
				}
			}
		}
		
		return false;
	}
	
	public String filter(String str) {
		if (type == ACTree.TYPE_HEX)
			str = StringCode.encode(str);
		Node p = root;
		StringBuffer sb = new StringBuffer();
		int lastStar = -1;
		
		for (int i = 0; i < str.length(); ++ i) {
			int k = getIndex(str.charAt(i));
			sb.append(str.charAt(i));
			p = p.getNextNodeByIndex(k);
			if (p.getCnt() > 0) {
				Node tmp = p;
				
				if (tmp.isFlag()) {
					int st = sb.length() - tmp.getLayer();
					if (st > lastStar) {
						sb = sb.delete(st, sb.length());
						lastStar = sb.length();
						sb.append(STAR_HEX);
					}
				}
			}
		}
		str = sb.toString();
		if (type == ACTree.TYPE_HEX)
			str = StringCode.decode(str);
		
		return str;
	}
	
	public int query(String str) {
		if (type == ACTree.TYPE_HEX)
			str = StringCode.encode(str);
		Node p = root;
		int cnt = 0;
		
		for (int i = 0; i < str.length(); ++ i) {
			int k = getIndex(str.charAt(i));
			p = p.getNextNodeByIndex(k);
			if (p.getCnt() > 0) {
				Node tmp = p;
				while (tmp != root) {
					if (tmp.isFlag()) {
						cnt += tmp.getCnt();
						tmp.setCnt(0);
					}
					tmp = tmp.getFail();
				}
			}
		}

		return cnt;
	}

	private int getIndex(char ch) {
		int index = 0;
		switch(type) {
			case TYPE_BYTE:
				index = ch - 0;
				break;
			case TYPE_HEX:
				if ('0' <= ch && ch <= '9')
					index = ch - '0';
				else
				if ('a' <= ch && ch <= 'f')
					index = ch - 'a' + 10;
				else
				if ('A' <= ch && ch <= 'F')
					index = ch - 'A' + 10;
				break;
			case TYPE_LOWERCASE:
				index = ch - 'a';
				break;
			case TYPE_UPPERCASE:
				index = ch - 'A';
				break;
			case TYPE_NUMBER:
				index = ch - '0';
				break;
			default:
				break;
		}
		return index;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}


class Node {
	private int cnt = 0;
	private boolean flag = false;
	private Node[] next = null;
	private int layer = 0;
	private Node fail = null;
	
	public Node(int childrenLength) {
		next = new Node[childrenLength];
		layer = 0;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public void plusCnt() {
		this.cnt ++;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Node getNextNodeByIndex(int index) {
		return next[index];
	}

	public void setNextNodeByIndex(int index, Node node) {
		this.next[index] = node;
	}

	public Node getFail() {
		return fail;
	}

	public void setFail(Node fail) {
		this.fail = fail;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
}