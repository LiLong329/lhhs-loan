/*
 *  @File: URLUtils.java
 *  @Description:TODO(用一句话描述该文件做什么) 
 *  @Copyright 2012-2018 ComCredit Corporation. All rights reserved.
 *  @Create Date:2015年9月1日
 *  @Author:zhangpenghong
 *  @version V1.0
 *  @Modify Date:2015年9月1日
 *  @Modify By:zhangpenghong
 */
package com.lhhs.loan.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Description: URL工具类
 */

public class URLUtils {

	/**
	 * 
	 * @Title: encode
	 * @Description: URL编码
	 * @param value
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String encode(String value) {
		if (value == null || value.length() == 0) {
			return "";
		}
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @Title: decode
	 * @Description: URL解码
	 * @param value
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String decode(String value) {
		if (value == null || value.length() == 0) {
			return "";
		}
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
