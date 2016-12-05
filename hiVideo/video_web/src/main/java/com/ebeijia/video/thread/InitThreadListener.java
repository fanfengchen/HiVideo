package com.ebeijia.video.thread;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程监听(用于开启、关闭线程)
 * 
 */
public class InitThreadListener extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger=LoggerFactory.getLogger(this.getClass());  
	
	//处理事件线程
	private ThreadPoolTask threadPoolTask = null;
	
	@Override
	public void destroy() {
		
		System.out.println("destroy ===================threadPoolTask:"+threadPoolTask); 
		 if(threadPoolTask != null && threadPoolTask.isInterrupted()) {  
			 logger.info("关闭线程===================threadPoolTask");
			 threadPoolTask.interrupt();  
         }  
		 
	}

	@Override
	public void init() {
		
		if (threadPoolTask == null) {  
			threadPoolTask = new ThreadPoolTask();  
			threadPoolTask.start();			
			logger.info("开启线程===================threadPoolTask");
        }	
		 
	}
}
