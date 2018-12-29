/**
 * Project Name:loan-web
 * File Name:AccountsSubjectInfoController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年9月13日上午10:40:34
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.service.AccountsSubjectInfoService;

/**
 * ClassName:AccountsSubjectInfoController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年9月13日 上午10:40:34 <br/>
 * @author   lenovo
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/accountsSubjectInfo")
@SuppressWarnings("all")
public class AccountsSubjectInfoController {
	@Autowired
	private AccountsSubjectInfoService accountsSubjectInfoService;
	/**
	 * ajaxSubjectInfo:(异步查询科目信息). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/subjectList")
	@ResponseBody
	public List<LoanAccountsSubjectInfo> subjectList(HttpServletRequest request, LoanAccountsSubjectInfo parm){
		List<LoanAccountsSubjectInfo> list=accountsSubjectInfoService.queryList(parm);
		return list;
	}

}

