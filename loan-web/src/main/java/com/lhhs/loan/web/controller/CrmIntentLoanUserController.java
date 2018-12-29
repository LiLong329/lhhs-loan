/**
 * Project Name:loan-web
 * File Name:CrmIntentLoanUserController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年11月14日上午9:43:12
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-web
 * File Name:CrmIntentLoanUserController.java
 * Package Name:com.lhhs.loan.web.controller
 * Date:2017年11月14日上午9:43:12
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lhhs.bump.api.ChannelApplyApi;
import com.lhhs.bump.domain.ChannelApply;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.enums.crm.ActionType;
import com.lhhs.loan.common.enums.crm.AppointEmp;
import com.lhhs.loan.common.enums.crm.CustomerStatus;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.shared.zip.AnalyseTransExcel;
import com.lhhs.loan.common.utils.ExcelUtil;
import com.lhhs.loan.common.utils.ImportExcel;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.dao.domain.LoanParentProduct;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.ProductService;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;

/**
 * ClassName:CrmIntentLoanUserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年11月14日 上午9:43:12 <br/>
 * @author   zhanghui
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/crmIntentLoanUser")
public class CrmIntentLoanUserController {

	private static final Logger LOGGER = Logger.getLogger(CrmIntentLoanUserController.class);
	Page pageParams = new Page();
	@Autowired
	private CrmIntentLoanUserService crmIntentLoanUserService;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private UnionCompanyService unionCompanyService;
	@Autowired
	private ProductService productService;
	@Reference
	private ChannelApplyApi channelApplyApi;
	
	/**
	 * 
	 * crmIntentList:(意向客户列表). <br/>
	 * @author zhanghui
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/crmIntentList")
	public String crmIntentList(HttpServletRequest request,CrmIntentLoanUser entity){
		 CommonUtils.fillCompany(entity);
		 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		 entity.setPage(pageParams);
		 Page page =crmIntentLoanUserService.queryListPage(entity);
		 Map<String, Object> param=  new HashMap<String, Object>();
		 request.setAttribute("companyId", entity.getAppointCompanyId());
		 request.setAttribute("companys", companyList);
		 request.setAttribute("entity", entity);
		 request.setAttribute("page", page);
		 request.setAttribute("isAssign", ActionType.LIST.getKey());
		 request.setAttribute("productList", productService.queryProductByStatus("1"));
		 return "crm/crmIntentLoanUser";
	}
	
	/**
	 * 待分配列表
	 * crmIntentList:(意向客户指派列表). <br/>
	 * @author zhanghui
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/crmIntentAssignList")
	public String crmIntentAssignList(HttpServletRequest request,CrmIntentLoanUser entity){
		 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		 CommonUtils.fillCompany(entity);
		 //entity.setActionType(ActionType.ASSIGN.getKey());
		 //只查询待分配的
//		 entity.setBusinessStatus(BusinessStatus.BS1.getKey());
		 entity.setStatus(CustomerStatus.STATIS1.getKey());
		 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		 entity.setPage(pageParams);
		 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		/* List<LoanUnionCompany> companyList = null;
		 List<LoanDept> deptList = null;
		 Map<String, Object> param=  new HashMap<String, Object>();
		 if(null !=entity.getCompanyIdList() && entity.getCompanyIdList().size()>0){
			 param.clear();
			 LoanUnionCompany vo = new LoanUnionCompany();
			 vo.setCompanyIdList(entity.getCompanyIdList());
			 companyList = unionCompanyService.getCompanyList(vo);
			 param.put("status", "1");
			 deptList = systemManagerService.deptList(param,null);
		 
		 }else{
			 String companyId = entity.getCompanyId();
			 if(StringUtils.isNotEmpty(companyId)){
				 LoanUnionCompany vo = new LoanUnionCompany();
				 vo.setCompanyId(entity.getCompanyId());
				 companyList = unionCompanyService.getCompanyList(vo);
			 }
			param.clear();
			if(null != loanEmp.getLeDeptId()){
				param.put("ldDeptId", loanEmp.getLeDeptId());
				deptList = systemManagerService.deptList(param, null);
			}
		 }
		 request.setAttribute("companys", companyList);
		 request.setAttribute("depts", deptList);*/
		 /*if(null!=entity.getAppointDepId()){
			  param.clear();
				param.put("deptId", entity.getAppointDepId());
				param.put("status", "1");
				List<LoanGroup> groupList = systemManagerService.groupList(param,null);
				request.setAttribute("groups", groupList);
		 }*/
		 Page page =crmIntentLoanUserService.queryListPage(entity);
		 request.setAttribute("entity", entity);
		 request.setAttribute("page", page);
		 request.setAttribute("isAssign", ActionType.ASSIGN.getKey());
		 request.setAttribute("flag",ActionType.ASSIGNLIST.getKey());
		 request.setAttribute("fpOrzy", "fenpei");
		 request.setAttribute("LeEmpId", loanEmp.getLeEmpId());
		 request.setAttribute("companys", companyList);
		 request.setAttribute("productList", productService.queryProductByStatus("1"));
		 return "crm/crmIntentLoanUser";
	}
	
	/**
	 * 
	 * crmIntentList:(意向客户转移列表). <br/>
	 * @author zhanghui
	 * @param request
	 * @param entity 
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/crmIntentChangeList")
	public String crmIntentChangeList(HttpServletRequest request,CrmIntentLoanUser entity){
		 CommonUtils.fillCompany(entity);
		 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		 entity.setPage(pageParams);
		 entity.setActionType(ActionType.CHANGE.getKey());
		 Page page =crmIntentLoanUserService.queryListPage(entity);
		 request.setAttribute("entity", entity);
		 request.setAttribute("page", page);
		 request.setAttribute("isAssign", ActionType.CHANGE.getKey());
		 return "crm/crmIntentLoanUser";
	}
	
	/**
	 * 已分配列表
	 * crmIntentList:(意向客户待回访列表). <br/>
	 * @author zhanghui
	 * @param request
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/crmIntentFollowList")
	public String crmIntentFollowList(HttpServletRequest request,CrmIntentLoanUser entity){
		 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		 CommonUtils.fillCompany(entity);
		 List<UnionCompany> companyList = CommonUtils.getCompanyList(entity);
		 //只有客户所属团能访问
//		 entity.setActionType(ActionType.FOLLOW.getKey());
		 pageParams.setPageIndex(entity.getPageIndex()==null?1:entity.getPageIndex());
		 entity.setPage(pageParams);
		 entity.setStatus(CustomerStatus.STATIS2.getKey());
		 List<LoanDept> deptList = null;
		 Map<String, Object> param=  new HashMap<String, Object>();
//		 if(null != entity.getAppointDepId()){
		 Page page =crmIntentLoanUserService.queryListPage(entity);
//			param.put("ldDeptId", entity.getAppointDepId());
//			deptList = systemManagerService.deptList(param, null);
//		 }
//		 request.setAttribute("dept", deptList);
		 request.setAttribute("companys", companyList);
		 request.setAttribute("entity", entity);
		 request.setAttribute("page", page);
		 request.setAttribute("isAssign", ActionType.FOLLOW.getKey());
		 request.setAttribute("flag",ActionType.CHANGELIST.getKey());
		 request.setAttribute("fpOrzy", "zhuanyi");
		 request.setAttribute("LeEmpId", loanEmp.getLeEmpId());
		 request.setAttribute("productList", productService.queryProductByStatus("1"));
		 return "crm/crmIntentLoanUser";
	}
	
	@RequestMapping("/toAddCrmView")
	public String toAddCrmView(HttpServletRequest request,CrmIntentLoanUser entity){
		CommonUtils.fillCompany(entity);
		UnionCompany unionCompany=new UnionCompany();
		unionCompany.setLgUnionId(entity.getUnionId());
		unionCompany.setLgCompanyId("crmIntentLoanUser");
		List<UnionCompany> companys=CommonUtils.getBumpCompanyList(unionCompany);
		List<LoanParentProduct> productList = productService.queryProductByStatus("1");
		request.setAttribute("companys", companys);
		request.setAttribute("productList", productList);
		return "crm/addCrmIntent";
	}
	
	
	@RequestMapping("/saveCrmIntent")
	@ResponseBody
	public Map<String,Object> saveCrmIntent(HttpServletRequest request, CrmIntentLoanUser entity){
		 Map<String,Object>  reslut = new HashMap<String, Object>();
		 LoanEmp loanEmp=CommonUtils.getEmpFromSession();
		 reslut =crmIntentLoanUserService.save(loanEmp,entity);
		 return reslut;
	}
	
	/**
	 * 部门下的组别
	 * @param request
	 * @param deptId 部门id
	 * @return
	 */
	@RequestMapping("/getDeptOrGroup")
	@ResponseBody
	public Map<String,Object> getDeptOrGroup(HttpServletRequest request,
			@RequestParam(value = "deptId", required = false) Integer deptId){
		Map<String, Object> result=  new HashMap<String, Object>();
		try {
			Map<String, Object> param=  new HashMap<String, Object>();
			if(null!=deptId){
				param.put("deptId", deptId);
				param.put("status", "1");
				List<LoanGroup> groupList = systemManagerService.groupList(param,null);
				result.put("groupList", groupList);
			}
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			
		} catch (Exception e) {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "系统异常");
		}
		
		return result;
	}
	
	
	
	
	
	/**
	 * 导出意向客户
	 * @param request
	 * @param response
	 * @param entity
	 */
	@RequestMapping("/intentLoanUserExport")
     public void intentLoanUserExport( HttpServletRequest request,HttpServletResponse response,CrmIntentLoanUser entity){
    	 String fileName="意向客户列表";
    	 Map<String, String> titles=new LinkedHashMap<String, String>();
    	 CommonUtils.fillCompany(entity);
		 entity.setPage(null);
		 List<CrmIntentLoanUser> loanUserList = crmIntentLoanUserService.getLoanUserList(entity);
		 for(CrmIntentLoanUser loanUser:loanUserList){
			 if(StringUtils.isNoneEmpty(loanUser.getInterestRate())&&StringUtils.isNoneEmpty(loanUser.getRateUnit())){
				 loanUser.setInterestRate(loanUser.getInterestRate()+"%/"+loanUser.getRateUnit());
			 }
			 if(StringUtils.isNoneEmpty(loanUser.getDuration())&&StringUtils.isNoneEmpty(loanUser.getDurationUnit())){
				 loanUser.setDuration(loanUser.getDuration()+loanUser.getDurationUnit());
			 }
		 }
		 titles.put("city", "省市");
  	     titles.put("id", "客户编号");
  	     titles.put("name", "客户姓名");
  	     titles.put("mobile", "手机号");
  	     titles.put("loanAmount", "意向贷款金额(万元)");
  	     titles.put("duration", "借款期限");
  	     titles.put("interestRate", "借款利率");
  	     titles.put("productType", "业务类型");
//  	     titles.put("businessType1", "业务类型");
  	     titles.put("businessStatus1", "客户状态");
  	     titles.put("source1", "客户来源");
	     titles.put("grade1", "客户等级");
	     titles.put("appointCompanyName", "分公司");
  	     titles.put("appointDeptName", "事业部");
  	     titles.put("appointGroupName", "组别");
	     titles.put("appointEmpName", "客户经理");
	     titles.put("createrEmpName", "创建人");
	     titles.put("lastModifyTime", "最新跟进时间");
  	     titles.put("createTime", "添加时间");
  	     AnalyseTransExcel.downLoadExcel(request, response, fileName, loanUserList, CrmIntentLoanUser.class, titles);
  	     System.out.println(fileName+"下载完成"); 
    
	
	
	
	}
	
	/**
	 * 导入意向客户列表
	 * @param request
	 * @param filePath  文件路径
	 * @return
	 */
	@RequestMapping("/intentLoanUserImport")
	@ResponseBody
	public Map<String,Object> intentLoanUserImport(HttpServletRequest request,
			@RequestParam(value="file",required = true)MultipartFile file){
		Map<String, Object> result=  new HashMap<String, Object>();
		String AppointEmMobile = "";
		try {
			ImportExcel ei = new ImportExcel(file,0,0);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			System.out.println(ei.getLastDataRowNum());
			if(ei.getLastDataRowNum()>5002){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "导入数据量不能超过5000");
				return result;
			}
			System.out.println(ei.getDataRowNum());
			for (int i = ei.getDataRowNum()+1; i < ei.getLastDataRowNum(); i++) {
				CrmIntentLoanUser vo = new CrmIntentLoanUser();
				Row row = ei.getRow(i);
				System.out.print("行号:"+i);
				for (int j = 0; j < ei.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					String val = ExcelUtil.getValue(cell);
					if(j==0){
						String name = (String)val;
						if(StringUtils.isEmpty(name)){
							result.put(SystemConst.retCode, SystemConst.FAIL);
							result.put(SystemConst.retMsg, "客户姓名不能为空,行号为:"+(i+1));
							return result;
						}
						vo.setName(name);
					}else if(j==1){
						String mobile = (String)val;
						if(StringUtils.isEmpty(mobile)){
							result.put(SystemConst.retCode, SystemConst.FAIL);
							result.put(SystemConst.retMsg, "手机号不能为空,行号为:"+(i+1));
							return result;
						}
						CrmIntentLoanUser findByMobile = crmIntentLoanUserService.findByMobile(mobile);
						if(findByMobile != null){
							result.put(SystemConst.retCode, SystemConst.FAIL);
							result.put(SystemConst.retMsg, "该客户已存在,行号为:"+(i+1));
							return result;
						}
						vo.setMobile(mobile);
					}else if(j==2){
						vo.setLoanAmount(val);
					}else if(j==3){
						vo.setDuration((String)val);
					}else if(j==4){
						vo.setDurationUnit((String)val);
					}else if(j==5){
						vo.setInterestRate((String)val);
					}else if(j==6){
						vo.setRateUnit((String)val);
					}else if(j==7){
						String businessTypeName = (String)val;
						if(StringUtils.isNotEmpty(businessTypeName)){
							Integer businessType=null;
							switch(businessTypeName){
							case "房产抵押贷":
								businessType=1;
							    break;
							case "信用贷":
								businessType=2;
							    break;
							case "车辆抵押贷":
								businessType=3;
							    break;
							case "垫资过桥":
								businessType=4;
							    break;
							case "解查封":
								businessType=5;
							    break;
							case "其他":
								businessType=6;
							    break;  
							 default:
								 businessType=null;
							}
						vo.setBusinessType(businessType==null?"":businessType.toString());
					}
				}else if(j==8){
						String businessStatusName = (String)val;
						if(StringUtils.isNotEmpty(businessStatusName)){
							String businessStatus = "";
							switch(businessStatusName){
							case "初步接触":
								businessStatus = "01";
							    break;
							case "意向客户":
								businessStatus = "02";
							    break;
							case "跟进客户":
								businessStatus = "03";
							    break;
							case "面谈客户":
								businessStatus = "07";
							    break;
							case "签约客户":
								businessStatus = "04";
							    break;
							case "放弃客户":
								businessStatus = "05";
							    break;
							case "黑名单":
								businessStatus = "06";
							    break; 
							case "已报单":
								businessStatus = "08";
							    break; 
							 default:
								 businessStatus = "";
							    break;
							}
							vo.setBusinessStatus(businessStatus);	
						}
					}else if(j==9){
						String sourceName= (String)val;
						if(StringUtils.isNotEmpty(sourceName)){
						String source="";
							switch(sourceName){
							case "陌拜":
								source="01";
							    break;
							case "电销":
								source="02";
							    break;
							case "转介绍":
								source="03";
							    break;
							case "网络":
								source="04";
							    break;
							case "报刊":
								source="05";
							    break;
							case "其他":
								source="06";
							    break;
							case "中视天脉":
								source="07";
								break;
							default:
								source = "";
							    break;
							}
							vo.setSource(source);
						}
					}else if(j==10){
						String grageName=(String)val;
						if(StringUtils.isNotEmpty(grageName)){
							String grage="";
							switch(grageName){
							case "普通客户":
								grage="01";
							    break;
							case "重要客户":
								grage="02";
							    break;
							case"重要紧急":
								grage="03";
							    break;
							 default:
								 grage="";
							    break;
							}
							vo.setGrade(grage);
						}
					}else if(j==11){
						String companyNameAndId = (String)val;
						if(StringUtils.isNotEmpty(companyNameAndId)){
							String[] split = companyNameAndId.split("—");
							String appCompanyId = split[0];
							Map<String,Object> param = new HashMap<String,Object>();
							param.put("companyId", appCompanyId);
							List<LoanUnionCompany> queryList = unionCompanyService.queryList(param);
							if(null == queryList || queryList.size()<0){
								result.put(SystemConst.retCode, SystemConst.FAIL);
								result.put(SystemConst.retMsg, "分公司不存在,行号为:"+(i+1));
								return result;
							}
							vo.setAppointCompanyId(appCompanyId);
							vo.setAppointCompanyName(queryList.get(0).getCompanyName());
							vo.setCreaterUnionId(queryList.get(0).getUnionId());
							vo.setAppointUnionId(queryList.get(0).getUnionId());
						}else{
							result.put(SystemConst.retCode, SystemConst.FAIL);
							result.put(SystemConst.retMsg, "分公司不能为空,行号为:"+(i+1));
							return result;
						}
					}else if(j==12){
						vo.setAppointEmpName((String)val);
					}else if(j==13){
						//根据手机号查询客户经理
						 AppointEmMobile = (String)val;
						if(StringUtils.isNotEmpty(AppointEmMobile)){
							List<LoanEmp> empList = systemManagerService.queryEmpByMobile(AppointEmMobile);
							if(null ==empList || empList.size()<=0){
								result.put(SystemConst.retCode, SystemConst.FAIL);
								result.put(SystemConst.retMsg, "客户经理手机号不存在,行号为:"+(i+1));
								return result;
							}else{
								if(!empList.get(0).getBranchCompanyId().equals(vo.getAppointCompanyId())){
									result.put(SystemConst.retCode, SystemConst.FAIL);
									result.put(SystemConst.retMsg, "客户经理所在分公司与选择的分公司不一致,行号为:"+(i+1));
									return result;
								}
							}
							vo.setStatus(CustomerStatus.STATIS2.getKey());
						}else{
							vo.setStatus(CustomerStatus.STATIS1.getKey());
						}
					}else if(j==14){
						String date = (String)(val);
						if(StringUtils.isNotEmpty(date)){
							vo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
						}
					}
					System.out.print("\t"+val+"\t ");
				}
				System.out.print("\t"+vo+"\t ");
				System.out.print("\n");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("vo", vo);
				map.put("mobile", AppointEmMobile);
				list.add(map);
			}
		    result = crmIntentLoanUserService.saveList(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "系统异常");
		}
		return result;
	}
	
	/**
	 * 拉取碰碰旺意向客户，保存到小贷意向客户
	 * @author chenyinhui
	 * @param 
	 * @return
	 */
	@RequestMapping("/intentUserPullTask")
	@ResponseBody
	public String intentUserPullTask(){
		//查询碰碰旺意向客户列表
		ChannelApply entity=new ChannelApply();
		entity.setField1("00");
		List<ChannelApply> list=channelApplyApi.queryList(entity);
		if(null==list||list.size()==0){
			LOGGER.info("无未推送意向客户");
			return "无未推送意向客户";
		}
		//可分配客户经理列表
		List<Map<String, Object>> empList=AppointEmp.getList();
		if(null==empList||empList.size()==0){
			LOGGER.info("无可分配客户经理");
			return "无可分配客户经理";
		}
		try {
			crmIntentLoanUserService.intentUserPullTask(list, empList);
		} catch (Exception e) {
			LOGGER.error("保存碰碰旺意向客户失败：",e);
		}
		return "success";
	}
	
	/**
	 * 接收碰碰旺推送的意向客户
	 * 保存到小贷意向客户表
	 * @author chenyinhui
	 * @param 
	 * @return
	 */
	@RequestMapping("/savePpwIntentUser")
	@ResponseBody
	public void savePpwIntentUser(HttpServletRequest request, @RequestBody String intentUser){
		if(StringUtil.isEmpty(intentUser)){
			LOGGER.info("无碰碰旺推送意向客户");
			return;
		}
		//碰碰旺推送的意向客户
		ChannelApply entity=JSON.parseObject(intentUser,ChannelApply.class);
		if(null==entity){
			LOGGER.info("碰碰旺推送意向客户信息错误");
			return;
		}
		List<ChannelApply> list=new ArrayList<ChannelApply>();
		list.add(entity);
		//可分配客户经理列表
		List<Map<String, Object>> empList=AppointEmp.getList();
		if(null==empList||empList.size()==0){
			LOGGER.info("无可分配客户经理");
			return;
		}
		crmIntentLoanUserService.intentUserPullTask(list, empList);
	}
	
}