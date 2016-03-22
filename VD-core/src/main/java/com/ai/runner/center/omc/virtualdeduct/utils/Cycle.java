package com.ai.runner.center.omc.virtualdeduct.utils;

import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;

public class Cycle {
	private String currCycle;
	private String lastCycle;
	private String nextCycle;
	private String last2Cycle;
	private String last3Cycle;
	private Boolean twoMonthsReal;
	
	public Cycle(AccCycleInfo cycle){
		currCycle = cycle.getCycle_month();
		lastCycle = DateUtils.monthsAdd(currCycle, -1);
		last2Cycle = DateUtils.monthsAdd(currCycle, -2);
		last3Cycle = DateUtils.monthsAdd(currCycle, -3);
		nextCycle = DateUtils.monthsAdd(currCycle, 1);
		String settleDate = DateUtils.format(cycle.getSettle_date(),"YYYYMMDD");
		if (!settleDate.equals(currCycle + "01")){
			twoMonthsReal = true;
		}else{
			twoMonthsReal = false;
		}
	}

	public String getCurrCycle() {
		return currCycle;
	}

	public String getLastCycle() {
		return lastCycle;
	}

	public String getNextCycle() {
		return nextCycle;
	}

	public String getLast2Cycle() {
		return last2Cycle;
	}

	public String getLast3Cycle() {
		return last3Cycle;
	}

	public Boolean getTwoMonthsReal() {
		return twoMonthsReal;
	}

	
}
