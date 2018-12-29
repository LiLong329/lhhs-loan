package com.lhhs.loan.service.transport;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanProviderInfo;

public interface ProviderTransportService {
	
    /**
     * 
     * providerInfoUpdate:(碰碰旺后台调用接口报单人信息). <br/>
     * @author Administrator
     * @param loanProviderInfo
     * @return
     * @since JDK 1.8
     */
	public String providerInfoUpdate(LoanProviderInfo loanProviderInfo);
	/**
	 * 
	 * asynEmpUpdate:(客户经理账号信息推送). <br/>
	 * @author Administrator
	 * @param emp
	 * @since JDK 1.8
	 */
	public void asynEmpUpdate(String leAccount);
	
	
	/**
	 * 
	 * updateLoanApply:(放款申请信息推送). <br/>
	 * @author Administrator
	 * @param loanCapEarn
	 * @param loanCapInfo
	 * @param loanCapExpend
	 * @param orderNo
	 * @since JDK 1.8
	 */
	 public void updateLoanApply(List<LoanCapitalEarning> loanCapEarn,List<LoanCapitalInfo> loanCapInfo,List<LoanCapitalExpenditure> loanCapExpend,String orderNo, String isLoanAdd);
	
	/**
	 * 
	 * credentialsSend:(资质表信息更新推送). <br/>
	 * @author Administrator
	 * @param id
	 * @param orderCredentialsNo
	 * @param pathUrl
	 * @param type
	 * @since JDK 1.8
	 */
	 public void credentialsSend(Long id,Long orderCredentialsNo,String pathUrl,String type);
	 
	 
	 public String providerSend(LoanProviderInfo loanProviderInfo);
	 
	
}
