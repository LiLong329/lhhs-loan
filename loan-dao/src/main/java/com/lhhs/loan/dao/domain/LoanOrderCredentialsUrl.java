package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanOrderCredentialsUrl {
    private Long urlId;

    private Long orderCredentialsNo;

    private String pathUrl;

    private String fileExtension;

    private Date updateTime;
    
    private String parseJson;
    
    private String orderCredentialsName;
    
    //图片的base64编码
    private String imgBase64Str;
    //订单号
    private String orderNo;
    
    public String getOrderCredentialsName() {
		return orderCredentialsName;
	}

	public void setOrderCredentialsName(String orderCredentialsName) {
		this.orderCredentialsName = orderCredentialsName;
	}

	public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public Long getOrderCredentialsNo() {
        return orderCredentialsNo;
    }

    public void setOrderCredentialsNo(Long orderCredentialsNo) {
        this.orderCredentialsNo = orderCredentialsNo;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl == null ? null : pathUrl.trim();
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension == null ? null : fileExtension.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getParseJson() {
		return parseJson;
	}

	public void setParseJson(String parseJson) {
		this.parseJson = parseJson;
	}

	public String getImgBase64Str() {
		return imgBase64Str;
	}

	public void setImgBase64Str(String imgBase64Str) {
		this.imgBase64Str = imgBase64Str;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}