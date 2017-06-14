package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.robot.core.pojo.TAllocationUser;

public interface TAllocationUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(TAllocationUser record);

    int insertSelective(TAllocationUser record);

    TAllocationUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(TAllocationUser record);

    int updateByPrimaryKey(TAllocationUser record);
    
    
    /**
     * 查询 符合条件的记录总数
     * @author lijm
     * @param params
     * @return
     */
    int selectCountByParams(@Param(value="params") Map<String,Object> params);
    /**
     * 获取集合
     * @param params
     * @param orderParam
     * @return
     */
    List<TAllocationUser> selectListParams(@Param(value="params") Map<String,Object> params,
			   @Param(value="orderParam") String orderParam );
    /**
     * 分页查询 记录
     * @author lijm
     * @param params 查询条件
     * @param pageOffset 开始游标
     * @param pageSize 每页显示的数量
     * @param orderParam 排序参数
     * @return
     */
    List<TAllocationUser> selectListByParams(@Param(value="params") Map<String,Object> params,
			   @Param(value="pageOffset")Integer pageOffset,
			   @Param(value="pageSize")Integer pageSize,
			   @Param(value="orderParam") String orderParam );
}