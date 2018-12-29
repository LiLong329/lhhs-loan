package com.lhhs.workflow.bs.inf;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.workflow.dao.domain.VActToDoVO;

public interface ActToDoService extends BaseService<VActToDoVO>{

	List<VActToDoVO> findOnlyLine(String string);

	List<VActToDoVO> findOnlyCom(String string);

	List<VActToDoVO> findOnlyOffice(String string);

	List<VActToDoVO> findOnlyDpt(String string);

}
