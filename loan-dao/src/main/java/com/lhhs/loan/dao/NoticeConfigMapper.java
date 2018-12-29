package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.NoticeConfig;

public interface NoticeConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeConfig record);

    int insertSelective(NoticeConfig record);

    NoticeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeConfig record);

    int updateByPrimaryKey(NoticeConfig record);

	NoticeConfig getByEntity(NoticeConfig entity);
}