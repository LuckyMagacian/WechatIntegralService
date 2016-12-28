package com.lanxi.WechatIntegralService.util;

public class CheckReplaceUtil {
	/**
	 * 传入�?个字符串,若该字符串为null则返回空,否则返回去除空字符的结果
	 * @param string
	 * @return
	 */
	public static String nullAsSpace(String string){
		return string==null?"":string.trim();
	}
	/**
	 * 传入�?个字符串,若该字符串为空则返回null,否则返回去除空字符的结果
	 * @param string
	 * @return
	 */
	public static String spaceAsNull(String string){
		return string==null?null:string.trim().equals("")?null:string.trim();
	}
	/**
	 * 传入�?个字符串,若该字符串为null则返回null
	 * 			若该字符串第�?个字符为小写英文字母,则将其改为大写并返回
	 * 			否则返回原字符串
	 * @param string
	 * @return
	 */
	public static String firstCharUpcase(String string){
		if(string==null)
			return null;
		char first=string.charAt(0);
		if(first>='a'&&first<='z'){
			return string.replaceFirst(""+first,""+(char)(first-32));
		}
		return string;
	}
	/**
	 * 传入�?个字符串,若该字符串为null则返回null
	 * 			若该字符串第�?个字符为大写英文字母,则将其改为小写写并返�?
	 * 			否则返回原字符串
	 * @param string
	 * @return
	 */
	public static String firstCharLowcase(String string){
		if(string==null)
			return null;
		char first=string.charAt(0);
		if(first>='A'&&first<='Z'){
			return string.replaceFirst(""+first,""+(char)(first+32));
		}
		return string;
	}
	/**
	 * 传入�?个字符串,若该字符串为null ,直接返回null
	 * 			若该字符串包含大写英文字母则将其改为下划�?+对应小写的形�?(_?)
	 * @param string
	 * @return
	 */
	public static String upcaseToUnderlineLowcaser(String string){
		if(string==null)
			return null;
		StringBuffer temp=new StringBuffer();
		boolean flag=true;
		for(char each:string.toCharArray()){
			if(each>='A'&&each<='Z'){
				if(flag){
					flag=false;
					temp.append((char)(each+32));
					continue;
				}
				temp.append("_").append((char)(each+32));
			}
			else{
				temp.append(each);
			}
		}
		return temp.toString();
	}
	
}
