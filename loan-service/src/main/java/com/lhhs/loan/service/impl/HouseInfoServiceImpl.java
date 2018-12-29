/**
 * Project Name:loan-service
 * File Name:HouseInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年6月29日下午3:40:10
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
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanHouseInfoMapper;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.service.HouseInfoService;

/**
 * ClassName:HouseInfoServiceImpl <br/>
 * Function: 客户管理模块中借款人的房抵押信息接口具体实现<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午3:40:10 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class HouseInfoServiceImpl implements HouseInfoService {
	private static final Logger LOGGER = Logger.getLogger(CarInfoServiceImpl.class);
	
	@Autowired
	private LoanHouseInfoMapper loanHouseInfoMapper;

	/**
	 * 
	 *  保存或更新借款人的房抵押信息
	 * @see com.lhhs.loan.service.HouseInfoService#saveHouseInfo(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional
	public int saveHouseInfo(List<LoanHouseInfo> houseLists,String custId,String customerId) {
		LOGGER.debug("保存房抵押信息；参数：\n"+JSON.toJSONString(houseLists));
		int num = 0;
		loanHouseInfoMapper.deleteByCustId(custId);
		for(LoanHouseInfo houseInfo : houseLists){
			houseInfo.setId(null);
			houseInfo.setCustId(custId);
			houseInfo.setCustomerId(customerId);
			String address = houseInfo.getHouseAddress();
			if(!StrUtils.isNullOrEmpty(address)){
				houseInfo.setHouseAddress(address.replaceAll("&", "-"));
			}
			houseInfo.setUpdateTime(new Date());
			num = num + loanHouseInfoMapper.insertSelective(houseInfo);
		}
		return num;
	}
}

