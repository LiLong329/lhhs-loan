/**
 * 
 */
package com.lhhs.loan.core.pool;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author zhangpenghong
 *
 */
public class DecryptDruidSource extends DruidDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPassword(String password) {
		try {
			password = ConfigTools.decrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setPassword(password.split("@")[1]);
	}

	public void setUsername(String username) {
		try {
			username = ConfigTools.decrypt(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setUsername(username);
	}
	public final static void main(String[] sarg) throws Exception{
		System.out.println("@:"+ConfigTools.encrypt("root"));
		String password = ConfigTools.encrypt("root@1122qq..");
		System.out.println("@:"+password);
		System.out.println(ConfigTools.decrypt(password).split("@")[1]);
	}
}
