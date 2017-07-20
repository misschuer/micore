package cc.mi.core.ex;

public class UndefinedProtocolException extends RuntimeException {
	
	private static final long serialVersionUID = -5724184039735349181L;

	public UndefinedProtocolException() {
		super();
	}
	
	public UndefinedProtocolException(String message) {
		super(message);
	}
}
