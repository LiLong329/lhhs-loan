package com.lhhs.loan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAccountsTransMapper;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.service.FundsAccountsManagerService;

/**
 * 资金账目管理实现
 * 
 * @author Administrator
 *
 */
@Service
public class FundsAccountsManagerServiceImpl implements FundsAccountsManagerService {
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanAccountsTransMapper loanAccountsTransMapper;
	
	@Override
	public List<LoanAccountInfo> getAssetsLiabilitiesInfos(Map<String, Object> params, Page page) {
		List<LoanAccountInfo> assetsLiabilitiesInfoList = loanAccountInfoMapper.getAssetsLiabilitiesInfos(params);
		Integer assetsLiabilitiesInfoCount = loanAccountInfoMapper.getAssetsLiabilitiesInfoCount(params);
		if (page != null) {
			page.setResultList(assetsLiabilitiesInfoList);
			page.setTotalCount(assetsLiabilitiesInfoCount);
		}
		return assetsLiabilitiesInfoList;
	}

	@Override
	public List<LoanAccountInfo> getAccountOverviewInfos(Map<String, Object> params, Page page) {
		List<LoanAccountInfo> accountsTotalList = loanAccountInfoMapper.getAccountOverviewInfos(params);
		Integer accountsTotalCount = loanAccountInfoMapper.getAccountOverviewInfoCount(params);
		if (page != null) {
			page.setResultList(accountsTotalList);
			page.setTotalCount(accountsTotalCount);
		}
		return accountsTotalList;
	}

}