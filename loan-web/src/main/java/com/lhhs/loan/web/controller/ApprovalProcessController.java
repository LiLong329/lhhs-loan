package com.lhhs.loan.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.enums.Quarters;
import com.lhhs.loan.common.jedis.JedisComponent;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.AuditingHandledType;
import com.lhhs.loan.common.shared.enums.AuditingNodeType;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanDictionaryMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanExTaskMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanFundTask;
import com.lhhs.loan.dao.domain.LoanFundTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanQuarters;
import com.lhhs.loan.dao.domain.vo.LoanOrderInfoVo;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.CapitalInfoService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CustomerManagerService;
import com.lhhs.loan.service.MortgageInfoService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.account.AccountTransactionService;
import com.lhhs.loan.service.transport.MortgageInfoTransService;
import com.lhhs.loan.service.transport.OrderInfoService;

/**
 * 业务操作Controller
 * 包含:
 * 	1、待办事项
 * 	2、已办事项
 * @author kernel.org
 *
 */
@Controller
@RequestMapping("/approval")
@SuppressWarnings("all")
public class ApprovalProcessController {

	private String LOAN_LOCK_PREFIX = "loan_lock_";//订单锁前缀
	
	private String LOAN_LOCK_ID_PREFIX = "loan_lock_id_";//订单锁员工id前缀
	
	private static final int PAGESIZE = 10;
	
	Page page = new Page(PAGESIZE);
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ApprovalProcessController.class);
	
	@Autowired
	private ApprovalProcessService approvalProcessService;
	
	@Autowired
	private MortgageInfoService mortgageInfoService;
	
	@Autowired
	private MortgageInfoTransService mortgageInfoTransService;
	
	@Autowired
	private JedisComponent jedisComponent;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanDictionaryMapper dictionaryMapper;
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private CustomerManagerService customerManagerService;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private CapitalInfoService capitalInfoService;
	@Autowired
	private SystemManagerService systemManager;
	
	
	/**
	 * 订单审核授权判断
	 * @param request
	 * @param response
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/viewOrderDetail")
	@ResponseBody
	public Map<String, Object> viewOrderDetail(HttpServletRequest request, HttpServletResponse response,
											   @RequestParam(value = "orderNo",required = false) String orderNo){
		Map<String, Object> result = new HashMap<String, Object>();
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		LoanOrderInfo loanOrderInfo = approvalProcessService.selectByPrimaryKey(orderNo);
		//面审会签
		if((int)loanOrderInfo.getOrderStatus() == (AuditingNodeType.MS.getIndex())&&loanOrderInfo.getFundFlowNode() != null && loanOrderInfo.getFundFlowNode() != 0){
			if((int)loanOrderInfo.getFundFlowNode() != (int)loanEmp.getLeEmpId()){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u5f53\u524d\u8ba2\u5355\u6b63\u5728\u4f1a\u7b7e\u72b6\u6001\u4e2d\uff0c\u8bf7\u7a0d\u5019\u002e\u002e\u002e");// 当前订单正在会签状态中，请稍候...！
				return result;
			}
		}
		//终审会签
		if((int)loanOrderInfo.getOrderStatus() == (AuditingNodeType.ZS.getIndex()) && loanOrderInfo.getFundFlowNode() != null && loanOrderInfo.getFundFlowNode() != 0){
			List<String> idList = loanEmp.getCompanyIdList();
			if(idList != null && idList.size() > 0 && idList.contains(loanOrderInfo.getCompanyId())){
				if(!StringUtils.isEmpty(Quarters.getName(loanOrderInfo.getFundFlowNode()))||loanEmp.getLeEmpId().intValue()==loanOrderInfo.getFundFlowNode().intValue()){
					loanOrderInfo.setFundFlowNode(loanEmp.getLeEmpId());
					approvalProcessService.updateOrderInfo(loanOrderInfo);
				}else{
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "\u5f53\u524d\u8ba2\u5355\u6b63\u5728\u4f1a\u7b7e\u72b6\u6001\u4e2d\uff0c\u8bf7\u7a0d\u5019\u002e\u002e\u002e");// 当前订单正在会签状态中，请稍候...！
					return result;
				}
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "对不起，您没有该订单的审核权限...");
				return result;
			}
		}
		//放款审批会签
		if((int)loanOrderInfo.getOrderStatus() == (AuditingNodeType.FKSH.getIndex()) && loanOrderInfo.getFundFlowNode() != null && loanOrderInfo.getFundFlowNode() != 0){
			List<String> idList = loanEmp.getCompanyIdList();
			if(idList != null && idList.size() > 0 && idList.contains(loanOrderInfo.getCompanyId())){
				if(!StringUtils.isEmpty(Quarters.getName(loanOrderInfo.getFundFlowNode()))){
					loanOrderInfo.setFundFlowNode(loanEmp.getLeEmpId());
					approvalProcessService.updateOrderInfo(loanOrderInfo);
				}else{
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "\u5f53\u524d\u8ba2\u5355\u6b63\u5728\u4f1a\u7b7e\u72b6\u6001\u4e2d\uff0c\u8bf7\u7a0d\u5019\u002e\u002e\u002e");// 当前订单正在会签状态中，请稍候...！
					return result;
				}
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "对不起，您没有该订单的审核权限...");
				return result;
			}
		}
		//放款确认（判断是不是结算部和权证部的）
		if((int)loanOrderInfo.getOrderStatus() == AuditingNodeType.FKQR.getIndex()){
			LoanDept loanDept = approvalProcessService.selectLoanDeptByLdDeptId(loanEmp.getLeDeptId());
			if(loanDept != null){
				if("结算部".equals(loanDept.getLdName()) || "权证部".equals(loanDept.getLdName())){
					if("结算部".equals(loanDept.getLdName()) && (int)loanOrderInfo.getAuditStatus() == 1){
						result.put(SystemConst.retCode, SystemConst.FAIL);
						result.put(SystemConst.retMsg,"此订单已通过结算部审核");
						return result;
					}
					if("权证部".equals(loanDept.getLdName()) && (int)loanOrderInfo.getAuditStatus() == 0){
						result.put(SystemConst.retCode, SystemConst.FAIL);
						result.put(SystemConst.retMsg,"此订单未通过结算部审核，请稍后再试...");
						return result;
					}
				}else{
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg,"对不起，您没有该订单的审核权限...");
					return result;
				}
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg,"审核数据有误...");
				return result;
			}
		}
		boolean flag = true;
		if((int)loanOrderInfo.getOrderStatus() != AuditingNodeType.FKQR.getIndex()){
			flag = jedisComponent.lockOrder(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId(), 3600);
		}
		if(!StrUtils.isNullOrEmpty(orderNo) && flag){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u6388\u6743\u6210\u529f\uff01");// 授权成功！
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u8BE5\u8BA2\u5355\u6B63\u5728\u88AB\u5BA1\u6838\uFF01");//该订单正在被审核！
		}
		return result;
	}
	
	/**
	 * 查询我的待办任务列表
	 * @param request
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/selectMyNewTaskList/{approvalNode}")
	public String selectMyNewTaskList(HttpServletRequest request, 
			@PathVariable("approvalNode") Integer approvalNode,  
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber){
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询贷前系统任务列表
		List<Map<String, Object>> myNewTaskList = null;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		String leCity = loanEmp.getLeCity();
		String leAccount = loanEmp.getLeAccount();
		if(loanEmp != null){
			params.put("page", page);
			if(leAccount.equals("admin")){
				params.put("leAccount", "");
			}else{
				params.put("leAccount", leAccount);
			}
			params.put("approvalNode", approvalNode);
			if(!(StrUtils.isNullOrEmpty(leCity) || "".equals(leCity))){
				params.put("city", leCity);
			}
			CommonUtils.fillCompanyToMap(params);
			myNewTaskList = approvalProcessService.selectMyNewTaskList(params, page);
		}
		request.setAttribute("myNewTaskList", myNewTaskList);
		request.setAttribute("page", page);
		request.setAttribute("node", approvalNode);
		return "auditing/bussinessLoanManagement";
	}
	
	/**
	 * selectLoanApplyList:财务管理放款申请列表显示(放款申请、放款审核、放款确认)
	 * @author kernel.org
	 * @param request
	 * @param loanOrderInfoVo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/selectLoanApplyList/{approvalNode}")
	public String selectLoanApplyList(HttpServletRequest request,
									  @PathVariable("approvalNode") Integer approvalNode,
									  LoanOrderInfoVo entity){
		CommonUtils.fillCompany(entity);
		entity.setApprovalNode(approvalNode);
		page = approvalProcessService.queryListPage(entity);
		request.setAttribute("page", page);
		
		String mvURL = "";
		switch(approvalNode){
			case 4: mvURL = "financeManagment/financeLoanApply"; break;
			case 5: mvURL = "financeManagment/financeLoanAudit"; 	break;
			case 8: mvURL = "financeManagment/financeLoanConfirm"; break;
		}
		return mvURL;
	}
	
	/**
	 * selectLoanApplyList:分页财务管理放款申请列表显示(放款申请、放款审核、放款确认)
	 * @author kernel.org
	 * @param request
	 * @param loanOrderInfoVo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/asynSelectLoanApplyList/{approvalNode}")
	public String asynSelectLoanApplyList(HttpServletRequest request,
									  @PathVariable("approvalNode") Integer approvalNode,
									  LoanOrderInfoVo entity){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		/*String leCity = loanEmp.getLeCity();
		String leAccount = loanEmp.getLeAccount();
		if(loanEmp != null){
			if(leAccount.equals("admin")){
				entity.setLeAccount("");
			}else{
				entity.setLeAccount(leAccount);
			}
			if(!(StrUtils.isNullOrEmpty(leCity) || "".equals(leCity))){
				entity.setLeCity(leCity);
			}
		}*/
		CommonUtils.fillCompany(entity);
		
		String mvURL = "";
		switch(approvalNode){
			case 4:
				entity.setApprovalNode(approvalNode);
				page = approvalProcessService.queryListPage(entity);
				request.setAttribute("page", page);
				mvURL = "financeManagment/_financeLoanApplySub";
				break;
			case 5:
				entity.setApprovalNode(approvalNode);
				page = approvalProcessService.queryListPage(entity);
				request.setAttribute("page", page);
				mvURL = "financeManagment/_financeLoanAuditSub";
				break;
			case 8:
				entity.setApprovalNode(approvalNode);
				page = approvalProcessService.queryListPage(entity);
				request.setAttribute("page", page);
				mvURL = "financeManagment/_financeLoanConfirmSub";
				break;
		}
		return mvURL;
	}
	
	//过滤项目
	private void filterXM(List<LoanExTaskWithBLOBs> handledRecordList) {
		Integer[] statusArray = new Integer[]{0,1,2,3,6,7,13,14,15};
		List<Integer> array = Arrays.asList(statusArray);
		for(Iterator<LoanExTaskWithBLOBs> it = handledRecordList.iterator();it.hasNext();) {
			Integer status = it.next().getLetNodeStatus();
			if(!array.contains(status)) {
				it.remove();
			}
		}
						
	}
	
	
	/**
	 * selectLoanApplyList:财务管理放款申请详细信息展示
	 * @author kernel.org
	 * @param request
	 * @param loanOrderInfoVo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/selectLoanApplyDetail/{approvalNode}")
	public String selectLoanApplyDetail(HttpServletRequest request,
									    @PathVariable("approvalNode") Integer approvalNode,
									    String orderNo,
									    LoanOrderInfoVo entity){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		
		entity.setOrderNo(orderNo);
		LoanOrderBorrowerExtendWithBLOBs orderBorrowerExtendWithBLOBs = new LoanOrderBorrowerExtendWithBLOBs();
		LoanOrderHouseExtend orderHouseExtend = new LoanOrderHouseExtend();
		
		orderBorrowerExtendWithBLOBs.setOrderNo(orderNo);
		orderHouseExtend.setOrderNo(orderNo);
		LoanOrderInfoVo loanOrderInfo = approvalProcessService.selectOrderBasicInfo(entity);
		
		// 查询基本信息列表
		Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
		
		List<LoanEmp> empList = approvalProcessService.selectCustManagerByDeptId(loanOrderInfo.getLeDeptId());
		LoanOrderBorrowerExtendWithBLOBs orderBorrower = approvalProcessService.selectLoanOrderBorrowerExtendBLOBs(orderBorrowerExtendWithBLOBs);
		List<LoanOrderHouseExtend> orderHouse = approvalProcessService.selectLoanOrderHouseExtend(orderHouseExtend);
		// 查询项目审核记录表
		List<LoanExTaskWithBLOBs> handledRecordList = approvalProcessService.selectHandledRecord(params);
		//添加过滤
		filterXM(handledRecordList);
				
		// 查询资金审批流程
		List<LoanFundTaskWithBLOBs> fundTaskList = approvalProcessService.selectFundHandledRecord(orderNo);
		// 查询放款申请表-资金方基本信息
		List<LoanCapitalInfo> loanCapitalInfoList = approvalProcessService.selectLoanCapitalInfo(params);
		for (LoanCapitalInfo loanCapitalInfo : loanCapitalInfoList) {
			LoanAccountsTotal loanAccountsTotal = accountManagerService.getLoanAccountsTotal(loanCapitalInfo.getAccountId());
			if(loanAccountsTotal != null && loanAccountsTotal.getBalance() != null) {
				loanCapitalInfo.setBalance(loanAccountsTotal.getAmount());
			}
		}
		// 查询放款申请表-收入信息
		List<LoanCapitalEarning> loanCapitalEarningList = approvalProcessService.selectLoanCapitalEarning(params);
		
		// 查询放款申请表-支出信息
		List<LoanCapitalExpenditure> loanCapitalExpenditureList = approvalProcessService.selectLoanCapitalExpenditure(params);
		LoanParasConfig loanParasConfig = approvalProcessService.selectParasConfigByOrderNo(orderNo);
		LoanDept loanDept = approvalProcessService.selectLoanDeptByLdDeptId(loanEmp.getLeDeptId());
		
		//客户性质列表
		List<LoanDictionary> customerTypeList = dictionaryMapper.queryByCategory("invest_customer_nature");
		
		request.setAttribute("loanEmp", loanEmp);
		request.setAttribute("loanOrderInfo", loanOrderInfo);
		request.setAttribute("empList", empList);
		
		request.setAttribute("handledRecordList", handledRecordList);
		request.setAttribute("fundTaskList", fundTaskList);
		request.setAttribute("orderBorrower", orderBorrower);
		request.setAttribute("orderHouse", orderHouse);
		
		request.setAttribute("loanCapitalInfoList", loanCapitalInfoList);
		request.setAttribute("loanCapitalEarningList", loanCapitalEarningList);
		request.setAttribute("loanCapitalExpenditureList", loanCapitalExpenditureList);
		request.setAttribute("customerTypeList", customerTypeList);
		
		request.setAttribute("loanParasConfig", loanParasConfig);
		request.setAttribute("basicInfo", basicInfo);
		
		//风控列表
		List<LoanEmp> fkempList = loanOrderInfoMapper.getFengKongEmp(orderNo);
		request.setAttribute("fkempList", fkempList);
		String mvURL = "";
		
		List<String> names = orderInfoService.asyncTeamManager(loanOrderInfo.getCustomerManager());
		String[] arrayThree = names.toArray(new String[3]);
		request.setAttribute("array", arrayThree);
		
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> outSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		request.setAttribute("outSubjectList", outSubjectList);
		
		parm.setDirection("IN");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> inSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		request.setAttribute("inSubjectList", inSubjectList);
		
		switch(approvalNode){
			case 4:
				mvURL = "financeManagment/financeLoanApplyOne";
				break;
			case 5:
				mvURL = "financeManagment/financeLoanAuditOne";
				break;
			case 8:
				if(loanDept != null){
					if("结算部".equals(loanDept.getLdName())){
						mvURL = "financeManagment/financeLoanConfirmOneModify";
						break;
					}else{
						mvURL = "financeManagment/financeLoanConfirmOneView";
						break;
					}
				}else{
					mvURL = "financeManagment/financeLoanConfirmOneView";
					break;
				}
		}
		return mvURL;
	}
	
	/**
	 * asyncTeamManager:异步查询团队经理
	 * @author kernel.org
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/asyncTeamManager")
	public String asyncTeamManager(HttpServletRequest request,
								   @RequestParam(value = "leEmpId", required = false) Integer leEmpId){
		LoanEmp loanEmp = approvalProcessService.selectByPrimaryKey(leEmpId);
		LoanQuarters loanQuarters = approvalProcessService.selectLoanQuartersByQuartersId(loanEmp.getLeQuartersId());
		request.setAttribute("loanQuarters", loanQuarters);
		return "financeManagment/financeLoanApplyOne";
	}
	
	
	/**
	 * 查询审核列表标签
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectAuditing")
	public String selectAuditing(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "orderNo") String orderNo){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		Map<String, Object> params = new HashMap<String, Object>();
		//是否有审核单子的权限
		boolean flag = jedisComponent.isOvertime(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId());
		if(!flag){
			return "noAuthority";
		}
		
		params.put("orderNo", orderNo);
		// 查询项目审核记录表
		List<LoanExTaskWithBLOBs> handledRecordList = approvalProcessService.selectHandledRecord(params);
		//添加过滤
		filterXM(handledRecordList);
		
		
		// 查询基本信息列表
		Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
		
		Long orgId = (Long)basicInfo.get("fund_owner");
		//根据订单的资金方id查询产品列表
		List<LoanChildProduct> childProductList = null;
		if(orgId != null){
			childProductList = approvalProcessService.selectChildProductByOrgId(orgId, orderNo);
		}
		
		// 面审---会签---具体会签人：当前登录人所在部门的全部人员+集团下风控部所有人+集团下资金部所有人
		// 集团下风控部所有人：查询根据两个条件：1.集团编号  2.部门名称=风控部  拥有该分公司的权限
		// 集团下资金部所有人：查询根据两个条件：1.集团编号  2.部门名称=资金部  拥有该分公司的权限
		LoanOrderInfo loanOrderInfo = approvalProcessService.selectByPrimaryKey(orderNo);
		loanEmp.setBusinessCompanyId(loanOrderInfo.getCompanyId());
		List<LoanEmp> funderAndRiskerList = approvalProcessService.selectFunderAndRiskerList(loanEmp);
		// 查询借款人基本信息列表
		List<LoanOrderBorrowerExtendWithBLOBs> loanOrderBorrowerExtendBLOBsList = approvalProcessService.selectLoanOrderBorrowerExtendBLOBsList(params);
		// 查询放款申请表-资金方基本信息
		List<LoanCapitalInfo> loanCapitalInfoList = approvalProcessService.selectLoanCapitalInfo(params);
		for (LoanCapitalInfo loanCapitalInfo : loanCapitalInfoList) {
			LoanAccountsTotal loanAccountsTotal = accountManagerService.getLoanAccountsTotal(loanCapitalInfo.getAccountId());
			if(loanAccountsTotal != null && loanAccountsTotal.getBalance() != null) {
				loanCapitalInfo.setBalance(loanAccountsTotal.getAmount());
			}
		}
		//客户性质列表
		List<LoanDictionary> customerTypeList = dictionaryMapper.queryByCategory("invest_customer_nature");
		
		// 查询放款申请表-收入信息
		List<LoanCapitalEarning> loanCapitalEarningList = approvalProcessService.selectLoanCapitalEarning(params);
		
		// 查询放款申请表-支出信息
		List<LoanCapitalExpenditure> loanCapitalExpenditureList = approvalProcessService.selectLoanCapitalExpenditure(params);
		// 查询组织机构信息
		List<LoanOrgSupportAreas> loanOrgSupportAreas = approvalProcessService.selectLoanOrgSupportAreas(orderNo);
		// 查询房抵押信息
		List<LoanOrderHouseExtend> houseLists = mortgageInfoService.findHouseExtendListByOrderNo(orderNo);
		// 查询车抵押信息
		List<LoanOrderCarExtend> carLists = mortgageInfoService.findCarExtendListByOrderNo(orderNo);
		LoanParasConfig loanParasConfig = approvalProcessService.selectParasConfigByOrderNo(orderNo);
		
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> outSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		request.setAttribute("outSubjectList", outSubjectList);
		//放款审核信息
		List<LoanFundTaskWithBLOBs> fundTaskList = approvalProcessService.selectFundHandledRecord(orderNo);
		request.setAttribute("fundTaskList", fundTaskList);
		
		parm.setDirection("IN");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> inSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		request.setAttribute("inSubjectList", inSubjectList);
		//初评状态查询报单人与客户经理
		if(loanOrderInfo.getApprovalNodeStatus()==0){
			LoanProviderInfo provider =	customerManagerService.selProviderInfo(loanOrderInfo.getProviderNo());
	        if(provider!=null){
	        	List<LoanProviderInfo>  providerList =	customerManagerService.selectProviderByDeptId(provider.getMobileNo(),provider.getCompanyId());
	        	request.setAttribute("providerList", providerList);
	        }
	        List<LoanEmp> empList = systemManagerService.queryEmpByCompanyId(loanOrderInfo.getCompanyId());
	        request.setAttribute("empList", empList);
		}
		
		request.setAttribute("customerTypeList", customerTypeList);
		request.setAttribute("loanEmp", loanEmp);
		request.setAttribute("handledRecordList", handledRecordList);
		request.setAttribute("funderAndRiskerList", funderAndRiskerList);
		request.setAttribute("basicInfo", basicInfo);
		
		request.setAttribute("loanOrderBorrowerExtendBLOBsList", loanOrderBorrowerExtendBLOBsList);
		request.setAttribute("loanCapitalInfoList", loanCapitalInfoList);
		request.setAttribute("loanCapitalEarningList", loanCapitalEarningList);
		
		request.setAttribute("loanCapitalExpenditureList", loanCapitalExpenditureList);
		request.setAttribute("loanOrgSupportAreas", loanOrgSupportAreas);
		request.setAttribute("childProductList", childProductList);
		
		request.setAttribute("houseLists", houseLists);
		request.setAttribute("carLists", carLists);
		request.setAttribute("loanParasConfig", loanParasConfig);
		request.setAttribute("banks", systemManager.queryAllBank());
		return "auditing/orderInfoHandle";
	}
	
	/**
	 * asynQueryLoanCapitalInfo:资金方变化时异步查询放款申请账户信息(未完待续,待二期开发时使用。暂时搁浅)
	 * @author kernel.org
	 * @param request
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/asynQueryLoanCapitalInfo")
	public String asynQueryLoanCapitalInfo(HttpServletRequest request,
										   @RequestParam(value = "orderNo", required = true) String orderNo){
		Map<String, Object> map = new HashMap<String, Object>();
		List<LoanCapitalInfo> loanCapitalInfoList = new ArrayList<LoanCapitalInfo>();
		LoanCapitalInfo loanCapitalInfo = new LoanCapitalInfo();
		map.put("orderNo", orderNo);
		LoanOrganization loanOrganization = approvalProcessService.selectOrganizationAccountInfo(map);
		request.setAttribute("loanCapitalInfoList", loanCapitalInfoList);
		return "auditing/orderInfoHandle";
	}
	/**
	 * 异步查询二级产品信息
	 * @param request
	 * @param response
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/asynQueryChildProduct")
	@ResponseBody
	public List<LoanChildProduct> aysnQueryChildProduct(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "orgId") Long orgId,
			@RequestParam(value = "orderNo") String orderNo){
		// 根据组织机构id查询产品名称
		return approvalProcessService.selectChildProductByOrgId(orgId, orderNo);
	}
	
	/**
	 * 节点审核流程
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/approvalProcess")
	@ResponseBody
	public Map<String, Object> approvalProcess(HttpServletRequest request, HttpServletResponse response,
											   @RequestParam(value = "orderNo") String orderNo,
											   @RequestParam(value = "letNode", required = false) Integer letNode,
											   @RequestParam(value = "approvalNodeStatus", required = false) Integer approvalNodeStatus,
												
											   @RequestParam(value = "exTask", required = false) String exTask,
											   @RequestParam(value = "flowLetNode", required = false) Integer flowLetNode,
											   @RequestParam(value = "fundSide", required = false) Long fundSide,
												
											   @RequestParam(value = "productId", required = false) String productId,
											   @RequestParam(value = "productTypeNo", required = false) String productTypeNo,
											   @RequestParam(value = "actualTerm", required = false) Integer actualTerm,
											   
											   @RequestParam(value = "isPayPlanTemp", required = false) String isPayPlanTemp,
											   @RequestParam(value = "isLoanRecordTemp", required = false) String isLoanRecordTemp,
											   @RequestParam(value = "isPayPlanCompanyTemp", required = false) String isPayPlanCompanyTemp,
												
											   @RequestParam(value = "actualTermUnit", required = false) String actualTermUnit,
											   @RequestParam(value = "actualLoanDate", required = false) String actualLoanDate,
											   @RequestParam(value = "fundFlowNode", required = false) Integer fundFlowNode,
											   @RequestParam(value = "customerManager", required = false) Integer customerManager,
											   @RequestParam(value = "loanMethod", required = false) String loanMethod,
											   @RequestParam(value = "historyLoanMethod", required = false) String historyLoanMethod
			){
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();//获得当前登录用户
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		Map<String, Object> result = new HashMap<String, Object>();
		//是否有审核单子的权限或已经超时
		boolean flag = true;
		if((int)letNode != AuditingNodeType.FKQR.getIndex()){
//			flag = jedisComponent.lockOrder(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId(), 3600);
			flag = jedisComponent.isOvertime(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId());
		}
		
		//boolean flag = jedisComponent.isOvertime(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId());
		if(!flag){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u5BA1\u6838\u65F6\u95F4\u5DF2\u8FC7");//审核时间已过
			return result;
		}
		
		// 查看当前订单是否已被审核
		LoanOrderInfo orderInfo = approvalProcessService.selectByPrimaryKey(orderNo);
		if(null != orderInfo){
			if(!letNode.equals(orderInfo.getApprovalNode())){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u8be5\u8ba2\u5355\u5df2\u88ab\u5ba1\u6838");//该订单已被审核
				return result;
			}
		}
		
		LoanFundTask loanFundTask = new LoanFundTask();
		// 项目审批借点任务表对象
		LoanExTaskWithBLOBs loanExTaskWithBLOBs = StrUtils.isNullOrEmpty(exTask) ? null : JSON.parseObject(exTask, LoanExTaskWithBLOBs.class);
		if(loanExTaskWithBLOBs == null){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u7cfb\u7edf\u5f02\u5e38\u002c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u002e\u002e\u002e");// 系统异常,请联系管理员...
			return result;
		}
		
		//同意财务放款时验证必传资质是否已经全部上传
		if((int)letNode == AuditingNodeType.ZXFK.getIndex() && (int)loanExTaskWithBLOBs.getLetResult() == (AuditingHandledType.TY.getIndex())){
			boolean isUpload = approvalProcessService.isUploadRequiredCredentials(orderNo,productId);
			if(!isUpload){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u5FC5\u4F20\u8D44\u8D28\u4FE1\u606F\u672A\u4E0A\u4F20");//必传资质信息未上传
				return result;
			}
		}
		
		Date currentDate = new Date();
		loanExTaskWithBLOBs.setLetTime(currentDate);
		loanExTaskWithBLOBs.setLetEmployeename(loanEmp.getLeStaffName());
		loanExTaskWithBLOBs.setLetEmployeeno(loanEmp.getLeEmpId());
		loanExTaskWithBLOBs.setLetDeclarationformid(orderNo);
		int ret = approvalProcessService.updateExTaskByOrderNo(loanExTaskWithBLOBs,
															   letNode, 
															   approvalNodeStatus,
															   flowLetNode, 
															   fundSide, 
															   productId, 
															   productTypeNo,
															   actualTerm,
															   isPayPlanTemp,
															   isLoanRecordTemp,
															   isPayPlanCompanyTemp,
															   actualTermUnit,
															   actualLoanDate,
															   fundFlowNode,
															   customerManager,
															   loanMethod,
															   historyLoanMethod);
		if(ret == 1){
			//如果是下户的审核或者财务放款的审核，都同步借款人的所有信息到客户信息表
			if((int)letNode >= AuditingNodeType.XH.getIndex() && (int)letNode<= AuditingNodeType.ZXFK.getIndex()&&(loanExTaskWithBLOBs.getLetResult() != SystemConst.AuditingHandledBJ)){
				if(!orderInfo.getIsLoanAdd().equals("Y")){
					mortgageInfoTransService.saveBorrowerAllInfoToRemote(orderNo,loanEmp);
				}
			}
			//审核成功之后释放锁
			jedisComponent.unLockOrder(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId());
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u5df2\u5ba1\u6838\u0021");// 已审核！
			
			//同步至碰碰旺
			try {
				orderInfoService.updateApproval(orderNo);
			} catch (Exception e) {
				logger.error("Exception"+e);
			}
			
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u5ba1\u6838\u5931\u8d25\u0021");// 审核失败!
		}
		return result;
	}
	
	/**
	 * 保存基本信息
	 * @param orderNo
	 * @param customerOrigin
	 * @param orderInfoDetail
	 * @param loanBorrowerInfoWithBLOBs
	 * @return
	 */
	@RequestMapping("/updateBasicInfo")
	@ResponseBody
	public Map<String, Object> updateBasicInfo(
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "customerOrigin", required = false) Integer customerOrigin,
			@RequestParam(value = "orderInfo", required = false) String orderInfo, 
			@RequestParam(value = "orderInfoDetail", required = false) String orderInfoDetail, 
			@RequestParam(value = "loanBorrowerInfoWithBLOBs", required = false) String loanBorrowerInfoWithBLOBs,
			@RequestParam(value = "relationList", required = false) String relationList
			){
		Map<String, Object> result = new HashMap<String, Object>();
		// 订单实体bean对象
		LoanOrderInfo loanOrderInfo = StrUtils.isNullOrEmpty(orderInfo) ? null : JSON.parseObject(orderInfo, LoanOrderInfo.class);
		LoanOrderInfoDetail loanOrderInfoDetail = StrUtils.isNullOrEmpty(orderInfoDetail) ? null : JSON.parseObject(orderInfoDetail, LoanOrderInfoDetail.class);
		LoanOrderBorrowerExtendWithBLOBs loanOrderBorrowerExtendWithBLOBs = StrUtils.isNullOrEmpty(loanBorrowerInfoWithBLOBs) ? null : JSON.parseObject(loanBorrowerInfoWithBLOBs, LoanOrderBorrowerExtendWithBLOBs.class);
		List<RelevantPersonOrder> relevantPersonList=StrUtils.isNullOrEmpty(relationList)?null:JSON.parseArray(relationList, RelevantPersonOrder.class);
		int ret = approvalProcessService.updateBasicInfo(loanOrderInfo,loanOrderInfoDetail, loanOrderBorrowerExtendWithBLOBs, orderNo,customerOrigin,relevantPersonList);
		if(ret == 1){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u4fdd\u5b58\u57fa\u672c\u4fe1\u606f\u6210\u529f\uff01");//保存基本信息成功！
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u4fdd\u5b58\u57fa\u672c\u4fe1\u606f\u5931\u8d25\uff01");//保存基本信息失败！
		}
		return result;
	}
	
	@RequestMapping("/updateOrAddLoanApply")
	@ResponseBody
	public Map<String, Object> updateOrAddLoanApply(
			HttpServletRequest request,
			String orderNo,
			String loanCapitalEarning,
			String loanCapitalInfo,
			String loanCapitalExpenditure,
			String parasConfig
			){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			loanCapitalEarning=URLDecoder.decode(loanCapitalEarning,"UTF-8");
			loanCapitalInfo=URLDecoder.decode(loanCapitalInfo,"UTF-8");
			loanCapitalExpenditure=URLDecoder.decode(loanCapitalExpenditure,"UTF-8");
			resultMap=approvalProcessService.addUpdateLoanApply(loanCapitalEarning,loanCapitalInfo,loanCapitalExpenditure,orderNo,parasConfig);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u5931\u8d25");
		}catch(Exception e ) {
			 resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			 resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u5931\u8d25" + e.getMessage());
		}
		return resultMap;
	}
	/**
	 * changeOrderCredentials:二级产品改变时，复制产品资质到订单 <br/>
	 * 先让碰碰旺复制资质，然后返回所有信息，存入我们系统<br/>
	 * @author xiangfeng
	 * @param orderNo
	 * @param productId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/changeOrderCredentials")
	@ResponseBody
	public Map<String,Object> changeOrderCredentials(@RequestParam String orderNo,@RequestParam String productId){
		return mortgageInfoTransService.changeOrderCredentials(orderNo,productId);
	}
	
	/**
	 * makePreview:(放款预览). <br/>
	 * @author chenyinhui
	 * @param request
	 * @param orderNo
	 * @param actualLoanDate
	 * @param isPayPlanTemp
	 * @param isLoanRecordTemp
	 * @param isPayPlanCompanyTemp
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/makePreview")
	public String makePreview(HttpServletRequest request,
			String orderNo,//订单号
			String actualLoanDate,//实际放款时间
			@RequestParam(value = "isPayPlanTemp", required = false) String isPayPlanTemp,
			@RequestParam(value = "isLoanRecordTemp", required = false) String isLoanRecordTemp,
			@RequestParam(value = "isPayPlanCompanyTemp", required = false) String isPayPlanCompanyTemp,
			String loanMethod,
			String historyLoanMethod){
		if(loanMethod.equals("2")){//全部放款（更新本次放款金额）
			Map<String, Object> map=capitalInfoService.updateCapitalInfo(1, orderNo);
			if(map.get(SystemConst.retCode).equals(SystemConst.FAIL)){
				request.setAttribute("errorTxt", map.get(SystemConst.retMsg));
				return "alertError";
			}
		}
		LoanTransMain loanTransMain=approvalProcessService.getLoanTransMain(orderNo, actualLoanDate, 
				isPayPlanTemp, isLoanRecordTemp, isPayPlanCompanyTemp, historyLoanMethod);
		if(!StrUtils.isNullOrEmpty(loanTransMain.getRetCode())&&loanTransMain.getRetCode().equals(SystemConst.FAIL)){
			request.setAttribute("errorTxt", loanTransMain.getRetMsg());
			return "alertError";
		}
		try {
			loanTransMain=accountTransactionService.loanTransPre(loanTransMain);
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("errorTxt",  CommonUtils.errorMessageHandler(e.getMessage(), "数据有误"));
			return "alertError";
		}
		request.setAttribute("loanTransMain", loanTransMain);
		return "auditing/_preview";
	}
	/**
	 * checkTime:(验证放款时间). <br/>
	 * @author chenyinhui
	 * @param request
	 * @param orderNo
	 * @param actualLoanDate
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/checkTime")
	@ResponseBody
	public Map<String,Object> checkTime(HttpServletRequest request,
			String orderNo,//订单号
			String actualLoanDate){//实际放款时间
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(SystemConst.retCode, SystemConst.SUCCESS);
		LoanParasConfig loanParasConfig = approvalProcessService.selectParasConfigByOrderNo(orderNo);//订单参数配置
		if(loanParasConfig!=null){
			Date loanDate=DateUtils.String2Date(actualLoanDate);//实际放款时间
			String lpcInterestType=loanParasConfig.getLpcInterestType();//结息方式
			Date lpcInterestRegular=loanParasConfig.getLpcInterestRegular();//结息固定日日期
			String lpcPayType=loanParasConfig.getLpcPayType();//付息方式
			Date lpcPayRegular=loanParasConfig.getLpcPayRegular();//付息固定日日期
			if(lpcInterestType.equals(SystemConst.InterestWay.SPECIFIED_DATE)){
				if(lpcInterestRegular==null){
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "\u7ed3\u606f\u56fa\u5b9a\u65e5\u672a\u586b\u5199");//结息固定日未填写
					return map;
				}
				Date startDate=DateUtils.addDate(lpcInterestRegular, Calendar.MONTH, -1);
				int dayNum=DateUtils.daysBetween(startDate, loanDate);
				int dayNumSub=DateUtils.daysBetween(loanDate,  lpcInterestRegular);
				if(dayNum<0||dayNumSub<0){
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "\u653e\u6b3e\u65f6\u95f4\u5e94\u5728\u7ed3\u606f\u65f6\u95f4\u524d\u4e00\u4e2a\u6708\u4e4b\u5185");//放款时间应在结息时间前一个月之内
					return map;
				}
			}
			if(lpcPayType.equals(SystemConst.InterestWay.SPECIFIED_DATE)){
				if(lpcPayRegular==null){
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "\u4ed8\u606f\u56fa\u5b9a\u65e5\u672a\u586b\u5199");//付息固定日未填写
					return map;
				}
				Date startDate=DateUtils.addDate(lpcPayRegular, Calendar.MONTH, -1);
				int dayNum=DateUtils.daysBetween(startDate, loanDate);
				int dayNumSub=DateUtils.daysBetween(loanDate,  lpcPayRegular);
				if(dayNum<0||dayNumSub<0){
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "\u653e\u6b3e\u65f6\u95f4\u5e94\u5728\u4ed8\u606f\u65f6\u95f4\u524d\u4e00\u4e2a\u6708\u4e4b\u5185");//放款时间应在付息时间前一个月之内
					return map;
				}
			}
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u8bf7\u586b\u5199\u653e\u6b3e\u5ba1\u6279\u8868\u53c2\u6570\u914d\u7f6e");//请填写放款审批表参数配置
		}
		return map;
	}
	
}