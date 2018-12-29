/**
 * Project Name:loan-service
 * File Name:TimeingTaskServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年7月7日上午9:25:57
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler.impl;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.ServiceType;
import com.lhhs.loan.dao.LoanProviderInfoMapper;
import com.lhhs.loan.dao.LoanTimerTaskMapper;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanTimerTask;
import com.lhhs.loan.service.scheduler.TimingTaskService;

/**
 * ClassName:TimeingTaskServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年7月7日 上午9:25:57 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
@Service
public class TimingTaskServiceImpl implements TimingTaskService {

	private static final Logger LOGGER = Logger.getLogger(TimingTaskServiceImpl.class);
	@Autowired
	private RestTemplateComponent restTemplateComponent;
	@Autowired
	private LoanTimerTaskMapper loanTimerTaskMapper;
	@Autowired
	private LoanProviderInfoMapper loanProviderInfoMapper;

	/**
	 * TODO 跑批任务信息维护方法
	 * 
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#timedTask(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void timedTask(String url, String parameter, String taskWorkId, String taskMethed, String taskState,
			String requestType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskWorkId", taskWorkId);
		paramMap.put("taskType", requestType);
		LoanTimerTask ltt = loanTimerTaskMapper.queryTaskWorkIdAndType(paramMap);
		if (ltt != null) {
			ltt.setTaskUrl(url);
			ltt.setTaskParameter(parameter);
			ltt.setTaskRequestMethed(taskMethed);
			ltt.setTaskState(taskState);// 01:失败 00:成功
			ltt.setTaskWorkId(taskWorkId);
			ltt.setTaskRequestMethed(taskMethed);
			ltt.setTaskTime(new Date());
			loanTimerTaskMapper.updateByPrimaryKeySelective(ltt);
		} else {
			if (taskState.equals(SystemConst.FAIL)) {// 只插入通讯失败记录
				ltt = new LoanTimerTask();
				ltt.setTaskUrl(url);
				ltt.setTaskParameter(parameter);
				ltt.setTaskWorkId(taskWorkId);
				ltt.setTaskTime(new Date());
				ltt.setTaskRequestMethed(taskMethed);// 1:post 0:get
				ltt.setTaskState(taskState);// 01:失败 00:成功
				ltt.setTaskRequestType(requestType);
				loanTimerTaskMapper.insertSelective(ltt);
			}
		}
	}

	/**
	 * 
	 * TODO 通讯失败后的数据处理--跑批任务.
	 * 
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#timedRunTask()
	 */
	@Override
	@SuppressWarnings("all")
	//@Scheduled(cron = "0 0 0,1,2 * * ? ")
	public void timedRunTask() {
		Map<String, Object> retMap = null;
		List<LoanTimerTask> loanTimerTask = loanTimerTaskMapper.queryAllTask();
		if (loanTimerTask.size() > 0) {
			for (LoanTimerTask tt : loanTimerTask) {
				try {
					if (tt.getTaskRequestMethed().equals(SystemConst.POST)) {
						retMap = restTemplateComponent.post(tt.getTaskUrl(), tt.getTaskParameter(), Map.class);
						if (retMap.get("retCode").equals(SystemConst.FAIL)) {
							timedTask(tt.getTaskUrl(), tt.getTaskParameter(), tt.getTaskWorkId(), SystemConst.POST,
									SystemConst.FAIL, tt.getTaskRequestType());
						} else {
							timedTask(tt.getTaskUrl(), tt.getTaskParameter(), tt.getTaskWorkId(), SystemConst.POST,
									SystemConst.SUCCESS, tt.getTaskRequestType());
						}
					} else if (tt.getTaskRequestMethed().equals(SystemConst.GET)) {
						if (tt.getTaskRequestType().trim().equals(ServiceType.PROVIDERINFO.getIndex())) {
							this.providerUpdate(tt.getTaskWorkId());
						}
					}
				} catch (Exception e) {
					LOGGER.debug("同步碰碰旺系统异常!!!!!!!!!!!异常信息:" + e.getMessage());
					timedTask(tt.getTaskUrl(), tt.getTaskParameter(), tt.getTaskWorkId(), tt.getTaskRequestMethed(),
							SystemConst.FAIL, tt.getTaskRequestType());
				}
			}
		}
	}

	/**
	 * TODO 拉取碰碰旺经纪人数据（可选）.
	 * 
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#providerUpdate(java.lang.String)
	 */
	@Override
	@Async
	public void providerUpdate(String providerNo) {
		LOGGER.debug("开始拉取报单人信息:" + providerNo);
		try {
			// 拉取碰碰旺经纪人信息
			String provider = restTemplateComponent.get("/agent/provider/" + providerNo, String.class);
			LOGGER.debug("报单人信息拉去回来了。。。。。。。。。。:" + provider);

			LoanProviderInfo providerInfo = JSON.parseObject(URLDecoder.decode(provider, "utf-8"),
					LoanProviderInfo.class);
			LOGGER.debug("拉取的ProviderInfo信息:" + providerInfo.toString());

			List<LoanProviderInfo> proInfo = loanProviderInfoMapper.queryProviderByNo(providerNo);// 查询本地是否存在该经济人

			providerInfo.setProvinceNo(providerInfo.getProvice());
			providerInfo.setProvinceName(providerInfo.getProvice());
			providerInfo.setCityNo(providerInfo.getRegion());
			providerInfo.setCityName(providerInfo.getRegion());

			if (proInfo.size() >= 1) {
				providerInfo.setProviderNo(providerNo);
				int pro = loanProviderInfoMapper.updateByProviderNo(providerInfo);
				if (pro < 1) {
					LOGGER.debug("更新本地ProviderInfo失败:" + providerInfo.toString());
					timedTask("/agent/provider/" + providerNo, null, providerNo, SystemConst.GET, SystemConst.FAIL,
							ServiceType.PROVIDERINFO.getIndex());
				} else {
					timedTask("/agent/provider/" + providerNo, null, providerNo, SystemConst.GET, SystemConst.SUCCESS,
							ServiceType.PROVIDERINFO.getIndex());
				}
			} else {
				LOGGER.debug("新插入一条ProviderInfo信息:" + providerInfo.toString());
				providerInfo.setId(null);
				providerInfo.setProviderNo(providerNo);
				int procount = loanProviderInfoMapper.insertSelective(providerInfo);
				if (procount < 1) {
					timedTask("/agent/provider/" + providerNo, null, providerNo, SystemConst.GET, SystemConst.FAIL,
							ServiceType.PROVIDERINFO.getIndex());
				} else {
					timedTask("/agent/provider/" + providerNo, null, providerNo, SystemConst.GET, SystemConst.SUCCESS,
							ServiceType.PROVIDERINFO.getIndex());
				}
			}
		} catch (Exception e) {
			LOGGER.debug("ProviderInfo信息出现异常！！！异常信息：" + e.getMessage() + "\n");
			timedTask("/agent/provider/" + providerNo, null, providerNo, SystemConst.GET, SystemConst.FAIL,
					ServiceType.PROVIDERINFO.getIndex());
		}
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see com.lhhs.loan.service.scheduler.TimingTaskService#getProviderList()
	 */

	@SuppressWarnings("unused")
	@Override
	//@Scheduled(cron = "0 0/15 * * * ? ")
	public void getProviderList() {

		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			System.out.println("************拉取经纪人信息开始***********");
			String providerListString = restTemplateComponent.post("/agent/provider/getProviderList.json", "");
			List<LoanProviderInfo> providerList = JSONObject.parseArray(providerListString, LoanProviderInfo.class);
			if (providerList != null && providerList.size() > 0) {
				for (LoanProviderInfo provider : providerList) {
					this.saveOrUpdateProvider(provider);
				}

			}

		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	public void saveOrUpdateProvider(LoanProviderInfo provider) {

		try {
			if (provider != null) {
				provider.setProvinceNo(provider.getProvice());
				provider.setProvinceName(provider.getProvice());
				provider.setCityNo(provider.getRegion());
				provider.setCityName(provider.getRegion());
				List<LoanProviderInfo> proInfo = loanProviderInfoMapper.queryProviderByNo(provider.getProviderNo());// 查询本地是否存在该经济人
				if (proInfo != null && proInfo.size() > 0) {
					int pro = loanProviderInfoMapper.updateByProviderNo(provider);
				} else {
					provider.setId(null);
					int procount = loanProviderInfoMapper.insertSelective(provider);
				}

			}

		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

}
