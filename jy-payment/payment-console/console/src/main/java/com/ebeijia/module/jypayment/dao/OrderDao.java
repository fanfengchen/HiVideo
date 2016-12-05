package com.ebeijia.module.jypayment.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.jypayment.OrderDto;
import com.ebeijia.entity.jypayment.TblOrders;
import com.ebeijia.entity.jypayment.enums.PayStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with com.ebeijia.module.jypayment.dao
 * User : zc
 * Date : 2016/10/24
 */
@Repository
public class OrderDao extends BaseDaoImplHibernate<TblOrders>{

    private static Logger log = LoggerFactory.getLogger(OrderDao.class);

    //获取分页/不分页 的所有数据
    public Map<String, Object> queryOrderDetail(OrderDto dto, Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        //获取分页数据
        String sql = getSql(dto, map);
        List listData = this.getListSQL(sql);
        listData = switchData(listData);

        resMap.put("data", listData);

        return resMap;
    }

    //获取条数
    public Map<String, Object> queryOrderDetailCount(OrderDto dto, Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();
        String sql = getSql(dto, map);
        String countSql = "select count(*) ";
        countSql += sql.substring(sql.indexOf("FROM"), sql.indexOf("limit"));
        List listCount = this.getListSQL(countSql);
        Long count = switchCount(listCount);
        resMap.put("count", count);
        return resMap;
    };

    //获取拼接好的sql
    private String getSql(OrderDto dto, Map<String, Object> map) throws Exception{
        StringBuffer sql = new StringBuffer();
                sql.append("SELECT")
                        .append(" tbo.order_time orderTime, tbo.order_type orderType, tod.pay_account_no payAccountNo,")
                        .append(" tod.need_pay needPay, tbo.order_amt orderAmt, tod.sent_no sentNo,")
                        .append(" tbo.order_no orderNo, tbo.order_status orderStatus, tod.sent_result sentResult")
                        .append(" FROM")
                        .append(" tbl_orders tbo, tbl_orders_detail tod")
                        .append(" WHERE")
                        .append(" tbo.id = tod.order_id ");

        String orderTimeStart = dto.getOrderTimeStart();
        String orderTimeEnd = dto.getOrderTimeEnd();
        String orderType = dto.getOrderType();
        String orderNo = dto.getOrderNo();
        String payAccountNo = dto.getPayAccountNo();
        String orderStatus = dto.getOrderStatus();
        String sentResult = dto.getSentResult();

        if(!StringUtils.isEmpty(orderTimeEnd)){
            sql.append(" AND tbo.order_time <= '").append(orderTimeEnd).append("' ");
        }
        if(!StringUtils.isEmpty(orderTimeStart)){
            sql.append(" AND tbo.order_time >= '").append(orderTimeStart).append("' ");
        }
        if(!StringUtils.isEmpty(orderType)){
            sql.append(" AND tbo.order_type = '").append(orderType).append("'");
        }
        if(!StringUtils.isEmpty(orderNo)){
            sql.append(" AND tbo.order_no = '").append(orderNo).append("'");
        }
        if(!StringUtils.isEmpty(payAccountNo)){
            sql.append(" AND tod.pay_account_no = '").append(payAccountNo).append("'");
        }
        if(!StringUtils.isEmpty(orderStatus)){
            sql.append(" AND tbo.order_status = '").append(orderStatus).append("'");
        }else{
            sql.append(" AND tbo.order_status <> '01'");
        }
        if(!StringUtils.isEmpty(sentResult)){
            sql.append(" AND tod.sent_result = '").append(sentResult).append("'");
        }
        sql.append(" order by tbo.order_time desc ");
        //source为0表示需要分页参数
        String source = (String)map.get("source");
        if("0".equals(source)){
            sql.append(" limit ").append(map.get("page")).append(" , ").append(map.get("size"));
        }
        return sql.toString();
    }

    //将map中的数据状态转为正确的标示
    private List<Map<String, Object>> switchData(List list){
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal divide = new BigDecimal(100);
        for(Object obj : list){
            Object[] objs = (Object[])obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Date date = (Date)objs[0];
            map.put("orderTime", format.format(date));
            map.put("orderType", PayStatusEnum.AccountType.getDesc((String)objs[1]));
            map.put("payAccountNo", objs[2]);
            BigDecimal needPay = new BigDecimal((BigInteger)objs[3]);
            map.put("needPay", needPay.divide(divide, 2, 2).toString());
            BigDecimal orderAmt = new BigDecimal((BigInteger)objs[4]);
            map.put("orderAmt", orderAmt.divide(divide, 2, 2).toString());
            map.put("sentNo", objs[5]);
            map.put("orderNo", objs[6]);
            map.put("orderStatus", PayStatusEnum.PayStatus.getDesc((String)objs[7]));
            String sentResult = (String)objs[8];
            String sentResultTemp = StringUtils.isEmpty(sentResult) ? "" :
                    PayStatusEnum.SendStatus.getDesc(sentResult);
            map.put("sentResult",sentResultTemp);
            resList.add(map);
        }
        return resList;
    }

    //获取
    private Long switchCount(List list){
        Long count = 0l;
        for(Object obj : list){
            count = ((BigInteger)obj).longValue();
        }
        return count;
    }
}
