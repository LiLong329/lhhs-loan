package com.lhhs.workflow.dao;

import java.util.List;

import com.lhhs.workflow.dao.domain.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */

public interface UserPermissionMapper  {

	/**
	 * 查询用户信息 对接权限系统
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String userName);
	/**
	 * 查询用户信息 对接权限系统
	 * @param entity
	 * @return
	 */
	public List<User> queryTastUserList(User user);
	/**
	 * 查询部门的上级部门
	 * @param departmentId
	 * @return
	 */
	public User finddepList(User user);
	/**
	 * 查询岗位信息
	 * @param roleId
	 * @return
	 */
	public User findQuarters(User user);
	
	/**
	 * 获取单条数据 
	 * @param id
	 * @return
	 */
	public User selectByPrimaryKey(String userId);

	
	/**
	 * 查询数据列表
	 * @param entity
	 * @return
	 */
	public List<User> findList(User user);

}
