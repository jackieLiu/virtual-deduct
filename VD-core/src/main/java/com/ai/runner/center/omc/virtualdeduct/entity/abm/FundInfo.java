package com.ai.runner.center.omc.virtualdeduct.entity.abm;

import java.util.List;
/**
 * 
 * @author zhaixs
 *
 */
public final class FundInfo {
	private long accountId;
	private long balance;//fundBooks合计出来的balance
	private List<FundBookInfo> fundBooks;
	private String tenantId;
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public List<FundBookInfo> getFundBooks() {
		return fundBooks;
	}
	public void setFundBooks(List<FundBookInfo> fundBooks) {
		this.fundBooks = fundBooks;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	@Override
	public String toString() {
		return "FundInfo [accountId=" + accountId + ", balance=" + balance + ", tenantId=" + tenantId + "]";
	}
	
}
