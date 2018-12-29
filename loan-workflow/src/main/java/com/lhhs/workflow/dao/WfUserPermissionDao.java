package com.lhhs.workflow.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.workflow.dao.domain.UserPermission;
/**
 * 
 * @author lenovo
 *
 */

public interface WfUserPermissionDao {
	public List<UserPermission> getUsersByRole(Map map);
	public List<UserPermission> getOrgInfo_with_typeId(String typeId);

}
