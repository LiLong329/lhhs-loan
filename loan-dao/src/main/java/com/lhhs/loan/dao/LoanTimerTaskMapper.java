package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanTimerTask;

public interface LoanTimerTaskMapper {
    int deleteByPrimaryKey(Long taskId);

    int insert(LoanTimerTask record);

    int insertSelective(LoanTimerTask record);

    LoanTimerTask selectByPrimaryKey(Long taskId);

    int updateByPrimaryKeySelective(LoanTimerTask record);

    int updateByPrimaryKeyWithBLOBs(LoanTimerTask record);

    int updateByPrimaryKey(LoanTimerTask record);
    
    int queryTaskByWorkId(Map<String, Object> map);
    
    List<LoanTimerTask> queryAllTask();
    
    LoanTimerTask queryTaskWorkIdAndType(Map<String, Object> map);
}