package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;

public interface LoanAccountsTransMapper extends CrudDao<LoanAccountsTrans>{
	/**
	 * 
	 * 统计交易明细收入金额、支出金额
	 */
	LoanAccountsTrans querySumAmount(LoanAccountsTrans parm);
	
	/**
	 * 分页获取用户交易记录信息
	 * @param params
	 * @return
	 */
	List<LoanAccountsTrans> getUserTransInfos(Map<String, Object> params);
	
	/**
	 * 获取用户交易记录信息总数
	 * @param params
	 * @return
	 */
	Integer getUserTransInfoCount(Map<String, Object> params);
	
	/**
	 * 公司资金交易记录列表
	 * @param entity
	 * @return
	 */
	public List<LoanAccountsTransVo> queryCompanyTransList(LoanAccountsTransVo entity);
	
	/**
	 * 公司资金交易记录总条数
	 * @param entity
	 * @return
	 */
	public int queryCompanyTransCount(LoanAccountsTransVo entity);
	
	/**
	 * 公司资金交易记录统计： 收入总额、支出总额
	 * @param entity
	 * @return
	 */
	public Map<String, BigDecimal> queryCompanyTransTotalAmount(LoanAccountsTransVo entity);
	
	/**
	 * 机构资金交易记录列表
	 * @param entity
	 * @return
	 */
	public List<LoanAccountsTransVo> queryOrganizationTransList(LoanAccountsTransVo entity);
	
	/**
	 * 机构资金交易记录总条数
	 * @param entity
	 * @return
	 */
	public int queryOrganizationTransCount(LoanAccountsTransVo entity);
	
	/**
	 * 机构资金交易记录统计： 收入总额、支出总额
	 * @param entity
	 * @return
	 */
	public Map<String, BigDecimal> queryOrganizationTransTotalAmount(LoanAccountsTransVo entity);
	
}