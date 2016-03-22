package com.ai.runner.center.omc.virtualdeduct.res;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;




import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IResAccountFeeFund;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;

public class ResAccountFeeFund implements IResAccountFeeFund{
	//抵扣后情况
	private RealTimeBalance realTimeBalanceAfterDecuct;
	//资源类型信息
	private Map<Long,FundResBook> fundResBookMap=new HashMap<Long, FundResBook>();
	private OmcObj owner;
	private BigDecimal extBalance;


	public ResAccountFeeFund(OmcObj owner, Double extBalance) {
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

	@Override
	public void addFundResBook(Long bookid, FundResBook resBook) {
		// TODO Auto-generated method stub
		fundResBookMap.put(bookid, resBook);
	}
	private RealTimeBalance caclRealInfo(){
		RealTimeBalance realTimeBalance=new RealTimeBalance();
		realTimeBalance.setOwner(owner);
		//realTimeBalance.setAcctMonth(acctMonth);
		//realTimeBalance.setBusinessCode("00");
		realTimeBalance.setUnIntoBill(extBalance);
		BigDecimal balance=new BigDecimal("0.00");
		for(Long key:fundResBookMap.keySet()){
			FundResBook fundResBook = fundResBookMap.get(key);
			balance=balance.add(fundResBook.getBalanceAmount());
		}
		realTimeBalance.setBalance(balance);
		return realTimeBalance;
	}
}




