package com.ai.runner.center.omc.virtualdeduct.mapper.abm;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * fun_fund_book
 * @author zhaixs
 *
 */
public interface FundBookMapper {
	/**
	 * 根据入参信息查出FundInfo
	 * @param owner
	 * @return
	 * @throws OmcException
	 */
	FundInfo query(OmcObj owner) throws OmcException;
}
