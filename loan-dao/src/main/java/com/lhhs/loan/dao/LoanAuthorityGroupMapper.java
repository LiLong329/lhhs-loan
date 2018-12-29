package com.lhhs.loan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanAuthorityGroup;

public interface LoanAuthorityGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanAuthorityGroup record);

    int insertSelective(LoanAuthorityGroup record);

    LoanAuthorityGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanAuthorityGroup record);

    int updateByPrimaryKey(LoanAuthorityGroup record);

	int insertList(List<LoanAuthorityGroup> loanAuthorityGroupList);
	/**
	 * 根据数据组id删除
	 * @param groupId
	 * @return
	 */
	int delByGroupId(@Param("groupId") Integer groupId);

	List<LoanAuthorityGroup> getSelectAutGupByGupId(@Param("groupId") Integer groupId);
}