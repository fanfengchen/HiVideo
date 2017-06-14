package com.ebeijia.service;

import com.ebeijia.common.RollPage;
import com.ebeijia.dto.VerSofttypeDto;

public interface VerSofttypeService {

	RollPage<VerSofttypeDto> findInfoList();
}
