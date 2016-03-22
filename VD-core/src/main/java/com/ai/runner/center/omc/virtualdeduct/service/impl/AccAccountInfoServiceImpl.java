package com.ai.runner.center.omc.virtualdeduct.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.inv.IAccAccountInfoMapper;
import com.ai.runner.center.omc.virtualdeduct.service.AccAccountInfoService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class AccAccountInfoServiceImpl implements AccAccountInfoService  {
	 @Autowired
	 private IAccAccountInfoMapper accAccountInfoMapper;
	private static final Logger logger = LoggerFactory.getLogger(AccAccountInfoServiceImpl.class);
	@Override
	public AccAccountInfo query(Owner acct) throws OmcException {
		logger.info("进入AccAccountInfoServiceImpl的query方法，参数为Owner:"+acct);
		if(acct==null){
			throw new OmcException("outprama", "获取owner为null" );
		}
		try {
				AccAccountInfo accAccountInfo = accAccountInfoMapper.query(acct);
			if(accAccountInfo==null){
				throw new OmcException("acc_account_info", "获取AccAccountInfo异常" );
			}
			logger.info("AccAccountInfoServiceImpl的query方法,AccAccountInfo:"+accAccountInfo.getBalance());
			accAccountInfo.setBalance(Cal.BigDecimalFromDouble(Double.parseDouble(accAccountInfo.getBalance().toString()),FEESOURCE.FROMCHARGE));
			logger.info("AccAccountInfoServiceImpl的query方法,AccAccountInfo:"+accAccountInfo.getBalance());
			logger.info("AccAccountInfoServiceImpl的query方法,返回值为AccAccountInfo:"+accAccountInfo);
			return accAccountInfo;
		} catch (Exception e) {
			throw new OmcException("AccAccountInfo",e.getMessage(),e.getCause());
		}
	}

}
