/**
 * Project Name:loan-web
 * File Name:CrmIntentLoanUserRecordController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年11月14日上午9:42:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.crm.ActionType;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.HttpClientUtil;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.CrmIntentLoanUserRecord;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.SipAccount;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.ProductService;
import com.lhhs.loan.service.SipAccountService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserRecordService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;
import com.lhhs.workflow.common.utils.StringUtils;

/**
 * ClassName:CrmIntentLoanUserRecordController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月14日 上午9:42:02 <br/>
 * @author   df
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/crmIntentLoanUserRecord")
public class CrmIntentLoanUserRecordController {
	private static final Logger LOGGER = Logger.getLogger(CrmIntentLoanUserRecordController.class);
	@Autowired
	private CrmIntentLoanUserService crmIntentLoanUserService;
	@Autowired
	private CrmIntentLoanUserRecordService crmIntentLoanUserRecordService;
	@Autowired
	private SystemManagerService systemManager;
	@Autowired
	private UnionCompanyService unionCompanyService;
	@Reference
	private UserApi userApi;
	@Autowired
	private SipAccountService sipAccountService;
	@Autowired
	private ProductService productService;
	/**
	 * 客户回访<br/>
	 * selectType:d:明细；f：跟踪;a:指派
	 */
	@RequestMapping("/intentLoanUseDetail/{actionType}/{customerStatus}")
	public ModelAndView intentLoanUseDetail(CrmIntentLoanUser entity,
			@PathVariable("actionType") String actionType,
			@PathVariable("customerStatus") String customerStatus,
			String isCall,
			@Value("${ucpaas.serverAddress}") String serverAddress,
			@Value("${ucpaas.accountSid}") String accountSid,
			@Value("${ucpaas.token}") String token){
		LoanEmp emp=CommonUtils.getEmpFromSession();
		//查询客户信息
		ModelAndView mav = new ModelAndView("crm/crmIntentLoanUserDetail");
		CrmIntentLoanUser crmIntentLoanUser = crmIntentLoanUserService.get(entity);
		//查询要指派的人
		List<User> list=null;
		//指派处理
		if(ActionType.ASSIGN.getKey().equals(actionType)||ActionType.CHANGE.getKey().equals(actionType)){
			mav = new ModelAndView("crm/crmIntentLoanUserAssign");
			User temp_user=new User();
			CommonUtils.fillBumpCompany(temp_user);
			//查询要指派的人
			list=userApi.queryListXd(temp_user);
		}else if(ActionType.FOLLOW.getKey().equals(actionType)){
			mav = new ModelAndView("crm/crmIntentLoanUserFollow");
			if(StringUtils.isNotEmpty(crmIntentLoanUser.getAppointEmpId())){
				if(!String.valueOf(emp.getLeEmpId()).equals(crmIntentLoanUser.getAppointEmpId())){
					throw new AccessDeniedException("不是客户经理，不能回访!");
				}
			}else{
				if(!String.valueOf(emp.getLeEmpId()).equals(crmIntentLoanUser.getCreaterEmpId())){
					throw new AccessDeniedException("不是创建人，不能回访!");
				}
			}
			//明细
		}else if(ActionType.DETAIL.getKey().equals(actionType)){
			mav = new ModelAndView("crm/crmIntentLoanUserDetail");
			mav.addObject("customerStatus", customerStatus);
		}
		
		if ("yes".equals(isCall)) {
			String mobile = crmIntentLoanUser.getMobile();
			SecurityUser loanEmp = (SecurityUser)SecurityUserHolder.getCurrentUser();
			String userId = loanEmp.getUserId().toString();
			SipAccount sipAccount = sipAccountService.getByUserId(userId);
			if (sipAccount==null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Authorization", "Basic " + Base64.encodeBase64String((accountSid + ":" + token).getBytes()));
				HttpClientUtil.setHeaders(map);
				String result = HttpClientUtil.sendHttpPost(serverAddress+"accounts/"+accountSid+"/sipNumbers");
				sipAccount  = JSON.parseObject(result,SipAccount.class);
				sipAccount.setUserId(userId);
				sipAccount.setDeptId(loanEmp.getDeptId());
				sipAccount.setCompanyId(loanEmp.getCompanyId());
				sipAccount.setUnionId(loanEmp.getUnionId());
				sipAccountService.save(sipAccount);
			}
			mav.addObject("sipAccount", sipAccount);
			mav.addObject("mobile", mobile);
		}
		if(crmIntentLoanUser!=null&&(ActionType.DETAIL.getKey().equals(actionType)||ActionType.FOLLOW.getKey().equals(actionType))){
			if((StringUtils.isNotEmpty(crmIntentLoanUser.getAppointEmpId())&&!emp.getLeEmpId().toString().equals(crmIntentLoanUser.getAppointEmpId()))
					|| (StringUtils.isEmpty(crmIntentLoanUser.getAppointEmpId()) && !emp.getLeEmpId().toString().equals(crmIntentLoanUser.getCreaterEmpId()))){
				if(StringUtils.isNoneBlank(crmIntentLoanUser.getIdNumber())){
					String idNumber=crmIntentLoanUser.getIdNumber().substring(0,6);
					idNumber+="******"+crmIntentLoanUser.getIdNumber().substring(14, crmIntentLoanUser.getIdNumber().length());
					crmIntentLoanUser.setIdNumber(idNumber);
				}
			}
			if(!"08".equals(crmIntentLoanUser.getBusinessStatus())){//08为已报单
				if(StringUtils.isNoneBlank(crmIntentLoanUser.getMobile())){
					String mobile=crmIntentLoanUser.getMobile().substring(0, 3);
					mobile+="****"+crmIntentLoanUser.getMobile().substring(7, crmIntentLoanUser.getMobile().length());
					crmIntentLoanUser.setMobile(mobile);
				}
			}
		}
		crmIntentLoanUser.setVisitTime(new Date());
		//设置操作类型
		crmIntentLoanUser.setActionType(actionType);
		//查询跟踪记录
		List<CrmIntentLoanUserRecord> followRecord=crmIntentLoanUserRecordService.queryList(entity.getId());
		mav.addObject("followRecord", followRecord);
		mav.addObject("crmUser", crmIntentLoanUser);
		mav.addObject("allManager", list);
		mav.addObject("customerStatus", customerStatus);
		mav.addObject("productList", productService.queryProductByStatus("1"));
		return mav;
	}
	
	/**
	 * Handler:指派  获取客户经理列表<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getCustomerList/{actionType}")
	@ResponseBody
	public Map<String,Object> getCustomerList(@PathVariable("actionType") String actionType){
		Map<String,Object> ret = new HashMap<String,Object>();
		try{
			if(ActionType.ASSIGN.getKey().equals(actionType)||ActionType.CHANGE.getKey().equals(actionType)){
				User user = new User();
				//TODO 查询条件要完善
//				Map<String,Object> map=new HashMap<String,Object>();
				//map.put("oaOrgIdList", null);
//				CommonUtils.fillCompanyToMap(map);
				/*if(emp.getCompanyIdList() != null && emp.getCompanyIdList().size() > 0){
					//map.put("companyIdList", emp.getCompanyIdList());
	    		}else{
	    			List<String> companyIdList=new ArrayList<String>();
					companyIdList.add(emp.getBranchCompanyId());
					//map.put("companyIdList", companyIdList);
	    		}*/
				CommonUtils.fillBumpCompany(user);
				//查询要指派的人
				List<User>  list=userApi.queryListXd(user);
				//查询要指派的人
				ret.put("allManager", list);
			}
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}catch(Exception e){
			LOGGER.error(e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg,"系统异常");
		}
		return ret;
	}
	
	
	
	/**
	 * Handler:转移 获取分公司列表<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getZYCompanyList")
	@ResponseBody
	public Map<String,Object> getZYCompanyList(){
		Map<String,Object> ret = new HashMap<String,Object>();
		try{
			CrmIntentLoanUser vo = new CrmIntentLoanUser();
			 CommonUtils.fillCompany(vo);
	    	 List<UnionCompany> companyList = CommonUtils.getCompanyList(vo);
			ret.put("companyList", companyList);
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}catch(Exception e){
			LOGGER.error(e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg,"系统异常");
		}
		return ret;
	}
	
	
	/**
	 * Handler:转移 获取分公司下的客户经理<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getZYCompanyEmpList")
	@ResponseBody
	public Map<String,Object> getZYCompanyEmpList(String companyId){
		Map<String,Object> ret = new HashMap<String,Object>();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			User user = new User();
			user.setCompanyId(companyId);
			//查询要指派的人
			List<User>  employeeList=userApi.queryListXd(user);
			ret.put("LoanEmployeeList", employeeList);
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}catch(Exception e){
			LOGGER.error(e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg,"系统异常");
		}
		return ret;
	}
	
	
	
	
	
	/**
	 * Handler:客户回访<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/savefollowRecord")
	@ResponseBody
	public Map<String,Object> savefollowRecord(CrmIntentLoanUserRecord entity){
		Map<String,Object> ret = new HashMap<String,Object>();
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		ret.put(SystemConst.retMsg, "提交成功");
		try{
			//插入跟踪记录
			int flag = crmIntentLoanUserRecordService.savefollowRecord(entity);
		}catch(Exception e){
			LOGGER.error(e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "提交失败"));
		}
		return ret;
	}
	
	/**
	 * Handler:客户指派<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/assignRecordZP")
	@ResponseBody
	public Map<String,Object> assignRecordZP(CrmIntentLoanUserRecord entity){
		Map<String,Object> ret = new HashMap<String,Object>();
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		ret.put(SystemConst.retMsg, "提交成功");
		try{
			//插入跟踪记录
			int flag = crmIntentLoanUserRecordService.assignRecord(entity);
			System.out.println(flag);
		}catch(Exception e){
			LOGGER.error(e);
			e.printStackTrace();
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "提交失败"));
		}
		return ret;
	}
	
	
	/**
	 * Handler:客户转移<br/>
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/assignRecordChange")
	@ResponseBody
	public Map<String,Object> assignRecordChange(CrmIntentLoanUserRecord entity){
		Map<String,Object> ret = new HashMap<String,Object>();
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		ret.put(SystemConst.retMsg, "提交成功");
		try{
			//插入跟踪记录
			int flag = crmIntentLoanUserRecordService.assignRecord(entity);
			System.out.println(flag);
		}catch(Exception e){
			LOGGER.error(e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "提交失败"));
		}
		return ret;
	}
	
}

