package com.ai.runner.center.omc.api.virtualdeduct.param;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 出参实体，经过服务处理后返还给客户端的实体
 * @author HP
 *
 */
public class RealTimeBalance implements Serializable {
	private static final long serialVersionUID = 690621398847099331L;
	private OmcObj owner;
	//private String businessCode;//
	private String servicetype;
	private String returncode;
	private BigDecimal realbalance;//抵扣后预存
	private BigDecimal realbill;//当前实时欠费0
	private BigDecimal balance;//当前总可销账余额
	private BigDecimal unsettlebill;//当前历史欠费0
	private BigDecimal unIntoBill;//外部费用0
	private String fstunsettlemon;//最早欠费月份 当前月
	private int unsettlemons;//未交缴费月数0
	private BigDecimal creditline;//信贷额度0
	private String acctmonth;//当前账期 当前月
	private String  expandinfo;//扩展信息 {}
	public OmcObj getOwner() {
		return owner;
	}
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	public String getReturncode() {
		return returncode;
	}
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	public BigDecimal getRealbalance() {
		return realbalance;
	}
	public void setRealbalance(BigDecimal realbalance) {
		this.realbalance = realbalance;
	}
	public BigDecimal getRealbill() {
		return realbill;
	}
	public void setRealbill(BigDecimal realbill) {
		this.realbill = realbill;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getUnsettlebill() {
		return unsettlebill;
	}
	public void setUnsettlebill(BigDecimal unsettlebill) {
		this.unsettlebill = unsettlebill;
	}
	public BigDecimal getUnIntoBill() {
		return unIntoBill;
	}
	public void setUnIntoBill(BigDecimal unIntoBill) {
		this.unIntoBill = unIntoBill;
	}
	public String getFstunsettlemon() {
		return fstunsettlemon;
	}
	public void setFstunsettlemon(String fstunsettlemon) {
		this.fstunsettlemon = fstunsettlemon;
	}
	public int getUnsettlemons() {
		return unsettlemons;
	}
	public void setUnsettlemons(int unsettlemons) {
		this.unsettlemons = unsettlemons;
	}
	public BigDecimal getCreditline() {
		return creditline;
	}
	public void setCreditline(BigDecimal creditline) {
		this.creditline = creditline;
	}
	public String getAcctmonth() {
		return acctmonth;
	}
	public void setAcctmonth(String acctmonth) {
		this.acctmonth = acctmonth;
	}
	public String getExpandinfo() {
		return expandinfo;
	}
	public void setExpandinfo(String expandinfo) {
		this.expandinfo = expandinfo;
	}
	public void setOwner(OmcObj owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "RealTimeBalance [owner=" + owner + ", servicetype="
				+ servicetype + ", returncode=" + returncode + ", realbalance="
				+ realbalance + ", realbill=" + realbill + ", balance="
				+ balance + ", unsettlebill=" + unsettlebill + ", unIntoBill="
				+ unIntoBill + ", fstunsettlemon=" + fstunsettlemon
				+ ", unsettlemons=" + unsettlemons + ", creditline="
				+ creditline + ", acctmonth=" + acctmonth + ", expandinfo="
				+ expandinfo + "]";
	}
	
	
	


}