package cc.mi.core.callback;

public interface Callback<T> {
	public void invoke(T obj);
	
	public boolean isMatched(T obj);
}
