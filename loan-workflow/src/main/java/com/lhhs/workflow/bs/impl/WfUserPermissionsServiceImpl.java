package com.lhhs.workflow.bs.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lhhs.workflow.bs.inf.WfUserPermissionsService;

/**
 * 根据工作流角色和传入的条件得到最终应该接受任务的人员
 * @author lenovo
 * 处理逻辑：1.根据工作流流转到的角色查询权限表得到所有拥有角色的人和所所有人权限
 * 			2.根据查询得到的按照权限号分组
 * 			3.根据传入的条件对比每个权限号所限制的条件，得到最终应该接受目标的人员
 *
 */
@Service
public class WfUserPermissionsServiceImpl implements WfUserPermissionsService {

	@Override
	public List<String> getTaskUsers(String role, Map variableMap) {
		
		// TODO Auto-generated method stub
		return null;
	}

	
}
