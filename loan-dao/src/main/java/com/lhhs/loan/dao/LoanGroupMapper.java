package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanGroup;

public interface LoanGroupMapper {
    int deleteByPrimaryKey(Integer lgGroupId);

    int insert(LoanGroup record);

    int insertSelective(LoanGroup record);

    LoanGroup selectByPrimaryKey(Integer lgGroupId);

    int updateByPrimaryKeySelective(LoanGroup record);

    int updateByPrimaryKey(LoanGroup record);
    
    List<LoanGroup> selectGroupList(Map<String, Object> map);
    
    Integer selectGroupListCount(Map<String, Object> map);
    
    int updateGroupByStuts(LoanGroup group);
    
    LoanGroup queryGroupCount(Map<String, Object> map);
    
    int queryGroupbyName(Map<String, Object> map);
    
    List<LoanGroup> allGroup();
    
    List<String> queryGroupIdByDept(int lgDeptId);
}