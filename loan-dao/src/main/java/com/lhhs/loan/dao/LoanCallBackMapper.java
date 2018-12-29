package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.LoanCallBack;

public interface LoanCallBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanCallBack record);

    int insertSelective(LoanCallBack record);

    LoanCallBack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanCallBack record);

    int updateByPrimaryKey(LoanCallBack record);
}