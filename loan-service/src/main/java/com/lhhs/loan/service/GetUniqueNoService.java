package com.lhhs.loan.service;


public interface GetUniqueNoService {
	/**
	 * 获取订单编号(length:30,prefix:101)
	 * @return
	 */
	public String getOrderNo();
	
	/**
	 * 获取注册编号(length:23,prefix:100)
	 * @return
	 */
	public String getProviderNo();
	
	/**
	 * 获取客户编号
	 */
	public String getCustId();
	
	/**
	 * 获取邀请码
	 */
	public String getSelfInviteCode();
	
	/**
	 * 获得8位uuid
	 * @return
	 */
	public String getUUID();
}
