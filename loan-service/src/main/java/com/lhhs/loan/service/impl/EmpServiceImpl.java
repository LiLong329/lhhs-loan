//package com.lhhs.loan.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.lhhs.loan.common.utils.SsoRSAUtils;
//import com.lhhs.loan.dao.LoanAuthorityMapper;
//import com.lhhs.loan.dao.LoanEmpMapper;
//import com.lhhs.loan.dao.LoanEmpRoleMapper;
//import com.lhhs.loan.dao.domain.LoanAuthority;
//import com.lhhs.loan.dao.domain.LoanEmp;
//import com.lhhs.loan.service.EmpService;
//
//@Component("empService")
//public class EmpServiceImpl implements EmpService {
//	
//	private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);
//
//	@Autowired
//	private LoanEmpMapper loanEmpMapper;
//	@Autowired
//	private LoanAuthorityMapper loanAuthorityMapper;
//	@Autowired
//	private LoanEmpRoleMapper loanEmpRoleMapper;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username)
//			throws UsernameNotFoundException,DisabledException {
//		
//		    //添加用户权限
//			LoanEmp loanEmp = loanEmpMapper.selectByAccount(username);
//			if(loanEmp == null){
//				throw new UsernameNotFoundException("\u7528\u6237\u4e0d\u5b58\u5728");//用户不存在
//			}
//			if("0".equals(loanEmp.getLdStatus())){
//				
//				throw new DisabledException("\u8be5\u8d26\u53f7\u6240\u5728\u90e8\u95e8\u5df2\u88ab\u7981\u7528\u0021");//该账号所在部门已被禁用!
//			}
//			if("0".equals(loanEmp.getLgStatus())){
//				
//				throw new DisabledException("\u8be5\u8d26\u53f7\u6240\u5728\u7ec4\u522b\u5df2\u88ab\u7981\u7528\u0021");//该账号所在组别已被禁用!
//			}
//			if("0".equals(loanEmp.getLqStatus())){
//				
//				throw new DisabledException("\u8be5\u8d26\u53f7\u6240\u5728\u5c97\u4f4d\u5df2\u88ab\u7981\u7528\u0021");//该账号所在岗位已被禁用!
//			}
//			if("0".equals(loanEmp.getLrStatus())){
//				
//				throw new DisabledException("\u8be5\u8d26\u53f7\u89d2\u8272\u5df2\u88ab\u7981\u7528\u0021");//该账号角色已被禁用!
//			}
//			if("0".equals(loanEmp.getLeStatus())){
//				
//				throw new DisabledException("\u8be5\u8d26\u53f7\u5df2\u88ab\u7981\u7528\u0021");//该账号已被禁用!
//			}
//			//查询用户所有角色ID
//			List<Integer>  roleId = loanEmpRoleMapper.getRoleIdByEmpId(loanEmp.getLeEmpId());
//			if(roleId==null||roleId.size()==0){
//				throw new DisabledException("\u8be5\u8d26\u53f7\u89d2\u8272\u4e0d\u5b58\u5728\u0021");//该账号角色不存在!
//			}
//			
//			loanEmp.setLrRoleId(roleId);
//			
//			List<LoanAuthority> loanAuthorityList = loanAuthorityMapper.findAuthListByEmpId(0,loanEmp.getLeEmpId());
//			 
//			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//			GrantedAuthority ga = null;
//			for (LoanAuthority loanAuthority : loanAuthorityList) {
//				if(StringUtils.isNotEmpty(loanAuthority.getLaAuthority())){
//					 authorities.add(new SimpleGrantedAuthority(loanAuthority.getLaAuthority()));
//			}
//		
//			}
//			GrantedAuthority role = new SimpleGrantedAuthority("ROLE_AUTHORITY");
//			authorities.add(role);
//			loanEmp.setAuthorities(authorities);
//			return loanEmp;
//		
//	}
//	
//	@Override
//	public void checkUserToken(HttpServletRequest request) {
//		
//		String userToken = request.getParameter("userToken");
//		// 还原会话
//		if (StringUtils.isNotBlank(userToken)) {
//			String userName = null;
//			try {
//				userName = SsoRSAUtils.decrypt(userToken);
//			} catch (Exception e) {
//				logger.error("还原回话失败：\t"+userName,e);
//				return;
//			}
//			UserDetails riskEmp = loadUserByUsername(userName);
//			if (riskEmp != null) {
//				Authentication auth = new UsernamePasswordAuthenticationToken(riskEmp, riskEmp.getPassword(), riskEmp.getAuthorities());
//				SecurityContextHolder.getContext().setAuthentication(auth);
//				// 设置token 延迟TOKEN时间
//				// setToken(user_temp);
//				HttpSession session = request.getSession();
//				session.setAttribute("currentUser", riskEmp);
//				logger.info("还原回话",riskEmp);
//			}
//
//		}
//
//	}
//
//}
