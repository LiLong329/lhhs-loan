package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanOrderInfo extends BaseObject{
	private static final long serialVersionUID = -9092226976646589525L;

	private String orderNo;

    private String productNo;

    private String providerNo;

    private String childProductNo;

    private Integer customerManager;

    private Integer customerOrigin;

    private Integer storeId;

    private String province;

    private String city;

    private Integer orderStatus;

    private Date applyDate;

    private Date operatorDate;

    private Integer approvalNode;

    private Integer approvalNodeStatus;
    
    private String companyId;
    
    private String unionId;
    
    private String auditor;
    
    private String contractNo;

    private String archivesNo;
    
    private String customerId;
    
    private String reportName;
    
    private String abutmentName;
    
    private Integer fundFlowNode;
    
    private Integer auditStatus;
    
    private Date loanApplyDate;
    
    private String loanMethod;
    
    private String procInsId;

    private String nextTaskDefKey;

    private String nextTaskName;

    private String nextAssignee;

    private String nextAssigneeName;
    
    private String isLoanAdd;
    //产品名称
    private String productName;
    //当前节点
    private String taskDefKey;
    //原节点状态
    private Integer orderStatusOld;
    //客户来源
    private String customerSource;
    //业务类型
    private String businessType;
    //意向客户-创建人归属分公司
    private String createrCompanyId;
    //意向客户-指派人归属分公司
    private String appointCompanyId;
    //客户经理账号
    private String managerAccount;
    
    private String depId;
    //放款申请人
    private String fkApplyUser;
    
    private String groupId;
    //意向客户编号
    private String custId;
    //意向客户ID
    private String intentUserId;
    
    //客户经理组别
    private String  customerManagerGroupName;
    private Integer customerManagerGroupId;
    //客户经理分公司
    private String  customerManagerComponyName;
    private Integer customerManagerComponyId;
    //客户经理部门
    private String  customerManagerDeptName;
    private Integer customerManagerDeptId;
    //客户经理城市
    private String customerManagerCity;
    //客户经理省
    private String customerManagerProvince;
    //客户经理创建开始时间
    private Date customerManagerBeginingTime;
    //客户经理创建结束时间
    private Date customerManagerEndingTime;
    //客户经理创建时间
    private Date customerManagerCreateTime;
    //客户经理姓名
    private String customerManagerName;
    
    private Integer orderCount;//报单量
    private BigDecimal dealAmount;//放款量
    private String timeUnit;//时间单位
    private String weekNum;
    
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getCreaterCompanyId() {
		return createrCompanyId;
	}

	public void setCreaterCompanyId(String createrCompanyId) {
		this.createrCompanyId = createrCompanyId;
	}

	public String getAppointCompanyId() {
		return appointCompanyId;
	}

	public void setAppointCompanyId(String appointCompanyId) {
		this.appointCompanyId = appointCompanyId;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getFundFlowNode() {
		return fundFlowNode;
	}

	public void setFundFlowNode(Integer fundFlowNode) {
		this.fundFlowNode = fundFlowNode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo == null ? null : providerNo.trim();
    }

    public String getChildProductNo() {
        return childProductNo;
    }

    public void setChildProductNo(String childProductNo) {
        this.childProductNo = childProductNo == null ? null : childProductNo.trim();
    }

    public Integer getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(Integer customerManager) {
        this.customerManager = customerManager;
    }

    public Integer getCustomerOrigin() {
        return customerOrigin;
    }

    public void setCustomerOrigin(Integer customerOrigin) {
        this.customerOrigin = customerOrigin;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }

    public Integer getApprovalNode() {
        return approvalNode;
    }

    public void setApprovalNode(Integer approvalNode) {
        this.approvalNode = approvalNode;
    }

    public Integer getApprovalNodeStatus() {
        return approvalNodeStatus;
    }

    public void setApprovalNodeStatus(Integer approvalNodeStatus) {
        this.approvalNodeStatus = approvalNodeStatus;
    }
    
    public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getAbutmentName() {
		return abutmentName;
	}

	public void setAbutmentName(String abutmentName) {
		this.abutmentName = abutmentName;
	}
	
	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getLoanApplyDate() {
		return loanApplyDate;
	}

	public void setLoanApplyDate(Date loanApplyDate) {
		this.loanApplyDate = loanApplyDate;
	}

	public String getLoanMethod() {
		return loanMethod;
	}

	public void setLoanMethod(String loanMethod) {
		this.loanMethod = loanMethod == null ? null : loanMethod.trim();
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getNextTaskDefKey() {
		return nextTaskDefKey;
	}

	public void setNextTaskDefKey(String nextTaskDefKey) {
		this.nextTaskDefKey = nextTaskDefKey;
	}

	public String getNextTaskName() {
		return nextTaskName;
	}

	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}

	public String getNextAssignee() {
		return nextAssignee;
	}

	public void setNextAssignee(String nextAssignee) {
		this.nextAssignee = nextAssignee;
	}

	public String getNextAssigneeName() {
		return nextAssigneeName;
	}

	public void setNextAssigneeName(String nextAssigneeName) {
		this.nextAssigneeName = nextAssigneeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getIsLoanAdd() {
		return isLoanAdd;
	}

	public void setIsLoanAdd(String isLoanAdd) {
		this.isLoanAdd = isLoanAdd;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public Integer getOrderStatusOld() {
		return orderStatusOld;
	}

	public void setOrderStatusOld(Integer orderStatusOld) {
		this.orderStatusOld = orderStatusOld;
	}

	public String getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}

	public String getFkApplyUser() {
		return fkApplyUser;
	}

	public void setFkApplyUser(String fkApplyUser) {
		this.fkApplyUser = fkApplyUser;
	}

	public String getIntentUserId() {
	
		return intentUserId;
	}

	public void setIntentUserId(String intentUserId) {
	
		this.intentUserId = intentUserId;
	}

	public String getCustomerManagerGroupName() {
		return customerManagerGroupName;
	}

	public void setCustomerManagerGroupName(String customerManagerGroupName) {
		this.customerManagerGroupName = customerManagerGroupName;
	}

	public Integer getCustomerManagerGroupId() {
		return customerManagerGroupId;
	}

	public void setCustomerManagerGroupId(Integer customerManagerGroupId) {
		this.customerManagerGroupId = customerManagerGroupId;
	}

	public String getCustomerManagerComponyName() {
		return customerManagerComponyName;
	}

	public void setCustomerManagerComponyName(String customerManagerComponyName) {
		this.customerManagerComponyName = customerManagerComponyName;
	}

	public Integer getCustomerManagerComponyId() {
		return customerManagerComponyId;
	}

	public void setCustomerManagerComponyId(Integer customerManagerComponyId) {
		this.customerManagerComponyId = customerManagerComponyId;
	}

	public String getCustomerManagerDeptName() {
		return customerManagerDeptName;
	}

	public void setCustomerManagerDeptName(String customerManagerDeptName) {
		this.customerManagerDeptName = customerManagerDeptName;
	}

	public Integer getCustomerManagerDeptId() {
		return customerManagerDeptId;
	}

	public void setCustomerManagerDeptId(Integer customerManagerDeptId) {
		this.customerManagerDeptId = customerManagerDeptId;
	}

	public String getCustomerManagerCity() {
		return customerManagerCity;
	}

	public void setCustomerManagerCity(String customerManagerCity) {
		this.customerManagerCity = customerManagerCity;
	}

	public String getCustomerManagerProvince() {
		return customerManagerProvince;
	}

	public void setCustomerManagerProvince(String customerManagerProvince) {
		this.customerManagerProvince = customerManagerProvince;
	}

	public Date getCustomerManagerBeginingTime() {
		return customerManagerBeginingTime;
	}

	public void setCustomerManagerBeginingTime(Date customerManagerBeginingTime) {
		this.customerManagerBeginingTime = customerManagerBeginingTime;
	}

	public Date getCustomerManagerEndingTime() {
		return customerManagerEndingTime;
	}

	public void setCustomerManagerEndingTime(Date customerManagerEndingTime) {
		this.customerManagerEndingTime = customerManagerEndingTime;
	}

	public Date getCustomerManagerCreateTime() {
		return customerManagerCreateTime;
	}

	public void setCustomerManagerCreateTime(Date customerManagerCreateTime) {
		this.customerManagerCreateTime = customerManagerCreateTime;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(BigDecimal dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(String weekNum) {
		this.weekNum = weekNum;
	}

	

}