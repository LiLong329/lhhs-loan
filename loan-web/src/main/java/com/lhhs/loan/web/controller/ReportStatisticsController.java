/**
 * Project Name:loan-web
 * File Name:ReportStatisticsController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年8月29日下午5:17:08
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanTransAccounts;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.ReportStatisticsService;

/**
 * ClassName:ReportStatisticsController <br/>
 * Function: 报表统计
 * Date:     2017年8月29日 下午5:17:08 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/reportStatistics")
public class ReportStatisticsController {
 
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	private static final Logger LOGGER = Logger.getLogger(ReportStatisticsController.class);
	
	@Autowired
	private ReportStatisticsService reportService;
	
	
	/**
	 * transReportList:固定理财报表统计
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transReportList")
	public ModelAndView transReportList(HttpServletRequest request,LoanPayPlanCompany entity){
		ModelAndView model = new ModelAndView("reportStatistics/transAccountsReport");
		entity.setTransType(SystemConst.TransType.TYPEID1007);
	    return reportStatistics(entity,model);
	}
	
	/**
	 * CorporateLending:公司放款统计
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/companyLending")
	public ModelAndView companyLending(HttpServletRequest request,LoanPayPlanCompany entity){
		ModelAndView model = new ModelAndView("reportStatistics/companyLending");
		entity.setField3(SystemConst.InvestCustomerNature.COMPANY);
		entity.setTransType(SystemConst.TransType.TYPEID1001);
		return reportStatistics(entity,model);
	}
	
	/**
	 * 
	 * organizationReport:机构放款统计查询
	 * @author suncj
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/organizationReport")
	public ModelAndView organizationReport(HttpServletRequest request,LoanPayPlanCompany entity){
		ModelAndView model = new ModelAndView("reportStatistics/organizationReport");
		entity.setField3(SystemConst.InvestCustomerNature.ORGANIZATION);
		entity.setTransType(SystemConst.TransType.TYPEID1001);
		return reportStatistics(entity,model);
	}
	
	
	/**
	 * 
	 * peerReport:同行放款统计查询
	 * @author suncj
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/peerReport")
	public ModelAndView peerReport(HttpServletRequest request,LoanPayPlanCompany entity){
		ModelAndView model = new ModelAndView("reportStatistics/peerReport");
		entity.setField3(SystemConst.InvestCustomerNature.PEER);
		entity.setTransType(SystemConst.TransType.TYPEID1001);
		return reportStatistics(entity,model);
	}
	/**
	 * 
	 * buttJointReport:对接放款统计查询
	 * @author suncj
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/buttJointReport")
	public ModelAndView buttJointReport(HttpServletRequest request,LoanPayPlanCompany entity){
		ModelAndView model = new ModelAndView("reportStatistics/buttJointReport");
		String condition = "12,13,14,15";
		entity.setDocking(condition);
		entity.setTransType(SystemConst.TransType.TYPEID1001);
		return reportStatistics(entity,model);
	}
	
	
	/**
	 * reportStatistics:报表统计查询 公共查询方法
	 * @author suncj
	 * @param entity
	 * @param model
	 * @return
	 * @since JDK 1.8
	 */
	public ModelAndView reportStatistics(LoanPayPlanCompany entity,ModelAndView model){
		CommonUtils.fillCompany(entity);
		if(SystemConst.InvestCustomerNature.PEER.equals(entity.getField3())||SystemConst.InvestCustomerNature.ORGANIZATION.equals(entity.getField3())){
			entity.setIsCompany("isCompany");
		}
		Page page = reportService.queryListPage(entity);
		entity.setPage(null);
		List<LoanPayPlanCompany> list = reportService.queryReportExport(entity);
		BigDecimal total=new BigDecimal(0.00);
		String orderNo="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				LoanPayPlanCompany payPlanCompany=list.get(i);
				total=total.add(payPlanCompany.getRepaymentCapital()==null?new BigDecimal("0"):payPlanCompany.getRepaymentCapital());
			}
		}
		model.addObject("page", page);
		model.addObject("total", total);
		return model;
	}
	/**
	 * ajaxQueryList:
	 * 报表统计列表 异步搜索查询及分页查询<br/>
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanPayPlanCompany entity){
		String url="";
		if("buttJointReportId".equals(entity.getSearcherId())){
			url="reportStatistics/temp/buttJointReportTemp";
			String condition = "12,13,14,15";
			entity.setDocking(condition);
			entity.setTransType(SystemConst.TransType.TYPEID1001);
		}else if("companyId".equals(entity.getSearcherId())){
			url="reportStatistics/temp/companyLendingTemp";
			entity.setField3(SystemConst.InvestCustomerNature.COMPANY);
			entity.setTransType(SystemConst.TransType.TYPEID1001);
		}else if("peerReportId".equals(entity.getSearcherId())){
			url="reportStatistics/temp/peerReportTemp";
			entity.setField3(SystemConst.InvestCustomerNature.PEER);
			entity.setTransType(SystemConst.TransType.TYPEID1001);
		}else if("organizationReportId".equals(entity.getSearcherId())){
			url="reportStatistics/temp/organizationReportTemp";
			entity.setField3(SystemConst.InvestCustomerNature.ORGANIZATION);
			entity.setTransType(SystemConst.TransType.TYPEID1001);
		}else{
			url="reportStatistics/temp/transAccountsReportTemp";
			entity.setTransType(SystemConst.TransType.TYPEID1007);
		}
		ModelAndView mav = new ModelAndView(url);
		CommonUtils.fillCompany(entity);
		if(SystemConst.InvestCustomerNature.PEER.equals(entity.getField3())||SystemConst.InvestCustomerNature.ORGANIZATION.equals(entity.getField3())){
			entity.setIsCompany("isCompany");
		}
		mav.addObject("page", reportService.queryListPage(entity));
		entity.setPage(null);
		List<LoanPayPlanCompany> list = reportService.queryReportExport(entity);
		BigDecimal total=new BigDecimal(0.00);
		String orderNo="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				LoanPayPlanCompany payPlanCompany=list.get(i);
				total=total.add(payPlanCompany.getRepaymentCapital()==null?new BigDecimal("0"):payPlanCompany.getRepaymentCapital());
				}
		}
		mav.addObject("total", total);
		return mav;
	}
	
	/**
	 * 报表统计--导出
	 * @author suncj
	 * @param entity
	 * @param response
	 * @param request
	 * @since JDK 1.8
	 */
	@RequestMapping("/reportExport")
	public void reportExport(LoanPayPlanCompany entity,HttpServletResponse response,HttpServletRequest request){
		CommonUtils.fillCompany(entity);
		String fileName="";
		String amount="放款金额";//或理财金额
		String rate="放款利率";//或理财利率
		if("buttJointReportId".equals(entity.getSearcherId())){
			fileName="对接放款统计";
			String condition = "12,13,14,15";
			entity.setDocking(condition);
			entity.setTransType(SystemConst.TransType.TYPEID1001);
		}else if("companyId".equals(entity.getSearcherId())){
			fileName="公司放款统计";
			entity.setTransType(SystemConst.TransType.TYPEID1001);
			entity.setField3(SystemConst.InvestCustomerNature.COMPANY);
		}else if("peerReportId".equals(entity.getSearcherId())){
			fileName="同行放款统计";
			entity.setTransType(SystemConst.TransType.TYPEID1001);
			entity.setField3(SystemConst.InvestCustomerNature.PEER);
		}else if("organizationReportId".equals(entity.getSearcherId())){
			fileName="机构放款统计";
			entity.setTransType(SystemConst.TransType.TYPEID1001);
			entity.setField3(SystemConst.InvestCustomerNature.ORGANIZATION);
		}else{
			fileName="固定理财统计";
			amount="理财金额";
			entity.setTransType(SystemConst.TransType.TYPEID1007);
		}
		Map<String, String> titles=new LinkedHashMap<String, String>();
		entity.setPage(null);
		List<LoanPayPlanCompany> exportList = reportService.queryReportExport(entity);
		if(exportList!=null&&exportList.size()>0){
			for(LoanPayPlanCompany payPlanCompany:exportList){
				if(SystemConst.TransType.TYPEID1007.equals(entity.getTransType())){
					payPlanCompany.setBorrowerAmount((new BigDecimal(new DecimalFormat("0.00").format(payPlanCompany.getBorrowerAmount()))));
				}else{
					payPlanCompany.setBorrowerAmount((new BigDecimal(new DecimalFormat("0.00").format(payPlanCompany.getLoanAmount()))));
				}
				payPlanCompany.setRepaymentTotal((new BigDecimal(new DecimalFormat("0.00").format(payPlanCompany.getRepaymentTotal()))));
				payPlanCompany.setRate((new BigDecimal(new DecimalFormat("0.000").format(payPlanCompany.getRate()))));
				payPlanCompany.setRepaymentInterest((new BigDecimal(new DecimalFormat("0.000").format(payPlanCompany.getRepaymentInterest()))));
				payPlanCompany.setRepaymentCapital((new BigDecimal(new DecimalFormat("0.00").format(payPlanCompany.getRepaymentCapital()))));
				payPlanCompany.setRateAndUnit(payPlanCompany.getRate()+"%/"+payPlanCompany.getRateUnit());
				payPlanCompany.setPeriodTotalAll(payPlanCompany.getPeriod()+"/"+payPlanCompany.getPeriodTotal());//当前期数
			}
		}
		titles.put("transMainId", "付款ID");
		titles.put("cityName", "省市");
		titles.put("customerName", "出借人");
		titles.put("typeName", "客户性质");
		titles.put("borrowerAmount", amount);
		if("transAccountsId".equals(entity.getSearcherId())){
			titles.put("rateAndUnit", "理财利率");
			titles.put("periodTotalAll", "当前期数");
			titles.put("overTime", "合同截止时间");
		}
		titles.put("lendingTime", "放款时间");
		titles.put("repaymentTotal", "应收总额");
		titles.put("repaymentInterest", "应收利息");
		titles.put("repaymentInterestTime", "应收息时间");
		titles.put("interestTime", "实际收息时间");
		titles.put("repaymentCapital", "应收本金");
		if(!"organizationReportId".equals(entity.getSearcherId())){
			titles.put("repaymentCapitalTime", "应收本时间");
			titles.put("capitalTime", "实际收本时间");
		}
		if(!"transAccountsId".equals(entity.getSearcherId())){
			titles.put("orderNo", "报单编号");
			titles.put("productType", "产品类型");
			titles.put("productName", "产品名称");
			titles.put("periodTotalAll", "当前期数");
			titles.put("field1", "借款利率");
			titles.put("borrower", "借款人姓名");
		}
//		if("organizationReportId".equals(entity.getSearcherId())||"peerReportId".equals(entity.getSearcherId())){
//			titles.put("field3", "预期利息");
//			titles.put("field4", "报单时间");
//		}
		titles.put("department", "部门");
		titles.put("accountManager", "客户经理");
		try{
			AnalyseTransExcel.downLoadExcel(request, response, fileName, exportList, LoanTransAccounts.class, titles);
			System.out.println(fileName+"下载完成");   
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
	}
}

