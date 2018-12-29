/**
 * Project Name:loan-service
 * File Name:CrmIntentLoanUserRecordServiceImpl.java
 * Package Name:com.lhhs.loan.service.crm.impl
 * Date:2017年11月13日下午2:51:43
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.crm.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.DeptApi;
import com.lhhs.bump.api.UserApi;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.enums.crm.ActionType;
import com.lhhs.loan.common.enums.crm.CustomerStatus;
import com.lhhs.loan.common.service.CrudService;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.CrmIntentLoanUserRecordMapper;
import com.lhhs.loan.dao.LoanDeptMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.CrmIntentLoanUserRecord;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserRecordService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;

/**
 * 回访记录管理service实现
 * @see
 */
@Service
public class CrmIntentLoanUserRecordServiceImpl extends CrudService<CrmIntentLoanUserRecordMapper,CrmIntentLoanUserRecord> implements CrmIntentLoanUserRecordService {
	private static final Logger LOGGER = Logger.getLogger(CrmIntentLoanUserRecordServiceImpl.class);
	@Autowired
	private CrmIntentLoanUserService crmIntentLoanUserService;
	
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	@Autowired
	private CrmIntentLoanUserRecordMapper crmIntentLoanUserRecordMapper;
	@Autowired
	private SystemManagerService systemManagerService;
	@Autowired
	private LoanDeptMapper loanDeptMapper;
	@Reference
	private UserApi userApi;
	@Reference
	private DeptApi deptApi;
	
	@Override
	public int savefollowRecord(CrmIntentLoanUserRecord entity) {
		Integer parentId=entity.getParentId();
		CrmIntentLoanUser user=new CrmIntentLoanUser();
		//查询意向客户信息
		CrmIntentLoanUser tempUser=crmIntentLoanUserService.get(parentId.toString());
		if(entity.getLeEmpId()!=null){
			entity.setLastUser(entity.getLeEmpId().toString());
		}
		entity.setVisitCount(tempUser.getVisitCount()+1);
		entity.setLastModifyTime(new Date());
		//复制loanPayPlan属性
		BeanUtils.copyProperties(entity,user);
		user.setId(parentId);
		user.setLastModifyTime(new Date());
		user.setRateUnit(entity.getRateUnit());
		user.setDurationUnit(entity.getDurationUnit());
		user.setVisitTime(new Date());
		if(StringUtils.isNoneEmpty(entity.getBusinessStatus())){
			user.setBusinessStatus(entity.getBusinessStatus());
		}
		//意向客户创建人信息
		entity.setCreaterUnionId(tempUser.getCreaterUnionId());
		entity.setCreaterCompanyId(tempUser.getCreaterCompanyId());
		entity.setCreaterDepId(tempUser.getCreaterDepId());
		entity.setCreaterGroupId(tempUser.getCreaterGroupId());
		//回访人信息
		if(null != entity.getLeEmpId()){
			entity.setAppointEmpId(entity.getLeEmpId().toString());
		}
		entity.setAppointEmpName(entity.getLeEmpName());
		entity.setAppointCompanyId(entity.getCompanyId());
		entity.setAppointUnionId(entity.getUnionId());
		entity.setAppointDepId(entity.getDepId());
		entity.setField9(ActionType.FOLLOW.getId());
		entity.setField9(entity.getActionType());
		entity.setAppointDepId(tempUser.getAppointDepId());
		entity.setAppointOrg(tempUser.getAppointOrg());
		if(null!=tempUser.getBusinessType()&&!"".equals(tempUser.getBusinessType())){
			entity.setBusinessType(tempUser.getBusinessType());
		}
		if(StringUtils.isNotEmpty(tempUser.getSource())){
			entity.setSource(tempUser.getSource());
		}
		//更新意向客户表
		int flag=0;
		if(entity.isNeedUpdate()){
		   flag=crmIntentLoanUserService.update(user);
		}
		//插入跟踪记录
		if("follow".equals(entity.getFollowTypeSed())){
		   flag = save(entity);
		}
		return flag;
	}
	/**
	 * 
	 * 分配和转移意向客户
	 */
	@Transactional
	public int assignRecord(CrmIntentLoanUserRecord entity) {
		//查询指定的客户经理
		String empId=entity.getAppointEmpId();
		User loanEmp=userApi.get(empId);
		Dept loanDept =null;
		if(null!=loanEmp.getDeptId()&&!"".equals(loanEmp.getDeptId())){
			 loanDept=deptApi.get(loanEmp.getDeptId());
		}
		if(StringUtils.isNotEmpty(empId)){
			if(null != loanEmp){
				//设置指定的客户经理编号、公司、集团、部门和组
				entity.setAppointEmpId(loanEmp.getUserId().toString());
				entity.setAppointEmpName(loanEmp.getStaffName());
				entity.setAppointCompanyId(loanEmp.getCompanyId());
				
				entity.setAppointUnionId(loanEmp.getCompanyId());
				//设置组，如果不为空设置AppointOrg
//				if(loanEmp.getGroupId()!=null){
//					entity.setAppointGroupId(loanEmp.getGroupId().toString());
//					entity.setAppointOrg(loanEmp.getGroupId().toString());
//				}else{
					entity.setAppointGroupId("");
					entity.setAppointOrg("");
//				}
				//设置部门
				if(loanEmp.getDeptId()!=null){
					entity.setAppointDepId(loanEmp.getDeptId().toString());
					entity.setAppointOrg(loanEmp.getDeptId().toString());
				}else{
					entity.setAppointDepId("");
					entity.setAppointOrg("");
				}
			}else{
				throw new RuntimeException("客户经理不存在");
			}
		}else{//设置状态为待分配
			entity.setAppointDepId("");
			entity.setAppointEmpId("");
			entity.setAppointEmpName("");
			entity.setAppointGroupId("");
			entity.setAppointOrg("");
			entity.setStatus(CustomerStatus.STATIS1.getKey());
		}
		if(entity.getLeEmpId()!=null){
			entity.setLastUser(entity.getLeEmpId().toString());
		}
		entity.setLastModifyTime(new Date());
		//指派后变更状态、转移不变更状态
		if(ActionType.ASSIGN.getKey().equals(entity.getActionType())){
			//待回访
//			entity.setBusinessStatus(BusinessStatus.BS2.getKey());
			if(StringUtils.isNotEmpty(empId)){
				entity.setStatus(CustomerStatus.STATIS2.getKey());
			}else{
				entity.setStatus(CustomerStatus.STATIS1.getKey());
			}
		}
		//更新意向客户表
		if(null!=loanDept){
			if(null!=loanDept.getParentId()){
				entity.setAppointDepId(loanDept.getParentId().toString());
			}else{
				entity.setAppointDepId(String.valueOf(loanEmp.getDeptId()));
			}
			entity.setAppointOrg(String.valueOf(loanEmp.getDeptId()));
		}
		int flag=crmIntentLoanUserService.update(entity);
		//添加回访记录
		entity.setNeedUpdate(false);
		if(flag>0){
			CrmIntentLoanUserRecord vo = new CrmIntentLoanUserRecord();
			CrmIntentLoanUser crmIntentLoanUser = crmIntentLoanUserService.get(String.valueOf(entity.getId()));
			BeanUtils.copyProperties(crmIntentLoanUser,vo);
			if(null != entity.getLeEmpId()){
				vo.setAppointEmpId(entity.getLeEmpId().toString());
			}
			/**
			vo.setAppointEmpName(entity.getLeEmpName());
			vo.setAppointCompanyId(entity.getCompanyId());
			vo.setAppointUnionId(entity.getUnionId());
			vo.setAppointDepId(entity.getDepId());
			**/
			String key = ActionType.getKey("", entity.getActionType());
			vo.setField9(key);
			vo.setParentId(entity.getId());
			vo.setId(null);
			if(null!=loanDept){
				if(null!=loanDept.getParentId()){
					vo.setAppointDepId(loanDept.getParentId().toString());
				}else{
					vo.setAppointDepId(String.valueOf(loanEmp.getDeptId()));
				}
				vo.setAppointOrg(String.valueOf(loanEmp.getDeptId()));
			}
			
			int save = this.save(vo);//插入跟踪记录
			
		}
		
		return flag;
	}

	/**
	 * 查询回访记录
	 */
	public List<CrmIntentLoanUserRecord> queryList(Integer parentId) {
		if(parentId==null)return null;
		CrmIntentLoanUserRecord temp=new CrmIntentLoanUserRecord();
		temp.setParentId(parentId);
		// TODO Auto-generated method stub
		return queryList(temp);
	}

	/**
	 * 查询回访记录（小贷三期）
	 */
	@Override
	public Page queryInviteRecord(CrmIntentLoanUserRecord entity) {
		Page page = entity.getPage();
		page.setResultList(crmIntentLoanUserRecordMapper.queryInviteRecord(entity));
		page.setTotalCount(crmIntentLoanUserRecordMapper.queryInviteRecordCount(entity));
		return page;
	}
	
	/**
	 * 查询回访详情（小贷三期）
	 */
	@Override
	public Page queryRecordInfo(CrmIntentLoanUserRecord entity) {
		Page page = entity.getPage();
//		List<CrmIntentLoanUserRecord> resultList = getRowSpan(crmIntentLoanUserRecordMapper.queryRecordInfo(entity));
		List<CrmIntentLoanUserRecord> resultList = crmIntentLoanUserRecordMapper.queryRecordInfo(entity);
		page.setResultList(resultList);
		page.setTotalCount(crmIntentLoanUserRecordMapper.queryRecordInfoCount(entity));
		return page;
	}
	
	@Override
	public List<CrmIntentLoanUserRecord> getRecordInfoList(CrmIntentLoanUserRecord entity){
		return crmIntentLoanUserRecordMapper.queryRecordInfo(entity);
	}
	
	
	
	/**
	 * 统计相同客户的数量
	 * @param detailList
	 * @return
	 */
	private List<CrmIntentLoanUserRecord> getRowSpan(List<CrmIntentLoanUserRecord> detailList) {
		int rowSpan = 0;//列宽
		if(null != detailList) {
			//遍历详情记录
			for(int i = 0; i < detailList.size(); i++) {
				String name = detailList.get(i).getName();
				if(!StringUtil.isEmpty(name)) {
					//按照姓名累加rowSpan
					for(int j = 0; j < detailList.size(); j++) {
						if(name .equals(detailList.get(j).getName())) {
							rowSpan++;
							if(j > i) {
								//名字重置为""，作为不设置列宽的标识
								detailList.get(j).setName("");
							}
						}
					}
					detailList.get(i).setRowSpan(rowSpan);//设置列宽
					rowSpan = 0;
				}
			}
		}
		return detailList;
	}
	
	/**
	 * 查询详情列表
	 */
	@Override
	public List<CrmIntentLoanUserRecord> queryInfoList(CrmIntentLoanUserRecord entity) {
		return crmIntentLoanUserRecordMapper.queryRecordInfo(entity);
	}
}

