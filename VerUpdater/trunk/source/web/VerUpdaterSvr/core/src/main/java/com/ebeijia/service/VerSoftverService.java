package com.ebeijia.service;

import com.ebeijia.common.RollPage;
import com.ebeijia.dto.VerSoftverDto;

public interface VerSoftverService {

	RollPage<VerSoftverDto> findListPageByParams(String chnlId, String typeId,
			String pageOffset, String pageSize) throws Exception;

}
