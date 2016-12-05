package com.ebeijia.quartz;

import com.ebeijia.api.socket.WaterFarPayApi;
import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.RefundDto;
import com.ebeijia.dto.socketModels.WaterPayReq;
import com.ebeijia.dto.socketModels.WaterPayRes;
import com.ebeijia.entity.*;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.service.*;
import com.ebeijia.service.impl.OrderBaseService;
import com.ebeijia.service.impl.TemplateMessageService;
import com.ebeijia.util.DateUtil;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by LiYan on 2016/9/26.
 * 查询缴费结果（重复调用）
 */
public class QueryPayResultQuartz {
    private static final Logger logger= LoggerFactory.getLogger(QueryPayResultQuartz.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private PayInfoService payInfoService;
    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private UserBillAccountService userBillAccountService;
    @Autowired
    private RefundInfoService refundInfoService;

    @Resource
    private GeneratlorOrderService generatlorOrderService;

    @Resource

    private TemplateMessageService templateMessageService;

    //远传表水费查询 5分钟执行一次
    @Scheduled(cron="0 0/5 *  * * ? ")
    public void waterFarReslut() throws Exception{
        try{
            //查询所有已入账未成功订单
            List<TaskInfo> taskInfoList=taskInfoService.selectByResult("02");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(TaskInfo taskInfo:taskInfoList){
                String taskNo=taskInfo.getTaskNo();
                Orders orders=orderService.findByOrderNo(taskNo);
                String typeName = "";
                if(orders.getOrderType().equals("02")){
                    typeName="远传水表";
                }else if(orders.getOrderType().equals("01")){
                    typeName="自来水";
                }else{
                    typeName="天然气";
                }
                String orderNo=orders.getOrderNo();
                String orderStatus=orders.getOrderType();
                OrdersDetail ordersDetail=orderDetailService.selectByOrderId(orders.getId());
                Map<String,Object> params=new HashMap<String,Object>();
                params.put("userId",orders.getUserId());
                UserBilAccount userBilAccount=userBillAccountService.findByUserId(params);
                PayInfo payInfo=payInfoService.selectByOrderNo(orderNo);

                if(CommonConstant.WATER_FAY_PAY.equals(orderStatus)){
                    //远程表缴费 调用socket
                    logger.info("执行订单号为》"+orderNo+"缴费操作");
                    WaterPayReq req = new WaterPayReq();
                    req.setFunctionCode("2001");
                    BigDecimal orderAmt=new BigDecimal(orders.getOrderAmt()).divide(new BigDecimal(100));
                    String amt=orderAmt.toString();
                    req.setAmt(amt);
                    req.setUserNo(ordersDetail.getPayAccountNo());
                    req.setThridPayment(orderNo);
                    req.setTerminalCode(PropertiesUtils.getProperties("socket.terminalCode"));
                    req.setCommand(PropertiesUtils.getProperties("socket.command"));
                    WaterPayRes res = WaterFarPayApi.payWaterBill(req);
                    if (null == res) {
                        //第三方接口无响应，更新执行次数
                        int count=taskInfo.getTaskRetryCount()+1;
                        taskInfo.setTaskRetryCount(count);
                        taskInfoService.modifyTaskInfo(taskInfo);
                    }
                    if ("1".equals(res.getResCode())) {
                        //第三方缴费成功
                        //缴费成功 更新订单表状态
                        ordersDetail.setSentResult("01");
                        ordersDetail.setSerialNumber(res.getCopyPayment());
                        updateOrder(orders,ordersDetail);
                        BigDecimal totalFee = new BigDecimal(orders.getOrderAmt().toString());
                        BigDecimal d100 = new BigDecimal(100);
                        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                        //推送缴费详细信息
                        templateMessageService.sendPaymentSuccessMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),simpleDateFormat.format(new Date()),"微信支付",fee.toString());
                    }else{
                        //第三方缴费失败 退款
                        refund(orders,payInfo);
                        ordersDetail.setSentResult("02");
                        BigDecimal totalFee = new BigDecimal(orders.getOrderAmt().toString());
                        BigDecimal d100 = new BigDecimal(100);
                        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                        templateMessageService.sendRefundMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),fee.toString(),typeName);
                    }
                }else{
                    //调用webservice
                    WsServiceServlet ws=new WsServiceServletServiceLocator().getyxws();
                    String time=DateUtil.formatDate(new Date(), "yyyy/MM/dd hh:mm:ss");
                    String date=DateUtil.formatDate(new Date(), "yyyyMMdd");
                    //生成流水号
                    String a=generatlorOrderService.generator("serival_no");
                    String b=a.substring(a.length()-5,a.length());
                    String payType = PropertiesUtils.getProperties("web_payType");
                    BigDecimal orderAmt=new BigDecimal(orders.getOrderAmt()).divide(new BigDecimal(100));
                    String amt=orderAmt.toString();
                    String flowNo=payType+date+b;
                    String str = ws.interfacePayTrans(ordersDetail.getPayAccountNo(), flowNo,PropertiesUtils.getProperties("web_operatorId"), amt, time, "1", payType);
                    if("1001".equals(str)){
                        //入账成功 更新表
                        ordersDetail.setSentResult("01");
                        updateOrder(orders,ordersDetail);
                        BigDecimal totalFee = new BigDecimal(orders.getOrderAmt().toString());
                        BigDecimal d100 = new BigDecimal(100);
                        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                        //推送缴费详细信息
                        templateMessageService.sendPaymentSuccessMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),simpleDateFormat.format(new Date()),"微信支付",fee.toString());
                    }
                    if("1002".equals(str)){
                        //入账失败 退款
                        ordersDetail.setSentResult("02");
                        orderDetailService.modifyOrderDetail(ordersDetail);
                        refund(orders,payInfo);
                        BigDecimal totalFee = new BigDecimal(orders.getOrderAmt().toString());
                        BigDecimal d100 = new BigDecimal(100);
                        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                        templateMessageService.sendRefundMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),fee.toString(),typeName);
                    }
                }
            }
        }catch (Exception e){
            logger.error("");
        }


    }

    public void updateOrder(Orders orders,OrdersDetail ordersDetail) throws Exception{
        orders.setOrderStatus("03");
        orderService.modifyOrder(orders);

        orderDetailService.modifyOrderDetail(ordersDetail);
        logger.info("更新order和orderDetail表");
    }

    public void refund(Orders orders,PayInfo payInfo) throws Exception{
        RefundDto refundDto=new RefundDto();
        refundDto.setOrderId(orders.getId());
        refundDto.setOpenId(orders.getUserId());
        refundDto.setOrderNo(orders.getOrderNo());
        refundDto.setPayAmt(Long.valueOf(payInfo.getTotalFee()));
        refundDto.setPaySeqNo(payInfo.getTransactionId());
        String outRefundNo= generatlorOrderService.generator("order_no");
        refundDto.setRefundSeqNo(outRefundNo);
        refundDto.setTotalAmt(Long.valueOf(payInfo.getTotalFee()));
        RefundResult refundResult=orderBaseService.sentToRefund(refundDto);

        RefundInfo refundInfo=refundInfoService.findByOrderId(orders.getId());
        if(refundResult.getResult_code().equals("0")){
            orders.setOrderStatus("05");

            refundInfo.setRefundSts("05");
        }else{
            orders.setOrderStatus("07");

            refundInfo.setRefundSts("07");
        }
        orderService.modifyOrder(orders);

        refundInfoService.updateByPrimaryKeySelective(refundInfo);
    }


}
