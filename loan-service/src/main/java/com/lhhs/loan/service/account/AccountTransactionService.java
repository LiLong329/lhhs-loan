/**
 * Project Name:loan-service
 * File Name:AccountTransactionService.java
 * Package Name:com.lhhs.loan.service.account
 * Date:2017年7月31日下午4:24:00
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.account;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.LoanFeesRecord;
import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;
import com.lhhs.loan.dao.domain.LoanPayPlanTemp;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.LoanTransMain;


/**
 * ClassName:AccountTransactionService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月31日 下午4:24:00 <br/>
 * @author   lenovo
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface AccountTransactionService {
	/**
	 * 
	 * loanTrans:放款、固定理财转账预交易、生产还款计划<br/>

	 * @author dongfei
	 * @param loanTransMain 放款交易实体
	 * @return
	 * @since JDK 1.8
	 */
	public LoanTransMain loanTransPre(LoanTransMain loanTransMain);

	/**
	 * 
	 * loanTrans:放款、固定理财转账<br/>

	 * @author dongfei
	 * @param loanTransMain 放款交易实体
	 * @return
	 * @since JDK 1.8
	 */
	public String loanTrans(LoanTransMain loanTransMain);
	
	/**
	 * 
	 * loanTrans:转账 <br/>
	 * @author dongfei
	 * @param LoanTransAccounts 转账
	 * @return
	 * @since JDK 1.8
	 */
	public String accountTrans(LoanAccountsTrans loanAccountsTrans);
	
	/**
	 * 
	 * accountFreezeTrans:冻结、解冻账户余额交易,解冻时根据冻结编号
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体
	 * @return 冻结编号
	 * @since JDK 1.8
	 */
	public String  accountFreezeTrans(LoanAccountsTrans loanAccountsTrans);
	
	/**
	 * 
	 * accountFreezeTrans:同一业务批量冻结、解冻账户余额交易
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体
	 * @return 冻结编号
	 * @since JDK 1.8
	 */
	public String  accountFreezeInTrans(List<LoanAccountsTrans> list);
	
	/**
	 * 
	 * accountFreezeTrans:根据业务申请号批量解冻
	 *
	 * @author dongfei
	 * @param loanAccountsTrans 账户交易流水实体
	 * @return 成功：00，失败：01
	 * @since JDK 1.8
	 */
	public String  accountFreezeOutTrans(LoanAccountsTrans loanAccountsTrans);

	/**
	 * 
	 * loanPayTrans:还款交易
	 *
	 * @author dongfei
	 * @param loanPayRecord
	 * @return
	 * @since JDK 1.8
	 */
	public String  loanPayTrans(LoanPayRecord loanPayRecord);

	/**
	 * 
	 * loanPayTrans:付款交易
	 *
	 * @author lenovo
	 * @param loanPayRecord
	 * @return
	 * @since JDK 1.8
	 */
	public String  loanPayCompanyTrans(LoanPayRecordCompany loanPayRecordCompany);
	
	/**
	 * 
	 * loanPayTrans:收款、支出
	 *
	 * @author dongfei
	 * @param LoanFeesRecord
	 * @return
	 * @since JDK 1.8
	 */
	public String  loanFeesTrans(LoanFeesRecord loanFeesRecord);
	
	/**
	 * 
	 * 充值、提现
	 *
	 * @author lenovo
	 * @param loanAccountsTrans
	 * @return
	 * @since JDK 1.8
	 */
	public String  accountInAndOutTrans(LoanAccountsTrans loanAccountsTrans);
	
	/**
	 * 
	 * 垫付
	 *
	 * @author lenovo
	 * @param loanPayRecord
	 * @return
	 * @since JDK 1.8
	 */
	public String  LoanAdvanceTrans(LoanAdvance loanAdvance);
	
	/**
	 * 
	 * 垫付债务还款
	 *
	 * @author lenovo
	 * @param loanPayRecord
	 * @return
	 * @since JDK 1.8
	 */
	public String  LoanAdvancePayTrans(LoanAdvanceRecord loanAdvanceRecord);
	/**
	 * 
	 * advanceAmountCompute:垫付债务利息计算器 <br/>
	 * @param advance 查询的垫付记录+当前垫付日期
	 * @return 债务天数、债务利息总额、应还总额
	 * @since JDK 1.8
	 */
	public LoanAdvance advanceAmountCompute(LoanAdvance advance);

	/**
	 * 
	 * accountChangeTrans:动账交易

	 * @author dongfei
	 * @param LoanAccountsTrans 转账
	 * @return 成功：00；失败：01
	 * @since JDK 1.8
	 */
	public String accountChangeTrans(LoanAccountsTrans loanAccountsTrans) ;
	
	/**
	 * 生成还款计划保存到临时表
	 * @param loanTransMain
	 * @return
	 */
	public List<LoanPayPlanTemp>  generateLoanPayPlanTemp(LoanTransMain loanTransMain);
	
	/**
	 * 生成放款记录保存到临时表
	 * @param loanTransMain
	 * @return
	 */
	public List<LoanRecordTemp>  generateLoanRecordTemp(LoanTransMain loanTransMain);
	
	/**
	 * 生成待付款计划保存到临时表
	 * @param loanTransMain
	 * @return
	 */
	public List<LoanPayPlanCompanyTemp>  generatePayPlanCompanyTemp(LoanTransMain loanTransMain);
	
	/**
	 * 生成收入支出计划
	 * @param loanTransMain
	 * @return
	 */
	public List<LoanFeesPlan>  generateLoanFeesPlan(LoanTransMain loanTransMain);
}

