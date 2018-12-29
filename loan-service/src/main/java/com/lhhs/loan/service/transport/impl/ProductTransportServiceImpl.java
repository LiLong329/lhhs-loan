/**
 * Project Name:loan-service
 * File Name:ProductTransportServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年6月28日上午10:43:07
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.LoanChildProductMapper;
import com.lhhs.loan.dao.LoanCredentialsMapper;
import com.lhhs.loan.dao.LoanOrganizationMapper;
import com.lhhs.loan.dao.LoanParentProductMapper;
import com.lhhs.loan.dao.LoanProductCredentialsMapper;
import com.lhhs.loan.dao.LoanProductSupportAreasMapper;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanProductCredentials;
import com.lhhs.loan.dao.domain.LoanProductSupportAreas;
import com.lhhs.loan.service.transport.ProductTransportService;

/**
 * ClassName:ProductTransportServiceImpl <br/>
 * Function: 碰碰旺系统调用星火系统产品信息的服务层处理 <br/>
 * Reason:   <br/>
 * Date:     2017年6月28日 上午10:43:07 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class ProductTransportServiceImpl implements ProductTransportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTransportServiceImpl.class);
	@Resource
	private LoanParentProductMapper loanParentProductMapper;
	@Resource
	private LoanChildProductMapper loanChildProductMapper;
	@Resource
	private LoanCredentialsMapper loanCredentialsMapper;
	@Resource
	private LoanProductCredentialsMapper loanProductCredentialsMapper;
	@Resource
	private LoanOrganizationMapper loanOrganizationMapper;
	@Resource
	private LoanProductSupportAreasMapper loanProductSupportAreasMapper;

	/**
	 * saveFirstlyProductInfo:(保存一级产品信息). <br/>
	 * TODO(接收碰碰旺系统推送的一级产品信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param Map<String,String>
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@Override
	public Map<String, Object> saveFirstlyProductInfo(LoanParentProduct loanParentProduct) {
		Map<String, Object> map=new HashMap<String,Object>();
		int i=0;
		String productNo = loanParentProduct.getProductNo();
		boolean flag = this.selectFirstlyProductCount(productNo);
		try {
			if(flag){//修改
				i=loanParentProductMapper.updateByPrimaryKeySelective(loanParentProduct);
			}else{//插入
				i=loanParentProductMapper.insertSelective(loanParentProduct);
			}
		} catch (Exception e) {
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
			return map;
		}
		if(i==1){
			map.put(SystemConst.retCode, SystemConst.SUCCESS);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u6210\u529f\uff01");//同步成功！
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
		}
		return map;
	}

	/**
	 * selectFirstlyProductCount:(查询一级产品是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	@Override
	public boolean selectFirstlyProductCount(String productNo) {
		LoanParentProduct loanParentProduct=loanParentProductMapper.selectByPrimaryKey(productNo);
		if(loanParentProduct!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * saveSecondaryProductInfo:(保存二级产品信息). <br/>
	 * TODO(接收碰碰旺系统推送的二级产品信息,包含二级产品资质信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param LoanChildProductWithBLOBs
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> saveSecondaryProductInfo(LoanChildProductWithBLOBs loanChildProductWithBLOBs) {
		Map<String, Object> map=new HashMap<String,Object>();
		String productId = loanChildProductWithBLOBs.getProductId();
		String orgName = loanChildProductWithBLOBs.getOrgName();
		if(StringUtils.isBlank(orgName)){
			LOGGER.debug("同步失败！资金方名称缺失");
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "同步失败！资金方名称缺失");//同步失败！
			return map;
		}
		if(orgName.contains("【")){
			orgName = orgName.substring(0, orgName.lastIndexOf("【"));
		}
		LoanOrganization loanOrganization=loanOrganizationMapper.selectByOrgName(orgName);
		if(loanOrganization!=null){
			loanChildProductWithBLOBs.setFundOwner(loanOrganization.getOrgId());
		}
		List<LoanProductCredentials> productCredentials = loanChildProductWithBLOBs.getProductCredentials();
		List<LoanProductSupportAreas> productSupportAreas = loanChildProductWithBLOBs.getProductSupportAreas();
		int i=0,j=0,k=0,m=0,n=0;
		boolean flag = this.selectSecondaryProductCount(productId);
		try {
			if(flag){//修改二级产品
				i=loanChildProductMapper.updateByPrimaryKeySelective(loanChildProductWithBLOBs);
			}else{//插入二级产品
				i=loanChildProductMapper.insertSelective(loanChildProductWithBLOBs);
			}
			if(i==1){
				loanProductCredentialsMapper.deleteByProductId(productId);
				if(null!=productCredentials){
					k=productCredentials.size();
					for(LoanProductCredentials loanProductCredentials : productCredentials){
						j=j+(loanProductCredentialsMapper.insertSelective(loanProductCredentials));
					}
				}
			}
			if(j==k){
				loanProductSupportAreasMapper.deleteByProductId(productId);
				if(null!=productSupportAreas){
					n=productSupportAreas.size();
					for(LoanProductSupportAreas loanProductSupportAreas : productSupportAreas){
						loanProductSupportAreas.setId(UUID.randomUUID().toString().replace("-", ""));
						loanProductSupportAreas.setProductId(productId);
						loanProductSupportAreas.setProductName(loanChildProductWithBLOBs.getProductName());
						m=m+(loanProductSupportAreasMapper.insertSelective(loanProductSupportAreas));
					}
				}
			}
		} catch (Exception e) {
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
			return map;
		}
		if(i==1&&j==k&&m==n){
			map.put(SystemConst.retCode, SystemConst.SUCCESS);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u6210\u529f\uff01");//同步成功！
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
		}
		return map;
	}

	/**
	 * selectSecondaryProductCount:(查询二级产品是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	@Override
	public boolean selectSecondaryProductCount(String productId) {
		LoanChildProductWithBLOBs loanChildProductWithBLOBs=loanChildProductMapper.selectByPrimaryKey(productId);
		if(loanChildProductWithBLOBs!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * saveCredentialsInfo:(保存资质模板信息). <br/>
	 * TODO(接收碰碰旺系统推送的资质模板信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param LoanCredentials
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	@Override
	public Map<String, Object> saveCredentialsInfo(LoanCredentials loanCredentials) {
		Map<String, Object> map=new HashMap<String,Object>();
		int i=0;
		String credentialsId = loanCredentials.getCredentialsId();
		boolean flag = this.selectCredentialsInfoCount(credentialsId);
		try {
			if(flag){//修改
				i=loanCredentialsMapper.updateByPrimaryKeySelective(loanCredentials);
			}else{//插入
				i=loanCredentialsMapper.insertSelective(loanCredentials);
			}
		} catch (Exception e) {
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
			return map;
		}
		if(i==1){
			map.put(SystemConst.retCode, SystemConst.SUCCESS);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u6210\u529f\uff01");//同步成功！
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "\u540c\u6b65\u5931\u8d25\uff01");//同步失败！
		}
		return map;
	}

	/**
	 * selectCredentialsInfoWithBLOBsCount:(查询资质模板信息是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	@Override
	public boolean selectCredentialsInfoCount(String credentialsId) {
		LoanCredentials loanCredentials=loanCredentialsMapper.selectByPrimaryKey(credentialsId);
		if(loanCredentials!=null){
			return true;
		}else{
			return false;
		}
	}

}

