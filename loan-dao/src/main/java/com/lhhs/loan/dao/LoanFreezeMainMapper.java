package com.lhhs.loan.dao;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanFreezeMain;

public interface LoanFreezeMainMapper extends CrudDao<LoanFreezeMain>{
	/**
	 * 
	 * 更新冻结总额和解冻总额
	 */
	int updateAmount(LoanFreezeMain entity);
	/**
	 * 汇总冻结金额、解冻金额、剩余冻结金额
	 */
	LoanFreezeMain querySum(LoanFreezeMain entity);
}