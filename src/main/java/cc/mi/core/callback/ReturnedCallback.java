package cc.mi.core.callback;

public abstract class ReturnedCallback<T, R> extends AbstractCallback<T> {
	public abstract R returnedInvoke(T value);
}
