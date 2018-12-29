/**
 * Project Name:loan-web
 * File Name:OrganizationController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年8月4日上午11:42:50
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.service.OrganizationService;

/**
 * ClassName:OrganizationController <br/>
 * Function: 机构管理 <br/>
 * Date:     2017年8月4日 上午11:42:50 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/org")
public class OrganizationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	@Reference
	private UserApi userApi;
	
	/**
	 * list:机构管理列表 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(LoanOrganization entity){
		ModelAndView mav = new ModelAndView("organization/orgList");
		mav.addObject("page", organizationService.queryListPage(entity));
		return mav;
	}
	
	/**
	 * ajaxQueryList: 机构管理列表异步查询<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanOrganization entity){
		ModelAndView mav = new ModelAndView("organization/_orgTemp");
		mav.addObject("page", organizationService.queryListPage(entity));
		return mav;
	}
	
	/**
	 * orgDetails:机构详情 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/orgDetails")
	public ModelAndView orgDetails(LoanOrganization entity){
		ModelAndView mav = new ModelAndView("organization/orgDetails");
		mav.addObject("org", organizationService.get(entity));
		return mav;
	}
	
	/**
	 * 机构新增页面 <br/>
	 * @param 
	 * @return ModelAndView
	 * @since JDK 1.8
	 */
	@RequestMapping("/orgAdd")
	public ModelAndView orgAdd(){
		ModelAndView mav = new ModelAndView("organization/orgUpdate");
		LoanOrganization entity=new LoanOrganization();
		mav.addObject("organization", entity);
		return mav;
	}
	
	/**
	 * 机构修改页面 <br/>
	 * @param 
	 * @return ModelAndView
	 * @since JDK 1.8
	 */
	@RequestMapping("/orgModify")
	public ModelAndView orgModify(LoanOrganization entity){
		ModelAndView mav = new ModelAndView("organization/orgUpdate");
		if(null!=entity&&null!=entity.getOrgId()){
			entity=organizationService.get(entity);
		}
		mav.addObject("organization",entity);
		return mav;
	}
	
	/**
	 * 根据城市查询客户经理 <br/>
	 * @param 
	 * @return ModelAndView
	 * @since JDK 1.8
	 */
	@RequestMapping("/getUserListByCity")
	@ResponseBody
	public List<User> getUserListByCity(String province,String city){
		SecurityUser emp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		User user=new User();
		user.setProvinceName(province);
		user.setCityName(city);
		user.setStatus("03");
		if(!"admin".equals(emp.getUsername())&&!"yunying".equals(emp.getUsername())) {
			user.setUnionId(emp.getUnionId());
		}
		List<User> list=userApi.querySparkUserListByCity(user);
		return list;
	}
	
	/**
	 * 保存机构信息 <br/>
	 * @param 
	 * @return map
	 * @since JDK 1.8
	 */
	@RequestMapping("/updateOrg")
	@ResponseBody
	public Map<String, Object> updateOrg(LoanOrganization entity){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result=organizationService.updataOrgInfo(entity);
		} catch (Exception e) {
			LOGGER.info("保存机构信息失败", e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "保存机构信息失败");
		}
		return result;
	}
	
}