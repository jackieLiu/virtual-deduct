package com.ai.runner.center.omc.virtualdeduct.service;

import java.util.List;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface FundResBookService {
	List<FundResBook> query(OmcObj owner) throws OmcException;
}
