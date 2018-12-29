package com.lhhs.loan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanRecord;
import com.lhhs.loan.dao.domain.vo.LoanRecordVo;

public interface LoanRecordMapper extends CrudDao<LoanRecord>{
   
    
    List<LoanRecordVo> queryListPage(LoanRecordVo entity);

	int queryCountPage(LoanRecordVo entity);
	
	Map<String, BigDecimal> queryTotalAmount(LoanRecordVo entity);

	List<Map<String,Object>> queryMonthStatisticsList(LoanRecordVo entity);

	BigDecimal queryWeekStatisticsList(LoanRecordVo entity);

	List<Map<String,Object>> queryDayStatisticsList(LoanRecordVo entity);
}