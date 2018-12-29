package com.lhhs.workflow.bs.inf;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.ResultEasyuiVO;
import com.lhhs.workflow.dao.domain.TaskVo;
import com.lhhs.workflow.dao.domain.User;

	/**
	 * 流程定义相关Service
	 * 
	 * @author ThinkGem
	 * @version 2013-11-03
	 */

public interface ActTaskService {
	/**
	 * 启动流程
	 * 
	 * @param parm 启动流程参数
	 * @return 流程实例ID
	 */

	public ActVo startProcess(ActVo parm);

	/**
	 * 提交任务, 并保存意见
	 * 
	 * @param parm 提交任务参数
	 */
	public ActVo complete(ActVo parm);

	

	/**
	 * 获取待办列表
	 * 分页查询
	 * @param act 工作流实体对象
	 * @return List<Act>工作流列表 
	 */
	public Page todoList(TaskVo act);

	/**
	 * 获取已办任务
	 * 
	 * @param page
	 * @param procDefKey
	 *            流程定义标识
	 * @return
	 */
	
	public Page historicList(TaskVo act);


	/**
	 * 获取流转历史列表
	 * 
	 * @param procInsId
	 *            流程实例
	 * @param startAct
	 *            开始活动节点名称
	 * @param endAct
	 *            结束活动节点名称
	 */
	public List<TaskVo> histoicFlowList(String procInsId, String startAct,	String endAct);
	/**
	 * 获取任务的候选组
	 * 
	 * @param taskId
	 * @return 组列表
	 */
	public List getTaskCandidate(String taskId);


	/**
	 * 获取流程列表
	 * @param page
	 *            分页对象
	 * @param category
	 *            流程分类
	 */
	public ResultEasyuiVO processList(Page page, String category);

	
	/**
	 * 获取流程表单跳转地址（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @param procDefId 流程定义的ID
	 * @param taskDefKey 当前任务节点KEY
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey);

	/**
	 * 获取运行中的流程实例对象
	 * 
	 * @param procInsId 流程实例ID
	 * @return
	 */
	public ProcessInstance getProcIns(String procInsId);
	/**
	 * 获取结束的流程实例对象
	 * 
	 * @param procInsId 流程实例ID
	 * @return
	 */
	
	public ProcessInstanceHistoryLog getProcInsHis(String procInsId);


	/**
	 * 删除任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param deleteReason
	 *            删除原因
	 */
	public void deleteTask(String taskId, String deleteReason);

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            签收用户ID（用户登录名）
	 */
	public void claim(String taskId, String userId);

	/**
	 * 获取运行中流程变量
	 * @param 流程实例ID
	 * @return Map
	 */
	public Map getVariables(String procInId);
	
	/**
	 * 新增任务办理人
	 * @param taskId 任务ID
	 * @param userId 任务办理人
	 */
	public void addCandidateUser(String taskId, String userId);
	
	/**
	 * 根据流程定义id获取流程定义对象
	 * @param procDefId
	 * @return
	 */
	public ProcessDefinition getProcDef(String procDefId);
	
	/**
	 * 根据当前流程实例ID或者任务id获取任务办理人,优先按任务ID查询
	 * 
	 * @param procInsId 流程实例ID
	 * @param taskId 任务ID
	 * @return User对象 返回登录名：loginName，姓名： name
	 */
	public List<User> getIdentityLinks(String procInsId,String taskId);
	
	/**
	 * 删除运行的流程实例 流程启动冲销使用
	 * @param procInsId 流程实例ID
	 * @param deleteReason 删除原因，不可为空
	 */
	public void deleteProcIns(String procInsId, String deleteReason);
	/**
	 * 根据流程实例ID获取所有节点
	 */
	public List<TaskVo> findActivitiList(String procInsId) ;
	/**
	 * 获取审批意见
	 * @param procInsId
	 * @return
	 */
	public List<TaskVo> findCommentList(TaskVo taskVo) ;
	/**
	 * 获取审批意见
	 * @param procInsId 流程实例ID
	 * @param TaskDefKey 节点KEY
	 * @return 最新审批意见信息
	 */
	public TaskVo findCommentList(String procInsId,String TaskDefKey) ;

	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId);
	
	/**
	 * 查询已审批的节点列表
	 */
	
	public List<TaskVo> listTastDefKey(TaskVo parm);
	
	/**
	 * 获取任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	
	public TaskVo getTaskVo(String taskId) ;
	/**
	 * 根据流程实例ID活动当前活动节点
	 * 
	 * @param procInsId
	 *            流程ID
	 * @return
	 */
	public List<Task> getCurrentTaskList(String procInsId);
}
