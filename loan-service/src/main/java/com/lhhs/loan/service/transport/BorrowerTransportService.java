/**
 * Project Name:loan-service
 * File Name:BorrowerTransportService.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年6月29日上午10:47:12
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport;

import java.util.Map;

import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;

/**
 * ClassName:BorrowerTransportService <br/>
 * Function: 用于贷前系统和碰碰旺系统的借款人信息同步 Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月29日 上午10:47:12 <br/>
 * 
 * @author kernel.org
 * @version
 * @since JDK 1.8
 * @see
 */
public interface BorrowerTransportService {

	////////// 贷前系统<=>碰碰旺系统对接模块接口 LiJianjun START //////////
	/**
	 * syncBorrowerInfoWithBLOBsFromRemote:同步碰碰旺系统中的借款人数据到贷前系统
	 * @author kernel.org
	 * @param map
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> syncBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs);
	
	/**
	 * syncBorrowerInfoWithBLOBsToRemote:同步贷前系统中的借款人数据到碰碰旺系统
	 * @author kernel.org
	 * @param map
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> syncBorrowerInfoWithBLOBsToRemote(String customerId);

	/**
	 * syncBorrowerInfoWithBLOBsToLocal:在将borrowerInfoWithBLOBs同步到碰碰旺系统后，再将返回的数据同步到贷前系统数据库中
	 * @author kernel.org
	 * @param loanBorrowerInfoWithBLOBs
	 * @return
	 * @since JDK 1.8
	 */
	Map<String, Object> syncBorrowerInfoWithBLOBsToLocal(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs);
	
	/**
	 * selectBorrowerInfoWithBLOBsCount:根据借款人的custId查询当前借款人是否存在.
	 * @author kernel.org
	 * @param record
	 * @return
	 * @since JDK 1.8
	 */
	boolean selectBorrowerInfoWithBLOBsCount(String custId);

	/**
	 * insertBorrowerInfoWithBLOBsToDQ:新增碰碰旺系统中的借款人信息到贷前系统.
	 * @author kernel.org
	 * @param record
	 * @return
	 * @since JDK 1.8
	 */
	int insertBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs);

	/**
	 * 
	 * updateBorrowerInfoWithBLOBsToDQ:根据custId更新碰碰旺系统中的借款人信息到贷前系统.
	 * @author kernel.org
	 * @param record
	 * @return
	 * @since JDK 1.8
	 */
	int updateBorrowerInfoWithBLOBsFromRemote(LoanBorrowerInfoWithBLOBs loanBorrowerInfoWithBLOBs);
	//////////贷前系统<=>碰碰旺系统对接模块接口 LiJianjun END //////////
}
