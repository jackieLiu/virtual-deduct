package com.ai.runner.center.omc.virtualdeduct.base;

import java.io.Serializable;

public final class Owner implements Serializable{
	private static final long serialVersionUID = -422474111194259818L;
	
	private String tenantId;
	private String owerType;
	private String owerId;
	
	public Owner(String tenantid,String owertyp,String owerid){
		this.tenantId = tenantid;
		this.owerType = owertyp;
		this.owerId = owerid;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getOwerType() {
		return owerType;
	}

	public String getOwerId() {
		return owerId;
	}
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setOwerType(String owerType) {
		this.owerType = owerType;
	}

	public void setOwerId(String owerId) {
		this.owerId = owerId;
	}
	
	public Owner() {
		super();
	}

	@Override
	public String toString() {
		return "Owner [tenantId=" + tenantId + ", owerType=" + owerType + ", owerId=" + owerId + "]";
	}

	
}
