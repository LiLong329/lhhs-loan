package com.lhhs.loan.common.enums.crm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意向客户被指定分配的客户经理列表
 */
public enum AppointEmp {
	
	ONE("1", "15901098468", 1),
	TWO("2 ", "13521464454", 2);
	
    //编号
    private String id; 
    //唯一的手机号
    private String key;
    //对应的值
    private Integer value;
    
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (AppointEmp s : EnumSet.allOf(AppointEmp.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getId());
			map.put("key", s.getKey());
			map.put("value", s.getValue());
			list.add(map);
		}
	}
	
	// 构造方法  
    private AppointEmp(String id,String key, Integer value) {  
        this.id = id;  
        this.key = key; 
        this.value = value;  
    }
    
    // 根据key获得AppointEmp
    public static AppointEmp getObject(String key) {  
        for (AppointEmp c : AppointEmp.values()) {  
            if (c.getKey().equals(key)) {  
                return c;  
            }  
        }  
        return null;  
    }
    
    // 根据key获得value
    public static Integer getValue(String key) {  
        for (AppointEmp c : AppointEmp.values()) { 
            if (c.getKey().equals(key)) {  
                return c.value;  
            }  
        }  
        return null;  
    } 
    
    // 根据id获得key
    public static String getKey(String id) {  
    	for (AppointEmp c : AppointEmp.values()) { 
    		if (c.getId().equals(id)) {  
    			return c.key;  
    		}  
    	}  
    	return null;  
    } 
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public static List<Map<String, Object>> getList() {
		return list;
	}
}

