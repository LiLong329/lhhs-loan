/**
 * Project Name:loan-dao
 * File Name:AuditParamVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年10月17日下午1:56:58
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanParasConfig;

/**
 * ClassName:AuditParamVo <br/>
 * Function: 流程审核提交参数VO <br/>
 * Date:     2017年10月17日 下午1:56:58 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public class AuditParamVo extends LoanOrderInfo {
	private static final long serialVersionUID = 1L;
	private LoanEmp loanEmp;//当前审核人信息
	private String letResult; //项目审批结果
	private String letRemark; //项目审批意见
	private Integer letNode;//节点编号
	private String flowLetNode;//补件方向
	private Long fundSide;//资金方
	private String isPayPlanTemp;//是否生成还款计划
	private String isLoanRecordTemp;//是否放款记录
	private String isPayPlanCompanyTemp;//是否待付款计划
	private String actualLoanDate;//实际放款时间
	private String historyLoanMethod;//历史放款方式
	//工作流使用
	private String wf_group;//会签岗位(岗位变量)
	private String wf_user;//人员变量
	private String taskDefKey;//节点KEY
	private String taskId;//任务编号
	
	//放款申请使用
	private String orderInfo;//订单信息JSON
	private String borrower;//借款人信息JSON
	private String houseList;//房产信息LIST JSON
	private String orderNo;
	private String loanCapitalEarning;
	private String loanCapitalInfo;
	private String loanCapitalExpenditure;
	private String parasConfig;
	private String type;//是否跳过节点
	private String relationPeopleInfo;//关联人信息
	private String fild3;//三方流程居间非担保跳过下户
	
	public String getFild3() {
		return fild3;
	}
	public void setFild3(String fild3) {
		this.fild3 = fild3;
	}
	public LoanEmp getLoanEmp() {
		return loanEmp;
	}
	public void setLoanEmp(LoanEmp loanEmp) {
		this.loanEmp = loanEmp;
	}
	public String getLetResult() {
		return letResult;
	}
	public void setLetResult(String letResult) {
		this.letResult = letResult;
	}
	public String getLetRemark() {
		return letRemark;
	}
	public void setLetRemark(String letRemark) {
		this.letRemark = letRemark;
	}
	public Integer getLetNode() {
		return letNode;
	}
	public void setLetNode(Integer letNode) {
		this.letNode = letNode;
	}
	public String getFlowLetNode() {
		return flowLetNode;
	}
	public void setFlowLetNode(String flowLetNode) {
		this.flowLetNode = flowLetNode;
	}
	public Long getFundSide() {
		return fundSide;
	}
	public void setFundSide(Long fundSide) {
		this.fundSide = fundSide;
	}
	public String getIsPayPlanTemp() {
		return isPayPlanTemp;
	}
	public void setIsPayPlanTemp(String isPayPlanTemp) {
		this.isPayPlanTemp = isPayPlanTemp;
	}
	public String getIsLoanRecordTemp() {
		return isLoanRecordTemp;
	}
	public void setIsLoanRecordTemp(String isLoanRecordTemp) {
		this.isLoanRecordTemp = isLoanRecordTemp;
	}
	public String getIsPayPlanCompanyTemp() {
		return isPayPlanCompanyTemp;
	}
	public void setIsPayPlanCompanyTemp(String isPayPlanCompanyTemp) {
		this.isPayPlanCompanyTemp = isPayPlanCompanyTemp;
	}
	public String getActualLoanDate() {
		return actualLoanDate;
	}
	public void setActualLoanDate(String actualLoanDate) {
		this.actualLoanDate = actualLoanDate;
	}
	public String getHistoryLoanMethod() {
		return historyLoanMethod;
	}
	public void setHistoryLoanMethod(String historyLoanMethod) {
		this.historyLoanMethod = historyLoanMethod;
	}
	public String getWf_group() {
		return wf_group;
	}
	public void setWf_group(String wf_group) {
		this.wf_group = wf_group;
	}
	public String getWf_user() {
		return wf_user;
	}
	public void setWf_user(String wf_user) {
		this.wf_user = wf_user;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getHouseList() {
		return houseList;
	}
	public void setHouseList(String houseList) {
		this.houseList = houseList;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLoanCapitalEarning() {
		return loanCapitalEarning;
	}
	public void setLoanCapitalEarning(String loanCapitalEarning) {
		this.loanCapitalEarning = loanCapitalEarning;
	}
	public String getLoanCapitalInfo() {
		return loanCapitalInfo;
	}
	public void setLoanCapitalInfo(String loanCapitalInfo) {
		this.loanCapitalInfo = loanCapitalInfo;
	}
	public String getLoanCapitalExpenditure() {
		return loanCapitalExpenditure;
	}
	public void setLoanCapitalExpenditure(String loanCapitalExpenditure) {
		this.loanCapitalExpenditure = loanCapitalExpenditure;
	}
	public String getParasConfig() {
		return parasConfig;
	}
	public void setParasConfig(String parasConfig) {
		this.parasConfig = parasConfig;
	}
	public String getRelationPeopleInfo() {
		return relationPeopleInfo;
	}
	public void setRelationPeopleInfo(String relationPeopleInfo) {
		this.relationPeopleInfo = relationPeopleInfo;
	}
	
	public LoanOrderInfo loanOrderInfo() {
		LoanOrderInfoVo vo = orderInfo();
		LoanOrderInfo oi = new LoanOrderInfo();
	    //oi.setAuditStatus(auditStatus);
		oi.setOrderNo(orderNo);
		oi.setContractNo(vo.getContractNo());
		oi.setArchivesNo(vo.getArchivesNo());
		oi.setCustomerManager(vo.getCustomerManager());
		oi.setDepId(vo.getDepId());
		oi.setReportName(vo.getReportName());
		oi.setAbutmentName(vo.getAbutmentName());
		oi.setOperatorDate(new Date());
		//放款申请时间
		oi.setLoanApplyDate(new Date());
		//设置下一审批人审批节点
		oi.setNextTaskName(this.getNextTaskName());
		oi.setNextTaskDefKey(this.getNextTaskDefKey());
		oi.setNextAssignee(this.getNextAssignee());
		oi.setNextAssigneeName(this.getNextAssigneeName());
		return oi;
	}
	
	public LoanOrderInfoDetail loanOrderInfoDetail() {
		LoanOrderInfoVo vo = orderInfo();
		LoanOrderInfoDetail oid = new LoanOrderInfoDetail();
		oid.setOrderNo(orderNo);
		oid.setLoanAmount(vo.getLoanAmount());
		oid.setLoanRate(vo.getLoanRate());
		oid.setLoanRateUnit(vo.getLoanRateUnit());
		oid.setLoanTerm(vo.getLoanTerm());
		oid.setLoanTermUnit(vo.getLoanTermUnit());
		oid.setRepayment(vo.getRepayment());
		return oid;
	}
	
	private LoanOrderInfoVo orderInfo() {
		LoanOrderInfoVo parseObject = JSON.parseObject(orderInfo, LoanOrderInfoVo.class);
		parseObject.setOrderNo(orderNo);
		return parseObject;
	}
	public LoanOrderBorrowerExtendWithBLOBs orderBorrower() {
		LoanOrderBorrowerExtendWithBLOBs parseObject = JSON.parseObject(borrower, LoanOrderBorrowerExtendWithBLOBs.class);
		parseObject.setOrderNo(orderNo);
		return parseObject;
	}
	public List<LoanOrderHouseExtend> houseList() {
		List<LoanOrderHouseExtend> parseArray = JSON.parseArray(houseList, LoanOrderHouseExtend.class);
		if(parseArray == null || parseArray.size() == 0) return new ArrayList<LoanOrderHouseExtend>();
		for (LoanOrderHouseExtend loanOrderHouseExtend : parseArray) {
			loanOrderHouseExtend.setOrderNo(orderNo);
		}
		return parseArray;
	}
	public List<LoanCapitalInfo> loanCapitalInfoList() {
		List<LoanCapitalInfo> parseArray = JSON.parseArray(loanCapitalInfo, LoanCapitalInfo.class);
		if(parseArray == null || parseArray.size() == 0) return new ArrayList<LoanCapitalInfo>();
		for (LoanCapitalInfo loanCapitalInfo : parseArray) {
			loanCapitalInfo.setOrderNo(orderNo);
		}
		return parseArray;
	}
	public List<LoanCapitalEarning> loanCapitalEarningList () {
		List<LoanCapitalEarning> parseArray = JSON.parseArray(loanCapitalEarning, LoanCapitalEarning.class);
		if(parseArray == null || parseArray.size() == 0) return new ArrayList<LoanCapitalEarning>();
		for (LoanCapitalEarning loanCapitalEarning : parseArray) {
			loanCapitalEarning.setOrderId(orderNo);
		}
		return parseArray;
	}
	public List<LoanCapitalExpenditure> loanCapitalExpenditureList() {
		List<LoanCapitalExpenditure> parseArray = JSON.parseArray(loanCapitalExpenditure, LoanCapitalExpenditure.class);
		if(parseArray == null || parseArray.size() == 0) return new ArrayList<LoanCapitalExpenditure>();
		for (LoanCapitalExpenditure loanCapitalExpenditure : parseArray) {
			loanCapitalExpenditure.setOrderId(orderNo);
		}
		return parseArray;
	}
	public LoanParasConfig loanParasConfig() {
		LoanParasConfig parseObject = JSON.parseObject(parasConfig, LoanParasConfig.class);
		parseObject.setOrderNo(orderNo);
		return parseObject;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}

