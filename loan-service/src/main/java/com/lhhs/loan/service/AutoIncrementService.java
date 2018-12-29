package com.lhhs.loan.service;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanAutoIncrement;

/**
 * 
 * ClassName: AutoIncrementService <br/>
 * Function: 获取 自增主键
 * date: 2017年8月7日 下午3:30:42 <br/>
 * @author suncj
 * @version 
 * @since JDK 1.8
 */

public interface AutoIncrementService {

	LoanAutoIncrement getAutoIncrement(Map<String, Object> map);

	int updateByPrimaryKeySelective(LoanAutoIncrement autoIncrement);
	
	
}
