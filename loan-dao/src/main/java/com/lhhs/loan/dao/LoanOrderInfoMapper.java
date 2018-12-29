package com.lhhs.loan.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.OrderInfo;
import com.lhhs.loan.dao.domain.vo.LoanOrderInfoVo;
import com.lhhs.loan.dao.domain.vo.OrderInfoDetailVo;

public interface LoanOrderInfoMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(LoanOrderInfo record);

    int insertSelective(LoanOrderInfo record);

    /**
     * 通过orderNo查询LoanOrderInfo对象
     */
    LoanOrderInfo selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(LoanOrderInfo record);

    int updateByPrimaryKey(LoanOrderInfo record);
    
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
     * selectLoanApplyList:财务管理放款申请列表显示
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    List<LoanOrderInfoVo> selectLoanApplyList(LoanOrderInfoVo entity);
    
    /**
     * selectLoanApplyList:财务管理放款申请列表显示记录数据统计
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    int selectLoanApplyListCount(LoanOrderInfoVo entity);
    /**
     * 根据订单号更新客户来源
     * @param record
     * @return
     */
    int updateOrderInfoByOrderNo(LoanOrderInfo record);
    
    /**
     * 查询我的待办任务列表
     * @param map
     * @return
     */
    List<Map<String, Object>> selectMyNewTaskList(Map<String, Object> map);
    
    /**
     * 查询我的待办任务列表的总记录数
     * @param map
     * @return
     */
    Integer selectMyNewTaskListCount(Map<String, Object> map);
    
    /**
     * 查询报单、借款人基本信息列表
     * @param map
     * @return
     */
    Map<String, Object> selectBasicInfo(Map<String, Object> map);
    
	/**
	 * 查询报单
	 */
	List<OrderInfo> findListByEntity( OrderInfo params);

	/**
	 * 查询报单  分页使用总条数
	 */
	int countListByEntity(OrderInfo params);

	/**
	 * 查询用户下属人数
	 */
	Integer findEmpCountById(Integer leEmpId);
    
    /**
     * 首页查询我的待办任务列表
     * @param map
     * @return
     */
    List<Map<String, Object>> selectIndexMyNewTaskList(Map<String, Object> map);
    
    /**
     * 首页查询我的待办任务列表的总记录数
     * @param map
     * @return
     */
    Integer selectIndexMyNewTaskListCount(Map<String, Object> map);

    /**
     * 
     * @param id
     * @return
     */
	OrderInfoDetailVo getEntityById(String id);

	/**
	 * 查找组别老大对应的组
	 * @param leEmpId
	 * @return
	 */
	String findGroupById(Integer leEmpId);
	/**
	 * 查找部门老大对应的部门
	 * @param leEmpId
	 * @return
	 */
	String findDeptById(Integer leEmpId);
	
    /**
     * 获取订单总额、当天订单总数
     * @return
     */
	Map<String, Object> getOrderSumInfo(Map<String, Object> param);
	
	List<LoanEmp>  getFengKongEmp(String orderNo);
	/**
	 * 根据放款账务主表更新订单状态为已结清
	 * 
	 */
	int updateLoanOrderInfoStatusCleanUp();
	/**
	 * 根据放款账务主表更新订单状态为已还款
	 * 
	 */
	int updateLoanOrderInfoStatusPaid();
	/**
	 * 根据还款计划更新订单状态为已逾期
	 * 
	 */
	int updateLoanOrderInfoStatusOver();
	/**
	 * 更新结清的订单放款主表field3为'1'
	 * 
	 */
	int updateLoanTransMainCleanUp();
	//查询没有生成流程的订单
	List<LoanOrderInfo> queryOrderInfoNoProcList(LoanOrderInfo record);
	/**
	 * 
	 * 启动工作流更新订单状态，流程信息
	 */
	int updateByProcInsId(LoanOrderInfo record);

	/**
	 * 报单统计
	 * @param params
	 * @return
	 */
	List<OrderInfo> getOrderCountList(LoanOrderInfo params);
	/**
	 * 报单统计
	 * @param params
	 * @return
	 */

	int getOrderCountListCount(LoanOrderInfo params);

	/**放款总金额
	 * 
	 * @param entity
	 * @return
	 */
	Map<String, Object> getVariousTotalCount(LoanOrderInfo entity);
	
	/**
	 * 根据时间查询报单两，成交量
	 * @param time
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	List<Map<String, Object>> getOrderInfoEchartsList(LoanOrderInfo entity);
	
	
	/**
	 * 根据时间查询报单量
	 * @param time
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	List<Map<String, Object>> getOrderCountByTime(LoanOrderInfo entity);
	
	/**
	 * 根据时间查询放款量
	 * @param time
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	List<Map<String, Object>> getDealCountByTime(LoanOrderInfo entity);

	List<LoanAccountCard> getAccountCardList(LoanOrderInfo orderInfo);
	
	
	
	
}