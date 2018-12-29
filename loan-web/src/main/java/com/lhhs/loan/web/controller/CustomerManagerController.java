/**
 * Project Name:loan-web
 * File Name:CustomerManegerController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月26日下午2:30:09
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanPayPlanCompanyMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.LoanBorrowerInfoVo;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.PayPlanService;
import com.lhhs.loan.service.PaymanyTransService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.TransMainService;
import com.lhhs.loan.service.account.AccountTransactionService;
import com.lhhs.loan.service.impl.CustomerManagerServiceImpl;
import com.lhhs.loan.service.transport.BorrowerTransportService;
import com.lhhs.loan.service.transport.MortgageInfoTransService;
import com.lhhs.loan.service.transport.ProviderTransportService;

/**
 * ClassName:CustomerManegerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月26日 下午2:30:09 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */

@Controller
@RequestMapping("/customerManager")
@SuppressWarnings("all")
public class CustomerManagerController {
	
     private static final Logger logger = LoggerFactory.getLogger(CustomerManagerController.class);
     
     private static final int PAGESIZE = 10;
     
     private Page page = new Page(PAGESIZE);
     
     @Autowired
     private CustomerManagerService customerManagerService;
     @Autowired
 	 private TransMainService transMainService;
     @Autowired
     private PaymanyTransService paymanyTransService;
     @Autowired
     private SystemManagerService systemManager;
     @Autowired
     private ProviderTransportService providerTransportService;
     @Autowired
     private BorrowerTransportService borrowerTransportService;
     @Autowired
     private MortgageInfoTransService mortgageInfoTransService;
     @Autowired
 	 private PayPlanService payPlanService;
     @Autowired
 	 private AccountTransactionService accountTransactionService;
     @Autowired
     private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
 	 @Autowired
 	 private LoanPayPlanCompanyMapper loanPayPlanCompanyMapper;
 	 @Autowired
 	 private LoanTransMainMapper loanTransMainMapper;
 	 @Autowired
 	 private LoanAccountInfoMapper loanAccountInfoMapper;
 	 @Autowired
 	 private LoanAccountCardMapper loanAccountCardMapper;
 	 @Autowired
 	 private LoanCustomerInfoMapper loanCustomerInfoMapper;
 	
     /**
      * 首页>客户管理>经纪人信息
      * loanProviderView:(这里用一句话描述这个方法的作用). <br/>
      *
      * @author Administrator
      * @param request
      * @param response
      * @param province
      * @param city
      * @param providerno
      * @param providermobile
      * @param pageNamuber
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerPerson")
     public ModelAndView providerPerson(
    		 HttpServletRequest request,
    		 HttpServletResponse response,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="providerno",required=false)String providerno,
    		 @RequestParam(name="providername",required=false)String providername,
    		 @RequestParam(name="providermobile",required=false)String providermobile,
    		 @RequestParam(name="pageNumber",required=false)Integer pageNumber
    		 ){
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 ModelAndView model=new ModelAndView("custManagment/providerPerson");
    	 pageNumber=pageNumber==null? 1 : pageNumber;
    	 page.setPageIndex(pageNumber);
    	 paramsMap.put("province", province);
    	 paramsMap.put("city", city);
    	 paramsMap.put("providerno", providerno);
    	 paramsMap.put("providername", providername);
    	 paramsMap.put("providermobile", providermobile);
    	 paramsMap.put("page", page);
    	 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
    	 List<LoanProviderInfo> providerInfo=customerManagerService.loanProviderList(paramsMap, page);
    	 model.addObject("loanEmp",loanEmp);
    	 model.addObject("banks", systemManager.queryAllBank());
    	 model.addObject("providerInfo", providerInfo);
    	 model.addObject("province", province);
    	 model.addObject("city", city);
    	 model.addObject("providerno", providerno);
    	 model.addObject("providername", providername);
    	 model.addObject("providermobile", providermobile);
    	 model.addObject("page", page);
    	 return model;
     }
     /**
      * 首页>客户管理>经纪人信息>查看详情
      * loanProviderInfo:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param providerno
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerPersonDetail")
     public ModelAndView providerPerson(
    		 HttpServletRequest request,
    		 @RequestParam(name="providerno",required=true)String providerno
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/providerPersonDetail");
    	 LoanProviderInfo providerInfo=customerManagerService.selProviderInfo(providerno);
    	 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
    	 model.addObject("loanEmp",loanEmp);
    	 model.addObject("providerInfo", providerInfo);
    	 return model;
     }

     /**
      * 首页>客户管理>经纪人信息>进入修改页面
      * loanProviderInfo:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param providerno
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerPersonModifyView")
     public ModelAndView providerPersonModifyView(
    		 HttpServletRequest request,
    		 @RequestParam(name="providerno",required=true)String providerno
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/providerPersonModify");
    	 LoanProviderInfo providerInfo=customerManagerService.selProviderInfo(providerno);
    	 model.addObject("banks", systemManager.queryAllBank());
    	 model.addObject("providerInfo", providerInfo);
    	 return model;
     }
     /**
      * 首页>客户管理>经纪人信息>修改
      * loanProviderModify:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param loanProviderInfo
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerPersonModify")
     @ResponseBody
     public Map<String, Object> providerPersonModify(
    		 HttpServletRequest request,
    		 @RequestParam(value = "providerInfo") String loanProviderInfo
    		 ){
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 try {
			loanProviderInfo=URLDecoder.decode(loanProviderInfo,"UTF-8");
		    } catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		    }
    	 LoanProviderInfo provider = JSON.parseObject(loanProviderInfo, LoanProviderInfo.class);
    	 
    	 String isSesscuss=providerTransportService.providerSend(provider);//SystemConst.SUCCESS;
    	 if(isSesscuss.equals(SystemConst.SUCCESS)){
    		 int count=customerManagerService.updateProvider(provider);
    		 if(count>=1){
        		 map.put(SystemConst.retCode, SystemConst.SUCCESS);
        		 map.put(SystemConst.retMsg,"\u4fdd\u5b58\u6210\u529f");
        	 }else{
        		 map.put(SystemConst.retCode, SystemConst.FAIL);
        		 map.put(SystemConst.retMsg,"\u4fdd\u5b58\u5931\u8d25");
        	 }
    	 }else{
    		 map.put(SystemConst.retCode, SystemConst.FAIL);
    		 map.put(SystemConst.retMsg,"\u7ecf\u7eaa\u4eba\u4fe1\u606f\u63a8\u9001\u78b0\u78b0\u65fa\u5931\u8d25");
    	 }
    	 return map;
     }
     /**
      * 首页>客户管理>经纪人信息>导出
      * providerInfoExport:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * @author Administrator
      * @since JDK 1.8
      */
     @RequestMapping("/providerExport")
     public void providerExport( HttpServletRequest request,
    		 HttpServletResponse response,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="providerno",required=false)String providerno,
    		 @RequestParam(name="providername",required=false)String providername,
    		 @RequestParam(name="providermobile",required=false)String providermobile,
    		 @RequestParam(name="isPerson",required=false)String isPerson
    		 ){
    	 String fileName="经纪人列表";
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 if(isPerson.equals("00")){//是经纪人(個人)
    		 paramsMap.put("isPerson", "isPerson");
    	 }else{
    		 paramsMap.put("isCompany", "isCompany");
    	 }
    	 paramsMap.put("province", province);
    	 paramsMap.put("city", city);
    	 paramsMap.put("providerno", providerno);
    	 paramsMap.put("providername", providername);
    	 paramsMap.put("providermobile", providermobile);
    	 List<LoanProviderInfo> providerInfo=customerManagerService.loanProviderListExport(paramsMap);
     	 titles.put("cityName", "省市");
  	     titles.put("providerNo", "客户编号");
  	     titles.put("providerName", "经济人姓名");
  	     titles.put("providerSex", "性别");
  	     titles.put("mobileNo", "手机号码");
  	     if(isPerson.equals("00")){
  	    	titles.put("idNo", "身份证号码");
  	     }
  	     titles.put("companyName", "公司名称");
  	     titles.put("registerTime", "注册时间");
  	     AnalyseTransExcel.downLoadExcel(request, response, fileName, providerInfo, LoanProviderInfo.class, titles);
  	    System.out.println(fileName+"下载完成");   
     }
     
     /**********************************经济人企业部分*************************************************/
     @RequestMapping("/providerCompany")
     public ModelAndView loanProviderCompanyView(
    		 HttpServletRequest request,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="providerno",required=false)String providerno,
    		 @RequestParam(name="pageNumber",required=false)Integer pageNumber,
    		 @RequestParam(name="providername",required=false)String providername,
    		 @RequestParam(name="providermobile",required=false)String providermobile
    		 ){
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 ModelAndView model=new ModelAndView("custManagment/providerCompany");
    	 pageNumber=pageNumber==null? 1 : pageNumber;
    	 page.setPageIndex(pageNumber);
    	 paramsMap.put("city", city);
    	 paramsMap.put("province", province);
    	 paramsMap.put("providerno", providerno);
    	 paramsMap.put("providername", providername);
    	 paramsMap.put("providermobile", providermobile);
    	 paramsMap.put("page", page);
    	 List<LoanProviderInfo> providerInfo=customerManagerService.loanProviderCompanyList(paramsMap, page);
    	 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
    	 model.addObject("loanEmp", loanEmp);
    	 model.addObject("providerInfo", providerInfo);
    	 model.addObject("city", city);
    	 model.addObject("province", province);
    	 model.addObject("providerno", providerno);
    	 model.addObject("providername", providername);
    	 model.addObject("providermobile", providermobile);
    	 model.addObject("page", page);
    	 return model;
     }
     /**
      * 首页>客户管理>经纪人信息>查看详情
      * loanProviderInfo:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param providerno
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerCompanyDetail")
     public ModelAndView loanProviderInfoCompany(
    		 HttpServletRequest request,
    		 @RequestParam(name="providerno",required=true)String providerno
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/providerCompanyDetail");
    	 LoanProviderInfo providerInfo=customerManagerService.selProviderInfo(providerno);
    	 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
    	 model.addObject("loanEmp", loanEmp);
    	 model.addObject("providerInfo", providerInfo);
    	 return model;
    	 
     }
    /**
     * 首页>客户管理>经纪人信息>进入修改页面
     * loanProviderInfoModifyView:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @author Administrator
     * @param request
     * @param providerno
     * @return
     * @since JDK 1.8
     */
     @RequestMapping("/providerCompanyModifyView")
     public ModelAndView companyModifyView(
    		 HttpServletRequest request,
    		 @RequestParam(name="providerno",required=true)String providerno
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/providerCompanyModifyView");
    	 LoanProviderInfo providerInfo=customerManagerService.selProviderInfo(providerno);
    	 model.addObject("providerInfo", providerInfo);
    	 return model;
     }
     /**
      * 首页>客户管理>经纪人信息>修改
      * loanProviderModify:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param loanProviderInfo
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/providerCompanyModify")
     @ResponseBody
     public Map<String, Object> loanProviderCompanyModify(
    		 HttpServletRequest request,
    		 @RequestParam(value = "providerInfo") String loanProviderInfo
    		 ){
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 try {
			loanProviderInfo=URLDecoder.decode(loanProviderInfo,"UTF-8");
		    } catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		    }
    	 LoanProviderInfo provider = JSON.parseObject(loanProviderInfo, LoanProviderInfo.class);
    	 String isSesscuss=providerTransportService.providerSend(provider);//SystemConst.SUCCESS;
    	 if(isSesscuss.equals(SystemConst.SUCCESS)){
    		 int count=customerManagerService.updateProvider(provider);
    		 if(count>=1){
        		 map.put(SystemConst.retCode, SystemConst.SUCCESS);
        		 map.put(SystemConst.retMsg,"\u4fdd\u5b58\u6210\u529f");
        	 }else{
        		 map.put(SystemConst.retCode, SystemConst.FAIL);
        		 map.put(SystemConst.retMsg,"\u4fdd\u5b58\u5931\u8d25");
        	 }
    	 }else{
    		 map.put(SystemConst.retCode, SystemConst.FAIL);
    		 map.put(SystemConst.retMsg,"\u7ecf\u7eaa\u4eba\u4fe1\u606f\u63a8\u9001\u78b0\u78b0\u65fa\u5931\u8d25");
    	 }
    	 return map;
     }
     
     /****************************************付款管理*************************************************/
     
     /**
      * 首页>财务管理>付款管理>待付款
      * loanPaymentList:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * @author Administrator
      * @param request
      * @param province
      * @param city
      * @param orderno
      * @param customer
      * @param capitalminTime
      * @param capitalmaxTime
      * @param interestminTime
      * @param interestmaxTime
      * @param pageNamuber
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/loanPaymentList")
     public ModelAndView loanPaymentList(
    		 HttpServletRequest request,
    		 @RequestParam(name="transMainId",required=false)String transMainId,
    		 @RequestParam(name="orderno",required=false)String orderno,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="customername",required=false)String customername,
    		 @RequestParam(name="capitalminTime",required=false)String capitalminTime,//本金时间
    		 @RequestParam(name="capitalmaxTime",required=false)String capitalmaxTime,
    		 @RequestParam(name="interestminTime",required=false)String interestminTime,//利息时间
    		 @RequestParam(name="interestmaxTime",required=false)String interestmaxTime,
    		 @RequestParam(name="pageNumber",required=false)Integer pageNumber
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/awaitingpayment");
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 pageNumber=pageNumber==null? 1 : pageNumber;
    	 page.setPageIndex(pageNumber);
    	 CommonUtils.fillCompanyToMap(paramsMap);
    	 paramsMap.put("transMainId", transMainId);
    	 paramsMap.put("orderNo", orderno);
    	 paramsMap.put("province", province);
    	 paramsMap.put("customerName", customername);
    	 paramsMap.put("status",SystemConst.Status.STATUS03);//有效状态
    	 paramsMap.put("beginTime", capitalminTime);
    	 paramsMap.put("endTime", capitalmaxTime);
    	 paramsMap.put("beginTimeTwo", interestminTime);
    	 paramsMap.put("endTimeTwo",  interestmaxTime);
    	 paramsMap.put("page", page);
    	 List<LoanPayPlanCompany>  payplan=customerManagerService.loanPayPlanComplanyList(paramsMap, page);
    	 model.addObject("transMainId", transMainId);
    	 model.addObject("orderno", orderno);
    	 model.addObject("province", province);
    	 model.addObject("payplan", payplan);
    	 model.addObject("customername", customername);
    	 model.addObject("capitalminTime", capitalminTime);
    	 model.addObject("capitalmaxTime",capitalmaxTime );
    	 model.addObject("interestminTime", interestminTime);
    	 model.addObject("interestmaxTime", interestmaxTime);
    	 model.addObject("map", customerManagerService.queryTotalAmount(paramsMap));
    	 model.addObject("page", page);
    	 return model;
     }
     /**
      * 单条记录付款
      * 弹出付款页面
      * payDetail:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * @author Administrator
      * @param payEntity
      * @return
      * @since JDK 1.8
      */
    @RequestMapping("/payPlanDetail")
 	public ModelAndView payDetail(
 			LoanPayPlanCompany payEntity
 			){
    	ModelAndView mav =null;
   		try {
    	Map map=new HashMap<String,Object>();
 		mav = new ModelAndView("custManagment/_awaitingpayment");
 		LoanTransMain tMain = new LoanTransMain();
 		tMain.setTransMainId(payEntity.getTransMainId());
 		List<LoanPayPlanCompany> payPlan = customerManagerService.queryPayaPlanList(payEntity);
 		tMain=transMainService.get(tMain);
 		mav.addObject("transMain",tMain);
 		if(payPlan != null && payPlan.size() > 0){
 			LoanPayRecordCompanyVo payrecord = new LoanPayRecordCompanyVo();
 			payrecord.setPayFlag("0");
 			payrecord.setActualTransTime(new Date());
 			payrecord.setPlanId(payPlan.get(0).getId());
 			payrecord.setAccountId(payEntity.getAccountId());
 			payrecord.setTransMainId(payPlan.get(0).getTransMainId());
 			LoanPayRecordCompany payRecordCompany =paymanyTransService.isOverdue(payrecord,false);
 			//存入页面
 			mav.addObject("pay", payPlan.get(0));
 			mav.addObject("newDate", new Date());
 			mav.addObject("investAmount",payRecordCompany.getPaidAllTotal());
 			mav.addObject("compensate",payRecordCompany.getPaidCompensate());
 			mav.addObject("paidCompensate", payRecordCompany.getPaidCompensate());
 			mav.addObject("repaymentTotal", payRecordCompany.getRepaymentTotal());
 			mav.addObject("overdueInterestTotal", payRecordCompany.getOverdueInterestTotal());
 			mav.addObject("overdueDays", payRecordCompany.getOverdueDays());
 			
 		}
   		} catch (Exception e) {
   			logger.error(e.getMessage());
   			e.printStackTrace();
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待付款数据有误"));
		}
   		return mav;
 	}
    
    /**
     * 弹出全部付款页面
     * payDetail:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @author Administrator
     * @param payEntity
     * @return
     * @since JDK 1.8
     */
    
    @RequestMapping("/payPlanDetailAll")
   	public ModelAndView payPlanDetailAll(LoanPayPlanCompany payEntity){
    	ModelAndView mav =null;
   		try {
   			mav = new ModelAndView("custManagment/_awaitingpaymentall");
   	   		payEntity.setStatus(SystemConst.Status.STATUS03);
   	   		LoanTransMain tMain = new LoanTransMain();
   	   		tMain.setTransMainId(payEntity.getTransMainId());
   	   		List<LoanPayPlanCompany> payList = customerManagerService.queryPayaPlanList(payEntity);
   	  		tMain=transMainService.get(tMain);
   	   		
   	   		//同步计算违约金
   	   		LoanPayRecordCompanyVo record = new LoanPayRecordCompanyVo();
   	   		record.setPayFlag("1");
   	   		record.setActualTransTime(new Date());
   	   	    record.setAccountId(payEntity.getAccountId());
   	   	    record.setCustomerId(payEntity.getCustomerId());
   	   		record.setTransMainId(payList.get(0).getTransMainId());
   			LoanPayRecordCompany payRecordCompany =paymanyTransService.fineCompute(record);
   			
   			mav.addObject("newDate", new Date());
   			mav.addObject("repaymentInterest", payRecordCompany.getRepaymentInterest());//
   			mav.addObject("repaymentInterest", payRecordCompany.getRepaymentInterest());//
   			mav.addObject("overdueInterestTotal", payRecordCompany.getOverdueInterestTotal());
   			mav.addObject("compensate",payRecordCompany.getPaidCompensate());
   			mav.addObject("repaymentTotal", payRecordCompany.getRepaymentTotal());
   			mav.addObject("compensateDays", payRecordCompany.getCompensateDays());
   			mav.addObject("useDays", payRecordCompany.getUseDays());
   			mav.addObject("startTime", payRecordCompany.getInterestStart());
   			mav.addObject("endTime", payRecordCompany.getInterestEnd());
   			
   			mav.addObject("investAmount", payRecordCompany.getRepaymentCapital());//剩余金额
   	   		mav.addObject("transMain", tMain);
   	   		mav.addObject("paylist",payList);
   	   		mav.addObject("period", payEntity.getPeriod());
   	   		mav.addObject("transMainId", payEntity.getTransMainId());
   	   	    mav.addObject("customerId", payEntity.getCustomerId());
   	   	    mav.addObject("accountId", payEntity.getAccountId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待付款数据有误"));
		}
   		return mav;
   	}
    
    /**
     * 弹出部分还本页面
     * @author suncj
     * @param payEntity
     * @return
     * @since JDK 1.8
     */
    @RequestMapping("/payPlanDetailPortion")
    public ModelAndView payPlanDetailPortion(LoanPayPlanCompany payEntity){
    	ModelAndView mav =null;
    	try {
   			mav = new ModelAndView("custManagment/_awaitingpaymentportion");
   	   		payEntity.setStatus(SystemConst.Status.STATUS03);
   	   		LoanTransMain tMain = new LoanTransMain();
   	   		tMain.setTransMainId(payEntity.getTransMainId());
   	   		List<LoanPayPlanCompany> payList = customerManagerService.queryPayaPlanList(payEntity);//付款计划列表查询
   	   		
   	  		tMain=transMainService.get(tMain);
   	  		LoanPayPlanCompany prePay=payList.get(0);
   	  		if(prePay!=null&&prePay.getCapitalTime()!=null){
   	  			prePay.setInterestStart(DateUtils.Date2String(prePay.getCapitalTime()));
   	  		}
   			mav.addObject("investAmount", payList.get(payList.size()-1).getRepaymentCapital());//已收总额
   	   		mav.addObject("transMain", tMain);
   	   		mav.addObject("period", payEntity.getPeriod());
   	   		mav.addObject("transMainId", payEntity.getTransMainId());
   	   	    mav.addObject("customerId", payEntity.getCustomerId());
   	   	    mav.addObject("accountId", payEntity.getAccountId());
   	   	    mav.addObject("payList", payList);
   	   	    mav.addObject("advanceAmount", new BigDecimal(0));
   	   	    mav.addObject("actualTransTime",prePay.getInterestStart());
   	   	    mav.addObject("prePay", prePay);
   	   	
		} catch (Exception e) {
    		logger.error(e.getMessage());
    		e.printStackTrace();
    		mav = new ModelAndView("alertError");
    		mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待付款数据有误"));
    	}
    	return mav;
    }
    
	/**
	 * getPayPortion: 待付款计划-部分还本预期计算(重新生成付款计划)
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/getPayPortion")
	public ModelAndView getPayPortion(LoanPayRecordCompanyVo payEntity){
		ModelAndView mav = new ModelAndView("custManagment/_awaitingpaymentportion");
		Map<String,Object> resuleMap = new HashMap<String,Object>();
		try{
			resuleMap = paymanyTransService.getPayPortion(payEntity);
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待还款数据有误"));
			
		}
		List<LoanPayPlanCompany> payList=(List<LoanPayPlanCompany>) resuleMap.get("list");
		mav.addObject(SystemConst.retCode, resuleMap.get(SystemConst.retCode));
		mav.addObject(SystemConst.retMsg, resuleMap.get(SystemConst.retMsg));
		
		LoanTransMain tMain = new LoanTransMain();
   		tMain.setTransMainId(payEntity.getTransMainId());
  		tMain=transMainService.get(tMain);
		mav.addObject("investAmount",payEntity.getInvestAmount());//剩余本金
   		mav.addObject("transMain", tMain);
		mav.addObject("payList", payList);
		mav.addObject("period", payEntity.getPeriod());
   		mav.addObject("transMainId", payEntity.getTransMainId());
   	    mav.addObject("customerId", payEntity.getCustomerId());
   	    mav.addObject("accountId", payEntity.getAccountId());
   	    mav.addObject("advanceAmount", payEntity.getAdvanceAmount());
   	    mav.addObject("actualTransTime", payEntity.getActualTransTime());
   	    mav.addObject("transRemark", payEntity.getTransRemark());
   	    mav.addObject("prePay", resuleMap.get("prePay"));
		return mav;
	
	}
	
    
    /**
     * 付款逾期利息计算Or全部付款违约金计算
     * amountCheck:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     * @author Administrator
     * @param payEntity
     * @return
     * @since JDK 1.8
     */
    @ResponseBody
    @RequestMapping("/amountCheck")
    public Map<String, Object> amountCheck(LoanPayRecordCompanyVo payEntity){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	LoanPayRecordCompany payRecordCompany = null;
		if(payEntity == null){
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u8bf7\u8f93\u5165\u6b63\u786e\u503c");
			return resultMap;
		}
		try{
			if("0".equals(payEntity.getPayFlag())){//单条付款
				payRecordCompany =paymanyTransService.isOverdue(payEntity, false);
			}
			if("1".equals(payEntity.getPayFlag())){//全部付款
				payRecordCompany = paymanyTransService.fineCompute(payEntity);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, e.getMessage());
			return resultMap;
		}
		resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
		resultMap.put("pay", payRecordCompany);
    	return resultMap;
    } 
     
    
    /**
	 * payHandler:执行付款<br/>
	 *
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/executeToPay")
	public Map<String,Object> executeToPay(LoanPayRecordCompanyVo payEntity){
		Map<String,Object> resuleMap = new HashMap<String,Object>();
		try{
			if("0".equals(payEntity.getPayFlag())){//单调付款
				resuleMap = paymanyTransService.executePayService(payEntity);
			}else{//全部付款
				resuleMap = paymanyTransService.executePayAllService(payEntity);
			}
		}catch(Exception e){
			logger.error(e.toString());
			e.printStackTrace();
			resuleMap.put(SystemConst.retCode, SystemConst.FAIL);
			resuleMap.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "付款失败"));
		}
		return resuleMap;
	}
	
	/**
	 * executeToPayPortion:执行部分付款(还本)<br/>
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/executeToPayPortion")
	public Map<String,Object> executeToPayPortion(LoanPayRecordCompanyVo payEntity){
		Map<String,Object> resuleMap = new HashMap<String,Object>();
		try{
			resuleMap = paymanyTransService.executePayPortion(payEntity);
		}catch(Exception e){
			logger.error(e.toString());
			e.printStackTrace();
			resuleMap.put(SystemConst.retCode, SystemConst.FAIL);
			resuleMap.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "部分还本失败"));
		}
		return resuleMap;
	}
	
     /**
      * 首页>财务管理>付款管理>待付款-导出
      * loanPaymentExport:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * @since JDK 1.8
      */
     @RequestMapping("/loanPaymentExport")
     public void loanPaymentExport(
    		 HttpServletRequest request,
    		 HttpServletResponse response,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="orderno",required=false)String orderno,
    		 @RequestParam(name="customer",required=false)String customer,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="capitalminTime",required=false)String capitalminTime,//本金时间
    		 @RequestParam(name="capitalmaxTime",required=false)String capitalmaxTime,
    		 @RequestParam(name="interestminTime",required=false)String interestminTime,//利息时间
    		 @RequestParam(name="interestmaxTime",required=false)String interestmaxTime
    		 ){
    	 String fileName="待付款计划";
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 CommonUtils.fillCompanyToMap(paramsMap);
    	 paramsMap.put("city", city);
    	 paramsMap.put("orderNo", orderno);
    	 paramsMap.put("customerName", customer);
    	 paramsMap.put("status",SystemConst.Status.STATUS03);
    	 paramsMap.put("capitalminTime", capitalminTime);
    	 paramsMap.put("capitalmaxTime", capitalmaxTime);
    	 paramsMap.put("interestminTime", interestminTime);
    	 paramsMap.put("interestmaxTime", interestmaxTime);
    	 List<LoanPayPlanCompany>  payplanCompany=customerManagerService.loanPayPlanComplanyExport(paramsMap);
    	 titles.put("id", "付款ID");
    	 titles.put("orderNo", "报单编号");
 	     titles.put("provienceName", "省");
 	     titles.put("cityName", "市");
 	     titles.put("productType", "产品类型");
 	     titles.put("productName", "产品名称");
 	     titles.put("customerName", "出借人姓名");
 	     titles.put("periodTotalAll", "当期/总期数");
 	     titles.put("repaymentTotal", "应收总额(元)");
 	     titles.put("repaymentCapital", "应收本金");
 	    titles.put("paidCapital", "已收本金");
 	     titles.put("repaymentCapitalTime", "应收本金时间");
 	     titles.put("repaymentInterest", "应收利息");
 	    titles.put("paidInterest", "已收利息");
 	     titles.put("repaymentInterestTime", "应收利息时间");
 	     titles.put("overdueDays", "逾期天数");
 	     titles.put("overdueInterestTotal", "应收贴息");
 	    AnalyseTransExcel.downLoadExcel(request, response, fileName, payplanCompany, LoanPayPlanCompany.class, titles);
 	    System.out.println(fileName+"下载完成");   
    	 
     }
     /*****************************************垫付********************************************************/
     
     /**
      * 垫付列表
      * capitalPrepaidList:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      * @author Administrator
      * @param entity
      * @return
      * @since JDK 1.8
      */
     
     @RequestMapping("/capitalPrepaidList")
     public ModelAndView capitalPrepaidList(
    		 LoanPayPlan entity
    		 ){
    	 ModelAndView model=new ModelAndView("capotalPrepaid/awaitcapitalprepaid");
    	 //LoanEmp emp=(LoanEmp) SecurityUserHolder.getCurrentUser();
    	 CommonUtils.fillCompany(entity);
    	 entity.setStatus(SystemConst.Status.STATUS03);
    	 entity.setAdvanceFlag(SystemConst.IsFlag.YES);
    	 Page page = customerManagerService.capitalPrepaidList(entity);
 		 model.addObject("page", page);
 		 model.addObject("map", customerManagerService.queryTotalAmount(entity));
 		 return model;
     }
     
     /**
  	 * ajaxQueryList:
  	 * 还款计划列表 异步搜索查询及分页查询<br/>
  	 *
  	 * @param entity
  	 * @return
  	 * @since JDK 1.8
  	 */
  	@RequestMapping("/ajaxQueryList")
  	public ModelAndView ajaxQueryList(LoanPayPlan entity){
  		ModelAndView mav = new ModelAndView("capotalPrepaid/_advancemoney");
  		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
  		CommonUtils.fillCompany(entity);
  		entity.setStatus(SystemConst.Status.STATUS03);
  		mav.addObject("page", customerManagerService.capitalPrepaidList(entity));
  		mav.addObject("map", customerManagerService.queryTotalAmount(entity));
  		return mav;
  	}
     
  	/**
     * 弹出垫付页面
     * payDetail:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @author Administrator
     * @param payEntity
     * @return
     * @since JDK 1.8
     */
   @RequestMapping("/capitalPrepaidView")
	public ModelAndView capitalPrepaidView(
			LoanPayPlan payEntity
			){
   	    ModelAndView mav =null;
  		try {
   	    Map map=new HashMap<String,Object>();
		mav = new ModelAndView("capotalPrepaid/_capitalprepaid");
		LoanTransMain tMain = new LoanTransMain();
		tMain.setTransMainId(payEntity.getTransMainId());
		//List<LoanPayPlan> payPlan = payPlanService.queryList(payEntity);
		
		LoanPayPlan pay=payPlanService.get(payEntity.getId().toString());
		pay.setRepaymentCapital(pay.getRepaymentCapital().subtract(pay.getPaidCapital()));
		pay.setRepaymentInterest(pay.getRepaymentInterest().subtract(pay.getPaidInterest()));
		pay.setRepaymentTotal(pay.getRepaymentCapital().add(pay.getRepaymentInterest()));
		tMain=transMainService.get(tMain);
		mav.addObject("transMain",tMain);
		mav.addObject("pay",pay);
		mav.addObject("newDate", new Date());
			
  		} catch (Exception e) {
  			logger.error(e.getMessage());
  			e.printStackTrace();
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待付款数据有误"));
		}
  		return mav;
	}
   
     /**
      * 执行垫付
      * executeCapotalPrepaid:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author Administrator
      * @param entity
      * @return
      * @since JDK 1.8
      */
 	@RequestMapping("/executeCapotalPrepaid")
 	@ResponseBody
 	public Map<String, Object> executeCapotalPrepaid(LoanAdvance entity){
 		Map<String, Object> map=new HashMap<String,Object>();
 		if(entity==null||entity.getPlanId()==null||entity.getTransMainId()==null||
 				entity.getAdvanceTime()==null||entity.getAdvanceTotal()==null){
 			map.put(SystemConst.retCode, SystemConst.FAIL);
 			map.put(SystemConst.retMsg, "垫付数据错误！");
 			return map;
 		}
 		 //垫付利息 控制成使用
 	    String isInterest=entity.getIsInterest();
 	    //垫付本金
 	    String isCapita=entity.getIsCapita();
 	    if(!SystemConst.IsFlag.YES.equals(isInterest)){
 	    	entity.setAdvanceInterest(null);
 	    }
 	   if(!SystemConst.IsFlag.YES.equals(isCapita)){
	    	entity.setAdvanceCapital(null);;
	    }
 		try {
 			String returnMsg=accountTransactionService.LoanAdvanceTrans(entity);//请求垫付接口
 			map.put(SystemConst.retCode, returnMsg);
 			map.put(SystemConst.retMsg, "垫付成功");
		} catch (Exception e) {
			logger.error("执行垫付失败"+e.getMessage());
			e.printStackTrace();
			map.put(SystemConst.retCode, SystemConst.FAIL);
 			map.put(SystemConst.retMsg, "执行垫付失败:"+e.getMessage());
 			return map;
		}
 		return map;
 	}
 	
 	
 	/**
 	 * 已垫付记录
 	 * capitalPrepaidList:(这里用一句话描述这个方法的作用). <br/>
 	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
 	 * @author Administrator
 	 * @param entity
 	 * @return
 	 * @since JDK 1.8
 	 */
 	@RequestMapping("/paidFinishList")
    public ModelAndView paidFinishList(
   		 LoanAdvance advance
   		 ){
   	 ModelAndView model=new ModelAndView("capotalPrepaid/paidamountduelist");
   	 //LoanEmp emp=(LoanEmp) SecurityUserHolder.getCurrentUser();
   	 CommonUtils.fillCompany(advance);
   	 //advance.setStatus(SystemConst.Status.STATUS03);
   	 Page page = customerManagerService.capitalPrepaidList(advance);
	 model.addObject("page", page);
	 model.addObject("map", customerManagerService.queryTotalAmountSum(advance));
	 return model;
    }
    
    /**
	 * ajaxQueryList:
	 * 还款计划列表 异步搜索查询及分页查询<br/>
	 *
	 * @author cuitao
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryAmountDueList")
	public ModelAndView ajaxQueryAmountDueList(LoanAdvance advance){
		ModelAndView mav = new ModelAndView("capotalPrepaid/_paidamountdue");
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		//TODO 查询条件要完善
		CommonUtils.fillCompany(advance);
//		advance.setStatus(SystemConst.Status.STATUS03);
		mav.addObject("page", customerManagerService.capitalPrepaidList(advance));
		mav.addObject("map", customerManagerService.queryTotalAmountSum(advance));
		return mav;
	}
     
	/**
 	 * 债务偿还记录
 	 * capitalPrepaidList:(这里用一句话描述这个方法的作用). <br/>
 	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
 	 * @author Administrator
 	 * @param entity
 	 * @return
 	 * @since JDK 1.8
 	 */
 	@RequestMapping("/repayAmountDue")
    public ModelAndView repayAmountDue(
    		LoanAdvanceRecord record
   		 ){
   	 ModelAndView model=new ModelAndView("capotalPrepaid/amountduerepay");
     LoanEmp emp=CommonUtils.getEmpFromSession();
   	 CommonUtils.fillCompany(record);
   	 record.setStatus(SystemConst.Status.STATUS03);
   	 Page page = customerManagerService.queryAdvanceRecord(record);
	 model.addObject("page", page);
	 LoanAdvanceRecord total =customerManagerService.queryTotalAmountSum(record);
	 if(total==null||total.getAdvanceTotal()==null){
		 total=new LoanAdvanceRecord();
		 total.setAdvanceTotal(new BigDecimal(0.00));
		 total.setPaidTotal(new BigDecimal(0.00));
	 }
	 model.addObject("map", total);
	 return model;
    }
    
    /**
	 * ajaxQueryList:
	 * 还款计划列表 异步搜索查询及分页查询<br/>
	 *
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxRepayAmountDue")
	public ModelAndView ajaxRepayAmountDue(LoanAdvanceRecord record){
		ModelAndView mav = new ModelAndView("capotalPrepaid/_amountduerepay");
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		//TODO 查询条件要完善
		CommonUtils.fillCompany(record);
		record.setStatus(SystemConst.Status.STATUS03);
		mav.addObject("page", customerManagerService.queryAdvanceRecord(record));
		mav.addObject("map", customerManagerService.queryTotalAmountSum(record));
		return mav;
	}
     
 /*********************************已付款记录*************************************************/    
     /**
      * 首页>财务管理>付款管理>已付款
      * loanPayRecordCompanyList:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param province
      * @param city
      * @param orderno
      * @param customer
      * @param capitalminTime
      * @param capitalmaxTime
      * @param interestminTime
      * @param interestmaxTime
      * @param pageNamuber
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/loanPayRecord")
     public ModelAndView loanPayRecordCompanyList(
    		 HttpServletRequest request,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="orderno",required=false)String orderno,
    		 @RequestParam(name="customername",required=false)String customername,
    		 @RequestParam(name="capitalminTime",required=false)String capitalminTime,//本金时间
    		 @RequestParam(name="capitalmaxTime",required=false)String capitalmaxTime,
    		 @RequestParam(name="interestminTime",required=false)String interestminTime,//利息时间
    		 @RequestParam(name="interestmaxTime",required=false)String interestmaxTime,
    		 @RequestParam(name="pageNumber",required=false)Integer pageNumber 
    		 ){
    	 ModelAndView model=new ModelAndView("custManagment/awaitingpaidpayment");
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 pageNumber=pageNumber==null? 1 : pageNumber;
    	 page.setPageIndex(pageNumber);
    	 CommonUtils.fillCompanyToMap(paramsMap);
    	 paramsMap.put("province", province);
    	 paramsMap.put("city", city);
    	 paramsMap.put("orderNo", orderno);
    	 paramsMap.put("customerName", customername);
    	 paramsMap.put("capitalminTime", capitalminTime);
    	 paramsMap.put("capitalmaxTime", capitalmaxTime);
    	 paramsMap.put("interestminTime", interestminTime);
    	 paramsMap.put("interestmaxTime", interestmaxTime);
    	 paramsMap.put("page", page);
    	 List<LoanPayRecordCompany>  payRecord=customerManagerService.loanPayRecordComplanyList(paramsMap, page);
    	 model.addObject("map", customerManagerService.queryPaidTotal(paramsMap));
    	 model.addObject("payRecord", payRecord);
    	 model.addObject("province", province);
    	 model.addObject("city", city);
    	 model.addObject("orderno", orderno);
    	 model.addObject("customername", customername);
    	 model.addObject("capitalminTime", capitalminTime);
    	 model.addObject("capitalmaxTime", capitalmaxTime);
    	 model.addObject("interestminTime",interestminTime);
    	 model.addObject("interestmaxTime",interestmaxTime);
    	 model.addObject("page", page);
    	 return model;
     }
     
     /**
      * 首页>财务管理>付款管理>已付款-导出
      * loanPaymentExport:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * @author Administrator
      * @param request
      * @param response
      * @param province
      * @param city
      * @param orderno
      * @param customer
      * @param capitalminTime
      * @param capitalmaxTime
      * @param interestminTime
      * @param interestmaxTime
      * @since JDK 1.8
      */
     @RequestMapping("/loanPayRecordExport")
     @ResponseBody
     public void loanPayRecordCompanyExport(
    		 HttpServletRequest request,
    		 HttpServletResponse response,
    		 @RequestParam(name="province",required=false)String province,
    		 @RequestParam(name="city",required=false)String city,
    		 @RequestParam(name="orderno",required=false)String orderno,
    		 @RequestParam(name="customername",required=false)String customername,
    		 @RequestParam(name="capitalminTime",required=false)String capitalminTime,//本金时间
    		 @RequestParam(name="capitalmaxTime",required=false)String capitalmaxTime,
    		 @RequestParam(name="interestminTime",required=false)String interestminTime,//利息时间
    		 @RequestParam(name="interestmaxTime",required=false)String interestmaxTime
    		 ){
    	 String fileName="已付款记录";
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 Map<String, Object> paramsMap=new HashMap<String,Object>();
    	 CommonUtils.fillCompanyToMap(paramsMap);
    	 paramsMap.put("city", city);
    	 paramsMap.put("orderNo", orderno);
    	 paramsMap.put("customerName", customername);
    	 paramsMap.put("capitalminTime", capitalminTime);
    	 paramsMap.put("capitalmaxTime", capitalmaxTime);
    	 paramsMap.put("interestminTime", interestminTime);
    	 paramsMap.put("interestmaxTime", interestmaxTime);
    	 List<LoanPayRecordCompany>  loanPayRecordCompany=customerManagerService.loanPayRecordComplanyExport(paramsMap);
    	 titles.put("orderNo", "报单编号");
 	     titles.put("cityName", "省市");
 	     titles.put("productType", "产品类型");
 	     titles.put("productName", "产品名称");
 	     titles.put("customerName", "出借人姓名");
 	     titles.put("periodTotalAll", "当期/总期数");
 	     titles.put("repaymentTotal", "应收总额(元)");
 	     titles.put("repaymentCapital", "应收本金");
 	     titles.put("paidCapital", "实收本金");
 	     titles.put("repaymentCapitalTime", "应收本金时间");
 	     titles.put("capitalTime", "实收本金时间");
 	     titles.put("repaymentInterest", "应收利息");
 	     titles.put("paidInterest", "实收利息");
 	     titles.put("repaymentInterestTime", "应收利息时间");
 	     titles.put("interestTime", "实收利息时间");
 	     titles.put("overdueDays", "逾期天数");
 	     titles.put("paidOverdue", "实收贴息");
 	     titles.put("paidCompensate", "实收违约赔偿金");
 	    AnalyseTransExcel.downLoadExcel(request, response, fileName, loanPayRecordCompany, LoanPayRecordCompany.class, titles);
 	    System.out.println(fileName+"下载完成");   
    	 
     }
     
     /*********************************已结清记录*************************************************/       
     /**
      * 首页>财务管理>付款管理>已结清-列表
      * settleList:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param province
      * @param city
      * @param orderno
      * @param customer
      * @param capitalminTime
      * @param capitalmaxTime
      * @param pageNamuber
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/querySettleList")
     public ModelAndView querySettleList(
    	 HttpServletRequest request,
		 @RequestParam(name="province",required=false)String province,
		 @RequestParam(name="city",required=false)String city,
		 @RequestParam(name="orderno",required=false)String orderno,
		 @RequestParam(name="customer",required=false)String customer,
		 @RequestParam(name="beginTime",required=false)String beginTime,//放款时间
		 @RequestParam(name="endTime",required=false)String endTime,
		 @RequestParam(name="pageNumber",required=false)Integer pageNumber
		 ){
	 ModelAndView model=new ModelAndView("custManagment/alreadysettled");
	 Map<String, Object> paramsMap=new HashMap<String,Object>();
	 pageNumber=pageNumber==null? 1 : pageNumber;
	 page.setPageIndex(pageNumber);
	 CommonUtils.fillCompanyToMap(paramsMap);
	 paramsMap.put("cityNo", city);
	 paramsMap.put("businessId", orderno);
	 paramsMap.put("borrower", customer);
	 paramsMap.put("beginTimeTwo", beginTime);
	 paramsMap.put("endTimeTwo", endTime);
	 paramsMap.put("page", page);
	 List<LoanTransMain>  payRecord=customerManagerService.querySettleList(paramsMap, page);
	 Map<String, Object> paidTotal=customerManagerService.querySettleListSum(paramsMap);
	 model.addObject("payRecord", payRecord);
	 model.addObject("province", province);
	 model.addObject("city", city);
	 model.addObject("orderno", orderno);
	 model.addObject("customer", customer);
	 model.addObject("beginTime", beginTime);
	 model.addObject("endTime",endTime );
	 //paidTotal.get("totalAmount")==null?
	 model.addObject("paidTotal",0);
	 model.addObject("page", page);
	 return model;
     }
    
     /**
      * 已结清列表-详情
      * settleChildList:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      * @author Administrator
      * @param request
      * @param orderno
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/settleDetailList")
     public ModelAndView settleDetailList(
    		 HttpServletRequest request,
    		 @RequestParam(name="orderNo",required=true)String orderNo
    	 ){
    	 ModelAndView model=new ModelAndView("custManagment/alreadysettleddetail");
    	 List<LoanPayRecordCompany> loanPayRecord=customerManagerService.querySettleDetail(orderNo);
    	 model.addObject("loanPayRecord", loanPayRecord);
    	 return model;
     }
     
     /**
      * 首页>财务管理>付款管理>已结清-导出
      * settleListExport:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      *
      * @author Administrator
      * @param request
      * @param response
      * @param province
      * @param city
      * @param orderno
      * @param customer
      * @param capitalminTime
      * @param capitalmaxTime
      * @since JDK 1.8
      */
     @RequestMapping("settleListExport")
     @ResponseBody
     public void settleListExport(
    	 HttpServletRequest request,
    	 HttpServletResponse response,
		 @RequestParam(name="province",required=false)String province,
		 @RequestParam(name="city",required=false)String city,
		 @RequestParam(name="orderno",required=false)String orderno,
		 @RequestParam(name="customer",required=false)String customer,
		 @RequestParam(name="beginTime",required=false)String beginTime,//放款时间
		 @RequestParam(name="endTime",required=false)String endTime
		 ){
     String fileName="已结清记录";
     Map<String, String> titles=new LinkedHashMap<String, String>();
	 Map<String, Object> paramsMap=new HashMap<String,Object>();
	 CommonUtils.fillCompanyToMap(paramsMap);
	 paramsMap.put("cityNo", city);
	 paramsMap.put("businessId", orderno);
	 paramsMap.put("borrower", customer);
	 paramsMap.put("beginTimeTwo", beginTime);
	 paramsMap.put("endTimeTwo", endTime);
	 List<LoanTransMain>  payRecordExport=customerManagerService.querySettleListExport(paramsMap);
	 titles.put("businessId", "报单编号");
	 titles.put("cityName", "省市");
	 titles.put("productType", "产品类型");
	 titles.put("productName", "产品名称");
	 titles.put("borrower", "出借人姓名");
	 titles.put("amount", "放款金额");
	 titles.put("rateAndUnit", "放款利率");
	 titles.put("repaymentMethod", "付款方式");
	 titles.put("interestWay", "收息方式");
	 titles.put("periodTotalAll", "实收/总期数");
	 titles.put("gatheringTotal", "实收总额");
	 titles.put("paidCapitalAmount", "实收本金");
	 titles.put("paidInterestAmount","实收利息");
	 titles.put("lendingTime", "放款时间");
	 AnalyseTransExcel.downLoadExcel(request, response, fileName, payRecordExport, LoanPayRecordCompany.class, titles);
	 System.out.println(fileName+"下载完成");   
     }
     
     
     /**************************************** 借款人信息管理 START ***********************************************/
     /**
      * 
      * borrowerList:借款人信息列表(个人)展示
      * @author kernel.org
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/borrowerInfoList")
     public String borrowerInfoList(HttpServletRequest request,LoanBorrowerInfoVo entity){
    	 Page page = customerManagerService.queryListPage(entity);
    	 List<LoanDictionary> custNatureList = customerManagerService.selectCustNatureByCategory();
    	 request.setAttribute("page", page);
    	 request.setAttribute("custNatureList", custNatureList);
    	 return "customerManage/borrowerInfoPersonal";
     }
     
     /**
      * 
      * borrowerList:异步查询(根据条件选择)借款人信息列表(个人)展示
      * @author kernel.org
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/asynBorrowerInfoList")
     public String asynBorrowerInfoList(HttpServletRequest request, 
    		 							LoanBorrowerInfoVo entity){
    	 Page page = customerManagerService.queryListPage(entity);
    	 List<LoanDictionary> custNatureList = customerManagerService.selectCustNatureByCategory();
    	 request.setAttribute("page", page);
    	 request.setAttribute("custNatureList", custNatureList);
    	 return "customerManage/_borrowerInfoPersonalSub";
     }
     
     /**
      * 
      * borrowerList:借款人信息导出到excel
      * @author kernel.org
      * @return
      * @since JDK 1.8
      */
 	@RequestMapping("/exportBorrowerInfoExcel")
 	public void exportBorrowerInfoExcel(HttpServletRequest request,
 										HttpServletResponse response,
 										LoanBorrowerInfoVo entity){
 		Page page = null;
 		// 导出所有的客户信息管理记录列表
 		if(entity.getCustomerNature() == "" && entity.getBname() == "" && entity.getMobile() == "" && entity.getCustomerId() == ""){
 			page = customerManagerService.queryListPage(entity);
 		// 导出所有的符合条件的客户信息管理查询记录列表
 		}else{
 			page = customerManagerService.queryListPage(entity);
 		}
 		
 		if(null != page){
 			String filename ="客户信息管理名单";
 			List<LoanBorrowerInfoVo> borrowerInfoList = new ArrayList<LoanBorrowerInfoVo>();
 			List<LoanBorrowerInfoVo> borrowerInfo = (List<LoanBorrowerInfoVo>) page.getResultList();
 			for (LoanBorrowerInfoVo borrower : borrowerInfo) {
 				
 				String sex = borrower.getSex();
 				sex = (!StrUtils.isNullOrEmpty(sex)) && "1".equals("sex") ? "男" : "女";
 				borrower.setSex(sex);
 				borrowerInfoList.add(borrower);
 			}
 			
 			Map<String,String>  titles = new LinkedHashMap<String,String>();
 			titles.put("customerNature", "客户性质");
 			titles.put("customerId", "客户编号");
 			titles.put("bname", "客户姓名");
 			titles.put("sex", "客户性别");
 			
 			titles.put("idNum", "身份证号");
 			titles.put("mobile", "手机号");
 			titles.put("creditStatus", "征信状况");
 			
 			AnalyseTransExcel.downLoadExcel(request, response, filename, borrowerInfoList, LoanBorrowerInfoVo.class, titles);
 		}
 	 }
     
     /**
      * borrowerList:借款人信息列表(个人)查看
      * @author kernel.org
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/selectBorrowerInfoDetial/{customerId}")
     public String selectBorrowerInfoDetial(HttpServletRequest request,
    		 						  		@PathVariable String customerId){
    	 LoanBorrowerInfoVo borrowerInfo = customerManagerService.selectBorrowerBasicInfo(customerId);
    	 List<LoanAccountCard> bankCarkList = customerManagerService.selectbankCarkList(customerId);
    	 LoanEmp leEmp=CommonUtils.getEmpFromSession();
    	//如果当前登录人不是客户经理 进行脱敏操作
 		if(null!=borrowerInfo && !leEmp.getLeEmpId().toString().equals(borrowerInfo.getManagerId())){
 			if(!StrUtils.isNullOrEmpty(borrowerInfo.getMobile())){
 				borrowerInfo.setMobile(borrowerInfo.getMobile().substring(0, 3)+"****"+borrowerInfo.getMobile().substring(borrowerInfo.getMobile().length()-4, borrowerInfo.getMobile().length()));
 			}
 			if(!StrUtils.isNullOrEmpty(borrowerInfo.getIdNum())){
 				borrowerInfo.setIdNum(borrowerInfo.getIdNum().substring(0, 1)+"****"+borrowerInfo.getIdNum().substring(borrowerInfo.getIdNum().length()-1, borrowerInfo.getIdNum().length()));
 			}
// 			if(StringUtils.isNotEmpty(borrowerInfo.getBankCardNo())){
// 				borrowerInfo.setBankCardNo(borrowerInfo.getBankCardNo().substring(0,1)+"****"+borrowerInfo.getBankCardNo().substring(borrowerInfo.getBankCardNo().length()-1, borrowerInfo.getBankCardNo().length()));
// 			}
 		}
 		 if(null!=bankCarkList&&bankCarkList.size()>0){
 			 for(LoanAccountCard bankCark:bankCarkList){
 				if(null!=bankCark&&!StrUtils.isNullOrEmpty(bankCark.getBankCardNo())){
 					bankCark.setBankCardNo(bankCark.getBankCardNo().substring(0,1)+"****"+bankCark.getBankCardNo().substring(bankCark.getBankCardNo().length()-1, bankCark.getBankCardNo().length()));
 	 			}
 			 }
 		 }
    	 List<LoanCarInfo> carInfoList = customerManagerService.selectCarInfoList(customerId);
    	 List<LoanHouseInfo> houseInfoList = customerManagerService.selectHouseInfoList(customerId);
    	 request.setAttribute("borrowerInfo", borrowerInfo);
    	 request.setAttribute("carInfoList", carInfoList);
    	 request.setAttribute("houseInfoList", houseInfoList);
    	 request.setAttribute("bankCarkList", bankCarkList);
    	 return "customerManage/borrowerInfoPersonalView";
     }
     
     /**
      * borrowerList:去借款人信息(个人)修改页面
      * @author kernel.org
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/toModifyBorrowerInfoDetial/{customerId}")
     public String toModifyBorrowerInfoDetial(HttpServletRequest request,
    		 						  		@PathVariable String customerId){
    	 //获取当前登录人 如果登录人不为空且公司id 集团id 不为空 且 集团id！=公司id情况下
    	 SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
    	 LoanEmp leEmp=CommonUtils.getEmpFromSession();
		 Map<String, Object> paramsMap=new HashMap<String,Object>();
 		 CommonUtils.fillCompanyToMap(paramsMap);
    	 LoanBorrowerInfoVo borrowerInfo = customerManagerService.selectBorrowerBasicInfo(customerId);
    	 List<LoanCarInfo> carInfoList = customerManagerService.selectCarInfoList(customerId);
    	 List<LoanHouseInfo> houseInfoList = customerManagerService.selectHouseInfoList(customerId);
    	 List<LoanDictionary> custNatureList = customerManagerService.selectCustNatureByCategory();
    	 List<LoanAccountCard> bankCarkList = customerManagerService.selectbankCarkList(customerId);
    	 request.setAttribute("borrowerInfo", borrowerInfo);
    	 request.setAttribute("carInfoList", carInfoList);
    	 request.setAttribute("houseInfoList", houseInfoList);
    	 request.setAttribute("custNatureList", custNatureList);
    	 request.setAttribute("bankCarkList", bankCarkList==null||(bankCarkList.size()==1&&bankCarkList.contains(null))? "" : bankCarkList);
    	 request.setAttribute("banks", systemManager.queryAllBank());
    	 request.setAttribute("allManager", systemManager.queryCustManagerBySaas(paramsMap));//查询客户经理
    	 return "customerManage/borrowerInfoPersonalModify";
     }
     
     /**
      * 
      * updateBasicInfo:保存借款人基本信息
      * @author kernel.org
      * @param orderNo
      * @param customerOrigin
      * @param orderInfoDetail
      * @param loanBorrowerInfoWithBLOBs
      * @return
      * @since JDK 1.8
      */
     @RequestMapping("/updateBasicInfo")
 	 @ResponseBody
 	 public Map<String, Object> updateBasicInfo(
 			@RequestParam(value = "loanBorrowerInfo", required = false) String loanBorrowerInfo
 			){
 		Map<String, Object> result = new HashMap<String, Object>();
 		LoanCustomerInfo loanCustomerInfo = new LoanCustomerInfo();
 		int ret = 0;
 		// 订单实体bean对象
 		LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs = StrUtils.isNullOrEmpty(loanBorrowerInfo) ? null : JSON.parseObject(loanBorrowerInfo, LoanBorrowerInfoWithBLOBs.class);
 		String[] bankList=loanBorrowerInfoWithBLOBs.getBankList();
 		if(null!=bankList&&bankList.length>0){
 		   try {
 			    saveBankInfo(bankList,loanBorrowerInfoWithBLOBs.getCustomerId());
			} catch (Exception e) {
				e.printStackTrace();
			}
 		}
 		loanCustomerInfo.setCustomerId(loanBorrowerInfoWithBLOBs.getCustomerId());
 		loanCustomerInfo.setCustomerNature(loanBorrowerInfoWithBLOBs.getCustomerNature());
 		loanCustomerInfo.setCustomerMobile(loanBorrowerInfoWithBLOBs.getMobile());
 		loanCustomerInfo.setIdNum(loanBorrowerInfoWithBLOBs.getIdNum());
 		loanCustomerInfo.setBankCardNo(loanBorrowerInfoWithBLOBs.getBankCardNo());
 		loanCustomerInfo.setBankId(loanBorrowerInfoWithBLOBs.getBankId());
 		loanCustomerInfo.setBranchName(loanBorrowerInfoWithBLOBs.getBranchName());
 		loanCustomerInfo.setBranchName(loanBorrowerInfoWithBLOBs.getBranchName());
 		loanCustomerInfo.setHidBankCardNo(loanBorrowerInfoWithBLOBs.getHidBankCardNo());
 		loanCustomerInfo.setManagerId(loanBorrowerInfoWithBLOBs.getManagerId());
 		//根据客户经理id 查询客户经理归属部分或组别
 		String managerNo=loanBorrowerInfoWithBLOBs.getManagerId();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(managerNo)){
			LoanEmp managerEmp=systemManager.selectLoanEmpByPrimaryKey(Integer.parseInt(managerNo));
			if(managerEmp!=null){
				loanCustomerInfo.setDeptId(managerEmp.getLeGroupId()==null?managerEmp.getLeDeptId().toString():managerEmp.getLeGroupId().toString());
			}
		}
 		ret = customerManagerService.updateLoanCustomerInfoByEntity(loanCustomerInfo);
 		if(ret != 1){
 			result.put(SystemConst.retCode, SystemConst.FAIL);
	 		result.put(SystemConst.retMsg, "\u4fdd\u5b58\u57fa\u672c\u4fe1\u606f\u5931\u8d25\uff01");//保存基本信息失败！
 		}
 		ret = 0;
 		
 		ret = customerManagerService.updateBorrowerInfoWithBLOBs(loanBorrowerInfoWithBLOBs);
 		if(ret == 1){
 			//保存成功后同步信息到碰碰旺
 			borrowerTransportService.syncBorrowerInfoWithBLOBsToRemote(loanBorrowerInfoWithBLOBs.getCustomerId());
	 		result.put(SystemConst.retCode, SystemConst.SUCCESS);
	 		result.put(SystemConst.retMsg, "\u4fdd\u5b58\u57fa\u672c\u4fe1\u606f\u6210\u529f\uff01");//保存基本信息成功！
 		 }else{
	 		result.put(SystemConst.retCode, SystemConst.FAIL);
	 		result.put(SystemConst.retMsg, "\u4fdd\u5b58\u57fa\u672c\u4fe1\u606f\u5931\u8d25\uff01");//保存基本信息失败！
 		 }
 		 return result;
 	 }
     
     /**
 	 * saveMortgageInfo:保存抵押物信息保存
 	 * @author kernel.org
     * @param houseList
     * @param carList
     * @return
     * @since JDK 1.8
 	 */
 	 @RequestMapping("/saveMortgageInfo")
 	 @ResponseBody
 	 public Map<String, Object> saveMortgageInfo(
 			 									@RequestParam(value = "houseList", required = false) String houseList,
 												@RequestParam(value = "carList",required = false) String carList,
 												@RequestParam(value = "customerId", required = false)String customerId) {
 		Map<String, Object> result = new HashMap<String, Object>();
 		boolean ret = false;
 		List<LoanHouseInfo> houseLists = StrUtils.isNullOrEmpty(houseList) ? null
 				: JSON.parseArray(houseList, LoanHouseInfo.class);
 		List<LoanCarInfo> carLists = StrUtils.isNullOrEmpty(carList) ? null
 				: JSON.parseArray(carList, LoanCarInfo.class);
 		ret = customerManagerService.saveMortgageInfo(houseLists, carLists, customerId);
 		if (ret) {
 			//保存成功后同步信息到碰碰旺
 			mortgageInfoTransService.saveMortgageInfoToRemote(customerId);
 			result.put(SystemConst.retCode, SystemConst.SUCCESS);
 			result.put(SystemConst.retMsg, "\u62B5\u62BC\u7269\u4FE1\u606F\u4FDD\u5B58\u6210\u529F");// 抵押物信息保存成功
 		} else {
 			result.put(SystemConst.retCode, SystemConst.FAIL);
 			result.put(SystemConst.retMsg, "\u62B5\u62BC\u7269\u4FE1\u606F\u4FDD\u5B58\u5931\u8D25");// 抵押物信息保存失败
 		}
 		return result;
 	 }
 	 
 	 
 	 /**
 	  * saveBankInfo:保存借款人银行卡信息
 	  * @param bankInfoList
 	  * @return
 	  * @since JDK 1.8
 	  */
 	 public Map<String, Object> saveBankInfo(String[] bankLists,String customerId) {
 		 Map<String, Object> result = new HashMap<String, Object>();
 		 boolean ret = false;
 		 ret = customerManagerService.saveBankInfo(bankLists, customerId);
 		 if (ret) {
 			 result.put(SystemConst.retCode, SystemConst.SUCCESS);
 		 } else {
 			 result.put(SystemConst.retCode, SystemConst.FAIL);
 		 }
 		 return result;
 	 }
     
     /**************************************** 借款人信息管理 END *************************************************/
}
