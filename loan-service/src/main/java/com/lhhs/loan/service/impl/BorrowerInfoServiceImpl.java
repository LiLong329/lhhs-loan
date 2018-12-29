/**
 * Project Name:loan-service
 * File Name:BorrowerInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月8日上午11:39:30
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.service.BorrowerInfoService;
import com.lhhs.loan.service.transport.impl.MortgageInfoTransServiceImpl;

/**
 * ClassName:BorrowerInfoServiceImpl <br/>
 * Function: 借款人信息操作 <br/>
 * Date:     2017年7月8日 上午11:39:30 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class BorrowerInfoServiceImpl implements BorrowerInfoService {
	private static final Logger LOGGER = Logger.getLogger(MortgageInfoTransServiceImpl.class);
	
	@Autowired
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;

	/**
	 * 保存借款人基本信息
	 * @see com.lhhs.loan.service.BorrowerInfoService#saveBorrowerBaseInfo(com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs)
	 */
	@Override
	public Map<String, Object> saveBorrowerBaseInfo(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs) {
		Map<String, Object> result = new HashMap<String, Object>();
		int ret = 0;
		String custId = loanBorrowerInfoWithBLOBs.getCustId();
		String liveAddress = loanBorrowerInfoWithBLOBs.getLiveAddress();
		String corAddress = loanBorrowerInfoWithBLOBs.getCorAddress();
		String spoAddress = loanBorrowerInfoWithBLOBs.getSpouseAddress();
		if(!StrUtils.isNullOrEmpty(liveAddress)){
			loanBorrowerInfoWithBLOBs.setLiveAddress(liveAddress.replaceAll("&", "-"));
		}
		if(!StrUtils.isNullOrEmpty(corAddress)){
			loanBorrowerInfoWithBLOBs.setCorAddress(corAddress.replaceAll("&", "-"));
		}
		if(!StrUtils.isNullOrEmpty(spoAddress)){
			loanBorrowerInfoWithBLOBs.setSpouseAddress(spoAddress.replaceAll("&", "-"));
		}
		//int count = loanBorrowerInfoMapper.selectBorrowerInfoWithBLOBsCount(custId);
		LoanBorrowerInfoWithBLOBs queryObj = loanBorrowerInfoMapper.selectByPrimaryKey(custId);
		if(queryObj != null){
			ret = loanBorrowerInfoMapper.updateByPrimaryKeySelective(loanBorrowerInfoWithBLOBs);
			//ret = loanBorrowerInfoMapper.updateBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
		}else{
			ret = loanBorrowerInfoMapper.insertSelective(loanBorrowerInfoWithBLOBs);
			//ret = loanBorrowerInfoMapper.insertBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
		}
		if(ret == 1){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u66f4\u65b0\u6570\u636e\u6210\u529f\u0021"); // 更新数据成功!
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			LOGGER.debug("更新数据失败!");
		}
		return result;
	}

	@Override
	public LoanCustomerInfo selectByCustomerId(String customerId) {
		return loanCustomerInfoMapper.selectByCustomerId(customerId);
	}

}

