package com.ai.runner.center.omc.virtualdeduct.service;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface AccAccountInfoService {
	AccAccountInfo query(Owner acct) throws OmcException;
}
