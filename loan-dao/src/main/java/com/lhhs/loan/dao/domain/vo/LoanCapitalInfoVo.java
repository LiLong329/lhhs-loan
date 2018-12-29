/**
 * Project Name:loan-dao
 * File Name:LoanCapitalInfoVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年8月8日下午4:53:46
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.util.Date;

import com.lhhs.loan.dao.domain.LoanCapitalInfo;

/**
 * ClassName:LoanCapitalInfoVo ,暂时不用...(备用)<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月8日 下午4:53:46 <br/>
 * @author   kernel.org
 * @version
 * @since    JDK 1.8
 * @see
 */
public class LoanCapitalInfoVo extends LoanCapitalInfo {

	private Date loanApplyDate; // 放款申请时间

	public Date getLoanApplyDate() {
		return loanApplyDate;
	}

	public void setLoanApplyDate(Date loanApplyDate) {
		this.loanApplyDate = loanApplyDate;
	}
	
	
}

