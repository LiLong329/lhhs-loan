/**
 * Project Name:loan-web
 * File Name:UnionCompanyController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年8月5日上午10:46:38
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.UnionCompanyService;

/**
 * ClassName:UnionCompanyController <br/>
 * Function: 集团 公司web接口<br/>
 * Date:     2017年8月5日 上午10:46:38 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/unionCompany")
public class UnionCompanyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnionCompanyController.class);
	
	@Autowired
	private UnionCompanyService unionCompanyService;
	@Reference
	private UnionCompanyApi unionCompanyApi;
	
	/**
	 * companyList:公司管理列表 <br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/companyList")
	public ModelAndView companyList(LoanUnionCompany company){
		ModelAndView mav = new ModelAndView("company/companyList");
		//TODO 添加查询条件
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		CommonUtils.fillBumpCompany(unionCompany);
		unionCompany.setLgCompanyId(null);
		mav.addObject("page", unionCompanyApi.queryListPage(unionCompany));
		return mav;
	}
	
	/**
	 * ajaxQueryList:
	 * 公司管理列表  异步搜索查询及分页查询<br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanUnionCompany company){
		ModelAndView mav = new ModelAndView("company/_companyTemp");
		//TODO 添加查询条件
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		CommonUtils.fillBumpCompany(unionCompany);
		unionCompany.setLgCompanyId(null);
		unionCompany.setPageIndex(company.getPage().getPageIndex());
		unionCompany.setPageSize(company.getPageSize());
		mav.addObject("page", unionCompanyApi.queryListPage(unionCompany));
		return mav;
	}
	
	/**
	 * unionCompanyDetails:跳转到集团或者公司详情页面 <br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/unionCompanyDetails")
	public ModelAndView unionCompanyDetails(LoanUnionCompany company){
		ModelAndView mav = new ModelAndView("company/unionCompanyDetails");
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		mav.addObject("com", unionCompanyApi.get(unionCompany));
		return mav;
	}
	
	/**
	 * addPage:跳转到新增集团或者公司页面 <br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/addPage")
	public ModelAndView addPage(LoanUnionCompany company){
		ModelAndView mav = new ModelAndView("company/unionCompanyAdd");
//		company.setLevel("1");//查集团
//		mav.addObject("comList", unionCompanyService.queryList(company));
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		unionCompany.setCompanyId("");
		unionCompany.setParentCompanyId("0");
		mav.addObject("comList", unionCompanyApi.queryList(unionCompany));
		return mav;
	}
	
	/**
	 * ajaxCompanyName:验证公司名是否重复 <br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/ajaxCompanyName")
	public Map<String,Object> ajaxCompanyName(LoanUnionCompany company){
		Map<String,Object> ret = new HashMap<String,Object>();
		if(StrUtils.isNullOrEmpty(company.getCompanyName())){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
		}
		int count = unionCompanyService.queryCount(company);
		if(count > 0){
			ret.put(SystemConst.retCode, SystemConst.FAIL);
		}else{
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}
		return ret;
	}
	/**
	 * saveCompany:保存或更新公司信息 <br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@ResponseBody
	@RequestMapping("/saveCompany")
	public Map<String,String> saveCompany(LoanUnionCompany company){
		Map<String,String> ret = new HashMap<String,String>();
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		if(StrUtils.isNullOrEmpty(company.getCompanyId())){
			company.setCreateUser(user.getUserId().toString());
			company.setCreateTime(new Date());
		}
		company.setLastUser(user.getUserId().toString());
		company.setLastModifyTime(new Date());
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		if(StrUtils.isNullOrEmpty(company.getCompanyId())){
			unionCompany.setOrgCategory("1");
			unionCompany.setOrgType("2");
			unionCompany.setBusinessType("0");
		}
		int code =  unionCompanyApi.saveBySpark(unionCompany);
		if(code == 1){
			ret.put(SystemConst.retCode, SystemConst.SUCCESS);
		}else{
			ret.put(SystemConst.retCode, SystemConst.FAIL);
		}
		return ret;
		
	}
	
	/**
	 * updatePage:跳转到修改集团或者公司页面<br/>
	 *
	 * @author xiangfeng
	 * @param company
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/updatePage")
	public ModelAndView updatePage(LoanUnionCompany company){
		ModelAndView mav = new ModelAndView("company/unionCompanyEdit");
//		mav.addObject("c", unionCompanyService.get(company));
		UnionCompany unionCompany=new UnionCompany();
		BeanUtils.copyProperties(company,unionCompany);
		mav.addObject("c", unionCompanyApi.get(unionCompany));
		//TODO 添加查询条件
//		company.setLevel("1");//查集团
//		mav.addObject("comList", unionCompanyService.queryList(company));
		unionCompany.setCompanyId("");
		unionCompany.setParentCompanyId("0");
		mav.addObject("comList", unionCompanyApi.queryList(unionCompany));
		return mav;
	}
}

