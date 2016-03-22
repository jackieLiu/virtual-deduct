package com.ai.runner.center.omc.virtualdeduct.mapper.abm;

import java.util.List;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * fun_settle_rule
 * @author zhaixs
 *
 */
public interface FundSettleRuleMapper {
	/**
	 * 根据fundsubject Id查出 FundSettleRule集合
	 * @param fundsubject
	 * @return
	 * @throws OmcException
	 */
	List<FundSettleRule> query(Long fundsubjectId) throws OmcException;
}