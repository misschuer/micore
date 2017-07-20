package cc.mi.core.algorithm;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class DLX {
	public static final PrintWriter    out = new PrintWriter(
											 new OutputStreamWriter(System.out));
	public static final int MAXV = 1 << 10;
	public static final int MAXE = 1 << 12;
	public static final int N = 9;
	
	private DLXEdge[] e = new DLXEdge[MAXE];
	private DLXNode[] d = new DLXNode[MAXE];
	private int p[] = new int[MAXV];
	private int H[] = new int[MAXV];
	private Integer stack[] = new Integer[MAXV];
	private int ROW[] = new int[MAXV];
	
	private int aid = 0;
	private int n = 0, m = 0, head = 0;
	public int P = 0;
	
	private String str = null;
	
	public static void main(String[] args) throws Exception {
		DLX dlx = new DLX();
	    while(true) {
//TODO 输入数据	        String str = next();
	    	String str = "";
			if(str.charAt(0) == 'e') break;
			
			dlx.setStr(str);
			dlx.setN(N * N * N);
			dlx.setM(N * N + N * N + N * N + N * N);
			dlx.setP((int) Math.sqrt(N + 0.5));
			//格子的位置K, J, 数字K在J行, 数字K在J列,数字K在J宫.
			dlx.reset();
			dlx.make_mat();
			dlx.make_graph();
	        if(!dlx.dfs(0)) 
	        	System.out.println("No solution");
	    }
	}
	
	public DLX() {
		init();
	}
	
	private void init() {
		for (int i = 0; i < e.length; ++ i)
			e[ i ] = new DLXEdge(0, 0);
		
		for (int i = 0; i < d.length; ++ i)
			d[ i ] = new DLXNode();
	}
	
	
	public void reset() {
	    for(int i = 0; i <= m; ++ i) {
	        d[ i ].setS(0);
	        d[ i ].setU(i);
	        d[ i ].setD(i);
	        d[i+1].setL(i);
	        d[ i ].setR(i+1);
	    }
	    d[ 0 ].setL(m); 
	    d[ m ].setR(0);
	    
	    for (int i = 0; i <= n; ++ i)
	    	H[ i ] = -1;
	}
	
	
	public void make_mat() {
		aid = 0;
		for (int i = 0; i <= n; ++ i)
			p[ i ] = -1;
		
		for(int i = 0; i < N; ++ i) {
			for(int j = 0; j < N; ++ j) {
				char ch = str.charAt(i*N+j);
				int val = i*N+j;
				int row, col;
				if(ch == '.') {
					for(int k = 1; k <= N; ++ k) {
						
						row = val * N + k;
						col = val + 1;              add(row, col);
						col = N*N + i*N + k;        add(row, col);
						col = N*N + N*N + j*N + k;  add(row, col);
						col = N*N + N*N + N*N + palace(i, j) * N + k;
						add(row, col);
					}
				}
				else {
					
					int k = ch - '0';
					row = val * N + k;
					col = val + 1;              add(row, col);
					col = N*N + i*N + k;        add(row, col);
					col = N*N + N*N + j*N + k;  add(row, col);
					col = N*N + N*N + N*N + palace(i, j) * N + k;    
					add(row, col);
				}
			}
		}
	}
	
	public int palace(int i, int j) {
		
		int a = i / P;
		int b = j / P;
		return a * P + b;
	}
	
	
	public void make_graph() {
	    int cnt = m;
		int aid = 0;
		
	    for(int i = 1; i <= n; ++ i) {
			boolean flag = false;
	        for(int j = p[ i ]; j != -1; j = e[ j ].getNext()) {
				if(!flag) aid ++;
				flag = true;
				link(i, e[ j ].getV(), ++cnt, aid);
	        }
	    }
	}
	
	
	public void link(int r, int c, int val, int aid)  {
		ROW[aid] = r;
	    d[val].setRow(aid);
	    d[ c ].modifyS(1);
	    d[val].setC(c);
	    d[val].setU(d[ c ].getU());
	    d[d[ c ].getU()].setD(val);
	    d[val].setD(c);
	    d[ c ].setU(val);
	    
	    if(H[ r ] == -1) {
	        H[ r ] = val;
    		d[val].setL(val);
    		d[val].setR(val);
	    } 
	    else {
	        d[val].setL(d[H[ r ]].getL());
	        d[d[H[ r ]].getL()].setR(val);
	        d[val].setR(H[ r ]);
	        d[H[ r ]].setL(val);
	    }
	}


	public void add(int u, int v) {
		e[aid].setNext(p[ u ]);
		e[aid].setV(v);
		p[ u ] = aid ++;
	}
	
	
	public boolean dfs(int k) {
	    if (d[head].getR() == head) {
			StringBuilder sb = new StringBuilder();
			
			Arrays.sort(stack, 0, k, new Comparator<Integer>() {
				@Override
				public int compare(Integer arg0, Integer arg1) {
					if (arg0 < arg1) return -1;
					return 1;
				}
			});
			
	        for(int z = 0; z < k; ++ z) {
				int row = ROW[stack[ z ]];
				int modc = row % N;
				if(modc == 0) modc = N;
				sb.append((char)(modc + '0'));
			}
	        System.out.println(sb.toString());
	        return true;
	    }
	    
	    int s = Integer.MAX_VALUE, c = 0, i = 0, j = 0;
	    for (int t = d[head].getR(); t != head; t = d[ t ].getR()) {
	        if(d[ t ].getS() == 0) return false;
	        if (s > d[ t ].getS()) {
	            s = d[ t ].getS(); 
	            c = t;
	        }
	    }
		
	    remove(c);
		
	    //枚举c列中的每一行
	    for (i = d[ c ].getD(); i != c; i = d[ i ].getD()) {
	        stack[ k ] = d[ i ].getRow();
	        for (j = d[ i ].getR(); j != i; j = d[ j ].getR()) {
	            remove(d[ j ].getC());
	        }
	        if(dfs(k+1)) return true;
	        for (j = d[ i ].getL(); j != i; j = d[ j ].getL()) {
	            resume(d[ j ].getC());
	        }
	    }
	    resume(c);
	    return false;
	}

	
	public void resume(int c) {
	    d[d[ c ].getR()].setL(c);
	    d[d[ c ].getL()].setR(c);
	    //恢复c列中每个有1的行
	    int i = c, j;
	    for(i = d[ i ].getU(); i != c; i = d[ i ].getU()) {
	        j = i;
	        for(j = d[ j ].getL(); j != i; j = d[ j ].getL()) {
	            d[d[ j ].getC()].modifyS(1);
	            d[d[ j ].getD()].setU(j);
	            d[d[ j ].getU()].setD(j);
	        }
	    }
	}

	
	public void remove(int c) {
		d[d[ c ].getR()].setL(d[ c ].getL());
	    d[d[ c ].getL()].setR(d[ c ].getR());
	    //删除c列中每个有1的行
	    int i = c, j;
	    for(i = d[ i ].getD(); i != c; i = d[ i ].getD()) {
	        j = i;
	        for(j = d[ j ].getR(); j != i; j = d[ j ].getR()) {
	            d[d[ j ].getC()].modifyS(-1);
	            d[d[ j ].getD()].setU(d[ j ].getU());
	            d[d[ j ].getU()].setD(d[ j ].getD());
	        }
	    }
	}

	public int getP() {
		return P;
	}

	public void setP(int P) {
		this.P = P;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	
}

class DLXEdge {
	private int v    = 0;
	private int next = 0;
	
	public DLXEdge(int v, int next) {
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

class DLXNode {
    private int S = 0, C = 0;
    private int L = 0, R = 0;
    private int U = 0, D = 0;
    private int Row = 0;
    
    public DLXNode() {
    	
    }
    
    public void modifyS(int changed) {
    	S += changed;
    }

	public int getS() {
		return S;
	}

	public void setS(int s) {
		S = s;
	}

	public int getC() {
		return C;
	}

	public void setC(int c) {
		C = c;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public int getU() {
		return U;
	}

	public void setU(int u) {
		U = u;
	}

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public int getRow() {
		return Row;
	}

	public void setRow(int row) {
		Row = row;
	}
};

/**
.2738..1..1...6735.......293.5692.8...........6.1745.364.......9518...7..8..6534.
......52..8.4......3...9...5.1...6..2..7........3.....6...1..........7.4.......3.
.................................................................................
527389416819426735436751829375692184194538267268174593643217958951843672782965341
416837529982465371735129468571298643293746185864351297647913852359682714128574936
 **/