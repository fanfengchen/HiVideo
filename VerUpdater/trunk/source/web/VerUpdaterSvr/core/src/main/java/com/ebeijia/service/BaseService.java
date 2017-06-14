package com.ebeijia.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.ebeijia.common.RollPage;
import com.ebeijia.mapper.IBaseMapper;

public abstract class BaseService implements IBaseService{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Integer pageSizeDefault = 20;

    protected IBaseMapper baseMapper;

    public void setBaseMapper(IBaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    public abstract Long addBasic(Object obj) throws Exception;

    public abstract void modifyBasic(Object obj) throws Exception;

    public abstract void delBasic(Object obj) throws Exception;

    @Override
    public Object findObjByKey(Integer seq) throws Exception {
        return baseMapper.selectByPrimaryKey(seq);
    }

    @Override
    public Object findObj(Map<String, Object> params) throws Exception {

        return baseMapper.selectByParams(params);
    }

    @Override
    public List findListByParams(Map<String, Object> params, Order order) throws Exception {
        return baseMapper.selectListByParams(params,null,null,order==null?null:order.toString());
    }



    @Override
    public Integer findCountByParams(Map<String, Object> params) {
        return baseMapper.selectCountByParams(params);
    }

    @Override
    public List findListByParams(Map<String, Object> params, Order order, Integer maxRecord) throws Exception {
        logger.info("**************params="+params+
                ";maxRecord="+maxRecord+";order="+order.toString());
       return baseMapper.selectListByParams(params, 0, maxRecord, order==null?null:order.toString());
    }
/*
    @Override
    public RollPage findListPageByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize) throws Exception {
        Integer recordSum= baseMapper.selectCountByParams(params);
        //Integer pageSizeCustom = ManageConfigCache.getConfigByConfigCode("admin.pagesize").configValue as Integer
        Integer pageSizeCustom = this.pageSizeDefault;
        RollPage rollPage=new RollPage();
        rollPage.iTotalRecords=recordSum;
        rollPage.iTotalDisplayRecords=recordSum;
        rollPage.pageSize=pageSize==null?pageSize:pageSize;
        rollPage.pageNum=pageNum==null?1:pageNum;

        Integer pageOffset=(rollPage.pageNum - 1) * rollPage.pageSize;

        if (recordSum>0) {
            rollPage.recordList =baseMapper.selectListByParams(params, pageOffset, rollPage.pageSize, order == null ? null : order.toString());
        }
        else{
            rollPage.recordList = new ArrayList();
        }

        return rollPage;
    }*/


}
