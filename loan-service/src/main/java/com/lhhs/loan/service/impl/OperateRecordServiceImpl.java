package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanOperateRecordMapper;
import com.lhhs.loan.dao.domain.LoanOperateRecordWithBLOBs;
import com.lhhs.loan.service.OperateRecordService;

@Service
public class OperateRecordServiceImpl implements OperateRecordService{

	@Autowired
	private LoanOperateRecordMapper loanOperateRecordMapper;
	
	@Override
	public LoanOperateRecordWithBLOBs getEntityById(Long id) {
		return loanOperateRecordMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int save(LoanOperateRecordWithBLOBs record) {
		return loanOperateRecordMapper.insertSelective(record);
	}

	@Override
	public int updateEntityById(LoanOperateRecordWithBLOBs record) {
		return loanOperateRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page queryListPage(Page page, LoanOperateRecordWithBLOBs record) {
		record.setPage(page);
		List<LoanOperateRecordWithBLOBs> resultList = loanOperateRecordMapper.queryList(record);
		if(page==null){
			page=new Page();
		}
		page.setResultList(resultList);
		page.setTotalCount(loanOperateRecordMapper.queryCount(record));
		return page;
	}

}
