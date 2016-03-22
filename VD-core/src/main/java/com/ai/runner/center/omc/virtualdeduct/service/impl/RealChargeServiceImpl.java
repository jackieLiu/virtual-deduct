package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.inv.IRealChargeMapper;
import com.ai.runner.center.omc.virtualdeduct.service.RealChargeService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class RealChargeServiceImpl implements RealChargeService{

	@Autowired
	 private IRealChargeMapper realChargeMapper;
	private static final Logger logger = LoggerFactory.getLogger(RealChargeServiceImpl.class);
	@Override
	public List<AccChargeInfo> query(Owner acct,String accMonth) throws OmcException {
		
		logger.info("进入RealChargeServiceImpl的query方法，参数为Owner:"+acct+",String:"+accMonth);
		if(acct==null){
			throw new OmcException("outprama", "获取Owner为null" );

		}
		try {
				List<AccChargeInfo> accChargeInfos = realChargeMapper.query(acct, accMonth); 
   		    if ((accChargeInfos == null)||(accChargeInfos.isEmpty())){
				return null;
   		    }
   		    for (AccChargeInfo accChargeInfo : accChargeInfos) {
	    		String disc = (accChargeInfo.getDisc() == null) ? "0.0":accChargeInfo.getDisc().toString()  ;
	    		String total = (accChargeInfo.getTotal() == null) ? "0.0":accChargeInfo.getTotal().toString()  ;
	    		accChargeInfo.setDisc(Cal.BigDecimalFromDoubleStr(disc, FEESOURCE.FROMCHARGE));
	    		accChargeInfo.setTotal(Cal.BigDecimalFromDoubleStr(total, FEESOURCE.FROMCHARGE));
	    		accChargeInfo.setAdjust(Cal.BigDecimalFromDouble(0.0, FEESOURCE.FROMCHARGE));
	    		BigDecimal subtract = accChargeInfo.getTotal().subtract(accChargeInfo.getDisc());
	    		accChargeInfo.setBalance(subtract);
	    
			}
   		   
			logger.info("RealChargeServiceImpl的query方法,返回值为List<AccChargeInfo>："+accChargeInfos);
		    return accChargeInfos;
		} catch (Exception e) {
			throw new OmcException("RealChargeServiceImpl",e.getMessage(),e.getCause());
		}
	}
}
