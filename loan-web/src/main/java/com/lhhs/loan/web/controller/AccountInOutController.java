/**
 * Project Name:loan-web
 * File Name:AccountInOutController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月31日下午8:06:22
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-web
 * File Name:AccountInOutController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月31日下午8:06:22
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.domain.ActComment;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanTransAccounts;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.excel.PayPlanExcelVo;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.service.AccountInOutService;
import com.lhhs.loan.service.CommonUtils;

/**
 * ClassName:AccountInOutController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月31日 下午8:06:22 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
/**
 * ClassName: AccountInOutController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年7月31日 下午8:06:22 <br/>
 *
 * @author Administrator
 * @version 
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/accountInOut")
public class AccountInOutController  {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountInOutController.class);
	@Autowired
	private AccountInOutService accountInOutService;
	@Autowired
	private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
	
	 private static final int PAGESIZE = 10;
		
	/**
	 * toDepositApply:(跳转到充值申请页). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toDepositApply")
	public String toDepositApply(HttpServletRequest  request){
		
		 SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		 String companyId = user.getCompanyId();
		 //客户类型
		 List<LoanDictionary>  typeList =  accountInOutService.queryCustomerTypeList();
		 //客户性质
		 List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
		 //全部银行列表
		 List<LoanBank>  bankList = accountInOutService.queryAllBank();
		 //查询所属公司银行卡信息
		 List<LoanAccountCard>  cardList = accountInOutService.queryCardByCompany(companyId,SystemConst.InOutCome.IN);
		 request.setAttribute("transType", SystemConst.TransType.TYPEID1005);
		 request.setAttribute("companyId", companyId);
		 request.setAttribute("typeList", typeList);
		 request.setAttribute("natureList", natureList);
		 request.setAttribute("bankList", bankList);
		 request.setAttribute("cardList", cardList);
		 return "accountInOut/depositApply";
	}
	
	
	/**
	 * queryCustomerInfo:(根据手机号或者机构Id查询信息). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryCustomerInfo")
	@ResponseBody
	public Map<String,Object> queryCustomerInfo(HttpServletRequest  request,
		@RequestParam(value = "mobileOrOwnerId", required = false) String mobileOrOwnerId,
		@RequestParam(value = "customerType", required = false) String customerType,
		@RequestParam(value = "transType", required = false) String transType){
		Map<String,Object> result =  accountInOutService.getAccountsByMobileOrOwnerId(mobileOrOwnerId,customerType,transType);
		return result;
	}
	/**
	 * 
	 * insertTransApply:(充值/提现申请提交). <br/>
	 * @author zhanghui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/insertTransApply")
	@ResponseBody
	public Map<String,Object>  insertTransApply(HttpServletRequest  request,
		@RequestParam(value = "accountInOut", required = false) String accountInOut,
		@RequestParam(value = "imageUrl", required = false) String[] imageUrl){
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
			LoanAccountInOutInfo loanAccountInOutInfo = StrUtils.isNullOrEmpty(accountInOut) ? null : JSON.parseObject(accountInOut, LoanAccountInOutInfo.class);
			if(SystemConst.TransType.TYPEID1005.equals(loanAccountInOutInfo.getTransType())){
				loanAccountInOutInfo.setCreateUser(user.getStaffName());
			}
			loanAccountInOutInfo.setLastUser(user.getStaffName());
			loanAccountInOutInfo.setUnionId(user.getUnionId());
			result  =accountInOutService.insertTransApply(loanAccountInOutInfo,imageUrl);
		} catch (Exception e) {
			logger.error("Exception"+e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(),"申请失败！"));
			return result;
		}
		return result;
	}
	
	
	
	/**
	 * depositRecord:(查询充值记录). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/depositRecord")
	public String depositRecord(HttpServletRequest request, 
		@RequestParam(value = "transType", required = false) String transType,
		@RequestParam(value = "transNo", required = false) String transNo,
		@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
		@RequestParam(value = "approveStatus", required = false) String approveStatus,
		@RequestParam(value = "startTime", required = false) String  startTime ,
		@RequestParam(value = "endTime", required = false) String  endTime,
		@RequestParam(value = "customerNature", required = false) String  customerNature,
		@RequestParam(value = "accountId", required = false) String  accountId,
		@RequestParam(value = "mobile", required = false) String  mobile){
		
		Page page = new Page(PAGESIZE);
		Map<String, Object> params = new HashMap<String, Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		params.put("transType", SystemConst.TransType.TYPEID1005);
		params.put("transStatus", SystemConst.Status.STATUS03);
		params.put("transNo", transNo);
		params.put("approveStatus", approveStatus);
		params.put("startTime",startTime);
		params.put("endTime", endTime);
		params.put("customerNature", customerNature);
		params.put("mobile", mobile);
		params.put("accountId", accountId);
		params.put("page", page);
		CommonUtils.fillCompanyToMap(params);
		//客户性质
		List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
	    accountInOutService.queryTransRecord(params, page);
	    Map<String,Object> total = accountInOutService.queryTransTotal(params);
		request.setAttribute("transType", SystemConst.TransType.TYPEID1005);
		request.setAttribute("total", total);
		request.setAttribute("natureList", natureList);
		request.setAttribute("transNo", transNo);
		request.setAttribute("approveStatus", approveStatus);
		request.setAttribute("startTime", "".equals(startTime)?null:startTime);
		request.setAttribute("endTime", "".equals(endTime)?null:endTime);
		request.setAttribute("customerNature", customerNature);
		request.setAttribute("mobile", mobile);
		request.setAttribute("accountId", accountId);
		request.setAttribute("page", page);
		return "accountInOut/depositRecord";
	}
	
	/**
	 * 
	 * queryDepositDetail:(充值详情). <br/>
	 * @author zhanghui
	 * @param transNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryDepositDetail")
	public String  queryDepositDetail(HttpServletRequest  request,String transNo){
		
	    Map<String,Object>  detail = accountInOutService.queryDetailByTransNo(transNo);
	    request.setAttribute("obj", detail);
		return "accountInOut/depositDetail";
	}
	
	
	/**
	 * toDepositApply:(跳转到提现申请页). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toWithdrawApply")
	public String toWithdrawApply(HttpServletRequest  request,@RequestParam(value = "transNo", required = false) String transNo){
		 SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		 String companyId = user.getCompanyId();
		 LoanAccountInOutInfo entity=new LoanAccountInOutInfo();
		 entity.setTransNo(transNo);
		 Page page = accountInOutService.queryDepositApplyList(entity);
		 List<LoanAccountInOutInfo> list=null;
		 if(null!=page&&page.getResultList().size()>0){
			 list=(List<LoanAccountInOutInfo>) page.getResultList();
		 }
		 //客户类型
		 List<LoanDictionary>  typeList =  accountInOutService.queryCustomerTypeList();
		 //客户性质
		 List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
		 //全部银行列表
		 List<LoanBank>  bankList = accountInOutService.queryAllBank();
		 //查询所属公司银行卡信息
		 List<LoanAccountCard>  cardList = accountInOutService.queryCardByCompany(companyId,SystemConst.InOutCome.OUT);
		 request.setAttribute("transType", SystemConst.TransType.TYPEID1006);
		 request.setAttribute("companyId", companyId);
		 request.setAttribute("typeList", typeList);
		 request.setAttribute("natureList", natureList);
		 request.setAttribute("bankList", bankList);
		 request.setAttribute("cardList", cardList);
		 request.setAttribute("accountInfo", list.get(0));
		 return "accountInOut/withdrawApply";
	}
	
	
	/**
	 * withdrawRecord:(查询提现记录). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/withdrawRecord")
	public String withdrawRecord(HttpServletRequest request, 
		@RequestParam(value = "transType", required = false) String transType,
		@RequestParam(value = "transNo", required = false) String transNo,
		@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
		@RequestParam(value = "approveStatus", required = false) String approveStatus,
		@RequestParam(value = "startTime", required = false) String  startTime ,
		@RequestParam(value = "endTime", required = false) String  endTime,
		@RequestParam(value = "customerNature", required = false) String  customerNature,
		@RequestParam(value = "accountId", required = false) String  accountId,
		@RequestParam(value = "mobile", required = false) String  mobile){
		
		Page page = new Page(PAGESIZE);
		Map<String, Object> params = new HashMap<String, Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		params.put("transType", SystemConst.TransType.TYPEID1006);
		params.put("transStatus", SystemConst.Status.STATUS03);
		params.put("transNo", transNo);
		params.put("approveStatus", approveStatus);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("customerNature", customerNature);
		params.put("mobile", mobile);
		params.put("accountId", accountId);
		params.put("page", page);
		CommonUtils.fillCompanyToMap(params);
		//客户性质
		List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
	    accountInOutService.queryTransRecord(params, page);
	    Map<String,Object> total = accountInOutService.queryTransTotal(params);
		request.setAttribute("transType", SystemConst.TransType.TYPEID1006);
		request.setAttribute("total", total);
		request.setAttribute("natureList", natureList);
		request.setAttribute("transNo", transNo);
		request.setAttribute("approveStatus", approveStatus);
		request.setAttribute("startTime",  "".equals(startTime)?null:startTime);
		request.setAttribute("endTime",  "".equals(endTime)?null:endTime);
		request.setAttribute("customerNature", customerNature);
		request.setAttribute("mobile", mobile);
		request.setAttribute("accountId", accountId);
		request.setAttribute("page", page);
		return "accountInOut/withdrawRecord";
	}
	
	/**
	 * 
	 * queryWithdrawDetail:(提现详情). <br/>
	 * @author zhanghui
	 * @param transNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryWithdrawDetail")
	public String  queryWithdrawDetail(HttpServletRequest  request,String transNo){
		
	    Map<String,Object>  detail = accountInOutService.queryDetailByTransNo(transNo);
	    request.setAttribute("obj", detail);
		return "accountInOut/withdrawDetail";
	}
	
	
	
	/**
	 * withdrawRecord:(查询充值记账审核记录). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/depositApproveRecord")
	public String depositApproveRecord(HttpServletRequest request, 
		@RequestParam(value = "transType", required = false) String transType,
		@RequestParam(value = "transNo", required = false) String transNo,
		@RequestParam(value = "customerName", required = false) String customerName,
		@RequestParam(value = "approveStatus", required = false) String approveStatus,
		@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
		@RequestParam(value = "startTime", required = false) String  startTime ,
		@RequestParam(value = "endTime", required = false) String  endTime,
		@RequestParam(value = "customerNature", required = false) String  customerNature,
		@RequestParam(value = "customerId", required = false) String  customerId,
		@RequestParam(value = "mobile", required = false) String  mobile){
		
		Page page = new Page(PAGESIZE);
		Map<String, Object> params = new HashMap<String, Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		params.put("transType", SystemConst.TransType.TYPEID1005);
		params.put("transNo", transNo);
		params.put("transStatus", SystemConst.Status.STATUS93);
		params.put("approveStatus", approveStatus);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("customerNature", customerNature);
		params.put("mobile", mobile);
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		params.put("page", page);
		CommonUtils.fillCompanyToMap(params);
		//客户性质
		List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
	    accountInOutService.queryTransRecord(params, page);
	    Map<String,Object> total = accountInOutService.queryTransTotal(params);
		request.setAttribute("total", total);
		request.setAttribute("natureList", natureList);
		request.setAttribute("transNo", transNo);
		request.setAttribute("transStatus", SystemConst.Status.STATUS93);
		request.setAttribute("approveStatus", approveStatus);
		request.setAttribute("startTime",  "".equals(startTime)?null:startTime);
		request.setAttribute("endTime",  "".equals(endTime)?null:endTime);
		request.setAttribute("customerNature", customerNature);
		request.setAttribute("mobile", mobile);
		request.setAttribute("customerName", customerName);
		request.setAttribute("customerId", customerId);
		request.setAttribute("page", page);
		return "accountInOut/depositApproveRecord";
	}
	
	/**
	 * 
	 * toDepositApprove:(跳转充值审核页). <br/>
	 * @author zhanghui
	 * @param transNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toDepositApprove")
	public String  toDepositApprove(HttpServletRequest  request,String transNo){
		
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
	    Map<String,Object>  detail = accountInOutService.queryDetailByTransNo(transNo);
	    request.setAttribute("obj", detail);
	    request.setAttribute("staffName", user.getStaffName());
		return "accountInOut/depositApprove";
	}
	
	
	/**
	 * withdrawApproveRecord:(查询提现审核记录). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/withdrawApproveRecord")
	public String withdrawApproveRecord(HttpServletRequest request, 
		@RequestParam(value = "transType", required = false) String transType,
		@RequestParam(value = "transNo", required = false) String transNo,
		@RequestParam(value = "customerName", required = false) String customerName,
		@RequestParam(value = "approveStatus", required = false) String approveStatus,
		@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
		@RequestParam(value = "startTime", required = false) String  startTime ,
		@RequestParam(value = "endTime", required = false) String  endTime,
		@RequestParam(value = "customerNature", required = false) String  customerNature,
		@RequestParam(value = "customerId", required = false) String  customerId,
		@RequestParam(value = "mobile", required = false) String  mobile){
		
		Page page = new Page(PAGESIZE);
		Map<String, Object> params = new HashMap<String, Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		params.put("transType", SystemConst.TransType.TYPEID1006);
		params.put("transNo", transNo);
		params.put("transStatus", SystemConst.Status.STATUS93);
		request.setAttribute("approveStatus", approveStatus);
		params.put("approveStatus", approveStatus);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("customerNature", customerNature);
		params.put("mobile", mobile);
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		params.put("page", page);
		CommonUtils.fillCompanyToMap(params);
		//客户性质
		List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
	    accountInOutService.queryTransRecord(params, page);
	    Map<String,Object> total = accountInOutService.queryTransTotal(params);
		request.setAttribute("total", total);
		request.setAttribute("natureList", natureList);
		request.setAttribute("transNo", transNo);
		request.setAttribute("transStatus", SystemConst.Status.STATUS93);
		request.setAttribute("startTime",  "".equals(startTime)?null:startTime);
		request.setAttribute("endTime",  "".equals(endTime)?null:endTime);
		request.setAttribute("customerNature", customerNature);
		request.setAttribute("mobile", mobile);
		request.setAttribute("customerName", customerName);
		request.setAttribute("customerId", customerId);
		request.setAttribute("page", page);
		return "accountInOut/withdrawApproveRecord";
	}
	
	/**
	 * 
	 * toDepositApprove:(跳转提现审核页). <br/>
	 * @author zhanghui
	 * @param transNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toWithdrawApprove")
	public String  toWithdrawApprove(HttpServletRequest  request,String transNo){
		
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
	    Map<String,Object>  detail = accountInOutService.queryDetailByTransNo(transNo);
	    request.setAttribute("obj", detail);
	    request.setAttribute("staffName", user.getStaffName());
		return "accountInOut/withdrawApprove";
	}
	
	
	/**
	 * 
	 * saveDepositApprove:(充值审核提交). <br/>
	 * @author zhanghui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/saveDepositApprove")
	@ResponseBody
	public Map<String,Object>  saveDepositApprove(HttpServletRequest  request,
		@RequestParam(value = "depositApprove", required = false) String depositApprove){
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			ActComment actComment = StrUtils.isNullOrEmpty(depositApprove) ? null : JSON.parseObject(depositApprove, ActComment.class);
		    result  =accountInOutService.saveTransApprove(actComment,SystemConst.TransType.TYPEID1005);
		} catch (Exception e) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(),"审核失败！"));
			return result;
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * saveWithdrawApprove:(提现审核提交). <br/>
	 * @author zhanghui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/saveWithdrawApprove")
	@ResponseBody
	public Map<String,Object>  saveWithdrawApprove(HttpServletRequest  request,
		@RequestParam(value = "withdrawApprove", required = false) String withdrawApprove){
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			ActComment actComment = StrUtils.isNullOrEmpty(withdrawApprove) ? null : JSON.parseObject(withdrawApprove, ActComment.class);
		    result  =accountInOutService.saveTransApprove(actComment,SystemConst.TransType.TYPEID1006);
		} catch (Exception e) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(),"审核失败！"));
			return result;
		}
		
		return result;
	}
	
	
	
	
	/**
	 * depositRecord:(充值提现导出记录). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/exportRecord")
	@ResponseBody
	public void exportRecord(HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value = "transType", required = false) String transType,
		@RequestParam(value = "transNo", required = false) String transNo,
		@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
		@RequestParam(value = "approveStatus", required = false) String approveStatus,
		@RequestParam(value = "startTime", required = false) String  startTime ,
		@RequestParam(value = "endTime", required = false) String  endTime,
		@RequestParam(value = "customerNature", required = false) String  customerNature,
		@RequestParam(value = "accountId", required = false) String  accountId,
		@RequestParam(value = "mobile", required = false) String  mobile){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transType", transType);
		params.put("transStatus", SystemConst.Status.STATUS03);
		params.put("transNo", transNo);
		params.put("approveStatus", approveStatus);
		params.put("startTime",startTime);
		params.put("endTime", endTime);
		params.put("customerNature", customerNature);
		params.put("mobile", mobile);
		params.put("accountId", accountId);
		CommonUtils.fillCompanyToMap(params);
		List<Map<String, Object>>  resultList  =  accountInOutService.queryTransRecord(params, null);
		List<Map<String, Object>>  result  =  accountInOutService.convertProperties(resultList);
		Map<String,String>  titles = new LinkedHashMap<String,String>();
		String filename ="";
		if(SystemConst.TransType.TYPEID1005.equals(transType)){
			filename ="线下充值交易记录";
			titles.put("trans_no", "订单号");
			titles.put("mobile", "手机号");
			titles.put("type_name", "客户类型");
			titles.put("nature_name", "客户性质");
			titles.put("account_holder", "持卡人姓名");
			titles.put("amount", "充值金额（元）");
			titles.put("bank_name", "支付银行");
			titles.put("bank_card_no", "支付银行卡号");
			titles.put("create_time", "申请时间");
			titles.put("create_user", "申请人");
			titles.put("task_end_date", "审核时间");
			titles.put("assignee_name", "审核人");
			titles.put("status", "审核结果");
		}else if(SystemConst.TransType.TYPEID1006.equals(transType)){
			filename ="线下提现交易记录";
			titles.put("trans_no", "订单号");
			titles.put("mobile", "手机号");
			titles.put("type_name", "客户类型");
			titles.put("nature_name", "客户性质");
			titles.put("account_holder", "持卡人姓名");
			titles.put("amount", "提现金额（元）");
			titles.put("bank_name", "提现银行");
			titles.put("bank_card_no", "提现银行卡号");
			titles.put("create_time", "申请时间");
			titles.put("create_user", "申请人");
			titles.put("task_end_date", "审核时间");
			titles.put("assignee_name", "审核人");
			titles.put("status", "审核结果");
		}
		
		AnalyseTransExcel.downLoadExcel(request, response, filename, result, params.getClass(), titles);
	}
	
	
	/**
	 * list:提现申请列表查询<br/>
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(LoanAccountInOutInfo entity){
		ModelAndView mav = new ModelAndView("accountInOut/depositApplyList");
		entity.setTransType(SystemConst.TransType.TYPEID1006);
		entity.setStatus("03");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		Page page = accountInOutService.queryDepositApplyList(entity);
		//客户性质
		List<LoanDictionary>  natureList =  accountInOutService.queryCustomerNatureList();
		mav.addObject("page", page);
		mav.addObject("entity", entity);
		mav.addObject("natureList", natureList);
		mav.addObject("map", accountInOutService.queryTotalAmount(entity));
		return mav;
	}

	/**
	 * ajaxQueryList:
	 * 提现申请列表 异步搜索查询及分页查询<br/>
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanAccountInOutInfo entity){
		ModelAndView mav = new ModelAndView("accountInOut/_depositApplyTemp");
		entity.setTransType(SystemConst.TransType.TYPEID1006);
		entity.setStatus("03");
		CommonUtils.fillCompany(entity);
		Page page = accountInOutService.queryDepositApplyList(entity);
		mav.addObject("page", page);
		entity.setPage(null);
		mav.addObject("map", accountInOutService.queryTotalAmount(entity));
		return mav;
	}
	
	/**
	 * export:
	 * 提现申请列表导出 <br/>
	 * @author suncj
	 * @param entity
	 * @param response
	 * @since JDK 1.8
	 */
	@RequestMapping("/export")
	public void export(LoanAccountInOutInfo entity,HttpServletResponse response,HttpServletRequest request){
		CommonUtils.fillCompany(entity);
		entity.setTransType(SystemConst.TransType.TYPEID1006);
		entity.setStatus("03");
		entity.setPage(null);
		try{
			List<LoanAccountInOutInfo> exportList = loanAccountInOutInfoMapper.queryDepositApplyList(entity);
			Map<String, String> titles=new LinkedHashMap<String, String>();
			titles.put("transNo", "订单号");
		    titles.put("orderNo", "报单编号");
		    titles.put("subjectName", "提现类型摘要");
		    titles.put("mobile", "手机号");
		    titles.put("customerTypeSed", "客户类型");
		    titles.put("customerNatureSed", "客户性质");
		    titles.put("customerName", "姓名");
		    titles.put("amount", "提现金额(元)");
		    titles.put("bankName", "提现银行");
		    titles.put("bankCardNo", "提现至银行卡");
		    AnalyseTransExcel.downLoadExcel(request, response, "提现申请列表导出", exportList, LoanAccountInOutInfo.class, titles);
		    System.out.println("提现申请列表"+"下载完成");  
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}

