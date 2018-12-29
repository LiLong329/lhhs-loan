package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanUserOrder {
    private int id;

    private String intentUserId;

    private String orderNo;

    private Date creatDate;
    
    private String appointEmpId;
    
    private Date createTime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntentUserId() {
        return intentUserId;
    }

    public void setIntentUserId(String intentUserId) {
        this.intentUserId = intentUserId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

	public String getAppointEmpId() {
	
		return appointEmpId;
	}

	public void setAppointEmpId(String appointEmpId) {
	
		this.appointEmpId = appointEmpId;
	}

	public Date getCreateTime() {
	
		return createTime;
	}

	public void setCreateTime(Date createTime) {
	
		this.createTime = createTime;
	}
    
}