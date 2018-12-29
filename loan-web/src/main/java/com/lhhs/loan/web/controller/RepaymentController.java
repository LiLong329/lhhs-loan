/**
 * Project Name:loan-web
 * File Name:repaymentController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年12月28日上午10:36:35
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.LoanPayPlanMapper;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.dao.domain.vo.PayPlanVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.PayPlanService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;

/**
 * ClassName:repaymentController <br/>
 * Function: 还款金额统计表(待还款金额)
 * Date:     2017年12月28日 上午10:36:35 <br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/repaymentManager")
public class RepaymentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepaymentController.class);
	
	@Autowired
	private PayPlanService payPlanService;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private UnionCompanyService unionCompanyService;
	@Autowired
	private LoanPayPlanMapper loanPayPlanMapper;
	
	/**
	 * list:还款金额统计表(待还款金额)
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,LoanPayPlan entity){
		 ModelAndView model = new ModelAndView("reportStatistics/repaymentReport");
		 Map<String, Object> param=  new HashMap<String, Object>();
		 //获得当前登录用户
		 CommonUtils.fillCompany(entity);
		 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		 entity.setCompanyId(null);
		 entity.setStatus(SystemConst.Status.STATUS03);
		 Page page = payPlanService.queryListPageReport(entity);//事业部
		 Page personPage = payPlanService.queryListPageReportByPerson(entity);//借款人
		 model.addObject("total", payPlanService.queryReportTotal(entity));
		 model.addObject("personTotal", payPlanService.queryPersonReportTotal(entity));
	     if(null!=personPage){
	    	 List<LoanPayPlan> personList=(List<LoanPayPlan>) personPage.getResultList();
	    	 if(personList!=null&&personList.size()>0){
	    		 for(int i=0;i<personList.size();i++){
	    			 LoanPayPlan LoanPayPlan= personList.get(i);
//	    			 personTotal=personTotal.add(LoanPayPlan.getRepaymentTotal()==null?new BigDecimal("0.00"):LoanPayPlan.getRepaymentTotal());
	    			 //统一隐藏，不区分客户经理，别在改回来了
	    			 if( null!=LoanPayPlan.getCustomerMobile()&&!"".equals(LoanPayPlan.getCustomerMobile())){
	 	    				 LoanPayPlan.setCustomerMobile(LoanPayPlan.getCustomerMobile().substring(0, 3)+"****"+LoanPayPlan.getCustomerMobile().substring(LoanPayPlan.getCustomerMobile().length()-4, LoanPayPlan.getCustomerMobile().length()));
	 	    		}
	    		 }
	    	 }
	     }
		model.addObject("page", page);
		model.addObject("personPage", personPage);
		model.addObject("companys", companyList);
		return model;
	}
	
	/**
	 * list:还款金额统计表(待还款金额)
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList/{type}")
	public ModelAndView ajaxQueryList(HttpServletRequest request,LoanPayPlan entity,@PathVariable("type") String type){
		 Page page=new Page();
		 Page personPage=new Page();
		 ModelAndView model = new ModelAndView();
		 //获得当前登录用户
		 String companyId = entity.getCompanyId();
		 CommonUtils.fillCompany(entity);
		 entity.setCompanyId(companyId);
		 entity.setStatus(SystemConst.Status.STATUS03);
		 if("repaymentPersonId".equals(type)){
			 model= new ModelAndView("reportStatistics/temp/repaymentReportPersonTemp");
			 personPage = payPlanService.queryListPageReportByPerson(entity);//借款人
			 model.addObject("personTotal", payPlanService.queryPersonReportTotal(entity));
		 }else{
			 model = new ModelAndView("reportStatistics/temp/repaymentReportTemp");
			 page = payPlanService.queryListPageReport(entity);
			 model.addObject("total", payPlanService.queryReportTotal(entity));
		 }
	     if(null!=personPage){
	    	 List<LoanPayPlan> personList=(List<LoanPayPlan>) personPage.getResultList();
	    	 if(personList!=null&&personList.size()>0){
	    		 for(int i=0;i<personList.size();i++){
	    			 LoanPayPlan LoanPayPlan= personList.get(i);
	    			//统一隐藏，不区分客户经理，别在改回来了
	    			 if(null!=LoanPayPlan.getCustomerMobile()&&!"".equals(LoanPayPlan.getCustomerMobile())){
	    				 LoanPayPlan.setCustomerMobile(LoanPayPlan.getCustomerMobile().substring(0, 3)+"****"+LoanPayPlan.getCustomerMobile().substring(LoanPayPlan.getCustomerMobile().length()-4, LoanPayPlan.getCustomerMobile().length()));
	    			 }
	    		 }
	    	 }
	     }
		model.addObject("page", page);
		model.addObject("personPage", personPage);
		return model;
	}
	

	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping("/export/{actionType}")
     public void export( HttpServletRequest request,HttpServletResponse response,LoanPayPlan entity,@PathVariable("actionType") String actionType){
    	 String fileName="";
    	 List<PayPlanVo> list=new ArrayList<PayPlanVo>();
    	 SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 formatter.setLenient(false);
    	 Date beginTime=null;
    	 Date endTime=null;
 		 if(null!=entity.getBeginTime()&&!"".equals(entity.getBeginTime())){
			try {
				beginTime = formatter.parse(entity.getBeginTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			 formatter = new SimpleDateFormat("yyyyMMdd");
			 String beginTimeSed =formatter.format(beginTime);
			 fileName+=beginTimeSed;
		}
 		 if(null!=entity.getEndTime()&&!"".equals(entity.getEndTime())){
 			 try {
 				 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				 endTime = formatter.parse(entity.getEndTime());
 			 } catch (ParseException e) {
 				 e.printStackTrace();
 			 }
 			 formatter = new SimpleDateFormat("yyyyMMdd");
 			 String endTimeSed = formatter.format(endTime);
 			 fileName+="-"+endTimeSed;
 		 }
 		 if(null!=entity.getBeginTimeTwo()&&!"".equals(entity.getBeginTimeTwo())){
 			 try {
 				 beginTime = formatter.parse(entity.getBeginTimeTwo());
 			 } catch (ParseException e) {
 				 e.printStackTrace();
 			 }
 			 formatter = new SimpleDateFormat("yyyyMMdd");
 			 String beginTimeSed =formatter.format(beginTime);
 			 fileName+=beginTimeSed;
 		 }
 		 if(null!=entity.getEndTimeTwo()&&!"".equals(entity.getEndTimeTwo())){
 			 try {
 				 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				 endTime = formatter.parse(entity.getEndTimeTwo());
 			 } catch (ParseException e) {
 				 e.printStackTrace();
 			 }
 			 formatter = new SimpleDateFormat("yyyyMMdd");
 			 String endTimeSed = formatter.format(endTime);
 			 fileName+="-"+endTimeSed;
 		 }
 		 String companyId = entity.getCompanyId();
		 CommonUtils.fillCompany(entity);
		 entity.setCompanyId(companyId);
		 entity.setStatus(SystemConst.Status.STATUS03);
		 entity.setPage(null);
    	 if("person".equals(actionType)){
    		 fileName+="借款人";
    		 list = loanPayPlanMapper.queryListPageReportByPerson(entity);//借款人
    		 if(list!=null&&list.size()>0){
	    		 for(int i=0;i<list.size();i++){
	    			 PayPlanVo LoanPayPlan= list.get(i);
	    			//统一隐藏，不区分客户经理，别在改回来了
	    			 if(null!=LoanPayPlan.getCustomerMobile()&&!"".equals(LoanPayPlan.getCustomerMobile())){
	    				 LoanPayPlan.setCustomerMobile(LoanPayPlan.getCustomerMobile().substring(0, 3)+"****"+LoanPayPlan.getCustomerMobile().substring(LoanPayPlan.getCustomerMobile().length()-4, LoanPayPlan.getCustomerMobile().length()));
	    			 }
	    		 }
	    	 }
    	 }else{
    		 fileName+="事业部";
    		 list = loanPayPlanMapper.queryListPageReport(entity);
    	 }
    	 fileName+="还款金额统计报表";
    	 for(int i=0; i<list.size(); i++) {
    		 PayPlanVo ppv=list.get(i);
    		 ppv.setCityName(ppv.getProvienceName()+"-"+ppv.getCityName());
    		 if(null!=ppv.getRepaymentTotal()){
    			 ppv.setRepaymentTotal(ppv.getRepaymentTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
    		 }
		 }
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 CommonUtils.fillCompany(entity);
		 entity.setPage(null);
		 titles.put("cityName", "省市");
  	     if("person".equals(actionType)){
	  		 titles.put("customerName", "借款人");
	  		 titles.put("customerMobile", "借款人手机号");
	  	 }else{
	  		 titles.put("companyName", "分公司");
	  		 titles.put("department", "部门");
//	  		 titles.put("department", "组别");
	  		 titles.put("accountManager", "客户经理");
	  	 }
  	     titles.put("repaymentTotal", "待还款金额(元)");
  	     AnalyseTransExcel.downLoadExcel(request, response, fileName, list, PayPlanVo.class, titles);
  	     System.out.println(fileName+"下载完成"); 
	}
	
	
	
	/**
	 * 部门下的组别
	 * @param request
	 * @param deptId 部门id
	 * @return
	 */
	@RequestMapping("/getDeptOrGroup")
	@ResponseBody
	public Map<String,Object> getDeptOrGroup(HttpServletRequest request,
			@RequestParam(value = "deptId", required = false) Integer deptId){
		Map<String, Object> result=  new HashMap<String, Object>();
		try {
			Map<String, Object> param=  new HashMap<String, Object>();
			if(null!=deptId){
				param.put("deptId", deptId);
				param.put("status", "1");
				List<LoanGroup> groupList = systemManagerService.groupList(param,null);
				result.put("groupList", groupList);
			}
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
		} catch (Exception e) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "系统异常");
		}
		return result;
	}
}

