package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanCapitalEarningMapper;
import com.lhhs.loan.dao.LoanCapitalExpenditureMapper;
import com.lhhs.loan.dao.LoanFeesPlanMapper;
import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.LoanFeesRecord;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;
import com.lhhs.loan.service.FeesPlanService;
import com.lhhs.loan.service.account.AccountTransactionService;

@Service
public class FeesPlanServiceImpl implements FeesPlanService {

	@Autowired
	private LoanFeesPlanMapper feesPlanMapper;
	@Autowired
	private LoanCapitalEarningMapper loanCapitalEarningMapper;
	@Autowired
	private LoanCapitalExpenditureMapper loanCapitalExpenditureMapper;
	
	@Autowired
	private AccountTransactionService accountTransactionService;
	
	@Override
	public Page findPageByEntity(Page page, FeesPlanVo entity) {
		entity.setPage(page);
		List<FeesPlanVo> resultList = feesPlanMapper.queryFeesPlanVoList(entity);
		page.setResultList(resultList);
		page.setTotalCount(feesPlanMapper.queryFeesPlanVoCount(entity));
		return page;
	}

	@Override
	public String feesPlanService(LoanFeesPlan loanFeesPlan) {
		SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
		if(loanFeesPlan.getDoTime() == null  || loanFeesPlan.getPaidAmount() == null || user.getUserId() == null) {
			return null;
		}
		LoanFeesRecord loanFeesRecord = new LoanFeesRecord();
		
		loanFeesRecord.setPaidAmount(loanFeesPlan.getPaidAmount());
		loanFeesRecord.setFeesId(loanFeesPlan.getFeesId());
		loanFeesRecord.setDoTime(loanFeesPlan.getDoTime());
		loanFeesRecord.setCreateUser("" + user.getUserId() );
		loanFeesRecord.setRemark(loanFeesPlan.getRemark() );
		loanFeesRecord.setTransType(loanFeesPlan.getTransType());
		if(null!=loanFeesPlan.getSubjectId()) loanFeesRecord.setSubjectId(loanFeesPlan.getSubjectId());
		return  accountTransactionService.loanFeesTrans(loanFeesRecord);
	}
	
	@Override
	public List<LoanFeesPlan> queryFeesPlanInByOrderNo(String orderNo) {
		return loanCapitalEarningMapper.queryFeesPlanInByOrderNo(orderNo);
	}

	@Override
	public List<LoanFeesPlan> queryFeesPlanOutByOrderNo(String orderNo) {
		return loanCapitalExpenditureMapper.queryFeesPlanOutByOrderNo(orderNo);
	}

}
