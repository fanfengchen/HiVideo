package com.ebeijia.service.impl;

import com.ebeijia.api.socket.SocketUtil;
import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.UserBillAccountDto;
import com.ebeijia.dto.webService.QueryUser;
import com.ebeijia.dto.webService.QueryUserDto;
import com.ebeijia.entity.UserBilAccount;
import com.ebeijia.mapper.UserBilAccountMapper;
import com.ebeijia.service.UserBillAccountService;
import com.ebeijia.util.XmlUtils;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by LiYan on 2016/9/23.
 */
@Service
public class UserBillAccountServiceImpl implements UserBillAccountService {
    private static Logger log = LoggerFactory.getLogger(ArrearageBillServiceImpl.class);

    @Autowired
    private UserBilAccountMapper userBilAccountMapper;

    @Override
    @Transactional
    public int addUserBillAccount(UserBilAccount userBilAccount) {
        return userBilAccountMapper.insert(userBilAccount);
    }

    @Override
    public UserBilAccount findByUserNoAndOpenid(Map<String,Object> params) {
        return userBilAccountMapper.selectByParams(params);
    }

    @Override
    public UserBilAccount findByUserId(Map<String,Object> params) {
        return userBilAccountMapper.selectByParams(params);
    }

    @Override
    @Transactional
    public Map<String, Object> addUserAccount(String openId, String userNo, String type, String tipsArrears, String isRecord) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(openId)) {
            //传入的数据缺失
            resMap.put("code", "-1");
            return resMap;
        }

        Integer id = 0;
        if("1".equals(isRecord)) {
            //表示保存账户数据
            id = this.insertUserBillAccount(userNo, openId, tipsArrears, type);
        }

        if(CommonConstant.WATER_FAY_PAY.equals(type)){
            Map<String, Object> resData = SocketUtil.queryRechargeBySocket(userNo);
            String resCode = (String)resData.get("code");
            if("0".equals(resCode)) {
                //修改数据
                UserBilAccount userBill = new UserBilAccount();
                userBill.setId(id.longValue());
                userBill.setUserName((String)resData.get("name"));
                userBilAccountMapper.updateByPrimaryKeySelective(userBill);
                resMap.put("code", "0");
                resMap.put("data", resData.get("data"));
                return resMap;
            }
            if("-1".equals(resCode)) {
                resMap.put("code", "-3");
                return resMap;
            }
            if("-2".equals(resCode)){
                resMap.put("code", "-2");
                return resMap;
            }
            if("-3".equals(resCode)){
                resMap.put("code", "-1");
                return resMap;
            }
            resMap.put("code", "");
            return resMap;
        }else if(CommonConstant.WATER_PAY.equals(type) || CommonConstant.GAS_PAY.equals(type)){
            Map<String, Object> mapData = this.queryByWebService(userNo);
            String resCode = (String)mapData.get("code");
            if("-2".equals(resCode)) {
                //输入的户号不存在
                resMap.put("code", "-2");
                return resMap;
            }
            if("-1".equals(resCode)) {
                //调用webservice失败
                resMap.put("code", "-3");
                return resMap;
            }
            if("0".equals(resCode)){
            /*//修改数据
            UserBilAccount userBill = new UserBilAccount();
            userBill.setId(id.longValue());
            userBill.setAccountNo((String)mapData.get("no"));
            userBilAccountMapper.updateByPrimaryKeySelective(userBill);*/
                resMap.put("data", mapData.get("data"));
                resMap.put("code", "0");
                return resMap;
            }
            resMap.put("code", "");
            return resMap;
        }
        return resMap;
    }

    @Override
    public List<UserBilAccount> selectByTips(String tips) {
        return userBilAccountMapper.selectByTips(tips);
    }

    @Override
    public List<UserBilAccount> selectByUserNo(String userNo) {
        return userBilAccountMapper.selectByUserNo(userNo);
    }

    /**
     * 根据accountNo户号,openId已经保存过
     * 如果已经添加过就不再添加，只是调接口查询数据
     */
    private UserBilAccount queryUserBillAccountByAccountNo(String accountNo,String openId,String accountStatus){
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userNo", accountNo);
        condition.put("userId", openId);
        condition.put("accountStatus", accountStatus);
        return userBilAccountMapper.selectByParams(condition);
    }

    /**
     *插入账户信息数据
     */
    private int insertUserBillAccount(String userNo, String openId,
                                      String tipsArrears, String type) {

        UserBilAccount userTemp = queryUserBillAccountByAccountNo(userNo,openId,"1");

        if(userTemp != null){
            //表示数据已经添加过了
            return 0;
        }

        UserBilAccount user = new UserBilAccount();
        user.setUserNo(userNo);
        user.setAccountType(type);
        user.setAccountStatus("1");
        user.setTipsArrears(StringUtils.isEmpty(tipsArrears)?"0":tipsArrears);
        user.setUserId(openId);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        this.userBilAccountMapper.insertSelective(user);
        return user.getId().intValue();
    }



    /**
     * 调用webservice，查询远传水表数据
     */
    private Map<String, Object> queryByWebService(String accountNo) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            WsServiceServlet e = (new WsServiceServletServiceLocator()).getyxws();
            String str = e.interfaceQueryUser(accountNo);
            QueryUserDto queryUserDto = (QueryUserDto) XmlUtils.toBean(str, QueryUserDto.class);
            List<QueryUser> list = queryUserDto.getResult();
            if(list == null || list.size() == 0) {
                resMap.put("code", "-1");
                return resMap;
            }
            List<UserBillAccountDto> resList = new ArrayList<UserBillAccountDto>();
            String no = "";
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

                no = q.getWarrantNo();

                resList.add(dto);
            }

            resMap.put("data", resList);
            resMap.put("code", "0");
            resMap.put("no", no);
            return resMap;
        } catch (Exception e) {
            log.error("调用webservice错误" , e);
            resMap.put("code", "-1");
            return resMap;
        }
    }
}
