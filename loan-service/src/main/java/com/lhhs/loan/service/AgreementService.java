package com.lhhs.loan.service;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.loan.dao.domain.Agreement;

public interface AgreementService extends BaseService<Agreement> {

	int saveAuditingInfo(Agreement params);

	Agreement getByEntity(Agreement params);
	
}

