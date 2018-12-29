package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanCarInfo;

public interface LoanCarInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanCarInfo record);

    int insertSelective(LoanCarInfo record);

    LoanCarInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanCarInfo record);

    int updateByPrimaryKeyWithBLOBs(LoanCarInfo record);

    int updateByPrimaryKey(LoanCarInfo record);

    /**
     * 
     * deleteByCustId:通过客户编号删除借款人车抵押信息 <br/>
     *
     * @author xiangfeng
     * @param custId
     * @return
     * @since JDK 1.8
     */
	int deleteByCustId(String custId);
	
	/**
	 * selectCarInfoList:根据customerId查询当前客户的车产信息
	 * @author kernel.org
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanCarInfo> selectCarInfoList(String customerId);
	
	/**
	 * selectHouseInfoList:删除客户编号为customerId的借款人车产信息
	 * @author kernel.org
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	int deleteCarInfoByCustomerId(String customerId);
}