package cc.mi.core.annotation.utils;

public class CC {
	public static final String path() {
		return CC.class.getPackage().getName();
	}
	
	public static void main(String[] args) {
		System.out.println(path());
	}
}
