/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.lhhs.wftest.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lhhs.workflow.common.utils.IdGen;
import com.lhhs.workflow.dao.domain.ActVo;
import com.lhhs.workflow.dao.domain.User;
import com.lhhs.workflow.sys.utils.bs.UserUtils;

/**
 * 审批Entity
 * @author thinkgem
 * @version 2014-05-16
 */
public class TestAudit extends  ActVo{
	
	private static final long serialVersionUID = 1L;
	private String id; // 事业部
	private String productdpt; // 事业部
	private String branchcom; // 销售组织
	private String productline; // 产品线
	private String salesoffice;//销售办公室
	private String department; // 行政组织
	
	private String assigneelist;//子流程创建集合、多实例集合、会签人员列表
	private String subkey;//子流程、多实例集合权限过滤KEY
	private String addNum;
	/**
	 * 归属用户
	 */
	private User 	user;	//	归属用户
	private String 	office;	//	归属部门
	private String  officeId;// 部门id
	private String 	post;	//	岗位
	private String 	age;	//	性别
	private String 	edu;	//	学历
	private String 	content;	//	调整原因
	private String 	olda;	//	现行标准 薪酬档级
	private String 	oldb;	//	现行标准 月工资额
	private String 	oldc;	//	现行标准 年薪总额
	private String 	newa;	//	调整后标准 薪酬档级
	private String 	newb;	//	调整后标准 月工资额
	private String 	newc;	//	调整后标准 年薪总额
	
	private String 	exeDate;	//	执行时间
	private String 	hrText;		//	人力资源部门意见
	private String 	leadText;	//	分管领导意见
	private String 	mainLeadText;//	集团主要领导意见

	private String remarks;	// 备注
	private User createBy;	// 创建者
	private Date createDate;	// 创建日期
	private User updateBy;	// 更新者
	private Date updateDate;	// 更新日期
	private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewRecord = false;
	public TestAudit() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOlda() {
		return olda;
	}

	public void setOlda(String olda) {
		this.olda = olda;
	}

	public String getOldb() {
		return oldb;
	}

	public void setOldb(String oldb) {
		this.oldb = oldb;
	}

	public String getOldc() {
		return oldc;
	}

	public void setOldc(String oldc) {
		this.oldc = oldc;
	}

	public String getNewa() {
		return newa;
	}

	public void setNewa(String newa) {
		this.newa = newa;
	}

	public String getNewb() {
		return newb;
	}

	public void setNewb(String newb) {
		this.newb = newb;
	}

	public String getNewc() {
		return newc;
	}

	public void setNewc(String newc) {
		this.newc = newc;
	}

	public String getExeDate() {
		return exeDate;
	}

	public void setExeDate(String exeDate) {
		this.exeDate = exeDate;
	}

	public String getHrText() {
		return hrText;
	}

	public void setHrText(String hrText) {
		this.hrText = hrText;
	}

	public String getLeadText() {
		return leadText;
	}

	public void setLeadText(String leadText) {
		this.leadText = leadText;
	}

	public String getMainLeadText() {
		return mainLeadText;
	}

	public void setMainLeadText(String mainLeadText) {
		this.mainLeadText = mainLeadText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}


	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		/*User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}*/
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getUserId())){
			this.updateBy = user;
		}
		this.updateDate = new Date();
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * @return
     */
	public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getId());
    }

	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}


	public String getSubkey() {
		return subkey;
	}

	public void setSubkey(String subkey) {
		this.subkey = subkey;
	}

	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public String getAssigneelist() {
		return assigneelist;
	}

	public void setAssigneelist(String assigneelist) {
		this.assigneelist = assigneelist;
	}

	public String getAddNum() {
		return addNum;
	}

	public void setAddNum(String addNum) {
		this.addNum = addNum;
	}


	public String getProductdpt() {
		return productdpt;
	}

	public void setProductdpt(String productdpt) {
		this.productdpt = productdpt;
	}

	public String getBranchcom() {
		return branchcom;
	}

	public void setBranchcom(String branchcom) {
		this.branchcom = branchcom;
	}

	public String getProductline() {
		return productline;
	}

	public void setProductline(String productline) {
		this.productline = productline;
	}

	public String getSalesoffice() {
		return salesoffice;
	}

	public void setSalesoffice(String salesoffice) {
		this.salesoffice = salesoffice;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}


