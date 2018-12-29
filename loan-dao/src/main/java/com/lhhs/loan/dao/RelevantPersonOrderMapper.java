package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.RelevantPersonOrder;

public interface RelevantPersonOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int delete(RelevantPersonOrder record);
    
    int insert(RelevantPersonOrder record);

    int insertSelective(RelevantPersonOrder record);

    RelevantPersonOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RelevantPersonOrder record);

    int updateByPrimaryKey(RelevantPersonOrder record);
    
    List<RelevantPersonOrder> queryList(RelevantPersonOrder entity);
    
    int insertList(List<RelevantPersonOrder> relevantPersonList);
}