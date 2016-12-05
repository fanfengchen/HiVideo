package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.TblFeeConfig;
import com.ebeijia.module.jypayment.dao.FeeConfigDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lf on 2016/10/9.
 */
@Service
public class FeeConfigServiceImpl implements FeeConfigService {

    @Autowired
    private FeeConfigDao feeConfigDao;

    /**
     * 查询欠、缴费金额
     * @param aoData
     * @return
     */
    @Transactional
    public Map<String ,Object> queryArrearsCeiling(String aoData){

        if(aoData == null || "".equals(aoData) || aoData.indexOf("[{") != 0){
            Map<String ,Object> map = new HashMap<String, Object>();
            map.put("resCode","-1");
            return map;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("from TblFeeConfig order by id");
        Map<String ,Object> data = feeConfigDao.queryByPage(sql.toString(),aoData);
        return dataConversion(data);
    }

    public Map<String ,Object> dataConversion(Map<String ,Object> map){
        Map<String ,Object> res = new HashMap<String, Object>();
        List<TblFeeConfig> list = (List<TblFeeConfig>) map.get("list");
        JSONArray jsonArray = new JSONArray();

        BigDecimal div = new BigDecimal(100);
        for(TblFeeConfig feeConfig : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",feeConfig.getId());
            jsonObject.put("oprType",feeConfig.getOprType());
            jsonObject.put("feeType",feeConfig.getFeeType());

            BigDecimal amt = new BigDecimal(feeConfig.getAmt());
            jsonObject.put("amt",amt.divide(div, 2, 2).toString());
            jsonArray.add(jsonObject);
        }
        res.put("data",jsonArray);
        res.put("count",map.get("total"));
        return res;
    }


    /**
     * 设置欠费金额上限
     * @param tblFeeConfig
     * @return
     */
    @Transactional
    public Map<String, Object> arrearsCeiling(TblFeeConfig tblFeeConfig) {
        Map<String ,Object> map = new HashMap<String, Object>();
        if(tblFeeConfig.getId() == 0){
            map.put("resCode","-1");
            return map;
        }
        String amt = tblFeeConfig.getAmt();
        if(StringUtils.isEmpty(amt)){
            map.put("resCode","-2");
            return map;
        }
        String patternStr = "^(\\d{1,9}(\\.\\d{1,2})?)$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(amt);
        if(!matcher.matches()){
            map.put("resCode","-3");
            return map;
        }

        feeConfigDao.arrearsCeiling(tblFeeConfig);
        map.put("resCode","0");
        return map;
    }

}
