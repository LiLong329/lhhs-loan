package com.lhhs.loan.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class GetUniqueNoUtil{

	public static synchronized String getCustNo() {
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String custNo = currentTime+getFixLenthString(2,true);
		return custNo;
	}

	public static synchronized String getOrderNo() {
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String orderNo = currentTime+getFixLenthString(7,false);
		return orderNo;
	}
	public static synchronized String getUUid() {
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return currentTime;
	}
	private static String getFixLenthString(int length, boolean flag) {
		if( flag )
			return getFixLenthStringContinChar( length);
		else
			return getFixLenthStringNotContinChar( length);
	}
	/*
	 * 返回长度为strLength的随机数，不足则在前面补0
	*/
	 private static String getFixLenthStringContinChar(int length) {
		//35是因为数组是从0开始的，26个字母+10个数字
		  final int  maxNum = 36;
		  int i;  //生成的随机数
		  int count = 0; //生成的密码的长度
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < length){
		   //生成随机数，取绝对值，防止生成负数，
		   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
		  
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }
		 
		  return pwd.toString();
	}
	 /*
		 * 返回长度为strLength的随机数，不足则在前面补0
		*/
		 private static String getFixLenthStringNotContinChar(int length) {
			//35是因为数组是从0开始的，26个字母+10个数字
			  final int  maxNum = 10;
			  int i;  //生成的随机数
			  int count = 0; //生成的密码的长度
			  char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
			  StringBuffer pwd = new StringBuffer("");
			  Random r = new Random();
			  while(count < length){
			   //生成随机数，取绝对值，防止生成负数，
			   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
			  
			   if (i >= 0 && i < str.length) {
			    pwd.append(str[i]);
			    count ++;
			   }
			  }
			 
			  return pwd.toString();
		}
}
