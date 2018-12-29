package com.lhhs.workflow.sys.utils.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.util.StringUtils;
import com.lhhs.workflow.common.config.GlobalConstants;
import com.lhhs.workflow.common.utils.Constant;
import com.lhhs.workflow.common.utils.SpringContextHolderWf;
import com.lhhs.workflow.dao.UserPermissionMapper;
import com.lhhs.workflow.dao.domain.SessionUser;
import com.lhhs.workflow.dao.domain.User;

/**
 * 用户工具类
 */

public class UserUtils {
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(UserUtils.class);
	private static UserPermissionMapper userMapper = (UserPermissionMapper) SpringContextHolderWf.getBean(UserPermissionMapper.class);

//	private static WfUserPermissionsService wfUserPermissionsService=(WfUserPermissionsService) SpringContextHolderWf.getBean(WfUserPermissionsService.class);

	/**
	 * 根据登录名获取用户 
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {
		//本地测试使用
		//return userMapper.selectByPrimaryKey(loginName);
		return userMapper.getByLoginName(loginName);
	}
	/**
	//根据组查询权限关系表获取任务的候选人和获取委派人员信息
	public static List<User> getTaskUsers(String role, Map vars) {
		logger.debug("创建任务：根据角色和流程变量查询角色具体人员。");
		logger.debug("角色名称："+role);
		logger.debug("流程变量："+vars);
		List<User> list_user=new ArrayList();
		List<String> list= wfUserPermissionsService.getTaskUsers(role, vars);
		logger.debug("查询返回结果："+RmJsonHelper.bean2json(list));
		//转换调用权限接口返回的数据
		for(int i=0;i<list.size();i++){
			list_user.add(new User(list.get(i),list.get(i)));
			//获取该用户的委派信息
			List<User> list_dt=getDelegatedUsers(role,list.get(i));
			if(list_dt!=null&&list_dt.size()>0){
				list_user.addAll(list_dt);
			}
		}
		return list_user;
	}
	**/
	//根据组和用户ID获取委派人员信息
	public static List<User> getDelegatedUsers(String role, String userId) {
		List<User> list_user=new ArrayList();
		//调用委派接口
		List<String> list= null;
		if(list==null)return list_user;
		//转换调用权限接口返回的数据
		for(int i=0;i<list.size();i++){
			list_user.add(new User(list.get(i),list.get(i)));
		}
		return list_user;
	}
	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		HttpSession session = getSession();
		if(session==null)return null;
		SessionUser sessionVo = (SessionUser) session.getAttribute(GlobalConstants.SESSION_USER);
		if(sessionVo==null)return null;
		User user = new User(sessionVo.getUserId(), sessionVo.getUserId());
		// 如果没有登录，则返回实例化空的User对象 测试。
		//User user = new User("1", "10000635");
		return user;
	}

	/**
	 * 获取任务的候选人和委派办理人员
	 * 根据组查询权限关系表获取任务的候选人、根据用户ID和角色查询委派办理人员
	 * @param user
	 * @return
	 */
	public static List<User> findUser(User user,Map vars) {
		if(user==null ||user.getRoleId()==null)return null;
		//String roleId=user.getRole().getId();
		//开发期间使用userMapper.findList，正式联调时请使用getTaskUsers
		//List<User> list=userMapper.queryTastUserList(user);
		if(vars!=null){
			String companyId=(String)vars.get(Constant.Field.COMPANYID);
			String unionId=(String)vars.get(Constant.Field.UNIONID);
			String departmentId=(String)vars.get(Constant.Field.DEPID);
			user.setCompanyId(companyId);
			user.setUnionId(unionId);
			user.setDepartmentId(departmentId);
		}
		User user_q=userMapper.findQuarters(user);
		if(user_q!=null){
			String isDepFlag =user_q.getIsDepFlag();
			//限制部门
			if("1".equals(isDepFlag)){
				//查询组对应的部门及上级部门
				List<String> depList=new ArrayList();
				depList.add(user.getDepartmentId());
				if(!StringUtils.isEmpty(user.getDepartmentId())){
					//查询部门信息，排除上级部门是0的
					User list_dep=userMapper.finddepList(user);
					if(list_dep!=null&&!StringUtils.isEmpty(list_dep.getDepartmentParent())){
						String departmentParent=list_dep.getDepartmentParent();
						//把分割的部门转成数组
						String depparents[] =departmentParent.split(",");
						for(String depId:depparents){
							depList.add(depId);
						}
					}
				}
				user.setDepList(depList);
				user.setIsDepFlag("1");
			}
		}
		List<User> list=userMapper.queryTastUserList(user);
		//List<User> list = userMapper.findList(user);
		return list;
	}
	public static HttpServletRequest getHttpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public static HttpSession getSession() {
		HttpSession session =null;
		if(getHttpRequest()!=null){
			session =getHttpRequest().getSession();
		}
		return session;
	}
}
