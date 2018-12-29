package com.lhhs.workflow.bs.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lhhs.bump.api.UserApi;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.bs.inf.ActCommentService;
import com.lhhs.workflow.bs.inf.ActHaddoService;
import com.lhhs.workflow.bs.inf.ActServiceService;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.bs.inf.ActToDoService;
import com.lhhs.workflow.bs.inf.ProcessInstanceHighlightsService;
import com.lhhs.workflow.common.cmd.JumpTaskCmd;
import com.lhhs.workflow.common.enumeration.Resolution;
import com.lhhs.workflow.common.utils.ActUtils;
import com.lhhs.workflow.common.utils.Constant;
import com.lhhs.workflow.common.utils.ProcessDefUtils;
import com.lhhs.workflow.common.utils.RmJsonHelper;
import com.lhhs.workflow.common.utils.TimeUtils;
import com.lhhs.workflow.dao.domain.Act;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.ResultEasyuiVO;
import com.lhhs.workflow.dao.domain.TaskVo;
import com.lhhs.workflow.dao.domain.User;
import com.lhhs.workflow.sys.utils.bs.UserUtils;

@Service("ActTaskService")
public class ActTaskServiceImpl implements ActTaskService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcessEngineFactoryBean processEngineFactory;

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired(required = false)
	private ActCommentService actCommentService;
	@Autowired(required = false)
	private ActHaddoService actHaddoService;
	@Autowired(required = false)
	private ActToDoService actToDoService;
	@Autowired(required = false)
	private ActServiceService actServiceService;
	@Autowired(required = false)
	private ProcessInstanceHighlightsService processInstanceHighlightsService;
	@Reference
	private UserApi userApi;
	/**
	 * 启动流程
	 * 
	 * @param parm 启动流程参数
	 * @return 流程实例ID
	 */
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, Exception.class})
	@Transactional(readOnly = false)
	public ActVo startProcess(ActVo parm){
		if(parm==null) {
			throw new RuntimeException("ERROR:启动参数不能为空!");
		}
		Map<String, Object> vars=parm.getVars();
		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
			parm.setVars(vars);
		}
		String procDefKey=parm.getProcDefKey();
		String businessId=parm.getBusinessId();
		String title=parm.getTitle();
		String businessTable=parm.getBusinessTable();
		String remark=parm.getRemark();
		String sys="";
		if(!StringUtils.isEmpty(parm.getSys())){
			sys=parm.getSys().toUpperCase();
		}
		String userId=parm.getUserId();
		String userName=parm.getUserName();
		String channel=parm.getChannel();
		if (StringUtils.isEmpty(procDefKey)){
			throw new RuntimeException("ERROR:procDefKey流程定义KEY不能为空!");
		}
		if (StringUtils.isEmpty(userId)){
			throw new RuntimeException("ERROR:userId流程启动用户不能为空!");
		}
		// 设置业务流水号 
		if (StringUtils.isEmpty(businessId)) {
			throw new RuntimeException("ERROR:businessId业务编号不能为空!");
		}
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		
		// 设置流程的发起人驳回修改时使用
		if (StringUtils.isNotBlank(userId)) {
			vars.put(Constant.Field.APPLY, userId);
		}
		// 设置流程的发起人驳回修改时使用
		if (StringUtils.isNotBlank(userName)) {
			vars.put(Constant.Field.APPLYNAME, userName);
		}
		// 设置流程标题
		if (StringUtils.isNotBlank(title)) {
			vars.put(Constant.Field.TITLE, title);
		}
		// 设置系统标识
		if (StringUtils.isNotBlank(sys)) {
			vars.put(Constant.Field.SYS, sys);
		}
		// 设置渠道标识
		if (StringUtils.isNotBlank(channel)) {
			vars.put(Constant.Field.CHANNEL, channel);
		}
		// 设置启动备注
		if (StringUtils.isNotBlank(remark)) {
			vars.put(Constant.Field.REMARK, remark);
		}
		
		// 设置启动集团
		if (!StringUtils.isEmpty(parm.getUnionId())) {
			vars.put(Constant.Field.UNIONID, parm.getUnionId());
		}
		// 设置启动公司
		if (!StringUtils.isEmpty(parm.getCompanyId())) {
			vars.put(Constant.Field.COMPANYID, parm.getCompanyId());
		}
		// 设置启动备注
		if (!StringUtils.isEmpty(parm.getDepId())) {
			vars.put(Constant.Field.DEPID, parm.getDepId());
		}
		vars.put(Constant.Field.BUSINESSID, businessId);
        // 启动流程
		String procInsId=null;
		if (!StringUtils.isEmpty(parm.getDepId())) {
			com.lhhs.bump.domain.User userEntity=userApi.queryTeamDirector(parm.getDepId());
			if(null!=userEntity){
				vars.put("wf_user", userEntity.getUsername());
			}else{
				vars.put("wf_user", "");
			}
		}
		
		// 如果业务表不为空
		if (StringUtils.isNotBlank(businessTable)) {
			// 启动流程
			ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable + ":" + businessId, vars);
			procInsId=procIns.getId();
		}else{
			// 启动流程
			ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessId, vars);
			procInsId=procIns.getId();
		}
		parm.setProcInsId(procInsId);
		//添加审批意见
		TaskVo task=new TaskVo();
		task.setChannel(channel);
		task.setTaskName("流程启动");
		task.setTaskId(procInsId);
		task.setTaskDefKey("start");
		task.setProcDefKey(procDefKey);
		task.setProcInsId(procInsId);
		task.setAssignee(userId);
		task.setTaskCreateDate(new Date());
		task.setIp(parm.getIp());
		task.setVars(parm.getVars());
		task.setCategory(parm.getSys());
		//设置集团、公司、事业部（组）
		task.setUnionId(parm.getUnionId());
		task.setCompanyId(parm.getCompanyId());
		task.setDepId(parm.getDepId());
		task.setField5(task.getRemark());
		//添加审批意见
		task=addComment(task);
		parm.setNextAssignee(task.getNextAssignee());
		parm.setNextAssigneeName(task.getNextAssigneeName());
		parm.setNextTaskDefKey(task.getNextTaskDefKey());
		parm.setNextTaskName(task.getNextTaskName());
		
		return parm;
	}

	/**
	 * 提交任务, 并保存意见
	 * 
	 * @param parm 提交任务参数
	 * @return 处理成功时返回空字符""，失败时返回对应的错误提示
	 */
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, Exception.class})
	@Transactional(readOnly = false)
	public ActVo complete(ActVo parm){
		
        String taskId=parm.getTaskId();
        String userId=parm.getUserId();
		if (StringUtils.isEmpty(taskId)) {
			throw new RuntimeException("ERROR:taskId任务编号不能为空!");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new RuntimeException("ERROR:userId任务办理用户不能为空!");
		}
		String comment=parm.getComment();
		//添加评论
		TaskVo task=new TaskVo();
		Map<String, Object> vars=parm.getVars();
		// 设置流程变量
		if (vars == null) {
			vars = Maps.newHashMap();
			parm.setVars(vars);
		}
		TaskEntity curTask = getTask(taskId);
		if(curTask==null){
			throw new RuntimeException("ERROR:taskId:"+taskId+"，根据任务编号没查询到对应的任务，任务可能已处理!");
		}
		
		//优先取parm属性中的，如果为空取流程变量中的
		String pass=parm.getPass();
		if(StringUtils.isEmpty(pass)){
			pass=(String) vars.get("pass");
		}else{
			vars.put("pass", pass);
		}
		
		//String channel=parm.getChannel();
		//设置渠道流程变量
		//vars.put(Constant.Field.CHANNEL, channel);
		
		String procInsId = curTask.getProcessInstanceId();
		String def_key = curTask.getTaskDefinitionKey();
		
		
		// 如果任务没签收，先签收任务
		if (curTask != null &&StringUtils.isEmpty(curTask.getAssignee())){
			taskService.claim(taskId, userId);
		}
		/**
		// 添加意见 不用自带的审批意见，用自定义
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		**/
		// 决议为：通过、驳回
		if (Resolution.YES.getId().equals(pass) || Resolution.NO.getId().equals(pass)) {
			// 会签任务判断 只要含有countersign都作为会签
			if (def_key.indexOf(Constant.UserTaskKey.COUNTERSIGN)>-1) {
				//设置同意计数器、用户集合数后缀
				String suffix="";
				if(!Constant.UserTaskKey.COUNTERSIGN.equals(def_key)){
					suffix="_"+def_key;
				}
				// 获取流程变量
				Map vars_proInId = (HashMap) runtimeService.getVariables(curTask.getProcessInstanceId());
				String yescounter = (String) vars_proInId.get(Constant.Field.YESCOUNTER+suffix);
				// 多实例任务集合
				List assigneeList = (List) vars_proInId.get(Constant.Field.ASSIGNEELIST);
				int assigneeCounter = assigneeList.size();
				if (yescounter == null || "".equals(yescounter)) {
					yescounter = "0";
				}
				int temp_pass = 0;
				//如果决议类型为同意
				if (Resolution.YES.getId().equals(pass)) {
					temp_pass = 1;
				}else{
					temp_pass = 0;
				}
				// 同意计数
				int tempCounter = temp_pass + Integer.parseInt(yescounter);

				// 设置流程变量
				runtimeService.setVariable(curTask.getProcessInstanceId(), Constant.Field.ASSIGNEECOUNTER+suffix, Integer.toString(assigneeCounter));
				runtimeService.setVariable(curTask.getProcessInstanceId(), Constant.Field.YESCOUNTER+suffix, Integer.toString(tempCounter));
			}
			// 提交任务
			taskService.complete(taskId, vars);

		} else if (Resolution.REJECT.getId().equals(pass)&&def_key.indexOf(Constant.UserTaskKey.MODIFY)>-1) {
			// 决议为：驳回修改
			taskBackModify(curTask, vars);
		} else if (Resolution.CANCEL.getId().equals(pass)) {
			// 决议为：结束当前任务
			taskForwardFinish(curTask, vars);
			
		}else if (!StringUtils.isEmpty(pass)&&Resolution.OVER.getId().charAt(0)==pass.charAt(0)) {
			// 决议为：结束所有任务,首位为9的都为结束所有任务
			allTaskForwardFinish(curTask, vars);
		}else if (Resolution.BACK.getId().equals(pass)) {
			// 决议为：返回前一处理节点
			taskBack(curTask, vars);
		}else{
			// 提交任务
			taskService.complete(taskId, vars);
		}
		
		//检查下一审批人如果没有回滚
		//checkNextAssignee(procInsId);
		//提交事务************
        //transactionManager.commit(transactionStatus);
		//转换TaskEntity对象为TaskVo
		ActUtils.setTaskVo(task,curTask);
		//设置决议类型
		task.setAssignee(userId);
		task.setPass(pass);
		//task.setChannel(channel);
		task.setComment(comment);
		task.setIp(parm.getIp());
		task.setVars(parm.getVars());
		task.setCategory(parm.getSys());
		//设置集团、公司、事业部（组）
		task.setUnionId(parm.getUnionId());
		task.setCompanyId(parm.getCompanyId());
		task.setDepId(parm.getDepId());
		//添加审批意见
		task=addComment(task);
		parm.setNextAssignee(task.getNextAssignee());
		parm.setNextAssigneeName(task.getNextAssigneeName());
		parm.setNextTaskDefKey(task.getNextTaskDefKey());
		parm.setNextTaskName(task.getNextTaskName());
		return parm;
	}
	
	
	
	/**
	 * 获取待办列表
	 * 分页查询
	 * @param act 工作流实体对象
	 * @return List<Act>工作流列表 
	 */
	public Page todoList(TaskVo act) {
		//代办用户为空时直接返回
		String userId = act.getUserId();
		if(StringUtils.isBlank(userId))return null;
		Page page=new Page();
		if(act.getPage()!=null)page=act.getPage();
		List<TaskVo> result_list = new ArrayList<TaskVo>();
		int startRow=act.getPage().getPageStartIndex();
		int endRow=act.getPage().getPageEndIndex();
		int pageSize=act.getPage().getPageSize();
		// =============== 已经签收的任务 ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
												.includeProcessVariables().orderByTaskCreateTime().desc();
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (StringUtils.isNotBlank(act.getProcName())) {
			//设置查询流程定义名称
			todoTaskQuery.processDefinitionNameLike(act.getProcName());
		}
		if (act.getBeginDate() != null) {
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		//设置流程分类
		if (StringUtils.isNotBlank(act.getCategory())) {
			List<String> processCategoryInList=new ArrayList();
			processCategoryInList.add(act.getCategory());
			todoTaskQuery.processCategoryIn(processCategoryInList);
		}
		//任务编号查询
		if(!StringUtils.isEmpty(act.getTaskId())){
			todoTaskQuery.taskId(act.getTaskId());
		}
		
		//已签收任务总数
		int todoTask_count=(int)todoTaskQuery.count();
		

		// =============== 等待签收的任务 ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId).includeProcessVariables().active().orderByTaskCreateTime().desc();

		// 设置查询条件

		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			//设置流程定义KEY
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (StringUtils.isNotBlank(act.getProcName())) {
			//设置查询流程定义名称
			toClaimQuery.processDefinitionNameLike(act.getProcName());
		}
		if (act.getBeginDate() != null) {
			//设置查询任务创建开始时间
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			//设置查询任务创建结束时间
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}
		//设置流程分类
		if (StringUtils.isNotBlank(act.getCategory())) {
			List<String> processCategoryInList=new ArrayList();
			processCategoryInList.add(act.getCategory());
			todoTaskQuery.processCategoryIn(processCategoryInList);
		}
		int toClaim_count=(int)toClaimQuery.count();
		//查询起始笔数、结束笔数小于已签收任务总数，直接查询待办任务列表
		if(startRow<=todoTask_count&&endRow<=todoTask_count){
			// 待办查询列表
			List<Task> todoList = todoTaskQuery.listPage(startRow,pageSize);
			result_list.addAll(setListTaskInfor(todoList,Constant.TaskStatus.TODO));
			//查询起始笔数小于已签收任务总数，结束笔数大于已签收任务总数，查询待办任务列表(startRow,todoTask_count)+待签收任务列表(0,endRow-todoTask_count-)
		}else if(startRow<=todoTask_count&&endRow>todoTask_count){
			// 待办查询列表
			List<Task> todoList = todoTaskQuery.listPage(startRow,pageSize);
			result_list.addAll(setListTaskInfor(todoList,Constant.TaskStatus.TODO));
			// 待签收查询列表
			List<Task> toClaimList = toClaimQuery.listPage(0, endRow-todoTask_count);
			result_list.addAll(setListTaskInfor(toClaimList,Constant.TaskStatus.TODO));
			//起始笔数大于
		}else if(startRow>todoTask_count){
			// 待签收查询列表
			List<Task> toClaimList = toClaimQuery.listPage(startRow-todoTask_count, pageSize);
			result_list.addAll(setListTaskInfor(toClaimList,Constant.TaskStatus.TODO));
		}
		
		page.setTotalCount(todoTask_count+toClaim_count);
		
		page.setResultList(result_list);
		
		return page;
	}
	/**
	 * 根据把任务列表转换成自定义对象列表信息
	 * @param list
	 * @param type 任务类型
	 * @return
	 */
	private List setListTaskInfor(List<Task> list,String TaskStatus){
		List result_list = new ArrayList<TaskVo>();
		for (Task task : list) {
			TaskVo vo=new TaskVo();
			//转换histTask为TaskVo
			ActUtils.setTaskVo(vo, task);
			vo.setStatus(TaskStatus);
			
			User user = UserUtils.getByLoginName(task.getAssignee());
			if (user != null) {
				vo.setAssigneeName(user.getName());
			}
			
			if(vo.getVars()!=null&&vo.getVars().get(Constant.Field.BUSINESSID)!=null){
				//设置业务编号
				vo.setBusinessId((String)vo.getVars().get(Constant.Field.BUSINESSID));
			}else{
				//获取流程实例
				ProcessInstance proc=getProcIns(task.getProcessInstanceId());
				//设置业务编号
				if(proc!=null)vo.setBusinessId(proc.getBusinessKey());
			}
			
			//获取流程定义
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			vo.setProcName(pd.getName());
			/**获取流程版本*/
			if(pd != null) {
				vo.setVersion( "V:"+pd.getVersion());
			}else{
				vo.setVersion("V:");
			}
			//流程分类 /系统标识
			vo.setCategory(pd.getCategory());
			String formKey=getFormKey(vo.getProcDefId(), vo.getTaskDefKey());
			String formUrl=ActUtils.getFormUrl(formKey, vo,vo.getCategory());
			//设置流程跳转URL
			vo.setFormKey(formKey);
			//设置流程跳转URL
			vo.setFormUrl(formUrl);
			result_list.add(vo);
		}
		return result_list;
	}

	/**
	 * 获取已办任务
	 * 
	 * @param act 工作流实体对象
	 *            
	 * @return
	 */
	@Override
	public Page historicList(TaskVo act) {
		Page page=new Page();
		if(act.getPage()!=null)page=act.getPage();
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(act.getUserId()).finished().includeProcessVariables()
				.orderByHistoricTaskInstanceEndTime().desc();
		int startRow=act.getPage().getPageStartIndex();
		int endRow=act.getPage().getPageEndIndex();
		int pageSize=act.getPage().getPageSize();
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())) {
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (StringUtils.isNotBlank(act.getProcName())) {
			//设置查询流程定义名称
			histTaskQuery.processDefinitionNameLike(act.getProcName());
		}
		if (act.getBeginDate() != null) {
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null) {
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}
		//设置流程分类
		if (StringUtils.isNotBlank(act.getCategory())) {
			List<String> processCategoryInList=new ArrayList();
			processCategoryInList.add(act.getCategory());
			histTaskQuery.processCategoryIn(processCategoryInList);
		}
		// 查询总数
		page.setTotalCount((int)histTaskQuery.count());
		// 查询列表 分页查询起始行，分页查询结束行
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(act.getPage().getPageStartIndex(),act.getPage().getPageSize());
		// 处理查询结果
		List<Object> actList = Lists.newArrayList();
		for (HistoricTaskInstance histTask : histList) {
			TaskVo vo = new TaskVo();
			//转换histTask为TaskVo
			ActUtils.setTaskVo(vo, histTask);
			vo.setStatus(Constant.TaskStatus.FINISH);
			//设置办理人ID和姓名
			User user = UserUtils.getByLoginName(histTask.getAssignee());
			if (user != null) {
				vo.setAssigneeName(user.getName());
			}
			//设置任务历时
			if (histTask != null && histTask.getDurationInMillis() != null) {
				vo.setDurationTime(TimeUtils.toTimeString(histTask.getDurationInMillis()));
			}
			if(vo.getVars()!=null&&vo.getVars().get(Constant.Field.BUSINESSID)!=null){
				//设置业务编号
				vo.setBusinessId((String)vo.getVars().get(Constant.Field.BUSINESSID));
			}else{
				//获取流程实例
				ProcessInstance proc=getProcIns(histTask.getProcessInstanceId());
				
				//设置业务编号
				if(proc!=null){
					vo.setBusinessId(proc.getBusinessKey());
				}else{
					//查询历史流程
					ProcessInstanceHistoryLog procHis=getProcInsHis(histTask.getProcessInstanceId());
					vo.setBusinessId(procHis.getBusinessKey());
				}
					
			}
			
			//获取流程定义
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(histTask.getProcessDefinitionId()).singleResult();
			
			vo.setProcName(pd.getName());
			/**获取流程版本*/
			if(pd != null) {
				vo.setVersion( "V:"+pd.getVersion());
			}else{
				vo.setVersion("V:");
			}
			//流程分类 /系统标识
			vo.setCategory(pd.getCategory());
			String formKey=getFormKey(vo.getProcDefId(), vo.getTaskDefKey());
			String formUrl=ActUtils.getFormUrl(formKey, vo,vo.getCategory());
			//设置流程跳转URL
			vo.setFormKey(formKey);
			//设置流程跳转URL
			vo.setFormUrl(formUrl);
			actList.add(vo);
			
		}
		//设置查询返回结果
		page.setResultList(actList);
		return page;
	}
	
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
	@Override
	public List<TaskVo> histoicFlowList(String procInsId, String startAct, String endAct) {
		List<TaskVo> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId).orderByHistoricActivityInstanceStartTime().asc()
				.orderByHistoricActivityInstanceEndTime().asc().list();

		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();

		for (int i = 0; i < list.size(); i++) {

			HistoricActivityInstance histIns = list.get(i);
			
			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())) {
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start) {
				continue;
			}

			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee()) || "startEvent".equals(histIns.getActivityType()) || "endEvent".equals(histIns.getActivityType())) {

				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null) {
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				TaskVo vo = new TaskVo();
				//任务Id，设置开始时间、结束时间
				vo.setTaskId(histIns.getTaskId());
				vo.setEndDate(histIns.getEndTime());
				vo.setBeginDate(histIns.getStartTime());
				//设置执行环节名称
				vo.setTaskName(histIns.getActivityName());
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())) {
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
					// List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0) {
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())) {
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null) {
								vo.setAssignee(user.getUserName());
								vo.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())) {
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null) {
						vo.setAssignee(user.getUserName());
						vo.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())) {
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size() > 0) {
						vo.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(vo);
			}

			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())) {
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j = i + 1; j < list.size(); j++) {
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())) {
						bl = true;
					}
				}
				if (!bl) {
					break;
				}
			}
		}
		return actList;
	}
	/**
	 * 获取任务的候选组
	 * 
	 * @param taskId
	 * @return 组列表
	 */
	@Override
	public List getTaskCandidate(String taskId) {
		List groupIds = new ArrayList();
		List identityLinkList = taskService.getIdentityLinksForTask(taskId);
		if (identityLinkList == null || identityLinkList.size() <= 0)
			return groupIds;
		for (Iterator iterator = identityLinkList.iterator(); iterator.hasNext();) {
			IdentityLink identityLink = (IdentityLink) iterator.next();
			if (identityLink.getGroupId() != null) {
				groupIds.add(identityLink.getGroupId());
			}
		}
		return groupIds;
	}

	/**
	 * 获取流程定义列表
	 * @param page
	 *            分页对象
	 * @param category
	 *            流程分类
	 */
	@Override
	public ResultEasyuiVO processList(Page page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		ResultEasyuiVO result=new ResultEasyuiVO();
		List actList = Lists.newArrayList();
		//查询流程定义列表
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion().active().orderByDeploymentId().desc();

		if (StringUtils.isNotEmpty(category)) {
			processDefinitionQuery.processDefinitionCategory(category);
		}
		result.setTotal((int)processDefinitionQuery.count());

		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getStartNum(), page.getEndNum());
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			Act act=new Act();
			//设置流程定义对象
			act.setProcDef(processDefinition);
			//设置流程部署对象
			act.setDeployment(deployment);
			actList.add(act);
		}
		result.setRows(actList);
		return result;
	}
	/**
	 * 根据流程定义id获取流程定义对象
	 * @param procDefId
	 * @return
	 */
	public ProcessDefinition getProcDef(String procDefId){
		//获取流程定义
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
	}
	/**
	 * 获取流程表单跳转地址（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @param procDefId 流程定义的ID
	 * @param taskDefKey 当前任务节点KEY
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey) {
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)) {
			if (StringUtils.isNotBlank(taskDefKey)) {
				try {
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				} catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)) {
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)) {
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}

	/**
	 * 获取运行中的流程实例对象
	 * 
	 * @param procInsId 流程实例ID
	 * @return
	 */
	@Override
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	/**
	 * 获取结束的流程实例对象
	 * 
	 * @param procInsId 流程实例ID
	 * @return
	 */
	
	public ProcessInstanceHistoryLog getProcInsHis(String procInsId) {
		return historyService.createProcessInstanceHistoryLogQuery(procInsId).singleResult();
	}
	/**
	 * 获取任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	
	public TaskVo getTaskVo(String taskId) {
		if(StringUtils.isEmpty(taskId))return null;
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskVo vo =new TaskVo();
		//转换histTask为TaskVo
		ActUtils.setTaskVo(vo, task);
		User user = UserUtils.getByLoginName(task.getAssignee());
		if (user != null) {
			vo.setAssigneeName(user.getName());
		}
		return vo;
		
	}
	/**
	 * 获取任务
	 * 
	 * @param taskId
	 *            任务ID
	 */
	
	private TaskEntity getTask(String taskId) {
		return (TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param deleteReason
	 *            删除原因
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteTask(String taskId, String deleteReason) {
		taskService.deleteTask(taskId, deleteReason);
	}

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            签收用户ID（用户登录名）
	 */
	@Override
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}
	/**
	 * 获取运行中流程变量
	 * @param 流程实例ID
	 * @return Map
	 */
	@Override
	public Map getVariables(String procInId){
		return (HashMap) runtimeService.getVariables(procInId);
	}
	
	/**
	 * 新增任务办理人
	 * @param taskId 任务ID
	 * @param userId 任务办理人
	 */
	@Override
	@Transactional(readOnly = false)
	public void addCandidateUser(String taskId, String userId){
		/**指定个人任务和组任务的办理人*/
		taskService.addCandidateUser(taskId, userId);
		//taskService.delegateTask(taskId, userId);
	}

	/**
	 * 返回上一步
	 */

	private void taskBack(Task curTask, Map<String, Object> vars) {
		String procInsId = curTask.getProcessInstanceId();
		String userTaskKey="";
		TaskVo parm =new TaskVo();
		parm.setProcInsId(procInsId);
		//排除自身节点
		parm.setNoTaskDefKeyList(curTask.getTaskDefinitionKey());
		List<TaskVo> actList =actCommentService.listTastDefKey(parm);
		if(actList==null ||actList.size()<2){
			return ;
		}
		userTaskKey=actList.get(0).getTaskDefKey();
		//jumpTask((TaskEntity)curTask, userTaskKey, vars);
		
		try {
			turnTransition(curTask.getId(), userTaskKey, vars);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}
		
	}


	/**
	 * 任务驳回到修改节点
	 */

	private void taskBackModify(Task curTask, Map<String, Object> vars) {

		String procInsId = curTask.getProcessInstanceId();
		
		TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active();
		List<Task> list_task1 = findTaskListByKey(procInsId, curTask.getTaskDefinitionKey());
		List<Task> list_task = taskQuery.list();
		// 同时驳回所有活动节点
		if (list_task != null) {
			for (int i = 0; i < list_task.size(); i++) {
				TaskEntity task = (TaskEntity) list_task.get(i);
				try {
					if (curTask.getId().equals(task.getId())) {
						turnTransition(task.getId(), Constant.UserTaskKey.MODIFY, vars);
					} else {
						// 完成任务
						taskService.complete(task.getId(), vars);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/**
				 * if(!Constant.UserTaskKey.MODIFY.equals(task.getProcessDefinitionId())){ jumpTask((TaskEntity)list_task.get(i), Constant.UserTaskKey.MODIFY, vars); }
				 */

			}
		}

	}

	/**
	 * ***************************************************************************************************************************************************<br>
	 * ************************************************以下为流程转向操作核心逻辑******************************************************************************<br>
	 * ***************************************************************************************************************************************************<br>
	 */

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}

	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);

		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	/**
	 * ***************************************************************************************************************************************************<br>
	 * ************************************************以上为流程转向操作核心逻辑******************************************************************************<br>
	 * ***************************************************************************************************************************************************<br>
	 */

	/**
	 * 任务前至结束节点
	 */
	private void taskForwardFinish(TaskEntity curTask, Map<String, Object> vars) {
		jumpTask(curTask, Constant.UserTaskKey.END, vars);
	}
	
	/**
	 * 当前所有活动节点前至结束节点
	 */
	private void allTaskForwardFinish(TaskEntity curTask, Map<String, Object> vars) {
		String procInsId = curTask.getProcessInstanceId();
		
		TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active();
		List<Task> list_task = taskQuery.list();
		// 同时结束所有活动节点
		if (list_task != null) {
			for (int i = 0; i < list_task.size(); i++) {
				TaskEntity task = (TaskEntity) list_task.get(i);
				try {
					if (curTask.getId().equals(task.getId())) {
						jumpTask(task, Constant.UserTaskKey.END, vars);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}



	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetTaskDefinitionKey
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	
	private void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), targetTaskDefinitionKey);
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetActivity
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */

	private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
	}

	private TaskEntity getTaskEntity(String taskId) {
		return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {
		List<ActivityImpl> rtnList = null;
		rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>(), new ArrayList<ActivityImpl>());

		return reverList(rtnList);
	}

	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId, ActivityImpl currActivity, List<ActivityImpl> rtnList, List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归
				return rtnList;
			} else if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
				currActivity = transitionImpl.getSource();
				exclusiveGateways.add(currActivity);
			}
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		currActivity = filterNewestActivity(processInstance, tempList);
		if (currActivity != null) {
			// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity);
			if (id == null) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
				rtnList.add(currActivity);
			} else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
				currActivity = findActivitiImpl(taskId, id);
			}

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}
		return rtnList;
	}

	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}

	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
	 * 
	 * @param activityImpl
	 *            当前节点
	 * @return
	 */
	private String findParallelGatewayId(ActivityImpl activityImpl) {
		List<PvmTransition> incomingTransitions = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					return gatewayId.substring(0, gatewayId.lastIndexOf("_")) + "_start";
				}
			}
		}
		return null;
	}

	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance, List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 查询指定任务节点的最新记录
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().activityType("userTask").processInstanceId(processInstance.getId())
				.activityId(activityId).finished().orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private TaskEntity findTaskById(String taskId){
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		
		return task;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	private List<Task> findTaskListByKey(String processInstanceId, String key) {
		return taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();
	}

	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(findTaskById(taskId).getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new Exception("流程实例未找到!");
		}
		return processInstance;
	}

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)

		((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (activityId == null) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);
		
		return activityImpl;
	}
	/**
	 * 根据当前流程实例ID或者任务id获取任务办理人,优先按任务ID查询
	 * 
	 * @param procInsId 流程实例ID
	 * @param taskId 任务ID 
	 * @return User对象 返回登录名：loginName，姓名： name
	 */
	@Override
	public List<User> getIdentityLinks(String procInsId, String taskId) {
		logger.debug("流程实例procInsId："+procInsId);
		logger.debug("任务taskId："+taskId);
		List<User> listUser  = new ArrayList();
		Map user_map=new HashMap();
		if(taskId!=null&&!"".equals(taskId)){
			List linkList = taskService.getIdentityLinksForTask(taskId);
			//查询任务信息
			TaskEntity task=findTaskById(taskId);
			//转换用户信息
			identityLinkToUser(linkList,listUser,user_map,task);
		}else if(procInsId!=null&&!"".equals(procInsId)){
			//查询当前所有活动节点
			TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active();
			List<Task> list_task = taskQuery.list();
			// 所有活动节点
			if(list_task!=null){
				for (int i = 0; i < list_task.size(); i++) {
					TaskEntity task = (TaskEntity) list_task.get(i);
					
					List<IdentityLink> linkList = taskService.getIdentityLinksForTask(task.getId());
					//转换用户信息
					identityLinkToUser(linkList,listUser,user_map,task);
				}
			}
			
		}
		logger.debug("查询当前待办人查询结果："+RmJsonHelper.bean2json(listUser));
		return listUser;
	}
	/**
	 * 任务候选人转换成用户信息
	 * @param identityLinkList
	 * @param listUser
	 * @param user_map
	 * @param task
	 */
	private void identityLinkToUser(List identityLinkList,List<User> listUser,Map<String,User> user_map,TaskEntity task){
		if (identityLinkList == null || identityLinkList.size()<= 0) return ;
		for (Iterator iterator = identityLinkList.iterator(); iterator.hasNext();) {
			IdentityLink identityLink = (IdentityLink) iterator.next();
			
			//只返回当前参与者,排除组
			if(identityLink.getUserId()!=null&&identityLink.getUserId()!=""){
				//查询办理人ID和姓名
				User user = UserUtils.getByLoginName(identityLink.getUserId());
				if (user == null) {
					user=new User();
					user.setUserId(identityLink.getUserId());
					user.setUserName(identityLink.getUserId());
				}
				//设置任务信息
				if(task!=null&&task.getId()!=null){
					user.setTaskId(task.getId());
					user.setTaskDefKey(task.getTaskDefinitionKey());
					user.setTaskName(task.getName());
					user.setProcInsId(task.getProcessInstanceId());
					if (StringUtils.isNotBlank(task.getProcessDefinitionId())) {
						String procDefKey = StringUtils.split(task.getProcessDefinitionId(), ":")[0];
						user.setProcDefKey(procDefKey);
					}
					
				}
				//设置办理人ID和姓名
				if(user_map.get(user.getUserId())==null){
					listUser.add(user);
					user_map.put(user.getUserId(), user);
				}
				
			}
			
		}
	}
	/**
	 * 删除运行的流程实例 流程启动冲销使用
	 * @param procInsId 流程实例ID
	 * @param deleteReason 删除原因，不可为空
	 */
	@Transactional(readOnly = false)
	public void deleteProcIns(String procInsId, String deleteReason) {
		if(StringUtils.isBlank(procInsId)||StringUtils.isBlank(deleteReason))return;
		runtimeService.deleteProcessInstance(procInsId, deleteReason);
	}
	/**
	 * 根据流程实例ID获取所有节点
	 */
	public List<TaskVo> findActivitiList(String procInsId){
		if(StringUtils.isEmpty(procInsId))return null;
		//获取流程实例
		ProcessInstance proc=getProcIns(procInsId);
		if(proc==null)return null;
		// 取得流程定义
		ProcessDefinitionEntity def=(ProcessDefinitionEntity)repositoryService.getProcessDefinition(proc.getProcessDefinitionId());
		if (def == null) return null;
		
		List<TaskVo> list=new ArrayList();
		List<ActivityImpl>  list_act=def.getActivities();
		
		for (ActivityImpl activity : list_act) {
			String type=(String)activity.getProperty("type");
			if("userTask".equals(type)){
				TaskVo task=new TaskVo();
				TaskDefinition taskDefinition=(TaskDefinition)activity.getProperty("taskDefinition");
				Map map1=taskDefinition.getCustomUserIdentityLinkExpressions();
				Map map2=taskDefinition.getCustomGroupIdentityLinkExpressions();
				Expression expression=taskDefinition.getAssigneeExpression();
				Set map3=taskDefinition.getCandidateGroupIdExpressions();
				Set map4=taskDefinition.getCandidateUserIdExpressions();
			}
			
			
		}

		return null;
	}
	/**
	 * 添加审批意见
	 */
	
	public TaskVo addComment(TaskVo task) {
		//流程状态 待处理
		String status="todo";
		//设置事业部、产品线、平台、销售办公室
		Map var =task.getVars();
		if(var!=null){
			task.setBusinessId((String)var.get(Constant.Field.BUSINESSID));
			task.setTitle((String)var.get(Constant.Field.TITLE));
		}
		//获取下一审批人
		//List<User> list_user=getIdentityLinks(task.getProcInsId(),null);
		List<User> list_user  = new ArrayList();
		Map user_map=new HashMap();
		//查询当前所有活动节点
		TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(task.getProcInsId()).active();
		List<Task> list_task = taskQuery.list();
		// 所有活动节点
		if(list_task!=null&&list_task.size()>0){
			for (int i = 0; i < list_task.size(); i++) {
				TaskEntity taskTemp = (TaskEntity) list_task.get(i);
				List<IdentityLink> linkList = taskService.getIdentityLinksForTask(taskTemp.getId());
//				//初评节点 只展示当前客户经理归属的部门团队总监
//				if("初评".equals(taskTemp.getName())){
//					linkList=checkDeptUser(linkList,task);
//				}
				//转换用户信息
				identityLinkToUser(linkList,list_user,user_map,taskTemp);
			}
		}else{
			//流程已完成
			status="finish";
		}
		//下一审批人
		String nextAssignee=null;
		String nextAssigneeName=null;
		String nextTaskName=null; // 下一任务名称，多个,分割
		String nextTaskDefKey=null; // 下一任务Key，多个,分割
		//节点MAP
		Map<String ,String> nextTaskDefMap=Maps.newHashMap();
		if(null!=list_user&&list_user.size()>0){
			for(int i=0;i<list_user.size();i++){
				if(StringUtils.isEmpty(list_user.get(i).getUserId())||StringUtils.isEmpty(list_user.get(i).getName())){
					continue;
				}
				if(nextAssignee==null){
					nextAssignee=list_user.get(i).getUserId();
					nextAssigneeName=list_user.get(i).getName();
				}else{
					nextAssignee=nextAssignee+","+list_user.get(i).getUserId();
					nextAssigneeName=nextAssigneeName+","+list_user.get(i).getName();
				}
				//去除节点重复
				if(StringUtils.isEmpty(nextTaskDefMap.get(list_user.get(i).getTaskDefKey()))){
					nextTaskDefMap.put(list_user.get(i).getTaskDefKey(), list_user.get(i).getTaskName());
					if(nextTaskDefKey==null){
						nextTaskDefKey=list_user.get(i).getTaskDefKey();
						nextTaskName=list_user.get(i).getTaskName();
					}else{
						nextTaskDefKey=nextTaskDefKey+","+list_user.get(i).getTaskDefKey();
						nextTaskName=nextTaskName+","+list_user.get(i).getTaskName();
					}
				}
			}
		}else{
			if(null!=list_task&&list_task.size()>0){
				for (int j = 0; j < list_task.size(); j++) {
					if(nextTaskDefKey==null){
						nextTaskDefKey=list_task.get(j).getTaskDefinitionKey();
						nextTaskName=list_task.get(j).getName();
					}else{
						nextTaskDefKey=nextTaskDefKey+","+list_task.get(j).getTaskDefinitionKey();
						nextTaskName=nextTaskName+","+list_task.get(j).getName();
					}
				}
			}
		}
		
		task.setNextAssignee(nextAssignee);
		task.setNextAssigneeName(nextAssigneeName);
		task.setNextTaskDefKey(nextTaskDefKey);
		task.setNextTaskName(nextTaskName);
		//设置办理时间 
		task.setTaskEndDate(new Date());
		//查询办理人ID和姓名
		User user = UserUtils.getByLoginName(task.getAssignee());
		
		if(user!=null)task.setAssigneeName(user.getName());
		//实例号和任务号相同说明是流程启动
		if(task.getProcInsId().equals(task.getTaskId())){
			task.setOrganiser(task.getAssignee());
			task.setOrganiserName(task.getAssigneeName());
			//流程启动标识
			task.setField1("1");
		}else{
			//获取申请人信息
			TaskVo temp=new TaskVo();
			temp.setProcInsId(task.getProcInsId());
			temp.setTaskId(task.getProcInsId());
			//取启动流程人员信息
			TaskVo first=actCommentService.get(temp);
			if(first!=null&&first.getAssignee()!=null){
				task.setOrganiser(first.getAssignee());
				task.setOrganiserName(first.getAssigneeName());
			}
			task.setPassName(Resolution.getName(task.getPass()));
			task.setBusinessId(first.getBusinessId());
		}
		/**
		if(task.getProcInsId().equals(task.getTaskId())){
			ActServiceVO asvo=new ActServiceVO(task);
			asvo.setActStatus(ProcStatus.RUNNING.getKey());
			actServiceService.save(asvo);
		}else if("finish".equals(status)){
			ActServiceVO asvo=new ActServiceVO();
			asvo.setId(task.getProcInsId());
			asvo.setProcInsId(task.getProcInsId());
			asvo.setActStatus(ProcStatus.OVER.getKey());
			asvo.setEndDate(new Date());
			actServiceService.save(asvo);
		}
		**/
		task.setField2(task.getUnionId());
		task.setField3(task.getCompanyId());
		task.setField4(task.getDepId());
		task.setMsg(task.getComment());
		actCommentService.save(task);
		return task;
	}
	
	private List<IdentityLink> checkDeptUser(List<IdentityLink> linkList, TaskVo task) {
		List<IdentityLink> list=new ArrayList<IdentityLink>();
		if(null!=linkList&&linkList.size()>0){
			for(int i=0;i<linkList.size();i++){
				IdentityLink identityLink=linkList.get(i);
				User user = UserUtils.getByLoginName(identityLink.getUserId());
				if(null!=user&&task.getDepId().equals(user.getDepartmentId())&&task.getCompanyId().equals(user.getCompanyId())){
					list.add(identityLink);
				}
			}
		}
		return list;
	}

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
	@Override
	public List<TaskVo> findCommentList(TaskVo taskVo) {
		List<TaskVo> actList =actCommentService.queryList(taskVo);
		return actList;
	}
	
	/**
	 * 获取审批意见
	 * @param procInsId 流程实例ID
	 * @param TaskDefKey 节点KEY
	 * @return 最新审批意见信息
	 */
	public TaskVo findCommentList(String procInsId,String TaskDefKey) {
		TaskVo taskVo=new TaskVo ();
		taskVo.setProcInsId(procInsId);
		taskVo.setTaskDefKey(TaskDefKey);
		List<TaskVo> actList =findCommentList(taskVo);
		if(actList!=null){
			taskVo=actList.get(0);
		}else{
			return null;
		}
		return taskVo;
	}
	/**
	 * 查询已审批的节点列表
	 */
	@Override
	public List<TaskVo> listTastDefKey(TaskVo parm) {
		List<TaskVo> actList =actCommentService.listTastDefKey(parm);
		return actList;
	}

	private void checkNextAssignee(String procInsId){
		if(StringUtils.isEmpty(procInsId))return;
		ProcessInstance pro=getProcIns(procInsId);
		if(pro==null)return;
		// 取得流程定义
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(pro.getProcessDefinitionId());
		//查询当前所有活动节点
		TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active();
		List<Task> list_task = taskQuery.list();
		// 所有活动节点
		if(list_task!=null){
			for (int i = 0; i < list_task.size(); i++) {
				TaskEntity task = (TaskEntity) list_task.get(i);
				// 根据节点ID，获取对应的活动节点
				ActivityImpl activity = pd.findActivity(task.getTaskDefinitionKey());
				
				String type=(String)activity.getProperty("type");
				if("userTask".equals(type)){
					//查询节点办理人
					List<User> list_user=getIdentityLinks(null,task.getId());
					if(list_user==null||list_user.size()==0){
						throw new RuntimeException("ERROR:下一节点没有分配审批人员，请联系管理员!");
					}
				}
				
			}
		}
	}

	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());
		
		ProcessDiagramGenerator diagramGenerator=processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		List<String> highLightedFlows = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}

       //高亮环节id集合
        //highLightedFlows=getHighLightedFlows(executionId,definitionEntity);
        highLightedFlows=processInstanceHighlightsService.getHighLightedFlows(executionId,definitionEntity);
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "jpg", activeActivityIds,highLightedFlows,"宋体","宋体","宋体",processEngine.getProcessEngineConfiguration().getClassLoader(),1.0);
		
		return imageStream;
	}
	/**
	 * 设置业务信息
	 */
	private List<TaskVo> setBusinessInfor(List<TaskVo> list){
		List<TaskVo> relist=new ArrayList<TaskVo>();
		List<String> procInsIdList=new ArrayList<String>();
		Map<String ,TaskVo> procMap=Maps.newHashMap();
		for(TaskVo vo :list){
			procInsIdList.add(vo.getProcInsId());
		}
		//流程启动业务信息
		if(procInsIdList!=null&&procInsIdList.size()>0){
			TaskVo parm=new TaskVo();
			parm.setProcInsIdList(procInsIdList);
			List<TaskVo> actList =actCommentService.queryList(parm);
			if(actList==null)return list;
			for(TaskVo temp:actList){
				procMap.put(temp.getProcInsId(), temp);
			}
		}
		for(TaskVo task :list){
			procInsIdList.add(task.getProcInsId());
			TaskVo temp =procMap.get(task.getProcInsId());
			if(temp!=null){
				task.setBusinessId(temp.getBusinessId());
				task.setUnionId(temp.getField2());
				task.setCompanyId(temp.getField3());
				task.setDepId(temp.getField4());
				task.setField5(temp.getField5());
			}
			relist.add(task);
		}
		return relist;
	}
	/**
	 * 根据流程实例ID活动当前活动节点
	 * 
	 * @param procInsId
	 *            流程ID
	 * @return
	 */
	public List<Task> getCurrentTaskList(String procInsId){
		return taskService.createTaskQuery().processInstanceId(procInsId).active().list();
	}
}
