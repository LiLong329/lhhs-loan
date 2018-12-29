package com.lhhs.loan.dao;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanFeesRecord;

public interface LoanFeesRecordMapper extends CrudDao<LoanFeesRecord>{
	/**
	 * 
	 * 汇总应付金额和实收金额
	 */
	LoanFeesRecord queryPaysum(LoanFeesRecord entity);

}