/**
 * Project Name:loan-web
 * File Name:LoanManagerController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年7月28日上午9:40:36
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.vo.LoanRecordVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.LoanRecordService;

/**
 * ClassName:LoanManagerController <br/>
 * Function: 放款管理模块<br/>
 * Date:     2017年7月28日 上午9:40:36 <br/>
 * @author   chenyinhui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/loanManager")
@SuppressWarnings("all")
public class LoanManagerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanManagerController.class);
	
	@Autowired
	private LoanRecordService loanRecordService;
	
	/**
	 * loanRecord:(放款记录查询，包含个人、机构、同行). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @param entity
	 * @param selectType
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/loanRecord/{selectType}")
	public ModelAndView loanRecord(HttpServletRequest request, 
			LoanRecordVo entity,
			@PathVariable("selectType") Integer selectType){
		ModelAndView mav=null;
		selectType=(null==selectType)?1:selectType;
		switch(selectType){
		case 1:
			mav=new ModelAndView("loanManager/personalLoanRecord");
			break;
		case 2:
			mav=new ModelAndView("loanManager/peerLoanRecord");
			break;
		case 3:
			mav=new ModelAndView("loanManager/organizationLoanRecord");
			break;
		default:
			mav=new ModelAndView("loanManager/personalLoanRecord");
			break;
		}
		CommonUtils.fillCompany(entity);
		Page page = loanRecordService.queryListPage(entity);
		mav.addObject("page", page);
		mav.addObject("map", loanRecordService.queryTotalAmount(entity));//放款总金额
		mav.addObject("selectType", selectType);//查询类型
		return mav;
	}
	
	/**
	 * loanRecord:(放款记录异步查询，包含个人、机构、同行). <br/>
	 *
	 * @author chenyinhui
	 * @param request
	 * @param entity
	 * @param selectType
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxLoanRecord/{selectType}")
	public ModelAndView ajaxLoanRecord(HttpServletRequest request, 
			LoanRecordVo entity,
			@PathVariable("selectType") Integer selectType){
		ModelAndView mav=null;
		selectType=(null==selectType)?1:selectType;
		switch(selectType){
		case 1:
			mav=new ModelAndView("loanManager/_personalLoanRecordSub");
			break;
		case 2:
			mav=new ModelAndView("loanManager/_peerLoanRecordSub");
			break;
		case 3:
			mav=new ModelAndView("loanManager/_organizationLoanRecordSub");
			break;
		default:
			mav=new ModelAndView("loanManager/_personalLoanRecordSub");
			break;
		}
		CommonUtils.fillCompany(entity);
		Page page = loanRecordService.queryListPage(entity);
		mav.addObject("page", page);
		mav.addObject("map", loanRecordService.queryTotalAmount(entity));//放款总金额
		return mav;
	}
	
	
	@RequestMapping("/exportLoanRecord/{selectType}")
	public void exportLoanRecord(
			HttpServletRequest request,
			HttpServletResponse response,
			LoanRecordVo entity,
			@PathVariable("selectType") Integer selectType){
		
		String fileName = "";
	    String lenderName = "";
	    if (selectType==1) {
	    	fileName = "个人放款记录";
	    	lenderName = "出借人姓名";
		}else if (selectType==2) {
			fileName = "同行放款记录";
			lenderName = "机构名称";
			
		}else {
			fileName = "机构放款记录";
			lenderName = "机构名称";
		}
	    Map<String, String> titles = new LinkedHashMap<String, String>();
	    
	    titles.put("id", "序号");
	    titles.put("provienceName", "省市");
	    titles.put("companyName", "分公司");
		titles.put("lenderName", lenderName);
		titles.put("typeName", "客户性质");
		titles.put("loanAmount", "放款金额（元）");
		if (selectType==3) {
			titles.put("termUnit", "放款期限");
		}
		titles.put("loanRateUnit", "放款利率");
		titles.put("loanTime", "放款时间");
		titles.put("orderNo", "报单编号");
		titles.put("productType", "产品类型");
		titles.put("productName", "产品名称");
		titles.put("termUnit", "借款期限");
		titles.put("rateUnit", "借款利率");
		titles.put("borrower", "借款人姓名");
		titles.put("anticipatedInterest", "预期利息（元）");
		if (selectType!=2) {
			titles.put("interestMargin", "息差");
		}
		titles.put("applyTime", "申请时间");
		titles.put("status", "状态");
		CommonUtils.fillCompany(entity);
		List<LoanRecordVo> list = loanRecordService.queryList(entity);
		for (int i = 0; i < list.size(); i++) {
			LoanRecordVo vo = list.get(i);
			vo.setId(i+1+"");
			vo.setProvienceName(vo.getProvienceName()+vo.getCityName());
			vo.setLoanRateUnit(vo.getLoanRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+vo.getLoanRateUnit());
			vo.setTermUnit(vo.getTerm()+vo.getTermUnit());
			vo.setRateUnit(vo.getRate().setScale(3, BigDecimal.ROUND_HALF_UP)+"%/"+vo.getRateUnit());
			vo.setAnticipatedInterest(vo.getAnticipatedInterest().setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setInterestMargin(vo.getInterestMargin().setScale(2, BigDecimal.ROUND_HALF_UP));
			if ("03".equals(vo.getStatus())) {
				vo.setStatus("放款成功");
			}
		}
		AnalyseTransExcel.downLoadExcel(request, response, fileName, list, LoanRecordVo.class, titles);
	}
	
}