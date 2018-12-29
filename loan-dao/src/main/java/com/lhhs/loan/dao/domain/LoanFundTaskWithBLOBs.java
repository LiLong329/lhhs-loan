package com.lhhs.loan.dao.domain;

public class LoanFundTaskWithBLOBs extends LoanFundTask {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lftSuggestion;

    private String lftRemark;
    
    public LoanFundTaskWithBLOBs() {
	}
    
    public LoanFundTaskWithBLOBs(String  lftDeclarationformid) {
    	   super();
    	   super.setLftDeclarationformid(lftDeclarationformid);
   	}
    

    public String getLftSuggestion() {
        return lftSuggestion;
    }

    public void setLftSuggestion(String lftSuggestion) {
        this.lftSuggestion = lftSuggestion == null ? null : lftSuggestion.trim();
    }

    public String getLftRemark() {
        return lftRemark;
    }

    public void setLftRemark(String lftRemark) {
        this.lftRemark = lftRemark == null ? null : lftRemark.trim();
    }
}