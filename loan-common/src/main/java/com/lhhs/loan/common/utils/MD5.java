package com.lhhs.loan.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: Robin
 * Date: 2013-11-05
 * Time: 20:58:13
 * To change this template use File | Settings | File Templates.
 */
public class MD5 {

    private String inStr;
    
    private String outStr;

	private MessageDigest md5;

	/**
	 * Constructs the MD5 object and sets the string whose MD5 is to be computed.
	 *
	 * @param inStr the <code>String</code> whose MD5 is to be computed
	 */
	public MD5(String inStr) {
		this.inStr = inStr;
		try {
			this.md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	public static String MD5(String str){
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
	/**
	 * Computes the MD5 fingerprint of a string.
	 *
	 * @return the MD5 digest of the input <code>String</code>
	 */
	public String compute() {
		char[] charArray = this.inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = this.md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		outStr=hexValue.toString();
		return outStr;
	}
	
	public String getSixteenBitsString(){
		this.compute();
		return outStr.substring(8,24);
	}

	
}
