package com.lhhs.workflow.bs.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhhs.loan.common.service.CrudService;
import com.lhhs.workflow.bs.inf.ActCommentService;
import com.lhhs.workflow.dao.ActCommentMapper;
import com.lhhs.workflow.dao.domain.TaskVo;
@Service
@Transactional
public class ActCommentServiceImpl extends CrudService<ActCommentMapper, TaskVo> implements ActCommentService{
	/**
	 * 查询已审批的节点列表
	 */
	public List<TaskVo> listTastDefKey(TaskVo parm){
		return dao.listTastDefKey(parm);
	}
	public List<TaskVo> queryChangeBusinessList(TaskVo parm){
		return dao.queryChangeBusinessList(parm);
	}
	/**
	 * 
	 * 查询流程审批记录
	 */
	public List<TaskVo> transTaskByProcInsId(String procInsId){
		return dao.transTaskByProcInsId(procInsId);
	}
}
