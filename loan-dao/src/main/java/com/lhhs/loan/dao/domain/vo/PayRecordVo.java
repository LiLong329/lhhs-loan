/**
 * Project Name:loan-dao
 * File Name:PayRecordVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年7月31日上午11:09:44
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.dao.domain.LoanPayRecord;

/**
 * ClassName:PayRecordVo <br/>
 * Function: 已还款记录查询Vo<br/>
 * Date:     2017年7月31日 上午11:09:44 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@SuppressWarnings("all")
public class PayRecordVo extends LoanPayRecord {
	private String cityName;//城市名称
	
	private String productType;//产品类型
	
	private String productName;//产品名称

	private BigDecimal amount;//借款金额
	
	private BigDecimal rate;//借款利率
	
	private String rateUnit;//借款利率单位

	private Short periodTotal;//总期数
	
	private String interestWay;//结息方式

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public Short getPeriodTotal() {
		return periodTotal;
	}

	public void setPeriodTotal(Short periodTotal) {
		this.periodTotal = periodTotal;
	}

	public String getInterestWay() {
		return interestWay;
	}

	public void setInterestWay(String interestWay) {
		this.interestWay = interestWay;
	}
}

