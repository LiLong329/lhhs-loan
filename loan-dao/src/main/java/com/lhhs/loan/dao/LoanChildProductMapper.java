package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanProductCredentials;

public interface LoanChildProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(LoanChildProductWithBLOBs record);

    int insertSelective(LoanChildProductWithBLOBs record);

    LoanChildProductWithBLOBs selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(LoanChildProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LoanChildProductWithBLOBs record);

    int updateByPrimaryKey(LoanChildProduct record);
    
    List<LoanChildProduct> queryProductById(Map<String,Object> map);
    
    List<LoanProductCredentials> queryProductCredentInfo(String productId);
    
    LoanChildProductWithBLOBs queryProductInfoById(String productId);
    
    /**
     * 根据组织机构id查询产品名称对象
     * @param orgId
     * @return
     */
    List<LoanChildProduct> selectChildProductByOrgId(Map<String,Object> map);

    LoanChildProductWithBLOBs getCredentialsProductById(String id);
}