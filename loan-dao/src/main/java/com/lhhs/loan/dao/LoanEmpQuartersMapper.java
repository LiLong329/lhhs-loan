package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanEmpQuarters;

public interface LoanEmpQuartersMapper {
    int deleteByPrimaryKey(Integer leqEqId);

    int insert(LoanEmpQuarters record);

    int insertSelective(LoanEmpQuarters record);

    LoanEmpQuarters selectByPrimaryKey(Integer leqEqId);

    int updateByPrimaryKeySelective(LoanEmpQuarters record);

    int updateByPrimaryKey(LoanEmpQuarters record);
    
    int deleteByEmpId(Integer leqEmpId);
    
    List<LoanEmpQuarters>  selectByEmpId(Integer leqEmpId);
    
    List<Integer>  selectQuartersIdByEmpId(Integer leqEmpId);
    
    List<String>  selectQuartersNamesByEmpId(Integer leqEmpId);

	List<LoanEmpQuarters> queryListPage(LoanEmpQuarters entity);

	int queryCount(LoanEmpQuarters entity);

	List<Map<String, Object>> queryAllCompany(LoanEmpQuarters entity);

	List<Map<String, Object>> queryAllDept(String companyId);
}