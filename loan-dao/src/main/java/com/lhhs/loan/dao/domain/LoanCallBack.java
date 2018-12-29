package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanCallBack extends BaseObject{
    private Integer id;

    private String attachDatas;

    private String localIp;

    private String calleridNumber;

    private String destinationNumber;

    private String context;

    private Long startStamp;

    private Long answerStamp;

    private Long endStamp;

    private Integer duration;

    private Integer billsec;

    private String hangupCause;

    private String uuid;

    private String blegUuid;

    private String accountSid;

    private String ctiCallId;

    private String direction;

    private Integer ctiCallType;

    private String ctiTenantDn;

    private String ctiRecordPath;

    private BigDecimal cost;

    private String transferBy;

    private String transferTo;

    private String thisQueue;

    private String agentId;

    private Integer evaluateLevel;

    private String mediaType;

    private String userId;

    private String deptId;

    private String companyId;

    private String unionId;

    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachDatas() {
        return attachDatas;
    }

    public void setAttachDatas(String attachDatas) {
        this.attachDatas = attachDatas == null ? null : attachDatas.trim();
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp == null ? null : localIp.trim();
    }

    public String getCalleridNumber() {
        return calleridNumber;
    }

    public void setCalleridNumber(String calleridNumber) {
        this.calleridNumber = calleridNumber == null ? null : calleridNumber.trim();
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber == null ? null : destinationNumber.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Long getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(Long startStamp) {
        this.startStamp = startStamp;
    }

    public Long getAnswerStamp() {
        return answerStamp;
    }

    public void setAnswerStamp(Long answerStamp) {
        this.answerStamp = answerStamp;
    }

    public Long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(Long endStamp) {
        this.endStamp = endStamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBillsec() {
        return billsec;
    }

    public void setBillsec(Integer billsec) {
        this.billsec = billsec;
    }

    public String getHangupCause() {
        return hangupCause;
    }

    public void setHangupCause(String hangupCause) {
        this.hangupCause = hangupCause == null ? null : hangupCause.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getBlegUuid() {
        return blegUuid;
    }

    public void setBlegUuid(String blegUuid) {
        this.blegUuid = blegUuid == null ? null : blegUuid.trim();
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid == null ? null : accountSid.trim();
    }

    public String getCtiCallId() {
        return ctiCallId;
    }

    public void setCtiCallId(String ctiCallId) {
        this.ctiCallId = ctiCallId == null ? null : ctiCallId.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Integer getCtiCallType() {
        return ctiCallType;
    }

    public void setCtiCallType(Integer ctiCallType) {
        this.ctiCallType = ctiCallType;
    }

    public String getCtiTenantDn() {
        return ctiTenantDn;
    }

    public void setCtiTenantDn(String ctiTenantDn) {
        this.ctiTenantDn = ctiTenantDn == null ? null : ctiTenantDn.trim();
    }

    public String getCtiRecordPath() {
        return ctiRecordPath;
    }

    public void setCtiRecordPath(String ctiRecordPath) {
        this.ctiRecordPath = ctiRecordPath == null ? null : ctiRecordPath.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getTransferBy() {
        return transferBy;
    }

    public void setTransferBy(String transferBy) {
        this.transferBy = transferBy == null ? null : transferBy.trim();
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo == null ? null : transferTo.trim();
    }

    public String getThisQueue() {
        return thisQueue;
    }

    public void setThisQueue(String thisQueue) {
        this.thisQueue = thisQueue == null ? null : thisQueue.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public Integer getEvaluateLevel() {
        return evaluateLevel;
    }

    public void setEvaluateLevel(Integer evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType == null ? null : mediaType.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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
}