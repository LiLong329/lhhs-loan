package com.lhhs.loan.service.transport.impl;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.ServiceType;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanProviderInfoMapper;
import com.lhhs.loan.dao.LoanTimerTaskMapper;
import com.lhhs.loan.dao.domain.LoanCapitalEarning;
import com.lhhs.loan.dao.domain.LoanCapitalExpenditure;
import com.lhhs.loan.dao.domain.LoanCapitalInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.service.scheduler.TimingTaskService;
import com.lhhs.loan.service.transport.ProviderTransportService;

@Service
@SuppressWarnings("all")
public class ProviderTransportServiceImpl implements ProviderTransportService {

	
	private static final Logger LOGGER = Logger.getLogger(ProviderTransportServiceImpl.class);

	@Autowired
	private LoanProviderInfoMapper loanProviderInfoMapper;
	@Autowired
	private RestTemplateComponent restTemplateComponent;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private TimingTaskService taskService;

	/**
	 * TODO 同步碰碰旺经纪人数据
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#providerInfoUpdate(com.lhhs.loan.dao.domain.LoanProviderInfo)
	 */
	@Override
	public String providerInfoUpdate(LoanProviderInfo loanProviderInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String providerNo = loanProviderInfo.getProviderNo();
		List<LoanProviderInfo> proList = loanProviderInfoMapper.queryProviderByNo(providerNo);
		if (proList.size() >= 1) {// 更新操作
			loanProviderInfo.setProviderNo(loanProviderInfo.getProvice());
			loanProviderInfo.setProviderName(loanProviderInfo.getProvice());
			loanProviderInfo.setCityNo(loanProviderInfo.getRegion());
			loanProviderInfo.setCityName(loanProviderInfo.getRegion());
			int pro = loanProviderInfoMapper.updateByProviderNo(loanProviderInfo);
			if (pro >= 1) {
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			} else {
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			}
		} else {
			resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 
	 * TODO 客户经理信息更新或新增向碰碰旺推送..
	 * 
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#asynEmpUpdate(com.lhhs.loan.dao.domain.LoanEmp)
	 */
	@Override
	@Async
	public void asynEmpUpdate(String leAccount) {
		LoanEmp loanEmp=loanEmpMapper.queryEmpByAccount(leAccount);
		try {
			if (loanEmp != null) {
				LOGGER.debug("-------------------马上请求碰碰旺系统---------------------");
				LOGGER.debug("-------------------请求参数："+JSON.toJSONString(loanEmp));
				String isSuccsess = restTemplateComponent.post("/clientManager/operationClientInfo",
						JSON.toJSONString(loanEmp), String.class);
				LOGGER.debug("-------------------请求碰碰旺系统结束---------------------");
				LOGGER.debug("-------------------返回数据："+isSuccsess);
				Map<String, String> isExist = JSON.parseObject(isSuccsess, Map.class);
				if (isExist.get("retCode").equals(SystemConst.FAIL)) {
					LOGGER.debug("！！！！！！！！客户经理信息推送失败！！！！！！！！");
					taskService.timedTask("/clientManager/operationClientInfo", JSON.toJSONString(loanEmp),
							loanEmp.getLeAccount(), SystemConst.POST, SystemConst.FAIL, ServiceType.EMPSEND.getIndex());
				} else {
					LOGGER.debug("！！！！！！！！客户经理信息推送成功！！！！！！！！");
					taskService.timedTask("/clientManager/operationClientInfo", JSON.toJSONString(loanEmp),
							loanEmp.getLeAccount(), SystemConst.POST, SystemConst.SUCCESS,ServiceType.EMPSEND.getIndex());
				}
			}
		} catch (Exception e) {
			LOGGER.debug("-------------------请求碰碰旺系统结束，抛出异常信息---------------------");
			LOGGER.debug("-------------------异常信息："+e.getMessage());
			LOGGER.debug("-------------------异常信息2："+e);
			taskService.timedTask("/clientManager/operationClientInfo", JSON.toJSONString(loanEmp),
					loanEmp.getLeAccount(), SystemConst.POST, SystemConst.FAIL, ServiceType.EMPSEND.getIndex());
		}
	}

	/**
	 * TODO 放款记录信息更新向碰碰旺推送..
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#updateLoanApply(java.util.List,
	 *      java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	@Async
	public void updateLoanApply(List<LoanCapitalEarning> loanCapEarn, List<LoanCapitalInfo> loanCapInfo,
			List<LoanCapitalExpenditure> loanCapExpend, String orderNo, String isLoanAdd) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!"Y".equals(isLoanAdd)){//非小贷自己报单
			try {
				paramMap.put("mhdCode", "05");
				paramMap.put("orderNo", orderNo);
				paramMap.put("loanCapitalEarning", JSON.toJSONString(loanCapEarn));
				paramMap.put("loanCapitalInfo", JSON.toJSONString(loanCapInfo));
				paramMap.put("loanCapitalExpenditure", JSON.toJSONString(loanCapExpend));
				Map<String, Object> retMap = restTemplateComponent.post("/loanOrderController/syncOrderInfo",
						JSON.toJSONString(paramMap), Map.class);
				if (retMap.get("retCode").equals(SystemConst.FAIL)) {
					LOGGER.debug("！！！！！借款申请表信息同步推送失败！！！！！！！");
					taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), orderNo,
							SystemConst.POST, SystemConst.FAIL, ServiceType.LOANAPPLY.getIndex());
				} else {
					LOGGER.debug("！！！！！借款申请表信息同步推送成功！！！！！！！");
					taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), orderNo,
							SystemConst.POST, SystemConst.SUCCESS, ServiceType.LOANAPPLY.getIndex());
				}
			} catch (Exception e) {
				LOGGER.debug("借款申请表信息同步:" + e + "\n");
				taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), orderNo,
						SystemConst.POST, SystemConst.FAIL, ServiceType.LOANAPPLY.getIndex());
			}
		}
	}

	/**
	 * TODO 资质信息更新后的向碰碰旺推送.
	 * @see com.lhhs.loan.service.transport.ProviderTransportService#credentialsSend(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	@Async
	@SuppressWarnings("unchecked")
	public void credentialsSend(Long id, Long orderCredentialsNo, String pathUrl, String type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (type.equals("add")) {
			paramMap.put("deloradd", "add");
			entityMap.put("orderCredentialsNo", String.valueOf(orderCredentialsNo));
			entityMap.put("pathUrl", pathUrl);
		} else {
			paramMap.put("deloradd", "del");
			entityMap.put("orderCredentialsNo", String.valueOf(orderCredentialsNo));
			entityMap.put("pathUrl", pathUrl);
		}
		paramMap.put("mhdCode", "04");
		paramMap.put("lhhs_order_credentials_url", entityMap);
		try {
			Map<String, Object> retMap = restTemplateComponent.post("/loanOrderController/syncOrderInfo",
					JSON.toJSONString(paramMap), Map.class);
			if (retMap.get("retCode").equals(SystemConst.FAIL)) {
				LOGGER.debug("！！！！！资质信息推送失败！！！！！！！");
				taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), pathUrl,
						SystemConst.POST, SystemConst.FAIL, ServiceType.CREDENTIALSSEND.getIndex());
			} else {
				LOGGER.debug("！！！！！资质信息推送成功！！！！！！！");
				taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), pathUrl,
						SystemConst.POST, SystemConst.SUCCESS, ServiceType.CREDENTIALSSEND.getIndex());

			}
		} catch (Exception e) {
			LOGGER.debug("资质信息推送参数:" + e + "\n");
			taskService.timedTask("/loanOrderController/syncOrderInfo", JSON.toJSONString(paramMap), pathUrl,
					SystemConst.POST, SystemConst.FAIL, ServiceType.CREDENTIALSSEND.getIndex());
		}
	}
	
	
	@Override
	public String providerSend(LoanProviderInfo loanProviderInfo) {
		loanProviderInfo.setRegion(loanProviderInfo.getCityName());
		loanProviderInfo.setProvice(loanProviderInfo.getProviderName());
		Map<String, String> param=new HashMap<String, String>();
		String providerInfo=JSON.toJSONString(loanProviderInfo);
		param.put("loanProviderInfo", providerInfo);
			LOGGER.debug("-------------------马上请求碰碰旺系统---------------------");
			LOGGER.debug("-------------------请求参数："+JSON.toJSONString(providerInfo));
			String	isSuccsess = restTemplateComponent.post("/agent/provider/update",param,String.class);
			LOGGER.debug("-------------------请求碰碰旺系统结束---------------------");
			LOGGER.debug("-------------------返回数据："+isSuccsess);
			Map<String, String> isExist = JSON.parseObject(isSuccsess, Map.class);
			String retCode=isExist.get("retCode");
			if(isExist.get("retCode")!=null && retCode.equals(SystemConst.SUCCESS) ){
				 return SystemConst.SUCCESS;
			}else{
				 return SystemConst.FAIL;
			}
		   
	}

}
