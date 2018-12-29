package com.lhhs.workflow.dao.domain;

import java.util.Date;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhhs.loan.common.shared.page.BaseObject;
import com.lhhs.workflow.common.enumeration.ActCategory;
import com.lhhs.workflow.common.utils.Constant;

public class ProcessVO extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5306126638915189364L;
	private String procDefId; 	// 流程定义ID
	private String procDefKey; 	// 流程定义Key（流程定义标识）
	
	private String category; 	// 流程分类
	private String catName; 	// 流程分类名称
	private String procName; 	// 流程名称
	private String procVersion; 	//流程版本
	
	private String deploymentId; 	// 流程部署ID
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date deploymentTime; 	// 部署时间
	private String resourceName; 	// 流程XML
	private String diagramResourceName; 	// 流程部署图片 
	private boolean suspended;//是否挂起状态
	private String statusName;//流程状态名称:正常、挂起
	
	private String executionId;//执行ID
	private String procInsId;//流程实例ID
	private String activityId;//当前环节
	private String businessKey;//业务编号
	private String taskIdList;//当前任务ID列表
	public ProcessVO(){
	}
	public ProcessVO(Act act){
		this.procDefId=act.getProcDef().getId(); 	// 流程定义ID
		this.procDefKey=act.getProcDef().getKey(); 	// 流程定义Key（流程定义标识）
		this.procName=act.getProcDef().getName(); 	// 流程定义名称
		this.category=act.getProcDef().getCategory(); 	// 流程分类
		this.procVersion="V:"+act.getProcDef().getVersion(); 	//流程版本
		this.deploymentId=act.getDeployment().getId(); 	// 流程部署ID
		this.deploymentTime=act.getDeployment().getDeploymentTime(); 	// 部署时间
		this.resourceName=act.getProcDef().getResourceName(); 	// 流程XML
		this.diagramResourceName=act.getProcDef().getDiagramResourceName(); 	// 流程部署图片
		this.suspended=act.getProcDef().isSuspended();//流程状态
		
		if(this.suspended){
			this.statusName=Constant.ProcDefStatus.SUSPEND;
		}else{
			this.statusName=Constant.ProcDefStatus.ACTIVE;
		}
	}
	public ProcessVO(ProcessInstance process){
		this.procDefId=process.getProcessDefinitionId(); 	// 流程定义ID
		this.procDefKey=process.getProcessDefinitionKey(); 	// 流程定义Key（流程定义标识）
		this.procName=process.getProcessDefinitionName(); 	// 流程名称
		this.procVersion="V:"+process.getProcessDefinitionVersion(); 	//流程版本
		this.executionId=process.getId();//执行ID
		this.procInsId=process.getProcessInstanceId();//流程实例ID
		this.activityId=process.getActivityId();//当前环节
		this.suspended=process.isSuspended();//流程状态
		this.businessKey=process.getBusinessKey();
		if(this.suspended){
			this.statusName=Constant.ProcDefStatus.SUSPEND;
		}else{
			this.statusName=Constant.ProcDefStatus.ACTIVE;
		}
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcVersion() {
		return procVersion;
	}
	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public Date getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getDiagramResourceName() {
		return diagramResourceName;
	}
	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public String getCatName() {
		
		return ActCategory.getName(this.category,this.category);
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getTaskIdList() {
		return taskIdList;
	}
	public void setTaskIdList(String taskIdList) {
		this.taskIdList = taskIdList;
	}

}
