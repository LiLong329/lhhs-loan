package com.lhhs.loan.dao;

import java.util.List;

import com.lhhs.loan.dao.domain.LoanHouseInfo;

public interface LoanHouseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanHouseInfo record);

    int insertSelective(LoanHouseInfo record);

    LoanHouseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanHouseInfo record);

    int updateByPrimaryKey(LoanHouseInfo record);

    /**
     * deleteByCustId:通过custId删除房抵押信息
     * @author xiangfeng
     * @param custId
     * @return
     * @since JDK 1.8
     */
	int deleteByCustId(String custId);
	
	/**
	 * selectHouseInfoList:(这里用一句话描述这个方法的作用)
	 * @author kernel.org
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanHouseInfo> selectHouseInfoList(String customerId);
	
	/**
	 * selectHouseInfoList:删除客户编号为customerId的借款人房产信息
	 * @author kernel.org
	 * @param customerId
	 * @return
	 * @since JDK 1.8
	 */
	int deleteHouseInfoByCustomerId(String customerId);
}