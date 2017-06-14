package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.robot.core.pojo.TAllocationApp;

public interface TAllocationAppMapper {
	int deleteByPrimaryKey(Integer aId);

	int insert(TAllocationApp record);

	int insertSelective(TAllocationApp record);

	TAllocationApp selectByPrimaryKey(Integer aId);

	int updateByPrimaryKeySelective(TAllocationApp record);

	int updateByPrimaryKey(TAllocationApp record);

	/**
	 * 获取集合
	 * 
	 * @param params
	 * @param orderParam
	 * @return
	 */
	List<TAllocationApp> selectListParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);

	/***
	 * 
	 * @param params
	 * @return
	 */
	Integer selectCountByParams(
			@Param(value = "params") Map<String, Object> params);

	/**
	 * 分页查询 记录
	 * 
	 * @author lijm
	 * @param params
	 *            查询条件
	 * @param pageOffset
	 *            开始游标
	 * @param pageSize
	 *            每页显示的数量
	 * @param orderParam
	 *            排序参数
	 * @return
	 */
	List<TAllocationApp> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "pageOffset") Integer pageOffset,
			@Param(value = "pageSize") Integer pageSize,
			@Param(value = "orderParam") String orderParam);
}