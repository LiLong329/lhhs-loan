package com.lhhs.loan.web.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.minlog.Log;
import com.lhhs.loan.common.enums.crm.BusinessStatus;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.CrmIntentLoanUser;
import com.lhhs.loan.dao.domain.LoanUnionCompany;
import com.lhhs.loan.service.SystemManagerService;
import com.lhhs.loan.service.UnionCompanyService;
import com.lhhs.loan.service.crm.CrmIntentLoanUserService;

/**
 * 呼叫系统客户数据录入接口
 */
@Controller
@RequestMapping("/crm/api")
public class IntentLoanUserController {
	private static final Logger LOGGER = Logger.getLogger(IntentLoanUserController.class);
	Page pageParams = new Page();
	@Autowired
	private CrmIntentLoanUserService crmIntentLoanUserService;
	@Autowired
	private UnionCompanyService unionCompanyService;

	/**
	 * 保存用户数据
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUser(@RequestBody String data, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(data)) {
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "参数不能为空");
			return map;
		}
		try {
			LOGGER.info("data:" + data);
			JSONObject jsonObject = JSON.parseObject(data);
			String mobile = (String) jsonObject.get("mobile");
			if (StringUtils.isEmpty(mobile)) {
				map.put(SystemConst.retCode, SystemConst.FAIL);
				map.put(SystemConst.retMsg, "手机号不能为空");
				return map;
			}
			Integer age = null;
			String businessType = "";
			String name = (String) jsonObject.get("name");
			Log.info("name" + name);
			Integer sex = (Integer) jsonObject.get("gender");
			String filed1 = (String) jsonObject.get("field1");
			if (!StringUtils.isEmpty(filed1)) {
				age = Integer.parseInt(filed1);
			}
			String province = (String) jsonObject.get("field2");
			Log.info("province" + province);
			String city = (String) jsonObject.get("field3");
			String houseAddress = (String) jsonObject.get("field4");
			String loanAmount = (String) jsonObject.get("field5");
			String interestRate = (String) jsonObject.get("field6");
			String duration = (String) jsonObject.get("field7");
			String idNumber = (String) jsonObject.get("field8");
			String field9 = (String) jsonObject.get("field9");
			if (!StringUtils.isEmpty(field9)) {
//				businessType = Integer.parseInt(field9);
				businessType = field9;
			}
			String seatNumber = (String) jsonObject.get("thisDN");
			String extendParam = (String) jsonObject.get("extend_param");
			String sourceOrg = (String) jsonObject.get("field10");

			String appointCompany = "10001";
			String appointUnion = "10001";
			if(StringUtils.isNotEmpty(province)){
				province=province.replace("省", "");
			}
			if (StringUtils.isNotEmpty(city) && (city.equals("北京") || city.equals("北京市"))) {
				appointCompany = "10002";
			} else {
				List<LoanUnionCompany> companys = unionCompanyService.queryCompanyByCity(city);
				if (companys != null && companys.size() > 0) {
					appointCompany = companys.get(0).getCompanyId();
					appointUnion = companys.get(0).getUnionId();
				}
			}
			CrmIntentLoanUser intentLoanUser = new CrmIntentLoanUser();
			intentLoanUser.setMobile(mobile);
			intentLoanUser.setName(name);
			intentLoanUser.setSex(sex);
			intentLoanUser.setAge(age);
			intentLoanUser.setProvince(province);
			intentLoanUser.setCity(city);
			intentLoanUser.setHouseAddress(houseAddress);
			loanAmount = String.valueOf(Integer.valueOf(loanAmount)/10000);
			intentLoanUser.setLoanAmount(loanAmount);
			intentLoanUser.setInterestRate(interestRate);
			intentLoanUser.setDuration(duration);
			intentLoanUser.setIdNumber(idNumber);
			intentLoanUser.setBusinessType(businessType);
			intentLoanUser.setSeatNumber(seatNumber);
			intentLoanUser.setExtendParam(extendParam);
			intentLoanUser.setAppointCompanyId(appointCompany);
			intentLoanUser.setAppointUnionId(appointUnion);
			intentLoanUser.setSourceOrg(sourceOrg);
			intentLoanUser.setCreaterCompanyId(sourceOrg);
			intentLoanUser.setCreaterUnionId("10001");
			intentLoanUser.setStatus("01");

			if (!StringUtils.isEmpty(extendParam)) {
				String[] extendParams = extendParam.split("\\|");
				for (int i = 0; i < extendParams.length; i++) {
					if (i == 0) {
						intentLoanUser.setField1(extendParams[i].split("\\^")[1]);
					}
					if (i == 1) {
						intentLoanUser.setField2(extendParams[i].split("\\^")[1]);
					}
					if (i == 2) {
						intentLoanUser.setField3(extendParams[i].split("\\^")[1]);
					}
					if (i == 3) {
						intentLoanUser.setField4(extendParams[i].split("\\^")[1]);
					}
					if (i == 4) {
						intentLoanUser.setField5(extendParams[i].split("\\^")[1]);
					}
				}
			}
			CrmIntentLoanUser crmIntent = crmIntentLoanUserService.findByMobile(mobile);
			if (crmIntent == null) {
				int count = crmIntentLoanUserService.save(intentLoanUser);
				Log.info("count:" + count);
				if (0 < count) {
					map.put(SystemConst.retCode, SystemConst.SUCCESS);
					map.put(SystemConst.retMsg, "保存用户信息成功");
				} else {
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "保存用户信息失败");
				}
			} else {
				if (crmIntent.getBusinessStatus() != null
						&& crmIntent.getBusinessStatus().equals(BusinessStatus.BS1.getKey())) {
					intentLoanUser.setId(crmIntent.getId());
					int count = crmIntentLoanUserService.update(intentLoanUser);
					Log.info("count:" + count);
					if (0 < count) {
						map.put(SystemConst.retCode, SystemConst.SUCCESS);
						map.put(SystemConst.retMsg, "更新用户信息成功");
					} else {
						map.put(SystemConst.retCode, SystemConst.FAIL);
						map.put(SystemConst.retMsg, "更新用户信息失败");
					}
				} else {
					map.put(SystemConst.retCode, SystemConst.FAIL);
					map.put(SystemConst.retMsg, "客户信息已存在！");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put(SystemConst.retCode, SystemConst.FAIL);
			map.put(SystemConst.retMsg, "系统异常");
			return map;
		}
		return map;
	}
}
