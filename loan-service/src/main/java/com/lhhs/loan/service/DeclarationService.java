package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanProviderInfo;

/**
 * 报单信息接口
 * @ClassName: DeclarationService
 * @Description: TODO
 * @author xiangfeng
 * @date 2017年6月6日 上午11:50:55
 *
 */
public interface DeclarationService {
	
	/**
	 * 报单信息插入
	 * @param loanOrderInfo
	 * @param loanOrderInfoDetail
	 * @param borrower
	 * @return
	 */
	public Map<String,Object> addDeclaration(LoanOrderInfo loanOrderInfo,LoanOrderInfoDetail loanOrderInfoDetail,LoanOrderBorrowerExtendWithBLOBs borrower);
	
	/**
	 * 根据当前登录用户查询报单人
	 * @param loanEmp
	 * @return List<LoanProviderInfo>
	 */
	public List<LoanProviderInfo> selectProviderByEmp(LoanEmp loanEmp);


}
