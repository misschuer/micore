package cc.mi.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectUtils {
	private ReflectUtils(){}
	
	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException, SecurityException {
		return clazz.getDeclaredField(fieldName);
	}
	
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... classArgs) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		return clazz.getDeclaredMethod(methodName, classArgs);
	}
	
	public static Object invoke(Object obj, Method method, Object...args) {
		try {
			return method.invoke(obj, args);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object getValue(Object object, Method method, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
