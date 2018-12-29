package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanParasConfig  extends BaseObject {
	private static final long serialVersionUID = 1L;

	//
	private String lpcId;
	//是否支持担保  0:不支持   1:支持
	private String lpcIsGuarantee;
	//是否需要垫还  0:不支持  1:支持
	private String lpcIsAdvance;
	//结息方式 0:放款日结息  1:到期日结息
	private String lpcInterestType;
	//结息固定日日期
	private Date lpcInterestRegular;
	//付息方式  0:固定日  1:放款日 2:到期日
	private String lpcPayType;
	//付息固定日日期
	private Date lpcPayRegular;
	//逾期贷款利率
	private BigDecimal lpcOverdueInteType;
	//逾期贷款利率单位
	private String lpcOverdueInteTypeUnit;
	//债务利率
	private BigDecimal lpcDebtInteRate;
	//债务利率单位
	private String lpcDebtInteRateUnit;
	//还款违约金率
	private BigDecimal lpcRepayLiquRate;
	//还款违约金率单位
	private String lpcRepayLiquRateUnit;
	//订单编号
	private String orderNo;

	private String remark;
	//逾期付款利率
	private BigDecimal lpcOverOutRate;
	//逾期付款利率单位
	private String lpcOverOutRateUnit;
	//付款违约金率
	private BigDecimal lpcPayLiquRate;
	//付款违约金率单位
	private String lpcPayLiquRateUnit;
	/**
	 * 设置：
	 */
	public void setLpcId (String lpcId) {
		this.lpcId = lpcId;
	}
	/**
	 * 获取：
	 */
	public String getLpcId() {
		return this.lpcId;
	}
	/**
	 * 设置：是否支持担保  0:不支持   1:支持
	 */
	public void setLpcIsGuarantee (String lpcIsGuarantee) {
		this.lpcIsGuarantee = lpcIsGuarantee;
	}
	/**
	 * 获取：是否支持担保  0:不支持   1:支持
	 */
	public String getLpcIsGuarantee() {
		return this.lpcIsGuarantee;
	}
	/**
	 * 设置：是否需要垫还  0:不支持  1:支持
	 */
	public void setLpcIsAdvance (String lpcIsAdvance) {
		this.lpcIsAdvance = lpcIsAdvance;
	}
	/**
	 * 获取：是否需要垫还  0:不支持  1:支持
	 */
	public String getLpcIsAdvance() {
		return this.lpcIsAdvance;
	}
	/**
	 * 设置：结息方式 0:放款日结息  1:到期日结息
	 */
	public void setLpcInterestType (String lpcInterestType) {
		this.lpcInterestType = lpcInterestType;
	}
	/**
	 * 获取：结息方式 0:放款日结息  1:到期日结息
	 */
	public String getLpcInterestType() {
		return this.lpcInterestType;
	}
	/**
	 * 设置：结息固定日日期
	 */
	public void setLpcInterestRegular (Date lpcInterestRegular) {
		this.lpcInterestRegular = lpcInterestRegular;
	}
	/**
	 * 获取：结息固定日日期
	 */
	public Date getLpcInterestRegular() {
		return this.lpcInterestRegular;
	}
	/**
	 * 设置：付息方式  0:固定日  1:放款日 2:到期日
	 */
	public void setLpcPayType (String lpcPayType) {
		this.lpcPayType = lpcPayType;
	}
	/**
	 * 获取：付息方式  0:固定日  1:放款日 2:到期日
	 */
	public String getLpcPayType() {
		return this.lpcPayType;
	}
	/**
	 * 设置：付息固定日日期
	 */
	public void setLpcPayRegular (Date lpcPayRegular) {
		this.lpcPayRegular = lpcPayRegular;
	}
	/**
	 * 获取：付息固定日日期
	 */
	public Date getLpcPayRegular() {
		return this.lpcPayRegular;
	}
	/**
	 * 设置：逾期贷款利率
	 */
	public void setLpcOverdueInteType (BigDecimal lpcOverdueInteType) {
		this.lpcOverdueInteType = lpcOverdueInteType;
	}
	/**
	 * 获取：逾期贷款利率
	 */
	public BigDecimal getLpcOverdueInteType() {
		return this.lpcOverdueInteType;
	}
	/**
	 * 设置：逾期贷款利率单位
	 */
	public void setLpcOverdueInteTypeUnit (String lpcOverdueInteTypeUnit) {
		this.lpcOverdueInteTypeUnit = lpcOverdueInteTypeUnit;
	}
	/**
	 * 获取：逾期贷款利率单位
	 */
	public String getLpcOverdueInteTypeUnit() {
		return this.lpcOverdueInteTypeUnit;
	}
	/**
	 * 设置：债务利率
	 */
	public void setLpcDebtInteRate (BigDecimal lpcDebtInteRate) {
		this.lpcDebtInteRate = lpcDebtInteRate;
	}
	/**
	 * 获取：债务利率
	 */
	public BigDecimal getLpcDebtInteRate() {
		return this.lpcDebtInteRate;
	}
	/**
	 * 设置：债务利率单位
	 */
	public void setLpcDebtInteRateUnit (String lpcDebtInteRateUnit) {
		this.lpcDebtInteRateUnit = lpcDebtInteRateUnit;
	}
	/**
	 * 获取：债务利率单位
	 */
	public String getLpcDebtInteRateUnit() {
		return this.lpcDebtInteRateUnit;
	}
	/**
	 * 设置：还款违约金率
	 */
	public void setLpcRepayLiquRate (BigDecimal lpcRepayLiquRate) {
		this.lpcRepayLiquRate = lpcRepayLiquRate;
	}
	/**
	 * 获取：还款违约金率
	 */
	public BigDecimal getLpcRepayLiquRate() {
		return this.lpcRepayLiquRate;
	}
	/**
	 * 设置：还款违约金率单位
	 */
	public void setLpcRepayLiquRateUnit (String lpcRepayLiquRateUnit) {
		this.lpcRepayLiquRateUnit = lpcRepayLiquRateUnit;
	}
	/**
	 * 获取：还款违约金率单位
	 */
	public String getLpcRepayLiquRateUnit() {
		return this.lpcRepayLiquRateUnit;
	}
	/**
	 * 设置：订单编号
	 */
	public void setOrderNo (String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取：订单编号
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getLpcOverOutRate() {
		return lpcOverOutRate;
	}
	public void setLpcOverOutRate(BigDecimal lpcOverOutRate) {
		this.lpcOverOutRate = lpcOverOutRate;
	}
	public String getLpcOverOutRateUnit() {
		return lpcOverOutRateUnit;
	}
	public void setLpcOverOutRateUnit(String lpcOverOutRateUnit) {
		this.lpcOverOutRateUnit = lpcOverOutRateUnit;
	}
	public BigDecimal getLpcPayLiquRate() {
		return lpcPayLiquRate;
	}
	public void setLpcPayLiquRate(BigDecimal lpcPayLiquRate) {
		this.lpcPayLiquRate = lpcPayLiquRate;
	}
	public String getLpcPayLiquRateUnit() {
		return lpcPayLiquRateUnit;
	}
	public void setLpcPayLiquRateUnit(String lpcPayLiquRateUnit) {
		this.lpcPayLiquRateUnit = lpcPayLiquRateUnit;
	}
	
}