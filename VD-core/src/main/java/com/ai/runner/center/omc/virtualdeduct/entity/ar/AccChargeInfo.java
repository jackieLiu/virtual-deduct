package com.ai.runner.center.omc.virtualdeduct.entity.ar;

import java.math.BigDecimal;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
/**
 * acc_change_tmp
 * @author zhaixs
 *
 */
public class AccChargeInfo {
	private Owner owner;
	private String subsid;//subscription_id
	private String  subjectid;//subject_id
	private BigDecimal total;//total_amount
	private BigDecimal disc;//disc_total_amount
	private BigDecimal adjust;//adjust_afterwards
	private BigDecimal balance;//balance
	private String acctmonth;//acct_month
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public String getSubsid() {
		return subsid;
	}
	public void setSubsid(String subsid) {
		this.subsid = subsid;
	}
	public String getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getDisc() {
		return disc;
	}
	public void setDisc(BigDecimal disc) {
		this.disc = disc;
	}
	public BigDecimal getAdjust() {
		return adjust;
	}
	public void setAdjust(BigDecimal adjust) {
		this.adjust = adjust;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getAcctmonth() {
		return acctmonth;
	}
	public void setAcctmonth(String acctmonth) {
		this.acctmonth = acctmonth;
	}
	@Override
	public String toString() {
		return "Charge [owner=" + owner + ", subsid=" + subsid + ", subjectid=" + subjectid + ", total=" + total
				+ ", disc=" + disc + ", adjust=" + adjust + ", balance=" + balance + ", acctmonth=" + acctmonth + "]";
	}

	
}
