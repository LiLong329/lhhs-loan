package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.SipAccountMapper;
import com.lhhs.loan.dao.domain.SipAccount;
import com.lhhs.loan.service.SipAccountService;

@Service
public class SipAccountServiceImpl implements SipAccountService{
	@Autowired
	private SipAccountMapper sipAccountMapper;

	@Override
	public SipAccount get(String id) {
		return sipAccountMapper.selectByPrimaryKey(Integer.valueOf(id));
	}

	@Override
	public SipAccount get(SipAccount entity) {
		return sipAccountMapper.getByEntity(entity);
	}

	@Override
	public List queryList(SipAccount entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(SipAccount entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(SipAccount entity) {
		return sipAccountMapper.insertSelective(entity);
	}

	@Override
	public int update(SipAccount entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(SipAccount entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(SipAccount entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SipAccount getByUserId(String userId) {
		return sipAccountMapper.getByUserId(userId);
	}
	
}

