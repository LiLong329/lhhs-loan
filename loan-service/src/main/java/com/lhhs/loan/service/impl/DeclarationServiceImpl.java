package com.lhhs.loan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.AreaApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.Area;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.utils.GetUniqueNoUtil;
import com.lhhs.loan.common.utils.Identities;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.AgreementMapper;
import com.lhhs.loan.dao.CrmIntentLoanUserMapper;
import com.lhhs.loan.dao.LoanOrderBorrowerExtendMapper;
import com.lhhs.loan.dao.LoanOrderInfoDetailMapper;
import com.lhhs.loan.dao.LoanOrderInfoMapper;
import com.lhhs.loan.dao.LoanProviderInfoMapper;
import com.lhhs.loan.dao.LoanUserOrderMapper;
import com.lhhs.loan.dao.domain.Agreement;
import com.lhhs.loan.dao.domain.AgreementType;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.LoanProviderInfo;
import com.lhhs.loan.dao.domain.LoanUserOrder;
import com.lhhs.loan.service.CredentialsInfoService;
import com.lhhs.loan.service.DeclarationService;

@Service
public class DeclarationServiceImpl implements DeclarationService {
	@Autowired
	private LoanOrderInfoMapper loanOrderInfoMapper;
	@Autowired
	private LoanUserOrderMapper loanUserOrderMapper;
	@Autowired
	private LoanOrderInfoDetailMapper loanOrderInfoDetailMapper;
	@Autowired
	private LoanOrderBorrowerExtendMapper loanOrderBorrowerExtendMapper;
	@Autowired
	private CredentialsInfoService credentialsInfoService;
	@Autowired
	private LoanProviderInfoMapper loanProviderInfoMapper;
	@Autowired
	private CrmIntentLoanUserMapper crmIntentLoanUserMapper;
	@Autowired
	private AgreementMapper agreementMapper;
	@Reference
	private AreaApi areaApi;
	@Reference
	private UnionCompanyApi unionCompanyApi;
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addDeclaration(LoanOrderInfo loanOrderInfo, LoanOrderInfoDetail loanOrderInfoDetail,
			LoanOrderBorrowerExtendWithBLOBs borrower) {
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		Map<String, Object> res = new HashMap<String, Object>();
		int ret = 0;
		// String orderNo = Identities.uuid2();
		String orderNo = GetUniqueNoUtil.getCustNo();
		loanOrderInfo.setOrderNo(orderNo);
		loanOrderInfo.setOrderStatus(-1);
		loanOrderInfo.setApplyDate(new Date());
		loanOrderInfo.setOperatorDate(new Date());
		loanOrderInfo.setIsLoanAdd("Y");
		// 默认未放款
		loanOrderInfo.setLoanMethod("0");
		// 客户来源
		if (!StrUtils.isNullOrEmpty(loanOrderInfo.getCreaterCompanyId())
				&& !StrUtils.isNullOrEmpty(loanOrderInfo.getAppointCompanyId())
				&& !loanOrderInfo.getCreaterCompanyId().equals(loanOrderInfo.getAppointCompanyId())) {
			loanOrderInfo.setCustomerSource("1");
		} else {
			loanOrderInfo.setCustomerSource("0");
		}
		// 业务类型
		if (!StrUtils.isNullOrEmpty(loanOrderInfo.getBusinessType()) && "1".equals(loanOrderInfo.getBusinessType())) {
			loanOrderInfo.setBusinessType("1");
		} else {
			loanOrderInfo.setBusinessType("0");
		}
		ret += loanOrderInfoMapper.insertSelective(loanOrderInfo);
		loanOrderInfoDetail.setOrderNo(orderNo);
		ret += loanOrderInfoDetailMapper.insertSelective(loanOrderInfoDetail);
		if(!StrUtils.isNullOrEmpty(loanOrderInfo.getCustId())){
			borrower.setCustId(loanOrderInfo.getCustId());
		}else{
			borrower.setCustId(Identities.uuid2());
		}
		borrower.setOrderNo(orderNo);
		ret += loanOrderBorrowerExtendMapper.insertSelective(borrower);
		
		LoanUserOrder loanUserOrder = new LoanUserOrder();
		loanUserOrder.setIntentUserId(loanOrderInfo.getIntentUserId());
		loanUserOrder.setOrderNo(loanOrderInfo.getOrderNo());
		loanUserOrder.setCreatDate(new Date());
		CrmIntentLoanUser crmIntentUser=new CrmIntentLoanUser();
		crmIntentUser.set_id(loanOrderInfo.getIntentUserId());
		crmIntentUser.setAppointEmpId(loanEmp.getUserId().toString());
		if(null!=loanOrderInfo.getIntentUserId()&&!"".equals(loanOrderInfo.getIntentUserId())){
			CrmIntentLoanUser selectedcrmIntentUser = crmIntentLoanUserMapper.selectIntentUserByOrder(Integer.parseInt(loanOrderInfo.getIntentUserId()));
			if (loanEmp.getUserId().intValue() == Integer.parseInt(selectedcrmIntentUser.getAppointEmpId())) {
				ret += loanUserOrderMapper.insertSelective(loanUserOrder);
			} 
		} 
		
//      意向客户保护期30天暂时注销 suncj-start
//		else {
//			List<LoanUserOrder> userOrderList=loanUserOrderMapper.selectLoanUserOrderById(Integer.parseInt(loanUserOrder.getIntentUserId()));
//			if(userOrderList!=null&&userOrderList.size()!=0){
//				if (DateUtils.pastDays(selectedcrmIntentUser.getCreateTime()) > 30) {
//					ret += loanUserOrderMapper.insertSelective(loanUserOrder);
//					int flag=crmIntentLoanUserMapper.updateByPrimaryKey(crmIntentUser);
//					if (flag < 1) {
//						res.put(SystemConst.retCode, SystemConst.FAIL);
//						res.put(SystemConst.retMsg, "更新客户经理失败");// 新增报单失败
//						return res;
//					}
//				} else {
//					res.put(SystemConst.retCode, SystemConst.FAIL);
//					res.put(SystemConst.retMsg, "无权创建报单");
//					return res;
//				}
//			}
//		}
//      意向客户保护期30天暂时注销 suncj-end
		
		// 复制产品资质列表
		if (!StrUtils.isNullOrEmpty(loanOrderInfo.getChildProductNo())) {
			credentialsInfoService.copyProductCredentialsToOrderCredentials(orderNo, loanOrderInfo.getChildProductNo());
			// res=mortgageInfoTransService.changeOrderCredentials(orderNo,loanOrderInfo.getChildProductNo());
		}

		if (null != loanOrderInfo.getCustId()) {
			CrmIntentLoanUser intentLoanUser = new CrmIntentLoanUser();
			intentLoanUser.setBusinessStatus("08");
			intentLoanUser.setId(Integer.parseInt(loanOrderInfo.getCustId()));
			int flag = crmIntentLoanUserMapper.updateByPrimaryKeySelective(intentLoanUser);
			if (flag < 1) {
				res.put(SystemConst.retCode, SystemConst.FAIL);
				res.put(SystemConst.retMsg, "\u65b0\u589e\u62a5\u5355\u5931\u8d25");// 新增报单失败
				return res;
			}
		}
		if (ret >= 3) {
			res.put(SystemConst.retCode, SystemConst.SUCCESS);
			//生成合同
			Agreement agreement = new Agreement();
			//获取区域英文简称
			Area area = new Area();
			area.setName(loanOrderInfo.getProvince());
			area.setParentCode(0);
			area = areaApi.get(area);
			//获取公司英文简称
			UnionCompany union = unionCompanyApi.get(loanOrderInfo.getUnionId());
			//获取分公司英文简称
			UnionCompany company = unionCompanyApi.get(loanOrderInfo.getCompanyId());
			//格式化日期
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
			Date date = new Date();
			//生成合同编号
			String agreementNo = union.getEnglishName()+"-"+area.getField3()+"-"+company.getEnglishName()+"-"+AgreementType.JKHT+"-"+dateFormat.format(date);
			agreement.setAgreementNo(agreementNo);
			agreement.setOrderNo(orderNo);
			agreement.setBorrowerName(borrower.getBname());
			agreement.setBorrowerIdNum(borrower.getIdNum());
			agreement.setType(AgreementType.JKHT);
			agreement.setCreateTime(date);
			agreement.setAuditingStatus("0");
			agreement.setCompanyId(loanOrderInfo.getCompanyId());
			agreement.setUnionId(loanOrderInfo.getUnionId());
			agreement.setDeptId(loanOrderInfo.getDepId());
			agreement.setLoanAmount(loanOrderInfoDetail.getLoanAmount());
			agreement.setLoanTerm(loanOrderInfoDetail.getLoanTerm());
			agreement.setLoanTermUnit(loanOrderInfoDetail.getLoanTermUnit());
			
			agreementMapper.insertSelective(agreement);
		} else {
			res.put(SystemConst.retCode, SystemConst.FAIL);
			res.put(SystemConst.retMsg, "\u65b0\u589e\u62a5\u5355\u5931\u8d25");// 新增报单失败
		}
		return res;
	}
public static void main(String[] args) {
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
	System.out.println(dateFormat.format(date));
}
	/**
	 * 根据当前登录用户查询报单人
	 * 
	 * @param loanEmp
	 * @return List<LoanProviderInfo>
	 */
	@Override
	public List<LoanProviderInfo> selectProviderByEmp(LoanEmp loanEmp) {
		return loanProviderInfoMapper.selectProviderByEmp(loanEmp);
	}

}
