package com.lhhs.loan.dao.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanParasConfig;
/*
 *  订单跟新接口vo
 */
public class OrderInfoVo {

	private String orderInfo;
	private String borrower;
	private String houseList;
	private String orderNo;
	private String loanCapitalEarning;
	private String loanCapitalInfo;
	private String loanCapitalExpenditure;
	private String parasConfig;
	private String loanApplyDate;
	
	private String letEmployeeno;// 风控经理
	private Integer auditStatus;// 放款确认提交状态
	private Integer applyNode; // 放款申请节点(非正式)
	private Integer currentNode;  // 当前节点
	
	public Integer getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(Integer currentNode) {
		this.currentNode = currentNode;
	}
	public Integer getApplyNode() {
		return applyNode;
	}
	public void setApplyNode(Integer applyNode) {
		this.applyNode = applyNode;
	}
	private String shForm; // 放款确认审批意见
	
	public String getShForm() {
		return shForm;
	}
	public void setShForm(String shForm) {
		this.shForm = shForm;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getLetEmployeeno() {
		return letEmployeeno;
	}
	public void setLetEmployeeno(String letEmployeeno) {
		this.letEmployeeno = letEmployeeno;
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
	public String getLoanApplyDate() {
		return loanApplyDate;
	}
	public void setLoanApplyDate(String loanApplyDate) {
		this.loanApplyDate = loanApplyDate;
	}

	public OrderInfoConverter cv = new OrderInfoConverter();
	
	public  class OrderInfoConverter{
		public LoanOrderInfo loanOrderInfo() {
			LoanOrderInfoVo vo = orderInfo();
			
			LoanOrderInfo oi = new LoanOrderInfo();
		    oi.setAuditStatus(auditStatus);
			oi.setOrderNo(orderNo);
			oi.setContractNo(vo.getContractNo());
			oi.setArchivesNo(vo.getArchivesNo());
			oi.setCustomerManager(vo.getCustomerManager());
			oi.setReportName(vo.getReportName());
			oi.setAbutmentName(vo.getAbutmentName());
			oi.setOperatorDate(new Date());
			if(!StrUtils.isNullOrEmpty(loanApplyDate)){
				oi.setLoanApplyDate(DateUtils.String2Date(loanApplyDate));
			}
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
	}
	
	
	

}
