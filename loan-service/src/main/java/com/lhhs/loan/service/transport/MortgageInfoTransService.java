/**
 * Project Name:loan-service
 * File Name:MortgageInfoTransService.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年6月29日下午2:21:11
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;


/**
 * ClassName:MortgageInfoTransService <br/>
 * Function: 同步客户信息模块的抵押物信息<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午2:21:11 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface MortgageInfoTransService {
	
	/**
	 * findBorrowerInfoExtendListByOrderNo:根据订单编号查询订单借款人基本信息 <br/>
	 * 注意事项 – 该方法只用于同步订单中借款人信息到碰碰旺系统<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> findBorrowerInfoExtendByOrderNo(String orderNo);
	/**
	 * findHouseExtendListByOrderNo:根据订单编号查询订单房产抵押物信息 <br/>
	 * 注意事项 – 该方法只用于同步订单中借款人信息到碰碰旺系统<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	public List<Map<String,Object>> findHouseExtendListMapByOrderNo(String orderNo);
	
	/**
	 * findCarExtendListByOrderNo:根据订单编号查询订单车产抵押物信息 <br/>
	 * 注意事项 – 该方法只用于同步订单中借款人信息到碰碰旺系统<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	public List<Map<String,Object>> findCarExtendListMapByOrderNo(String orderNo);
	
	
	/**
	 * saveMortgageInfo:同步客户管理模块中借款人抵押物信息<br/>
	 *
	 * @author xiangfeng
	 * @param mortgageInfo
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> saveMortgageInfoFromRemote(String mortgageInfo);

	/**
	 * saveMortgageInfoToRemote: <br/>
	 * 星火项目修改客户管理中借款人的抵押物信息后，<br/>
	 * 同步到碰碰旺系统
	 * @author xiangfeng
	 * @param custId
	 * @param houseInfoLists
	 * @param carInfoLists
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String,Object> saveMortgageInfoToRemote(String customerId);

	/**
	 * saveBorrowerAllInfoToRemote:
	 * 下户审核或者财务放款审核，同步订单里借款人的所有信息到客户信息表<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @param loanEmp
	 * @since JDK 1.8
	 */
	public void saveBorrowerAllInfoToRemote(String orderNo,LoanEmp loanEmp);
	
	/**
	 * getBorrowerInfoFromRemote:
	 * 从碰碰旺系统获取借款人所有信息<br/>
	 * 这个方法适用条件 – 通过custId拉取借款人的所有信息
	 * @author xiangfeng
	 * @param custId
	 * @param loanEmp 当获取不到登录人信息时，公司和集团默认归属晋商睦家集团
	 * @param orderNo 为了更新customerId到订单信息表
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> getBorrowerInfoFromRemote(String custId,LoanEmp loanEmp,String orderNo);
	/**
	 * changeOrderCredentials:下户时产品改变时，复制产品资质到订单 <br/>
	 * 先让碰碰旺复制资质，然后返回所有信息，存入我们系统<br/>
	 * @author xiangfeng
	 * @param orderNo
	 * @param productId
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> changeOrderCredentials(String orderNo, String productId);
	/**
	 * saveBorrowerAllInfoToCustomerInfo:<br/>
	 * 借款人的所有信息首先保存到自己库 <br/>
	 * 然后保存数据到碰碰旺
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @param loanEmp
	 * @param isLoanAdd
	 * @since JDK 1.8
	 */
	public void saveBorrowerAllInfoToCustomerInfo(String orderNo, LoanEmp loanEmp, String isLoanAdd,AuditParamVo auditParamVo);
}

