package com.ebeijia.video.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.video.modle.api.DominateLogRes;
import com.ebeijia.video.service.DominateLogService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.pojo.DomateLog;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.DataMap;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 获取机器人的推送列表(自动推送)
 * 
 * @author ff
 *
 */
@Controller
@RequestMapping("/")
public class DominateLogController {
	
	@Autowired
	private DominateLogService dominateLogService;

	@RequestMapping(value = "getDomateLog")
	@ResponseBody
	public ResponseMessage getDomateLog(RequestMessage resMessage) throws Exception {
		
		Map<String ,Object> map = new HashMap<String, Object>();
		
		String token = resMessage.getAuthToken();//获取token信息
		
		Map currInfo = TokenUtil.getToken(token);
		if(currInfo==null){
			
			//代表token失效
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}
		List<DominateLogRes> list = dominateLogService.getdominateLog(token);
		map.put("list", list);
		return ResponseMessage.success(map);
	}	
	
	/**
	 * 删除机器人自动推送列表
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delRebotDomainLog")
	@ResponseBody
	public ResponseMessage delDomateLog(RequestMessage resMessage) throws Exception {
		
		DataMap<String, Object> data = resMessage.getDataMap();		

		List<Map<String, Object>> maps = data.getValueObAsList("list");	
		
		if(maps!=null){			
			
			
			List<DomateLog> res = new ArrayList<DomateLog>();			
			for(Map<String, Object> m : maps){
				
				DomateLog re= new DomateLog();
				re.setId((String) m.get("id"));				
				res.add(re);
			}
			
			dominateLogService.delDominateLog(res);			      
		}
		return ResponseMessage.success();
	}	
	
}
