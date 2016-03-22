package com.ai.runner.center.omc.virtualdeduct.service;

import java.util.List;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface FundSettleRuleService {
	List<FundSettleRule> query(Long fundsubject) throws OmcException;
}
