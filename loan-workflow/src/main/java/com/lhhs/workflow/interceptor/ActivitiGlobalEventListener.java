package com.lhhs.workflow.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lhhs.workflow.common.utils.Constant;
import com.lhhs.workflow.common.utils.RmJsonHelper;
import com.lhhs.workflow.dao.domain.User;
import com.lhhs.workflow.sys.utils.bs.UserUtils;
/**
 * 工作流全局监听事件
 * @author dongf
 *
 */
public class ActivitiGlobalEventListener implements ActivitiEventListener {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void onEvent(ActivitiEvent event) {
		
		//获取事件类型
		ActivitiEventType eventType=event.getType();
		//如果为任务创建
		if(eventType.equals(ActivitiEventType.TASK_CREATED)){
			ActivitiEvent entityEvent =(ActivitiEntityEvent)event;
			RuntimeService runtimeService =entityEvent.getEngineServices().getRuntimeService();
			Object entity=entityEvent.getEntity();
			//流程实例ID
			String procInId=entityEvent.getProcessInstanceId();
			//流程定义ID
			String proDefId=entityEvent.getProcessDefinitionId();
			//执行实例ID
			String exId=entityEvent.getExecutionId();
			
			if(entity instanceof TaskEntity){
				TaskEntity task =(TaskEntity)entity;
				String multiInstance=(String)task.getExecution().getActivity().getProperty("multiInstance");
				String tastKey=(String)task.getExecution().getActivity().getId();
				String taskId=task.getId();
				//获取流程变量
				Map vars =(HashMap)runtimeService.getVariables(exId);
				Map vars_temp =null;
				logger.debug("打印流程变量"+exId+":"+RmJsonHelper.bean2json(vars));
				//子流程和多实例任务权限流程变量处理,子流程建立流程变量subkey不为空,并且节点名称含有countersign
				if(!procInId.equals(exId)&&vars.get(Constant.Field.SUBKEY)!=null&&tastKey.indexOf(Constant.UserTaskKey.COUNTERSIGN)>-1){
					//子流程流程变量
					Map subkeyMap=(Map)vars.get(Constant.Field.SUBKEY);
					//子流程任务ID和流程变量对照
					Map subtaskMap=(Map)vars.get(Constant.Field.SUBTASK);
					Map subtemp=(Map)vars.get(Constant.Field.SUBTEMP+task.getTaskDefinitionKey());
					if(subtaskMap==null)subtaskMap=new HashMap();
					if(subtemp==null||subtemp.isEmpty()){
						//新申请一个MAP
						subtemp=new HashMap();
						//复制MAP 不改变原流程变量
						subtemp.putAll(subkeyMap);
					}
					Set set = subtemp.entrySet();        
					Iterator temp = set.iterator();
					String key=null;
					while(temp.hasNext()){     
					     Map.Entry<String, Map> entry1=(Map.Entry<String, Map>)temp.next();
					     key=entry1.getKey();
					     if(entry1.getValue()!=null)vars_temp=entry1.getValue();
					     //设置新建任务和流程变量关系
					     subtaskMap.put(task.getId(), entry1.getValue());
					     break;
					}
					//移除
					subtemp.remove(key);
					if(subtemp!=null){
						runtimeService.setVariable(procInId, Constant.Field.SUBTEMP+task.getTaskDefinitionKey(), 
								subtemp);
					}
					if(subtaskMap!=null)runtimeService.setVariable(procInId, Constant.Field.SUBTASK, subtaskMap);
					
					if(vars_temp!=null)vars=vars_temp;
					
				}
				 //获取任务节点设置的候选组和候选人
				List list_task_user=getIdentityLinksForTask(entityEvent.getEngineServices().getTaskService(),taskId);
				
				//根据组查询权限关系表获取任务的候选人
				List<String> list_user=getTaskCandidate(list_task_user,vars);
				//把组成员设置成任务办理人
				if(list_user!=null&&list_user.size()>0){
					for(int i=0;i<list_user.size();i++){
						/**指定个人任务和组任务的办理人*/
						entityEvent.getEngineServices().getTaskService().addCandidateUser(taskId, list_user.get(i));
						//同addCandidateUser方法一样
						//entityEvent.getEngineServices().getTaskService().addUserIdentityLink(taskId, list_user.get(i).getLoginName(), IdentityLinkType.CANDIDATE);
						
					}
			
				}
			}
		}

	}
	
	/**
	 * 获取任务的候选组和候选人
	 * @param taskId
	 * @return 组列表
	 */
	
	public List getIdentityLinksForTask(TaskService taskService,String taskId) {
		List users  = new ArrayList();
		List identityLinkList = taskService.getIdentityLinksForTask(taskId);
		if (identityLinkList == null || identityLinkList.size()<= 0) return users;
		for (Iterator iterator = identityLinkList.iterator(); iterator.hasNext();) {
			IdentityLink identityLink = (IdentityLink) iterator.next();
			Map user_map=new HashMap();
			if (identityLink.getGroupId() != null&&identityLink.getGroupId()!="") {
				user_map.put("userId", identityLink.getGroupId());
				user_map.put("userType", "group");
			}else if(identityLink.getUserId()!=null&&identityLink.getUserId()!=""){
				user_map.put("userId", identityLink.getUserId());
				user_map.put("userType", "user");
			}
			users.add(user_map);
		}
		return users;
	}
	
	/**
	 * 根据组查询权限关系表获取任务的候选人
	 * @param IdentityLinks候选组和候选人
	 * @param Vars 流程变量
	 * @return 组列表
	 */
	
	public List getTaskCandidate(List IdentityLinks,Map vars) {
		if(IdentityLinks==null||IdentityLinks.size()<=0)return null;
		List<String> user_list=new ArrayList();
		List temp_list=new ArrayList();
		Map temp_user=new HashMap();
		for(int i=0;i<IdentityLinks.size();i++){
			Map map=(Map)IdentityLinks.get(i);
			if(map!=null&&map.get("userId")!=null){
				String userId=(String)map.get("userId");
				String userType=(String)map.get("userType");
				if("group".equals(userType)){
					User user=new User();
					//角色编号
					user.setRoleId(userId);
					//调权限接口
					List<User> list_user=UserUtils.findUser(user,vars);
					if(list_user!=null&&list_user.size()>0){
						temp_list.addAll(list_user);
					}
					
				}else if("user".equals(userType)){
					//记录任务当前人和签收人
					temp_user.put(userId, userId);
				}
			}
			
		}
		//移除任务当前人和签收人，防止代办任务重复出现
		if(temp_list!=null&&temp_list.size()>0){
			for(int i=0;i<temp_list.size();i++){
				String userName=((User)temp_list.get(i)).getUserName();
				if(temp_user.get(userName)==null||"".equals(temp_user.get(userName)))user_list.add(userName);
			}
		}
		return user_list;
	}
	

}
