package com.ai.runner.center.omc.virtualdeduct.service;

import com.ai.runner.center.omc.virtualdeduct.entity.abm.FundSubject;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;

/**
 * 
 * @author HP
 *
 */
public interface FundSubjectService {
	FundSubject query(Long fundsubject) throws OmcException;
}