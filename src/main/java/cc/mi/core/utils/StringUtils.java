package cc.mi.core.utils;

import java.util.ArrayList;
import java.util.List;

import cc.mi.core.algorithm.KMP;

public final class StringUtils {
	private StringUtils() {}
	
    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String capitalized(String str) {
        return capitalized(str, 1);
    }
    
    /**
     * 前index个字母大写
     * @param str
     * @param index
     * @return
     */
    public static String capitalized(String str, int index) {
        return str.substring(0, index).toUpperCase() + str.substring(index);
    }
    
    public static String uncapitalize(String str) {
    	return str.substring(0, 1).toLowerCase() + str.substring(str.length());
    }
    
    public static String toClassQualifier(String str) {
    	return str.replaceAll("/", "\\.").substring(1, str.length()-1);
    }
    
    public static String descriptorToClazzName(String desc) {
    	return desc.substring(1, desc.length()-1);
    }
    
    /**
	 * @param str
	 *	a string need to be seperated
	 * @param sep
	 * 	a seperator
	 * 
	 * @return strList
	 * 	seperated string list
	 * @throws Exception
	 * 	if sep is an empty string
	 **/
	public static List<String> split(String str, String sep) throws Exception {
		List<String> strList = new ArrayList<String>();
		if ("".equals(str.trim()))
			return strList;
		if ("".equals(sep.trim()))
			throw new Exception("param sep cannot be an empty string");
		
		List<Integer> indexList = KMP.matchIndexes(sep, str);
		int start = 0;
		for (int i = 0; i < indexList.size(); ++ i) {
			int end = indexList.get(i);
			strList.add(str.substring(start, end));
			start = end + sep.length();
		}
		indexList.clear();
		indexList = null;
		
		return strList;
	}
	
	
	/**
	 * @param strList
	 * 	a string list need to be joined
	 * @param sep
	 * 	a seperator
	 * @return ret
	 * 	joined string
	 **/
	public static String join(List<String> strList, String sep) {
		if (strList.size() == 0)
			return "";
		
		StringBuffer buffer = new StringBuffer(strList.get(0));
		for (int i = 1; i < strList.size(); ++ i) {
			buffer.append(sep);
			buffer.append(strList.get(i));
		}
		String ret = buffer.toString();
		buffer = null;
		
		return ret;
	}
	
	/**
	 * @param str
	 *	a string need to be strip left
	 * @param sep
	 * 	a striper
	 * 
	 * @return str
	 * 	stripped string
	 **/
	public static String lStrip(String str, String sep) {
		if ("".equals(str.trim()))
			return str;
		if ("".equals(sep.trim()))
			return str;
		
		List<Integer> indexList = KMP.matchIndexes(sep, str);
		int start = 0;
		for (int i = 0; i < indexList.size() - 1; ++ i) {
			int first = indexList.get(i);
			if (start != first)
				break;
			start = first + sep.length();
		}
		
		return str.substring(start);
	}
	
	/**
	 * @param str
	 *	a string need to be strip right
	 * @param sep
	 * 	a striper
	 * 
	 * @return str
	 * 	stripped string
	 **/
	public static String rStrip(String str, String sep) {
		if ("".equals(str.trim()))
			return str;
		if ("".equals(sep.trim()))
			return str;
		
		List<Integer> indexList = KMP.matchIndexes(sep, str);
		int start = str.length() - sep.length();
		for (int i = indexList.size() - 2; i >= 0; -- i) {
			int first = indexList.get(i);
			if (start != first)
				break;
			start = first - sep.length();
		}
		
		return str.substring(0, start+sep.length());
	}
	
	/**
	 * @param str
	 *	a string need to be just left
	 * @param len
	 * 	new string length (if str.length <= len)
	 * @param sep
	 * 	a juster
	 * 
	 * @return str
	 * 	stripped string
	 **/
	public static String lJust(String str, int len, String sep) throws Exception {
		int addition = len - str.length();
		if (addition < 0)
			return str;
		if (addition % sep.length() != 0)
			throw new Exception("check sep.length * K + str.length = len");
		
		int k = addition / sep.length();
		return just("", sep, str, k);
	}
	
	/**
	 * @param str
	 *	a string need to be just right
	 * @param len
	 * 	new string length (if str.length <= len)
	 * @param sep
	 * 	a juster
	 * 
	 * @return str
	 * 	stripped string
	 **/
	public static String rJust(String str, int len, String sep) throws Exception {
		int addition = len - str.length();
		if (addition < 0)
			return str;
		if (addition % sep.length() != 0)
			throw new Exception("check sep.length * K + str.length = len");
		
		int k = addition / sep.length();
		return just(str, sep, "", k);
	}
	
	private static String just(String left, String sep, String right, int k) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(left);
		for (int i = 0; i < k; ++ i) {
			buffer.append(sep);
		}
		buffer.append(right);
		String ret = buffer.toString();
		buffer = null;
		
		return ret;
	}
}
