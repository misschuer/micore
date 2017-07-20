package cc.mi.core.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Tarjan {
	public static final int MAXV = 10001;
	public static final int MAXE = 500000;
	
	public static final StreamTokenizer in = new StreamTokenizer(
											new BufferedReader(
											new InputStreamReader(System.in)));
	public static final PrintWriter out = new PrintWriter(
											new OutputStreamWriter(System.out));
	
	private TarjanEdge[] e = new TarjanEdge[MAXE];
	
	private boolean[] instack = new boolean[MAXV];
	public int[] head   = new int[MAXV];
	public int[] dfn    = new int[MAXV];
	public int[] low    = new int[MAXV];
	public int[] belong = new int[MAXV];
	public int[] S      = new int[MAXV];
	
	private int eid = 0, cnt = 0, cc = 0, top = 0;
	
	public static boolean hasNextInt() throws Exception {
		return in.nextToken() != StreamTokenizer.TT_EOF;
	}
	
	public static int getNextInt() {
		return (int) in.nval;
	}
	
	public static int nextInt() throws Exception {
		in.nextToken();
		return (int) in.nval;
	}
	
	public static void main(String[] args) throws Exception {
		Tarjan tar = new Tarjan();
		
		while (hasNextInt()) {
			int n = getNextInt();
			
			tar.reset();
			int[] inq = new int[n+1];
			int[] ouq = new int[n+1];
			for (int u = 1; u <= n; ++ u) {
				while (true) {
					int v = nextInt();
					if (v == 0) break;
					tar.add(u, v);
				}
				inq[ u ] = ouq[ u ] = 0;
			}
			
			for (int u = 1; u <= n; ++ u) if(tar.dfn[ u ] == -1) tar.tarjan(u);
			
			for (int u = 1; u <= n; ++ u) {
				for(int j = tar.head[ u ]; j != -1; j = tar.e[ j ].getNext()) {
					int v = tar.e[ j ].getV();
					if(tar.belong[ u ] != tar.belong[ v ]) {
						ouq[tar.belong[ u ]] ++;
						inq[tar.belong[ v ]] ++;
					}
				}
			}
			
			if(tar.cnt == 1) {
				System.out.println("1\n0");
				continue;
			}
	
			int c1 = 0, c2 = 0;
			for(int i = 0; i < tar.cnt; ++ i) {
			
				if(inq[ i ] == 0) c1 ++;
				if(ouq[ i ] == 0) c2 ++;
			}
			System.out.printf("%d\n%d\n", c1, Math.max(c1, c2));
		}
	}
	
	
	public Tarjan() {
		init();
	}
	
	
	private void init() {
		for (int i = 0; i < e.length; ++ i) {
			e[ i ] = new TarjanEdge(0, 0);
		}
	}
	
	
	public void reset() {
		eid = cnt = cc = top = 0;
		for (int i = 0; i < head.length; ++ i) {
			head   [ i ] =    -1;
			dfn    [ i ] =    -1;
			belong [ i ] =    -1;
			low    [ i ] =     0;
			instack[ i ] = false;
		}
	}
	
	
	public void add(int u, int v) {
		e[eid].setNext(head[ u ]);
		e[eid].setV(v);
		head[ u ] = eid ++;
	}

	public void tarjan(int u) {
		dfn[ u ] = low[ u ] = cc ++;
		S[top ++] = u;
		instack[ u ] = true;

		for(int j = head[ u ]; j != -1; j = e[ j ].getNext()) {
			int v = e[ j ].getV();
			if(dfn[ v ] == -1) {
				tarjan(v);
				low[ u ] = Math.min(low[ u ], low[ v ]);
			}
			else if(instack[ v ]) {
			
				low[ u ] = Math.min(low[ u ], dfn[ v ]);
			}
		}

		if(low[ u ] == dfn[ u ]) {
			while(true) {
				int tt = S[top-1];
				top --;
				belong[ tt ] = cnt;
				instack[ tt ] = false;
				if(tt == u) break;
			}
			cnt ++;
		}
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}


class TarjanEdge {
	private int v    = 0;
	private int next = 0;
	
	public TarjanEdge(int v, int next) {
		this.v    =    v;
		this.next = next;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}
}
/**
 *
 **/