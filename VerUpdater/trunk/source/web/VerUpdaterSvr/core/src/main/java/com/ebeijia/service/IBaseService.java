package com.ebeijia.service;



import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;

import com.ebeijia.common.RollPage;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/11/18
 */
public interface IBaseService {

    /**
     * 增加记录
     * @author
     * @param obj
     * @throws Exception
     */
    Long addBasic(Object obj) throws Exception;

    /**
     * 修改记录
     * @author
     * @param obj
     * @throws Exception
     */
    void modifyBasic(Object obj) throws Exception;

    /**
     * 删除记录
     * @author 张斌
     * @param obj
     * @throws Exception
     */
    void delBasic(Object obj) throws Exception;

    /**
     * 根据主键查询记录
     * @author
     * @param seq
     * @return
     * @throws Exception
     */
    Object findObjByKey(Integer seq) throws Exception;

    /**
     * 根据条件查询记录
     * @author
     * @param params
     * @return
     * @throws Exception
     */
    Object findObj(Map<String, Object> params) throws Exception;

    /**
     * 根据条件查询列表
     * @author
     * @param params
     * @param order
     * @return
     * @throws Exception
     */
    List findListByParams(Map<String, Object> params, Order order) throws Exception;



    /**
     * 根据条件查询列表
     * @param params
     * @param order
     * @param maxRecord
     * @return
     * @throws Exception
     */
    List findListByParams(Map<String, Object> params, Order order,Integer maxRecord) throws Exception;

    /**
     * 根据条件查询 ，返回记录总数
     * @param params
     * @return
     */
    Integer findCountByParams(Map<String, Object> params);

    /**
     * 根据条件查询 列表（分页查询）
     * @author
     * @param params
     * @param order
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    RollPage findListPageByParams(Map<String, Object> params, Order order, Integer pageNum, Integer pageSize)throws Exception;


}