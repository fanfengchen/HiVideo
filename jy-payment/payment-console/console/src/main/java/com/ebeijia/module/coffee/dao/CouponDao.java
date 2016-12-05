package com.ebeijia.module.coffee.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.coffee.TblCoupon;
import com.ebeijia.entity.system.page.Page;
import com.ebeijia.entity.system.page.PageEntity;
import org.ebeijia.tools.DateTime4J;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf on 2016/9/19.
 */
@Repository
public class CouponDao extends BaseDaoImplHibernate<TblCoupon> {

    /**
     * 修改时查询奖品是否存在
     * @param tblCoupon
     * @return
     */
    public List<TblCoupon> addCoupon(TblCoupon tblCoupon){
        String sql = " from TblCoupon where id = '"+tblCoupon.getId()+"'";
        List<TblCoupon> result = find(sql);
        return result;
    }

    /**
     * 修改奖品
     * @param tblCoupon
     */
    public void updateCoupon(TblCoupon tblCoupon){
       StringBuffer strb = new StringBuffer();
        if(tblCoupon.getActiveDate() != null){
            strb.append(" activeDate = '"+ DateTime4J.dateTimeFormat(tblCoupon.getActiveDate()) +"',");
        }
        if(tblCoupon.getExpireDate() != null){
            strb.append(" expireDate = '"+DateTime4J.dateTimeFormat(tblCoupon.getExpireDate())+"',");
        }
        if(tblCoupon.getCouponName() != null){
            strb.append(" couponName = '"+tblCoupon.getCouponName()+"',");
        }
        if(tblCoupon.getCouponIntegral() != null){
            strb.append(" couponIntegral = "+tblCoupon.getCouponIntegral()+",");
        }
        if(tblCoupon.getCouponDesc() != null){
            strb.append(" couponDesc = '"+tblCoupon.getCouponDesc()+"',");
        }
        if(tblCoupon.getImageUrl() != null){
            strb.append(" imageUrl = '"+tblCoupon.getImageUrl()+"',");
        }
        if(tblCoupon.getCouponType() != null){
            strb.append(" couponType = '"+tblCoupon.getCouponType()+"',");
        }
        String sql = "";
        if(!"".equals(strb.toString())){
            strb.insert(0," update TblCoupon set ");
            sql = strb.substring(0,strb.length()-1) + " where id = " + tblCoupon.getId();
        }
        updateHql(sql);
    }

    /**
     * 分页查询
     * @param querySql
     * @param aoData
     * @return
     */
    public Map<String ,Object> queryByPage(String querySql,String aoData){
        PageEntity pageEntity = new PageEntity();
        pageEntity = Page.init(aoData);
        Map<String, Object> result = this.findByPageAndTotal(querySql, pageEntity.getiDisplayStart(), pageEntity.getiDisplayLength());
        return result;
    }

    /**
     * 删除奖品
     * @param id
     */
    public void deteleById(String id){
        String sql = "update TblCoupon set state = '1' where id = " + id;
        updateHql(sql);
    }
}
