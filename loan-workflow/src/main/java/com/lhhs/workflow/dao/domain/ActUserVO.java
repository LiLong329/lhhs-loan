package com.lhhs.workflow.dao.domain;

import com.lhhs.loan.common.shared.page.BaseObject;


public class ActUserVO extends BaseObject{
	
    
    /** 工作流中员工编码  */
    private String userid;

    /** 员工姓名  */
    private String name;

    /** 员工密码  */
    private String passwd;

    /** 员工状态  */
    private String status;

    /** 备用字段  */
    private String description;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}