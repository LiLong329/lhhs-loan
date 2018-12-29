package com.lhhs.loan.common.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatHandler {

	public DataFormatHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 非空值转化字符串
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getNotNullStr(Object obj) throws Exception {
		if (obj instanceof Double) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(obj) + "";
		} else if (obj instanceof Timestamp || obj instanceof Date) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			return sf.format(obj);

		} else if (null != obj) {
			return obj + "";
		}
		return "";
	}

	/**
	 * 处理日期数据转化
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date getDateData(String str) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != str && !str.equals("")) {
			return sf.parse(str);
		}
		return null;
	}

	/**
	 * 处理整形
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Long getLongData(String str) throws Exception {
		if (null != str && !str.equals("")) {
			return new Long(str);
		}
		return null;
	}

	/**
	 * 处理字符串
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getStringData(Object str) throws Exception {
		if (null != str && !str.equals("")) {
			return str + "";
		}
		return null;
	}

	/**
	 * 处理双精度
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Double getDoubleData(String str) throws Exception {

		if (null != str && !str.equals("")) {
			return new Double(str);
		}
		return null;
	}
}
