package com.ebeijia.controller.pay;

import com.ebeijia.api.socket.WaterPayFarQueryApi;
import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.OrderResultDto;
import com.ebeijia.dto.socketModels.OverdueQueryReq;
import com.ebeijia.dto.socketModels.OverdueQueryRes;
import com.ebeijia.dto.webService.QueryUser;
import com.ebeijia.dto.webService.QueryUserDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.entity.OrdersDetail;
import com.ebeijia.entity.PayLimitTime;
import com.ebeijia.service.*;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.XmlUtils;
import com.ebeijia.web.ResponseMessage;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiYan on 2016/9/27.
 */
@RestController
public class PayHandleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "waterFarPayService")
    private PayHandleService waterFarPayService;

    @Resource(name = "payGasHandleBaseService")
    private PayHandleService payGasHandleBaseService;

    @Resource
    private UserBillAccountService userBillAccountService;
    @Resource
    private FeeConfigService feeConfigService;

    @Resource
    private GeneratlorOrderService generatlorOrderService;

    @Resource
    private PayLimitTimeService payLimitTimeService;
    /**
     * 水费远端表支付
     * @param openId
     * @param userNo
     * @param amount
     */
    @RequestMapping(value = "toPay")
    public ResponseMessage waterFarSendPay(String openId, String userNo, String amount, String type,
                                           String tipsArrears, String isRecord,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try{
            //判断是否添加绑定信息
            Map resMap =userBillAccountService.addUserAccount(openId,userNo,type,tipsArrears,isRecord);
            String resCode = (String)resMap.get("code");
            if("0".equals(resCode)){
                logger.info("添加绑定数据成功");
            }
            if("-1".equals(resCode)){
                return ResponseMessage.error("输入的参数缺失");
            }
            if("-2".equals(resCode)){
                return ResponseMessage.error("输入的户号不存在");
            }
            if("-3".equals(resCode)){
                return ResponseMessage.error("查询数据失败");
            }

            //判断缴费时间是否在区间内
            PayLimitTime payLimitTime=payLimitTimeService.selectByType(type);
            boolean flagDate=payLimitTimeService.isDateInLimit(payLimitTime);
            //按日期判断
            if(!flagDate){
                return ResponseMessage.error("当前日期不在缴费区间内");
            }
            boolean flagTime=payLimitTimeService.isTimeInLimit(payLimitTime);
            //按当日时间判断
            if(!flagTime){
                return ResponseMessage.error("当前时间不在缴费时间内");
            }

            //判断amount是否是两位小数
            boolean flag=regMatch(amount);
            if(!flag){
                logger.info("传入金额为两位小数");
                return ResponseMessage.error("传入金额为两位小数");
            }

            BigDecimal shouldMoney=null;//欠费金额
            BigDecimal balance=null;//上期余额

            Orders orders=new Orders();
            String orderNo= generatlorOrderService.generator("order_no");
            orders.setOrderNo(orderNo);
            orders.setOrderType(type);
            orders.setOrderTime(new Date());
            BigDecimal ob=new BigDecimal(amount).multiply(new BigDecimal(100));
            Long orderAmt=ob.longValue();
            orders.setOrderAmt(orderAmt);
            orders.setPayType("01");
            orders.setOrderStatus("01");
            orders.setUserId(openId);

            OrdersDetail ordersDetail=new OrdersDetail();
            ordersDetail.setPayAccountNo(userNo);
            String serivalNo=generatlorOrderService.generator("serival_no");
            ordersDetail.setSentNo(serivalNo);

            OrderResultDto orderResultDto=new OrderResultDto();
            //远传表查询socket
            if(CommonConstant.WATER_FAY_PAY.equals(type)){
                logger.info("调用socket支付接口");
                OverdueQueryReq req=new OverdueQueryReq();
                req.setUserNo(userNo);
                req.setTerminalCode(PropertiesUtils.getProperties("socket.terminalCode"));
                req.setCommand(PropertiesUtils.getProperties("socket.command"));

                OverdueQueryRes res= WaterPayFarQueryApi.queryOverdue(req);

                if(null == res){
                    logger.info("调用socket接口无响应");
                    return ResponseMessage.error("远端繁忙请稍后再试");
                }
                if(!CommonConstant.SOC_RES_SUCC.equals(res.getResCode())){
                    logger.info("socket查询信息有误");
                    return ResponseMessage.error(res.getResCode(),"请输入正确信息查询");
                }
                shouldMoney=res.getShouldMoney();
                balance=res.getBalance();
                BigDecimal needpayB=shouldMoney.add(balance);

                Long needpayL=0L;
                int needTemp=needpayB.compareTo(new BigDecimal(0));
                //当needTep=0或1 表示 查询账户余额大于欠费金额 -1表示小于
                if(-1 == needTemp){
                    //查询账户余额小于欠费金额
                    //应缴金额为余额和欠费和
                    needpayL=(needpayB.multiply(new BigDecimal(100))).longValue();
                }

                if(orderAmt + needpayL < 0){
                    logger.info("预存金额不能小于欠费金额");
                    return ResponseMessage.error("预存金额不能小于欠费金额");
                }

                //判断欠费和缴费金额是否超出限制
                String feestatus=feeConfigService.isAmtInLimit(type,orderAmt,needpayL);
                if("01".equals(feestatus)){
                    return ResponseMessage.error("欠费金额过大，请去柜台缴费");
                }
                if("02".equals(feestatus)){
                    return ResponseMessage.error("缴费金额大于缴费限制");
                }


                ordersDetail.setNeedPay(Math.abs(needpayL));
                ordersDetail.setPrestoreAmt(orderAmt+needpayL);
                orderResultDto=waterFarPayService.toAuthPay(orders,ordersDetail);
            }else {
                logger.info("调用webservice支付接口");
                //调用webservice
                WsServiceServlet ws=new WsServiceServletServiceLocator().getyxws();
                String str = ws.interfaceQueryUser(ordersDetail.getPayAccountNo());

                QueryUserDto queryUserDto = XmlUtils.toBean(str, QueryUserDto.class);
                if("N".equals(queryUserDto.getFlag())){
                    return null;
                }

                Long shouldPayMoney=null;
                List<QueryUser> list=queryUserDto.getResult();
                QueryUser queryUser=list.get(list.size()-1);
                BigDecimal omb=new BigDecimal(queryUser.getMoney()).multiply(new BigDecimal(100));
                shouldPayMoney=omb.longValue();

                //判断欠费和缴费金额是否超出限制
                String feestatus=feeConfigService.isAmtInLimit(type,orderAmt,shouldPayMoney);
                if("01".equals(feestatus)){
                    return ResponseMessage.error("欠费金额过大，请去柜台缴费");
                }
                if("02".equals(feestatus)){
                    return ResponseMessage.error("缴费金额大于缴费限制");
                }

                ordersDetail.setNeedPay(Math.abs(shouldPayMoney));
                ordersDetail.setPrestoreAmt(orders.getOrderAmt()-shouldPayMoney);

                orderResultDto=payGasHandleBaseService.toAuthPay(orders,ordersDetail);
            }

            Map resulMap = new HashMap();
            resulMap.put("token_id", orderResultDto.getTokeId());
            resulMap.put("sentStatus",orderResultDto.getSentStatus());
            return ResponseMessage.success(resulMap);


        }catch (Exception ex){
            logger.error("================本地插入订单数据出现异常===================", ex);
            return ResponseMessage.error("系统繁忙,请稍后再试");
        }
    }


    //验证是否是两位小数
    public boolean regMatch(String amt){
        String reg="\\d+\\.?\\d{2}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher=pattern.matcher(amt);
        boolean flag=matcher.matches();
        return flag;
    }

}
