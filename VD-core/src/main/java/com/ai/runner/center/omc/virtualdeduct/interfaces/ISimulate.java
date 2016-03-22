package com.ai.runner.center.omc.virtualdeduct.interfaces;


import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 此接口负责销账所加载信息
 * 屏蔽了不同产品的差异
 * @author zhaixs
 *
 */
public interface ISimulate {
	/**
	 * 初始化所需要的信息，信息从客户端传来
	 * @param owner
	 * @param extbalance
	 * @return
	 * @throws OmcException
	 */
	Boolean init(OmcObj owner,double extbalance) throws OmcException;
	/**
	 * 执行加载
	 * @return
	 * @throws OmcException
	 */
	RealTimeBalance process() throws OmcException;
}
