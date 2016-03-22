package com.ai.runner.center.omc.virtualdeduct.entity;
import java.math.BigDecimal;

import com.ai.runner.center.omc.virtualdeduct.base.Fund;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;

public class BwoParaForFundFee {
	private AccChargeInfo charge;
	private Fund fund;
	private Boolean DeducteD;
	private BigDecimal deductecharge;
	private String businessCode;
	public AccChargeInfo getCharge() {
		return charge;
	}
	public void setCharge(AccChargeInfo charge) {
		this.charge = charge;
	}
	public Fund getFund() {
		return fund;
	}
	public void setFund(Fund fund) {
		this.fund = fund;
	}
	public Boolean getDeducteD() {
		return DeducteD;
	}
	public void setDeducteD(Boolean deducteD) {
		DeducteD = deducteD;
	}
	public BigDecimal getDeductecharge() {
		return deductecharge;
	}
	public void setDeductecharge(BigDecimal deductecharge) {
		this.deductecharge = deductecharge;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	@Override
	public String toString() {
		return "BwoParaForFundFee [charge=" + charge + ", fund=" + fund + ", DeducteD=" + DeducteD + ", deductecharge="
				+ deductecharge + ", businessCode=" + businessCode + "]";
	}
	

	
}
