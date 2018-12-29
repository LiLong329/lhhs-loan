package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanAuthgroup;

public interface LoanAuthgroupMapper {
	
    int deleteByPrimaryKey(Integer groupId);

    int insert(LoanAuthgroup record);

    int insertSelective(LoanAuthgroup record);

    LoanAuthgroup selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(LoanAuthgroup record);

    int updateByPrimaryKey(LoanAuthgroup record);
    
    List<LoanAuthgroup> queryAuthGroupList(Map<String, Object> map);

	List<LoanAuthgroup> getAuthorityGroups(Map<String, Object> params);

	Integer getAuthorityCount(Map<String, Object> params);

	List<String> getAuthgroupNames();

	LoanAuthgroup authorityGroupDetail(@Param("groupId")Integer groupId);
    
}