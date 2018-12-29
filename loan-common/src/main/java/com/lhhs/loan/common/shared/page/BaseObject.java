package com.lhhs.loan.common.shared.page;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * ClassName: BaseObject <br/>
 * Function: 对象基类 <br/>
 * date: 2017年7月28日 上午11:12:23 <br/>
 *
 * @author dongfei
 * @version 
 * @since JDK 1.8
 */
public class BaseObject implements Serializable ,Cloneable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 6849203468080759947L;
	//分页对象
	private Page page = new Page();
	
	private Integer pageIndex = 1;
	
	private Integer pageSize = 10;
	
	//开始时间1
	private String beginTime;
	//结束时间1
	private String endTime;
	//开始时间2
	private String beginTimeTwo;
	//结束时间2
	private String endTimeTwo;
	
	private String _id;
	
	//省编号
	private String provienceNo;

	//市编号
    private String cityNo;

    //公司编号
    private String companyId;

    //集团编号
    private String unionId;
    /** 行政组织 （事业部或者组）**/
	private String depId;
    
    //登录人编号
    private Integer leEmpId;
    //登录人姓名
    private String leEmpName;
    //登录人手机号
    private String empMobile;
    
    //当前登录人角色列表
    private List<Integer> lrRoleId;//角色列表
    
    //当前登录人所管理的公司
    private List<String> companyIdList;//分公司列表
    //当前登录人所管理的事业部和组
    private List<String> oaOrgIdList;//事业部组列表
    private String status;
    
    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;
    //状态码
    private String retCode;
    //描述
    private String retMsg;
    
    private List authgroupList;//数据权限组列表
    
	public BaseObject() {
		super();
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBeginTimeTwo() {
		return beginTimeTwo;
	}
	public void setBeginTimeTwo(String beginTimeTwo) {
		this.beginTimeTwo = beginTimeTwo;
	}
	public String getEndTimeTwo() {
		return endTimeTwo;
	}
	public void setEndTimeTwo(String endTimeTwo) {
		this.endTimeTwo = endTimeTwo;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getProvienceNo() {
		return provienceNo;
	}

	public void setProvienceNo(String provienceNo) {
		this.provienceNo = provienceNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getLeEmpId() {
		return leEmpId;
	}

	public void setLeEmpId(Integer leEmpId) {
		this.leEmpId = leEmpId;
	}

	public List<Integer> getLrRoleId() {
		return lrRoleId;
	}

	public void setLrRoleId(List<Integer> lrRoleId) {
		this.lrRoleId = lrRoleId;
	}

	public List<String> getCompanyIdList() {
		return companyIdList;
	}

	public void setCompanyIdList(List<String> companyIdList) {
		this.companyIdList = companyIdList;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public List<String> getOaOrgIdList() {
		return oaOrgIdList;
	}

	public void setOaOrgIdList(List<String> oaOrgIdList) {
		this.oaOrgIdList = oaOrgIdList;
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}

	public String getLeEmpName() {
		return leEmpName;
	}

	public void setLeEmpName(String leEmpName) {
		this.leEmpName = leEmpName;
	}

	public List getAuthgroupList() {
		return authgroupList;
	}

	public void setAuthgroupList(List authgroupList) {
		this.authgroupList = authgroupList;
	}

}
