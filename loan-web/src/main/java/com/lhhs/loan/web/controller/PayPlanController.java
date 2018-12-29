/**
 * Project Name:loan-web
 * File Name:PayPlanController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月28日上午9:40:36
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.CalendarUtil;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.domain.LoanAdvance;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.excel.AdvancePayFinishedExcelVo;
import com.lhhs.loan.dao.domain.excel.PayFinishedExcelVo;
import com.lhhs.loan.dao.domain.excel.PayPlanExcelVo;
import com.lhhs.loan.dao.domain.vo.PayRecordVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.PayPlanService;
import com.lhhs.loan.service.PayRecordService;
import com.lhhs.loan.service.RepaymentTransService;
import com.lhhs.loan.service.TransMainService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * ClassName:PayPlanController <br/>
 * Function: 还款管理模块<br/>
 * Date:     2017年7月28日 上午9:40:36 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/payPlan")
public class PayPlanController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayPlanController.class);
	
	@Autowired
	private PayPlanService payPlanService;
	@Autowired
	private PayRecordService payRecordService;
	@Autowired
	private TransMainService transMainService;
	@Autowired
	private RepaymentTransService repaymentTransService;
	@Autowired
    private CustomerManagerService customerManagerService;
	 @Autowired
 	 private AccountTransactionService accountTransactionService;
	
	/**
	 * list:还款计划列表查询<br/>
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(LoanPayPlan entity){
		ModelAndView mav = new ModelAndView("payPlan/payPlanList");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		entity.setStatus(SystemConst.Status.STATUS03);
		Page page = payPlanService.queryListPage(entity);
		getDates(page);
		mav.addObject("orderNo", entity.getOrderNo()==null ? "" : entity.getOrderNo());
		mav.addObject("page", page);
		mav.addObject("map", payPlanService.queryTotalAmount(entity));
		return mav;
	}
	
	/**
	 * ajaxQueryList:
	 * 还款计划列表 异步搜索查询及分页查询<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanPayPlan entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_payPlanTemp");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		entity.setStatus(SystemConst.Status.STATUS03);
		Page page = payPlanService.queryListPage(entity);
		getDates(page);
		mav.addObject("page", page);
		mav.addObject("map", payPlanService.queryTotalAmount(entity));
		return mav;
	}
	
	public Page getDates(Page page){
		if(null!=page&&null!=page.getResultList()&&page.getResultList().size()>0){
			for(LoanPayPlan plan:(List<LoanPayPlan>) page.getResultList()){
				int t=plan.getOverdueDays();//逾期天数
				if(t>0){
					Date capitalTime=plan.getRepaymentCapitalTime();
					Date interestTime=plan.getRepaymentInterestTime();
					int capitalDate=CalendarUtil.getIntervalDays(capitalTime,new Date());
					int interestDate=CalendarUtil.getIntervalDays(interestTime,new Date());
					plan.setCapitalDate(capitalDate);
					plan.setInterestDate(interestDate);
				}
			}
		}
		return page;
	}
	
	
	/**
	 * payDetail:
	 * 单条还款弹出详情查询 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/payDetail")
	public ModelAndView payDetail(LoanPayPlan entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_payTemp");
		//TODO 查询条件要完善
		entity.setStatus(SystemConst.Status.STATUS03);
		//还款主记录查询对象
		LoanTransMain tMain = new LoanTransMain();
		tMain.setTransMainId(entity.getTransMainId());
		try{
			mav.addObject("transMain", transMainService.get(tMain));
			List temp = payPlanService.queryList(entity);
			if(temp != null && temp.size() > 0){
				LoanPayPlan payPlan = (LoanPayPlan) temp.get(0);
				//计算罚息对象
				PayRecordVo record = new PayRecordVo();
				BeanUtils.copyProperties(payPlan,record);
				record.setPlanId(payPlan.getId());
				record.setPayFlag("0");
				record.setActualTransTime(new Date());
				mav.addObject("prePay", repaymentTransService.overdueCalculationByCapital(record, false));
				mav.addObject("payPlan", payPlan);
				mav.addObject("lastPeriod", temp.size());//剩余期数
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待还款数据有误"));
		}
		return mav;
	}
	/**
	 * ajaxCalculation:
	 * 异步计算逾期罚息、提前还款违约金、还款总额<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/ajaxCalculation")
	public Map<String,Object> ajaxCalculation(PayRecordVo entity){
		Map<String,Object> ret = new HashMap<String,Object>();
		LoanPayRecord record = null;
		if(entity == null){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "参数不合法");
			return ret;
		}
		try{
			if("0".equals(entity.getPayFlag())){
				record = repaymentTransService.overdueCalculationByCapital(entity, false);
			}
			if("1".equals(entity.getPayFlag())){
				record = repaymentTransService.compensateCalculation(entity);
			}
		}catch(Exception e){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(),"计算失败"));
			return ret;
		}
		ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		ret.put("prePay", record);
		return ret;
	}
	
	/**
	 * payHandler:执行还款时，提交的数据<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/payHandler")
	public Map<String,Object> payHandler(PayRecordVo entity){
		Map<String,Object> ret = new HashMap<String,Object>();
		
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		entity.setCreateUser(user.getUserId().toString());
		entity.setLastUser(user.getUserId().toString());
		CommonUtils.fillCompany(entity);
		
		try{
			if("0".equals(entity.getPayFlag())){//正常还款
				ret = repaymentTransService.repaymentHandler(entity);
			}else if("1".equals(entity.getPayFlag())){//提前还本
				ret = repaymentTransService.repaymentAllHandler(entity);
			}else if("2".equals(entity.getPayFlag())){//部分还本
				ret = repaymentTransService.repaymentCapitalHandler(entity);
			}else{
				ret.put(SystemConst.retCode, SystemConst.FAIL);
				ret.put(SystemConst.retMsg,"还款数据有误");
			}
		}catch(Exception e){
			LOGGER.error(e.toString());
			LOGGER.error(e.getMessage());
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(),"还款失败"));
		}
		return ret;
	}
	/**
	 * payAllDetail:
	 * 全部还款弹出详情查询 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/payAllDetail")
	public ModelAndView payAllDetail(LoanPayPlan entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_payAllTemp");
		//TODO 查询条件要完善
		entity.setStatus(SystemConst.Status.STATUS03);
		
		LoanTransMain tMain = new LoanTransMain();
		tMain.setTransMainId(entity.getTransMainId());
		//计算还款违约金参数对象
		PayRecordVo record = new PayRecordVo();
		record.setTransMainId(entity.getTransMainId());
		record.setPayFlag("1");
		record.setActualTransTime(new Date());
		try{
			mav.addObject("prePay",repaymentTransService.compensateCalculation(record));
			mav.addObject("transMain", transMainService.get(tMain));
			mav.addObject("list", payPlanService.queryList(entity));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待还款数据有误"));
		}
		return mav;
	}
	
	/**
	 * payCapitalDetail:弹出部分还本页面<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/payCapitalDetail")
	public ModelAndView payCapitalDetail(PayRecordVo entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_payCapitalTemp");
		try{
			mav.addObject("result",repaymentTransService.payCapitalCalculation(entity));
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待还款数据有误"));
		}
		return mav;
	}
	/**
	 * payRecordList:已还款记录列表查询 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/payRecordList")
	public ModelAndView payRecordList(LoanPayRecord entity){
		ModelAndView mav = new ModelAndView("payPlan/payRecordList");
		//TODO 查询条件要完善
		entity.setStatus(SystemConst.Status.STATUS03);
		CommonUtils.fillCompany(entity);
		
		mav.addObject("page", payRecordService.queryListPage(entity));
		mav.addObject("map", payRecordService.queryTotalAmount(entity));
		return mav;
	}
	
	/**
	 * ajaxQueryRecordList:
	 * 已还款记录列表 异步搜索查询及分页查询<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryRecordList")
	public ModelAndView ajaxQueryRecordList(LoanPayRecord entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_payRecordTemp");
		//TODO 查询条件要完善
		entity.setStatus(SystemConst.Status.STATUS03);
		CommonUtils.fillCompany(entity);
		
		mav.addObject("page", payRecordService.queryListPage(entity));
		mav.addObject("map", payRecordService.queryTotalAmount(entity));
		return mav;
	}
	
	/**
	 * loanPayFinishedList:
	 * A.已还清记录列表<br/>
	 * B.提前还款记录列表<br/>
	 * 注意事项： 两个列表查询的入口<br/>
	 * 
	 * @author xiangfeng
	 * @param entity
	 * @param status  90 正常结清  ；88 提前结清；
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/loanPayFinishedList/{status}")
	public ModelAndView loanPayFinishedList(LoanTransMain entity,@PathVariable String status){
		ModelAndView mav = new ModelAndView("payPlan/loanPayFinishedList");
		if(!StrUtils.isNullOrEmpty(status)){
			//TODO 查询条件要完善
			entity.setCleanUpStatus(status);
			CommonUtils.fillCompany(entity);
			
			mav.addObject("type", status);
			mav.addObject("page", transMainService.queryListPage(entity));
			mav.addObject("map", transMainService.queryTotalAmount(entity));
		}
		return mav;
	}
	
	/**
	 * ajaxQueryPayFinishedList:
	 * A.已还清记录列表<br/>
	 * B.提前还款记录列表<br/>
	 * 
	 * 异步搜索查询及分页查询<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryPayFinishedList")
	public ModelAndView ajaxQueryPayFinishedList(LoanTransMain entity){
		ModelAndView mav = new ModelAndView("payPlan/temp/_PayFinishedTemp");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		
		mav.addObject("type", entity.getCleanUpStatus());
		mav.addObject("page", transMainService.queryListPage(entity));
		mav.addObject("map", transMainService.queryTotalAmount(entity));
		return mav;
	}
	
	/**
	 * payFinishedDetails: 
	 * A.已还清记录<br/>
	 * B.提前还款记录<br/>
	 * 
	 * 详情列表查询<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/payFinishedDetails/{type}")
	public ModelAndView payFinishedDetails(LoanPayRecord entity,@PathVariable String type){
		ModelAndView mav = new ModelAndView("payPlan/payFinishedDetails");
		//TODO 查询条件要完善
		entity.setStatus(SystemConst.Status.STATUS03);
		CommonUtils.fillCompany(entity);
		
		mav.addObject("list", payRecordService.queryList(entity));
		mav.addObject("periodTotal", entity.getPeriodTotal());
		mav.addObject("type", type);
		return mav;
	}
	
	/**
	 * Excel导出模块
	 */
	/**
	 * payPlanExport:
	 * 待还款计划导出 <br/>
	 * @author xiangfeng
	 * @param entity
	 * @param response
	 * @since JDK 1.8
	 */
	@RequestMapping("/payPlanExport")
	public void payPlanExport(LoanPayPlan entity,HttpServletResponse response){
		CommonUtils.fillCompany(entity);
		entity.setStatus(SystemConst.Status.STATUS03);
		try{
			List<PayPlanExcelVo> exportList = payPlanService.queryPayPlanExport(entity);
			CommonUtils.excelExport(response,"待还款计划表.xls","待还款计划表",exportList,PayPlanExcelVo.class);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
	}
	/**
	 * payFinishedExport:<br/>
	 * A.已还清记录列表<br/>
	 * B.提前还款记录列表<br/>
	 * 导出列表
	 * 
	 * @author xiangfeng
	 * @param entity
	 * @param status  90 正常结清  ；88 提前结清；
	 * @param response
	 * @since JDK 1.8
	 */
	@RequestMapping("/payFinishedExport/{status}")
	public void payFinishedExport(LoanTransMain entity,@PathVariable String status,HttpServletResponse response){
		CommonUtils.fillCompany(entity);
		entity.setStatus(SystemConst.Status.STATUS03);
		try{
			if(!StrUtils.isNullOrEmpty(status)){
				if(status.equals(SystemConst.Status.STATUS90)){
					List<PayFinishedExcelVo> exportList = transMainService.queryPayFinishedExport(entity);
					CommonUtils.excelExport(response,"已还清记录表.xls","已还清记录表",exportList,PayFinishedExcelVo.class);
				}
				if(status.equals(SystemConst.Status.STATUS88)){
					List<AdvancePayFinishedExcelVo> exportList = transMainService.queryAdvancePayFinishedExport(entity);
					CommonUtils.excelExport(response,"提前还款记录表.xls","提前还款记录表",exportList,AdvancePayFinishedExcelVo.class);
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
	}
	
	
	/**
 	 * 待还债务
 	 * amountDueList:(这里用一句话描述这个方法的作用). <br/>
 	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
 	 *
 	 * @author Administrator
 	 * @param entity
 	 * @return
 	 * @since JDK 1.8
 	 */
 	
 	 @RequestMapping("/amountDueList")
     public ModelAndView amountDueList(
    		 LoanAdvance entity
    		 ){
    	 ModelAndView model=new ModelAndView("capotalPrepaid/amountduelist");
    	 CommonUtils.fillCompany(entity);
    	 entity.setStatus(SystemConst.Status.STATUS03);
    	 Page page = customerManagerService.capitalPrepaidList(entity);
 		 model.addObject("page", page);
 		 model.addObject("map", customerManagerService.queryTotalAmountSum(entity));
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
   	@RequestMapping("/ajaxAmountDueList")
   	public ModelAndView ajaxAmountDueList(LoanAdvance entity){
   		ModelAndView mav = new ModelAndView("capotalPrepaid/_amountdue");
   		CommonUtils.fillCompany(entity);
   		entity.setStatus(SystemConst.Status.STATUS03);
   		mav.addObject("page", customerManagerService.capitalPrepaidList(entity));
   		mav.addObject("map", customerManagerService.queryTotalAmountSum(entity));
   		return mav;
   	}
      
   	
	/**
     * 弹出还债页面
     * payDetail:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * @author Administrator
     * @param payEntity
     * @return
     * @since JDK 1.8
     */
   @RequestMapping("/amountDueView")
	public ModelAndView amountDueView(
			LoanAdvance entity
			){
   	    ModelAndView mav =null;
  		try {
		mav = new ModelAndView("capotalPrepaid/_executeamountdue");
		LoanTransMain tMain = new LoanTransMain();
		tMain.setTransMainId(entity.getTransMainId());
		LoanAdvance pay = customerManagerService.queryAdvance(entity.getAdvanceId());
		//默认当前日期
		pay.setNowPaidAdvanceTime(new Date());
		LoanAdvance resPay=accountTransactionService.advanceAmountCompute(pay);
		tMain=transMainService.get(tMain);
		mav.addObject("transMain",tMain);
		mav.addObject("pay", resPay);
		mav.addObject("newDate", new Date());
			
  		} catch (Exception e) {
  			LOGGER.error(e.getMessage());
			mav = new ModelAndView("alertError");
			mav.addObject("errorTxt", CommonUtils.errorMessageHandler(e.getMessage(), "待付款数据有误"));
		}
  		return mav;
	}
   
   @RequestMapping("/ajaxAmountCompute")
   @ResponseBody
   public Map<String, Object> ajaxAmountCompute(LoanAdvance advance){
	   Map<String, Object> map=new HashMap<String,Object>();
	   LoanAdvance loanAdvance=null; 
	   if(advance==null||advance.getPaidAdvanceTime()==null){
		   map.put(SystemConst.retCode, SystemConst.FAIL);
		   map.put(SystemConst.retMsg, "日期不能为空");
		   return map;
	   }
	   try {
		   LoanAdvance pay = customerManagerService.queryAdvance(advance.getAdvanceId());
			//默认当前日期
		   pay.setNowPaidAdvanceTime(advance.getPaidAdvanceTime());
		   loanAdvance=accountTransactionService.advanceAmountCompute(pay);
		   map.put(SystemConst.retCode, SystemConst.SUCCESS);
		   map.put(SystemConst.retMsg, "日期不能为空");
	    } catch (Exception e) {
		   LOGGER.error("还债计算错误"+e.getMessage());
		   map.put(SystemConst.retCode, SystemConst.FAIL);
		   map.put(SystemConst.retMsg, "计算错误");
		   return map;
	   }
	   map.put("pay", loanAdvance);
	   return map;
   }
   
   
   /**
    * 执行还债
    * executeAmount:(这里用一句话描述这个方法的作用). <br/>
    * TODO(这里描述这个方法的注意事项 – 可选).<br/>
    *
    * @author Administrator
    * @param advance
    * @return
    * @since JDK 1.8
    */
   @RequestMapping("/executeAmount")
   @ResponseBody
   public Map<String, Object> executeAmount(LoanAdvance advance){
	   Map<String, Object> ret=new HashMap<String,Object>();
	   if(advance==null||advance.getPaidAdvanceTime()==null||
			   advance.getPaidTotal()==null){
		   ret.put(SystemConst.retCode, SystemConst.FAIL);
		   ret.put(SystemConst.retMsg, "还债参数错误");
		   return ret;
	   }
	   LoanAdvanceRecord record=new LoanAdvanceRecord();
	   BeanUtils.copyProperties(advance, record);
	   record.setPaidTotal(advance.getPaidTotal());
	   record.setPaidInterest(advance.getPaidInterest());
	   try {
			String returnMsg=accountTransactionService.LoanAdvancePayTrans(record);//请求垫付接口
			ret.put(SystemConst.retCode, returnMsg);
			ret.put(SystemConst.retMsg, "垫付成功");
		} catch (Exception e) {
			LOGGER.error("执行垫付失败"+e.getMessage());
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "执行垫付失败:"+e.getMessage());
		}
		return ret;
   }
}

