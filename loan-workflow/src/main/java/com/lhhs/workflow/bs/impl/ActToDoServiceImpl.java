package com.lhhs.workflow.bs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.workflow.bs.inf.ActToDoService;
import com.lhhs.workflow.dao.VActToDoMapper;
import com.lhhs.workflow.dao.domain.VActToDoVO;
@Service
public class ActToDoServiceImpl extends CrudService<VActToDoMapper, VActToDoVO> implements ActToDoService {

	@Autowired
	private VActToDoMapper vactodo; 
	
	@Override
	public List<VActToDoVO> findOnlyLine(String string) {
		// TODO Auto-generated method stub
		List<VActToDoVO>  vo= vactodo.findOnlyLine(string);
		return vo;
	}


	@Override
	public List<VActToDoVO> findOnlyCom(String string) {
		// TODO Auto-generated method stub
		List<VActToDoVO>  vo=vactodo.findOnlyCom(string);
		return vo;
	}


	@Override
	public List<VActToDoVO> findOnlyOffice(String string) {
		// TODO Auto-generated method stub
		List<VActToDoVO>  vo=vactodo.findOnlyOffice(string);
		return vo;
	}


	@Override
	public List<VActToDoVO> findOnlyDpt(String string) {
		// TODO Auto-generated method stub
		List<VActToDoVO>  vo=vactodo.findOnlyDpt(string);
		return vo;
	}


	
}
