/**
 * Project Name:loan-web
 * File Name:CredentialsController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年8月3日上午10:07:44
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.RandomUtil;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.service.CredentialsService;

/**
 * ClassName:CredentialsController <br/>
 * Function:  资质模板管理   <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月3日 上午10:07:44 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/credentialsManage")
public class CredentialsController extends BaseController{
	
	@Autowired
	private CredentialsService credentialsService;
	
	/**
	 * transAccountsList:(资质模板管理-列表查询). <br/>
	 * @author suncj
	 * @param request
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/credentialsList")
	public String credentialsList(HttpServletRequest request,LoanCredentials entity){
		 Page page =credentialsService.queryListPage(entity);
		 request.setAttribute("page", page);
		return "system/credentialsManage/credentialsList";
	}
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add")	
	public String add(){
		return "system/credentialsManage/credentialsAdd";
	}
	
	/**
	 * 保存
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save")	
	@ResponseBody
	public Map<String, Object> save(LoanCredentials credentials){
		credentials.setCredentialsId(String.valueOf(RandomUtil.random()));
		credentials.setStatus("1");//默认为启用
		//判断英文名称唯一性
		LoanCredentials entity = new LoanCredentials();
		entity.setEnglishName(credentials.getEnglishName());
		List<LoanCredentials> list = credentialsService.queryList(entity);
		if (list!=null&&list.size()>0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("retCode", "01");
			map.put("retMsg", "产品英文名称已存在");
			return map;
		}
		
		int count = credentialsService.save(credentials);
		if (1 == count) {
			return SUCCESS_MESSAGE;
		} 
		return ERROR_MESSAGE;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit")	
	public String edit(String id,ModelMap modelMap ){
		modelMap.put("entity", credentialsService.get(id));
		return "system/credentialsManage/credentialsEdit";
	}
	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")	
	@ResponseBody
	public Map<String, Object> update(LoanCredentials credentials){
		int count = credentialsService.update(credentials);
		if (1 == count) {
			return SUCCESS_MESSAGE;
		} 
		return ERROR_MESSAGE;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")	
	@ResponseBody
	public Map<String, Object> delete(String id){
		LoanCredentials credentials = new LoanCredentials();
		credentials.setCredentialsId(id);;
		int count = credentialsService.delete(credentials);
		if (1 == count) {
			return SUCCESS_MESSAGE;
		} 
		return ERROR_MESSAGE;
	}
	
	/**
	 * 资质禁用、启用
	 */
	@RequestMapping("/credentialsIfEnable")
	@ResponseBody
	public Map<String,Object> credentialsIfEnable(HttpServletRequest request,String ifEnable,String credentialsId){
		Map<String, Object> map = new HashMap<String, Object>();
		LoanCredentials credentials = new LoanCredentials();
		credentials.setCredentialsId(credentialsId);
		credentials.setStatus(ifEnable);
		int count = credentialsService.update(credentials);
		if(1 == count){
			map.put("retCode",	 "00");
			if ("1".equals(ifEnable)) {
				map.put("retMsg", "启用成功");
			}else {
				map.put("retMsg", "禁用成功");
				
			}
		}else {
			map.put("retCode", "01");
			map.put("retMsg", "操作失败");
		}
		return map;
	}
	
	/**
	 * 
	 * selectBorrowerInfoDetial:(资质模板管理-查看详情). <br/>
	 * @author Administrator
	 * @param request
	 * @param customerId 
	 * @param credentialsNo 序号 
	 * @return
	 * @since JDK 1.8
	 */
    @RequestMapping("/getCredentialsInfo")
    public String getCredentialsInfo(HttpServletRequest request,
   		 	@RequestParam(name = "credentialsId", required = false) String credentialsId,
   		 	@RequestParam(name = "credentialsNo", required = false) String credentialsNo
   		 	){
    	 LoanCredentials credentialsInfo = credentialsService.get(credentialsId);
	   	 request.setAttribute("credentials", credentialsInfo);
	   	 request.setAttribute("credentialsNo", credentialsNo);
	   	return "system/credentialsManage/credentialsInfo";
    }
}

