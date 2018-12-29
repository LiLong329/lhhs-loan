package com.lhhs.loan.web.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.dao.domain.vo.LoanRecordVo;
import com.lhhs.loan.dao.domain.vo.LoanTransRecordVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.LoanRecordService;
import com.lhhs.loan.service.TransMainService;
import com.lhhs.loan.service.UnionCompanyService;

/**
 * @author xuejinxiong
 * 成交额统计报表
 *
 */
@RestController
@RequestMapping("/transactionVolumeStatistics")
public class TransactionVolumeStatisticsController {
	private static final Logger LOGGER = Logger.getLogger(TransactionVolumeStatisticsController.class);
	@Autowired
	private TransMainService transMainService;
	@Autowired
	private LoanRecordService loanRecordService;
	@Autowired
	private UnionCompanyService unionCompanyService;
	/**
	 * @author xuejinxiong
	 * 事业部/借款人/放款人
	 */
	@RequestMapping("/business")
	public ModelAndView business(LoanTransRecordVo entity,String statisticsName){
		CommonUtils.fillCompany(entity);
		BigDecimal total = new BigDecimal("0");
		ModelAndView mav =null; //new ModelAndView("transactionVolumeStatistics/business");
		if(StringUtils.isEmpty(statisticsName)){
			statisticsName="1";
		}
		if(statisticsName.equals("1")){//事业部
			//分公司
		    List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		    //事业部
		    List<String> departmentList = transMainService.queryDepartment();	
			mav=new ModelAndView("transactionVolumeStatistics/business");
			Page queryBusinessListPage = transMainService.queryBusinessListPage(entity);
			mav.addObject("total", transMainService.queryBusinessList(entity));
			mav.addObject("page", queryBusinessListPage);
			mav.addObject("companyList", companyList);
			mav.addObject("departmentList", departmentList);
		}else if(statisticsName.equals("2")){//借款人
			mav=new ModelAndView("transactionVolumeStatistics/borrower");
			Page queryBorrowerListPage = transMainService.queryBorrowerListPage(entity);
			List resultList=queryBorrowerListPage.getResultList();
			List reList=new ArrayList();
			for (int i=0;i<resultList.size();i++) {
				LoanTransRecordVo recordVo=(LoanTransRecordVo)resultList.get(i);
				recordVo.setProvienceName(recordVo.getProvienceName()+recordVo.getCityName());
				if(!StringUtils.isEmpty(recordVo.getCustomerMobile()) ){
					recordVo.setCustomerMobile(recordVo.getCustomerMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}
				reList.add(recordVo);
			}
			queryBorrowerListPage.setResultList(reList);
			mav.addObject("page", queryBorrowerListPage);
		}else if(statisticsName.equals("3")){//放款人
			mav=new ModelAndView("transactionVolumeStatistics/loaner");
			Page queryLoanerListPage = transMainService.queryLoanerListPage(entity);
			mav.addObject("page", queryLoanerListPage);
		}
		mav.addObject("statisticsName",statisticsName);
		return mav;
	}
	
	/**
	 * @author xuejinxiong
	 * 异步加载 事业部/借款人/放款人 分页数据
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanTransRecordVo entity,String statisticsName){
		CommonUtils.fillCompany(entity);
		ModelAndView mav =null;
		BigDecimal total = new BigDecimal("0");
		if(StringUtils.isEmpty(statisticsName)){
			statisticsName="1";
		}
		if(statisticsName.equals("1")){//事业部
			mav=new ModelAndView("transactionVolumeStatistics/_business");
			Page queryBusinessListPage = transMainService.queryBusinessListPage(entity);
			mav.addObject("total", transMainService.queryBusinessList(entity));
			mav.addObject("page", queryBusinessListPage);
		}else if(statisticsName.equals("2")){//借款人
			mav=new ModelAndView("transactionVolumeStatistics/_borrower");
			Page queryBorrowerListPage = transMainService.queryBorrowerListPage(entity);
			List resultList=queryBorrowerListPage.getResultList();
			List reList=new ArrayList();
			for (int i=0;i<resultList.size();i++) {
				LoanTransRecordVo recordVo=(LoanTransRecordVo)resultList.get(i);
				if(!StringUtils.isEmpty(recordVo.getCustomerMobile()) ){
					recordVo.setCustomerMobile(recordVo.getCustomerMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}
				reList.add(recordVo);
			}
			queryBorrowerListPage.setResultList(reList);
			mav.addObject("total", transMainService.queryBorrowerList(entity));
			mav.addObject("page", queryBorrowerListPage);
			mav.addObject("leEmpName", entity.getLeEmpName());
		}else if(statisticsName.equals("3")){//放款人
			mav=new ModelAndView("transactionVolumeStatistics/_loaner");
			if(!StringUtils.isEmpty(entity.getCustomerNature())){
				List list=new ArrayList<>();
				for(int i=0;i<entity.getCustomerNature().split(",").length;i++){
					list.add(entity.getCustomerNature().split(",")[i]);
				}
				entity.setBorrowCustomerTypeList(list);
			}
			Page queryLoanerListPage = transMainService.queryLoanerListPage(entity);
			mav.addObject("total", transMainService.queryLoanerList(entity));
			mav.addObject("page", queryLoanerListPage);
		}
		mav.addObject("statisticsName",statisticsName);
		return mav;
	}
	
	
	/**
	 * @author xuejinxiong
	 * 按月/周/日 统计
	 */
	@RequestMapping("/queryStatisticsByType")
	public List<Map<String,Object>> queryStatisticsByType(String type){
		LoanRecordVo loanRecordVo=new LoanRecordVo();
		CommonUtils.fillCompany(loanRecordVo);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isEmpty(type)){
			type="1";
		}
		List<Map<String,Object>> list=null;
		List listStr=new ArrayList();;
		Map<String,Object> mapDay=new HashMap();
		Object[] keyArray=null;//new Object[n];
		Object[] valueArray=null;//new Object[n];
		if(type.equals("1")){//月
			keyArray=new Object[12];
			valueArray=new Object[12];
			list=loanRecordService.queryMonthStatisticsList(loanRecordVo);
			int i;
			int j;
			for(i=0;i<12;i++){
			 if(list!=null && list.size()>0){
				 for(j=0;j<list.size();j++){
					 if(list.get(j).get("TIME").equals(getNextMonth(i))){
							keyArray[i]=getNextMonth(i)+"月";
							valueArray[i]=list.get(j).get("allAmount");
							break;
						}else{
							keyArray[i]=getNextMonth(i)+"月";
							valueArray[i]=0;
						}
				 } 
			 }else{
				 keyArray[i]=getNextMonth(i)+"月";
				 valueArray[i]=0;
			 }
				mapDay.put("keyArra", keyArray);
				mapDay.put("valueArra", valueArray);
			}
		}else if(type.equals("2")){//周
			keyArray=new Object[4];
			valueArray=new Object[4];
			keyArray[3]="第四周";
			keyArray[2]="第三周";
			keyArray[1]="第二周";
			keyArray[0]="第一周";
			loanRecordVo.setNum(0);
			valueArray[3]=loanRecordService.queryWeekStatisticsList(loanRecordVo);
			loanRecordVo.setNum(1);
			valueArray[2]=loanRecordService.queryWeekStatisticsList(loanRecordVo);
			loanRecordVo.setNum(2);
			valueArray[1]=loanRecordService.queryWeekStatisticsList(loanRecordVo);
			loanRecordVo.setNum(3);
			valueArray[0]=loanRecordService.queryWeekStatisticsList(loanRecordVo);
			mapDay.put("keyArray", keyArray);
			mapDay.put("valueArray", valueArray);
		}else if(type.equals("3")){//日
			keyArray=new Object[7];
			 valueArray=new Object[7];
			list=loanRecordService.queryDayStatisticsList(loanRecordVo);
			int i;
			int j;
			for(i=0;i<7;i++){
			  if(list!=null && list.size()>0){
				  for(j=0;j<list.size();j++){
						if(list.get(j).get("TIME").equals(formatter2.format(getNextDay(i)))){
							if(i==0){
								keyArray[i]="今天";
							}else if(i==1){
								keyArray[i]="昨天";
							}else{
								keyArray[i]=list.get(j).get("TIME");
							}
							valueArray[i]=list.get(j).get("allAmount");
							break;
						}else{
							if(i==0){
								keyArray[i]="今天";
							}else if(i==1){
								keyArray[i]="昨天";
							}else{
								keyArray[i]=formatter2.format(getNextDay(i));
							}
							valueArray[i]=0;
						}
					}
			  }else{
				  if(i==0){
					keyArray[i]="今天";
					 valueArray[i]=0; 
				  }else if(i==1){
					keyArray[i]="昨天"; 
					valueArray[i]=0; 
				  }else{
					  keyArray[i]=formatter2.format(getNextDay(i));
					  valueArray[i]=0; 
				  }
			  }
			
			  mapDay.put("keyArra", keyArray);
			  mapDay.put("valueArra", valueArray);
			}
		}
		if(type.equals("1") || type.equals("3")){
			keyArray=(Object[]) mapDay.get("keyArra");
			valueArray=(Object[]) mapDay.get("valueArra");
			for (int start = 0, end = keyArray.length - 1; start < end; start++, end--) {
				Object temp =  keyArray[end];
		        keyArray[end] = keyArray[start];
		        keyArray[start] = temp;
		    }
			for (int start = 0, end = valueArray.length - 1; start < end; start++, end--) {
				Object temp = valueArray[end];
		        valueArray[end] = valueArray[start];
		        valueArray[start] = temp;
		    }
			
			  mapDay.put("keyArray",keyArray);
			  mapDay.put("valueArray", valueArray);
		}
		listStr.add(mapDay);
		return listStr;
	}
	
	/**
	 * @author xuejinxiong
	 * 获取当前日期的前几天
	 */
	public static Date getNextDay(int i) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -i);  
        return calendar.getTime();  
    }  
	/**
	 * @author xuejinxiong
	 * 获取当前月的上一月
	 */
	public static String getNextMonth(int i) {  
		 Calendar cal = Calendar.getInstance();  
	       cal.add(cal.MONTH, -i);  
	       SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");  
	       String preMonth = dft.format(cal.getTime());  
	        return preMonth;    
    } 
	/**
	 * @author xuejinxiong
	 * 获取下一周
	 */
	public static String getNextWeeks(int i){
		return "0"+(i+1);
	}
	/**
	 * @author xuejinxiong
	 * 事业部/借款人/放款人维度统计导出
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/export")	
	public void export(LoanTransRecordVo entity,String statisticsName,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		CommonUtils.fillCompany(entity);
		Map<String,String> titles = new LinkedHashMap<String,String>();
		entity.setField5("noPage");
		if(StringUtils.isEmpty(statisticsName)){
			statisticsName="1";
		}
		String fileName="";
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		if(null!=entity.getBeginTime() && entity.getBeginTime()!=""){
			 String beginTimeSed =entity.getBeginTime().replaceAll("-", "");
			 fileName+=beginTimeSed;
		}
		if(null!=entity.getEndTime() && entity.getEndTime()!=""){
			formatter = new SimpleDateFormat("yyyyMMdd");
			String endTimeSed=entity.getEndTime().replaceAll("-", "");;
			fileName+="-"+endTimeSed;
		}
		if(statisticsName.equals("1")){//事业部
			fileName+="事业部维度统计";
			Page queryBusinessListPage = transMainService.queryBusinessListPage(entity);
			List<LoanTransRecordVo> resultList = (List<LoanTransRecordVo>) queryBusinessListPage.getResultList();
			for (LoanTransRecordVo loanTransRecord : resultList) {
				loanTransRecord.setLoanAmount((new BigDecimal(new DecimalFormat("0.00").format(loanTransRecord.getLoanAmount()))));
				loanTransRecord.setProvienceName(loanTransRecord.getProvienceName()+loanTransRecord.getCityName());
			}
			titles.put("provienceName", "省市");
			titles.put("companyName", "分公司");
			titles.put("department", "部门");
			titles.put("accountManager", "客户经理");
			titles.put("loanAmount", "放款金额（元）");
//			titles.put("lendingTime", "放款时间");
			AnalyseTransExcel.downLoadExcel(request, response, fileName, resultList, entity.getClass(), titles);
		}else if(statisticsName.equals("2")){//借款人
			fileName+="借款人维度统计";
			Page queryBorrowerListPage = transMainService.queryBorrowerListPage(entity);
			List<LoanTransRecordVo> resultList = (List<LoanTransRecordVo>) queryBorrowerListPage.getResultList();
			for (LoanTransRecordVo recordVo : resultList) {
				recordVo.setAmount((new BigDecimal(new DecimalFormat("0.00").format(recordVo.getAmount()))));
				recordVo.setProvienceName(recordVo.getProvienceName()+recordVo.getCityName());
				if(!StringUtils.isEmpty(recordVo.getCustomerMobile()) ){
					recordVo.setCustomerMobile(recordVo.getCustomerMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}
			}
			titles.put("provienceName", "省市");
			titles.put("borrower", "借款人");
			titles.put("customerMobile", "借款人手机号");
			titles.put("amount", "借款金额");
//			titles.put("lendingTime", "放款时间");
			AnalyseTransExcel.downLoadExcel(request, response, fileName, resultList, entity.getClass(), titles);
		}else if(statisticsName.equals("3")){//放款人
			fileName+="放款人维度统计";
			if(!StringUtils.isEmpty(entity.getCustomerNature())){
				List list=new ArrayList<>();
				for(int i=0;i<entity.getCustomerNature().split(",").length;i++){
					list.add(entity.getCustomerNature().split(",")[i]);
				}
				entity.setBorrowCustomerTypeList(list);
			}
			Page queryLoanerListPage = transMainService.queryLoanerListPage(entity);
			List<LoanTransRecordVo> resultList = (List<LoanTransRecordVo>) queryLoanerListPage.getResultList();
			for (LoanTransRecordVo loanTransRecord : resultList) {
				loanTransRecord.setLoanAmount((new BigDecimal(new DecimalFormat("0.00").format(loanTransRecord.getLoanAmount()))));
				loanTransRecord.setProvienceName(loanTransRecord.getProvienceName()+loanTransRecord.getCityName());
				if("10,11,12,13,14,15".contains(loanTransRecord.getCustomerNature())){
					loanTransRecord.setCustomerNature("对接放款");
				}else if("31".contains(loanTransRecord.getCustomerNature())){
					loanTransRecord.setCustomerNature("机构放款");
				}else if("33".contains(loanTransRecord.getCustomerNature())){
					loanTransRecord.setCustomerNature("同行放款");
				}else if("41".contains(loanTransRecord.getCustomerNature())){
					loanTransRecord.setCustomerNature("公司放款");
				}
			}
			titles.put("provienceName", "省市");
			titles.put("lenderName", "放款方");
			titles.put("customerNature", "放款类型");
			titles.put("loanAmount", "放款金额");
//			titles.put("lendingTime", "放款时间");
			AnalyseTransExcel.downLoadExcel(request, response, fileName, resultList, entity.getClass(), titles);
		}
	}
}
