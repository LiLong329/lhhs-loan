package com.lhhs.loan.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lhhs.loan.common.dao.CrudDao;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;

public interface CrmIntentLoanUserMapper extends CrudDao<CrmIntentLoanUser> {
	
	CrmIntentLoanUser findbyMobile(String mobile);
	
	CrmIntentLoanUser selectIntentUserByOrder(int id);

	/**
	 * 根据条件统计意向客户（以意向客户表中的客户经理为维度）
	 * @param entity
	 * @return
	 */
	List<CrmIntentLoanUser> getcrmIntentUserCountList(CrmIntentLoanUser entity);

	/**
	 *  根据条件统计意向客户的数量（以意向客户表中的客户经理为维度）
	 * @param entity
	 * @return
	 */
	int getcrmIntentUserCountNum(CrmIntentLoanUser entity);

	Map<String, Object> getVariousTotalCount(CrmIntentLoanUser entity);
	
	/**
	 * 根据时间统计客户总量  约见总量 签约总量
	 * @param time
	 * @return
	 */
	List<Map<String, Object>> getCrmAppointCountByTime(CrmIntentLoanUser entity);
	
	/**
	 * 根据时间统计签约总量
	 * @param time
	 * @return
	 */
	List<Map<String, Object>> getDealCountByTime(CrmIntentLoanUser entity);
	/**
	 * 根据时间统计约见总量 
	 * @param time
	 * @return
	 */
	List<Map<String, Object>> getMianshenCountByTime(CrmIntentLoanUser entity);
	/**
	 * 根据时间统计客户总量 
	 * @param time
	 * @return
	 */
	List<Map<String, Object>> getIntentCountByTime(CrmIntentLoanUser entity);
	
	
	
	
	
   
}