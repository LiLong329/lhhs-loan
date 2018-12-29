package com.lhhs.workflow.dao.domain;

import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

/**
 * Created by Lawrence on 2016/8/9.
 */
public class ResultEasyuiVO extends BaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 返回结果默认true 成功，false 失败
    private boolean status = true;
    /** 服务状态码 */
    private int code = 200;

    /** 返回消息 */
    private String message;
    
    private int total;
    
    private List<Object> rows;
    public ResultEasyuiVO() {
        super();
    }
    public ResultEasyuiVO(boolean status) {
        this.status=status;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
   
}
