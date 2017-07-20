package cc.mi.core.algorithm;

import java.util.Iterator;
import java.util.Set;

public class FetterUnit {
	private final int id;
	private final Set<Integer> heroIdSet;
	private final int value;
	
	public Iterator<Integer> iterator() {
		return heroIdSet.iterator();
	}
	
	public boolean hasValue() {
		return heroIdSet.size() > 0;
	}
	
	public FetterUnit(int id, Set<Integer> heroIdSet, int value) {
		this.id = id;
		this.heroIdSet = heroIdSet;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		return value;
	}
}
