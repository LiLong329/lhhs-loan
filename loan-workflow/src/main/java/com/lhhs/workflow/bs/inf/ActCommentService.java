package com.lhhs.workflow.bs.inf;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.workflow.dao.domain.TaskVo;

public interface ActCommentService extends BaseService<TaskVo>{
	/**
	 * 查询已审批的节点列表
	 */
	public List<TaskVo> listTastDefKey(TaskVo parm);
	/**
	 * 查询已变化的流程
	 */
	public List<TaskVo> queryChangeBusinessList(TaskVo parm);
	/**
	 * 
	 * 查询流程审批记录
	 */
	public List<TaskVo> transTaskByProcInsId(String procInsId);
}
