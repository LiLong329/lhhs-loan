package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;

public interface LoanOrderHouseExtendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanOrderHouseExtend record);

    int insertSelective(LoanOrderHouseExtend record);

    LoanOrderHouseExtend selectByPrimaryKey(Long id);
    
    LoanOrderHouseExtend selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(LoanOrderHouseExtend record);

    int updateByPrimaryKey(LoanOrderHouseExtend record);

	List<LoanOrderHouseExtend> findHouseExtendListByOrderNo(@Param("orderNo") String orderNo);

	LoanOrderHouseExtend findOneHouseExtendByParam(Map<String, Object> param);
	
	int deleteHouseExtendByOrderNo(String orderNo);
	
	/**
	 * selectLoanOrderHouseExtend:查询放款申请中的房产扩展信息
	 * @author kernel.org
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	List<LoanOrderHouseExtend> selectLoanOrderHouseExtend(LoanOrderHouseExtend entity);

	/**
	 * findHouseExtendListMapByOrderNo:根据订单编号查询订单房产抵押物信息<br/>
	 * 这个方法适用条件 – 该方法只用于查询订单中房产抵押物信息同步到碰碰旺系统<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> findHouseExtendListMapByOrderNo(String orderNo);

	List<Map<String, Object>> selectOrderHouseExtendToMap(String orderNo);

	int queryOrderHouseExtend(LoanOrderHouseExtend entity);

}