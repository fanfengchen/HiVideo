package com.ebeijia.service.impl;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.entity.FeeConfig;
import com.ebeijia.mapper.FeeConfigMapper;
import com.ebeijia.service.FeeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LiYan on 2016/10/13.
 */
@Service
public class FeeConfigServiceImpl implements FeeConfigService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeeConfigMapper feeConfigMapper;

    @Override
    public FeeConfig findByFeeTypeAndOprType(String feeType, String oprType) {
        return feeConfigMapper.selectByFeeTypeAndOprType(feeType,oprType);
    }

    @Override
    public String isAmtInLimit(String feeType,Long orderAmt,Long needPay) {
        FeeConfig oweFee=feeConfigMapper.selectByFeeTypeAndOprType(feeType,"01");
        FeeConfig payFee=feeConfigMapper.selectByFeeTypeAndOprType(feeType,"02");
        String oweStr=oweFee.getAmt();
        String payStr=payFee.getAmt();
        if(CommonConstant.WATER_FAY_PAY.equals(feeType)){
            if(!"".equals(oweStr) && null != oweStr){
                Long oweLimit=Long.parseLong(oweStr);
                if(oweLimit != 0){
                    if(oweLimit + needPay < 0){
                        logger.info("欠费金额大于欠费限制");
                        return "01";//ResponseMessage.error("欠费金额过大，请去柜台缴费");
                    }
                }
            }

            Long payLimit=Long.parseLong(payStr);
            if(payLimit != 0){
                if(payLimit < Math.abs(orderAmt)){
                    logger.info("缴费金额大于缴费限制");
                    return "02";//ResponseMessage.error("缴费金额大于缴费限制");
                }
            }

            return "";
        }else {
            Long oweLimit=Long.parseLong(oweStr);
            Long payLimit=Long.parseLong(payStr);
            if(oweLimit != 0){
                if(oweLimit < Math.abs(needPay)){
                    logger.info("欠费金额大于欠费限制");
                    return "01";//ResponseMessage.error("欠费金额过大，请去柜台缴费");
                }
            }
            if(payLimit != 0){
                if(payLimit < Math.abs(orderAmt)){
                    logger.info("缴费金额大于缴费限制");
                    return "02";//ResponseMessage.error("缴费金额大于缴费限制");
                }
            }

            return "";
        }

    }


}
