package com.lhhs.workflow.bs.impl;

import org.springframework.stereotype.Service;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.workflow.bs.inf.ActServiceService;
import com.lhhs.workflow.dao.ActServiceMapper;
import com.lhhs.workflow.dao.domain.ActServiceVO;
@Service
public class ActServiceServiceImpl extends CrudService<ActServiceMapper, ActServiceVO>implements ActServiceService {

	
}
