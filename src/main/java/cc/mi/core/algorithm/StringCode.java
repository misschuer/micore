package cc.mi.core.algorithm;

public class StringCode {
	public static String encode(String str) {
        if ("".equals(str)) {  
            return "";
        }
          
        StringBuffer strBuff = new StringBuffer("");  
        try {
            byte b[] = str.getBytes("UTF-16");  
            for (int n = 0; n < b.length; n++) {
                str = (Integer.toHexString(b[n] & 0XFF));
                if (str.length() == 1) {  
                    strBuff.append("0").append(str);
                } else {  
                    strBuff.append(str);
                }  
            }  
            str = strBuff.toString().toUpperCase().substring(4);  
            char[] chs = str.toCharArray();  
            strBuff.delete(0, strBuff.length());  
            for (int i = 0; i < chs.length; i = i + 4) {  
                strBuff.append(chs[i])  
                       .append(chs[i + 1])  
                       .append(chs[i + 2])  
                       .append(chs[i + 3]);
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
          
        return strBuff.toString();  
    }  
      
    public static String decode(String str) {  
        if ("".equals(str)) {  
            return "";  
        }
        StringBuffer sb = new StringBuffer(""); 
        try {  
            for (int i =0; i < str.length()-3; i = i + 4) {
                sb.append((char)Integer.valueOf(str.substring(i, i+4),16).intValue());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
          
        return sb.toString();  
    }
}