package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.modle.api.RebotRes;
import com.ebeijia.video.service.ReboteService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.pojo.TRobot;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 机器人信息
 * @author lijm
 * @date 2016-11-09
 * 
 */
@RequestMapping("/")
@Controller
public class RebotController {
	
    @Autowired
    private ReboteService rebotService;
    
	/**
	 * 获取机器人列表
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRebot")
	@ResponseBody
	public ResponseMessage getRebot(RequestMessage reMessage)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();
		
		String token = reMessage.getAuthToken();//获取token信息		
		String rid =  (String)map.get("deviceId");//设备编号
		String rName = (String)map.get("deviceName");
		
		Map currInfo = TokenUtil.getToken(token);
		if(currInfo==null){
			
			//代表token失效
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}
		
		String intervalTime= PropertiesUtils.getProperties("intervalTime");//间隔时间
		List<RebotRes> rList = rebotService.getRebotList(rid, rName,token,intervalTime);
		
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("list", rList);
		return ResponseMessage.success(res);
	}
	
	/**
	 * 删除机器人手机端已经绑定的机器人信息
	 * @param reMessage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delRebot")
	@ResponseBody
	public ResponseMessage delRebot(RequestMessage reMessage)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();		
		
		String deviceId = (String)map.get("deviceId");//设备编号
		
		if (StringUtil.checkNull(false, deviceId)) {// 验证必填参数
		
			rebotService.delRebot(deviceId);
		}else{
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(), ApiResultCode.Err_0002.getMsg());
		}		
		
	   return ResponseMessage.success();
	}
	
	/**
	 * 手机端App新增机器人数据
	 * @param reMessage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addRebot")
	@ResponseBody
	public ResponseMessage addRebot(RequestMessage reMessage)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();		
		
		String token = reMessage.getAuthToken();//获取token信息
		String deviceId = (String)map.get("deviceId");//设备编号
		String deviceName = (String)map.get("deviceName");//设备名称
		String dType = (String)map.get("dType");//设备类型
	
		TRobot rb = new TRobot();
		rb.setDeviceId(deviceId);
		rb.setDeviceName(deviceName);
		rb.setDtype(dType);				
		
		Map currInfo = TokenUtil.getToken(token);
		if(currInfo==null){
			
			//代表token失效
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}
				
	   rebotService.addRebot(rb, token);
	   return ResponseMessage.success();
	}
}
