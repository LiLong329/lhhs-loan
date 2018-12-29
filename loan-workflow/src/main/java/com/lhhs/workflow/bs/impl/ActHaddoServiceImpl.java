package com.lhhs.workflow.bs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.workflow.bs.inf.ActHaddoService;
import com.lhhs.workflow.dao.VActHaddoMapper;
import com.lhhs.workflow.dao.domain.VActHaddoVO;

@Service
public class ActHaddoServiceImpl extends CrudService<VActHaddoMapper, VActHaddoVO> implements ActHaddoService {
 
	@Autowired
	private VActHaddoMapper vachaddo; 
	@Override
	public List<VActHaddoVO> findHadOnlyLine(String id) {
		// TODO Auto-generated method stub
		List<VActHaddoVO> vo=vachaddo.findHadOnlyLine(id);
		return vo;
	}

	@Override
	public List<VActHaddoVO> findHadOnlyCom(String id) {
		// TODO Auto-generated method stub
		List<VActHaddoVO> vo=vachaddo.findHadOnlyCom(id);
		return vo;
	}

	@Override
	public List<VActHaddoVO> findHadOnlyOffice(String id) {
		// TODO Auto-generated method stub
		List<VActHaddoVO> vo=vachaddo.findHadOnlyOffice(id);
		return vo;
	}

	@Override
	public List<VActHaddoVO> findHadOnlyDpt(String id) {
		// TODO Auto-generated method stub
		List<VActHaddoVO> vo=vachaddo.findHadOnlyDpt(id);
		return vo;
	}

}
