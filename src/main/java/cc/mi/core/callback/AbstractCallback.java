package cc.mi.core.callback;

public abstract class AbstractCallback<T> implements Callback<T> {	
	public AbstractCallback() {}
	
	@Override
	public void invoke(T obj) {}
	
	@Override
	public boolean isMatched(T obj) {
		return false;
	}
}
