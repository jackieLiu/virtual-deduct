package com.ai.runner.center.omc.api.virtualdeduct.interfaces;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.omc.api.virtualdeduct.param.OmcObj;
import com.ai.runner.center.omc.api.virtualdeduct.param.RealTimeBalance;


/**
 * 此类对应的服务类型为dubbo服务
 * @author zhaixs
 *
 */
@Path(value="/virtualdeduct")
@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON})
public interface DubboCancelAccountSV   {
	/**
	 * 
	 * @param OmcObj 
	 * @return RealTimeBalance
	 * @author zhaixs
     * @ApiCode OMC-0001
     * @RestRelativeURL virtualdeduct/cancelAccountProcess
	 */
	@POST
	@Path(value="/cancelAccountProcess")
	RealTimeBalance cancelAccountProcess(OmcObj owner) throws CallerException;
	
}
