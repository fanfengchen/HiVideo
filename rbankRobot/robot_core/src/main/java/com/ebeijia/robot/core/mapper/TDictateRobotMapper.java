package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ebeijia.robot.core.pojo.TDictateRobot;

public interface TDictateRobotMapper {
	int deleteByPrimaryKey(Integer dId);

	int insert(TDictateRobot record);

	int insertSelective(TDictateRobot record);

	TDictateRobot selectByPrimaryKey(Integer dId);

	int updateByPrimaryKeySelective(TDictateRobot record);

	int updateByPrimaryKey(TDictateRobot record);

	TDictateRobot selectByParams(
			@Param(value = "params") Map<String, Object> params);

	List<TDictateRobot> selectRobotByParams(@Param(value = "params") Map<String, Object> params);
	/**
	 * 获取总记录数
	 * 
	 * @param params
	 * @return
	 */
	int selectCountByParams(@Param(value = "params") Map<String, Object> params);

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
	List<TDictateRobot> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "pageOffset") Integer pageOffset,
			@Param(value = "pageSize") Integer pageSize,
			@Param(value = "orderParam") String orderParam);
}