package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;


/**
 * 订单信息表
 * 
 * @author lichao
 * @email 284845673@qq.com
 * @date 2017-08-02 16:13:10
 */
public class OrderInfo extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	//订单编号 : 订单编号
	private String orderNo;
	private String province;
	//市级 : 市级
	private String city;
	//订单状态 : 0:初评
	//1:下户
	//2:终审
	//3:签约公正
	//4:权证抵押
	//5:待放款
	//6:已撤单
	//7:已拒贷
	//8:放款成功
	private Integer orderStatus;
	//申请时间 : 申请时间
	private Date applyDate;
	//业务类型
	private String orgBusinessType;
	//下级审批人
	private String nextAssigneeName;
	//借款人
	private String bname;
	//借款人手机号
	private String mobileNo;
	//报单人
	private String providerName;
	//客户经理
	private String leStaffName;
	//资金方
	private String orgName;
	//产品名称
	private String productName;
	
	private Date startDate;
	
	private Integer customerManager;
	
	private Date endDate;
	//状态查询
	private String orderStatusCondition;
	
	//------------临时字段-----------
	//业务类型名称
	private String businessTypeName;
	//订单状态名称
	private String orderStatusName;
	//借款人身份证号-订单查询使用
	private String idNum;
	
    private String propertyNum;

    private String propertyName;
    
    private String type;
    
  	private String orderNoSed;
    
	
	public String getOrderNoSed() {
		return orderNoSed;
	}
	public void setOrderNoSed(String orderNoSed) {
		this.orderNoSed = orderNoSed;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPropertyNum() {
		return propertyNum;
	}
	public void setPropertyNum(String propertyNum) {
		this.propertyNum = propertyNum;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	/**
	 * 设置：订单编号 : 订单编号
	 */
	public void setOrderNo (String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取：订单编号 : 订单编号
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	/**
	 * 获取：订单状态 : 0:初评
		1:下户
		2:终审
		3:签约公正
		4:权证抵押
		5:待放款
		6:已撤单
		7:已拒贷
		8:放款成功
	 */
	public Integer getOrderStatus() {
		return this.orderStatus;
	}
	
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getOrgBusinessType() {
		return orgBusinessType;
	}
	public void setOrgBusinessType(String orgBusinessType) {
		this.orgBusinessType = orgBusinessType;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getLeStaffName() {
		return leStaffName;
	}
	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOrderStatusCondition() {
		return orderStatusCondition;
	}
	public void setOrderStatusCondition(String orderStatusCondition) {
		this.orderStatusCondition = orderStatusCondition;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getOrderStatusName() {
		return orderStatusName;
	}
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public String getNextAssigneeName() {
		return nextAssigneeName;
	}
	public void setNextAssigneeName(String nextAssigneeName) {
		this.nextAssigneeName = nextAssigneeName;
	}
	public Integer getCustomerManager() {
	
		return customerManager;
	}
	public void setCustomerManager(Integer customerManager) {
	
		this.customerManager = customerManager;
	}
	
}
