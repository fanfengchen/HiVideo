package com.ebeijia.robot.service;

import com.ebeijia.robot.modle.api.FileUploadRes;

public interface IFileInfoService {

	FileUploadRes uploadFile(Object file, String fileType, String fileName);

}
