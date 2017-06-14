package com.ebeijia.robot.core.service.file;

import org.springframework.web.multipart.MultipartFile;

import com.ebeijia.robot.core.exception.ServiceException;

public interface FileHandleService {
	Integer  saveFile(MultipartFile file,String fileType,String fileName)
			throws ServiceException;
}
