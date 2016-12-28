package com.lanxi.WechatIntegralService.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtil {
	/**
	 * 获取类中�?有字�?
	 * @param clazz
	 * @return
	 */
	public static Map<String,Field> getAllFields(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();
		Map<String, Field> map=new LinkedHashMap<String, Field>();
		for(Field each:fields){
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中 非static 字段 以linkedHashMap形式返回
	 * @param  clazz  传入的class对象
	 * @return map<name,Field> 
	 */
	public static Map<String, Field> getFieldsNoStatic(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();
		Map<String, Field> map=new LinkedHashMap<String, Field>();
		for(Field each:fields){
			each.setAccessible(true);
			if(Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中�?有方�?
	 * @param clazz
	 * @return
	 */
	public static Map<String, Method> getAllMethods(Class<?> clazz){
		Map<String, Method> map=new LinkedHashMap<String, Method>();
		Method[] methods=clazz.getDeclaredMethods();
		for(Method each:methods){
			each.setAccessible(true);
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 获取类中 非static 方法 以linkedHashMap形式返回
	 * @param  clazz  传入的class对象
	 * @return map<name,Method>
	 */
	public static Map<String, Method> getMethodsNoStatic(Class<?> clazz){
		Map<String, Method> map=new LinkedHashMap<String, Method>();
		Method[] methods=clazz.getDeclaredMethods();
		for(Method each:methods){
			each.setAccessible(true);
			if(Modifier.isStatic(each.getModifiers()))
				continue;
			map.put(each.getName(), each);
		}
		return map;
	}
	/**
	 * 从table中创建java文件  未实�?
	 * @param table
	 * @return
	 */
	public static String makeBeanFileFromTable(DBTable table){
		try {
			StringBuffer file=new StringBuffer();
			String packageStr =BeanUtil.class.getPackage().toString();
			packageStr=packageStr.substring(0,packageStr.lastIndexOf("."));
			packageStr="package "+packageStr+".entity";
			file.append(packageStr);
			file.append("\n");
			
			
			
			return file.toString();
		} catch (Exception e) {
			throw new AppException("从表中生成bean文件异常",e);
		}
	}
	
	/**
	 * 从请求bean中获取参�? 已map形式返回 由type决定是否要返回签�?
	 * @param bean
	 * @return
	 * @throws EleGiftException 
	 */
	public static Map<String,String> getParamMap(Object bean){
		Map<String, String> rs=new LinkedHashMap<String, String>();
		Map<String, Field> map=getFieldsNoStatic(bean.getClass());
		try {
			for(Map.Entry<String, Field> each:map.entrySet()){
				String name=each.getKey();
				Field  field=each.getValue();
				field.setAccessible(true);
				String value = (String) field.get(bean);
				rs.put(name,value);
			}
		} catch (Exception e) {
			throw new AppException("获取属�?�异�?",e);
		}
		return rs;
	}
}
