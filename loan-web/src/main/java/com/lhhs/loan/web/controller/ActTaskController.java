package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.InvestCustomerService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.dao.domain.TaskVo;
/**
 * 流程个人任务相关Controller
 */
@Controller
@RequestMapping(value = "/workflow/acttask/")
public class ActTaskController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ActTaskController.class);
	
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	
	@Autowired(required = false)
	private ActTaskService actTaskService;
	@Autowired
	private InvestCustomerService investCustomerService;
	@Autowired
	private SystemManagerService systemManagerService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping("todoList/{selectType}")
	public ModelAndView todoList(TaskVo parm,@PathVariable("selectType") Integer selectType) throws Exception {
		ModelAndView mav=new ModelAndView("workflow/actTaskTodoList");
		selectType=(null==selectType)?1:selectType;
		switch(selectType){//1： 代办任务 ； 3：指派任务
		case 1:
			mav.addObject("taskType", "1");
			break;
		case 3:
			mav.addObject("taskType", "3");
			break;
		default:
			mav.addObject("taskType", "1");
			break;
		}
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		if(loanEmp!=null){
			parm.setUserId(loanEmp.getLeAccount());
			Page page = actTaskService.todoList(parm);
			mav.addObject("page", page);
		}
		return mav;
	}
	
	
		
	/**
	 * 获取待办列表
	 * @param page分页查询
	 * @return
	 */
	@RequestMapping("todoListPage")
	public ModelAndView todoListPage(@RequestParam(name="pageNumber",required=false)Integer pageNumber,
			@RequestParam(name="taskType",required=false)String taskType) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/_actTaskTodoList");
		if("3".equals(taskType)){
			mav.addObject("taskType", "3");
		}else{
			mav.addObject("taskType", "1");
		}
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		if(loanEmp!=null){
			TaskVo parm=new TaskVo();
			parm.setPage(page);
			parm.setUserId(loanEmp.getLeAccount());
			Page page = actTaskService.todoList(parm);
			mav.addObject("page", page);
		}
		return mav;
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping("historicList")
	public ModelAndView historicList(TaskVo parm) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/actTaskHistoricList");
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		if(loanEmp!=null){
			parm.setUserId(loanEmp.getLeAccount());
			Page page = actTaskService.historicList(parm);
			mav.addObject("page", page);
		}
		return mav;
	}
	
	/**
	 * 获取已办任务--分页
	 * @param page分页查询
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping("historicListSed")
	public ModelAndView historicListSed(@RequestParam(name="pageNumber",required=false)Integer pageNumber) throws Exception {
		ModelAndView mav = new ModelAndView("workflow/_actTaskHistoricList");
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		if(loanEmp!=null){
			TaskVo parm=new TaskVo();
			parm.setPage(page);
			parm.setUserId(loanEmp.getLeAccount());
			Page page = actTaskService.historicList(parm);
			mav.addObject("page", page);
		}
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
	 * 指派任务
	 * 
	 */
	@RequestMapping(value = "candidateUser")
	public ModelAndView candidateUser(TaskVo act){
		ModelAndView mav = new ModelAndView("workflow/actTaskCandidateUser");
/*		if(!StringUtils.isEmpty(act.getTaskId())){
			act=actTaskService.getTaskVo(act.getTaskId());
			mav.addObject(act);
		}*/
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", "act");
		CommonUtils.fillCompanyToMap(map);
		map.put("oaOrgIdList", null);
		List<LoanEmp> list=systemManagerService.queryCustManagerByDeptId(map);
		mav.addObject("task", act);
		mav.addObject("allManager", list);
		return mav;
	}
	/**
	 * 指派任务
	 * 
	 */
	@RequestMapping(value = "candidateUserSave")
	@ResponseBody
	public Map<String, Object> candidateUserSave(TaskVo act){
//		ModelAndView mav = new ModelAndView("workflow/actTaskTodoList");
//		actTaskService.addCandidateUser(act.getTaskId(), act.getUserId());
//		return mav;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			actTaskService.addCandidateUser(act.getTaskId(), act.getUserId());
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
		} catch (Exception e) {
			resultMap.put(SystemConst.retMsg, e.getMessage());
			logger.error("保存指派任务Get a Exception："+e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 手动指派任务
	 */
	@RequestMapping(value = "manuallyAssignTask")
	public ModelAndView manuallyAssignTask(){
		ModelAndView mav = new ModelAndView("workflow/actTaskManuallyAssign");
		Map<String,Object> map=new HashMap<String,Object>();
		CommonUtils.fillCompanyToMap(map);
		List<Map<String, Object>> companys=investCustomerService.queryAllCompany(map);//查询公司
		mav.addObject("companys", companys);
		return mav;
	}
}

