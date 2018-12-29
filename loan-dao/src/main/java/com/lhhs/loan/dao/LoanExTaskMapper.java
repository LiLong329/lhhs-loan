package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanExTask;
import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;

public interface LoanExTaskMapper {
    int deleteByPrimaryKey(Long letTaskid);

    /**
     * 节点审核流程
     * @param loanExTaskWithBLOBs
     * @return
     */
    int insert(LoanExTaskWithBLOBs record);

    int insertSelective(LoanExTaskWithBLOBs record);
    
    /**
     * 通过 orderNo insert审批流程
     * @param record
     * @return
     */
    int insertExTaskByOrderNo(LoanExTaskWithBLOBs record);

    LoanExTaskWithBLOBs selectByPrimaryKey(Long letTaskid);

    int updateByPrimaryKeySelective(LoanExTaskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LoanExTaskWithBLOBs record);

    int updateByPrimaryKey(LoanExTask record);
    
    /**
     * 通过 orderNo 更新审批流程
     * @param record
     * @return
     */
    int updateExTaskByOrderNo(LoanExTaskWithBLOBs record);
    /**
     * 更新风控经理
     * @param record
     * @return
     */
    int updateFengKongExTaskByOrderNo(LoanExTaskWithBLOBs record);
    
    /**
     * 查询项目审核记录表(审批流程节点)
     * @param map
     * @return
     */
    List<LoanExTaskWithBLOBs> selectHandledRecord(Map<String, Object> map);
    /**
     * 查询项目审核记录表(不包括补件)
     * @param map
     * @return
     */
    List<LoanExTaskWithBLOBs> transTaskByOrderNo(String orderNo);
}