/**
 * Project Name:loan-common
 * File Name:Direction.java
 * Package Name:com.lhhs.loan.common.shared.enums
 * Date:2017年8月2日下午6:04:55
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.common.shared.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借贷方向(收入：IN;支出：OUT；冻结解冻：F)
 */
public enum Direction {
	IN("收入","IN"),OUT("支出", "OUT"),F("冻结解冻", "F"),ON("在途", "ON");  
	// 成员变量  
    private String name; 
    private String id;
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (Direction s : EnumSet.allOf(Direction.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getName());
			map.put("id", s.getId());
			list.add(map);
		}
	}
	// 构造方法  
    private Direction(String name,String id) {  
        this.name = name;  
        this.id = id; 
        
    } 
 // 根据id或者key获得name
    public static String getName(String id) {  
        for (Direction c : Direction.values()) { 
            if (c.getId().equals(id)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getId(String name) {  
        for (Direction c : Direction.values()) {  
            if (c.getId().equals(name)) {  
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

