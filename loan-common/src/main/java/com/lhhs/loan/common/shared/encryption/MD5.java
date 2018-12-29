package com.lhhs.loan.common.shared.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * @author yangyang
 * 时间：2016年10月25日
 * 类说明：md5加密
 */
public class MD5 {
	public static String MD5(String str) {
        String encode = str;  
        StringBuilder stringbuilder = new StringBuilder();  
        MessageDigest md5=null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        md5.update(encode.getBytes());  
        byte[] str_encoded = md5.digest();  
        for (int i = 0; i < str_encoded.length; i++) {  
            if ((str_encoded[i] & 0xff) < 0x10) {  
                stringbuilder.append("0");  
            }  
            stringbuilder.append(Long.toString(str_encoded[i] & 0xff, 16));  
           }  
           return stringbuilder.toString();  
        }
}
