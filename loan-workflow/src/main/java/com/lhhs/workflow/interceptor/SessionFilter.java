package com.lhhs.workflow.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.lhhs.workflow.common.config.GlobalConstants;
import com.lhhs.workflow.dao.domain.SessionUser;

/**
 * 用于检查用户是否登录了系统的过滤器<br>
 * <p>
 * 创建日期：2012-01-09
 *
 * @author　刘桂玲
 */

public class SessionFilter implements Filter {
	private final Logger log = LogManager.getLogger(SessionFilter.class);
	/**
	 * 要检查的 session 的名称
	 */
	private String sessionKey;
	/**
	 * 需要排除（不拦截）的URL的正则表达式
	 */
	private Pattern excepUrlPattern;
	/**
	 * 检查不通过时，转发的URL
	 */
	private String forwardUrl;
	/**
	 * 检查不通过时，转发的URL
	 */
	private String loginUrl;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		// sessionKey = cfg.getInitParameter("sessionKey");
		sessionKey = GlobalConstants.SESSION_USER;
		String excepUrlRegex = cfg.getInitParameter("excepUrlRegex");
		if (!StringUtils.isBlank(excepUrlRegex)) {
			excepUrlPattern = Pattern.compile(excepUrlRegex);
		}
		forwardUrl = cfg.getInitParameter("forwardUrl");
		loginUrl = GlobalConstants.LOGINPAGE;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// 如果 sessionKey 为空，则直接放行
		if (StringUtils.isBlank(sessionKey)) {
			chain.doFilter(req, res);
			return;
		}
		// * 请求 http://127.0.0.1:8080/webApp/home.jsp?&a=1&b=2 时
		// * request.getRequestURL()： http://127.0.0.1:8080/webApp/home.jsp
		// * request.getContextPath()： /webApp
		// * request.getServletPath()：/home.jsp
		// * request.getRequestURI()： /webApp/home.jsp
		// * request.getQueryString()：a=1&b=2
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String servletPath = request.getServletPath();
		log.info("开始过滤....");
		log.info("servletPath=" + servletPath);
		// 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
		if (servletPath.equals(forwardUrl) || servletPath.equals(loginUrl) || excepUrlPattern.matcher(servletPath).matches()) {
			log.info("放过该请求...");
			chain.doFilter(req, res);
			return;
		}
		String contextPath = request.getContextPath();
		String redirect = servletPath + "?" + StringUtils.defaultString(request.getQueryString());
		String url = contextPath + StringUtils.defaultIfEmpty(forwardUrl, "/") + "?redirect=" + URLEncoder.encode(redirect, "UTF-8");
		Object sessionObj = request.getSession().getAttribute(sessionKey);
		// 如果Session为空，则跳转到指定页面
		if (sessionObj == null) {
			/*
			 * login.jsp 的 <form> 表单中新增一个隐藏表单域： <input type="hidden" name="redirect" value="${param.redirect }"> LoginServlet.java 的 service 的方法中新增如下代码： String redirect =
			 * request.getParamter("redirect"); if(loginSuccess){ if(redirect == null || redirect.length() == 0){ // 跳转到项目主页（home.jsp） }else{ // 跳转到登录前访问的页面（java.net.URLDecoder.decode(s, "UTF-8")） } }
			 */
			log.debug("未登录，跳转到登录页面...");
			
			response.sendRedirect(url);
			
		} else {
			SessionUser sessionUser = (SessionUser) sessionObj;
			log.info("已登录，取消过滤...");
			log.info("userId=" + sessionUser.getUserId() + ";userName=" + sessionUser.getUserName());
			chain.doFilter(req, res);
		}
		log.info("完成过滤....");
	}

	@Override
	public void destroy() {
		log.debug("destroy...");
	}
}
