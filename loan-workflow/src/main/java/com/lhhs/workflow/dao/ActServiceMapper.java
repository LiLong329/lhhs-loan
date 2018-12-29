package com.lhhs.workflow.dao;
import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.workflow.dao.domain.ActServiceVO;

public interface ActServiceMapper extends CrudDao<ActServiceVO>{
	public ActServiceVO selectByActID(String procInsIds);
}