package com.ebeijia.api.socket;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.UserBillAccountDto;
import com.ebeijia.util.MD5;
import com.ebeijia.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiYan on 2016/9/21.
 */
public class SocketUtil {
    public static final Logger logger = LoggerFactory.getLogger(SocketUtil.class);

    //报文编码
    private static final String encode = "UTF-8";

    //socket端口号
    private static final Integer portNo = Integer.parseInt(PropertiesUtils.getProperties("socketHost"));
    //private static final Integer portNo = Integer.parseInt("9020");

    public static String connect(byte[] data){
        Socket socket=null;
        DataInputStream dis=null;
        DataOutputStream dos=null;
        //socket地址
        InetAddress addr = null;
        try{
            addr=InetAddress.getByName(PropertiesUtils.getProperties("soceketAddress"));
            //addr=InetAddress.getByName("61.157.98.198");
        }catch (UnknownHostException e){}

        try {
            socket = new Socket(addr, portNo);
            socket.setSoTimeout(30 * 1000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            /*往服务端发送请求报文*/
            dos.write(data);
            dos.flush();
            socket.shutdownOutput();
            //报文体
            logger.info("请求报文体：:"+new String(data, encode));
            byte[] body = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len=dis.read(body)) != -1) {
                sb.append(new String(body, 0, len,"UTF-8"));
            }
            String str=sb.toString();
            logger.info("查询报文结果:"+str);

            return str;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        } finally {
            logger.info("socket通信结束");
            if (dis != null) {
                try{
                dis.close();}
                catch (IOException e){
                }
            }
            if (dos != null) {
                try{
                    dos.close();}
                catch (IOException e){
                }
            }
            if (socket != null) {
                try{
                    socket.close();}
                catch (IOException e){
                }
            }
        }
    }

    /**
     * 调用socket查询远传水表数据
     */
    public static Map<String, Object> queryRechargeBySocket(String accountNo) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        //终端代码
        String terminalCode = PropertiesUtils.getProperties("socket.terminalCode");
        //口令
        String command = PropertiesUtils.getProperties("socket.command");

        String condition = "1001|" + terminalCode + "|" + command + "|" + accountNo;
        String connect = SocketUtil.connect(condition.getBytes());

        if(StringUtils.isEmpty(connect)) {
            //调用socket错误
            resMap.put("code", "-1");
            return resMap;
        }
        String[] resData = connect.split("\\|");
        if(resData == null || resData.length == 0) {
            //查询出的数据为空
            resMap.put("code", "-1");
            return resMap;
        }

        //返回的状态码为1时，表示成功
        if(CommonConstant.SOC_RES_SUCC.equals(resData[0])) {
            if (CommonConstant.SOC_USERNO_FAIL.equals(resData[3])) {
                //账户号为空
                resMap.put("code", "-2");
                return resMap;
            } else {
                UserBillAccountDto dto = new UserBillAccountDto();
                dto.setUserNo(resData[1]);//resData[1] 户号
                dto.setUsername(resData[2]);//resData[2] 户名
                //resData[3] 用户状态
                dto.setBalance(resData[4]);//resData[4] 上次余额
                //resData[5] 用户地址
                //resData[6] 手机号
                dto.setStatementMonth(resData[7]);//resData[7] 结算月份
                dto.setTotalPowe(resData[8]);//resData[8] 用量
                dto.setPunishMoney(resData[9]);//resData[9] 违约金
                dto.setShouldMoney(resData[10]);//resData[10] 欠费金额
                if (resData.length > 11) {
                    dto.setEndNum(resData[11]);//resData[11] 终止表数
                    //resData[12] 起始月份
                    dto.setStartNum(resData[13]);//resData[13] 起始表数
                    //resData[14] 单价
                }
                resMap.put("data", dto);
                resMap.put("code", "0");
                resMap.put("name", resData[2]);
                return resMap;
            }
        }
        resMap.put("code", "-1");
        return resMap;

    }

    public static void main(String args[]){
        String data = "1001|1133|" + MD5.toMD5("").substring(0,20) + "|6400791";
        String a = connect(data.getBytes());
        System.out.println(a);
    }
}
