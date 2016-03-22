package com.ai.runner.center.omc.virtualdeduct.entity.abm;
/**
 * fun_settle_rule
 * @author zhaixs
 *
 */
public final class FundSettleRule {
	private long feeSubjectId;//fee_Subject_Id
	private long subjectId;//subject_Id
	private String tenantId;
	public long getFeeSubjectId() {
		return feeSubjectId;
	}
	public void setFeeSubjectId(long feeSubjectId) {
		this.feeSubjectId = feeSubjectId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	@Override
	public String toString() {
		return "FundSettleRela [feeSubjectId=" + feeSubjectId + ", subjectId=" + subjectId + ", tenantId=" + tenantId
				+ "]";
	}
	
}
