package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanProductCredentials;

public interface LoanProductCredentialsMapper {
    int deleteByPrimaryKey(String proCredentialsNo);
    
    int deleteByProductId(String productId);

    int insert(LoanProductCredentials record);

    int insertSelective(LoanProductCredentials record);

    LoanProductCredentials selectByPrimaryKey(String proCredentialsNo);

    int updateByPrimaryKeySelective(LoanProductCredentials record);

    int updateByPrimaryKey(LoanProductCredentials record);

	List<LoanProductCredentials> findProductCredentialsByProductId(@Param("childProductNo") String childProductNo);

	List<LoanProductCredentials> proCredList(Map<String, Object> map);
	/** 根据二级产品id删除产品模板**/
	int deleteByproductId(String productId);
}