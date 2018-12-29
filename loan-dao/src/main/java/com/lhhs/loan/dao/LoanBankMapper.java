package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanBank;

public interface LoanBankMapper extends CrudDao<LoanBank>{
    LoanBank selectByBankName(String bankName);
    List<LoanBank>  queryAllBank();
}