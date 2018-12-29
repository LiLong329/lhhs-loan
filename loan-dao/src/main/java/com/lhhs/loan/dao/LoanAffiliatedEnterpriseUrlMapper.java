package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanAffiliatedEnterpriseUrl;

public interface LoanAffiliatedEnterpriseUrlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanAffiliatedEnterpriseUrl record);

    int insertSelective(LoanAffiliatedEnterpriseUrl record);

    LoanAffiliatedEnterpriseUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanAffiliatedEnterpriseUrl record);

    int updateByPrimaryKey(LoanAffiliatedEnterpriseUrl record);

	List<LoanAffiliatedEnterpriseUrl> queryList(LoanAffiliatedEnterpriseUrl entity);
}