package com.ebeijia.api.socket;

import com.ebeijia.dto.socketModels.WaterPayReq;
import com.ebeijia.dto.socketModels.WaterPayRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by LiYan on 2016/9/26.
 * 远端表交水费接口
 */
public class WaterFarPayApi {
    private static Logger logger = LoggerFactory.getLogger(WaterFarPayApi.class);

    //报文编码
    private static final String encode = "UTF-8";

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

    public static WaterPayRes payWaterBill(WaterPayReq req){
        logger.info("调用智能表交远端水费");
        byte[] data =join(
                getStrByte((req.getFunctionCode() + "|")),
                getStrByte((req.getTerminalCode()+ "|")),
                getStrByte((req.getCommand() + "|")),
                getStrByte((req.getUserNo())+ "|"),
                getStrByte((req.getThridPayment() + "|")),
                getStrByte((req.getAmt()))
        );
        WaterPayRes res=analyzePayBody(SocketUtil.connect(data));
        return res;
    }

    /**
     *返回报文处理 转成bean接收
     * @param body
     * @return
     */
    private static WaterPayRes analyzePayBody(String body) {
        try {
            if(null!=body){
                String[] datas = body.split("\\|");
                String resCode = datas[0];
                if ("1".equals(resCode)) {
                    WaterPayRes res=new WaterPayRes();
                    res.setResCode(resCode);
                    res.setCopyPayment(datas[1]);
                    return res;
                } else {
                    logger.error("调用查询接口失败，失败原因：" + resCode);
                    WaterPayRes res=new WaterPayRes();
                    res.setResCode(resCode);
                    return res;
                }
            }else{
                logger.error("socket调用异常" );
                return null;
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }


}
