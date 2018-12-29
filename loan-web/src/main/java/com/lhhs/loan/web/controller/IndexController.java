package com.lhhs.loan.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.lhhs.bump.api.AuthorityApi;
import com.lhhs.bump.api.SecurityUserApi;
import com.lhhs.bump.domain.Authority;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.jedis.JedisComponent;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.dao.domain.NoticeRecord;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.NoticeRecordService;
import com.lhhs.loan.service.TransMainService;
import com.lhhs.loan.service.transport.OrderInfoService;
import com.lhhs.workflow.bs.inf.ActTaskService;
import com.lhhs.workflow.dao.domain.TaskVo;
import com.pathcurve.oss.bean.Policy;
import com.pathcurve.oss.bean.Token;
import com.pathcurve.oss.policy.PostObjectPolicyComponent;
import com.pathcurve.oss.token.AppTokenComponent;

/**
 * @author zhangpenghong
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PostObjectPolicyComponent postObjectPolicyComponent;
	@Autowired
	private AppTokenComponent appTokenComponent;
	@Autowired
	private JedisComponent jedisComponent;
	@Autowired(required=false)
	private OrderInfoService orderInfoService;
	@Autowired(required = false)
	private ActTaskService actTaskService;
	@Autowired
	private NoticeRecordService noticeRecordService;
	@Reference
	private AuthorityApi authorityApi;
	@Reference
	private SecurityUserApi userService;
	@Value("${server.sys.id}")
	private String sysId;
	@Autowired
	private TransMainService transMainService;
	
    private static final int PAGESIZE = 10;
	Page page = new Page(PAGESIZE);

	/**
	 * 首页查询我的待办任务列表
	 * @param request
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, TaskVo parm){
		//待办列表
		SecurityUser loanEmp = (SecurityUser) SecurityUserHolder.getCurrentUser();
		if(loanEmp!=null){
			parm.setUserId(loanEmp.getUsername());
			Page page = actTaskService.todoList(parm);
			request.setAttribute("page", page);
		}
		//当前时间，上个月，下个月
		Date today=new Date();
		Date lastMonth=DateUtils.addMonth(today, -1);
		Date nextMonth=DateUtils.addMonth(today, 1);
		request.setAttribute("today", today);
		request.setAttribute("lastMonth", lastMonth);
		request.setAttribute("nextMonth", nextMonth);
		//统计
		Map<String, Object> param=new HashMap<String, Object>();
		CommonUtils.fillCompanyToMap(param);
		Map<String, Object> obj = orderInfoService.getOrderSumInfo(param);
		request.setAttribute("obj", obj);
		return "index";
	}

	@RequestMapping("/policy")
	public @ResponseBody Policy requestPolicy(Model model) {
		Policy policy = null;
		try {
			policy = postObjectPolicyComponent.creatPolicy("test");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("@test:" + policy);
		return policy;
	}

	@RequestMapping("/sts")
	public @ResponseBody Token requestToken(Model model) {
		Token token = null;
		try {
			token = appTokenComponent.getToken();
		} catch (NumberFormatException | ClientException e) {
			e.printStackTrace();
		}
		logger.info("@test:" + token);
		return token;
	}

	/**
	 * 查询菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/menu")
	@ResponseBody
	public List<Authority> getMenu(HttpServletRequest request,
			HttpServletResponse response) {
		List<Authority> list = null;
		try {
			SecurityUser user = (SecurityUser) SecurityUserHolder.getCurrentUser();
			if (user != null) {
				list=user.getMenuList();
				if (list == null || list.size() == 0) {
					Authority authority = new Authority();
					authority.setUserId(user.getUserId().toString());
					authority.setIsShow("1");
					authority.setServerId(sysId);
					authority.setField1("0");
					list = userService.queryAuthorityList(authority);
				}
			}
			//未读消息条数
			HttpSession session = request.getSession();
			NoticeRecord noticeRecord  = new NoticeRecord();
			if (user.getUserId()!=1) {
				noticeRecord.setCompanyId(user.getCompanyId());
				noticeRecord.setUnionId(user.getUnionId());
				noticeRecord.setReceiverId(user.getUserId().toString());
			}
			noticeRecord.setStatus("0");
			noticeRecord.setNoticeType("3");
			int count = noticeRecordService.queryCount(noticeRecord);
			session.setAttribute("noticeTotal", count);
		} catch (Exception e) {
			logger.error("Exception:"+e);
		}
		return list;

	}
	
	/**
	 * unLockOrder:订单释放锁 <br/>
	 */
	@RequestMapping("/unLockOrder")
	public String unLockOrder(@RequestParam(name="orderNo",required=true) String orderNo,Model model){
		String value = jedisComponent.getValue("loan_lock_"+orderNo);
		if(StringUtils.isEmpty(value)){
			model.addAttribute("errorTxt", "该订单没有加锁");
		}else{
			if(jedisComponent.del("loan_lock_"+orderNo)){
				model.addAttribute("errorTxt", "解锁成功");
			}else{
				model.addAttribute("errorTxt", "解锁失败");
			}
		}
		return "alertError";
	}
	
	/*
	 * 报表-查询平台成交额
	 */
	@RequestMapping("/queryTransAmount")
	@ResponseBody
	public List<Map<String, Object>> queryTransAmount(
			String queryType, 
			String compareType, 
			String beginTime, 
			String endTime,
			String lendingTime){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("queryType", queryType);
		map.put("compareType", compareType);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("lendingTime", lendingTime);
		map.put("transType", SystemConst.TransType.TYPEID1001);
		CommonUtils.fillCompanyToMap(map);
		List<Map<String, Object>> list=transMainService.queryTransAmount(map);
		return list;
	}
	
}