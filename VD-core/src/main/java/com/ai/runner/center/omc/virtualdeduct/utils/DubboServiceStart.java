package com.ai.runner.center.omc.virtualdeduct.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.runner.center.omc.api.virtualdeduct.interfaces.DubboCancelAccountSV;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;


public class DubboServiceStart {
	@SuppressWarnings("resource")
	private static void startDubboService() {
		//log.info("开始启动 PaaS Dubbo 服务---------------------------");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"dubbo/provider/dubbo-provider.xml");
		context.registerShutdownHook();
		context.start();
		DubboCancelAccountSV sv = context.getBean("doubleCancelAccountServiceImpl",DubboCancelAccountSV.class);
//		System.out.println("111");
		OmcObj owner =new OmcObj("VIV-BYD", "0", "259","opt","data");
		RealTimeBalance cancelAccountProcess = sv.cancelAccountProcess(owner);
		System.out.println(cancelAccountProcess);
		//DubboCancelAccountSV bean = (DubboCancelAccountSV) context.getBean("doubleCancelAccountServiceImpl");
		//log.info("PaaS Dubbo 服务启动完毕---------------------------");
		//bean.cancelAccountProcess(owner)
		while (true) {
			try {
				Thread.currentThread();
				Thread.sleep(3000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		startDubboService();
	}
}
