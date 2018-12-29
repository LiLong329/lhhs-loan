package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanOrderCredentials;

public interface LoanOrderCredentialsMapper {
    int deleteByPrimaryKey(Long orderCredentialsNo);

    int insert(LoanOrderCredentials record);

    int insertSelective(LoanOrderCredentials record);

    LoanOrderCredentials selectByPrimaryKey(Long orderCredentialsNo);

    int updateByPrimaryKeySelective(LoanOrderCredentials record);

    int updateByPrimaryKey(LoanOrderCredentials record);

	List<LoanOrderCredentials> findOrderCredentialsInfoLists(Map<String, Object> param);

	Integer findOrderCredentialsInfoCount(Map<String, Object> param);

	LoanOrderCredentials findOrderCredentialsByNo(Integer orderCredentialsNo);

	Integer findOrderRequiredCredentialsInfoNoUploadCount(@Param("orderNo")String orderNo, @Param("productId")String productId);
	
	List<Map<String, Object>> queryOrderCredentialsParseJson(Map<String, Object> param);
	
}