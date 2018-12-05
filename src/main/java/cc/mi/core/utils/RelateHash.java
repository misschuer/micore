package cc.mi.core.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RelateHash<K, V> {
	protected final Map<K, Set<V>> relateHash;
	public RelateHash() {
		this.relateHash = new HashMap<>();
	}
	
	/**
	 * 下面2个方法最好在同一线程中执行
	 * @param k
	 * @param v
	 */
	public void addWatch(K k, V v) {
		if (!this.relateHash.containsKey(k)) {
			this.relateHash.put(k, new HashSet<>());
		}
		Set<V> set = this.relateHash.get(k);
		set.add(v);
	}
	
	public Set<V> removeAll(K k) {
		return this.relateHash.remove(k);
	}
	
	public void remove(K k, V v) {
		this.relateHash.get(k).remove(v);
		if (this.relateHash.get(k).isEmpty()) {
			this.relateHash.remove(k);
		}
	}
	
	public Iterator<V> relatedList(K k) {
		if (!this.relateHash.containsKey(k)) {
			return null;
		}
		return this.relateHash.get(k).iterator();
	}
}
