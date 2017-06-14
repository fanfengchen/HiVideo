package com.ebeijia.module.wechat.service;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by ld on 2015/11/13.
 *
 */
public class CoreUtfm {

    /***
     * Java过滤掉非UTF-8字符方法
     * 例如：Emoji表情是4个字节，而Mysql的utf8编码最多3个字节
     * */
    public  String filterOffUtf8Mb4(String str)
    {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < str.length(); i++)
        {
            int c = str.codePointAt(i);
            //判断字符串中是否有某个字符是四个字节的
            if (c < 0x0000 || c > 0xffff)
            {
                // do nothing
                sb.append(filterStr(str.substring(i,i+1).toString()));
            }
            else
            {
                sb.append(Character.toChars(c));
            }
        }

        return sb.toString();
    }

    /**
     * 表情字符转
     * */
    public String filterStr(String text)  {

        try {
            byte[] bytes = text.getBytes("UTF-8");
             ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                }

            }
            buffer.flip();
           // System.out.println("=================="+buffer.array());
            return new String(buffer.array(), "utf-8");
        }catch (UnsupportedEncodingException e){
            System.out.println("Java过滤掉非UTF-8字符方法出错"+e);
        }
            return null;
    }


    /**
     * 如果有字符串的某一个字符是四个字节的就替换成*
     * */
    public  String filterString(String str)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            int c = str.codePointAt(i);
            if (c < 0x0000 || c > 0xffff)
            {
                // do nothing
                sb.append("*");
            }
            else
            {
                sb.append(Character.toChars(c));
            }
        }
        return sb.toString();
    }




    /**解析 建周短信平台接口的获取余额等用户基本信息
     * 获取短信剩余次数
     * */
    public static String remainFeeParseXML(String url){
        String remainFee=null;
        try {
          //  String url="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<userinfo><usertype>2</usertype><vip>190</vip><userId>4753</userId><mmsrate>6</mmsrate><remainFee>48</remainFee><Channels><Channel><id>14</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel><Channel><id>7</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel><Channel><id>9</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel></Channels></userinfo>";

            Document dom = DocumentHelper.parseText(url);
            Element root = dom.getRootElement();
            //剩余短信次数
             remainFee = root.element("remainFee").getText();


        }catch (Exception e)   {
            e.printStackTrace();
            System.out.println("获取剩余短信次数出错"+e);
        }
    return remainFee ;

}


    public static void main(String[] args) {

       String url="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<userinfo><usertype>2</usertype><vip>190</vip><userId>4753</userId><mmsrate>6</mmsrate><remainFee>48</remainFee><Channels><Channel><id>14</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel><Channel><id>7</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel><Channel><id>9</id><name>test</name><MsgCount>70</MsgCount><MsgCount_l>65</MsgCount_l></Channel></Channels></userinfo>";
       String a= CoreUtfm.remainFeeParseXML(url);
        System.out.println("===="+a);
//subTime = 1442992028--DateTime4J.timestampFormat(subTime):19700118004952
           /* CoreUtfm c = new CoreUtfm();
           String a= c.filterOffUtf8Mb4("сатжан");
            System.out.println("===="+a);*/
        //import com.ebeijia.util.Sha1Util; import org.ebeijia.tools.DateTime4J;
          //  System.out.println(DateTime4J.timestampFormat(1442992028));
       // System.out.println( Sha1Util.getTimeStamp());
    }




}
