package com.lhhs.loan.service;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.NoticeRecord;

public interface NoticeRecordService extends BaseService<NoticeRecord> {

	void saveList(List<NoticeRecord> noticeRecords);
}

