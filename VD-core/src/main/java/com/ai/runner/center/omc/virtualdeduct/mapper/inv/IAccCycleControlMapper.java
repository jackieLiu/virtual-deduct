package com.ai.runner.center.omc.virtualdeduct.mapper.inv;

import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 对应acc_cycle_control表
 * @author zhaixs
 *
 */
public interface IAccCycleControlMapper {
	/**
	 * 
	 * @param tenantid 由于数据库中没有 tenantid属性 所有用status代替了
	 * @return
	 * @throws OmcException
	 */
	AccCycleInfo query(String tenantid) throws OmcException;
}
