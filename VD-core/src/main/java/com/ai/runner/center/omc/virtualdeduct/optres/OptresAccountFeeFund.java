package com.ai.runner.center.omc.virtualdeduct.optres;

import java.math.BigDecimal;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IOptResAccountFeeFund;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.DateUtils;
import com.ai.runner.viv.api.queryresallowance.param.ResAmount;

public class OptresAccountFeeFund implements IOptResAccountFeeFund{
	//抵扣后情况
	private RealTimeBalance realTimeBalanceAfterDecuct;
	//资源类型信息
	private ResAmount amounts ;
	private OmcObj owner;
	@SuppressWarnings("unused")
	private BigDecimal extBalance;


	public OptresAccountFeeFund(OmcObj owner, Double extBalance) {
		super();
		this.owner = owner;
		this.extBalance = Cal.BigDecimalFromDouble(extBalance, FEESOURCE.FROMCHARGE);
	}

	@Override
	public RealTimeBalance process() {
		realTimeBalanceAfterDecuct=caclRealInfo();
		// TODO Auto-generated method stub
		return realTimeBalanceAfterDecuct;
	}

	public ResAmount getAmounts() {
		return amounts;
	}

	public void setAmounts(ResAmount amounts) {
		this.amounts = amounts;
	}

	private RealTimeBalance caclRealInfo(){
		RealTimeBalance realTimeBalance=new RealTimeBalance();
		realTimeBalance.setOwner(owner);
		realTimeBalance.setAcctmonth(DateUtils.getCurrMonth());
		realTimeBalance.setFstunsettlemon(DateUtils.getCurrMonth());
		realTimeBalance.setUnIntoBill(new BigDecimal(0.0));
		realTimeBalance.setBalance(new BigDecimal(amounts.getTotalAmount()));
		realTimeBalance.setRealbalance(new BigDecimal(amounts.getTotalAmount()));
		realTimeBalance.setRealbill(new BigDecimal(0.0));
		realTimeBalance.setUnsettlebill(new BigDecimal(0.0));
		realTimeBalance.setExpandinfo("{}");
		realTimeBalance.setCreditline(new BigDecimal(0.0));
		return realTimeBalance;
	}
}




