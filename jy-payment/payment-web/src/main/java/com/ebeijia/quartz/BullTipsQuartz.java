package com.ebeijia.quartz;

import com.ebeijia.api.socket.SocketUtil;
import com.ebeijia.dto.UserBillAccountDto;
import com.ebeijia.dto.webService.QueryUser;
import com.ebeijia.dto.webService.QueryUserDto;
import com.ebeijia.dto.webService.QueryUserInfo;
import com.ebeijia.entity.BillDueTips;
import com.ebeijia.entity.UserBilAccount;
import com.ebeijia.service.BillDueTipsService;
import com.ebeijia.service.UserBillAccountService;
import com.ebeijia.service.impl.TemplateMessageService;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.XmlUtils;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import com.ebeijia.wsdl.query.YxWebServiceLocator;
import com.ebeijia.wsdl.query.YxWebServiceSoap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YJ on 2016/10/18.
 */
@Component("bullTipsQuartz")
@Lazy(false)
public class BullTipsQuartz {

    @Resource
    private UserBillAccountService userBillAccountService;
    @Resource
    private BillDueTipsService     billDueTipsService;

    @Resource
    private TemplateMessageService templateMessageService;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    //0 0 10 5,15 * ?


    @Scheduled(cron = "0 0 10 5,15 * ?")
  /* @Scheduled(cron = "0 0/1 * * * ?")*/
    public void sendBullTips() throws ServiceException, RemoteException {

        String tips = "1";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("推送任务开启{}",simpleDateFormat.format(new Date()));
        List<UserBilAccount> userBilAccountList  = userBillAccountService.selectByTips(tips);
        WsServiceServlet ws = new WsServiceServletServiceLocator().getyxws();
        for (UserBilAccount userBilAccount : userBilAccountList) {
            BillDueTips billDueTips = new BillDueTips();
            if (userBilAccount.getAccountType().equals("02")) {
                Map<String, Object> map = SocketUtil.queryRechargeBySocket(userBilAccount.getUserNo());
                if (map.get("code").equals("0")) {
                    UserBillAccountDto userBillAccountDto = (UserBillAccountDto) map.get("data");
                    BigDecimal bigDecimal = new BigDecimal(userBillAccountDto.getBalance());
                    if(bigDecimal.compareTo(new BigDecimal("0.00"))==1){
                        continue;
                    }
                    String balance = userBillAccountDto.getBalance().substring(1,userBillAccountDto.getBalance().length()-4);
                    billDueTips.setAccountNo(userBillAccountDto.getUserNo());
                    billDueTips.setAccName(userBillAccountDto.getUsername());
                    billDueTips.setStartReads(userBillAccountDto.getStartNum());
                    billDueTips.setEndReads(userBillAccountDto.getEndNum());
                    billDueTips.setUseAmount(userBillAccountDto.getTotalPowe());
                    billDueTips.setDueAmt(balance);
                    billDueTips.setSettleMonth(userBillAccountDto.getStatementMonth());
                    billDueTipsService.insertSelective(billDueTips);
                    List<UserBilAccount> openIdList = userBillAccountService.selectByUserNo(userBillAccountDto.getUserNo());
                    String type ="远传水表";
                    if(openIdList.size()>1){
                        for(UserBilAccount open:openIdList){
                        String msgId =     templateMessageService.sendUnpaidBillMsg(type,open.getUserId(),billDueTips.getAccName(),userBilAccount.getUserNo(),balance,simpleDateFormat.format(new Date()),"","");
                            if(msgId!=null){
                                billDueTips.setPushSts("1");
                            }
                        }
                    }else{
                        String msgId =     templateMessageService.sendUnpaidBillMsg(type,openIdList.get(0).getUserId(),billDueTips.getAccName(),userBilAccount.getUserNo(), balance,simpleDateFormat.format(new Date()),"","");
                        if(msgId!=null){
                            billDueTips.setPushSts("1");
                        }
                    }
                    billDueTips.setPushDate(new Date());

                } else {
                    continue;
                }
            } else {
                String type = "";
                String userName ="";
                logger.info("查询用户详细信息{}",userBilAccount.getUserNo());
                YxWebServiceLocator locator=new YxWebServiceLocator();
                YxWebServiceSoap soap=locator.getYxWebServiceSoap12();
                StringHolder stringHolder = new StringHolder();
                String resStr = null;
                if(userBilAccount.getAccountType().equals("01")){
                    resStr = soap.findGasUserInfo(userBilAccount.getUserNo(),stringHolder);
                    type = "自来水";
                }else {
                    type="天然气";
                    resStr = soap.findWaterUserInfo(userBilAccount.getUserNo(),stringHolder);
                }
                QueryUserInfo queryUserInfo = XmlUtils.toBean(stringHolder.value,QueryUserInfo.class);
                userName = queryUserInfo.getUserInfo().getUserName();

                String str = ws.interfaceQueryUser(userBilAccount.getUserNo());
                logger.info("xml,户号{}{}",str,userBilAccount.getUserNo());
                QueryUserDto queryUserDto = XmlUtils.toBean(str, QueryUserDto.class);
                if (queryUserDto.getResult().size() == 1) {
                    continue;
                } else {
                    String remark = "";
                    for (QueryUser queryUser : queryUserDto.getResult()) {
                        remark += queryUser.getMon() + "\n";
                    }
                    int size = queryUserDto.getResult().size()-1;
                    billDueTips.setAccountNo(queryUserDto.getResult().get(0).getUserNo());
                    billDueTips.setSettleMonth(queryUserDto.getResult().get(size - 1).getMon());
                    billDueTips.setEndReads(queryUserDto.getResult().get(size - 1).getEndNum());
                    billDueTips.setStartReads(queryUserDto.getResult().get(0).getStartNum());
                    billDueTips.setStartMonth(queryUserDto.getResult().get(0).getMon());
                    billDueTips.setDueAmt(queryUserDto.getResult().get(size).getMoney());
                    billDueTips.setPushContents(remark);
                    billDueTipsService.insertSelective(billDueTips);
                    List<UserBilAccount> openIdList = userBillAccountService.selectByUserNo(queryUserDto.getResult().get(0).getUserNo());
                    if(openIdList.size()>1){
                        for(UserBilAccount open:openIdList){
                            String msgId =     templateMessageService.sendUnpaidBillMsg(type,open.getUserId(),userName,userBilAccount.getUserNo(),billDueTips.getDueAmt(),simpleDateFormat.format(new Date()),"",remark);
                            if(msgId!=null){
                                billDueTips.setPushSts("1");
                            }
                        }
                    }else{
                        String msgId =     templateMessageService.sendUnpaidBillMsg(type,openIdList.get(0).getUserId(),userName,userBilAccount.getUserNo(),billDueTips.getDueAmt(),simpleDateFormat.format(new Date()),"",remark);
                        if(msgId!=null){
                            billDueTips.setPushSts("1");
                        }
                    }
                    billDueTips.setPushDate(new Date());

                }
            }
            billDueTipsService.updateByPrimaryKeySelective(billDueTips);
        }

    }
}
