/**
 * Project Name:loan-service
 * File Name:ProductServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年7月31日上午11:27:45
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.utils.systemConst;
import com.lhhs.loan.dao.LoanChildProductMapper;
import com.lhhs.loan.dao.LoanCredentialsMapper;
import com.lhhs.loan.dao.LoanOrgSupportAreasMapper;
import com.lhhs.loan.dao.LoanParentProductMapper;
import com.lhhs.loan.dao.LoanProductCredentialsMapper;
import com.lhhs.loan.dao.LoanProductSupportAreasMapper;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanProductCredentials;
import com.lhhs.loan.dao.domain.LoanProductSupportAreas;
import com.lhhs.loan.service.ProductService;

/**
 * ClassName:ProductServiceImpl <br/>
 * Function: 产品<br/>
 * Date:     2017年7月31日 上午11:27:45 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private LoanParentProductMapper loanParentProductMapper;
	@Autowired
	private LoanChildProductMapper loanChildProductMapper;
	@Autowired
	private GetUniqueNoServiceImpl getUniqueNoServiceImpl;
	@Autowired
	private LoanProductCredentialsMapper productCredentialsMapper;
	@Autowired
	private LoanCredentialsMapper credentialsMapper; 
	@Autowired
	private LoanProductSupportAreasMapper loanProductSupportAreasMapper;
	@Autowired
	private LoanOrgSupportAreasMapper loanOrgSupportAreasMapper;
	
	@Override
	public LoanParentProduct selectParentProductByProductNo(String productNo) {
		return loanParentProductMapper.selectByPrimaryKey(productNo);
	}

	@Override
	public LoanChildProductWithBLOBs selectChildProductByProductId(String productId) {
		return loanChildProductMapper.selectByPrimaryKey(productId);
	}

	@Override
	public Integer addParentProduct(LoanParentProduct product) {
		product.setCreateDate(new Date());
		product.setProductState("1");
		product.setProductNo(getUniqueNoServiceImpl.getOrderNo());
		Integer len = loanParentProductMapper.insertSelective(product);
		return len;
	}

	@Override
	public Map<String, Object> productIfEnable(String productNo, String ifEnable) {

		Map<String, Object> result = new HashMap<String,Object>();
		LoanParentProduct product = new LoanParentProduct();
		product.setProductNo(productNo);
		product.setProductState(ifEnable);
		Integer count = loanParentProductMapper.updateByPrimaryKeySelective(product);
		if(count!=null && count>0){
			if(!"1".equals(ifEnable)){
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u7981\u7528\u6210\u529f");//禁用成功！
			}else{
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u542f\u7528\u6210\u529f");//启用成功!
			}
		}else{
			result.put(systemConst.retCode, systemConst.FAIL);
			result.put(systemConst.retCode, "\u64cd\u4f5c\u5931\u8d25");//操作失败！
		}
		return result;
	
	}

	@Override
	public LoanChildProduct getChildProductById(String productId) {
		return loanChildProductMapper.selectByPrimaryKey(productId);
	}

	@Override
	public Map<String, Object> updateChildProductByEntity(LoanChildProduct childProduct) {
		Map<String, Object> result = new HashMap<String,Object>();
		LoanChildProductWithBLOBs record = new LoanChildProductWithBLOBs();
		record.setProductId(childProduct.getProductId());
		record.setProductStatus(childProduct.getProductStatus());
		Integer count = loanChildProductMapper.updateByPrimaryKeySelective(record);
		if(count!=null && count>0){
			if(!"1".equals(childProduct.getProductStatus())){
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u7981\u7528\u6210\u529f");//禁用成功
			}else{
				result.put(systemConst.retCode, systemConst.SUCCESS);
				result.put(systemConst.retMsg, "\u542f\u7528\u6210\u529f");//启用成功
			}
		}else{
			result.put(systemConst.retCode, systemConst.FAIL);
			result.put(systemConst.retCode, "\u64cd\u4f5c\u5931\u8d25");//操作失败
		}
		return result;
	
	}

	@Override
	public Integer updateParentProduct(LoanParentProduct product) {
		return loanParentProductMapper.updateByPrimaryKeySelective(product);
	}

	@Override
	public int saveChildProductByEntity(LoanChildProductWithBLOBs childProduct) {
		String productId = childProduct.getProductId();
		childProduct.setCreateTime(new Date());
		childProduct.setProductStatus("1");// 启用
		LoanChildProductWithBLOBs bloBs = loanChildProductMapper.selectByPrimaryKey(productId);
		if(bloBs!=null){
			return -2;
		}
		int result = loanChildProductMapper.insertSelective(childProduct);
		if(result == 0 || StringUtils.isBlank(productId)){
			return result;
		}
        //删除原先关联模板
		productCredentialsMapper.deleteByproductId(productId);
		
		List<LoanCredentials> credentialsList = childProduct.getCredentialList();
		if(credentialsList!=null&&credentialsList.size()>0){
			for (LoanCredentials c : credentialsList) {
				if(StringUtils.isNotEmpty(c.getProRequired()) && StringUtils.isNotEmpty(c.getCredentialsId())){
					LoanCredentials credentials = credentialsMapper.selectByPrimaryKey(c.getCredentialsId());
					if(credentials != null){
						LoanProductCredentials pc = new LoanProductCredentials();
						pc.setProCredentialsNo(credentials.getCredentialsId());
						pc.setProductId(productId);
						pc.setProCredentialsName(credentials.getCredentialsName());
						pc.setProCrendentialsType(credentials.getCredentialsType());
						pc.setProRequired(c.getProRequired());
						pc.setProStatus("1");
						pc.setProEnglishName(credentials.getEnglishName());
						productCredentialsMapper.insertSelective(pc);
					}
				}
			}
		}
		List<LoanProductSupportAreas> productSupportAreas = childProduct.getProductSupportAreas();
		loanProductSupportAreasMapper.deleteByProductId(productId);
		if(productSupportAreas!=null&&productSupportAreas.size()>0){
			for(LoanProductSupportAreas loanProductSupportAreas : productSupportAreas){
				loanProductSupportAreas.setId(UUID.randomUUID().toString().replace("-", ""));
				loanProductSupportAreas.setProductId(productId);
				loanProductSupportAreas.setProductName(childProduct.getProductName());
				loanProductSupportAreasMapper.insertSelective(loanProductSupportAreas);
			}
		}
		return result;
	}

	@Override
	public LoanChildProductWithBLOBs getCredentialsProductById(String id) {
		List<LoanProductSupportAreas> areas = loanProductSupportAreasMapper.selectByProductId(id);
		LoanChildProductWithBLOBs bloBs = loanChildProductMapper.getCredentialsProductById(id);
		bloBs.setProductSupportAreas(areas);
		return bloBs;
	}

	@Override
	public int updateChildProduct(LoanChildProductWithBLOBs childProduct) {

		String productId = childProduct.getProductId();
		//删除原先关联模板
		productCredentialsMapper.deleteByproductId(productId);
		
		List<LoanCredentials> credentialsList = childProduct.getCredentialList();
		if(credentialsList!=null&&credentialsList.size()>0){
			for (LoanCredentials c : credentialsList) {
				if(StringUtils.isNotEmpty(c.getProRequired())){
					LoanCredentials credentials = credentialsMapper.selectByPrimaryKey(c.getCredentialsId());
					LoanProductCredentials pc = new LoanProductCredentials();
					pc.setProCredentialsNo(credentials.getCredentialsId());
					pc.setProductId(productId);
					pc.setProCredentialsName(credentials.getCredentialsName());
					pc.setProCrendentialsType(credentials.getCredentialsType());
					pc.setProRequired(c.getProRequired());
					pc.setProStatus("1");
					pc.setProEnglishName(credentials.getEnglishName());
					productCredentialsMapper.insertSelective(pc);
				}
			}
		}
		List<LoanProductSupportAreas> productSupportAreas = childProduct.getProductSupportAreas();
		loanProductSupportAreasMapper.deleteByProductId(productId);
		if(productSupportAreas!=null&&productSupportAreas.size()>0){
			for(LoanProductSupportAreas loanProductSupportAreas : productSupportAreas){
				loanProductSupportAreas.setId(UUID.randomUUID().toString().replace("-", ""));
				loanProductSupportAreas.setProductId(productId);
				loanProductSupportAreas.setProductName(childProduct.getProductName());
				loanProductSupportAreasMapper.insertSelective(loanProductSupportAreas);
			}
		}
		return loanChildProductMapper.updateByPrimaryKeySelective(childProduct);
	
	}

	@Override
	public List<LoanOrgSupportAreas> getOrgSupportProvince(LoanOrgSupportAreas orgSupportAreas) {
		return loanOrgSupportAreasMapper.getOrgSupportProvince(orgSupportAreas);
	}

	@Override
	public List<LoanOrgSupportAreas> getOrgSupportCity(LoanOrgSupportAreas orgSupportAreas) {
		return loanOrgSupportAreasMapper.getOrgSupportCity(orgSupportAreas);
	}

	@Override
	public List<LoanParentProduct> queryProductByStatus(String productType) {
		return loanParentProductMapper.queryProductByStatus(productType);
	}
}