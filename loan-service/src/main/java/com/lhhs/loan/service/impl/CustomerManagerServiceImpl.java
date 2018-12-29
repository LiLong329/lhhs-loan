/**
 * Project Name:loan-service
 * File Name:CustomerManagerServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月28日上午11:45:24
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanAdvanceMapper;
import com.lhhs.loan.dao.LoanAdvanceRecordMapper;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCarInfoMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanDictionaryMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanHouseInfoMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyMapper;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.LoanPayRecordCompanyMapper;
import com.lhhs.loan.dao.LoanProviderInfoMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.LoanBorrowerInfoVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:CustomerManagerServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月28日 上午11:45:24 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service("customerManagerService")
public class CustomerManagerServiceImpl implements CustomerManagerService{

	private static final Logger logger = LoggerFactory.getLogger(CustomerManagerServiceImpl.class);
	@Autowired
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper; 
	@Autowired
	private LoanDictionaryMapper loanDictionaryMapper; 
	@Autowired
	private LoanCarInfoMapper loanCarInfoMapper; 
	@Autowired
	private LoanHouseInfoMapper loanHouseInfoMapper; 
	@Autowired
	private LoanProviderInfoMapper loanProviderInfoMapper;
	@Autowired
	private LoanPayPlanCompanyMapper loanPayPlanCompanyMapper;
	@Autowired
	private LoanPayRecordCompanyMapper loanPayRecordCompanyMapper;
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private LoanCustomerBaseInfoMapper loanCustomerBaseInfoMapper;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanPayPlanMapper loanPayPlanMapper;
	@Autowired
	private LoanAdvanceMapper loanAdvenceMapper;
	@Autowired
	private LoanAdvanceRecordMapper loanAdvenceRecordMapper;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Reference
	private UserApi userApi;
	/**
     * selectBorrowerInfo:客户信息管理-借款人信息管理
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    /*public List<LoanBorrowerInfoVo> selectBorrowerInfo(Map<String, Object> map, Page page){
    	List<LoanBorrowerInfoVo> borrowerInfoList = loanBorrowerInfoMapper.selectBorrowerInfo(map);
    	int count = loanBorrowerInfoMapper.selectBorrowerInfoCount(map);
    	
    	page.setResultList(borrowerInfoList);
		page.setTotalCount(count);
    	return borrowerInfoList;
    }*/
	public LoanBorrowerInfoVo get(String id) {
		return null;
	}

	public LoanBorrowerInfoVo get(int id) {
		return null;
	}

	public LoanBorrowerInfoVo get(LoanBorrowerInfoVo entity) {
		return null;
	}

	public List queryList(LoanBorrowerInfoVo entity) {
		return null;
	}

	public Page queryListPage(LoanBorrowerInfoVo entity) {
		Page page = entity.getPage();
		CommonUtils.fillCompany(entity);
		List<LoanBorrowerInfoVo> loanBorrowerInfoList = loanBorrowerInfoMapper.selectBorrowerInfo(entity);
		int count = loanBorrowerInfoMapper.selectBorrowerInfoCount(entity);
		for(LoanBorrowerInfoVo loanBorrower:loanBorrowerInfoList){
			if(null==entity.getLeEmpId()){
				loanBorrower.setMobile(loanBorrower.getMobile().replaceAll(loanBorrower.getMobile().substring(3,7), "****"));
				loanBorrower.setIdNum(loanBorrower.getIdNum().replaceAll(loanBorrower.getIdNum().substring(6,13), "********"));
			}
			if(null!=entity.getLeEmpId()&&!entity.getLeEmpId().toString().equals(loanBorrower.getManagerId())){
				loanBorrower.setMobile(loanBorrower.getMobile().replaceAll(loanBorrower.getMobile().substring(3,7), "****"));
				loanBorrower.setIdNum(loanBorrower.getIdNum().replaceAll(loanBorrower.getIdNum().substring(6,13), "********"));
			}
		}
		page.setResultList(loanBorrowerInfoList);
		page.setTotalCount(count);
		return page;
	}

	@Override
	public int save(LoanBorrowerInfoVo entity) {
		return 0;
	}

	@Override
	public int update(LoanBorrowerInfoVo entity) {
		return 0;
	}

	@Override
	public int delete(LoanBorrowerInfoVo entity) {
		return 0;
	}
		

	public int queryCount(LoanBorrowerInfoVo entity) {
		return 0;
	}
	
	/**
	 * selectCustNatureByCategory:根据分类名称查询客户性质的名称
	 * @author kernel.org
	 * @return
	 * @since JDK 1.8
	 */
	public List<LoanDictionary> selectCustNatureByCategory(){
		return loanDictionaryMapper.selectCustNatureByCategory();
	}
    
    /**
     * selectBorrowerBasicInfo:根据customer_id查询当前客户的基本信息和抵押物信息之基本信息查询
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
    public LoanBorrowerInfoVo selectBorrowerBasicInfo(String customerId){
    	return loanBorrowerInfoMapper.selectBorrowerBasicInfo(customerId);
    }

    /**
     * selectCarInfoList:根据customerId查询借款名下的车产信息
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
	public List<LoanCarInfo> selectCarInfoList(String customerId) {
		return loanCarInfoMapper.selectCarInfoList(customerId);
	}

	 /**
     * selectCarInfoList:根据customerId查询借款名下的房产信息
     * @author kernel.org
     * @param customerId
     * @return
     * @since JDK 1.8
     */
	public List<LoanHouseInfo> selectHouseInfoList(String customerId) {
		return loanHouseInfoMapper.selectHouseInfoList(customerId);
	}
    
	/**
     * updateBorrowerInfoWithBLOBs:通过客户基本信息的 LoanBorrowerInfoWithBLOBs 对象更新数据库表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    public int updateBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs entity){
    	return loanBorrowerInfoMapper.updateBorrowerInfoWithBLOBs(entity);
    }
    
    /**
	 * updateLoanCustomerInfoByEntity:通过实体loanCustomerInfo更新loanCustomerInfo
	 * @author kernel.org
	 * @param loanCustomerInfo
	 * @return
	 * @since JDK 1.8
	 */
	public int updateLoanCustomerInfoByEntity(LoanCustomerInfo entity){
		try {
			LoanCustomerBaseInfo baseInfo = loanCustomerBaseInfoMapper.selectByPrimaryKey(entity.getCustomerId());
			if(baseInfo != null){
				LoanCustomerBaseInfo base = new LoanCustomerBaseInfo();
				base.setCustomerId(entity.getCustomerId());
				base.setCertificateNo(entity.getIdNum());
				base.setCustomerMobile(entity.getCustomerMobile());
				base.setLastModifyTime(new Date());
			    loanCustomerBaseInfoMapper.updateByPrimaryKeySelective(base);
				
				//账户信息
				LoanAccountCard card = new LoanAccountCard();
				card.setOwnerId(entity.getCustomerId());
				card.setMobile(entity.getCustomerMobile());
				card.setCertificateNo(entity.getIdNum());
				card.setAccountType(baseInfo.getCustomerType());
				card.setOwnerName(baseInfo.getCustomerName());
				card.setUnionId(baseInfo.getUnionId());
				card.setCompanyId(baseInfo.getCompanyId());
				card.setBankCardNo(entity.getBankCardNo());
				card.setBankId(entity.getBankId());
				card.setBranchName(entity.getBranchName());
				card.setHidBankCardNo(entity.getHidBankCardNo());
			    accountManagerService.saveOrUpdateCard(card);
			}
			int ret = loanCustomerInfoMapper.updateLoanCustomerInfoByEntity(entity);
			return ret;
			
		} catch (Exception e) {
			logger.error("Exception"+e);
		}
		return 0;
	  
	}
    
    /**
     * saveMortgageInfo:保存抵押物信息
     * @author kernel.org
     * @param houseList
     * @param carList
     * @param orderNo
     * @return
     * @since JDK 1.8
     */
    @Transactional(rollbackFor = Exception.class)
	public boolean saveMortgageInfo(List<LoanHouseInfo> houseList, List<LoanCarInfo> carList, String customerId) {
    	return this.updateLoanHouseInfo(houseList, customerId) && this.updateLoanCarInfo(carList, customerId);
	}
    
    /**
     * updateLoanHouseInfo:更新房产记录
     * @author kernel.org
     * @param record
     * @return
     * @since JDK 1.8
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoanHouseInfo(List<LoanHouseInfo> loanHouseInfoList, String customerId){
    	boolean result = true;
		try{
			loanHouseInfoMapper.deleteHouseInfoByCustomerId(customerId);
			if(null != loanHouseInfoList && loanHouseInfoList.size() > 0){
				for(LoanHouseInfo houseInfo : loanHouseInfoList){
					if(houseInfo != null){
						//插入或者更新房产扩展信息
						loanHouseInfoMapper.insertSelective(houseInfo);
					}
				}
			}
		}catch(Exception e){
			result = false;
			logger.debug(e.getMessage());
		}finally{
			return result;
		}
    }
    
    /**
     * updateLoanCarInfo:更新车产记录
     * @author kernel.org
     * @param record
     * @return
     * @since JDK 1.8
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoanCarInfo(List<LoanCarInfo> loanCarInfoList, String customerId){
    	boolean result = true;
		try{
			loanCarInfoMapper.deleteCarInfoByCustomerId(customerId);
			if(null != loanCarInfoList && loanCarInfoList.size() > 0){
				for(LoanCarInfo carInfo : loanCarInfoList){
					if(carInfo != null){
						loanCarInfoMapper.insertSelective(carInfo);
					}
				}
			}
		}catch(Exception e){
			result = false;
			logger.debug(e.getMessage());
		}finally{
			return result;
		}
    }

	@Override
	public List<LoanProviderInfo> loanProviderList(Map<String, Object> map, Page page) {
		List<LoanProviderInfo> providerInfos=loanProviderInfoMapper.provaderList(map);
		int count = loanProviderInfoMapper.provaderListCount(map);
		page.setResultList(providerInfos);
		page.setTotalCount(count);
		return providerInfos;
	}

	@Override
	public LoanProviderInfo selProviderInfo(String providerno) {
		return loanProviderInfoMapper.queryProviderInfo(providerno);
	}

	@Override
	public int updateProvider(LoanProviderInfo loanProviderInfo) {
		return loanProviderInfoMapper.updateByPrimaryKeySelective(loanProviderInfo);
	}

	@Override
	public List<LoanProviderInfo> loanProviderCompanyList(Map<String, Object> map, Page page) {
		List<LoanProviderInfo> providerInfos=loanProviderInfoMapper.provaderCompanyList(map);
		int count = loanProviderInfoMapper.provaderCompanyListCount(map);
		page.setResultList(providerInfos);
		page.setTotalCount(count);
		return providerInfos;
	}

	@Override
	public List<LoanProviderInfo> loanProviderListExport(Map<String, Object> map) {
		return loanProviderInfoMapper.provaderListExport(map);
	}
	
	@Override
	public List<LoanPayPlanCompany> loanPayPlanComplanyList(Map<String, Object> map, Page page) {
		
		List<LoanPayPlanCompany> payPlanCompany=loanPayPlanCompanyMapper.querypayplanCompany(map);
		Integer count = loanPayPlanCompanyMapper.querypayplanCompanyCount(map);
		page.setResultList(payPlanCompany);
		page.setTotalCount(count);
		return payPlanCompany;
		
	}
	
	@Override
	public Map<String, Object> queryTotalAmount(Map<String, Object> map) {
		return loanPayPlanCompanyMapper.queryTotalAmount(map);
	}

	@Override
	public List<LoanPayPlanCompany> queryPayaPlanList(LoanPayPlanCompany loanPayPlanCompany) {
		return loanPayPlanCompanyMapper.queryPayMentList(loanPayPlanCompany);
	}

	@Override
	public List<LoanPayPlanCompany> loanPayPlanComplanyExport(Map<String, Object> map) {
		
		return loanPayPlanCompanyMapper.querypayplanCompanyExport(map);
	
	}
	@Override
	public List<LoanPayRecordCompany> loanPayRecordComplanyList(Map<String, Object> map, Page page) {
		 List<LoanPayRecordCompany> loanPayRecord=loanPayRecordCompanyMapper.querypayRecordCompany(map);
		 Integer count = loanPayRecordCompanyMapper.querypayRecordCompanyCount(map);
		 page.setResultList(loanPayRecord);
		 page.setTotalCount(count);
		 return loanPayRecord;
	}
	
	@Override
	public Map<String, Object> queryPaidTotal(Map<String, Object> map) {
		return loanPayRecordCompanyMapper.queryPaidTotal(map);
	}

	@Override
	public List<LoanPayRecordCompany> loanPayRecordComplanyExport(Map<String, Object> map) {
		
		return loanPayRecordCompanyMapper.querypayRecordCompanyExport(map);
	}

	@Override
	public List<LoanTransMain> querySettleList(Map<String, Object> map, Page page) {
		List<LoanTransMain> loanTransMain=loanTransMainMapper.querySettleList(map);
		Integer count=loanTransMainMapper.querySettleListCount(map);
		page.setResultList(loanTransMain);
		page.setTotalCount(count);
		return loanTransMain;
	}

	@Override
	public List<LoanTransMain> querySettleListExport(Map<String, Object> map) {
		List<LoanTransMain> ltMains= loanTransMainMapper.querySettleListExport(map);
		for (LoanTransMain main : ltMains) {
			if(main.getRepaymentMethod()!=null){
			if(main.getRepaymentMethod().equals("0")){
				main.setRepaymentMethod("每月还息,到期还本");
			}else if(main.getRepaymentMethod().equals("1")){
				main.setRepaymentMethod("等额本息");
			}else if(main.getRepaymentMethod().equals("2")){
				main.setRepaymentMethod("一次性还款");
			}
			}
		   if(main.getInterestWay()!=null){
           if(main.getInterestWay().equals("0")){
        	   main.setInterestWay("放款日结息");
			}else if(main.getInterestWay().equals("1")){
				main.setInterestWay("到期日结息");
			}else if(main.getInterestWay().equals("2")){
				main.setInterestWay("指定日期结息");
			}
		}
		}
		return ltMains;
	}

	@Override
	public Map<String, Object> querySettleListSum(Map<String, Object> map) {
		return loanTransMainMapper.queryPaidTotalAmount(map);
	}
	
	@Override
	public List<LoanPayRecordCompany> querySettleDetail(String orderNo) {
		return loanPayRecordCompanyMapper.querySettleDetail(orderNo);
	}

	@Override
	public Page capitalPrepaidList(LoanPayPlan entity){
		Page page=entity.getPage();
		page.setResultList(loanPayPlanMapper.queryCapitalList(entity));
		page.setTotalCount(loanPayPlanMapper.queryCapitalCount(entity));
		return page;
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmount(LoanPayPlan entity) {
		return loanPayPlanMapper.queryTotalAmount(entity);
	}

	@Override
	public Page capitalPrepaidList(LoanAdvance entity) {
		Page page=entity.getPage();
		page.setResultList(loanAdvenceMapper.queryAdvanceAll(entity));
		page.setTotalCount(loanAdvenceMapper.queryCount(entity));
		return page;
	}

	@Override
	public LoanAdvance queryAdvancesum(LoanAdvance entity) {
		
		return loanAdvenceMapper.queryAdvancesum(entity);
	}

	@Override
	public Map<String, BigDecimal> queryTotalAmountSum(LoanAdvance entity) {
		return loanAdvenceMapper.queryTotalAmount(entity);
	}

	@Override
	public LoanAdvance queryAdvance(String advanceId) {
		return loanAdvenceMapper.selectByPrimaryKey(advanceId);
	}

	@Override
	public LoanAdvance amountCompute(LoanAdvance advance) {
		LoanAdvance advanceTwo=loanAdvenceMapper.selectByPrimaryKey(advance.getAdvanceId());
		//本次还债时间
		advanceTwo.setNowPaidAdvanceTime(advance.getPaidAdvanceTime());
		//计算垫付债务利息
		LoanAdvance resAdvance=accountTransactionService.advanceAmountCompute(advanceTwo);
		return resAdvance;
	}

	@Override
	public Page queryAdvanceRecord(LoanAdvanceRecord record) {
		Page page=record.getPage();
		page.setResultList(loanAdvenceRecordMapper.queryList(record));
		page.setTotalCount(loanAdvenceRecordMapper.queryCount(record));
		return page;
	}

	@Override
	public LoanAdvanceRecord queryTotalAmountSum(LoanAdvanceRecord record) {
		return loanAdvenceRecordMapper.queryTotalAmountSum(record);
	}

	@Override
	public Map<String, Object> executeRepay(LoanAdvance advance) {
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.CustomerManagerService#selectProviderByDeptId(java.lang.String, java.lang.String)
	 */
	 
	@Override
	public List<LoanProviderInfo> selectProviderByDeptId(String mobile, String companyId) {
		User user = new User();
		user.setCompanyId(companyId);
		user.setMobile(mobile);
        User  loanEmp =  userApi.get(user);
		 List<LoanProviderInfo>  providerList =null;
		if(loanEmp!=null){
			String deptId =String.valueOf(loanEmp.getDeptId());
		    providerList =	loanProviderInfoMapper.selectByDeptAndCompany(deptId,loanEmp.getCompanyId());
		}
		return providerList;
	}
    /**
     * saveBankInfo:更新银行卡信息
     * @since JDK 1.8
     */
    @Transactional(rollbackFor = Exception.class)
	public boolean saveBankInfo(String[] bankLists, String customerId) {
    	boolean result = true;
		try{
			LoanAccountInfo accountInfo=new LoanAccountInfo();
			accountInfo.setOwnerId(customerId);
			LoanAccountInfo accountEntity=loanAccountInfoMapper.get(accountInfo);
			if(StringUtil.isEmpty(accountEntity.getAccountId())){
				result = false;
			}
			loanAccountCardMapper.deleteBankInfoByCustomerId(customerId);
			if(null != bankLists && StringUtil.isNotEmpty(accountEntity.getAccountId()) && bankLists.length > 0){
				for(int i=0;i<bankLists.length;i++){
					String bankInfo =bankLists[i];
					LoanAccountCard bankEntity=JSON.parseObject(bankInfo, LoanAccountCard.class);
					if(bankEntity != null){//插入银行卡相关信息
						bankEntity.setStatus("03");
						bankEntity.setCreateTime(new Date());
						bankEntity.setLastModifyTime(new Date());
						bankEntity.setAccountId(accountEntity.getAccountId());
						bankEntity.setAccountName(accountEntity.getOwnerName());
						bankEntity.setAccountHolder(accountEntity.getOwnerName());
						loanAccountCardMapper.insertSelective(bankEntity);
					}
				}
			}
		}catch(Exception e){
			result = false;
			logger.debug(e.getMessage());
		}finally{
			return result;
		}
	}

	public List<LoanAccountCard> selectbankCarkList(String customerId) {
		return loanAccountCardMapper.selectbankCarkList(customerId);
	}
}

