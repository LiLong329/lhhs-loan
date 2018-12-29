package com.lhhs.loan.dao.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lhhs.loan.common.enums.crm.BusinessStatus;
import com.lhhs.loan.common.enums.crm.BusinessType;
import com.lhhs.loan.common.enums.crm.CustomerSource;
import com.lhhs.loan.common.shared.page.BaseObject;

public class CrmIntentLoanUser extends BaseObject{
    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = -7999937423703101266L;

	private Integer id;

    private String mobile;

    private String custId;

    private String name;

    private Integer sex;

    private Integer age;

    private String province;

    private String city;

    private String houseAddress;

    private String loanAmount;

    private String interestRate;

    private String duration;

    private String idNumber;

    private String businessType;

    private String seatNumber;

    private String extendParam;

    private String createrEmpId;

    private String createrEmpName;

    private String createrUnionId;

    private String createrCompanyId;
    
    private String createrDepId;

    private String createrGroupId;
    private String createrOrg;

    private String appointEmpId;

    private String appointEmpName;

    private String appointUnionId;

    private String appointCompanyId;

    private String appointDepId;

    private String appointGroupId;
    private String appointOrg;

    private String status;

    private String businessStatus;

    private Integer visitCount;

    private Date createTime;

    private Date visitTime;

    private String lastUser;

    private Date lastModifyTime;

    private String source;

    private String sourceOrg;

    private String remark;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    private String field6;

    private String field7;

    private String field8;

    private String field9;

    private String field10;

    private Integer parentId;
    
    private String rateUnit;
    
    private String durationUnit;
    
    private String grade;
    
    private Date endingTime;
    
    private String dateFlag;
    
    private Date beginingTime;
    
    private String businessType1;
    private String businessStatus1;
    
    private String source1;
    private String grade1;
    
    //扩展字段
    
    private String businessStatusName;
    private String businessTypeName;
    private String deptName;
    private String customerSource;
    private String createrCompanyName;
    private String createrDeptName;
    private String createrGroupName;
    private String appointCompanyName;
    private String appointDeptName;
    private String appointGroupName;
    private String followTypeSed;//区分基本信息-回访记录
    private String followType;//回访-回访方式
    private String followType1;//回访-回访方式(名称)
    private String followStatus;//回访-跟进状态
    //操作类型
    private String actionType;
    //业务状态列表
    private List<String> businessStatusList;
    
    private boolean needUpdate = true;
    
    //是否是统计意向客户
    private String isCountIntent;
    
    //签约量（放款成功量）
    private Integer dealCount;
    //面审量
    private Integer mianshenCount;
    //客户量
    private Integer intentCount;
    
    //客户经理所在城市
    private String appEmpCity;
    //客户经理所在省
    private String appEmpProvince;
    //客户经理创建时间
    private Date appBeginingTime;
    private Date appEndingTime;
    //客户经理创建时间
    private Date appointCreateTime;
    //时间单位 月 天 周
    private String timeUnit;
    
    private String weekNum;
    //业务类型
    private String productType;
    
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFollowTypeSed() {
		return followTypeSed;
	}

	public void setFollowTypeSed(String followTypeSed) {
		this.followTypeSed = followTypeSed;
	}

	public String getFollowType() {
		return followType;
	}

	public void setFollowType(String followType) {
		this.followType = followType;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public String getCreaterDeptName() {
		return createrDeptName;
	}

	public void setCreaterDeptName(String createrDeptName) {
		this.createrDeptName = createrDeptName;
	}

	public String getCreaterGroupName() {
		return createrGroupName;
	}

	public void setCreaterGroupName(String createrGroupName) {
		this.createrGroupName = createrGroupName;
	}

	public String getAppointDeptName() {
		return appointDeptName;
	}

	public void setAppointDeptName(String appointDeptName) {
		this.appointDeptName = appointDeptName;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public String getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(Date endingTime) {
		this.endingTime = endingTime;
	}

	public String getDateFlag() {
		return dateFlag;
	}

	public void setDateFlag(String dateFlag) {
		this.dateFlag = dateFlag;
	}

	public Date getBeginingTime() {
		return beginingTime;
	}

	public void setBeginingTime(Date beginingTime) {
		this.beginingTime = beginingTime;
	}

//	public String getBusinessType1() {
//		String str="";
//		if(null!=this.getBusinessType()){
//			switch(this.getBusinessType()){
//			case 1:
//				str ="房产抵押贷";
//			    break;
//			case 2:
//				str = "信用贷";
//			    break;
//			case 3:
//				str = "车辆抵押贷";
//			    break;
//			case 4:
//				str = "垫资过桥";
//			    break;
//			case 5:
//				str = "解查封";
//			    break;
//			case 6:
//				str = "其他";
//			    break;
//			 default:
//				 str = this.getBusinessType()+"";
//			    break;
//			}
//		}
//		
//		return str;
//	}

	public void setBusinessType1(String businessType1) {
		this.businessType1 = businessType1;
	}

	public String getBusinessStatus1() {
		String str="";
		if(StringUtils.isNotEmpty(this.getBusinessStatus())){
			switch(this.getBusinessStatus()){
			case "01":
				str ="初步接触";
			    break;
			case "02":
				str = "意向客户";
			    break;
			case "03":
				str = "跟进客户";
			    break;
			case "07":
				str = "面谈客户";
			    break;
			case "04":
				str = "签约客户";
			    break;
			case "05":
				str = "放弃客户";
			    break;
			case "06":
				str = "黑名单";
			    break; 
			    
			case "08":
				str = "已报单";
			    break; 
			    
			 default:
				 str = this.getBusinessStatus();
			    break;
			}
		}
		
		return str;
		
	}
	

	public void setBusinessStatus1(String businessStatus1) {
		this.businessStatus1 = businessStatus1;
	}

	public String getSource1() {
		String str="";
		if(StringUtils.isNotEmpty(this.getSource())){
			switch(this.getSource()){
			case "01":
				str ="陌拜";
			    break;
			case "02":
				str = "电销";
			    break;
			case "03":
				str = "转介绍";
			    break;
			case "04":
				str = "网络";
			    break;
			case "05":
				str = "报刊";
			    break;
			case "06":
				str = "其他";
			    break;
			case "07":
				str = "中视天脉";
				break;
			default:
				 str = this.getSource();
			    break;
			}
		}
		
		return str;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getGrade1() {
		String str="";
		if(StringUtils.isNotEmpty(this.getGrade())){
			switch(this.getGrade()){
			case "01":
				str ="普通客户";
			    break;
			case "02":
				str = "重要客户";
			    break;
			case "03":
				str = "重要紧急";
			    break;
			 default:
				 str = this.getGrade();
			    break;
			}
		}
		
		return str;
		
	}

	public void setGrade1(String grade1) {
		this.grade1 = grade1;
	}

	public String getAppointGroupName() {
		return appointGroupName;
	}

	public void setAppointGroupName(String appointGroupName) {
		this.appointGroupName = appointGroupName;
	}

	public String getBusinessStatusName() {
		if(this.businessStatus!=null&&!this.businessStatus.equals("")){
			businessStatusName=BusinessStatus.getName(businessStatus);
		}
		return businessStatusName;
	}
	
	public String getBusinessTypeName() {
		if(this.businessType!=null&&!this.businessType.equals("")){
			businessTypeName=BusinessType.getName(String.valueOf(businessType));
		}
		return businessTypeName;
	}
	
	public String getCustomerSource() {
		if(this.source!=null&&!this.source.equals("")){
			customerSource=CustomerSource.getName(source);
		}
		return customerSource;
	}
	
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount == null ? null : loanAmount.trim();
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate == null ? null : interestRate.trim();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    
    public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber == null ? null : seatNumber.trim();
    }

    public String getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam == null ? null : extendParam.trim();
    }

    public String getCreaterEmpId() {
        return createrEmpId;
    }

    public void setCreaterEmpId(String createrEmpId) {
        this.createrEmpId = createrEmpId == null ? null : createrEmpId.trim();
    }

    public String getCreaterEmpName() {
        return createrEmpName;
    }

    public void setCreaterEmpName(String createrEmpName) {
        this.createrEmpName = createrEmpName == null ? null : createrEmpName.trim();
    }

    public String getCreaterUnionId() {
        return createrUnionId;
    }

    public void setCreaterUnionId(String createrUnionId) {
        this.createrUnionId = createrUnionId == null ? null : createrUnionId.trim();
    }

    public String getCreaterCompanyId() {
        return createrCompanyId;
    }

    public void setCreaterCompanyId(String createrCompanyId) {
        this.createrCompanyId = createrCompanyId == null ? null : createrCompanyId.trim();
    }

    public String getCreaterDepId() {
        return createrDepId;
    }

    public void setCreaterDepId(String createrDepId) {
        this.createrDepId = createrDepId == null ? null : createrDepId.trim();
    }

    public String getCreaterGroupId() {
        return createrGroupId;
    }

    public void setCreaterGroupId(String createrGroupId) {
        this.createrGroupId = createrGroupId == null ? null : createrGroupId.trim();
    }

    public String getAppointEmpId() {
        return appointEmpId;
    }

    public void setAppointEmpId(String appointEmpId) {
        this.appointEmpId = appointEmpId == null ? null : appointEmpId.trim();
    }

    public String getAppointEmpName() {
        return appointEmpName;
    }

    public void setAppointEmpName(String appointEmpName) {
        this.appointEmpName = appointEmpName == null ? null : appointEmpName.trim();
    }

    public String getAppointUnionId() {
        return appointUnionId;
    }

    public void setAppointUnionId(String appointUnionId) {
        this.appointUnionId = appointUnionId == null ? null : appointUnionId.trim();
    }

    public String getAppointCompanyId() {
        return appointCompanyId;
    }

    public void setAppointCompanyId(String appointCompanyId) {
        this.appointCompanyId = appointCompanyId == null ? null : appointCompanyId.trim();
    }

    public String getAppointDepId() {
        return appointDepId;
    }

    public void setAppointDepId(String appointDepId) {
        this.appointDepId = appointDepId == null ? null : appointDepId.trim();
    }

    public String getAppointGroupId() {
        return appointGroupId;
    }

    public void setAppointGroupId(String appointGroupId) {
        this.appointGroupId = appointGroupId == null ? null : appointGroupId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus == null ? null : businessStatus.trim();
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getSourceOrg() {
        return sourceOrg;
    }

    public void setSourceOrg(String sourceOrg) {
        this.sourceOrg = sourceOrg == null ? null : sourceOrg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6 == null ? null : field6.trim();
    }

    public String getField7() {
        return field7;
    }

    public void setField7(String field7) {
        this.field7 = field7 == null ? null : field7.trim();
    }

    public String getField8() {
        return field8;
    }

    public void setField8(String field8) {
        this.field8 = field8 == null ? null : field8.trim();
    }

    public String getField9() {
        return field9;
    }

    public void setField9(String field9) {
        this.field9 = field9 == null ? null : field9.trim();
    }

    public String getField10() {
        return field10;
    }

    public void setField10(String field10) {
        this.field10 = field10 == null ? null : field10.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }



	public void setBusinessStatusName(String businessStatusName) {
		this.businessStatusName = businessStatusName;
	}
	
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getCreaterCompanyName() {
		return createrCompanyName;
	}

	public void setCreaterCompanyName(String createrCompanyName) {
		this.createrCompanyName = createrCompanyName;
	}

	public String getAppointCompanyName() {
		return appointCompanyName;
	}

	public void setAppointCompanyName(String appointCompanyName) {
		this.appointCompanyName = appointCompanyName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getCreaterOrg() {
		return createrOrg;
	}

	public void setCreaterOrg(String createrOrg) {
		this.createrOrg = createrOrg;
	}

	public String getAppointOrg() {
		return appointOrg;
	}

	public void setAppointOrg(String appointOrg) {
		this.appointOrg = appointOrg;
	}

	public List<String> getBusinessStatusList() {
		return businessStatusList;
	}

	public void setBusinessStatusList(List<String> businessStatusList) {
		this.businessStatusList = businessStatusList;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	public String getIsCountIntent() {
		return isCountIntent;
	}

	public void setIsCountIntent(String isCountIntent) {
		this.isCountIntent = isCountIntent;
	}

	public Integer getDealCount() {
		return dealCount;
	}

	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}

	public Integer getMianshenCount() {
		return mianshenCount;
	}

	public void setMianshenCount(Integer mianshenCount) {
		this.mianshenCount = mianshenCount;
	}

	public Integer getIntentCount() {
		return intentCount;
	}

	public void setIntentCount(Integer intentCount) {
		this.intentCount = intentCount;
	}

	public String getAppEmpCity() {
		return appEmpCity;
	}

	public void setAppEmpCity(String appEmpCity) {
		this.appEmpCity = appEmpCity;
	}

	public Date getAppBeginingTime() {
		return appBeginingTime;
	}

	public void setAppBeginingTime(Date appBeginingTime) {
		this.appBeginingTime = appBeginingTime;
	}

	public Date getAppEndingTime() {
		return appEndingTime;
	}

	public void setAppEndingTime(Date appEndingTime) {
		this.appEndingTime = appEndingTime;
	}

	public Date getAppointCreateTime() {
		return appointCreateTime;
	}

	public void setAppointCreateTime(Date appointCreateTime) {
		this.appointCreateTime = appointCreateTime;
	}

	public String getAppEmpProvince() {
		return appEmpProvince;
	}

	public void setAppEmpProvince(String appEmpProvince) {
		this.appEmpProvince = appEmpProvince;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getFollowType1() { 
		String str="";
		if(StringUtils.isNotEmpty(this.getFollowType())){
			switch(this.getFollowType()){
			case "1":
				str ="电话";
			    break;
			case "2":
				str = "QQ";
			    break;
			case "3":
				str = "微信";
			    break;
			case "4":
				str = "当面拜访";
			    break;
			case "5":
				str = "其他";
			    break;
			 default:
				 str = this.getFollowType();
			    break;
			}
		}
		
		return str;
	}

	public void setFollowType1(String followType1) {
		this.followType1 = followType1;
	}

	public String getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(String weekNum) {
		this.weekNum = weekNum;
	}
  
	
	
	
}