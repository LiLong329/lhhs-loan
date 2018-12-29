/**
 * Project Name:loan-web
 * File Name:CompanyCapitalController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月28日上午9:40:36
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;
import com.lhhs.loan.service.AccountsSubjectInfoService;
import com.lhhs.loan.service.AccountsTransService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.LoanAccountInfoService;

/**
 * ClassName:CompanyCapitalController <br/>
 * Function: 公司资金账目管理模块<br/>
 * Date:     2017年7月28日 上午9:40:36 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/companyCapital")
@SuppressWarnings("all")
public class CompanyCapitalController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyCapitalController.class);
	
	@Autowired
	private LoanAccountInfoService loanAccountInfoService;
	@Autowired
	private AccountsTransService accountsTransService;
	@Autowired
	private AccountsSubjectInfoService accountsSubjectInfoService;
	
	/**
	 * accountOverview:(公司资金账户总览列表). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/accountOverview")
	public ModelAndView accountOverview(HttpServletRequest request,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
		@RequestParam(value = "pageSize", required = false) Integer pageSize){
		ModelAndView mav=new ModelAndView("companyCapital/accountOverview");
		LoanAccountsTotal entity=new LoanAccountsTotal();
		pageIndex=pageIndex!=null?pageIndex:1;
		pageSize=pageSize!=null?pageSize:10;
		Page page=new Page(pageIndex,pageSize);
		CommonUtils.fillCompany(entity);
		entity.setPage(page);
		entity.setAccountType(SystemConst.AccountType.COMPANY);
		page = loanAccountInfoService.queryCompanyAccountList(entity);
		mav.addObject("page", page);
		return mav;
	}
	
	/**
	 * accountOverview:(公司资金账户总览异步查询). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxAccountOverview")
	public ModelAndView ajaxAccountOverview(HttpServletRequest request,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
		@RequestParam(value = "pageSize", required = false) Integer pageSize){
		ModelAndView mav=new ModelAndView("companyCapital/_accountOverviewSub");
		LoanAccountsTotal entity=new LoanAccountsTotal();
		pageIndex=pageIndex!=null?pageIndex:1;
		pageSize=pageSize!=null?pageSize:10;
		Page page=new Page(pageIndex,pageSize);
		CommonUtils.fillCompany(entity);
		entity.setPage(page);
		entity.setAccountType(SystemConst.AccountType.COMPANY);
		page = loanAccountInfoService.queryOrganizationAccountList(entity);
		mav.addObject("page", page);
		return mav;
	}
	
	/**
	 * transRecord:(公司资金交易记录列表). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transRecord")
	public ModelAndView transRecord(HttpServletRequest request, LoanAccountsTransVo entity){
		ModelAndView mav=new ModelAndView("companyCapital/transRecord");
		CommonUtils.fillCompany(entity);
		entity.setAccountType(SystemConst.AccountType.COMPANY);
		Page page = accountsTransService.queryListPage(entity);
		mav.addObject("page", page);
		mav.addObject("map", accountsTransService.querySumAmount(entity));//金额统计
		return mav;
	}
	
	/**
	 * transRecord:(公司资金交易记录异步查询). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxTransRecord")
	public ModelAndView ajaxTransRecord(HttpServletRequest request, LoanAccountsTransVo entity){
		ModelAndView mav=new ModelAndView("companyCapital/_transRecordSub");
		CommonUtils.fillCompany(entity);
		entity.setAccountType(SystemConst.AccountType.COMPANY);
		Page page = accountsTransService.queryListPage(entity);
		mav.addObject("page", page);
		mav.addObject("map", accountsTransService.querySumAmount(entity));//金额统计
		return mav;
	}

	
	/**
	 * transRecord:(导出公司资金交易记录). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/exportTransRecord")
	@ResponseBody
	public void exportTransRecord(HttpServletRequest request, HttpServletResponse response,
			LoanAccountsTransVo entity){
		CommonUtils.fillCompany(entity);
		entity.setAccountType(SystemConst.AccountType.COMPANY);
		List<LoanAccountsTransVo> list = accountsTransService.queryList(entity);
		if(list.size()>0){
 			String filename ="公司交易记录";
 			Map<String,String>  titles = new LinkedHashMap<String,String>();
 			titles.put("transId", "交易ID");
 			titles.put("ownerName", "公司名称");
 			titles.put("orderNo", "业务编号");
 			titles.put("debitAmount", "收入（元）");
 			titles.put("creditAmount", "支出（元）");
 			titles.put("balance", "账户余额（元）");
 			titles.put("subjectName", "交易摘要");
 			titles.put("transTime", "交易时间");
 			titles.put("transRemark", "备注");
 			AnalyseTransExcel.downLoadExcel(request, response, filename, list, LoanAccountsTransVo.class, titles);
 		}
	}
	
}