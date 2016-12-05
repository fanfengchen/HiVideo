package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.TblFeeConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lf on 2016/10/9.
 */
@Service
public interface FeeConfigService {

    //查询缴、欠费金额
    public Map<String ,Object> queryArrearsCeiling(String aoData);
    //设置欠费金额上限
    public Map<String ,Object> arrearsCeiling(TblFeeConfig tblFeeConfig);

}
