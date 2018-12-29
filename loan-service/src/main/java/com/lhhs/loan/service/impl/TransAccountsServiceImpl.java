/**
 * Project Name:loan-service
 * File Name:TransAccountsServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月28日下午2:18:10
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.ActCommentSelfMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanDictionaryMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyTempMapper;
import com.lhhs.loan.dao.LoanTransAccountsMapper;
import com.lhhs.loan.dao.domain.ActComment;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;
import com.lhhs.loan.dao.domain.LoanTransAccounts;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.TransAccountsService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:TransAccountsServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月28日 下午2:18:10 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service("transAccountsService")
public class TransAccountsServiceImpl implements TransAccountsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoanTransAccountsMapper transAccountsMapper;
	@Autowired
	private LoanDictionaryMapper dictionaryMapper;
	@Autowired
	private ActCommentSelfMapper actCommentMapper;
	@Autowired
	private LoanAccountInfoMapper accountInfoMapper;//查询账户信息
	@Autowired
	private AccountTransactionService accountTransactionService;//固定理财转账交易
	@Autowired
	private LoanAccountInOutInfoMapper accountInOutInfoMapper;//线下充值提现记账信息查询--by 充值订单编号
	//应付款计划临时Mapper
	@Autowired
	private LoanPayPlanCompanyTempMapper loanPayPlanCompanyTempMapper;
	
	/**
	 * 
	 * TODO 固定理财转账记录列表查询
	 * @see com.lhhs.loan.service.TransAccountsService#transAccountsList(java.util.Map, com.lhhs.loan.common.shared.page.Page)
	 */
	@Override
	public List<LoanTransAccounts> transAccountsList(Map<String, Object> map, Page page) {
		List<LoanTransAccounts> list = transAccountsMapper.transAccountsList(map);
		Integer Count =null;
		 if(list!=null&&list.size()>0){
			 for(LoanTransAccounts transAccounts:list){
				 if(page==null){//导出
					//审核结果
					transAccounts.setField1(transAccounts.getCreateUser()==null||"".equals(transAccounts.getCreateUser())?"审核失败":"审核成功");
				 }
			 }
		 }
		 if(page!=null){
			 Count = transAccountsMapper.transAccountsListCount(map);
			 page.setResultList(list);
			 page.setTotalCount(Count==null?0:Count);
		 }
		 return list;
	}
	
	public Map<String, Object> transAccountsAdd(LoanTransAccounts transAccounts) {
		    Map<String, Object> resultMap = new HashMap<String, Object>();
			LoanEmp loanEmp= CommonUtils.getEmpFromSession();
			 if(loanEmp!=null && loanEmp.getBranchCompanyId()!=null && loanEmp.getCompanyId()!=null&&loanEmp.getLeEmpId()!=null){
				 transAccounts.setCreateUser(loanEmp.getLeEmpId().toString());//登录用户id
				 transAccounts.setCompanyId(loanEmp.getBranchCompanyId());//所属分公司
				 transAccounts.setUnionId(loanEmp.getCompanyId());//所属集团
			 }else{//登录用户所属集团或公司不能为空
				 resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				 resultMap.put(SystemConst.retMsg, "\u767b\u5f55\u7528\u6237\u6240\u5c5e\u96c6\u56e2\u6216\u516c\u53f8\u4e0d\u80fd\u4e3a\u7a7a");
				 return resultMap;
			 }
			int count = 0;
			transAccounts.setCreateTime(new Date());
			transAccounts.setStatus(SystemConst.Status.STATUS01);//草稿状态
			if(org.springframework.util.StringUtils.isEmpty(transAccounts.getOutAccountCustProperty())&&
			 transAccounts.getOutAccountCustPropertySed()!=null&&!"".equals(transAccounts.getOutAccountCustPropertySed())){
				transAccounts.setOutAccountCustProperty(transAccounts.getOutAccountCustPropertySed());
			}
			//付息方式
			if("0".equals(transAccounts.getInterestPaymentMethod())){
				transAccounts.setInterestPaymentMethod(SystemConst.InterestWay.LOAN_DATE); 
			}else if("1".equals(transAccounts.getInterestPaymentMethod())){ 
				transAccounts.setInterestPaymentMethod(SystemConst.InterestWay.DUE_DATE);
			}else{
				transAccounts.setInterestPaymentMethod(SystemConst.InterestWay.SPECIFIED_DATE);
			}
			//还款方式
			if("0".equals(transAccounts.getRepaymentMethod())){
				transAccounts.setRepaymentMethod(SystemConst.RepaymentMethod.MONTHLY_INTERSETS);
			}else if("1".equals(transAccounts.getRepaymentMethod())){//MONTHLY_INTERSETS
				transAccounts.setRepaymentMethod(SystemConst.RepaymentMethod.EQUAL_PRINCIPAL_INTEREST);
			}else{//ONCE_PAYMENT
				transAccounts.setRepaymentMethod(SystemConst.RepaymentMethod.ONCE_PAYMENT);
			}
			if("1".equals(transAccounts.getTransType())){//固定理财转账申请
				transAccounts.setTransType(SystemConst.TransType.TYPEID1007);
			}else{//转账记账申请
				transAccounts.setTransType(SystemConst.TransType.TYPEID1008);
			}
			
			if(transAccounts.getInAccount()!=null&&!"".equals(transAccounts.getInAccount())){
				//判断转出账户转账前账户金额
				if(transAccounts.getOutAccountPreBalance()!=null&&!"".equals(transAccounts.getOutAccountPreBalance())){
					//转账金额--转出账户--转账后账户余额
					transAccounts.setOutAccountSufBalance(transAccounts.getOutAccountPreBalance().subtract(transAccounts.getInAccount()));
				}else{
					//转账金额--转出账户--转账后账户余额
					transAccounts.setOutAccountSufBalance(new BigDecimal(0).subtract(transAccounts.getInAccount()));
				}
				//判断转入账户转账前账户金额
				if(transAccounts.getInAccountPreBalance()!=null&&!"".equals(transAccounts.getInAccountPreBalance())){
					//转账金额--转ru账户--转账后账户余额
					transAccounts.setInAccountSufBalance(transAccounts.getInAccountPreBalance().add(transAccounts.getInAccount()));
				}else{
					//转账金额--转ru账户--转账后账户余额
					transAccounts.setInAccountSufBalance(transAccounts.getInAccount());
				}
			}
			
			if(transAccounts.getFinancialAmount()!=null&&!"".equals(transAccounts.getFinancialAmount())){
				//判断转出账户转账前账户金额
				if(transAccounts.getOutAccountPreBalance()!=null&&!"".equals(transAccounts.getOutAccountPreBalance())){
					//理财金额--转出账户-转账后账户余额
					transAccounts.setOutAccountSufBalance(transAccounts.getOutAccountPreBalance().subtract(transAccounts.getFinancialAmount()));
				}else{
					//理财金额--转出账户-转账后账户余额
					transAccounts.setOutAccountSufBalance(new BigDecimal(0).subtract(transAccounts.getFinancialAmount()));
				}
				//判断转入账户转账前账户金额
				if(transAccounts.getInAccountPreBalance()!=null&&!"".equals(transAccounts.getInAccountPreBalance())){
					//理财金额--转入账户-转账后账户余额
					transAccounts.setInAccountSufBalance(transAccounts.getInAccountPreBalance().add(transAccounts.getFinancialAmount()));
				}else{
					//理财金额--转入账户-转账后账户余额
					transAccounts.setInAccountSufBalance(transAccounts.getFinancialAmount());
				}
			}
			count = transAccountsMapper.insertSelective(transAccounts);
			if (count >= 1) {
				//申请成功-进行冻结操作
					LoanAccountInfo outAccountInfo=accountInfoMapper.selectByOwnerId(transAccounts.getOutAccountCustId());//转出账户id查询
					if(outAccountInfo==null){
						 resultMap.put(SystemConst.retCode, SystemConst.FAIL);
						 resultMap.put(SystemConst.retMsg, "\u51bb\u7ed3\u8d26\u6237\u002c\u8f6c\u51fa\u8d26\u6237\u0069\u0064\u4e0d\u80fd\u4e3a\u7a7a\uff01");
						 return resultMap;
					}
					LoanAccountsTrans accountsTrans=new LoanAccountsTrans();
					accountsTrans.setOrderNo(transAccounts.getTransId());//
					accountsTrans.setAccountId(outAccountInfo.getAccountId());//账户编码
					accountsTrans.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_FREEZEFINANCING);//科目编码
					accountsTrans.setTransType(SystemConst.TransType.TYPEID1011);
					accountsTrans.setCompanyId(transAccounts.getCompanyId());
					accountsTrans.setUnionId(transAccounts.getUnionId());
					if(SystemConst.TransType.TYPEID1008.equals(transAccounts.getTransType())){
						accountsTrans.setAmount(transAccounts.getInAccount());//金额
					}else{
						accountsTrans.setAmount(transAccounts.getFinancialAmount());//金额
					}
					try {
						String code=accountTransactionService.accountFreezeTrans(accountsTrans);//冻结账户金额
						if(!SystemConst.FAIL.equals(code)){
							transAccounts.setStatus(SystemConst.Status.STATUS93);
							int t=transAccountsMapper.updateByPrimaryKeySelective(transAccounts);//更新申请表状态-待审核
							if(t>0){ 
								resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
								resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
							}         
						}else{
							resultMap.put(SystemConst.retCode, SystemConst.FAIL);
							resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
						}
					} catch (Exception e) {
						resultMap.put(SystemConst.retCode, SystemConst.FAIL);
						resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
						return resultMap;
					}
			   }else{
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
			}
			return resultMap;
		}

	
	/**
	 * transExamineAdd:转账审核
	 * @author suncj
	 * @param request
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	public Map<String, Object> transExamineAdd(LoanTransAccounts transAccounts) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		String recode="";
		if("90".equals(transAccounts.getPass())){//通过
			transAccounts.setStatus(SystemConst.Status.STATUS90);
			LoanTransAccounts accounts=transAccountsMapper.selectByPrimaryKey(transAccounts.getTransId());
			if(accounts==null){
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u8f6c\u8d26\u7533\u8bf7\u4fe1\u606f\u67e5\u8be2\u5f02\u5e38");
				return resultMap;
			}
			//进行转账处理
			LoanTransMain loanTransMain=new LoanTransMain();
			LoanAccountsTrans loanAccountsTrans=new LoanAccountsTrans();
			if(SystemConst.TransType.TYPEID1007.equals(accounts.getTransType())){//固定理财转账审核
				LoanAccountInfo accountInfo=accountInfoMapper.selectByOwnerId(accounts.getInAccountCustId());
				if(accountInfo==null){//收款账户信息查询异常！
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u6536\u6b3e\u8d26\u6237\u4fe1\u606f\u67e5\u8be2\u5f02\u5e38\uff01");
					return resultMap;
				}
				loanTransMain.setBorrower(accounts.getInAccountRealName());//借款人姓名(收款客户姓名)
				loanTransMain.setBorrowerId(accounts.getInAccountCustId());//借款人编号-收款账户用户id
				loanTransMain.setBusinessId(accounts.getTransId());//业务编号
				loanTransMain.setAmount(accounts.getFinancialAmount());//转账金额-理财金额
				loanTransMain.setTransType(SystemConst.TransType.TYPEID1007);//交易类型 :固定理财转账
				loanTransMain.setLendingTime(accounts.getActualLendingTime());//放款时间
				loanTransMain.setOverTime(accounts.getContractEndTime());//合同截止日期
				loanTransMain.setTerm(Short.valueOf(accounts.getFinancialTerm().toString()));//借款（理财）期限
				loanTransMain.setTermUnit(accounts.getFinancialTermUnit());//借款（理财）期限单位
				loanTransMain.setRate(accounts.getFinancialInterestRate());//借款利率
				loanTransMain.setRateUnit(accounts.getFinancialInterestRateUnit());//借款利率单位
				loanTransMain.setRepaymentMethod(accounts.getRepaymentMethod());//还款方式
				//取消总期数loanTransMain.setPeriodTotal(Short.valueOf(accounts.getFinancialTerm().toString()));//period_total总期数
				loanTransMain.setPayInterestWay(accounts.getInterestPaymentMethod());//pay_interest_way 付息方式
				loanTransMain.setInvestOverRate(accounts.getOverduePenaltyInterestRate().toString());//over_rate 逾期贷款利率
				loanTransMain.setInvestBreachRate(accounts.getRepaymentPenaltyInterestRate()==null||"".equals(accounts.getRepaymentPenaltyInterestRate())?null:accounts.getRepaymentPenaltyInterestRate().toString());//还款违约金率breach_rate - repayment_penalty_interest_rate
				loanTransMain.setAccountManager(accounts.getAccountManagerName());//account_manager  客户经理
				loanTransMain.setCreateTime(accounts.getCreateTime());
				loanTransMain.setCreateUser(accounts.getCreateUser());
				loanTransMain.setLastUser(accounts.getLastUser());
				loanTransMain.setLastModifyTime(accounts.getLastmodifyTime());
				loanTransMain.setPayerName(accounts.getOutAccountRealName());//payer_name  转出方客户名称
				loanTransMain.setPayerAccountId(accounts.getAccountId());//payer_account_id - 转出方账户id
				loanTransMain.setBorrowerAccountId(accountInfo.getAccountId());//收款账户id
				loanTransMain.setCompanyId(accounts.getCompanyId());
				loanTransMain.setUnionId(accounts.getUnionId());
				loanTransMain.setSetRemark(transAccounts.getMsg());
				loanTransMain.setAccountManager(accounts.getAccountManagerName());
				loanTransMain.setDepartment(accounts.getAccountManagerDepartmentName());
				loanTransMain.setField2(accounts.getOutAccountCustProperty());//客户性质
//				loanTransMain.setField2(accounts.getFinancialInterestRate().toString());
				try {
					recode=accountTransactionService.loanTrans(loanTransMain);
				} catch (Exception e) {
					logger.error(e.getMessage());
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u8f6c\u8d26\u5ba1\u6838\u5931\u8d25");
					return resultMap;
				}
			}else{//转账记账审核
				LoanAccountInfo outAccountInfo=accountInfoMapper.selectByOwnerId(accounts.getOutAccountCustId());
				LoanAccountInfo inAccountInfo=accountInfoMapper.selectByOwnerId(accounts.getInAccountCustId());
				if(outAccountInfo==null||inAccountInfo==null){//转出或转入账户编号不能为空
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u8f6c\u51fa\u6216\u8f6c\u5165\u8d26\u6237\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
					return resultMap;
				}
				loanAccountsTrans.setAccountId(outAccountInfo.getAccountId());//账户编号
				loanAccountsTrans.setOpponentAccountId(inAccountInfo.getAccountId());//交易对手账号
				loanAccountsTrans.setSubjectId(accounts.getOutAccountRealSummary());//转出科目编号
				loanAccountsTrans.setOpponentSubjectId(accounts.getInAccountRealSummary());//转入科目编号
				loanAccountsTrans.setAmount(accounts.getInAccount());//交易金额
				loanAccountsTrans.setTransTime(accounts.getTransferTime());//交易时间
				loanAccountsTrans.setBusinessId(accounts.getTransId());//业务编号 
				loanAccountsTrans.setCreateUser(accounts.getCreateUser()==null||"".equals(accounts.getCreateUser())?"":accounts.getCreateUser());
				loanAccountsTrans.setCreateTime(accounts.getCreateTime());
				loanAccountsTrans.setLastUser(accounts.getLastUser()==null||"".equals(accounts.getLastUser())?"":accounts.getLastUser());
				loanAccountsTrans.setLastModifyTime(accounts.getLastmodifyTime());
				loanAccountsTrans.setTransRemark(accounts.getRemarks()==null||"".equals(accounts.getRemarks())?"":accounts.getRemarks());
				loanAccountsTrans.setOrderNo(accounts.getTransId());
				loanAccountsTrans.setCompanyId(accounts.getCompanyId());
				loanAccountsTrans.setUnionId(accounts.getUnionId());
				loanAccountsTrans.setTransRemark(transAccounts.getMsg());
				try {
					recode=accountTransactionService.accountTrans(loanAccountsTrans);//转账记账审核
				} catch (Exception e) {
					logger.error(e.getMessage());
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "\u8f6c\u8d26\u5ba1\u6838\u5931\u8d25");
					return resultMap;
				}
			}
		}else{//不通过
			transAccounts.setStatus(SystemConst.Status.STATUS91);
			LoanAccountsTrans accountsTrans=new LoanAccountsTrans();
			if(org.springframework.util.StringUtils.isEmpty(transAccounts.getTransId())){//业务编码不能为空
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u4e1a\u52a1\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a");
				return resultMap;
			}
			accountsTrans.setOrderNo(transAccounts.getTransId());
			recode=accountTransactionService.accountFreezeOutTrans(accountsTrans);
		}
		if(!SystemConst.SUCCESS.equals(recode)){//账户转账异常，审核失败 ！
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u8d26\u6237\u8f6c\u8d26\u5f02\u5e38\uff0c\u5ba1\u6838\u5931\u8d25");
			return resultMap;
		}
		  int act=0;
		  int t =transAccountsMapper.updateByPrimaryKeySelective(transAccounts);//更新申请表状态
		  if(t>0){//插入工作流审批意见表 审核结果
			LoanEmp loanEmp= CommonUtils.getEmpFromSession();
			ActComment actComment=new ActComment();
			actComment.setBusinessId(transAccounts.getTransId());
			actComment.setProcDefKey("loan_trans_accounts");
			actComment.setTaskEndDate(transAccounts.getTaskEndDate());
			actComment.setAssigneeName(loanEmp.getLeStaffName());
			actComment.setPass(transAccounts.getStatus());
			actComment.setMsg(transAccounts.getMsg());
			actComment.setStatus(transAccounts.getStatus());
			act=actCommentMapper.insertSelective(actComment);
		}
		if(t==0||act==0){//申请状态或审核结果更新失败
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u7533\u8bf7\u72b6\u6001\u6216\u5ba1\u6838\u7ed3\u679c\u66f4\u65b0\u5931\u8d25");
		}else{
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
		}
		return resultMap;
	}
	
	
	
	@Override
	public LoanTransAccounts selectByPrimaryKey(String customerId) {
		
		return null;
	}
	

	@Override
	public LoanTransAccounts get(String transId) {
		return transAccountsMapper.selectByPrimaryKey(transId);
	}

	@Override
	public List queryList(LoanTransAccounts entity) {
		return null;
	}

	@Override
	public Page queryListPage(LoanTransAccounts entity) {
		return null;
	}


	@Override
	public int queryCount(LoanTransAccounts entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LoanTransAccounts> investCustomerList(Map<String, Object> map, Page page) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanTransAccounts> investCustomerExport(Map<String, Object> map) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanTransAccounts get(LoanTransAccounts entity) {
		
		return null;
	}


	@Override
	public List<Map<String, Object>> queryAccountCustType() {
		return  transAccountsMapper.queryAccountCustType();
	}

	@Override
	public List<Map<String, Object>> queryAccountCustProperty(Map<String, Object> map) {
		return  transAccountsMapper.queryAccountCustProperty(map);
	}

	@Override
	public List<LoanDictionary> getCustPropertyByTypeId(Map<String, Object> params) {
		return  dictionaryMapper.getCustPropertyByTypeId(params);
	}

	@Override
	public int save(LoanTransAccounts entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanTransAccounts entity) {
		return transAccountsMapper.updateByPrimaryKeySelective(entity);//更新申请表状态-待审核
	}

	@Override
	public int delete(LoanTransAccounts entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryAccountSubject(Map<String, Object> map) {
		return transAccountsMapper.queryAccountSubject(map);
	}

	
	
	@Override
	public Map<String, Object> getAccountsByCustId(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(map.get("custId").toString())){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请输入用户ID！");
			return result;
		}
		if(StringUtils.isEmpty(map.get("outCustType").toString())){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请选择客户类型！");
			return result;
		}
//		取消
//		if(StringUtils.isEmpty(map.get("outCustProperty").toString())){
//			result.put(SystemConst.retCode, SystemConst.FAIL);
//			result.put(SystemConst.retMsg, "请选择客户性质！");
//			return result;
//		}
		LoanTransAccounts info = transAccountsMapper.getAccountsByCustId(map);
		if(info!=null){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("customerName", info.getOutAccountRealName());
			result.put("customerMobile", info.getOutAccountMobile());
			result.put("balance", info.getOutAccountSufBalance());
			return result;
		}
		result.put(SystemConst.retCode, SystemConst.FAIL);
		result.put(SystemConst.retMsg, "账户信息不存在");
		return result;
	}

	/**
	 * 
	 * TODO 线下充值提现记账信息查询--by 充值订单编号
	 * @see com.lhhs.loan.service.TransAccountsService#getAccountInOutInfo(java.lang.String)
	 */
	@Override
	public Map<String, Object> getAccountInOutInfo(String transNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(transNo)){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请输入充值订单编号！");
			return result;
		}
//		LoanAccountInOutInfo info = accountInOutInfoMapper.getAccountInOutInfo(transNo);
		LoanTransAccounts info = transAccountsMapper.getAccountInOutInfo(transNo);
		if(info!=null){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("out_account_cust_id", info.getOutAccountCustId());
			result.put("out_account_cust_type", info.getOutAccountCustType());
			result.put("out_account_cust_property", info.getOutAccountCustProperty());
			result.put("out_account_real_name", info.getOutAccountRealName());
			result.put("out_account_mobile", info.getOutAccountMobile());
			result.put("out_account_bank_card", info.getOutAccountBankCard());
			result.put("out_account_cardholder", info.getOutAccountCardholder());
			result.put("out_account_pre_balance", info.getOutAccountPreBalance()==null||"".equals(info.getOutAccountPreBalance())?new BigDecimal(0):info.getOutAccountPreBalance());//转出账户金额
			result.put("in_account_pre_balance", info.getInAccountPreBalance()==null||"".equals(info.getInAccountPreBalance())?new BigDecimal(0):info.getInAccountPreBalance());//转入账户金额
			result.put("in_account_mobile", info.getInAccountMobile());
			result.put("in_account_cust_id", info.getInAccountCustId());
			result.put("in_account_real_name", info.getInAccountRealName());
			result.put("in_account_cardholder", info.getInAccountCardholder());
			result.put("in_account_bank_card", info.getInAccountBankCard());
			result.put("account_type", info.getAccountType());
			return result;
		}
		result.put(SystemConst.retCode, SystemConst.FAIL);
		result.put(SystemConst.retMsg, "充值订单信息不存在");
		return result;
	}

	@Override
	public List<LoanPayPlanCompanyTemp> getPayPlanCompanyTemp(String transId) {
		return loanPayPlanCompanyTempMapper.selectByTransAccounts(transId);
	}

	@Override
	public int selectByCustId(String custId) {
		return transAccountsMapper.selectByCustId(custId);
	}

	@Override
	public List<Map<String, Object>> queryAllManager(String accountManagerDepartmentNo) {
		return transAccountsMapper.queryAllManager(accountManagerDepartmentNo);
	}
}

