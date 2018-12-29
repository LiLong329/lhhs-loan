package com.lhhs.loan.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.AuditingHandledResultType;
import com.lhhs.loan.common.shared.enums.AuditingHandledType;
import com.lhhs.loan.common.shared.enums.AuditingNodeStatusType;
import com.lhhs.loan.common.shared.enums.AuditingNodeType;
import com.lhhs.loan.common.shared.enums.OrderStatusType;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCapitalEarningMapper;
import com.lhhs.loan.dao.LoanCapitalExpenditureMapper;
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.LoanChildProductMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanExTaskMapper;
import com.lhhs.loan.dao.LoanFundTaskMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanOrderCredentialsMapper;
import com.lhhs.loan.dao.LoanOrderHouseExtendMapper;
import com.lhhs.loan.dao.LoanOrderInfoDetailMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanOrgSupportAreasMapper;
import com.lhhs.loan.dao.LoanOrganizationMapper;
import com.lhhs.loan.dao.LoanParasConfigMapper;
import com.lhhs.loan.dao.LoanParentProductMapper;
import com.lhhs.loan.dao.LoanQuartersMapper;
import com.lhhs.loan.dao.RelevantPersonOrderMapper;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.LoanFundTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanQuarters;
import com.lhhs.loan.dao.domain.LoanRecordTemp;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.dao.domain.vo.LoanOrderInfoVo;
import com.lhhs.loan.service.ApprovalProcessService;
import com.lhhs.loan.service.account.AccountTransactionService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.loan.service.transport.ProviderTransportService;


/**
 * 待办事项中业务审批流程Service实现类
 * @author kernel.org
 *
 */
@SuppressWarnings("all")
@Service
public class ApprovalProcessServiceImpl implements ApprovalProcessService {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalProcessServiceImpl.class);
	
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private LoanDeptMapper loanDeptMapper;
	@Autowired
	private LoanFundTaskMapper loanFundTaskMapper;
	@Autowired
	private LoanQuartersMapper loanQuartersMapper;
	@Autowired
	private LoanOrderInfoDetailMapper loanOrderInfoDetailMapper;
	@Autowired
	private LoanOrderHouseExtendMapper loanOrderHouseExtendMapper;
	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;
	@Autowired
	private LoanCapitalEarningMapper loanCapitalEarningMapper;
	@Autowired
	private LoanCapitalExpenditureMapper loanCapitalExpenditureMapper;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper;
	@Autowired
	private LoanExTaskMapper loanExTaskMapper;
	@Autowired
	private LoanOrganizationMapper loanOrganizationMapper;
	@Autowired
	private LoanChildProductMapper loanChildProductMapper;
	@Autowired
	private LoanOrgSupportAreasMapper loanOrgSupportAreasMapper;
	@Autowired
	private LoanOrderCredentialsMapper loanOrderCredentialsMapper;
	//@Autowired
	//private OrderInfoService orderInfoService;
	@Autowired
	private ProviderTransportService providerTransportService;
	@Autowired
	private LoanParasConfigMapper loanParasConfigMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private LoanParentProductMapper loanParentProductMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private AccountTransactionService accountTransactionService;
	@Autowired
	private RelevantPersonOrderMapper relevantPersonOrderMapper;
	
	/**
	 * 查询我的待办任务列表
	 */
	public List<Map<String, Object>> selectMyNewTaskList(Map<String, Object> map, Page page) {
		List<Map<String, Object>> myNewTaskList = loanOrderInfoMapper.selectMyNewTaskList(map);
		Integer count = loanOrderInfoMapper.selectMyNewTaskListCount(map);
		page.setResultList(myNewTaskList);
		page.setTotalCount(count);
		return myNewTaskList;
	}

	/**
	 * 查询报单人基本信息列表
	 */
	public Map<String, Object> selectBasicInfo(Map<String, Object> map) {
		return loanOrderInfoMapper.selectBasicInfo(map);
	}
	
	/**
     * selectDeptIdByZJName:根据部门名称为资金部查询部门id
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    public LoanDept selectLoanDeptByLdDeptId(Integer ldDeptId){
    	return loanDeptMapper.selectLoanDeptByLdDeptId(ldDeptId);
    }
	
	/**
     * selectByPrimaryKey:根据员工id查询LoanEmp对象
     * @author kernel.org
     * @param leEmpId
     * @return
     * @since JDK 1.8
     */
    public LoanEmp selectByPrimaryKey(Integer leEmpId){
    	return loanEmpMapper.selectByPrimaryKey(leEmpId);
    }
	
    /**
     * selectFundHandledRecord:查询项目审核记录表(审批流程节点)
     * @author kernel.org
     * @param orderNo
     * @return
     * @since JDK 1.8
     */
	public List<LoanFundTaskWithBLOBs> selectFundHandledRecord(String orderNo){
		return loanFundTaskMapper.selectFundHandledRecord(orderNo);
	}
    /**
     * selectFunderAndRiskerList:
     * 面审---会签---具体会签人：当前登录人所在部门的全部人员+集团下风控部所有人+集团下资金部所有人
	 * 集团下风控部所有人：查询根据两个条件：1.集团编号  2.部门名称=风控部
	 * 集团下资金部所有人：查询根据两个条件：1.集团编号  2.部门名称=资金部

     * @author kernel.org
     * @param 
     * @return
     * @since JDK 1.8
     */
    public List<LoanEmp> selectFunderAndRiskerList(LoanEmp emp){
    	List<LoanEmp> loanEmpList = loanEmpMapper.selectFunderAndRiskerList(emp);
    	return loanEmpList;
    }
    
    /**
	 * selectLoanQuartersByQuartersId:根据QuartersId查询对象
	 * @author kernel.org
	 * @param lqQuartersId
	 * @return
	 * @since JDK 1.8
	 */
	public LoanQuarters selectLoanQuartersByQuartersId(Integer lqQuartersId){
		return loanQuartersMapper.selectLoanQuartersByQuartersId(lqQuartersId);
	}
    
	/**
     * selectCustManagerByDeptId:根据部门编号查询下属的所有客户经理
     * @author kernel.org
     * @param leDeptId
     * @return
     * @since JDK 1.8
     */
    public List<LoanEmp> selectCustManagerByDeptId(Integer leDeptId){
    	return loanEmpMapper.selectCustManagerByDeptId(leDeptId);
    }
	
	/**
     * 查询放款申请,报单基本信息
     * selectOrderBasicInfo:
     * @author kernel.org
     * @param loanOrderInfoVo
     * @return
     * @since JDK 1.8
     */
    public LoanOrderInfoVo selectOrderBasicInfo(LoanOrderInfoVo loanOrderInfoVo){
    	LoanOrderInfoVo orderInfoVo = loanOrderInfoMapper.selectOrderBasicInfo(loanOrderInfoVo);
    	return orderInfoVo;
    }
    
    /**
	 * selectLoanOrderHouseExtend:查询放款申请中的房产扩展信息
	 * @author kernel.org
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	public List<LoanOrderHouseExtend> selectLoanOrderHouseExtend(LoanOrderHouseExtend entity){
		return loanOrderHouseExtendMapper.selectLoanOrderHouseExtend(entity);
	}
    
    /**
     * selectLoanOrderBorrowerExtendBLOBs:根据订单号查询放款申请的详细数据
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
    public LoanOrderBorrowerExtendWithBLOBs selectLoanOrderBorrowerExtendBLOBs(LoanOrderBorrowerExtendWithBLOBs entity){
    	return loanOrderBorrowerExtendMapper.selectLoanOrderBorrowerExtendBLOBs(entity);
    }
	
	/**
	 * 查询借款人基本信息列表
	 */
	public List<LoanOrderBorrowerExtendWithBLOBs> selectLoanOrderBorrowerExtendBLOBsList(Map<String, Object> map) {
		return loanOrderBorrowerExtendMapper.selectLoanOrderBorrowerExtendBLOBsList(map);
	}

	/**
	 * 查询放款申请表-资金方基本信息
	 */
	public List<LoanCapitalInfo> selectLoanCapitalInfo(Map<String, Object> map) {
		return loanCapitalInfoMapper.selectLoanCapitalInfo(map);
	}

	/**
     * selectOrganizationAccountInfo:根据订单号查询资金方对公账户信息
     * @author kernel.org
     * @param map
     * @return
     * @since JDK 1.8
     */
    public LoanOrganization selectOrganizationAccountInfo(Map<String, Object> map){
    	return loanOrganizationMapper.selectOrganizationAccountInfo(map);
    }
	
	/**
	 * 查询放款申请表-收入信息
	 */
	public List<LoanCapitalEarning> selectLoanCapitalEarning(Map<String, Object> map) {
		return loanCapitalEarningMapper.selectLoanCapitalEarning(map);
	}

	/**
	 * 查询放款申请表-支出信息
	 */
	public List<LoanCapitalExpenditure> selectLoanCapitalExpenditure(Map<String, Object> map) {
		return loanCapitalExpenditureMapper.selectLoanCapitalExpenditure(map);
	}

	/**
	 * 查询项目审核记录表(审批流程节点)
	 */
	public List<LoanExTaskWithBLOBs> selectHandledRecord(Map<String, Object> map) {
		return loanExTaskMapper.selectHandledRecord(map);
	}

	/**
     * 节点审核流程
     * @param loanExTaskWithBLOBs
     * @return
     */
	public int insert(LoanExTaskWithBLOBs record) {
		return 0;
	}

	/**
     * 根据订单号更新订单表
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("all")
	public int updateOrderInfoByOrderNo(LoanOrderInfo record) {
		int ret = 0;
			if(null != record){
				ret = loanOrderInfoMapper.updateByPrimaryKeySelective(record);
			}
			return ret;
	}
	
	/**
     * 根据订单号更新订单详情表
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("all")
	public int updateOrderInfoDetailByOrderNo(LoanOrderInfoDetail record) {
		int ret = 0;
			if(null != record){
				ret = loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(record);
			}
			return ret;
	}
	
	/**
	 * 更新借款基本信息列表
	 * @param record
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("all")
	public int updateOrderBorrowerExtendBLOBsByOrderNo(LoanOrderBorrowerExtendWithBLOBs record) {
		int ret = 0;
			if(null != record){
				ret = loanOrderBorrowerExtendMapper.updateOrderBorrowerExtendBLOBsByOrderNo(record);
			}
			return ret;
	}
	

	/**
     * update基本信息
     * @param loanOrderInfo
     * @param loanOrderInfoDetail
     * @param orderNo
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateBasicInfo(LoanOrderInfo loanOrderInfo,LoanOrderInfoDetail loanOrderInfoDetail, 
							   LoanOrderBorrowerExtendWithBLOBs loanOrderBorrowerExtendWithBLOBs, 
							   String orderNo, 
							   Integer customerOrigin,
							   List<RelevantPersonOrder> relevantPersonList) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		int ret = 0;
		
		//保存订单中的客户来源
		if(loanOrderInfo.getProviderNo()!=null||loanOrderInfo.getCustomerManager()!=null||customerOrigin != null){
			loanOrderInfo.setOrderNo(orderNo);
			loanOrderInfo.setCustomerOrigin(customerOrigin); 
			ret = this.updateOrderInfoByOrderNo(loanOrderInfo);
			if(ret == 0){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u4fdd\u5b58\u5ba2\u6237\u6765\u6e90\u5931\u8d25\uff01"); // 保存客户来源失败！
			}
		}
		result.clear();
		ret = 0;
		
		// 保存订单详情中的借款信息
		loanOrderInfoDetail.setOrderNo(orderNo);
		ret = this.updateOrderInfoDetailByOrderNo(loanOrderInfoDetail);
		if(ret == 0){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u4fdd\u5b58\u501f\u6b3e\u4fe1\u606f\u5931\u8d25\uff01"); // 保存借款信息失败！
		}
		result.clear();
		ret = 0;
		
		//保存关系人信息
		RelevantPersonOrder vo=new RelevantPersonOrder();
		vo.setOrderNo(orderNo);
		relevantPersonOrderMapper.delete(vo);
		if(null!=relevantPersonList&&relevantPersonList.size()>0){
			int num=relevantPersonOrderMapper.insertList(relevantPersonList);
			if(num!=relevantPersonList.size()){
				return ret;
			}
		}
		
		// 保存借款人信息
		loanOrderBorrowerExtendWithBLOBs.setOrderNo(orderNo);
		ret = this.updateOrderBorrowerExtendBLOBsByOrderNo(loanOrderBorrowerExtendWithBLOBs);
		/**
		 * 不同步借款人到碰碰旺
		 
		if(ret!=0){
			try {
				orderInfoService.updateBasicInfo(loanOrderInfoDetail, loanOrderBorrowerExtendWithBLOBs);
			} catch (Exception e) {
				logger.error("Exception:"+e);
			}
		}
		*/
		return ret;
	}

	/**
     * insertFundTaskByOrderNo:进行资金审批时将会签记录保存于会签记录表
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
	public int insertFundTaskWithEntity(LoanFundTaskWithBLOBs entity) {
		return loanFundTaskMapper.insertFundTaskWithEntity(entity);
	}
	
	/**
     * 通过 orderNo 更新审批流程
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskByOrderNo(LoanExTaskWithBLOBs record,
									 Integer letNode, 
									 Integer approvalNodeStatus,
									 Integer flowLetNode, 
									 Long fundSide, 
									 String productId, 
									 String productTypeNo,
									 Integer actualTerm,
									 String isPayPlanTemp,
									 String isLoanRecordTemp,
									 String isPayPlanCompanyTemp,
									 String actualTermUnit,
									 String actualLoanDate,
									 Integer fundFlowNode,
									 Integer customerManager,
									 String loanMethod,
									 String historyLoanMethod){
		
		int ret = 0;
		//下户时如果二级产品改变，复制相应的资质信息
		/*if(!StrUtils.isNullOrEmpty(productId)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderNo", record.getLetDeclarationformid());
			param.put("productId", productId);
			List<LoanOrderCredentials> lists = loanOrderCredentialsMapper.findOrderCredentialsInfoLists(param);
			if(lists == null || lists.size() == 0){
				credentialsInfoService.copyProductCredentialsToOrderCredentials(record.getLetDeclarationformid(), productId);
			}
		}*/
		
		if(approvalNodeStatus <= AuditingNodeType.ZXFK.getIndex()){
			Integer letResult = record.getLetResult();
			switch(letResult){
				// 处理结果:同意
				case 0:
					ret = this.updateExTaskHandleTYByOrderNo(record, 
															 letNode, 
															 fundSide, 
															 productId, 
															 productTypeNo,
															 actualTerm,
															 isPayPlanTemp,
															 isLoanRecordTemp,
															 isPayPlanCompanyTemp,
															 actualTermUnit,
															 actualLoanDate,
															 customerManager,
															 loanMethod,
															 historyLoanMethod);
					break;
				// 处理结果:拒贷
				case 1:
					ret = this.updateExTaskHandleJDByOrderNo(record, letNode);
					break;
				// 处理结果:补件	
				case 2:
					ret = this.updateExTaskHandleBJByOrderNo(record, 
															 letNode, 
															 flowLetNode, 
															 fundSide, 
															 productId, 
															 productTypeNo,
															 actualTerm,
															 actualTermUnit,
															 actualLoanDate,
															 customerManager);
					break;
				// 处理结果:撤单
				case 3:
					ret = this.updateExTaskHandleCDByOrderNo(record,
															 letNode);
					break;
				case 5:
				// 处理结果:会签
					ret = this.updateExTaskHandleHQByOrderNo(record,
															 letNode,
															 approvalNodeStatus,
															 flowLetNode,
															 fundFlowNode);
					break;
				// 资金审批处理结果:同意
				case 6:
					ret = this.updateExTaskHandleTYByOrderNo(record, 
															 letNode, 
															 fundSide, 
															 productId, 
															 productTypeNo,
															 actualTerm,
															 isPayPlanTemp,
															 isLoanRecordTemp,
															 isPayPlanCompanyTemp,
															 actualTermUnit,
															 actualLoanDate,
															 customerManager,
															 loanMethod,
															 historyLoanMethod);
					break;
				// 资金审批处理结果:拒贷
				case 7:
					ret = this.updateExTaskHandleJDByOrderNo(record, letNode);
					break;
				// 资金审批处理结果:放款终止
				case 8:
					ret = this.updateExTaskHandleFKZZByOrderNo(record, letNode);
					break;
			}
		}else if(approvalNodeStatus >= AuditingNodeStatusType.CPBJ.getIndex()){
			switch(approvalNodeStatus){
			case 13:
				ret = this.updateExTaskHandleBJExtendByOrderNo(record, approvalNodeStatus);
				break;
			case 14:
				ret = this.updateExTaskHandleBJExtendByOrderNo(record, approvalNodeStatus);
				break;
			case 15:
				ret = this.updateExTaskHandleBJExtendByOrderNo(record, approvalNodeStatus);
			}
		}
		return ret;
	}
	
	/**
     * updateExTaskHandleHQTYByOrderNo:资金审批处理结果:同意
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    public int updateExTaskHandleHQTYByOrderNo(LoanExTaskWithBLOBs record, 
									    Integer letNode, 
									    Integer approvalNodeStatus){
    	LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
    	return 0;
    }
	
	 /**
     * 处理审核节点鸡类(审批结果:同意)
     * @param letNode
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
    public int updateExTaskHandleTYCommonByOrderNo(LoanExTaskWithBLOBs record, 
												   Integer letNode, 
												   Long fundSide, 
												   String productId, 
												   String productTypeNo,
												   Integer actualTerm,
												   String isPayPlanTemp,
												   String isLoanRecordTemp,
												   String isPayPlanCompanyTemp,
												   String actualTermUnit,
												   String actualLoanDate,
												   Integer customerManager,
												   String loanMethod,
												   String historyLoanMethod){
    	Map<String, Object> param = new HashMap<String, Object>();
    	Map<String, Object> result = new HashMap<String, Object>();
    	int ret = 0;
    	LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		LoanOrderInfoDetail loanOrderInfoDetail = new LoanOrderInfoDetail();
		LoanFundTaskWithBLOBs loanFundTaskWithBLOBs = new LoanFundTaskWithBLOBs();
		BigDecimal bigDecimal = new BigDecimal(0.0);
		
		loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
		loanOrderInfo.setFundFlowNode(0); // 会签结束，如果会签人同意提交，将会签人的id置为默认值:0
		loanOrderInfoDetail.setOrderNo(record.getLetDeclarationformid());
		Date actualLoanDateTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 如果当前节点不是执行放款，那么同意一步，节点往下游移动一步
		if(AuditingNodeType.ZXFK.getIndex() != letNode){
			if(letNode == AuditingNodeType.FKQR.getIndex()){
				
				SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();
				LoanDept loanDept = this.selectLoanDeptByLdDeptId(Integer.parseInt(loanEmp.getDeptId()));
				if("结算部".equals(loanDept.getLdName())){
					loanOrderInfo.setOrderStatus(letNode);
					loanOrderInfo.setApprovalNode(letNode);
					loanOrderInfo.setApprovalNodeStatus(letNode);
				}else if("权证部".equals(loanDept.getLdName())){
					loanOrderInfo.setOrderStatus(letNode + 1);
					loanOrderInfo.setApprovalNode(letNode + 1);
					loanOrderInfo.setApprovalNodeStatus(letNode + 1);
					loanOrderInfo.setAuditStatus(0);
				}
			}else{
				loanOrderInfo.setOrderStatus(letNode + 1);
				loanOrderInfo.setApprovalNode(letNode + 1);
				loanOrderInfo.setApprovalNodeStatus(letNode + 1);
			}
		}else{
			loanOrderInfo.setLoanMethod(loanMethod);
			if(historyLoanMethod.equals("0")){//未进行过放款
				if(loanMethod.equals("2")){//全部放款
					loanOrderInfo.setOrderStatus(OrderStatusType.FKCG.getIndex());
					loanOrderInfo.setApprovalNode(AuditingNodeType.FKCG.getIndex());
					loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.FKCG.getIndex());
				}else{//部分放款
					loanOrderInfo.setOrderStatus(OrderStatusType.FKCG.getIndex());
					loanOrderInfo.setApprovalNode(AuditingNodeType.ZXFK.getIndex());
					loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.ZXFK.getIndex());
				}
			}else if(historyLoanMethod.equals("1")){
				if(loanMethod.equals("2")){//全部放款
					loanOrderInfo.setApprovalNode(AuditingNodeType.FKCG.getIndex());
					loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.FKCG.getIndex());
				}
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "该报单已经全部放款,不可再次放款");
				throw new RuntimeException("该报单已经全部放款,不可再次放款...");
			}
			
			// 调用放款服务
			isPayPlanTemp = StrUtils.isNullOrEmpty(isPayPlanTemp)?SystemConst.IsFlag.NO:SystemConst.IsFlag.YES;
			isLoanRecordTemp = StrUtils.isNullOrEmpty(isLoanRecordTemp)?SystemConst.IsFlag.NO:SystemConst.IsFlag.YES;
			isPayPlanCompanyTemp = StrUtils.isNullOrEmpty(isPayPlanCompanyTemp)?SystemConst.IsFlag.NO:SystemConst.IsFlag.YES;
			LoanTransMain loanTransMain = this.getLoanTransMain(record.getLetDeclarationformid(), actualLoanDate, 
					isPayPlanTemp, isLoanRecordTemp, isPayPlanCompanyTemp, historyLoanMethod);
			String recode = accountTransactionService.loanTrans(loanTransMain);
			if(!recode.equals(SystemConst.SUCCESS)){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u653e\u6b3e\u5931\u8d25"); // 放款失败
				throw new RuntimeException("放款失败...");
			}
			if(recode.equals(SystemConst.SUCCESS)){
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "\u653e\u6b3e\u6210\u529f"); // 放款成功
			}
		}
		
		// 如果当前节点是下户节点，那么对资金方和产品名称做保存操作
		if(AuditingNodeType.XH.getIndex() == letNode){
			if(null != productTypeNo && null != productId && null != fundSide){
				loanOrderInfo.setProductNo(productTypeNo);
				loanOrderInfo.setChildProductNo(productId);
				if(null != customerManager){
					loanOrderInfo.setCustomerManager(customerManager);
				}
				loanOrderInfoDetail.setFundOwner(fundSide);
				
				// 下户同意之后将组织机构表中的账户和银行卡信息等同步到放款申请表中(暂时停用)
				/*LoanOrganization loanOrganization = loanOrganizationMapper.selectByPrimaryKey(fundSide);
				String bankCardId = "";
				if(null == loanOrganization){
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "\u8d44\u91d1\u65b9\u6240\u652f\u6301\u7684\u8d26\u6237\u4fe1\u606f\u4e0d\u5b58\u5728\u002e\u002e\u002e"); // 资金方所支持的账户信息不存在...
				}else{
					bankCardId = loanOrganization.getOrgAccountNo();
					if(StrUtils.isNullOrEmpty(bankCardId)){
						result.put(SystemConst.retCode, SystemConst.FAIL);
						result.put(SystemConst.retMsg, "\u5bf9\u516c\u94f6\u884c\u5361\u8d26\u53f7\u4e0d\u5b58\u5728\uff0c\u8bf7\u6838\u5bf9\u002e\u002e\u002e"); // 对公银行卡账号不存在，请核对...
					}else{
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderNo", record.getLetDeclarationformid());
						LoanCapitalInfo loanCapitalInfo = this.selectLoanCapitalInfoByBankCardIdAndOrderno(map);
						if(null == loanCapitalInfo){
							LoanCapitalInfo loanCapital = new LoanCapitalInfo();
							String capitalInfoId = UUID.randomUUID().toString().replace("-", "");
							loanCapital.setCapitalInfoId(capitalInfoId);
							loanCapital.setAccountName(loanOrganization.getOrgAccountName());
							loanCapital.setBankcardId(bankCardId);
							loanCapital.setBankName(loanOrganization.getOrgAccountBank());
							loanCapital.setBranchBank(loanOrganization.getOrgAccountBanchBank());
							loanCapital.setOrderNo(record.getLetDeclarationformid());
							ret = loanCapitalInfoMapper.insertSelective(loanCapital);
							if(ret != 0){
								result.put(SystemConst.retCode, SystemConst.SUCCESS);
								result.put(SystemConst.retMsg, "\u66f4\u65b0\u653e\u6b3e\u7533\u8bf7\u6210\u529f\uff01"); //更新放款申请成功！
							}else{
								result.put(SystemConst.retCode, SystemConst.FAIL);
								result.put(SystemConst.retMsg, "\u66f4\u65b0\u653e\u6b3e\u7533\u8bf7\u5931\u8d25\uff01"); //更新放款申请失败！
							}
							ret = 0;
						}
					}
				}*/
				
				ret = loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(loanOrderInfoDetail);
				ret = 0;
			}
		}else if(AuditingNodeType.FKSQ.getIndex() == letNode){
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(AuditingNodeType.FKSQ.getIndex());
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			loanFundTaskWithBLOBs.setLftResult(0);
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		}else if(AuditingNodeType.FKSH.getIndex() == letNode){
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(AuditingNodeType.FKSH.getIndex());
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			loanFundTaskWithBLOBs.setLftResult(record.getLetResult());
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		}else if(AuditingNodeType.FKQR.getIndex() == letNode){
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(AuditingNodeType.FKQR.getIndex());
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			loanFundTaskWithBLOBs.setLftResult(record.getLetResult());
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		}else if(AuditingNodeType.ZXFK.getIndex() == letNode){ // 如果当前节点是执行放款，保存放款时间和放款期限
			param.put("orderNo", record.getLetDeclarationformid());
			List<LoanCapitalInfo> loanCapitalInfo = this.selectLoanCapitalInfo(param);
			int i=0;
			if(null != loanCapitalInfo && loanCapitalInfo.size() > 0){
				for (LoanCapitalInfo loanCapital : loanCapitalInfo) {
					loanCapital.setAmountPaidAlready(loanCapital.getAmountPaidThis().add(loanCapital.getAmountPaidAlready()));
					bigDecimal = bigDecimal.add(loanCapital.getAmountPaidAlready());
					i+=loanCapitalInfoMapper.updateByPrimaryKeySelective(loanCapital);
				}
				loanOrderInfoDetail.setActualAmount(bigDecimal);
			}
			
			if(null != actualLoanDate){
				try {
					actualLoanDateTime = sdf.parse(actualLoanDate);
					loanOrderInfoDetail.setActualLoanDate(actualLoanDateTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			ret = loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(loanOrderInfoDetail);
			ret = 0;
//			if(null != actualTerm && null != actualTermUnit){
//				loanOrderInfoDetail.setActualTerm(actualTerm);
//				loanOrderInfoDetail.setActualTermUnit(actualTermUnit);
//			}
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(AuditingNodeType.ZXFK.getIndex());
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			loanFundTaskWithBLOBs.setLftResult(0);
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		}
		
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		if(AuditingNodeType.ZXFK.getIndex() != letNode){
			record.setLetNode(letNode + 1);
			record.setLetNodeStatus(letNode);
		}else{
			record.setLetNode(AuditingNodeType.FKCG.getIndex());
			record.setLetNodeStatus(letNode);
		}
		ret = loanExTaskMapper.insertExTaskByOrderNo(record);
    	return ret;
    }
	
	/**
     * 处理结果:同意
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleTYByOrderNo(LoanExTaskWithBLOBs record, 
											 Integer letNode, 
											 Long fundSide, 
											 String productId, 
											 String productTypeNo,
											 Integer actualTerm,
											 String isPayPlanTemp,
											 String isLoanRecordTemp,
											 String isPayPlanCompanyTemp,
											 String actualTermUnit,
											 String actualLoanDate,
											 Integer customerManager,
											 String loanMethod,
											 String historyLoanMethod) {
		int ret = 0;
		if(AuditingNodeType.CP.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.XH.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.MS.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.ZS.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.FKSQ.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
	    }else if(AuditingNodeType.FKSH.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
	    }else if(AuditingNodeType.QYGZ.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.QZDY.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.FKQR.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}else if(AuditingNodeType.ZXFK.getIndex() == letNode){
			ret = this.updateExTaskHandleTYCommonByOrderNo(record, 
														   letNode, 
														   fundSide, 
														   productId, 
														   productTypeNo,
														   actualTerm,
														   isPayPlanTemp,
														   isLoanRecordTemp,
														   isPayPlanCompanyTemp,
														   actualTermUnit,
														   actualLoanDate,
														   customerManager,
														   loanMethod,
														   historyLoanMethod);
		}
		return ret;
	}

    /**
     * 处理结果:拒贷
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleJDByOrderNo(LoanExTaskWithBLOBs record, Integer letNode) {
		Map<String, Object> result = new HashMap<String, Object>();
		LoanFundTaskWithBLOBs loanFundTaskWithBLOBs = new LoanFundTaskWithBLOBs();
		int ret = 0;
		LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
		loanOrderInfo.setOrderStatus(OrderStatusType.YJD.getIndex());
		loanOrderInfo.setApprovalNode(AuditingNodeType.YJD.getIndex());
		loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.YJD.getIndex());
		loanOrderInfo.setFundFlowNode(0); // 会签结束，如果会签人拒贷提交，将会签人的id置为默认值:0
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		record.setLetNode(letNode);
		record.setLetNodeStatus(letNode);
		//record.setLetResult(AuditingHandledType.XMSPJD.getIndex());
		ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		
		if(AuditingNodeType.ZXFK.getIndex() == letNode || AuditingNodeType.FKSH.getIndex() == letNode){
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(letNode);
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			//loanFundTaskWithBLOBs.setLftResult(AuditingHandledType.ZJSPJD.getIndex());
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
			loanOrderInfo.setFundFlowNode(0); // 会签结束，如果会签人拒贷提交，将会签人的id置为默认值:0
			ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		}
		
		return ret;
	}
	
	
	/**
     * 处理结果:放款终止
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleFKZZByOrderNo(LoanExTaskWithBLOBs record, Integer letNode) {
		LoanFundTaskWithBLOBs loanFundTaskWithBLOBs = new LoanFundTaskWithBLOBs();
		int ret = 0;
		LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
		loanOrderInfo.setApprovalNode(AuditingNodeType.FKCG.getIndex());
		loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.FKCG.getIndex());
		loanOrderInfo.setFundFlowNode(0); // 会签结束，如果会签人拒贷提交，将会签人的id置为默认值:0
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		record.setLetNode(letNode);
		record.setLetNodeStatus(letNode);
		//record.setLetResult(AuditingHandledType.FKZZ.getIndex());
		ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		
		loanFundTaskWithBLOBs.setLftNode(letNode);
		loanFundTaskWithBLOBs.setLftNodeStatus(letNode);
		loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
		loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
		loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
		//loanFundTaskWithBLOBs.setLftResult(AuditingHandledType.FKZZ.getIndex());
		loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
		loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
		loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
		ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		
		return ret;
	}
	
	
	

	/**
     * 处理结果:补件鸡类 
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleBJCommonByOrderNo(LoanExTaskWithBLOBs record, 
												   Integer letNode, 
												   Integer flowLetNode, 
												   Long fundSide, 
												   String productId, 
												   String productTypeNo,
												   Integer actualTerm,
												   String actualTermUnit,
												   String actualLoanDate,
												   Integer customerManager){
		
		int ret = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		LoanOrderInfoDetail loanOrderInfoDetail = new LoanOrderInfoDetail();
		
		loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
		loanOrderInfoDetail.setOrderNo(record.getLetDeclarationformid());
		Date actualLoanDateTime = null;
		
		// 实际放款期限(财务放款节点)
		if(letNode == AuditingNodeType.ZXFK.getIndex()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				actualLoanDateTime = sdf.parse(actualLoanDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		loanOrderInfo.setOrderStatus(letNode);
		loanOrderInfo.setApprovalNode(flowLetNode);
		if(AuditingNodeType.CP.getIndex() == flowLetNode){
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.CPBJ.getIndex());
		}else if(AuditingNodeType.XH.getIndex() == flowLetNode){
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.XHBJ.getIndex());
		}else if(AuditingNodeType.ZS.getIndex() == flowLetNode){
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.ZSBJ.getIndex());
		}
		
		if(AuditingNodeType.XH.getIndex() == letNode){
			if(StrUtils.isNullOrEmpty(productTypeNo)){
				productTypeNo = "";
			}
			loanOrderInfo.setProductNo(productTypeNo);
			loanOrderInfo.setChildProductNo(productId);
			if(null != customerManager){
				loanOrderInfo.setCustomerManager(customerManager);
			}
			loanOrderInfoDetail.setFundOwner(fundSide);
			
			ret = loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(loanOrderInfoDetail);
			ret = 0;
		}else if(AuditingNodeType.ZXFK.getIndex() == letNode){
			loanOrderInfoDetail.setLoanTerm(actualTerm);
			loanOrderInfoDetail.setLoanTermUnit(actualTermUnit);
			loanOrderInfoDetail.setActualLoanDate(actualLoanDateTime);
				
			ret = loanOrderInfoDetailMapper.updateOrderInfoDetailByOrderNo(loanOrderInfoDetail);
			ret = 0;
		}
		
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		record.setLetNode(flowLetNode);
		record.setLetNodeStatus(letNode);
		ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		return ret;
	}
    /**
     * 处理结果:补件
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleBJByOrderNo(LoanExTaskWithBLOBs record, 
											 Integer letNode, 
											 Integer flowLetNode, 
											 Long fundSide, 
											 String productId, 
											 String productTypeNo,
											 Integer actualTerm,
											 String actualTermUnit,
											 String actualLoanDate,
											 Integer customerManager){
		int ret = 0;
		if(AuditingNodeType.CP.getIndex() == flowLetNode){
			ret = this.updateExTaskHandleBJCommonByOrderNo(record, 
														   letNode, 
														   flowLetNode, 
														   fundSide, 
														   productId, 
														   productTypeNo, 
														   actualTerm, 
														   actualTermUnit, 
														   actualLoanDate,
														   customerManager);
		}else if(AuditingNodeType.XH.getIndex() == flowLetNode){
			ret = this.updateExTaskHandleBJCommonByOrderNo(record, 
														   letNode, 
														   flowLetNode, 
														   fundSide, 
														   productId, 
														   productTypeNo, 
														   actualTerm, 
														   actualTermUnit, 
														   actualLoanDate,
														   customerManager);
		}else if(AuditingNodeType.MS.getIndex() == flowLetNode){
			ret = this.updateExTaskHandleBJCommonByOrderNo(record, 
														   letNode, 
														   flowLetNode, 
														   fundSide, 
														   productId, 
														   productTypeNo, 
														   actualTerm, 
														   actualTermUnit, 
														   actualLoanDate,
														   customerManager);
		}else if(AuditingNodeType.ZS.getIndex() == flowLetNode){
			ret = this.updateExTaskHandleBJCommonByOrderNo(record, 
														   letNode, 
														   flowLetNode, 
														   fundSide, 
														   productId, 
														   productTypeNo, 
														   actualTerm, 
														   actualTermUnit, 
														   actualLoanDate,
														   customerManager);
		}
		return ret;
	}

    /**
     * 处理结果:撤单
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleCDByOrderNo(LoanExTaskWithBLOBs record, Integer letNode) {
		Map<String, Object> result = new HashMap<String, Object>();
		int ret = 0;
		LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
		loanOrderInfo.setOrderStatus(OrderStatusType.YCD.getIndex());
		loanOrderInfo.setApprovalNode(AuditingNodeType.YCD.getIndex());
		loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.YCD.getIndex());
		loanOrderInfo.setFundFlowNode(0); // 会签结束，如果会签人撤单，将会签人的id置为默认值:0
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		record.setLetNode(AuditingNodeType.YCD.getIndex());
		record.setLetNodeStatus(letNode);
		record.setLetResult(AuditingHandledType.CD.getIndex());
		ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		return ret;
	}
	
	/**
	 * updateExTaskHandleHQByOrderNo:处理结果:会签
	 * @author kernel.org
	 * @param record
	 * @param letNode
	 * @return
	 * @since JDK 1.8
	 */
	public int updateExTaskHandleHQByOrderNo(LoanExTaskWithBLOBs record,
											 Integer letNode,
											 Integer approvalNodeStatus,
											 Integer flowLetNode,
											 Integer fundFlowNode){
		Map<String, Object> result = new HashMap<String, Object>();
		int ret = 0;
		LoanOrderInfo loanOrderInfo = new LoanOrderInfo();
		LoanFundTaskWithBLOBs loanFundTaskWithBLOBs = new LoanFundTaskWithBLOBs();
		// 如果当前节点是面审节点，保存会签信息到项目审批列表
		if(letNode == AuditingNodeType.MS.getIndex()){
			loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
			loanOrderInfo.setOrderStatus(OrderStatusType.MS.getIndex());
			loanOrderInfo.setApprovalNode(AuditingNodeType.MS.getIndex());
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.MS.getIndex());
			loanOrderInfo.setFundFlowNode(fundFlowNode);
			ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
			ret = 0;
			
			record.setLetNode(AuditingNodeType.MS.getIndex());
			record.setLetNodeStatus(letNode);
			record.setLetResult(AuditingHandledType.HQ.getIndex());
			ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		// 如果当前节点是终审，保存会签信息到资金审批列表
		}if(letNode == AuditingNodeType.ZS.getIndex()){
			loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
			loanOrderInfo.setOrderStatus(OrderStatusType.ZS.getIndex());
			loanOrderInfo.setApprovalNode(AuditingNodeType.ZS.getIndex());
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.ZS.getIndex());
			loanOrderInfo.setFundFlowNode(fundFlowNode);
			ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
			ret = 0;
			
			record.setLetNode(AuditingNodeType.ZS.getIndex());
			record.setLetNodeStatus(letNode);
			record.setLetResult(AuditingHandledType.HQ.getIndex());
			ret = loanExTaskMapper.insertExTaskByOrderNo(record);
		// 如果当前节点是放款审核，保存会签信息到资金审批列表
		}else if(letNode == AuditingNodeType.FKSH.getIndex()){
			loanOrderInfo.setOrderNo(record.getLetDeclarationformid());
			loanOrderInfo.setOrderStatus(OrderStatusType.FKSH.getIndex());
			loanOrderInfo.setApprovalNode(AuditingNodeType.FKSH.getIndex());
			loanOrderInfo.setApprovalNodeStatus(AuditingNodeStatusType.FKSH.getIndex());
			loanOrderInfo.setFundFlowNode(fundFlowNode);
			ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
			ret = 0;
			
			loanFundTaskWithBLOBs.setLftNode(letNode);
			loanFundTaskWithBLOBs.setLftNodeStatus(approvalNodeStatus);
			loanFundTaskWithBLOBs.setLftEmployeename(record.getLetEmployeename());
			
			loanFundTaskWithBLOBs.setLftTime(record.getLetTime());
			loanFundTaskWithBLOBs.setLftEmployeeno(record.getLetEmployeeno());
			loanFundTaskWithBLOBs.setLftResult(record.getLetResult());
			
			loanFundTaskWithBLOBs.setLftSuggestion(record.getLetSuggestion());
			loanFundTaskWithBLOBs.setLftRemark(record.getLetRemark());
			loanFundTaskWithBLOBs.setLftDeclarationformid(record.getLetDeclarationformid());
			ret = this.insertFundTaskWithEntity(loanFundTaskWithBLOBs);
		}
		return ret;
	}
	/**
     * 处理结果:(初评、下户、终审)补件鸡类
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleBJExtendCommonByOrderNo(LoanExTaskWithBLOBs record, Integer approvalNodeStatus) {
		int ret = 0;
		LoanOrderInfo loanOrderInfo = this.selectByPrimaryKey(record.getLetDeclarationformid());
		Integer orderStatus = loanOrderInfo.getOrderStatus();
		
		loanOrderInfo.setApprovalNode(orderStatus);
		loanOrderInfo.setApprovalNodeStatus(orderStatus);
		ret = loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
		ret = 0;
		
		record.setLetNode(orderStatus);
		record.setLetNodeStatus(approvalNodeStatus);
		record.setLetResult(AuditingHandledResultType.BJWC.getIndex());
		ret = loanExTaskMapper.insertSelective(record);
		return ret;
	}
	
	/**
     * 处理结果:(初评、下户、终审)补件
     * @param record
     * @return
     */
	@Transactional(rollbackFor = Exception.class)
	public int updateExTaskHandleBJExtendByOrderNo(LoanExTaskWithBLOBs record, Integer approvalNodeStatus){
		int ret = 0;
		if(AuditingNodeStatusType.CPBJ.getIndex() == approvalNodeStatus){
			ret = this.updateExTaskHandleBJExtendCommonByOrderNo(record, approvalNodeStatus);
		}else if(AuditingNodeStatusType.XHBJ.getIndex() == approvalNodeStatus){
			ret = this.updateExTaskHandleBJExtendCommonByOrderNo(record, approvalNodeStatus);
		}else if(AuditingNodeStatusType.ZSBJ.getIndex() == approvalNodeStatus){
			ret = this.updateExTaskHandleBJExtendCommonByOrderNo(record, approvalNodeStatus);
		}
		return ret;
	}
	
	/**
	 * 通过orderNo查询LoanOrderInfo对象
	 * @param orderNo
	 * @return
	 */
	public LoanOrderInfo selectByPrimaryKey(String orderNo) {
		return loanOrderInfoMapper.selectByPrimaryKey(orderNo);
	}

	 /**
     * 通过 orderNo insert审批流程
     * @param record
     * @return
     */
	public int insertExTaskByOrderNo(LoanExTaskWithBLOBs record) {
		return loanExTaskMapper.insert(record);
	}
	
	/**
     * selectLoanCapitalInfoByBankCardId:根据银行卡号查询放款申请表中的实体bean
     * @author kernel.org
     * @param bankcardId
     * @return
     * @since JDK 1.8
     */
    public LoanCapitalInfo selectLoanCapitalInfoByBankCardIdAndOrderno(Map<String, Object> map){
    	return loanCapitalInfoMapper.selectLoanCapitalInfoByBankCardIdAndOrderno(map);
    }
    
    /**
     * updateByBankCardIdAndOrderno:根据银行卡号和订单号更新放款申请表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    public int updateByBankCardIdAndOrderno(LoanCapitalInfo record){
    	return loanCapitalInfoMapper.updateByBankCardIdAndOrderno(record);
    }
    
    /**
     * selectLoanApplyList:财务管理放款申请列表显示
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    /*public List<LoanOrderInfoVo> selectLoanApplyList(LoanOrderInfoVo entity){
    	return null;
    }*/
    
    @Override
	public LoanOrderInfoVo get(String id) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanOrderInfoVo get(LoanOrderInfoVo entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanOrderInfoVo entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(LoanOrderInfoVo entity) {
		Page page = entity.getPage();
		List<LoanOrderInfoVo> loanApplyList = loanOrderInfoMapper.selectLoanApplyList(entity);
		int count = loanOrderInfoMapper.selectLoanApplyListCount(entity);
		page.setResultList(loanApplyList);
		page.setTotalCount(count);
		return page;
	}

	@Override
	public int save(LoanOrderInfoVo entity) {
		
		return 0;
	}

	@Override
	public int update(LoanOrderInfoVo entity) {
		
		return 0;
	}

	@Override
	public int delete(LoanOrderInfoVo entity) {
		
		return 0;
	}

	@Override
	public int queryCount(LoanOrderInfoVo entity) {
		
		return 0;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Map<String, Object> addUpdateLoanApply(String loanCapitalEarning,String loanCapitalInfo,
			String loanCapitalExpenditure,String orderNo,String parasConfig) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		int loanCapEarnCount = 0, loanCapInfoCount = 0, loanCapExpendCount = 0;
		//int loanCapCount=0;int loanCount=0;int loanCapExCount=0;
		//String loanEarnId="";String loanInfoId="";String loanExpendId="";
		
		LoanParasConfig loanParasConfig = JSON.parseObject(parasConfig, LoanParasConfig.class);
		loanParasConfig.setOrderNo(orderNo);
		if(StringUtils.isNotBlank(loanParasConfig.getLpcId())){
			loanParasConfigMapper.updateByPrimaryKey(loanParasConfig);
		}else {
			loanParasConfig.setLpcId(UUID.randomUUID().toString().replace("-", ""));
			loanParasConfigMapper.insertSelective(loanParasConfig);
		}
		
		//获取机构id
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", orderNo);
        Map<String, Object> basicInfo = selectBasicInfo(params);
		String orgId = basicInfo.get("fund_owner") == null ? null : "" + basicInfo.get("fund_owner");
		String orgName = basicInfo.get("org_name") == null ? null : "" + basicInfo.get("org_name");
		
		
		List<LoanCapitalEarning> loanCapEarn=JSON.parseArray(loanCapitalEarning, LoanCapitalEarning.class);
		List<LoanCapitalInfo> loanCapInfo=JSON.parseArray(loanCapitalInfo, LoanCapitalInfo.class);
		List<LoanCapitalExpenditure> loanCapExpend=JSON.parseArray(loanCapitalExpenditure, LoanCapitalExpenditure.class);
		loanCapitalEarningMapper.delByOrderNo(orderNo);
		loanCapitalInfoMapper.delByOrderNo(orderNo);
		loanCapitalExpenditureMapper.delByOrderNo(orderNo);
		for (LoanCapitalEarning loanEarn : loanCapEarn) {
			if (StringUtils.isNotBlank(loanEarn.getEarningVariety())) {
				loanEarn.setCapitalEarningId(UUID.randomUUID().toString().replace("-", ""));
				loanEarn.setOrgId(orgId);
				loanEarn.setOrderId(orderNo);
				loanCapEarnCount += loanCapitalEarningMapper.insertSelective(loanEarn);
			} else {
				loanCapEarnCount++;
			}
		}
		for (LoanCapitalInfo loanInfo : loanCapInfo) {
			if(StringUtils.isNotBlank(loanInfo.getCustomerNature())) {
				loanInfo.setCapitalInfoId(UUID.randomUUID().toString().replace("-", ""));
				loanInfo.setOrderNo(orderNo);
				loanInfo.setOrgId(orgId);
				loanCapInfoCount += loanCapitalInfoMapper.insertSelective(loanInfo);
			}else {
				loanCapInfoCount++;
			}
		}
		for (LoanCapitalExpenditure loanExpend : loanCapExpend) {
			if (StringUtils.isNotBlank(loanExpend.getExpenditureVariety())) {
				loanExpend.setCapitalExpenditureId(UUID.randomUUID().toString().replace("-", ""));
				loanExpend.setOrderId(orderNo);
				loanExpend.setOrgId(orgId);
				loanCapExpendCount += loanCapitalExpenditureMapper.insertSelective(loanExpend);
			} else {
				loanCapExpendCount++;
			}
		}
            //异步推送借款申请表信息
		try {
//			providerTransportService.updateLoanApply(loanCapEarn, loanCapInfo, loanCapExpend, orderNo);
		} catch (Exception e) {
			logger.error("Exception:" + e);
		}
				
		if (loanCapEarnCount==loanCapEarn.size() && loanCapInfoCount==loanCapInfo.size() && loanCapExpendCount ==loanCapExpend.size()) {
			   resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			   resultMap.put(SystemConst.retMsg, "\u66f4\u65b0\u6210\u529f");
		}else{
			  throw new RuntimeException();
		}
		return resultMap;
	}

	public List<LoanOrganization> selectLoanOrganizationList() {
		return loanOrganizationMapper.selectLoanOrganizationList();
	}

	public List<LoanChildProduct> selectChildProductByOrgId(Long orgId, String orderNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		LoanOrderInfo orderInfo =loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		params.put("orgId", orgId);
		params.put("city", orderInfo==null?"":orderInfo.getCity());
		List<LoanChildProduct> loanChildProduct = loanChildProductMapper.selectChildProductByOrgId(params);
		return loanChildProduct;
	}
	
	public List<LoanChildProduct> selectChildProductByFundOwner(Map<String, Object> params) {
		List<LoanChildProduct> loanChildProduct = loanChildProductMapper.selectChildProductByOrgId(params);
		return loanChildProduct;
	}

	@Override
	public boolean isUploadRequiredCredentials(String orderNo, String productId) {
		Integer count = loanOrderCredentialsMapper.findOrderRequiredCredentialsInfoNoUploadCount(orderNo,productId);
		if(count != null && count.intValue() > 0){
			return false;
		}
		return true;
	}

	 /**
     * 通过借款人的 custId 更新借款信息表
     * @param record
     * @return
     */
	public int insertBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs record) {
		int ret = loanBorrowerInfoMapper.insertBorrowerInfoWithBLOBs(record);
		return ret;
	}

	/**
     * 根据订单号查询资金方
     * @param orderNo
     * @return
     */
	public List<LoanOrgSupportAreas> selectLoanOrgSupportAreas(String orderNo) {
		return loanOrgSupportAreasMapper.selectLoanOrgSupportAreas(orderNo);
	}
	
	/**
	 * 根据Emp查询资金方
	 * @param LoanEmp
	 * @return
	 */
	public List<LoanOrgSupportAreas> selectOrgSupportAreasByEmp(LoanEmp loanEmp) {
		return loanOrgSupportAreasMapper.selectOrgSupportAreasByEmp(loanEmp);
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.ApprovalProcessService#selectIndexMyNewTaskList(java.util.Map, com.lhhs.loan.common.shared.page.Page)
	 */
	 
	@Override
	public List<Map<String, Object>> selectIndexMyNewTaskList(Map<String, Object> map, Page page) {
		List<Map<String, Object>> myNewTaskList = loanOrderInfoMapper.selectIndexMyNewTaskList(map);
		Integer count = loanOrderInfoMapper.selectIndexMyNewTaskListCount(map);
		page.setResultList(myNewTaskList);
		page.setTotalCount(count);
		return myNewTaskList;
	}

	@Override
	public LoanOrderInfoDetail selectOrderDetailByOrderNo(String orderNo) {
		return loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);
	}

	@Override
	public LoanParasConfig selectParasConfigByOrderNo(String orderNo) {
		return loanParasConfigMapper.selectByOrderNo(orderNo);
	}

	@Override
	public LoanTransMain getLoanTransMain(String orderNo,String actualLoanDate, String isPayPlanTemp,
			String isLoanRecordTemp, String isPayPlanCompanyTemp, String historyLoanMethod) {
		LoanTransMain loanTransMain=new LoanTransMain();
		LoanOrderInfoDetail loanOrderInfoDetail=loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);//订单详情
		if(loanOrderInfoDetail!=null){
			String loanRateUnit=loanOrderInfoDetail.getLoanRateUnit();
			String repayment=loanOrderInfoDetail.getRepayment();
			if(loanRateUnit.equals(SystemConst.RateUnit.TIME)&&!repayment.equals(SystemConst.RepaymentMethod.ONCE_PAYMENT)){
				loanTransMain.setRetCode(SystemConst.FAIL);
				//当借款利率单位为‘%/次’时，还款方式必须是一次性还款
				loanTransMain.setRetMsg("\u5f53\u501f\u6b3e\u5229\u7387\u5355\u4f4d\u4e3a\u2018\u0025\u002f\u6b21\u2019\u65f6\uff0c\u8fd8\u6b3e\u65b9\u5f0f\u5fc5\u987b\u662f\u4e00\u6b21\u6027\u8fd8\u6b3e");
			}
		}
		LoanOrderInfo loanOrderInfo=loanOrderInfoMapper.selectByPrimaryKey(orderNo);//订单 
		LoanParasConfig loanParasConfig=loanParasConfigMapper.selectByOrderNo(orderNo);//订单参数配置
		LoanOrganization loanOrganization;//机构
		LoanEmp loanEmp;//客户经理
		LoanDept loanDept;//部门
		LoanCustomerInfo loanCustomerInfo=loanCustomerInfoMapper.selectByCustomerId(loanOrderInfo.getCustomerId());//借款人
		LoanAccountInfo loanAccountInfo;//账户信息
		LoanParentProduct loanParentProduct=loanParentProductMapper.selectByPrimaryKey(loanOrderInfo.getProductNo());//一级产品
		LoanChildProduct loanChildProduct=loanChildProductMapper.selectByPrimaryKey(loanOrderInfo.getChildProductNo());//二级产品
		List<LoanRecordTemp> listLoanRecordTemp=loanCapitalInfoMapper.queryLoanRecordByOrderNo(orderNo);//放款
		List<LoanFeesPlan> listFeesPlan_in=null;//收入
		List<LoanFeesPlan> listFeesPlan_out=null;//支出
		if(historyLoanMethod.equals("0")){//未进行过放款
			listFeesPlan_in=loanCapitalEarningMapper.queryFeesPlanInByOrderNo(orderNo);
			listFeesPlan_out=loanCapitalExpenditureMapper.queryFeesPlanOutByOrderNo(orderNo);
		}
		if(listFeesPlan_out!=null&&listFeesPlan_out.size()>0){
			for(LoanFeesPlan loanFeesPlan:listFeesPlan_out){
				if(!StrUtils.isNullOrEmpty(loanFeesPlan.getCardNo())){
					List<LoanAccountInfo> list=loanAccountInfoMapper.selectByBankCardNo(loanFeesPlan.getCardNo());
					if(list!=null&&list.size()>0){
						LoanAccountInfo temp=list.get(0);
						loanFeesPlan.setAccountId(temp.getAccountId());
						loanFeesPlan.setCustomerId(temp.getOwnerId());
						if(!StringUtils.isEmpty(temp.getField1())){
							loanFeesPlan.setAccountName(temp.getField1());
						}else{
							loanFeesPlan.setAccountName(temp.getOwnerName());
						}
						
					}
				}
			}
		}
		loanTransMain.setBusinessId(orderNo);
		loanTransMain.setLendingTime(DateUtils.String2Date(actualLoanDate));
		loanTransMain.setLoanTime(DateUtils.String2Date(actualLoanDate));
		loanTransMain.setTransType(SystemConst.TransType.TYPEID1001);
		loanTransMain.setIsPayPlanTemp(isPayPlanTemp);
		loanTransMain.setIsLoanRecordTemp(isLoanRecordTemp);
		loanTransMain.setIsPayPlanCompanyTemp(isPayPlanCompanyTemp);
		if(loanOrderInfo!=null){
			loanTransMain.setBorrowerId(loanOrderInfo.getCustomerId());
			loanTransMain.setProvienceNo(loanOrderInfo.getProvince());
			loanTransMain.setProvienceName(loanOrderInfo.getProvince());
			loanTransMain.setCityNo(loanOrderInfo.getCity());
			loanTransMain.setCityName(loanOrderInfo.getCity());
			loanTransMain.setCompanyId(loanOrderInfo.getCompanyId());
			loanTransMain.setUnionId(loanOrderInfo.getUnionId());
			if(loanOrderInfo.getCustomerManager()!=null){
				loanTransMain.setField4(loanOrderInfo.getCustomerManager().toString());
				loanEmp=loanEmpMapper.selectByPrimaryKey(loanOrderInfo.getCustomerManager());
				if(loanEmp!=null){
					loanTransMain.setAccountManager(loanEmp.getLeStaffName());
					if(loanEmp.getLeDeptId()!=null){
						loanDept=loanDeptMapper.selectByPrimaryKey(loanEmp.getLeDeptId());
						if(loanDept!=null){
							loanTransMain.setField1(loanDept.getLdDeptId().toString());
							loanTransMain.setDepartment(loanDept.getLdName());
						}
					}
				}
			}
		}
		if(loanOrderInfoDetail!=null){
			loanTransMain.setAmount(loanOrderInfoDetail.getLoanAmount().multiply(new BigDecimal(10000)));
			loanTransMain.setTerm(loanOrderInfoDetail.getLoanTerm().shortValue());
			loanTransMain.setTermUnit(loanOrderInfoDetail.getLoanTermUnit());
			loanTransMain.setRate(loanOrderInfoDetail.getLoanRate());
			loanTransMain.setRateUnit(loanOrderInfoDetail.getLoanRateUnit());
			loanTransMain.setRepaymentMethod(loanOrderInfoDetail.getRepayment());
			loanTransMain.setOrgId(loanOrderInfoDetail.getFundOwner().toString());
			loanOrganization=loanOrganizationMapper.selectByPrimaryKey(loanOrderInfoDetail.getFundOwner());
			if(loanOrganization!=null){
				loanTransMain.setOrgName(loanOrganization.getOrgName());
			}
		}
		if(loanParentProduct!=null){
			loanTransMain.setProductType(loanParentProduct.getProductType());
			loanTransMain.setProductNo(loanParentProduct.getProductNo());
		}
		if(loanChildProduct!=null){
			loanTransMain.setProductName(loanChildProduct.getProductName());
			loanTransMain.setProductId(loanChildProduct.getProductId());
		}
		if(loanCustomerInfo!=null){
			loanTransMain.setBorrower(loanCustomerInfo.getCustomerName());
			loanTransMain.setBorrowCustomerType(loanCustomerInfo.getCustomerType());
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("ownerId", loanCustomerInfo.getCustomerId());
			params.put("accountType", loanCustomerInfo.getCustomerType());
			loanAccountInfo=loanAccountInfoMapper.selectByOwnerIdAndAccountType(params);
			if(loanAccountInfo!=null){
				loanTransMain.setBorrowerAccountId(loanAccountInfo.getAccountId());
			}
		}
		if(loanParasConfig!=null){
			loanTransMain.setIsGuarantee(loanParasConfig.getLpcIsGuarantee());
			loanTransMain.setIsAdvanced(loanParasConfig.getLpcIsAdvance());
			loanTransMain.setInterestWay(loanParasConfig.getLpcInterestType());
			loanTransMain.setPayInterestWay(loanParasConfig.getLpcPayType());
			loanTransMain.setDateOfInterest(loanParasConfig.getLpcInterestRegular());
			loanTransMain.setDateOfPayInterest(loanParasConfig.getLpcPayRegular());
			loanTransMain.setOverRate(loanParasConfig.getLpcOverdueInteType().toString());
			loanTransMain.setDebtRate(loanParasConfig.getLpcDebtInteRate().toString());
			loanTransMain.setBreachRate(loanParasConfig.getLpcRepayLiquRate().toString());
			loanTransMain.setInvestBreachRate(loanParasConfig.getLpcPayLiquRate().toString());
			loanTransMain.setInvestOverRate(loanParasConfig.getLpcOverOutRate().toString());
		}
		loanTransMain.setListLoanRecordTemp(listLoanRecordTemp);
		loanTransMain.setListFeesPlan_in(listFeesPlan_in);
		loanTransMain.setListFeesPlan_out(listFeesPlan_out);
		return loanTransMain;
	}

	@Override
	public Integer updateOrderInfo(LoanOrderInfo loanOrderInfo) {
		return loanOrderInfoMapper.updateOrderInfoByOrderNo(loanOrderInfo);
	}

}
