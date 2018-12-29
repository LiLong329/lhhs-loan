package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanOrganization;

public interface LoanOrganizationMapper {
	 int deleteByPrimaryKey(Long orgId);

	 int insert(LoanOrganization record);

	 int insertSelective(LoanOrganization record);

	 LoanOrganization selectByPrimaryKey(Long orgId);

	 int updateByPrimaryKeySelective(LoanOrganization record);

	 int updateByPrimaryKeyWithBLOBs(LoanOrganization record);

	 int updateByPrimaryKey(LoanOrganization record);
	
    /**
     * selectOrganizationAccountInfo:根据订单号查询资金方对公账户信息
     * @author kernel.org
     * @param map
     * @return
     * @since JDK 1.8
     */
    LoanOrganization selectOrganizationAccountInfo(Map<String, Object> map);
    /**
     * 查询 LoanOrganization 实体bean
     */
    List<LoanOrganization> selectLoanOrganizationList();

    /**
     * findOrgByName:根据组织机构名称查询<br/>
     * @author xiangfeng
     * @param orgName
     * @return
     * @since JDK 1.8
     */
    LoanOrganization selectByOrgName(String orgName);

    /**
     * queryListPage:分页查询机构信息 <br/>
     *
     * @author xiangfeng
     * @param entity
     * @return
     * @since JDK 1.8
     */
	List<LoanOrganization> queryListPage(LoanOrganization entity);

	/**
	 * queryCount:查询总条数<br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	int queryCount(LoanOrganization entity);

	/**
	 * getByEntity:根据实体查询结果 <br/>
	 *
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	LoanOrganization getByEntity(LoanOrganization entity);

	List queryList(LoanOrganization entity);
}