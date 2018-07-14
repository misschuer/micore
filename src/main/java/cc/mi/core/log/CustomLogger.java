package cc.mi.core.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public class CustomLogger {
	protected static final String FQCN = CustomLogger.class.getName();
	protected static final Level ERROR_LOG	= Level.forName("ERROR", 12);
	protected static final Level WARN_LOG	= Level.forName("WARN"  , 23);
	protected static final Level DEV_LOG	= Level.forName("DEV"  , 30);
	protected static final Level DEBUG_LOG	= Level.forName("DEBUG"  , 40);
	
	private final ExtendedLoggerWrapper extendLogger;
	
	public CustomLogger(Class<?> clazz) {
		Logger logger = LogManager.getLogger(clazz);
		extendLogger = new ExtendedLoggerWrapper((ExtendedLogger) logger, clazz.getName(), logger.getMessageFactory());
	}
			
	public static CustomLogger getLogger(Class<?> clazz) {
		return new CustomLogger(clazz);
	}
	
	public void debugLog(String message, Object... params) {
		extendLogger.logIfEnabled(FQCN, DEBUG_LOG, null, message, params);
	}
	
	public void devLog(String message, Object... params) {
		extendLogger.logIfEnabled(FQCN, DEV_LOG, null, message, params);
	}
	
	public void warnLog(String message, Object... params) {
		extendLogger.logIfEnabled(FQCN, WARN_LOG, null, message, params);
	}
	
	public void errorLog(String message, Object... params) {
		extendLogger.logIfEnabled(FQCN, ERROR_LOG, null, message, params);
	}
}
