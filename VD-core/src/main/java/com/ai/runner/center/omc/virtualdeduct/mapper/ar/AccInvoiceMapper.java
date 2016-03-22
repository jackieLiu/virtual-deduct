package com.ai.runner.center.omc.virtualdeduct.mapper.ar;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccInvoiceInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 对应acc_incoice_tpm 表
 * @author zhaixs
 *
 */
public interface AccInvoiceMapper {
	/**
	 * 
	 * @param acct
	 * @param accMonth
	 * @return
	 * @throws OmcException
	 */
	
	List<AccInvoiceInfo> query(@Param("owner")Owner acct,@Param("accmonth")String accMonth) throws OmcException;
}
