package com.lhhs.loan.dao;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectAmount;
/**
 * 账户总账表（科目账）
 * ClassName: LoanAccountsSubjectAmountMapper <br/>
 * date: 2017年8月3日 上午10:54:46 <br/>
 * @author dongfei
 * @version 
 * @since JDK 1.8
 */
public interface LoanAccountsSubjectAmountMapper extends CrudDao<LoanAccountsSubjectAmount>{
	/**
	 * 根据主键更新账户科目余额数据
	 * @param entity
	 * @return
	 */
    int updateAmount(LoanAccountsSubjectAmount entity);
}