/**
 * 
 */
package com.lhhs.workflow.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lhhs.workflow.common.config.GlobalConstants;

/**
 * @author dongfei
 *
 */
@Controller
@RequestMapping("/workflow")
public class IndexWorkFlowController {
	@Autowired
    private HttpServletRequest request;
	@RequestMapping("/index")
	public String index(){
		String view = "workflow/index";
		HttpSession session=request.getSession();
		if(session.getAttribute(GlobalConstants.SESSION_USER)==null){
			//view="redirect:/workflow/login/login";
			view="redirect:"+GlobalConstants.LOGINPAGE;;
		}
		return view;
	}
	/**
	 * 登陆页面
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request){
		String view = "workflow/login";
		HttpSession session=request.getSession();
		if(session.getAttribute(GlobalConstants.SESSION_USER)!=null){
			view="redirect:"+GlobalConstants.INDEX;;
		}
		return view;
	}
}