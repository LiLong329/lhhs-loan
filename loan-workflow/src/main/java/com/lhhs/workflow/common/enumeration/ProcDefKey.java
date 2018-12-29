package com.lhhs.workflow.common.enumeration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProcDefKey {
	TESTAUDIT("test_audit", "流程测试"),  OA(" OA系统","OA"), WL("物流系统","WL");  
	/**
     * 流程标识
     */
    private String key;
	/**
	 * 流程名称
	 */
    private String name; 
    
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (ActCategory s : EnumSet.allOf(ActCategory.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
	 // 构造方法  
    private ProcDefKey(String key,String name) {  
    	this.key = key; 
        this.name = name; 
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
