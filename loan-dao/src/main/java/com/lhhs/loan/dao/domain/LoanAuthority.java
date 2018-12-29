package com.lhhs.loan.dao.domain;

import java.io.Serializable;
import java.util.List;

public class LoanAuthority implements Serializable ,Cloneable{
    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = -6823097447926253124L;

	private Integer laAuthorityId;

    private String laName;

    private String laUrl;

    private String laAuthority;

    private String laLayer;

    private Integer laFatherNode;

    private String laType;
    
    private String dataFlag;
    
    private List<LoanAuthority> loanAuthorityList;

    
    public String getDataFlag() {
	
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
	
		this.dataFlag = dataFlag;
	}

	public Integer getLaAuthorityId() {
        return laAuthorityId;
    }

    public void setLaAuthorityId(Integer laAuthorityId) {
        this.laAuthorityId = laAuthorityId;
    }

    public String getLaName() {
        return laName;
    }

    public void setLaName(String laName) {
        this.laName = laName == null ? null : laName.trim();
    }

    public String getLaUrl() {
        return laUrl;
    }

    public void setLaUrl(String laUrl) {
        this.laUrl = laUrl == null ? null : laUrl.trim();
    }

    public String getLaAuthority() {
        return laAuthority;
    }

    public void setLaAuthority(String laAuthority) {
        this.laAuthority = laAuthority == null ? null : laAuthority.trim();
    }

    public String getLaLayer() {
        return laLayer;
    }

    public void setLaLayer(String laLayer) {
        this.laLayer = laLayer == null ? null : laLayer.trim();
    }

    public Integer getLaFatherNode() {
        return laFatherNode;
    }

    public void setLaFatherNode(Integer laFatherNode) {
        this.laFatherNode = laFatherNode;
    }

    public String getLaType() {
        return laType;
    }

    public void setLaType(String laType) {
        this.laType = laType == null ? null : laType.trim();
    }

	public List<LoanAuthority> getLoanAuthorityList() {
		return loanAuthorityList;
	}

	public void setLoanAuthorityList(List<LoanAuthority> loanAuthorityList) {
		this.loanAuthorityList = loanAuthorityList;
	}
    
    
}