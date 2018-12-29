package com.lhhs.loan.service.impl;

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

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.CrmIntentLoanUserMapper;
import com.lhhs.loan.dao.LoanBorrowerInfoMapper;
import com.lhhs.loan.dao.LoanCustomerBaseInfoMapper;
import com.lhhs.loan.dao.LoanCustomerInfoMapper;
import com.lhhs.loan.dao.LoanOrderCarExtendMapper;
import com.lhhs.loan.dao.LoanOrderHouseExtendMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanUserOrderMapper;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanBorrowerInfoWithBLOBs;
import com.lhhs.loan.dao.domain.LoanCarInfo;
import com.lhhs.loan.dao.domain.LoanCustomerBaseInfo;
import com.lhhs.loan.dao.domain.LoanCustomerInfo;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanHouseInfo;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanUserOrder;
import com.lhhs.loan.dao.domain.vo.AuditParamVo;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.BorrowerInfoService;
import com.lhhs.loan.service.CarInfoService;
import com.lhhs.loan.service.HouseInfoService;
import com.lhhs.loan.service.MortgageInfoService;
import com.lhhs.loan.service.account.AccountManagerService;

@Service
public class MortgageInfoServiceImpl implements MortgageInfoService {
	private static final Logger logger = LoggerFactory.getLogger(MortgageInfoServiceImpl.class);
	
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanOrderHouseExtendMapper loanOrderHouseExtendMapper;
	@Autowired
	private LoanOrderCarExtendMapper loanOrderCarExtendMapper;
	@Autowired
	private LoanBorrowerInfoMapper loanBorrowerInfoMapper;
	@Autowired
	private LoanCustomerBaseInfoMapper loanCustomerBaseInfoMapper;
	@Autowired
	private LoanCustomerInfoMapper loanCustomerInfoMapper;
	@Autowired
	private HouseInfoService houseInfoService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private BorrowerInfoService borrowerInfoService;
	@Autowired
	private AccountManagerService accountManagerService;
	@Autowired
	private LoanUserOrderMapper loanUserOrderMapper;
	@Autowired
	private CrmIntentLoanUserMapper crmIntentLoanUserMapper;
	
	@Override
	public List<LoanOrderHouseExtend> findHouseExtendListByOrderNo(String orderNo) {
		return loanOrderHouseExtendMapper.findHouseExtendListByOrderNo(orderNo);
	}

	@Override
	public List<LoanOrderCarExtend> findCarExtendListByOrderNo(String orderNo) {
		return loanOrderCarExtendMapper.findCarExtendListByOrderNo(orderNo);
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional
	public boolean saveHouseExtendList(List<LoanOrderHouseExtend> houseList,String orderNo) {
		boolean result = true;
		try{
			loanOrderHouseExtendMapper.deleteHouseExtendByOrderNo(orderNo);
			if(houseList != null && houseList.size() > 0){
				for(LoanOrderHouseExtend extend : houseList){
//					TODO 暂时不校验房产证编号唯一性
//					if(extend != null && !StrUtils.isNullOrEmpty(extend.getPropertyNum())){
					if(extend != null){
						//插入或者更新房产扩展信息
						loanOrderHouseExtendMapper.insertSelective(extend);
					}
				}
			}
		}catch(Exception e){
			result = false;
			logger.debug(e.getMessage());
		}finally{
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional
	public boolean saveCarExtendList(List<LoanOrderCarExtend> carList,String orderNo) {
		boolean result = true;
		try{
			loanOrderCarExtendMapper.deleteCarExtendByOrderNo(orderNo);
			if(carList != null && carList.size() > 0){
				for(LoanOrderCarExtend extend : carList){
//					TODO 暂时不校验机动车登记编号唯一性
//					if(extend != null && !StrUtils.isNullOrEmpty(extend.getVehicleNum())){
					if(extend != null){
						//插入或者更新车产扩展信息
						loanOrderCarExtendMapper.insertSelective(extend);
					}
				}
			}
		}catch(Exception e){
			result = false;
			logger.debug(e.getMessage());
		}finally{
			return result;
		}
	}

	@Override
	@Transactional
	public boolean saveMortgageInfo(List<LoanOrderHouseExtend> houseList, List<LoanOrderCarExtend> carList,String orderNo) {
		return  this.saveHouseExtendList(houseList,orderNo) && this.saveCarExtendList(carList,orderNo);
	}

	/**
	 * 保存从碰碰旺获取的借款人所有信息到本地数据库
	 * 操作：客户基本信息、个人基本信息、抵押物信息、借款人信息、账户信息、订单表插入customerId
	 * @see com.lhhs.loan.service.transport.MortgageInfoTransService#saveBorrowerAllInfo(java.util.Map)
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean saveBorrowerAllInfo(Map<String,Object> info,LoanEmp loanEmp){
		String orderNo = info.get("orderNo").toString();
		String custId=null;
		LoanBorrowerInfoWithBLOBs borrowInfo = JSON.parseObject(info.get("borrowerInfo").toString(), LoanBorrowerInfoWithBLOBs.class);
		LoanBorrowerInfoWithBLOBs queryObj = loanBorrowerInfoMapper.selectByPrimaryKey(borrowInfo.getCustId());
		info.put("custId", borrowInfo.getCustId());
		//如果操作人信息为null,默认为晋商睦家集团操作
		if(loanEmp == null){
			loanEmp = new LoanEmp();
			loanEmp.setLeEmpId(1);
			loanEmp.setCompanyId("10001");
			loanEmp.setBranchCompanyId("10001");
		}
//		AuditParamVo auditParamVo=(AuditParamVo) info.get("auditParamVo");//报单信息loan_order_info
		LoanOrderInfo loanOrderInfo=loanOrderInfoMapper.selectByPrimaryKey(orderNo);
		//操作表返回值定义
		int baseInfoRet = 0; //客户基本信息表操作返回
		Map<String,Object> borrowerRet = null;//个人基本信息表操作返回
		Map<String,Object> mortgageRet = null;//抵押物信息表操作返回
		String accountInfoRet = "01";//账户信息表操作返回
		int customerInfoRet = 0;//借款人信息表操作返回
		int orderInfoRet = 1;//订单表操作返回
		int updateIntentCustId=0;
		//是否生成客户基本信息、个人信息、抵押物信息、借款人信息、账户信息
		if(queryObj != null && !StringUtils.isEmpty(queryObj.getCustomerId())){
			custId=queryObj.getCustomerId();
			//更新客户基本信息、借款人信息、账户信息
			//操作客户基本信息
			LoanCustomerBaseInfo baseInfo = loanCustomerBaseInfoMapper.selectByPrimaryKey(queryObj.getCustomerId());
			if(baseInfo == null){
				baseInfo = new LoanCustomerBaseInfo();
				baseInfo.setCustomerId(queryObj.getCustomerId());
				baseInfo.setCertificateNo(borrowInfo.getIdNum());
				baseInfo.setCustomerMobile(borrowInfo.getMobile());
				baseInfo.setCustomerName(borrowInfo.getBname());
				baseInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
				baseInfo.setStatus(SystemConst.Status.STATUS03);
				baseInfo.setUnionId(loanEmp.getCompanyId());
				baseInfo.setCompanyId(loanEmp.getBranchCompanyId());
				
				baseInfo.setCreateUser(loanEmp.getLeEmpId().toString());
				baseInfo.setCreateTime(new Date());
				baseInfo.setLastModifyTime(new Date());
				baseInfo.setLastUser(loanEmp.getLeEmpId().toString());
				
				baseInfoRet = loanCustomerBaseInfoMapper.insertSelective(baseInfo);
			}else{//更新客户基本信息
				baseInfo.setCertificateNo(borrowInfo.getIdNum());
				baseInfo.setCustomerMobile(borrowInfo.getMobile());
				baseInfo.setCustomerName(borrowInfo.getBname());
				baseInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
				
				baseInfo.setUnionId(loanEmp.getCompanyId());
				baseInfo.setCompanyId(loanEmp.getBranchCompanyId());
				baseInfo.setLastModifyTime(new Date());
				baseInfo.setLastUser(loanEmp.getLeEmpId().toString());
				
				baseInfoRet = loanCustomerBaseInfoMapper.updateByPrimaryKeySelective(baseInfo);
			}
			
			//插入或者更新个人信息
			borrowInfo.setCustomerId(baseInfo.getCustomerId());
			borrowInfo.setUnionId(loanEmp.getCompanyId());
			borrowInfo.setCompanyId(loanEmp.getBranchCompanyId());
			borrowInfo.setLastModifyTime(new Date());
			borrowInfo.setLastUser(loanEmp.getLeEmpId().toString());
			borrowerRet = borrowerInfoService.saveBorrowerBaseInfo(borrowInfo);
			
			//插入或者更新客户抵押物信息
			info.put("customerId", baseInfo.getCustomerId());
			mortgageRet = this.saveMortgageInfo(info);
			
			//账户信息
			LoanAccountCard card = new LoanAccountCard();
			card.setOwnerId(baseInfo.getCustomerId());
			card.setOwnerName(baseInfo.getCustomerName());
			card.setMobile(borrowInfo.getMobile());
			card.setCertificateNo(baseInfo.getCertificateNo());
			card.setAccountType(baseInfo.getCustomerType());
			card.setUnionId(baseInfo.getUnionId());
			card.setCompanyId(baseInfo.getCompanyId());
			card.setBankId(borrowInfo.getBankId());
			card.setBankName(borrowInfo.getBankName());
			card.setBankCardNo(borrowInfo.getBankCardNo());
			accountInfoRet = accountManagerService.saveOrUpdateCard(card);
			
			//操作借款人表
			LoanCustomerInfo customerInfo = loanCustomerInfoMapper.selectByCustomerId(baseInfo.getCustomerId());
			if(customerInfo == null){
				customerInfo = new LoanCustomerInfo();
				customerInfo.setId(CommonUtils.getAutoIncrement("loan_customer_info", SystemConst.AccountType.PERSONAL.substring(0, 1), 10).toString());
				customerInfo.setCustomerId(baseInfo.getCustomerId());
				customerInfo.setCustomerMobile(borrowInfo.getMobile());
				customerInfo.setCustomerName(borrowInfo.getBname());
				customerInfo.setIdNum(borrowInfo.getIdNum());
				customerInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
				customerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PERSONAL);
				customerInfo.setAccountId(card.getAccountId());
				customerInfo.setStatus(SystemConst.Status.STATUS03);
				customerInfo.setUnionId(loanEmp.getCompanyId());
				customerInfo.setCompanyId(loanEmp.getBranchCompanyId());
				customerInfo.setCreateUser(loanEmp.getLeEmpId().toString());
				customerInfo.setCreateTime(new Date());
				customerInfo.setLastUser(loanEmp.getLeEmpId().toString());
				customerInfo.setLastModifyTime(new Date());
				customerInfo.setManagerId(loanOrderInfo.getCustomerManager().toString());
				customerInfo.setDeptId(loanOrderInfo.getDepId());
				customerInfoRet = loanCustomerInfoMapper.insertSelective(customerInfo);
			}else{
				customerInfo.setCustomerMobile(borrowInfo.getMobile());
				customerInfo.setCustomerName(borrowInfo.getBname());
				customerInfo.setIdNum(borrowInfo.getIdNum());
				customerInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
				customerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PERSONAL);
				customerInfo.setAccountId(card.getAccountId());
				customerInfo.setUnionId(loanEmp.getCompanyId());
				customerInfo.setCompanyId(loanEmp.getBranchCompanyId());
				customerInfo.setLastUser(loanEmp.getLeEmpId().toString());
				customerInfo.setLastModifyTime(new Date());
				customerInfo.setManagerId(loanOrderInfo.getCustomerManager().toString());
				customerInfo.setDeptId(loanOrderInfo.getDepId());
				customerInfoRet = loanCustomerInfoMapper.updateByPrimaryKeySelective(customerInfo);
			}
			
			//更新订单信息中的customerId
			LoanOrderInfo orderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
			if(orderInfo != null){
				orderInfo.setCustomerId(baseInfo.getCustomerId());
				orderInfoRet = loanOrderInfoMapper.updateByPrimaryKeySelective(orderInfo);
			}
			
		}else{
			//插入客户基本信息、借款人信息、账户信息
			//操作客户基本信息
			LoanCustomerBaseInfo baseInfo = new LoanCustomerBaseInfo();
			baseInfo.setCustomerId(CommonUtils.getAutoIncrement("loan_customer_base_info", SystemConst.AccountType.PERSONAL.substring(0, 1), 10).toString());
			baseInfo.setCertificateNo(borrowInfo.getIdNum());
			baseInfo.setCustomerMobile(borrowInfo.getMobile());
			baseInfo.setCustomerName(borrowInfo.getBname());
			baseInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
			baseInfo.setStatus(SystemConst.Status.STATUS03);
			
			baseInfo.setUnionId(loanEmp.getCompanyId());
			baseInfo.setCompanyId(loanEmp.getBranchCompanyId());
			baseInfo.setCreateUser(loanEmp.getLeEmpId().toString());
			baseInfo.setCreateTime(new Date());
			baseInfo.setLastModifyTime(new Date());
			baseInfo.setLastUser(loanEmp.getLeEmpId().toString());
			
			custId=baseInfo.getCustomerId();
			baseInfoRet = loanCustomerBaseInfoMapper.insertSelective(baseInfo);
			
			//插入或者更新个人信息
			borrowInfo.setCustomerId(baseInfo.getCustomerId());
			borrowInfo.setUnionId(loanEmp.getCompanyId());
			borrowInfo.setCompanyId(loanEmp.getBranchCompanyId());
			borrowInfo.setCreateUser(loanEmp.getLeEmpId().toString());
			borrowInfo.setCreateTime(new Date());
			borrowInfo.setLastModifyTime(new Date());
			borrowInfo.setLastUser(loanEmp.getLeEmpId().toString());
			borrowerRet = borrowerInfoService.saveBorrowerBaseInfo(borrowInfo);
			
			//插入或者更新客户抵押物信息
			info.put("customerId", baseInfo.getCustomerId());
			mortgageRet = this.saveMortgageInfo(info);
			
			//账户信息
			LoanAccountCard card = new LoanAccountCard();
			card.setOwnerId(baseInfo.getCustomerId());
			card.setOwnerName(baseInfo.getCustomerName());
			card.setMobile(borrowInfo.getMobile());
			card.setCertificateNo(baseInfo.getCertificateNo());
			card.setAccountType(baseInfo.getCustomerType());
			card.setUnionId(baseInfo.getUnionId());
			card.setCompanyId(baseInfo.getCompanyId());
			card.setBankId(borrowInfo.getBankId());
			card.setBankName(borrowInfo.getBankName());
			card.setBankCardNo(borrowInfo.getBankCardNo());
			accountInfoRet = accountManagerService.saveOrUpdateCard(card);
			
			//操作借款人表
			LoanCustomerInfo customerInfo = new LoanCustomerInfo();
			customerInfo.setId(CommonUtils.getAutoIncrement("loan_customer_info", SystemConst.AccountType.PERSONAL, 10).toString());
			customerInfo.setCustomerId(baseInfo.getCustomerId());
			customerInfo.setCustomerMobile(borrowInfo.getMobile());
			customerInfo.setCustomerName(borrowInfo.getBname());
			customerInfo.setIdNum(borrowInfo.getIdNum());
			customerInfo.setCustomerType(SystemConst.AccountType.PERSONAL);
			customerInfo.setCustomerNature(SystemConst.LoanCustomerNature.PERSONAL);
			
			customerInfo.setAccountId(card.getAccountId());
			
			customerInfo.setStatus(SystemConst.Status.STATUS03);
			customerInfo.setUnionId(loanEmp.getCompanyId());
			customerInfo.setCompanyId(loanEmp.getBranchCompanyId());
			customerInfo.setCreateUser(loanEmp.getLeEmpId().toString());
			customerInfo.setCreateTime(new Date());
			customerInfo.setLastUser(loanEmp.getLeEmpId().toString());
			customerInfo.setLastModifyTime(new Date());
			customerInfo.setManagerId(loanOrderInfo.getCustomerManager().toString());
			customerInfo.setDeptId(loanOrderInfo.getDepId());
			customerInfoRet = loanCustomerInfoMapper.insertSelective(customerInfo);
			
			//更新订单信息中的customerId
			LoanOrderInfo orderInfo = loanOrderInfoMapper.selectByPrimaryKey(orderNo);
			if(orderInfo != null){
				orderInfo.setCustomerId(baseInfo.getCustomerId());
				orderInfoRet = loanOrderInfoMapper.updateByPrimaryKeySelective(orderInfo);
			}
		}
		LoanUserOrder loanUserOrder=loanUserOrderMapper.selectLoanUserOrderByOrderNo(orderNo);
		//不能因为意向客户影响主流程，是否更新成功，不用判断。
		if(loanUserOrder!=null){
			CrmIntentLoanUser crmIntentLoanUser=new CrmIntentLoanUser();
			crmIntentLoanUser.setId(Integer.parseInt(loanUserOrder.getIntentUserId()));
			crmIntentLoanUser.setCustId(custId);
			updateIntentCustId=crmIntentLoanUserMapper.updateByPrimaryKeySelective(crmIntentLoanUser);
		}
		if(SystemConst.SUCCESS.equals(borrowerRet.get(SystemConst.retCode)) 
		   && baseInfoRet == 1 && customerInfoRet == 1 && orderInfoRet == 1
		   && SystemConst.SUCCESS.equals(accountInfoRet)
		   && SystemConst.SUCCESS.equals(mortgageRet.get(SystemConst.retCode))
		   ){
			return true;
		}else{
			throw new RuntimeException("借款客户信息操作失败");
		}
	}
	
	/**
	 * saveMortgageInfo: 保存借款人抵押物信息<br/>
	 * @author xiangfeng
	 * @param param Map key:(custId、houseInfoLists、carInfoLists)
	 * @return
	 * @since JDK 1.8
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveMortgageInfo(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		String custId = (String) param.get("custId");
		String customerId = (String) param.get("customerId");
		String houseInfoLists = (String) param.get("houseInfoLists");
		String carInfoLists = (String) param.get("carInfoLists");
		List<LoanHouseInfo> houseLists = new ArrayList<>();
		List<LoanCarInfo> carLists = new ArrayList<>();
		
		if (!StrUtils.isNullOrEmpty(houseInfoLists)) {
			houseLists = JSON.parseArray(houseInfoLists, LoanHouseInfo.class);
		}
		if (!StrUtils.isNullOrEmpty(carInfoLists)) {
			carLists = JSON.parseArray(carInfoLists, LoanCarInfo.class);
		}
		int houseNum = houseInfoService.saveHouseInfo(houseLists, custId,customerId);
		int carNum = carInfoService.saveCarInfo(carLists, custId,customerId);
		if (houseNum == houseLists.size() && carNum == carLists.size()) {
			result.put(SystemConst.retCode, SystemConst.SUCCESS);
		} else {
			result.put(SystemConst.retCode, SystemConst.FAIL);
			logger.debug("借款人抵押物信息更新失败，需要重新调试接口");
		}
		return result;
	}

	@Override
	public int queryOrderHouseExtend(LoanOrderHouseExtend entity) {
		return loanOrderHouseExtendMapper.queryOrderHouseExtend(entity);
	}
}
