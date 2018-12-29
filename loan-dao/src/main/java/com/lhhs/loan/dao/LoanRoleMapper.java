package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.lhhs.loan.dao.domain.LoanRole;

public interface LoanRoleMapper {
    int deleteByPrimaryKey(Integer lrRoleId);

    int insert(LoanRole record);

    int insertSelective(LoanRole record);

    LoanRole selectByPrimaryKey(Integer lrRoleId);

    int updateByPrimaryKeySelective(LoanRole record);

    int updateByPrimaryKey(LoanRole record);
    //查询所有的角色分页列表
	List<LoanRole> getRoles(Map<String, Object> params);
    //查询角色数量
	Integer getRolesCount(Map<String, Object> params);
    //查询角色名称列表
	List<String> getRoleNames();
	//查询所有可用角色列表
	List<LoanRole> getEnableRoleList(List<Integer> roleIdList);
	
	List<Map<String, Object>> getAllRole();
	
	LoanRole selectByRoleName(String roleName);
	
	
}