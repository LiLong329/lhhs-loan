package com.lhhs.loan.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhhs.bump.api.SecurityUserApi;
import com.lhhs.bump.api.UnionCompanyApi;
import com.lhhs.bump.domain.AuthgroupUser;
import com.lhhs.bump.domain.Dept;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.bump.domain.UnionCompany;
import com.lhhs.loan.common.utils.SsoRSAUtils;
import com.lhhs.loan.common.utils.StringUtil;

/**
 * Created by zhanghui 2017.06.01
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	private final  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Reference
    private  SecurityUserApi myUserDetailService;  
	@Autowired(required=false)
	private SecurityUserApi userService;
	@Autowired(required=false)
	private UnionCompanyApi unionCompanyApi;
    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    public void invoke(FilterInvocation fi) throws IOException, ServletException {
	//fi里面有一个被拦截的url
	//里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
	//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
    		HttpServletRequest request = fi.getRequest();
		String userToken = request.getParameter("userToken");
		
		if(StringUtils.isNotBlank(userToken)) {
			try {
				// 还原会话
				if (StringUtils.isNotBlank(userToken)) {
					String userName = null;
					try {
						userName = SsoRSAUtils.decrypt(userToken);
					} catch (Exception e) {
						logger.error("还原回话失败：\t"+userName,e);
						return;
					}
					
					SecurityUser userDetails = null;  
				    SecurityUser user=new SecurityUser();
					if(userName != null) {
						user.setUsername(userName);
						//后台账号
//						user.setUserType("0");
						//查询菜单使用
						user.setLgServerId("lhhs_spark");
			            userDetails = myUserDetailService.loadUser(user);  
			        }  
					if (userDetails != null) {
						Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(auth);
						// 设置token 延迟TOKEN时间
						// setToken(user_temp);
						//数据权限还原
						//查询数据权限
						List<AuthgroupUser> empAuthgroupList = userDetails.getAuthgroupUserList();
						if(empAuthgroupList!=null && empAuthgroupList.size()>0){//拥有数据权限组
							List<AuthgroupUser> authgroupList = new ArrayList<AuthgroupUser>();//数据权限组列表
							List<AuthgroupUser> tempList = new ArrayList<AuthgroupUser>();//自定义菜单数据权限组
							for (AuthgroupUser lau : empAuthgroupList) {
								String dataZone = lau.getDataZone();
								if(StringUtil.isNotEmpty(dataZone)){
									if(dataZone.equals("1")){//集团数据
										List<UnionCompany> tempCompanyList=unionCompanyApi.queryCompanyByOrg(userDetails.getCompanyId());
										for (UnionCompany luc : tempCompanyList) {
											String companyId = luc.getCompanyId();
											if(StringUtil.isNotEmpty(companyId)){
												AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
												loanAuthgroupUser.setOrgId(companyId);
												authgroupList.add(loanAuthgroupUser);
											}
										}
									}else if(dataZone.equals("2")){//公司数据
										String companyId = userDetails.getCompanyId();
										if(StringUtil.isNotEmpty(companyId)){
											AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
											loanAuthgroupUser.setOrgId(companyId);
											authgroupList.add(loanAuthgroupUser);
										}
									}else if(dataZone.equals("3")){//部门及以下数据
										String leDeptId = userDetails.getDeptId();
										if(leDeptId!=null){
											AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
											loanAuthgroupUser.setOrgId(userDetails.getCompanyId());
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
										String leDeptId = userDetails.getDeptId();
										if(leDeptId!=null){
											AuthgroupUser loanAuthgroupUser=new AuthgroupUser();
											loanAuthgroupUser.setOrgId(userDetails.getCompanyId());
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
								userDetails.setTempAuthgroupList(authgroupList);
							}
							if(tempList.size()>0){
								userDetails.setMenuAuthgroupList(tempList);
							}
						}
						HttpSession session = request.getSession();
						session.setAttribute("currentUser", userDetails);
						logger.info("还原回话",userDetails);
					}

				}

			
			} catch (Exception e) {
				request.getSession().setAttribute("exception", e.getMessage());
				throw e;
			}
		}
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
        	//执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}