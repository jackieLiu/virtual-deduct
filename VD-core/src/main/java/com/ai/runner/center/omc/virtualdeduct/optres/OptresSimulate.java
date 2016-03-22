package com.ai.runner.center.omc.virtualdeduct.optres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IOptResSimulate;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
import com.ai.runner.viv.api.queryresallowance.interfaces.IQueryResAllowanceSV;
import com.ai.runner.viv.api.queryresallowance.param.ResAmount;
import com.ai.runner.viv.api.queryresallowance.param.ResAmountQuery;
@Service
public class OptresSimulate implements IOptResSimulate {
	@Autowired
	private IQueryResAllowanceSV iResQuerySV;
	private OptresAccountFeeFund resAccountFeeFund=null;
	private  OmcObj owner = null;
	private static final Logger logger = LoggerFactory.getLogger(OptresSimulate.class);
	@Override
	public Boolean init(OmcObj owner, double extbalance) throws OmcException {
		// TODO Auto-generated method stub
		this.owner = owner;
		resAccountFeeFund=new OptresAccountFeeFund(owner,extbalance);
		return true;
	}
	@Override
	public RealTimeBalance process() throws OmcException {
		// TODO Auto-generated method stub
		ResAmountQuery res= new ResAmountQuery();
		res.setOwnerId(Long.valueOf(owner.getOwerid()));
		res.setOwnerType(Integer.valueOf(owner.getOwertype()));
		if(owner.getBusinesscode()=="voice"){
		res.setResourceType(10);
		}else
		if(owner.getBusinesscode()=="data"){
			System.out.println("1111");
			res.setResourceType(60);
		}else
		if(owner.getBusinesscode()=="sm"){
			res.setResourceType(50);
		}
		res.setTenantId(owner.getTenantid());
		logger.info("进入IQueryResAllowanceSV服务拿参数为"+res.toString());
		ResAmount amounts = iResQuerySV.queryResAllowance(res);
		logger.info("从IQueryResAllowanceSV服务拿到的返回值为"+amounts.getTotalAmount());
		resAccountFeeFund.setAmounts(amounts);
		return resAccountFeeFund.process();
	}

}
