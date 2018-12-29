package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.dao.LoanOrderInfoDetailMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.domain.Agreement;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.service.AgreementService;
import com.lhhs.loan.service.CommonUtils;
/**

 * 合同管理
 * @author HB
 */
@Controller
@RequestMapping("/agreement")
public class AgreementController {
	
	@Autowired
	private AgreementService agreementService;
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanOrderInfoDetailMapper loanOrderInfoDetailMapper;
	
	/**
	 * 合同列表
	 */
	@RequestMapping("/list/{isAuditing}")
	public ModelAndView list(Agreement params,@PathVariable("isAuditing") Integer isAuditing){
		ModelAndView mav = new ModelAndView("agreement/agreementList");
		if (isAuditing==0) {
			//待审核合同列表
			mav.addObject("isAuditing", 0);
			params.setAuditingStatus("0");
		}else{
			//全部合同列表
			mav.addObject("isAuditing", 1);
			params.setAuditingStatus("3");
		}
		CommonUtils.fillCompany(params); 
		mav.addObject("page", agreementService.queryListPage(params));
		mav.addObject("params", params);
		return mav;
	}
	/**
	 * 审核
	 */
	@RequestMapping("/auditing")
	public ModelAndView auditing(String id){
		ModelAndView mav = new ModelAndView("agreement/agreementAuditing");
		Agreement agreement = agreementService.get(id);
		//处理放款人信息
		String investorNames = agreement.getInvestorName();
		if (StringUtils.isNotEmpty(investorNames)) {
			String [] investorNameList = investorNames.split(",");
			mav.addObject("investorNameList", investorNameList);
		}
		mav.addObject("agreement", agreement);
		
		return mav;
	}
	/**
	 * 保存审核信息
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,String> save(Agreement params){
		Map<String,String> map = new HashMap<String,String>();
		int code =  agreementService.saveAuditingInfo(params);
		if(code == 1){
			map.put(SystemConst.retCode, SystemConst.SUCCESS);
		}else{
			map.put(SystemConst.retCode, SystemConst.FAIL);
		}
		return map;
		
	}
	/**
	 * 查看
	 */
	@RequestMapping("/details")
	public ModelAndView details(String id){
		ModelAndView mav = new ModelAndView("agreement/agreementDetails");
		Agreement agreement = agreementService.get(id);
		//处理放款人信息
		String investorNames = agreement.getInvestorName();
		if (StringUtils.isNotEmpty(investorNames)) {
			String [] investorNameList = investorNames.split(",");
			mav.addObject("investorNameList", investorNameList);
		}
		mav.addObject("agreement", agreement);
		return mav;
	}
	/**
	 * 资质信息
	 */
	@RequestMapping("/credentials/{upOrDown}")
	public ModelAndView credentials(String orderNo,String isAuditing,@PathVariable("upOrDown") Integer upOrDown){
		ModelAndView mav = new ModelAndView("agreement/credentials");
		LoanOrderInfo orderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		LoanOrderInfoDetail orderInfoDetail = loanOrderInfoDetailMapper.selectByPrimaryKey(orderNo);
		Agreement param = new Agreement();
		param.setOrderNo(orderNo);
		Agreement agreement = agreementService.get(param);		
		mav.addObject("bname", agreement.getBorrowerName());
		mav.addObject("orderNo", orderNo);
		mav.addObject("productId", orderInfo.getChildProductNo());
		mav.addObject("fundSide", orderInfoDetail.getFundOwner());
		mav.addObject("isAuditing", isAuditing);
		mav.addObject("isAgreement", "1");
		return mav;
	}
}

