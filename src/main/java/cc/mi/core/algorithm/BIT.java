package cc.mi.core.algorithm;

/**
 * BinaryIndexedTree
 * k = lowbit(x)
 * x 维护  k(即2^leadingZeroNum(x)) 个数
 * arr[x] = sum(a[x-k+1],..., a[x])
 **/
public class BIT {
	private int[] arr = null;
	private int     n =    0;
	
	/** x二进制的末尾0个数 */
	public static int lowbit(int x) {
		return x & -x;
	}
	
	public BIT(int n) {
		this.n   =            n;	
		this.arr = new int[n+1];
	}

	public void add(int x, int val) {
		while (x <= n) {
			arr[ x ] += val;
			x +=  lowbit(x);
		}
	}
	
	public int sum(int x) {
		int ans = 0;
		while (x > 0) {
			ans += arr[ x ];
			x -=  lowbit(x);
		}
		
		return ans;
	}
}


/** 改段求点 */
class BIT2 {
    private int[] arr = null;
    private int     n =    0;
    
    /** x二进制的末尾0个数 */
    public static int lowbit(int x) {
        return x & -x;
    }
    
    
    public BIT2(int n) {
        this.n   =            n;    
        this.arr = new int[n+1];
    }

    
    public void add(int x, int val) {
        while (x > 0) {
            arr[ x ] += val;
            x -=  lowbit(x);
        }
    }
    
    public int sum(int x) {
        int ans = 0;
        while (x <= n) {
            ans += arr[ x ];
            x +=  lowbit(x);
        }
        
        return ans;
    }
    
    public void demo() {
    	/**
    	 * l, r, c
    	 * ADD(l-1, -c); ADD(r, c);
    	 * x
    	 * SUM(x)
    	 **/
    }
}

/** 改段求段 */
class BIT3 {
    private long[] arrB = null;
    private long[] arrC = null;
    private int     n =    0;
    
    /** x二进制的末尾0个数 */
    private static int lowbit(int x) {
        return x & -x;
    }
    
    
    public BIT3(int n) {
        this.n    =             n;
        this.arrB = new long[n+1];
        this.arrC = new long[n+1];
    }

    
    private void add(long[] arr, int x, long val) {
        while (x <= n) {
            arr[ x ] += val;
            x +=  lowbit(x);
        }
    }
    
    private long sum(long[] arr, int x) {
        long ans = 0;
        while (x > 0) {
            ans += arr[ x ];
            x -=  lowbit(x);
        }
        
        return ans;
    }
    
    /** 
     * 根据公式Sum[x] = SUM(A[i], i=1...x)+(x+1)*SUM(delta[i],i=1...x)-SUM(i*delta[i],i=1...x)
     * 求Sum[x]
     **/ 
    private long sumResult(long[] sums, long[] t1, long[] t2, int x) {  
        long res = sums[ x ];
        res += (x+1) * sum(t1, x);
        res -= sum(t2, x);
        return res;
    } 
    
    /** sums[i] = sum(a[1],..,a[i]) */
    public long getResult(long[] sums, int a, int b) {
    	return sumResult(sums, arrB, arrC, b) -  sumResult(sums, arrB, arrC, a-1);
    }
    
    public void updateSeg(int a, int b, int c) {
    	 add(arrB, a, c); 
         add(arrC, a, a*c);
         add(arrB, b+1, -c);
         add(arrC, b+1, -(b+1)*c);
    }
    
    public void demo() {
    	/**
    	 * a, b
    	 * sumResult(initSum, t1, t2, b)-sumResult(initSum, t1, t2, a-1)
    	 * a, b, c
    	 * /Sum[x] = SUM(A[i], i=1...x)+(x+1)*SUM(delta[i],i=1...x)-SUM(i*delta[i],i=1...x)  
            //在成段更新时,是对delta数组进行更新,于是用两个树状数组t1,t2来维护  
            //两个前缀和SUM(delta[i],i=1...x),SUM(i*delta[i],i=1...x),  
            //而SUM(A[i], i=1...x)在更新过程中是不变的，所以可以预处理在数组中  
            //将区间的成段更新转化成对t1,t2两个树状数组的单点更新，使更新复杂度保持O(log(n))  
            add(t1, a, c); //更新SUM(delta[i],i=1...x)中的delta[a]  
            add(t2, a, a*c);//更新SUM(i*delta[i],i=1...x)中的delta[a]  
                              //这两个update后,区间A[a...n]每个数都增加了c。  
            add(t1, b+1, -c);
            add(t2, b+1, -(b+1)*c);
         //这两个update后,区间A[b+1...n]每个数都减少了c
         //完成对区间A[a...b]的更新  
    	 **/
    }
}