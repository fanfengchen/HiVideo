package com.ebeijia.video.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import net.rubyeye.xmemcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ebeijia.video.until.MyApplicationContextUtil;
import com.ebeijia.videocore.cache.service.IMemcachedService;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.constant.MemcachedConstant;
import com.ebeijia.videocore.mapper.TDominateLogMapper;
import com.ebeijia.videocore.pojo.TDominateLog;
import com.ebeijia.videocore.util.DateUtil;
/**
 * 加载监控日志到缓存
 * @author lijm
 * @date 2016-11-22
 *
 */
@Component
public class DomainLogCache {

	@Autowired
	private IMemcachedService memcachedService;
	
	@Autowired
	private TDominateLogMapper logMapper;	
	
	private static Logger log=LoggerFactory.getLogger(DomainLogCache.class);
	
	/**
	 * 将监控日志加入到缓存中
	 * @return
	 * @throws Exception 
	 */
   @PostConstruct
   public void  init(){
	   
	  
	   log.info("======项目启动的时候加载监控日志到缓存方法开始==========");
	   Map<String,Object> params = new HashMap<String,Object>();
	   params.put("dtype", CommonTypeConstant.DomainType.Automaticw.toString());//自动推送
	   params.put("beginTime", DateUtil.getTimes());
	   params.put("res2", "1");
	   List<TDominateLog> tList = logMapper.selectListByParams(params, null);
	   BlockingQueue<TDominateLog>userQueue=new LinkedBlockingQueue<TDominateLog>();//需要推送队列
	   if(tList!=null){		  
		  	
		try {			
			 //memcachedService.delete(MemcachedConstant.DomainConfig);
			for(TDominateLog dLog:tList){				
			
				userQueue.offer(dLog);				
				memcachedService.set(MemcachedConstant.DomainConfig, 0, userQueue);
			}
			
		} catch (Exception e) {
		   e.printStackTrace();
		}	   
		log.info("======项目启动的时候加载监控日志到缓存方法结束==========");
	   }	   
	}
 
 /**
  * 重新加载
 * @throws Exception 
  */
  public void reLoad(){
		init();
	} 
  
  public static List<TDominateLog> getDomainLogValue(){
	  
	  List<TDominateLog> domainDateLog = new ArrayList<TDominateLog>();
	  MemcachedClient client = MyApplicationContextUtil.getClient();
	  
	  try{
		  
		  domainDateLog =  client.get(MemcachedConstant.DomainConfig);
	  }catch(Exception ex){		  
		 
		 log.error("===根据条件从缓存中获取监控日志异常====",ex.getMessage());
	  }
	  return domainDateLog;
  }
}
