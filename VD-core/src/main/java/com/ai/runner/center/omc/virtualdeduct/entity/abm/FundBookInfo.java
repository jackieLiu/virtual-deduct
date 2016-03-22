package com.ai.runner.center.omc.virtualdeduct.entity.abm;

import java.sql.Timestamp;
/**
 * fun_fund_book
 * @author zhaixs
 *
 */
public final class FundBookInfo {
	private long balance;//balance
	private long bookId;//book_id
	private Timestamp effectDate;//effect_date
	private Timestamp expireDate;//expire_date
	private long subjectId;//subject_id
	private long subsId;//subs_freeze_id
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public Timestamp getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}
	public Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public long getSubsId() {
		return subsId;
	}
	public void setSubsId(long subsId) {
		this.subsId = subsId;
	}
	@Override
	public String toString() {
		return "FundBook [balance=" + balance + ", bookId=" + bookId + ", effectDate=" + effectDate + ", expireDate="
				+ expireDate + ", subjectId=" + subjectId + ", subsId=" + subsId + "]";
	}

}
