/**
 * Project Name:loan-service
 * File Name:LoanTaskListServiceImpl.java
 * Package Name:com.lhhs.loan.service.scheduler.impl
 * Date:2017年10月25日下午5:58:09
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.scheduler.impl;

import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.dao.LoanTaskListMapper;
import com.lhhs.loan.dao.domain.LoanTaskList;
import com.lhhs.loan.service.scheduler.LoanTaskListService;

/**
 * 定时任务列表
 */
@Service
public class LoanTaskListServiceImpl extends CrudService<LoanTaskListMapper, LoanTaskList> implements LoanTaskListService {

}

