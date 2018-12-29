/**
 * Project Name:loan-service
 * File Name:CustomerManagerService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月28日上午11:45:12
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.LoanBorrowerInfoVo;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;

/**
 * ClassName:CustomerManagerService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月28日 上午11:45:12 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface CustomerManagerService extends BaseService<LoanBorrowerInfoVo>{
	/**
     * selectBorrowerInfo:客户信息管理-借款人信息管理
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    //List<LoanBorrowerInfoVo> selectBorrowerInfo(Map<String, Object> map, Page page);
	/**
	 * selectCustNatureByCategory:根据分类名称查询客户性质的名称
	 * @author kernel.org
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanDictionary> selectCustNatureByCategory();
    
    /**
     * selectBorrowerBasicInfo:根据customer_id查询当前客户的基本信息和抵押物信息之基本信息查询
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
	LoanBorrowerInfoVo selectBorrowerBasicInfo(String customerId);
	
	/**
	 * updateLoanCustomerInfoByEntity:通过实体loanCustomerInfo更新loanCustomerInfo
	 * @author kernel.org
	 * @param loanCustomerInfo
	 * @return
	 * @since JDK 1.8
	 */
	int updateLoanCustomerInfoByEntity(LoanCustomerInfo entity);
    
    /**
	 * selectCarInfoList:根据customerId查询当前客户的车产信息
	 * @author kernel.org
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanCarInfo> selectCarInfoList(String customerId);
    
    /**
     * selectCarInfoList:根据customerId查询借款名下的房产信息
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
    List<LoanHouseInfo> selectHouseInfoList(String customerId);
    
    /**
     * updateBorrowerInfoWithBLOBs:通过客户基本信息的 LoanBorrowerInfoWithBLOBs 对象更新数据库表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int updateBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs entity);
    
    /**
     * saveMortgageInfo:保存抵押物信息
     * @author kernel.org
     * @param houseList
     * @param carList
     * @param customerId
     * @return
     * @since JDK 1.8
     */
    boolean saveMortgageInfo(List<LoanHouseInfo> houseList, List<LoanCarInfo> carList, String customerId);
    
    /**
     * updateLoanHouseInfo:更新房产记录
     * @author kernel.org
     * @param record
     * @return
     * @since JDK 1.8
     */
    boolean updateLoanHouseInfo(List<LoanHouseInfo> loanHouseInfoList, String customerId);
    
    /**
     * updateLoanCarInfo:更新车产记录
     * @author kernel.org
     * @param record
     * @return
     * @since JDK 1.8
     */
    boolean updateLoanCarInfo(List<LoanCarInfo> loanCarInfoList, String customerId);
    
    List<LoanProviderInfo> loanProviderList(Map<String, Object> map, Page page);
    
    LoanProviderInfo selProviderInfo(String providerno);
    
    int updateProvider(LoanProviderInfo loanProviderInfo);
    
    List<LoanProviderInfo> loanProviderCompanyList(Map<String, Object> map, Page page);
    
    List<LoanProviderInfo> loanProviderListExport(Map<String, Object> map);  
    
    List<LoanPayPlanCompany> loanPayPlanComplanyList(Map<String, Object> map, Page page);
    
    Page capitalPrepaidList(LoanPayPlan entity);
    
    Page capitalPrepaidList(LoanAdvance entity);
    
    Page queryAdvanceRecord(LoanAdvanceRecord record);
    
    LoanAdvance queryAdvancesum(LoanAdvance entity);
    
    Map<String, BigDecimal> queryTotalAmountSum(LoanAdvance entity);
    
    LoanAdvanceRecord queryTotalAmountSum(LoanAdvanceRecord entity);
    
    Map<String,BigDecimal> queryTotalAmount(LoanPayPlan entity);
    
    Map<String, Object> queryTotalAmount(Map<String, Object> map);
    
    List<LoanPayPlanCompany> queryPayaPlanList(LoanPayPlanCompany loanPayPlanCompany);
    
    List<LoanPayPlanCompany> loanPayPlanComplanyExport(Map<String, Object> map);
    
    List<LoanPayRecordCompany> loanPayRecordComplanyList(Map<String, Object> map, Page page);
    
    Map<String, Object> queryPaidTotal(Map<String, Object> map);
    
    List<LoanPayRecordCompany> loanPayRecordComplanyExport(Map<String, Object> map);
    
    /**
     * 已付清 部分
     * querySettleList:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @author Administrator
     * @param map
     * @param page
     * @return
     * @since JDK 1.8
     */
    List<LoanTransMain> querySettleList(Map<String, Object> map, Page page);
    
    List<LoanTransMain> querySettleListExport(Map<String, Object> map);
    
    Map<String, Object> querySettleListSum(Map<String, Object> map);
    
    List<LoanPayRecordCompany> querySettleDetail(String orderNo);
    
    LoanAdvance queryAdvance(String advanceId);
    
    LoanAdvance amountCompute(LoanAdvance advance);
    
    Map<String, Object> executeRepay(LoanAdvance advance);
    
    /**
     * loanProviderCompanyList:(根据). <br/>
     * @author zhanghui 
     * @param mobile
     * @param companyId
     * @return
     * @since JDK 1.8
     */
    List<LoanProviderInfo>  selectProviderByDeptId(String mobile,String companyId);

	boolean saveBankInfo(String[] bankLists, String customerId);

	List<LoanAccountCard> selectbankCarkList(String customerId);
    
}

