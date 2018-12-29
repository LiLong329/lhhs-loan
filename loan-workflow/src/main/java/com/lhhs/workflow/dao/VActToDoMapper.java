package com.lhhs.workflow.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.workflow.dao.domain.VActToDoVO;
public interface VActToDoMapper extends CrudDao<VActToDoVO>{

	List<VActToDoVO> findOnlyCom(String string);

	List<VActToDoVO> findOnlyOffice(String string);

	List<VActToDoVO> findOnlyLine(String string);

	List<VActToDoVO> findOnlyDpt(String string);
}