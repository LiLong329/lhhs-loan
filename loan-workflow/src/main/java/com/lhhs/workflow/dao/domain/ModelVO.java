package com.lhhs.workflow.dao.domain;

import java.util.Date;

import org.activiti.engine.repository.Model;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lhhs.loan.common.shared.page.BaseObject;
import com.lhhs.workflow.common.enumeration.ActCategory;
/**
 * 模型自定义类
 * @author dongf
 *
 */
public class ModelVO extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 226882389918853902L;
	private String id;
	private String name;
	private String key;
	private String category;
	private String catName;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date lastUpdateTime;
	private String version;
	public ModelVO(){
		super();
	}
	public ModelVO(Model model){
		this.id=model.getId();
		this.name = model.getName();
		this.key = model.getKey();
		this.category = model.getCategory();
		this.createTime=model.getCreateTime();
		this.lastUpdateTime=model.getLastUpdateTime();
		this.version="V:"+model.getVersion();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCatName() {
		
		return ActCategory.getName(this.category,this.category);
	}


}
