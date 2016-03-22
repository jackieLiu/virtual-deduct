package com.ai.runner.center.omc.virtualdeduct.entity.ar;

import java.sql.Timestamp;
/**
 * acc_cycle
 * @author zhaixs
 *
 */
public class AccCycleInfo {

	private String tenant_id;
	private String cycle_month;//cycle_month
	private Timestamp settle_date;//settle_date

	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getCycle_month() {
		return cycle_month;
	}
	public void setCycle_month(String cycle_month) {
		this.cycle_month = cycle_month;
	}

	public Timestamp getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(Timestamp settle_date) {
		this.settle_date = settle_date;
	}
	@Override
	public String toString() {
		return "AccCycle: " + ", tenant_id=" + tenant_id + ", cycle_month=" + cycle_month
				+ ", settle_date=" + settle_date + "]";
	}

}
