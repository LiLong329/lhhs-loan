/**
 * Project Name:loan-web
 * File Name:OrganizationCapitalController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月28日上午9:40:36
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.vo.LoanAccountInfoVo;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;
import com.lhhs.loan.service.AccountsSubjectInfoService;
import com.lhhs.loan.service.AccountsTransService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.LoanAccountInfoService;

/**
 * ClassName:OrganizationCapitalController <br/>
 * Function: 机构资金账目管理模块<br/>
 * Date:     2017年7月28日 上午9:40:36 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/organizationCapital")
@SuppressWarnings("all")
public class OrganizationCapitalController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationCapitalController.class);
	
	@Autowired
	private LoanAccountInfoService loanAccountInfoService;
	@Autowired
	private AccountsTransService accountsTransService;
	@Autowired
	private AccountsSubjectInfoService accountsSubjectInfoService;
	
	/**
	 * accountOverview:(机构资金账户总览列表). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/accountOverview")
	public ModelAndView accountOverview(HttpServletRequest request, LoanAccountsTotal entity){
		ModelAndView mav=new ModelAndView("organizationCapital/accountOverview");
		entity.setAccountType(SystemConst.AccountType.ORGANIZATION);
		Page page = loanAccountInfoService.queryOrganizationAccountList(entity);
		mav.addObject("page", page);
		mav.addObject("map", loanAccountInfoService.queryOrganizationAccountTotalAmount(entity));//账户总余额
		return mav;
	}
	
	/**
	 * accountOverview:(机构资金账户总览异步查询). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxAccountOverview")
	public ModelAndView ajaxAccountOverview(HttpServletRequest request, LoanAccountsTotal entity){
		ModelAndView mav=new ModelAndView("organizationCapital/_accountOverviewSub");
		entity.setAccountType(SystemConst.AccountType.ORGANIZATION);
		Page page = loanAccountInfoService.queryOrganizationAccountList(entity);
		mav.addObject("page", page);
		mav.addObject("map", loanAccountInfoService.queryOrganizationAccountTotalAmount(entity));//账户总余额
		return mav;
	}
	
	/**
	 * transRecord:(机构资金交易记录列表). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transRecord")
	public ModelAndView transRecord(HttpServletRequest request, LoanAccountsTransVo entity){
		ModelAndView mav=new ModelAndView("organizationCapital/transRecord");
		CommonUtils.fillCompany(entity);
		Page page = accountsTransService.queryOrganizationTransList(entity);
		mav.addObject("page", page);
		mav.addObject("map", accountsTransService.queryOrganizationTransTotalAmount(entity));//金额统计
		return mav;
	}
	
	/**
	 * transRecord:(机构资金交易记录异步查询). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxTransRecord")
	public ModelAndView ajaxTransRecord(HttpServletRequest request, LoanAccountsTransVo entity){
		ModelAndView mav=new ModelAndView("organizationCapital/_transRecordSub");
		CommonUtils.fillCompany(entity);
		Page page = accountsTransService.queryOrganizationTransList(entity);
		mav.addObject("page", page);
		mav.addObject("map", accountsTransService.queryOrganizationTransTotalAmount(entity));//金额统计
		return mav;
	}
	
	/**
	 * ajaxSubjectInfo:(异步查询科目信息). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxSubjectInfo")
	@ResponseBody
	public List<LoanAccountsSubjectInfo> ajaxSubjectInfo(HttpServletRequest request, String direction){
		List<LoanAccountsSubjectInfo> list=accountsSubjectInfoService.selectSubjectByDirection(direction);
		return list;
	}
	
}