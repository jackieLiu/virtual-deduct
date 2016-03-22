package com.ai.runner.center.omc.virtualdeduct.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;







import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.omc.api.virtualdeduct.interfaces.DubboCancelAccountSV;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IMvneSimulate;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IOptResSimulate;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IOptSimulate;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IResSimulate;
import com.ai.runner.center.omc.virtualdeduct.interfaces.ISimulate;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 此类为服务的具体实现
 * @author zhaixs
 *
 */
@Service
@Transactional
public class DoubleCancelAccountServiceImpl implements DubboCancelAccountSV{
	private ISimulate isi=null;
	@Autowired
	private IOptResSimulate iOptResSimulate;
	@Autowired
	private IResSimulate iResSimulate;
	@Autowired
	private IOptSimulate iOptSimulate;
	@Autowired
	private IMvneSimulate iMvneSimulate;
	private static Logger logger = LoggerFactory.getLogger(DoubleCancelAccountServiceImpl.class);
	
	@Override
	public RealTimeBalance cancelAccountProcess(OmcObj owner) throws CallerException {
		logger.info("进入DoubleCancelAccountServiceImpl类的cancelAccountProcess，参数为"+owner.toString());
		// TODO Auto-generated method stub
		if("opt".equals(owner.getProducttype())){
			if("cash".equals(owner.getBusinesscode())){
				isi=iOptSimulate;	
			}else if("voice".equals(owner.getBusinesscode())||"sm".equals(owner.getBusinesscode())||"data".equals(owner.getBusinesscode())){
			isi= iOptResSimulate;
			}else{
				throw new CallerException("重传", "资源类型传入错误");
			}
		}else
		if("mvne".equals(owner.getProducttype())){
			if("cash".equals(owner.getBusinesscode())){
				isi=iMvneSimulate;	
			}else if("voice".equals(owner.getBusinesscode())||"sm".equals(owner.getBusinesscode())||"data".equals(owner.getBusinesscode())){
				isi= iResSimulate;
			}else {
				throw new CallerException("重传", "资源类型传入错误");
			}
		}else{
			throw new CallerException("重传", "产品类型传入错误");
		}
		
		RealTimeBalance process=null;
		
			logger.info("初始化ISimulate为"+isi.toString());
			try {
				isi.init(owner, 100);
				process = isi.process();
			} catch (OmcException e) {
				// TODO Auto-generated catch block
				throw new CallerException("DoubleCancelAccountServiceImpl","服务计算失败",e);
			}
			process.setServicetype(owner.getProducttype());	
		process.setReturncode("MMP-000000");
		logger.info("DoubleCancelAccountServiceImpl类的cancelAccountProcess返回值为"+process.toString());	
		return process;
	}

	
}
