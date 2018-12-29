/**
 * Project Name:loan-service
 * File Name:BorrowerTransportServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年6月29日上午10:51:55
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.ServiceType;
import com.lhhs.loan.common.utils.Encodes;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.service.scheduler.TimingTaskService;
import com.lhhs.loan.service.transport.BorrowerTransportService;

/**
 * ClassName:BorrowerTransportServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月29日 上午10:51:55 <br/>
 * 
 * @author kernel.org
 * @version
 * @since JDK 1.8
 * @see
 */
@Service("borrowerTransportService")
public class BorrowerTransportServiceImpl implements BorrowerTransportService {
	private static final Logger LOGGER = Logger.getLogger(BorrowerTransportServiceImpl.class);
	
	@Resource
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper;
	@Resource
	private RestTemplateComponent restTemplateComponent;
	@Autowired
	private TimingTaskService timingTaskService;
	
	
	//////////贷前系统<=>碰碰旺系统对接模块接口 LiJianjun START //////////
	// RECEIVED PENGPENGWANG START
	/**
	 * 同步碰碰旺系统中的借款人数据到贷前系统
	 */
	public Map<String, Object> syncBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs) {
		Map<String, Object> result = new HashMap<String, Object>();
		int ret = 0;
		String custId = loanBorrowerInfoWithBLOBs.getCustId();
		boolean flag = this.selectBorrowerInfoWithBLOBsCount(custId);
		if (flag) {
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
			ret = this.updateBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
			if(ret != 0){
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "\u540c\u6b65\u501f\u6b3e\u4eba\u6570\u636e\u6210\u529f\uff01"); // 同步借款人数据成功！
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "\u540c\u6b65\u501f\u6b3e\u4eba\u6570\u636e\u5931\u8d25\uff01"); // 同步借款人数据失败！
			}
		}else{
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u8be5\u501f\u6b3e\u4eba\u4fe1\u606f\u4e0d\u5c5e\u4e8e\u5c0f\u8d37\u7cfb\u7edf"); // 该借款人信息不属于小贷系统
		}
		return result;
	}
	// RECEIVED PENGPENGWANG END
	
	// SEND PENGPENGWANG START
	/**
	 * syncBorrowerInfoWithBLOBsToRemote:同步贷前系统中的借款人数据到碰碰旺系统
	 * @author kernel.org
	 * @param map
	 * @return
	 * @since JDK 1.8
	 */
	@Async
	public Map<String, Object> syncBorrowerInfoWithBLOBsToRemote(String customerId){
		int ret = 0;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		LoanBorrowerInfoWithBLOBs borrowerInfo = loanBorrowerInfoMapper.selectByCustomerId(customerId);
		try {
			retMap = restTemplateComponent.post("borrowerInfo/borrowerInfo", JSON.toJSONString(borrowerInfo), Map.class);
			if(retMap.get("retCode").equals(SystemConst.FAIL)){ 
				timingTaskService.timedTask("borrowerInfo/borrowerInfo", JSON.toJSONString(borrowerInfo), customerId, SystemConst.POST, SystemConst.FAIL, ServiceType.CUSTBORROWERINFO.getIndex());
			}else{
				timingTaskService.timedTask("borrowerInfo/borrowerInfo", JSON.toJSONString(borrowerInfo), customerId, SystemConst.POST, SystemConst.SUCCESS, ServiceType.CUSTBORROWERINFO.getIndex());
			}
		} catch (Exception e) {
			timingTaskService.timedTask("borrowerInfo/borrowerInfo", JSON.toJSONString(borrowerInfo), customerId, SystemConst.POST, SystemConst.FAIL, ServiceType.CUSTBORROWERINFO.getIndex());
			LOGGER.error("同步借款人数据到碰碰旺系统失败");
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	// SEND PENGPENGWANG END

	// COMMON INTERFACE START
	/**
	 * syncBorrowerInfoWithBLOBsToLocal:在将borrowerInfoWithBLOBs同步到碰碰旺系统后，再将返回的数据同步到贷前系统数据库中
	 * @author kernel.org
	 * @param loanBorrowerInfoWithBLOBs
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> syncBorrowerInfoWithBLOBsToLocal(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs){
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
		boolean localFlag = this.selectBorrowerInfoWithBLOBsCount(custId);
		if(localFlag){
			ret = this.updateBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
		}else{
			ret = this.insertBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
		}
		if(ret == 1){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "\u66f4\u65b0\u6570\u636e\u6210\u529f\u0021"); // 更新数据成功!
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
		}
		return result;
	}
	
	/**
	 * 1.根据LoanBorrowerInfoWithBLOBs实体bean对象中的custId查询当前报单人是否存在.
	 * 2.根据返回结果判断当前报单人是否存在: true:存在 false:不存在
	 */
	public boolean selectBorrowerInfoWithBLOBsCount(String custId) {
		int count = loanBorrowerInfoMapper.selectBorrowerInfoWithBLOBsCount(custId);
		if (count != 0) {
			return true;
		}
		return false;
	}

	/**
	 * insertBorrowerInfoWithBLOBsToDQ:新增碰碰旺系统中的借款人信息到贷前系统.
	 * @author kernel.org
	 * @param record
	 * @return
	 * @since JDK 1.8
	 */
	public int insertBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs) {
		return loanBorrowerInfoMapper.insertBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
	}

	/**
	 * updateBorrowerInfoWithBLOBsToDQ:根据custId更新碰碰旺系统中的借款人信息到贷前系统.
	 * @author kernel.org
	 * @param record
	 * @return
	 * @since JDK 1.8
	 */
	public int updateBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs) {
		return loanBorrowerInfoMapper.updateBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
	}
	// COMMON INTERFACE END
	//////////贷前系统<=>碰碰旺系统对接模块接口 LiJianjun END //////////

}
