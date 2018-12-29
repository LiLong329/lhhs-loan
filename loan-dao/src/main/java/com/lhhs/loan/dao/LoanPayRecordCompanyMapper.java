package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;

public interface LoanPayRecordCompanyMapper extends CrudDao<LoanPayRecordCompany>{
    List<LoanPayRecordCompany> querypayRecordCompany(Map<String, Object> map);
    Integer querypayRecordCompanyCount(Map<String, Object> map);
    List<LoanPayRecordCompany> querypayRecordCompanyExport(Map<String, Object> map);
    LoanPayRecordCompany queryPaysum(LoanPayRecordCompany entity);
    Map<String, Object> queryPaidTotal(Map<String, Object> map);
    List<LoanPayRecordCompany> querySettleDetail(String orderNo);
    
}