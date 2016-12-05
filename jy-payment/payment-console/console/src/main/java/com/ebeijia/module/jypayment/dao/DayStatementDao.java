package com.ebeijia.module.jypayment.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.jypayment.TblOrders;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with com.ebeijia.module.jypayment.dao
 * User : zc
 * Date : 2016/10/25
 */
@Repository
public class DayStatementDao extends BaseDaoImplHibernate<TblOrders> {

    /**
     * 获取详细的数据
     */
    public Map<String, Object> queryDayStatement(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String sql = getSql(map);

        List list = this.getListSQL(sql);
        list = switchData(list);

        resMap.put("detailData", list);
        return resMap;
    }

    /**
     * 获取详细的数据，总条数
     */
    public Map<String, Object> queryDayStatementCount(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String sql = getSql(map);

        String sqlCount = "select count(*) from ( select tbl_orders.id "
                + sql.substring(sql.indexOf("FROM"), sql.indexOf("limit")) + ") t";
        List listCount = this.getListSQL(sqlCount);
        Long count = switchCount(listCount);
        resMap.put("count", count);
        return resMap;
    }

    public Map<String, Object> queryMonthStatement(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String sql = getMonthSql(map);

        List list = this.getListSQL(sql);
        list = switchData(list);

        resMap.put("detailData", list);
        return resMap;
    }

    //获取总数
    public Map<String, Object> queryMonthStatementCount(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String sql = getMonthSql(map);

        String sqlCount = "select count(*) from ( select tbl_orders.id "
                + sql.substring(sql.indexOf("FROM"), sql.indexOf("limit"))+") t";
        List listCount = this.getListSQL(sqlCount);
        Long count = switchCount(listCount);

        resMap.put("count", count);
        return resMap;
    }

    /**
     * 获取拼接好的sql
     */
    private String getSql(Map<String, Object> map) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT tbl_orders.id, sum(if(order_status='03',order_amt,0)) as orderAmt,")
             .append(" sum(if(order_status='03',need_pay,0)) as needPay,")
             .append(" sum(if(order_status='03',prestore_amt,0)) as prestoreAmt,")
             .append(" count( distinct(pay_account_no)) as accountTotal,")
             .append(" sum(if(order_status='03',1,0)) as orderStatusSuccTotal,")
             .append(" sum(if(sent_result='01',1,0)) as sentResultSuccTotal,")
             .append(" sum(if(order_status ='05',1,0)) as refundTotal,")
             .append(" DATE_FORMAT(tbl_orders.order_time,'%Y-%m-%d') as orderTime")
             .append(" FROM tbl_orders LEFT JOIN tbl_orders_detail")
             .append(" on tbl_orders.id = tbl_orders_detail.order_id")
             .append(" WHERE order_status in('03','05')");

        String orderTimeStart = (String)map.get("orderTimeStart");
        String orderTimeEnd = (String)map.get("orderTimeEnd");
        String orderType = (String)map.get("orderType");
        if(!(StringUtils.isEmpty(orderTimeStart)) || StringUtils.isEmpty(orderTimeEnd)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(orderTimeEnd);
            Calendar dateTemp = Calendar.getInstance();
            dateTemp.setTime(date);
            dateTemp.add(Calendar.DAY_OF_YEAR, 1);
             sql.append(" and order_time >='").append(orderTimeStart).append("'")
                .append(" and order_time <='").append(format.format(dateTemp.getTime())).append("'");
        }
        if(!(StringUtils.isEmpty(orderType))){
            sql.append(" and order_type ='").append(orderType).append("'");
        }
         sql.append(" GROUP BY")
         .append(" TO_DAYS(tbl_orders.order_time) ")
         .append(" order by tbl_orders.order_time desc ");
        //判断是否需要分页
        String source = (String)map.get("source");
        if("0".equals(source)){
                sql.append(" limit ").append(map.get("page")).append(" , ").append(map.get("size"));
        }
        return sql.toString();
    }


    private String getMonthSql(Map<String, Object> map) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT tbl_orders.id, sum(if(order_status='03',order_amt,0)) as orderAmt,")
                .append(" sum(if(order_status='03',need_pay,0)) as needPay,")
                .append(" sum(if(order_status='03',prestore_amt,0)) as prestoreAmt,")
                .append(" count( distinct(pay_account_no)) as accountTotal,")
                .append(" sum(if(order_status='03',1,0)) as orderStatusSuccTotal,")
                .append(" sum(if(sent_result='01',1,0)) as sentResultSuccTotal,")
                .append(" sum(if(order_status ='05',1,0)) as refundTotal,")
                .append(" DATE_FORMAT(tbl_orders.order_time,'%Y-%m') as orderTime")
                .append(" FROM tbl_orders LEFT JOIN tbl_orders_detail")
                .append(" on tbl_orders.id = tbl_orders_detail.order_id")
                .append(" WHERE order_status in('03','05')");

        String orderTimeStart = (String)map.get("orderTimeStart");
        String orderTimeEnd = (String)map.get("orderTimeEnd");
        String orderType = (String)map.get("orderType");
        if(orderTimeStart!=null&&!orderTimeStart.equals("")){
            sql.append(" and order_time >='").append(orderTimeStart).append("'");
        }
        if(orderTimeEnd!=null&&!orderTimeEnd.equals("")){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date date = format.parse(orderTimeEnd);
            Calendar dateTemp = Calendar.getInstance();
            dateTemp.setTime(date);
            dateTemp.add(Calendar.MONTH, 1);
            sql.append(" and order_time <='").append(format.format(dateTemp.getTime())).append("'");
        }
        if(!(StringUtils.isEmpty(orderType))){
            sql.append(" and order_type ='").append(orderType).append("'");
        }
        sql.append(" GROUP BY")
                .append(" DATE_FORMAT(tbl_orders.order_time,'%Y-%m')  ")
                .append(" order by tbl_orders.order_time desc ");

        //判断是否需要分页
        String source = (String)map.get("source");
        if("0".equals(source)){
            sql.append(" limit ").append(map.get("page")).append(" , ").append(map.get("size"));
        }
        return sql.toString();
    }

    public Map<String, Object> queryMonthStatementTotal(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String baseSql = getMonthSql(map);
        String source = (String)map.get("source");
        if("0".equals(source)){
            baseSql = baseSql.substring(0,baseSql.indexOf("limit"));
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select sum(t.orderAmt),sum(t.needPay),sum(t.prestoreAmt),")
                .append("sum(t.accountTotal), sum(t.orderStatusSuccTotal),")
                .append("sum(t.sentResultSuccTotal),sum(t.refundTotal) ")
                .append("from (")
                .append(baseSql)
                .append(") t");
        List list = this.getListSQL(sql.toString());

        list = switchDataTotal(list);
        resMap.put("data", list == null || list.size() == 0 ? null : list.get(0));
        return  resMap;
    }

    //将map中的数据状态转为正确的标示
    private List<Map<String, Object>> switchData(List list){
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal d100 = new BigDecimal(100);
        for(Object obj : list){
            Object[] objs = (Object[])obj;
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("id", objs[0].toString());
            Long orderAmt = ((BigDecimal)objs[1]).longValue();
            BigDecimal totalFee = new BigDecimal(orderAmt);
            map.put("orderAmt", totalFee.divide(d100,2,2).toString());
            Long needPay = ((BigDecimal)objs[2]).longValue();
            map.put("needPay", new BigDecimal(needPay).divide(d100,2,2).toString());
            Long prestoreAmt = ((BigDecimal)objs[3]).longValue();

            map.put("prestoreAmt", new BigDecimal(prestoreAmt).divide(d100,2,2).toString());
            map.put("accountTotal", objs[4].toString());
            map.put("orderStatusSuccTotal", objs[5].toString());
            map.put("sentResultSuccTotal", objs[6].toString());
            map.put("refundTotal", objs[7].toString());
            map.put("orderTime", objs[8].toString());
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

    /**
     * 获取合计数据
     */
    public Map<String, Object> queryDayStatementTotal(Map<String, Object> map) throws Exception{
        Map<String, Object> resMap = new HashMap<String, Object>();

        String baseSql = getSql(map);
        String source = (String)map.get("source");
        if("0".equals(source)){
            baseSql = baseSql.substring(0, baseSql.indexOf("limit"));
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select sum(t.orderAmt),sum(t.needPay),sum(t.prestoreAmt),")
                .append("sum(t.accountTotal), sum(t.orderStatusSuccTotal),")
                .append("sum(t.sentResultSuccTotal),sum(t.refundTotal) ")
                .append("from (")
                .append(baseSql)
                .append(") t");
        List list = this.getListSQL(sql.toString());

        list = switchDataTotal(list);
        resMap.put("data", list == null || list.size() == 0 ? null : list.get(0));
        return  resMap;
    }

    //将map中的数据状态转为正确的标示
    private List<Map<String, Object>> switchDataTotal(List list){
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal divide = new BigDecimal(100);
        for(Object obj : list){
            Object[] objs = (Object[])obj;
            Map<String, Object> map = new HashMap<String, Object>();

            //在查询时，obj可能是都为null
            if(StringUtils.isEmpty(objs[0])){
                return resList;
            }

            BigDecimal orderAmt = (BigDecimal)objs[0];
            map.put("orderAmt", orderAmt.divide(divide, 2, 2).toString());
            BigDecimal needPay = (BigDecimal)objs[1];
            map.put("needPay", needPay.divide(divide, 2, 2).toString());
            BigDecimal prestoreAmt = (BigDecimal)objs[2];
            map.put("prestoreAmt", prestoreAmt.divide(divide, 2, 2).toString());
            map.put("accountTotal", objs[3].toString());
            map.put("orderStatusSuccTotal", objs[4].toString());
            map.put("sentResultSuccTotal", objs[5].toString());
            map.put("refundTotal", objs[6].toString());
            resList.add(map);
        }
        return resList;
    }

}
