package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanOrderAffiliatedEnterpriseInfo;

public interface LoanOrderAffiliatedEnterpriseInfoMapper {
    int deleteByPrimaryKey(Long affiliatedEnterpriseId);

    int insert(LoanOrderAffiliatedEnterpriseInfo record);

    int insertSelective(LoanOrderAffiliatedEnterpriseInfo record);

    LoanOrderAffiliatedEnterpriseInfo selectByPrimaryKey(Long affiliatedEnterpriseId);

    int updateByPrimaryKeySelective(LoanOrderAffiliatedEnterpriseInfo record);

    int updateByPrimaryKeyWithBLOBs(LoanOrderAffiliatedEnterpriseInfo record);

    int updateByPrimaryKey(LoanOrderAffiliatedEnterpriseInfo record);

	List<LoanOrderAffiliatedEnterpriseInfo> queryList(LoanOrderAffiliatedEnterpriseInfo entity);

	int delete(LoanOrderAffiliatedEnterpriseInfo info);
}