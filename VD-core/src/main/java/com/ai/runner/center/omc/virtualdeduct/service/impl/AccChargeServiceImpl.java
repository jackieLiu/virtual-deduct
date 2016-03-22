package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.inv.IAccChargeMapper;
import com.ai.runner.center.omc.virtualdeduct.service.AccChargeService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class AccChargeServiceImpl  implements AccChargeService{
	 @Autowired
	 private IAccChargeMapper accChargeMapper;
	private static final Logger logger = LoggerFactory.getLogger(AccChargeServiceImpl.class);
	@Override
	public List<AccChargeInfo> query(Owner acct,String accMonth) throws OmcException {
		logger.info("进入AccChargeServiceImpl类的query方法，参数为Owner:"+acct+",String:"+accMonth);
		if(acct==null){
			throw new OmcException("outprama", "获取owner为null" );
		}
		try {
				List<AccChargeInfo> charges = accChargeMapper.query(acct, accMonth);   
			if ((charges == null)||(charges.isEmpty())){
				throw new OmcException("acc_charge_tmp", "获取charges异常" );
			}
			for (AccChargeInfo charge : charges) {
				String adjust = (charge.getAdjust() == null) ? "0.0":charge.getAdjust().toString()  ;
	    		String balance = (charge.getBalance() == null) ? "0.0":charge.getBalance().toString()  ;
	    		String disc = (charge.getDisc() == null) ? "0.0":charge.getDisc().toString()  ;
	    		String total = (charge.getTotal() == null) ? "0.0":charge.getTotal().toString()  ;
				charge.setAdjust(Cal.BigDecimalFromDoubleStr(adjust, FEESOURCE.FROMCHARGE));
	    		charge.setBalance(Cal.BigDecimalFromDoubleStr(balance, FEESOURCE.FROMCHARGE));
	    		charge.setDisc(Cal.BigDecimalFromDoubleStr(disc, FEESOURCE.FROMCHARGE));
	    		charge.setTotal(Cal.BigDecimalFromDoubleStr(total, FEESOURCE.FROMCHARGE));
			}
		logger.info("AccChargeServiceImpl类的query方法,返回值为List<AccChargeInfo>:"+charges);
			return charges;

		} catch (Exception e) {
			throw new OmcException("AccChargeServiceImpl",e.getMessage(),e.getCause());
		}
	}
}
