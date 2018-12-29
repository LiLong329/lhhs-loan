package com.lhhs.loan.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.EnumType;
import com.lhhs.loan.common.jedis.JedisComponent;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.OrderStatusType;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.Identities;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanProviderInfoMapper;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanAffiliatedEnterpriseUrl;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanFundTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;
import com.lhhs.loan.dao.domain.LoanOrderAffiliatedEnterpriseInfo;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtend;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.OrderInfo;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.dao.domain.vo.OrderInfoDetailVo;
import com.lhhs.loan.dao.domain.vo.OrderInfoVo;
import com.lhhs.loan.service.AffiliatedEnterpriseService;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.DeclarationService;
import com.lhhs.loan.service.GetUniqueNoService;
import com.lhhs.loan.service.InvestCustomerService;
import com.lhhs.loan.service.RelevantPersonOrderService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;
import com.lhhs.loan.service.transport.OrderInfoService;



/**
 * 订单信息表
 * 
 * @author lichao
 * @email 284845673@qq.com
 * @date 2017-08-02 16:13:10
 */
@Controller
@RequestMapping("orderInfo")
public class OrderInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalProcessController.class);
	
	@Reference
	private UserApi userApi;
	@Reference
	private UnionCompanyApi unionCompanyApi;
	
	private String LOAN_LOCK_PREFIX = "loan_lock_";//订单锁前缀
	
	private String LOAN_LOCK_ID_PREFIX = "loan_lock_id_";//订单锁员工id前缀
	
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private CredentialsInfoService credentialsInfoService;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private JedisComponent jedisComponent;
	@Autowired
	private DeclarationService declarationService;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private CrmIntentLoanUserService crmIntentLoanUserService;
	@Autowired
	private LoanProviderInfoMapper loanProviderInfoMapper;
	@Autowired
	private GetUniqueNoService getUniqueNoService;
	@Autowired
	private RelevantPersonOrderService relevantPersonOrderService;
	@Autowired
	private AffiliatedEnterpriseService affiliatedEnterpriseService;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	/**
	 * 所有报单
	 */
	@RequestMapping("/list")	
	public String list(OrderInfo params, ModelMap modelMap ){
		if(!"1".equals(params.getType())){
			CommonUtils.fillCompany(params); 
			if(StringUtils.isEmpty(params.getType())) params.setType(null);
			if(StringUtils.isEmpty(params.getIdNum())) params.setIdNum(null);
			if(StringUtils.isEmpty(params.getPropertyNum())) params.setPropertyNum(null);
			if(StringUtils.isEmpty(params.getPropertyName())) params.setPropertyName(null);
		}else{
			modelMap.put("type", params.getType()==null||"".equals(params.getType())? null : params.getType());
			modelMap.put("idNum", params.getIdNum()==null||"".equals(params.getIdNum())? null : params.getIdNum());
			modelMap.put("propertyNum", params.getPropertyNum()==null||"".equals(params.getPropertyNum())? null : params.getPropertyNum());
			modelMap.put("propertyName", params.getPropertyName()==null||"".equals(params.getPropertyName())? null : params.getPropertyName());
		}
		Page page  = orderInfoService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		modelMap.put("page", page);
		modelMap.put("params", params);
		modelMap.put("orderStatusCondition", "");
		return "orderInfo/orderInfoList";
	}
	
	/**
	 * 进行中报单
	 */
	@RequestMapping("/progress")	
	public String progress(OrderInfo params, ModelMap modelMap ){
		CommonUtils.fillCompany(params);
		String condition = "0,1,2,3,4,5,6,7,8,9,12,13,14,20,21,22,23";
		params.setOrderStatusCondition(condition);
		Page page  = orderInfoService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		modelMap.put("page", page);
		modelMap.put("params", params);
		modelMap.put("orderStatusCondition", condition);
		return "orderInfo/progress";
	}
	
	/**
	 * 完成的报单
	 */
	@RequestMapping("/complete")
	public String complete(OrderInfo params, ModelMap modelMap ){
		CommonUtils.fillCompany(params);
		String condition = "15";
		params.setOrderStatusCondition(condition);
		Page page  = orderInfoService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		modelMap.put("page", page);
		modelMap.put("params", params);
		modelMap.put("orderStatusCondition", condition);
		return "orderInfo/complete";
	}
	
	/**
	 * 失败的报单
	 */
	@RequestMapping("/fail")	
	public String fail(OrderInfo params, ModelMap modelMap ){
		CommonUtils.fillCompany(params);
		String condition = "10,11";
		params.setOrderStatusCondition(condition);
		Page page  = orderInfoService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		modelMap.put("page", page);
		modelMap.put("params", params);
		modelMap.put("orderStatusCondition", condition);
		return "orderInfo/fail";
	}
	
	/**
	 * 信息
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)	
	public String get(String orderNo,ModelMap modelMap ){
		OrderInfoDetailVo entity = orderInfoService.getEntityById(orderNo);
		List<LoanOrderCredentials> list=credentialsInfoService.findOrderCredentialsInfoLists(entity.getOrderNo(), entity.getChildProductNo());
		modelMap.put("entity", entity);
		modelMap.put("list", list);
		boolean show = false;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
		
		// 查询项目审核记录表
		List<LoanExTaskWithBLOBs> handledRecordList = new ArrayList<>();
		List<LoanExTaskWithBLOBs> handledRecordListAll = approvalProcessService.selectHandledRecord(params);
		Integer[] statusArray = new Integer[]{0,1,2,3,6,7,13,14,15};
		List<Integer> array = Arrays.asList(statusArray);
		for (LoanExTaskWithBLOBs bs : handledRecordListAll) {
			Integer status = bs.getLetNodeStatus();
			if(array.contains(status)) {
				handledRecordList.add(bs);
			}
		}
		modelMap.put("handledRecordList", handledRecordList);
		
		//bussiness
		if(entity.getOrderStatus() >  EnumType.OrderInfo.终审.ordinal()) {

			// 查询基本信息列表
			Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
			
			// 查询放款申请表-资金方基本信息
			List<LoanCapitalInfo> loanCapitalInfoList = approvalProcessService.selectLoanCapitalInfo(params);
			for (LoanCapitalInfo loanCapitalInfo : loanCapitalInfoList) {
				LoanAccountsTotal loanAccountsTotal = accountManagerService.getLoanAccountsTotal(loanCapitalInfo.getAccountId());
				if(loanAccountsTotal != null && loanAccountsTotal.getBalance() != null) {
					loanCapitalInfo.setBalance(loanAccountsTotal.getAmount());
				}
			}
			modelMap.put("basicInfo", basicInfo);
			modelMap.put("loanCapitalInfoList", loanCapitalInfoList);
			
			// 查询放款申请表-收入信息
			LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
			parm.setDirection("IN");
			parm.setSubjectTypes("105,106,107,108,111");
			List<LoanAccountsSubjectInfo> inSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
			List<LoanCapitalEarning> loanCapitalEarningList = approvalProcessService.selectLoanCapitalEarning(params);
			
			modelMap.put("inSubjectList", inSubjectList);
			modelMap.put("loanCapitalEarningList", loanCapitalEarningList);
			// 查询放款申请表-支出信息
			parm.setDirection("OUT");
			parm.setSubjectTypes("105,106,107,108,111");
			List<LoanAccountsSubjectInfo> outSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
			List<LoanCapitalExpenditure> loanCapitalExpenditureList = approvalProcessService.selectLoanCapitalExpenditure(params);
			
			modelMap.put("outSubjectList", outSubjectList);
			modelMap.put("loanCapitalExpenditureList", loanCapitalExpenditureList);
			
			//放款审核信息
			List<LoanFundTaskWithBLOBs> fundTaskList = approvalProcessService.selectFundHandledRecord(orderNo);
			modelMap.put("fundTaskList", fundTaskList);
			
			//资金报备放款
			/**
			LoanBuzFundApply buzFundApply = new LoanBuzFundApply();
			buzFundApply.setLbfaOrderNo(orderNo);
			buzFundApply = buzFundApplyService.get(buzFundApply );
			modelMap.put("buzFundApply", buzFundApply);
			 */
			//参数设置
			LoanParasConfig parasConfig = approvalProcessService.selectParasConfigByOrderNo(orderNo);
			
			modelMap.put("loanParasConfig", parasConfig);
			show = true;
		}
		//联系人、共同借款人、担保人
		RelevantPersonOrder relevantPerson=new RelevantPersonOrder();
		relevantPerson.setOrderNo(orderNo);
		List<RelevantPersonOrder> tempList=relevantPersonOrderService.queryList(relevantPerson);
		List<RelevantPersonOrder> contactsList=new ArrayList<RelevantPersonOrder>();//联系人
		List<RelevantPersonOrder> coborrowerList=new ArrayList<RelevantPersonOrder>();//共借人
		List<RelevantPersonOrder> guaranteeList=new ArrayList<RelevantPersonOrder>();//担保人
		if(null!=tempList&&tempList.size()>0){
			for (int i = 0; i < tempList.size(); i++) {
				String type=tempList.get(i).getType();
				if("1".equals(type)){
					contactsList.add(tempList.get(i));
				}else if("2".equals(type)){
					coborrowerList.add(tempList.get(i));
				}else if("3".equals(type)){
					guaranteeList.add(tempList.get(i));
				}
			}
		}
		modelMap.put("contactsList", contactsList);
		modelMap.put("coborrowerList", coborrowerList);
		modelMap.put("guaranteeList", guaranteeList);
		//关联企业信息
		LoanOrderAffiliatedEnterpriseInfo affiliatedEnterprise=new LoanOrderAffiliatedEnterpriseInfo();
		affiliatedEnterprise.setOrderNo(orderNo);
		List<LoanOrderAffiliatedEnterpriseInfo> enterpriseInfoList=affiliatedEnterpriseService.queryList(affiliatedEnterprise);
		if(null!=enterpriseInfoList&&enterpriseInfoList.size()>0){
			LoanAffiliatedEnterpriseUrl enterpriseUrl=new LoanAffiliatedEnterpriseUrl();
			for(int i = 0; i < enterpriseInfoList.size(); i++) {
				enterpriseUrl.setAffiliatedEnterpriseId(enterpriseInfoList.get(i).getAffiliatedEnterpriseId().toString());
				List<LoanAffiliatedEnterpriseUrl> urlList=affiliatedEnterpriseService.queryUrlList(enterpriseUrl);
				enterpriseInfoList.get(i).setUrlList(urlList);
			}
		}
		modelMap.put("enterpriseInfoList", enterpriseInfoList);
		modelMap.put("show", show);
		return "orderInfo/orderInfoDetail";
	}
	
	/**
	 * asyncTeamManager:异步查询团队经理
	 * @author kernel.org
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/asyncTeamManager/{leEmpId}")
	@ResponseBody
	public String asyncTeamManager(@PathVariable("leEmpId") Integer leEmpId){
		List<String> names = orderInfoService.asyncTeamManager(leEmpId);
		String[] array = names.toArray(new String[3]);
		return JSON.toJSONString(array);
	}
	
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private InvestCustomerService customerService;
	
	@RequestMapping("/queryAccountCardList")
	@ResponseBody
	public String queryAccountCardList(LoanAccountCard parm){
		
		String result = null;
		List<LoanAccountCard> cardList = accountManagerService.queryAccountCardBalList(parm);
		if(cardList == null || cardList.size() == 0  ) {
			if(!StringUtils.equals("40", parm.getAccountType())) {
				Map<String, Object> map =new HashMap<>();
				if(parm.getAccountType().equals("30")){
					map.put("customerId", parm.getMobile());
					map.put("investCustomerType", "30");
				}else{
					map.put("investCustomerMobile", parm.getMobile());
					map.put("investCustomerType", "10");
				}
				List<LoanInvestCustomerInfo> customerList = customerService.investCustomerList(map , new Page());
				if(customerList != null && customerList.size() != 0) {
					Map<String, Object> entity = new HashMap<>();
					entity.put("accountName", customerList.get(0).getInvestCustomerName());
					entity.put("ownerId", customerList.get(0).getCustomerId());
					entity.put("ownerName", customerList.get(0).getInvestCustomerName());
					entity.put("mobile", parm.getMobile());
					result = JSON.toJSONString(entity);
				}
			}
		}else {
			for (LoanAccountCard loanAccountCard : cardList) {
				if(StringUtils.isNotEmpty(loanAccountCard.getBankCardNo())){
					LoanAccountCard object = loanAccountCard;
					if(object != null) {
						if(StringUtils.isNotBlank(object.getAccountHolder() )){
							object.setAccountName(object.getAccountHolder() );
						}
						object.setMobile(parm.getMobile());
						result =  JSON.toJSONString(object);
					}
					break;
				}
					
			}
		}
		return result;
	}
	
	
	
	/**
	 * 更新放款申请的订单信息
	 * @param vo
	 * @return
	 */
	@RequestMapping("/updateOrderInfo")
	@ResponseBody
	public Map<String, Object> updateOrderInfo(OrderInfoVo vo) {
		Map<String, Object> resultMap = new HashMap<>();
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		String orderNo = vo.getOrderNo();
		try {
			resultMap = orderInfoService.updateOrderInfo(vo);
		} catch (Exception e) {
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg,  CommonUtils.errorMessageHandler(e.getMessage(), "\u66f4\u65b0\u5931\u8d25")) ;
		}
		if(resultMap.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
			//审核成功之后释放锁
			jedisComponent.unLockOrder(LOAN_LOCK_PREFIX+orderNo, LOAN_LOCK_ID_PREFIX+loanEmp.getLeEmpId());
			//同步至碰碰旺
			try {
				orderInfoService.updateApproval(vo.getOrderNo());
			} catch (Exception e) {
				logger.error("Exception"+e);
			}
		}
		return resultMap;
	}
	
	/**
	 * 查询订单资质信息列表
	 * 
	 * @param currentPageNo
	 * @param pageSize
	 * @param orderNo
	 *            订单号
	 * @param productId
	 *            二级产品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/toOrderCredentials")
	public String orderInfoToOrderCredentials(@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "productId", required = false) String productId, Model model) {
		if (!StrUtils.isNullOrEmpty(orderNo) && !StrUtils.isNullOrEmpty(productId)) {
			List<LoanOrderCredentials> list=credentialsInfoService.findOrderCredentialsInfoLists(orderNo, productId);
			model.addAttribute("list", list);
		}
		return "orderInfo/temp/credentialsList";
	}
	
	
	/**
	 * 订单信息表excel导出
	 */
	@RequestMapping(value="/export", method =RequestMethod.POST)
	public void providerCompanyExport(HttpServletRequest request,HttpServletResponse response,  OrderInfo params){
		CommonUtils.fillCompany(params);
		Page page  = orderInfoService.findPageByEntity(null,params);
		List<?> retMap = page.getResultList();
		for (Object info : retMap) {
			OrderInfo orderInfo=(OrderInfo)info;
			if(orderInfo.getOrgBusinessType().equals("1")){//资金批发
				orderInfo.setBusinessTypeName("资金批发");
			}else if(orderInfo.getOrgBusinessType().equals("2")){//非资金批发
				orderInfo.setBusinessTypeName("非资金批发");
			}
			orderInfo.setOrderStatusName(OrderStatusType.getStatusDesc(orderInfo.getOrderStatus()));
		}
		Map<String,String> titles = new LinkedHashMap<String,String>();
		titles.put("city", "省市");
		titles.put("orderNo", "报单编号");
		titles.put("businessTypeName", "业务类型");
		titles.put("bname", "借款人");
		titles.put("providerName", "报单人");
		titles.put("mobileNo", "报单人手机号");
		titles.put("leStaffName", "客户经理");
		titles.put("orgName", "资金方");
		titles.put("productName", "产品名称");
		titles.put("applyDate", "申请时间");
		titles.put("orderStatusName", "状态");
		AnalyseTransExcel.downLoadExcel(request, response, "订单信息表", retMap, OrderInfo.class, titles);
	}
	
	/**
	 * 新增报单页面
	 * @param
	 * @return
	 */
	@RequestMapping("/addOrderInfo")
	public String addOrderInfo(HttpServletRequest request){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		//资金方列表
		List<LoanOrgSupportAreas> loanOrgSupportAreas = approvalProcessService.selectOrgSupportAreasByEmp(loanEmp);
		//报单人列表
		List<LoanProviderInfo> providerList=declarationService.selectProviderByEmp(loanEmp);
		//客户经理列表
		List<LoanEmp> empList=systemManagerService.queryEmpListByEmp(loanEmp);
		request.setAttribute("loanOrgSupportAreas", loanOrgSupportAreas);
		request.setAttribute("providerList", providerList);
		request.setAttribute("empList", empList);
		return "orderInfo/addOrderInfo";
	}
	
	/**
	 * 根据资金方查询二级产品
	 * @param
	 * @return
	 */
	@RequestMapping("/selectChildProductByFundOwner")
	@ResponseBody
	public List<LoanChildProduct> selectChildProductByFundOwner(HttpServletRequest request,String orgId){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		if(!StrUtils.isNullOrEmpty(loanEmp.getBranchCompanyId())&&!loanEmp.getBranchCompanyId().equals(loanEmp.getCompanyId())){
			params.put("city", loanEmp.getLeCity());
		}
		List<LoanChildProduct> list=approvalProcessService.selectChildProductByFundOwner(params);
		return list;
	}
	
	/**
	 * 保存订单信息
	 * @param
	 * @return
	 */
	@RequestMapping("/saveOderInfo")
	@ResponseBody
	public Map<String,Object> saveOderInfo(HttpServletRequest request,
			LoanOrderInfo orderInfo,
			LoanOrderInfoDetail orderDetail,
			LoanOrderBorrowerExtendWithBLOBs borrower){
		return declarationService.addDeclaration(orderInfo, orderDetail, borrower);
	}
	
	/**
	 * 意向客户--创建报单
	 * @param
	 * @return
	 */
	@RequestMapping("/crmAddOrderInfo")
	public String crmAddOrderInfo(HttpServletRequest request,@RequestParam(name="id",required=false)String id){
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		//资金方列表
		List<LoanOrgSupportAreas> loanOrgSupportAreas = approvalProcessService.selectOrgSupportAreasByEmp(loanEmp);
		//查询客户信息
		CrmIntentLoanUser entity=new CrmIntentLoanUser();
		//查询报单人信息
		LoanProviderInfo providerInfo=loanProviderInfoMapper.selectProviderByMobile(loanEmp.getLeMobile());
		String providerNo="";
		String providerName="";//报单人名称
		if(providerInfo==null){
			providerNo=Identities.uuid2();
			providerNo=getUniqueNoService.getProviderNo();
			LoanProviderInfo providerEntity=new LoanProviderInfo();
			providerEntity.setCompanyId(loanEmp.getBranchCompanyId());
			providerEntity.setCityNo(loanEmp.getLeCity());
			providerEntity.setProviderNo(providerNo);
			providerEntity.setMobileNo(loanEmp.getLeMobile());
			providerEntity.setProviderName(loanEmp.getLeStaffName());
			if(StringUtils.isNotEmpty(entity.getAppointEmpId())){
				providerEntity.setCustomerManager(entity.getAppointEmpId());
			}
			providerEntity.setCityNo(loanEmp.getLeCity());
			providerEntity.setCreateTime(new Date());
			providerEntity.setUnionId(loanEmp.getCompanyId());
			providerEntity.setPassword(loanEmp.getLePassword());
			loanProviderInfoMapper.insertSelective(providerEntity);
		}else{
			if(StringUtils.isNotEmpty(providerInfo.getProviderNo())&&StringUtils.isNotEmpty(providerInfo.getProviderName())){
				providerNo=providerInfo.getProviderNo();
				providerName=providerInfo.getProviderName();
			}
		}
		if("".equals(providerName)){
			providerName=loanEmp.getLeStaffName(); 
		}
		entity.setId(Integer.parseInt(id));
		CrmIntentLoanUser crmUser = crmIntentLoanUserService.get(entity);
		if(crmUser!=null&&StringUtils.isNotEmpty(crmUser.getAppointEmpId())){
			 User user=new User();
			 user.setUserId(Long.valueOf(crmUser.getAppointEmpId()));
			 user=userApi.get(user);
			 if(user!=null){
				 UnionCompany company = unionCompanyApi.get(user.getCompanyId());
				 request.setAttribute("province", company.getProvinceName()==null||"".equals(company.getProvinceName())?crmUser.getProvince():company.getProvinceName());
				 request.setAttribute("city", company.getCityName()==null||"".equals(company.getCityName())?crmUser.getCity():company.getCityName());
				 request.setAttribute("unionid", user.getUnionId());
				 request.setAttribute("companyid", user.getCompanyId());
				 request.setAttribute("deptid", user.getDeptId()==null||"".equals(user.getDeptId())?"":user.getDeptId());
			 }
		}
		request.setAttribute("id", id);
		request.setAttribute("crmUser", crmUser);
		request.setAttribute("loanOrgSupportAreas", loanOrgSupportAreas);
		request.setAttribute("providerNo",providerNo);//报单人编号
		request.setAttribute("providerName",providerName);
		return "orderInfo/crmAddOrderInfo";
	}
	
	
	/**
	 * 暂时取消
	 * 意向客户--创建报单--检索借款人信息(手机号)
	 * @param
	 * @return
	 */
	@RequestMapping("/searchOrderInfo")
	@ResponseBody
	public Map<String, Object> searchOrderInfo(HttpServletRequest request,@RequestParam(name="mobile",required=false)String mobile){
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(mobile)){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "请输入借款人手机号！");
			return result;
		}
		CrmIntentLoanUser entity= crmIntentLoanUserService.findByMobile(mobile);
		if(entity!=null){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put("name", entity.getName());
			result.put("idNumber", entity.getIdNumber());
			result.put("loanAmount", entity.getLoanAmount());
			result.put("duration", entity.getDuration());
			result.put("interestRate", entity.getInterestRate());
			return result;
		}
		result.put(SystemConst.retCode, SystemConst.FAIL);
		result.put(SystemConst.retMsg, "借款人手机号不存在");
		return result;
	}
	
	
	
	/**
	 * 报单量统计
	 */
	@RequestMapping("/getOrderCountList")
    public ModelAndView getOrderCountList(HttpServletRequest request,LoanOrderInfo entity){
   	 	 ModelAndView model=new ModelAndView("orderInfo/orderInfoCount");
   	     CommonUtils.fillCompany(entity);
	 	 Page page  = orderInfoService.getOrderCountList(new Page(entity.getPageIndex(),entity.getPageSize()),entity);
		 //放款金额
		 Map<String,Object> totalCountMap = orderInfoService.getVariousTotalCount(entity);
		 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		 model.addObject("entity", entity);
		 model.addObject("totalCountMap", totalCountMap);
		 model.addObject("companys", companyList);
		 model.addObject("page", page);
		 return model;
    }
	
	
	 /**
	  * 
	  * @param request 日、周、月
	  * @param timeUnit 时间单位
	  * @return
	  */
	@RequestMapping("/getOrderInfoEchartsList")
    @ResponseBody
    public Map<String, Object> getOrderInfoEchartsList(HttpServletRequest request,@RequestParam(value = "timeUnit") String timeUnit){
	   	 Map<String, Object> map=new HashMap<String, Object>();
	   	 LoanOrderInfo entity = new LoanOrderInfo();
	   	 CommonUtils.fillCompany(entity);
	     Date time = DateUtils.getTime(timeUnit);
	     map = orderInfoService.getOrderInfoEchartsList(time,timeUnit,entity);
	   	 map.put("timeUnit", timeUnit);
	   	 return map;
    }
	
	/**
	 * 导出报单量统计
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping("/orderCountListExport")
     public void orderCountListExport( HttpServletRequest request,HttpServletResponse response,LoanOrderInfo entity){
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 CommonUtils.fillCompany(entity);
    	 List<OrderInfo> list = orderInfoService.orderCountListExport(entity);
		 titles.put("customerManagerCity", "省市");
  	     titles.put("customerManagerComponyName", "分公司");
  	     titles.put("customerManagerDeptName", "部门");
  	     titles.put("customerManagerGroupName", "组别");
  	     titles.put("customerManagerName", "客户经理");
  	     titles.put("orderCount", "报单量");
  	     titles.put("dealAmount", "成交量");
  	     String fileName="";
  	     SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
  	     formatter.setLenient(false);
		 if(null!=entity.getCustomerManagerBeginingTime()){
			 formatter = new SimpleDateFormat("yyyyMMdd");
			 String beginTime =formatter.format(entity.getCustomerManagerBeginingTime());
			 fileName+=beginTime;
		}
		 if(null!=entity.getCustomerManagerEndingTime()){
			 formatter = new SimpleDateFormat("yyyyMMdd");
			 String endTime= formatter.format(entity.getCustomerManagerEndingTime());
			 fileName+="-"+endTime;
		 }
		 fileName+="报单量统计报表";
  	     AnalyseTransExcel.downLoadExcel(request, response, fileName, list, LoanOrderInfo.class, titles);
  	     System.out.println(fileName+"下载完成"); 
	}
	
	
	/**
	 * 查看借款人重复校验
	 * @param request
	 * @param entity
	 */
	@RequestMapping("/queryBorrower")
	@ResponseBody
	public Map<String, Object> queryBorrower(HttpServletRequest request,@RequestParam(name="bname",required=false)String bname,@RequestParam(name="idNum",required=false)String idNum){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		LoanOrderBorrowerExtend borrowerExtend=new LoanOrderBorrowerExtend();
		borrowerExtend.setIdNum(idNum);
		borrowerExtend.setBname(bname);
		try {
			int count=loanOrderBorrowerExtendMapper.queryBorrower(borrowerExtend);
			if(count>0){
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
