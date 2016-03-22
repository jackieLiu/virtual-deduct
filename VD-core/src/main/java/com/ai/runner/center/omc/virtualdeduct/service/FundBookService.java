package com.ai.runner.center.omc.virtualdeduct.service;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface FundBookService {
	FundInfo query(OmcObj owner) throws OmcException;
}
