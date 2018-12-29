package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.InvestCustomerService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.TransAccountsService;

/**
 * 
 * ClassName: InvestCustomerController <br/>
 * Function: 理财人信息（银主）. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年8月3日 下午3:13:14 <br/>
 * @author suncj
 * @version 
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/investCustomer")
public class InvestCustomerController {
	public static final int PAGESIZE = 10;
	private Page page = new Page(PAGESIZE);
	private static final Logger LOGGER = Logger.getLogger(InvestCustomerController.class);
	
	@Autowired
	private InvestCustomerService investCustomerService;
	@Autowired
	private TransAccountsService transAccountsService;
	@Autowired
	private SystemManagerService systemManager;
	
	/**
	 * investCustomerList:(客户管理-理财人信息（银主）列表查询). <br/>
	 * @author suncj
	 * @param request
	 * @param pageNumber
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/investCustomerList")
	public ModelAndView investCustomerList(HttpServletRequest request,
			@RequestParam(name="customerId",required=false)String customerId,
			@RequestParam(name="investCustomerNature",required=false)String investCustomerNature,
			@RequestParam(name="investCustomerName",required=false)String investCustomerName,
			@RequestParam(name="investCustomerMobile",required=false)String investCustomerMobile,
			@RequestParam(name="pageNumber",required=false)Integer pageNumber){
		 //获取当前登录人 如果登录人不为空且公司id 集团id 不为空 且 集团id！=公司id情况下
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Map<String,Object> map = new HashMap<>();
		 if(user!=null && user.getUnionId()!=null && user.getCompanyId()!=null
				        && !user.getUnionId().equals(user.getCompanyId())){
			 map.put("companyId", user.getCompanyId());
			            request.setAttribute("isadmin", "admin");
		 }
		CommonUtils.fillCompanyToMap(map);
		map.put("customerId", customerId);
		map.put("investCustomerNature", investCustomerNature);
		map.put("investCustomerName", investCustomerName);
		map.put("investCustomerMobile", investCustomerMobile);
		map.put("investCustomerType", "10");
		ModelAndView model=new ModelAndView("system/investCustomer/investCustomerList");
		pageNumber=pageNumber==null?1:pageNumber;
		page.setPageIndex(pageNumber);
		map.put("page", page);
		List<LoanInvestCustomerInfo> investCustomerList=investCustomerService.investCustomerList(map,page);
		List<Map<String, Object>> accountCustProperty=getAccountCustProperty(transAccountsService);
		model.addObject("page", page);
		model.addObject("investCustomerList", investCustomerList);
		model.addObject("accountCustProperty", accountCustProperty);
		model.addObject("customerId", customerId);
		model.addObject("investCustomerNature", investCustomerNature);
		model.addObject("investCustomerName", investCustomerName);
		model.addObject("investCustomerMobile", investCustomerMobile);
		model.addObject("customerManager", user.getUserId().toString());
		return model;
	}
	
	/**
	 * getAccountCustProperty:(公共：查询投资人客户性质). <br/>
	 * @author suncj
	 * @param transAccountsService
	 * @return
	 * @since JDK 1.8
	 */
	public static List getAccountCustProperty(TransAccountsService transAccountsService){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("big_category", "10");
		return transAccountsService.queryAccountCustProperty(map);
	}
	
	/**
	 * 
	 * toInvestCustomerAdd:(理财人信息-to新增页面). <br/>
	 * @author Administrator
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toInvestCustomerAdd")
	public String toInvestCustomerAdd(HttpServletRequest request){
		Map<String, Object> paramsMap=new HashMap<String,Object>();
		CommonUtils.fillCompanyToMap(paramsMap);
		List<Map<String, Object>> accountCustProperty=getAccountCustProperty(transAccountsService);
		List<Map<String, Object>> allDepts=investCustomerService.queryAllDepts();//查询部门
		List<Map<String, Object>> companys=investCustomerService.queryAllCompany(paramsMap);//查询公司
		request.setAttribute("accountCustProperty", accountCustProperty);
		request.setAttribute("allDepts", allDepts);
		request.setAttribute("investCustomer", new LoanInvestCustomerInfo());
		request.setAttribute("companys",companys); //systemManager.queryAllCompany()
		request.setAttribute("msg", "save");
		request.setAttribute("banks", systemManager.queryAllBank());
		return "system/investCustomer/investCustomerAdd";
	}
	/**
	 * 
	 * toInvestCustomerUpdate:(理财人信息-编辑页面). <br/>
	 * @author suncj
	 * @param request
	 * @since JDK 1.8
	 */
	@RequestMapping("/toInvestCustomerUpdate")
	public ModelAndView toInvestCustomerUpdate(HttpServletRequest request,@RequestParam(name="id",required=false)String id){
		ModelAndView model=new ModelAndView("system/investCustomer/investCustomerAdd");
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Map<String, Object> paramsMap=new HashMap<String,Object>();
		CommonUtils.fillCompanyToMap(paramsMap);
		LoanInvestCustomerInfo investCustomer= investCustomerService.selectByPrimaryKey(id);
		List<Map<String, Object>> accountCustProperty=getAccountCustProperty(transAccountsService);
		List<Map<String, Object>> companys=investCustomerService.queryAllCompany(paramsMap);//查询公司
		List<Map<String, Object>> allManager=investCustomerService.queryAllManager(investCustomer.getAffiliatedCompanyId());
		//如果当前登录人不是客户经理 进行脱敏操作
		if(null!=investCustomer && !user.getUserId().toString().equals(investCustomer.getAccountManagerNo())){
			if(StringUtils.isNotEmpty(investCustomer.getInvestCustomerMobile())){
				investCustomer.setInvestCustomerMobile(investCustomer.getInvestCustomerMobile().substring(0, 3)+"****"+investCustomer.getInvestCustomerMobile().substring(investCustomer.getInvestCustomerMobile().length()-4, investCustomer.getInvestCustomerMobile().length()));
			}
			if(StringUtils.isNotEmpty(investCustomer.getIdNum())){
				investCustomer.setIdNum(investCustomer.getIdNum().substring(0, 1)+"****"+investCustomer.getIdNum().substring(investCustomer.getIdNum().length()-4, investCustomer.getIdNum().length()));
			}
			if(StringUtils.isNotEmpty(investCustomer.getBankCardNo())){
				investCustomer.setBankCardNo(investCustomer.getBankCardNo().substring(0, 5)+"****"+investCustomer.getBankCardNo().substring(investCustomer.getBankCardNo().length()-4, investCustomer.getBankCardNo().length()));
			}
		}
		model.addObject("investCustomer", investCustomer);
		model.addObject("companys",companys);
		model.addObject("banks", systemManager.queryAllBank());
		model.addObject("allManager", allManager);
		model.addObject("accountCustProperty", accountCustProperty);
		model.addObject("msg", "edit");
		return model;
	}
	
	/**
	 * -更新或新增
	 * @param request
	 * @param LoanInvestCustomerInfo
	 * @return
	 */
	@RequestMapping("/investCusAddOrUpdate")
	@ResponseBody
	public Map<String, Object> investCusAddOrUpdate(HttpServletRequest request,LoanInvestCustomerInfo investCustomerInfo){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			int count=investCustomerService.save(investCustomerInfo);
			if (count >= 1) {
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
			   }else{
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u5931\u8d25");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 
	 * toInvestCustomerInfo:(理财人信息(银主)查看理财人详情信息). <br/>
	 * @author suncj
	 * @param request
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/toInvestCustomerInfo")
	public ModelAndView toInvestCustomerInfo(HttpServletRequest request,@RequestParam(name="id",required=false)String id){
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Map<String, Object> paramMap=new HashMap<String,Object>();
		ModelAndView model=new ModelAndView("system/investCustomer/investCustomerInfo");
		LoanInvestCustomerInfo investCustomer= investCustomerService.selectByPrimaryKey(id);
		model.addObject("investCustomer", investCustomer);
		model.addObject("msg", "viewDetails");
		model.addObject("customerManager", user.getUserId());
		return model;
	}
	
	/**
	 * 理财人信息信息导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/investCustomerExport")
	@ResponseBody
	public void investCustomerExport(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="customerId",required=false)String customerId,
			@RequestParam(name="investCustomerNature",required=false)String investCustomerNature,
			@RequestParam(name="investCustomerName",required=false)String investCustomerName,
			@RequestParam(name="investCustomerMobile",required=false)String investCustomerMobile
			){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("customerId", customerId);
		map.put("investCustomerNature", investCustomerNature);
		map.put("investCustomerName", investCustomerName);
		map.put("investCustomerMobile", investCustomerMobile);
		List<LoanInvestCustomerInfo> list=investCustomerService.investCustomerExport(map);	   
		String fileName="理财人信息";
	    Map<String, String> titles=new LinkedHashMap<String, String>();
	    titles.put("customerId", "客户编号");
	    titles.put("customerNatureName", "客户性质");
	    titles.put("investCustomerName", "客户名称");
	    titles.put("sex", "性别");
	    titles.put("investCustomerMobile", "手机号码");
	    AnalyseTransExcel.downLoadExcel(request, response, fileName, list, LoanInvestCustomerInfo.class, titles);
	    System.out.println(fileName+"下载完成");   
	}
	
	
	/**
	 * 
	 * queryCustomerMobile:校验手机号唯一性
	 * @author suncj
	 * @param request
	 * @param transAccounts
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/queryCustomerMobile")
	@ResponseBody
	public Map<String, Object> queryCustomerMobile(HttpServletRequest request
			,@RequestParam(name="mobile",required=false)String mobile
			,@RequestParam(name="type",required=false)String type
			){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			int count=investCustomerService.selectByMobile(mobile);
			if(count==0){
				resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
				resultMap.put(SystemConst.retMsg, "\u64cd\u4f5c\u6210\u529f");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	
	/**
	 * companyManagerNoLink:公司-客户经理级联查询
	 * @author suncj
	 * @param request
	 * @param affiliatedCompanyId
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/companyManagerNoLink")
	@ResponseBody
	public Map<String, Object> companyManagerNoLink(HttpServletRequest request,String affiliatedCompanyId){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		List<Map<String, Object>> list=investCustomerService.queryAllManager(affiliatedCompanyId);
		resultMap.put("groupList", list);
		return resultMap;
	}
	
}
