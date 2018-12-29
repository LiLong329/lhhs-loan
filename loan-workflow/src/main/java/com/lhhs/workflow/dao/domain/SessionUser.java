package com.lhhs.workflow.dao.domain;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("sessionUser")
public class SessionUser implements Serializable {


	private static final long serialVersionUID = 1L;
	private String id;	
	
    /** 工作流中员工编码 */
    private String userId;
	private String userName;//unis_user表的name字段
	private String password;
	private String sys;//系统标识
	private String host;//unisbi系统的ip+端口号
	private User user;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
	    if(user!=null){
	        return user.getUserName();
	    }else{	        
	       return userName;
	    }
		
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
