package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.dao.RelevantPersonOrderMapper;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;
import com.lhhs.loan.service.RelevantPersonOrderService;

@Service
public class RelevantPersonOrderServiceImpl implements RelevantPersonOrderService{

	@Autowired
	private RelevantPersonOrderMapper relevantPersonOrderMapper;
	
	@Override
	public List<RelevantPersonOrder> queryList(RelevantPersonOrder entity) {
		return relevantPersonOrderMapper.queryList(entity);
	}

}
