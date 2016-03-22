package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundBookInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.abm.FundBookMapper;
import com.ai.runner.center.omc.virtualdeduct.service.FundBookService;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class FundBookServiceImpl  implements FundBookService {
	@Autowired
	 private SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FundBookServiceImpl.class);
	@Override
	public FundInfo query(OmcObj owner) throws OmcException {
		logger.info("进入FundBookServiceImpl的query方法，参数为OmcObj:"+owner);
		if(owner==null){
			throw new OmcException("outprama", "获取owner为null" );
		}
		try{
			FundBookMapper fundBookMapper = sqlSessionTemplate.getMapper(FundBookMapper.class);
			FundInfo fundInfo = fundBookMapper.query(owner);
			if(fundInfo==null){
				throw new OmcException("FundBook", "获取账本异常，没有有效账本");
			}else{
				Long balance=0L;
				List<FundBookInfo> fundBooks = fundInfo.getFundBooks();
				for (Iterator<FundBookInfo> iterator = fundBooks.iterator(); iterator.hasNext();) {
					FundBookInfo fundBookInfo = (FundBookInfo) iterator.next();
					balance+= fundBookInfo.getBalance();
				}
				fundInfo.setBalance(balance);
			}
				logger.info("FundBookServiceImpl的query方法,返回值为FundInfo:"+fundInfo);
				return fundInfo;
		}catch(Exception e){
			throw new OmcException("FundBookServiceImpl","错误来自fundBookServiceImpl");
		}

	}

}

