package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.vo.PayRecordVo;

public interface LoanPayRecordMapper extends CrudDao<LoanPayRecord>{
    List<PayRecordVo> queryListPage(LoanPayRecord entity);
	Map<String, BigDecimal> queryTotalAmount(LoanPayRecord entity);
	LoanPayRecord queryPaysum(LoanPayRecord entity);
}