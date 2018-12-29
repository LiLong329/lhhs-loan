package com.lhhs.loan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.lhhs.loan.common.utils.IdWorker;
import com.lhhs.loan.common.utils.systemConst;

@Service("getUniqueNoService")
public class GetUniqueNoServiceImpl implements com.lhhs.loan.service.GetUniqueNoService {

	@Override
	public String getOrderNo() {
		return getFixLenthString(6, false);
	}

	@Override
	public String getUUID() {
		return getFixLenthStringContinChar(8);
	}

	@Override
	public String getProviderNo() {
		return systemConst.PROVIDERNO.concat(IdWorker.getNextId()) + getFixLenthString(6, true);
	}

	@Override
	public String getCustId() {
		return systemConst.CUSTINFONO.concat(IdWorker.getNextId()) + getFixLenthString(6, true);
	}
	
	@Override
	public String getSelfInviteCode() {
		return getInvitationNumber(6).toUpperCase();
	}
	
	private static String getInvitationNumber(int length){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String now = dateFormat.format(new Date());
		return getFixLenthStringContinChar(length) + now;
	} 

	private static String getFixLenthString(int length, boolean flag) {
		if (flag) {
			return getFixLenthStringContinChar(length);
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String now = dateFormat.format(new Date());
			return now + getFixLenthStringNotContinChar(length);
		}
	}

	/*
	 * 返回长度为strLength的随机数，不足则在前面补0
	 */
	private static String getFixLenthStringContinChar(int length) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/*
	 * 返回长度为strLength的随机数，不足则在前面补0
	 */
	private static String getFixLenthStringNotContinChar(int length) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 10;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}
	
}
