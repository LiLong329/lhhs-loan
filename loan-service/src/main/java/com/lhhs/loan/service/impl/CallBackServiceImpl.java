package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanCallBackMapper;
import com.lhhs.loan.dao.domain.LoanCallBack;
import com.lhhs.loan.service.CallBackService;
@Service
public class CallBackServiceImpl implements CallBackService{

	@Autowired
	private LoanCallBackMapper loanCallBackMapper;
	@Override
	public LoanCallBack get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanCallBack get(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(LoanCallBack entity) {
		return loanCallBackMapper.insertSelective(entity);
	}

	@Override
	public int update(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanCallBack entity) {
		// TODO Auto-generated method stub
		return 0;
	}

}
