package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrgSupportAreas;

public interface LoanOrgSupportAreasMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanOrgSupportAreas record);

    int insertSelective(LoanOrgSupportAreas record);

    LoanOrgSupportAreas selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanOrgSupportAreas record);

    int updateByPrimaryKey(LoanOrgSupportAreas record);
    
    /**
     * 根据订单号查询资金方
     * @param orderNo
     * @return
     */
    List<LoanOrgSupportAreas> selectLoanOrgSupportAreas(String orderNo);

    /**
     * 
     * deleteByOrgId:通过机构ID删除该机构的所有城市 <br/>
     *
     * @author xiangfeng
     * @param orgId
     * @return
     * @since JDK 1.8
     */
	int deleteByOrgId(Long orgId);

	/**
	 * queryList:根据参数查询全部列表 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanOrgSupportAreas> queryList(LoanOrgSupportAreas entity);
	
	/**
     * 根据Emp查询资金方
     * @param LoanEmp
     * @return
     */
    List<LoanOrgSupportAreas> selectOrgSupportAreasByEmp(LoanEmp loanEmp);

	List<LoanOrgSupportAreas> getOrgSupportProvince(LoanOrgSupportAreas orgSupportAreas);

	List<LoanOrgSupportAreas> getOrgSupportCity(LoanOrgSupportAreas orgSupportAreas);
}