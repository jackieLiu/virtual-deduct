package com.ai.runner.center.omc.virtualdeduct.mvne;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;
import com.ai.runner.center.omc.virtualdeduct.base.Fund;
import com.ai.runner.center.omc.virtualdeduct.constant.FEESOURCE;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundBookInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSettleRule;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.entity.BwoParaForFundFee;
import com.ai.runner.center.omc.virtualdeduct.interfaces.IMvneAccountFeeFund;
import com.ai.runner.center.omc.virtualdeduct.utils.Cal;
import com.ai.runner.center.omc.virtualdeduct.utils.Cycle;
import com.ai.runner.center.omc.virtualdeduct.utils.DateUtils;


public final class MvneAccountFeeFund implements IMvneAccountFeeFund{
	private static Logger logger = LoggerFactory.getLogger(MvneAccountFeeFund.class);
	private OmcObj owner;
	//欠费明细
	private SortedMap<String,SortedMap<String,List<AccChargeInfo>>> ownDetailMap = new TreeMap<String,SortedMap<String,List<AccChargeInfo>>>();
	//欠费汇总
	private SortedMap<String,List<AccInvoiceInfo>> ownsummaryMap = new TreeMap<String,List<AccInvoiceInfo>>();
	//实时费用 
	private SortedMap<String,List<AccChargeInfo>> realChargesMap =  new TreeMap<String,List<AccChargeInfo>>();
	//余额信息
	private FundInfo fundInfo;
	//账本类型
	private Map<Long, FundSubject> subjectFundMap;
	//抵扣关系
	private Map<Long,List<FundSettleRule>> fundSettleRuleMap;
	//账户未销账总述
	private AccAccountInfo accAccountInfo;
	//抵扣前情况
	private RealTimeBalance realTimeBalanceBeforeDecuct;
	//抵扣后情况
	private RealTimeBalance realTimeBalanceAfterDecuct;
	//外部费用
	private BigDecimal extBalance;
	//
	private Cycle cycle;

	public MvneAccountFeeFund(OmcObj owner,Cycle cyclepara,Double extbalance) {
		super();
		this.owner = owner;
		this.cycle = cyclepara;
		this.extBalance = Cal.BigDecimalFromDouble(extbalance, FEESOURCE.FROMCHARGE);
	}
	@Override
	public RealTimeBalance process(){
		//抵扣前费用
		realTimeBalanceBeforeDecuct = caclRealInfo();
		logger.info("Ower:["+owner.toString() + "]虚扣前账本费用情况"+ realTimeBalanceBeforeDecuct.toString());
		//抵扣欠费
		processOwn();
		//抵扣实时费用
		processRealCharge();
		//抵扣后情况
		realTimeBalanceAfterDecuct = caclRealInfo();
		logger.info("Ower:["+owner.toString() + "]虚扣后账本费用情况"+ realTimeBalanceBeforeDecuct.toString());
		return realTimeBalanceAfterDecuct;
	}
	
	private void processOwn(){

		List<FundBookInfo> fundBooks = fundInfo.getFundBooks();
		
		Iterator<FundBookInfo> iterator = fundBooks.iterator();
		
		while (iterator.hasNext()) {
			Fund fundBook = convert((FundBookInfo) iterator.next());
			
			if (fundBook.getBalance().signum()<=0) 
				continue; 
			//先对欠费进行销账
			//按月份从小到大
			for(String key:ownsummaryMap.keySet()){
				
				List<AccInvoiceInfo> accInvoices = ownsummaryMap.get(key);
				
				for (Iterator<AccInvoiceInfo> it = accInvoices.iterator(); it.hasNext();) {
					AccInvoiceInfo accInvoice = (AccInvoiceInfo) it.next();
					if (accInvoice.getBalance().signum() == 0){
						continue;
					}
					List<AccChargeInfo> charges = ownDetailMap.get(key).get(accInvoice.getSubsid());
					BigDecimal sumdeduct = new BigDecimal(0.00);
					for (Iterator<AccChargeInfo> itcharge = charges.iterator(); itcharge.hasNext();) {
						AccChargeInfo charge = (AccChargeInfo) itcharge.next();
						if (charge.getBalance().signum() == 0){
							continue;
						}
						//进行抵扣
						BwoParaForFundFee bwoParaForFundFee =  deduction(fundBook,charge);
						if (bwoParaForFundFee.getDeducteD()){
							charge.setBalance(charge.getBalance().subtract(bwoParaForFundFee.getDeductecharge()));
							fundBook.setBalance(fundBook.getBalance().subtract(bwoParaForFundFee.getDeductecharge()));
							accInvoice.setBalance(accInvoice.getBalance().subtract(bwoParaForFundFee.getDeductecharge()));
							sumdeduct = sumdeduct.add(bwoParaForFundFee.getDeductecharge());
						}
					}
				}
				
			}
		}
	}
	/**
	 * 
	* @Title: caclRealInfo 
	* @Description: 对账户余额和费用情况进行汇总 
	* @param @return    设定文件 
	* @return RealTimeBalance    返回类型 
	* @throws
	 */
	private RealTimeBalance caclRealInfo(){
		RealTimeBalance realTimeBalance = new RealTimeBalance();
		realTimeBalance.setAcctmonth(cycle.getCurrCycle());
		realTimeBalance.setOwner(owner);
		//realTimeBalance.setBusinessCode("00");
		realTimeBalance.setUnIntoBill(extBalance);
		
		//当前账期
		realTimeBalance.setAcctmonth(cycle.getCurrCycle());
		//欠费月数
		realTimeBalance.setFstunsettlemon(accAccountInfo.getMonth());
		//欠费账单
		realTimeBalance.setUnsettlebill(accAccountInfo.getBalance());
		//欠费月数
		realTimeBalance.setUnsettlemons(DateUtils.monthDiffs(accAccountInfo.getMonth(),cycle.getCurrCycle()));
		//当前余额
		Long sumBalance = 0L;
		List<FundBookInfo> fundBooks = fundInfo.getFundBooks();
		if (fundBooks != null){
			for (Iterator<FundBookInfo> iterator = fundBooks.iterator(); iterator.hasNext();) {
				FundBookInfo fundBook = (FundBookInfo) iterator.next();
				sumBalance =  sumBalance + fundBook.getBalance();
			}
		}
		realTimeBalance.setBalance(Cal.BigDecimalFromLong(sumBalance, FEESOURCE.FROMBALANCE));
		//实时费用求和
		BigDecimal sumRealBill = new BigDecimal("0.00");
		
		for(String key:realChargesMap.keySet()){
			List<AccChargeInfo> charges = realChargesMap.get(key);
			if (charges != null){
				for (Iterator<AccChargeInfo> iterator = charges.iterator(); iterator.hasNext();) {
					AccChargeInfo charge = (AccChargeInfo) iterator.next();
					sumRealBill = sumRealBill.add(charge.getBalance())  ;
				}
			}
		}
		realTimeBalance.setRealbill(sumRealBill);
		//欠费账单求和
		BigDecimal sumUnsettleBill =  new BigDecimal("0.00");
		
		for (String key:ownsummaryMap.keySet()){
			List<AccInvoiceInfo> accInvoices = ownsummaryMap.get(key);
			if (accInvoices != null){
				for (Iterator<AccInvoiceInfo> iterator = accInvoices.iterator(); iterator.hasNext();) {
					AccInvoiceInfo accInvoice = (AccInvoiceInfo) iterator.next();
					sumUnsettleBill = sumUnsettleBill.add(accInvoice.getBalance());
				}
			}
		}
		realTimeBalance.setUnsettlebill(sumUnsettleBill);
		realTimeBalance.setRealbalance(realTimeBalance.getBalance().subtract(realTimeBalance.getUnIntoBill()).subtract(realTimeBalance.getRealbill()).subtract(realTimeBalance.getUnsettlebill()));
		return realTimeBalance;
	}
	
	private void processRealCharge(){

		List<FundBookInfo> fundBooks = fundInfo.getFundBooks();
		
		Iterator<FundBookInfo> iterator = fundBooks.iterator();
		
		while (iterator.hasNext()) {
			Fund fundBook = convert((FundBookInfo) iterator.next());
			
			if (fundBook.getBalance().signum()==0) 
				continue; 
			//先对欠费进行销账
			//按月份从小到大
			for(String key:realChargesMap.keySet()){
				
				List<AccChargeInfo> charges  = realChargesMap.get(key);
				
				BigDecimal sumdeduct = new BigDecimal("0.00");
				
				for (Iterator<AccChargeInfo> itcharge = charges.iterator(); itcharge.hasNext();) {
					AccChargeInfo charge = (AccChargeInfo) itcharge.next();
					if (charge.getBalance().signum()==0){
						continue;
					}
					//进行抵扣
					BwoParaForFundFee bwoParaForFundFee =  deduction(fundBook,charge);
					if (bwoParaForFundFee.getDeducteD()){
						charge.setBalance(charge.getBalance().subtract( bwoParaForFundFee.getDeductecharge()));
						fundBook.setBalance(fundBook.getBalance().subtract(bwoParaForFundFee.getDeductecharge()));
						sumdeduct = sumdeduct.add(bwoParaForFundFee.getDeductecharge()) ;
						fundInfo.setBalance(fundInfo.getBalance() - sumdeduct.multiply(new BigDecimal("1000")).longValue());
					}
				}
			}
		}
	}
	/**
	 * 
	* @Title: deduction 
	* @Description: 余额抵扣 
	* @param @param fundBook
	* @param @param charge
	* @param @return    设定文件 
	* @return Map<FundBook,Charge>    成功销账后返回销账后的 账本 和 费用，没有销账返回 null
	* @throws
	 */
	private BwoParaForFundFee deduction(Fund fundBook,AccChargeInfo charge){
		Boolean isCanUse = true;
		
		if (isCanUse){
			if ((fundBook.getBalance().signum() == 0)||(charge.getBalance().signum() == 0)){
				isCanUse = false;
			}
		}

		//1.比较日期
		if (isCanUse){
			if ((DateUtils.format(fundBook.getEffectDate(), "YYYYMM").compareTo(charge.getAcctmonth()) > 0)
					||(DateUtils.format(fundBook.getExpireDate(), "YYYYMM").compareTo(charge.getAcctmonth()) < 0)) {
				isCanUse = false;
			}
		}

		//2.比较用户专款
		if (isCanUse){
			if ((!fundBook.getSubsId().equals("0")) && (charge.getSubsid().compareTo(fundBook.getSubsId()) != 0)){
				isCanUse = false;
			}
		}
		//3.比较科目
		if (isCanUse){
			FundSubject subjectFund = subjectFundMap.get(fundBook.getSubjectId());
			
			if (subjectFund.getCanSettleAll().equals("1")){
				 List<FundSettleRule> funSettleRules = fundSettleRuleMap.get(fundBook.getSubjectId());
				 if ((funSettleRules == null)||(funSettleRules.isEmpty())){
					 isCanUse = false;
				 }else{
					 Boolean bFind = false;
					 for (Iterator<FundSettleRule> it = funSettleRules.iterator(); it.hasNext();) {
						FundSettleRule funSettleRule = (FundSettleRule) it.next();
						
						if (charge.getSubjectid().compareTo(Long.toString(funSettleRule.getFeeSubjectId())) == 0){
							bFind = true;
							break;
						}
					}
					if (!bFind){
						 isCanUse = false;
					}
				 }
			}

		}
		
		//4.账本优先级（暂不实现）
		//5.余额和费用比较
		
		BigDecimal decuct = new BigDecimal(0.00);
		Boolean decucted = false;
		if (isCanUse){
			//进行销账
			if (fundBook.getBalance().compareTo(charge.getBalance()) >= 0 ){
				decuct = charge.getBalance();
				fundBook.setBalance(fundBook.getBalance().subtract(charge.getBalance()));
				charge.setBalance(new BigDecimal(0.00));
			}else{
				decuct =  charge.getBalance();
				fundBook.setBalance(new BigDecimal(0.00));
				charge.setBalance(charge.getBalance().subtract(fundBook.getBalance()));
			}
			decucted = true;
		}
		BwoParaForFundFee bwoParaForFundFee = new BwoParaForFundFee();
		bwoParaForFundFee.setCharge(charge);
		bwoParaForFundFee.setFund(fundBook);
		bwoParaForFundFee.setDeducteD(decucted);
		bwoParaForFundFee.setDeductecharge(decuct);
		return bwoParaForFundFee;
	}
	
	public void addSummary(String acctmonth,AccInvoiceInfo accInvoice){
		List<AccInvoiceInfo>  accInvoices = ownsummaryMap.get(acctmonth);
		if (accInvoices == null){
			accInvoices = new ArrayList<AccInvoiceInfo>();
			accInvoices.add(accInvoice);
			ownsummaryMap.put(acctmonth, accInvoices);	
		}else{
			accInvoices.add(accInvoice);
		}
	}

	public void addDetail(String acctmonth,AccChargeInfo charge){
		SortedMap<String,List<AccChargeInfo>>  cMap = ownDetailMap.get(acctmonth);
		if (cMap == null){
    			cMap = new TreeMap<String,List<AccChargeInfo>>();
    			List<AccChargeInfo> charges = new ArrayList<AccChargeInfo>();
				charges.add(charge);
				cMap.put(charge.getSubsid(), charges);
	
		}else{
			List<AccChargeInfo> charges = cMap.get(charge.getSubsid());
			if (charges == null){
				charges = new ArrayList<AccChargeInfo>();
				charges.add(charge);
				cMap.put(charge.getSubsid(), charges);
			}else{
				charges.add(charge);
//				cMap.put(charge.getSubs_id(), charges);
			}
			
		}
	}
	
	public void addRealCharge(String acctmonth,AccChargeInfo charge){
		
		List<AccChargeInfo>  charges = realChargesMap.get(acctmonth);
		if (charges == null){
			charges = new ArrayList<AccChargeInfo>();
			charges.add(charge);
			realChargesMap.put(acctmonth, charges);
		}else{
			charges.add(charge);
		}
	}
	
	public void addFundinfo(FundInfo fundInfo,Map<Long, FundSubject> subjectFund,Map<Long, List<FundSettleRule>> funSettleRule){
		this.fundInfo = fundInfo;
		this.subjectFundMap = subjectFund;
		this.fundSettleRuleMap = funSettleRule;
	}
	public void addAccAccountInfo(AccAccountInfo accAccountInfo){
		this.accAccountInfo = accAccountInfo;
	}
	
	private Fund convert(FundBookInfo fundBook){
		Fund fund = new Fund();
		fund.setBalance(Cal.BigDecimalFromLong(fundBook.getBalance(),FEESOURCE.FROMBALANCE));
		fund.setBookId(Long.toString(fundBook.getBookId()));
		fund.setEffectDate(fundBook.getEffectDate());
		fund.setExpireDate(fundBook.getExpireDate());
		fund.setSubjectId(Long.toString(fundBook.getSubjectId()));
		fund.setSubsId(Long.toString(fundBook.getSubsId()));
		return fund;
	}
}
