package com.lhhs.loan.dao.domain;

public class LoanRoleNode {
    private Integer lrnId;

    private String roleId;

    private String nodeId;

    public Integer getLrnId() {
        return lrnId;
    }

    public void setLrnId(Integer lrnId) {
        this.lrnId = lrnId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }
}