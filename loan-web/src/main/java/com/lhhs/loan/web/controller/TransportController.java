/**
 * Project Name:loan-web
 * File Name:TransportController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年6月28日上午9:28:00
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.service.transport.BorrowerTransportService;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.service.transport.MortgageInfoTransService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.loan.service.transport.OrganizationTransService;
import com.lhhs.loan.service.transport.ProductTransportService;
import com.lhhs.loan.service.transport.ProviderTransportService;


/**
 * ClassName:TransportController <br/>
 * Function: 与碰碰旺系统通信接口类 <br/>
 * Reason:   <br/>
 * Date:     2017年6月28日 上午9:28:00 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@RestController
@RequestMapping("/transport")
public class TransportController {
	private static final Logger LOGGER = Logger.getLogger(TransportController.class);
	
	@Resource
	private BorrowerTransportService borrowerTransportService;
	@Autowired
	private ProviderTransportService providerTransportService;
	@Autowired
	private OrganizationTransService organizationTransService;
	@Autowired
	private MortgageInfoTransService mortgageInfoTransService;
	@Autowired
	private ProductTransportService productTransportService;
	@Autowired
	private OrderInfoService orderInfoService;
	/**
	 * 
	 * insertOrderInfo:(报单数据同步方法入口). <br/>
	 * @author zhanghui
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/insertOrderInfo")
	@ResponseBody
	public Map<String,Object>  insertOrderInfo(@RequestBody Map<String,String> params){
		Map<String,Object>  result = new HashMap<String, Object>();
		try {
			result =orderInfoService.saveOrderInfo(params);
		} catch (Exception e) {
			LOGGER.error("Exception"+e);
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "报单同步失败");
			return result;
		}
		return result;
	}
	
	@RequestMapping("/borrowerInfo")
	@ResponseBody
	public Map<String, Object> syncLoanBorrowerInfo(HttpServletRequest request, @RequestBody String orderBorrowerExtendWithBLOBs){
		Map<String, Object> result = new HashMap<String, Object>();
		LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs = JSON.parseObject(orderBorrowerExtendWithBLOBs, LoanBorrowerInfoWithBLOBs.class);
		result = borrowerTransportService.syncBorrowerInfoWithBLOBsFromRemote(loanBorrowerInfoWithBLOBs);
		return result;
	}

	/**
	 * 经纪人信息同步入口
	 * @param providerInfo
	 * @return
	 */
	@RequestMapping(value="/providerTransport",method = RequestMethod.POST)
	public String providerTransport(@RequestBody LoanProviderInfo loanProviderInfo){
		    return providerTransportService.providerInfoUpdate(loanProviderInfo);
	   }

	/**
	 * 
	 * saveOrgInfo:同步组织机构信息<br/>
	 *
	 * @author xiangfeng
	 * @param org
	 * @param supportCityList
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/orgInfo")
	public Map<String,Object> saveOrgInfo(@RequestBody String org){
		LOGGER.debug("组织机构信息同步参数："+org+"\n");
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			map=organizationTransService.saveOrganizationInfo(org);
		}catch(Exception e) {
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "同步失败，需要重新调试接口");//同步失败，需要重新调试接口
		}
		return map;
	}
	/**
	 * 
	 * saveMortgageInfo:同步客户管理中借款人的抵押物信息 <br/>
	 *
	 * @author xiangfeng
	 * @param custId  借款人编号
	 * @param houseInfoLists  房抵押
	 * @param carInfoLists  车抵押
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/mortgageInfo")
	public Map<String,Object> saveMortgageInfoFromRemote(@RequestBody String mortgageInfo){
		LOGGER.debug("接受数据：\n"+mortgageInfo);
		return mortgageInfoTransService.saveMortgageInfoFromRemote(mortgageInfo);
	}
	
	/**
	 * saveFirstlyProductInfo:(保存一级产品信息). <br/>
	 * (接收碰碰旺系统推送的一级产品信息).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@RequestMapping("/saveFirstlyProductInfo")
	public Map<String,Object> saveFirstlyProductInfo(HttpServletRequest request,@RequestBody String parentProductInfo){
		Map<String, Object> map=new HashMap<String,Object>();
		if(StrUtils.isNullOrEmpty(parentProductInfo)){
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");//参数不合法！
			return map;
		}
		LoanParentProduct loanParentProduct=JSON.parseObject(parentProductInfo, LoanParentProduct.class);
		if(loanParentProduct==null){
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");//参数不合法！
			return map;
		}
		return productTransportService.saveFirstlyProductInfo(loanParentProduct);
	}
	
	/**
	 * saveSecondaryProductInfo:(保存二级产品信息). <br/>
	 * (接收碰碰旺系统推送的二级产品信息，包含二级产品资质).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@RequestMapping("/saveSecondaryProductInfo")
	public Map<String,Object> saveSecondaryProductInfo(HttpServletRequest request,@RequestBody String childProductInfo){
		Map<String, Object> map=new HashMap<String,Object>();
		if(StrUtils.isNullOrEmpty(childProductInfo)){
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");//参数不合法！
			return map;
		}
		LoanChildProductWithBLOBs loanChildProduct=JSON.parseObject(childProductInfo, LoanChildProductWithBLOBs.class);
		if(loanChildProduct==null){
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");//参数不合法！
			return map;
		}
		return productTransportService.saveSecondaryProductInfo(loanChildProduct);
	}
	
	/**
	 * saveCredentialsInfo:(保存资质模板信息). <br/>
	 * (接收碰碰旺系统推送的资质模板信息).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@RequestMapping("/saveCredentialsInfo")
	public Map<String,Object> saveCredentialsInfo(HttpServletRequest request,@RequestBody LoanCredentials loanCredentials){
		Map<String, Object> map=new HashMap<String,Object>();
		if(loanCredentials==null){
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5\uff01");//参数不合法！
			return map;
		}
		return productTransportService.saveCredentialsInfo(loanCredentials);
	}
}

