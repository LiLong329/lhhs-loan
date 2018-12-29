package com.lhhs.loan.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.NoticeRecord;
import com.lhhs.loan.service.NoticeRecordService;
/**

 * 通知信息接口设置
 * @author HB
 */
@Controller
@RequestMapping("/noticeRecord")
public class NoticeRecordController {
	
	@Autowired
	private NoticeRecordService noticeRecordService;
	/**
	 * 设置页面跳转
	 */
	@RequestMapping("/list")
	public ModelAndView list(NoticeRecord noticeRecord,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("noticeRecord/noticeRecordList");
		SecurityUser loanEmp = (SecurityUser)SecurityUserHolder.getCurrentUser();
		noticeRecord.setNoticeType("3");
		if (loanEmp.getUserId()!=1) {
			noticeRecord.setCompanyId(loanEmp.getCompanyId());
			noticeRecord.setUnionId(loanEmp.getUnionId());
			noticeRecord.setReceiverId(loanEmp.getUserId().toString());
		}
		Page page = noticeRecordService.queryListPage(noticeRecord);
		mav.addObject("page", page);
//		HttpSession session = request.getSession();
//		noticeRecord.setStatus("0");
//		int count = noticeRecordService.queryCount(noticeRecord);
//		session.setAttribute("noticeTotal", count);
		return mav;
	}
}

