package com.ebeijia.video.service;

import java.util.List;
import com.ebeijia.video.modle.api.DictDataRes;


public interface DictDataService {

	List<DictDataRes> getDict(String dictId);
}
