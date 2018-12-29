/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserServiceImpl.java
 * Package Name:com.lhhs.loan.service.crm.impl
 * Date:2017年11月14日上午10:19:27
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserServiceImpl.java
 * Package Name:com.lhhs.loan.service.crm.impl
 * Date:2017年11月14日上午10:19:27
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.service.crm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.ChannelApplyApi;
import com.lhhs.bump.api.DeptApi;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.ChannelApply;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.crm.CustomerSource;
import com.lhhs.loan.common.jedis.JedisComponent;
import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.CrmIntentLoanUserMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanDept;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanGroup;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;

/**
 * ClassName:CrmIntentLoanUserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月14日 上午10:19:27 <br/>
 * 
 * @author zhanghui
 * @version
 * @since JDK 1.8
 * @see
 */
@Service("crmIntentLoanUserService")
public class CrmIntentLoanUserServiceImpl extends CrudService<CrmIntentLoanUserMapper, CrmIntentLoanUser>
		implements CrmIntentLoanUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(CrmIntentLoanUserServiceImpl.class);
	
	@Autowired
	private CrmIntentLoanUserMapper crmIntentLoanUserMapper;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private LoanDeptMapper loanDeptMapper;
	@Reference
	private ChannelApplyApi channelApplyApi;
	@Autowired
	private JedisComponent jedisComponent;
	@Reference
	private UserApi userApi;
	@Reference
	private DeptApi deptApi;
	
	@Override
	public Page queryListPage(CrmIntentLoanUser entity) {
		List<CrmIntentLoanUser> crmIntentLoanUserList = crmIntentLoanUserMapper.queryList(entity);
		List<LoanDept> deptList = systemManagerService.deptList(new HashMap<String,Object>(),null);
		List<LoanGroup> groupList = systemManagerService.groupList(new HashMap<String,Object>(),null);
		for (CrmIntentLoanUser crmIntentLoanUser : crmIntentLoanUserList) {
			if(null!=crmIntentLoanUser.getMobile()){
				String encriptionTel = crmIntentLoanUser.getMobile().substring(0, 4)+"****"+crmIntentLoanUser.getMobile().substring(7, 11);
				crmIntentLoanUser.setMobile(encriptionTel);
			}
			if(deptList!=null){
				for(LoanDept dept:deptList){
					if(crmIntentLoanUser.getAppointDepId()!=null&&dept.getLdDeptId()!=null&&crmIntentLoanUser.getAppointDepId().equals(dept.getLdDeptId())){
						crmIntentLoanUser.setAppointDeptName(dept.getLdName());
					}
				}
			}
			if(groupList!=null){
				for(LoanGroup group:groupList){
					if(crmIntentLoanUser.getAppointGroupId()!=null&&group.getLgGroupId()!=null&&crmIntentLoanUser.getAppointGroupId().equals(group.getLgGroupId())){
						crmIntentLoanUser.setAppointGroupName(group.getLgName());
					}
				}
			}
		}
		Page page = entity.getPage();
		page.setResultList(crmIntentLoanUserList);
		page.setTotalCount(crmIntentLoanUserMapper.queryCount(entity));
		return page;
	}

	@Override
	public List<CrmIntentLoanUser> getLoanUserList(CrmIntentLoanUser entity) {
		return crmIntentLoanUserMapper.queryList(entity);
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> saveList(List<Map<String,Object>> list) {
		Map<String, Object> result= new HashMap<String, Object>();
		if(null!=list&&list.size()>0){
			int count=0;
			for (Map<String, Object> map : list) {
				CrmIntentLoanUser crmIntentLoanUser = (CrmIntentLoanUser) map.get("vo");
				String mobile = (String) map.get("mobile");
				LoanEmp loanEmp = null;
				if(StringUtils.isNotEmpty(mobile)){
					List<LoanEmp> queryEmpByMobile = systemManagerService.queryEmpByMobile(mobile);
					if(null != queryEmpByMobile && queryEmpByMobile.size()>0){
						loanEmp = queryEmpByMobile.get(0);
						crmIntentLoanUser.setAppointEmpId(String.valueOf(loanEmp.getLeEmpId()));
					}
				}
				Map<String, Object> returnMap = this.save(loanEmp, crmIntentLoanUser);
				if(returnMap.get(SystemConst.retCode).equals(SystemConst.SUCCESS)){
					count++;
				}
			}
			if(count==list.size()){
				result.put(SystemConst.retCode, SystemConst.SUCCESS);
				result.put(SystemConst.retMsg, "导入成功");
			}else{
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "导入失败");
			}
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "导入数据不可为空");
		}
		return result;
	}
	
	
	
	
	@Override
	public Map<String, Object> save(LoanEmp loanEmp, CrmIntentLoanUser entity) {
		Map<String, Object> reslut = new HashMap<String, Object>();
		if (entity == null) {
			reslut.put(SystemConst.retCode, SystemConst.FAIL);
			reslut.put(SystemConst.retMsg, "添加失败！");
			return reslut;
		}
		CrmIntentLoanUser crmIntent = crmIntentLoanUserMapper.findbyMobile(entity.getMobile());
		if (crmIntent != null) {
			reslut.put(SystemConst.retCode, SystemConst.FAIL);
			reslut.put(SystemConst.retMsg, "该客户已存在添加失败！");
			return reslut;
		}
		if (loanEmp != null) {
			LoanDept loanDept=loanDeptMapper.selectByPrimaryKey(loanEmp.getLeDeptId());
			entity.setCreaterEmpId(String.valueOf(loanEmp.getLeEmpId()));
			entity.setCreaterEmpName(loanEmp.getLeStaffName());
			entity.setCreaterUnionId(loanEmp.getCompanyId());
			entity.setCreaterCompanyId(loanEmp.getBranchCompanyId());
			if(loanEmp.getLeDeptId()!=null){
				if(null!=loanDept&&null!=loanDept.getParentId()){
					entity.setCreaterDepId(loanDept.getParentId().toString());
				}else{
					entity.setCreaterDepId(String.valueOf(loanEmp.getLeDeptId()));
				}
			}
			if(loanEmp.getLeGroupId()!=null){
				entity.setCreaterGroupId(String.valueOf(loanEmp.getLeGroupId()));
			}
			entity.setVisitCount(0);
			if (StringUtils.isEmpty(entity.getAppointCompanyId())) {
				entity.setAppointEmpId(String.valueOf(loanEmp.getLeEmpId()));
				entity.setAppointEmpName(loanEmp.getLeStaffName());
				entity.setAppointUnionId(loanEmp.getCompanyId());
				entity.setAppointCompanyId(loanEmp.getBranchCompanyId());
				if(loanEmp.getLeDeptId()!=null){
					if(null!=loanDept&&null!=loanDept.getParentId()){
						entity.setAppointDepId(loanDept.getParentId().toString());
					}else{
						entity.setAppointDepId(String.valueOf(loanEmp.getLeDeptId()));
					}
				}
				
				if(loanEmp.getLeGroupId()!=null){
					entity.setAppointGroupId(String.valueOf(loanEmp.getLeGroupId()));
				}
				
			} else {
				if (entity.getAppointCompanyId().equals(loanEmp.getBranchCompanyId())) {
					entity.setAppointEmpId(String.valueOf(loanEmp.getLeEmpId()));
					entity.setAppointEmpName(loanEmp.getLeStaffName());
					entity.setAppointUnionId(loanEmp.getCompanyId());
					entity.setAppointCompanyId(loanEmp.getBranchCompanyId());
					if(loanEmp.getLeDeptId()!=null){
						if(null!=loanDept&&null!=loanDept.getParentId()){
							entity.setAppointDepId(loanDept.getParentId().toString());
						}else{
							entity.setAppointDepId(String.valueOf(loanEmp.getLeDeptId()));
						}
					}
							
					if(loanEmp.getLeGroupId()!=null){
						entity.setAppointGroupId(String.valueOf(loanEmp.getLeGroupId()));
					}
					entity.setStatus("03");
				}

			}
			//设置权限过滤组织机构
			entity.setCreaterOrg(entity.getCreaterDepId());
//			entity.setAppointOrg(entity.getAppointDepId());
			entity.setAppointOrg(String.valueOf(loanEmp.getLeDeptId()));
			if(!StringUtils.isEmpty(entity.getCreaterGroupId())){
				entity.setCreaterOrg(entity.getCreaterGroupId());
			}
			if(!StringUtils.isEmpty(entity.getAppointGroupId())){
				entity.setAppointOrg(entity.getAppointGroupId());
			}
		}
		int count = save(entity);
		if (count == 0) {
			reslut.put(SystemConst.retCode, SystemConst.FAIL);
			reslut.put(SystemConst.retMsg, "添加失败！");
			return reslut;
		}
		reslut.put(SystemConst.retCode, SystemConst.SUCCESS);
		reslut.put(SystemConst.retMsg, "添加成功！");
		return reslut;
	}
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public CrmIntentLoanUser get(CrmIntentLoanUser entity) {
		if(entity==null ||entity.getId()==null)return null;
		Integer id=entity.getId();
		CrmIntentLoanUser temp=new CrmIntentLoanUser();
		temp.setId(id);
		//调用实体查询只设置ID
		entity=super.get(temp);
//		Map<String,String> companyMap=unionCompanyService.queryAllCompanyMap();
//		if(!StringUtils.isEmpty(entity.getCreaterCompanyId())){
//			entity.setCreaterCompanyName(companyMap.get(entity.getCreaterCompanyId()));
//		}
//		if(!StringUtils.isEmpty(entity.getAppointCompanyId())){
//			entity.setAppointCompanyName(companyMap.get(entity.getAppointCompanyId()));
//		}
		return entity;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.lhhs.loan.service.crm.CrmIntentLoanUserService#findByMobile(java.lang.String)
	 */
	 
	@Override
	public CrmIntentLoanUser findByMobile(String mobile) {
		return crmIntentLoanUserMapper.findbyMobile(mobile);
	}

	@Override
	public Page getcrmIntentUserCountList(CrmIntentLoanUser entity) {
		List<CrmIntentLoanUser> list =  crmIntentLoanUserMapper.getcrmIntentUserCountList(entity);
		Page page = entity.getPage();
		page.setResultList(list);
		page.setTotalCount(crmIntentLoanUserMapper.getcrmIntentUserCountNum(entity));
		return page;
	}

	@Override
	public Map<String, Object> getVariousTotalCount(CrmIntentLoanUser entity) {
		return crmIntentLoanUserMapper.getVariousTotalCount(entity);
	}

	
	@Override
	public Map<String, Object> getCrmAppointCountByTime(Date time,String timeUnit,CrmIntentLoanUser entity) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			entity.setTimeUnit(timeUnit);
			Date endTime = new Date();
			if("周".equals(timeUnit)){
				 List<Map<String, Object>> returnDealList = new ArrayList<Map<String, Object>>();
				 List<Map<String, Object>> returnIntentList = new ArrayList<Map<String, Object>>();
				 List<Map<String, Object>> returnMianShenList = new ArrayList<Map<String, Object>>();
				 //第一周
				 entity.setWeekNum("04");
				 addWeekDate(entity,returnDealList,returnIntentList,returnMianShenList);
				 //第二周
				 entity.setWeekNum("03");
				 addWeekDate(entity,returnDealList,returnIntentList,returnMianShenList);
			     //第三周
				 entity.setWeekNum("02");
				 addWeekDate(entity,returnDealList,returnIntentList,returnMianShenList);
			     //第四周
				 entity.setWeekNum("01");
				 addWeekDate(entity,returnDealList,returnIntentList,returnMianShenList);
				 returnMap.put("dealCountList", returnDealList);
				 returnMap.put("intentCountList", returnIntentList);
				 returnMap.put("mianshenCountList", returnMianShenList);
			}else{
				 entity.setBeginingTime(time);
				 entity.setEndingTime(endTime);
				 List<Map<String, Object>> dealCountList = crmIntentLoanUserMapper.getDealCountByTime(entity);
				 List<Map<String, Object>> intentCountList  = crmIntentLoanUserMapper.getIntentCountByTime(entity);
				 List<Map<String, Object>> mianshenCountList = crmIntentLoanUserMapper.getMianshenCountByTime(entity);
				 returnMap.put("dealCountList", dealCountList);
				 returnMap.put("intentCountList", intentCountList);
				 returnMap.put("mianshenCountList", mianshenCountList);
			 }
			returnMap.put(SystemConst.retCode, SystemConst.SUCCESS);
			returnMap.put(SystemConst.retMsg, "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put(SystemConst.retCode, SystemConst.FAIL);
			returnMap.put(SystemConst.retMsg, "系统异常"); 
		}
		return returnMap;
	
	}
	
	
	public void addWeekDate(CrmIntentLoanUser entity, List<Map<String, Object>> returnDealList,List<Map<String, Object>> returnIntentList,List<Map<String, Object>> returnMianShenList ){
		 List<Map<String, Object>> week1DealList = crmIntentLoanUserMapper.getDealCountByTime(entity);
		 List<Map<String, Object>> week1IntentList = crmIntentLoanUserMapper.getIntentCountByTime(entity);
		 List<Map<String, Object>> week1MianshenList = crmIntentLoanUserMapper.getMianshenCountByTime(entity);
		 returnDealList.addAll(week1DealList);
		 returnIntentList.addAll(week1IntentList);
		 returnMianShenList.addAll(week1MianshenList);
	}
	

	@Override
	public List<CrmIntentLoanUser> getcrmIntentUserCountExportList(CrmIntentLoanUser entity) {
		entity.setPage(null);
		List<CrmIntentLoanUser> list =  crmIntentLoanUserMapper.getcrmIntentUserCountList(entity);
		return list;
	}

	@Override
	public void intentUserPullTask(List<ChannelApply> list, List<Map<String, Object>> empList) {
		CrmIntentLoanUser intentUser=null;
		int num=-1;
		for(int i=0;i<list.size();i++){
			ChannelApply temp=list.get(i);
			//渠道编号不为空，且不是“鹏全融通”的渠道编号
			if(StringUtil.isNotEmpty(temp.getChannelNo())&&!temp.getChannelNo().equals("ndj2kcz6iw0omuu79762")){
				if(empList.size()==0){
					logger.info("无可分配客户经理,枚举中配置的客户经理手机号错误，或客户经理已被禁用！");
					break;
				}
				//判断意向客户手机号是否存在，存在就跳出本次循环
				CrmIntentLoanUser crmIntent = crmIntentLoanUserMapper.findbyMobile(temp.getMobile());
				if(null!=crmIntent){
					temp.setField1("01");
					channelApplyApi.update(temp);
					logger.info("该手机号意向客户已存在："+temp.getMobile());
					continue;
				}
				//获取redis中上次分配意向客户的客户经理的手机号
				String mobile=jedisComponent.getValue("appoint_emp_mobile");
				String mobile2=null;
				if(StringUtil.isNotEmpty(mobile)){
					for(int j=0;j<empList.size();j++){
						if(mobile.equals(empList.get(j).get("key"))){
							if((j+1)==empList.size()){
								num=0;
								mobile2=empList.get(0).get("key").toString();
							}else{
								num=j+1;
								mobile2=empList.get(j+1).get("key").toString();
							}
							break;
						}
					}
					if(StringUtil.isNotEmpty(mobile2)){
						mobile=mobile2;
					}else{
						num=0;
						mobile=empList.get(0).get("key").toString();
					}
				}else{
					num=0;
					mobile=empList.get(0).get("key").toString();
				}
				//redis存储本次分配意向客户的客户经理手机号
				jedisComponent.set("appoint_emp_mobile", mobile);
				User user=new User();
				user.setMobile(mobile);
				user.setStatus("03");
				user=userApi.get(user);
				if(null!=user){
					Dept dept=deptApi.get(user.getDeptId());
					intentUser=new CrmIntentLoanUser();
					intentUser.setMobile(temp.getMobile());
					intentUser.setName(temp.getName());
					intentUser.setSex(Integer.valueOf(temp.getSex()));
					intentUser.setCreaterEmpId(user.getUserId().toString());
					intentUser.setCreaterEmpName(user.getStaffName());
					intentUser.setCreaterUnionId(user.getUnionId());
					intentUser.setCreaterCompanyId(user.getCompanyId());
					intentUser.setAppointEmpId(user.getUserId().toString());
					intentUser.setAppointEmpName(user.getStaffName());
					intentUser.setAppointUnionId(user.getUnionId());
					intentUser.setAppointCompanyId(user.getCompanyId());
					if(null!=dept&&null!=dept.getParentId()&&dept.getParentId().intValue()!=0){
						intentUser.setCreaterDepId(dept.getParentId().toString());
						intentUser.setCreaterGroupId(user.getDeptId());
						intentUser.setAppointDepId(dept.getParentId().toString());
						intentUser.setAppointGroupId(user.getDeptId());
					}else{
						intentUser.setCreaterDepId(user.getDeptId());
						intentUser.setAppointDepId(user.getDeptId());
					}
					intentUser.setCreaterOrg(user.getDeptId());
					intentUser.setAppointOrg(user.getDeptId());
					intentUser.setStatus("03");
					intentUser.setVisitCount(0);
					intentUser.setCreateTime(temp.getApplyTime());
					intentUser.setLastUser(user.getUserId().toString());
					intentUser.setLastModifyTime(new Date());
					intentUser.setSource(CustomerSource.CS7.getKey());
					intentUser.setGrade("01");
					intentUser.setField1(temp.getChannelNo());
					intentUser.setField2(temp.getChannelName());
					int count=crmIntentLoanUserMapper.insertSelective(intentUser);
					if(count==0){
						logger.info("保存碰碰旺意向客户到小贷系统失败：手机号为"+intentUser.getMobile());
					}else{
						temp.setField1("01");
						channelApplyApi.update(temp);
					}
				}else{
					logger.info("枚举中配置的手机号无法查询到员工信息，或员工已被禁用：手机号为"+mobile);
					i-=1;
					empList.remove(num);
				}
			}
		}
	}

}
