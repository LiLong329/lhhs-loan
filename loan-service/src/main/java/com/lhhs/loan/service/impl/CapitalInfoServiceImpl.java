/**
 * Project Name:loan-service
 * File Name:CapitalInfoServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年6月29日下午3:39:43
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.LoanCapitalInfoMapper;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.service.CapitalInfoService;

/**
 * ClassName:CapitalInfoServiceImpl <br/>
 * Function: 报单资金方信息<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 下午3:39:43 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class CapitalInfoServiceImpl implements CapitalInfoService {
	
	private static final Logger LOGGER = Logger.getLogger(CapitalInfoServiceImpl.class);

	@Autowired
	private LoanCapitalInfoMapper loanCapitalInfoMapper;
	
	/**
	 * saveCapitalInfo更新本次放款金额或已放款金额:<br/>
	 *	
	 * @author chenyinhui
	 * @param type（1为更新本次放款金额，2为更新已放款金额）
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	@Override
	public Map<String, Object> updateCapitalInfo(int type, String orderNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int i=0;
		result.put("orderNo", orderNo);
		result.put(SystemConst.retCode, SystemConst.SUCCESS);
		List<LoanCapitalInfo> list=loanCapitalInfoMapper.selectLoanCapitalInfo(result);
		if(list!=null&&list.size()==0){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "不能查询到该订单资金方信息");
			return result;
		}
		for(LoanCapitalInfo loanCapitalInfo:list){
			if(type==1){
				loanCapitalInfo.setAmountPaidThis(loanCapitalInfo.getAmountPaid().subtract(loanCapitalInfo.getAmountPaidAlready()));
				i+=loanCapitalInfoMapper.updateByPrimaryKeySelective(loanCapitalInfo);
			}
			if(type==2){
				loanCapitalInfo.setAmountPaidAlready(loanCapitalInfo.getAmountPaidThis().add(loanCapitalInfo.getAmountPaidAlready()));
				i+=loanCapitalInfoMapper.updateByPrimaryKeySelective(loanCapitalInfo);
			}
		}
		if(i!=list.size()){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "更新资金方信息失败");
			return result;
		}
		return result;
	}

}

