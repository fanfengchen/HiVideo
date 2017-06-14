package com.ebeijia.robot.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.robot.core.mapper.TFileinfoMapper;
import com.ebeijia.robot.core.pojo.TFileinfo;
import com.ebeijia.robot.modle.api.FileUploadRes;
import com.ebeijia.robot.service.IFileInfoService;

@Service
public class FileInfoServiceImpl implements IFileInfoService {

	protected @Value("${upload.file.path}") String filePath;

	@Autowired
	private TFileinfoMapper fileMapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transactional
	@Override
	public FileUploadRes uploadFile(Object file, String fileType,
			String fileName) {
		StringBuffer localPath = new StringBuffer();
		String uuid = UUID.randomUUID().toString();
		localPath.append(filePath);
		localPath.append(uuid.toString().replaceAll("\\-", ""));
		String fileStr = file.toString();
		localPath.append(fileStr.substring(fileStr.lastIndexOf(".")));// 拼接文件名
		logger.info(localPath.toString());
		// 添加文件数据
		TFileinfo fileInfo = new TFileinfo();
		fileInfo.setType(fileType);
		fileInfo.setFileName(fileName);
		fileInfo.setLocalPath(localPath.toString());
		fileMapper.insertSelective(fileInfo);
		logger.info(fileInfo.getFileId().toString());
		FileUploadRes fileRes = new FileUploadRes();
		fileRes.setFileId(fileInfo.getFileId().toString());
		return fileRes;
	}
}