package com.ai.runner.center.omc.virtualdeduct.interfaces;

import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;


/**
 * 此借口是一个用于存储销账所需要的信息操作的
 * 并处理业务逻辑
 * 屏蔽了不同产品的差异
 * @author zhaixs
 *
 */
public interface IAccountFeeFund {
	/**
	 * 执行处理
	 * @return
	 */
	 RealTimeBalance process();
}
