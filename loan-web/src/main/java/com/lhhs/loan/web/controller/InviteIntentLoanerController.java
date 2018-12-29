/**
 * Project Name:loan-web
 * File Name:ReportStatisticsController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年8月29日下午5:17:08
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.CrmIntentLoanUserRecord;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserRecordService;

/**
 * Function: 回访客户记录表
 * @author   wangguoli
 */
@Controller
@RequestMapping("/inviteIntentLoaner")
public class InviteIntentLoanerController {
 
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	@Autowired
	private CrmIntentLoanUserRecordService recordService;
	@Autowired
	private CrmIntentUserCountController crmIntentUserCountController;
	
	/**
	 * 回访记录列表
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("/inviteList")
	public ModelAndView inviteList(HttpServletRequest request,CrmIntentLoanUserRecord entity){
		ModelAndView model = new ModelAndView("inviteRecord/inviteIntentLoanerList");
		CommonUtils.fillCompany(entity);
		initCompanyDate(request, entity);
		page = recordService.queryInviteRecord(entity);
		if(StringUtils.isNotEmpty(entity.getAppointCompanyId())){
			Map<String, Object> map = crmIntentUserCountController.getDeptByCompanyId(request, entity.getAppointCompanyId());
			Set<LoanDept> returnList = (Set<LoanDept>) map.get("deptList");
			request.setAttribute("depts", returnList);
		}
		request.setAttribute("page", page);
		request.setAttribute("entity", entity);
		return model;
	}
	
	/**
	 * 查询回访记录列表
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(HttpServletRequest request,CrmIntentLoanUserRecord entity){
		CommonUtils.fillCompany(entity);
		ModelAndView model = new ModelAndView("inviteRecord/temp/InviteTemp");
		request.setAttribute("page", recordService.queryInviteRecord(entity));
		return model;
	}
	
	/**
	 * 查询回访详情列表
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("/ajaxQueryDetailList")
	public ModelAndView ajaxQueryDetailList(HttpServletRequest request,CrmIntentLoanUserRecord entity){
		ModelAndView model = new ModelAndView("inviteRecord/temp/infoTemp");
		entity.setAppointEmpId(entity.getLastUser());
		request.setAttribute("page", recordService.queryRecordInfo(entity));
		return model;
	}

	/**
	 * 初始化公司
	 * @param request
	 * @param entity
	 */
	private void initCompanyDate(HttpServletRequest request, CrmIntentLoanUserRecord entity) {
		List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		request.setAttribute("entity", entity);
		request.setAttribute("companys", companyList);//分公司
	}
	
	
	/**
	 * 
	 * 回訪客戶详情信息
	 */
	@RequestMapping("/toRecordInfo")
	public ModelAndView toRecordInfo(HttpServletRequest request,@RequestParam(name="lastUser",required=false)String lastUser,CrmIntentLoanUserRecord entity){
//		CrmIntentLoanUserRecord entity = new CrmIntentLoanUserRecord();
		entity.setAppointEmpId(entity.getLastUser());
		page.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		entity.setPage(page);
		ModelAndView model=new ModelAndView("inviteRecord/toRecordInfo");
		page = recordService.queryRecordInfo(entity);
		model.addObject("page", page);
		model.addObject("lastUser", lastUser);
		return model;
	}
	

	/**
	 * 导出回访客户记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request,HttpServletResponse response,CrmIntentLoanUserRecord entity){
		CommonUtils.fillCompany(entity);
		initCompanyDate(request, entity);
		entity.setPage(null);
		List<CrmIntentLoanUserRecord> list=recordService.getRecordInfoList(entity);
		Map<String, String> titles=new LinkedHashMap<String, String>();
		titles.put("managerCompanyName", "分公司");
		titles.put("managerDepName", "上级部门");
		titles.put("managerGroupName", "当前部门");
		titles.put("managerName", "客户经理");
		titles.put("managerMobile", "客户经理手机号");
	    titles.put("name", "客户姓名");
	    titles.put("mobile", "客户手机号");
	    titles.put("visitCount", "回访次数");
	    titles.put("businessType1", "业务类型");
	    titles.put("source1", "客户来源");
	    titles.put("businessStatus1", "客户状态");
	    titles.put("followType1", "回访方式");
	    titles.put("remark", "回访内容");
	    String fileName="";
	    SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
	    formatter.setLenient(false);
	    formatter = new SimpleDateFormat("yyyyMMdd");
	    if(null!=entity.getBeginingTime()){
	    	String beginTimeSed =formatter.format(entity.getBeginingTime());
	    	fileName+=beginTimeSed;
	    }
 	    if(null!=entity.getEndingTime()){
			 String endTimeSed = formatter.format(entity.getEndingTime());
			 fileName+="-"+endTimeSed;
		}
 		fileName+="回访客户记录";
	    AnalyseTransExcel.downLoadExcel(request, response, fileName, list, CrmIntentLoanUserRecord.class, titles);
	    System.out.println(fileName+"下载完成");
	}
	
}

