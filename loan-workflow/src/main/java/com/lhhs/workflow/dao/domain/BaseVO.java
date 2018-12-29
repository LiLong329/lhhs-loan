package com.lhhs.workflow.dao.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhhs.loan.common.shared.page.BaseObject;
public class BaseVO  extends BaseObject{

	private static final long serialVersionUID = -5123479202031204686L;
	/**流程定义*/
	private String procDefId;
	private String procDefKey; 	// 流程定义Key（流程定义标识）
	private String procName; 	// 流程定义名称
	private String userId;//用户编号,登录名称
	private String category;//流程分类/系统标识
	private String categoryName;//流程分类名称
	
	private String nextAssignee; // 下一审批人ID多人,分割
	private String nextAssigneeName; // 下一审批人姓名多人,分割
	private String nextTaskName; // 下一任务名称，多个,分割
	private String nextTaskDefKey; // 下一任务Key，多个,分割
	private String businessId; // 业务绑定ID
	/**机构相关参数**/
	/** 事业部、产品线  选输 **/
	private String  officId;
	/** 销售组织 销售办公室 选输**/
	private String salesOrgId;
	/** 行政组织 选输**/
	private String depId;
	//流程启动的备注
	private String remark;
	
	
	private String productDpt; // 事业部
	private String branchCom; // 销售组织
	private String productLine; // 产品线
	private String salesOffice;//销售办公室
	private String department; // 行政组织

	
	private String departmentName; // 行政组织名称
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date beginDate;	// 查询时用,任务创建开始日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date endDate;	// 查询时用,任务创建结束日期
	
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	/**
	 * 获取流程定义KEY
	 * 
	 * @return
	 */
	public String getProcDefKey() {
		if (StringUtils.isEmpty(procDefKey) && !StringUtils.isEmpty(procDefId)) {
			procDefKey = StringUtils.split(procDefId, ":")[0];
		}
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getOfficId() {
		return officId;
	}
	public void setOfficId(String officId) {
		this.officId = officId;
	}
	public String getSalesOrgId() {
		return salesOrgId;
	}
	public void setSalesOrgId(String salesOrgId) {
		this.salesOrgId = salesOrgId;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getProductDpt() {
		return productDpt;
	}
	public void setProductDpt(String productDpt) {
		this.productDpt = productDpt;
	}
	public String getBranchCom() {
		return branchCom;
	}
	public void setBranchCom(String branchCom) {
		this.branchCom = branchCom;
	}
	public String getProductLine() {
		return productLine;
	}
	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}
	public String getSalesOffice() {
		return salesOffice;
	}
	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public String getNextTaskName() {
		return nextTaskName;
	}
	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}
	public String getNextTaskDefKey() {
		return nextTaskDefKey;
	}
	public void setNextTaskDefKey(String nextTaskDefKey) {
		this.nextTaskDefKey = nextTaskDefKey;
	}
	public String getBusinessId() {
		return businessId;
	}


	/**
	 * 业务ID可能有拼业务表
	 * @param businessId
	 */
	public void setBusinessId(String businessId) {
		if (businessId != null ) {
			String[] ss = businessId.split(":");
			if(ss.length>1){
				this.businessId=ss[1];
			}else{
				this.businessId=ss[0];
			}
			
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
