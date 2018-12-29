/**
 * 
 */
package com.lhhs.workflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhangpenghong
 *
 @Configuration
@EnableWebSecurity

*/

public class WebSecurityConfigWorkflow extends WebSecurityConfigurerAdapter {

	/**
	 * 
	 */
	public WebSecurityConfigWorkflow() {
		this(false);
	}

	/**
	 * @param disableDefaults
	 */
	public WebSecurityConfigWorkflow(boolean disableDefaults) {
		super(disableDefaults);
	}
	public void configure(WebSecurity web) throws Exception {  
		 // 设置不拦截规则  
        web.ignoring().antMatchers("/js/**", "/img/**","/css/**","/webjars/**","/static/**", "/act/**");    
    }  
  
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
	}

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
}
