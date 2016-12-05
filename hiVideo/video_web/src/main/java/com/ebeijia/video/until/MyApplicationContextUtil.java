package com.ebeijia.video.until;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;//声明一个静态变量保存

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context=arg0;
		
	}
	public static ApplicationContext getContext(){
       return context;

    }

	/**
	 * 获取 memcachedClient
	 * @author ljm
	 * @date 2016-04-8
	 * @modify by
	 * @return
	 */
	public static  MemcachedClient getClient(){
		return (MemcachedClient)context.getBean("memcachedClient");
	}
	
	
	/**
	 * 根据Bean名称获取spring中的bean	 
	 * @author lijm
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}

}
