package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanAffiliatedEnterpriseUrl;
import com.lhhs.loan.dao.domain.LoanOrderAffiliatedEnterpriseInfo;

public interface AffiliatedEnterpriseService {

	public List<LoanOrderAffiliatedEnterpriseInfo> queryList(LoanOrderAffiliatedEnterpriseInfo entity);
	
	public List<LoanAffiliatedEnterpriseUrl> queryUrlList(LoanAffiliatedEnterpriseUrl entity);

	public int saveEnterpriseInfo(List<LoanOrderAffiliatedEnterpriseInfo> list, String orderNo);
	
	public int saveEnterpriseUrl(LoanAffiliatedEnterpriseUrl enterpriseUrl);

	public int deleteEnterpriseUrl(Long id);

}
