package com.lhhs.loan.common.shared.constant;


public class SystemConst {
	
	/**
	 * 返回状态标识
	 */
	public static final String SUCCESS = "00";//成功
	public static final String FAIL = "01";//失败
	
	/**
	 * 系统通信跑批任务参数常量
	 */
	public static final String POST="POST";
	public static final String GET="GET";
	
	/**
	 * 返回码
	 * 消息常量
	 * 返回类型
	 */
	public static final String retMsg = "retMsg";
	public static final String retCode = "retCode";
	public static final String retType = "retType";

	/**
	 * 验证码功能名称
	 */
	public static final String  SCAPTCHA="SCAPTCHA";
	
	/**
	 * 报单人编号
	 */
	public static final String PROVIDERNO = "100";
	
	/**
	 * 借款人编号
	 */
	public static final String CUSTINFONO = "200";
	
	/**
	 * 审批处理结果
	 */
	public static final Integer AuditingHandledTY = 0; // 同意
	public static final Integer AuditingHandledJD = 1; // 拒贷
	public static final Integer AuditingHandledBJ = 2; // 补件
	public static final Integer AuditingHandledCD = 3; // 撤单
	/**
	 * 
	 * 状态码
	 */
	public class Status{
		/**
		 * 草稿
		 */
		public static final String STATUS01 = "01"; 
		/**
		 * 有效
		 */
		public static final String STATUS03 = "03"; 
		/**
		 * 无效、取消
		 */
		public static final String STATUS99 = "99"; 
		/**
		 * 成功/完成//通过
		 */
		public static final String STATUS90 = "90"; 
		/**
		 * 失败//不通过
		 */
		public static final String STATUS91 = "91"; 
		/**
		 * 作废
		 */
		public static final String STATUS92 = "92"; 
		/**
		 * 待审核
		 */
		public static final String STATUS93 = "93"; 
		/**
		 * 垫付
		 */
		public static final String STATUS87 = "87"; 
		/**
		 * 提前结清
		 */
		public static final String STATUS88 = "88"; 
		/**
		 * 逾期/逾期结清
		 */
		public static final String STATUS89 = "89"; 
		
		
	}
	/**
	 * 
	 * 交易编码
	 */
	public class TransType{
		/**放款	**/
		public static final String TYPEID1001="1001";
		/**还款	**/
		public static final String TYPEID1002="1002";
		/**付款	**/
		public static final String TYPEID1003="1003";
		/**垫付	**/
		public static final String TYPEID1004="1004";
		/**充值	**/
		public static final String TYPEID1005="1005";
		/**提现	**/
		public static final String TYPEID1006="1006";
		/**固定理财转账	**/
		public static final String TYPEID1007="1007";
		/**转账	**/
		public static final String TYPEID1008="1008";
		/**收款	**/
		public static final String TYPEID1009="1009";
		/**支出	**/
		public static final String TYPEID1010="1010";
		/**冻结	**/
		public static final String TYPEID1011="1011";
		/**解冻	**/
		public static final String TYPEID1012="1012";
		/**增量冻结	**/
		public static final String TYPEID1013="1013";
		/**垫付还款	**/
		public static final String TYPEID1014="1014";
		/**交易流水	**/
		public static final String TYPEID2001="2001";


	}

	/**
	 * 
	 * 特殊科目编码
	 */
	public class SubjectInfo{
		/**理财冻结**/
		public static final String SUBJECTID_FREEZEFINANCING="3301001";
		/**投资冻结**/
		public static final String SUBJECTID_FREEZEINVEST="3301002";
		/**提现冻结**/
		public static final String SUBJECTID_FREEZEWITHDRAWALS="3301003";
		/**还款在途**/	
		public static final String SUBJECTID_TRANSITTOTAL="4401001";
		/**充值**/	
		public static final String SUBJECTID_ACCOUNT_IN="1100001";
		/**提现**/	
		public static final String SUBJECTID_ACCOUNT_OUT="2200001";
		/**投资放款**/	
		public static final String SUBJECTID_INVEST_LOAN_OUT="2104001";
		/**借款入账**/	
		public static final String SUBJECTID_BORROWER_LOAN_IN="1102001";
		

		
		
		/**理财放款**/	
		public static final String SUBJECTID_FINANCING_LOAN_OUT="2110001";
		/**本金还款**/	
		public static final String SUBJECTID_REPAY_CAPITAL_OUT="2204001";
		/**利息还款**/	
		public static final String SUBJECTID_REPAY_INTEREST_OUT="2204002";
		
		
		/**提前还款违约金**/	
		public static final String SUBJECTID_REPAY_COMPENSATE_OUT="2204003";
		/**逾期罚息还款**/	
		public static final String SUBJECTID_REPAY_OVERDUE_INTEREST_OUT="2204004";
		/**回款本金**/	
		public static final String SUBJECTID_INVEST_CAPITAL_IN="1104001";
		/**回款利息**/	
		public static final String SUBJECTID_INVEST_INTEREST_IN="1104002";
		/**收入违约偿还金**/	
		public static final String SUBJECTID_INVEST_COMPENSATE_IN="1104003";
		/**回款逾期罚息**/	
		public static final String SUBJECTID_INVEST_OVERDUE_INTEREST_IN="1104004";

		/**理财回款本金**/	
		public static final String SUBJECTID_FINANCING_CAPITAL_IN="1110002";
		/**理财回款利息**/	
		public static final String SUBJECTID_FINANCING_INTEREST_IN="1110003";
		/**理财收入违约偿还金**/	
		public static final String SUBJECTID_FINANCING_COMPENSATE_IN="1110004";
		/**理财回款逾期罚息**/	
		public static final String SUBJECTID_FINANCING_OVERDUE_INTEREST_IN="1110005";
		
		
		/**理财本金支出**/	
		public static final String SUBJECTID_FINANCING_CAPITAL_OUT="2110002";
		
		/**融资入账**/	
		public static final String SUBJECTID_FINANCING_LOAN_IN="1103001";
	
		/**理财利息支出**/	
		public static final String SUBJECTID_FINANCING_INTEREST_OUT="2110003";
		/**理财违约偿还金支出**/	
		public static final String SUBJECTID_FINANCING_COMPENSATE_OUT="2110004";
		/**理财逾期罚息支出**/	
		public static final String SUBJECTID_FINANCING_OVERDUE_INTEREST_OUT="2110005";

		

		/**息差收入**/	
		public static final String SUBJECTID_REPAY_INTEREST_SPREAD_IN="1107001";
		/**逾期罚息利差收入**/	
		public static final String SUBJECTID_REPAY_OVERDUE_INTEREST_SPREAD_IN="1107002";
		/**返平台服务费**/	
		public static final String SUBJECTID_REPAY_FEES_IN="1106005";
		/**借款服务费**/	
		public static final String SUBJECTID_REPAY_FEES_OUT="2106001";
		/**分期服务费还款**/	
		public static final String SUBJECTID_REPAY_FEES_MONTH_OUT="2106007";
		
		/**理财利息退款收入**/	
		public static final String SUBJECTID_FINANCING_INTEREST_REFUND_IN="1110006";
		/**理财利息退款支出**/	
		public static final String SUBJECTID_FINANCING_INTEREST_REFUND_OUT="2110006";
		/**息差退款支出**/	
		public static final String SUBJECTID_REPAY_INTEREST_SPREAD_OUT="2107001";
		/**利息还款退款收入**/	
		public static final String SUBJECTID_REPAY_INTEREST_IN="1204002";
		/**回款利息退款支出**/	
		public static final String SUBJECTID_INVEST_INTEREST_OUT="2104002";
		
		/**垫付本金收入**/	
		public static final String SUBJECTID_ADVANCE_CAPITAL_IN="1201001";
		/**垫付利息收入**/	
		public static final String SUBJECTID_ADVANCE_INTEREST_IN="1201002";
		
		/**垫付本金支出**/	
		public static final String SUBJECTID_ADVANCE_CAPITAL_OUT="2201001";
		/**垫付利息支出**/	
		public static final String SUBJECTID_ADVANCE_INTEREST_OUT="2201002";
			
		/**回款垫付债务本金**/	
		public static final String SUBJECTID_PAY_ADVANCE_CAPITAL_IN="1101001";
		/**债务利息收入**/	
		public static final String SUBJECTID_PAY_ADVANCE_INTEREST_IN="1101003";
		
		/**还债本金支出**/	
		public static final String SUBJECTID_PAY_ADVANCE_CAPITAL_OUT="2101001";
		/**还债利息支出**/	
		public static final String SUBJECTID_PAY_ADVANCE_INTEREST_OUT="2101002";
		
			
			



	}
	/**
	 * 
	 * 冲销标识、冻结解冻标识
	 */
	public class WriteOff{
		/**已解冻、已冲销**/
		public static final String YES="1";
		/**未解冻、未冲销**/
		public static final String NO="0";
		/**主记录**/
		public static final String M="2";
	}
	
	/**
	 * 借款期限单位标识
	 */
	public class LoanTermUnit{
		/**天**/
		public static final String DAY="天";
		/**个月**/
		public static final String MONTH="个月";
	}
	
	/**
	 * 借款/放款利率单位标识
	 */
	public class RateUnit{
		/**%/天**/
		public static final String DAY="天";
		/**%/月**/
		public static final String MONTH="月";
		/**%/年**/
		public static final String YEAR="年";
		/**%/次**/
		public static final String TIME="次";
	}
	
	
	/**
	 * 还款方式标识
	 */
	public class RepaymentMethod{  
		/**每月还息到期还本**/      
		public static final String MONTHLY_INTERSETS="0";
		/**等额本息**/
		public static final String EQUAL_PRINCIPAL_INTEREST="1";
		/**一次性支付**/
		public static final String ONCE_PAYMENT="2";
	}
	
	
	/**
	 * 结息/付息方式标识
	 */
	public class InterestWay{  
		/**放款日结息**/
		public static final String LOAN_DATE="0";
		/**到期日结息**/      
		public static final String DUE_DATE="1";
		/**指定日期结息**/
		public static final String SPECIFIED_DATE="2";
	}
	
	/**
	 * 
	 * 业务类型
	 */
	public class BusinessType{
		/**投资**/
		public static final String INVEST="104";
		/**理财**/
		public static final String FINANCING="110";
	}
	
	/**
	 * 
	 * 是否标识
	 */
	public class IsFlag{
		/**是**/
		public static final String YES="Y";
		/**否**/
		public static final String NO="N";
	}
	/**
	 * 
	 * 账户类型
	 */
	public class AccountType{
		/**个人**/
		public static final String PERSONAL="10";
		/**企业**/
		public static final String ENTERPRISE="20";
		/**机构**/
		public static final String ORGANIZATION="30";
		/**公司**/
		public static final String COMPANY="40";
	}
	
	/**
	 * 
	 * 投资人客户性质
	 */
	public class InvestCustomerNature{
		/**固定理财银主**/
		public static final String FIXED_FINANCING="12";
		/**对接银主**/
		public static final String DOCKING="13";
		/**投资人**/
		public static final String INVESTOR="14";
		/**公户**/
		public static final String ATTACH="15";
		/**机构**/
		public static final String ORGANIZATION="31";
		/**公司**/
		public static final String COMPANY="41";
		/**同行**/
		public static final String PEER="33";
	}
	
	/**
	 * 
	 * 借款人客户性质
	 */
	public class LoanCustomerNature{
		/**个人**/
		public static final String PERSONAL="11";
		/**公户**/
		public static final String ATTACH="15";
		/**企业**/
		public static final String ENTERPRISE="21";
		/**机构**/
		public static final String ORGANIZATION="31";
		/**公司**/
		public static final String COMPANY="41";
		/**同行**/
		public static final String PEER="33";
	}
	
	/**
	 * 
	 * 收入支出方式
	 */
	public class transWay{
		/**一次性收取**/
		public static final String ONCE="0";
		/**按月收取**/
		public static final String MONTH="1";
	}
	
	/**
	 * 
	 * 收入、支出
	 */
	public class InOutCome{
		/**收入**/
		public static final String IN="01";
		/**支出**/
		public static final String OUT="02";
	}
	
	/**
	 * 
	 * 是否生成还款计划
	 */
	public class IsPayPlanTemp{
		/**是**/
		public static final String YES="Y";
		/**否**/
		public static final String NO="N";
	}
	
	/**
	 * 
	 * 是否生成放款记录
	 */
	public class IsLoanRecordTemp{
		/**是**/
		public static final String YES="Y";
		/**否**/
		public static final String NO="N";
	}
	
	/**
	 * 
	 * 是否生成待付款计划
	 */
	public class IsPayPlanCompanyTemp{
		/**是**/
		public static final String YES="Y";
		/**否**/
		public static final String NO="N";
	}
	
	public class AccountCardType{
		/**賬戶收款銀行卡**/
		public static final String INCARD="01";
		/**賬戶放款銀行卡**/
		public static final String OUTCARD="02";
		
	}
}
