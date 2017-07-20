package cc.mi.core.algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnionFindSet {
	static final int MAX_HERO_NUMBER = 230;
	private final Map<Integer, Integer> prev;
	
	public UnionFindSet(int maxId) {
		prev = new HashMap<>();
	}
	
	public UnionFindSet() {
		this(MAX_HERO_NUMBER);
	}
	
	public void putIfNotAbsent(int heroId) {
		if (!prev.containsKey(heroId)) {
			prev.put(heroId, heroId);
		}
	}
	
	public List<UnionGroup> group() {
		List<UnionGroup> ret = new ArrayList<>();
		for (int i : prev.keySet()) {  
			find(i);  
		}
		
		Map<Integer, UnionGroup> groupHash = new HashMap<>();
		for (int i : prev.keySet()) {  
			int root = prev.get(i);
			if (!groupHash.containsKey(root)) {
				groupHash.put(root, new UnionGroup());
			}
			groupHash.get(root).put(i);
		}
		
		ret.addAll(groupHash.values());
		groupHash.clear();
		
		return ret;
	}
	
	public int find(int x) {
		int fx = prev.get(x);
		if (x == fx) {
			return x;
		}
		int ffx = find(fx);
		prev.put(x, ffx);
		return ffx;
	}
	
	public void union(int x, int y) {
		putIfNotAbsent(x);
		putIfNotAbsent(y);
		
		int fx = find(x);
		int fy = find(y);
		
		if (fx != fy) {
			prev.put(fx, fy);
		}
	}
	
	public static class UnionGroup {
		private final Set<Integer> elementSet = new HashSet<>();
		public UnionGroup() {}
		
		public boolean contains(int key) {
			return elementSet.contains(key);
		}
		
		public void put(int key) {
			elementSet.add(key);
		}

		public Set<Integer> getElementSet() {
			return elementSet;
		}
	}
}

