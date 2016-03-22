package com.ai.runner.center.omc.virtualdeduct.utils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtil {

	private static ApplicationContextUtil instance = null;
	private static ApplicationContext context=  new ClassPathXmlApplicationContext("classpath:context/app-resources.xml");
	private ApplicationContextUtil(){}
	public static ApplicationContextUtil getInstance(){
		if(instance == null){
			synchronized(ApplicationContextUtil.class){
				if(instance == null){
					instance = new ApplicationContextUtil();
				}
			}
		}
		return instance;
	}
	
	public  Object getBean(String beanName) {
		if (beanName != null && !"".equals(beanName)) {
			return context.getBean(beanName);
		}
		return null;
	}
	public static ApplicationContext getCtx(){
		return context;
	}
}
