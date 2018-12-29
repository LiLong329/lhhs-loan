package com.lhhs.loan.dao.domain;

import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

public class NoticeModel extends BaseObject{
	
	private static final long serialVersionUID = 4162765022112495940L;

	private Integer id;

    private String name;

    private String englishName;

    private String modelType;

    private String content;

    private String receiver;

    private String noticeType;

    private String modelStatus;

    private Integer sequence;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    private String [] idsArray;
    
    private List<String> quartersNameList;
    
    private Integer customerManager;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType == null ? null : noticeType.trim();
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(String modelStatus) {
        this.modelStatus = modelStatus == null ? null : modelStatus.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

	public String [] getIdsArray() {
		return idsArray;
	}

	public void setIdsArray(String [] idsArray) {
		this.idsArray = idsArray;
	}

	public List<String> getQuartersNameList() {
		return quartersNameList;
	}

	public void setQuartersNameList(List<String> quartersNameList) {
		this.quartersNameList = quartersNameList;
	}

	public Integer getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(Integer customerManager) {
		this.customerManager = customerManager;
	}
}