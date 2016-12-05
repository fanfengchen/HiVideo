package com.ebeijia.video.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.ebeijia.video.service.DominateLogService;
import com.ebeijia.video.until.MyApplicationContextUtil;
import com.ebeijia.videocore.util.SpringContextUtil;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.constant.MemcachedConstant;
import com.ebeijia.videocore.pojo.TDominateLog;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.util.PropertiesUtils;

/**
 * 监控是否需要处理推送机器人事件
 * @author lijm
 * @date 2016-11-25
 *
 */
public class ThreadPoolTask extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MemcachedClient client=MyApplicationContextUtil.getClient();
	private static String masterSecret=PropertiesUtils.getProperties("masterSecret");
    private static String appKey=PropertiesUtils.getProperties("appKey");
    
    private DominateLogService logService= (DominateLogService) SpringContextUtil.getBean("dominateLogService");
	
    @Override
	public void run(){
		
		while(true){
			try {					
			  if(client != null && client.get(MemcachedConstant.DomainConfig) != null){//当前缓存中需要推送的业务数据	
              
        		   BlockingQueue<TDominateLog>userQueue=new LinkedBlockingQueue<TDominateLog>();//需要推送队列
        		   userQueue = client.get(MemcachedConstant.DomainConfig);
        		  
					if(userQueue!=null&&userQueue.size()>0){						
						
						for(TDominateLog dLog:userQueue){
							
							TDominateLog log  = new TDominateLog();
							Long dTime = dLog.getDtime().getTime();//获取需要推送的时间
							Long xTime = DateUtil.getTimes().getTime();//获取系统时间
							logger.info("推送时间："+DateUtil.getTimes()+"|推送编号为："+dLog.getId());
							if(xTime>=dTime){
							   //代表刚好到了时间点去推送用户信息
								JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);		
								
								Map<String,String> extras=new HashMap<String, String>();
						        extras.put("vcontext",dLog.getDcontext());        
						        PushPayload payload = buildPushObject_all_alias_alert(dLog.getDeviceid(),extras);
						        payload.resetOptionsTimeToLive(0);	              							        
						          							        
						        PushResult result = jpushClient.sendPush(payload);	     								
						        logger.info("=====远程控制机器人定时播放视频返回参数:" + result+"====="); 
						        log.setDstate(CommonTypeConstant.DomaineState.Succ.toString());	
						        log.setId(dLog.getId());
						        log.setRes1(result.toString());
						        log.setRes2("0");//代表已经有推送成功
						        logService.update(log);
						        
						        userQueue.peek();
						        delMapMem();
  							}else{
  		  						logger.info("没有需要自动推送的业务数据，没有匹配到合适的时间！");
  		  					}  
  						 }
  					}else{
  					  logger.info("没有需要自动推送的业务数据,缓存中获取的业务数据为空！");
  					}				                        		   
            	   }else{
            		   logger.info("缓存里面没有需要处理的数据，或者缓存中数据为空！");
            	   }
				  sleep(60*1000);//每隔1分钟线程休息					
				}catch(Exception ex){
					ex.printStackTrace();
					logger.error("访问事件轮询异常:",ex);
				}
			}		
	}
	
	private synchronized void delMapMem(){
		try {		
			
			client.delete(MemcachedConstant.DomainConfig);//清楚掉之前缓存中的数据	
			Map<String,Object> params = new HashMap<String,Object>();
			logService.findListByParams(params, null);
		} catch (Exception e) {
			logger.info("删除缓存中需要推送的业务数据 异常：",e);
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
