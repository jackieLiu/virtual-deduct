package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;
import com.ai.runner.center.omc.virtualdeduct.mapper.abm.FundResBookMapper;
import com.ai.runner.center.omc.virtualdeduct.service.FundResBookService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class FundResBookServiceImpl implements FundResBookService {
	
	@Autowired
	 private SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FundBookServiceImpl.class);
	@Override
	public List<FundResBook> query(OmcObj owner) throws OmcException {
		// TODO Auto-generated method stub
		logger.info("进入FundResBookServiceImpl的query方法，参数为OmcObj:"+owner);
		if(owner==null){
			throw new OmcException("outprama", "获取owner为null" );
		}
		try {
			FundResBookMapper fundResBookMapper = sqlSessionTemplate.getMapper(FundResBookMapper.class);
				List<FundResBook> fundResBooks = fundResBookMapper.query(owner);
			if(fundResBooks==null||fundResBooks.isEmpty()){
				throw new OmcException("Fun_res_book", "获取FundResBook异常" );
			}
			for (FundResBook fundResBook : fundResBooks) {
				String totalAmount = (fundResBook.getTotalAmount() == null) ? "0.0":fundResBook.getTotalAmount().toString();
				String transferAmount = (fundResBook.getTransferAmount() == null) ? "0.0":fundResBook.getTransferAmount().toString();
	    		String usedAmount = (fundResBook.getUsedAmount() == null) ? "0.0":fundResBook.getUsedAmount().toString()  ;
	    		String balanceAmount = (fundResBook.getBalanceAmount() == null) ? "0.0":fundResBook.getBalanceAmount().toString()  ;
	    		fundResBook.setTotalAmount(Cal.BigDecimalFromDoubleStr(totalAmount, FEESOURCE.FROMCREDIT));
	    		fundResBook.setTransferAmount(Cal.BigDecimalFromDoubleStr(transferAmount, FEESOURCE.FROMCREDIT));
	    		fundResBook.setUsedAmount(Cal.BigDecimalFromDoubleStr(usedAmount, FEESOURCE.FROMCREDIT));
	    		fundResBook.setBalanceAmount(Cal.BigDecimalFromDoubleStr(balanceAmount, FEESOURCE.FROMCREDIT));
			}
			logger.info("FundResBookServiceImpl的query方法,返回值为List<FundResBook>:"+fundResBooks);
			return fundResBooks;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new OmcException("FundResBookServiceImpl",e.getMessage(),e.getCause());
			
		}
	}

}
