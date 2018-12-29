package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.NoticeConfigMapper;
import com.lhhs.loan.dao.domain.NoticeConfig;
import com.lhhs.loan.service.NoticeConfigService;

@Service
public class NoticeConfigServiceImpl implements NoticeConfigService{
	
	@Autowired
	private NoticeConfigMapper noticeConfigMapper;
	
	@Override
	public NoticeConfig get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeConfig get(NoticeConfig entity) {
		return noticeConfigMapper.getByEntity(entity);
	}

	@Override
	public List queryList(NoticeConfig entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(NoticeConfig entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(NoticeConfig entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NoticeConfig entity) {
		return noticeConfigMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(NoticeConfig entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(NoticeConfig entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

