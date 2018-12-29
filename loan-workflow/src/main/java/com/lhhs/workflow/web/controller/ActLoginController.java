package com.lhhs.workflow.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lhhs.workflow.bs.inf.ActLoginService;
import com.lhhs.workflow.common.config.GlobalConstants;
import com.lhhs.workflow.dao.domain.ExceptionResponse;
import com.lhhs.workflow.dao.domain.SessionUser;
import com.lhhs.workflow.dao.domain.User;


/**
 * @author ganminjun
 */
@Controller
@RequestMapping("/workflow/login")
@SessionAttributes({GlobalConstants.SESSION_USER})
public class ActLoginController {
    private final Logger log = LogManager.getLogger(ActLoginController.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ActLoginService actLoginService;

	/**
	 * 登陆页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		String view = "workflow/login";
		HttpSession session=request.getSession();
		if(session.getAttribute(GlobalConstants.SESSION_USER)!=null){
			view="redirect:/workflow/index";
		}
		return view;
	}
	
    @ResponseBody
    @RequestMapping(value = "/logincheck")
    // 验证账号与密码是否匹配
    public ExceptionResponse logincheck(ModelMap model,User user) {
        // 清除session
        Enumeration<String> em = request.getSession().getAttributeNames();
        /**
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        **/
        request.getSession().removeAttribute(GlobalConstants.SESSION_USER);
        request.getSession().invalidate();

        // new 一个message status状态为false 当status状态为true时返回结果
        ExceptionResponse message = new ExceptionResponse(false);
        // 将前台获取的明码进行加密
        String encryptPassword = DigestUtils.md5Hex("UNIS_" + user.getPassword());
        log.debug("loginname=" + user.getUserId() + "明码：" + user.getPassword() + "密文：" + encryptPassword);
        try {
            // 较验用户是否存在,并返回用户信息
        	User unisUserVO = actLoginService.login(user);
            // 找到用户后进行密码的校验
            if (unisUserVO != null) {
                log.debug("unisUserVO=" + unisUserVO.getUserId() + unisUserVO.getPassword());
//                log.debug(user.getUserid() + "用户已找到了" + "前台密文" + encryptPassword + "后台密文" + unisUserVO.getPasswd());
                // 数据库中加密后的密码
                String passwd = unisUserVO.getPassword();
                // 接受页面的系统标识
                
                // 比对
                if (passwd.equals(encryptPassword)) {
                    message.setStatus(true);
                    message.setCode(00);
                    SessionUser sessionUser = new SessionUser();
                    StringBuffer host = new StringBuffer();

                    sessionUser.setUserId(user.getUserId());
                    User user1 = new User();
                    sessionUser.setHost(host.toString());
                    BeanUtils.copyProperties(unisUserVO,user1);
                    sessionUser.setUser(user1);
                    sessionUser.setUserName(user1.getUserName());
                    request.getSession().setAttribute(GlobalConstants.SESSION_USER, sessionUser);
                    model.addAttribute(GlobalConstants.SESSION_USER, sessionUser);
                } else {
                    // 不通过把提示信息返回页面
                    message.setMessage("密码和账户名不匹配，请重新输入");
                    log.debug(user.getUserId() + "的密码不正确");
                }
            }
        } catch (Exception e) {
            message.setStatus(false);
            message.setCode(500);
            message.setMessage(e.getMessage());
            log.error(e);
        }
        // 返回信息
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/exist")
    // 验证用户账号是否可以使用
    public ExceptionResponse exist(@ModelAttribute("user") User user) {
        ExceptionResponse message = new ExceptionResponse(true);
        try {
            // 较验用户是否存在,并返回用户信息
        	User unisUserVO = actLoginService.login(user);
            if (unisUserVO != null) {
                log.debug("用户" + unisUserVO.getUserId() + "存在");

                // message.setMessage("用户存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(false);
            message.setCode(500);
            message.setMessage("用户账号不可用！请重新输入！");
        }
        return message;
    }


    /**
     * 本地登出
     *
     * @return
     */
    @RequestMapping(value = "/loginout")
    public String loginout() {
        // 清除session
        Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        request.getSession().removeAttribute(GlobalConstants.SESSION_USER);
        request.getSession().invalidate();
        String path = request.getContextPath();
        // 拼接跳转页面路径
        // String basePath =
        String basePath = GlobalConstants.LOGINPAGE;

        return "redirect:" + basePath;
    }

    @RequestMapping(value = "/logout")
    public String logout(SessionStatus sessionStatus) {
        // 清除session
        sessionStatus.setComplete();
        request.getSession().invalidate();
        String path = request.getContextPath();
        // 拼接跳转页面路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        
        return "redirect:" + basePath;
    }

}
