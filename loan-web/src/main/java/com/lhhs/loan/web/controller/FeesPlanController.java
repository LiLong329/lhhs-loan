package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.domain.LoanAccountsSubjectInfo;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.FeesPlanService;
import com.lhhs.loan.service.account.AccountManagerService;

@Controller
@RequestMapping("/feesPlan")
public class FeesPlanController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FeesPlanService feesPlanService;
	@Autowired
	private AccountManagerService accountManagerService;

	/**
	 * 计划   收入列表
	 */
	@RequestMapping("/earn")	
	public String earn(FeesPlanVo params, ModelMap modelMap ){
		CommonUtils.fillCompany(params);
		params.setTransType("01");
		Page page  = feesPlanService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		
		BigDecimal total = new BigDecimal("0");
		for (Object vo :page.getResultList()) {
			FeesPlanVo feesPlanVo = (FeesPlanVo) vo;
			String feesAmount = feesPlanVo.getFeesAmount().toString();
			if(StringUtils.isNoneBlank(feesAmount)) {
				BigDecimal bd = new  BigDecimal(feesAmount);
				total = total.add(bd);
			}
		}
		modelMap.put("total", total);
		
		modelMap.put("page", page);
		modelMap.put("params", params);
		
		
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("IN");
		parm.setSubjectTypes("105,106,107,108,111");
		List<LoanAccountsSubjectInfo> accountsSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm );
		
		modelMap.put("accountsSubjectInfoList", accountsSubjectInfoList);
		
		return "feesPlan/earn";
	}
	
	/**
	 * 计划   支出列表
	 */
	@RequestMapping("/expend")	
	public String expend(FeesPlanVo params, ModelMap modelMap ){
		CommonUtils.fillCompany(params);
		params.setTransType("02");
		Page page  = feesPlanService.findPageByEntity(new Page(params.getPageIndex(),params.getPageSize()),params);
		modelMap.put("page", page);
		modelMap.put("params", params);
		LoanAccountsSubjectInfo parm = new LoanAccountsSubjectInfo();
		parm.setDirection("OUT");
		parm.setSubjectTypes("105,106,107,108,111");
		List<LoanAccountsSubjectInfo> accountsSubjectInfoList = accountManagerService.queryAccountsSubjectInfoList(parm );
		modelMap.put("accountsSubjectInfoList", accountsSubjectInfoList);
		
		BigDecimal total = new BigDecimal("0");
		for (Object vo :page.getResultList()) {
			FeesPlanVo feesPlanVo = (FeesPlanVo) vo;
			String feesAmount = feesPlanVo.getFeesAmount().toString();
			if(StringUtils.isNoneBlank(feesAmount)) {
				BigDecimal bd = new  BigDecimal(feesAmount);
				total = total.add(bd);
			}
		}
		modelMap.put("total", total);
		
		return "feesPlan/expend";
	}
	
	
	/**
	 * 收入
	 * @param loanFeesRecord
	 * @return
	 */
	@RequestMapping("/loanFeesTrans")
	@ResponseBody
	public Map<String, Object> loanFeesTrans(LoanFeesPlan loanFeesPlan) {
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			String retCode=feesPlanService.feesPlanService(loanFeesPlan);
			if(StringUtil.isNotEmpty(retCode)&&retCode.equals(SystemConst.SUCCESS)){
				result.put("retCode", SystemConst.SUCCESS);
			}else{
				result.put("retCode", SystemConst.FAIL);
				result.put("retMsg", "操作失败");
			}
		} catch (Exception e) {
			logger.info(e.toString());
			result.put("retCode", SystemConst.FAIL);
			result.put("retMsg", e.getMessage());
		}
		return result;
	}
}
