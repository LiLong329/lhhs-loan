package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.CrmIntentLoanUserRecord;

public interface CrmIntentLoanUserRecordMapper extends CrudDao<CrmIntentLoanUserRecord>{
	/**
	 * 查询回访记录（crm_intent_loan_user_record，）
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUserRecord> queryInviteRecord(CrmIntentLoanUserRecord entity);

	/**
	 * 回访记录数量
	 * @param entity
	 * @return
	 */
	int queryInviteRecordCount(CrmIntentLoanUserRecord entity);

	/**
	 * 回访记录详情
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUserRecord> queryRecordInfo(CrmIntentLoanUserRecord entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	int queryRecordInfoCount(CrmIntentLoanUserRecord entity);
    
}