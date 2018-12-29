/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserService.java
 * Package Name:com.lhhs.loan.service.crm
 * Date:2017年11月14日上午10:18:23
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserService.java
 * Package Name:com.lhhs.loan.service.crm
 * Date:2017年11月14日上午10:18:23
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.service.crm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lhhs.bump.domain.ChannelApply;
import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanEmp;

/**
 * ClassName:CrmIntentLoanUserService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月14日 上午10:18:23 <br/>
 * @author   zhanghui
 * @version
 * @since    JDK 1.8
 * @see
 */

public interface CrmIntentLoanUserService  extends BaseService<CrmIntentLoanUser> {

	Map<String,Object> save( LoanEmp  loanEmp,CrmIntentLoanUser entity);
	CrmIntentLoanUser findByMobile(String mobile);

	List<CrmIntentLoanUser> getLoanUserList(CrmIntentLoanUser entity);
	Map<String,Object> saveList(List<Map<String,Object>> list);
	/**
	 * 根据条件统计意向客户（以客户经理为维度）
	 * @param entity
	 * @return
	 */
	Page getcrmIntentUserCountList(CrmIntentLoanUser entity);
	/**
	 * 根据条件统计客户总量  约见总量 签约总量
	 * @param entity
	 * @return
	 */
	Map<String, Object> getVariousTotalCount(CrmIntentLoanUser entity);
	/**
	 * 根据时间统计客户总量  约见总量 签约总量
	 * @param time
	 * @return
	 */
	Map<String, Object> getCrmAppointCountByTime(Date time,String timeUnit,CrmIntentLoanUser entity);
	/**
	 * 意向客户统计导出
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUser> getcrmIntentUserCountExportList(CrmIntentLoanUser entity);
	
	void intentUserPullTask(List<ChannelApply> list, List<Map<String, Object>> empList);
	
}