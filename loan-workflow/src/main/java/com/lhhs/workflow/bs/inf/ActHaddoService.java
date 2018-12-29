package com.lhhs.workflow.bs.inf;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.workflow.dao.domain.VActHaddoVO;

public interface ActHaddoService extends BaseService<VActHaddoVO>{

	List<VActHaddoVO> findHadOnlyLine(String id);

	List<VActHaddoVO> findHadOnlyCom(String id);

	List<VActHaddoVO> findHadOnlyOffice(String id);

	List<VActHaddoVO> findHadOnlyDpt(String id);
 
}
