package com.ai.runner.center.omc.virtualdeduct.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.mapper.abm.FundSubjectMapper;
import com.ai.runner.center.omc.virtualdeduct.service.FundSubjectService;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class FundSubjectServiceImpl implements FundSubjectService{
	@Autowired
	 private SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FundSubjectServiceImpl.class);
	@Override
	public FundSubject query(Long fundsubject) throws OmcException {
		logger.info("进入FundSubjectServiceImpl的query方法，参数为Long:"+fundsubject);
		if(fundsubject==null){
			throw new OmcException("outprama", "获取fundsubject为null" );
		}
		try{
			FundSubjectMapper fundSubjectMapper = sqlSessionTemplate.getMapper(FundSubjectMapper.class);
		FundSubject fundSubject = fundSubjectMapper.query(fundsubject);
		if(fundSubject==null){
			throw new OmcException("Fund_Subject", "获取FundSubject异常" );
		}
		logger.info("FundSubjectServiceImpl的query方法,返回值为FundSubject:"+fundSubject);
		return fundSubject;
	}catch(Exception e){
		throw new OmcException("FundSubjectServiceImpl",e.getMessage(),e.getCause());
		
	}
	}

}
