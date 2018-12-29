package com.lhhs.loan.common.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单节点枚举
 */
public enum EnumOrderNode {
	//'订单状态 : 0:初评 1:下户 2:面审 3:终审 4:放款申请 5:放款审核 6:签约公正 7:权证抵押 8：放款确认 9:财务放款（执行放款） 10:已撤单 11:已拒贷 12:放款成功',
	CHUPING("初评", "chuping","0"),
	XIAHU("下户", "xiahu","1"),
	MIANSHEN("面审", "mianshen","2"),
	ZHONGSHEN("终审", "zhongshen","3"),
	FANGKUAN_SQ("放款申请", "fangkuan_sq","4"),
	FANGKUAN_SH("放款审核", "fangkuan_sh","5"),
	FANGKUAN_QYGZ("签约公证", "fangkuan_qygz","6"),
	FANGKUAN_QZDY("权证抵押", "fangkuan_qzdy","7"),
	FANGKUAN_JSQR("（结算）放款确认", "fangkuan_jsqr","8"),
	FANGKUAN_QZQR("（权证）放款确认", "fangkuan_qzqr","8"),
	FANGKUAN_ZX("执行放款", "fangkuan_zx","9"),
	BUJIAN_CP("初评补件", "bujian_cp","80"),
	BUJIAN_CPXH_QY("初评/下户补件", "bujian_cpxh_qy","81"),
	BUJIAN_CPXH_QZ("初评/下户补件", "bujian_cpxh_qz","82"),
	MIANSHEN_HQ("面审（会签）", "mianshen_hq","2"),
	ZHONGSHEN_HQ("终审（会签）", "zhongshen_hq","3"),
	FANGKUAN_SH_HQ("放款审核（会签）", "fangkuan_sh_hq","5"),
	FANGKUAN_ZX_BF("执行放款（部分）", "fangkuan_zx_bf","9"),
	MODIFY("放款申请修改", "modify","79"),
	XIAHU_ZP("下户指派", "xiahuzhipai","20"),
	XIAHU_SH("下户审核", "xiahushenhe","21"), 
	XIAHU_SH_HQ("下户审核（会签）", "xiahushenhe_hq","22"),  
	FENGKONG_FH("风控复核", "fengkongfuhe","23");
	
    //节点名称
    private String name; 
    //key必须唯一：节点KEY
    private String key;
    //对应编码
    private String id;  
    private static final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static {
		for (EnumOrderNode s : EnumSet.allOf(EnumOrderNode.class)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.getId());
			map.put("name", s.getName());
			map.put("key", s.getKey());
			list.add(map);
		}
	}
	// 构造方法  
    private EnumOrderNode(String name,String key, String id) {  
        this.name = name;  
        this.key = key; 
        this.id = id;  
        
    } 
    // 根据key获得EnumOrderNode
    public static EnumOrderNode getEnumOrderNode(String key) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
            if (c.getKey().equals(key)) {  
                return c;  
            }  
        }  
        return null;  
    }
    // 根据id获得name
    public static String getName(String key) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
            if (c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    // 根据id或者key获得name
    public static String getName(String id,String key) {  
        for (EnumOrderNode c : EnumOrderNode.values()) { 
            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String id) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
            if (c.getId().equals(id)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据id获得 key
    public static String getKey(String id,String name) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
            if (c.getId().equals(id) ||c.getName().equals(name)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据key获得id  
    public static String getId(String key) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
            if (c.getKey().equals(key)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
 // 根据key或者name获得id  
    public static String getId(String key,String name) {  
        for (EnumOrderNode c : EnumOrderNode.values()) {  
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

