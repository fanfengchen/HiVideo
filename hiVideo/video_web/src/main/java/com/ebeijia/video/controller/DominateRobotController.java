package com.ebeijia.video.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.cache.DomainLogCache;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.mapper.TDominateLogMapper;
import com.ebeijia.videocore.pojo.TDominateLog;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;
import java.util.HashMap;

/**
 * 远程控制机器人播放视频
 * @author lijm
 * @date 2016-11-14
 */
@RequestMapping("/")
@Controller
public class DominateRobotController{

	@Autowired
	private TDominateLogMapper logMapper;
	
	@Autowired
	private DomainLogCache logCache;
	private static String masterSecret=PropertiesUtils.getProperties("masterSecret");
    private static String appKey=PropertiesUtils.getProperties("appKey");    
 
    
    private Logger logger=LoggerFactory.getLogger(this.getClass());    
    		
	
	@RequestMapping( value = "getDominnate",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseMessage getDominateRobot(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();
		
		String vcontext = (String)map.get("vcontext");
		String deviceId = (String)map.get("deviceId");
		String vtime = (String)map.get("vtime");//推送时间
		TDominateLog log = new TDominateLog();
		
        try {
        	 
             log.setDeviceid(deviceId);             

             log.setDstate(CommonTypeConstant.RobotState.Online.toString());
             log.setDcontext(vcontext);
             log.setRes2("1");//未推送            
             if(vtime!=null&&vtime!=""){
            	 
            	 //代表是传入时间的是需要自动推送的
                 log.setDtime(DateUtil.getTime(vtime,"yyyy-MM-dd HH:mm:ss"));
            	 log.setDtype(CommonTypeConstant.DomainType.Automaticw.toString());
            	 logMapper.insertSelective(log);
            	 logCache.reLoad();//重新从缓存中加载监控日志信息 
             }else{
            	 
            	 //代表 是需要手动推送的
            	 log.setDtype(CommonTypeConstant.DomainType.Automatic.toString());
            	 
        		 JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);		
        		
        		 Map<String,String> extras=new HashMap<String, String>();
                 extras.put("vcontext",vcontext);        
                 PushPayload payload = buildPushObject_all_alias_alert(deviceId,extras);
                 payload.resetOptionsTimeToLive(0);
                
                 logger.info("=====远程控制机器人手动播放视频传入的参数:deviceId:" + deviceId+"||vcontext:"+vcontext+"||masterSecret:"+masterSecret+"||appKey:"+appKey+"====");
                
                 PushResult result = new PushResult();
            	 result = jpushClient.sendPush(payload);
                 logger.info("====远程控制机器人手动播放视频返回参数:" + result+"====");             
                	 
 		        	//代表推送成功，去更新状态为推送成功 		        	
                 log.setDstate(CommonTypeConstant.DomaineState.Succ.toString());
 		       
                 log.setRes1(result.toString());//返回结果信息           
                 logMapper.insertSelective(log); 
             }                      
                   
            return ResponseMessage.success();
        } catch (APIConnectionException e) {
        	
        	log.setDstate(CommonTypeConstant.DomaineState.Fail.toString());
        	log.setRes1(e.getMessage());//返回结果信息
        	logMapper.insertSelective(log);  
            logger.error("Connection error, should retry later", e);
            return ResponseMessage.error(ApiResultCode.Err_0015.getCode(), ApiResultCode.Err_0015.getMsg());
        } catch (APIRequestException e) {
        	
        	log.setDstate(CommonTypeConstant.DomaineState.Fail.toString());
        	log.setRes1(e.getStatus()+"|"+e.getErrorCode()+"|"+e.getErrorMessage());//返回结果信息
        	logMapper.insertSelective(log); 
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            return ResponseMessage.error(ApiResultCode.Err_0015.getCode(), ApiResultCode.Err_0015.getMsg());
        }
		
	} 
	
	 public static PushPayload buildPushObject_all_alias_alert(String deviceId,Map<String,String> extras) {

	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(deviceId))
	                .setNotification(Notification.android(deviceId, deviceId, extras))

	                .build();
	 }
}
