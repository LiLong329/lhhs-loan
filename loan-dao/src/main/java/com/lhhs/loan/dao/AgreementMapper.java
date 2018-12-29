package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.Agreement;

public interface AgreementMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Agreement record);

    int insertSelective(Agreement record);

    Agreement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agreement record);

    int updateByPrimaryKey(Agreement record);

	List<Agreement> queryListPage(Agreement entity);

	int queryCount(Agreement entity);

	Agreement getByEntity(Agreement entity);
}