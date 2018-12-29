package com.lhhs.loan.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lhhs.bump.domain.User;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.utils.SsoRSAUtils;
import com.lhhs.server.api.UserTicketApi;

@Controller
public class TeleportingController {

	final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${server.sso.url}")
	private String ssoUrl;

	@Value("${server.sys.id}")
	private String myId;
	@Autowired
	private UserTicketApi userTicketApi; // ticket验证
//	@Autowired
//	private EmpService empService;
	/**
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author macbook
	 * @param myId
	 *            自身系统id
	 * @param peerId
	 *            跳转的系统id
	 * @param userName
	 *            当前登录用户
	 * @return String
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/teleporting/{peerId}")
	public String teleporting(@PathVariable("peerId") String peerId, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) SecurityUserHolder.getCurrentUser();
		String userName = user.getUsername();
		String ticket = null;
		try {
			ticket = userTicketApi.createTicket(myId, userName);
			userName = SsoRSAUtils.encryptBase64UrlEncoder(userName.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("跳转错误", e);
			return "500";
		}

		modelMap.put("myId", myId);
		modelMap.put("peerId", peerId);
		modelMap.put("ticket", ticket);
		modelMap.put("ssoUrl", ssoUrl + "teleporting");
		modelMap.put("userName", userName);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { // 退出登录
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "teleporting";
	}

	/**
	 * 验证用户名密码
	 * 
	 * @param empMap.put("account",
	 *            账号);empMap.put("password", 密文);
	 * @param accessToken
	 * @return
	
	@RequestMapping("/checkAccount/{accessToken}")
	@ResponseBody
	public Map<String, Object> checkAccount(@RequestBody Map<String, Object> empMap, @PathVariable("accessToken") String accessToken) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			boolean checkTicket = userTicketApi.checkTicket(accessToken);
			Assert.isTrue(checkTicket, "传票错误:\t" + checkTicket);
			String username = SsoRSAUtils.decrypt(empMap.get("username") + "");
			LoanEmp emp = (LoanEmp) empService.loadUserByUsername(username);
			if (emp != null) {
				if (emp.getCompanyId().equals(empMap.get("orgId").toString())||true) {
					// 数据库用户的密码
					String password = emp.getLePassword();
					if(StringUtils.equals(password, "" + empMap.get("password"))) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("mobile", emp.getLeMobile());
						
						resultMap.put(SystemConst.retCode, SystemConst.SUCCESS);
						resultMap.put(SystemConst.retMsg, JSON.toJSONString(map));
					}else {
						resultMap.put(SystemConst.retCode, SystemConst.FAIL);
						resultMap.put(SystemConst.retMsg,  "用户密码不正确");
					}

				} else {
					resultMap.put(SystemConst.retCode, SystemConst.FAIL);
					resultMap.put(SystemConst.retMsg, "跨机构不可绑定！！！");
				}
			} else {
				resultMap.put(SystemConst.retCode, SystemConst.FAIL);
				resultMap.put(SystemConst.retMsg, "验证失败");
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			resultMap.put(SystemConst.retCode, SystemConst.FAIL);
			resultMap.put(SystemConst.retMsg, e.getMessage());
			return resultMap;
		}
		return resultMap;
	}
	*/

}
