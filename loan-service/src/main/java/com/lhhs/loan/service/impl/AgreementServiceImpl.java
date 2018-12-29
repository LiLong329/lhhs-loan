package com.lhhs.loan.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StrUtils;
import com.lhhs.loan.dao.AgreementMapper;
import com.lhhs.loan.dao.RelevantPersonAgreementMapper;
import com.lhhs.loan.dao.domain.Agreement;
import com.lhhs.loan.dao.domain.RelevantPersonAgreement;
import com.lhhs.loan.service.AgreementService;

@Service
public class AgreementServiceImpl implements AgreementService {
	
	@Autowired
	private AgreementMapper agreementMapper;
	
	@Autowired
	private RelevantPersonAgreementMapper relevantPersonAgreementMapper;
	
	@Override
	public Agreement get(String id) {
		Agreement agreement = agreementMapper.selectByPrimaryKey(Integer.valueOf(id));
		RelevantPersonAgreement params =  new RelevantPersonAgreement();
		params.setAgreementNo(agreement.getAgreementNo());
		//查询相关人列表
		List<RelevantPersonAgreement> list = relevantPersonAgreementMapper.queryList(params);
		//联系人
		List<RelevantPersonAgreement> contactsList = new LinkedList<RelevantPersonAgreement>();
		//共借人
		List<RelevantPersonAgreement> totalLoanList = new LinkedList<RelevantPersonAgreement>();
		//担保人
		List<RelevantPersonAgreement> guaranteeList = new LinkedList<RelevantPersonAgreement>();
		if (list!=null&&list.size()>0) {
			//分类
			for (int i = 0; i < list.size(); i++) {
				RelevantPersonAgreement rpa = list.get(i);
				if ("1".equals(rpa.getType())) {
					contactsList.add(rpa);
				}else if ("2".equals(rpa.getType())) {
					totalLoanList.add(rpa);
				}else if ("3".equals(rpa.getType())) {
					guaranteeList.add(rpa);
				}
			}
		}
		agreement.setContactsList(contactsList);
		agreement.setTotalLoanList(totalLoanList);
		agreement.setGuaranteeList(guaranteeList);
		return agreement;
	}

	@Override
	public Agreement get(Agreement entity) {
		return agreementMapper.getByEntity(entity);
	}

	@Override
	public List<Agreement> queryList(Agreement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page queryListPage(Agreement entity) {
		Page page = entity.getPage();
		page.setPageIndex(entity.getPageIndex());
		page.setResultList(agreementMapper.queryListPage(entity));
		page.setTotalCount(agreementMapper.queryCount(entity));
		return page;
	}

	@Override
	public int save(Agreement entity) {
		return agreementMapper.insertSelective(entity);
	}

	@Override
	public int update(Agreement entity) {
		return agreementMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(Agreement entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(Agreement entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public int saveAuditingInfo(Agreement params) {
		List<RelevantPersonAgreement> relevantPersonList=StrUtils.isNullOrEmpty(params.getRpaList())?null:JSON.parseArray(params.getRpaList(), RelevantPersonAgreement.class);
		RelevantPersonAgreement vo= new RelevantPersonAgreement();
		vo.setAgreementNo(params.getAgreementNo());
		relevantPersonAgreementMapper.delete(vo);
		if (relevantPersonList!=null&&relevantPersonList.size()>0) {
			relevantPersonAgreementMapper.insertList(relevantPersonList);
		}
		params.setUpdateTime(new Date());
		return agreementMapper.updateByPrimaryKeySelective(params);
	}

	@Override
	public Agreement getByEntity(Agreement params) {
		return agreementMapper.getByEntity(params);
	}
}

