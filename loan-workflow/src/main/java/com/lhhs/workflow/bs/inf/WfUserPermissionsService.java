package com.lhhs.workflow.bs.inf;

import java.util.List;
import java.util.Map;

/**
 * 根据工作流角色和传入的条件得到最终应该接受任务的人员
 * @author lenovo
 * 处理逻辑：1.根据工作流流转到的角色查询权限表得到所有拥有角色的人
 * 			2.根据人得到所有人的所有角色
 * 			3.根据传入的条件对比查询后得到的人的权限，得到最终应该接受目标的人员
 *
 */
public interface WfUserPermissionsService {
	
	List<String> getTaskUsers(String role, Map variableMap);
}
