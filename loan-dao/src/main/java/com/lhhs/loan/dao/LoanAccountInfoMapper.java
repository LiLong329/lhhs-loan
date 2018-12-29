package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;

public interface LoanAccountInfoMapper extends CrudDao<LoanAccountInfo>{
	
	/**
     * 分页获取用户资产负债信息
     * @param params
     * @return
     */
	List<LoanAccountInfo> getAssetsLiabilitiesInfos(Map<String, Object> params);
	/**
	 * 获取用户资产负债信息总数
	 * @param params
	 * @return
	 */
	Integer getAccountsTotalCount(Map<String, Object> params);
	
	List<LoanAccountCard> queryCompanyAccount(Map<String, Object> map);
	
	Integer queryCompanyAccountCount(Map<String, Object> map);
	
	LoanAccountInfo  queryCompanyAccountDetail(String accountId);
	
    
	Integer getAssetsLiabilitiesInfoCount(Map<String, Object> params);
	
	/**
	 * 分页获取用户账户总览信息
	 * @param params
	 * @return
	 */
	List<LoanAccountInfo> getAccountOverviewInfos(Map<String, Object> params);
	
	/**
	 * 获取用户账户总览信息总数
	 * @param params
	 * @return
	 */
	Integer getAccountOverviewInfoCount(Map<String, Object> params);
	
	/**
	 * 
	 * 公司资金账户总览
	 * @param loanEmp
	 * @return
	 */
	public List<LoanAccountsTotal> queryCompanyAccountList(LoanAccountsTotal entity);
	
	/**
	 * 
	 * 公司资金账户总条数
	 * @param loanEmp
	 * @return
	 */
	public int queryCompanyAccountListCount(LoanAccountsTotal entity);
	
	/**
	 * 
	 * 机构资金账户总览列表
	 * @param entity
	 * @return
	 */
	public List<LoanAccountsTotal> queryOrganizationAccountList(LoanAccountsTotal entity);
	
	/**
	 * 
	 * 机构资金账户总条数
	 * @param entity
	 * @return
	 */
	public int queryOrganizationAccountCount(LoanAccountsTotal entity);
	
	/**
	 * 
	 * 机构资金账户总余额
	 * @param entity
	 * @return
	 */
	public Map<String,Object> queryOrganizationAccountTotalAmount(LoanAccountsTotal entity);
	
	
	/**
     * 根据手机号或机构ID查询账户信息
     * @param params
     * @return
     */
	Map<String, Object> getAccountsByMobileOrOwnerId(Map<String, Object> params);
	
	/**
	 * 根据账户所有者编码查询账户信息
	 * @param params
	 * @return
	 */
	public LoanAccountInfo selectByOwnerId(String ownerId);
	
	List<LoanAccountInfo> queryCompanyInfo(String companyId);
	
	List<LoanAccountInfo> selectByBankCardNo(String bankCardNo);
	
	/**
	 * 根据账户所有者编码和账户类型查询账户信息
	 * @param params
	 * @return
	 */
	public LoanAccountInfo selectByOwnerIdAndAccountType(Map<String, Object> params);
}