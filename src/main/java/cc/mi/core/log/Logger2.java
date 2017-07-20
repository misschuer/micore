package cc.mi.core.log;

public class Logger2 {
	private Class<?> clazz;
	
	private Logger2(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public static Logger2 getLogger(Class<?> clazz) {
		return new Logger2(clazz);
	}
	
	public void addDevLog(Object... args) {
		System.out.print(String.format("%s, %s -->", this.clazz.getName(), Thread.currentThread().getName()));
		for (int i = 0; i < args.length; ++ i) {
			if (i > 0) System.out.print(", ");
			System.out.print(args[ i ]);
		}
		System.out.println();
	}
}
