package com.lhhs.loan.dao;

import java.util.List;
import java.util.Map;

import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtend;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;

public interface LoanOrderBorrowerExtendMapper {
    int deleteByPrimaryKey(String custId);

    int insert(LoanOrderBorrowerExtendWithBLOBs record);

    int insertSelective(LoanOrderBorrowerExtendWithBLOBs record);

    LoanOrderBorrowerExtendWithBLOBs selectByPrimaryKey(String custId);

    int updateByPrimaryKeySelective(LoanOrderBorrowerExtendWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LoanOrderBorrowerExtendWithBLOBs record);

    int updateByPrimaryKey(LoanOrderBorrowerExtend record);
    
    /**
     * selectLoanOrderBorrowerExtendBLOBs:根据订单号查询放款申请的详细数据
     * @author kernel.org
     * @param entity
     * @return
     * @since JDK 1.8
     */
    LoanOrderBorrowerExtendWithBLOBs selectLoanOrderBorrowerExtendBLOBs(LoanOrderBorrowerExtendWithBLOBs entity);
    
    /**
     * 更新借款基本信息列表
     * @param record
     * @return
     */
    int updateOrderBorrowerExtendBLOBsByOrderNo(LoanOrderBorrowerExtendWithBLOBs record);
    /**
     * 查询借款人基本信息列表
     * @param map
     * @return
     */
    List<LoanOrderBorrowerExtendWithBLOBs> selectLoanOrderBorrowerExtendBLOBsList(Map<String, Object> map);

    /**
     * 
     * findBorrowerInfoExtendByOrderNo:查询订单中的借款人基本信息 <br/>
     * 这个方法适用条件 – 该方法只用于查询订单中借款人信息同步到碰碰旺系统<br/>
     *
     * @author xiangfeng
     * @param orderNo
     * @return
     * @since JDK 1.8
     */
	Map<String, Object> findBorrowerInfoExtendByOrderNo(String orderNo);

	LoanOrderBorrowerExtendWithBLOBs findBorrowerInfoExtendListByOrderNo(String orderNo);

	Map<String, Object> selectOrderBorrowerExtendBLOBsToMap(String orderNo);

	int queryBorrower(LoanOrderBorrowerExtend borrowerExtend);

}