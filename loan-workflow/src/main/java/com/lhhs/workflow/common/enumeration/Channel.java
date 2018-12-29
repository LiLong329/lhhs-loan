package com.lhhs.workflow.common.enumeration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 工作流渠道标识
 * @author dongf
 *
 */
public enum Channel {
	Computer("电脑","C"),Mobile("手机", "M");  
	// 成员变量  
    private String name; 
    private String key;
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (Channel s : EnumSet.allOf(Channel.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
	// 构造方法  
    private Channel(String name,String key) {  
        this.name = name;  
        this.key = key; 
        
    } 
 // 根据id或者key获得name
    public static String getName(String key) {  
        for (Channel c : Channel.values()) { 
            if (c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String name) {  
        for (Channel c : Channel.values()) {  
            if (c.getKey().equals(name)) {  
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public static List<Map<String, Object>> getList() {
		return list;
	}
	
}
