package com.ebeijia.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext springContext;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		springContext = context;
	}

	public static Object getBean(String beanName) {
		return springContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> c) {
		return springContext.getBean(c);
	}
}
