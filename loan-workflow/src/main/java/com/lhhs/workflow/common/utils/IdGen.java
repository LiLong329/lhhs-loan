package com.lhhs.workflow.common.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;


/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 */

public class IdGen implements IdGenerator{

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	/**
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	**/
	
	public static String uuid() {
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String custNo = currentTime+getFixLenthStringContinChar(4);
		return custNo;
	}
	
	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	@Override
	public String getNextId() {
		return IdGen.uuid();
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
		
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());

	}

}
