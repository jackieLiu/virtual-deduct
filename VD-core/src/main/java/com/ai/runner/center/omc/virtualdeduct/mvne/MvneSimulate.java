package com.ai.runner.center.omc.virtualdeduct.mvne;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundBookInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IMvneSimulate;
import com.ai.runner.center.omc.virtualdeduct.mvne.MvneAccountFeeFund;
import com.ai.runner.center.omc.virtualdeduct.service.AccAccountInfoService;
import com.ai.runner.center.omc.virtualdeduct.service.AccChargeService;
import com.ai.runner.center.omc.virtualdeduct.service.AccCycleControlService;
import com.ai.runner.center.omc.virtualdeduct.service.AccInvoiceService;
import com.ai.runner.center.omc.virtualdeduct.service.FundBookService;
import com.ai.runner.center.omc.virtualdeduct.service.FundSettleRuleService;
import com.ai.runner.center.omc.virtualdeduct.service.FundSubjectService;
import com.ai.runner.center.omc.virtualdeduct.service.RealChargeService;
import com.ai.runner.center.omc.virtualdeduct.utils.Cycle;
import com.ai.runner.center.omc.virtualdeduct.utils.DateUtils;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
public class MvneSimulate implements IMvneSimulate {
	@Autowired
	private  AccAccountInfoService accAccountInfoService;
	@Autowired
	private  AccChargeService accChargeService;
	@Autowired
	private  AccCycleControlService accCycleControlService;
	@Autowired
	private  AccInvoiceService accInvoiceService;
	@Autowired
	private  RealChargeService realChargeService;
	@Autowired
	private  FundSettleRuleService fundSettleRuleService;
	@Autowired
	private  FundSubjectService fundSubjectService;
	@Autowired
	private  FundBookService fundBookService;
	
	private  OmcObj owner = null;
	private AccAccountInfo accAccountInfo = null;
	private MvneAccountFeeFund accountFeeFund = null;
	private Cycle cycle = null;
	@Override
	public Boolean init(OmcObj owner, double extbalance) throws OmcException {
		this.owner = owner;
		AccCycleInfo accCycle = accCycleControlService.query(owner.getTenantid());
		if (accCycle == null){
			throw new OmcException("BWO", "模拟冲销取账期异常");
		}
		cycle = new Cycle(accCycle);
		accountFeeFund = new MvneAccountFeeFund(owner,cycle,extbalance);
		return true;
	}
	
	@Override
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
				List<AccChargeInfo> charges = realChargeService.query(new Owner(owner.getTenantid(),owner.getOwertype(),owner.getOwerid()), fromMonth);				
				if ((charges != null)&&(!charges.isEmpty())){
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
		Map<Long, FundSubject> subjectFundMap = new HashMap<Long,FundSubject>();
		Map<Long,List<FundSettleRule>> settleRuelMap = new HashMap<Long,List<FundSettleRule>>();
		
		try{
			FundInfo fundInfo = fundBookService.query(owner);
			if (fundInfo != null){
				List<FundBookInfo> fundBooks = fundInfo.getFundBooks();
				for (Iterator<FundBookInfo> iterator = fundBooks.iterator(); iterator.hasNext();) {
					FundBookInfo fundBook = (FundBookInfo) iterator.next();
					Long subjectfundid = fundBook.getSubjectId();
					FundSubject fundSubject = fundSubjectService.query(subjectfundid);
					if (fundSubject==null){
						throw new OmcException("getSubjectFund","没取到对应账本信息 Tenantid:" + owner.getTenantid() + "subjectfundid:" + Long.toString(subjectfundid));
					}
					if (subjectFundMap.get(fundSubject.getSubjectId()) == null){
						subjectFundMap.put(fundSubject.getSubjectId(), fundSubject);						
						if ("0".equals(fundSubject.getCanSettleAll())){
							List<FundSettleRule> settleRules = fundSettleRuleService.query(subjectfundid);
							if ((settleRules==null)||(settleRules.isEmpty())){
								throw new OmcException("getSubjectFund","没取到销账关系信息 Tenantid:" + owner.getTenantid() + "subjectfundid:" + Long.toString(fundSubject.getSubjectId()));
							}
							if (settleRuelMap.get(fundSubject.getSubjectId())==null){
								settleRuelMap.put(fundSubject.getSubjectId(), settleRules);
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
			e.printStackTrace();
			throw new OmcException("getFundBook",e.getMessage(),e.getCause());
		}		
	}
}
