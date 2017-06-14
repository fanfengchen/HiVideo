package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.robot.core.pojo.TRole;

public interface TRoleMapper {
	int deleteByPrimaryKey(Integer roleId);

	int insert(TRole record);

	int insertSelective(TRole record);

	TRole selectByPrimaryKey(Integer roleId);

	int updateByPrimaryKeySelective(TRole record);

	int updateByPrimaryKey(TRole record);

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

	List<TRole> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "pageOffset") Integer pageOffset,
			@Param(value = "pageSize") Integer pageSize,
			@Param(value = "orderParam") String orderParam);

	List<TRole> selectListByParam(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);

	List<TRole> selectByParams(@Param(value = "params")Map<String, Object> params);
}