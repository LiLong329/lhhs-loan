package com.lhhs.loan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.constant.SystemConst.AccountType;
import com.lhhs.loan.common.shared.encryption.MD5;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanAccountCardMapper;
import com.lhhs.loan.dao.LoanAccountInfoMapper;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanEmpMapper;
import com.lhhs.loan.dao.LoanInvestCustomerInfoMapper;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanAccountInfo;
import com.lhhs.loan.dao.domain.LoanBorrowerInfo;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanEmpRole;
import com.lhhs.loan.dao.domain.LoanInvestCustomerInfo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.InvestCustomerService;

/**
 *  理财人相关Service
 * 
 * @author Administrator
 *
 */
@Service("investCustomerService")
public class InvestCustomerServiceImpl implements InvestCustomerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoanInvestCustomerInfoMapper investCustomerMapper;
	@Autowired
	private LoanCustomerBaseInfoMapper customerBaseInfoMapper;
	@Autowired
	private LoanBorrowerInfoMapper borrowerInfoMapper;
	
	@Autowired
	private LoanAccountInfoMapper loanAccountInfoMapper;
	
	@Autowired
	private LoanAccountCardMapper loanAccountCardMapper;
	@Autowired
	private LoanEmpMapper loanEmpMapper;
	
	
	
	@Override
	public List<LoanInvestCustomerInfo> investCustomerList(Map<String, Object> map, Page page) {
		List<LoanInvestCustomerInfo> list = investCustomerMapper.investCustomerList(map);
		Integer Count = investCustomerMapper.investCustomerListCount(map);
		page.setResultList(list);
		page.setTotalCount(Count);
		return list;
	}

	@Override
	public List<LoanInvestCustomerInfo> investCustomerExport(Map<String, Object> map) {
		List<LoanInvestCustomerInfo> list=investCustomerMapper.investCustomerList(map);
		if (list!=null & list.size()>0) {
			for (LoanInvestCustomerInfo investCustomerInfo : list) {
				if("0".equals(investCustomerInfo.getSex())){
					investCustomerInfo.setSex("女");
				}else{
					investCustomerInfo.setSex("男");
				}
			}
		}
		return list;
	}

	@Override
	public LoanInvestCustomerInfo selectByPrimaryKey(String id) {
		return investCustomerMapper.selectByPrimaryKey(id);
	}

	@Override
	public LoanInvestCustomerInfo get(String id) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanInvestCustomerInfo get(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryList(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int save(LoanInvestCustomerInfo investCustomerInfo) {
//		LoanEmp  loanEmp= (LoanEmp)SecurityUserHolder.getCurrentUser();
		LoanEmp  loanEmp = CommonUtils.getEmpFromSession();
		String id = investCustomerInfo.getId();
		int count = 0;
		int count1 = 0;
		int count2= 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		investCustomerInfo.setCompanyId(investCustomerInfo.getAffiliatedCompanyId());//所属分公司
		investCustomerInfo.setUnionId(loanEmp.getCompanyId());//所属集团
		String managerNo=investCustomerInfo.getAccountManagerNo();//客户经理编号
		if(!StringUtils.isEmpty(managerNo)){
			LoanEmp managerEmp=loanEmpMapper.selectByPrimaryKey(Integer.parseInt(managerNo));
			if(managerEmp!=null){
				investCustomerInfo.setAccountManagerDepartmentNo(managerEmp.getLeGroupId()==null?managerEmp.getLeDeptId().toString():managerEmp.getLeGroupId().toString());
			}
		}
		try {
			  if (!"".equals(id)) {//更新操作
				investCustomerInfo.setLastUser(loanEmp.getLeEmpId().toString());
				investCustomerInfo.setLastModifyTime(new Date());
				//投资人信息更新
				count5 = investCustomerMapper.updateByPrimaryKeySelective(investCustomerInfo);
				if(count5>0){//客户基 本信息更新
					count1 = customerBaseInfoMapper.updateByPrimaryKeySelective(changeBeanByBaseInfo(investCustomerInfo,loanEmp,"edit"));
				}
				if(count1>0){//个人信息更新
					count2 = borrowerInfoMapper.updateByPrimaryKeySelective(changeBeanByBorrower(investCustomerInfo,loanEmp,"edit"));
				}
				if(count2>0){//账户信息保存
					count3 =loanAccountInfoMapper.updateByPrimaryKeySelective(changeBeanByAccount(investCustomerInfo,loanEmp,"edit"));
				}
				if(count3>0&&(!"".equals(investCustomerInfo.getBankCardNo())
						|| !"".equals(investCustomerInfo.getBankId())
						|| !"".equals(investCustomerInfo.getBranchName()))){//账户银行卡信息更新
					count4 = loanAccountCardMapper.updateByInvestCustomer(changeBeanByAccountCard(investCustomerInfo,loanEmp,"edit"));
					if(count4==0){
						count4=loanAccountCardMapper.insertSelective(changeBeanByAccountCard(investCustomerInfo,loanEmp,""));
						if(count4==0){
							throw new RuntimeException("账户银行卡信息更新异常 ");
						}
					}
				}
			} else {//新增
					investCustomerInfo.setId(UUID.randomUUID().toString().replace("-",""));
					investCustomerInfo.setCreateTime(new Date());// 新增理财人信息时间戳
					investCustomerInfo.setCreateUser(loanEmp.getLeEmpId().toString());
					investCustomerInfo.setInvestCustomerType("10");//投资类型
					investCustomerInfo.setStatus(SystemConst.Status.STATUS03);
					String accountId=CommonUtils.getAutoIncrement("loan_account_info", SystemConst.AccountType.PERSONAL.substring(0, 1), 10).toString();
					Long baseInfoId=CommonUtils.getAutoIncrement("loan_customer_base_info",SystemConst.AccountType.PERSONAL.substring(0, 1), 10);//获取客户基本信息 自增主键
					if(baseInfoId==null||"".equals(accountId)){
						throw new RuntimeException("客户基本信息或者账户信息--获取主键异常 ");
					}
					investCustomerInfo.setCustomerId(baseInfoId.toString());//客户编号
					investCustomerInfo.setAccountId(accountId);//账户编号
					//客户基本信息保存
					count5 = customerBaseInfoMapper.insertSelective(changeBeanByBaseInfo(investCustomerInfo,loanEmp,""));
				    if(count5>0){//个人信息保存
				     Long custId=CommonUtils.getAutoIncrement("loan_borrower_info",SystemConst.AccountType.PERSONAL.substring(0, 1), 10);//获取个人基本信息 自增主键
					 if(custId==null){
						 throw new RuntimeException("个人信息--获取主键异常 ");
					 }
					 investCustomerInfo.setCustId(custId.toString());
					 count1 = borrowerInfoMapper.insertSelective(changeBeanByBorrower(investCustomerInfo,loanEmp,""));
					}
					if(count1>0){//投资人信息保存
						count2 = investCustomerMapper.insertSelective(investCustomerInfo);
					}
					if(count2>0){//账户信息保存
						count3=loanAccountInfoMapper.insertSelective(changeBeanByAccount(investCustomerInfo,loanEmp,""));
					}
					if(count3>0&&!"".equals(investCustomerInfo.getBankCardNo())){//账户银行卡信息保存
						count4=loanAccountCardMapper.insertSelective(changeBeanByAccountCard(investCustomerInfo,loanEmp,""));
						if(count4==0){
							throw new RuntimeException("账户银行卡信息更新异常 ");
						}
					}
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count5>0&&count1>0&&count2>0&&count3>0) count=1;
		else
			try {
				throw new Exception("更新失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return count;
	}

	
	/**
	 * 
	 * changeBeanByAccount:投资客户信息对象 转换 账户信息对象
	 * @author suncj
	 * @param investCustomerInfo
	 * @param loanEmp
	 * @param type: "edit"  xiugai
	 * @return
	 * @since JDK 1.8
	 */
	public static LoanAccountInfo changeBeanByAccount(LoanInvestCustomerInfo investCustomerInfo,LoanEmp loanEmp,String type){
			LoanAccountInfo accountInfo=new LoanAccountInfo();
			 if("edit".equals(type)){
				 accountInfo.setLastUser(loanEmp.getLeEmpId().toString());
				 accountInfo.setLastModifyTime(new Date());
			    }else{
			    	accountInfo.setCreateTime(new Date());
					accountInfo.setCreateUser(loanEmp.getLeEmpId().toString());
			 }
			accountInfo.setAccountId(investCustomerInfo.getAccountId());//账户编号
			accountInfo.setOwnerId(investCustomerInfo.getCustomerId().toString());//账户所有者编码
			accountInfo.setOwnerName(investCustomerInfo.getInvestCustomerName());//账户所有者名称
			accountInfo.setCertificateNo(investCustomerInfo.getIdNum());// 证件号--身份证号
			accountInfo.setMobile(investCustomerInfo.getInvestCustomerMobile());//手机号
			accountInfo.setAccountType("10");//账户分类账户分类（个人：10;企业：20;机构：30;公司：40;其他：90）
			accountInfo.setStatus(SystemConst.Status.STATUS03);
//			accountInfo.setCompanyId(investCustomerInfo.getAffiliatedCompanyId());//所属公司id
			accountInfo.setCompanyId(investCustomerInfo.getCompanyId());
			accountInfo.setUnionId(investCustomerInfo.getUnionId());
		    return accountInfo;
	}
	
	/**
	 * 
	 * changeBeanByAccountCard:投资客户信息对象 转换 账户银行卡对象
	 * @author suncj
	 * @param investCustomerInfo
	 * @param loanEmp
	 * @param type: "edit"  xiugai
	 * @return
	 * @since JDK 1.8
	 */
	public static LoanAccountCard changeBeanByAccountCard(LoanInvestCustomerInfo investCustomerInfo,LoanEmp loanEmp,String type){
			LoanAccountCard accountCard=new LoanAccountCard();
			 if("edit".equals(type)){
				 accountCard.setLastUser(loanEmp.getLeEmpId().toString());
				 accountCard.setLastModifyTime(new Date());
			    }else{
			    	accountCard.setCreateTime(new Date());
					accountCard.setCreateUser(loanEmp.getLeEmpId().toString());
			 }
			accountCard.setBankCardNo(investCustomerInfo.getBankCardNo());
			accountCard.setMobile(investCustomerInfo.getInvestCustomerMobile());//手机号
			accountCard.setAccountName(investCustomerInfo.getInvestCustomerName());//客户名称
			accountCard.setBankId(investCustomerInfo.getBankId());
			accountCard.setBranchName(investCustomerInfo.getBranchName());//开户支行名称
			accountCard.setAccountId(investCustomerInfo.getAccountId());//所属系统账号
			accountCard.setAccountName(investCustomerInfo.getInvestCustomerName());//账户名
			accountCard.setAccountHolder(investCustomerInfo.getInvestCustomerName());//持卡人
			accountCard.setStatus(SystemConst.Status.STATUS03);
			accountCard.setBankName(investCustomerInfo.getBankName());//开户行名称
//			accountCard.setCompanyId(investCustomerInfo.getAffiliatedCompanyId());
			accountCard.setCompanyId(investCustomerInfo.getCompanyId());
			accountCard.setUnionId(investCustomerInfo.getUnionId());
		    return accountCard;
	}
	
	/**
	 * 
	 * changeBeanByBaseInfo:投资客户信息对象 转换 客户基本信息对象
	 * @author suncj
	 * @param investCustomerInfo
	 * @param loanEmp
	 * @param type: "edit"  xiugai
	 * @return
	 * @since JDK 1.8
	 */
	public static LoanCustomerBaseInfo changeBeanByBaseInfo(LoanInvestCustomerInfo investCustomerInfo,LoanEmp loanEmp,String type){
		    LoanCustomerBaseInfo baseInfo=new LoanCustomerBaseInfo();
		    if("edit".equals(type)){
		    	baseInfo.setLastUser(loanEmp.getLeEmpId().toString());
		    	baseInfo.setLastModifyTime(new Date());
		    }else{
		    	baseInfo.setCreateTime(new Date());
		    	baseInfo.setCreateUser(loanEmp.getLeEmpId().toString());
		    }
			baseInfo.setCustomerId(investCustomerInfo.getCustomerId().toString());//客户编号
			baseInfo.setCertificateNo(investCustomerInfo.getIdNum());//身份证号
			baseInfo.setCustomerMobile(investCustomerInfo.getInvestCustomerMobile());//手机号
			baseInfo.setCustomerName(investCustomerInfo.getInvestCustomerName());//客户名称
			baseInfo.setCustomerType("10");
//			baseInfo.setCompanyId(investCustomerInfo.getAffiliatedCompanyId());
			baseInfo.setCompanyId(investCustomerInfo.getCompanyId());
			baseInfo.setUnionId(investCustomerInfo.getUnionId());
		    return baseInfo;
	}
	
	/**
	 * changeBeanByBorrower:投资客户信息对象 转换 个人基本信息对象
	 * @author suncj
	 * @param investCustomerInfo
	 * @param loanEmp
	 * @param type: "edit"  xiugai
	 * @return
	 * @since JDK 1.8
	 */
	public static LoanBorrowerInfoWithBLOBs changeBeanByBorrower(LoanInvestCustomerInfo investCustomerInfo,LoanEmp loanEmp,String type){
		LoanBorrowerInfoWithBLOBs borrowerInfo=new LoanBorrowerInfoWithBLOBs();
		 if("edit".equals(type)){
			 borrowerInfo.setLastUser(loanEmp.getLeEmpId().toString());
			 borrowerInfo.setLastModifyTime(new Date());
		    }else{
		    	borrowerInfo.setCreateTime(new Date());
				borrowerInfo.setCreateUser(loanEmp.getLeEmpId().toString());
		 }
		borrowerInfo.setCustId(investCustomerInfo.getCustomerId().toString());
		borrowerInfo.setIdNum(investCustomerInfo.getIdNum());//身份证号
		borrowerInfo.setMobile(investCustomerInfo.getInvestCustomerMobile());//手机号
		borrowerInfo.setBname(investCustomerInfo.getInvestCustomerName());//客户名称
		borrowerInfo.setCorName(investCustomerInfo.getAffiliatedCompany());//公司名称
		borrowerInfo.setSex(investCustomerInfo.getSex());//性别
		borrowerInfo.setCustType(0);
		borrowerInfo.setCustNature(0);
		borrowerInfo.setCustomerId(investCustomerInfo.getCustomerId().toString());
//		borrowerInfo.setCompanyId(investCustomerInfo.getAffiliatedCompanyId());
		borrowerInfo.setCompanyId(investCustomerInfo.getCompanyId());
		borrowerInfo.setUnionId(investCustomerInfo.getUnionId());
	    return borrowerInfo;
}
	
	
	
	@Override
	public List<Map<String, Object>> queryAllDepts() {
		return investCustomerMapper.queryAllDepts();
	}
	
	
	@Override
	public int update(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(LoanInvestCustomerInfo entity) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int selectByMobile(String mobile) {
		return investCustomerMapper.selectByMobile(mobile);
	}

	@Override
	public List<Map<String, Object>> queryAllCompany(Map<String, Object> paramsMap) {
		return investCustomerMapper.queryAllCompany(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryAllManager(String affiliatedCompanyId) {
		return investCustomerMapper.queryAllManager(affiliatedCompanyId);
	}
}