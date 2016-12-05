package com.ebeijia.module.jypayment.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.jypayment.TblPayLimitTime;
import com.ebeijia.entity.system.page.Page;
import com.ebeijia.entity.system.page.PageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf on 2016/10/24.
 */
@Repository
public class PayLimitTimeDao extends BaseDaoImplHibernate<TblPayLimitTime>{

    /**
     * 分页查询（缴费时间）
     * @param querySql
     * @param aoData
     * @return
     */
    public Map<String ,Object> queryPaymentDate(String querySql,String aoData){
        PageEntity pageEntity = new PageEntity();
        pageEntity = Page.init(aoData);
        Map<String ,Object> map = this.findByPageAndTotal(querySql, pageEntity.getiDisplayStart(), pageEntity.getiDisplayLength());
        return map;
    }

    public List<TblPayLimitTime> findById(TblPayLimitTime tblPayLimitTime){
        String sql = " from TblPayLimitTime where id = " + tblPayLimitTime.getId();
        List<TblPayLimitTime> list = find(sql);
        return list;
    }

    public void paymentDate(TblPayLimitTime tblPayLimitTime){
        StringBuffer str = new StringBuffer();

        if(tblPayLimitTime.getLimitDateStart() != null && !"".equals(tblPayLimitTime.getLimitDateStart())){

            str.append(" limitDateStart = '"+tblPayLimitTime.getLimitDateStart()+"',");
        }
        if(tblPayLimitTime.getLimitDateEnd() != null && !"".equals(tblPayLimitTime.getLimitDateEnd())){

            str.append(" limitDateEnd = '"+tblPayLimitTime.getLimitDateEnd()+"',");
        }
        if(tblPayLimitTime.getLimitTimeStart() != null && !"".equals(tblPayLimitTime.getLimitTimeStart())){

            str.append(" limitTimeStart = '"+tblPayLimitTime.getLimitTimeStart()+"',");
        }
        if(tblPayLimitTime.getLimitTimeEnd() != null && !"".equals(tblPayLimitTime.getLimitTimeEnd())){

            str.append(" limitTimeEnd = '" + tblPayLimitTime.getLimitTimeEnd() + "',");
        }
        String sql = "";
        if(!"".equals(str.toString())){
            str.insert(0,"  update TblPayLimitTime set ");
            sql = str.substring(0,str.length()-1) + " where id = " + tblPayLimitTime.getId();
        }
        updateHql(sql);
    }
}
