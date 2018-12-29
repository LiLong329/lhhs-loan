package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.bump.domain.User;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;

public interface LoanEmpMapper {
	
    int deleteByPrimaryKey(Integer leEmpId);

    int insert(LoanEmp record);

    int insertSelective(LoanEmp record);

    LoanEmp selectByPrimaryKey(Integer leEmpId);

    int updateByPrimaryKeySelective(LoanEmp record);

    int updateByPrimaryKey(LoanEmp record);
    
    int updateEmpPassword(Map<String, Object> map);
    
    LoanEmp selectByAccount(String leAccount);
    
    List<LoanEmp> queryEmployeeList(Map<String, Object> map);
    
    int queryEmployeeCount(Map<String, Object> map);
    
    List<Map<String, Object>> queryAllCity();
    
    List<Map<String, Object>> queryAllDept();
    
    List<LoanDept> queryOneLevelDeptByCompanyId(@Param("ldCompany")String ldCompany);
    
    List<Map<String, Object>> queryAllQuarters(Map<String, Object> map);
    
    List<Map<String, Object>> queryAllGroup(int lgDeptId);
   
    List<Map<String, Object>> queryEmpRole(int queryEmpRole);
    
    LoanEmp queryEmpInfo(Integer leEmpId);
    
    LoanEmp queryEmpByAccount(String account);
    
    List<LoanEmp> queryEmployeeListExport(Map<String, Object> map);
    
    List<LoanEmp> queryEmpByMobile(String leMobile);
    
    List<Map<String, Object>>  queryDeptAll();
    
    /**
     * selectCustManagerByDeptId:根据部门编号查询下属的所有客户经理
     * @author kernel.org
     * @param leDeptId
     * @return
     * @since JDK 1.8
     */
    List<LoanEmp> selectCustManagerByDeptId(Integer leDeptId);
    
    /**
     * selectFunderAndRiskerList:查询资金部门和风控部门下面的人员
     * @author kernel.org
     * @param 
     * @return
     * @since JDK 1.8
     */
    List<LoanEmp> selectFunderAndRiskerList(LoanEmp emp);

    /**
     * 根据部门和岗位登录查找用户
     * @param lqDeptId
     * @param parentId
     * @return
     */
	LoanEmp selectByDeptQuarters(@Param("leDeptId")Integer lqDeptId, @Param("leQuartersId") Integer leQuartersId);
	
	/**
	 * 根据部门和岗位登录查找用户
	 * @param lqDeptId
	 * @param parentId
	 * @return
	 */
	LoanEmp selectByDeptQuartersTwo(@Param("leDeptId")Integer lqDeptId, @Param("leQuartersId") Integer leQuartersId, 
			@Param("branchCompanyId") String branchCompanyId);
	/**
	 * 根据手机号和公司查询用户
	 * @param mobile
	 * @param companyId
	 * @return
	 */
	LoanEmp selectByMobileAndCompany(@Param("mobile")String mobile, @Param("companyId") String companyId);
	/**
	 * 根据公司查询员工列表
	 * @param companyId
	 * @return
	 */
	List<LoanEmp> queryEmpByCompanyId(String companyId);
	
	List<LoanEmp> queryEmpListByEmp(LoanEmp loanEmp);
	
	/**
	 * queryCustManagerByDeptId:查询部门下所有客户经理信息
	 * @author suncj
	 * @param paramsMap
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanEmp> queryCustManagerByDeptId(Map<String, Object> paramsMap);

	/**
	 * 查询组别领导
	 */
	List<LoanEmp> queryTeamDirector(LoanEmp loanEmp);
	/**
	 * 查询部门领导
	 */
	List<LoanEmp> queryDeptLeader(LoanEmp loanEmp);
	
	/**
	 * 查询用户手机号是否存在
	 */
	List<LoanEmp> queryEmpMobileIsExistence(LoanEmp loanEmp);

	List<LoanDept> querySubDeptByDeptId(@Param("ldDeptId")String ldDeptId);

	List<User> queryCustManagerBySaas(Map<String, Object> paramsMap);
	
}