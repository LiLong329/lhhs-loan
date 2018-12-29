package com.lhhs.workflow.dao;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.workflow.dao.domain.TaskVo;

public interface ActCommentMapper extends CrudDao<TaskVo>{
	/**
	 * 查询已审批的节点列表
	 */
	public List<TaskVo> listTastDefKey(TaskVo taskVo);
	/**
	 * 
	 * 查询放款流程变化的审批记录
	 */
	public List<TaskVo> queryChangeBusinessList(TaskVo taskVo);
	
	/**
	 * 
	 * 查询流程审批记录
	 */
	public List<TaskVo> transTaskByProcInsId(String procInsId);
	
	
}