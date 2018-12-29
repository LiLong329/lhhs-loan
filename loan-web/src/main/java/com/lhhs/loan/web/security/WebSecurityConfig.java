/**
 * 
 */
package com.lhhs.loan.web.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lhhs.bump.api.SecurityUserApi;

/**
 * @author zhangpenghong
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Resource
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Autowired
	private SecurityUserApi userService;
	//private EmpService empService;
	public WebSecurityConfig() {
		this(false);
	}
	
	@Bean  
    public AuthenticationProvider authenticationProvider(){  
        AuthenticationProvider authenticationProvider=new AuthenticationProviderCustom(userService);  
        return authenticationProvider;  
    }  
	
	/**
	 * @param disableDefaults
	 */
	public WebSecurityConfig(boolean disableDefaults) {
		super(disableDefaults);
	}
	public void configure(WebSecurity web) throws Exception {  
        // 设置不拦截规则  
        web.ignoring().antMatchers("/js/**", "/img/**","/css/**","/webjars/**", "/act/**", "/sounds/**");  
    }  
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager authenticationManager =  this.authenticationManagerBuilder.build();
        return authenticationManager;
    }
	 @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authenticationProvider());    
	}
	protected void configure(HttpSecurity http) throws Exception {
		 http.headers().frameOptions().disable();
		
		 http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)//在正确的位置添加我们自定义的过滤器  
	        .authorizeRequests().antMatchers("/transport/**","/crm/api/**","/checkAccount/**","/callBack/receiveCdrUrl","/crmIntentLoanUser/savePpwIntentUser").permitAll()
	        .anyRequest().authenticated()
	        .and().formLogin().loginPage("/login/login")
	        .failureUrl("/login/loginFail")
			.loginProcessingUrl("/spring_security_check").successForwardUrl("/login/loginSucc").
			 permitAll().and().httpBasic().and().csrf().disable();
		 
		 http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  
            .logoutSuccessUrl("/login/login") 
            .invalidateHttpSession(true);
		 
	}
	

}
