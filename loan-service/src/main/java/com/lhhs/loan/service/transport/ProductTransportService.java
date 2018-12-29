/**
 * Project Name:loan-service
 * File Name:ProductTransportService.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年6月28日上午10:42:26
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanChildProductWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCredentials;
import com.lhhs.loan.dao.domain.LoanParentProduct;

/**
 * ClassName:ProductTransportService <br/>
 * Function: 星火系统与碰碰旺系统产品信息的接口处理层 <br/>
 * Reason:   <br/>
 * Date:     2017年6月28日 上午10:42:26 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface ProductTransportService {
	
	/**
	 * saveFirstlyProductInfo:(保存一级产品信息). <br/>
	 * TODO(接收碰碰旺系统推送的一级产品信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param LoanParentProduct
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	public Map<String,Object> saveFirstlyProductInfo(LoanParentProduct loanParentProduct);
	
	/**
	 * selectFirstlyProductCount:(查询一级产品是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	public boolean selectFirstlyProductCount(String productNo);
	
	/**
	 * saveSecondaryProductInfo:(保存二级产品信息). <br/>
	 * TODO(接收碰碰旺系统推送的二级产品信息,包含二级产品资质信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param LoanChildProductWithBLOBs
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	public Map<String,Object> saveSecondaryProductInfo(LoanChildProductWithBLOBs loanChildProductWithBLOBs);
	
	/**
	 * selectSecondaryProductCount:(查询二级产品是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	public boolean selectSecondaryProductCount(String productId);
	
	/**
	 * saveCredentialsInfo:(保存资质模板信息). <br/>
	 * TODO(接收碰碰旺系统推送的资质模板信息).<br/>
	 * TODO(存在即修改，不存在即插入).<br/>
	 * @author chenyinhui
	 * @param LoanCredentials
	 * @return Map<String,Object>
	 * @since JDK 1.8
	 */
	public Map<String,Object> saveCredentialsInfo(LoanCredentials loanCredentials);
	
	/**
	 * selectCredentialsInfoCount:(查询资质模板信息是否存在). <br/>
	 * TODO(存在返回true，不存在返回false).<br/>
	 * @author chenyinhui
	 * @param String
	 * @return boolean
	 * @since JDK 1.8
	 */
	public boolean selectCredentialsInfoCount(String credentialsId);
	
}

