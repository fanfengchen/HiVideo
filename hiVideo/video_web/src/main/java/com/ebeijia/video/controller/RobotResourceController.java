package com.ebeijia.video.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.video.modle.api.RobotRes;
import com.ebeijia.video.modle.api.RobotResourceRes;
import com.ebeijia.video.service.RobotResourceService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.pojo.TRobotResource;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.DataMap;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 手机端绑定机器人资源
 * @author lijm
 * @date 2016-11-10
 *
 */

@RequestMapping("/")
@Controller
public class RobotResourceController{

	@Autowired
	private RobotResourceService reService;
	
	/***
	 * 获取机器人视频列表
	 * @param reMessage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRebotResource")
	@ResponseBody
	public ResponseMessage getRebotSource(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();
		
		String token = reMessage.getAuthToken();//获取token信息		
		String vtype = (String)map.get("vtype");//类型
		String vname = (String)map.get("vname");//名称
		String deviceId = (String)map.get("deviceId");//设备编码
		Map currInfo = TokenUtil.getToken(token);
		if(currInfo==null){
			
			//代表token失效
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}
		
		List<RobotResourceRes> rL = reService.getResource(vtype, vname,deviceId,token);
		Map<String,Object> resL = new HashMap<String, Object>();
		resL.put("list", rL);
		return ResponseMessage.success(resL);
	}
	/**
	 * 添加机器人绑定资源
	 * @param reMessage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addRebotResource")
	@ResponseBody
	public ResponseMessage addRebotResource(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
		try{
			
			Map<String,Object> map = reMessage.getDataMap();		

			String vid = (String)map.get("vid");//资源编号
			String rid = (String)map.get("deviceId");//机器人编号
			String about = (String)map.get("about");//描述信息
		
	       if (StringUtil.checkNull(false, vid,rid)) {// 验证必填参数
				
	    	    TRobotResource rb = new TRobotResource();
		   		rb.setAbout(about);
		   		rb.setVid(Integer.parseInt(vid));   		
		   		return reService.addRobotResource(rb,rid);
				
			}else{
				return ResponseMessage.error(ApiResultCode.Err_0002.getCode(), ApiResultCode.Err_0002.getMsg());
			}
		}catch(ServiceException ex){
			//异常处理
			return  ResponseMessage.error(ApiResultCode.Err_0001.getCode(), ex.getExceptionMesssage());
		}	
	  
	}
	
	/**
	 * 手机端绑定机器人资源删除方法
	 * @param reMessage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delRebotResource")
	@ResponseBody
	public ResponseMessage delRebotResource(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
		try{
			
			DataMap<String, Object> data = reMessage.getDataMap();		

			List<Map<String, Object>> maps = data.getValueObAsList("list");	
			
			if(maps!=null){
				
				List<RobotRes> res = new ArrayList<RobotRes>();
				
				for(Map<String, Object> m : maps){
					
					RobotRes re= new RobotRes();
					re.setDeviceId((String) m.get("deviceId"));
					re.setVid((String)m.get("vid"));
					res.add(re);
				}
				
				reService.delRobotResorce(res);			      
			}
	       
	       
		}catch(ServiceException ex){
			//异常处理
			return  ResponseMessage.error(ApiResultCode.Err_0001.getCode(), ex.getExceptionMesssage());
		}
		return ResponseMessage.success();
	}
}
