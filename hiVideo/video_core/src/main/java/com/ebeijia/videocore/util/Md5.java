package com.ebeijia.videocore.util;

import java.security.MessageDigest;

/**
 *
 */
public class Md5 {
	public static String md5(String str, boolean upper) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				if (upper) {
					buf.append(Integer.toHexString(i).toUpperCase());
				} else {
					buf.append(Integer.toHexString(i));
				}

			}
			str = buf.toString();
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
		return str;
	}
	public static String md5Upper(String data){
		return md5(data, true);
	}

}
