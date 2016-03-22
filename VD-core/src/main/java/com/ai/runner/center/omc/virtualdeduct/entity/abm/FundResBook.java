package com.ai.runner.center.omc.virtualdeduct.entity.abm;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 对应表fun_res_book
 * @author zhaixs
 *
 */
public class FundResBook {
  private BigDecimal balanceAmount;
  private Long bookId;
  private Timestamp effectTime;
  private Timestamp expireTime;
  private Long subjectId;
  private BigDecimal totalAmount;
  private BigDecimal transferAmount;
  private BigDecimal usedAmount;
public BigDecimal getBalanceAmount() {
	return balanceAmount;
}
public void setBalanceAmount(BigDecimal balanceAmount) {
	this.balanceAmount = balanceAmount;
}
public Long getBookId() {
	return bookId;
}
public void setBookId(Long bookId) {
	this.bookId = bookId;
}
public Timestamp getEffectTime() {
	return effectTime;
}
public void setEffectTime(Timestamp effectTime) {
	this.effectTime = effectTime;
}
public Timestamp getExpireTime() {
	return expireTime;
}
public void setExpireTime(Timestamp expireTime) {
	this.expireTime = expireTime;
}
public Long getSubjectId() {
	return subjectId;
}
public void setSubjectId(Long subjectId) {
	this.subjectId = subjectId;
}
public BigDecimal getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}
public BigDecimal getTransferAmount() {
	return transferAmount;
}
public void setTransferAmount(BigDecimal transferAmount) {
	this.transferAmount = transferAmount;
}
public BigDecimal getUsedAmount() {
	return usedAmount;
}
public void setUsedAmount(BigDecimal usedAmount) {
	this.usedAmount = usedAmount;
}
@Override
public String toString() {
	return "FundResBook [balanceAmount=" + balanceAmount + ", bookId=" + bookId
			+ ", effectTime=" + effectTime + ", expireTime=" + expireTime
			+ ", subjectId=" + subjectId + ", totalAmount=" + totalAmount
			+ ", transferAmount=" + transferAmount + ", usedAmount="
			+ usedAmount + "]";
}

  
}
