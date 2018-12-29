package com.lhhs.loan.common.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;

/**   
*    
* 项目名称：risk-control-common   
* 类名称：SSORSAUtils   
* 类描述： 私钥解密  使用已生成好的秘钥文件进行与SAAS进行数据处理
* 创建人：macbook   
* 创建时间：2018年2月2日 下午5:10:39   
* @version        
*/
public class SsoRSAUtils {

	static String privatePath = "SPRAK_PRIVATE.KEY"; // 私钥地址
	
	
	public static  Key privateKey ;
	static {
		// 获取私钥
		InputStream inputStream = SsoRSAUtils.class.getResourceAsStream(privatePath);
		try {
			 privateKey =getKey(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static Key getKey(InputStream input) throws Exception, IOException {  
        Key key;  
        ObjectInputStream ois = null;  
        try {  
            /** 将文件中的私钥对象读出 */  
            ois = new ObjectInputStream(input);  
            key = (Key) ois.readObject();  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            ois.close();  
        }  
        return key;  
    }  
	
	/**
	 * 解密base64 数据
	 * @param encodeBase64
	 * @return
	 * @throws Exception
	 */
	public static  String decrypt(String encodeUrlBase64) throws Exception {
		byte[] content = Base64.getUrlDecoder().decode(encodeUrlBase64);
		byte[] decryptedBytes = decrypt(content,privateKey);
		return new String(decryptedBytes,"utf-8");
	}
	
	/**
	 * 解密普通 数据
	 * @param 数组
	 * @return
	 * @throws Exception
	 */
	public static  String decrypt(byte[] content) throws Exception {
		byte[] decryptedBytes = decrypt(content,privateKey);
		return new String(decryptedBytes,"utf-8");
	}
	
	

	// 私钥解密
	private static byte[] decrypt(byte[] content, Key privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}

	/**
	 * 
	 * 私钥加密，对方使用公钥解密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}
	
	/**
	 * 加密数据
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64UrlEncoder(byte[] content) throws Exception {
		byte[] encrypt = encrypt(content);
		return Base64.getUrlEncoder().encodeToString(encrypt);
	}

}
