package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;

/**
 * 资金账目管理服务
 * @author Administrator
 *
 */
public interface FundsAccountsManagerService {

	/**
	 * 分页查询用户资产负债信息
	 * @param params
	 * @param page
	 * @return
	 */
	public List<LoanAccountInfo> getAssetsLiabilitiesInfos(Map<String, Object> params, Page page);
	
	/**
	 * 分页查询用户账户总览信息
	 * @param params
	 * @param page
	 * @return
	 */
	public List<LoanAccountInfo> getAccountOverviewInfos(Map<String, Object> params, Page page);

}

