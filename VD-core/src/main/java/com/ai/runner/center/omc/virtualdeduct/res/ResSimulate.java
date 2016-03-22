package com.ai.runner.center.omc.virtualdeduct.res;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IResSimulate;
import com.ai.runner.center.omc.virtualdeduct.service.FundResBookService;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
public class ResSimulate implements IResSimulate {
	@Autowired
	private FundResBookService fundResBookService;
	private ResAccountFeeFund resAccountFeeFund=null;
	private  OmcObj owner = null;

	@Override
	public Boolean init(OmcObj owner, double extbalance) throws OmcException {
		// TODO Auto-generated method stub
		this.owner = owner;
		resAccountFeeFund=new ResAccountFeeFund(owner,extbalance);
		return true;
	}

	@Override
	public RealTimeBalance process() throws OmcException {
		// TODO Auto-generated method stub
		List<FundResBook> fundResBooks = fundResBookService.query(owner);
		BigDecimal balance=new BigDecimal("0.00");
		for (FundResBook fundResBook : fundResBooks) {
			balance=fundResBook.getTotalAmount().subtract(fundResBook.getTransferAmount()).subtract(fundResBook.getUsedAmount());
			fundResBook.setBalanceAmount(balance);
			resAccountFeeFund.addFundResBook(fundResBook.getBookId(), fundResBook);
		}
		return resAccountFeeFund.process();
	}

}
