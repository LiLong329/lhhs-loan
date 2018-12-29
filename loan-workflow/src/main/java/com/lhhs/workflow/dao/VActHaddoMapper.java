package com.lhhs.workflow.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.workflow.dao.domain.VActHaddoVO;

public interface VActHaddoMapper extends CrudDao<VActHaddoVO>{

	List<VActHaddoVO> findHadOnlyLine(String id);

	List<VActHaddoVO> findHadOnlyCom(String id);

	List<VActHaddoVO> findHadOnlyOffice(String id);

	List<VActHaddoVO> findHadOnlyDpt(String id);
	
	
	
	
}