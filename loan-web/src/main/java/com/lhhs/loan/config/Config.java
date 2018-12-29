/**
 * 
 */
package com.lhhs.loan.config;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lhhs.loan.core.filter.ClientLogFilter;
import com.lhhs.loan.listener.IndexListener;

/**
 * @author zhangpenghong
 *
 */
@Configuration
@MapperScan("com.lhhs.**.dao")
public class Config {

	/**
	 * 
	 */
	public Config() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 注册filter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		ClientLogFilter clientLogFilter = new ClientLogFilter();
		registrationBean.setFilter(clientLogFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
	@Bean
    public ServletListenerRegistrationBean<IndexListener> servletListenerRegistrationBean(){
        ServletListenerRegistrationBean<IndexListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<IndexListener>();
        servletListenerRegistrationBean.setListener(new IndexListener());
        return servletListenerRegistrationBean;
    }
}
