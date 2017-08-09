package cc.mi.core.callback;

public interface Callback<T> {
	public void invoke(T value);
	
	public boolean isMatched(T value);
}
