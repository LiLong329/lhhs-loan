package com.lhhs.loan.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.QuartersApi;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.NoticeModelMapper;
import com.lhhs.loan.dao.domain.NoticeModel;
import com.lhhs.loan.service.NoticeModelService;

@Service
public class NoticeModelServiceImpl implements  NoticeModelService{
	
	@Autowired
	private NoticeModelMapper noticeModelMapper;
	@Reference
	private QuartersApi quartersApi;
	
	@Override
	public NoticeModel get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoticeModel get(NoticeModel entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoticeModel> queryList(NoticeModel entity) {
		List<NoticeModel> list = noticeModelMapper.queryList(entity);
		if (list!=null&&list.size()>0) {
			for (NoticeModel model : list) {
				if (StringUtils.isNotEmpty(model.getReceiver())) {
					String [] quartersEnNames = model.getReceiver().split(",");
					List<String> quartersNames = quartersApi.getByEnNames(quartersEnNames);
					model.setQuartersNameList(quartersNames);
				}
			}
		}
		return list;
	}

	@Override
	public Page queryListPage(NoticeModel entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(NoticeModel entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NoticeModel entity) {
		return noticeModelMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(NoticeModel entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryCount(NoticeModel entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<NoticeModel> getByType(NoticeModel noticeModel) {
		List<NoticeModel> list = noticeModelMapper.getByType(noticeModel);
		return list;
	}

	@Override
	public int updateModelStatus(NoticeModel params) {
		return noticeModelMapper.updateModelStatus(params);
	}
	
}

