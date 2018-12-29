package com.lhhs.loan.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.bump.api.SecurityUserApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.AuthgroupUser;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.jedis.JedisComponent;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.StringUtil;
import com.lhhs.loan.dao.domain.NoticeRecord;
import com.lhhs.loan.service.NoticeRecordService;


/**
 * 登录控制
 * @ClassName: LoginController
 */
@Controller
@RequestMapping("/login")
@SuppressWarnings("unused")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private JedisComponent jedisComponent;
	//@Autowired
	//private SystemManagerService systemManager;
	//@Autowired
	//private EmpAuthgroupService empAuthgroupService;
	@Autowired(required=false)
	private SecurityUserApi userService;
	@Autowired(required=false)
	private UnionCompanyApi unionCompanyApi;
	@Autowired
	private NoticeRecordService noticeRecordService;
	/**
	 * 登陆页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		return "login";
	}

	/**
	 * 登陆成功之后的方法
	 * @return
	 */
	@RequestMapping("/loginSucc")
	@ResponseBody
	public Map<String,Object> loginSucc(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		SecurityUser loanEmp = (SecurityUser)SecurityUserHolder.getCurrentUser();
		//List<LoanAuthority> authList=systemManager.findAuthListByEmpId(loanEmp.getLeEmpId());
		//loanEmp.setOperateAuthList(authList);
		//List<LoanAuthority> loanAuthorityList = systemManager.findAuthListByEmpList(authList);
		//jedisComponent.set("loanKey"+loanEmp.getUsername(), JSON.toJSONString(loanAuthorityList),3600);

		//查询数据权限
		List<AuthgroupUser> empAuthgroupList = loanEmp.getAuthgroupUserList();
		if(empAuthgroupList!=null && empAuthgroupList.size()>0){//拥有数据权限组
			List<AuthgroupUser> authgroupList = new ArrayList<AuthgroupUser>();//数据权限组列表
			List<AuthgroupUser> tempList = new ArrayList<AuthgroupUser>();//自定义菜单数据权限组
			for (AuthgroupUser lau : empAuthgroupList) {
				String dataZone = lau.getDataZone();
				if(StringUtil.isNotEmpty(dataZone)){
					if(dataZone.equals("1")){//集团数据
						List<UnionCompany> tempCompanyList=unionCompanyApi.queryCompanyByOrg(loanEmp.getCompanyId());
						for (UnionCompany luc : tempCompanyList) {
							String companyId = luc.getCompanyId();
							if(StringUtil.isNotEmpty(companyId)){
								AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
								loanAuthgroupUser.setOrgId(companyId);
								authgroupList.add(loanAuthgroupUser);
							}
						}
					}else if(dataZone.equals("2")){//公司数据
						String companyId = loanEmp.getCompanyId();
						if(StringUtil.isNotEmpty(companyId)){
							AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
							loanAuthgroupUser.setOrgId(companyId);
							authgroupList.add(loanAuthgroupUser);
						}
					}else if(dataZone.equals("3")){//部门及以下数据
						String leDeptId = loanEmp.getDeptId();
						if(leDeptId!=null){
							AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
							loanAuthgroupUser.setOrgId(loanEmp.getCompanyId());
							loanAuthgroupUser.setDeptId(leDeptId);
							authgroupList.add(loanAuthgroupUser);
							List<Dept> tempDeptList = userService.querySubDeptByDeptId(leDeptId.toString());
							for (Dept ld : tempDeptList) {
								Integer ldDeptId = ld.getDeptId();
								if(ldDeptId!=null){
									AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
									loanAuthgroupUser2.setOrgId(ld.getCompanyId());
									loanAuthgroupUser2.setDeptId(ldDeptId.toString());
									authgroupList.add(loanAuthgroupUser2);
								}
							}
						}
					}else if(dataZone.equals("4")){//部门数据
						String leDeptId = loanEmp.getDeptId();
						if(leDeptId!=null){
							AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
							loanAuthgroupUser.setOrgId(loanEmp.getCompanyId());
							loanAuthgroupUser.setDeptId(leDeptId.toString());
							authgroupList.add(loanAuthgroupUser);
						}
					}else if(dataZone.equals("6")){
						authgroupList.add(lau);
						String deptId=lau.getDeptId();
						if(StringUtil.isNotEmpty(deptId)){
							List<Dept> tempDeptList = userService.querySubDeptByDeptId(deptId);
							for (Dept ld : tempDeptList) {
								Integer ldDeptId = ld.getDeptId();
								if(ldDeptId!=null){
									AuthgroupUser loanAuthgroupUser2=new AuthgroupUser();
									loanAuthgroupUser2.setOrgId(ld.getCompanyId());
									loanAuthgroupUser2.setDeptId(ldDeptId.toString());
									authgroupList.add(loanAuthgroupUser2);
								}
							}
						}
					}else if(dataZone.equals("7")){
						tempList.add(lau);
					}
				}
			}
			if(authgroupList.size()>0){
				loanEmp.setTempAuthgroupList(authgroupList);
			}
			if(tempList.size()>0){
				loanEmp.setMenuAuthgroupList(tempList);
			}
		}
	
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", loanEmp);
		session.setAttribute("userName", loanEmp.getUsername());
		
		NoticeRecord noticeRecord = new NoticeRecord();
		noticeRecord.setStatus("0");
		noticeRecord.setNoticeType("3");
		if (loanEmp.getUserId()!=1) {
			noticeRecord.setCompanyId(loanEmp.getCompanyId());
			noticeRecord.setUnionId(loanEmp.getUnionId());
			noticeRecord.setReceiverId(loanEmp.getUserId().toString());
		}
		int count = noticeRecordService.queryCount(noticeRecord);
		session.setAttribute("noticeTotal", count);
		map.put("retCode", SystemConst.SUCCESS);
 		map.put("retMsg", "\u767b\u9646\u6210\u529f\uff01");//登陆成功
		return map;
	}
	/**
	 * 登陆失败
	 * @return
	 */
	@RequestMapping("/loginFail")
	@ResponseBody
	public Map<String,Object> loginFail(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Exception exception = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String msg = null;
		if(exception!=null){
			 msg  =  exception.getMessage();
			 //只能有一个账户进行登陆
			 if (exception instanceof SessionAuthenticationException) {
				 //您已经登录，不能重复登录
				 msg = "\u60a8\u5df2\u7ecf\u767b\u5f55\uff0c\u4e0d\u80fd\u91cd\u590d\u767b\u5f55";
			 }else if(exception instanceof LockedException){
				 //您的账号已被冻结
				 msg = "\u60a8\u7684\u8d26\u53f7\u5df2\u88ab\u51bb\u7ed3";
			 }else if(exception instanceof DisabledException){
				 //您的账号已被禁用
				 msg = exception.getMessage();;
			 }else if(exception instanceof BadCredentialsException){
				 //用户名或密码错误     (坏的凭据)
				 msg = "\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef";
			 }else{
				// 用户名或密码错误
				msg = "\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef";
			}
		}else{
			// 用户名或密码错误
			msg = "\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef";
		}
		result.put("retCode", SystemConst.FAIL);
		result.put("retMsg", msg);
		return result;
	}
	
	/**
	 * 未登录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/unSecurity")
	public String unSecurity(HttpServletRequest request,HttpServletResponse response,  ModelMap model) {
		return "login";
	}
	
	/**
	 * 403 无访问权限
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/noAuthority")
	public String noPermission(HttpServletRequest request,HttpServletResponse response,  ModelMap model) {
		return "noAuthority";
	}
	
	/**
	 * 404 请求未找到
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/notFound")
	public String notFound(HttpServletRequest request,HttpServletResponse response,  ModelMap model) {
		return "notFound";
	}
	
	
	/**
	 * 500 服务错误
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/serverError")
	public String serverError(HttpServletRequest request,HttpServletResponse response,  ModelMap model) {
		return "serverError";
	}
	
}
