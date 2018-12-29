/**
 * Project Name:loan-web
 * File Name:HttpTest.java
 * Package Name:com.lhhs.loan.web
 * Date:2017年7月1日下午4:34:09
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.lhhs.loan.boot.Application;
import com.lhhs.loan.common.http.RestTemplateComponent;
import com.lhhs.loan.dao.domain.LoanCarInfo;


/**
 * ClassName:HttpTest <br/>
 * Function: 测试RestTemplateComponent中的方法<br/>
 * Reason:   <br/>
 * Date:     2017年7月1日 下午4:34:09 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Application.class})
public class HttpTest {
	@Autowired
	private RestTemplateComponent restTemplateComponent;
	
	/**
	 * testGet:测试get请求 <br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	public void testGet(){
		try{
			String ret = restTemplateComponent.get("/", String.class);
			System.out.println(ret);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * testPostForMap:测试post请求，参数是map对象<br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	@SuppressWarnings("unchecked")
	public void testPostForMap(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", "1957739b164a49cdbdad0b4f4dd9896d");
		param.put("houseInfoLists", "");
		param.put("carInfoLists", "");
		try{
			String ret = restTemplateComponent.post("/transport/mortgageInfo", param);
			Map<String,Object> re = JSON.parseObject(ret, Map.class);
			System.out.println(re.get("retCode"));
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	/**
	 * testPost:测试post请求，参数类型自己指定，返回类型自己指定，比如返回map<br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	@SuppressWarnings("unchecked")
	public void testPost(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", "1957739b164a49cdbdad0b4f4dd9896d");
		param.put("houseInfoLists", "");
		param.put("carInfoLists", "");
		try{
			Map<String,Object> ret = restTemplateComponent.post("/transport/mortgageInfo", param,Map.class);
			System.out.println(ret.get("retCode"));
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/**
	 * testPostEntity:发送post请求，参数传入实体对象 <br/>
	 * 
	 * 注意事项 –- 接口接收对象时，必须加上@RequestBody注解<br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	@SuppressWarnings("unchecked")
	public void testPostEntity(){
		LoanCarInfo car = new LoanCarInfo();
		car.setCustId("1111111111");
		car.setId(2233L);
		try{
			Map<String,Object> ret = restTemplateComponent.post("/transport/test", car,Map.class);
			System.out.println(ret.toString());
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * 
	 * testPostEntity2:发送post请求，参数传入实体对象   返回用实体对象去接受返回值<br/>
	 *
	 * 注意事项 –- 接口接收对象时，必须加上@RequestBody注解<br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	public void testPostEntity2(){
		LoanCarInfo car = new LoanCarInfo();
		car.setCustId("1111111111");
		car.setId(2233L);
		try{
			LoanCarInfo ret = restTemplateComponent.post("/transport/test1", car,LoanCarInfo.class);
			System.out.println(ret.toString());
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * 
	 * testPostString: 发送post请求，参数传入字符串或者json串<br/>
	 * 
	 * 注意事项 –- 接口接收字符串时，必须加上@RequestBody注解<br/>
	 * @author xiangfeng
	 * @since JDK 1.8
	 */
	//@Test
	public void testPostString(){
		try{
			String ret = restTemplateComponent.post("/transport/test2", "String字符串",String.class);
			System.out.println(ret.toString());
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

