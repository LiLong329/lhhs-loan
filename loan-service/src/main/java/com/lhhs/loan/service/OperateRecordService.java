package com.lhhs.loan.service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanOperateRecordWithBLOBs;

public interface OperateRecordService {
	
	public LoanOperateRecordWithBLOBs getEntityById(Long id);
	
	public int save(LoanOperateRecordWithBLOBs record);
	
	public int updateEntityById(LoanOperateRecordWithBLOBs record);

	public Page queryListPage(Page page, LoanOperateRecordWithBLOBs record);

}