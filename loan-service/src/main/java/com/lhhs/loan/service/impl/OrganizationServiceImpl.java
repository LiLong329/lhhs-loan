/**
 * Project Name:loan-service
 * File Name:OrganizationServiceImpl.java
 * Package Name:com.lhhs.loan.service.impl
 * Date:2017年8月4日上午10:40:11
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.common.utils.StringUtil;
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
import com.lhhs.loan.service.OrganizationService;

/**
 * ClassName:OrganizationServiceImpl <br/>
 * Function: 机构管理模块服务<br/>
 * Date:     2017年8月4日 上午10:40:11 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
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
	
	@Override
	public LoanOrganization get(String id) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanOrganization get(LoanOrganization entity) {
		entity = loanOrganizationMapper.getByEntity(entity);
		LoanOrgSupportAreas obj = new LoanOrgSupportAreas();
		obj.setOrgId(entity.getOrgId());
		entity.setSupportCityList(loanOrgSupportAreasMapper.queryList(obj));
		return entity;
	}

	@Override
	public List queryList(LoanOrganization entity) {
		return loanOrganizationMapper.queryList(entity);
	}

	@Override
	public Page queryListPage(LoanOrganization entity) {
		Page page = entity.getPage();
		page.setResultList(loanOrganizationMapper.queryListPage(entity));
		page.setTotalCount(loanOrganizationMapper.queryCount(entity));
		return page;
	}

	@Override
	public int save(LoanOrganization entity) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LoanOrganization entity) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanOrganization entity) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanOrganization entity) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LoanOrganization selectByOrgId(Long orgId) {
		return loanOrganizationMapper.selectByPrimaryKey(orgId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updataOrgInfo(LoanOrganization loanOrganization) {
		Map<String, Object> result = new HashMap<String, Object>();
		//验证参数
		if(null==loanOrganization){
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");//参数不能为空
			return result;
		}
		//验证机构用户名和机构代码是否存在
		if(null==loanOrganization.getOrgId()){
			LoanOrganization vo = new LoanOrganization();
			vo.setOrgUsername(loanOrganization.getOrgUsername());
			LoanOrganization org1 = loanOrganizationMapper.getByEntity(vo);
			if(null!=org1){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "机构用户名称已存在");
				return result;
			}
			vo = new LoanOrganization();
			vo.setOrgCode(loanOrganization.getOrgCode());
			LoanOrganization org2 = loanOrganizationMapper.getByEntity(vo);
			if(null!=org2){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "机构代码已存在");
				return result;
			}
		}
		//验证机构名称是否存在
		LoanOrganization org = loanOrganizationMapper.selectByOrgName(loanOrganization.getOrgName());
		if(null!=org){
			if(null==loanOrganization.getOrgId()){//新增
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "机构名称已存在");
				return result;
			}else if(org.getOrgId().intValue()!=loanOrganization.getOrgId().intValue()){
				result.put(SystemConst.retCode, SystemConst.FAIL);
				result.put(SystemConst.retMsg, "机构名称已经存在");
				return result;
			}
		}
		//查询数据库机构信息
		LoanOrganization temp=new LoanOrganization();
		if(null!=loanOrganization.getOrgId()){
			temp=loanOrganizationMapper.selectByPrimaryKey(loanOrganization.getOrgId());
		}
		int i=0;
		boolean areaFlag = true;
		if(null!=loanOrganization.getOrgId()){//企业机构表存在
			//--------------操作客户基本信息表---------------
			LoanCustomerBaseInfo loanCustomerBaseInfo=null;
			if(!StrUtils.isNullOrEmpty(temp.getCustomerId())){//客户编号存在
				loanCustomerBaseInfo=loanCustomerBaseInfoMapper.selectByPrimaryKey(temp.getCustomerId());
				loanCustomerBaseInfo.setCustomerName(loanOrganization.getOrgName());
				loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				loanCustomerBaseInfo.setLastModifyTime(new Date());
				i=loanCustomerBaseInfoMapper.updateByPrimaryKeySelective(loanCustomerBaseInfo);
			}else{//客户编号不存在
				loanCustomerBaseInfo=new LoanCustomerBaseInfo();
				String customerId=CommonUtils.getAutoIncrement("loan_customer_base_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanCustomerBaseInfo.setCustomerId(customerId);
				loanCustomerBaseInfo.setCustomerName(loanOrganization.getOrgName());
				loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				loanCustomerBaseInfo.setStatus(SystemConst.Status.STATUS03);
				loanCustomerBaseInfo.setCreateTime(new Date());
				loanCustomerBaseInfo.setLastModifyTime(new Date());
				i=loanCustomerBaseInfoMapper.insertSelective(loanCustomerBaseInfo);
			}
			//--------------修改企业机构信息表---------------
			if(i==1){
				loanOrganization.setCustomerId(loanCustomerBaseInfo.getCustomerId());
				loanOrganization.setLastModifyTime(new Date());
				i=loanOrganizationMapper.updateByPrimaryKeySelective(loanOrganization);//修改企业机构表信息
			}
			//--------------删除机构支持地区表---------------
			if(i==1){
				loanOrgSupportAreasMapper.deleteByOrgId(loanOrganization.getOrgId());
			}
		}else{//企业机构表不存在
			//--------------新增客户基本信息表---------------
			LoanCustomerBaseInfo loanCustomerBaseInfo=new LoanCustomerBaseInfo();
			String customerId=CommonUtils.getAutoIncrement("loan_customer_base_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
			loanCustomerBaseInfo.setCustomerId(customerId);
			loanCustomerBaseInfo.setCustomerName(loanOrganization.getOrgName());
			loanCustomerBaseInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
			loanCustomerBaseInfo.setStatus(SystemConst.Status.STATUS03);
			loanCustomerBaseInfo.setCreateTime(new Date());
			loanCustomerBaseInfo.setLastModifyTime(new Date());
			i=loanCustomerBaseInfoMapper.insertSelective(loanCustomerBaseInfo);	
			//--------------新增企业机构信息表---------------
			if(i==1){
				loanOrganization.setCustomerId(customerId);
				loanOrganization.setCategory(SystemConst.AccountType.ORGANIZATION);
				loanOrganization.setStatus(SystemConst.Status.STATUS03);
				loanOrganization.setOrgAddTime(new Date());
				loanOrganization.setCreateTime(new Date());
				loanOrganization.setLastModifyTime(new Date());
				i=loanOrganizationMapper.insertSelective(loanOrganization);
			}	
		}
		//--------------操作机构支持地区表---------------
		if(i==1){
			if(StringUtil.isNotEmpty(loanOrganization.getCityList())){
				List<LoanOrgSupportAreas> supportCityList=JSON.parseArray(loanOrganization.getCityList(), LoanOrgSupportAreas.class);    
				if(null!=supportCityList && supportCityList.size()>0){
					loanOrganization.setSupportCityList(supportCityList);
					areaFlag = saveOrgSupportAreas(loanOrganization);
				}
			}
		}
		//--------------操作账户信息表---------------
		LoanAccountInfo loanAccountInfo=loanAccountInfoMapper.selectByOwnerId(loanOrganization.getCustomerId());
		if(i==1){
			if(loanAccountInfo==null){//账户不存在
				loanAccountInfo=new LoanAccountInfo();
				String accountId=CommonUtils.getAutoIncrement("loan_account_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanAccountInfo.setAccountId(accountId);
				loanAccountInfo.setUseType("00");
				loanAccountInfo.setOwnerId(loanOrganization.getCustomerId());
				loanAccountInfo.setOwnerName(loanOrganization.getOrgName());
				loanAccountInfo.setCertificateNo(loanOrganization.getOrgBusinessLicenseNo());
				loanAccountInfo.setLevel("1");
				loanAccountInfo.setAccountType(SystemConst.AccountType.ORGANIZATION);
				loanAccountInfo.setStatus(SystemConst.Status.STATUS03);
				loanAccountInfo.setCreateTime(new Date());
				loanAccountInfo.setLastModifyTime(new Date());
				loanAccountInfo.setUnionId("10001");
				loanAccountInfo.setCompanyId("10001");
				i=loanAccountInfoMapper.insertSelective(loanAccountInfo);
			}else{//账户存在
				loanAccountInfo.setOwnerName(loanOrganization.getOrgName());
				loanAccountInfo.setCertificateNo(loanOrganization.getOrgBusinessLicenseNo());
				loanAccountInfo.setAccountType(SystemConst.AccountType.ORGANIZATION);
				loanAccountInfo.setLastModifyTime(new Date());
				i=loanAccountInfoMapper.updateByPrimaryKeySelective(loanAccountInfo);
			}
		}
		//--------------操作账户银行卡表---------------
		if(i==1&&!StrUtils.isNullOrEmpty(loanOrganization.getOrgAccountNo())){
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("accountId", loanAccountInfo.getAccountId());
			params.put("bankCardNo", loanOrganization.getOrgAccountNo());
			LoanAccountCard loanAccountCard=loanAccountCardMapper.queryCardByAccountAndCardNo(params);
			LoanBank loanBank=null;
			if(!StrUtils.isNullOrEmpty(loanOrganization.getOrgAccountBank())){
				loanBank=loanBankMapper.selectByBankName(loanOrganization.getOrgAccountBank());
			}
			if(loanAccountCard==null){//账户银行卡不存在
				loanAccountCard=new LoanAccountCard();
				loanAccountCard.setBankCardNo(loanOrganization.getOrgAccountNo());
				loanAccountCard.setAccountId(loanAccountInfo.getAccountId());
				loanAccountCard.setAccountName(loanOrganization.getOrgAccountName());
				loanAccountCard.setBankName(loanOrganization.getOrgAccountBank());
				loanAccountCard.setAccountType("00");
				loanAccountCard.setBranchName(loanOrganization.getOrgAccountBanchBank());
				loanAccountCard.setStatus(SystemConst.Status.STATUS03);
				loanAccountCard.setCreateTime(new Date());
				loanAccountCard.setLastModifyTime(new Date());
				if(null!=loanBank){
					loanAccountCard.setBankId(loanBank.getBankId());
					loanAccountCard.setBankAb(loanBank.getBankAb());
				}
				i=loanAccountCardMapper.insertSelective(loanAccountCard);
			}else{//账户银行卡存在
				loanAccountCard.setAccountName(loanOrganization.getOrgAccountName());
				loanAccountCard.setBankName(loanOrganization.getOrgAccountBank());
				loanAccountCard.setBranchName(loanOrganization.getOrgAccountBanchBank());
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
			LoanCustomerInfo loanCustomerInfo=loanCustomerInfoMapper.selectByCustomerId(loanOrganization.getCustomerId());
			if(loanCustomerInfo==null){//借款人信息表不存在
				String id=CommonUtils.getAutoIncrement("loan_customer_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanCustomerInfo=new LoanCustomerInfo();
				loanCustomerInfo.setId(id);
				loanCustomerInfo.setCustomerId(loanOrganization.getCustomerId());
				loanCustomerInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(loanOrganization.getOrgBusinessType()==2){
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PEER);
				}else{
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.ORGANIZATION);
				}
				loanCustomerInfo.setCustomerName(loanOrganization.getOrgName());
				loanCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanCustomerInfo.setStatus(SystemConst.Status.STATUS03);
				loanCustomerInfo.setCreateTime(new Date());
				loanCustomerInfo.setLastModifyTime(new Date());
				i=loanCustomerInfoMapper.insertSelective(loanCustomerInfo);
			}else{//借款人信息表存在
				loanCustomerInfo.setCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(loanOrganization.getOrgBusinessType()==2){
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PEER);
				}else{
					loanCustomerInfo.setCustomerNature(SystemConst.LoanCustomerNature.ORGANIZATION);
				}
				loanCustomerInfo.setCustomerName(loanOrganization.getOrgName());
				loanCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanCustomerInfo.setLastModifyTime(new Date());
				i=loanCustomerInfoMapper.updateByPrimaryKeySelective(loanCustomerInfo);
			}
		}
		//--------------操作投资人信息表---------------
		if(i==1){
			LoanInvestCustomerInfo loanInvestCustomerInfo=loanInvestCustomerInfoMapper.selectByCustomerId(loanOrganization.getCustomerId());
			if(loanInvestCustomerInfo==null){//投资人信息表不存在
				String id=CommonUtils.getAutoIncrement("loan_invest_customer_info", SystemConst.AccountType.ORGANIZATION.substring(0, 1), 10).toString();
				loanInvestCustomerInfo=new LoanInvestCustomerInfo();
				loanInvestCustomerInfo.setId(id);
				loanInvestCustomerInfo.setCustomerId(loanOrganization.getCustomerId());
				loanInvestCustomerInfo.setInvestCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(loanOrganization.getOrgBusinessType()==2){
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.PEER);
				}else{
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.ORGANIZATION);
				}
				loanInvestCustomerInfo.setInvestCustomerName(loanOrganization.getOrgName());
				loanInvestCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanInvestCustomerInfo.setStatus(SystemConst.Status.STATUS03);
				loanInvestCustomerInfo.setCreateTime(new Date());
				loanInvestCustomerInfo.setLastModifyTime(new Date());
				i=loanInvestCustomerInfoMapper.insertSelective(loanInvestCustomerInfo);
			}else{//投资人信息表存在
				loanInvestCustomerInfo.setInvestCustomerType(SystemConst.AccountType.ORGANIZATION);
				if(loanOrganization.getOrgBusinessType()==2){
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.PEER);
				}else{
					loanInvestCustomerInfo.setInvestCustomerNature(SystemConst.InvestCustomerNature.ORGANIZATION);
				}
				loanInvestCustomerInfo.setInvestCustomerName(loanOrganization.getOrgName());
				loanInvestCustomerInfo.setAccountId(loanAccountInfo.getAccountId());
				loanInvestCustomerInfo.setLastModifyTime(new Date());
				i=loanInvestCustomerInfoMapper.updateByPrimaryKeySelective(loanInvestCustomerInfo);
			}
		}
		if(i==1 && areaFlag){
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
			result.put(SystemConst.retMsg, "保存信息成功");
		}else{
			result.put(SystemConst.retCode, SystemConst.FAIL);
			result.put(SystemConst.retMsg, "保存信息失败");
			throw new RuntimeException();
		}
		return result;
	}

	/**
	 * saveOrgSupportAreas:保存机构所支持的城市及客户经理等数据 <br/>
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

