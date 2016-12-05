package com.ebeijia.util.common;

import com.ebeijia.util.core.PatternUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * 公用类
 * @author laiwen.liu
 *
 */
public class CommonUtils {

	private SimpleDateFormat showDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sysDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private SimpleDateFormat sysTimeFormat = new SimpleDateFormat("HHmmss");
	private SimpleDateFormat sysDateFormat8 = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat showDateFormatZHCN = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");

	/**
	 * 获得系统当前日期时间
	 * 
	 * @return
	 */
	public String getCurrentDateTime() {
		return sysDateFormat.format(new Date());
	}
	/**
	 * 获得系统当前日期后一个月 日期时间
	 * @return
	 */
	public String getBehindDateTime(){
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH,1); //将当前日期加一个月
		return sysDateFormat.format(c.getTime());
	}
	
	/**
	 * 获得系统当前时间
	 * 
	 * @return
	 */
	public String getCurrentTime() {
		return sysTimeFormat.format(new Date());
	}

	/**
	 * 获得系统当前日期
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		return sysDateFormat8.format(new Date());
	}

	/**
	 * 获得向前系统时间用于显示
	 * 
	 * @return
	 */
	public String getCurrentDateTimeForShow() {
		return showDateFormat.format(new Date());
	}

	/**
	 * 获得中文系统时间
	 * 
	 * @return
	 */
	public String getCurrentDateTimeZHCN() {
		return showDateFormatZHCN.format(new Date());
	}

	/**
	 * 填补字符串
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public String fillString(String str, char fill, int len, boolean isEnd) {
		int fillLen = len - str.getBytes().length;
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 填补字符串(中文字符扩充)
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public String fillStringForChinese(String str, char fill, int len,
			boolean isEnd) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			Matcher m = PatternUtil.CHINESE_REGEX.matcher(str.substring(i, i + 1));
			if (m.find()) {
				num++;
			}
		}
		int fillLen = len - (str.length() + num);
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 获得指定日期的偏移日期
	 * 
	 * @param refDate
	 *            参照日期
	 * @param offSize
	 *            偏移日期
	 * @return
	 */
	public String getOffSizeDate(String refDate, String offSize) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Integer.parseInt(refDate.substring(0, 4)), Integer
				.parseInt(refDate.substring(4, 6)) - 1, Integer
				.parseInt(refDate.substring(6, 8)));
		calendar.add(Calendar.DATE, Integer.parseInt(offSize));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String retDate = String.valueOf(calendar.get(Calendar.DATE));
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if (Integer.parseInt(retDate) < 10) {
			retDate = "0" + retDate;
		}
		return year + month + retDate;
	}

	/**
	 * 将金额元转分
	 * 
	 * @param str
	 * @return
	 */
	public String transYuanToFen(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointRight(2).toString();
	}

	/**
	 * 将金额分转元
	 * 
	 * @param str
	 * @return
	 */
	public String transFenToYuan(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointLeft(2).toString();
	}

	/**
	 * 获得指定个数的随机数组合
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public String getRandomNum(int len) {
		String ran = "";
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			ran += String.valueOf(random.nextInt(10));
		}
		return ran;
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public boolean isMoney(String str) {
		String reg = "\\d+(\\.\\d+)?";
		return str.matches(reg);
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public boolean isAllDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public Date getCurrentTs() {
		Date now = new Date();
		return new Timestamp(now.getTime());
	}

	/**
	 * 验证字符串是否符合 金额 模式
	 * @param str
	 * @return
	 */
	public boolean isAmount(String str) {
		Matcher match = PatternUtil.AMOUNT_REGEX.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	
	

	/**
	 * 
	 * @param str
	 * @return
	 */
	public String formate8Date(String str) {
		if (str.length() == 8) {
			return str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
					+ str.substring(6, 8);
		}
		return str;
	}

	public String getCurrDate(String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date());
	}

	/**
	 * 16进制
	 * 
	 * @param c
	 * @return 2011-7-27上午11:50:28
	 */
	private byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	/**
	 * 16进制转BCD
	 * 
	 * @param hex
	 * @return 2011-7-27上午11:49:20
	 */
	public byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * BCD转成16进制
	 * 
	 * @param bArray
	 * @return 2011-7-27上午11:47:56
	 */
	public String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public String urlToRoleId(String url) {
		try {
			String[] array = url.split("/");
			String[] result = array[array.length - 1].split("\\.");
			String res = result[0];
			return res;
		} catch (Exception e) {
			return url;
		}
	}

	public String transMoney(double n) {
		try {
			String[] fraction = { "角", "分" };
			String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			String[][] unit = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

			String head = n < 0 ? "负" : "";
			n = Math.abs(n);

			String s = "";

			for (int i = 0; i < fraction.length; i++) {
				s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
						.replaceAll("(零.)+", "");
			}
			if (s.length() < 1) {
				s = "整";
			}
			int integerPart = (int) Math.floor(n);

			for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
				String p = "";
				for (int j = 0; j < unit[1].length && n > 0; j++) {
					p = digit[integerPart % 10] + unit[1][j] + p;
					integerPart = integerPart / 10;
				}
				s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零")
						+ unit[0][i] + s;
			}
			return head
					+ s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "")
							.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String insertString(String src, String fill) {
		String tmp = "";
		for (int i = 0; i < src.length(); i++) {
			tmp += fill;
			tmp += src.substring(i, i + 1);
		}
		return tmp;
	}

	/**
	 * 判断 id 是否在 list中
	 * @param id
	 * @param list
	 * @return
	 */
	public boolean inList(String id,List<String> list){
		Boolean flag = false;
		for(String value : list){
			if(id.equals(value)){
				flag = true;
				continue;
			}
		}
		return flag;
	}
	
}
