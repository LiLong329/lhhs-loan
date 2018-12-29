package com.lhhs.workflow.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.workflow.bs.inf.ActModelService;
import com.lhhs.workflow.common.controller.BaseController;
import com.lhhs.workflow.dao.domain.ModelVO;

/**
 * 流程模型相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "/workflow/url/actmodel")
public class ActModelUrlController  extends BaseController{
	private final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ActModelService actModelService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 流程模型列表
	 */
	@RequestMapping(value = { "/list"})
	public ModelAndView modelList(ModelVO parm) {
		ModelAndView mav = new ModelAndView("workflow/actModelList");
		
		Page pageVo = new Page();
		if(parm.getPage()!=null){
			pageVo=parm.getPage();
		}
		pageVo = actModelService.modelList(pageVo, parm.getCategory());
		
		List<ModelVO> list=new ArrayList();
		if(pageVo!=null&pageVo.getResultList()!=null&pageVo.getResultList().size()>0){
		
			for (Iterator itr = pageVo.getResultList().iterator(); itr.hasNext();) {
				 list.add(new ModelVO((org.activiti.engine.repository.Model)itr.next()));
			}
			pageVo.setResultList(list);
		}
		
		mav.addObject("page", pageVo);
		return mav;
	}

	/**
	 * 创建模型
	 */
	@RequestMapping(value = "create",method = RequestMethod.GET)
	public String create(Model model) {
		return "workflow/actModelCreate";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public void create(String name, String key, String description, String category,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			response.sendRedirect(request.getContextPath() + "/act/process-editor/modeler.jsp?modelId=" + modelData.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建模型失败：", e);
		}
	}
	@RequestMapping(value = "deploy")
	public void deploy(String id, HttpServletResponse response) throws IOException {
		String message = actModelService.deploy(id);
		response.sendRedirect("/workflow/url/actprocess/list");
	}
	/**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "export")
	@ResponseBody
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 * @throws IOException 
	 */
	@RequestMapping(value = "updateCategory")
	public void updateCategory(String id, String category, HttpServletResponse response) throws IOException {
		actModelService.updateCategory(id, category);
		response.sendRedirect("/workflow/url/actmodel");
	}
	
	/**
	 * 删除Model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "delete")
	public void delete(String id, HttpServletResponse response) throws IOException {
		actModelService.delete(id);
		//redirectAttributes.addFlashAttribute("message", "删除成功，模型ID=" + id);
		response.sendRedirect("/workflow/url/actmodel/list");
	}
}
