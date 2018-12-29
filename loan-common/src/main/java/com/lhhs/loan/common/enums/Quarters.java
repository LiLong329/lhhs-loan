package com.lhhs.loan.common.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位枚举类
 */
public enum Quarters {	

	Quarters1("审贷会", 1001), 
	Quarters2("集团执行总裁",2001),
	Quarters3("区域总经理", 3001),  
	Quarters4("区域总监(作废不用)",4001);
	
	/**
	 * 岗位名称
	 */
    private String name; 
	/**
     * 岗位标识
     */
    private Integer key;

    
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (Quarters s : EnumSet.allOf(Quarters.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
	 // 构造方法  
    private Quarters(String name,Integer key) { 
    	this.name = name;
    	this.key = key; 
       
    }
    // 根据id或者key获得name
    public static String getName(Integer key) {  
        for (Quarters c : Quarters.values()) { 
            if (c.getKey().intValue()==key.intValue()) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static Integer getKey(String name) {  
        for (Quarters c : Quarters.values()) {  
            if (c.getName().equals(name)) {  
                return c.key;  
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
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public static List<Map<String, Object>> getList() {
		return list;
	}
}

