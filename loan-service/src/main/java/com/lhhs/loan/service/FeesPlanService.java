package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;

public interface FeesPlanService {
	Page findPageByEntity(Page page, FeesPlanVo params);
	
	String feesPlanService(LoanFeesPlan loanFeesPlan);
	
	/**
	 * queryFeesPlanByOrderNo: 
	 *  根据订单号组装收入列表数据<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanFeesPlan> queryFeesPlanInByOrderNo(String orderNo);
	
	/**
	 * queryFeesPlanOutByOrderNo: 
	 *  根据订单号组装支出列表数据<br/>
	 *
	 * @author chenyinhui
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanFeesPlan> queryFeesPlanOutByOrderNo(String orderNo);
	
}
