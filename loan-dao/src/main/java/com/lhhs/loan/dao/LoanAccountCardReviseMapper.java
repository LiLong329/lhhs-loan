package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanAccountCardRevise;

public interface LoanAccountCardReviseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanAccountCardRevise record);

    int insertSelective(LoanAccountCardRevise record);

    LoanAccountCardRevise selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanAccountCardRevise record);

    int updateByPrimaryKey(LoanAccountCardRevise record);
    
    List<LoanAccountCardRevise> queryReviseDetail(Map<String, Object> map);
}