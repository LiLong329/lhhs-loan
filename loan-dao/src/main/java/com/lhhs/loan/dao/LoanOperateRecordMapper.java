package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanOperateRecord;
import com.lhhs.loan.dao.domain.LoanOperateRecordWithBLOBs;

public interface LoanOperateRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanOperateRecordWithBLOBs record);

    int insertSelective(LoanOperateRecordWithBLOBs record);

    LoanOperateRecordWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanOperateRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LoanOperateRecordWithBLOBs record);

    int updateByPrimaryKey(LoanOperateRecord record);

	List<LoanOperateRecordWithBLOBs> queryList(LoanOperateRecordWithBLOBs record);

	int queryCount(LoanOperateRecordWithBLOBs record);
}