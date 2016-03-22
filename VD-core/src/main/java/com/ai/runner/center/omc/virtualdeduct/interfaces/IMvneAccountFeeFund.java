package com.ai.runner.center.omc.virtualdeduct.interfaces;

import java.util.List;
import java.util.Map;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
/**
 * 此接口继承了IAccountFeeFund，仅适用于mvne产品
 * 并进一步完善了功能日后如需在mvne产品中添加功能可在此接口添加
 * @author zhaixs
 *
 */
public interface IMvneAccountFeeFund extends IAccountFeeFund {
	/**
	 * 
	 * @param acctmonth
	 * @param accInvoice
	 */
	void addSummary(String acctmonth,AccInvoiceInfo accInvoice);
	/**
	 * 
	 * @param acctmonth
	 * @param charge
	 */
	void addDetail(String acctmonth,AccChargeInfo charge);
	/**
	 * 
	 * @param acctmonth
	 * @param charge
	 */
	void addRealCharge(String acctmonth,AccChargeInfo charge);
	/**
	 * 
	 * @param fundInfo
	 * @param subjectFund
	 * @param funSettleRule
	 */
	void addFundinfo(FundInfo fundInfo,Map<Long, FundSubject> subjectFund,Map<Long, List<FundSettleRule>> funSettleRule);
	/**
	 * 
	 * @param accAccountInfo
	 */
	void addAccAccountInfo(AccAccountInfo accAccountInfo);
}
