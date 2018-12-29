package com.lhhs.workflow.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhhs.loan.common.shared.page.BaseObject;

public class VActToDoVO extends BaseObject{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 8837284782681646179L;
	
	private String id;
	
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.TASK_ID_
     *
     * @mbggenerated
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.TASK_DEF_KEY_
     *
     * @mbggenerated
     */
    private String taskDefKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.NAME_
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.FORM_KEY_
     *
     * @mbggenerated
     */
    private String formKey;
    //全路径URL
  	private String formUrl;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PROC_DEF_ID_
     *
     * @mbggenerated
     */
    private String procDefId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.CREATE_TIME_
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date CreateTime2;
    
    public Date getCreateTime2() {
		return CreateTime2;
	}

	public void setCreateTime2(Date createTime2) {
		CreateTime2 = createTime2;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.OID
     *
     * @mbggenerated
     */
    private BigDecimal oid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PROC_INS_ID
     *
     * @mbggenerated
     */
    private String procInsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PRODUCT_DPT
     *
     * @mbggenerated
     */
    private String productDpt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PRODUCT_LINE
     *
     * @mbggenerated
     */
    private String productLine;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.BRANCH_COM
     *
     * @mbggenerated
     */
    private String branchCom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PARTNER
     *
     * @mbggenerated
     */
    private String partner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.ACT_NAME
     *
     * @mbggenerated
     */
    private String actName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.BRANCH_OFFIC
     *
     * @mbggenerated
     */
    private String branchOffic;

    
    private String branchOfficId;
    
    public String getBranchOfficId() {
		return branchOfficId;
	}

	public void setBranchOfficId(String branchOfficId) {
		this.branchOfficId = branchOfficId;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.ORGANISER_NAME
     *
     * @mbggenerated
     */
    private String organiserName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.OPERATOR_NAME
     *
     * @mbggenerated
     */
    private String operatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.ORGANISER
     *
     * @mbggenerated
     */
    private String organiser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.OPERATOR
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PROCDEFKEY
     *
     * @mbggenerated
     */
    private String procdefkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.ACT_DESC
     *
     * @mbggenerated
     */
    private String actDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PRODUCT_DPT_ID
     *
     * @mbggenerated
     */
    private String productDptId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.PRODUCT_LINE_ID
     *
     * @mbggenerated
     */
    private String productLineId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.BRANCH_COM_ID
     *
     * @mbggenerated
     */
    private String branchComId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.SYS_TYPE
     *
     * @mbggenerated
     */
    private String sysType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.CREATE_DATE
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column V_ACT_TODO.ACT_STATUS
     *
     * @mbggenerated
     */
    private String actStatus;
    private String businessid;
    
    public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.USER_ID_
     *
     * @return the value of V_ACT_TODO.USER_ID_
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.USER_ID_
     *
     * @param userId the value for V_ACT_TODO.USER_ID_
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.TASK_ID_
     *
     * @return the value of V_ACT_TODO.TASK_ID_
     *
     * @mbggenerated
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.TASK_ID_
     *
     * @param taskId the value for V_ACT_TODO.TASK_ID_
     *
     * @mbggenerated
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.TASK_DEF_KEY_
     *
     * @return the value of V_ACT_TODO.TASK_DEF_KEY_
     *
     * @mbggenerated
     */
    public String getTaskDefKey() {
        return taskDefKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.TASK_DEF_KEY_
     *
     * @param taskDefKey the value for V_ACT_TODO.TASK_DEF_KEY_
     *
     * @mbggenerated
     */
    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.NAME_
     *
     * @return the value of V_ACT_TODO.NAME_
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.NAME_
     *
     * @param name the value for V_ACT_TODO.NAME_
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.FORM_KEY_
     *
     * @return the value of V_ACT_TODO.FORM_KEY_
     *
     * @mbggenerated
     */
    public String getFormKey() {
        return formKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.FORM_KEY_
     *
     * @param formKey the value for V_ACT_TODO.FORM_KEY_
     *
     * @mbggenerated
     */
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PROC_DEF_ID_
     *
     * @return the value of V_ACT_TODO.PROC_DEF_ID_
     *
     * @mbggenerated
     */
    public String getProcDefId() {
        return procDefId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PROC_DEF_ID_
     *
     * @param procDefId the value for V_ACT_TODO.PROC_DEF_ID_
     *
     * @mbggenerated
     */
    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.CREATE_TIME_
     *
     * @return the value of V_ACT_TODO.CREATE_TIME_
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.CREATE_TIME_
     *
     * @param createTime the value for V_ACT_TODO.CREATE_TIME_
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.OID
     *
     * @return the value of V_ACT_TODO.OID
     *
     * @mbggenerated
     */
    public BigDecimal getOid() {
        return oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.OID
     *
     * @param oid the value for V_ACT_TODO.OID
     *
     * @mbggenerated
     */
    public void setOid(BigDecimal oid) {
        this.oid = oid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PROC_INS_ID
     *
     * @return the value of V_ACT_TODO.PROC_INS_ID
     *
     * @mbggenerated
     */
    public String getProcInsId() {
        return procInsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PROC_INS_ID
     *
     * @param procInsId the value for V_ACT_TODO.PROC_INS_ID
     *
     * @mbggenerated
     */
    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId == null ? null : procInsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PRODUCT_DPT
     *
     * @return the value of V_ACT_TODO.PRODUCT_DPT
     *
     * @mbggenerated
     */
    public String getProductDpt() {
        return productDpt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PRODUCT_DPT
     *
     * @param productDpt the value for V_ACT_TODO.PRODUCT_DPT
     *
     * @mbggenerated
     */
    public void setProductDpt(String productDpt) {
        this.productDpt = productDpt == null ? null : productDpt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PRODUCT_LINE
     *
     * @return the value of V_ACT_TODO.PRODUCT_LINE
     *
     * @mbggenerated
     */
    public String getProductLine() {
        return productLine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PRODUCT_LINE
     *
     * @param productLine the value for V_ACT_TODO.PRODUCT_LINE
     *
     * @mbggenerated
     */
    public void setProductLine(String productLine) {
        this.productLine = productLine == null ? null : productLine.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.BRANCH_COM
     *
     * @return the value of V_ACT_TODO.BRANCH_COM
     *
     * @mbggenerated
     */
    public String getBranchCom() {
        return branchCom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.BRANCH_COM
     *
     * @param branchCom the value for V_ACT_TODO.BRANCH_COM
     *
     * @mbggenerated
     */
    public void setBranchCom(String branchCom) {
        this.branchCom = branchCom == null ? null : branchCom.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PARTNER
     *
     * @return the value of V_ACT_TODO.PARTNER
     *
     * @mbggenerated
     */
    public String getPartner() {
        return partner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PARTNER
     *
     * @param partner the value for V_ACT_TODO.PARTNER
     *
     * @mbggenerated
     */
    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.ACT_NAME
     *
     * @return the value of V_ACT_TODO.ACT_NAME
     *
     * @mbggenerated
     */
    public String getActName() {
        return actName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.ACT_NAME
     *
     * @param actName the value for V_ACT_TODO.ACT_NAME
     *
     * @mbggenerated
     */
    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.BRANCH_OFFIC
     *
     * @return the value of V_ACT_TODO.BRANCH_OFFIC
     *
     * @mbggenerated
     */
    public String getBranchOffic() {
        return branchOffic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.BRANCH_OFFIC
     *
     * @param branchOffic the value for V_ACT_TODO.BRANCH_OFFIC
     *
     * @mbggenerated
     */
    public void setBranchOffic(String branchOffic) {
        this.branchOffic = branchOffic == null ? null : branchOffic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.ORGANISER_NAME
     *
     * @return the value of V_ACT_TODO.ORGANISER_NAME
     *
     * @mbggenerated
     */
    public String getOrganiserName() {
        return organiserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.ORGANISER_NAME
     *
     * @param organiserName the value for V_ACT_TODO.ORGANISER_NAME
     *
     * @mbggenerated
     */
    public void setOrganiserName(String organiserName) {
        this.organiserName = organiserName == null ? null : organiserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.OPERATOR_NAME
     *
     * @return the value of V_ACT_TODO.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.OPERATOR_NAME
     *
     * @param operatorName the value for V_ACT_TODO.OPERATOR_NAME
     *
     * @mbggenerated
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.ORGANISER
     *
     * @return the value of V_ACT_TODO.ORGANISER
     *
     * @mbggenerated
     */
    public String getOrganiser() {
        return organiser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.ORGANISER
     *
     * @param organiser the value for V_ACT_TODO.ORGANISER
     *
     * @mbggenerated
     */
    public void setOrganiser(String organiser) {
        this.organiser = organiser == null ? null : organiser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.OPERATOR
     *
     * @return the value of V_ACT_TODO.OPERATOR
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.OPERATOR
     *
     * @param operator the value for V_ACT_TODO.OPERATOR
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PROCDEFKEY
     *
     * @return the value of V_ACT_TODO.PROCDEFKEY
     *
     * @mbggenerated
     */
    public String getProcdefkey() {
        return procdefkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PROCDEFKEY
     *
     * @param procdefkey the value for V_ACT_TODO.PROCDEFKEY
     *
     * @mbggenerated
     */
    public void setProcdefkey(String procdefkey) {
        this.procdefkey = procdefkey == null ? null : procdefkey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.ACT_DESC
     *
     * @return the value of V_ACT_TODO.ACT_DESC
     *
     * @mbggenerated
     */
    public String getActDesc() {
        return actDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.ACT_DESC
     *
     * @param actDesc the value for V_ACT_TODO.ACT_DESC
     *
     * @mbggenerated
     */
    public void setActDesc(String actDesc) {
        this.actDesc = actDesc == null ? null : actDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PRODUCT_DPT_ID
     *
     * @return the value of V_ACT_TODO.PRODUCT_DPT_ID
     *
     * @mbggenerated
     */
    public String getProductDptId() {
        return productDptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PRODUCT_DPT_ID
     *
     * @param productDptId the value for V_ACT_TODO.PRODUCT_DPT_ID
     *
     * @mbggenerated
     */
    public void setProductDptId(String productDptId) {
        this.productDptId = productDptId == null ? null : productDptId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.PRODUCT_LINE_ID
     *
     * @return the value of V_ACT_TODO.PRODUCT_LINE_ID
     *
     * @mbggenerated
     */
    public String getProductLineId() {
        return productLineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.PRODUCT_LINE_ID
     *
     * @param productLineId the value for V_ACT_TODO.PRODUCT_LINE_ID
     *
     * @mbggenerated
     */
    public void setProductLineId(String productLineId) {
        this.productLineId = productLineId == null ? null : productLineId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.BRANCH_COM_ID
     *
     * @return the value of V_ACT_TODO.BRANCH_COM_ID
     *
     * @mbggenerated
     */
    public String getBranchComId() {
        return branchComId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.BRANCH_COM_ID
     *
     * @param branchComId the value for V_ACT_TODO.BRANCH_COM_ID
     *
     * @mbggenerated
     */
    public void setBranchComId(String branchComId) {
        this.branchComId = branchComId == null ? null : branchComId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.SYS_TYPE
     *
     * @return the value of V_ACT_TODO.SYS_TYPE
     *
     * @mbggenerated
     */
    public String getSysType() {
        return sysType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.SYS_TYPE
     *
     * @param sysType the value for V_ACT_TODO.SYS_TYPE
     *
     * @mbggenerated
     */
    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.CREATE_DATE
     *
     * @return the value of V_ACT_TODO.CREATE_DATE
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.CREATE_DATE
     *
     * @param createDate the value for V_ACT_TODO.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column V_ACT_TODO.ACT_STATUS
     *
     * @return the value of V_ACT_TODO.ACT_STATUS
     *
     * @mbggenerated
     */
    public String getActStatus() {
        return actStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column V_ACT_TODO.ACT_STATUS
     *
     * @param actStatus the value for V_ACT_TODO.ACT_STATUS
     *
     * @mbggenerated
     */
    public void setActStatus(String actStatus) {
        this.actStatus = actStatus == null ? null : actStatus.trim();
    }

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}