package cc.mi.core.callback;

public abstract class AbstractCallback<T> implements Callback<T> {	
	public AbstractCallback() {}
	
	@Override
	public void invoke(T value) {}
	
	@Override
	public boolean isMatched(T value) {
		return false;
	}
	
	public T createObject() {
		return null;
	}
}
