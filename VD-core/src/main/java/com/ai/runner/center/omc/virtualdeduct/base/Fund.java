package com.ai.runner.center.omc.virtualdeduct.base;

import java.math.BigDecimal;
import java.sql.Timestamp;

public final class Fund {
	private String bookId;
	private BigDecimal balance; 
	private Timestamp effectDate; 
	private Timestamp expireDate; 
	private String subjectId;
	private String subsId;
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Timestamp getEffectDate() {
		return effectDate==null?null:(Timestamp)effectDate.clone();
	}
	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}
	public Timestamp getExpireDate() {
		return expireDate==null?null:(Timestamp)expireDate.clone();
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubsId() {
		return subsId;
	}
	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}
	@Override
	public String toString() {
		return "Fund [bookId=" + bookId + ", balance=" + balance + ", effectDate=" + effectDate + ", expireDate="
				+ expireDate + ", subjectId=" + subjectId + ", subsId=" + subsId + "]";
	}


}


