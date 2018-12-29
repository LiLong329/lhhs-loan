package com.lhhs.workflow.dao;

import com.lhhs.workflow.dao.domain.User;

public interface ActUserMapper {

    User selectByPrimaryKey(String userId);
    
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}