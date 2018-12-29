package com.lhhs.loan.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.NoticeRecordMapper;
import com.lhhs.loan.dao.domain.NoticeRecord;
import com.lhhs.loan.service.NoticeRecordService;

@Service
public class NoticeRecordServiceImpl implements NoticeRecordService{
	@Autowired
	private NoticeRecordMapper noticeRecordMapper;
	
	@Override
	public NoticeRecord get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeRecord get(NoticeRecord entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeRecord> queryList(NoticeRecord entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(NoticeRecord entity) {
		Page page = entity.getPage();
		page.setPageIndex(entity.getPageIndex());
		List<NoticeRecord> list = noticeRecordMapper.queryListPage(entity);
		page.setResultList(list);
		page.setTotalCount(noticeRecordMapper.queryCount(entity));
		if (StringUtils.isNotEmpty(entity.getReceiverId())&&list!=null&&list.size()>0) {
			noticeRecordMapper.updateByList(list);
		}
		return page;
	}

	@Override
	public int save(NoticeRecord entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NoticeRecord entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(NoticeRecord entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(NoticeRecord entity) {
		return noticeRecordMapper.queryCount(entity);
	}

	@Override
	public void saveList(List<NoticeRecord> noticeRecords) {
		noticeRecordMapper.insertList(noticeRecords);
	}
	
}

