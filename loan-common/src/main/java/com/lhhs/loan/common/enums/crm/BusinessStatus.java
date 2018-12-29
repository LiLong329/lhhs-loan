package com.lhhs.loan.common.enums.crm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意向客户业务状态
 */
public enum BusinessStatus {
	//业务状态 01 待分配  02 待跟踪  03 继续跟踪；04待报单；05审批中；06：已成交；90：已拒贷；99无效
	BS1("初步接触", "01","1"),
	BS2("意向客户", "02","2"),
	BS3("跟进客户", "03","3"),
	BS4("面谈客户", "03","4"),
	BS5("签约客户", "04","5"),
	BS6("放弃客户", "05","6");
	
    //节点名称
    private String name; 
    //key必须唯一：节点KEY
    private String key;
    //对应编码
    private String id;  
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (BusinessStatus s : EnumSet.allOf(BusinessStatus.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getId());
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
	// 构造方法  
    private BusinessStatus(String name,String key, String id) {  
        this.name = name;  
        this.key = key; 
        this.id = id;  
        
    } 
    // 根据key获得EnumOrderNode
    public static BusinessStatus getObject(String key) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
            if (c.getKey().equals(key)) {  
                return c;  
            }  
        }  
        return null;  
    }
    // 根据id获得name
    public static String getName(String key) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
            if (c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    // 根据id或者key获得name
    public static String getName(String id,String key) {  
        for (BusinessStatus c : BusinessStatus.values()) { 
            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String id) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
            if (c.getId().equals(id)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据id获得 key
    public static String getKey(String id,String name) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
            if (c.getId().equals(id) ||c.getName().equals(name)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据key获得id  
    public static String getId(String key) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
            if (c.getKey().equals(key)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
 // 根据key或者name获得id  
    public static String getId(String key,String name) {  
        for (BusinessStatus c : BusinessStatus.values()) {  
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

