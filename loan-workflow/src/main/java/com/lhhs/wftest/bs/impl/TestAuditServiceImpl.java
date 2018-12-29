package com.lhhs.wftest.bs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.lhhs.loan.common.service.CrudService;
import com.lhhs.wftest.bs.inf.TestAuditService;
import com.lhhs.wftest.dao.inf.TestAuditDao;
import com.lhhs.wftest.model.SysTestAudit;
import com.lhhs.wftest.model.TestAudit;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.common.enumeration.Channel;
import com.lhhs.workflow.common.enumeration.ProcDefKey;
import com.lhhs.workflow.common.enumeration.Resolution;
import com.lhhs.workflow.common.utils.ActUtils;
import com.lhhs.workflow.common.utils.Constant;
import com.lhhs.workflow.common.utils.IdGen;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.User;

/**
 * 审批Service
 * @author dongfei
 * @version 2016-09-12
 */
@Transactional
@Service("TestAuditService")
public class TestAuditServiceImpl extends CrudService<TestAuditDao, TestAudit> implements TestAuditService {

	@Autowired
	private ActTaskService actTaskService;
	public TestAudit getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, Exception.class})
	public void insert(TestAudit testAudit) throws Exception {
		// 申请发起
		if (StringUtils.isBlank(testAudit.getId())){
			//得到用户信息
			User user = testAudit.getCreateBy();
			//得到用户id
			String userid = user.getUserName();
			//得到officeId
			String officeId = dao.getOfficeId(userid);
			
			testAudit.setUser(user);
			testAudit.setOfficeId(officeId);
			testAudit.setId(IdGen.uuid());
			testAudit.preInsert();
			dao.insert(testAudit);
			//设置流程变量
			Map<String, Object> vars =new HashMap();
			
			//平台
			if(StringUtils.isNotEmpty(testAudit.getBranchcom())){
				vars.put("branchcom", testAudit.getBranchcom());
			}
			//销售办公室
			if(StringUtils.isNotEmpty(testAudit.getSalesoffice())){
				vars.put("salesoffice", testAudit.getSalesoffice());
			}
			//事业部
			if(StringUtils.isNotEmpty(testAudit.getProductdpt())){
				vars.put("productdpt", testAudit.getProductdpt());
			}
			//产品线
			if(StringUtils.isNotEmpty(testAudit.getProductline())){
				vars.put("productline", testAudit.getProductline());
			}
			//部门
			if(StringUtils.isNotEmpty(testAudit.getDepartment())){
				vars.put("department", testAudit.getDepartment());
			}
			//设置会签人员、设置启动子流程、多实例任务集合
			if(StringUtils.isNotEmpty(testAudit.getAssigneelist())){
				String[] str=testAudit.getAssigneelist().split(",");
				List<String> sublist=new ArrayList();
				//设置子流程权限过滤KEY
				Map subvars =new HashMap();
				for(int i=0;i<str.length;i++){
					//设置生成子流程集合
					sublist.add(str[i]);
					//如果Subkey不为空，分别设置子流程、多实例用户权限过滤流程变量
					if(StringUtils.isNotEmpty(testAudit.getSubkey())){
						Map map =new HashMap();
						map.put(testAudit.getSubkey(), str[i]);
						//可以设置多个
						//map.put("department", ***);
						//map.put("branchcom", ***);
						//map.put("productline", ***);
						subvars.put(str[i], map);
					}
					
				}
				vars.put(Constant.Field.ASSIGNEELIST, sublist);
				if(StringUtils.isNotEmpty(testAudit.getSubkey())){
					vars.put(Constant.Field.SUBKEY, subvars);
				}
				
			}
			
			//流程定义KEY
			String procDefKey=testAudit.getProcDefKey();
			if(StringUtils.isEmpty(procDefKey)){
				procDefKey=ProcDefKey.TESTAUDIT.getKey();
			}
			String userId = user.getUserId();//ObjectUtils.toString(UserUtils.getUser().getId())
			
			ActVo parm =new ActVo(procDefKey,  testAudit.getId(),  userId,  testAudit.getContent(),  vars,  "BA",  "1" ,  ActUtils.PD_TEST_AUDIT[1]);
			// 启动流程
			
			parm=actTaskService.startProcess(parm);
			String procInsId =parm.getProcInsId();
			List<User> list_user=actTaskService.getIdentityLinks(procInsId,null);
			//String PROC_INS_ID=actTaskService.startProcess(ActUtils.PD_TEST_AUDIT[0], ActUtils.PD_TEST_AUDIT[1], testAudit.getId(), testAudit.getContent());
			//设置流程实例ID
			testAudit.setProcInsId(procInsId);
			//更新业务表
			dao.updateByPrimaryKeySelective(testAudit);
		// 重新编辑申请	
		}else{
			
			testAudit.preUpdate();
			dao.updateByPrimaryKeySelective(testAudit);
			testAudit.setComment(("yes".equals(testAudit.getFlag())?"[重申] ":"[销毁] ")+testAudit.getComment());
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			//平台
			if(StringUtils.isNotEmpty(testAudit.getBranchcom())){
				vars.put("branchcom", testAudit.getBranchcom());
			}
			//销售办公室
			if(StringUtils.isNotEmpty(testAudit.getSalesoffice())){
				vars.put("salesoffice", testAudit.getSalesoffice());
			}
			//事业部
			if(StringUtils.isNotEmpty(testAudit.getProductdpt())){
				vars.put("productdpt", testAudit.getProductdpt());
			}
			//产品线
			if(StringUtils.isNotEmpty(testAudit.getProductline())){
				vars.put("productline", testAudit.getProductline());
			}
			//部门
			if(StringUtils.isNotEmpty(testAudit.getDepartment())){
				vars.put("department", testAudit.getDepartment());
			}
			vars.put("pass", "yes".equals(testAudit.getFlag())? "1" : "0");
			ActVo parm=new ActVo(testAudit.getTaskId(), testAudit.getProcInsId(), testAudit.getUserId(), testAudit.getComment(), Resolution.getId(testAudit.getFlag()),  vars, Channel.Computer.getKey());
			actTaskService.complete(parm);
			List<User> list_user=actTaskService.getIdentityLinks(testAudit.getProcInsId(),null);
		}
	}

	/**
	 * 审核审批保存
	 * @param testAudit
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {RuntimeException.class, Exception.class})
	public void auditSave(TestAudit testAudit) throws Exception {
		ActVo parm =new ActVo();
		String userId = testAudit.getUser().getUserName();
		parm.setUserId(userId);
		parm.setTaskId(testAudit.getTaskId());
		parm.setProcInsId(testAudit.getProcInsId());
		//枚举类型获取
		String comment=Resolution.getName("", testAudit.getFlag());
		// 设置意见
		if(comment!=null&&!"".equals(comment)){
			comment=comment+testAudit.getComment();
		}
		parm.setComment(comment);
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = testAudit.getTaskDefKey();
		parm.setTaskDefKey(taskDefKey);
		// 审核环节
		if ("audit".equals(taskDefKey)){
			
		}
		else if ("audit2".equals(taskDefKey)){
			testAudit.setHrText(testAudit.getComment());
			//dao.updateHrText(testAudit);
		}
		else if ("audit3".equals(taskDefKey)){
			testAudit.setLeadText(testAudit.getComment());
			//dao.updateLeadText(testAudit);
		}
		else if ("audit4".equals(taskDefKey)){
			testAudit.setMainLeadText(testAudit.getComment());
			//dao.updateMainLeadText(testAudit);
		}
		else if ("apply_end".equals(taskDefKey)){
			//结束节点处理
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		String pass=Resolution.getId(testAudit.getFlag(), "");
		vars.put("pass", pass);
		//设置会签人员、设置启动子流程、多实例任务集合
		if(StringUtils.isNotEmpty(testAudit.getAssigneelist())){
			String[] str=testAudit.getAssigneelist().split(",");
			List<String> sublist=new ArrayList();
			//设置子流程权限过滤KEY
			Map subvars =new HashMap();
			for(int i=0;i<str.length;i++){
				//设置生成子流程集合
				sublist.add(str[i]);
				//如果Subkey不为空，分别设置子流程、多实例用户权限过滤流程变量
				if(StringUtils.isNotEmpty(testAudit.getSubkey())){
					Map map =new HashMap();
					map.put(testAudit.getSubkey(), str[i]);
					//可以设置多个
					//map.put("department", ***);
					//map.put("branchcom", ***);
					//map.put("productline", ***);
					subvars.put(str[i], map);
				}
				
			}
			
			vars.put(Constant.Field.ASSIGNEELIST, sublist);
			if(StringUtils.isNotEmpty(testAudit.getSubkey())){
				vars.put(Constant.Field.SUBKEY, subvars);
			}
			
		}
		//平台
		if(StringUtils.isNotEmpty(testAudit.getBranchcom())){
			vars.put("branchcom", testAudit.getBranchcom());
		}
		//销售办公室
		if(StringUtils.isNotEmpty(testAudit.getSalesoffice())){
			vars.put("salesoffice", testAudit.getSalesoffice());
		}
		//事业部
		if(StringUtils.isNotEmpty(testAudit.getProductdpt())){
			vars.put("productdpt", testAudit.getProductdpt());
		}
		//产品线
		if(StringUtils.isNotEmpty(testAudit.getProductline())){
			vars.put("productline", testAudit.getProductline());
		}
		//部门
		if(StringUtils.isNotEmpty(testAudit.getDepartment())){
			vars.put("department", testAudit.getDepartment());
		}
		parm.setVars(vars);
		parm.setChannel(Channel.Computer.getKey());
		parm=actTaskService.complete(parm);
		List<User> list_user=actTaskService.getIdentityLinks(testAudit.getProcInsId(),null);
		//设置流程实例ID
		testAudit.setProcInsId("");
	}
	/**
	 * 查找审批列表
	 */
	public List<SysTestAudit> getAllAssass() {
		return dao.getAll();
	}
	
}
