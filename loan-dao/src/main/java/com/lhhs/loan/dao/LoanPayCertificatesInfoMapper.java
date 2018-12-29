package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanPayCertificatesInfo;

public interface LoanPayCertificatesInfoMapper {
    int deleteByPrimaryKey(Long certificatesNo);

    int insert(LoanPayCertificatesInfo record);

    int insertSelective(LoanPayCertificatesInfo record);

    LoanPayCertificatesInfo selectByPrimaryKey(Long certificatesNo);

    int updateByPrimaryKeySelective(LoanPayCertificatesInfo record);

    int updateByPrimaryKey(LoanPayCertificatesInfo record);
    
    List<LoanPayCertificatesInfo>  queryInfoByTransNo(String transNo);
}