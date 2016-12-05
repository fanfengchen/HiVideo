package com.ebeijia.videocore.mapper;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TVerification;

public interface TVerificationMapper {
	int insert(TVerification record);

	int insertSelective(TVerification record);

	TVerification selectByParams(@Param("mobile") String userId,
			@Param("verfCode") String verfCode);
    
    int updateSelect(TVerification record);
}