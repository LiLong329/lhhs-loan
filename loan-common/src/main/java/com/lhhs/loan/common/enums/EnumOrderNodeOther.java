package com.lhhs.loan.common.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单节点枚举
 */
public enum EnumOrderNodeOther {
	//'订单状态 : 0:初评 1:下户 2:面审 3:终审 4:放款申请 5:放款审核 6:签约公正 7:权证抵押 8：放款确认 9:财务放款（执行放款） 10:已撤单 11:已拒贷 12:放款成功',

	CHUPING("初评", "chuping","0","1",1),
	XIAHU("下户", "xiahu","1","2",2),
	FANGKUAN_SH("放款审核", "fangkuan_sh","2","6",3),
	FANGKUAN_QYGZ("签约公证", "fangkuan_qygz","6","7",4),
	FANGKUAN_QZDY("权证抵押", "fangkuan_qzdy","7","9",5),
	//FANGKUAN_JSQR("放款确认", "fangkuan_jsqr","8","9",6),
	FANGKUAN_ZX("执行放款", "fangkuan_zx","9","12",6);
	
	/**
	 * 
	 * CHUPING("初评", "chuping","0","2",1),
	XIAHU("下户", "xiahu","1","3",2),
	FANGKUAN_SH("放款审核", "fangkuan_sh","2","4",3),
	FANGKUAN_QYGZ("签约公证", "fangkuan_qygz","6","5",4),
	FANGKUAN_QZDY("权证抵押", "fangkuan_qzdy","7","6",5),
	FANGKUAN_QZQR("放款确认", "fangkuan_qzqr","9","7",6),
	FANGKUAN_ZX("执行放款", "fangkuan_zx","12","8",7);
	 */

    //节点名称
    private String name; 
    //key必须唯一：节点KEY
    private String key;
    //当前节点编码
    private String id1; 
    //下一节点编码
    private String id; 
    //索引
    private int index;  
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (EnumOrderNodeOther s : EnumSet.allOf(EnumOrderNodeOther.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getId());
			map.put("id1", s.getId1());
			map.put("name", s.getName());
			map.put("key", s.getKey());
			map.put("index", s.getIndex());
			list.add(map);
		}
	}
	// 构造方法  
    private EnumOrderNodeOther(String name,String key, String id1, String id,int index) {  
        this.name = name;  
        this.key = key; 
        this.id1 = id1; 
        this.id = id;   
        this.index = index; 
    } 
	// 构造方法  
    private EnumOrderNodeOther(String name,String key, String id) {  
        this.name = name;  
        this.key = key; 
        this.id = id;  
        
    } 
    // 根据key获得EnumOrderNode
    public static EnumOrderNodeOther getEnumOrderNode(String key) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getKey().equals(key)) {  
                return c;  
            }  
        }  
        return null;  
    }
 // 根据index获得EnumOrderNode
    public static EnumOrderNodeOther getEnumOrderNode(int index) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getIndex()==index) {  
                return c;  
            }  
        }  
        return null;  
    }
    // 根据id1获得EnumOrderNode
    public static EnumOrderNodeOther getEnumOrderNodeId(String id1) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getId1()==id1) {  
                return c;  
            }  
        }  
        return null;  
    }
    // 根据id获得name
    public static String getName(String key) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getId().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    // 根据id或者key获得name
    public static String getName(String id,String key) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) { 
            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String id) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getId().equals(id)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据id获得 key
    public static String getKey(String id,String name) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getId().equals(id) ||c.getName().equals(name)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据key获得id  
    public static String getId(String key) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
            if (c.getKey().equals(key)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
 // 根据key或者name获得id  
    public static String getId(String key,String name) {  
        for (EnumOrderNodeOther c : EnumOrderNodeOther.values()) {  
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
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	
}

