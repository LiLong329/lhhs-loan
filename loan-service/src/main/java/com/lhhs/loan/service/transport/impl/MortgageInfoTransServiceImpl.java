/**
 * Project Name:loan-service
 * File Name:MortgageInfoTransServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年6月29日下午2:25:17
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.zxing.common.StringUtils;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.enums.ServiceType;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCarInfoMapper;
import com.lhhs.loan.dao.LoanHouseInfoMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanOrderCarExtendMapper;
import com.lhhs.loan.dao.LoanOrderCredentialsMapper;
import com.lhhs.loan.dao.LoanOrderHouseExtendMapper;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCredentials;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;
import com.lhhs.loan.service.CarInfoService;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.HouseInfoService;
import com.lhhs.loan.service.MortgageInfoService;
import com.lhhs.loan.service.scheduler.TimingTaskService;
import com.lhhs.loan.service.transport.MortgageInfoTransService;

/**
 * ClassName:MortgageInfoTransServiceImpl <br/>
 * Function: 与碰碰旺系统通信同步客户信息中的抵押物信息<br/>
 * Reason: <br/>
 * Date: 2017年6月29日 下午2:25:17 <br/>
 * 
 * @author xiangfeng
 * @version
 * @since JDK 1.8
 * @see
 */
@Service
@SuppressWarnings("all")
public class MortgageInfoTransServiceImpl implements MortgageInfoTransService {
	private static final Logger LOGGER = Logger.getLogger(MortgageInfoTransServiceImpl.class);

	@Autowired
	private RestTemplateComponent restTemplateComponent;
	@Autowired
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper;
	@Autowired
	private LoanHouseInfoMapper loanHouseInfoMapper;
	@Autowired
	private LoanCarInfoMapper loanCarInfoMapper;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private LoanOrderHouseExtendMapper loanOrderHouseExtendMapper;
	@Autowired
	private LoanOrderCarExtendMapper loanOrderCarExtendMapper;
	@Autowired
	private LoanOrderCredentialsMapper loanOrderCredentialsMapper;
	@Autowired
	private CredentialsInfoService credentialsInfoService;
	@Autowired
	private HouseInfoService houseInfoService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private TimingTaskService timingTaskService;
	@Autowired
	private MortgageInfoService mortgageInfoService;

	
	/**
	 * 从碰碰旺后台系统同步更新借款人抵押物信息
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#saveMortgageInfo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveMortgageInfoFromRemote(String mortgageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<LoanHouseInfo> houseLists = new ArrayList<>();
		List<LoanCarInfo> carLists = new ArrayList<>();
		if (StrUtils.isNullOrEmpty(mortgageInfo)) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "参数不能为空");
			return result;
		}
		Map<?, ?> param = JSON.parseObject(mortgageInfo, Map.class);
		String custId = (String) param.get("custId");
		String houseInfoLists = (String) param.get("houseInfoLists");
		String carInfoLists = (String) param.get("carInfoLists");
		if (!StrUtils.isNullOrEmpty(custId)) {
			LoanBorrowerInfoWithBLOBs obj = loanBorrowerInfoMapper.selectByPrimaryKey(custId);
			if (obj != null) {
				if (!StrUtils.isNullOrEmpty(houseInfoLists)) {
					houseLists = JSON.parseArray(houseInfoLists, LoanHouseInfo.class);
				}
				if (!StrUtils.isNullOrEmpty(carInfoLists)) {
					carLists = JSON.parseArray(carInfoLists, LoanCarInfo.class);
				}
				int houseNum = houseInfoService.saveHouseInfo(houseLists, custId,obj.getCustomerId());
				int carNum = carInfoService.saveCarInfo(carLists, custId,obj.getCustomerId());
				if (houseNum == houseLists.size() && carNum == carLists.size()) {
					result.put(SystemConst.retCode, SystemConst.SUCCESS);
					result.put(SystemConst.retMsg, "信息同步成功");
				} else {
					result.put(SystemConst.retCode, SystemConst.FAIL);
					result.put(SystemConst.retMsg, "同步失败，需要重新调试接口");
					LOGGER.debug("借款人抵押物信息同步失败，需要重新调试接口");
				}
			} else {
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "该客户信息不属于小贷系统");
			}
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "参数不合法，custId不能为空");
		}
		return result;
	}

	/**
	 * 同步客户管理中借款人抵押物到碰碰旺系统
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#saveMortgageInfoToRemote(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveMortgageInfoToRemote(String customerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> ret = new HashMap<String, Object>();
		LoanBorrowerInfoWithBLOBs borrowerInfo = loanBorrowerInfoMapper.selectByCustomerId(customerId);
		List<LoanHouseInfo> houseList = loanHouseInfoMapper.selectHouseInfoList(customerId);
		List<LoanCarInfo> carList = loanCarInfoMapper.selectCarInfoList(customerId);
		param.put("custId", borrowerInfo.getCustId());
		param.put("houseInfoLists", JSON.toJSONString(houseList));
		param.put("carInfoLists", JSON.toJSONString(carList));
		try {
			ret = restTemplateComponent.post("borrowerInfo/collateralInfo", JSON.toJSONString(param), Map.class);
			if (SystemConst.SUCCESS.equals((ret.get(SystemConst.retCode)))) {
				timingTaskService.timedTask("borrowerInfo/collateralInfo", JSON.toJSONString(param), customerId, SystemConst.POST, SystemConst.SUCCESS, ServiceType.CUSTMORTGAGEINFO.getIndex());
				LOGGER.error("保存借款人抵押物信息到碰碰旺系统成功;\n返回参数为：" + JSON.toJSONString(ret));
			} else {
				timingTaskService.timedTask("borrowerInfo/collateralInfo", JSON.toJSONString(param), customerId, SystemConst.POST, SystemConst.FAIL, ServiceType.CUSTMORTGAGEINFO.getIndex());
				LOGGER.error("远程系统返回的数据错误，需要重新调试接口");
			}
		} catch (Exception e) {
			timingTaskService.timedTask("borrowerInfo/collateralInfo", JSON.toJSONString(param), customerId, SystemConst.POST, SystemConst.FAIL, ServiceType.CUSTMORTGAGEINFO.getIndex());
			LOGGER.error("保存借款人抵押物信息到碰碰旺系统失败;\n请求参数为：" + JSON.toJSONString(param));
			LOGGER.error(e);
		}
		return ret;
	}

	/**
	 * 下户审核或者财务放款审核之后，首先保存数据到碰碰旺，<br/>
	 * 如果保存成功，调用碰碰旺拉取接口，然后保存到自己库<br/>
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#saveBorrowerAllInfoToRemote(java.lang.String)
	 */
	@Async
	@Override
	public void saveBorrowerAllInfoToRemote(String orderNo,LoanEmp loanEmp) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> borrowerInfo = this.findBorrowerInfoExtendByOrderNo(orderNo);
		List<Map<String, Object>> houseInfoLists = this.findHouseExtendListMapByOrderNo(orderNo);
		List<Map<String, Object>> carInfoLists = this.findCarExtendListMapByOrderNo(orderNo);
		String custId = (String) borrowerInfo.get("custId");
		if(!StrUtils.isNullOrEmpty(custId)){
			param.put("custId", custId);
			param.put("borrowerInfo", JSON.toJSONString(borrowerInfo));
			param.put("houseInfoLists", JSON.toJSONString(houseInfoLists));
			param.put("carInfoLists", JSON.toJSONString(carInfoLists));
			try{
				ret =restTemplateComponent.post("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), Map.class);
				LOGGER.debug("返回数据：\n"+JSON.toJSONString(ret));
				if(SystemConst.SUCCESS.equals(ret.get(SystemConst.retCode))){
					//验证是否有跑批数据需要处理
					timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.SUCCESS, ServiceType.BORROWERINFOSEND.getIndex());
					custId = (String) ret.get("custId");
					if(!StrUtils.isNullOrEmpty(custId)){
						this.getBorrowerInfoFromRemote(custId,loanEmp,orderNo);
					}else{
						LOGGER.error("保存借款人信息后碰碰旺系统返回的数据错误;\n返回参数为：" + JSON.toJSONString(ret));
					}
				}else{
					//保存跑批数据
					timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.BORROWERINFOSEND.getIndex());
				}
			}catch(Exception e){
				LOGGER.error("保存借款人信息到碰碰旺系统失败;\n请求参数为：" + JSON.toJSONString(param));
				LOGGER.error(e);
				//保存跑批数据
				timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.BORROWERINFOSEND.getIndex());
			}
		}else{
			LOGGER.error("身份证号为空，无法保存借款人信息到碰碰旺系统;\n借款人基本信息为：" + JSON.toJSONString(borrowerInfo));
		}
	}

	/**
	 * 从碰碰旺系统获取借款人所有信息
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#getBorrowerInfoFromRemote(java.lang.String)
	 */
	public Map<String, Object> getBorrowerInfoFromRemote(String custId,LoanEmp loanEmp,String orderNo){
		Map<String, Object> ret = new HashMap<String, Object>();
		Integer empId = loanEmp.getLeEmpId();
		try{
			ret = restTemplateComponent.get("borrowerInfo/findBorrowerInfo/"+custId, Map.class);
			ret.put("orderNo", orderNo);
			boolean flag = mortgageInfoService.saveBorrowerAllInfo(ret,loanEmp);
			if(!flag){
				//保存跑批数据
				timingTaskService.timedTask("borrowerInfo/findBorrowerInfo/"+custId, null, custId+"|"+orderNo+"|"+empId, SystemConst.GET, SystemConst.FAIL, ServiceType.GETBORROWERINFO.getIndex());
			}else{
				//验证是否有跑批数据需要处理
				timingTaskService.timedTask("borrowerInfo/findBorrowerInfo/"+custId, null, custId+"|"+orderNo+"|"+empId, SystemConst.GET, SystemConst.SUCCESS, ServiceType.GETBORROWERINFO.getIndex());
			}
		}catch(Exception e){
			LOGGER.error("从碰碰旺系统拉取借款人信息失败;\n请求参数为：" + JSON.toJSONString(custId));
			LOGGER.error(e);
			//保存跑批数据
			timingTaskService.timedTask("borrowerInfo/findBorrowerInfo/"+custId, null, custId+"|"+orderNo+"|"+empId, SystemConst.GET, SystemConst.FAIL, ServiceType.GETBORROWERINFO.getIndex());
		}
		return ret;
	}
	
	/**
	 * 根据订单编号查询订单借款人基本信息
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#findBorrowerInfoExtendByOrderNo(java.lang.String)
	 */
	@Override
	public Map<String, Object> findBorrowerInfoExtendByOrderNo(String orderNo) {
		return loanOrderBorrowerExtendMapper.findBorrowerInfoExtendByOrderNo(orderNo);
	}

	/**
	 * 根据订单编号查询订单房产抵押物信息 
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#findHouseExtendListByOrderNo(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findHouseExtendListMapByOrderNo(String orderNo) {
		return loanOrderHouseExtendMapper.findHouseExtendListMapByOrderNo(orderNo);
	}

	/**
	 * 根据订单编号查询订单车产抵押物信息
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#findCarExtendListByOrderNo(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findCarExtendListMapByOrderNo(String orderNo) {
		return loanOrderCarExtendMapper.findCarExtendListMapByOrderNo(orderNo);
	}

	/**
	 *先让碰碰旺复制资质，然后返回所有信息，存入我们系统<br/>
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#changeOrderCredentials(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> changeOrderCredentials(String orderNo, String productId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if(!StrUtils.isNullOrEmpty(orderNo)&&!StrUtils.isNullOrEmpty(productId)){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderNo", orderNo);
			param.put("productId", productId);
			List<LoanOrderCredentials> lists = loanOrderCredentialsMapper.findOrderCredentialsInfoLists(param);
			if(lists == null || lists.size() == 0){
				boolean flag=credentialsInfoService.copyProductCredentialsToOrderCredentials(orderNo, productId);
				if(flag){
					ret.clear();
					ret.put(SystemConst.retCode, SystemConst.SUCCESS);
					ret.put(SystemConst.retMsg, "订单资质生成成功");
				}else{
					ret.clear();
					ret.put(SystemConst.retCode, SystemConst.FAIL);
					ret.put(SystemConst.retMsg, "资质生成失败");
				}
			}else{
				ret.clear();
				ret.put(SystemConst.retCode, SystemConst.SUCCESS);
				ret.put(SystemConst.retMsg, "订单资质已经存在");
			}
		}else{
			ret.clear();
			ret.put(SystemConst.retCode, SystemConst.FAIL);
			ret.put(SystemConst.retMsg, "订单编号或二级产品编号不能为空");
		}
		return ret;
	}

	@Override
	public void saveBorrowerAllInfoToCustomerInfo(String orderNo, LoanEmp loanEmp, String isLoanAdd,AuditParamVo auditParamVo) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		param.put("borrowerInfo", JSON.toJSONString(loanOrderBorrowerExtendMapper.selectOrderBorrowerExtendBLOBsToMap(orderNo)));
		param.put("houseInfoLists", JSON.toJSONString(loanOrderHouseExtendMapper.selectOrderHouseExtendToMap(orderNo)));
		param.put("carInfoLists", JSON.toJSONString(loanOrderCarExtendMapper.selectOrderCarExtendToMap(orderNo)));
//		param.put("auditParamVo", auditParamVo);//报单信息-客户经理 and 归属事业部或者组
		boolean flag = mortgageInfoService.saveBorrowerAllInfo(param,loanEmp);
		if(!"Y".equals(isLoanAdd) && flag){//保存本地借款人客户信息成功后，推送给碰碰旺
			Map<String, Object> borrowerInfo = this.findBorrowerInfoExtendByOrderNo(orderNo);
			List<Map<String, Object>> houseInfoLists = this.findHouseExtendListMapByOrderNo(orderNo);
			List<Map<String, Object>> carInfoLists = this.findCarExtendListMapByOrderNo(orderNo);
			String custId = (String) borrowerInfo.get("custId");
			if(!StrUtils.isNullOrEmpty(custId)){
				param.put("custId", custId);
				param.put("borrowerInfo", JSON.toJSONString(borrowerInfo));
				param.put("houseInfoLists", JSON.toJSONString(houseInfoLists));
				param.put("carInfoLists", JSON.toJSONString(carInfoLists));
				try{
					ret =restTemplateComponent.post("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), Map.class);
					LOGGER.debug("返回数据：\n"+JSON.toJSONString(ret));
					if(SystemConst.SUCCESS.equals(ret.get(SystemConst.retCode))){
						//验证是否有跑批数据需要处理
						timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.SUCCESS, ServiceType.BORROWERINFOSEND.getIndex());
						custId = (String) ret.get("custId");
					}else{
						//保存跑批数据
						timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.BORROWERINFOSEND.getIndex());
					}
				}catch(Exception e){
					LOGGER.error("保存借款人信息到碰碰旺系统失败;\n请求参数为：" + JSON.toJSONString(param));
					LOGGER.error(e);
					//保存跑批数据
					timingTaskService.timedTask("borrowerInfo/syncBorrowerInfo", JSON.toJSONString(param), orderNo, SystemConst.POST, SystemConst.FAIL, ServiceType.BORROWERINFOSEND.getIndex());
				}
			}else{
				LOGGER.error("身份证号为空，无法保存借款人信息到碰碰旺系统;\n借款人基本信息为：" + JSON.toJSONString(borrowerInfo));
			}
		}
	}
}
