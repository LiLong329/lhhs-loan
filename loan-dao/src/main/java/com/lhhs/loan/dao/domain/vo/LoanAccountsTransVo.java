package com.lhhs.loan.dao.domain.vo;

import com.lhhs.loan.dao.domain.LoanAccountsTrans;

@SuppressWarnings("all")
public class LoanAccountsTransVo extends LoanAccountsTrans{

	private Byte orgCategories;//机构类别

	public Byte getOrgCategories() {
		return orgCategories;
	}

	public void setOrgCategories(Byte orgCategories) {
		this.orgCategories = orgCategories;
	}

}