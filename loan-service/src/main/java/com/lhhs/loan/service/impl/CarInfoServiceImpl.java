/**
 * Project Name:loan-service
 * File Name:CarInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年6月29日下午3:39:43
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.dao.LoanCarInfoMapper;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.service.CarInfoService;

/**
 * ClassName:CarInfoServiceImpl <br/>
 * Function: 客户管理模块中借款人的车抵押信息接口具体实现<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午3:39:43 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class CarInfoServiceImpl implements CarInfoService {
	private static final Logger LOGGER = Logger.getLogger(CarInfoServiceImpl.class);
	
	@Autowired
	private LoanCarInfoMapper loanCarInfoMapper;

	/**
	 * 
	 * 保存或更新借款人的车抵押信息
	 * @see com.lhhs.loan.service.CarInfoService#saveCarInfo(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional
	public int saveCarInfo(List<LoanCarInfo> carLists,String custId,String customerId) {
		LOGGER.debug("保存车抵押信息；参数：\n"+JSON.toJSONString(carLists));
		int num = 0;
		loanCarInfoMapper.deleteByCustId(custId);
		for(LoanCarInfo carInfo : carLists){
			carInfo.setId(null);
			carInfo.setCustId(custId);
			carInfo.setCustomerId(customerId);
			carInfo.setUpdateTime(new Date());
			num = num + loanCarInfoMapper.insertSelective(carInfo);
		}
		return num;
	}

}

