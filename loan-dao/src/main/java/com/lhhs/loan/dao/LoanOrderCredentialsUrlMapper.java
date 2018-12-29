package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanOrderCredentialsUrl;

public interface LoanOrderCredentialsUrlMapper {
    int deleteByPrimaryKey(Long urlId);

    int insert(LoanOrderCredentialsUrl record);

    Long insertSelective(LoanOrderCredentialsUrl record);

    LoanOrderCredentialsUrl selectByPrimaryKey(Long urlId);

    int updateByPrimaryKeySelective(LoanOrderCredentialsUrl record);

    int updateByPrimaryKey(LoanOrderCredentialsUrl record);

	List<LoanOrderCredentialsUrl> findOrderCredentialsURLs(Long orderCredentialsNo);

	List<LoanOrderCredentialsUrl> findAllOrderCredentialsURLs(String orderNo);
}