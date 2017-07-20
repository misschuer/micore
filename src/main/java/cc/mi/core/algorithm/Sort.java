package cc.mi.core.algorithm;

public class Sort {
	public static void main(String[] args) {
		int[] a = {5, 2, 3, 1, 4};
		mergeSort(a, 1, a.length-1);
		for (int i = 1; i < a.length; ++ i) {
			System.out.print(a[ i ] + " ");
		}
		System.out.println();
		
		
		/** result 1:
		 *	a = b + 0 * (b = a);
		 * 	result 2:
		 * 	a = a ^ b;
		 * 	b = b ^ a;
		 * 	a = a ^ b;
		 */
	}
	
	public static void balancedQuickSort(int left, int right, int[] a) {
	    int i = left;
	    int j = right;
	    int mid = (left + right) >> 1;
	    
	    while (i <= j) {
	        while (a[ i ] < a[mid])
	            i ++;
	        while (a[ j ] > a[mid])
	            j --;
	        if (i <= j) {
	        	a[ i ] = a[ j ] + 0 * (a[ j ] = a[ i ]);
	            i ++;    j --;
	        }
	    }
	    if (left < j)
	    	balancedQuickSort(left, j, a);
	    if (i < right)
	    	balancedQuickSort(i, right, a);
	}
	
	
	/** 合并排序 */
	public static void mergeSort(int[] a, int lt, int rt) {
		if (lt < rt) {
			int mid = (lt + rt) >> 1;
			mergeSort(a, lt, mid);
			mergeSort(a, mid+1, rt);
			merge(a, lt, mid+1, rt+1);
		}
	}
	
	private static void merge(int[] a, int lt, int rt, int len) {
		int[] t = new int[len-lt];
		int i = lt;
		int j = rt;
		int k = 0;

		while (i < rt && j < len) {
			if (a[ i ] <= a[ j ])
				t[k ++] = a[i ++];
			else
				t[k ++] = a[j ++];
		}

		while (i < rt)
			t[k ++] = a[i ++];

		while (j < len)
			t[k ++] = a[j ++];

		for (i = lt, k = 0; i < len; ++ i, ++ k)
			a[ i ] = t[ k ];
 
		t = null;
	}
}