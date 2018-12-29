package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanProviderInfo;

public interface LoanProviderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanProviderInfo record);

    int insertSelective(LoanProviderInfo record);

    LoanProviderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanProviderInfo record);

    int updateByPrimaryKey(LoanProviderInfo record);
    
    List<LoanProviderInfo> queryProviderByNo(String providerNo);
    
    int updateByProviderNo(LoanProviderInfo provider);
     //经纪人(个人)
    List<LoanProviderInfo> provaderList(Map<String, Object> map);
    
    Integer provaderListCount(Map<String, Object> map);
    
    LoanProviderInfo queryProviderInfo(String providerno);
    
    //经纪人(企业)
    List<LoanProviderInfo> provaderCompanyList(Map<String, Object> map);
    
    Integer provaderCompanyListCount(Map<String, Object> map);
    
    List<LoanProviderInfo> provaderListExport(Map<String, Object> map);
    
    List<LoanProviderInfo> selectByDeptAndCompany(@Param("deptId")String deptId, @Param("companyId") String companyId);
    
    List<LoanProviderInfo> selectProviderByEmp(LoanEmp loanEmp);
    
    LoanProviderInfo selectProviderByMobile(String mobile);
  
}