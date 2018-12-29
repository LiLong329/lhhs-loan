/**
 * Project Name:loan-common
 * File Name:BusinessType.java
 * Package Name:com.lhhs.loan.common.enums
 * Date:2017年11月15日上午11:27:58
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
/**
 * Project Name:loan-common
 * File Name:BusinessType.java
 * Package Name:com.lhhs.loan.common.enums
 * Date:2017年11月15日上午11:27:58
 * Copyright (c) 2017,All Rights Reserved.
 *
 */
package com.lhhs.loan.common.enums.crm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CustomerSource {

	//业务状态 01 待分配  02 待跟踪  03 继续跟踪；04待报单；05审批中；06：已成交；90：已拒贷；99无效
		CS1("陌拜", "01","1"),
		CS2("电销 ", "02","2"),
		CS3("转介绍", "03","3"),
		CS4("网络", "04","4"),
		CS5("报刊", "05","5"),
		CS6("其他", "06","6"),
		CS7("中视天脉", "07","7");
		 
	    //节点名称
	    private String name; 
	    //key必须唯一：节点KEY
	    private String key;
	    //对应编码
	    private String id;  
	    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		static {
			for (CustomerSource s : EnumSet.allOf(CustomerSource.class)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getId());
				map.put("name", s.getName());
				map.put("key", s.getKey());
				list.add(map);
			}
		}
		// 构造方法  
	    private CustomerSource(String name,String key, String id) {  
	        this.name = name;  
	        this.key = key; 
	        this.id = id;  
	        
	    } 
	    // 根据key获得EnumOrderNode
	    public static CustomerSource getObject(String key) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id获得name
	    public static String getName(String key) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id或者key获得name
	    public static String getName(String id,String key) {  
	        for (CustomerSource c : CustomerSource.values()) { 
	            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    } 
	    // 根据id获得 key
	    public static String getKey(String id) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getId().equals(id)) {  
	                return c.key;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id获得 key
	    public static String getKey(String id,String name) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getId().equals(id) ||c.getName().equals(name)) {  
	                return c.key;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据key获得id  
	    public static String getId(String key) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c.id;  
	            }  
	        }  
	        return null;  
	    }
	 // 根据key或者name获得id  
	    public static String getId(String key,String name) {  
	        for (CustomerSource c : CustomerSource.values()) {  
	            if (c.getKey().equals(key)||c.getName().equals(name)) {  
	                return c.id;  
	            }  
	        }  
	        return null;  
	    }
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public static List<Map<String, Object>> getList() {
			return list;
		}
}

