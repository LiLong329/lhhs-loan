package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanParentProduct;

public interface LoanParentProductMapper {
    int deleteByPrimaryKey(String productNo);

    int insert(LoanParentProduct record);

    int insertSelective(LoanParentProduct record);

    LoanParentProduct selectByPrimaryKey(String productNo);

    int updateByPrimaryKeySelective(LoanParentProduct record);

    int updateByPrimaryKey(LoanParentProduct record);
    
    List<LoanParentProduct> queryProductList(String productNo);
    
    LoanParentProduct queryProductInfo(Map<String, Object> map);
    
    List<Map<String, Object>> queryAllProduct();

	List<LoanParentProduct> queryProductListByType(String productType);

	List<LoanParentProduct> queryProductByStatus(String productType);
}