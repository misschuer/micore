package cc.mi.core.algorithm;

import java.util.List;

public class BinarySearch {
	public static int findLevel(List<Integer> list, int exp) {
		int lt = 0;
		int rt = list.size() - 1;
		int ans = 1;

		while (lt <= rt) {
			int mid = (lt + rt) >> 1;
			int val = list.get(mid);
			if (exp < val) {
				rt = mid - 1;
			} else {
				lt = mid + 1;
				ans = mid + 1;
			}
		}

		return ans;
	}

	public static int findCeilNumber(int[] array, int value) {
		int lt = 0;
		int rt = array.length - 1;
		int ans = 0;

		while (lt <= rt) {
			int mid = (lt + rt) >> 1;
			int val = array[mid];
			if (value < val) {
				rt = mid - 1;
			} else if (value > val) {
				lt = mid + 1;
				ans = lt;
			} else {
				ans = mid;
				break;
			}
		}

		return ans;
	}
}
