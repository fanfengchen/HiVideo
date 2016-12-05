package com.ebeijia.api.socket;

import com.ebeijia.dto.socketModels.OverdueQueryReq;
import com.ebeijia.dto.socketModels.OverdueQueryRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * Created by LiYan on 2016/9/21.
 */
public class WaterPayFarQueryApi {

    private static Logger logger = LoggerFactory.getLogger(WaterPayFarQueryApi.class);

    //报文编码
    private static final String encode = "utf-8";

    //拼接发送报文
    private static byte[] join(byte[] ... bytes) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try{
            for (byte[] b : bytes) {
                bs.write(b);
            }
        }catch (IOException e){
            logger.info("IOException");
        }

        return bs.toByteArray();
    }

    public static byte[] getStrByte(String str){
        byte[] b=null;
        try{
            b= str.getBytes(encode);
        }catch (UnsupportedEncodingException e){
        }
        return  b;
    }

    //返回的报文截取两位小数
    private static String getTwoNum(String date){
        try{
            String[] a=date.split(".");
            String b=a[1].substring(2);
            String c=a[0]+"."+b;
            return c;
        }catch (Exception e){
            logger.error("报文截取错误");
            return null;
        }

    }

    /**
     * 发送socket连接获取返回信息
     * @param req
     * @return
     */
    public static OverdueQueryRes queryOverdue(OverdueQueryReq req){
        logger.info("开始查询欠费信息");

        byte[] data =join(
                getStrByte((req.getFunctionCode() + "|")),
                getStrByte((req.getTerminalCode()+ "|")),
                getStrByte((req.getCommand() + "|")),
                getStrByte((req.getUserNo()))
        );

        OverdueQueryRes res=analyzePayBody(SocketUtil.connect(data));
        return  res;
    }

    /**
     *返回报文处理 转成bean接收
     * @param body
     * @return
     */
    private static OverdueQueryRes analyzePayBody(String body) {
        try {
            if(null!=body){
                String[] datas = body.split("\\|");
                String resCode = datas[0];
                if ("1".equals(resCode)) {
                    OverdueQueryRes res = new OverdueQueryRes();
                    res.setResCode(resCode);
                    res.setUserNo(datas[1]);
                    res.setUsername(datas[2]);
                    res.setUserStatus(datas[3]);
                    res.setBalance(new BigDecimal(datas[4]).setScale(2,BigDecimal.ROUND_HALF_DOWN));
                    res.setAddress(datas[5]);
                    res.setPhoneNo(datas[6]);
                    res.setStatementMonth(datas[7]);
                    res.setPower(datas[8]);
                    res.setPunishMoney(new BigDecimal(datas[9]).setScale(2,BigDecimal.ROUND_HALF_DOWN));
                    res.setShouldMoney(new BigDecimal(datas[10]).setScale(2,BigDecimal.ROUND_HALF_DOWN));
                    res.setStartNum(Integer.parseInt(datas[11]));
                    if(!datas[12].equals("")){
                        res.setEndNum(Integer.parseInt(datas[12]));
                    }
                    res.setStartMonth(datas[13]);
                    res.setUnitPrice(new BigDecimal(datas[14]).setScale(2,BigDecimal.ROUND_HALF_DOWN));
                    return res;
                } else {
                    logger.error("查询接口失败，失败原因：" + resCode);
                    OverdueQueryRes res = new OverdueQueryRes();
                    res.setResCode(resCode);
                    return res;
                }
            }else{
                logger.error("socket调用异常" );
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
