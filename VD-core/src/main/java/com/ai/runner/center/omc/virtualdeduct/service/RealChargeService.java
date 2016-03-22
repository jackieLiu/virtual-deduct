package com.ai.runner.center.omc.virtualdeduct.service;

import java.util.List;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface RealChargeService {
	List<AccChargeInfo> query(Owner acct,String accMonth) throws OmcException; 
}