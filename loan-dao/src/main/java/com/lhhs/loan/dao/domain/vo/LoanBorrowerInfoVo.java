/**
 * Project Name:loan-dao
 * File Name:LoanBorrowerInfoVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年8月1日下午6:16:32
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;

/**
 * ClassName:LoanBorrowerInfoVo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月1日 下午6:16:32 <br/>
 * @author   kernel.org
 * @version
 * @since    JDK 1.8
 * @see
 */
public class LoanBorrowerInfoVo extends LoanBorrowerInfoWithBLOBs{

	private String typeName;//客户性质名称
	
	private String customerType;
	
	private String customerNature;
	
	private String typeId;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getCustomerNature() {
		return customerNature;
	}
	
	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}
	
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}

