package com.lhhs.workflow.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.bs.inf.ActProcessService;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.common.controller.BaseController;
import com.lhhs.workflow.dao.domain.Act;
import com.lhhs.workflow.dao.domain.ProcessVO;
/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "/workflow/url/actprocess")
public class ActProcessUrlController extends BaseController{

	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private HttpServletRequest request;
	/**
	 * 流程定义列表
	 */
	@RequestMapping(value = {"list"})
	public ModelAndView processList(ProcessVO parm) {
		
		ModelAndView mav = new ModelAndView("workflow/actProcessList");
		Page pageVo = new Page();
		if(parm.getPage()!=null){
			pageVo=parm.getPage();
		}
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		
		pageVo = actProcessService.processList(pageVo,parm);
		List<ProcessVO> list=new ArrayList();
		if(pageVo!=null&&pageVo.getResultList()!=null){
			//转换前端List使用对象
			for (Iterator itr = pageVo.getResultList().iterator(); itr.hasNext();) {
				 list.add(new ProcessVO((Act)itr.next()));
			}
			pageVo.setResultList(list);
		}
		mav.addObject("page", pageVo);
		return mav;
	}
	
	/**
	 * 运行中的实例列表
	 */
	@RequestMapping(value = "running")
	public ModelAndView runningList(ProcessVO parm) {
		ModelAndView mav = new ModelAndView("workflow/actProcessRunningList");
		Page pageVo = new Page();
		if(parm.getPage()!=null){
			pageVo=parm.getPage();
		}
		pageVo = actProcessService.runningList(pageVo, parm);
		
		List<ProcessVO> list=new ArrayList();
		if(pageVo!=null&&pageVo.getResultList()!=null){
			//转换前端List使用对象
			for (Iterator itr = pageVo.getResultList().iterator(); itr.hasNext();) {
				ProcessVO vo=new ProcessVO((ProcessInstance)itr.next());
				List<Task> list_tast=actTaskService.getCurrentTaskList(vo.getProcInsId());
				if(list_tast!=null){
					String taskIdList="";
					for(Task temp:list_tast){
						taskIdList=taskIdList+temp.getId()+",";
					}
					taskIdList=taskIdList.substring(0, taskIdList.length()-1);
					vo.setTaskIdList(taskIdList);
					
				}
				list.add(vo);
			}
			pageVo.setResultList(list);
		}
		
		mav.addObject("page", pageVo);
		return mav;
	}
	/**
	 * 运行中的实例列表
	 */
	@RequestMapping(value = "runningPage")
	public ModelAndView runningListPage(ProcessVO parm) {
		ModelAndView mav = new ModelAndView("workflow/_actProcessRunningList");
		Page pageVo = new Page();
		if(parm.getPage()!=null){
			pageVo=parm.getPage();
		}
		pageVo = actProcessService.runningList(pageVo, parm);
		
		List<ProcessVO> list=new ArrayList();
		if(pageVo!=null&&pageVo.getResultList()!=null){
			//转换前端List使用对象
			for (Iterator itr = pageVo.getResultList().iterator(); itr.hasNext();) {
				ProcessVO vo=new ProcessVO((ProcessInstance)itr.next());
				List<Task> list_tast=actTaskService.getCurrentTaskList(vo.getProcInsId());
				if(list_tast!=null){
					String taskIdList="";
					for(Task temp:list_tast){
						taskIdList=taskIdList+temp.getId()+",";
					}
					taskIdList=taskIdList.substring(0, taskIdList.length()-1);
					vo.setTaskIdList(taskIdList);
					
				}
				list.add(vo);
			}
			pageVo.setResultList(list);
		}
		
		mav.addObject("page", pageVo);
		return mav;
	}
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		if(resType.equals("image")){
			response.setContentType("image/gif");
		}
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
	@RequestMapping(value = "deploy", method=RequestMethod.GET)
	public String deploy(Model model) {
		return "workflow/actProcessDeploy";
	}
	@RequestMapping(value = "deploy", method=RequestMethod.POST)
	public void deploy(String category, MultipartFile file, HttpServletResponse response) throws IOException {

		String fileName = file.getOriginalFilename();
		
		if (!StringUtils.isEmpty(fileName)){
			String message = actProcessService.deploy(category, file);
		}
		response.sendRedirect("/workflow/url/actprocess/list");
	}

	
	/**
	 * 设置流程分类
	 * @throws IOException 
	 */
	@RequestMapping(value = "updateCategory")
	public void updateCategory(String procDefId, String category, HttpServletResponse response) throws IOException {
		actProcessService.updateCategory(procDefId, category);
		response.sendRedirect("/workflow/url/actprocess/list");
	}


	/**
	 * 挂起、激活流程实例
	 * @throws IOException 
	 */
	@RequestMapping(value = "updateState")
	public void updateState(String state, String procDefId, HttpServletResponse response) throws IOException {
		String message = actProcessService.updateState(state, procDefId);
		response.sendRedirect("/workflow/url/actprocess/list");
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @param redirectAttributes
	 * @return
	 * @throws XMLStreamException
	 * @throws IOException 
	 */
	@RequestMapping(value = "convert/toModel")
	public void convertToModel(String procDefId, HttpServletResponse response) throws XMLStreamException, IOException {
		org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
		response.sendRedirect("/workflow/url/actmodel/list");
	}
	


	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete")
	public void delete(String deploymentId,HttpServletResponse response) throws IOException {
		actProcessService.deleteDeployment(deploymentId);
		response.sendRedirect("/workflow/url/actprocess/list");
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 * @throws IOException 
	 */
	@RequestMapping(value = "deleteProcIns")
	public void deleteProcIns(String procInsId, String reason,HttpServletResponse response) throws IOException {
		actProcessService.deleteProcIns(procInsId,reason);
		response.sendRedirect("/workflow/url/actprocess/running");
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequestMapping(value = "export/diagrams")
	public ModelAndView exportDiagrams(@Value("${activiti.export.diagram.path}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);
		ModelAndView mav = new ModelAndView("workflow/actProcessExportPic");
		mav.addObject("files", files);
		mav.addObject("path", exportDir);
		return mav;
	}
	
}
