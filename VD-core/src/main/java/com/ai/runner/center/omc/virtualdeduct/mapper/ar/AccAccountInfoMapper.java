package com.ai.runner.center.omc.virtualdeduct.mapper.ar;
import com.ai.runner.center.omc.virtualdeduct.base.Owner;
import com.ai.runner.center.omc.virtualdeduct.entity.ar.AccAccountInfo;
import com.ai.runner.center.omc.virtualdeduct.utils.OmcException;
/**
 * 对应数据库的acc_account_info 操作
 * @author zhaixs
 *
 */
public interface AccAccountInfoMapper {
	/**
	 * 
	 * @param acct 参数Owner入参对象
	 * @return 返回AccAccountInfo实体 
	 * @throws OmcException
	 */
	AccAccountInfo query(Owner acct) throws OmcException;
}
