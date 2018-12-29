package com.lhhs.workflow.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

public class ExceptionResponse  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean status = true;// 返回结果
    /** 服务状态码 */
    private int code = 200;
    /** 返回消息 */
    private String message;
	private boolean hasErrors;	
	private Date now;
	public ExceptionResponse() {

	}

	public ExceptionResponse(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
	

	
	
}
