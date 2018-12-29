package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanProductSupportAreas;

public interface LoanProductSupportAreasMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteByProductId(String productId);

    int insert(LoanProductSupportAreas record);

    int insertSelective(LoanProductSupportAreas record);

    LoanProductSupportAreas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoanProductSupportAreas record);

    int updateByPrimaryKey(LoanProductSupportAreas record);

	List<LoanProductSupportAreas> selectByProductId(String id);
}