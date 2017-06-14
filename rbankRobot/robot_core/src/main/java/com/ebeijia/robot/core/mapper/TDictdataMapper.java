package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ebeijia.robot.core.pojo.TDictdata;

public interface TDictdataMapper {
	
	int insertSelective(TDictdata record);

	List<TDictdata> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);

	TDictdata selectByParams(@Param(value = "params") Map<String, Object> params);

	int deleteBySelective(@Param(value = "dictId") String dictId);

	int updateSelective(TDictdata dictData);

	int deleteByParams(@Param(value = "params") Map<String, Object> params);
	
	 /**
     * 查询 符合条件的记录总数
     * @author lijm
     * @param params
     * @return
     */
    int selectCountByParams(@Param(value="params") Map<String,Object> params);
    /**
     * 分页查询 记录
     * @author lijm
     * @param params 查询条件
     * @param pageOffset 开始游标
     * @param pageSize 每页显示的数量
     * @param orderParam 排序参数
     * @return
     */
    List<TDictdata> selectListByPageParams(@Param(value="params") Map<String,Object> params,
			   @Param(value="pageOffset")Integer pageOffset,
			   @Param(value="pageSize")Integer pageSize,
			   @Param(value="orderParam") String orderParam );
}