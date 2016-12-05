package com.ebeijia.util;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 */
public class CommonCheckUtil {
	/**
	 * 18位身份证正则表达式
	 */
	private static final String ID18_REG = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

	/**
	 * 15位身份证正则表达式
	 */
	private static final String ID15_REG = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	private static boolean checkIdFormat(String idCard, int len) {
		String reg = null;
		if (len == 18) {
			reg = ID18_REG;
		} else {
			reg = ID15_REG;
		}
		return idCard.matches(reg);
	}
	public static String gbs_checkIdNo(String idCard) {
		String tmpIdCard = idCard;
		// 非空校验
		if (StringUtils.isEmpty(tmpIdCard)) {
			return "身份证不能为空;";
		} else {
			int len = tmpIdCard.length();
			if (len != 18 && len != 15) {
				return "身份证号码为15位或18位;";
			} else {
				if (!checkIdFormat(tmpIdCard, len)) {
					return "身份证格式错误;";
				}
			}
		}
		return null;
	}
	
	
	
	//中文
	private static final String CHINESE = "[\u0391-\uFFE5]";
	/**
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 * @since %I%
	 */
	public static int strLength(String value) {
		int valueLength = 0;
		for (int i = 0; i < value.length(); i++) {
			// 以字符串形式返回一个字符
			String ch = value.substring(i, i + 1);
			if (ch.matches(CHINESE)) { // 中文
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	

	/**
	 * 验证电话号码
	 * 
	 * @param areaCode
	 *            区号
	 * @param phone
	 *            电话号码
	 * @param ext
	 *            分机号
	 * @return
	 */
	public static String checkPhone(String areaCode, String phone,
			String ext) {
		if (StringUtils.isEmpty(phone)) {
			return "电话号码不能为空;";
		}
		if (!StringUtils.isNumeric(phone)) {
			return "电话号码必须为数字;";
		}
		if (!phone.startsWith("400") && !phone.startsWith("800")) {
			if (phone.length() != 7 && phone.length() != 8) {
				return "电话号码必须为7或8位;";
			}
			if (StringUtils.isEmpty(areaCode)) {
				return "区号不能为空;";
			}
			if (areaCode.length() != 3 && areaCode.length() != 4) {
				return "区号必须为3-4位数字;";
			}
			if (!StringUtils.isNumeric(areaCode)) {
				return "区号必须为数字;";
			}
			if (areaCode.charAt(0) != '0') {
				return "区号必须以0开头;";
			}
		} else {
			if (phone.length() != 10) {
				return "400/800开头的工作电话必须为10位;";
			}

		}
		if (ext != null && !"".equals(ext)) {
			if (!StringUtils.isNumeric(ext)) {
				return "分机号必须为数字;";
			}
			int len = ext.length();
			if (len < 1 || len > 8) {
				return "分机号为1-8位数字;";
			}
		}
		return null;
	}

	public static String checkMobilePhone(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return "手机号码不能为空;";
		}
		if (!StringUtils.isNumeric(mobile)) {
			return "手机号码必须为数字;";
		}
		if (mobile.length() != 11) {
			return "手机号码必须为11位数字;";
		}
		if (!mobile.startsWith("13") && !mobile.startsWith("14")
				&& !mobile.startsWith("15") && !mobile.startsWith("18") 
				&& !mobile.startsWith("17")) {
			return "手机号必须以13、14、15、17或18开头;";
		}
		return null;
	}

	

	public static String checkEmail(String email) {
		if (!email.matches("^[a-zA-Z0-9-_.@]+$") || email.endsWith(".")
				|| email.startsWith(".") || email.endsWith("@")
				|| email.startsWith("@") || email.indexOf(".@") > 0
				|| email.indexOf("@.") > 0) {
			return "请输入正确的电子邮件地址";
		}
		if (email.indexOf('@') < 0 || email.indexOf('.') < 0) {
			return "电子邮件地址必须有@和.";
		}
		if (email.replaceFirst("@", "").indexOf('@') >= 0) {
			return "电子邮件地址只能有一个@";
		}
		return null;
	}

}
