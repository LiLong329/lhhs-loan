package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;
import com.lhhs.loan.dao.domain.vo.PayRecordVo;

/**
 * 待办事项中业务审批流程Service
 * 
 * @author kernel.org
 *
 */
public interface PaymanyTransService {
	/**
	 * 单条付款 executePay:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author Administrator
	 * @param payEntity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> executePayService(LoanPayRecordCompanyVo payEntity);

	/**
	 * 全部付款 executePayAll:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * 
	 * @author Administrator
	 * @param payEntity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> executePayAllService(LoanPayRecordCompanyVo payEntity);

	/**
	 * 逾期贴息 OverdueCalculation:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param payEntity
	 * @param flag
	 * @return
	 * @since JDK 1.8
	 */
	LoanPayRecordCompany isOverdue(LoanPayRecordCompanyVo payEntity,boolean flag);

	/**
	 * 违约金计算 fineCompute:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 *
	 * @author Administrator
	 * @param actualTransTime
	 * @param transMainId
	 * @param planId
	 * @return
	 * @since JDK 1.8
	 */
	LoanPayRecordCompany fineCompute(LoanPayRecordCompanyVo payEntity);
	
	/**
	 * 付款计划生成
	 * createPayPlanRecord:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author Administrator
	 * @param payEntity
	 * @return
	 * @since JDK 1.8
	 */
	Long createPayPlanRecord(LoanPayRecordCompanyVo payEntity);
	/**
	 * executePayPortion:部分付本
	 * @author suncj
	 * @param payEntity
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> executePayPortion(LoanPayRecordCompanyVo payEntity);

	Map<String, Object> getPayPortion(LoanPayRecordCompanyVo payEntity);

}
