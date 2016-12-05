package com.ebeijia.videocore.service.file;

import org.springframework.web.multipart.MultipartFile;

import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.web.DataMap;

public interface FileHandleService {
	Integer  saveFile(MultipartFile file, DataMap<String,Object> data)
			throws ServiceException;
}
