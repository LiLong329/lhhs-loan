/**
 * Project Name:loan-service
 * File Name:OrderInfoService.java
 * Package Name:com.lhhs.loan.service.transport
 * Date:2017年6月28日下午4:04:41
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.service.transport;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.shared.page.Page;
import com.lhhs.loan.dao.domain.LoanAccountCard;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanOrderInfo;
import com.lhhs.loan.dao.domain.LoanOrderInfoDetail;
import com.lhhs.loan.dao.domain.OrderInfo;
import com.lhhs.loan.dao.domain.vo.OrderInfoDetailVo;
import com.lhhs.loan.dao.domain.vo.OrderInfoVo;

/**
 * ClassName:OrderInfoService <br/>
 * Function: 同步报单数据接口 <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年6月28日 下午4:04:41 <br/>
 * @author   zhanghui
 * @version
 * @since    JDK 1.8
 * @see
 */
public interface OrderInfoService {

	/**
	 * 
	 * saveOrderInfo:(保存报单). <br/>
	 * TODO(保存碰碰旺推送报单).<br/>
	 * @author zhanghui
	 * @param params
	 * @return
	 * @since JDK 1.8
	 */
	Map<String,Object>  saveOrderInfo(Map<String,String> params) throws Exception;
	/**
	 * 
	 * updateOrderInfo:(更新报单审批). <br/>
	 * TODO(与碰碰旺同步数据 – 可选).<br/>
	 * @author zhanghui
	 * @param params
	 * @return
	 * @throws Exception
	 * @since JDK 1.8
	 */
	Map<String,Object>  updateApproval(String orderNo) throws Exception;
	
	/**
	 * 
	 * updateOrderInfo:(更新报单审批). <br/>
	 * TODO(与碰碰旺同步数据 – 可选).<br/>
	 * @author zhanghui
	 * @param params
	 * @return
	 * @throws Exception
	 * @since JDK 1.8
	 */
	Map<String,Object>  updateApproval(String orderNo,String procId) throws Exception;
	
	
	/**
	 * 
	 * updateBasicInfo:(更新报单的基本信息). <br/>
	 * TODO(报单详情表和借款人扩展表).<br/>
	 * @author zhanghui
	 * @param loanOrderInfoDetail
	 * @param loanOrderBorrowerExtendWithBLOBs
	 * @return
	 * @throws Exception
	 * @since JDK 1.8
	 */
	Map<String,Object> updateBasicInfo(LoanOrderInfoDetail loanOrderInfoDetail, LoanOrderBorrowerExtendWithBLOBs loanOrderBorrowerExtendWithBLOBs) throws Exception;

	/**
	 * updateMortgageInfo:更新抵押物信息<br/>
	 * TODO(房、车扩展信息).<br/>
	 * @author zhanghui
	 * @param houseList
	 * @param carList
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
    Map<String,Object> updateMortgageInfo(List<LoanOrderHouseExtend> houseList,List<LoanOrderCarExtend> carList,String orderNo);
	
    /**
     * 根据当前登录用户查询报单列表
     * 
     * @param page
     * @param params
     * @return
     */
    Page findPageByEntity(Page page, OrderInfo params);
    /**
     * 查找报单详情
     * @param id
     * @return
     */
    OrderInfoDetailVo getEntityById(String id);
    
    /**
     * 更新接口
     * @param vo
     * @return
     */
	Map<String, Object> updateOrderInfo(OrderInfoVo vo);
    
    /**
     * 获取订单总额、当天订单总数
     * @return
     */
	Map<String, Object>  getOrderSumInfo(Map<String, Object> param);
	
	List<String> asyncTeamManager(Integer leEmpId);
	/**
	 * 报单统计
	 * @param page
	 * @param params
	 * @return
	 */
	Page getOrderCountList(Page page, LoanOrderInfo params);
	/**
	 * 放款总金额
	 * @param entity
	 * @return
	 */
	Map<String, Object> getVariousTotalCount(LoanOrderInfo entity);
	/**
	 * 根据时间统计成交量，报单量
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	Map<String, Object> getOrderInfoEchartsList(Date time, String timeUnit,LoanOrderInfo entity);
	
	List<OrderInfo> orderCountListExport(LoanOrderInfo entity);
	/**
	 * 根据报单编号查询借款人银行卡号信息
	 * @param orderInfo
	 * @return
	 */
	List<LoanAccountCard> getAccountCardList(LoanOrderInfo orderInfo);
}

