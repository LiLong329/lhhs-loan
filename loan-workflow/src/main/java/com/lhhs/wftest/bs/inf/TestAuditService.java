/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.lhhs.wftest.bs.inf;

import java.util.List;

import com.lhhs.loan.common.service.BaseService;
import com.lhhs.wftest.model.SysTestAudit;
import com.lhhs.wftest.model.TestAudit;

/**
 * 审批Service接口
 * @author thinkgem
 * @version 2014-05-16
 */

public interface TestAuditService extends BaseService<TestAudit>{

	public TestAudit getByProcInsId(String procInsId) ;
	
	
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 */
	
	public void insert(TestAudit testAudit) throws Exception ;

	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	public void auditSave(TestAudit testAudit) throws Exception;
	
	/**
	 * 查找审批列表
	 */
	public List<SysTestAudit> getAllAssass();
}
