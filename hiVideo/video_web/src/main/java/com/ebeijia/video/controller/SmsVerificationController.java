package com.ebeijia.video.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.service.VerificationService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 短信验证码
 * @author lijm
 * @Date 2016-11-08
 *
 */
@RequestMapping("/")
@Controller
public class SmsVerificationController {

	@Autowired
	private VerificationService verService;
	
	
	/**
	 * 手机发送验证码
	 * 
	 * @param resultMessage
	 * @return
	 */
	@RequestMapping("getVerf")
	@ResponseBody
	public ResponseMessage getVerf(RequestMessage resultMessage,
			HttpServletRequest request) {

		Map<String, Object> map = resultMessage.getDataMap();

		String mobile = map.get("mobile").toString();// 手机号码
		String type = map.get("operType").toString();// 操作类型
		if (StringUtil.checkNull(false, mobile, type)) {// 验证必填参数
		
			ResponseMessage res = verService.getVerifivation(mobile, type);
			if("0000".equals(res.getRspCode())){
				
		    	//正常的业务返回	    	
		    	return ResponseMessage.success();
		    }else{
		    	return ResponseMessage.error(res.getRspCode(), res.getRspMsg());
		    }	
		}else{
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(), ApiResultCode.Err_0002.getMsg());
		}
	}

	
}
