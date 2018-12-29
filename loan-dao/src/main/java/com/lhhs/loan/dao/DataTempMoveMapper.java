package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanDept;

public interface DataTempMoveMapper {
	int deleteDeptBack();
	int deleteDeptBackIsEmp(LoanDept dept);
	int deleteDeptTemp();
	int insertDeptBack();
	LoanDept getDep(Long deptId);
	LoanDept getDept(LoanDept dept);
	List<LoanDept> queryDeptList();
	List<LoanDept> queryGroupList();
	List<String> queryDeptNotEmp();
	/**
	 * 
	 * 按部门、组、公司、集团分组
	 */
	List<LoanDept> queryGroupListFromEmp();
	
	/**
	 * 
	 * copy 部门、组到field1,field2
	 */
	int updateEmpBack();
	/**
	 * 
	 * 更新部门
	 */
	int updateEmpByDep();
	/**
	 * 
	 * 更新组
	 */
	int updateEmpByGroup();
	/**
	 * 
	 * 清除组信息
	 */
	int clearEmpGroup();
	/**
	 * 
	 * copy 创建人部门、组、客户归属部门、组到field3-field8
	 */
	int updateCrmUserBack();
	int updateCrmUserByCreaterDep();
	int updateCrmUserByCreaterGroup();
	int updateCrmUserByCreaterOrg();
	int updateCrmUserByCreaterOrgByGroup();
	int updateCrmUserByAppointDep();
	int updateCrmUserByAppointGroup();
	int updateCrmUserByAppointOrg();
	int updateCrmUserByAppointOrgByGroup();
	
	int updateCrmUserRecordBack();
	int updateCrmUserRecordByCreaterDep();
	int updateCrmUserRecordByCreaterGroup();
	int updateCrmUserRecordByCreaterOrg();
	int updateCrmUserRecordByCreaterOrgByGroup();
	int updateCrmUserRecordByAppointDep();
	int updateCrmUserRecordByAppointGroup();
	int updateCrmUserRecordByAppointOrg();
	int updateCrmUserRecordByAppointOrgByGroup();
	/**
	 * 
	 * 更新借款人客户信息
	 */
	int updateCustomerInfoBack();
	int updateCustomerInfoDep();
	
	/**
	 * 
	 * 更新投资人客户信息
	 */
	int updateInvestCustomerInfoBack();
	int updateInvestCustomerInfoDep();
	int updateInvestCustomerInfoGroup();
	
	/**
	 * 
	 * 更新订单信息
	 */
	int updateOrderInfoBack();
	int updateOrderInfoDep();
	/**
	 * 
	 * 更新审批记录
	 */
	int updateActCommentBack();
	int updateActCommentDep();
	/**
	 * 
	 * 迁移操作员属性为部门领导的
	 */
	int insertAuthgroupUserByEmp();
	/**
	 * 
	 * 更新放款主表
	 */
	int updateLoanTransMain();
}