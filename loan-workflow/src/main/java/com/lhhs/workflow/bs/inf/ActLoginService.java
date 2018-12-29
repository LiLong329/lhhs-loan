package com.lhhs.workflow.bs.inf;

import com.lhhs.workflow.dao.domain.User;


public interface ActLoginService {


    /**
     * 获取 ShkusertableVo对象user
     *
     * @param user
     * @return
     * @throws Exception
     */
    public User login(User user);


}
