package com.lhhs.loan.web.security;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lhhs.bump.api.SecurityUserApi;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.utils.SecurityUtil;
import com.pathcurve.security.CryptoUtils;

/**
 * Created by zhanghui 2017.06.06
 */
public class AuthenticationProviderCustom implements AuthenticationProvider {
	private final SecurityUserApi myUserDetailService;
	// private final EmpService myUserDetailService;

	public AuthenticationProviderCustom(SecurityUserApi securityUserApi) {
		this.myUserDetailService = securityUserApi;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String username = token.getName();
		// 从数据库找到的用户
		SecurityUser userDetails = null;
		SecurityUser user = new SecurityUser();

		if (username != null) {
			user.setUsername(username);
			// 后台账号
//			user.setUserType("0");
			// 查询菜单使用
			user.setLgServerId("lhhs_spark");
			userDetails = myUserDetailService.loadUser(user);
			System.out.println("*************查询用户成功"+userDetails.getUsername());
		}
		//
		if (userDetails == null) {
			throw new UsernameNotFoundException("用户名/密码无效");
		} else if (!userDetails.isEnabled()) {
			throw new DisabledException("用户已被禁用");
		} else if (!userDetails.isAccountNonExpired()) {
			throw new AccountExpiredException("账号已过期");
		} else if (!userDetails.isAccountNonLocked()) {
			throw new LockedException("账号已被锁定");
		} else if (!userDetails.isCredentialsNonExpired()) {
			throw new LockedException("凭证已过期");
		}
		// 数据库用户的密码
		String password = userDetails.getPassword();
		// 与authentication里面的credentials相比较
		String tempPassword = null;
		if (token.getCredentials() != null) {
			tempPassword = (String) token.getCredentials();
		}
		
		  
		/*String userPassword=SecurityUtil.encryptDes(userDetails.getEncryption(),tempPassword);
		 if(!password.equals(userPassword)){ throw new
		 BadCredentialsException("Invalid username/password"); }*/
		 
		System.out.println("@@@@@@@@@@@@@@:" + userDetails.getField3());
		boolean rfc_unsafe_convert = false;
		// 验证登录密码是否从新加密
		if (userDetails.getField3() == null || userDetails.getField3().equals("")) {
			// secretKey应送用户登录密码的MD516进制字符串，
			// inputPass应送分散因子，
			// storePass为数据库中存储的密码
			try {
				rfc_unsafe_convert = CryptoUtils.rfc_unsafe_convert(tempPassword, userDetails.getEncryption(),password);
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + rfc_unsafe_convert);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!rfc_unsafe_convert) {
				throw new BadCredentialsException("Invalid username/password");
			}
		}
		String encrypt = null;
		// 加密用户密码
		// secretKey用户登录密码的MD516进制字符串
		// plainText分散因子
		try {
			encrypt = CryptoUtils.encrypt(userDetails.getEncryption(),tempPassword);
			System.out.println("&&&&&&&&&&&&&&&&&&:" + encrypt);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("Invalid username/password");
		}
		if (rfc_unsafe_convert) {
			userDetails.setPassword(encrypt);
			userDetails.setField3("1");
			myUserDetailService.update(userDetails);
		} else {
			System.out.println("((((((((((((((((((((((password" + password);
			System.out.println("((((((((((((((((((((((encrypt" + encrypt);
			if (!password.equals(encrypt)) {
				throw new BadCredentialsException("Invalid username/password");
			}
			rfc_unsafe_convert = true;
		}

		// 授权
		return new UsernamePasswordAuthenticationToken(userDetails, tempPassword, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// 返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}