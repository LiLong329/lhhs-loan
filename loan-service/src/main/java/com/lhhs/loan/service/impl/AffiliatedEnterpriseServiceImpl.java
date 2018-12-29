package com.lhhs.loan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.LoanAffiliatedEnterpriseUrlMapper;
import com.lhhs.loan.dao.LoanOrderAffiliatedEnterpriseInfoMapper;
import com.lhhs.loan.dao.domain.LoanAffiliatedEnterpriseUrl;
import com.lhhs.loan.dao.domain.LoanOrderAffiliatedEnterpriseInfo;
import com.lhhs.loan.service.AffiliatedEnterpriseService;

@Service
public class AffiliatedEnterpriseServiceImpl implements AffiliatedEnterpriseService {

	@Autowired
	private LoanOrderAffiliatedEnterpriseInfoMapper enterpriseDao;
	@Autowired
	private LoanAffiliatedEnterpriseUrlMapper enterpriseUrlDao;
	
	@Override
	public List<LoanOrderAffiliatedEnterpriseInfo> queryList(LoanOrderAffiliatedEnterpriseInfo entity) {
		return enterpriseDao.queryList(entity);
	}

	@Override
	public List<LoanAffiliatedEnterpriseUrl> queryUrlList(LoanAffiliatedEnterpriseUrl entity) {
		return enterpriseUrlDao.queryList(entity);
	}

	@Override
	public int saveEnterpriseUrl(LoanAffiliatedEnterpriseUrl enterpriseUrl) {
		return enterpriseUrlDao.insertSelective(enterpriseUrl);
	}

	@Override
	public int deleteEnterpriseUrl(Long id) {
		return enterpriseUrlDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveEnterpriseInfo(List<LoanOrderAffiliatedEnterpriseInfo> list, String orderNo) {
		int num=0;
		LoanOrderAffiliatedEnterpriseInfo info=new LoanOrderAffiliatedEnterpriseInfo();
		LoanAffiliatedEnterpriseUrl url=new LoanAffiliatedEnterpriseUrl();
		info.setOrderNo(orderNo);
		enterpriseDao.delete(info);
		for (int i = 0; i < list.size(); i++) {
			info=list.get(i);
			int j=enterpriseDao.insertSelective(info);
			if(j==1){
				if(!StrUtils.isNullOrEmpty(info.getUrlIdList())){
					String[] idList=info.getUrlIdList().split(",");
					url.setAffiliatedEnterpriseId(info.getAffiliatedEnterpriseId().toString());
					for (int k = 0; k < idList.length; k++) {
						url.setId(Long.valueOf(idList[k]));
						enterpriseUrlDao.updateByPrimaryKeySelective(url);
					}
				}
				num+=j;
			}
		}
		return num;
	}

}
