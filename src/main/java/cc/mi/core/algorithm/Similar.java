package cc.mi.core.algorithm;

import java.util.Scanner;

public class Similar {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		while(cin.hasNext()) {
			String p1 = cin.next();
			String p2 = cin.next();
			similar(p1, p2);
		}
		cin.close();
	}
	
	public static void similar(String p1, String p2) {
		int[][] sim = new int[p1.length()+1][p2.length()+1];
		
		for (int i = 1; i <= p1.length(); ++ i) {
			for (int j = 1; j <= p2.length(); ++ j) {
				if (p1.charAt(i-1) == p2.charAt(j-1))
					sim[ i ][ j ] = sim[i-1][j-1] + 1;
				else
					sim[ i ][ j ] = max(sim[i-1][ j ], sim[ i ][j-1]);
			}
		}
		System.out.println(sim[p1.length()][p2.length()]);
	}
	
	public static int max(int a, int b) {
		return a > b ? a : b;
	}

}
