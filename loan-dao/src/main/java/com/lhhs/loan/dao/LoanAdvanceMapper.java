package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanAdvance;

public interface LoanAdvanceMapper extends CrudDao<LoanAdvance>{
	public LoanAdvance queryAdvancesum(LoanAdvance parm);
	
	public List<LoanAdvance> queryAdvanceAll(LoanAdvance parm);
	
	public int queryCount(LoanAdvance parm);
	
	public Map<String, BigDecimal> queryTotalAmount(LoanAdvance parm);
	
	public LoanAdvance selectByPKey(LoanAdvance param);
	
}