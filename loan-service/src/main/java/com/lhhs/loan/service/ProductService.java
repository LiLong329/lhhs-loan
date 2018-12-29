/**
 * Project Name:loan-service
 * File Name:ProductService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年7月31日上午11:05:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanParentProduct;

/**
 * ClassName:ProductService <br/>
 * Function: 产品 <br/>
 * Date:     2017年7月31日 上午11:05:02 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface ProductService{
	
	LoanParentProduct selectParentProductByProductNo(String productNo);
	
	LoanChildProductWithBLOBs selectChildProductByProductId(String productId);

	Integer addParentProduct(LoanParentProduct product);
	
	Integer updateParentProduct(LoanParentProduct product);

	/**
	 * 产品禁用、启用
	 * @param productNo 产品编号
	 * @param ifEnable 标识
	 * @return
	 */
	public Map<String, Object> productIfEnable(String productNo, String ifEnable);

	LoanChildProduct getChildProductById(String productId);
	/**
	 * 二级产品禁用、启用
	 * @param childProduct
	 * @return
	 */
	public Map<String, Object> updateChildProductByEntity(LoanChildProduct childProduct);

	int saveChildProductByEntity(LoanChildProductWithBLOBs childProduct);

	LoanChildProductWithBLOBs getCredentialsProductById(String id);

	int updateChildProduct(LoanChildProductWithBLOBs childProduct);

	List<LoanOrgSupportAreas> getOrgSupportProvince(LoanOrgSupportAreas orgSupportAreas);

	List<LoanOrgSupportAreas> getOrgSupportCity(LoanOrgSupportAreas orgSupportAreas);
	
	List<LoanParentProduct> queryProductByStatus(String productType);
	
}