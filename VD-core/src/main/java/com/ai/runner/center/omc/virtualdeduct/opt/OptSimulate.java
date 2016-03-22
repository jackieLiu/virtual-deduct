package com.ai.runner.center.omc.virtualdeduct.opt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.runner.base.exception.CallerException;
//import com.ai.runner.center.balance.api.fundquery.interfaces.IFundQuerySV;
//import com.ai.runner.center.balance.api.fundquery.param.AccountIdParam;
//import com.ai.runner.center.balance.api.fundquery.param.FundBook;
//import com.ai.runner.center.balance.api.fundquery.param.FundInfo;
import com.ai.runner.center.common.api.subject.interfaces.IFunSettleRuleSV;
import com.ai.runner.center.common.api.subject.interfaces.IGnSubjectQuerySV;
import com.ai.runner.center.common.api.subject.param.FunSettleRule;
import com.ai.runner.center.common.api.subject.param.SubjectFund;
import com.ai.runner.center.common.api.subject.param.SubjectIdParam;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IOptSimulate;
import com.ai.runner.center.omc.virtualdeduct.service.AccAccountInfoService;
import com.ai.runner.center.omc.virtualdeduct.service.AccChargeService;
import com.ai.runner.center.omc.virtualdeduct.service.AccCycleControlService;
import com.ai.runner.center.omc.virtualdeduct.service.AccInvoiceService;
import com.ai.runner.center.omc.virtualdeduct.service.RealChargeService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cycle;
import com.ai.runner.center.omc.virtualdeduct.utils.DateUtils;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
import com.ai.runner.viv.api.queryavailabledetail.interfaces.IQueryAvailableDetailSV;
import com.ai.runner.viv.api.queryavailabledetail.param.AccountIdParam;
import com.ai.runner.viv.api.queryavailabledetail.param.FundBook;
import com.ai.runner.viv.api.queryavailabledetail.param.FundInfo;
@Service
public class OptSimulate implements IOptSimulate {
	@Autowired
	private    IQueryAvailableDetailSV fundQuerySV; 
	@Autowired
	private     IGnSubjectQuerySV gnSubjectQuerySV ;
	@Autowired
	private     IFunSettleRuleSV iFunSettleRuleSV ;
	@Autowired
	private      AccCycleControlService accCycleControl;
	@Autowired
	private      RealChargeService realChargeService;
	@Autowired
	private      AccAccountInfoService accAccountInfoService;
	@Autowired
	private      AccInvoiceService accInvoiceService;
	@Autowired
	private     AccChargeService chargeService; 
	
	private  OmcObj owner = null;
	private AccAccountInfo accAccountInfo = null;
	private OptAccountFeeFund accountFeeFund = null;
	private Cycle cycle = null;
	
	
	public Boolean init(OmcObj owner,double extbalance) throws OmcException {
		this.owner = owner;

		AccCycleInfo accCycle = accCycleControl.query(owner.getTenantid());
		if (accCycle == null){
			throw new OmcException("BWO", "模拟冲销取账期异常");
		}
		cycle = new Cycle(accCycle);
		
		accountFeeFund = new OptAccountFeeFund(owner,cycle,extbalance);
		
		return true;
	}

	
	public RealTimeBalance process() throws OmcException {
        //获取欠费汇总
		accAccountInfo = accAccountInfoService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()));
		if (accAccountInfo == null){
			throw new OmcException("BWO", "模拟冲销取欠费汇总异常");
		}
		accountFeeFund.addAccAccountInfo(accAccountInfo);
		//获取账单汇总
        getAccInvoice(cycle,accAccountInfo.getMonth());
        //获取账单明细
        getOwnCharge(cycle,accAccountInfo.getMonth());
        //获取账本
        getFundBook();
        //获取实时费用
        getRealCharge(cycle);
        
        return accountFeeFund.process();
		
	}

	/**
	 * @throws OmcException 
	 * 
	* @Title: getRealCharge 
	* @Description: 获取实时费用 
	* @param @param cycle
	* @param @return    设定文件 
	* @return List<Charge>    返回类型 
	* @throws
	 */
	private void getRealCharge(Cycle cycle) throws OmcException{
		try{
				
			List<AccChargeInfo> realCharges1 = realChargeService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()),cycle.getCurrCycle());
			
			if ((realCharges1 != null)&&(!realCharges1.isEmpty())){
				
				for (Iterator<AccChargeInfo> iterator = realCharges1.iterator(); iterator.hasNext();) {
					AccChargeInfo charge = (AccChargeInfo) iterator.next();
					accountFeeFund.addRealCharge(cycle.getCurrCycle(), charge);
				}
			}
			
			if (cycle.getTwoMonthsReal()){
				
				List<AccChargeInfo> realCharges2 = realChargeService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()),cycle.getLastCycle());
				if ((realCharges2 != null)&&(!realCharges2.isEmpty())){
					for (Iterator<AccChargeInfo> iterator = realCharges2.iterator(); iterator.hasNext();) {
						AccChargeInfo charge = (AccChargeInfo) iterator.next();
						accountFeeFund.addRealCharge(cycle.getLastCycle(), charge);
					}
				}
			}
		}catch (OmcException e){
			throw e;
		}catch (Exception e){
			throw new OmcException("getRealCharge",e.getMessage(),e.getCause());
		}
	}
	
	/**
	 * @throws OmcException 
	 * 
	* @Title: getAccInvoice 
	* @Description: 加载欠费汇总 
	* @param @param cycle
	* @param @param unsettledMonths
	* @param @return    设定文件 
	* @return Map<String,List<AccInvoice>>    返回类型 
	* @throws
	 */
	private void getAccInvoice(Cycle cycle,String unsettledMonths) throws OmcException{
		try{
			String fromMonth = unsettledMonths;
			String toMonth = "";
			if (cycle.getTwoMonthsReal()){
				toMonth = cycle.getLast2Cycle();
			}else{
				toMonth = cycle.getLastCycle();
			}
			
			while (true) {
				
				if (fromMonth.compareTo(toMonth) > 0){
					break;
				}
				List<AccInvoiceInfo> accInvoices = accInvoiceService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()), fromMonth);
				
				if ((accInvoices != null)&&(!accInvoices.isEmpty())){
					for (Iterator<AccInvoiceInfo> iterator = accInvoices.iterator(); iterator.hasNext();) {
						AccInvoiceInfo accInvoice = (AccInvoiceInfo) iterator.next();
						
						accountFeeFund.addSummary(fromMonth, accInvoice);					
					}
				}
			    
				fromMonth = DateUtils.monthsAdd(fromMonth, 1);
			}
		}catch (OmcException e){
			throw e;
		}catch (Exception e){
			throw new OmcException("getRealCharge",e.getMessage(),e.getCause());
		}
	}
	/**
	 * @throws OmcException 
	 * 
	* @Title: getOwnCharge 
	* @Description: 加载欠费明细 
	* @param @param cycle
	* @param @param unsettledMonths
	* @param @return    设定文件 
	* @return Map<String,List<Charge>>    返回类型 
	* @throws
	 */
	private void getOwnCharge(Cycle cycle,String unsettledMonths) throws OmcException{
		String fromMonth = unsettledMonths;
		String toMonth = "";
		try{
			if (cycle.getTwoMonthsReal()){
				toMonth = cycle.getLast2Cycle();
			}else{
				toMonth = cycle.getLastCycle();
			}
			
			while (true) {
				if (fromMonth.compareTo(toMonth) > 0){
					break;
				}
				List<AccChargeInfo> charges = chargeService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()), fromMonth);
				
				if ((charges != null)&&(!charges.isEmpty())){
					System.out.println(charges.toString());
					for (Iterator<AccChargeInfo> iterator = charges.iterator(); iterator.hasNext();) {
						AccChargeInfo charge = (AccChargeInfo) iterator.next();
						accountFeeFund.addDetail(fromMonth, charge);					
					}
				}
			    
				fromMonth = DateUtils.monthsAdd(fromMonth, 1);
			}
		}catch (OmcException e){
			throw e;
		}catch (Exception e){
			throw new OmcException("getRealCharge",e.getMessage(),e.getCause());
		}
	}
	/**
	 * 
	* @Title: getFundBook 
	* @Description: 获取账户余额,账本类型，结算关系
	* @param @return    设定文件 
	* @return FundInfo    返回类型 
	* @throws
	 */
	private void  getFundBook() throws OmcException{
		
		AccountIdParam accountIdParam = new AccountIdParam();
		accountIdParam.setAccountId(Long.parseLong(owner.getOwerid()));
		accountIdParam.setTenantId(owner.getTenantid());
		Map<Long, SubjectFund> subjectFundMap = new HashMap<Long,SubjectFund>();
		Map<Long,List<FunSettleRule>> settleRuelMap = new HashMap<Long,List<FunSettleRule>>();
		
		try{

			FundInfo fundInfo = fundQuerySV.queryAvailableDetail(accountIdParam);
			
			if (fundInfo != null){
				List<FundBook> fundBooks = fundInfo.getFundBooks();
				for (Iterator<FundBook> iterator = fundBooks.iterator(); iterator.hasNext();) {
					FundBook fundBook = (FundBook) iterator.next();
					Long subjectfundid = fundBook.getSubjectId();
					SubjectIdParam subjectIdParam = new SubjectIdParam();
					subjectIdParam.setTenantId(owner.getTenantid());
					subjectIdParam.setSubjectId(subjectfundid);
					SubjectFund subjectfund = gnSubjectQuerySV.getSubjectFund(subjectIdParam);
					if (subjectfund==null){
						throw new OmcException("getSubjectFund","没取到对应账本信息 Tenantid:" + owner.getTenantid() + "subjectfundid:" + Long.toString(subjectfundid));
					}
					if (subjectFundMap.get(subjectfund.getSubjectId()) == null){
						subjectFundMap.put(subjectfund.getSubjectId(), subjectfund);
						
						if (subjectfund.getCanSettleAll().equals("0")){
							SubjectIdParam sIdParam = new SubjectIdParam();
							sIdParam.setTenantId(owner.getTenantid());
							sIdParam.setSubjectId(subjectfund.getSubjectId());
							List<FunSettleRule> settleRules = iFunSettleRuleSV.querySettleRule(subjectIdParam);
							if ((settleRules==null)||(settleRules.isEmpty())){
								throw new OmcException("getSubjectFund","没取到销账关系信息 Tenantid:" + owner.getTenantid() + "subjectfundid:" + Long.toString(subjectfund.getSubjectId()));
							}

							if (settleRuelMap.get(subjectfund.getSubjectId())==null){
								settleRuelMap.put(subjectfund.getSubjectId(), settleRules);
							}

						}
					}
	
				}
				accountFeeFund.addFundinfo(fundInfo, subjectFundMap, settleRuelMap);
			}
		}catch (CallerException e){
			e.printStackTrace();
			throw new OmcException(e.getErrorCode(),e.getMessage(),e.getCause());
		}catch (Exception e){
			//e.printStackTrace();
			throw new OmcException("getFundBook",e.getMessage(),e.getCause());
		}
		
	}
}
