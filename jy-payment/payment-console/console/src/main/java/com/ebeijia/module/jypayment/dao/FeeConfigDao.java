package com.ebeijia.module.jypayment.dao;

import com.ebeijia.dao.base.BaseDaoImplHibernate;
import com.ebeijia.entity.jypayment.TblFeeConfig;
import com.ebeijia.entity.system.page.Page;
import com.ebeijia.entity.system.page.PageEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by lf on 2016/10/10.
 */
@Repository
public class FeeConfigDao extends BaseDaoImplHibernate<TblFeeConfig> {

    /**
     * 分页查询（叫、欠费金额）
     * @param querySql
     * @param aoData
     * @return
     */
    public Map<String ,Object> queryByPage(String querySql,String aoData){
        PageEntity pageEntity = new PageEntity();
        pageEntity = Page.init(aoData);
        Map<String ,Object> map = this.findByPageAndTotal(querySql, pageEntity.getiDisplayStart(), pageEntity.getiDisplayLength());
        return map;
    }


    public List<TblFeeConfig> queryConfig(TblFeeConfig tblFeeConfig){
        String sql = " from TblFeeConfig where id = "+tblFeeConfig.getId();
        List<TblFeeConfig> list = find(sql);
    return list;
}

    public void arrearsCeiling(TblFeeConfig tblFeeConfig){
        StringBuffer str = new StringBuffer();
        if(tblFeeConfig.getAmt() != null && !"".equals(tblFeeConfig.getAmt())){
            BigDecimal amt = new BigDecimal(tblFeeConfig.getAmt());
            int amtInt = amt.multiply(new BigDecimal(100)).intValue();
            str.append(" amt = '" + amtInt + "',");
        }
        String sql = "";
        if(!"".equals(str.toString())){
            str.insert(0," update TblFeeConfig set ");
            sql = str.substring(0,str.length()-1) + " where id = " + tblFeeConfig.getId();
        }
        updateHql(sql);
    }
}
