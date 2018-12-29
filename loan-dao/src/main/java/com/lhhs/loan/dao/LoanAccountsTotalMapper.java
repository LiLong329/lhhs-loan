package com.lhhs.loan.dao;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
/**
 * 账户余额汇总表
 * ClassName: LoanAccountsTotalMapper <br/>
 * date: 2017年8月3日 上午10:57:31 <br/>
 * @author dongfei
 * @version 
 * @since JDK 1.8
 */
public interface LoanAccountsTotalMapper extends CrudDao<LoanAccountsTotal>{
	
	/**
	 * 根据主键主账户账户余额、和可以余额
	 * @param entity
	 * @return
	 */
    int updateAmount(LoanAccountsTotal entity);

}