package com.lhhs.loan.dao;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanParasConfig;

public interface LoanParasConfigMapper extends CrudDao<LoanParasConfig> {
    
    LoanParasConfig selectByOrderNo(String orderNo);
}