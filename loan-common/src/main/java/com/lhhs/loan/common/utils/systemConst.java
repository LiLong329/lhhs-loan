package com.lhhs.loan.common.utils;

public class systemConst {
	
	/**
	 * 返回状态标识
	 */
	public static final String SUCCESS = "00";//成功
	public static final String FAIL = "01";//失败
	
	/**
	 * 返回码
	 * 消息常量
	 * 返回类型
	 */
	public static final String retMsg = "retMsg";
	public static final String retCode = "retCode";
	public static final String retType = "retType";
	
	/**
	 * 抢单标示
	 */
	public static final String CONTENTIONCODE="CONTENRTIONORDER";
	/**
	 * 当前登录用户
	 */
	public static final String CURRENT_LOGIN_USER_WAP = "CURRENT_LOGIN_USER_WAP";

	/**
	 * 验证码功能名称
	 */
	public static final String  SCAPTCHA="SCAPTCHA";
	
	/**
	 * redis存放平台报单总额key值
	 */
	public static final String PLATFORM_LOAN_AMOUNT_TOTAL = "PLATFORM_LOAN_AMOUNT_TOTAL";
	/**
	 * redis存放平台报单数量
	 */
	public static final String PLATFORM_LOAN_COUNT_TOTAL = "PLATFORM_LOAN_COUNT_TOTAL";
	/**
	 * redis首页广告地址缓存
	 */
	public static final String ADVERTISING_PICTURE = "ADVERTISING_PICTURE";
	/**
	 * redis存放产品列表
	 */
	public static final String PRODUCT_BACKGROUND_PICTURE = "PRODUCT_BACKGROUND_PICTURE";
	
	/**
	 * redis缓存待抢单总量
	 */
	public static final String SINGLEORDERQUANTITY = "singleOrderQuantity";
	
	/**
	 * 报单人编号
	 */
	public static final String PROVIDERNO = "100";
	
	/**
	 * 借款人编号
	 */
	public static final String CUSTINFONO = "200";
	
	/**
	 * 初始密码
	 */
	public static final String INITIALPASSWORD = "111111";
}
