package com.ai.runner.center.omc.api.virtualdeduct.param;

import java.io.Serializable;

import com.ai.runner.base.vo.BaseInfo;
/**
 * 入参实体，由客户端传来
 * @author HP
 *
 */
public final class OmcObj extends BaseInfo implements Serializable  {

	private static final long serialVersionUID = -8316527956692995970L;
	
	private String tenantid;//租户id
	private String owertype;//信控对象id
	private String owerid;//信控对象类型
	private String businesscode;//资源类型
	private String producttype;//产品类型
	
	public OmcObj() {
		super();
	}
	public OmcObj(String tenantId,String owerType,String owerId,String producttype,String businesscode){
		this.tenantid = tenantId;
		this.owertype = owerType;
		this.owerid = owerId;
		this.businesscode=businesscode;
		this.producttype = producttype;
	}
	public String getTenantid() {
		return tenantid;
	}
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	public String getOwertype() {
		return owertype;
	}
	public void setOwertype(String owertype) {
		this.owertype = owertype;
	}
	public String getOwerid() {
		return owerid;
	}
	public void setOwerid(String owerid) {
		this.owerid = owerid;
	}
	
	public String getBusinesscode() {
		return businesscode;
	}
	public void setBusinesscode(String businesscode) {
		this.businesscode = businesscode;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	@Override
	public String toString() {
		return "OmcObj [tenantid=" + tenantid + ", owertype=" + owertype
				+ ", owerid=" + owerid + ", businessCode=" + businesscode
				+ ", productType=" + producttype + "]";
	}
	
//	
//	public OmcObj(JsonObject jObject){
//		this.tenantid = jObject.get(OmcCalKey.OMC_TENANT_ID).getAsString();
//		this.owertype = jObject.get(OmcCalKey.OMC_OWNER_TYPE).getAsString();
//		this.owerid = jObject.get(OmcCalKey.OMC_OWNER_ID).getAsString();
//		this.businesscode = jObject.get(OmcCalKey.OMC_BUSINESS_CODE).getAsString();
//	}
//	public OmcObj(String json){
//		Gson gson = new Gson();
//		JsonObject jObject = gson.fromJson(json, JsonObject.class);
//		
//		this.tenantid = jObject.get(OmcCalKey.OMC_TENANT_ID).getAsString();
//		this.owertype = jObject.get(OmcCalKey.OMC_OWNER_TYPE).getAsString();
//		this.owerid = jObject.get(OmcCalKey.OMC_OWNER_ID).getAsString();
//		this.businesscode = jObject.get(OmcCalKey.OMC_BUSINESS_CODE).getAsString();
//		
//	}
//	
	

	
}
