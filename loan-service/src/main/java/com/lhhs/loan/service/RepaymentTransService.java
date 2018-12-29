/**
 * Project Name:loan-service
 * File Name:RepaymentTransService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月8日上午10:30:12
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.vo.PayRecordVo;

/**
 * ClassName:RepaymentTransService <br/>
 * Function: 执行还款交易服务 <br/>
 * Date:     2017年8月8日 上午10:30:12 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface RepaymentTransService {

	/**
	 * repaymentHandler:当期执行还款服务<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> repaymentHandler(PayRecordVo entity);
	/**
	 * repaymentAllHandler:执行提前还款服务(全部还款) <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> repaymentAllHandler(PayRecordVo entity);
	/**
	 * OverdueCalculation:
	 * 还款逾期罚息计算、还款总额计算 <br/>
	 * ---本金逾期算本金，利息逾期算利息
	 * @author xiangfeng
	 * @param entity
	 * @param flag 是否更新逾期罚息 ture:更新
	 * @return
	 * @since JDK 1.8
	 */
	public LoanPayRecord overdueCalculation(PayRecordVo entity,boolean flag);
	/**
	 * overdueCalculationByCapital:
	 * 还款逾期罚息计算、还款总额计算 <br/>
	 * ---所有逾期--都按照剩余本金进行计算
	 * @author xiangfeng
	 * @param entity
	 * @param flag 是否更新逾期罚息 ture:更新
	 * @return
	 * @since JDK 1.8
	 */
	public LoanPayRecord overdueCalculationByCapital(PayRecordVo entity,boolean flag);
	/**
	 * generatePayPlan:
	 * 提前还款时，重新生成新的还款计划（新计划状态标识为提前） <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return 新还款计划id(生成失败返回0)
	 * @since JDK 1.8
	 */
	public Long generatePayPlan(PayRecordVo entity);
	/**
	 * compensateCalculation: 
	 * 提前还款违约金计算、还款总额计算<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public LoanPayRecord compensateCalculation(PayRecordVo entity);
	/**
	 * payCapitalCalculation:部分还本预期计算<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> payCapitalCalculation(PayRecordVo entity);
	/**
	 * repaymentCapitalHandler:执行部分还本 <br/>
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> repaymentCapitalHandler(PayRecordVo entity);
}

