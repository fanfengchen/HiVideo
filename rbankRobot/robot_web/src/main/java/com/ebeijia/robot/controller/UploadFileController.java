package com.ebeijia.robot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.service.file.FileHandleService;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.FileUploadRes;

/**
 * 上传文件操作
 * 
 * @author ff
 *
 */
@RequestMapping("/")
@Controller
public class UploadFileController {

	@Autowired
	private FileHandleService fileService;

	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage fileUpload(@RequestParam(value="file",required = false) MultipartFile file,
			RequestMessage resMessage, HttpServletRequest request,String fileType,String fileName)
			throws ServiceException {
		 
	//	System.out.println("两个参数："+"fileType"+fileType+"fileNam"+fileName);
		FileUploadRes res = new FileUploadRes();
		Integer fileId = fileService.saveFile(file, fileType,fileName);
		res.setFileId(fileId.toString());
		return ResponseMessage.success(res);
	}
}
