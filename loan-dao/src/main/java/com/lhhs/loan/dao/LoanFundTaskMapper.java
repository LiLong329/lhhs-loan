package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanFundTaskWithBLOBs;

public interface LoanFundTaskMapper extends CrudDao<LoanFundTaskWithBLOBs> {

	/** 进行资金审批时将会签记录保存于会签记录表 **/
	int insertFundTaskWithEntity(LoanFundTaskWithBLOBs entity);
	
	/** 查询项目审核记录表(审批流程节点) **/
	List<LoanFundTaskWithBLOBs> selectFundHandledRecord(String orderNo);
}