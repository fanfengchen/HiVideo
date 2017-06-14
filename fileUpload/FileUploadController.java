package com.edu.cmhmnscore.web.file;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.edu.cmhmnscore.service.file.FileHandleService;
import com.edu.cmhmnscore.web.DataMap;
import com.edu.cmhmnscore.web.RequestMessage;
import com.edu.cmhmnscore.web.ResponseMessage;
   


/**
 * 单个文件上传
 * 
 * @author chyulin
 * 
 */
@RestController
public class FileUploadController {
	@Autowired
	private FileHandleService fileService;

	@RequestMapping(value="fileUpload",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage uploadFile(@RequestParam MultipartFile file,
			RequestMessage requestMessage, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DataMap<String,Object> data=requestMessage.getDataMap();
		Integer fileId = fileService.saveFile(file, data);
		resultMap.put("fileId", fileId);
		
		return ResponseMessage.success(resultMap);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public ResponseMessage handleException(Exception ex,
			HttpServletRequest request) {
		return ResponseMessage.error("文件过大");

	}


	/**
	 * 多文件上传
	 * 
	 * @param files
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "filesUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage filesUpload(
			@RequestParam("files") MultipartFile[] files)
			throws ServiceException {
		List<FileUploadLocalPathRes> list = new ArrayList<FileUploadLocalPathRes>();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				FileUploadLocalPathRes res = new FileUploadLocalPathRes();
				MultipartFile file = files[i];
				String localPath = fileService.saveFileLocal(file, "01",
						"胡话.jpg");
				res.setLocalUrl(localPath);
				list.add(res);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return ResponseMessage.success(map);

	}



}
