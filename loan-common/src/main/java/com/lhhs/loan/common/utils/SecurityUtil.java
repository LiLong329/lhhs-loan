package com.lhhs.loan.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * FileName：EncryptUtil.java
 * 
 * Description：DES加密解密工具类
 * 
 */
public class SecurityUtil {

	private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	/**
	 * Des加密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDes(byte[] bytes, String key) throws Exception  {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(buildDesKey(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(buildDesKey(key));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * Des解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 */
	public static byte[] decryptDes(byte[] bytes, String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(buildDesKey(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(buildDesKey(key));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytes);
			return retByte;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		} 
	}

	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptAes(byte[] bytes, String key) throws Exception {
		try {
			Key keySpec = buildAesKey(key);
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}
	
	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptAes(byte[] bytes, byte[] key) throws Exception{
			Key keySpec = null;
			try {
				keySpec = buildAesKey(key);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new Exception(e.getMessage(), e);
			}
			return encryptAes(bytes, keySpec);

	}
	
	/**
	 * AES加密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptAes(byte[] bytes, Key key) throws Exception{
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptAes(byte[] bytes, String key) throws Exception{
		Key keySpec;
		try {
			keySpec = buildAesKey(key);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
		return decryptAes(bytes, keySpec);
	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptAes(byte[] bytes, byte[] key) throws Exception{
			Key keySpec;
			try {
				keySpec = buildAesKey(key);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new Exception(e.getMessage(), e);
			}
			return decryptAes(bytes, keySpec);

	}
	
	/**
	 * AES解密
	 * 
	 * @param bytes
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptAes(byte[] bytes, Key key) throws Exception{
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 执行操作
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}

	/*
	 * 根据字符串生成密钥字节数组 ,Des规定为64位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] buildDesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[8]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/*
	 * 根据字符串生成密钥字节数组 ,Aes规定为128位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static Key buildAesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[16]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		return keySpec;
	}
	
	/*
	 * 根据字符串生成密钥字节数组 ,Aes规定为128位
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static Key buildAesKey(byte[] keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[16]; // 声明一个8位的字节数组，默认里面都是0
		byte[] temp =keyStr; // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		return keySpec;
	}
	/**
	 * Des加密
	 * 
	 * @param encryption 加密因子
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptDes(String encryption, String key){
		String password=null;
		String tempKey="";
		if(key!=null){
			tempKey=key;
		}
		byte[] bytes =encryption.getBytes();
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(buildDesKey(tempKey));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(buildDesKey(tempKey));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			password=Encodes.encodeUrlSafeBase64(cipher.doFinal(bytes));
			return password;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return password;
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
	/**
	 * 随机指定长度字符串
	 * @return
	 */
	public static String getRandomChar(int length) {
		return getFixLenthStringContinChar(length);
	}
	public static void main(String[] args) {
		String encryption="20171218121212120bu";
		String key="123456";//KBIArjOynAj5LwmOPi8rDxovVEvEwYwQ
		String mdpassword=null;
		mdpassword=MD5.MD5(key);
		String password=SecurityUtil.encryptDes(encryption, mdpassword);
		System.out.println(password);
	}
}