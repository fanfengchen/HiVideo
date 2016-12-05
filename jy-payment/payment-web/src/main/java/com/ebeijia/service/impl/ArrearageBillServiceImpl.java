package com.ebeijia.service.impl;

import com.ebeijia.api.socket.SocketUtil;
import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.UserBillAccountDto;
import com.ebeijia.dto.webService.QueryUser;
import com.ebeijia.dto.webService.QueryUserDto;
import com.ebeijia.dto.webService.QueryUserInfo;
import com.ebeijia.dto.webService.UserInfo;
import com.ebeijia.entity.UserBilAccount;
import com.ebeijia.mapper.UserBilAccountMapper;
import com.ebeijia.service.ArrearageBillService;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.XmlUtils;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import com.ebeijia.wsdl.query.YxWebServiceLocator;
import com.ebeijia.wsdl.query.YxWebServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.rpc.holders.StringHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with com.ebeijia.service.impl
 * User : zc
 * Date : 2016/9/28
 */
@Service
public class ArrearageBillServiceImpl implements ArrearageBillService {

    private static Logger log = LoggerFactory.getLogger(ArrearageBillServiceImpl.class);

    @Autowired
    private UserBilAccountMapper userBilAccountMapper;

    /**
     * 查询自来水表数据
     *
     * SuppressWarnings("all") 是抑制提醒 Finds duplicated code
     */
    @SuppressWarnings("all")
    public Map<String, Object> queryWaterBill(String userNo, String openId) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(openId)) {
            //传入的数据缺失
            resMap.put("code", CommonConstant.arreaImplResConst_par_err);
            return resMap;
        }
        String type ="01";

        Map<String, Object> mapData = this.queryByWebService(userNo,type);
        String resCode = (String)mapData.get("code");
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)) {
            //输入的户号不存在
            resMap.put("code", CommonConstant.arreaImplResConst_res_null);
            return resMap;
        }
        if(CommonConstant.arreaWebService_err.equals(resCode)) {
            //调用webservice失败
            resMap.put("code", CommonConstant.arreaImplResConst_fail);
            return resMap;
        }
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)){
            resMap.put("data", mapData.get("data"));
            UserInfo userInfo = (UserInfo) mapData.get("userInfo");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",openId);
            paramMap.put("userNo",userNo);
            paramMap.put("accountStatus","1");
            UserBilAccount userBilAccount =  userBilAccountMapper.selectByParams(paramMap);
            if(null!=userBilAccount){
                userInfo.setRemark(userBilAccount.getRemark());
            }
            resMap.put("userInfo",userInfo );
            resMap.put("code", CommonConstant.arreaImplResConst_succ);
            return resMap;
        }
        resMap.put("code", "");
        return resMap;
    }

    /**
     * 查询燃气数据
     *
     * SuppressWarnings("all") 是抑制提醒 Finds duplicated code
     */
    @SuppressWarnings("all")
    public Map<String, Object> queryGasBill(String userNo, String openId) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(openId)) {
            //输入的参数缺失
            resMap.put("code", CommonConstant.arreaImplResConst_par_err);
            return resMap;
        }
        String type = "03";
        Map<String, Object> mapData = this.queryByWebService(userNo,type);
        String resCode = (String)mapData.get("code");
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)) {
            //输入的户号不存在
            resMap.put("code", CommonConstant.arreaImplResConst_res_null);
            return resMap;
        }
        if(CommonConstant.arreaWebService_err.equals(resCode)) {
            //调用webservice失败
            resMap.put("code", CommonConstant.arreaImplResConst_fail);
            return resMap;
        }
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)){
            resMap.put("data", mapData.get("data"));
            UserInfo userInfo = (UserInfo) mapData.get("userInfo");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",openId);
            paramMap.put("userNo",userNo);
            paramMap.put("accountStatus","1");
            UserBilAccount userBilAccount =  userBilAccountMapper.selectByParams(paramMap);
            if(null!=userBilAccount){
                userInfo.setRemark(userBilAccount.getRemark());
            }
            resMap.put("userInfo",userInfo );
            resMap.put("code", CommonConstant.arreaImplResConst_succ);
            return resMap;
        }
        resMap.put("code", "");
        return resMap;
    }

    /**
     * 查询远传水表数据
     */
    public Map<String, Object> querRechargeBill(String userNo, String openId) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(openId)) {
            //输入的参数缺失
            resMap.put("code", CommonConstant.arreaImplResConst_par_err);
            return resMap;
        }

        Map<String, Object> resData = this.queryRechargeBySocket(userNo);
        String resCode = (String)resData.get("code");
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)) {
            resMap.put("code", CommonConstant.arreaImplResConst_succ);

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",openId);
            paramMap.put("userNo",userNo);
            paramMap.put("accountStatus","1");
            UserBilAccount userBilAccount =  userBilAccountMapper.selectByParams(paramMap);
            UserInfo userInfo = new UserInfo();
            if(null!=userBilAccount){
                userInfo.setRemark(userBilAccount.getRemark());
            }
            resMap.put("userInfo",userInfo );
            resMap.put("data", resData.get("data"));
            return resMap;
        }
        if(CommonConstant.arreaWebService_err.equals(resCode)) {
            //调用socket失败
            resMap.put("code", CommonConstant.arreaImplResConst_res_null);
            return resMap;
        }
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)){
            //输入的账号不存在
            resMap.put("code", CommonConstant.arreaImplResConst_fail);
            return resMap;
        }
        //表示用户不需要缴费
        if(CommonConstant.arreaImplResConst_balance.equals(resCode)){
            resMap.put("code", CommonConstant.arreaImplResConst_balance);
            return resMap;
        }
        resMap.put("code", "");
        return resMap;
    }

    /**
     * 调用socket查询远传水表数据
     */
    private Map<String, Object> queryRechargeBySocket(String accountNo) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        //终端代码
        String terminalCode = PropertiesUtils.getProperties("socket.terminalCode");
        //口令
        String command = PropertiesUtils.getProperties("socket.command");
        //String terminalCode = "1133";
        //String command = MD5.toMD5("").substring(0, 20);
        String condition = "1001|" + terminalCode + "|" + command + "|" + accountNo;

        //log.info("socket查询userNo : " + accountNo);

        String connect = SocketUtil.connect(condition.getBytes());

        //log.info("socket返回信息 : " + connect);

        if(StringUtils.isEmpty(connect)) {
            //调用socket错误
            resMap.put("code", CommonConstant.arreaWebService_err);
            return resMap;
        }
        String[] resData = connect.split("\\|");
        if(resData == null || resData.length == 0) {
            //查询出的数据为空
            resMap.put("code", CommonConstant.arreaWebService_err);
            return resMap;
        }

        //返回的状态码为1时，表示成功
        if(CommonConstant.SOC_RES_SUCC.equals(resData[0])) {
            if (CommonConstant.socResUserNoErr.equals(resData[3])) {
                //账户号为空
                resMap.put("code", CommonConstant.arreaImplResConst_res_null);
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
                resMap.put("code", CommonConstant.arreaImplResConst_succ);
                return resMap;
            }
        }else if(CommonConstant.socResUserNoErr.equals(resData[0])){
            //输入的账号不存在
            resMap.put("code", CommonConstant.arreaImplResConst_res_null);
            return resMap;
        }else if(CommonConstant.socResBillNoArre.equals(resData[0])){
            //账号不欠费不需要缴费
            resMap.put("code", CommonConstant.arreaImplResConst_balance);
            return resMap;
        }
        resMap.put("code", CommonConstant.arreaWebService_err);
        return resMap;

    }

    /**
     * 调用webservice，
     */
    private Map<String, Object> queryByWebService(String accountNo,String type) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            log.info("webService查询userNo : " + accountNo);
            WsServiceServlet e = (new WsServiceServletServiceLocator()).getyxws();
            String str = e.interfaceQueryUser(accountNo);
            log.info("webService返回信息 ： " + str);
            QueryUserDto queryUserDto = (QueryUserDto) XmlUtils.toBean(str, QueryUserDto.class);
            List<QueryUser> list = queryUserDto.getResult();
            if(list == null || list.size() == 0) {
                resMap.put("code", CommonConstant.arreaImplResConst_res_null);
                return resMap;
            }
            log.info("查询用户详细信息{}",accountNo);
            YxWebServiceLocator locator=new YxWebServiceLocator();
            YxWebServiceSoap soap=locator.getYxWebServiceSoap12();
            StringHolder stringHolder = new StringHolder();
            String resStr = null;
            if(type.equals("01")){
                resStr = soap.findWaterUserInfo(accountNo,stringHolder);
            }else if(type.equals("03")){
                resStr = soap.findGasUserInfo(accountNo,stringHolder);
            }
            log.info("用户信息返回数据{}{}",resStr,stringHolder.value);
            QueryUserInfo queryUserInfo = XmlUtils.toBean(stringHolder.value,QueryUserInfo.class);
            List<UserBillAccountDto> resList = new ArrayList<UserBillAccountDto>();
            for(QueryUser q : list){
                UserBillAccountDto dto = new UserBillAccountDto();

                dto.setUserNo(q.getUserNo()); //用户编号
                //dto.setUsername(""); //用户名称
                dto.setStatementMonth(q.getMon());//结算月份
                dto.setStartNum(q.getStartNum());// 起码
                dto.setEndNum(q.getEndNum());// 止码
                dto.setTotalPowe(q.getTotalPower());// 用量
                dto.setShouldMoney(q.getShouldMoney());// 欠费金额
                dto.setPunishMoney(q.getPunishMoney());// 违约金
                dto.setMoney(q.getMoney());// 合计欠费金额
                //dto.setBalance("");// 上次余额

                resList.add(dto);
            }

            resMap.put("data", resList);
            resMap.put("userInfo",queryUserInfo.getUserInfo());
            resMap.put("code", CommonConstant.arreaImplResConst_succ);
            return resMap;
        } catch (Exception e) {
            log.error("调用webservice错误" , e);
            resMap.put("code", CommonConstant.arreaWebService_err);
            return resMap;
        }
    }

}
