package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanChildProduct;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.DeclarationService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private DeclarationService declarationService;
	
	@RequestMapping("/toOrderInfo")
	public String toOrderInfo(Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<LoanParentProduct> productList = systemManagerService.queryProductList(null);
		map.clear();
		Page page = new Page();
		page.setPageSize(1000);
		page.setPageIndex(0);
		map.put("page", page);
		List<LoanEmp> empList = systemManagerService.employeeList(map, page);
		
		model.addAttribute("productList", productList);
		model.addAttribute("empList", empList);
		return "报单";
	}
	
	@RequestMapping("/findChildProductList")
	@ResponseBody
	public List<LoanChildProduct> findChildProductList(@RequestParam("productNo") String productNo){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("product_no", productNo);
		List<LoanChildProduct> lists = systemManagerService.queryProductById(map);
		return lists;
	}
	
	@RequestMapping("/saveOderInfo")
	@ResponseBody
	public Map<String,Object> saveOderInfo(@RequestParam("orderInfo") String orderInfo,
			@RequestParam("orderInfoDetail") String orderInfoDetail,
			@RequestParam("borrowerInfo") String borrowerInfo){
		LoanOrderInfo loanOrderInfo = JSON.parseObject(orderInfo, LoanOrderInfo.class);
		LoanOrderInfoDetail loanOrderInfoDetail = JSON.parseObject(orderInfoDetail, LoanOrderInfoDetail.class);
		LoanOrderBorrowerExtendWithBLOBs borrower = JSON.parseObject(borrowerInfo, LoanOrderBorrowerExtendWithBLOBs.class);
		
		return declarationService.addDeclaration(loanOrderInfo, loanOrderInfoDetail, borrower);
	}
}
