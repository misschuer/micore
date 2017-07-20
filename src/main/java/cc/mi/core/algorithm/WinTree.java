package cc.mi.core.algorithm;

public class WinTree {
	private int n = 0;
	private int[] tree = null;
	private int[] values = null;
	
	public WinTree(int n) {
		this.n = n;
		values = new int[n+1];
		tree = new int[n<<1];
	}
	
	public void add(int index, int id, int value) {
		this.update(index, id, value);
	}
	
	public void update(int index, int id, int value) {
		int x = n + index;
		values[id] = value;
		tree[ x ] = id;
		
		int g = x;
		while (g > 1) {
			int ng = g | 1;
			int win = Math.max(values[tree[ng]], values[tree[g]]);
			g = g >> 1;
			if (tree[ g ] == win) break;
			tree[ g ] = win;
		}
	}
	
	public int query(int a, int b) {
		a = a + n;
		b = b + n;
		while (a != b) {
			a = a >> 1;
			b = b >> 1;
		}
		return tree[ a ];
	}
}