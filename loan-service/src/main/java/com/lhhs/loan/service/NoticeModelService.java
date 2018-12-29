package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.NoticeModel;

public interface NoticeModelService extends BaseService<NoticeModel> {

	List<NoticeModel> getByType(NoticeModel noticeModel);

	int updateModelStatus(NoticeModel params);
	
}

