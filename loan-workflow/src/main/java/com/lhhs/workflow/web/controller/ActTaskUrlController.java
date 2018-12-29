package com.lhhs.workflow.web.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.common.controller.BaseController;
import com.lhhs.workflow.common.utils.ActUtils;
import com.lhhs.workflow.dao.domain.Act;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.TaskVo;
import com.lhhs.workflow.dao.domain.User;
import com.lhhs.workflow.sys.utils.bs.UserUtils;

/**
 * 流程个人任务相关Controller
 */
@Controller
@RequestMapping(value = "/workflow/url/acttask/")
public class ActTaskUrlController extends BaseController{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ActTaskService actTaskService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = {"todo"})
	public ModelAndView todoList(TaskVo parm) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/actTaskTodoListAdmin");
		
		if(parm.getUserId()==null){
			//用户工具类
			User user=UserUtils.getUser();
			if(user!=null){
				parm.setUserId(user.getUserId());
			}else{
				parm.setUserId("admin");
			}
		}
		
		Page page = actTaskService.todoList(parm);
		mav.addObject("page", page);
		return mav;
	}
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "historic")
	public ModelAndView historicList(TaskVo parm) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/actTaskHistoricList");
		if(parm.getUserId()==null){
			//用户工具类
			User user=UserUtils.getUser();
			if(user!=null){
				parm.setUserId(user.getUserId());
			}else{
				parm.setUserId("admin");
			}
		}
		Page page = actTaskService.historicList(parm);
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "histoicFlow")
	public ModelAndView histoicFlow(TaskVo act){
		ModelAndView mav = new ModelAndView("workflow/_actTaskHistoricFlow");
		//Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(act.getProcInsId())){
			List<TaskVo> histoicFlowList = actTaskService.findCommentList(act);
			mav.addObject("resultList", histoicFlowList);
			mav.addObject("total", histoicFlowList.size());
		}
		return mav;
	}
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	@RequestMapping(value = "process")
	public String processList(String category, Model model) {
		model.addAttribute("category", category);
		return "/workflow/actTaskProcessList";
	}
	
	/**
	 * 获取流程表单
	 * @param taskId	任务ID
	 * @param taskName	任务名称
	 * @param taskDefKey 任务环节标识
	 * @param procInsId 流程实例ID
	 * @param procDefId 流程定义ID
	 */
	@RequestMapping(value = "form")
	public String form(TaskVo act){
		
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
		ProcessDefinition procDef=actTaskService.getProcDef(act.getProcDefId());
		//流程分类
		String category=procDef.getCategory();
		act.setProcDefKey(procDef.getKey());
		String formUrl=ActUtils.getFormUrl(formKey, act,category);
		return "redirect:" + formUrl;
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 */
	@RequestMapping(value = "start")
	public String start(ActVo parm, Model model) throws Exception {
		User user=UserUtils.getUser();
		//Map<String, Object> vars =new HashMap<String, Object>();
		//ActParm parm=ActParm(act.getProcDefKey(), act.getBusinessId(), user.getLoginName(), act.getTitle(), vars, String sys, String channel)
		
		parm=actTaskService.startProcess(parm) ;
		String procInsId=parm.getProcInsId();
		model.addAttribute("procInsId", procInsId);
		//actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
		return "true";//adminPath + "/act/task";
	}

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "claim")
	public String claim(Act act) {
		String userId = UserUtils.getUser().getUserName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务流程变量，如下
	 * 		vars.keys=flag,pass
	 * 		vars.values=1,true
	 * @throws Exception 
	 */
	@RequestMapping(value = "complete")
	public String complete(ActVo parm) throws Exception {
		User user=UserUtils.getUser();
		parm.setUserId(user.getUserName());
		actTaskService.complete(parm);
		return "true";//adminPath + "/act/task";
	}


	/**
	 * 删除任务
	 * @param taskId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequestMapping(value = "deleteTask")
	public String deleteTask(String taskId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			actTaskService.deleteTask(taskId, reason);
			addMessage(redirectAttributes, "删除任务成功，任务ID=" + taskId);
		}
		return "redirect:" + "/workflow/act/task";
	}
	
	/**
	 * 获取获取审批列表
	 */
	@RequestMapping(value = "list")
	public String assessList(Model model) {
		return "wftest/testAuditList";
	}
	
	/**
	 * 显示跟踪流程图图
	 * 
	 */
	@RequestMapping(value = "processPic")
	public String processPic(String procDefId, String procInsId) throws Exception {
		
		String diagramViewer="/act/diagram-viewer/index.html?processDefinitionId="+procDefId+"&processInstanceId="+procInsId;
		logger.debug(diagramViewer);
		return "redirect:" + diagramViewer;
	}
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "tracePhoto")
	@ResponseBody
	public void tracePhoto(String procDefId, String procInsId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, procInsId);
		response.setContentType("image/gif");
		//response.setCharacterEncoding("UTF-8");
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

}
