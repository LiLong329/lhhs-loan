package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.LoanFeesPlanMapper;
import com.lhhs.loan.dao.domain.LoanPayPlan;
import com.lhhs.loan.dao.domain.LoanTransMain;
import com.lhhs.loan.dao.domain.vo.FeesPlanVo;
import com.lhhs.loan.service.AccountInOutService;
import com.lhhs.loan.service.CommonUtils;
import com.lhhs.loan.service.PayPlanService;
import com.lhhs.loan.service.TransMainService;
/**
 * WaitingTaskController <br/>
 * Function: 资金管理-待处理任务<br/>
 * @author   suncj
 * @version
 * @since    JDK 1.8
 * @see
 */
@Controller
@RequestMapping("/waitingTask")
public class WaitingTaskController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaitingTaskController.class);
	@Autowired
	private TransMainService transMainService;
	@Autowired
	private LoanFeesPlanMapper feesPlanMapper;
	@Autowired
	private AccountInOutService accountInOutService;
	
	/**
	 * list:待处理任务列表查询<br/>
	 * @author suncj
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/list")
	public ModelAndView list(LoanTransMain entity){
		ModelAndView mav = new ModelAndView("waitingTask/waitingTask");
		//TODO 查询条件要完善
		CommonUtils.fillCompany(entity);
		Page page = transMainService.queryListPageByTask(entity);
		if(null!=page.getResultList()&&page.getResultList().size()>0){
			queryFeesPlanVo(page);
		}
		mav.addObject("page", page);
		mav.addObject("businessId", entity.getBusinessId());
		return mav;
	}
	
	
	private void queryFeesPlanVo(Page page){
		FeesPlanVo feesPlanVo=new FeesPlanVo();
		Map<String, Object> map=new HashMap<String, Object>();
		List<LoanTransMain> list=(List<LoanTransMain>) page.getResultList();
		for(int i=0;i<list.size();i++){
			LoanTransMain transMain=list.get(i);
			feesPlanVo.setOrderNo(transMain.getBusinessId());
			List<FeesPlanVo> resultList = feesPlanMapper.queryFeesPlanVoListSum(feesPlanVo);
			if(null!=resultList&&resultList.size()>0){
				for(FeesPlanVo feesPlan:resultList){
					if("01".equals(feesPlan.getTransType())){
						transMain.setFeesPlanPaidAmount(feesPlan.getFeesAmount());
					}else{
						transMain.setFeesAmount(feesPlan.getFeesAmount());
					}
				}
			}
			map.put("orderNo", transMain.getBusinessId());
			map.put("transType", "1006");
			List<Map<String, Object>> listRecord=accountInOutService.queryTransRecord(map,null);
			if(null!=listRecord&&listRecord.size()>0){
				transMain.setField5("queren");
			}
		}
	}
	/**
	 * ajaxQueryList:
	 * 待处理任务列表 异步搜索查询及分页查询<br/>
	 * @author xiangfeng
	 * @param entity
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/ajaxQueryList")
	public ModelAndView ajaxQueryList(LoanTransMain entity){
		ModelAndView mav = new ModelAndView("waitingTask/waitingTaskTemp");
		CommonUtils.fillCompany(entity);
		Page page = transMainService.queryListPageByTask(entity);
		if(null!=page.getResultList()&&page.getResultList().size()>0){
			queryFeesPlanVo(page);
		}
		mav.addObject("page", page);
		return mav;
	}
	
}
