package com.ebeijia.service;

import com.ebeijia.common.RollPage;
import com.ebeijia.dto.SeachSoftVerDto;

public interface SearcherSoftVerService {
	RollPage<SeachSoftVerDto> selectCountBysoftId(String content,String pageOffset,String pageSize)throws Exception;
}
