package com.lhhs.workflow.common.enumeration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流系统标识(流程分类)
 * @author dongf
 *
 */
public enum ActCategory {
	BA("小贷系统", "LOAN","1");  
    // 成员变量  
    private String name; 
    private String key;
    private String id;  
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (ActCategory s : EnumSet.allOf(ActCategory.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getId());
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
 // 构造方法  
    private ActCategory(String name,String key, String id) {  
        this.name = name;  
        this.key = key; 
        this.id = id;  
        
    }  
    // 根据id获得name
    public static String getName(String id) {  
        for (ActCategory c : ActCategory.values()) {  
            if (c.getId().equals(id)) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    // 根据id或者key获得name
    public static String getName(String id,String key) {  
        for (ActCategory c : ActCategory.values()) { 
            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String id) {  
        for (ActCategory c : ActCategory.values()) {  
            if (c.getId().equals(id)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
 // 根据id获得 key
    public static String getKey(String id,String name) {  
        for (ActCategory c : ActCategory.values()) {  
            if (c.getId().equals(id) ||c.getName().equals(name)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据key获得id  
    public static String getId(String key) {  
        for (ActCategory c : ActCategory.values()) {  
            if (c.getKey().equals(key)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
 // 根据key或者name获得id  
    public static String getId(String key,String name) {  
        for (ActCategory c : ActCategory.values()) {  
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
