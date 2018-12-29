package com.lhhs.loan.dao.domain;

public class LoanOperateRecordWithBLOBs extends LoanOperateRecord {
    
	private static final long serialVersionUID = 1L;

	private String paramData;

    private String returnData;

    private String exceptionMessage;

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData == null ? null : paramData.trim();
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData == null ? null : returnData.trim();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage == null ? null : exceptionMessage.trim();
    }
}