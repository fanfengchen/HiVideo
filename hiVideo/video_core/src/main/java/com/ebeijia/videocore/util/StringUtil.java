package com.ebeijia.videocore.util;

import java.util.regex.Pattern;
public class StringUtil {
	
	/**
	 * 判断传入参数,如果是Null或者空值，返回false，不为空返回true	
	 * @author lijm
	 * @param checkAll
	 * True:所有的为空才返回,False:只要有一个为空返回
	 * @param strings
	 * @return
	 */
	public static Boolean checkNull(Boolean checkAll, Object... objects) {
		Boolean ret = true;
		if (objects == null)
			return false;
		for (Object s : objects) {
			if (null == s || ("").equals(s.toString().trim())) {
				if (!checkAll)
					return false;
				else {
					ret = false;
				}
			}
		}
		return ret;
	}
	/*
	 * 手机号码验证 
	 */
	public static boolean CheckPhoneNumber(String paramString) {
		if (Pattern.matches("^[1][3,4,5,7,8][0-9]{9}$", paramString)) {                 
			return true;    
		} else {
			return false;
		}
	}
	/**
	 * 验证邮箱格式
	 * @param param
	 * @return
	 */
	public static boolean CheckEmail(String param){
		if (Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", param)) {
			return true;    
		}else {
			return false;
		}  
	}
	///^[\u4e00-\u9fa5_a-zA-Z]*$/
	/**
	 * 判断含义非法字符
	 * @param param
	 * @return
	 */
	public static boolean CheckString(String param){
		if (Pattern.matches("^[\u4e00-\u9fa5_a-zA-Z]*$", param)) {
			return true;    
		}else {
			return false;
		}  
	}
}
