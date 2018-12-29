/**
 * Project Name:loan-core
 * File Name:RestTemplateConfig.java
 * Package Name:com.lhhs.loan.core.http
 * Date:2017年6月24日下午4:47:57
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.core.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:RestTemplateConfig <br/>
 * Date:     2017年6月24日 下午4:47:57 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Configuration
@EnableAutoConfiguration
public class RestTemplateConfig {
	@Autowired
	private HttpClient httpClient;
	
	@Bean
	@ConfigurationProperties(prefix="http.connection")
	public ClientHttpRequestFactory getClientHttpRequestFactory(){
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return factory;
	}
	
	@Bean
	public RestTemplate getRestTemplate(){
		// 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		restTemplate.setMessageConverters(messageConverters);

		return restTemplate;
	}
}

