package cc.mi.core.log;

import org.apache.log4j.Logger;

/**
 * learn lmax disruptor
 * @author mi
 *
 */
public class Demo {
	private static final Logger logger = Logger.getLogger(Demo.class.getName());
	
	public static void main(String[] args) throws InterruptedException {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 1000; ++ i) {
			logger.error("fff");
		}
		long b = System.currentTimeMillis();
		System.out.println((b - a) + "ms");
	}
}
