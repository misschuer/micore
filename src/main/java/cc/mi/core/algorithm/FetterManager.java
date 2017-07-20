package cc.mi.core.algorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.mi.core.algorithm.UnionFindSet.UnionGroup;

/**
 * 把羁绊记录的交集部分
 * dp[ i ][ j ] = max{dp[i-1][ j ], max{dp[i-1][j-weight[i][k]] + val[ i ][ k ]}};
 * 前 i个集合中合并j个数的最大值
 * 
 * 在没有重复的情况下是典型的0-1背包
 * @author gongyuan
 *
 */
public final class FetterManager {
	private static final List<Fetter> FETTER_LIST = Arrays.asList(
			new Fetter(Arrays.asList(1, 5), 1),
			new Fetter(Arrays.asList(2, 10), 3),
			new Fetter(Arrays.asList(1, 7, 10), 20),
			new Fetter(Arrays.asList(3, 4), 5),
			new Fetter(Arrays.asList(4, 8, 15), 10),
			new Fetter(Arrays.asList(9), 8)
		);
	
	public static void main(String[] args) {
//		Random random = new Random();
//		for (int cas = 0; cas < 10; ++ cas) {
//			List<Fetter> fetterList = new ArrayList<>();
//			int size = random.nextInt(20);
//			for (int j = 0; j < size; ++ j) {
//				int a = random.nextInt(100)+1;
//				int b = random.nextInt(100)+1;
//				int c = random.nextInt(100)+1;
//				int v = random.nextInt(100);
//				List<Integer> fetterInfo = Arrays.asList(a, b, c);
//				Fetter fetter = new Fetter(fetterInfo, v);
//				fetterList.add(fetter);
//				System.out.println(Arrays.toString(fetterInfo.toArray()) + " " + v);
//			}
//			action(fetterList, 5);
//			System.out.println("------------------------------------\n");
//		}
		long a = System.currentTimeMillis();
		action(FETTER_LIST, 3);
		action(FETTER_LIST, 4);
		action(FETTER_LIST, 5);
		long b = System.currentTimeMillis();
		System.out.println((b-a) + "ms");
	}
	
	public static List<Integer> getLineInfo(Set<Integer> teamHeroSet,
											Set<Integer> candidate,
											List<FetterUnit> fetterUnitList,
											int m) {
		List<Fetter> fetterList = filter(teamHeroSet, candidate, fetterUnitList);
		return action(fetterList, m);
	}
	
	/**
	 * 过滤 确保数据中的武将都是在candidate集合中且未上阵的
	 * @param teamHeroSet
	 * @param candidate
	 * @param fetterUnitList
	 * @return
	 */
	protected static List<Fetter> filter(Set<Integer> teamHeroSet,
									   Set<Integer> candidate,
									   List<FetterUnit> fetterUnitList) {
		List<Fetter> ret = new ArrayList<>();
		for (FetterUnit unit : fetterUnitList) {
			boolean valid = check(unit, teamHeroSet, candidate);
			if (!valid) {
				continue;
			}
			List<Integer> fetterInfo = new ArrayList<>();
			Iterator<Integer> iter = unit.iterator();
			for (;iter.hasNext();) {
				fetterInfo.add(iter.next());
			}
			if (fetterInfo.size() > 0) {
				Fetter fetter = new Fetter(fetterInfo, unit.getValue());
				ret.add(fetter);
			}
		}
		
		return ret;
	}
	
	/**
	 * 过滤 确保数据中的武将都是在candidate集合中且未上阵的
	 * 合并相同元素的集合
	 * @param teamHeroSet
	 * @param candidate
	 * @param fetterUnitList
	 * @return
	 */
	public static List<Fetter> filter2(Set<Integer> teamHeroSet,
									   Set<Integer> candidate,
									   List<FetterUnit> fetterUnitList) {
		Map<String, Fetter> fetterHash = new HashMap<>();
		for (FetterUnit unit : fetterUnitList) {
			boolean valid = check(unit, teamHeroSet, candidate);
			if (!valid) {
				continue;
			}
			List<Integer> fetterInfo = new ArrayList<>();
			Iterator<Integer> iter = unit.iterator();
			for (;iter.hasNext();) {
				fetterInfo.add(iter.next());
			}
			if (fetterInfo.size() > 0) {
				// 从小到大排序
				Collections.sort(fetterInfo);
				String key = parserToKey(fetterInfo);
				if (!fetterHash.containsKey(key)) {
					Fetter fetter = new Fetter(fetterInfo, unit.getValue());
					fetterHash.put(key, fetter);
				}
				else {
					Fetter fetter = fetterHash.get(key);
					fetter.addValue(unit.getValue());
				}
			}
		}
		
		List<Fetter> fetterList = new ArrayList<>();
		fetterList.addAll(fetterHash.values());
		fetterHash.clear();
		
		return fetterList;
	}
	
	private static String parserToKey(List<Integer> fetterInfo) {
		if (fetterInfo == null || fetterInfo.size() == 0) {
			throw new RuntimeException("集合里面无元素");
		}
		String str = fetterInfo.get(0) + "";
		for (int i = 1; i < fetterInfo.size(); ++ i) {
			str += "-" + fetterInfo.get(i);
		}
		
		return str;
	}
	
	private static boolean check(FetterUnit unit,
								 Set<Integer> teamHeroSet,
								 Set<Integer> candidate) {
		Iterator<Integer> iter = unit.iterator();
		for (;iter.hasNext();) {
			int heroId = iter.next();
			if (teamHeroSet.contains(heroId)) {
				iter.remove();
				continue;
			}
			if (!candidate.contains(heroId)) {
				return false;
			}
		}
		
		return unit.hasValue();
	}
	
	
	public static List<Integer> action(List<Fetter> fetterList, int m) {
		Map<Integer, Group> groupHash = group(fetterList);
		
		for (Group group : groupHash.values()) {
			group.calShape(m);
		}
		int n = groupHash.size();
		List<Integer> result = new ArrayList<>(m);
		int[] dp = new int[m+1];
		int[][] path = new int[n+1][m+1];
		
		for (int i = 1; i <= n; ++ i) {
			Group group = groupHash.get(i);
			for (int j = m; j >= 1; -- j) {
				for (int k = 1; k <= j; ++ k) {
					Fetter fetter = group.getFetterByWeight(k);
					if (fetter != null) {
						int value = fetter.getValue();
						if (dp[ j ] < dp[j-k] + value) {
							dp[ j ] = dp[j-k] + value;
							path[ i ][ j ] = k;
						}
					}
				}
			}
		}
		
		int W = m;
		for (int i = n; i >= 1; -- i) {
			if (path[ i ][ W ] > 0) {
				Group group = groupHash.get(i);
				int k = path[ i ][ W ];
				Fetter fetter = group.getFetterByWeight(k);
				for (int heroId : fetter.getFetterInfo()) {
					result.add(heroId);
				}
				W -= fetter.getWeight();
			}
		}
//		System.out.println(dp[ n ][ m ]);
		System.out.println("result: " + Arrays.toString(result.toArray()) + " " + dp[ m ]);
		return result;
	}
	
//	private static void printInit() {
//		System.out.println("======= init =======");
//		for (Fetter fetter : FETTER_LIST) {
//			System.out.println(Arrays.toString(fetter.getFetterInfo().toArray()) + " " + fetter.getValue());
//		}
//	}
	
	/**
	 * 分组
	 * @param a
	 */
	private static Map<Integer, Group> group(List<Fetter> fetterList) {
//		System.out.println("======= group =======");
		// 群组
		Map<Integer, Group> groupHash = new HashMap<>();
		
		// 先分组
		UnionFindSet unionSet = new UnionFindSet();
		for (int i = 0; i < fetterList.size(); ++ i) {
			Fetter fetter = fetterList.get(i);
			List<Integer> fetterInfo = fetter.getFetterInfo();
			int a = fetterInfo.get(0);
			if (fetterInfo.size() > 1) {
				for (int j = 1; j < fetterInfo.size(); ++ j) {
					int b = fetterInfo.get(j);
					unionSet.union(a, b);
					a = b;
				}
			}
			else {
				unionSet.putIfNotAbsent(a);
			}
		}
		
		// 在分组完的fetter处理掉
		List<UnionGroup> ugList = unionSet.group();
		Map<Integer, Integer> heroGroupHash = new HashMap<>();
		for (int i = 0; i < ugList.size(); ++ i) {
			UnionGroup ug = ugList.get(i);
			final int k = i + 1;
			for (int heroId : ug.getElementSet()) {
				heroGroupHash.put(heroId, k);
			}
		}
		
		for (Fetter fetter : fetterList) {
			int heroId = fetter.getFirstHeroId();
			int groupId = heroGroupHash.get(heroId);
			if (!groupHash.containsKey(groupId)) {
				groupHash.put(groupId, new Group());
			}
			groupHash.get(groupId).putFetter(fetter);
		}
		
		return groupHash;
	}
	
	static class Fetter {
		private final List<Integer> fetterInfo;
		private int value;
		
		public int getWeight() {
			return fetterInfo.size();
		}
		
		public int getFirstHeroId() {
			return fetterInfo.get(0);
		}
		
		public Fetter(List<Integer> fetterInfo, int value) {
			this.fetterInfo = fetterInfo;
			this.value = value;
		}

		public List<Integer> getFetterInfo() {
			return fetterInfo;
		}

		public int getValue() {
			return value;
		}
		
		public void addValue(int val) {
			this.value += val;
		}
		
		public static Fetter createEmptyFetter() {
			List<Integer> emptyFetterInfo = new ArrayList<>();
			return new Fetter(emptyFetterInfo, 0);
		}
		
		public Fetter addFetter(Fetter a) {
			Set<Integer> heroSet = new HashSet<>();
			List<Integer> newFetterInfo = new ArrayList<>();
			for (int heroId : this.fetterInfo) {
				if (!heroSet.contains(heroId)) {
					heroSet.add(heroId);
					newFetterInfo.add(heroId);
				}
			}
			
			for (int heroId : a.getFetterInfo()) {
				if (!heroSet.contains(heroId)) {
					heroSet.add(heroId);
					newFetterInfo.add(heroId);
				}
			}
			
			int newValue = this.value + a.getValue();
//			System.out.println(Arrays.toString(this.fetterInfo.toArray()) + " " + this.value);
//			System.out.println("++++++++++++++++++++++++++++++");
//			System.out.println(Arrays.toString(a.getFetterInfo().toArray()) + " " + a.getValue());
//			System.out.println("==============================");
			Fetter ff = new Fetter(newFetterInfo, newValue);
//			System.out.println(Arrays.toString(ff.getFetterInfo().toArray()) + " " + ff.getValue());
			return ff;
		}
	}
	
	static class Group {
		private final List<Fetter> fetterList;
		private final Map<Integer, Fetter> shapeHash;
		private final Set<Integer> heroSet = new HashSet<>();
		
		public void showGroup() {
			System.out.println(Arrays.toString(heroSet.toArray()));
		}
		
		public Group() {
			fetterList = new ArrayList<>();
			shapeHash = new HashMap<>();
		}
		
		public List<Fetter> getFetterList() {
			return fetterList;
		}
		
		public void putFetter(Fetter fetter) {
			fetterList.add(fetter);
			for (int heroId : fetter.getFetterInfo()) {
				heroSet.add(heroId);
			}
		}
		
		public boolean contains(int heroId) {
			return heroSet.contains(heroId);
		}
		
		public void calShape(int m) {
			shapeHash.clear();
			if (fetterList.size() > 20) {
				System.out.println(Arrays.toString(heroSet.toArray()));
				return;
			}
			backtrack(fetterList.size(), m, 0, Fetter.createEmptyFetter());
//			showShape();
		}
		
		public void showShape() {
			System.out.println(this + "======= shaped =======");
			for (Fetter fetter : shapeHash.values()) {
				System.out.println(fetter.getWeight() + "" + Arrays.toString(fetter.getFetterInfo().toArray()) + " " + fetter.getValue());
			}
		}
		
		private void backtrack(int n, int m, int k, Fetter fetter){
			if (k >= n) {
				return;
			}
			
			for (int i = k; i < n; ++ i) {
				Fetter newFetter = fetter.addFetter(fetterList.get(i));
				int weight = newFetter.getWeight();
				// 超出最大重量的时候过滤
				if (weight > m) {
					continue;
				}
				// 没有对应重量的数据
				if (!shapeHash.containsKey(weight)) {
					shapeHash.put(weight, newFetter);
				}
				else {
					Fetter prev = shapeHash.get(weight);
					if (newFetter.getValue() > prev.getValue()) {
						shapeHash.put(weight, newFetter);
					}
				}
				backtrack(n, m, i+1, newFetter);
			}
		}

		public Fetter getFetterByWeight(int weight) {
			return shapeHash.get(weight);
		}
	}
}
