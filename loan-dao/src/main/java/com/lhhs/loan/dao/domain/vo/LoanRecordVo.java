package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;

import com.lhhs.loan.dao.domain.LoanRecord;

@SuppressWarnings("all")
public class LoanRecordVo extends LoanRecord{
    
    private Integer selectType;//查询类型    1个人放款记录  2同行放款记录  3机构放款记录
    
    private String provienceNo;//省编号
    
    private String provienceName;//省名称
    
    private String cityNo;//市编号
    
    private String cityName;//市名称
    
    private String productType;//产品类型
    
    private String productName;//产品名称
    
    private Short term;//借款期限
    
    private String termUnit;//借款期限单位
    
    private BigDecimal rate;//借款利率
    
    private String rateUnit;//借款利率单位
    
    private String borrower;//借款人姓名
    
    private String typeName;//客户性质名称
    
    private String companyName;//客户性质名称
    
    private Integer num;//周统计需要
    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public String getProvienceNo() {
		return provienceNo;
	}

	public void setProvienceNo(String provienceNo) {
		this.provienceNo = provienceNo;
	}

	public String getProvienceName() {
		return provienceName;
	}

	public void setProvienceName(String provienceName) {
		this.provienceName = provienceName;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

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

	public Short getTerm() {
		return term;
	}

	public void setTerm(Short term) {
		this.term = term;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
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

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}