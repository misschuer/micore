package cc.mi.core.callback;

public abstract class ReturnedCallback<T, R> {
	public abstract R returnedInvoke(T value);
}
