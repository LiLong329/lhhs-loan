package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanDept;
public interface LoanDeptMapper {
    int deleteByPrimaryKey(Integer ldDeptId);

    int insert(LoanDept record);

    int insertSelective(LoanDept record);

    LoanDept selectByPrimaryKey(Integer ldDeptId);

    int updateByPrimaryKeySelective(LoanDept record);

    int updateByPrimaryKey(LoanDept record);
    
    List<LoanDept> selectDeptList(Map<String, Object> map);
    
    int selectDeptListCount(Map<String, Object> map);
    
    int updateDeptByStuts(Map<String, Object> map);
    
    List<Map<String, Object>> queryAllDept();
    
    LoanDept queryDeptInfo(Map<String, Object> map);
    
    LoanDept queryDept(Integer ldDeptId);
    
    Integer queryDeptByName(LoanDept dept);
    
    List<LoanDept> queryDeptListByName(LoanDept dept);
    
    /**
     * selectDeptIdByZJName:根据部门名称为资金部查询部门id
     * @author kernel.org
     * @return
     * @since JDK 1.8
     */
    LoanDept selectLoanDeptByLdDeptId(Integer ldDeptId);
    
    List<LoanDept> queryDeptByCompanyId(@Param("ldCompany")String ldCompany);
    
    List<LoanDept> queryDeptName(Map<String, Object> params);
    
    int insertAuthgroupUserByEmp();
    
    List<LoanDept> queryDeptByUnionId(Map<String,Object> param);
}