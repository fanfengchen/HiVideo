package com.ebeijia.robot.core.service.file.impl;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TFileinfoMapper;
import com.ebeijia.robot.core.pojo.TFileinfo;
import com.ebeijia.robot.core.service.file.FileHandleService;
import com.ebeijia.robot.core.util.StringUtil;


/**
 * 文件上传处理service
 * 
 * @author chyulin
 * 
 */
@Service
public class FileHandleServiceImpl implements FileHandleService {

	protected @Value("${upload.file.path}") String filePath;

	@Autowired
	private TFileinfoMapper fileMapper;

	/**
	 * 文件写操作
	 */
	@Transactional
	@Override
	public Integer saveFile(MultipartFile file, String fileType, String fileName)
			throws ServiceException {
		try {
			// 判断参数 是不是为空
			if (!StringUtil.checkNull(false, fileType, fileName)) {
				throw new ServiceException(ApiResultCode.Err_0002.toString(),
						"缺少参数");
			}

			TFileinfo record = genFileInfo(file, fileType, fileName);
			// 文件写到磁盘
			writeFile(file, record);

			fileMapper.insertSelective(record);
			return record.getFileId();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 写文件到磁盘
	 * 
	 * @param file
	 * @param record
	 * @throws Exception
	 */
	private void writeFile(MultipartFile file, TFileinfo record)
			throws Exception {
		File tmpPahtFile = new File(filePath );
		System.out.println("保存的本地路径:" + record.getLocalPath());
		if (!tmpPahtFile.exists()) {
			tmpPahtFile.mkdirs();
		}
		// 写到的磁盘路径
		File tmpFile = new File(filePath + record.getLocalPath());
		file.transferTo(tmpFile);
		/*
		 * switch (record.getType()) { case CommonConstant.FILE_TYPE_AUDIO: case
		 * CommonConstant.FILE_TYPE_VEDIO: transferToRemote(tmpFile, record);
		 * break; default: break; }
		 */
	}

	private TFileinfo genFileInfo(MultipartFile file, String fileType,
			String fileName) throws Exception {
		TFileinfo record = new TFileinfo();
		// 文件名去掉 .jpg
		record.setFileName(fileName.substring(0,fileName.lastIndexOf(".")));
	//	System.out.println("这是fileName的值：" + fileName);
		// 包含文件名的路径地址
		
		StringBuffer localPath = new StringBuffer();
		
		String uuid = UUID.randomUUID().toString();
	
		localPath.append(uuid.replaceAll("\\-", ""));
		localPath.append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));// 拼接文件名
		
		record.setLocalPath(localPath.toString());
		System.out.println("file.getOriginalFilename()的值:"
				+ localPath.toString());
		// 文件类型
		record.setType(fileType);

		return record;
	}

}
