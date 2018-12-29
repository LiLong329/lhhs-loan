package com.lhhs.loan.dao;

import com.lhhs.loan.dao.domain.SipAccount;

public interface SipAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SipAccount record);

    int insertSelective(SipAccount record);

    SipAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SipAccount record);

    int updateByPrimaryKey(SipAccount record);

	SipAccount getByEntity(SipAccount entity);

	SipAccount getByUserId(String userId);
}