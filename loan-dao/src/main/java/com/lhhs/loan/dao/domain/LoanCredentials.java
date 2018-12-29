package com.lhhs.loan.dao.domain;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanCredentials extends BaseObject{
	private static final long serialVersionUID = -5901352153306224715L;

	private String credentialsId;

    private String credentialsName;
    
    private String englishName;

    private String credentialsType;

    private String required;

    private String status;

    private String credentialsDes;
	//产品模板是否必须 冗余字段
	private String proRequired;

    public String getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId == null ? null : credentialsId.trim();
    }

    public String getCredentialsName() {
        return credentialsName;
    }

    public void setCredentialsName(String credentialsName) {
        this.credentialsName = credentialsName == null ? null : credentialsName.trim();
    }

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType == null ? null : credentialsType.trim();
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required == null ? null : required.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCredentialsDes() {
        return credentialsDes;
    }

    public void setCredentialsDes(String credentialsDes) {
        this.credentialsDes = credentialsDes == null ? null : credentialsDes.trim();
    }

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getProRequired() {
		return proRequired;
	}

	public void setProRequired(String proRequired) {
		this.proRequired = proRequired;
	}
}