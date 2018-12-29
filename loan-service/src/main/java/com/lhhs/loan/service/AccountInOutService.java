/**
 * Project Name:loan-service
 * File Name:AccountInOutService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月1日下午4:57:16
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-service
 * File Name:AccountInOutService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年8月1日下午4:57:16
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.ActComment;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInOutInfo;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanDictionary;
import com.lhhs.loan.dao.domain.LoanOrderInfo;

/**
 * ClassName:AccountInOutService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月1日 下午4:57:16 <br/>
 * @author   zhanghui
 * @version
 * @since    JDK 1.8
 * @see
 */
/**
 * ClassName: AccountInOutService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年8月1日 下午4:57:16 <br/>
 *
 * @author zhanghui
 * @version 
 * @since JDK 1.8
 */

public interface AccountInOutService {
	
	/**
	 * 
	 * queryDepositRecord:(查询充值/提现列表). <br/>
	 * @author zhanghui
	 * @param params
	 * @param page
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> queryTransRecord(Map<String,Object> params,Page page);
	
	/**
	 * insertTransApply:(保存交易申请). <br/>
	 * @author zhanghui
	 * @param loanAccountInOutInfo
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,Object>  insertTransApply(LoanAccountInOutInfo loanAccountInOutInfo,String[] imageUrl)throws Exception;
	
	/**
	 * queryCustomerTypeList:(查询客户类型). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
    List<LoanDictionary>  queryCustomerTypeList();
    
    /**
	 * queryCustomerTypeList:(查询客户性质). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
    List<LoanDictionary>  queryCustomerNatureList();
    
    /**
     * 根据手机号或机构ID、客户类型查询账户信息
     * @param mobileOrOwnerId
     * @return
     */
	Map<String,Object> getAccountsByMobileOrOwnerId(String mobileOrOwnerId,String customerType,String transType);
	
	/**
	 * 
	 * queryCardByCompany:(根据公司ID查询银行卡). <br/>
	 * @author zhanghui
	 * @param companyId
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanAccountCard>  queryCardByCompany(String companyId, String kind);
	/**
	 * queryAllBank:(查询所有银行). <br/>
	 * @author zhanghui
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanBank>  queryAllBank();
	
	/**
	 * queryDetailByTransNo:(查询交易详情). <br/>
	 * @author zhanghui
	 * @param transNo
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object>  queryDetailByTransNo(String transNo);
	/**
	 * saveTransApprove:(充值、提现审核). <br/>
	 * @author zhanghui
	 * @param actComment
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,Object>  saveTransApprove(ActComment actComment,String transType) throws Exception;
	
	/**
	 * queryTransTotal:(查询交易总数和总金额). <br/>
	 * @author zhanghui
	 * @param actComment
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,Object>  queryTransTotal(Map<String, Object> params);
	
	/**
	 * 列表属性转换
	 * convertProperties:(这里用一句话描述这个方法的作用). <br/>
	 * @author zhanghui
	 * @param param
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>>  convertProperties(List<Map<String, Object>> param);

	Page queryDepositApplyList(LoanAccountInOutInfo entity);

	Map<String,BigDecimal> queryTotalAmount(LoanAccountInOutInfo entity);
}

