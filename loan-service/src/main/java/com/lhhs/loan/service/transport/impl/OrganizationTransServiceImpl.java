/**
 * Project Name:loan-service
 * File Name:OrganizationTransServiceImpl.java
 * Package Name:com.lhhs.loan.service.transport.impl
 * Date:2017年6月29日上午9:00:23
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanBankMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanInvestCustomerInfoMapper;
import com.lhhs.loan.dao.LoanOrgSupportAreasMapper;
import com.lhhs.loan.dao.LoanOrganizationMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanBank;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;
import com.lhhs.loan.dao.domain.LoanOrganization;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.transport.OrganizationTransService;

/**
 * ClassName:OrganizationTransServiceImpl <br/>
 * Function: 与碰碰旺系统对接组织机构信息<br/>
 * Reason:   <br/>
 * Date:     2017年6月29日 上午9:00:23 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class OrganizationTransServiceImpl implements OrganizationTransService {
	private static final Logger LOGGER = Logger.getLogger(OrganizationTransServiceImpl.class);
	
	@Autowired
	private LoanOrganizationMapper loanOrganizationMapper;
	@Autowired
	private LoanOrgSupportAreasMapper loanOrgSupportAreasMapper;
	@Autowired
	private LoanCustomerBaseInfoMapper loanCustomerBaseInfoMapper;
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private LoanInvestCustomerInfoMapper loanInvestCustomerInfoMapper;
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanBankMapper loanBankMapper;
	
	/**
	 *  保存或者更新组织机构信息和机构所支持的城市的信息
	 * @see com.lhhs.loan.service.transport.OrganizationTransService#saveOrganizationInfo(java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveOrganizationInfo(String org) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StrUtils.isNullOrEmpty(org)){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");//参数不能为空
			return result;
		}
		LoanOrganization orgInfo = JSON.parseObject(org, LoanOrganization.class);
		if(null==orgInfo||null==orgInfo.getOrgId()||StrUtils.isNullOrEmpty(orgInfo.getOrgName())){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u5408\u6cd5");//参数不合法
			return result;
		}
		LoanOrganization temp = loanOrganizationMapper.selectByOrgName(orgInfo.getOrgName());
		int i=0;
		boolean areaFlag = true;
		if(temp!=null){//企业机构表存在
			//--------------操作客户基本信息表---------------
			LoanCustomerBaseInfo loanCustomerBaseInfo=null;
			if(!StrUtils.isNullOrEmpty(temp.getCustomerId())){//客户编号存在
				loanCustomerBaseInfo=loanCustomerBaseInfoMapper.selectByPrimaryKey(temp.getCustomerId());
				loanCustomerBaseInfo.setCustomerName(orgInfo.getOrgName());
				loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				loanCustomerBaseInfo.setLastModifyTime(new Date());
				i=loanCustomerBaseInfoMapper.updateByPrimaryKeySelective(loanCustomerBaseInfo);
			}else{//客户编号不存在
				loanCustomerBaseInfo=new LoanCustomerBaseInfo();
				String customerId=CommonUtils.getAutoIncrement("loan_customer_base_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanCustomerBaseInfo.setCustomerId(customerId);
				loanCustomerBaseInfo.setCustomerName(orgInfo.getOrgName());
				loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				loanCustomerBaseInfo.setStatus(SystemConst.Status.STATUS03);
				loanCustomerBaseInfo.setCreateTime(new Date());
				loanCustomerBaseInfo.setLastModifyTime(new Date());
				i=loanCustomerBaseInfoMapper.insertSelective(loanCustomerBaseInfo);
			}
			//--------------修改企业机构信息表---------------
			if(i==1){
				orgInfo.setOrgId(temp.getOrgId());
				orgInfo.setOrgCode(temp.getOrgCode());
				orgInfo.setCustomerId(loanCustomerBaseInfo.getCustomerId());
				orgInfo.setLastModifyTime(new Date());
				i=loanOrganizationMapper.updateByPrimaryKeySelective(orgInfo);//修改企业机构表信息
			}
			//--------------删除机构支持地区表---------------
			if(i==1){
				loanOrgSupportAreasMapper.deleteByOrgId(orgInfo.getOrgId());
			}
		}else{//企业机构表不存在
			//--------------新增客户基本信息表---------------
			LoanCustomerBaseInfo loanCustomerBaseInfo=new LoanCustomerBaseInfo();
			String customerId=CommonUtils.getAutoIncrement("loan_customer_base_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
			loanCustomerBaseInfo.setCustomerId(customerId);
			loanCustomerBaseInfo.setCustomerName(orgInfo.getOrgName());
			loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
			loanCustomerBaseInfo.setStatus(SystemConst.Status.STATUS03);
			loanCustomerBaseInfo.setCreateTime(new Date());
			loanCustomerBaseInfo.setLastModifyTime(new Date());
			i=loanCustomerBaseInfoMapper.insertSelective(loanCustomerBaseInfo);	
			//--------------新增企业机构信息表---------------
			if(i==1){
				orgInfo.setCustomerId(customerId);
				orgInfo.setCategory(SystemConst.AccountType.ORGANIZATION);
				orgInfo.setLastModifyTime(new Date());
				orgInfo.setStatus(SystemConst.Status.STATUS03);
				orgInfo.setCreateTime(new Date());
				orgInfo.setLastModifyTime(new Date());
				i=loanOrganizationMapper.insertSelective(orgInfo);
			}	
		}
		//--------------操作机构支持地区表---------------
		if(i==1){
			if(orgInfo.getSupportCityList()!=null && orgInfo.getSupportCityList().size()>0){
				areaFlag = saveOrgSupportAreas(orgInfo);
			}
		}
		//--------------操作账户信息表---------------
		LoanAccountInfo loanAccountInfo=loanAccountInfoMapper.selectByOwnerId(orgInfo.getCustomerId());
		if(i==1){
			if(loanAccountInfo==null){//账户不存在
				loanAccountInfo=new LoanAccountInfo();
				String accountId=CommonUtils.getAutoIncrement("loan_account_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanAccountInfo.setAccountId(accountId);
				loanAccountInfo.setUseType("00");
				loanAccountInfo.setOwnerId(orgInfo.getCustomerId());
				loanAccountInfo.setOwnerName(orgInfo.getOrgName());
				loanAccountInfo.setCertificateNo(orgInfo.getOrgBusinessLicenseNo());
				loanAccountInfo.setLevel("1");
				loanAccountInfo.setAccountType(SystemConst.AccountType.ORGANIZATION);
				loanAccountInfo.setStatus(SystemConst.Status.STATUS03);
				loanAccountInfo.setCreateTime(new Date());
				loanAccountInfo.setLastModifyTime(new Date());
				loanAccountInfo.setUnionId("10001");
				loanAccountInfo.setCompanyId("10001");
				i=loanAccountInfoMapper.insertSelective(loanAccountInfo);
			}else{//账户存在
				loanAccountInfo.setOwnerName(orgInfo.getOrgName());
				loanAccountInfo.setCertificateNo(orgInfo.getOrgBusinessLicenseNo());
				loanAccountInfo.setAccountType(SystemConst.AccountType.ORGANIZATION);
				loanAccountInfo.setLastModifyTime(new Date());
				i=loanAccountInfoMapper.updateByPrimaryKeySelective(loanAccountInfo);
			}
		}
		//--------------操作账户银行卡表---------------
		if(i==1&&!StrUtils.isNullOrEmpty(orgInfo.getOrgAccountNo())){
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("accountId", loanAccountInfo.getAccountId());
			params.put("bankCardNo", orgInfo.getOrgAccountNo());
			LoanAccountCard loanAccountCard=loanAccountCardMapper.queryCardByAccountAndCardNo(params);
			LoanBank loanBank=null;
			if(!StrUtils.isNullOrEmpty(orgInfo.getOrgAccountBank())){
				loanBank=loanBankMapper.selectByBankName(orgInfo.getOrgAccountBank());
			}
			if(loanAccountCard==null){//账户银行卡不存在
				loanAccountCard=new LoanAccountCard();
				loanAccountCard.setBankCardNo(orgInfo.getOrgAccountNo());
				loanAccountCard.setAccountId(loanAccountInfo.getAccountId());
				loanAccountCard.setAccountName(orgInfo.getOrgAccountName());
				loanAccountCard.setBankName(orgInfo.getOrgAccountBank());
				loanAccountCard.setAccountType("00");
				loanAccountCard.setBranchName(orgInfo.getOrgAccountBanchBank());
				loanAccountCard.setStatus(SystemConst.Status.STATUS03);
				loanAccountCard.setCreateTime(new Date());
				loanAccountCard.setLastModifyTime(new Date());
				if(null!=loanBank){
					loanAccountCard.setBankId(loanBank.getBankId());
					loanAccountCard.setBankAb(loanBank.getBankAb());
				}
				i=loanAccountCardMapper.insertSelective(loanAccountCard);
			}else{//账户银行卡存在
				loanAccountCard.setAccountName(orgInfo.getOrgAccountName());
				loanAccountCard.setBankName(orgInfo.getOrgAccountBank());
				loanAccountCard.setBranchName(orgInfo.getOrgAccountBanchBank());
				loanAccountCard.setLastModifyTime(new Date());
				if(null!=loanBank){
					loanAccountCard.setBankId(loanBank.getBankId());
					loanAccountCard.setBankAb(loanBank.getBankAb());
				}
				i=loanAccountCardMapper.updateByPrimaryKeySelective(loanAccountCard);
			}
		}
		//--------------操作借款人信息表---------------
		if(i==1){
			LoanCustomerInfo loanCustomerInfo=loanCustomerInfoMapper.selectByCustomerId(orgInfo.getCustomerId());
			if(loanCustomerInfo==null){//借款人信息表不存在
				String id=CommonUtils.getAutoIncrement("loan_customer_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanCustomerInfo=new LoanCustomerInfo();
				loanCustomerInfo.setId(id);
				loanCustomerInfo.setCustomerId(orgInfo.getCustomerId());
				loanCustomerInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(orgInfo.getOrgBusinessType()==2){
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PEER);
				}else{
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.ORGANIZATION);
				}
				loanCustomerInfo.setCustomerName(orgInfo.getOrgName());
				loanCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanCustomerInfo.setStatus(SystemConst.Status.STATUS03);
				loanCustomerInfo.setCreateTime(new Date());
				loanCustomerInfo.setLastModifyTime(new Date());
				i=loanCustomerInfoMapper.insertSelective(loanCustomerInfo);
			}else{//借款人信息表存在
				loanCustomerInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(orgInfo.getOrgBusinessType()==2){
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PEER);
				}else{
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.ORGANIZATION);
				}
				loanCustomerInfo.setCustomerName(orgInfo.getOrgName());
				loanCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanCustomerInfo.setLastModifyTime(new Date());
				i=loanCustomerInfoMapper.updateByPrimaryKeySelective(loanCustomerInfo);
			}
		}
		//--------------操作投资人信息表---------------
		if(i==1){
			LoanInvestCustomerInfo loanInvestCustomerInfo=loanInvestCustomerInfoMapper.selectByCustomerId(orgInfo.getCustomerId());
			if(loanInvestCustomerInfo==null){//投资人信息表不存在
				String id=CommonUtils.getAutoIncrement("loan_invest_customer_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanInvestCustomerInfo=new LoanInvestCustomerInfo();
				loanInvestCustomerInfo.setId(id);
				loanInvestCustomerInfo.setCustomerId(orgInfo.getCustomerId());
				loanInvestCustomerInfo.setInvestCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(orgInfo.getOrgBusinessType()==2){
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.PEER);
				}else{
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.ORGANIZATION);
				}
				loanInvestCustomerInfo.setInvestCustomerName(orgInfo.getOrgName());
				loanInvestCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanInvestCustomerInfo.setStatus(SystemConst.Status.STATUS03);
				loanInvestCustomerInfo.setCreateTime(new Date());
				loanInvestCustomerInfo.setLastModifyTime(new Date());
				i=loanInvestCustomerInfoMapper.insertSelective(loanInvestCustomerInfo);
			}else{//投资人信息表存在
				loanInvestCustomerInfo.setInvestCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(orgInfo.getOrgBusinessType()==2){
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.PEER);
				}else{
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.ORGANIZATION);
				}
				loanInvestCustomerInfo.setInvestCustomerName(orgInfo.getOrgName());
				loanInvestCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanInvestCustomerInfo.setLastModifyTime(new Date());
				i=loanInvestCustomerInfoMapper.updateByPrimaryKeySelective(loanInvestCustomerInfo);
			}
		}
		if(i==1 && areaFlag){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "信息同步成功");//信息同步成功
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "同步失败，需要重新调试接口");//同步失败，需要重新调试接口
			throw new RuntimeException();
		}
		return result;
	}


	/**
	 * 
	  * saveOrgSupportAreas:保存机构所支持的城市及客户经理等数据 <br/>
	 *
	 * @author xiangfeng
	 * @param orgInfo
	 * @return
	 * @since JDK 1.8
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean saveOrgSupportAreas(LoanOrganization orgInfo) {
		int num = 0;
		List<LoanOrgSupportAreas> list = orgInfo.getSupportCityList();
		if(list != null && list.size() > 0){
			for(LoanOrgSupportAreas temp : list){
				temp.setOrgId(orgInfo.getOrgId());
				temp.setOrgCategories(orgInfo.getOrgCategories());
				temp.setOrgName(orgInfo.getOrgName());
				temp.setOrgCode(orgInfo.getOrgCode());
				temp.setOrgType(orgInfo.getOrgType());
				temp.setAreaNo(temp.getCity());
				temp.setAreaName(temp.getCity());
				temp.setCreateDate(new Date());
				temp.setProvinceNo(temp.getProvince());
				temp.setProvinceName(temp.getProvince());
				temp.setCityNo(temp.getCity());
				temp.setCityName(temp.getCity());
				num = num + loanOrgSupportAreasMapper.insertSelective(temp);
			}
			if(num == list.size()){
				return true;
			}
		}
		return false;
	}
}

