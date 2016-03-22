package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.inv.IAccInvoiceMapper;
import com.ai.runner.center.omc.virtualdeduct.service.AccInvoiceService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class AccInvoiceServiceImpl implements AccInvoiceService  {
	@Autowired
	 private IAccInvoiceMapper accInvoiceMapper;
	private static final Logger logger = LoggerFactory.getLogger(AccInvoiceServiceImpl.class);
	@Override
	public List<AccInvoiceInfo> query(Owner acct,String accMonth) throws OmcException {
		logger.info("进入AccInvoiceServiceImpl的query方法，参数为Owner:"+acct+",String:"+accMonth);
		if(acct==null){
			throw new OmcException("outprama", "获取owner为null" );
		}
		try {
				List<AccInvoiceInfo> accInvoiceInfos = accInvoiceMapper.query(acct, accMonth);  
   		    if ((accInvoiceInfos == null)||(accInvoiceInfos.isEmpty())){
				return null;
   		    }
   		    for (AccInvoiceInfo accInvoiceInfo : accInvoiceInfos) {
   		    	String adjust = (accInvoiceInfo.getAdjust() == null) ? "0.0":accInvoiceInfo.getAdjust().toString();
	    		String balance = (accInvoiceInfo.getBalance() == null) ? "0.0":accInvoiceInfo.getBalance().toString();
	    		String disc = (accInvoiceInfo.getDisc() == null) ? "0.0":accInvoiceInfo.getDisc().toString()  ;
	    		String total = (accInvoiceInfo.getTotal() == null) ? "0.0":accInvoiceInfo.getTotal().toString()  ;
	    		accInvoiceInfo.setAdjust(Cal.BigDecimalFromDoubleStr(adjust, FEESOURCE.FROMCHARGE));
	    		accInvoiceInfo.setBalance(Cal.BigDecimalFromDoubleStr(balance, FEESOURCE.FROMCHARGE));
	    		accInvoiceInfo.setDisc(Cal.BigDecimalFromDoubleStr(disc, FEESOURCE.FROMCHARGE));
	    		accInvoiceInfo.setTotal(Cal.BigDecimalFromDoubleStr(total, FEESOURCE.FROMCHARGE));
	    		
			}
   		    logger.info("AccInvoiceServiceImpl的query方法,返回值为List<AccInvoiceInfo>:"+accInvoiceInfos);
		    return accInvoiceInfos;		    
		} catch (Exception e) {
			throw new OmcException("AccInvoiceServiceImpl",e.getMessage(),e.getCause());
		}
	}
}
