package com.ai.runner.center.omc.virtualdeduct.entity.abm;
/**
 * 表fun_subject 和表fun_subject_fund
 * @author HP
 *
 */
public final class FundSubject {
	private String    canSettleAll;//         
	private String    fundType;   //fund_type 
	private String    isCash;  	//is_cash
	private Long      subjectId  ;//subject_Id
	private String    subjectName;//subject_Name
	private String    subjectType;//subject_Type
	private String    unitName   ;//unit_Name
	private String    useMode    ;//use_mode
	private Long      usePri     ;//use_pri
	private String    validType  ;//valid_type
	private String tenantId;
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCanSettleAll() {
		return canSettleAll;
	}
	public void setCanSettleAll(String canSettleAll) {
		this.canSettleAll = canSettleAll;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getIsCash() {
		return isCash;
	}
	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUseMode() {
		return useMode;
	}
	public void setUseMode(String useMode) {
		this.useMode = useMode;
	}
	public Long getUsePri() {
		return usePri;
	}
	public void setUsePri(Long usePri) {
		this.usePri = usePri;
	}
	public String getValidType() {
		return validType;
	}
	public void setValidType(String validType) {
		this.validType = validType;
	}
	@Override
	public String toString() {
		return "FundSubject [canSettleAll=" + canSettleAll + ", fundType=" + fundType + ", isCash=" + isCash
				+ ", subjectId=" + subjectId + ", subjectName=" + subjectName + ", subjectType=" + subjectType
				+ ", unitName=" + unitName + ", useMode=" + useMode + ", usePri=" + usePri + ", validType=" + validType
				+ ", tenantId=" + tenantId + "]";
	}


}
