package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanAccountsTrans;
import com.lhhs.loan.dao.domain.vo.LoanAccountsTransVo;
import com.lhhs.loan.service.AccountsSubjectInfoService;
import com.lhhs.loan.service.AccountsTransService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.FundsAccountsManagerService;

/**
 * 用户资金账目管理模块
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/userFundsAccountsManager")
public class UserFundsAccountsManager {
	
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	
	@Autowired
	FundsAccountsManagerService fundsAccountsManagerService;
	@Autowired
	private AccountsSubjectInfoService accountsSubjectInfoService;
	@Autowired
	private AccountsTransService accountsTransService;
	
	
	/**
	 * 分页获取用户资产负债信息
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageNumber 页码
	 * @return
	 */
	@RequestMapping("/assetsLiabilitiesInfo")
	public ModelAndView assetsLiabilitiesInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber
			){
		ModelAndView modelAndView = new ModelAndView("fundsAccountsManager/assetsLiabilitiesInfo");
		Map<String,Object> params = new HashMap<String,Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		
		CommonUtils.fillCompanyToMap(params);
		params.put("mobile", mobile);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("page", page);
		fundsAccountsManagerService.getAssetsLiabilitiesInfos(params, page);
		
		//计算资产总额和负债总额
		params.put("page", null);
		List<LoanAccountInfo> assetsLiabilitiesInfos = fundsAccountsManagerService.getAssetsLiabilitiesInfos(params, null);
		BigDecimal sumLiabilitiesTotal = new BigDecimal(0);
		BigDecimal sumAssetTotal = new BigDecimal(0);
		for (int i = 0; i < assetsLiabilitiesInfos.size(); i++) {
			LoanAccountInfo assetsLiabilitiesInfo = assetsLiabilitiesInfos.get(i);
			
			BigDecimal liabilitiesTotal = assetsLiabilitiesInfo.getLiabilitiesTotal();
			if (liabilitiesTotal!=null) {
				sumLiabilitiesTotal = sumLiabilitiesTotal.add(liabilitiesTotal);
			}
			BigDecimal assetTotal = assetsLiabilitiesInfo.getAssetTotal();
			if (assetTotal!=null) {
				sumAssetTotal = sumAssetTotal.add(assetTotal);
			}
		}
		modelAndView.addObject("sumLiabilitiesTotal", sumLiabilitiesTotal);
		modelAndView.addObject("sumAssetTotal", sumAssetTotal);
		modelAndView.addObject("params", params);
		modelAndView.addObject("page", page);
		return modelAndView;
	}
	
	/**
	 * 查看负债详情
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param ownerName 姓名
	 * @param certificateNo 身份证号
	 * @param assetTotal 资产总额
	 * @param liabilitiesTotal 负债总额
	 * @return
	 */
	@RequestMapping("/seeDetail")
	public ModelAndView seeDetail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "ownerName", required = false) String ownerName,
			@RequestParam(value = "certificateNo", required = false) String certificateNo,
			@RequestParam(value = "assetTotal", required = false) BigDecimal assetTotal,
			@RequestParam(value = "liabilitiesTotal", required = false) BigDecimal liabilitiesTotal
			){
		ModelAndView modelAndView = new ModelAndView("fundsAccountsManager/seeDetail");
		modelAndView.addObject("mobile", mobile);
		modelAndView.addObject("ownerName", ownerName);
		modelAndView.addObject("certificateNo", certificateNo);
		modelAndView.addObject("assetTotal", assetTotal);
		modelAndView.addObject("liabilitiesTotal", liabilitiesTotal);
		return modelAndView;
	}
	
	/**
	 * 分页获取用户账户总览信息
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageNumber 页码
	 * @return
	 */
	@RequestMapping("/accountOverviewInfo")
	public ModelAndView accountOverviewInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber
			){
		ModelAndView modelAndView = new ModelAndView("fundsAccountsManager/accountOverviewInfo");
		Map<String,Object> params = new HashMap<String,Object>();
		pageNumber = pageNumber == null ? 1 : pageNumber;
		page.setPageIndex(pageNumber);
		
		CommonUtils.fillCompanyToMap(params);
		params.put("mobile", mobile);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("page", page);
		fundsAccountsManagerService.getAccountOverviewInfos(params, page);
		
		//计算余额总数
		params.put("page", null);
		List<LoanAccountInfo> accountOverviewInfos = fundsAccountsManagerService.getAccountOverviewInfos(params, null);
		BigDecimal sumAmonut = new BigDecimal(0);
		for (int i = 0; i < accountOverviewInfos.size(); i++) {
			LoanAccountInfo accountOverviewInfo = accountOverviewInfos.get(i);
			BigDecimal amonut = accountOverviewInfo.getAmount();
			if(amonut!=null){
				sumAmonut = sumAmonut.add(amonut);
			}
		}
		modelAndView.addObject("sumAmonut", sumAmonut);
		modelAndView.addObject("params", params);
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	/**
	 * transRecord:(交易记录查询). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/transRecord")
	public ModelAndView transRecord(HttpServletRequest request, LoanAccountsTrans entity){
		ModelAndView mav=new ModelAndView("fundsAccountsManager/transRecord");
		entity.setAccountType(SystemConst.AccountType.PERSONAL);
		CommonUtils.fillCompany(entity);
		Page page = accountsTransService.queryListPage(entity);
		mav.addObject("page", page);
		LoanAccountsTrans sum=accountsTransService.querySumAmount(entity);//金额统计
		mav.addObject("map", sum);
		
		return mav;
	}
	/**
	 * transRecord:(交易记录异步查询). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxTransRecord")
	public ModelAndView ajaxTransRecord(HttpServletRequest request, LoanAccountsTrans entity){
		ModelAndView mav=new ModelAndView("fundsAccountsManager/_transRecordSub");
		entity.setAccountType(SystemConst.AccountType.PERSONAL);
		CommonUtils.fillCompany(entity);
		Page page = accountsTransService.queryListPage(entity);
		mav.addObject("page", page);
		LoanAccountsTrans sum=accountsTransService.querySumAmount(entity);//金额统计
		mav.addObject("map", sum);
		return mav;
	}
	
	/**
	 * transRecord:(导出用户资金交易记录). <br/>
	 * @author chenyinhui
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/exportTransRecord")
	@ResponseBody
	public void exportTransRecord(HttpServletRequest request, HttpServletResponse response,
			LoanAccountsTransVo entity){
		CommonUtils.fillCompany(entity);
		entity.setAccountType(SystemConst.AccountType.PERSONAL);
		List<LoanAccountsTransVo> list = accountsTransService.queryList(entity);
		if(list.size()>0){
 			String filename ="用户交易记录";
 			Map<String,String>  titles = new LinkedHashMap<String,String>();
 			titles.put("transId", "交易ID");
 			titles.put("ownerName", "用户名称");
 			titles.put("orderNo", "业务编号");
 			titles.put("debitAmount", "收入（元）");
 			titles.put("creditAmount", "支出（元）");
 			titles.put("balance", "账户余额（元）");
 			titles.put("subjectName", "交易摘要");
 			titles.put("transTime", "交易时间");
 			titles.put("transRemark", "备注");
 			AnalyseTransExcel.downLoadExcel(request, response, filename, list, LoanAccountsTransVo.class, titles);
 		}
	}

}
