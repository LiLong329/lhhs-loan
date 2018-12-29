/**
 * Project Name:loan-service
 * File Name:HouseInfoService.java
 * Package Name:com.lhhs.loan.service
 * Date:2017年6月29日下午2:50:35
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.SipAccount;

public interface SipAccountService extends BaseService<SipAccount>{

	SipAccount getByUserId(String userId);
}

