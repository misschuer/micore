package cc.mi.core.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class IndexTree {
	private final IndexSegment[] seg;
	private final int rootIndex = 1;
	
	public IndexTree(int num) {
		this.seg = new IndexSegment[num*3];
		for (int i = 0; i < seg.length; ++ i) {
			seg[ i ] = new IndexSegment();
		}
		this.make(1, num, rootIndex);
	}
	
	public void make(int a, int b, int root) {
		seg[root].setA(a);
		seg[root].setB(b);
		seg[root].setCnt(0);

        if(a == b) return;
        int mid = (a + b) >> 1;
        make(a, mid, root << 1);
        make(mid+1, b, (root << 1) | 1);
	}
	
	public int newIndex() {
	    return this.queryAndNew(rootIndex);
	}
	
	private int queryAndNew(int k) {
	    if(seg[ k ].getA() == seg[ k ].getB()) {
	    	if (seg[ k ].getCnt() == 0) {
	    		seg[ k ].setCnt(1);
	    		return seg[ k ].getB();
	    	}
	        return -1;
	    }

	    if(seg[ k ].isFill()) return -1;
	    // 左搜索
	    int index = queryAndNew(k << 1);
	    // 右搜索
	    if (index < 0) {
	        index = queryAndNew(k << 1 | 1);
	    }
	    // 找到了就加使用的数量
	    if (index > 0) {
	    	seg[ k ].modifyCnt(1);
	    }
	    
	    return index;
	}
	
	
	public boolean resume(int indx) {
		return this.remove(rootIndex, indx);
	}
	
	private boolean remove(int k, int indx) {
	    if(seg[ k ].getA() == seg[ k ].getB()) {
	    	if (seg[ k ].getCnt() == 1) {
	    		seg[ k ].setCnt(0);
	    		return true;
	    	}
	    	return false;
	    }
	    
	    boolean removed = false;
	    if (indx <= seg[k << 1].getB()) {
	    	removed = remove(k << 1, indx);
	    } else {
	    	removed = remove(k << 1 | 1, indx);
	    }
	    
	    if (removed) {
	    	seg[ k ].modifyCnt(-1);
	    }
	    
	    return removed;
	}
	
	public static void main(String[] args) throws IOException {
		StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
	    int h = 10;
	    int root = 1;
		IndexTree indexTree = new IndexTree(h);
		indexTree.make(1, h, root);
		while(in.nextToken() != StreamTokenizer.TT_EOF) {
			int oper = (int) in.nval;
			if (oper == 1) {
				int index = indexTree.newIndex();
				out.println("new index is " + index);
			} else {
				in.nextToken();
				int index = (int) in.nval;
				out.println("removed index ret = " + indexTree.resume(index));
			}
			out.flush();
		}
	}
}

class IndexSegment {
	private int a = 0;
	private int b = 0;
	private int cnt = 0;
	
	public IndexSegment(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	public IndexSegment() {}
	
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
	
	public int getCnt() {
		return cnt;
	}
	
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public boolean isFill() {
		return (b - a + 1) <= cnt;
	}
	
	public void modifyCnt(int modify) {
		cnt += modify;
		if (cnt < 0) {
			throw new RuntimeException("cnt < 0");
		}
	}
}
