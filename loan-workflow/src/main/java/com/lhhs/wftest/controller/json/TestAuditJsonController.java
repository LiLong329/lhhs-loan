package com.lhhs.wftest.controller.json;

/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lhhs.wftest.bs.inf.TestAuditService;
import com.lhhs.wftest.model.SysTestAudit;
import com.lhhs.wftest.model.TestAudit;
import com.lhhs.workflow.common.controller.BaseController;
import com.lhhs.workflow.dao.domain.User;
import com.lhhs.workflow.sys.utils.bs.UserUtils;


/**
 * 审批Controller
 * @author thinkgem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "/workflow/json/testAudit")
public class TestAuditJsonController extends BaseController {

	@Autowired
	private TestAuditService testAuditService;
	@ModelAttribute
	public TestAudit get(@RequestParam(required=false) String id){//, 
//			@RequestParam(value="act.procInsId", required=false) String procInsId) {
		TestAudit testAudit = null;
		if (StringUtils.isNotBlank(id)){
			testAudit = testAuditService.get(id);
//		}else if (StringUtils.isNotBlank(procInsId)){
//			testAudit = testAuditService.getByProcInsId(procInsId);
		}
		if (testAudit == null){
			testAudit = new TestAudit();
		}
		return testAudit;
	}
	
	/**
	 * 申请单填写
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(TestAudit testAudit, Model model,HttpServletRequest request) {
		
		String view = "testAuditForm";
		TestAudit temp=null;
		// 查看审批申请单
		if (StringUtils.isNotBlank(testAudit.getId())){
			temp=get(testAudit.getId());
			// 环节编号
			String taskDefKey = testAudit.getTaskDefKey();
			temp.setUser(UserUtils.getUser());
			temp.setTaskId(testAudit.getTaskId());
			temp.setProcInsId(testAudit.getProcInsId());
			temp.setTaskDefKey(testAudit.getTaskDefKey());
			model.addAttribute("procInsId", testAudit.getProcInsId());
			// 查看工单
			if(testAudit.isFinishTask()){
				view = "testAuditView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "testAuditForm";
			}
			// 审核环节
			else {
				view = "testAuditAudit";
			}
		}

		model.addAttribute("testAudit", temp);
		return "wftest/" + view;
	}
	
	
	/**
	 * 申请单保存/修改
	 * @param testAudit
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "save")
	public String save(TestAudit testAudit, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
		if (!beanValidator(model, testAudit)){
			//return form(testAudit, model);
		}
		//取得用户名，作为新建任务创建人员
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getUserId())){
			testAudit.setCreateBy(user);
			testAudit.setUpdateBy(user);
		}
		
		testAuditService.insert(testAudit);
		addMessage(redirectAttributes, "提交审批'" + testAudit.getContent()+ "'成功");
		return "redirect:"  + "/workflow/act/actTaskTodoList";
	}
	/**
	 * 工单执行（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveAudit")
	public String saveAudit(TestAudit testAudit, Model model,HttpServletRequest request) throws Exception {
		String flag=testAudit.getFlag();
		if (StringUtils.isBlank(flag)
				|| StringUtils.isBlank(testAudit.getComment())){
			addMessage(model, "请填写审核意见。");
			return form(testAudit, model,request);
		}
		User user = UserUtils.getUser();
		testAudit.setUser(user);
		testAuditService.auditSave(testAudit);
		return "redirect:"+"/workflow/url/acttask/todo";
	}
	@ResponseBody
	@RequestMapping(value ="assessList")
	public  List<SysTestAudit> assessList(Model model,String category,  @RequestParam("page") int nowpage, @RequestParam("rows") int pageSize,
	        @RequestParam("sort") String sort, @RequestParam("order") String order) {
		List<SysTestAudit> list = testAuditService.getAllAssass();
		return list;
	}
}
