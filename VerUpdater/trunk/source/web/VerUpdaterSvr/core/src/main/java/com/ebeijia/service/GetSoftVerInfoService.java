package com.ebeijia.service;

import com.ebeijia.common.RollPage;
import com.ebeijia.dto.VerSoftDto;


public interface GetSoftVerInfoService {

	RollPage<VerSoftDto> selectByPrimaryKey(String softId) throws Exception;
}
