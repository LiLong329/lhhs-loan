package com.lhhs.workflow.dao.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskVo extends BaseVO{
	
	private static final long serialVersionUID = 5062837046311340817L;

    
	
	private String taskId; // 任务编号
	private String taskName; // 任务名称
	private String taskDefKey; // 任务定义Key（任务环节标识）
	private String title; // 任务标题
	private String comment; // 任务意见
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date taskCreateDate;//任务创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date taskEndDate;//任务完成时间
	private String durationTime;//任务历时
	private String formKey; // 待办任务跳转的地址(流程设置的跳转地址(表单编号))
	private String formUrl; // 待办任务跳转的地址(带系统访问地址的跳转地址(表单编号))
	
	private String version;//流程版本
	private String procInsId;//流程实例ID
	private String executionId;//运行实例无子流程时同流程实例ID
	private String assignee; // 任务执行人编号
	private String assigneeName; // 任务执行人名称

	
	private String pass; // 决议类型
	private String passName; // 决议类型名称
	private String status; // 任务状态（待处理：todo/待签收：claim/已完成：finish）
	private String statusName;//任务状态名称


	/** 当前任务流程变量  **/
	Map<String, Object> vars;
	/** 流程变量 **/
	Map<String, Object> proVars;
	
	/**
	 * 申请人/经办人ID
	 */
	private String organiser;
	/**
	 * 申请人/经办人名称
	 */
    private String organiserName;
    /**
     * 审批ip
     */
    private String ip;
	/**
	 * 审批渠道
	 */
    private String channel;
    /** 合作伙伴 **/
	private String  partner;

    /**
	 * 总金额
	 */
	private String amount;
	/**
	 * 订单单号
	 */
	private String orderNo;
	private String businessId;
    private String field1;

    private String field2;

    private String field3;

    private String field4;
    private String field5;

	private String operator; // 经办人
	private String operatorName; // 经办人名称
	private Long id;
	private String msg;
    /**开始节点**/
    private String startAct;
    /**结束节点**/
    private String endAct;
	private List<String> procInsIdList;//流程实例ID列表
	//包含的节点
	private String taskDefKeyList;
	//排除的节点
	private String noTaskDefKeyList;
	//排除的决议类型
	private String noPassList;
	//决议类型列表
	private List<String> passList;
	public TaskVo(){
		super();
	}
	
	public Map<String, Object> getVars() {
		return vars;
	}
	/**
	 * 
	 * 设置业务数据
	 */
	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
		if(StringUtils.isEmpty(this.operator)&&vars.get("apply")!=null){
			this.operator=(String)vars.get("apply");
		}
		if(StringUtils.isEmpty(this.operatorName)&&vars.get("applyName")!=null){
			this.operatorName=(String)vars.get("applyName");
		}
		if(StringUtils.isEmpty(getRemark())&&vars.get("remark")!=null){
			setRemark((String)vars.get("remark"));
		}
		if(StringUtils.isEmpty(this.amount)&&vars.get("amount")!=null){
			this.amount=(String)vars.get("amount");
		}
		if(StringUtils.isEmpty(this.orderNo)&&vars.get("orderNo")!=null){
			this.orderNo=(String)vars.get("orderNo");
		}
		if(StringUtils.isEmpty(this.field1)&&vars.get("field1")!=null){
			this.field1=(String)vars.get("field1");
		}
		if(StringUtils.isEmpty(this.field2)&&vars.get("field2")!=null){
			this.field2=(String)vars.get("field2");
		}
		if(StringUtils.isEmpty(this.field3)&&vars.get("field3")!=null){
			this.field3=(String)vars.get("field3");
		}
		if(StringUtils.isEmpty(this.field4)&&vars.get("field4")!=null){
			this.field4=(String)vars.get("field4");
		}
		if(StringUtils.isEmpty(this.field5)&&vars.get("field5")!=null){
			this.field5=(String)vars.get("field5");
		}
		if(StringUtils.isEmpty(getCompanyId())&&vars.get("companyId")!=null){
			setCompanyId((String)vars.get("companyId"));
		}
		if(StringUtils.isEmpty(getUnionId())&&vars.get("unionId")!=null){
			setUnionId((String)vars.get("unionId"));
		}
		if(StringUtils.isEmpty(getDepId())&&vars.get("depId")!=null){
			setDepId((String)vars.get("depId"));
		}
	    
	}
	/**
	 * 是否是已完成任务
	 * 
	 * @return
	 */
	public boolean isFinishTask() {
		return "finish".equals(status) || StringUtils.isEmpty(taskId);
	}
	/**获取任务标题*/
	public String getTitle() {
		if (title == null && vars != null) {
			title = (String) vars.get("title");
		}
		return title;
	}

	/**
	 * 是否是一个待办任务
	 * 
	 * @return
	 */
	public boolean isTodoTask() {
		return "todo".equals(status) || "claim".equals(status);
	}


	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPassName() {
		return passName;
	}
	public void setPassName(String passName) {
		this.passName = passName;
	}
	public Date getTaskCreateDate() {
		return taskCreateDate;
	}
	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public Map<String, Object> getProVars() {
		return proVars;
	}
	public void setProVars(Map<String, Object> proVars) {
		this.proVars = proVars;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getOrganiser() {
		return organiser;
	}
	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}
	public String getOrganiserName() {
		return organiserName;
	}
	public void setOrganiserName(String organiserName) {
		this.organiserName = organiserName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getStartAct() {
		return startAct;
	}

	public void setStartAct(String startAct) {
		this.startAct = startAct;
	}

	public String getEndAct() {
		return endAct;
	}

	public void setEndAct(String endAct) {
		this.endAct = endAct;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public List<String> getProcInsIdList() {
		return procInsIdList;
	}

	public void setProcInsIdList(List<String> procInsIdList) {
		this.procInsIdList = procInsIdList;
	}

	public String getTaskDefKeyList() {
		return taskDefKeyList;
	}

	public void setTaskDefKeyList(String taskDefKeyList) {
		this.taskDefKeyList = taskDefKeyList;
	}

	public String getNoTaskDefKeyList() {
		return noTaskDefKeyList;
	}

	public void setNoTaskDefKeyList(String noTaskDefKeyList) {
		this.noTaskDefKeyList = noTaskDefKeyList;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getNoPassList() {
		return noPassList;
	}

	public void setNoPassList(String noPassList) {
		this.noPassList = noPassList;
	}

	public List<String> getPassList() {
		return passList;
	}

	public void setPassList(List<String> passList) {
		this.passList = passList;
	}

}
