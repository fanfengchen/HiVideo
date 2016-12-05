package com.ebeijia.util;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title:CheckIdCard
 * </p>
 * <p>
 * Description:校验身份证
 */
public class CheckIdCardUtil {
      
    //  如：2的0次方除以11取模=1,2的1次方除以11取模=2,2的2次方除以11取模=4

    static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 校验位数组

    static String[] ai = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
    
    
    /**
     * 身份证校验
     * 
     * @param idCard
     * @return
     */
    public static String checkIdCard(String idCard){
        String[] errors={"身份证号码为空","身份证号码位数错误","身份证号码含有非法字符","身份证地区非法","身份证号码出生日期错误","身份证号码末位校验位错误"};
        
        //非空校验
        if(idCard==null || "".equals(idCard)){
            return errors[0];
        }
        
        //位数校验
        if(idCard.length()!=18 && idCard.length()!=15){
            return errors[1];
        }
        
        //非法字符校验
        int size=0;
        if(idCard.length()==18){
            size=17;
            if(idCard.charAt(17)!='X' && (idCard.charAt(17)<'0' || idCard.charAt(17)>'9')){
                return errors[2];
            }
        }else{
            size=15;
        }
        for(int i=0;i<size;i++){
            if(idCard.charAt(i)<'0' || idCard.charAt(i)>'9'){
                return errors[2];
            }
        }
        
        //身份证地区校验
        Map areaMap=getArea();
        if(areaMap.get(idCard.substring(0,2))==null){
            return errors[3];
        }
        
        //出生日期校验
        String birthDate="";
        if(idCard.length()==18){
            birthDate=idCard.substring(6,14);
        }else{
            birthDate="19"+idCard.substring(6,12);
        }
        
        if(!isDate2(birthDate)){
            return errors[4];
        }
        
        //身份证校验位校验
        if(idCard.length()==18){
            if(!checkLastNum(idCard)){
                return errors[5];
            }
        }
        return null;
    }
    
    /**
     * 18位身份证校验位校验
     * 
     * @param idCard
     * @return boolean
     */
    private static boolean checkLastNum(String idCard){
        
        String[] idCardArray=new String[idCard.length()];
        for(int i=0;i<idCard.length();i++){
            idCardArray[i]=String.valueOf(idCard.charAt(i));
        }
        
        int sum = (Integer.parseInt(idCardArray[0]) + Integer.parseInt(idCardArray[10])) * 7
                + (Integer.parseInt(idCardArray[1]) + Integer.parseInt(idCardArray[11])) * 9
                + (Integer.parseInt(idCardArray[2]) + Integer.parseInt(idCardArray[12])) * 10
                + (Integer.parseInt(idCardArray[3]) + Integer.parseInt(idCardArray[13])) * 5
                + (Integer.parseInt(idCardArray[4]) + Integer.parseInt(idCardArray[14])) * 8
                + (Integer.parseInt(idCardArray[5]) + Integer.parseInt(idCardArray[15])) * 4
                + (Integer.parseInt(idCardArray[6]) + Integer.parseInt(idCardArray[16])) * 2
                +  Integer.parseInt(idCardArray[7]) * 1 
                +  Integer.parseInt(idCardArray[8]) * 6
                +  Integer.parseInt(idCardArray[9]) * 3 ;
        
        int value=sum % 11;
        String checkStr="10X98765432";
        String checkNum=checkStr.substring(value,value+1);
        
        if(!checkNum.equals(idCardArray[17])){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 日期校验
     * 
     * @param String
     * @return boolean
     * Description: 仅校验1900--2099年之间的日期,校验的日期格式为“yyyyMMdd”
     */
    public static boolean isDate2(String date){
        
        if(date==null || "".equals(date)){
            return false;
        }
        
        if(date.length()!=8){
            return false;
        }
        
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)<'0' || date.charAt(i)>'9'){
                return false;
            }
        }
        
        String year=date.substring(0,4);
        String ruleStr="";
        if(Integer.parseInt(year) % 4 ==0 || (Integer.parseInt(year) % 100 ==0 && Integer.parseInt(year) % 4 ==0)){
            //闰年
            ruleStr="^(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))";
        }else{
            //平年
            ruleStr="^(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))";
        }
        
        Pattern p=Pattern.compile(ruleStr);
        Matcher m=p.matcher(date);
        boolean b=m.matches();
        
        if(!b){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 日期校验
     * 
     * @param String
     * @return boolean
     * Description: 可校验所有年份日期,校验的日期格式为“yyyyMMdd”
     */
    public static boolean isDate(String date){
        
        if(date==null || "".equals(date)){
            return false;
        }
        
        if(date.length()!=8){
            return false;
        }
        
        for(int i=0;i<date.length();i++){
            if(date.charAt(i)<'0' || date.charAt(i)>'9'){
                return false;
            }
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date parsedDate = formatter.parse(date, new ParsePosition(0));
        String newDate = formatter.format(new Date(parsedDate.getTime()));
        // 当输入诸如“20079999”这样的非日期字符串时,Date也能将其转换成正确的日期，因此要判断一下前后两字符串是否相同
        if (date.equals(newDate)) {
           return true;
        } else {
           return false;
        }
    }
    
    /**
     * 获取地区代码
     * 
     * @return map
     */
    private static Map getArea(){
        Map areaMap=new HashMap();
        areaMap.put("11", "北京");
        areaMap.put("12", "天津");
        areaMap.put("13", "河北");
        areaMap.put("14", "山西");
        areaMap.put("15", "内蒙古");
        areaMap.put("21", "辽宁");
        areaMap.put("22", "吉林");
        areaMap.put("23", "黑龙江");
        areaMap.put("31", "上海");
        areaMap.put("32", "江苏");
        areaMap.put("33", "浙江");
        areaMap.put("34", "安徽");
        areaMap.put("35", "福建");
        areaMap.put("36", "江西");
        areaMap.put("37", "山东");
        areaMap.put("41", "河南");
        areaMap.put("42", "湖北");
        areaMap.put("43", "湖南");
        areaMap.put("44", "广东");
        areaMap.put("45", "广西");
        areaMap.put("46", "海南");
        areaMap.put("50", "重庆");
        areaMap.put("51", "四川");
        areaMap.put("52", "贵州");
        areaMap.put("53", "云南");
        areaMap.put("54", "西藏");
        areaMap.put("61", "陕西");
        areaMap.put("62", "甘肃");
        areaMap.put("63", "青海");
        areaMap.put("64", "宁夏");
        areaMap.put("65", "新疆");
        areaMap.put("71", "台湾");
        areaMap.put("81", "香港");
        areaMap.put("82", "澳门");
        areaMap.put("91", "国外");
        return areaMap;
    }
    
    public static String to18IDcard(String lowerIdNo) {

        if (null!= lowerIdNo && lowerIdNo.length() == 15) {
            StringBuffer bf = new StringBuffer();
            bf.append(lowerIdNo.substring(0, 6));
            bf.append("19");
            bf.append(lowerIdNo.substring(6));
            int mod = checkBit(bf.toString());
            bf.append(ai[mod]);
            return bf.toString();
        }else{
            return lowerIdNo;
        }

    }
    
    public static String to15IDcard(String higherIdNo) {

        if (null!= higherIdNo && higherIdNo.length() == 18) {
            StringBuffer bf = new StringBuffer();
            bf.append(higherIdNo.substring(0, 6));           
            bf.append(higherIdNo.substring(8,17));
            return bf.toString();
        }else{
            return higherIdNo;
        }

    }
    
    public static int checkBit(String lowerId) {

        int sum = 0;
        // 计算校验位，前 17位加权求和，然后除以11取模
        for (int i = 1; i < lowerId.length() + 1; i++) {
            sum = sum + wi[i - 1]
                    * (Integer.parseInt(lowerId.substring(i - 1, i)));
        }
        int mod = sum % 11;
        return mod;
    }


    /**
     * 测试入口
     */
    public static void main(String[] args) {
        System.out.println(to15IDcard("320303198103010620"));
       /* String message=checkIdCard("111111111111111111");
        System.out.println(message);*/
    }
    
}
