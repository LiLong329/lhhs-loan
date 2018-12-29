package com.lhhs.loan.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>
 * Title: Json工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: rocky
 * </p>
 * 
 * @author 
 * @date Oct 28, 2013
 */
public class FastJsonUtil {

	/**
	 * 
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * 
	 * @return
	 */

	public static String getJSONString(Object javaObj) {
		return JSONObject.toJSONString(javaObj);
	}

	/**
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject(String jsonString, Class pojoCalss) {
		return JSONObject.parseObject(jsonString, pojoCalss);
	}

}
