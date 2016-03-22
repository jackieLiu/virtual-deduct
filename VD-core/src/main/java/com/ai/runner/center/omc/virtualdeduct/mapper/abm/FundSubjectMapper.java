package com.ai.runner.center.omc.virtualdeduct.mapper.abm;
import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 对应数据的fun_subject
 * @author zhaixs
 *
 */
public interface FundSubjectMapper {
	/**
	 * 根据id查出FundSubject信息
	 * @param fundsubjectId  
	 * @return
	 * @throws OmcException
	 */
	FundSubject query(Long fundsubjectId) throws OmcException;
}
