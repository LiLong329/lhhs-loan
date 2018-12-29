package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.ActComment;

public interface ActCommentSelfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActComment record);

    int insertSelective(ActComment record);

    ActComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActComment record);
    int updateByPrimaryKey(ActComment record);
   
}