package com.ai.runner.center.omc.virtualdeduct.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccCycleInfo;
import com.ai.runner.center.omc.virtualdeduct.mapper.inv.IAccCycleControlMapper;
import com.ai.runner.center.omc.virtualdeduct.service.AccCycleControlService;
import com.ai.runner.center.omc.virtualdeduct.utils.DateUtils;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
@Service
@Transactional
public class AccCycleControlServiceImpl implements AccCycleControlService {
	 @Autowired
	 private IAccCycleControlMapper accCycleControlMapper;
	private static final Logger logger = LoggerFactory.getLogger(AccCycleControlServiceImpl.class);
	
	@Override
	public AccCycleInfo query(String tenantid) throws OmcException {
		logger.info("进入AccCycleControlServiceImpl的query方法，参数为String:"+tenantid);
		if("".equals(tenantid)||tenantid==null){
			throw new OmcException("outprama", "获取tenantid为null" );
		}
		try {
				AccCycleInfo accCycleInfo = accCycleControlMapper.query(tenantid);
			if (accCycleInfo == null){
				throw new OmcException("AccCycleControl", "获取账期异常，没有有效账期");
			}
			accCycleInfo.setSettle_date(DateUtils.getTimestamp(accCycleInfo.getSettle_date().toString(), "yyyy-MM-dd HH:mm:ss"));
		    logger.info("AccCycleControlServiceImpl的query方法,返回值为AccCycleInfo:"+accCycleInfo);
			return accCycleInfo;
		} catch (Exception e) {
			throw new OmcException("AccCycleControl",e.getMessage(),e.getCause());
		}
	}
}
