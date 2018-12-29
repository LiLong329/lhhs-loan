package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanFundTask extends BaseObject{
	private static final long serialVersionUID = 1L;
	//
	private Long lftFundid;
	//
	private Integer lftNode;
	//
	private Integer lftNodeStatus;
	//任务审批姓名
	private String lftEmployeename;
	//任务审批时间
	private Date lftTime;
	//任务审批结果
	private Integer lftResult;
	//任务审批意见
	private String lftSuggestion;
	//备注
	private String lftRemark;
	//任务审批人编号
	private Integer lftEmployeeno;
	//订单编号
	private String lftDeclarationformid;


	/**
	 * 设置：
	 */
	public void setLftFundid (Long lftFundid) {
		this.lftFundid = lftFundid;
	}
	/**
	 * 获取：
	 */
	public Long getLftFundid() {
		return this.lftFundid;
	}
	/**
	 * 设置：
	 */
	public void setLftNode (Integer lftNode) {
		this.lftNode = lftNode;
	}
	/**
	 * 获取：
	 */
	public Integer getLftNode() {
		return this.lftNode;
	}
	/**
	 * 设置：
	 */
	public void setLftNodeStatus (Integer lftNodeStatus) {
		this.lftNodeStatus = lftNodeStatus;
	}
	/**
	 * 获取：
	 */
	public Integer getLftNodeStatus() {
		return this.lftNodeStatus;
	}
	/**
	 * 设置：任务审批姓名
	 */
	public void setLftEmployeename (String lftEmployeename) {
		this.lftEmployeename = lftEmployeename;
	}
	/**
	 * 获取：任务审批姓名
	 */
	public String getLftEmployeename() {
		return this.lftEmployeename;
	}
	/**
	 * 设置：任务审批时间
	 */
	public void setLftTime (Date lftTime) {
		this.lftTime = lftTime;
	}
	/**
	 * 获取：任务审批时间
	 */
	public Date getLftTime() {
		return this.lftTime;
	}
	/**
	 * 设置：任务审批结果
	 */
	public void setLftResult (Integer lftResult) {
		this.lftResult = lftResult;
	}
	/**
	 * 获取：任务审批结果
	 */
	public Integer getLftResult() {
		return this.lftResult;
	}
	/**
	 * 设置：任务审批意见
	 */
	public void setLftSuggestion (String lftSuggestion) {
		this.lftSuggestion = lftSuggestion;
	}
	/**
	 * 获取：任务审批意见
	 */
	public String getLftSuggestion() {
		return this.lftSuggestion;
	}
	/**
	 * 设置：备注
	 */
	public void setLftRemark (String lftRemark) {
		this.lftRemark = lftRemark;
	}
	/**
	 * 获取：备注
	 */
	public String getLftRemark() {
		return this.lftRemark;
	}
	/**
	 * 设置：任务审批人编号
	 */
	public void setLftEmployeeno (Integer lftEmployeeno) {
		this.lftEmployeeno = lftEmployeeno;
	}
	/**
	 * 获取：任务审批人编号
	 */
	public Integer getLftEmployeeno() {
		return this.lftEmployeeno;
	}
	/**
	 * 设置：订单编号
	 */
	public void setLftDeclarationformid (String lftDeclarationformid) {
		this.lftDeclarationformid = lftDeclarationformid;
	}
	/**
	 * 获取：订单编号
	 */
	public String getLftDeclarationformid() {
		return this.lftDeclarationformid;
	}
}