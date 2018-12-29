/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.lhhs.wftest.dao.inf;

import java.util.List;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.wftest.model.SysTestAudit;
import com.lhhs.wftest.model.TestAudit;

/**
 * 审批DAO接口
 * @author dongfei
 * @version 2016-09-08
 */
public interface TestAuditDao extends CrudDao<TestAudit> {

	public TestAudit getByProcInsId(String procInsId);
	
	public int updateInsId(TestAudit testAudit);
	
	public int updateHrText(TestAudit testAudit);
	
	public int updateLeadText(TestAudit testAudit);
	
	public int updateMainLeadText(TestAudit testAudit);
	
	public List<SysTestAudit> getAll();
	
	public String getOfficeId(String id);
	
}
