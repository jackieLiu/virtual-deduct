package com.ai.runner.center.omc.virtualdeduct.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.mapper.abm.FundSettleRuleMapper;
import com.ai.runner.center.omc.virtualdeduct.service.FundSettleRuleService;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class FundSettleRuleServiceImpl implements FundSettleRuleService {
	@Autowired
	 private SqlSessionTemplate sqlSessionTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FundSettleRuleServiceImpl.class);
	@Override
	public List<FundSettleRule> query(Long fundsubject)
			throws OmcException {
		logger.info("进入FundSettleRuleServiceImpl的query方法，参数为Long:"+fundsubject);
		if(fundsubject==null){
			throw new OmcException("outprama", "获取fundsubject为null" );
		}
		try {
			FundSettleRuleMapper fundSettleRuleMapper = sqlSessionTemplate.getMapper(FundSettleRuleMapper.class);
			List<FundSettleRule> fundSettleRules = fundSettleRuleMapper.query(fundsubject);
			if(fundSettleRules==null||fundSettleRules.isEmpty()){
				throw new OmcException("fund_Settle_Rule", "获取fundSettleRules异常" );
			}
			logger.info("FundSettleRuleServiceImpl的query方法,返回值为List<FundSettleRule>:"+fundSettleRules);
			return fundSettleRules;
		} catch (Exception e) {
			throw new OmcException("FundSettleRuleServiceImpl",e.getMessage(),e.getCause());
		}
	}
	
}
