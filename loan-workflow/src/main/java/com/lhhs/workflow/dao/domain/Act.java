package com.lhhs.workflow.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhhs.loan.common.shared.page.BaseObject;

/**
 * 工作流Entity
 * 
 * @author ThinkGem
 * @version 2013-11-03
 */
public class Act extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private String userId;// 登录名
	private String taskId; // 任务编号
	private String taskName; // 任务名称
	private String taskDefKey; // 任务定义Key（任务环节标识） 

	private String procInsId; // 流程实例ID
	private String procDefId; // 流程定义ID
	private String procDefKey; // 流程定义Key（流程定义标识）
	
	private String businessTable; // 业务绑定Table
	private String businessId; // 业务绑定ID

	private String title; // 任务标题

	private String status; // 任务状态（todo/claim/finish）

	private String procDefUrl; // 先取任务节点配置的URL，如果为空取流程procDefUrl 各系统单独写待办任务时使用
	private String comment; // 任务意见
	private String flag; // 意见状态

	//private Task task; // 任务对象
	private ProcessDefinition procDef; // 流程定义对象
	private ProcessInstance procIns; // 流程实例对象
	private Deployment deployment;// 流程部署对象
	private HistoricTaskInstance histTask; // 历史任务
	private HistoricActivityInstance histIns; // 历史活动任务

	private String assignee; // 任务执行人编号
	private String assigneeName; // 任务执行人名称

	private Map vars; // 流程变量
	
	private Date beginDate; // 开始查询日期
	private Date endDate; // 结束查询日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date taskCreateDate;
	private String durationTime;//过去的任务历时
	public Act() {
		super();
	}

	
	/**获取流程分类*/
	public String getCategory(){
		if(procDef != null) return procDef.getCategory();
		return "";
	}
	
	/**获取流程名称*/
	public String getProcName(){
		if(procDef != null) return procDef.getName();
		return "";
	}
	/**获取流程版本*/
	public String getVersion(){
		if(procDef != null) return "V: "+procDef.getVersion();
		return "V: ";
	}
	/**获取任务标题*/
	public String getTitle() {
		if (title == null && vars != null) {
			title = (String) vars.get("title");
		}
		return title;
	}
	/**获取流程图名称*/
	public String getDiagramResourceName(){
		if(procDef != null) return procDef.getDiagramResourceName();
		return "";
	}
	/**流程部署时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeploymentTime(){
		if(deployment != null) return deployment.getDeploymentTime();
		return null;
	}

	public String getTaskId() {
		/*if (taskId == null && task != null) {
			taskId = task.getId();
		}*/
		if(taskId == null && histTask != null){
			taskId = histTask.getId();
		}
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		/*if (taskName == null && task != null) {
			taskName = task.getName();
		}*/
		if(taskName == null && histTask != null){
			taskName = histTask.getName();
		}
		
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskDefKey() {
		/*if (taskDefKey == null && task != null) {
			taskDefKey = task.getTaskDefinitionKey();
		}*/
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	/*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskCreateDate() {
		if (task != null) {
			return task.getCreateTime();
		}
		if (histTask != null) {
			return histTask.getCreateTime();
		}
		return null;
	}*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndDate() {
		if (histTask != null) {
			return histTask.getEndTime();
		}
		return null;
	}

/*	@JsonIgnore
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
*/
	@JsonIgnore
	public ProcessDefinition getProcDef() {
		return procDef;
	}

	public void setProcDef(ProcessDefinition procDef) {
		this.procDef = procDef;
	}

	/*
	 * public String getProcDefName() { return procDef.getName(); }
	 */
	@JsonIgnore
	public ProcessInstance getProcIns() {
		return procIns;
	}
	public void setProcIns(ProcessInstance procIns) {
		this.procIns = procIns;
		if (procIns != null && procIns.getBusinessKey() != null) {
			String[] ss = procIns.getBusinessKey().split(":");
			if(ss.length>1){
				setBusinessTable(ss[0]);
				setBusinessId(ss[1]);
			}else{
				setBusinessId(ss[0]);
			}
			
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public HistoricTaskInstance getHistTask() {
		return histTask;
	}

	public void setHistTask(HistoricTaskInstance histTask) {
		this.histTask = histTask;
	}

	@JsonIgnore
	public HistoricActivityInstance getHistIns() {
		return histIns;
	}

	public void setHistIns(HistoricActivityInstance histIns) {
		this.histIns = histIns;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProcDefId() {
		/*if (procDefId == null && task != null) {
			procDefId = task.getProcessDefinitionId();
		}*/
		if(procDefId == null && histTask != null){
			procDefId = histTask.getProcessDefinitionId();
		}
		/**通过流程定义获取流程定义ID*/
		if(procDefId == null && procDef != null){
			procDefId = procDef.getId();
		}
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInsId() {
		/*if (procInsId == null && task != null) {
			procInsId = task.getProcessInstanceId();
		}*/
		if (procInsId == null && histTask != null) {
			procInsId = histTask.getProcessInstanceId();
		}
		return procInsId;
	}
	
	
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssignee() {
		
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map getVars() {
		return vars;
	}


	public void setVars(Map vars) {
		this.vars = vars;
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

	/**
	 * 获取过去的任务历时
	 * 
	 * @return
	 */
	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}


	/**
	 * 是否是一个待办任务
	 * 
	 * @return
	 */
	public boolean isTodoTask() {
		return "todo".equals(status) || "claim".equals(status);
	}

	/**
	 * 是否是已完成任务
	 * 
	 * @return
	 */
	public boolean isFinishTask() {
		return "finish".equals(status) || StringUtils.isEmpty(taskId);
	}
	@JsonIgnore
	public Deployment getDeployment() {
		return deployment;
	}

	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}

	
	public Date getTaskCreateDate() {
		return taskCreateDate;
	}


	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}


	public String getProcDefUrl() {
		return procDefUrl;
	}


	public void setProcDefUrl(String procDefUrl) {
		this.procDefUrl = procDefUrl;
	}

}
