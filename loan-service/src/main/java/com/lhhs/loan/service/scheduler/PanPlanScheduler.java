/**
 * Project Name:loan-service
 * File Name:PanPlanScheduler.java
 * Package Name:com.lhhs.loan.service.scheduler
 * Date:2017年9月27日上午10:17:00
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler;
/**
 * ClassName:PanPlanScheduler <br/>
 * Function: 还款计划跑批，更新订单状态和还款计划 <br/>
 * Date:     2017年9月27日 上午10:17:00 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface PanPlanScheduler {
	public void orderStatusTask();
	public void payPlanNotice();
}
