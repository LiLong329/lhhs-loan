package com.lhhs.workflow.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

import com.lhhs.workflow.common.utils.Constant.TaskStatus;
import com.lhhs.workflow.dao.domain.TaskVo;
import com.lhhs.workflow.dao.domain.VActHaddoVO;
import com.lhhs.workflow.dao.domain.VActToDoVO;

/**
 * 流程工具
 * @author ThinkGem
 * @version 2013-11-03
 */
public class ActUtils {

	/**
	 * 定义流程定义KEY，必须以“PD_”开头
	 * 组成结构：string[]{"流程标识","业务主表表名"}
	 */
	public static final String[] PD_LEAVE = new String[]{"leave", "oa_leave"};
	public static final String[] PD_TEST_AUDIT = new String[]{"test_audit", "oa_test_audit"};

	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @param category 流程分类(系统标识)
	 * @return
	 */
	public static String getFormUrl(String formKey, TaskVo actVo,String category){
		
		StringBuilder formUrl = new StringBuilder();
		String formServerUrl = GlobalWorkFlow.getConfig("activiti.form.server."+category);
		if (StringUtils.isBlank(formServerUrl)){
			//formUrl.append(GlobalWorkFlow.getAdminPath());
		}else{
			formUrl.append(formServerUrl);
		}
		
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("taskId=").append(actVo.getTaskId() != null ? actVo.getTaskId() : "");
		formUrl.append("&taskName=").append(actVo.getTaskName() != null ? Encodes.urlEncode(actVo.getTaskName()) : "");
		formUrl.append("&taskDefKey=").append(actVo.getTaskDefKey() != null ? actVo.getTaskDefKey() : "");
		formUrl.append("&procInsId=").append(actVo.getProcInsId() != null ? actVo.getProcInsId() : "");
		formUrl.append("&procDefId=").append(actVo.getProcDefId() != null ? actVo.getProcDefId() : "");
		formUrl.append("&procDefKey=").append(actVo.getProcDefKey() != null ? actVo.getProcDefKey() : "");
		formUrl.append("&businessId=").append(actVo.getBusinessId() != null ? actVo.getBusinessId() : "");
		formUrl.append("&status=").append(actVo.getStatus() != null ? actVo.getStatus(): "");
		return formUrl.toString();
	}
	
	
	/**
	 * 转换流程节点类型为中文说明
	 * @param type 英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<String, String>();
		types.put("userTask", "用户任务");
		types.put("serviceTask", "系统任务");
		types.put("startEvent", "开始节点");
		types.put("endEvent", "结束节点");
		types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
		types.put("inclusiveGateway", "并行处理任务");
		types.put("callActivity", "子流程");
		return types.get(type) == null ? type : types.get(type);
	}
	/**
	 * TaskVo 实例转换，Task，HistoricTaskInstance
	 * @param vo
	 * @param obj
	 */
	public static void setTaskVo(TaskVo vo,Object obj){
		if (obj == null)return ;
		if (obj instanceof Task){
			Task task=(Task)obj;
			vo.setTaskCreateDate(task.getCreateTime());//任务创建时间
			vo.setBeginDate(task.getCreateTime());//任务创建时间
			vo.setTaskName(task.getName());//任务名称
			vo.setTaskId(task.getId());//任务ID
			vo.setTaskDefKey(task.getTaskDefinitionKey());//任务节点KEY
			vo.setProcDefId(task.getProcessDefinitionId());//流程定义ID
			vo.setProcInsId(task.getProcessInstanceId());//流程实例ID
			vo.setExecutionId(task.getExecutionId());//运行实例ID
			vo.setAssignee(task.getAssignee());//任务办理人
			//设置流程变量
			vo.setVars(task.getProcessVariables());
			
		}else if (obj instanceof HistoricTaskInstance){
			HistoricTaskInstance task=(HistoricTaskInstance)obj;
			vo.setTaskCreateDate(task.getCreateTime());//任务创建时间
			vo.setBeginDate(task.getCreateTime());//任务创建时间
			vo.setTaskEndDate(task.getEndTime());
			vo.setTaskName(task.getName());//任务名称
			vo.setTaskId(task.getId());//任务ID
			vo.setTaskDefKey(task.getTaskDefinitionKey());//任务节点KEY
			vo.setProcDefId(task.getProcessDefinitionId());//流程定义ID
			vo.setProcInsId(task.getProcessInstanceId());//流程实例ID
			vo.setExecutionId(task.getExecutionId());//运行实例ID
			vo.setAssignee(task.getAssignee());//任务办理人
			//设置流程变量
			vo.setVars(task.getProcessVariables());
		}else if(obj instanceof VActToDoVO){
			//代办任务
			VActToDoVO task=(VActToDoVO)obj;
			vo.setTaskName(task.getName());//任务名称
			vo.setTaskId(task.getId());//任务ID
			vo.setTaskDefKey(task.getTaskDefKey());//任务节点KEY
			vo.setProcDefId(task.getProcDefId());//流程定义ID
			vo.setProcDefKey(task.getProcdefkey());//流程定义KEY
			vo.setProcInsId(task.getProcInsId());//流程实例ID
			vo.setBusinessId(task.getBusinessid());//业务编号
			vo.setStatus(Constant.TaskStatus.TODO);
		}else if(obj instanceof VActHaddoVO){
			//已办任务
			VActHaddoVO task=(VActHaddoVO)obj;
			vo.setTaskName(task.getTaskName());//任务名称
			vo.setTaskId(task.getTaskId());//任务ID
			vo.setTaskDefKey(task.getTaskDefKey());//任务节点KEY
			vo.setProcDefKey(task.getProcdefkey());//流程定义KEY
			vo.setProcInsId(task.getProcInsId());//流程实例ID
			vo.setBusinessId(task.getBusinessid());//业务编号
			vo.setStatus(Constant.TaskStatus.FINISH);
		}
	}

	public static void main(String[] args) {
	}
}
