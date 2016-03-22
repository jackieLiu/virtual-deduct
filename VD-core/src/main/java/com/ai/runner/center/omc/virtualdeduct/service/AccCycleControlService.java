package com.ai.runner.center.omc.virtualdeduct.service;

import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface AccCycleControlService {
	 AccCycleInfo query(String tenantid) throws OmcException ;
}
