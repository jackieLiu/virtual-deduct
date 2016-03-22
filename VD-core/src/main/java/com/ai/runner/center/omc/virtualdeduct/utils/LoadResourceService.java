 package com.ai.runner.center.omc.virtualdeduct.utils;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
/**
 * 通过spring初始化资源信息
 * @author majun
 *
 */
public class LoadResourceService implements InitializingBean {

//	private Logger logger = Logger.getLogger(LoadResourceService.class);
	private List<String> resourcesName;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(String resName:resourcesName){
			PropertiesUtil.load(resName);
//			logger.debug("load resource ["+resName+"] is success!");
		}
	}

	public void setResourcesName(List<String> resourcesName) {
		this.resourcesName = resourcesName;
	}
	
}