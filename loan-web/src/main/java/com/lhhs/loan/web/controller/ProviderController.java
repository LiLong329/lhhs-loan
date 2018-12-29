package com.lhhs.loan.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.loan.dao.domain.LoanProviderInfo;

@RequestMapping("/")
public class ProviderController {
	
	@RequestMapping("/providerUpdate")
	@ResponseBody
	public Map<String, Object> providerUpdate(@RequestBody LoanProviderInfo loanProviderInfo){
		Map<String, Object> resultMap=new HashMap<String,Object>();
		if (loanProviderInfo!=null) {
			
		}
		
		return resultMap;
	}

}
