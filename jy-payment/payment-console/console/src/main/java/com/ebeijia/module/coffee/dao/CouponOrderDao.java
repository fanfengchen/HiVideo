package com.ebeijia.module.coffee.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.coffee.TblCouponOrder;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by win7 on 2016/9/19.
 */
@Repository("CouponOrderDao")
public class CouponOrderDao extends BaseDaoImplHibernate<TblCouponOrder> {

    private static Logger log = LoggerFactory.getLogger(CouponOrderDao.class);

    public List<Map<String, Object>> queryCouponOrder(String couponNo){

        String sql = " select tuc.coupon_no couponNo, tuc.expire_date expireDate, tuc.open_id openId, tco.id couponOrderId, " +
                " tco.order_sub_time useDate, tuc.id id, tco.order_no orderNo, tco.state state from tbl_user_coupon tuc " +
                " left join tbl_coupon_order tco on tuc.coupon_no = tco.coupon_no where tuc.coupon_no = '" + couponNo + "'";

        //使用jdbc查询
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection cp = null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try {
            cp = ((SessionFactoryImplementor) this.getHibernateTemplate().getSessionFactory()).getConnectionProvider().getConnection();
            ps = cp.prepareStatement(sql);

            log.debug("使用jdbc执行sql：" + sql);
            rs = ps.executeQuery();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("couponNo",rs.getString("couponNo"));
                dataMap.put("expireDate",format.format(rs.getTimestamp("expireDate")));
                //dataMap.put("openId",rs.getString("openId"));
                dataMap.put("useDate",rs.getTimestamp("useDate") == null ? "" : format.format(rs.getTimestamp("useDate")));
                dataMap.put("id",rs.getString("id"));
                dataMap.put("orderNo",rs.getString("orderNo") == null ? "" : rs.getString("orderNo"));
                dataMap.put("state",rs.getString("state") == null ? "0" : rs.getString("state"));
                dataMap.put("couponOrderId",rs.getString("couponOrderId") == null ? "" : rs.getString("couponOrderId"));
                list.add(dataMap);
            }

        }catch (Exception e){
            log.error("使用jdbc错误" , e);
        }finally {
            if(rs != null){
                try { rs.close();
                } catch (SQLException e) {}
            }
            if(ps != null){
                try {ps.close();
                } catch (SQLException e) {}
            }
            if(cp != null){
                try {cp.close();
                } catch (SQLException e) {}
            }
        }
        return list;
    }

    public int updateUserCouponById(String id){
        String sql = "update tbl_user_coupon set status = '1', use_date = date_format('" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "', '%Y-%m-%d %H:%i:%s') " +
                "where id = '" + id + "'";
        SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
        return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    public int updateCouponOrderById(String couponId, String orderId, String orderNo){
        String sqlOrder = "update tbl_coupon_order set order_no = '" + orderNo + "', order_sub_time = date_format('" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "', '%Y-%m-%d %H:%i:%s') " +
                "where id = '" + orderId + "'";
        String couponSql = "update tbl_user_coupon set status = '1', use_date = date_format('" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "', '%Y-%m-%d %H:%i:%s') " +
                "where id = '" + couponId + "'";
        Session session =this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sqlOrder).executeUpdate();
        return session.createSQLQuery(couponSql).executeUpdate();
    }
}
