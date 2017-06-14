package com.ebeijia.robot.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.robot.core.pojo.TRoleAcc;
import com.ebeijia.robot.core.pojo.TRoleAccKey;

public interface TRoleAccMapper {
	int deleteByPrimaryKey(TRoleAccKey key);

	int deleteByRoleId(@Param(value = "roleId") Integer roleId);

	int insert(TRoleAcc record);

	int insertSelective(TRoleAcc record);

	TRoleAcc selectByPrimaryKey(TRoleAccKey key);

	int updateByPrimaryKeySelective(TRoleAcc record);

	int updateByPrimaryKey(TRoleAcc record);

	List<TRoleAcc> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);
	
	List<TRoleAcc> selectListIdRoleAcc(Integer id);

}