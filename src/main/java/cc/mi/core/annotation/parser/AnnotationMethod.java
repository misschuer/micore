package cc.mi.core.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cc.mi.core.annotation.utils.CC;

public class AnnotationMethod {
	private static Map<Class<?>, Method> checkMethodHash = null;
	
	static {
		checkMethodHash = new HashMap<Class<?>, Method>();
		
		try {
			Method[] methods = Checkable.INSTANCE.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("check")) {
					String key = method.getName().substring(5);
					Class<?> clazz = Class.forName(CC.path() + "." + key);
					if (clazz == null) {
						throw new Exception("无此类:" + CC.path() + "." + key);
					}
					checkMethodHash.put(clazz, method);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * spring aop 调用
	 * @param method
	 * @param args
	 * @return
	 */
	public static boolean check(Method method, Object... args) {
		Annotation[][] anns = method.getParameterAnnotations();
		try {
			for (int i = 0; i < anns.length; ++ i) {
				for (int j = 0; j < anns[ i ].length; ++ j) {
					Annotation an = anns[ i ][ j ];
					Method checkMethod = checkMethodHash.get(an.annotationType());
					if (checkMethod != null) {
						boolean checked = (boolean) checkMethod.invoke(Checkable.INSTANCE, (Object)an, args[ i ]);
						if (!checked) return false;
					}
				}
			}
		} catch(IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
