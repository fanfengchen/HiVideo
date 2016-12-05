package com.ebeijia.videocore.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TUser;

public interface TUserMapper {
	int deleteByPrimaryKey(String userId);

	int insert(TUser record);

	int insertSelective(TUser record);

	TUser selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(TUser record);

	int updateByPrimaryKey(TUser record);

	TUser selectByParams(@Param(value = "params") Map<String, Object> params);

	TUser selectPwdByToken(@Param(value = "token") String token);

	int updatePassword(@Param(value = "password") String newPwd,
			@Param(value = "userId") String userId);
}