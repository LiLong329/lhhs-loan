/*
 *  @File: TokenHelper.java
 *  @Description:token生成工具类 
 *  @Copyright 2012-2018 ComCredit Corporation. All rights reserved.
 *  @Create Date:2015年9月11日
 *  @Author:zhangpenghong
 *  @version V1.0
 *  @Modify Date:2015年9月11日
 *  @Modify By:zhangpenghong
 */
package com.lhhs.loan.common.utils;

import java.util.UUID;

/**
 * @Description: token生成工具类
 */

public class TokenHelper {
	/**
	 * 
	 * @Title: generateToken
	 * @Description: 获取token
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String generateToken() {
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString().replace("-", "").toUpperCase();
		return token;
	}

	public static final void main(String[] sarg) {
		System.out.println(generateToken());// 8a62d7e2-5b4a-4940-9012-56184d22c105
	}
}
