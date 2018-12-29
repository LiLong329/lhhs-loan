package com.lhhs.workflow.bs.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhhs.workflow.bs.inf.ActLoginService;
import com.lhhs.workflow.dao.ActUserMapper;
import com.lhhs.workflow.dao.domain.User;


/**
 * Demo的业务层 <功能详细描述>
 *
 * @author
 * @version [版本号, ]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Service("actLoginService")
public class ActLoginServiceImpl implements ActLoginService {
	@Autowired
	private ActUserMapper  actUserMapper;
    private final Logger log = LogManager.getLogger(ActLoginServiceImpl.class);

    /**
     * 本地
     */
    @Override
    public User login(User user) {
    	User loginuser = null;
        log.debug("loginService方法调用mapper");
        try {
            //根据userid查找用户
           loginuser = actUserMapper.selectByPrimaryKey(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("loginService方法调用mapper" + e.getMessage());
        }
        if (loginuser == null) {
            throw new RuntimeException("用户名不存在！请重新输入！");
        }

        return loginuser;

    }


}
