package com.ebeijia.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
	"yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimeString = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	
	public static String getTimeString(){
		return sdfTimeString.format(new Date());
	}
	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}
	/**
	 * 获取系统当前时间（秒） 
	 * @author lijm
	 * @return
	 */
	public static Timestamp getTimes() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String mystrdate = myFormat.format(calendar.getTime());
		return Timestamp.valueOf(mystrdate);
	}
	
	/**
	 * 两个日期之间相差的秒数
	 * @param startDate
	 * @return
	 */
	public static int calLastedTime(Date startDate) {
		  long a = new Date().getTime();
		  long b = startDate.getTime();
		  int c = (int)((a - b) / 1000);
		  return c;
	}
	 public static  int sec(Long date){
	        Date date1=new Date(date);
	        Date date2=new Date();
	        int sec=(int) ((date2.getTime()-date1.getTime())/1000);
	        return sec;
	    }
	/**
	 * 将日期格式转换成string类型
	 * 
	 * @param 2016-03-07
	 * @return
	 */
	public static String DateToStr(Date date,String fmt) {

		SimpleDateFormat format = new SimpleDateFormat(fmt);
		String str = format.format(date);
		return str;
	}
	 /**
     * 格式化日期
     * @param date
     * @param fmt
     * @return
     * @throws Exception
     */
    public static String formatDate(Date date, String fmt) throws Exception {
        if (date == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
        return myFormat.format(date);
    }

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    /**
     * 计算两个日期相差的秒数
     * @param date
     * @return
     */
    public static int sec(String data){
    	
    	Date date1=new Date(data);
        Date date2=new Date();
        int sec=(int) ((date2.getTime()-date1.getTime())/1000);
        return sec;
    }
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    
    /**
     * 获取当前时间的毫秒数
     * @return
     */
    public static long getCurrentDateTime(){
    	return new Date().getTime();
    }
    
    
    public static String formateStringDate(String strDate){
    	
    	try {
			return sdfTime.format(sdfTimeString.parse(strDate));
		} catch (ParseException e) {
			com.ebeijia.util.LoggerUtil.error(e.getMessage(), e);
			return strDate;
		}
    }

}
