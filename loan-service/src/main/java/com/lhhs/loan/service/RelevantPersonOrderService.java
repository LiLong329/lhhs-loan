package com.lhhs.loan.service;

import java.util.List;
import com.lhhs.loan.dao.domain.RelevantPersonOrder;

public interface RelevantPersonOrderService {
	
	public List<RelevantPersonOrder> queryList(RelevantPersonOrder entity);
	
}
