package com.lhhs.loan.service;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanEmp;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;

/**
 * 抵押物相关操作的service
 * @ClassName: MortgageInfoService
 * @Description: TODO
 * @author xiangfeng
 * @date 2017年5月22日 下午2:01:40
 *
 */
public interface MortgageInfoService {
	/**
	 * 根据订单编号查询订单房产抵押物信息
	 * @param orderNo
	 * @return
	 */
	public List<LoanOrderHouseExtend> findHouseExtendListByOrderNo(String orderNo);
	/**
	 * 根据订单编号查询订单车产抵押物信息
	 * @param orderNo
	 * @return
	 */
	public List<LoanOrderCarExtend> findCarExtendListByOrderNo(String orderNo);
	/**
	 * 同时保存抵押物信息(房产信息和车产信息)
	 * @param houseList
	 * @param carList
	 */
	public boolean saveMortgageInfo(List<LoanOrderHouseExtend> houseList,List<LoanOrderCarExtend> carList,String orderNo);
	/**
	 * 保存房产扩展信息
	 * @param houseList
	 */
	public boolean saveHouseExtendList(List<LoanOrderHouseExtend> houseList,String orderNo);
	/**
	 * 保存车产扩展信息
	 * @param carList
	 */
	public boolean saveCarExtendList(List<LoanOrderCarExtend> carList,String orderNo);
	/**
	 * saveBorrowerAllInfo:保存从碰碰旺获取的借款人所有信息到本地数据库 <br/>
	 * 这个方法适用条件 – 保存客户信息中借款人所有信息到本地<br/>
	 * @author xiangfeng
	 * @param info
	 * @return
	 * @since JDK 1.8
	 */
	public boolean saveBorrowerAllInfo(Map<String,Object> info,LoanEmp loanEmp);
	/**
	 * saveMortgageInfo: 保存借款人抵押物信息<br/>
	 *  这个方法适用条件 – 保存客户信息中借款人抵押物<br/>
	 * @author xiangfeng
	 * @param param Map key:(custId、houseInfoLists、carInfoLists)
	 * @return
	 * @since JDK 1.8
	 */
	public Map<String, Object> saveMortgageInfo(Map<String, Object> param);
	/**
	 *  保存抵押物信息-房产信息重复校验
	 * @param entity
	 * @return
	 */
	public int queryOrderHouseExtend(LoanOrderHouseExtend entity);
}
