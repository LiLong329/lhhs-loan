/**
 * Project Name:loan-service
 * File Name:AuditProcessService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年10月17日下午2:19:54
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.audit;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;

/**
 * ClassName:AuditProcessService <br/>
 * Function: 工作流方式审核流程service <br/>
 * Date:     2017年10月17日 下午2:19:54 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AuditProcessService {

	Map<String, Object> handler(AuditParamVo auditParamVo);
	/**
	 * updateAmountPaidThis:(更新本次放款金额). <br/>
	 * @author chenyinhui
	 * @param loanCapitalInfo
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> updateAmountPaidThis(String loanCapitalInfo);
	
	LoanAccountCard getBankByCard(String id);

}

