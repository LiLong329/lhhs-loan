/**
 * Project Name:loan-service
 * File Name:AuditProcessServiceImpl.java
 * Package Name:com.lhhs.loan.service.audit.impl
 * Date:2017年10月17日下午2:23:07
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.audit.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.EnumOrderNode;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.AuditingHandledType;
import com.lhhs.loan.common.shared.enums.OrderStatusType;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.AgreementMapper;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInOutInfoMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanCapitalEarningMapper;
import com.lhhs.loan.dao.LoanCapitalExpenditureMapper;
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanFeesPlanMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanOrderHouseExtendMapper;
import com.lhhs.loan.dao.LoanOrderInfoDetailMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanParasConfigMapper;
import com.lhhs.loan.dao.LoanTransMainMapper;
import com.lhhs.loan.dao.RelevantPersonAgreementMapper;
import com.lhhs.loan.dao.RelevantPersonOrderMapper;
import com.lhhs.loan.dao.domain.Agreement;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanPayPlanCompany;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.dao.domain.RelevantPersonAgreement;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;
import com.lhhs.loan.dao.domain.vo.LoanPayRecordCompanyVo;
import com.lhhs.loan.dao.domain.vo.OrderInfoDetailVo;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.NoticeUtils;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.account.AccountTransactionService;
import com.lhhs.loan.service.audit.AuditProcessService;
import com.lhhs.loan.service.transport.MortgageInfoTransService;
import com.lhhs.loan.service.transport.ProviderTransportService;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.TaskVo;

/**
 * ClassName:AuditProcessServiceImpl <br/>
 * Function: 工作流方式审核流程service <br/>
 * Date:     2017年10月17日 下午2:23:07 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class AuditProcessServiceImpl implements AuditProcessService {
	private static final Logger LOGGER = Logger.getLogger(AuditProcessServiceImpl.class);
	
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanOrderInfoDetailMapper loanOrderInfoDetailMapper;
	@Autowired
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;
	@Autowired(required = false)
	private ActTaskService actTaskService;
	
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private LoanOrderHouseExtendMapper loanOrderHouseExtendMapper;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanCustomerBaseInfoMapper loanCustomerBaseInfoMapper;
	@Autowired
	private LoanCapitalExpenditureMapper loanCapitalExpenditureMapper;
	@Autowired
	private LoanParasConfigMapper loanParasConfigMapper;
	@Autowired
	private ProviderTransportService providerTransportService;
	@Autowired
	private MortgageInfoTransService mortgageInfoTransService;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Reference
	private UserApi userApi;
	@Autowired
	private AgreementMapper agreementMapper;
	@Autowired
	private RelevantPersonAgreementMapper relevantPersonAgreementMapper;
	@Autowired
	private RelevantPersonOrderMapper relevantPersonOrderMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private LoanAccountInOutInfoMapper loanAccountInOutInfoMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanCapitalEarningMapper loanCapitalEarningMapper;
	@Autowired
	private LoanTransMainMapper loanTransMainMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> handler(AuditParamVo auditParamVo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		ActVo parm=new ActVo();
		//初始化审批参数
		setTaskParm(parm,auditParamVo);
		//调用工作流中方法扭转节点记录审批记录
		parm=actTaskService.complete(parm);
		//设置下一审批人审批节点
		auditParamVo.setNextTaskName(parm.getNextTaskName());
		auditParamVo.setNextTaskDefKey(parm.getNextTaskDefKey());
		auditParamVo.setNextAssignee(parm.getNextAssignee());
		auditParamVo.setNextAssigneeName(parm.getNextAssigneeName());
		if(EnumOrderNode.FANGKUAN_SQ.getKey().equals(auditParamVo.getTaskDefKey())
				||EnumOrderNode.MODIFY.getKey().equals(auditParamVo.getTaskDefKey())){
			//放款申请和放款申请修改
			ret = updateOrderInfoApply(auditParamVo);
		}else{
			//修改订单信息
			ret = updateOrderInfo(auditParamVo);
		}
		
		if(ret.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
			
			if((auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX.getId())
					||auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX_BF.getId()))
					&& auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())){
				//执行放款节点添加生成提现申请记录插入loan_account_in_out_info线下充值提现记账信息表
				try {
					boolean flag=accountInOutInfoinsertData(auditParamVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//如果是下户的审核到财务放款的审核，都同步借款人的所有信息到客户信息表
			if(auditParamVo.getLetNode().intValue() >= Integer.valueOf(EnumOrderNode.XIAHU.getId()) 
					&& auditParamVo.getLetNode().intValue() <= Integer.valueOf(EnumOrderNode.FANGKUAN_ZX.getId())
					&& !auditParamVo.getLetResult().equals(AuditingHandledType.BJ.getPass())){
				 mortgageInfoTransService.saveBorrowerAllInfoToCustomerInfo(auditParamVo.getOrderNo(),auditParamVo.getLoanEmp(),auditParamVo.getIsLoanAdd(), auditParamVo);
			}
			//如果节点为终审且审核结果为同意---更新合同信息
			if((auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.ZHONGSHEN.getId())
					||auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.ZHONGSHEN_HQ.getId()))
					&& auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())){
				
				String orderNo = auditParamVo.getOrderNo();
				Agreement params = new Agreement();
				params.setOrderNo(orderNo);
				OrderInfoDetailVo orderDetailInfo = loanOrderInfoMapper.getEntityById(orderNo);
				LoanOrderBorrowerExtendWithBLOBs bs = loanOrderBorrowerExtendMapper.selectByPrimaryKey(orderNo);
				params = agreementMapper.getByEntity(params);
				if(null!=params){
					params.setBorrowerName(bs.getBname());
					params.setBorrowerIdNum(bs.getIdNum());
					params.setLoanAmount(new BigDecimal(orderDetailInfo.getLoanAmount()));
					params.setLoanTerm(Integer.valueOf(orderDetailInfo.getLoanTerm()));
					params.setLoanTermUnit(orderDetailInfo.getLoanTermUnit());
					params.setCapitalName(orderDetailInfo.getOrgName());
					params.setCompanyId(orderDetailInfo.getCompanyId());
					params.setUnionId(orderDetailInfo.getUnionId());
					agreementMapper.updateByPrimaryKeySelective(params);
				}
				
				//删除合同相关人信息
				RelevantPersonAgreement rpa = new RelevantPersonAgreement();
				rpa.setAgreementNo(params.getAgreementNo());
				relevantPersonAgreementMapper.delete(rpa);
				//查询订单相关人信息列表
				RelevantPersonOrder rpo = new RelevantPersonOrder();
				rpo.setOrderNo(orderNo);
				List<RelevantPersonOrder> rpoList = relevantPersonOrderMapper.queryList(rpo);
				if (rpoList!=null&&rpoList.size()>0) {
					//复制订单相关人信息插入合同相关人表
					List<RelevantPersonAgreement> rpaList = new ArrayList<RelevantPersonAgreement>();
					for (int i = 0; i < rpoList.size(); i++) {
						RelevantPersonOrder rpoTemp = rpoList.get(i);
						RelevantPersonAgreement rpaTemp = new RelevantPersonAgreement();
						rpaTemp.setAgreementNo(params.getAgreementNo());
						rpaTemp.setName(rpoTemp.getName());
						rpaTemp.setSex(rpoTemp.getSex());
						rpaTemp.setIdNum(rpoTemp.getIdNum());
						rpaTemp.setMobile(rpoTemp.getMobile());
						rpaTemp.setPhone(rpoTemp.getPhone());
						rpaTemp.setRelation(rpoTemp.getRelation());
						rpaTemp.setType(rpoTemp.getType());
						rpaTemp.setRpoId(rpoTemp.getId());
						rpaList.add(rpaTemp);
					}
					relevantPersonAgreementMapper.insertList(rpaList);
				}
			}
			//如果节点为执行放款且审核结果为同意---更新合同信息
			if((auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX.getId())
					||auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX_BF.getId()))
					&& auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())){
				
				String orderNo = auditParamVo.getOrderNo();
				Agreement params = new Agreement();
				params.setOrderNo(orderNo);
				List<LoanCapitalInfo> capitalInfoList = loanCapitalInfoMapper.getCapitalInfoByOrderNo(orderNo);
				OrderInfoDetailVo orderDetailInfo = loanOrderInfoMapper.getEntityById(orderNo);
				if (capitalInfoList!=null&&capitalInfoList.size()>0) {
					String investorName = "";
					String orgName = "";
					for (int i = 0; i < capitalInfoList.size(); i++) {
						LoanCapitalInfo capitalInfo = capitalInfoList.get(i);
						if (StringUtils.isNoneEmpty(capitalInfo.getOrgName())) {
							orgName = capitalInfo.getOrgName();
						}
						if (StringUtils.isNoneEmpty(capitalInfo.getOwnerName())) {
							investorName += capitalInfo.getOwnerName()+",";
						}
					}
					params = agreementMapper.getByEntity(params);
					if(null!=params){
						params.setInvestorName(investorName);
						params.setCapitalName(orgName);
						params.setLoanAmount(new BigDecimal(orderDetailInfo.getLoanAmount()));
						params.setLoanTerm(Integer.valueOf(orderDetailInfo.getLoanTerm()));
						params.setLoanTermUnit(orderDetailInfo.getLoanTermUnit());
						agreementMapper.updateByPrimaryKeySelective(params);
					}
				}
			}
			if(auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())||auditParamVo.getLetResult().equals(AuditingHandledType.BJ.getPass())){
				String orderNo = auditParamVo.getOrderNo();
				//发消息提醒
				LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
				NoticeModel entity = new NoticeModel();
				entity.setEnglishName(parm.getNextTaskDefKey());
				entity.setModelType("1");//业务类
				entity.setReceiver(parm.getNextAssignee());
				
				Map<String, String> map = new HashMap<>();
				map.put("【报单编号】", orderNo);
				map.put("【时间】", DateUtils.Date2String(loanOrderInfo.getOperatorDate(),"yyyy-MM-dd"));
				LoanOrderHouseExtend houseExtend = loanOrderHouseExtendMapper.selectByOrderNo(orderNo);
				if (houseExtend!=null) {
					if (StringUtils.isNotEmpty(houseExtend.getHouseAddress())) {
						map.put("【房屋位置】",houseExtend.getHouseAddress());
					}else {
						map.put("【房屋位置】","房屋位置");
					}
				}else {
					map.put("【房屋位置】","房屋位置");
				}
				LoanOrderBorrowerExtendWithBLOBs bs = loanOrderBorrowerExtendMapper.selectByPrimaryKey(orderNo);
//				map.put("【借款人】", bs.getBname()+(null==bs.getSex()||bs.getSex().equals("0")?"女士":"先生"));
				map.put("【借款人】", bs.getBname());
				if (StringUtils.isNotEmpty(parm.getNextTaskDefKey())&&StringUtils.isNotEmpty(parm.getNextAssignee())) {
					NoticeUtils.createMsg(entity, map );
				}
			}
			//执行放款结果为同意 发送资金类放款提醒消息
			if((auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX.getId())
					||auditParamVo.getLetNode().intValue() == Integer.valueOf(EnumOrderNode.FANGKUAN_ZX_BF.getId()))
					&& auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())){
				String orderNo = auditParamVo.getOrderNo();
				//发消息提醒
				LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
				LoanOrderInfoDetail detail = loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);
				NoticeModel entity = new NoticeModel();
				entity.setEnglishName("fktx");
				entity.setModelType("2");//资金类
				entity.setUnionId(loanOrderInfo.getUnionId());
				entity.setCompanyId(loanOrderInfo.getCompanyId());
				entity.setCustomerManager(loanOrderInfo.getCustomerManager());
				
				Map<String, String> map = new HashMap<>();
				map.put("【报单编号】", orderNo);
				map.put("【时间】", DateUtils.Date2String(detail.getActualLoanDate(),"yyyy-MM-dd"));
				map.put("【金额】", detail.getActualAmount().toString());
				NoticeUtils.createMsg(entity, map );
			}
		}
		return ret;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String,Object> updateOrderInfo(AuditParamVo auditParamVo){
		Map<String,Object> ret = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		int orderFlag = 1;//更新订单结果
		int orderDetailFlag = 1;//更新订单详情
		
		LoanOrderInfoDetail loanOrderInfoDetail = new LoanOrderInfoDetail();
		loanOrderInfoDetail.setOrderNo(auditParamVo.getOrderNo());
		//更新订单详情--资金方--初评
		if(auditParamVo.getTaskDefKey().equals(EnumOrderNode.CHUPING.getKey())){
			loanOrderInfoDetail.setFundOwner(auditParamVo.getFundSide());
		}
		//如果是执行放款，调用放款服务(结果同意)
		if(auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())
				&& (auditParamVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX.getKey())
				||auditParamVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX_BF.getKey()))){
			// 调用放款服务
			String isPayPlanTemp = StrUtils.isNullOrEmpty(auditParamVo.getIsPayPlanTemp())?SystemConst.IsFlag.NO:auditParamVo.getIsPayPlanTemp();
			String isLoanRecordTemp = StrUtils.isNullOrEmpty(auditParamVo.getIsLoanRecordTemp())?SystemConst.IsFlag.NO:auditParamVo.getIsLoanRecordTemp();
			String isPayPlanCompanyTemp = StrUtils.isNullOrEmpty(auditParamVo.getIsPayPlanCompanyTemp())?SystemConst.IsFlag.NO:auditParamVo.getIsPayPlanCompanyTemp();
			LoanTransMain loanTransMain = approvalProcessService.getLoanTransMain(auditParamVo.getOrderNo(), auditParamVo.getActualLoanDate(), 
					isPayPlanTemp, isLoanRecordTemp, isPayPlanCompanyTemp, auditParamVo.getHistoryLoanMethod());
			String recode = accountTransactionService.loanTrans(loanTransMain);
			if(!recode.equals(SystemConst.SUCCESS)){
				LOGGER.error("放款失败...");
				throw new RuntimeException("放款失败...");
			}
			
			param.put("orderNo", auditParamVo.getOrderNo());
			List<LoanCapitalInfo> loanCapitalInfo = approvalProcessService.selectLoanCapitalInfo(param);
			BigDecimal bigDecimal=new BigDecimal(0);
			if(null != loanCapitalInfo && loanCapitalInfo.size() > 0){
				for (LoanCapitalInfo loanCapital : loanCapitalInfo) {
					loanCapital.setAmountPaidAlready(loanCapital.getAmountPaidThis().add(loanCapital.getAmountPaidAlready()));
					bigDecimal = bigDecimal.add(loanCapital.getAmountPaidAlready());
					loanCapitalInfoMapper.updateByPrimaryKeySelective(loanCapital);
				}
				loanOrderInfoDetail.setActualAmount(bigDecimal);
			}
			
			if(null != auditParamVo.getActualLoanDate()){
				loanOrderInfoDetail.setActualLoanDate(DateUtils.parseDate(auditParamVo.getActualLoanDate()));
			}
		}
		//更新订单(订单状态、放款方式、产品编号(1级、2级)、客户经理)
		//设置订单状态
		if(StrUtils.isNullOrEmpty(auditParamVo.getNextTaskDefKey())){
			//判断处理结果
			if(auditParamVo.getLetResult().equals(AuditingHandledType.CD.getPass())){
				auditParamVo.setOrderStatus(OrderStatusType.YCD.getIndex());
			}
			if(auditParamVo.getLetResult().equals(AuditingHandledType.JD.getPass())){
				auditParamVo.setOrderStatus(OrderStatusType.YJD.getIndex());
			}
		}else{
			if(auditParamVo.getLetNode().intValue() < Integer.parseInt(EnumOrderNode.MODIFY.getId())){
				auditParamVo.setOrderStatus(Integer.valueOf(EnumOrderNode.getId(auditParamVo.getNextTaskDefKey())));
			}
		}
		if(auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())
				&& auditParamVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX.getKey())){
			auditParamVo.setOrderStatus(OrderStatusType.FKCG.getIndex());
		}
		auditParamVo.setOperatorDate(new Date());
		if(auditParamVo.getTaskDefKey().equals(EnumOrderNode.CHUPING.getKey())){
			LoanEmp managerEmp=loanEmpMapper.selectByPrimaryKey(auditParamVo.getCustomerManager());
			if(managerEmp!=null){
				auditParamVo.setDepId(managerEmp.getLeGroupId()==null?managerEmp.getLeDeptId().toString():managerEmp.getLeGroupId().toString());
			}
		}
		auditParamVo.setFkApplyUser(auditParamVo.getWf_user());
		orderFlag = loanOrderInfoMapper.updateByPrimaryKeySelective(auditParamVo);
		
		if(auditParamVo.getTaskDefKey().equals(EnumOrderNode.CHUPING.getKey()) ||
				(auditParamVo.getLetResult().equals(AuditingHandledType.TY.getPass())
						&&(auditParamVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX_BF.getKey())
						||auditParamVo.getTaskDefKey().equals(EnumOrderNode.FANGKUAN_ZX.getKey())))){//初评、执行放款
			orderDetailFlag = loanOrderInfoDetailMapper.updateByPrimaryKeySelective(loanOrderInfoDetail);
		}
		if(orderFlag == 1 && orderDetailFlag == 1){
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
			ret.put(SystemConst.retMsg, "审核成功");
		}else{
			throw new RuntimeException("审核时订单信息更新失败");
		}
		return ret;
	}
	
	public Map<String, Object> updateOrderInfoApply(AuditParamVo vo) {
		String orderNo = vo.getOrderNo();
		
		int ret = 0;
		//保存订单信息
		LoanOrderInfo loanOrderInfo = vo.loanOrderInfo();
		//更新订单(订单状态、放款方式、产品编号(1级、2级)、客户经理)
		//设置订单状态
		if(StrUtils.isNullOrEmpty(vo.getNextTaskDefKey())){
			//判断处理结果
			if(vo.getLetResult().equals(AuditingHandledType.JD.getPass())){
				loanOrderInfo.setOrderStatus(OrderStatusType.YJD.getIndex());
			}
		}else{
			loanOrderInfo.setOrderStatus(Integer.valueOf(EnumOrderNode.getId(vo.getNextTaskDefKey())));
		}
		if(loanOrderInfo.getCustomerManager()!=null){
			LoanEmp managerEmp=loanEmpMapper.selectByPrimaryKey(loanOrderInfo.getCustomerManager());
			if(managerEmp!=null){
				loanOrderInfo.setDepId(managerEmp.getLeGroupId()==null?managerEmp.getLeDeptId().toString():managerEmp.getLeGroupId().toString());
			}
		}
		loanOrderInfo.setOperatorDate(new Date());
		ret += loanOrderInfoMapper.updateByPrimaryKeySelective(loanOrderInfo);
		ret += loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(vo.loanOrderInfoDetail());
		if(ret < 2){
			throw new RuntimeException("更新订单信息失败");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
        Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
		
		String orgId = basicInfo.get("fund_owner") == null ? null : "" + basicInfo.get("fund_owner");
		String orgName = basicInfo.get("org_name") == null ? null : "" + basicInfo.get("org_name");
		
		// 保存订单详情中的借款信息
		LoanOrderBorrowerExtendWithBLOBs borrower = vo.orderBorrower();
		ret = loanOrderBorrowerExtendMapper.updateOrderBorrowerExtendBLOBsByOrderNo(borrower);;
		if(ret == 0){
			throw new RuntimeException("订单详情中的借款信息失败");
		}
		
		List<LoanOrderHouseExtend> houseExtendList = vo.houseList();
		for (LoanOrderHouseExtend houseExtend : houseExtendList) {
			ret = loanOrderHouseExtendMapper.updateByPrimaryKeySelective(houseExtend);
			if(ret ==0){
				throw new RuntimeException("保存抵押物房源失败");
			}
		}
		
		//参数
		LoanParasConfig loanParasConfig = vo.loanParasConfig();
		if(StringUtils.isNotBlank(loanParasConfig.getLpcId())){
			loanParasConfigMapper.updateByPrimaryKey(loanParasConfig);
		}else {
			loanParasConfig.setLpcId(UUID.randomUUID().toString().replace("-", ""));
			loanParasConfigMapper.insertSelective(loanParasConfig);
		}
		
		//保存放款资金方放款金额
		Map<String, Object> resultMap=new HashMap<String, Object>();
		int loanCapEarnCount=0, loanCapInfoCount=0, loanCapExpendCount=0;
		
		List<LoanCapitalEarning> loanCapEarn= vo.loanCapitalEarningList();
		List<LoanCapitalInfo> loanCapInfo= vo.loanCapitalInfoList();
		List<LoanCapitalExpenditure> loanCapExpend= vo.loanCapitalExpenditureList();
		loanCapitalEarningMapper.delByOrderNo(orderNo);
		loanCapitalInfoMapper.delByOrderNo(orderNo);
		loanCapitalExpenditureMapper.delByOrderNo(orderNo);
		for (LoanCapitalEarning loanEarn : loanCapEarn) {
			if(StringUtils.isNotBlank(loanEarn.getEarningVariety())) {
				loanEarn.setCapitalEarningId(UUID.randomUUID().toString().replace("-", ""));
				loanEarn.setOrgId(orgId);
				loanCapEarnCount += loanCapitalEarningMapper.insertSelective(loanEarn);
			}else {
				loanCapEarnCount ++;
			}
		}
		
		LoanOrderInfo orderInfoEntity = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		String unionId = orderInfoEntity.getUnionId();
		for (LoanCapitalInfo loanInfo : loanCapInfo) {
			String id =UUID.randomUUID().toString().replace("-", "");
			loanInfo.setCapitalInfoId(id);
			loanInfo.setOrgId(orgId);
			loanInfo.setOrgName(orgName);
			
			LoanAccountCard parm = new LoanAccountCard();
			if(StringUtils.isNoneBlank(loanInfo.getCardId())) {
				parm.setId(Long.valueOf(loanInfo.getCardId()));
			}
			BeanUtils.copyProperties(loanInfo, parm);
			if(StrUtils.isNullOrEmpty(loanInfo.getAccountId())){
				LoanCustomerBaseInfo loanCustomerBaseInfo=loanCustomerBaseInfoMapper.selectByCustomerIdOrMobile(loanInfo.getMobile());
				if(loanCustomerBaseInfo!=null){
					parm.setOwnerId(loanCustomerBaseInfo.getCustomerId());
					parm.setOwnerName(loanCustomerBaseInfo.getCustomerName());
					parm.setMobile(loanCustomerBaseInfo.getCustomerMobile());
				}
			}else{
				parm.setAccountId(loanInfo.getAccountId());
			}
			parm.setUnionId(unionId);
			parm.setAccountType(loanInfo.getCustomerType());
			parm.setCompanyId(orderInfoEntity.getCompanyId());
			parm.setBankCardNo(loanInfo.getBankcardId());
			parm.setBranchName(loanInfo.getBranchBank());
			parm.setAccountHolder(loanInfo.getAccountName());
			
			accountManagerService.saveOrUpdateCard(parm);
			
			loanInfo.setAccountId(parm.getAccountId());
			loanCapInfoCount += loanCapitalInfoMapper.insertSelective(loanInfo);
		}
		for (LoanCapitalExpenditure loanExpend : loanCapExpend) {
			if(StringUtils.isNotBlank(loanExpend.getExpenditureVariety())) {
				loanExpend.setCapitalExpenditureId(UUID.randomUUID().toString().replace("-", ""));
				loanExpend.setOrgId(orgId);
				loanCapExpendCount += loanCapitalExpenditureMapper.insertSelective(loanExpend);
			}else {
				loanCapExpendCount  ++;
			}
		}
		
		//保存借款人的关联人信息
		List<RelevantPersonOrder> relevantPersonList=StrUtils.isNullOrEmpty(vo.getRelationPeopleInfo())?null:JSON.parseArray(vo.getRelationPeopleInfo(), RelevantPersonOrder.class);
		RelevantPersonOrder relevantPersonOrder=new RelevantPersonOrder();
		relevantPersonOrder.setOrderNo(orderNo);
		relevantPersonOrder.setType("2");
		relevantPersonOrderMapper.delete(relevantPersonOrder);
		if(null!=relevantPersonList&&relevantPersonList.size()>0){
			int num=relevantPersonOrderMapper.insertList(relevantPersonList);
			if(num!=relevantPersonList.size()){
				throw new RuntimeException("保存共借人信息失败");
			}
		}
		
		if (loanCapEarnCount ==loanCapEarn.size() && loanCapInfoCount==loanCapInfo.size() && loanCapExpendCount==loanCapExpend.size()) {
			   resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			   resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u6210\u529f");
		} else {
			throw new RuntimeException("保存放款申请表失败");

		}
		if(ret == 1){
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			resultMap.put(SystemConst.retMsg, "审核成功"); // 提交成功
		}else{
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, "\u63d0\u4ea4\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u002e\u002e\u002e"); // 提交失败，请联系管理员...
			throw new RuntimeException("提交失败，请联系管理员");
		}
		//给碰碰旺同步放款申请信息
		providerTransportService.updateLoanApply(loanCapEarn, loanCapInfo,loanCapExpend, orderNo, vo.getIsLoanAdd());
//		注释冻结
// 		String code = freeze(orderNo);
// 		if(StringUtils.equals(code, "01")) {
// 			throw new RuntimeException("保存失败");
// 		}
 		
		return resultMap;
	}
	private void setTaskParm(ActVo parm,AuditParamVo auditParamVo){
		Map<String,Object> vars  = new HashMap<String,Object>();
		LoanEmp loanEmp=auditParamVo.getLoanEmp();
		String taskDefKey=auditParamVo.getTaskDefKey();
		String taskId=auditParamVo.getTaskId();
		String procInsId=auditParamVo.getProcInsId();
		String type=auditParamVo.getType();
		if(loanEmp==null ||loanEmp.getLeAccount()==null){
			throw new RuntimeException("操作员信息不能为空");
		}
		if(StringUtils.isEmpty(taskDefKey)||StringUtils.isEmpty(taskId)){
			throw new RuntimeException("节点信息和任务ID不能为空");
		}
		if(StringUtils.isEmpty(procInsId)){
			throw new RuntimeException("流程实例ID不能为空");
		}
		TaskVo taskvo=null;
		parm.setUserId(auditParamVo.getLoanEmp().getLeAccount());
		parm.setTaskId(taskId);
		//设置审核人员的集团、公司、部门、组
		parm.setCompanyId(loanEmp.getBranchCompanyId());
		parm.setUnionId(loanEmp.getCompanyId());
		if(loanEmp.getLeGroupId()!=null){
			parm.setDepId(loanEmp.getLeGroupId().toString());
		}else{
			parm.setDepId(loanEmp.getLeDeptId().toString());
		}
		
		//决议类型
		String pass_=auditParamVo.getLetResult();
		//设置意见
		String comment=auditParamVo.getLetRemark();
		
		parm.setTaskDefKey(taskDefKey);
		//放款节点处理 loanMethod
		//初评节点处理
		if(AuditingHandledType.TY.getPass().equals(pass_)
				&&(EnumOrderNode.CHUPING.getKey().equals(taskDefKey))){
			//跳转节点处理
			if(StringUtils.isEmpty(type)){
				vars.put("type", "1");
			}else{
				vars.put("type", type);
			}
			String wfGroup =auditParamVo.getWf_group();
			vars.put("wf_group", wfGroup);
			//如果指派的岗位是事业部总经理、团队总监，需要同时给报单人（客户经理），加面审权限
			if("BusinessUnitGM".equals(wfGroup)||"TeamDirector".equals(wfGroup)){
				vars.put("wf_user", auditParamVo.getManagerAccount());
			}else{
				vars.put("wf_user", "");
			}
		}
		//放款申请节点处理  所有流程
		if(EnumOrderNode.FANGKUAN_SQ.getKey().equals(taskDefKey)){
			//跳转节点处理
			if(StringUtils.isEmpty(type)){
				vars.put("type", "1");
			}else{
				vars.put("type", type);
			}
			//取当前审批人
			String user_act=auditParamVo.getLoanEmp().getLeAccount();
			   if(!StringUtils.isEmpty(user_act)){
				   vars.put("wf_user", user_act); 
			   }else{
				   vars.put("wf_user", "");
			   }
		}

		//权证抵押节点处理 三方放款
		if(EnumOrderNode.FANGKUAN_QZDY.getKey().equals(taskDefKey)){
			
			LoanOrderInfo entity = loanOrderInfoMapper.selectByPrimaryKey(auditParamVo.getOrderNo());
		   if(entity!=null&&!StringUtils.isEmpty(entity.getFkApplyUser())){
			   vars.put("wf_user", entity.getFkApplyUser()); 
		   }else{
			   vars.put("wf_user", "");
		   }
				
		}
		//面审
		if(AuditingHandledType.TY.getPass().equals(pass_)
				&&(taskDefKey.indexOf(EnumOrderNode.MIANSHEN.getKey())>-1)){
			if(StringUtils.isEmpty(type)){
				vars.put("type", "1");
			}else{
				vars.put("type", type);
				if(type.equals("0")){
					//获取业务居间非居间
					String businessType = auditParamVo.getBusinessType();
					if("0".equals(businessType)){//非居间
						vars.put("wf_group", "RiskControlDirector");//风控总监
					}else{
						vars.put("wf_group", "FundDepartmentDirector");//资金部总监
					}
				}
				
			}
		}
		//面审-选择会签跳过下户视为三方放款居间非担保流程加标识 file3=1
		if(AuditingHandledType.HQ.getPass().equals(pass_)&&(taskDefKey.indexOf(EnumOrderNode.MIANSHEN.getKey())>-1)){
			if(null!=type&&type.equals("0")){//跳过下户
					auditParamVo.setField3("1");
			}
		}
		
		//下户指派
		if(AuditingHandledType.TY.getPass().equals(pass_)&&(EnumOrderNode.XIAHU_ZP.getKey().equals(taskDefKey))){
			User cmLoanEmp=userApi.get(auditParamVo.getWf_group());
			vars.put("wf_user", cmLoanEmp.getUsername());
		}
		//下户
		if(AuditingHandledType.TY.getPass().equals(pass_)
				&&(EnumOrderNode.XIAHU.getKey().equals(taskDefKey))){
			//获取业务居间非居间
			String businessType="0";
			LoanOrderInfo entity = loanOrderInfoMapper.selectByPrimaryKey(auditParamVo.getOrderNo());
			if(entity!=null){
				businessType = entity.getBusinessType();
			}
			if("0".equals(businessType)){
				vars.put("wf_group", "BranchOfficeGM");
			}else{
				vars.put("wf_group", "FundDepartmentDirector");
			}
		}
		//下户审核 and 下户审核(会签)
		if(AuditingHandledType.TY.getPass().equals(pass_)&&((EnumOrderNode.XIAHU_SH.getKey().equals(taskDefKey))
				||(EnumOrderNode.XIAHU_SH_HQ.getKey().equals(taskDefKey)))){
			vars.put("wf_group", "RiskControlDirector");//风控总监
		}
		//下户、签约、权证补件节点处理 
		if(AuditingHandledType.BJ.getPass().equals(pass_)){
			taskvo=actTaskService.findCommentList(procInsId,auditParamVo.getFlowLetNode());
			String assignee=taskvo.getAssignee();
			//设置审批人
			auditParamVo.setWf_user(assignee);
			vars.put("wf_user", assignee);
			String flowLetNode=EnumOrderNode.CHUPING.getKey();
			if(auditParamVo.getFlowLetNode()!=null){
				flowLetNode=auditParamVo.getFlowLetNode();
			}
			if(StringUtils.isEmpty(comment)){
				comment=EnumOrderNode.getName(flowLetNode)+AuditingHandledType.BJ.getDesc();
			}else{
				comment=EnumOrderNode.getName(flowLetNode)+AuditingHandledType.BJ.getDesc()+","+comment;
			}
		}
		//会签节点处理 
		if(AuditingHandledType.HQ.getPass().equals(pass_)){
			vars.put("wf_group", auditParamVo.getWf_group());
		}
		//执行放款节点处理 loanMethod
		if(AuditingHandledType.TY.getPass().equals(pass_)
				&&(EnumOrderNode.FANGKUAN_ZX.getKey().equals(taskDefKey)
						||EnumOrderNode.FANGKUAN_ZX_BF.getKey().equals(taskDefKey))){
			vars.put("loanMethod", auditParamVo.getLoanMethod());
		}
		//终审节点处理 全流程
		if(taskDefKey.indexOf(EnumOrderNode.ZHONGSHEN.getKey())>=0){
			LoanOrderInfo orderInfoEntity = loanOrderInfoMapper.selectByPrimaryKey(auditParamVo.getOrderNo());
			String wf_user_cm="wf_user";
			if(orderInfoEntity!=null){
				Integer CustomerManager=orderInfoEntity.getCustomerManager();
				User cmLoanEmp=userApi.get(CustomerManager.toString());
				wf_user_cm=cmLoanEmp.getUsername();
			}
			
			if(orderInfoEntity!=null&&"1".equals(orderInfoEntity.getBusinessType())){
				//居间业务设置指定的放款申请人
				vars.put("wf_user", auditParamVo.getWf_user());
				
			}else{
				//设置放款申请客户经理
				vars.put("wf_user", wf_user_cm);
			}
			if(StringUtils.isEmpty(type)){
				vars.put("type", "1");
			}else{
				vars.put("type", type);
			}
			
		}
		
		//驳回修改设置放款申请人为代办人 
		if(AuditingHandledType.XG.getPass().equals(pass_)){
			//查询最新的放款申请审核记录
			TaskVo taskTept=actTaskService.findCommentList(procInsId,EnumOrderNode.FANGKUAN_SQ.getKey());
			String assignee=taskTept.getAssignee();
			//设置放款申请客户经理
			vars.put("wf_user", assignee);
		}
		//放款申请修改、权证放款确认
		if(EnumOrderNode.MODIFY.getKey().equals(taskDefKey)||EnumOrderNode.FANGKUAN_QZQR.getKey().equals(taskDefKey)){
			LoanOrderInfo entity = loanOrderInfoMapper.selectByPrimaryKey(auditParamVo.getOrderNo());
			if(entity!=null){
				String businessType = entity.getBusinessType();
				if("1".equals(businessType)){
					//查询最新的放款申请审核记录
					TaskVo taskTept=actTaskService.findCommentList(procInsId,EnumOrderNode.FANGKUAN_SQ.getKey());
					String assignee=taskTept.getAssignee();
					//设置放款申请客户经理
					vars.put("wf_user", assignee);
				}else{
					vars.put("wf_user", "");
				}
				
			}
			
		}
		// 补件完成
		if(taskDefKey.indexOf(EnumOrderNode.BUJIAN_CP.getKey())>=0){
			pass_=AuditingHandledType.BJWC.getPass();
		}
		parm.setComment(comment);
		parm.setPass(pass_);
		parm.setVars(vars);
	}

	
	/**
	 * 工作流-执行放款 生成提现申请数据
	 * @param auditParamVo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private boolean accountInOutInfoinsertData(AuditParamVo auditParamVo) {
		String orderNo=auditParamVo.getOrderNo();
		LoanOrderBorrowerExtendWithBLOBs borrower = loanOrderBorrowerExtendMapper.selectByPrimaryKey(orderNo);
		LoanOrderInfoDetail loanOrderInfoDetail=loanOrderInfoDetailMapper.selectByPrimaryKey(auditParamVo.getOrderNo());//订单详情
		LoanOrderInfo loanOrderInfo=loanOrderInfoMapper.selectByPrimaryKey(auditParamVo.getOrderNo());//订单 
		LoanCustomerInfo loanCustomerInfo=loanCustomerInfoMapper.selectByCustomerId(loanOrderInfo.getCustomerId());//借款人
		LoanTransMain loanTransMain=new LoanTransMain();
		loanTransMain.setBusinessId(orderNo);
		loanTransMain=loanTransMainMapper.getByEntity(loanTransMain);
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		LoanAccountInOutInfo loanAccountInOutInfo=new LoanAccountInOutInfo();
		loanAccountInOutInfo.setCreateUser(user.getStaffName());
		loanAccountInOutInfo.setUnionId(user.getUnionId());
		loanAccountInOutInfo.setCompanyId(user.getCompanyId());
		loanAccountInOutInfo.setTransNo(SystemConst.TransType.TYPEID1006 + GetUniqueNoUtil.getOrderNo());
		loanAccountInOutInfo.setStatus(SystemConst.Status.STATUS03);
		loanAccountInOutInfo.setCreateTime(new Date());
		loanAccountInOutInfo.setTransType(SystemConst.TransType.TYPEID1006);
		loanAccountInOutInfo.setAmount(loanTransMain.getAmount());//执行付款-实际放款金额
		loanAccountInOutInfo.setCustomerId(loanCustomerInfo.getCustomerId());
		loanAccountInOutInfo.setCustomerName(loanCustomerInfo.getCustomerName());
		loanAccountInOutInfo.setCustomerType(loanCustomerInfo.getCustomerType());
		loanAccountInOutInfo.setCustomerNature(loanCustomerInfo.getCustomerNature());
		loanAccountInOutInfo.setOrderNo(orderNo);
		loanAccountInOutInfo.setBankCardNo(borrower.getBankCardNo());
		loanAccountInOutInfo.setField1("1102001");//交易科目 借款入账
		LoanAccountCard card=new LoanAccountCard();
		card = loanAccountCardMapper.getBankByCard(borrower.getBankCardNo());
		if (card != null) {
			loanAccountInOutInfo.setBankId(card.getBankId());
			loanAccountInOutInfo.setAccountId(card.getAccountId());
			loanAccountInOutInfo.setMobile(card.getMobile());
			loanAccountInOutInfo.setCertificateNo(card.getCertificateNo());
			loanAccountInOutInfo.setAccountHolder(card.getAccountHolder());
			loanAccountInOutInfo.setBankName(card.getBankName());
		}
		loanAccountInOutInfo.setActualPayTime(new Date());
		try {
			int t=loanAccountInOutInfoMapper.insertSelective(loanAccountInOutInfo);
			if(t<1){
				return false;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public Map<String, Object> updateAmountPaidThis(String loanCapitalInfo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		int i=0;
		if(StrUtils.isNullOrEmpty(loanCapitalInfo)){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "参数错误");
		}
		List<LoanCapitalInfo> loanCapInfo=JSON.parseArray(loanCapitalInfo, LoanCapitalInfo.class);
		for (LoanCapitalInfo loanInfo : loanCapInfo) {
			i+=loanCapitalInfoMapper.updateByPrimaryKeySelective(loanInfo);
		}
		if(i==loanCapInfo.size()){
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
			ret.put(SystemConst.retMsg, "更新成功");
		}else{
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "更新失败");
		}
		return ret;
	}

	@Override
	public LoanAccountCard getBankByCard(String bankCardNo) {
		return loanAccountCardMapper.getBankByCard(bankCardNo);
	}
}

