package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanAffiliatedEnterpriseUrl {
    private Long id;

    private String affiliatedEnterpriseId;

    private String pathUrl;

    private String fileExtension;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAffiliatedEnterpriseId() {
        return affiliatedEnterpriseId;
    }

    public void setAffiliatedEnterpriseId(String affiliatedEnterpriseId) {
        this.affiliatedEnterpriseId = affiliatedEnterpriseId == null ? null : affiliatedEnterpriseId.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}