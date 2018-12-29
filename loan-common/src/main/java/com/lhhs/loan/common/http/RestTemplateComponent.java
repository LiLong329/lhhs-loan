/**
 * Project Name:loan-common
 * File Name:RestTemplateComponent.java
 * Package Name:com.lhhs.loan.common.http
 * Date:2017年6月25日上午11:24:02
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.common.http;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.common.utils.StrUtils;

/**
 * ClassName:RestTemplateComponent <br/>
 * Function: 调用远程服务的通用方法 <br/>
 * Date:     2017年6月25日 上午11:24:02 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@Component
@SuppressWarnings("all")
public class RestTemplateComponent {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateComponent.class);
	
	@Value("${remote.server.address}")
	private String serverAddress;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * get: 使用GET请求远程服务器 <br/>
	 *
	 * @author xiangfeng
	 * @param url   格式："/service/getInfo"
	 * @param responseType   返回数据类型
	 * @return
	 * @since JDK 1.8
	 */
	public <T> T get(String url,Class<T> responseType) throws RestClientException{
		if(StrUtils.isNullOrEmpty(serverAddress)){
			LOGGER.error("application.properties中未配置远程服务器地址");
			return null;
		}
		LOGGER.debug("请求的URL："+getFullUrl(url));
		return restTemplate.getForObject(getFullUrl(url), responseType);
	}
	
	/**
	 * 
	 * post:使用POST请求远程服务器<br/>
	 *
	 * @author xiangfeng
	 * @param url
	 * @param data  参数是String字符串<br/>
	 * 特别说明：请求头的ContentType为application/json;charset=UTF-8
	 * @return
	 * @since JDK 1.8
	 */
	public String post(String url,String data) throws RestClientException{
		if(StrUtils.isNullOrEmpty(serverAddress)){
			LOGGER.error("application.properties中未配置远程服务器地址");
			return null;
		}
		LOGGER.debug("请求的URL："+getFullUrl(url));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> entity = new HttpEntity<String>(data,headers);
		return restTemplate.postForObject(getFullUrl(url), entity, String.class);
	}
	
	/**
	 * 
	 * post:使用POST请求远程服务器<br/>
	 *
	 * @author xiangfeng
	 * @param url
	 * @param data 参数是Map&lt;String, Object&gt;<br/>
	 * 特别说明：请求头的ContentType为application/json;charset=UTF-8
	 * @return
	 * @since JDK 1.8
	 */
	public String post(String url,Map<String,Object> data) throws RestClientException{
		if(StrUtils.isNullOrEmpty(serverAddress)){
			LOGGER.error("application.properties中未配置远程服务器地址");
			return null;
		}
		LOGGER.debug("请求的URL："+getFullUrl(url));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Object> entity = new HttpEntity<Object>(this.mapToMultiValueMap(data),headers);
		return restTemplate.postForObject(getFullUrl(url), entity, String.class);
	}
	
	/**
	 * post: 使用POST请求远程服务器<br/>
	 *
	 * @author xiangfeng
	 * @param url   请求url
	 * @param data	请求所传数据<br/>
	 * 				data可以是：LinkedMultiValueMap&lt;String, String&gt; 对象<br/>
	 * 				data可以是：具体某个实体类对象<br/>
	 * 				data可以是：HttpEntity&lt;T&gt; 对象,使用该对象时,可以指定请求头相关的信息<br/>
	 * 特别说明：如果data不是HttpEntity对象时，所有请求头的ContentType为application/json;charset=UTF-8
	 * @param responseType 返回数据类型
	 * @return
	 * @since JDK 1.8
	 */
	@SuppressWarnings("unchecked")
	public <T,K> T post(String url,K data,Class<T> responseType) throws RestClientException{
		if(StrUtils.isNullOrEmpty(serverAddress)){
			LOGGER.error("application.properties中未配置远程服务器地址");
			return null;
		}
		LOGGER.debug("请求的URL："+getFullUrl(url));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Object request = null;
		if(data instanceof Map){
			request = new HttpEntity<Object>(this.mapToMultiValueMap((Map<Object, Object>)data),headers);
		}else if(data instanceof HttpEntity){
			request = data;
		}else{
			request = new HttpEntity<K>(data,headers);
		}
		ResponseEntity<T> res = restTemplate.postForEntity(getFullUrl(url), request, responseType);
		return res.getBody();
	}
	
	/**
	 * getFullUrl: URL拼接方法 <br/>
	 *
	 * @author xiangfeng
	 * @param uri
	 * @return
	 * @since JDK 1.8
	 */
	public String getFullUrl(String uri){
		StringBuffer URL = new StringBuffer(serverAddress);
		if(!serverAddress.endsWith("/")){
			URL.append("/");
		}
		if(!StrUtils.isNullOrEmpty(uri)){
			if(uri.startsWith("/")){
				if(uri.length() > 1){
					URL.append(uri.substring(1));
				}
			}else{
				URL.append(uri);
			}
		}
		return URL.toString();
	}
	/**
	 * 
	 * mapToMultiValueMap:将普通的Map对象转换成MultiValueMap对象<br/>
	 *
	 * @author xiangfeng
	 * @param param
	 * @return
	 * @since JDK 1.8
	 */
	public<K,V> MultiValueMap<K,V> mapToMultiValueMap(Map<K,V> param){
		MultiValueMap<K,V> ret = new LinkedMultiValueMap<K,V>();
		for(Map.Entry<K, V> ite : param.entrySet()){
			ret.add(ite.getKey(), ite.getValue());
		}
		return ret;
	}
}

