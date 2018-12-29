package com.lhhs.loan.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.dao.LoanAutoIncrementMapper;
import com.lhhs.loan.dao.domain.LoanAutoIncrement;
import com.lhhs.loan.service.AutoIncrementService;

/**
 * 获取客户基本信息，个人基本信息 自增主键实现类
 */
@Service("autoIncrementService")
public class AutoIncrementServiceImpl implements AutoIncrementService {
	private static final Logger logger = LoggerFactory.getLogger(AutoIncrementServiceImpl.class);

	@Autowired
	private LoanAutoIncrementMapper autoIncrementMapper;
	
	@Override
	public LoanAutoIncrement getAutoIncrement(Map<String, Object> map) {
		return autoIncrementMapper.getAutoIncrement(map);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public int updateByPrimaryKeySelective(LoanAutoIncrement autoIncrement) {
		return autoIncrementMapper.updateByPrimaryKeySelective(autoIncrement);
	}
}
