package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.NoticeModel;

public interface NoticeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeModel record);

    int insertSelective(NoticeModel record);

    NoticeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeModel record);

    int updateByPrimaryKey(NoticeModel record);

	List<NoticeModel> queryList(NoticeModel entity);

	List<NoticeModel> getByType(NoticeModel noticeModel);

	int updateModelStatus(NoticeModel params);
}