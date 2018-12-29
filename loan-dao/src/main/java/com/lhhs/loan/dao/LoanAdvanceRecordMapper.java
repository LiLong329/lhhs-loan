package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAdvanceRecord;

public interface LoanAdvanceRecordMapper extends CrudDao<LoanAdvanceRecord>{
	
	public LoanAdvanceRecord queryAdvancesum(LoanAdvanceRecord record);
	
    public List<LoanAdvanceRecord> queryList(LoanAdvanceRecord record);
	
	public int queryCount(LoanAdvanceRecord record);
	public LoanAdvanceRecord queryTotalAmountSum(LoanAdvanceRecord record);
	
}