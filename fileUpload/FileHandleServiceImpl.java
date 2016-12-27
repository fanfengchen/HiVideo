package com.edu.cmhmnscore.service.impl.file;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.edu.cmhmnscore.constant.ApiResultCode;
import com.edu.cmhmnscore.constant.CommonConstant;
import com.edu.cmhmnscore.exception.ServiceException;
import com.edu.cmhmnscore.mapper.FileinfoMapper;
import com.edu.cmhmnscore.pojo.Fileinfo;
import com.edu.cmhmnscore.service.file.FileHandleService;
import com.edu.cmhmnscore.util.CacheUtil;
import com.edu.cmhmnscore.util.FtpUtil;
//import com.edu.cmhmnscore.util.FtpUtil;
import com.edu.cmhmnscore.util.PropertiesUtils;
import com.edu.cmhmnscore.web.DataMap;

/**
 * 文件上传处理service
 * 
 * @author chyulin
 * 
 */
@Service
public class FileHandleServiceImpl implements FileHandleService {
	@Autowired
	private FileinfoMapper fileInfoMapper;

	public Integer saveFile(MultipartFile file, DataMap<String, Object> data)
			throws ServiceException {
		try {
			String fileType = data.getValueAsString("fileType");
			if (StringUtils.isEmpty(fileType)) {
				throw new ServiceException(ApiResultCode.Err_0002.toString(),
						"请选择文件类型");
			}

			Fileinfo record = genFileInfo(file, fileType);

			writeFile(file, record);

			fileInfoMapper.insert(record);
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
	private void writeFile(MultipartFile file, Fileinfo record)
			throws Exception {
		String fileTmpPath = PropertiesUtils.getProperties("upload.file.path");
		File tmpPahtFile = new File(fileTmpPath + record.getUrlPath());
		if (!tmpPahtFile.exists()) {
			tmpPahtFile.mkdirs();
		}
		File tmpFile = new File(fileTmpPath + record.getLocalPath());
		file.transferTo(tmpFile);
		switch (record.getType()) {
		case CommonConstant.FILE_TYPE_AUDIO:
		case CommonConstant.FILE_TYPE_VEDIO:
			transferToRemote(tmpFile, record);
			break;
		default:
			break;
		}
	}

	private Fileinfo genFileInfo(MultipartFile file, String fileType)
			throws Exception {
		Fileinfo record = new Fileinfo();
		String fileName = file.getOriginalFilename();
		String fileFormat = fileName.substring(fileName.lastIndexOf("."));
		StringBuffer filePth = new StringBuffer();
		switch (fileType) {
		case CommonConstant.FILE_TYPE_AUDIO:
			filePth.append("audio/");
			break;
		case CommonConstant.FILE_TYPE_VEDIO:
			filePth.append("video/");
			break;
		case CommonConstant.FILE_TYPE_HANDOUT:
			filePth.append("handout/");
			break;
		case CommonConstant.FILE_TYPE_IMG:
			filePth.append("image/");
			break;
		default:
			filePth.append("default/");
			break;
		}
		// filePth.append(datePref).append("/");
		record.setFileName(fileName);
		record.setFormat(fileFormat);
		record.setSize(Long.valueOf(file.getSize()).intValue());
		record.setLastTime(new Date());
		// 文件保存的相对路径
		record.setUrlPath(filePth.toString());
		// 包含文件名的路径地址
		record.setLocalPath(filePth.append(CacheUtil.getFileNameSeq())
				.append(fileFormat).toString());
		record.setType(fileType);
		record.setState(CommonConstant.FILE_STATE_NOUSE);

		return record;
	}

	/**
	 * 上传文件到文件服务器
	 * 
	 * @param file
	 * @throws ServiceException
	 */
	private void transferToRemote(File file, Fileinfo record)
			throws ServiceException {

		FtpUtil.uploadFile(file, record.getUrlPath());

	}

}
