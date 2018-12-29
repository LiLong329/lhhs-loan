package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.NoticeRecord;

public interface NoticeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeRecord record);

    int insertSelective(NoticeRecord record);

    NoticeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeRecord record);

    int updateByPrimaryKey(NoticeRecord record);

	List<NoticeRecord> queryListPage(NoticeRecord entity);

	int queryCount(NoticeRecord entity);

	void insertList(List<NoticeRecord> noticeRecords);

	int updateByList(List<NoticeRecord> list);
}