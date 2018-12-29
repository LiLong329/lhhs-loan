/**
 * Project Name:loan-core
 * File Name:HttpClientConfig.java
 * Package Name:com.lhhs.loan.core.http
 * Date:2017年6月24日下午5:21:07
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.core.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:HttpClientConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年6月24日 下午5:21:07 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Configuration
@EnableAutoConfiguration
public class HttpClientConfig {
	
	@Bean
	@ConfigurationProperties(prefix="http.pool")
	public PoolingHttpClientConnectionManager getPoolingConnectionManager(){
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		return manager;
	}
	
	@Bean
	public HttpClient getHttpClient(){
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(getPoolingConnectionManager());
		return builder.build();
	}
}

