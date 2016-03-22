package com.ai.runner.center.omc.virtualdeduct.service;

import java.util.List;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

public interface AccInvoiceService {
	List<AccInvoiceInfo> query(Owner acct,String accMonth) throws OmcException;
}
