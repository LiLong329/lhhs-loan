package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.LoanFeesPlan;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;

public interface LoanFeesPlanMapper  extends CrudDao<LoanFeesPlan>{

	List<FeesPlanVo> queryFeesPlanVoList(FeesPlanVo entity);

	int queryFeesPlanVoCount(FeesPlanVo entity);

	int deleteByOrderNo(String orderNo);

	List<FeesPlanVo> queryFeesPlanVoListSum(FeesPlanVo feesPlanVo);
   
}