package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.service.datatemp.DataTempMoveService;


@Controller
@RequestMapping("/dataTempMove")
public class DataTempMoveController  {
	
	@Autowired
	private DataTempMoveService dataTempMoveService;
		
	/**
	 * toDepositApply:(跳转到充值申请页). <br/>
	 * @author zhanghui 
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping("/start")
	@ResponseBody
	public Map<String,Object> toDepositApply(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//已迁移完成，作废程序
		//dataTempMoveService.dataTempMoveMain();
		resultMap.put(SystemConst.retCode, "处理成功");
		return resultMap;
	}
	


}

