package com.lhhs.loan.web.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;

@Controller
@RequestMapping("/crmIntentUserCount")
public class CrmIntentUserCountController {
	Page pageParams = new Page();
	 @Autowired
	 private CrmIntentLoanUserService crmIntentLoanUserService;
	 @Autowired
	 private SystemManagerService systemManagerService;
	 @Autowired
	 private UnionCompanyService unionCompanyService;
	
	/**
	 * 意向客户统计
	 * @param request
	 * @return
	 */
	 @RequestMapping("/getcrmIntentUserCountList")
     public ModelAndView getcrmIntentUserCountList(HttpServletRequest request,CrmIntentLoanUser entity){
    	 ModelAndView model=new ModelAndView("crmCount/crmIntentUserCount");
    	 CommonUtils.fillCompany(entity);
    	 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		 entity.setPage(pageParams);
		 entity.setIsCountIntent("yes");
		 Page page =crmIntentLoanUserService.getcrmIntentUserCountList(entity);
		 //客户总量  约见总量 签约总量
		 Map<String,Object> totalCountMap = crmIntentLoanUserService.getVariousTotalCount(entity);
    	 model.addObject("entity", entity);
    	 model.addObject("totalCountMap", totalCountMap);
    	 model.addObject("companys", companyList);
    	 model.addObject("page", page);
    	 return model;
     }

	 
	 
	 
		 
		 
	 /**
	  * 
	  * @param request 日、周、月
	  * @param timeUnit 时间单位
	  * @return
	  */
	 @RequestMapping("/getCrmEchartsList")
     @ResponseBody
     public Map<String, Object> getCrmEchartsList(HttpServletRequest request,@RequestParam(value = "timeUnit") String timeUnit){
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 CrmIntentLoanUser entity = new CrmIntentLoanUser();
    	 CommonUtils.fillCompany(entity);
    	 entity.setIsCountIntent("yes");
    	 Date time = DateUtils.getTime(timeUnit);
         map = crmIntentLoanUserService.getCrmAppointCountByTime(time,timeUnit,entity);
    	 map.put("timeUnit", timeUnit);
    	 return map;
     }
		 
		 

		/**
		 * 导出意向客户统计
		 * @param request
		 * @param response
		 * @param entity
		 */
		@RequestMapping("/intentLoanUserExport")
	     public void intentLoanUserExport( HttpServletRequest request,HttpServletResponse response,CrmIntentLoanUser entity){
	    	 Map<String, String> titles=new LinkedHashMap<String, String>();
	    	 CommonUtils.fillCompany(entity);
	    	 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
			 entity.setPage(pageParams);
			 entity.setIsCountIntent("yes");
			 List<CrmIntentLoanUser> loanUserList = crmIntentLoanUserService.getcrmIntentUserCountExportList(entity);
			 titles.put("appEmpCity", "省市");
	  	     titles.put("appointCompanyName", "分公司");
	  	     titles.put("appointDeptName", "部门");
	  	     titles.put("appointGroupName", "组别");
	  	     titles.put("appointEmpName", "客户经理");
	  	     titles.put("intentCount", "客户量");
	  	     titles.put("mianshenCount", "约见量");
	  	     titles.put("dealCount", "签约量");
	  	     String fileName="";
	  	     SimpleDateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
	  	     formatter.setLenient(false);
	  	     if(null!=entity.getAppBeginingTime()){
				 String beginTimeSed =formatter.format(entity.getAppBeginingTime());
				 fileName+=beginTimeSed;
			}
	  	     if(null!=entity.getAppEndingTime()){
	  	    	 String endTimeSed = formatter.format(entity.getAppEndingTime());
	  	    	 fileName+="-"+endTimeSed;
	  	     }
	  		 fileName+="意向客户统计报表";
	  	     AnalyseTransExcel.downLoadExcel(request, response, fileName, loanUserList, CrmIntentLoanUser.class, titles);
	  	     System.out.println(fileName+"下载完成"); 
		}
		
		
		
		/**
		 * 分公司-事业部
		 * @param request
		 * @param deptId 部门id
		 * @return
		 */
		@RequestMapping("/getDeptByCompanyId")
		@ResponseBody
		public Map<String,Object> getDeptByCompanyId(HttpServletRequest request,
				@RequestParam(value = "companyId", required = true) String companyId){
			Map<String, Object> result=  new HashMap<String, Object>();
			try {
				Set<LoanDept> returnList = new HashSet<LoanDept>();
				LoanEmp loanEmp=CommonUtils.getEmpFromSession();
				Map<String, Object> param=  new HashMap<String, Object>();
				List<LoanDept> deptList = null;
				if(null !=loanEmp.getCompanyIdList() && loanEmp.getCompanyIdList().size()>0){
					 param.put("status", "1");
					 param.put("companyIdList",loanEmp.getCompanyIdList());
					 deptList = systemManagerService.deptList(param,null);
				}else{
					if(null != loanEmp.getLeDeptId()){
						param.put("ldDeptId", loanEmp.getLeDeptId());
						deptList = systemManagerService.deptList(param, null);
					}
				}
				List<LoanDept> deptListByCompany = systemManagerService.queryDeptByCompanyId(companyId);
				if(null != deptList && deptList.size()>0){
					for (LoanDept loanDept : deptList) {
						if(null!=deptListByCompany && deptListByCompany.size()>0){
							for (LoanDept vo : deptListByCompany) {
								if(loanDept.getLdDeptId().equals(vo.getLdDeptId())){
									returnList.add(vo);
									continue;
								}
							}
						}else{
							break;
						}
						
					}
				}
				result.put("deptList", returnList);
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
			} catch (Exception e) {
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "系统异常");
			}
			
			return result;
		}
		
}
