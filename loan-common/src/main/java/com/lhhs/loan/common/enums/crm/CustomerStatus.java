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

public enum CustomerStatus {

		//分配状态
		STATIS1("待分配", "01","1"),
		STATIS2("已分配 ", "03","2"),
		STATIS3("无效", "99","3");
		 
	    //节点名称
	    private String name; 
	    //key必须唯一：节点KEY
	    private String key;
	    //对应编码
	    private String id;  
	    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		static {
			for (CustomerStatus s : EnumSet.allOf(CustomerStatus.class)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getId());
				map.put("name", s.getName());
				map.put("key", s.getKey());
				list.add(map);
			}
		}
		// 构造方法  
	    private CustomerStatus(String name,String key, String id) {  
	        this.name = name;  
	        this.key = key; 
	        this.id = id;  
	        
	    } 
	    // 根据key获得EnumOrderNode
	    public static CustomerStatus getObject(String key) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id获得name
	    public static String getName(String key) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id或者key获得name
	    public static String getName(String id,String key) {  
	        for (CustomerStatus c : CustomerStatus.values()) { 
	            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    } 
	    // 根据id获得 key
	    public static String getKey(String id) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
	            if (c.getId().equals(id)) {  
	                return c.key;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据id获得 key
	    public static String getKey(String id,String name) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
	            if (c.getId().equals(id) ||c.getName().equals(name)) {  
	                return c.key;  
	            }  
	        }  
	        return null;  
	    }
	    // 根据key获得id  
	    public static String getId(String key) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
	            if (c.getKey().equals(key)) {  
	                return c.id;  
	            }  
	        }  
	        return null;  
	    }
	 // 根据key或者name获得id  
	    public static String getId(String key,String name) {  
	        for (CustomerStatus c : CustomerStatus.values()) {  
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

