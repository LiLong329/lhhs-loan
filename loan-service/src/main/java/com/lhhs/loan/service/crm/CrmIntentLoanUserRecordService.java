/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserRecordService.java
 * Package Name:com.lhhs.loan.service.crm
 * Date:2017年11月13日下午2:49:46
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.crm;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.CrmIntentLoanUserRecord;

/**
 * 回访记录管理service
 * @see
 */
public interface CrmIntentLoanUserRecordService extends BaseService<CrmIntentLoanUserRecord>{
	int savefollowRecord(CrmIntentLoanUserRecord record);
	int assignRecord(CrmIntentLoanUserRecord entity) ;
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<CrmIntentLoanUserRecord> queryList(Integer parentId);
	/**
	 * 查询回访记录（crm_intent_loan_user_record，）
	 * @param entity
	 * @return
	 */
	Page queryInviteRecord(CrmIntentLoanUserRecord entity);
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUserRecord> queryList(CrmIntentLoanUserRecord entity);
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	Page queryRecordInfo(CrmIntentLoanUserRecord entity);
	/**
	 * 查询详情列表
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUserRecord> queryInfoList(CrmIntentLoanUserRecord entity);
	
	public List<CrmIntentLoanUserRecord> getRecordInfoList(CrmIntentLoanUserRecord entity);

}

