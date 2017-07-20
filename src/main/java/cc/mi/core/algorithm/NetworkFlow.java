package cc.mi.core.algorithm;

public class NetworkFlow {
	private static final int INF = 1 << 31;
	private int N = 0;
	private int M = 0;
	private Edge[] e = null;
	private int[] head = null;
	private int eid = 0;

	public NetworkFlow(int vertexNum, int edgeNum) {
		this.N = vertexNum;
		this.M = edgeNum;
		init();
	}

	private void init() {
		eid = 0;
		e = new Edge[M];
		head = new int[N + 1];
		for (int i = 0; i < head.length; ++i) {
			head[i] = -1;
		}
	}

	public void add(int u, int v, int val) {
		e[eid].setNext(head[u]);
		e[eid].setV(v);
		e[eid].setVal(val);
		head[u] = eid++;
	}

	public int sap(int s, int t) {
		int[] pre = new int[N + 1];
		int[] gap = new int[N + 1];
		int[] dis = new int[N + 1];
		int[] cur = new int[N + 1];
		int ans = 0;
		int i, j, u, v, val;
		int aug = INF;

		for (i = 0; i <= N; ++i) {
			cur[i] = head[i];
			gap[i] = dis[i] = 0;
		}

		gap[0] = N;
		u = pre[s] = s;
		while (dis[s] < N) {
			boolean flag = false;
			for (j = cur[u]; j != -1; j = e[j].getNext()) {
				cur[u] = j;
				v = e[j].getV();
				val = e[j].getVal();
				if (val > 0 && dis[u] == dis[v] + 1) {
					flag = true;
					if (aug > val)
						aug = val;
					pre[v] = u;
					u = v;
					if (u == t) {
						ans += aug;
						while (u != s) {
							u = pre[u];
							e[cur[u]].modifyValue(-aug);
							e[cur[u] ^ 1].modifyValue(aug);
						}
					}
					break;
				}
			}
			if (flag)
				continue;
			int mindis = N;
			for (j = head[u]; j != -1; j = e[j].getNext()) {
				v = e[j].getV();
				val = e[j].getVal();
				if (val > 0 && mindis > dis[v]) {

					mindis = dis[v];
					cur[u] = j;
				}
			}
			if ((--gap[dis[u]]) <= 0)
				break;
			dis[u] = mindis + 1;
			gap[dis[u]]++;
			u = pre[u];
		}

		return ans;
	}

}

class Edge {
	private int v = -1;
	private int val = -1;
	private int next = -1;

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public void modifyValue(int value) {
		this.val += value;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}
}