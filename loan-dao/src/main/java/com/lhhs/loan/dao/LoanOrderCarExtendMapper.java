package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.dao.domain.LoanOrderCarExtend;

public interface LoanOrderCarExtendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanOrderCarExtend record);

    int insertSelective(LoanOrderCarExtend record);

    LoanOrderCarExtend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanOrderCarExtend record);

    int updateByPrimaryKeyWithBLOBs(LoanOrderCarExtend record);

    int updateByPrimaryKey(LoanOrderCarExtend record);

	List<LoanOrderCarExtend> findCarExtendListByOrderNo(@Param("orderNo") String orderNo);

	LoanOrderCarExtend findOneCarExtendByParam(Map<String, Object> param);

	int deleteCarExtendByOrderNo(String orderNo);

	/**
	 * 
	 * findCarExtendListMapByOrderNo:根据订单编号查询订单车产抵押物信息 <br/>
	 * 这个方法适用条件 – 该方法只用于查询订单中车产抵押物信息同步到碰碰旺系统<br/>
	 *
	 * @author xiangfeng
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> findCarExtendListMapByOrderNo(String orderNo);

	List<Map<String, Object>> selectOrderCarExtendToMap(String orderNo);
}