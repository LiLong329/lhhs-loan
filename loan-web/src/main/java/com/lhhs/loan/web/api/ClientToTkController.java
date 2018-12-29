/*package com.lhhs.loan.web.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhhs.server.domain.TkServerConst;
import com.lhhs.server.domain.UserBase;

@Controller
@RequestMapping("/tkclient")
public class ClientToTkController {
	private static final Logger LOGGER = Logger.getLogger(IntentLoanUserController.class);
	*//**
	 * 客户端被调用接口路由
	 * @param entity
	 * actionType 交易类型必输 取值 见常量类SystemConst
	 * 新增：saveUser,修改：updateUser,更新密码：setPassword，更新状态:updateUserStatus,删除：deleteUser
	 * @return
	 *//*

	@RequestMapping("/clientTrans/{accessToken}")
	@ResponseBody
	public Map<String ,Object> clientTrans(UserBase entity,@PathVariable("accessToken") String accessToken){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//根据交易类型处理相关业务
		resultMap.put(TkServerConst.retCode, TkServerConst.SUCCESS);
		resultMap.put(TkServerConst.retMsg, "调用成功");
		return resultMap;
	}
}
*/