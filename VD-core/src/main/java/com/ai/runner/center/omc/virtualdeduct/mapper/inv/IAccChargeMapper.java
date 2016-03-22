package com.ai.runner.center.omc.virtualdeduct.mapper.inv;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccChargeInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * acc_charge_tmp
 * @author zhaixs
 *
 */
public interface IAccChargeMapper {
	/**
	 * 
	 * @param acct
	 * @param accMonth
	 * @return
	 * @throws OmcException
	 */
	List<AccChargeInfo> query(@Param("owner")Owner acct,@Param("accmonth")String accMonth) throws OmcException;
}

