package com.lhhs.loan.common.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Spring Security提供了一个线程安全的对象：SecurityContextHolder，
 * 通过这个对象，我们可以访问当前的登录用户
 * @author Administrator
 */
public class SecurityUserHolder {
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static Object getCurrentUser() {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		if(au == null){
			return null;
		}else{
			return au.getPrincipal();
		}
		
	}
	/**
	 * 获得当前用户的访问地址
	 * @return
	 */
	public static String getServletPath() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		String path=request.getServletPath();
		return path;
	}
	/**
	 * 获得当前用户的访问地址
	 * @return
	 */
	public static String getReferer() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		String path = request.getHeader("Referer");
		return path;
	}
	/**
	 * 获得当前用户的客户号(进行测试后使用)
	 * @return
	 */
	public static String getRemoteAddress() {
		try{
			Authentication au = SecurityContextHolder.getContext().getAuthentication();
			if(au == null){
				return null;
			}else{
				WebAuthenticationDetails auth = (WebAuthenticationDetails) au.getDetails();
				return auth.getRemoteAddress();
			}
		}catch(Exception e){
			return null;
		}
		
	}
	

	
}
