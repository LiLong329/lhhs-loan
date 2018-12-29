/**
 * Project Name:loan-service
 * File Name:TimeingTask.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年7月7日上午9:24:23
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler;
/**
 * ClassName:TimeingTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年7月7日 上午9:24:23 <br/>
 * @author   Administrator
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface TimingTaskService {
	
	/**
	 * 
	 * providerUpdate:(对接碰碰旺拉取报单人信息). <br/>
	 * @author Administrator
	 * @param providerNo
	 * @since JDK 1.8
	 */
    public void providerUpdate(String providerNo);
	
	/**
	 * 
	 * timedTask:(定时任务ADD或者UPDATE). <br/>
	 * @author Administrator
	 * @param url
	 * @param parameter
	 * @param taskWorkId
	 * @param taskMethed
	 * @param taskState
	 * @param requestType
	 * @since JDK 1.8
	 */
	 public void timedTask(String url,String parameter,String taskWorkId,String taskMethed,String taskState,String requestType);
	 
	 /**
	  * 
	  * timedRunTask:(跑批任务-处理通讯失败数据). <br/>
	  * @author Administrator
	  * @since JDK 1.8
	  */
	 public void timedRunTask();
	 
	 /**
	  * getProviderList:(从碰碰旺拉取所有经纪人信息). <br/>
	  * @author zhanghui
	  * @since JDK 1.8
	  */
	 public void getProviderList();
	 

}

