package com.ai.runner.center.omc.virtualdeduct.interfaces;

import java.util.List;
import java.util.Map;

import com.ai.runner.center.common.api.subject.param.FunSettleRule;
import com.ai.runner.center.common.api.subject.param.SubjectFund;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.viv.api.queryavailabledetail.param.FundInfo;
/**
 * 此接口继承了IAccountFeeFund，仅适用于opt产品
 * 并进一步完善了功能日后如需在opt产品中添加功能可在此接口添加
 * @author zhaixs
 *
 */
public interface IOptAccountFeeFund extends IAccountFeeFund{
	void addSummary(String acctmonth,AccInvoiceInfo accInvoice);
	void addDetail(String acctmonth,AccChargeInfo charge);
	void addRealCharge(String acctmonth,AccChargeInfo charge);
	void addFundinfo(FundInfo fundInfo,Map<Long, SubjectFund> subjectFund,Map<Long, List<FunSettleRule>> funSettleRule);
}
