package com.ai.runner.center.omc.virtualdeduct.entity.ar;

import java.math.BigDecimal;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
/**
 * acc_account_info
 * @author zhaixs
 *
 */
public class AccAccountInfo {
	
	private Owner account;
	private BigDecimal balance;//对应数据库unsettled_bill_balance属性
	private String month;//unsettled_month
	public Owner getAccount() {
		return account;
	}
	public void setAccount(Owner account) {
		this.account = account;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "AccAccountInfo [account=" + account + ", balance=" + balance + ", month=" + month + "]";
	}
	
	
}
