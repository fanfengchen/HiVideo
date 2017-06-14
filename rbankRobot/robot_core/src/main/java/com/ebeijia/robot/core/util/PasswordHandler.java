package com.ebeijia.robot.core.util;

import org.springframework.stereotype.Component;
/**
 * 密码加密类
 * @author lijm
 *
 */
@Component
public class PasswordHandler {
	
	/**
	 * 密码加密
	 * @author lijm
	 * @param password
	 * @return
	 */
	public static String getPassword(String password){
		return Md5.getMD5ofStrByUpperCase("bankebeijia2016"+password);
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(getPassword("1001011"));
		
	}
}
