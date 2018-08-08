package cc.mi.core.algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class SegmentTree {
	public static final int MAXN = 200001 * 3;
	public static int h = 0;
	public static int w = 0;
	public static int floor = 0;
	public static Segment[] seg = null;
	
	public static void main(String[] args) throws Exception {
		SegmentTree segmentTree = new SegmentTree();
		seg = new Segment[MAXN];
		for (int i = 0; i < seg.length; ++ i) {
			seg[ i ] = new Segment();
		}
		StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while(in.nextToken() != StreamTokenizer.TT_EOF) {
	    	h = (int) in.nval;
	    	in.nextToken();
	    	w = (int) in.nval;
	    	in.nextToken();
	    	int n = (int) in.nval;
	    	h = segmentTree.MIN(h, n);
	    	segmentTree.make(0, h-1, 1);
	    	
	    	for (int i = 0; i < n; ++ i) {
	    		in.nextToken();
				int a = (int) in.nval;
				floor = -1;
				segmentTree.update(1, a);
				out.println(floor);
			}
		}
		out.flush();
	}
	
	public void make(int a, int b, int k) {
		seg[ k ].setA(a);
		seg[ k ].setB(b);
		seg[ k ].setMaxspace(w);

        if(a == b) return;
        int mid = (a + b) >> 1;
        make(a, mid, k << 1);
        make(mid+1, b, (k << 1) | 1);
	}
	
	public void update(int k, int val) {
	    if(seg[ k ].getA() == seg[ k ].getB() && seg[ k ].getMaxspace() >= val) {
	        floor = seg[ k ].getB() + 1;
	        int vv = seg[ k ].getMaxspace() - val;
	        seg[ k ].setMaxspace(vv);
	        return ;
	    }

	    if(seg[ k ].getMaxspace() < val) return;
	    update(k << 1, val);
	    if(floor == -1) {
	        update(k << 1 | 1, val);
	    }
	    int gft = MAX(seg[k << 1].getMaxspace(), seg[(k << 1) | 1].getMaxspace());
	    seg[ k ].setMaxspace(gft);
	}
	
	int MAX(int a, int b) {
	    if(a > b) return a;
	    return b;
	}

	int MIN(int a, int b) {
	    if(a < b) return a;
	    return b;
	}
}

class Segment {
	private int a = 0;
	private int b = 0;
	private int maxspace = 0;
	
	public Segment(int a, int b, int maxspace) {
		this.a = a;
		this.b = b;
		this.maxspace = maxspace;
	}
	
	public Segment() {}
	
	public int getA() {
		return a;
	}
	
	public void setA(int a) {
		this.a = a;
	}
	
	public int getB() {
		return b;
	}
	
	public void setB(int b) {
		this.b = b;
	}
	
	public int getMaxspace() {
		return maxspace;
	}
	
	public void setMaxspace(int maxspace) {
		this.maxspace = maxspace;
	}
}
