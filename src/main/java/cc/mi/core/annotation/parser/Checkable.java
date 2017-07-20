package cc.mi.core.annotation.parser;

import java.lang.annotation.Annotation;

import cc.mi.core.annotation.utils.Max;
import cc.mi.core.annotation.utils.Min;
import cc.mi.core.annotation.utils.Range;

public class Checkable {
	private static Checkable instance = null;
	
	private Checkable() {
	}
	
	public static Checkable getInstance() {
		if (instance == null) {
			synchronized (Checkable.class) {
				if (instance == null) {
					instance = new Checkable();
				}
			}
		}
		return instance;
	}
	
	
	public boolean checkMin(Annotation an, int value) {
		int mainValue = ((Min)an).value();
		if (value < mainValue) {
			try {
				throw new Exception("取值必须  >= " + mainValue + ". 当前值:" + value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean checkMax(Annotation an, int value) {
		int mainValue = ((Max)an).value();
		if (value > mainValue) {
			try {
				throw new Exception("取值必须  <= " + mainValue + ". 当前值:" + value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean checkRange(Annotation an, int value) {
		Range range = (Range) an;
		int a = range.a();
		int b = range.b();
		if (value < a || value > b) {
			try {
				throw new Exception("取值范围必须 在" + a + " 与 " + b + "之间" + ". 当前值:" + value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean checkNotNone(Annotation an, Object value) {
		if (value == null) {
			try {
				throw new Exception("对象为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
}