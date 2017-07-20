package cc.mi.core.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.mi.core.algorithm.ArithmeticUtils;

public class RandomUtils {
	private static Random random = new Random();
	
	public static int nextInt(int a) {
		return random.nextInt(a);
	}
	
	
	public static int randomRange(int a, int b) {
		return random.nextInt(b - a + 1) + a;
	}
	
	
	public static boolean isTrigger(int succ) {
		int value = random.nextInt(100) + 1;
		return value <= succ;
	}

	
	public static boolean isTrigger4(int succ) {
		int value = random.nextInt(10000) + 1;
		return value <= succ;
	}

	
	public static int randomArrayListIndex(List<Integer> list) {
		return random.nextInt(list.size());
	}
	
	public static <T> T randomWeightObject(List<T> objectList, String methodName) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			for (int i = 0; i < objectList.size(); ++ i) {
				T obj = objectList.get(i);
				Method method = ReflectUtils.getMethod(obj.getClass(), methodName);
				int weight = (int) ReflectUtils.invoke(obj, method);
				list.add(weight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int index = randomWeightListIndex(list);
		
		return objectList.get(index);
	}
	
	
	public static int randomWeightListIndex(List<Integer> list) {
		int limit = ArithmeticUtils.sum(list);
		int ran = random.nextInt(limit) + 1;
		int add = 0;

		for (int i = 0; i < list.size(); ++i) {
			add += list.get(i);
			if (ran <= add) {
				return i;
			}
		}
		
		return 0;
	}
}
