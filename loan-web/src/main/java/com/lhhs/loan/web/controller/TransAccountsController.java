package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanPayPlanCompanyTemp;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.LoanTransAccounts;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.service.AutoIncrementService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.InvestCustomerService;
import com.lhhs.loan.service.LoanAccountInfoService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.TransAccountsService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.account.AccountTransactionService;

/**
 * @suncj
 *该Controller转账管理模块Controller
 */
@Controller
@RequestMapping("/transAccountsManager")
public class TransAccountsController {
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	private static final Logger LOGGER = Logger.getLogger(TransAccountsController.class);
	
	@Autowired
	private TransAccountsService transAccountsService;
	@Autowired
	private CustomerManagerService customerManagerService;//待付款
	@Autowired
	private static AutoIncrementService autoIncrementService;//自增主键
	@Autowired
	private AccountTransactionService accountTransactionService;//待付款计划列表预览
	@Autowired
	private AccountManagerService accountManagerService;//转账摘要
	@Autowired
	private LoanAccountInfoService accountInfoService;//转出账户id查询
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private InvestCustomerService investCustomerService;
	
	
	/**
	 * transAccountsList:固定理财转账记录查询
	 * @author suncj
	 * @param request
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transAccountsList")
	public ModelAndView transAccountsList(HttpServletRequest request,
			@RequestParam(name="transId",required=false)String transId,
			@RequestParam(name="createTime",required=false)String createTime,
			@RequestParam(name="createEndTime",required=false)String createEndTime,
			@RequestParam(name="outAccountCustProperty",required=false)String outAccountCustProperty,
			@RequestParam(name="outAccountMobile",required=false)String outAccountMobile,
			@RequestParam(name="outAccountRealName",required=false)String outAccountRealName,
			@RequestParam(name="outAccountCustId",required=false)String outAccountCustId,
			@RequestParam(name="outAccountRealSummary",required=false)String outAccountRealSummary,
			@RequestParam(name="transType",required=false)String transType,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("transId", transId);
		map.put("createTime", createTime);
		map.put("createEndTime", createEndTime);
		map.put("outAccountCustProperty", outAccountCustProperty);
		map.put("outAccountMobile", outAccountMobile);
		map.put("outAccountRealName", outAccountRealName);
		map.put("outAccountCustId", outAccountCustId);
		map.put("outAccountRealSummary", outAccountRealSummary);
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		ModelAndView model=getTransList(map,page,"1");//1：固定理财转账记录查询。其他：转账记账记录查询
		model.addObject("transId", transId);
		model.addObject("createTime", createTime);
		model.addObject("createEndTime", createEndTime);
		model.addObject("outAccountCustProperty", outAccountCustProperty);
		model.addObject("outAccountMobile", outAccountMobile);
		model.addObject("outAccountRealName", outAccountRealName);
		model.addObject("outAccountCustId", outAccountCustId);
		model.addObject("outAccountRealSummary", outAccountRealSummary);
		return model;
	}
	
	
	/**
	 * transManageList:转账记账记录查询
	 * @author suncj
	 * @param request
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transManagementList")
	public ModelAndView transManagementList(HttpServletRequest request,
			@RequestParam(name="transId",required=false)String transId,
			@RequestParam(name="createTime",required=false)String createTime,
			@RequestParam(name="createEndTime",required=false)String createEndTime,
			@RequestParam(name="outAccountCustProperty",required=false)String outAccountCustProperty,
			@RequestParam(name="outAccountMobile",required=false)String outAccountMobile,
			@RequestParam(name="outAccountRealName",required=false)String outAccountRealName,
			@RequestParam(name="outAccountCustId",required=false)String outAccountCustId,
			@RequestParam(name="outAccountRealSummary",required=false)String outAccountRealSummary,
			@RequestParam(name="transType",required=false)String transType,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		CommonUtils.fillCompanyToMap(map);
		map.put("transId", transId);
		map.put("createTime", createTime);
		map.put("createEndTime", createEndTime);
		map.put("outAccountCustProperty", outAccountCustProperty);
		map.put("outAccountMobile", outAccountMobile);
		map.put("outAccountRealName", outAccountRealName);
		map.put("outAccountCustId", outAccountCustId);
		map.put("outAccountRealSummary", outAccountRealSummary);
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		ModelAndView model=getTransList(map,page,"2");//1：固定理财转账记录查询。其他：转账记账记录查询
		model.addObject("transId", transId);
		model.addObject("createTime", createTime);
		model.addObject("createEndTime", createEndTime);
		model.addObject("outAccountCustProperty", outAccountCustProperty);
		model.addObject("outAccountMobile", outAccountMobile);
		model.addObject("outAccountRealName", outAccountRealName);
		model.addObject("outAccountCustId", outAccountCustId);
		model.addObject("outAccountRealSummary", outAccountRealSummary);
		return model;
	}
	
	/**
	 * getTransList:(查询转账记录). <br/>
	 * TODO(根据type==1： 查询固定理财转账记录;type==2： 查询转账记录).<br/>
	 * @author Administrator
	 * @param map
	 * @param type
	 * @return
	 * @since JDK 1.8
	 */
	public ModelAndView getTransList(Map<String, Object> map,Page page,String type){
		ModelAndView model=new ModelAndView();
		if("1".equals(type)){
			model.setViewName("system/transAccounts/transAccountsList");
			map.put("transType",SystemConst.TransType.TYPEID1007);
		}else{
			model.setViewName("system/transAccounts/transManageList");
			map.put("transType",SystemConst.TransType.TYPEID1008);
		}
		map.put("examineStatus","examineStatus");
		CommonUtils.fillCompanyToMap(map);
		List<LoanTransAccounts> transAccountsList=transAccountsService.transAccountsList(map,page);
		if(transAccountsList!=null&&transAccountsList.size()>0){
			BigDecimal financialAmountSum = new BigDecimal(0);//固定理财转账-理财金额计算
			BigDecimal inAmountSum = new BigDecimal(0);//转账申请发生额总额计算
			for (int i = 0; i < transAccountsList.size(); i++) {
				LoanTransAccounts amount = transAccountsList.get(i);
				if("1".equals(type)){//固定理财转账-理财金额计算
					BigDecimal financialAmount = amount.getFinancialAmount();
					if (financialAmount!=null) {
						financialAmountSum = financialAmountSum.add(financialAmount);
					}
				}else{
					BigDecimal inAmount = amount.getInAccount();
					if (inAmount!=null) {
						inAmountSum = inAmountSum.add(inAmount);
					}
				}
			}
			model.addObject("financialAmountSum", financialAmountSum);
			model.addObject("inAmountSum", inAmountSum);
		}
		//客户性质
		List<Map<String, Object>> accountCustProperty=transAccountsService.queryAccountCustProperty(map);
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,107,108,111");
		List<LoanAccountsSubjectInfo> accountsSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm);
		model.addObject("page", page);
		model.addObject("transAccountsList", transAccountsList);
		model.addObject("accountCustProperty", accountCustProperty);
		model.addObject("outAccountSubject", accountsSubjectInfoList);
		return model;
	}
	
	/**
	 * 
	 * totransAccountsAdd:(跳转到转账管理页面). <br/>
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/totransAccountsAdd")
	public ModelAndView totransAccountsAdd(HttpServletRequest request,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		List<Map<String, Object>> allAccountCustType=transAccountsService.queryAccountCustType();
		List<Map<String, Object>> accountCustProperty=transAccountsService.queryAccountCustProperty(map);
		String transId=GetUniqueNoUtil.getOrderNo();//固定理财申请id=业务编号
		request.setAttribute("allAccountCustType", allAccountCustType);
		request.setAttribute("accountCustProperty", accountCustProperty);
		request.setAttribute("transId", transId);
		Map<String, Object> param=  new HashMap<String, Object>();
		param.put("status", "1");
		CommonUtils.fillCompanyToMap(param);
		List<Dept> deptList = CommonUtils.getDeptList("");
		ModelAndView model=new ModelAndView("system/transAccounts/transAccountsAdd");
		model.addObject("page", page);
		model.addObject("deptList", deptList);
		return model;
	}
	
	
	/**
	 * 
	 * getCustPropertyByTypeId:(根据客户类型查询客户性质). <br/>
	 * @author Administrator
	 * @param request
	 * @param 客户类型ID bigCategory
	 * @param dictionary
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getCustPropertyByTypeId")
	@ResponseBody
	public Map<String,Object> getCustPropertyByTypeId(HttpServletRequest request,LoanDictionary dictionary) {
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String, Object> resultMap=new HashMap<String,Object>();
		params.put("bigCategory", dictionary.getBigCategory());
		params.put("invest_customer_nature", dictionary.getCategory());
		List<LoanDictionary> custPropertyList = transAccountsService.getCustPropertyByTypeId(params);
		resultMap.put("custPropertyList",custPropertyList);
		return resultMap;	
	}
	
	/**
	 * 
	 * empAddOrUpdate:( 转账记账管理-申请). <br/>
	 * @author Administrator
	 * @param request
	 * @param LoanTransAccounts transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toTransferManagementAdd")
	public ModelAndView toTransferManagementAdd(HttpServletRequest request,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		List<Map<String, Object>> allAccountCustType=transAccountsService.queryAccountCustType();
		List<Map<String, Object>> accountCustProperty=transAccountsService.queryAccountCustProperty(map);
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("IN");
		parm.setSubjectTypes("105,106,107,108,111");
		List<LoanAccountsSubjectInfo> accountsSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm);
		parm.setDirection("OUT");
		List<LoanAccountsSubjectInfo> outSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm);
		String transId=GetUniqueNoUtil.getOrderNo();//固定理财申请id=业务编号
		request.setAttribute("transId", transId);
		request.setAttribute("allAccountCustType", allAccountCustType);
		request.setAttribute("accountCustProperty", accountCustProperty);
		request.setAttribute("inAccountSubject", accountsSubjectInfoList);
		request.setAttribute("outAccountSubject", outSubjectInfoList);
		ModelAndView model=new ModelAndView("system/transAccounts/transManageAdd");
		model.addObject("page", page);
		return model;
	}
	
	/**
	 * 
	 * TotransAccountsById:(固定理财转账记录详情\转账记账记录详情). <br/>
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/TotransAccountsById")
	public ModelAndView TotransAccountsById(HttpServletRequest request,@RequestParam(name="transId",required=false)String transId
			,@RequestParam(name="transType",required=false)String transType
			,@RequestParam(name="examine",required=false)String examine
			){
		ModelAndView model=new ModelAndView();
		LoanTransAccounts transAccountInfo=(LoanTransAccounts) transAccountsService.get(transId);
		if("1".equals(transType)){
			if("examine".equals(examine)){//guding审核--查询代付款信息
				model.setViewName("system/transAccounts/transAccountsExamineInfo");
			}else{
				model.setViewName("system/transAccounts/transAccountsInfo");
			}
			List<LoanPayPlanCompanyTemp> payPlanlist=transAccountsService.getPayPlanCompanyTemp(transId);
			model.addObject("payPlanlist", payPlanlist);
		}else{
			if("examine".equals(examine)){//审核--查询代付款信息
				model.setViewName("system/transAccounts/transManageExamineInfo");
			}else{
				model.setViewName("system/transAccounts/transManagerInfo");
			}
		}
		model.addObject("transAccountInfo", transAccountInfo);
		return model;
	}
	
	/**
	 * 
	 * checkRechargeOrderNo:根据充值订单编号查询相关信息 - 如果存在 进行回显
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/checkRechargeOrderNo")
	public ModelAndView checkRechargeOrderNo(HttpServletRequest request,
			@RequestParam(name="orderNo",required=false)String orderNo
			){
		ModelAndView model=new ModelAndView();
		LoanTransAccounts transAccountInfo=(LoanTransAccounts) transAccountsService.get(orderNo);
		model.setViewName("system/transAccounts/transAccountsAdd");
		model.addObject("transAccountInfo", transAccountInfo);
		return model;
	}
	
	
	/**
	 * 
	 * totransAccountsExport:(转账管理). <br/>
	 * TODO(transType 其他：转账记账记录导出列表).<br/>
	 * TODO(transType 1:固定理财记录导出列表).<br/>
	 * @author Administrator
	 * @param request
	 * @param response
	 * @since JDK 1.8
	 */
	@RequestMapping("/totransAccountsExport")
	@ResponseBody
	public void totransAccountsExport(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="transId",required=false)String transId,
			@RequestParam(name="createTime",required=false)String createTime,
			@RequestParam(name="createEndTime",required=false)String createEndTime,
			@RequestParam(name="outAccountCustProperty",required=false)String outAccountCustProperty,
			@RequestParam(name="outAccountMobile",required=false)String outAccountMobile,
			@RequestParam(name="outAccountRealName",required=false)String outAccountRealName,
			@RequestParam(name="outAccountCustId",required=false)String outAccountCustId,
			@RequestParam(name="outAccountRealSummary",required=false)String outAccountRealSummary,
			@RequestParam(name="transType",required=false)String transType
			){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("transId", transId);
		map.put("createTime", createTime);
		map.put("createEndTime", createEndTime);
		map.put("outAccountCustProperty", outAccountCustProperty);
		map.put("outAccountMobile", outAccountMobile);
		map.put("outAccountRealName", outAccountRealName);
		map.put("outAccountCustId", outAccountCustId);
		map.put("outAccountRealSummary", outAccountRealSummary);
		map.put("transType", "1".equals(transType)?SystemConst.TransType.TYPEID1007:SystemConst.TransType.TYPEID1008);
		map.put("examineStatus", "examineStatus");
		CommonUtils.fillCompanyToMap(map);
		List<LoanTransAccounts> list=transAccountsService.transAccountsList(map,null);
		if(list.size()>0){
			for(LoanTransAccounts  transAccounts:list){
				if("90".equals(transAccounts.getStatus())){
					transAccounts.setStatus("成功");
				}else{
					transAccounts.setStatus("失败");
				}
				if("1".equals(transType)){
					transAccounts.setFinancialAmount((new BigDecimal(new DecimalFormat("0.00").format(transAccounts.getFinancialAmount()))));
				}else{
					transAccounts.setInAccount(new BigDecimal(new DecimalFormat("0.00").format(transAccounts.getInAccount())));
				}
			}
		}
		String fileName="转账记账记录";
		String financialAmount="转账金额";
		Map<String, String> titles=new LinkedHashMap<String, String>();
		if("1".equals(transType)){
			fileName="固定理财转账记录";
			financialAmount="理财金额";
		}
	    titles.put("transId", "交易ID");
	    titles.put("outPropertyName", "转出账号客户性质");
	    titles.put("outAccountRealName", "转出账户名称");
	    titles.put("inPropertyName", "转入账号客户性质");
	    titles.put("inAccountRealName", "收款账户名称");
	    if("1".equals(transType)){
	    	titles.put("financialAmount", financialAmount);
	    }else{
	    	titles.put("inAccount", financialAmount);
	    }
	    titles.put("createTime", "申请时间");
	    titles.put("leStaffName", "申请人");
	    titles.put("taskEndDate", "审核时间");
	    titles.put("assigneeName", "审核人");
	    titles.put("status", "审核结果");
	    AnalyseTransExcel.downLoadExcel(request, response, fileName, list, LoanTransAccounts.class, titles);
	    System.out.println(fileName+"下载完成");   
	}
	
	/////////////////////////////////////////////////////审核 start//////////////////////////////////
	/**
	 * transAccountsExamine:(固定理财转账审核列表查询). <br/>
	 * @author suncj
	 * @param request
	 * @param type: 1:固定理财审核;2:转账审核
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transAccountsExamine")
	public ModelAndView transAccountsExamine(HttpServletRequest request,
			@RequestParam(name="transId",required=false)String transId,
			@RequestParam(name="createTime",required=false)String createTime,
			@RequestParam(name="createEndTime",required=false)String createEndTime,
			@RequestParam(name="outAccountCustProperty",required=false)String outAccountCustProperty,
			@RequestParam(name="outAccountMobile",required=false)String outAccountMobile,
			@RequestParam(name="outAccountRealName",required=false)String outAccountRealName,
			@RequestParam(name="outAccountCustId",required=false)String outAccountCustId,
			@RequestParam(name="outAccountRealSummary",required=false)String outAccountRealSummary,
			@RequestParam(name="transType",required=false)String transType,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		 //获取当前登录人 如果登录人不为空且公司id 集团id 不为空 且 集团id！=公司id情况下
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();

		 if(user!=null && user.getCompanyId()!=null && user.getUnionId()!=null
				        && !user.getCompanyId().equals(user.getUnionId())){
			 map.put("companyId", user.getCompanyId());
			            request.setAttribute("isadmin", "admin");
		 }
		CommonUtils.fillCompanyToMap(map);
		map.put("transId", transId);
		map.put("createTime", createTime);
		map.put("createEndTime", createEndTime);
		map.put("outAccountCustProperty", outAccountCustProperty);
		map.put("outAccountMobile", outAccountMobile);
		map.put("outAccountRealName", outAccountRealName);
		map.put("outAccountCustId", outAccountCustId);
		map.put("outAccountRealSummary", outAccountRealSummary);
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		ModelAndView model=getTransExamineList(map,page,"1");
		model.addObject("transId", transId);
		model.addObject("createTime", createTime);
		model.addObject("createEndTime", createEndTime);
		model.addObject("outAccountCustProperty", outAccountCustProperty);
		model.addObject("outAccountMobile", outAccountMobile);
		model.addObject("outAccountRealName", outAccountRealName);
		model.addObject("outAccountCustId", outAccountCustId);
		model.addObject("outAccountRealSummary", outAccountRealSummary);
		return model;
	}
	
	
	/**
	 * transManageExamine:(转账记账审核列表查询). <br/>
	 * @author suncj
	 * @param request
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transManageExamine")
	public ModelAndView transManageExamine(HttpServletRequest request,
			@RequestParam(name="transId",required=false)String transId,
			@RequestParam(name="createTime",required=false)String createTime,
			@RequestParam(name="createEndTime",required=false)String createEndTime,
			@RequestParam(name="outAccountCustProperty",required=false)String outAccountCustProperty,
			@RequestParam(name="outAccountMobile",required=false)String outAccountMobile,
			@RequestParam(name="outAccountRealName",required=false)String outAccountRealName,
			@RequestParam(name="outAccountCustId",required=false)String outAccountCustId,
			@RequestParam(name="outAccountRealSummary",required=false)String outAccountRealSummary,
			@RequestParam(name="transType",required=false)String transType,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		Map<String, Object> map=new HashMap<String,Object>();
		CommonUtils.fillCompanyToMap(map);
		map.put("transId", transId);
		map.put("createTime", createTime);
		map.put("createEndTime", createEndTime);
		map.put("outAccountCustProperty", outAccountCustProperty);
		map.put("outAccountMobile", outAccountMobile);
		map.put("outAccountRealName", outAccountRealName);
		map.put("outAccountCustId", outAccountCustId);
		map.put("outAccountRealSummary", outAccountRealSummary);
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		ModelAndView model=getTransExamineList(map,page,transType);
		model.addObject("transId", transId);
		model.addObject("createTime", createTime);
		model.addObject("createEndTime", createEndTime);
		model.addObject("outAccountCustProperty", outAccountCustProperty);
		model.addObject("outAccountMobile", outAccountMobile);
		model.addObject("outAccountRealName", outAccountRealName);
		model.addObject("outAccountCustId", outAccountCustId);
		model.addObject("outAccountRealSummary", outAccountRealSummary);
		return model;
	}
	
	
	/**
	 * 
	 * getTransList:(查询转账记录). <br/>
	 * TODO(根据type==1： 固定理财转账审核;type==2： 查询转账审核).<br/>
	 * @author Administrator
	 * @param map
	 * @param type
	 * @return
	 * @since JDK 1.8
	 */
	public ModelAndView getTransExamineList(Map<String, Object> map,Page page,String type){
		ModelAndView model=new ModelAndView();
		if("1".equals(type)){//固定理财转账审核
			model.setViewName("system/transAccounts/transAccountsExamine");
			map.put("transType",SystemConst.TransType.TYPEID1007);
		}else{//转账记账管理审核
			map.put("transType",SystemConst.TransType.TYPEID1008);
			model.setViewName("system/transAccounts/transManageExamine");
		}
		map.put("status", SystemConst.Status.STATUS93);//待审核
		List<LoanTransAccounts> transExamineList=transAccountsService.transAccountsList(map,page);
		//客户性质
		List<Map<String, Object>> accountCustProperty=transAccountsService.queryAccountCustProperty(map);
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,107,108,111");
		List<LoanAccountsSubjectInfo> accountsSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm);
		if(transExamineList.size()>0){
			BigDecimal financialAmountSum = new BigDecimal(0);//固定理财转账-理财金额计算
			BigDecimal inAmountSum = new BigDecimal(0);//转账申请发生额总额计算
			for (int i = 0; i < transExamineList.size(); i++) {
				LoanTransAccounts amount = transExamineList.get(i);
				if("1".equals(type)){//固定理财转账-理财金额计算
					BigDecimal financialAmount = amount.getFinancialAmount();
					if (financialAmount!=null) {
						financialAmountSum = financialAmountSum.add(financialAmount);
					}
				}else{
					BigDecimal inAmount = amount.getInAccount();
					if (inAmount!=null) {
						inAmountSum = inAmountSum.add(inAmount);
					}
				}
			}
			model.addObject("financialAmountSum", financialAmountSum);
			model.addObject("inAmountSum", inAmountSum);
		}
		model.addObject("page", page);
		model.addObject("transExamineList", transExamineList);
		model.addObject("outAccountSubject", accountsSubjectInfoList);
		model.addObject("accountCustProperty", accountCustProperty);
		return model;
	}
	
	
	/**
	 * transExamineAdd:固定理财转账审核\转账记账审核
	 * @author suncj
	 * @param request
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transExamineAdd")
	@ResponseBody 
	public Map<String, Object> transExamineAdd(HttpServletRequest request,LoanTransAccounts transAccounts){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap =transAccountsService.transExamineAdd(transAccounts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	/**
	 * queryCustomerInfo:转账记账申请-用户id或公司id ---- 检索账户信息
	 * @author suncj
	 * @param request
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryCustomerInfo")
	@ResponseBody 
	public Map<String, Object> queryCustomerInfo(HttpServletRequest request,
			@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "outCustType", required = false) String outCustType,
			@RequestParam(value = "outCustProperty", required = false) String outCustProperty){
		Map<String, Object>  map= new HashMap<String, Object>();
		map.put("custId", custId);
		map.put("outCustType", outCustType);
		map.put("outCustProperty", outCustProperty);
		return transAccountsService.getAccountsByCustId(map);
	}
	
	/**
	 * getTransAccountsInfo:固定理财转账申请-根据充值订单编号 检索转账信息
	 * @author suncj
	 * @param request
	 * @param rechargeOrderNo 充值订单编号
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getTransAccountsInfo")
	@ResponseBody 
	public Map<String, Object> getTransAccountsInfo(HttpServletRequest request,
			@RequestParam(value = "rechargeOrderNo", required = false) String rechargeOrderNo){
		return transAccountsService.getAccountInOutInfo(rechargeOrderNo);
	}
	/**
	 * getTransObligations:固定理财转账申请-查询待付款预览
	 * @author suncj
	 * @param request
	 * @param LoanTransAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getTransObligations")
	public String getTransObligations(HttpServletRequest request,LoanTransAccounts transAccounts){
		if(transAccounts==null||StringUtils.isEmpty(transAccounts.getTransId())||StringUtils.isEmpty(transAccounts.getFinancialTerm().toString())||StringUtils.isEmpty(transAccounts.getFinancialTermUnit())
		   ||StringUtils.isEmpty(transAccounts.getInterestPaymentMethod())||StringUtils.isEmpty(transAccounts.getActualLendingTime())
		   ||StringUtils.isEmpty(transAccounts.getRepaymentMethod())||StringUtils.isEmpty(transAccounts.getFinancialInterestRate())
		   ||StringUtils.isEmpty(transAccounts.getFinancialAmount())||StringUtils.isEmpty(transAccounts.getFinancialInterestRateUnit())){
			//报单编号//理财期限//理财期限单位//放款时间//付息方式//还款方式//理财利率//理财金额//理财利率单位
			request.setAttribute("errorTxt", "生成待付款重要信息不能为空！");
			return "alertError";
		}
		LoanAccountInfo outAccountInfo=accountInfoService.selectByOwnerId(transAccounts.getOutAccountCustId());//转出账户id查询
		if(outAccountInfo==null||StringUtils.isEmpty(outAccountInfo.getAccountId())){
			request.setAttribute("errorTxt", "转出账户id不能为空！");
			return "alertError";
		}
		transAccounts.setAccountId(outAccountInfo.getAccountId());
		LoanTransMain transMain=new LoanTransMain();
		LoanRecordTemp recordTemp=new LoanRecordTemp();
	    //*****************************************//
		transMain.setBorrower(transAccounts.getInAccountRealName()==null||"".equals(transAccounts.getInAccountRealName())?"":transAccounts.getInAccountRealName());//借款人姓名(收款客户姓名)
		transMain.setBorrowerId(transAccounts.getInAccountCustId()==null||"".equals(transAccounts.getInAccountCustId())?"":transAccounts.getInAccountCustId());//借款人编号(收款客户ID)
		transMain.setOverTime(transAccounts.getContractEndTime());//合同截止日期
		transMain.setAmount(transAccounts.getFinancialAmount()); //借款(理财)金额
		transMain.setTerm(Short.valueOf(transAccounts.getFinancialTerm()==null||"".equals(transAccounts.getFinancialTerm())?"":transAccounts.getFinancialTerm().toString()));//理财期限
		transMain.setTermUnit(transAccounts.getFinancialTermUnit()==null||"".equals(transAccounts.getFinancialTermUnit())?"": transAccounts.getFinancialTermUnit());//理财期限单位
		if(SystemConst.InterestWay.SPECIFIED_DATE.equals(transAccounts.getInterestPaymentMethod())){
			transMain.setDateOfPayInterest(transAccounts.getAppointLendingTime()==null?null:transAccounts.getAppointLendingTime());//指定日付息日期
		}
		transMain.setBorrowerAccountId(transAccounts.getAccountId());
		transMain.setBorrowCustomerType(transAccounts.getInTypeName()==null||"".equals(transAccounts.getInTypeName())?"": transAccounts.getInTypeName());//borrow_customer_type  借款人类型 
		transMain.setPeriodTotal(Short.valueOf(transAccounts.getFinancialTerm()==null||"".equals(transAccounts.getFinancialTerm())?"":transAccounts.getFinancialTerm().toString()));//总期数
		transMain.setOverRate(transAccounts.getOverduePenaltyInterestRate()==null||"".equals(transAccounts.getOverduePenaltyInterestRate())?"":transAccounts.getOverduePenaltyInterestRate().toString());//逾期贷款利率--逾期罚息率
		transMain.setBreachRate(transAccounts.getRepaymentPenaltyInterestRate()==null||"".equals(transAccounts.getRepaymentPenaltyInterestRate().toString())?"": transAccounts.getRepaymentPenaltyInterestRate().toString());//breach_rate 还款违约金率
		transMain.setSetRemark(transAccounts.getRemarks()==null||"".equals(transAccounts.getRemarks())?"": transAccounts.getRemarks());
		transMain.setAccountManager(transAccounts.getAccountManagerName()==null||"".equals(transAccounts.getAccountManagerName())?"": transAccounts.getAccountManagerName());
		transMain.setDepartment(transAccounts.getAccountManagerDepartmentNo()==null||"".equals(transAccounts.getAccountManagerDepartmentNo())?"": transAccounts.getAccountManagerDepartmentNo());
		transMain.setCompanyId(transAccounts.getCompanyId()==null||"".equals(transAccounts.getCompanyId())?"": transAccounts.getCompanyId());
		transMain.setPayerId(transAccounts.getOutAccountCustId()==null||"".equals(transAccounts.getOutAccountCustId())?"": transAccounts.getOutAccountCustId());//payer_id 转出方客户编号
		transMain.setPayerName(transAccounts.getRepaymentPenaltyInterestRate()==null||"".equals(transAccounts.getOutAccountRealName())?"": transAccounts.getOutAccountRealName());//转出方客户名称
		transMain.setCreateUser(transAccounts.getCreateUser()==null||"".equals(transAccounts.getCreateUser())?"":transAccounts.getCreateUser());
		transMain.setCreateTime(transAccounts.getCreateTime());
		transMain.setLastUser(transAccounts.getLastUser()==null||"".equals(transAccounts.getLastUser())?"":transAccounts.getLastUser());
		//payer_account_id 转出方账户id
		//*****************************************//
		transMain.setBusinessId(transAccounts.getTransId());//报单编号
		transMain.setOrderNo(transAccounts.getRechargeOrderNo()==null||"".equals(transAccounts.getRechargeOrderNo())?"":transAccounts.getRechargeOrderNo());
		transMain.setTerm(Short.valueOf(transAccounts.getFinancialTerm()==null||"".equals(transAccounts.getFinancialTerm())?"":transAccounts.getFinancialTerm().toString()));//理财期限
		transMain.setTermUnit(transAccounts.getFinancialTermUnit()==null||"".equals(transAccounts.getFinancialTermUnit())?"": transAccounts.getFinancialTermUnit());//理财期限单位
		transMain.setPayInterestWay(transAccounts.getInterestPaymentMethod()==null||"".equals(transAccounts.getInterestPaymentMethod())?"": transAccounts.getInterestPaymentMethod());//付息方式
		transMain.setLendingTime(transAccounts.getActualLendingTime());//放款时间
		transMain.setRepaymentMethod(transAccounts.getRepaymentMethod());//还款方式
		transMain.setRate(transAccounts.getFinancialInterestRate());//理财利率
		recordTemp.setLoanRate(transAccounts.getFinancialInterestRate());//理财利率
		recordTemp.setLoanAmount(transAccounts.getFinancialAmount());//理财金额
		recordTemp.setLoanRateUnit(transAccounts.getFinancialInterestRateUnit()==null||"".equals(transAccounts.getFinancialInterestRateUnit())?"": transAccounts.getFinancialInterestRateUnit());//理财利率单位
		recordTemp.setAccountId(transAccounts.getAccountId());//账户ID
		recordTemp.setLenderId(transAccounts.getOutAccountCustId());//出借人编号--转出账户
		recordTemp.setLenderName(transAccounts.getOutAccountRealName());//出借人姓名--转出账户
		List<LoanRecordTemp> listLoanRecordTemp=new ArrayList<LoanRecordTemp>();
		listLoanRecordTemp.add(recordTemp);
		transMain.setListLoanRecordTemp(listLoanRecordTemp);
		transMain.setTransType(SystemConst.TransType.TYPEID1007);
		LoanTransMain loanTransMain=null;
		try {
			loanTransMain=accountTransactionService.loanTransPre(transMain);
		} catch (Exception e) {
			request.setAttribute("errorTxt",CommonUtils.errorMessageHandler(e.getMessage(), "固定理财转账预交易异常"));
			return "alertError";
		}
		if(loanTransMain!=null){
			//客户性质
			Map<String, Object> map=new HashMap<String,Object>();
			if(StrUtils.isNullOrEmpty(transAccounts.getOutPropertyName())){
				map.put("typeId", transAccounts.getOutAccountCustPropertySed());
				List<Map<String, Object>> accountCustProperty=transAccountsService.queryAccountCustProperty(map);
				if(accountCustProperty.size()>0){
					for(int i=0;i<accountCustProperty.size();i++){
						transAccounts.setOutPropertyName(accountCustProperty.get(i).get("type_name").toString());
					}
				}
			}
			List<LoanPayPlanCompanyTemp> payPlanlist=loanTransMain.getListLoanPayPlanCompanyTemp();
			if(loanTransMain.getListLoanPayPlanCompanyTemp().size()>0){
				for(LoanPayPlanCompanyTemp payPlan:payPlanlist){
					payPlan.setCustomerName(transAccounts.getOutAccountRealName());//出借人姓名
					payPlan.setOutPropertyName(transAccounts.getOutPropertyName());//客户性质
					payPlan.setFinancialAmount(transAccounts.getFinancialAmount());//理财金额
					payPlan.setFinancialInterestRate(transAccounts.getFinancialInterestRate());//理财利率
					payPlan.setFinancialInterestRateUnit(transAccounts.getFinancialInterestRateUnit());//理财利率单位
					payPlan.setActualLendingTime(transAccounts.getActualLendingTime());//放款时间
					payPlan.setContractEndTime(transAccounts.getContractEndTime());//合同截止日期
					payPlan.setAccountManagerDepartmentName(transAccounts.getAccountManagerDepartmentName());//部门
					payPlan.setAccountManagerName(transAccounts.getAccountManagerName());//客户经理
					payPlan.setFinancialTerm(transAccounts.getFinancialTerm());//理财期限
				}
			}
			request.setAttribute("payPlanlist", payPlanlist);
		}else{
			throw new RuntimeException("生成待付款列表信息失败！"); 
		}
		request.setAttribute("transAccounts", transAccounts);
		return "system/transAccounts/transObligationsList";
	}
	
	/**
	 * transAccountsAdd:(转账申请). <br/>
	 * @author Administrator
	 * @param request
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transAccountsAdd")
	@ResponseBody
	public Map<String, Object> transAccountsAdd(HttpServletRequest request,LoanTransAccounts transAccounts){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			resultMap=transAccountsService.transAccountsAdd(transAccounts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	/**
	 * 
	 * queryCustomerId:校验用户id 是否存在--即账户所有者编码
	 * @author suncj
	 * @param request
	 * @param custId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryCustomerId")
	@ResponseBody
	public int queryCustomerId(HttpServletRequest request,@RequestParam(name="custId",required=false)String custId){
		int count=0;
		try {
			 count=transAccountsService.selectByCustId(custId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * companyManagerNoLink:部门-客户经理级联查询
	 * @author suncj
	 * @param request
	 * @param affiliatedCompanyId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryCusmanagerList")
	@ResponseBody
	public Map<String, Object> queryCusmanagerList(HttpServletRequest request,String accountManagerDepartmentNo){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<Map<String, Object>> list=transAccountsService.queryAllManager(accountManagerDepartmentNo);
		resultMap.put("groupList", list);
		return resultMap;
	}
}
