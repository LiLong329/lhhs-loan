/**
 * Project Name:loan-web
 * File Name:AuditProcessController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年10月17日上午11:05:39
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.api.UserQuartersApi;
import com.lhhs.bump.domain.User;
import com.lhhs.bump.domain.UserQuarters;
import com.lhhs.loan.common.enums.EnumOrderNode;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.LoanDictionaryMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.RelevantPersonAgreementMapper;
import com.lhhs.loan.dao.domain.Agreement;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTotal;
import com.lhhs.loan.dao.domain.LoanAffiliatedEnterpriseUrl;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtend;
import com.lhhs.loan.dao.domain.LoanOrderAffiliatedEnterpriseInfo;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.RelevantPersonAgreement;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;
import com.lhhs.loan.service.AffiliatedEnterpriseService;
import com.lhhs.loan.service.AgreementService;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.DeclarationService;
import com.lhhs.loan.service.MortgageInfoService;
import com.lhhs.loan.service.RelevantPersonOrderService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.audit.AuditProcessService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.workflow.dao.domain.ActVo;

/**
 * ClassName:AuditProcessController <br/>
 * Function: 使用工作流审核业务流程<br/>
 * Date:     2017年10月17日 上午11:05:39 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/auditProcess")
public class AuditProcessController {
	private static final Logger LOGGER = Logger.getLogger(AuditProcessController.class);
	
	@Autowired
	private AuditProcessService auditProcessService;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private MortgageInfoService mortgageInfoService;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanDictionaryMapper dictionaryMapper;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private DeclarationService declarationService;
	@Autowired
	private CredentialsInfoService credentialsInfoService;
	@Autowired
	private RelevantPersonOrderService relevantPersonOrderService;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private RelevantPersonAgreementMapper relevantPersonAgreementMapper;
	@Autowired
	private AffiliatedEnterpriseService affiliatedEnterpriseService;
	@Reference
	private UserApi userApi;
	@Reference
	private UserQuartersApi userQuartersApi;
	@Autowired
	private AgreementService agreementService;
	/**
	 * toHandler:跳转到业务审批详情页 <br/>
	 * @author xiangfeng
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toHandler")
	public String toHandler(ActVo actVo,
			HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		String orderNo = actVo.getBusinessId();
		params.put("orderNo",orderNo);
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		//报单基本信息查询
		Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
		//将拼音节点编号转换成编号
		basicInfo.put("approval_node", EnumOrderNode.getId(actVo.getTaskDefKey()));
		//借款人扩展信息查询
		List<LoanOrderBorrowerExtendWithBLOBs> loanOrderBorrowerExtendBLOBsList = approvalProcessService.selectLoanOrderBorrowerExtendBLOBsList(params);
		if("finish".equals(actVo.getStatus())&& null!=basicInfo&&null!=loanOrderBorrowerExtendBLOBsList && loanOrderBorrowerExtendBLOBsList.size()>0){
			if(null!=basicInfo.get("customer_manager") && !loanEmp.getLeEmpId().toString().equals(basicInfo.get("customer_manager").toString())){
				for (int i = 0; i < loanOrderBorrowerExtendBLOBsList.size(); i++) {
					LoanOrderBorrowerExtendWithBLOBs extendWithBLOBs=loanOrderBorrowerExtendBLOBsList.get(i);
					if(StringUtils.isNotEmpty(extendWithBLOBs.getMobile())){
						extendWithBLOBs.setMobile(extendWithBLOBs.getMobile().substring(0, 3)+"****"+extendWithBLOBs.getMobile().substring(extendWithBLOBs.getMobile().length()-4, extendWithBLOBs.getMobile().length()));
					}
					if(StringUtils.isNotEmpty(extendWithBLOBs.getIdNum())){
						extendWithBLOBs.setIdNum(extendWithBLOBs.getIdNum().substring(0, 1)+"****"+extendWithBLOBs.getIdNum().substring(extendWithBLOBs.getIdNum().length()-1, extendWithBLOBs.getIdNum().length()));
					}
					if(StringUtils.isNotEmpty(extendWithBLOBs.getBankCardNo())){
						extendWithBLOBs.setBankCardNo(extendWithBLOBs.getBankCardNo().substring(0, 5)+"****"+extendWithBLOBs.getBankCardNo().substring(extendWithBLOBs.getBankCardNo().length()-4, extendWithBLOBs.getBankCardNo().length()));
					}
				}
			}
		}
		//抵押物信息查询
		List<LoanOrderHouseExtend> houseLists = mortgageInfoService.findHouseExtendListByOrderNo(orderNo);
		if("finish".equals(actVo.getStatus())&&houseLists.size()>0&&!loanEmp.getLeEmpId().toString().equals(basicInfo.get("customer_manager").toString())){
			for (int i = 0; i < houseLists.size(); i++) {
				LoanOrderHouseExtend orderHouseExtend=houseLists.get(i);
				if(StringUtil.isNotEmpty(orderHouseExtend.getHouseAddress())){
					orderHouseExtend.setHouseAddress(orderHouseExtend.getHouseAddress().replace(orderHouseExtend.getHouseAddress().substring(2, orderHouseExtend.getHouseAddress().length()), "*****************"));
				}
			}
		}
		List<LoanOrderCarExtend> carLists = mortgageInfoService.findCarExtendListByOrderNo(orderNo);
		
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
		//银行列表
		List<LoanBank> banks=systemManagerService.queryAllBank();
		// 查询放款申请表-收入信息
		List<LoanCapitalEarning> loanCapitalEarningList = approvalProcessService.selectLoanCapitalEarning(params);
		
		// 查询放款申请表-支出信息
		List<LoanCapitalExpenditure> loanCapitalExpenditureList = approvalProcessService.selectLoanCapitalExpenditure(params);
		LoanParasConfig loanParasConfig = approvalProcessService.selectParasConfigByOrderNo(orderNo);
		//支出科目
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> outSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		//收入科目
		parm.setDirection("IN");
		parm.setSubjectTypes("105,106,108,111");
		List<LoanAccountsSubjectInfo> inSubjectList = accountManagerService.queryAccountsSubjectInfoList(parm );
		//联系人、共同借款人、担保人
		RelevantPersonOrder relevantPerson=new RelevantPersonOrder();
		relevantPerson.setOrderNo(orderNo);
		List<RelevantPersonOrder> tempList=relevantPersonOrderService.queryList(relevantPerson);
		
		//如果节点为签约公正或权证抵押
		if(actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_QYGZ.getKey())||actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_QZDY.getKey())){
			Agreement param = new Agreement();
			param.setOrderNo(orderNo);
			Agreement agreement = agreementService.getByEntity(param);
			if(null!=tempList&&tempList.size()>0){
				for (int i = 0; i < tempList.size(); i++) {
					RelevantPersonOrder rpo = tempList.get(i);
					Integer rpoId = rpo.getId();
					RelevantPersonAgreement rpa = relevantPersonAgreementMapper.selectByRpoId(rpoId);
					if (rpa!=null) {
						if (!rpa.getName().equals(rpo.getName())) {
							rpo.setIsNameDifferent("姓名与合同不一致");
						}
						if (!rpa.getIdNum().equals(rpo.getIdNum())) {
							rpo.setIsIdNumDifferent("身份证与合同不一致");
						}
					}else {
						rpo.setIsNameDifferent("合同中不存在此人信息");
						rpo.setIsIdNumDifferent("合同中不存在此人信息");
					}
				}
			}
			request.setAttribute("borrowerName", agreement.getBorrowerName());
			request.setAttribute("borrowerIdNum", agreement.getBorrowerIdNum());
		}
				
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
		
		//查询订单资质身份证图片解析json
		List<Map<String, Object>> orderCredentialsParseJsonList=null;//订单资质身份证图片解析json
		String productId=(null==basicInfo.get("child_product_no")||"".equals(basicInfo.get("child_product_no")))?"":basicInfo.get("child_product_no").toString();
		if(StringUtil.isNotEmpty(productId)){
			params.put("englishNames", "'BorrowerIdCard','CoborrowerIdCard','GuarantorIdCard'");
			params.put("orderProductId", productId);
			orderCredentialsParseJsonList=credentialsInfoService.queryOrderCredentialsParseJson(params);
		}
		if(null!=orderCredentialsParseJsonList&&orderCredentialsParseJsonList.size()>0){
			for (int i = 0; i < orderCredentialsParseJsonList.size(); i++) {
				if(null!=orderCredentialsParseJsonList.get(i).get("orderCredentialsEnglishName")&&!"".equals(orderCredentialsParseJsonList.get(i).get("orderCredentialsEnglishName"))
						&&null!=orderCredentialsParseJsonList.get(i).get("parseJson")&&!"".equals(orderCredentialsParseJsonList.get(i).get("parseJson"))){
					String orderCredentialsName=orderCredentialsParseJsonList.get(i).get("orderCredentialsName").toString();
					String orderCredentialsEnglishName=orderCredentialsParseJsonList.get(i).get("orderCredentialsEnglishName").toString();
					Map<String, Object> jsonMap=JSON.parseObject(orderCredentialsParseJsonList.get(i).get("parseJson").toString());
					if(orderCredentialsEnglishName.equals("BorrowerIdCard")){//借款人
						request.setAttribute("credentialsBorrowerName", jsonMap.get("name"));
						request.setAttribute("credentialsBorrowerIdNum", jsonMap.get("num"));
					}
					if(orderCredentialsEnglishName.equals("CoborrowerIdCard")){//共借人
						for (int j = 0; j < coborrowerList.size(); j++) {
							if(jsonMap.get("num").equals(coborrowerList.get(j).getIdNum())&&!jsonMap.get("name").equals(coborrowerList.get(j).getName())&&StringUtil.isEmpty(coborrowerList.get(j).getIsNameDifferent())){
								coborrowerList.get(j).setIsNameDifferent("姓名与【" + orderCredentialsName + "】资质信息不一致");
							}
						}
					}
					if(orderCredentialsEnglishName.equals("GuarantorIdCard")){//担保人
						for (int j = 0; j < guaranteeList.size(); j++) {
							if(jsonMap.get("num").equals(guaranteeList.get(j).getIdNum())&&!jsonMap.get("name").equals(guaranteeList.get(j).getName())&&StringUtil.isEmpty(guaranteeList.get(j).getIsNameDifferent())){
								guaranteeList.get(j).setIsNameDifferent("姓名与【" + orderCredentialsName + "】资质信息不一致");
							}
						}
					}
				}
			}
		}
		
		if(EnumOrderNode.XIAHU_ZP.getKey().equals(actVo.getTaskDefKey())||EnumOrderNode.XIAHU_SH_HQ.getKey().equals(actVo.getTaskDefKey())
				||EnumOrderNode.ZHONGSHEN_HQ.getKey().equals(actVo.getTaskDefKey())||EnumOrderNode.ZHONGSHEN.getKey().equals(actVo.getTaskDefKey())){
			UserQuarters userQuarters=new UserQuarters();
			if(EnumOrderNode.XIAHU_ZP.getKey().equals(actVo.getTaskDefKey())){
				userQuarters.setEnglishName("RiskManager");//风控经理英文名称RiskManager
			}else if(EnumOrderNode.XIAHU_SH_HQ.getKey().equals(actVo.getTaskDefKey())){
				userQuarters.setEnglishName("DirectorResponsible");//风控负责人英文名称DirectorResponsible
			}else if(EnumOrderNode.ZHONGSHEN_HQ.getKey().equals(actVo.getTaskDefKey())){
				userQuarters.setEnglishName("RiskControlDirector");//风控总监英文名称RiskControlDirector
			}else if(EnumOrderNode.ZHONGSHEN.getKey().equals(actVo.getTaskDefKey())){
				userQuarters.setEnglishName("FundDepartmentDirector");//资金部总监英文名称FundDepartmentDirector
			}
			userQuarters.setServerId("lhhs_spark");
			userQuarters.setUnionId(loanEmp.getCompanyId());
			userQuarters.setCompanyId(loanEmp.getBranchCompanyId());
			List<UserQuarters> userQuartersList=userQuartersApi.queryList(userQuarters);
			request.setAttribute("userQuartersList", userQuartersList);
		}
		//下户节点-查询借款人、 抵押物是否申请过借款
		if(null!=basicInfo&&EnumOrderNode.XIAHU.getKey().equals(actVo.getTaskDefKey())
				&&(null!=loanOrderBorrowerExtendBLOBsList || null!=houseLists)){
			if(loanOrderBorrowerExtendBLOBsList.size()>0){
				LoanOrderBorrowerExtend borrowerExtend=new LoanOrderBorrowerExtend();
				borrowerExtend.setIdNum(loanOrderBorrowerExtendBLOBsList.get(0).getIdNum());
				borrowerExtend.setBname(loanOrderBorrowerExtendBLOBsList.get(0).getBname());
				borrowerExtend.setOrderNo(orderNo);
				int count=loanOrderBorrowerExtendMapper.queryBorrower(borrowerExtend);
				if(count>0){
					request.setAttribute("borrowerExtend", "borrowerExtend");
				}
			}
			if(houseLists.size()>0){
				List<LoanOrderHouseExtend> houseExtendList=new ArrayList<LoanOrderHouseExtend>();
				for(LoanOrderHouseExtend house:houseLists){
					LoanOrderHouseExtend houseExtend=new LoanOrderHouseExtend();
					houseExtend.setPropertyNum(house.getPropertyNum());
					houseExtend.setPropertyName(house.getPropertyName());
					houseExtend.setOrderNo(orderNo);
					try {
						int count=mortgageInfoService.queryOrderHouseExtend(houseExtend);
						if(count>0){
							houseExtendList.add(house);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				request.setAttribute("houseExtendList", houseExtendList);
			}
		}
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
		
		List<LoanChildProduct> childProductList = null;
		List<LoanOrgSupportAreas> loanOrgSupportAreas = null;
		List<LoanProviderInfo> providerList = null;
		List<User> empList = null;
		List<LoanAccountCard> accountCardList = null;
		//根据不同节点、查询不同内容
		if("todo".equals(actVo.getStatus())){//待办
			//------------------------------------------初评-----------------------------------------
			if(actVo.getTaskDefKey().equals(EnumOrderNode.CHUPING.getKey())){
				//资金方列表
				loanOrgSupportAreas = approvalProcessService.selectLoanOrgSupportAreas(orderNo);
				if(basicInfo != null){
					//二级产品列表
					Long orgId = (Long)basicInfo.get("fund_owner");
					if(orgId != null){
						childProductList = approvalProcessService.selectChildProductByOrgId(orgId, orderNo);
					}
					//报单人列表
					providerList=declarationService.selectProviderByEmp(loanEmp);
					//客户经理列表
					String companyId = (String) basicInfo.get("company_id");
					if(!StrUtils.isNullOrEmpty(companyId)){
						empList = userApi.queryUserByCompanyId(companyId);
					}
				}
				request.setAttribute("managerAccount", basicInfo.get("le_account"));
				request.setAttribute("loanOrgSupportAreas", loanOrgSupportAreas);
				request.setAttribute("childProductList", childProductList);
				request.setAttribute("providerList", providerList);
				request.setAttribute("empList", empList);
			}
			//------------------------------------------放款申请-----------------------------------------
			if(actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SQ.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.MODIFY.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX.getKey())){
				if(basicInfo != null){
					//客户经理列表
					String companyId = (String) basicInfo.get("company_id");
					if(!StrUtils.isNullOrEmpty(companyId)){
						empList = userApi.queryUserByCompanyId(companyId);
					}
					//借款人银行卡号
					LoanOrderInfo orderInfo=new LoanOrderInfo();
					orderInfo.setOrderNo(orderNo);
					accountCardList=orderInfoService.getAccountCardList(orderInfo);
				}
				request.setAttribute("empList", empList);
				request.setAttribute("accountCardList", accountCardList);
			}
			if(actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SH.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_JSQR.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_QZQR.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SH_HQ.getKey())){
				if(basicInfo != null){
					//客户经理所属团队
					Integer customerManager= (Integer) basicInfo.get("customer_manager");
					List<String> names = orderInfoService.asyncTeamManager(customerManager);
					String[] teamArray = names.toArray(new String[3]);
					request.setAttribute("teamArray", teamArray);
				}
			}
			//------------------------------------------下户审核或终审 是否上传资质图片（点击查看图片信息）--------------------------------
			if(actVo.getTaskDefKey().equals(EnumOrderNode.XIAHU_SH.getKey())||actVo.getTaskDefKey().equals(EnumOrderNode.ZHONGSHEN.getKey())){
				List<LoanOrderCredentials> orderCredentialsList=credentialsInfoService.findOrderCredentialsByworkFlow(orderNo, productId);
				request.setAttribute("orderCredentialsList",orderCredentialsList);
			}
			//------------------------------------------终审-----------------------------------------
			if(actVo.getTaskDefKey().equals(EnumOrderNode.ZHONGSHEN.getKey())&&"1".equals(basicInfo.get("business_type"))){
				//查询当前登录人部门所有人员
				User curr=new User();
//				curr.setDeptId(loanEmp.getLeDeptId().toString());
				curr.setCompanyId(loanEmp.getBranchCompanyId());
				curr.setStatus("1");//部门状态-有效
				curr.setField1("资金");
				curr.setUserId(null==basicInfo.get("customer_manager")?null:Long.valueOf(basicInfo.get("customer_manager").toString()));//客户经理id
				List<User>  deptEmpList = userApi.queryUserList(curr);
				request.setAttribute("deptEmpList", deptEmpList);
			}
			//------------------------------------------终审会签-----------------------------------------
			if(actVo.getTaskDefKey().equals(EnumOrderNode.ZHONGSHEN_HQ.getKey())&&"1".equals(basicInfo.get("business_type"))){
				//查询当前登录人部门所有人员
				//查询当前登录人部门所有人员
				User curr=new User();
				curr.setDeptId(loanEmp.getLeDeptId().toString());
				List<User>  deptEmpList = userApi.queryUserList(curr);
				request.setAttribute("deptEmpList", deptEmpList);
			}
		}
		
		//查询结果放入request中
		request.setAttribute("actVo", actVo);
		request.setAttribute("loanEmp", loanEmp);
		//基本信息
		request.setAttribute("basicInfo", basicInfo);
		request.setAttribute("loanOrderBorrowerExtendBLOBsList", loanOrderBorrowerExtendBLOBsList);
		//抵押物信息
		request.setAttribute("houseLists", houseLists);
		request.setAttribute("carLists", carLists);
		//放款申请表
		request.setAttribute("loanCapitalInfoList", loanCapitalInfoList);
		request.setAttribute("loanCapitalEarningList", loanCapitalEarningList);
		request.setAttribute("loanCapitalExpenditureList", loanCapitalExpenditureList);
		request.setAttribute("loanParasConfig", loanParasConfig);
		request.setAttribute("customerTypeList", customerTypeList);
		
		request.setAttribute("outSubjectList", outSubjectList);
		request.setAttribute("inSubjectList", inSubjectList);
		request.setAttribute("banks", banks);
		//借款人相关人
		request.setAttribute("contactsList", contactsList);
		request.setAttribute("coborrowerList", coborrowerList);
		request.setAttribute("guaranteeList", guaranteeList);
		//关联企业信息
		request.setAttribute("enterpriseInfoList", enterpriseInfoList);
		//跳转页面分类
		if("finish".equals(actVo.getStatus())){//已办
			return "historic/orderInfoDetail";
		}else if("todo".equals(actVo.getStatus())){//待办
			if(actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SQ.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.MODIFY.getKey())){
				return "auditing/financeLoanModify";
			}else if(actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SH.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_JSQR.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_QZQR.getKey())||
					actVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_SH_HQ.getKey())){
				return "auditing/financeLoanAuditing";
			}else{
				return "auditing/orderInfoHandle";
			}
		}else{
			request.setAttribute("errorTxt", "参数有误，请联系管理员");
			return "alertError";
		}
	}
	
	/**
	 * Handler:任务提交审核 <br/>
	 * @author xiangfeng
	 * @param auditParamVo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/handler")
	@ResponseBody
	public Map<String,Object> Handler(AuditParamVo auditParamVo){
		Map<String,Object> ret = new HashMap<String,Object>();
		LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		auditParamVo.setLoanEmp(loanEmp);
		try{
			ret = auditProcessService.handler(auditParamVo);
		}catch(Exception e){
			LOGGER.error("审核异常：",e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "审核失败"));
		}
		return ret;
	}
	
	/**
	 * Handler:更新本次放款金额 <br/>
	 * @author chenyinhui
	 * @param loanCapitalInfo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/updateAmountPaidThis")
	@ResponseBody
	public Map<String,Object> updateAmountPaidThis(String loanCapitalInfo){
		Map<String,Object> ret = new HashMap<String,Object>();
		try{
			loanCapitalInfo=URLDecoder.decode(loanCapitalInfo,"UTF-8");
			ret = auditProcessService.updateAmountPaidThis(loanCapitalInfo);
		}catch(Exception e){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, CommonUtils.errorMessageHandler(e.getMessage(), "更新失败"));
		}
		return ret;
	}
	
	
	
	/**
	 * getBankByCard:银行卡号-开户行级联查询
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/getBankByCard")
	@ResponseBody
	public LoanAccountCard getBankByCard(HttpServletRequest request,String bankCardNo){
		LoanAccountCard entity=auditProcessService.getBankByCard(bankCardNo.trim());
		return entity;
	}
	
	
	/**
	 * 关联企业信息上传图片资料
	 * 
	 * @param enterpriseUrl
	 * @return
	 */
	@RequestMapping("/saveEnterpriseUrl")
	@ResponseBody
	public Map<String, Object> saveEnterpriseUrl(LoanAffiliatedEnterpriseUrl enterpriseUrl) {
		Map<String, Object> result = new HashMap<String, Object>();
		enterpriseUrl.setCreateTime(new Date());
		int num = affiliatedEnterpriseService.saveEnterpriseUrl(enterpriseUrl);
		if (num == 1) {
			result.put("id", enterpriseUrl.getId());
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u4E0A\u4F20\u6210\u529F");// 上传成功
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u4E0A\u4F20\u5931\u8D25");// 上传失败
		}
		return result;
	}
	
	/**
	 * 删除关联企业信息上传图片资料
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteEnterpriseUrl")
	@ResponseBody
	public Map<String, Object> deleteEnterpriseUrl(Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		int num = affiliatedEnterpriseService.deleteEnterpriseUrl(id);
		if (num == 1) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "删除失败");
		}
		return result;
	}
	
	/**
	 * 保存关联企业信息
	 * 
	 * @param credentialsUrl
	 * @return
	 */
	@RequestMapping("/saveAffiliatedEnterprise")
	@ResponseBody
	public Map<String, Object> saveAffiliatedEnterprise(String enterpriseList, String orderNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<LoanOrderAffiliatedEnterpriseInfo> list=StrUtils.isNullOrEmpty(enterpriseList)?null:JSON.parseArray(enterpriseList, LoanOrderAffiliatedEnterpriseInfo.class);
		if(null==list||list.size()==0){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "无可用关联企业信息");
			return result;
		}
		int num=affiliatedEnterpriseService.saveEnterpriseInfo(list,orderNo);
		if (num == list.size()) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "保存关联企业信息成功");
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "保存关联企业信息失败");
		}
		return result;
	}
	
}