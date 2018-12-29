package com.lhhs.workflow.common.enumeration;

/**
 * 决议枚举类
 * @author dongf
 *
 */
public enum Resolution {
	YES("同意", "yes","1"),
	AGAIN("重走审批", "again","11"), 
	PATCHOVER("补件完成", "patchover","14"),
	NO("不同意","no", "0"), 
	REJECTALL("驳回修改","rejectall", "-1"),
	REJECT("修改","reject", "-2"), 
	BACK("返回驳回节点","back", "-3"), 
	OVER("结束任务", "over","90"),
	OVERMAIN("撤销", "overmain","91"),//主动撤回结束
	REFUSE("拒贷", "refuse","92"),//被审核人拒绝结束
	CANCEL("结束当前任务", "cancel","80"), 
	PATCH("补件", "patch","4"),
	
	SIGN("会签", "sign","5");  
    // 成员变量  
    private String name; 
    private String key;
    private String id;  
    // 构造方法  
    private Resolution(String name,String key, String id) {  
        this.name = name;  
        this.key = key; 
        this.id = id;  
        
    }  
    // 根据id获得name
    public static String getName(String id) {  
        for (Resolution c : Resolution.values()) {  
            if (c.getId().equals(id)) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    // 根据id或者key获得name
    public static String getName(String id,String key) {  
        for (Resolution c : Resolution.values()) { 
            if (c.getId().equals(id) ||c.getKey().equals(key)) {  
                return c.name;  
            }  
        }  
        return null;  
    } 
    // 根据id获得 key
    public static String getKey(String id) {  
        for (Resolution c : Resolution.values()) {  
            if (c.getId().equals(id)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
 // 根据id获得 key
    public static String getKey(String id,String name) {  
        for (Resolution c : Resolution.values()) {  
            if (c.getId().equals(id) ||c.getName().equals(name)) {  
                return c.key;  
            }  
        }  
        return null;  
    }
    // 根据key获得id  
    public static String getId(String key) {  
        for (Resolution c : Resolution.values()) {  
            if (c.getKey().equals(key)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
 // 根据key或者name获得id  
    public static String getId(String key,String name) {  
        for (Resolution c : Resolution.values()) {  
            if (c.getKey().equals(key)||c.getName().equals(name)) {  
                return c.id;  
            }  
        }  
        return null;  
    }
    // get set 方法  
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	} 
}
