package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanFundTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.dao.domain.LoanParasConfig;
import com.lhhs.loan.dao.domain.LoanQuarters;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.dao.domain.vo.LoanOrderInfoVo;

/**
 * 待办事项中业务审批流程Service
 * @author kernel.org
 *
 */
public interface ApprovalProcessService extends BaseService<LoanOrderInfoVo>{

	/**
     * 查询放款申请,报单基本信息
     * selectOrderBasicInfo:
     * @author kernel.org
     * @param loanOrderInfoVo
     * @return
     * @since JDK 1.8
     */
    LoanOrderInfoVo selectOrderBasicInfo(LoanOrderInfoVo loanOrderInfoVo);
    
    /**
     * selectDeptIdByZJName:根据部门名称为资金部查询部门id
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    LoanDept selectLoanDeptByLdDeptId(Integer ldDeptId);
    
    /** 查询项目审核记录表(审批流程节点) **/
	List<LoanFundTaskWithBLOBs> selectFundHandledRecord(String orderNo);
    
    /**
     * selectFunderAndRiskerList:查询资金部门和风控部门下面的人员
     * @author kernel.org
     * @param leDeptId
     * @return
     * @since JDK 1.8
     */
    List<LoanEmp> selectFunderAndRiskerList(LoanEmp emp);
    
    /**
     * selectByPrimaryKey:根据员工id查询LoanEmp对象
     * @author kernel.org
     * @param leEmpId
     * @return
     * @since JDK 1.8
     */
    LoanEmp selectByPrimaryKey(Integer leEmpId);
    
    /**
	 * selectLoanQuartersByQuartersId:根据QuartersId查询对象
	 * @author kernel.org
	 * @param lqQuartersId
	 * @return
	 * @since JDK 1.8
	 */
	LoanQuarters selectLoanQuartersByQuartersId(Integer lqQuartersId);
    
    /**
     * selectCustManagerByDeptId:根据部门编号查询下属的所有客户经理
     * @author kernel.org
     * @param leDeptId
     * @return
     * @since JDK 1.8
     */
    List<LoanEmp> selectCustManagerByDeptId(Integer leDeptId);
	
	/**
	 * 查询我的待办任务列表
	 * @param map
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> selectMyNewTaskList(Map<String, Object> map,Page page);
	
	/**
     * selectLoanApplyList:财务管理放款申请列表显示
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
   // List<LoanOrderInfoVo> selectLoanApplyList(LoanOrderInfoVo entity);
	
	/**
	 * 查询项目审核记录表(审批流程节点)
	 * @return
	 */
	List<LoanExTaskWithBLOBs> selectHandledRecord(Map<String, Object> map);	
	
	/**
	 * selectLoanOrderHouseExtend:查询放款申请中的房产扩展信息
	 * @author kernel.org
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanOrderHouseExtend> selectLoanOrderHouseExtend(LoanOrderHouseExtend entity);
	
	/**
     * 查询报单人基本信息列表
     * @param map
     * @return
     */
    Map<String, Object> selectBasicInfo(Map<String, Object> map);
    
    /**
     * selectLoanOrderBorrowerExtendBLOBs:根据订单号查询放款申请的详细数据
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
    LoanOrderBorrowerExtendWithBLOBs selectLoanOrderBorrowerExtendBLOBs(LoanOrderBorrowerExtendWithBLOBs entity);
    
    /**
     * 查询借款人基本信息列表
     */
    List<LoanOrderBorrowerExtendWithBLOBs> selectLoanOrderBorrowerExtendBLOBsList(Map<String, Object> map);
    
    /**
     * 查询放款申请表-资金方基本信息
     * @return
     */
    List<LoanCapitalInfo> selectLoanCapitalInfo(Map<String, Object> map);
    
    /**
     * selectOrganizationAccountInfo:根据订单号查询资金方对公账户信息
     * @author kernel.org
     * @param map
     * @return
     * @since JDK 1.8
     */
    LoanOrganization selectOrganizationAccountInfo(Map<String, Object> map);
    
    /**
     * 查询放款申请表-收入信息
     * @return
     */
    List<LoanCapitalEarning> selectLoanCapitalEarning(Map<String, Object> map);
    
    /**
     * 查询放款申请表-支出信息
     * @return
     */
    List<LoanCapitalExpenditure> selectLoanCapitalExpenditure(Map<String, Object> map);
    
    /**
     * 节点审核流程
     * @param loanExTaskWithBLOBs
     * @return
     */
    int insert(LoanExTaskWithBLOBs record);
    
    /**
     * 通过 orderNo insert审批流程
     * @param record
     * @return
     */
    int insertExTaskByOrderNo(LoanExTaskWithBLOBs record);
    
    /**
     * 根据订单号更新客户来源
     * @param record
     * @return
     */
    int updateOrderInfoByOrderNo(LoanOrderInfo loanOrderInfo);
    
    /**
     * 通过订单号更新订单详情表中的部分字段
     * @param record
     * @return
     */
    int updateOrderInfoDetailByOrderNo(LoanOrderInfoDetail loanOrderInfoDetail);
    
    /**
     * 更新借款基本信息列表
     * @param record
     * @return
     */
    int updateOrderBorrowerExtendBLOBsByOrderNo(LoanOrderBorrowerExtendWithBLOBs record);
    
    /**
     * update基本信息
     * @param loanOrderInfo
     * @param loanOrderInfoDetail
     * @param rderNo
     * @return
     */
    int updateBasicInfo(LoanOrderInfo  orderInfo,LoanOrderInfoDetail loanOrderInfoDetail, LoanOrderBorrowerExtendWithBLOBs loanOrderBorrowerExtendWithBLOBs, String orderNo, Integer customerOrigin,List<RelevantPersonOrder> relevantPersonList);

    /**
     * insertFundTaskByOrderNo:进行资金审批时将会签记录保存于会签记录表
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
	int insertFundTaskWithEntity(LoanFundTaskWithBLOBs entity);
    
    /**
     * 通过 orderNo 更新审批流程
     * @param record
     * @return
     */
    int updateExTaskByOrderNo(LoanExTaskWithBLOBs record, 
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
    						  String historyLoanMethod);
    
    /**
     * updateExTaskHandleHQTYByOrderNo:资金审批处理结果:同意
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int updateExTaskHandleHQTYByOrderNo(LoanExTaskWithBLOBs record, 
									    Integer letNode, 
									    Integer approvalNodeStatus);
    
    /**
     * 处理审核节点基类(审批结果:同意)
     * @param letNode
     * @return
     */
    int updateExTaskHandleTYCommonByOrderNo(LoanExTaskWithBLOBs record, 
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
											String historyLoanMethod);
    
    /**
     * 处理结果:同意
     * @param record
     * @return
     */
    int updateExTaskHandleTYByOrderNo(LoanExTaskWithBLOBs record, 
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
									  String historyLoanMethod);

    /**
     * 处理结果:拒贷
     * @param record
     * @return
     */
    int updateExTaskHandleJDByOrderNo(LoanExTaskWithBLOBs record, Integer letNode);

    /**
     * 处理结果:补件基类 
     * @param record
     * @return
     */
    int updateExTaskHandleBJCommonByOrderNo(LoanExTaskWithBLOBs record, 
										    Integer letNode, 
										    Integer flowLetNode, 
										    Long fundSide, 
										    String productId, 
										    String productTypeNo,
										    Integer actualTerm,
										    String actualTermUnit,
										    String actualLoanDate,
										    Integer customerManager);
    /**
     * 处理结果:补件
     * @param record
     * @return
     */
    int updateExTaskHandleBJByOrderNo(LoanExTaskWithBLOBs record, 
									  Integer letNode, 
									  Integer flowLetNode, 
									  Long fundSide, 
									  String productId, 
									  String productTypeNo,
									  Integer actualTerm,
									  String actualTermUnit,
									  String actualLoanDate,
									  Integer customerManager);

    /**
     * 处理结果:撤单
     * @param record
     * @return
     */
    int updateExTaskHandleCDByOrderNo(LoanExTaskWithBLOBs record, 
    								  Integer letNode);
    
    /**
     * 处理结果:会签
     * @param record
     * @return
     */
    int updateExTaskHandleHQByOrderNo(LoanExTaskWithBLOBs record, 
    								  Integer letNode,
    								  Integer approvalNodeStatus,
    								  Integer flowLetNode,
    								  Integer fundFlowNode);
    
    /**
     * selectLoanCapitalInfoByBankCardId:根据银行卡号查询放款申请表中的实体bean
     * @author kernel.org
     * @param bankcardId
     * @return
     * @since JDK 1.8
     */
    LoanCapitalInfo selectLoanCapitalInfoByBankCardIdAndOrderno(Map<String, Object> map);
    
    /**
     * updateByBankCardIdAndOrderno:根据银行卡号和订单号更新放款申请表
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int updateByBankCardIdAndOrderno(LoanCapitalInfo record);
    
    /**
     * 通过借款人的 custId 更新借款信息表
     * @param record
     * @return
     */
    int insertBorrowerInfoWithBLOBs(LoanBorrowerInfoWithBLOBs record);
    
    /**
     * 放款申请表更新and添加
     * @param loanCapitalEarning
     * @param loanCapitalInfo
     * @param loanCapitalExpenditure
     * @param parasConfig 
     * @return
     */
    Map<String, Object> addUpdateLoanApply(String loanCapitalEarning,String loanCapitalInfo,String loanCapitalExpenditure,String orderNo, String parasConfig);
    
    /**
     * 查询 LoanOrganization 实体bean
     */
    List<LoanOrganization> selectLoanOrganizationList();
    
    /**
     * 根据组织机构id查询产品名称对象
     * @param orgId
     * @return
     */
    List<LoanChildProduct> selectChildProductByOrgId(Long orgId,String orderNo);
    
    /**
     * 通过orderNo查询LoanOrderInfo对象
     */
    LoanOrderInfo selectByPrimaryKey(String orderNo);
    
    /**
     * 通过orderNo查询LoanOrderInfoDetail对象
     */
    LoanOrderInfoDetail selectOrderDetailByOrderNo(String orderNo);

    /**
     * 验证必传资质信息是否完全上传
     * @param orderNo
     * @param productId
     * @return
     * 		false 未完全上传
     * 		true  已完全上传
     */
	boolean isUploadRequiredCredentials(String orderNo, String productId);
	
	/**
     * 根据订单号查询资金方
     * @param orderNo
     * @return
     */
	List<LoanOrgSupportAreas> selectLoanOrgSupportAreas(String orderNo);
	
	/**
	 * 根据Emp查询资金方
	 * @param LoanEmp
	 * @return
	 */
	List<LoanOrgSupportAreas> selectOrgSupportAreasByEmp(LoanEmp loanEmp);
	
	/**
	 * 首页查询我的待办任务列表
	 * @param map
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> selectIndexMyNewTaskList(Map<String, Object> map,Page page);
	
	/**
	 * 查询报单参数设置对象
	 * @param String
	 * @return
	 */
	LoanParasConfig selectParasConfigByOrderNo(String orderNo);
	
	/**
	 * 生成账户交易大对象LoanTransMain
	 * @param String
	 * @return
	 */
	LoanTransMain getLoanTransMain(String orderNo, String actualLoanDate, String isPayPlanTemp,
			String isLoanRecordTemp, String isPayPlanCompanyTemp, String historyLoanMethod);
	
	/**
	 * updateOrderInfo:更新订单表<br/>
	 *
	 * @author xiangfeng
	 * @param loanOrderInfo
	 * @return
	 * @since JDK 1.8
	 */
	Integer updateOrderInfo(LoanOrderInfo loanOrderInfo);

	List<LoanChildProduct> selectChildProductByFundOwner(Map<String, Object> params);
	
}
