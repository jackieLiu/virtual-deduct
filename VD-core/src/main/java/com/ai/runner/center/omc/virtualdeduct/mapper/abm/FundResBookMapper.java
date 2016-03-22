package com.ai.runner.center.omc.virtualdeduct.mapper.abm;

import java.util.List;

import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundResBook;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

/**
 * fun_res_book
 * @author zhaixs
 *
 */
public interface FundResBookMapper {
	/**
	 * 
	 * @param owner
	 * @return
	 */
	List<FundResBook> query(OmcObj owner) throws OmcException;
}
