/**
 * Project Name:loan-web
 * File Name:EmpQuartersCotroller.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年11月6日下午5:17:28
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.QuartersApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.api.UserQuartersApi;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.bump.domain.UserQuarters;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanEmpQuarters;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.EmpQuartersService;

/**
 * ClassName:EmpQuartersCotroller <br/>
 * Function: 员工岗位列表查询
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月6日 下午5:17:28 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/empQuarters")
@SuppressWarnings("all")
public class EmpQuartersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpQuartersController.class);
	
	@Reference
	private UserQuartersApi userQuartersApi;
	
	@Reference
	private UnionCompanyApi unionCompanyApi;
	
	@Value("${server.sys.id}")
	private String sysId;
	
	/**
	 * list:员工岗位列表查询<br/>
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(UserQuarters params){
		ModelAndView mav = new ModelAndView("empQuarters/empQuartersList");
		//TODO 查询条件要完善
		CommonUtils.fillBumpCompany(params);
		params.setServerId(sysId);
		com.lhhs.bump.Page<UserQuarters> page = userQuartersApi.getListPage(params);
		UnionCompany vo=new UnionCompany();
		CommonUtils.fillBumpCompany(vo);
		List<UnionCompany> companys = CommonUtils.getBumpCompanyList(vo);
		mav.addObject("page", page);
		mav.addObject("companys", companys);
		mav.addObject("params", params);
		return mav;
	}
	
/*	*//**
	 * ajaxQueryList:
	 * 员工岗位列表 异步搜索查询及分页查询<br/>
	 *
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 *//*
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanEmpQuarters entity){
		ModelAndView mav = new ModelAndView("empQuarters/_empQuartersTemp");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		Page page = empQuartersService.queryListPage(entity);
		mav.addObject("page", page);
		return mav;
	}
	
	*//**
	 * 
	 * companyDeptLink:公司部门级联查询
	 * @author suncj
	 * @param request
	 * @param lgDeptId
	 * @return
	 * @since JDK 1.8
	 *//*
	@RequestMapping("/companyDeptLink")
	@ResponseBody
	public Map<String, Object> companyDeptLink(HttpServletRequest request,String companyId){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<Map<String, Object>> deptList=empQuartersService.queryAllDept(companyId);
		resultMap.put("deptList", deptList);
		return resultMap;
	}*/
	

}

