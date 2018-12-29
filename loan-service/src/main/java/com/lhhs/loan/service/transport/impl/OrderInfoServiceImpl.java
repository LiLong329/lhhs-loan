/**
 * Project Name:loan-service
 * File Name:OrderInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年6月29日上午9:46:01
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport.impl;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lhhs.bump.api.DeptApi;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.EnumOrderNodeOther;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.AuditingNodeType;
import com.lhhs.loan.common.shared.enums.ServiceType;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.LoanCapitalEarningMapper;
import com.lhhs.loan.dao.LoanCapitalExpenditureMapper;
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.LoanChildProductMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanExTaskMapper;
import com.lhhs.loan.dao.LoanGroupMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanOrderCarExtendMapper;
import com.lhhs.loan.dao.LoanOrderCredentialsMapper;
import com.lhhs.loan.dao.LoanOrderCredentialsUrlMapper;
import com.lhhs.loan.dao.LoanOrderHouseExtendMapper;
import com.lhhs.loan.dao.LoanOrderInfoDetailMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanOrganizationMapper;
import com.lhhs.loan.dao.LoanParasConfigMapper;
import com.lhhs.loan.dao.LoanQuartersMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanExTask;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.OrderInfo;
import com.lhhs.loan.dao.domain.vo.OrderInfoDetailVo;
import com.lhhs.loan.dao.domain.vo.OrderInfoVo;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.account.AccountManagerService;
import com.lhhs.loan.service.account.AccountTransactionService;
import com.lhhs.loan.service.scheduler.TimingTaskService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.workflow.bs.inf.ActCommentService;
import com.lhhs.workflow.dao.domain.TaskVo;

/**
 * ClassName:OrderInfoServiceImpl <br/>
 * Function: 报单数据接口同步实现类 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月29日 上午9:46:01 <br/>
 * 
 * @author zhanghui
 * @version
 * @since JDK 1.8
 * @see
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	private static final Logger logger = Logger.getLogger(OrderInfoServiceImpl.class);
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanOrderInfoDetailMapper loanOrderInfoDetailMapper;
	@Autowired
	private LoanOrderCredentialsMapper loanOrderCredentialsMapper;
	@Autowired
	private LoanOrderCredentialsUrlMapper loanOrderCredentialsUrlMapper;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private LoanOrderHouseExtendMapper loanOrderHouseExtendMapper;
	@Autowired
	private RestTemplateComponent restTemplateComponent;
	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;
	@Autowired
	private TimingTaskService timingTaskService;
	@Autowired
	private LoanExTaskMapper loanExTaskMapper;
	@Autowired
	private LoanChildProductMapper loanChildProductMapper;
	@Autowired
	private TimingTaskService taskService;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private LoanOrderCarExtendMapper carExtendMapper;
	@Autowired
	private LoanOrderHouseExtendMapper houseExtendMapper;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanCustomerBaseInfoMapper loanCustomerBaseInfoMapper;
	@Autowired(required = false)
	private ActCommentService actCommentService;
	@Reference
	private UserApi userApi;
	@Reference
	private DeptApi deptApi;
	
	/**
	 * 
	 * TODO 保存报单数据实现方法（可选）.
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#saveOrderInfo(java.util.Map)
	 */

	@Async
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveOrderInfo(Map<String, String> params) throws Exception {
		logger.debug("碰碰旺H5同步的报单数据："+JSON.toJSONString(params));
		Map<String, Object> result = new HashMap<String, Object>();
		if (params == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "参数为空，同步失败");
			return result;
		}
		// 订单表
		LoanOrderInfo orderInfo = JSON.parseObject((String) params.get("loan_order_info"), LoanOrderInfo.class);
		if (orderInfo == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "loan_order_info数据为空，同步失败");
			return result;
		}
		// 查询资金方
		String childProductNo = orderInfo.getChildProductNo();
		LoanChildProductWithBLOBs childProduct = loanChildProductMapper.selectByPrimaryKey(childProductNo);
		if (childProduct == null || childProduct.getFundOwner() == null) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "资金方信息未查到，同步失败");
			return result;
		}
		// 根据客户经理编号查询他所属公司和集团
		Integer customerManager = orderInfo.getCustomerManager();
		if(customerManager != null){
			LoanEmp emp = loanEmpMapper.selectByPrimaryKey(customerManager);
			if (emp == null) {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "客户经理信息未查到，同步失败");
				return result;
			}
			//设置单子所属集团跟公司
			orderInfo.setUnionId(emp.getCompanyId());
			orderInfo.setCompanyId(emp.getBranchCompanyId());
			//设置单子所在组或者部门
			if(emp.getLeGroupId()!=null){
				orderInfo.setDepId(emp.getLeGroupId().toString());
			}else{
				orderInfo.setDepId(emp.getLeDeptId().toString());
			}
			
		}
		
		String orderNo = orderInfo.getOrderNo();
		//订单状态设置成待处理
		orderInfo.setOrderStatus(-1);
		orderInfo.setApprovalNode(0);
		orderInfo.setApprovalNodeStatus(0);
		if(!StrUtils.isNullOrEmpty(orderInfo.getProvince())){
			orderInfo.setProvince(orderInfo.getProvince().replace("省", "").replace("市", ""));
		}
		//默认未放款
		orderInfo.setLoanMethod("0");
		loanOrderInfoMapper.insertSelective(orderInfo);
		// 订单详情表
		LoanOrderInfoDetail orderDetail = JSON.parseObject((String) params.get("loan_order_info_detail"),
				LoanOrderInfoDetail.class);
		orderDetail.setFundOwner(childProduct.getFundOwner());
		loanOrderInfoDetailMapper.insertSelective(orderDetail);

		// 订单资质表
		List<LoanOrderCredentials> credentialsList = JSONObject
				.parseArray((String) params.get("loan_order_credentials"), LoanOrderCredentials.class);
		if (credentialsList!=null&&credentialsList.size() > 0) {
			for (LoanOrderCredentials credentials : credentialsList) {
				loanOrderCredentialsMapper.insertSelective(credentials);
			}
		}
		// 订单资质url
		/*@SuppressWarnings("rawtypes")
		List<List> credentialsUrlList = JSONObject.parseArray((String) params.get("loan_order_credentials_url"),
				List.class);
		if(credentialsUrlList!=null&&credentialsUrlList.size()>0){
			for (List<Map<String, Object>> list : credentialsUrlList) {
				for (Map<String, Object> map : list) {
					String credentialsUrlString = JSON.toJSONString(map);
					LoanOrderCredentialsUrl credentialsUrl = JSON.parseObject(credentialsUrlString,
							LoanOrderCredentialsUrl.class);
					if (credentialsUrl != null) {
						loanOrderCredentialsUrlMapper.insertSelective(credentialsUrl);
					}

				}

			}
		}*/
		// 借款人信息扩展
		//String custId = Identities.randomBase62(32);
		LoanOrderBorrowerExtendWithBLOBs borrowerExtend = JSON
				.parseObject((String) params.get("loan_order_borrower_extend"), LoanOrderBorrowerExtendWithBLOBs.class);
		//borrowerExtend.setCustId(custId);
		borrowerExtend.setOrderNo(orderNo);
		loanOrderBorrowerExtendMapper.insertSelective(borrowerExtend);
		// 房产信息扩展
		/*LoanOrderHouseExtend houseExtend = JSON.parseObject((String) params.get("loan_order_house_extend"),
				LoanOrderHouseExtend.class);
		if (houseExtend != null) {
			houseExtend.setCustId(borrowerExtend.getCustId());
			houseExtend.setOrderNo(orderNo);
			loanOrderHouseExtendMapper.insertSelective(houseExtend);
		}*/

		// 调用报单人同步接口
		try {
			timingTaskService.providerUpdate(orderInfo.getProviderNo());
		} catch (Exception e) {
			logger.error("Exception" + e);
		}

		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		result.put(SystemConst.retMsg, "报单信息数据同步成功");
		return result;
	}

	/**
	 * 
	 * TODO 更新审批节点及报单状态至碰碰旺
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#updateApproval(com.lhhs.loan.dao.domain.LoanOrderInfo,
	 *      com.lhhs.loan.dao.domain.LoanOrderInfoDetail,
	 *      com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs)
	 */
	@Async
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateApproval(String orderNo) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();// 报单详情表
		Map<String, Object> orderInfoDetailMap = new HashMap<String, Object>();// 报单详情表

		LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		//如果是小贷自己报单不同步
		if(loanOrderInfo.getIsLoanAdd().equals("Y")){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "小贷自己报单不同步");
			return ret;
		}
		LoanOrderInfoDetail  loanOrderInfoDetail = loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);
		if (loanOrderInfo != null) {
			orderInfoMap.put("orderNo", loanOrderInfo.getOrderNo());
			orderInfoMap.put("orderStatus", loanOrderInfo.getOrderStatus());
			orderInfoMap.put("productNo", loanOrderInfo.getProductNo());
			orderInfoMap.put("childProductNo", loanOrderInfo.getChildProductNo());
		}
		int orderStatus =loanOrderInfo.getOrderStatus();
		//如果是终审、放款申请、放款审核、放款确认  不同步
		if(3==orderStatus||4==orderStatus||5==orderStatus||8==orderStatus||13==orderStatus||14==orderStatus||15==orderStatus){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "面审、终审、放款申请、放款确认  不同步");
			return ret;
		}
		if (loanOrderInfoDetail != null) {
			Map<String, Object> capitalInfo = loanCapitalInfoMapper.searchAmountPaidByOrderNo(loanOrderInfo.getOrderNo());
			if(capitalInfo!=null){
				orderInfoDetailMap.put("actualAmount", capitalInfo.get("sumAmount"));
				orderInfoDetailMap.put("actualRate", capitalInfo.get("amountRate"));
				orderInfoDetailMap.put("actualRateUnit", capitalInfo.get("amountRateUnit"));
			}
			orderInfoDetailMap.put("orderNo", loanOrderInfoDetail.getOrderNo());
			orderInfoDetailMap.put("actualTerm", loanOrderInfoDetail.getLoanTerm());
			orderInfoDetailMap.put("actualTermUnit", loanOrderInfoDetail.getLoanTermUnit());
			orderInfoDetailMap.put("actualLoanDate", loanOrderInfoDetail.getActualLoanDate());
			orderInfoDetailMap.put("fundOwner", loanOrderInfoDetail.getFundOwner());
		}
		List<LoanExTaskWithBLOBs> listRecord = loanExTaskMapper.transTaskByOrderNo(loanOrderInfo.getOrderNo());
		String orderInfo = JSON.toJSONString(loanOrderInfo);
		String orderInfoDetail = JSON.toJSONString(orderInfoDetailMap);
		String loanExTask = JSON.toJSONString(listRecord);
		params.put("mhdCode", "01");
		params.put("lhhs_order_info", orderInfo);
		params.put("lhhs_order_info_detail", orderInfoDetail);
		params.put("lhhs_ex_task", loanExTask);
		String paramsString = JSON.toJSONString(params);
		try {
			ret = restTemplateComponent.post("/loanOrderController/syncOrderInfo", paramsString, Map.class);
			if (SystemConst.SUCCESS.equals((ret.get(SystemConst.retCode)))) {
				//定时任务更新状态
				taskService.timedTask("/clientManager/operationClientInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.SUCCESS,ServiceType.UPDATEAPPROVAL.getIndex());
			}else{
				//定时任务添加任务
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEAPPROVAL.getIndex());
			}
		} catch (Exception e) {
			logger.error("Exception"+e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			//定时任务添加任务
			taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
			orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEAPPROVAL.getIndex());
		}
		return ret;

	}
	/**
	 * 
	 * TODO 更新审批节点及报单状态至碰碰旺
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#updateApproval(com.lhhs.loan.dao.domain.LoanOrderInfo,
	 *      com.lhhs.loan.dao.domain.LoanOrderInfoDetail,
	 *      com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs)
	 */
	@Async
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateApproval(String orderNo,String procId) throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();// 报单详情表
		Map<String, Object> orderInfoDetailMap = new HashMap<String, Object>();// 报单详情表

		LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		String procInsId=loanOrderInfo.getProcInsId();
		//如果是小贷自己报单不同步
		if(loanOrderInfo.getIsLoanAdd().equals("Y")){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "小贷自己报单不同步");
			return ret;
		}
		LoanOrderInfoDetail  loanOrderInfoDetail = loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);
		if (loanOrderInfo != null) {
			orderInfoMap.put("orderNo", loanOrderInfo.getOrderNo());
			orderInfoMap.put("orderStatus", loanOrderInfo.getOrderStatus());
			orderInfoMap.put("productNo", loanOrderInfo.getProductNo());
			orderInfoMap.put("childProductNo", loanOrderInfo.getChildProductNo());
		}
		int orderStatus =loanOrderInfo.getOrderStatus();
		
/*		if("20171102527439".equals(orderNo)){
			logger.error("订单号："+orderNo);
		}*/
		
		//如果是终审、放款申请、放款审核、放款确认  不同步
		if(3==orderStatus||4==orderStatus||5==orderStatus||8==orderStatus||13==orderStatus
				||14==orderStatus||15==orderStatus||orderStatus>=79){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "终审、放款申请、放款确认、放款申请修改、补件  不同步");
			return ret;
		}
		
		if (loanOrderInfoDetail != null) {
			Map<String, Object> capitalInfo = loanCapitalInfoMapper.searchAmountPaidByOrderNo(loanOrderInfo.getOrderNo());
			if(capitalInfo!=null){
				orderInfoDetailMap.put("actualAmount", capitalInfo.get("sumAmount"));
				orderInfoDetailMap.put("actualRate", capitalInfo.get("amountRate"));
				orderInfoDetailMap.put("actualRateUnit", capitalInfo.get("amountRateUnit"));
			}
			orderInfoDetailMap.put("orderNo", loanOrderInfoDetail.getOrderNo());
			orderInfoDetailMap.put("actualTerm", loanOrderInfoDetail.getLoanTerm());
			orderInfoDetailMap.put("actualTermUnit", loanOrderInfoDetail.getLoanTermUnit());
			orderInfoDetailMap.put("actualLoanDate", loanOrderInfoDetail.getActualLoanDate());
			orderInfoDetailMap.put("fundOwner", loanOrderInfoDetail.getFundOwner());
		}
		List<TaskVo> listRecord = actCommentService.transTaskByProcInsId(procInsId);
		TaskVo one=null;
		//最后一条审批记录是否推送
		LoanExTask tempTask=null;
		if(listRecord==null||listRecord.size()<1){
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
			ret.put(SystemConst.retMsg, "无同步审批记录");
			return ret;
		}
		one=listRecord.get(0);
		//最后一条审批记录处理，如果最后一条审批记录不在返回节点中，并且为拒贷撤单需要返回给碰碰旺
		EnumOrderNodeOther nodeone=EnumOrderNodeOther.getEnumOrderNode(one.getTaskDefKey());
		
		List<LoanExTask> listloanExTask =new ArrayList();
		Map<Integer,LoanExTask> tempMap=Maps.newHashMap();
		int max=0;
		for(int i=0;i<listRecord.size();i++){
			TaskVo vo=listRecord.get(i);
			EnumOrderNodeOther orderNode=EnumOrderNodeOther.getEnumOrderNode(vo.getTaskDefKey());
			
			if(orderNode!=null&&tempMap.get(orderNode.getIndex())==null){
				
				if(nodeone!=null){
					max=nodeone.getIndex();
				}else if(max==0){
					max=orderNode.getIndex();
				}
				if(max<orderNode.getIndex())continue;
				LoanExTask temp=new LoanExTask();
				temp.setLetTaskid(vo.getId());
				temp.setLetEmployeename(vo.getAssigneeName());
				//temp.setLetEmployeeno(new Integer(vo.getAssignee()));
				temp.setLetTime(vo.getTaskEndDate());
				temp.setLetDeclarationformid(orderNo);
				temp.setLetRemark(vo.getMsg());
				temp.setLetSuggestion(vo.getMsg());
				temp.setLetNodeStatus(new Integer(orderNode.getId()));
				temp.setLetNode(new Integer(orderNode.getId1()));
				if(i!=0){
					temp.setLetResult(0);
				}else{
					//设置节点编号
					loanOrderInfo.setTaskDefKey(orderNode.getId1());
					//同意
					if("1".equals(vo.getPass())||"5".equals(vo.getPass())){
						temp.setLetResult(0);
						//撤单
					}else if("91".equals(vo.getPass())){
						temp.setLetResult(3);
						//拒贷设置当前节点
						temp.setLetNodeStatus(new Integer(orderNode.getId1()));
						//设置节点编号
						loanOrderInfo.setTaskDefKey(orderNode.getId1());
						//拒贷
					}else if("92".equals(vo.getPass())){
						temp.setLetResult(1);
						temp.setLetNodeStatus(new Integer(orderNode.getId1()));
						
					}
				}
				tempMap.put(orderNode.getIndex(), temp);
				
			}
		}
		int size=tempMap.values().size();
		LoanExTask extask_max=tempMap.get(max);
		//通过后节点状态不一致问题。
		if(extask_max.getLetResult()==0){
			EnumOrderNodeOther nextNode=EnumOrderNodeOther.getEnumOrderNodeId(loanOrderInfo.getOrderStatus().toString());
			if(nextNode!=null&&nextNode.getIndex()-1>max)max=nextNode.getIndex()-1;
		}
		//判断是否有跳过节点
		if(max>size){
			//下户
			if(max>1){
				LoanExTask extask=tempMap.get(2);
				if(extask==null){
					extask=tempMap.get(1);
					LoanExTask newTask=new LoanExTask();
		    		//复制loanPayPlan属性
		    		BeanUtils.copyProperties(extask,newTask);
		    		EnumOrderNodeOther node=EnumOrderNodeOther.getEnumOrderNode(2);
		    		newTask.setLetNodeStatus(new Integer(node.getId()));
		    		newTask.setLetNode(new Integer(node.getId1()));
		    		newTask.setLetResult(0);
		    		newTask.setLetRemark("");
					tempMap.put(2, newTask);
				}
			}
			//权证
			if(max==6){
				LoanExTask extask=tempMap.get(3);
				if(extask==null){
					extask=tempMap.get(6);
					LoanExTask newTask3=new LoanExTask();
					LoanExTask newTask4=new LoanExTask();
					LoanExTask newTask5=new LoanExTask();
		    		//复制loanPayPlan属性
		    		BeanUtils.copyProperties(extask,newTask3);
		    		EnumOrderNodeOther node=EnumOrderNodeOther.getEnumOrderNode(3);
		    		newTask3.setLetNodeStatus(new Integer(node.getId()));
		    		newTask3.setLetNode(new Integer(node.getId1()));
		    		newTask3.setLetResult(0);
		    		newTask3.setLetRemark("");
		    		tempMap.put(3, newTask3);
		    		//复制loanPayPlan属性
		    		BeanUtils.copyProperties(extask,newTask4);
		    		EnumOrderNodeOther node4=EnumOrderNodeOther.getEnumOrderNode(4);
		    		newTask4.setLetNodeStatus(new Integer(node4.getId()));
		    		newTask4.setLetNode(new Integer(node4.getId1()));
		    		newTask4.setLetResult(0);
		    		newTask4.setLetRemark("");
		    		tempMap.put(4, newTask4);
		    		//复制loanPayPlan属性
		    		BeanUtils.copyProperties(extask,newTask5);
		    		EnumOrderNodeOther node5=EnumOrderNodeOther.getEnumOrderNode(5);
		    		newTask5.setLetNodeStatus(new Integer(node5.getId()));
		    		newTask5.setLetNode(new Integer(node5.getId1()));
		    		newTask5.setLetResult(0);
		    		newTask5.setLetRemark("");
					tempMap.put(5, newTask5);
				}
				
			}
		}
		if(nodeone==null && ("91".equals(one.getPass())||"92".equals(one.getPass()))){
			max=max+1;
			if(max>6)max=6;
			EnumOrderNodeOther nodenew=EnumOrderNodeOther.getEnumOrderNode(max);
			tempTask=new LoanExTask();
			tempTask.setLetTaskid(one.getId());
			tempTask.setLetEmployeename(one.getAssigneeName());
			//temp.setLetEmployeeno(new Integer(vo.getAssignee()));
			tempTask.setLetTime(one.getTaskEndDate());
			tempTask.setLetDeclarationformid(orderNo);
			tempTask.setLetRemark(one.getMsg());
			tempTask.setLetSuggestion(one.getMsg());
			tempTask.setLetNodeStatus(new Integer(nodenew.getId1()));
			tempTask.setLetNode(new Integer(nodenew.getId1()));
			//设置节点编号
			loanOrderInfo.setTaskDefKey(nodenew.getId1());
			//撤单
			if("91".equals(one.getPass())){
				tempTask.setLetResult(3);
				//拒贷
			}else if("92".equals(one.getPass())){
				tempTask.setLetResult(1);
			}
			tempMap.put(max, tempTask);
		}
		
		//组装审批意见列表
		for(int i=max;i>0;i--){
			if(tempMap.get(i)!=null){
				LoanExTask temp_t=tempMap.get(i);
				listloanExTask.add(temp_t);
			}
		}
		
		//int taskId=(new Date()).get
		//baseTask.setLetTaskid(letTaskid);
		//0:同意\r\n1:拒贷\r\n2:补件\r\n3:撤单
		String orderInfo = JSON.toJSONString(loanOrderInfo);
		String orderInfoDetail = JSON.toJSONString(orderInfoDetailMap);
		String loanExTask = JSON.toJSONString(listloanExTask);
		params.put("mhdCode", "01");
		params.put("lhhs_order_info", orderInfo);
		params.put("lhhs_order_info_detail", orderInfoDetail);
		params.put("lhhs_ex_task", loanExTask);
		String paramsString = JSON.toJSONString(params);
		try {
			ret = restTemplateComponent.post("/loanOrderController/syncOrderInfo", paramsString, Map.class);
			if (SystemConst.SUCCESS.equals((ret.get(SystemConst.retCode)))) {
				//定时任务更新状态
				taskService.timedTask("/clientManager/operationClientInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.SUCCESS,ServiceType.UPDATEAPPROVAL.getIndex());
			}else{
				//定时任务添加任务
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEAPPROVAL.getIndex());
			}
		} catch (Exception e) {
			logger.error("Exception"+e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			//定时任务添加任务
			taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
			orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEAPPROVAL.getIndex());
		}
		return ret;

	}
	/**
	 * 
	 * TODO 修改报单、借款人等基本信息同步至碰碰旺
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#updateBasicInfo(com.lhhs.loan.dao.domain.LoanOrderInfoDetail,
	 *      com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs)
	 */
	@Async
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateBasicInfo(LoanOrderInfoDetail loanOrderInfoDetail,
			LoanOrderBorrowerExtendWithBLOBs loanOrderBorrowerExtendWithBLOBs) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> ret = new HashMap<String, Object>();
		LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(loanOrderInfoDetail.getOrderNo());
		//如果是小贷自己报单不同步
		if(loanOrderInfo.getIsLoanAdd().equals("Y")){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "小贷自己报单不同步");
			return ret;
		}
		String liveAddr = loanOrderBorrowerExtendWithBLOBs.getLiveAddress();
		String comAddr = loanOrderBorrowerExtendWithBLOBs.getCorAddress();
		if (StringUtils.isNotEmpty(liveAddr)) {
			loanOrderBorrowerExtendWithBLOBs.setLiveAddress(liveAddr.replaceAll("-", "&"));
		}
		if (StringUtils.isNotEmpty(comAddr)) {
			loanOrderBorrowerExtendWithBLOBs.setCorAddress(liveAddr.replaceAll("-", "&"));
		}
		String detailString = JSON.toJSONString(loanOrderInfoDetail);
		String borrowerString = JSON.toJSONString(loanOrderBorrowerExtendWithBLOBs);
		params.put("mhdCode", "02");
		params.put("lhhs_order_info_detail", detailString);
		params.put("borrower_info_extend", borrowerString);
		String paramsString = JSON.toJSONString(params);
		try {

			ret = restTemplateComponent.post("/loanOrderController/syncOrderInfo", paramsString, Map.class);
			if (SystemConst.SUCCESS.equals((ret.get(SystemConst.retCode)))) {
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				loanOrderInfoDetail.getOrderNo(), SystemConst.POST, SystemConst.SUCCESS, ServiceType.UPDATEBASICINFO.getIndex());
			}else{
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				loanOrderInfoDetail.getOrderNo(), SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEBASICINFO.getIndex());
			}
		} catch (Exception e) {
			logger.error("Exception"+e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
			loanOrderInfoDetail.getOrderNo(), SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEBASICINFO.getIndex());
		}

		return ret;
	}

	/**
	 * TODO 同步抵押物信息至碰碰旺
	 * 
	 * @see com.lhhs.loan.service.transport.OrderInfoService#updateMortgageInfo(java.util.List,
	 *      java.util.List, java.lang.String)
	 */
	@Async
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateMortgageInfo(List<LoanOrderHouseExtend> houseList,
			List<LoanOrderCarExtend> carList, String orderNo) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> ret = new HashMap<String, Object>();
		LoanOrderInfo loanOrderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		//如果是小贷自己报单不同步
		if(loanOrderInfo.getIsLoanAdd().equals("Y")){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "小贷自己报单不同步");
			return ret;
		}
		List<LoanOrderHouseExtend> paramsList = new ArrayList<LoanOrderHouseExtend>();
		if (houseList != null && houseList.size() > 0) {
			for (LoanOrderHouseExtend house : houseList) {
				String houseAddr = house.getHouseAddress();
				if (StringUtils.isNotEmpty(houseAddr)) {
					house.setHouseAddress(houseAddr.replaceAll("-", "&"));
				}
				paramsList.add(house);
			}
		}
		String houseString = JSON.toJSONString(paramsList);
		String carString = JSON.toJSONString(carList);
		String houseStr = houseString.replaceAll("valuationPrice", "valuation").replaceAll("mortgageRate",
				"mortgRate");
		params.put("mhdCode", "03");
		params.put("house_info_extend", houseStr);
		params.put("car_info_extend", carString);
		params.put("orderNo", orderNo);
		String paramsString = JSON.toJSONString(params);
		try {
			ret = restTemplateComponent.post("/loanOrderController/syncOrderInfo", paramsString, Map.class);
			if (SystemConst.SUCCESS.equals((ret.get(SystemConst.retCode)))) {
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.SUCCESS, ServiceType.UPDATEMORTGAGEINFO.getIndex());
			}else{
				taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
				orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEMORTGAGEINFO.getIndex());
			}
		} catch (Exception e) {
			logger.error("Exception"+e);
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			taskService.timedTask("/loanOrderController/syncOrderInfo", paramsString,
			orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.UPDATEMORTGAGEINFO.getIndex());
		}
		return ret;
	}

	@Override
	public Page findPageByEntity(Page page, OrderInfo params) {
		params.setPage(page);
		List<OrderInfo> resultList = loanOrderInfoMapper.findListByEntity(params);
		if(page==null){
			page=new Page();
		}
		page.setResultList(resultList);
		page.setTotalCount(loanOrderInfoMapper.countListByEntity(params));
		return page;
	}
	
	@Override
	public OrderInfoDetailVo getEntityById(String id) {
		SecurityUser emp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		OrderInfoDetailVo detail = loanOrderInfoMapper.getEntityById(id);
		if(detail != null && detail.getOrderNo()!= null) {
			LoanOrderBorrowerExtendWithBLOBs borrowerExtendWithBLOBs = loanOrderBorrowerExtendMapper.findBorrowerInfoExtendListByOrderNo(detail.getOrderNo());
	        if(null!=borrowerExtendWithBLOBs&&null!=detail.getCustomerManager()&&!emp.getUserId().toString().equals(detail.getCustomerManager())){
	        	if(StringUtils.isNotEmpty(borrowerExtendWithBLOBs.getMobile())){
	        		borrowerExtendWithBLOBs.setMobile(borrowerExtendWithBLOBs.getMobile().substring(0, 3)+"****"+borrowerExtendWithBLOBs.getMobile().substring(borrowerExtendWithBLOBs.getMobile().length()-4, borrowerExtendWithBLOBs.getMobile().length()));
				}
				if(StringUtils.isNotEmpty(borrowerExtendWithBLOBs.getIdNum())){
					borrowerExtendWithBLOBs.setIdNum(borrowerExtendWithBLOBs.getIdNum().substring(0, 1)+"***************"+borrowerExtendWithBLOBs.getIdNum().substring(borrowerExtendWithBLOBs.getIdNum().length()-1, borrowerExtendWithBLOBs.getIdNum().length()));
				}
			}
			detail.setBorrowerExtendWithBLOBs(borrowerExtendWithBLOBs);
			
			List<LoanExTaskWithBLOBs> loanExTaskWithBLOBList = loanExTaskMapper.transTaskByOrderNo(detail.getOrderNo());
			detail.setLoanExTaskWithBLOBList(loanExTaskWithBLOBList);
			
			List<LoanOrderCarExtend> carExtendList = carExtendMapper.findCarExtendListByOrderNo(detail.getOrderNo());
			detail.setCarExtendList(carExtendList);
			
			List<LoanOrderHouseExtend> houseExtendList = houseExtendMapper.findHouseExtendListByOrderNo(detail.getOrderNo());
			if(null!=houseExtendList&&houseExtendList.size()>0&&null!=detail.getCustomerManager()&&!emp.getUserId().toString().equals(detail.getCustomerManager())){
				for (int i = 0; i < houseExtendList.size(); i++) {
					LoanOrderHouseExtend orderHouseExtend =houseExtendList.get(i);
					if(StringUtil.isNotEmpty(orderHouseExtend.getHouseAddress())){
						orderHouseExtend.setHouseAddress(orderHouseExtend.getHouseAddress().replace(orderHouseExtend.getHouseAddress().substring(2, orderHouseExtend.getHouseAddress().length()), "*****************"));
					}
				}
			}
			detail.setHouseExtendList(houseExtendList);
		}
		
		return detail;
	}
	
	/**
	 * 冻结账户
	 * @param orderNo
	 * @return
	 */
	public String freeze(String orderNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		List<LoanAccountsTrans> accountsTranList = new ArrayList<LoanAccountsTrans>();
		List<LoanCapitalInfo> capitalInfoList = loanCapitalInfoMapper.selectLoanCapitalInfo(map );
		for (LoanCapitalInfo ci : capitalInfoList) {
			LoanAccountsTrans at = new LoanAccountsTrans();
			at.setAccountId(ci.getAccountId());
			at.setSubjectId(SystemConst.SubjectInfo.SUBJECTID_FREEZEINVEST);
			at.setAmount(ci.getAmountPaid());
			SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
			at.setLastUser(user.getUserId().toString());
			at.setOrderNo(orderNo);
			
			accountsTranList.add(at);
		}
		return accountTransactionService.accountFreezeInTrans(accountsTranList);
	}

	@Autowired
	private LoanCapitalEarningMapper loanCapitalEarningMapper;
	@Autowired
	private LoanCapitalExpenditureMapper loanCapitalExpenditureMapper;
	@Autowired
	private LoanParasConfigMapper loanParasConfigMapper;
	@Autowired(required=false)
	private ApprovalProcessService approvalProcessService;
	@Autowired
	private LoanExTaskMapper exTaskMapper;
	@Override
	@Transactional(rollbackFor= Exception.class)
	public Map<String, Object> updateOrderInfo(OrderInfoVo vo) {
		String orderNo = vo.getOrderNo();
		
		int ret = 0;
		//保存订单信息
		LoanOrderInfo loanOrderInfo = vo.cv.loanOrderInfo();
		ret += loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret += loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(vo.cv.loanOrderInfoDetail());
		if(ret < 2){
			throw new RuntimeException("更新订单信息失败");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
        Map<String, Object> basicInfo = approvalProcessService.selectBasicInfo(params);
		
		String orgId = basicInfo.get("fund_owner") == null ? null : "" + basicInfo.get("fund_owner");
		String orgName = basicInfo.get("org_name") == null ? null : "" + basicInfo.get("org_name");
		
		LoanExTaskWithBLOBs record = new LoanExTaskWithBLOBs();
		record.setLetDeclarationformid(orderNo);
		if(StringUtils.isNoneBlank(vo.getLetEmployeeno())) {
			record.setLetEmployeeno(Integer.parseInt(vo.getLetEmployeeno()));
			ret = exTaskMapper.updateFengKongExTaskByOrderNo(record);
			if(ret == 0){
				throw new RuntimeException("更新风控经理失败");
			}
			
		}
		// 保存订单详情中的借款信息
		LoanOrderBorrowerExtendWithBLOBs borrower = vo.cv.orderBorrower();
		ret = loanOrderBorrowerExtendMapper.updateOrderBorrowerExtendBLOBsByOrderNo(borrower);;
		if(ret == 0){
			throw new RuntimeException("订单详情中的借款信息失败");
		}
		
		List<LoanOrderHouseExtend> houseExtendList = vo.cv.houseList();
		for (LoanOrderHouseExtend houseExtend : houseExtendList) {
			ret = loanOrderHouseExtendMapper.updateByPrimaryKeySelective(houseExtend);
			if(ret ==0){
				throw new RuntimeException("保存抵押物房源失败");
			}
		}
		
		//参数
		LoanParasConfig loanParasConfig = vo.cv.loanParasConfig();
		if(StringUtils.isNotBlank(loanParasConfig.getLpcId())){
			loanParasConfigMapper.updateByPrimaryKey(loanParasConfig);
		}else {
			loanParasConfig.setLpcId(UUID.randomUUID().toString().replace("-", ""));
			loanParasConfigMapper.insertSelective(loanParasConfig);
		}
		
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		int loanCapEarnCount=0, loanCapInfoCount=0, loanCapExpendCount=0;
		
		List<LoanCapitalEarning> loanCapEarn= vo.cv.loanCapitalEarningList();
		List<LoanCapitalInfo> loanCapInfo= vo.cv.loanCapitalInfoList();
		List<LoanCapitalExpenditure> loanCapExpend= vo.cv.loanCapitalExpenditureList();
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
				
		if (loanCapEarnCount ==loanCapEarn.size() && loanCapInfoCount==loanCapInfo.size() && loanCapExpendCount==loanCapExpend.size()) {
			   resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			   resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u6210\u529f");
		} else {
			throw new RuntimeException("保存放款申请表失败");

		}
		
		if((int)(vo.getCurrentNode()) == (int)(AuditingNodeType.FKQR.getIndex())){
			LoanExTaskWithBLOBs entity = StrUtils.isNullOrEmpty(vo.getShForm()) ? null : JSON.parseObject(vo.getShForm(), LoanExTaskWithBLOBs.class);
			if(null != entity){
				if(entity.getLetNode() == AuditingNodeType.FKQR.getIndex()){
					entity.setLetTime(new Date());
					String isPayPlanTemp = null;
					String isLoanRecordTemp = null;
					String isPayPlanCompanyTemp = null;
					
					Integer letNode = AuditingNodeType.FKQR.getIndex();
					Integer approvalNodeStatus = AuditingNodeType.FKQR.getIndex();
					
					ret = 0;
					ret = approvalProcessService.updateExTaskByOrderNo(entity,
							letNode, 
							approvalNodeStatus, 
							null, 
							null, 
							null,
							null, 
							null, 
							isPayPlanTemp,
							isLoanRecordTemp,
							isPayPlanCompanyTemp,
							null, 
							null,
							null,
							null,
							null,
							null);
					if(ret == 1){
						resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
						resultMap.put(SystemConst.retMsg, "\u63d0\u4ea4\u6210\u529f"); // 提交成功
					}else{
						resultMap.put(SystemConst.retCode, SystemConst.FAIL);
						resultMap.put(SystemConst.retMsg, "\u63d0\u4ea4\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u002e\u002e\u002e"); // 提交失败，请联系管理员...
						throw new RuntimeException("提交失败，请联系管理员");
					}
				}
			}
		}
		
		if(vo.getApplyNode() == AuditingNodeType.FKSQ.getIndex() && vo.getCurrentNode() == AuditingNodeType.FKSQ.getIndex()){
			SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();
			Date currentDate = new Date();
			record.setLetTime(currentDate);
			record.setLetEmployeename(loanEmp.getStaffName());
			record.setLetEmployeeno(loanEmp.getUserId().intValue());
			record.setLetDeclarationformid(orderNo);
			record.setLetResult(0);
			
			String isPayPlanTemp = null;
			String isLoanRecordTemp = null;
			String isPayPlanCompanyTemp = null;
			
			ret = approvalProcessService.updateExTaskByOrderNo(record,
					AuditingNodeType.FKSQ.getIndex(), 
					AuditingNodeType.FKSQ.getIndex(), 
					null, 
					null, 
					null,
					null, 
					null, 
					isPayPlanTemp,
					isLoanRecordTemp,
					isPayPlanCompanyTemp,
					null, 
					null,
					null,
					null,
					null,
					null);
			if(ret == 1){
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
				resultMap.put(SystemConst.retMsg, "\u63d0\u4ea4\u6210\u529f"); // 提交成功
			}else{
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u63d0\u4ea4\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u002e\u002e\u002e"); // 提交失败，请联系管理员...
				throw new RuntimeException("提交失败，请联系管理员");
			}
		}
 		
//		注释冻结
// 		String code = freeze(orderNo);
// 		if(StringUtils.equals(code, "01")) {
// 			throw new RuntimeException("保存失败");
// 		}
 		
		return resultMap;
	}

    /**
     * 获取订单总额、当天订单总数
     * @return
     */
	@Override
	public Map<String, Object> getOrderSumInfo(Map<String, Object> param) {
		return loanOrderInfoMapper.getOrderSumInfo(param);
	}
	@Autowired
	private LoanQuartersMapper loanQuartersMapper;
	@Autowired
	LoanOrganizationMapper loanOrganizationMapper ;
	@Autowired
	private LoanDeptMapper deptMapper ;
	@Autowired
	private LoanGroupMapper groupMapper;
	@Override
	public List<String> asyncTeamManager(Integer leEmpId) {
		List<String> names = new ArrayList<>();
		//客户经理
		User emp0 = userApi.get(leEmpId.toString());
		//团队经理
		User emp1=null;
		//事业部总经理
		User emp2=null;
		if(null!=emp0&&!StrUtils.isNullOrEmpty(emp0.getDeptId())){
			User vo= new User();
			vo.setUnionId(emp0.getUnionId());
			vo.setCompanyId(emp0.getCompanyId());
			vo.setDeptId(emp0.getDeptId());
			vo.setGrade(2);
			emp1 = userApi.queryTeamLeader(vo);
			vo.setGrade(1);
			emp2 = userApi.queryTeamLeader(vo);
		}
		//客户经理组别
		Dept group = deptApi.get(emp0.getDeptId());
		//客户经理部门
		Dept dept = null;
		if(null!=group&&null!=group.getParentId()&&group.getParentId().intValue()!=0){
			dept=deptApi.get(group.getParentId().toString());
		}
		String grName = (group == null)?"":group.getName();
		String ldName = (dept  ==  null)?"":dept.getName();
		String name =  ldName + "" + grName;
		names.add(name); //所属编制
		if(emp1!=null){
			names.add(emp1.getStaffName());
		}else{
			names.add("");
		}
		if(emp2!=null){
			names.add(emp2.getStaffName());
		}else{
			names.add("");
		}
		return names;
	}
	
	/**
	 * 递归查询
	 * @param empList
	 * @param emp
	 * @return
	 */
	private List<LoanEmp> digui(List<LoanEmp> empList, LoanEmp emp){
		if(emp != null) {
			Integer lqDeptId = emp.getLeDeptId();
			Integer lqQuartersId = emp.getLeQuartersId();
			String branchCompanyId = emp.getBranchCompanyId();
			Integer parentId = loanQuartersMapper.selectParentIdByIdDept(lqQuartersId, lqDeptId);
			if(parentId != null) {
//				LoanEmp empParent = loanEmpMapper.selectByDeptQuarters(lqDeptId,parentId);
				LoanEmp empParent = loanEmpMapper.selectByDeptQuartersTwo(lqDeptId,parentId,branchCompanyId);
				if(empParent != null && !empParent.getLeEmpId().equals(emp.getLeEmpId())) {
					empList.add(empParent);
					digui(empList,empParent);
				}
			}
		}
		return empList;
	}

	@Override
	public Page getOrderCountList(Page page, LoanOrderInfo params) {
		params.setPage(page);
		List<OrderInfo> resultList = loanOrderInfoMapper.getOrderCountList(params);
		if(page==null){
			page=new Page();
		}
		page.setResultList(resultList);
		page.setTotalCount(loanOrderInfoMapper.getOrderCountListCount(params));
		return page;
	}

	@Override
	public Map<String, Object> getVariousTotalCount(LoanOrderInfo entity) {
		return loanOrderInfoMapper.getVariousTotalCount(entity);
	}

	
	@Override
	public Map<String, Object> getOrderInfoEchartsList(Date time, String timeUnit,LoanOrderInfo entity) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			entity.setTimeUnit(timeUnit);
			Date endTime = new Date();
			if("周".equals(timeUnit)){
				 List<Map<String, Object>> returnOrderCountList = new ArrayList<Map<String, Object>>();
				 List<Map<String, Object>> returnDealCountList = new ArrayList<Map<String, Object>>();
				 //第一周
				 entity.setWeekNum("04");
				 addWeekDate(entity,returnOrderCountList,returnDealCountList);
				 //第二周
				 entity.setWeekNum("03");
				 addWeekDate(entity,returnOrderCountList,returnDealCountList);
			     //第三周
				 entity.setWeekNum("02");
				 addWeekDate(entity,returnOrderCountList,returnDealCountList);
			     //第四周
				 entity.setWeekNum("01");
				 addWeekDate(entity,returnOrderCountList,returnDealCountList);
			     returnMap.put("totalDealCountList", returnDealCountList);
			     returnMap.put("totalOrderCountList", returnOrderCountList);
			}else{
				 entity.setCustomerManagerBeginingTime(time);
				 entity.setCustomerManagerEndingTime(endTime);
				 List<Map<String, Object>> totalOrderCountList = loanOrderInfoMapper.getOrderCountByTime(entity);
				 List<Map<String, Object>> totalDealCountList = loanOrderInfoMapper.getDealCountByTime(entity);
				 returnMap.put("totalOrderCountList", totalOrderCountList);
				 returnMap.put("totalDealCountList", totalDealCountList);
			 }
			returnMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			returnMap.put(SystemConst.retMsg, "查询成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put(SystemConst.retCode, SystemConst.FAIL);
			returnMap.put(SystemConst.retMsg, "系统异常"); 
		}
		return returnMap;
	
	}
	

	@Override
	public List<OrderInfo> orderCountListExport(LoanOrderInfo entity) {
		entity.setPage(null);
		List<OrderInfo> resultList = loanOrderInfoMapper.getOrderCountList(entity);
		return resultList;
	}
	
	public void addWeekDate(LoanOrderInfo entity, List<Map<String, Object>> returnOrderCountList,List<Map<String, Object>> returnDealCountList){
		 List<Map<String, Object>>  week1OrderCountList = loanOrderInfoMapper.getOrderCountByTime(entity);
		 List<Map<String, Object>>  week1DealCountList = loanOrderInfoMapper.getDealCountByTime(entity);
		 returnOrderCountList.addAll(week1OrderCountList);
		 returnDealCountList.addAll(week1DealCountList);
	}

	@Override
	public List<LoanAccountCard> getAccountCardList(LoanOrderInfo orderInfo) {
		return loanOrderInfoMapper.getAccountCardList(orderInfo);
	}
}
